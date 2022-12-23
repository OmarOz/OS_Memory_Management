import java.util.Collections;
import java.util.Comparator;
import java.util.Vector;

public class policy {

    Vector<partitions> partitionsVector=new Vector<>();
    Vector<process> processVector=new Vector<>();
    Vector<process> cantAllocate=new Vector<>();
    int compact_sum=0;

    static int maxVectorNum=0;



    public policy(Vector<partitions> partitionsVector, Vector<process> processVector){
        this.partitionsVector=partitionsVector;
        this.processVector=processVector;



    }




    public void print(){


            for (partitions it : partitionsVector) {
                System.out.println(it.partitionName + "(" + it.partitionSize + "KB)" + " => " + it.processName);
            }

            System.out.println();

            for (process it : cantAllocate) {
                System.out.println(it.processName + " can not be allocated");
            }



    }


    public void compact(){

        for(int i=0;i<partitionsVector.size();i++){
            //get sum of size of unused partitions
            if(partitionsVector.get(i).taken==false){
                compact_sum+=partitionsVector.get(i).partitionSize;
                partitionsVector.remove(i);
                i--;
            }
        }
        //make new partition with a size of sum of unused partitions
        String new_name=countName(partitionsVector);
        partitionsVector.add(new partitions(new_name, compact_sum));

        //nested for loop of "vector of process" and "vector of partitions"
        //to allocate non-allocated process if there is available space after compact unused partitions
        for(int j=0;j<cantAllocate.size();j++){
            int sub=0;
            for (int i=0;i<partitionsVector.size();i++) {
                //every process will use first suitable partition and not used by another process
                if(partitionsVector.get(i).partitionSize>=cantAllocate.get(j).processSize && partitionsVector.get(i).taken==false){
                    sub=partitionsVector.get(i).partitionSize-cantAllocate.get(j).processSize;//memory subtraction
                    partitionsVector.get(i).partitionSize=cantAllocate.get(j).processSize;//resize partition memory size
                    partitionsVector.get(i).taken=true;
                    partitionsVector.get(i).processName=cantAllocate.get(j).processName;
                    cantAllocate.remove(j);
                    if(sub!=0) {
                        String newName=countName(partitionsVector);//making new partition with new name if there is available size of used partition
                        partitionsVector.add(new partitions(newName, sub));
                    }
                    break;
                }

            }



        }

    }



    public void firstFit(){

        //nested for loop of "vector of process" and "vector of partitions"
        for(process pro: processVector) {
            int sub=0;
            boolean flag=false;
            for (int i=0;i<partitionsVector.size();i++) {
                //every process will use first suitable partition and not used by another process
                if(partitionsVector.get(i).partitionSize>=pro.processSize && partitionsVector.get(i).taken==false){
                    sub=partitionsVector.get(i).partitionSize-pro.processSize;//memory subtraction
                    partitionsVector.get(i).partitionSize=pro.processSize;//resize partition memory size
                    partitionsVector.get(i).taken=true;
                    partitionsVector.get(i).processName=pro.processName;
                    flag=true;
                    if(sub!=0) {
                            String newName=countName(partitionsVector);//making new partition with new name if there is available size of used partition
                        partitionsVector.add(new partitions(newName, sub));
                    }
                    break;
                }

            }
            //if the process does not use any partition, it will be added to "cantAllocate" Vector
            if(!flag){cantAllocate.add(pro);}
        }


    }

    public void worstFit(){

        //sort "partitionVector" in descending order to get the biggest sizes first
        Collections.sort(this.partitionsVector, new Comparator<partitions>() {
            public int compare(partitions p1, partitions p2) {
                return Integer.valueOf(p2.partitionSize).compareTo(p1.partitionSize);
            }
        });

        //nested for loop of "vector of process" and "vector of partitions"
        for(process pro: processVector) {
            int sub=0;
            boolean flag=false;
            for (int i=0;i<partitionsVector.size();i++) {
                //every process will use a partition with a biggest Size and not used by another process
                if(partitionsVector.get(i).partitionSize>=pro.processSize && partitionsVector.get(i).taken==false){
                    sub=partitionsVector.get(i).partitionSize-pro.processSize;//memory subtraction
                    partitionsVector.get(i).partitionSize=pro.processSize;//resize partition memory size
                    partitionsVector.get(i).taken=true;
                    partitionsVector.get(i).processName=pro.processName;
                    flag=true;
                    if(sub!=0) {
                        String newName=countName(partitionsVector);//making new partition with new name if there is available size of used partition
                        partitionsVector.add(new partitions(newName, sub));
                    }
                    break;
                }

            }
            //if the process does not use any partition, it will be added to "cantAllocate" Vector
            if(!flag){cantAllocate.add(pro);}
        }


    }

    public void bestFit(){

        //sort "partitionVector" in ascending order to get the smallest sizes first
        Collections.sort(this.partitionsVector, new Comparator<partitions>() {
            public int compare(partitions p1, partitions p2) {
                return Integer.valueOf(p1.partitionSize).compareTo(p2.partitionSize);
            }
        });

        //nested for loop of "vector of process" and "vector of partitions"
        for(process pro: processVector) {
            int sub=0;
            boolean flag=false;
            for (int i=0;i<partitionsVector.size();i++) {
                //every process will use a partition with a smalles Size possible and not used by another process
                if(partitionsVector.get(i).partitionSize>=pro.processSize && partitionsVector.get(i).taken==false){
                    sub=partitionsVector.get(i).partitionSize-pro.processSize;//memory subtraction
                    partitionsVector.get(i).partitionSize=pro.processSize;//resize partition memory size
                    partitionsVector.get(i).taken=true;
                    partitionsVector.get(i).processName=pro.processName;
                    flag=true;
                    if(sub!=0) {
                        String newName=countName(partitionsVector);//making new partition with new name if there is available size of used partition
                        partitionsVector.add(new partitions(newName, sub));
                    }
                    break;
                }

            }
            //if the process does not use any partition, it will be added to "cantAllocate" Vector
            if(!flag){cantAllocate.add(pro);}
        }

    }


    public String countName(Vector<partitions> PartitionsVector){

        //copy "partitionsVector" to anothor Vector to prevent modification on original""partitionsVector""
        Vector<partitions> sortedPartitionsVector=new Vector<>();
        sortedPartitionsVector=partitionsVector;


        //sort the copiedVector
        Collections.sort(sortedPartitionsVector, new Comparator<partitions>() {
            public int compare(partitions p1, partitions p2) {
                return Integer.valueOf(Integer.parseInt(p1.partitionName.replaceAll("[\\D]", ""))).compareTo(Integer.parseInt(p2.partitionName.replaceAll("[\\D]", "")));
            }
        });

        //get last partition name
        String prevName=sortedPartitionsVector.get(sortedPartitionsVector.size()-1).partitionName;
        //copy name to another Srting to prevent modification on original String
        String prevNameDigitRemove=prevName;
        //get the number from the string
        int currNum=Integer.parseInt(prevNameDigitRemove.replaceAll("[\\D]", ""));
        //get max number
        maxVectorNum= Math.max(++maxVectorNum,++currNum);
        //concat name with the next number
        String currName=prevName.replaceAll("\\d","")+maxVectorNum;

        return currName;
    }



}
