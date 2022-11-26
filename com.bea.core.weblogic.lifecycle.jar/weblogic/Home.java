package weblogic;

import java.io.File;
import weblogic.utils.classloaders.BeaHomeHolder;

public final class Home {
   private String path;

   private Home() {
      String p = System.getProperty("weblogic.home");
      if (p == null) {
         p = getMiddlewareHomePath() + "/wlserver/server";
      }

      this.setHomePath(p);
   }

   private synchronized boolean isHomePathNull() {
      return this.path == null;
   }

   private synchronized String getHomePath() {
      return this.path;
   }

   private synchronized void setHomePath(String newPath) {
      this.path = newPath;
   }

   private static Home getInstance(boolean check) {
      Home home = Home.HomeSingleton.SINGLETON;
      if (check && home.isHomePathNull()) {
         throw new RuntimeException("error in finding weblogic.Home");
      } else {
         return home;
      }
   }

   private static Home getInstance() {
      return getInstance(true);
   }

   public static String getPath() {
      return getInstance().getHomePath();
   }

   public static void resetPath(String newPath) {
      getInstance(false).setHomePath(newPath);
   }

   public static File getFile() {
      return new File(getPath());
   }

   public static Home getHome() {
      return getInstance();
   }

   public static String getMiddlewareHomePath() {
      return BeaHomeHolder.getBeaHome();
   }

   public String toString() {
      return getPath();
   }

   public static void main(String[] argv) {
      System.out.println(getHome());
   }

   // $FF: synthetic method
   Home(Object x0) {
      this();
   }

   private static final class HomeSingleton {
      private static final Home SINGLETON = new Home();
   }
}
