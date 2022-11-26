package weblogic.platform;

public final class Unix extends OperatingSystem {
   private static final String LIB_NAME = "weblogicunix1";
   private static boolean libLoaded = false;

   private static synchronized void initNative() {
      if (!libLoaded) {
         System.loadLibrary("weblogicunix1");
         libLoaded = true;
      }
   }

   public String getUser() {
      initNative();

      try {
         return getUser0();
      } catch (Exception var2) {
         return null;
      }
   }

   public boolean setUser(String user) {
      initNative();
      if (user == null) {
         throw new NullPointerException("null user String");
      } else {
         return setUser0(user);
      }
   }

   public String getGroup() {
      initNative();

      try {
         return getGroup0();
      } catch (Exception var2) {
         return null;
      }
   }

   public boolean setGroup(String group) {
      if (group == null) {
         throw new NullPointerException("null group String");
      } else {
         initNative();
         return setGroup0(group);
      }
   }

   public String getEffectiveUser() {
      initNative();

      try {
         return getEUser0();
      } catch (Exception var2) {
         return null;
      }
   }

   public boolean setEffectiveUser(String user) {
      if (user == null) {
         throw new NullPointerException("null user String");
      } else {
         initNative();
         return setEUser0(user);
      }
   }

   public String getEffectiveGroup() {
      initNative();

      try {
         return getEGroup0();
      } catch (Exception var2) {
         return null;
      }
   }

   public boolean setEffectiveGroup(String group) {
      if (group == null) {
         throw new NullPointerException("null group String");
      } else {
         initNative();
         return setEGroup0(group);
      }
   }

   private static native String getUser0();

   private static native boolean setUser0(String var0);

   private static native String getGroup0();

   private static native boolean setGroup0(String var0);

   private static native String getEGroup0();

   private static native boolean setEGroup0(String var0);

   private static native String getEUser0();

   private static native boolean setEUser0(String var0);

   public static void main(String[] args) {
      try {
         System.out.println("Begining test of switching User to: " + args[0]);
         Unix u = new Unix();
         boolean success = u.setUser(args[0]);
         System.out.println("Did we switch to : " + args[0] + " : " + success);
         if (success) {
            System.out.println("Process will sleep for 2 minutes, please observe process and ensure that its owner is indeed the useryou provided.");

            try {
               Thread.sleep(120000L);
            } catch (InterruptedException var4) {
               System.out.println("Sleep failed");
               var4.printStackTrace();
            }
         }
      } catch (Exception var5) {
         System.out.println("java weblogic.platform.Unix <user>\nwhere <user> is the user name you want to switch to.");
         System.out.println("Exception:");
         var5.printStackTrace();
      }

   }
}
