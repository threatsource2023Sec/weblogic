package weblogic.application;

public interface UpdateListener {
   boolean acceptURI(String var1);

   void prepareUpdate(String var1) throws ModuleException;

   void activateUpdate(String var1) throws ModuleException;

   void rollbackUpdate(String var1);

   public interface Registration {
      Registration NOOP = new Registration() {
         public void addUpdateListener(UpdateListener ul) {
         }

         public void removeUpdateListener(UpdateListener ul) {
         }
      };

      void addUpdateListener(UpdateListener var1);

      void removeUpdateListener(UpdateListener var1);
   }
}
