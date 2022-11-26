package org.apache.taglibs.standard;

public class Version {
   public static String getVersion() {
      return getProduct() + " " + getMajorVersionNum() + "." + getReleaseVersionNum() + "." + getMaintenanceVersionNum() + (getDevelopmentVersionNum() > 0 ? "_D" + getDevelopmentVersionNum() : "");
   }

   public static void main(String[] argv) {
      System.out.println(getVersion());
   }

   public static String getProduct() {
      return "standard-taglib";
   }

   public static int getMajorVersionNum() {
      return 1;
   }

   public static int getReleaseVersionNum() {
      return 2;
   }

   public static int getMaintenanceVersionNum() {
      return 0;
   }

   public static int getDevelopmentVersionNum() {
      return 0;
   }
}
