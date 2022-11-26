package org.python.tests;

public class Visible extends Invisible {
   public int visibleField;
   public static final int sharedNameField = 112;
   public static int visibleStaticField = 102;
   public int visibleInstance;
   public static int visibleStatic = 111;

   public Visible() {
      this(101);
   }

   public Visible(int visibileFieldValue) {
      this.visibleInstance = 110;
      this.visibleField = visibileFieldValue;
   }

   public int visibleInstance(int input) {
      return 103;
   }

   public int visibleInstance(String input) {
      return 104;
   }

   public int visibleInstance(int iinput, String input) {
      return 105;
   }

   public int getSharedNameField() {
      return 1120;
   }

   public static int visibleInstance(String sinput, String input) {
      return 106;
   }

   public static int visibleStatic(int input) {
      return 107;
   }

   public static int visibleStatic(String input) {
      return 108;
   }

   public static int visibleStatic(int iinput, String input) {
      return 109;
   }

   public static int getVisibleStaticField() {
      return visibleStaticField;
   }

   public static void setVisibleStaticField(int newValue) {
      visibleStaticField = newValue;
   }

   public static class StaticInner {
      public static int visibleStaticField = 102;
   }
}
