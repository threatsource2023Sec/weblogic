package weblogic.management.bootstrap;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class WeblogicHome {
   private static String weblogicHome;

   private static void assignHome() {
      weblogicHome = System.getProperty("weblogic.home", getHomeWithReflection());
   }

   private static String getHomeWithReflection() {
      String currentDirectory = "." + File.pathSeparator;

      try {
         Class homeClass = Class.forName("weblogic.Home");
         Method getFileMethod = homeClass.getMethod("getPath", (Class[])null);
         return (String)getFileMethod.invoke((Object)null, (Object[])null);
      } catch (ClassNotFoundException var3) {
         return currentDirectory;
      } catch (NoSuchMethodException var4) {
         return currentDirectory;
      } catch (IllegalArgumentException var5) {
         return currentDirectory;
      } catch (IllegalAccessException var6) {
         return currentDirectory;
      } catch (InvocationTargetException var7) {
         return currentDirectory;
      }
   }

   public static String getWebLogicHome() {
      return weblogicHome;
   }

   static {
      assignHome();
   }
}
