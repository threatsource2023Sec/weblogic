package weblogic.application.ready;

public class ReadyLifecycleManager {
   public static ReadyLifecycle getInstance() {
      return ReadyLifecycleImpl.getInstance();
   }
}
