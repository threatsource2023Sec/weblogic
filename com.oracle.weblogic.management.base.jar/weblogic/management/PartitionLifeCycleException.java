package weblogic.management;

public class PartitionLifeCycleException extends Exception {
   public PartitionLifeCycleException() {
   }

   public PartitionLifeCycleException(String msg) {
      super(msg);
   }

   public PartitionLifeCycleException(Throwable th) {
      super(th);
   }

   public PartitionLifeCycleException(String msg, Throwable th) {
      super(msg, th);
   }
}
