package weblogic.application.ready;

public class ReadyManager {
   public static Ready getInstance() {
      return ReadyLifecycleImpl.getInstance();
   }
}
