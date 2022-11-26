package weblogic.application;

public abstract class ApplicationContextFactory {
   private static final String IMPL_CLASS = "weblogic.application.internal.ApplicationContextFactoryImpl";
   private static final ApplicationContextFactory theOne;

   public static ApplicationContextFactory getApplicationContextFactory() {
      return theOne;
   }

   public abstract ApplicationContext newApplicationContext(String var1);

   static {
      try {
         theOne = (ApplicationContextFactory)Class.forName("weblogic.application.internal.ApplicationContextFactoryImpl").newInstance();
      } catch (Exception var1) {
         throw new AssertionError(var1);
      }
   }
}
