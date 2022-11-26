package commonj.work;

public interface WorkItem extends Comparable {
   Work getResult() throws WorkException;

   int getStatus();
}
