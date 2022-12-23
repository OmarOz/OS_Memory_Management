import java.util.Vector;

public class partitions {

    public String partitionName;
    public String processName="External fragment";
    public int partitionSize;

    public boolean taken=false;

    public partitions(String partitionName,int partitionSize){
        this.partitionName=partitionName;
        this.partitionSize=partitionSize;
    }
}
