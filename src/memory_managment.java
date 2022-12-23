import java.util.Scanner;
import java.util.Vector;

public class memory_managment {


    public static void main(String args[]){


        System.out.println("Enter number of partition: ");
        Scanner pin=new Scanner(System.in);
        int numOfPartitions=pin.nextInt();

        Vector<partitions> partitionsVector=new Vector<>();

        //Making Vector of Partitions with Names and it's Size
        for(int i=0;i<numOfPartitions;i++){

            System.out.println("Partition name and its size: ");
            String name=pin.next();
            int size= pin.nextInt();

            partitionsVector.add(new partitions(name,size));
        }


        System.out.println("Enter number of processes: ");
        int numOfProcess=pin.nextInt();

        Vector<process> processVector=new Vector<>();

        //Making Vector of Process with Names and it's Size
        for(int i=0;i<numOfProcess;i++){

            System.out.println("Process name and its size: ");
            String name=pin.next();
            int size= pin.nextInt();

            processVector.add(new process(name,size));
        }

        System.out.println("Select the policy you want to apply: ");
        System.out.println("1. First fit ");
        System.out.println("2. Worst fit ");
        System.out.println("3. Best fit");
        int Policy=pin.nextInt();

        policy p =new policy(partitionsVector, processVector);
        if(Policy==1){p.firstFit();}
        else if(Policy==2){p.worstFit();}
        else if(Policy==3){p.bestFit();}


        p.print();

        System.out.println("Do you want to compact?  1.yes 2.no");
        int method= pin.nextInt();

        if(method==1){
            p.compact();
            p.print();
        }

    }
}
