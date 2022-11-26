package weblogic.jdbc.common.internal;

public abstract class SwitchingContextManager {
   private static SwitchingContextManager instance = new SwitchingContextManagerImpl();

   public static void setInstance(SwitchingContextManager scm) {
      instance = scm;
   }

   public static SwitchingContextManager getInstance() {
      return instance;
   }

   public abstract SwitchingContext get();

   public abstract void push(SwitchingContext var1);

   public abstract SwitchingContext pop();
}
