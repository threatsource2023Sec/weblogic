package weblogic.servlet.internal.async;

class CompletedState extends DefaultState {
   public void returnToContainer(AsyncStateSupport async) {
   }

   public String toString() {
      return "AsyncCompleted";
   }
}
