package weblogic.application;

public interface ModuleListener {
   State STATE_NEW = new State() {
      public String toString() {
         return "STATE_NEW";
      }
   };
   State STATE_PREPARED = new State() {
      public String toString() {
         return "STATE_PREPARED";
      }
   };
   State STATE_ADMIN = new State() {
      public String toString() {
         return "STATE_ADMIN";
      }
   };
   State STATE_ACTIVE = new State() {
      public String toString() {
         return "STATE_ACTIVE";
      }
   };
   State STATE_UPDATE_PENDING = new State() {
      public String toString() {
         return "STATE_UPDATE_PENDING";
      }
   };

   void beginTransition(ModuleListenerCtx var1, State var2, State var3);

   void endTransition(ModuleListenerCtx var1, State var2, State var3);

   void failedTransition(ModuleListenerCtx var1, State var2, State var3);

   public interface State {
      String toString();
   }
}
