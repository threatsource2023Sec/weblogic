package org.python.tests;

public class SubVisible extends Visible implements VisibleOverride {
   public static final int sharedNameField = 205;

   public static int visibleStatic(int input) {
      return 203;
   }

   public static int visibleStatic(double input, String sinput) {
      return 204;
   }

   public int visibleInstance(int input) {
      return 201;
   }

   public int visibleInstance(double input, String sinput) {
      return 202;
   }

   public int getSharedNameField() {
      return 2050;
   }

   public int packageMethod() {
      return super.packageMethod();
   }
}
