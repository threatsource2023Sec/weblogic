package weblogic.xml.saaj.mime4j.util;

public abstract class TempStorage {
   private static TempStorage inst = null;

   public abstract TempPath getRootTempPath();

   public static TempStorage getInstance() {
      return inst;
   }

   public static void setInstance(TempStorage inst) {
      if (inst == null) {
         throw new NullPointerException("inst");
      } else {
         TempStorage.inst = inst;
      }
   }

   static {
      String clazz = System.getProperty("weblogic.xml.saaj.mime4j.tempStorage");

      try {
         if (inst != null) {
            inst = (TempStorage)Class.forName(clazz).newInstance();
         }
      } catch (Throwable var2) {
      }

      if (inst == null) {
         inst = new SimpleTempStorage();
      }

   }
}
