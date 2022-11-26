package weblogic.application.io;

import java.io.File;

public enum AA {
   on,
   off;

   public static boolean isOn;
   public static boolean isOff;

   public static boolean useApplicationArchive(File f) {
      boolean result = f.getName().endsWith("-app.xml") ? true : initialize();
      return result;
   }

   public static boolean initialize() {
      AA defaultValue = off;
      isOn = valueOf(System.getProperty("ApplicationArchive", defaultValue.toString())) == on;
      isOff = !isOn;
      return isOn;
   }

   static {
      initialize();
   }
}
