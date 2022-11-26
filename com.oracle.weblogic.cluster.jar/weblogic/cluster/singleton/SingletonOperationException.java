package weblogic.cluster.singleton;

public class SingletonOperationException extends Exception {
   public SingletonOperationException(String msg) {
      super(msg);
   }

   public SingletonOperationException(String msg, Throwable t) {
      super(msg, t);
   }
}
