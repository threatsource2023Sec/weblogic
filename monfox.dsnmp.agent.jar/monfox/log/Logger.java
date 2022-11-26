package monfox.log;

import java.util.Hashtable;

public class Logger {
   private static String a = a("B\u0013\u001d`L@\u0013");
   private static boolean b = true;
   private static Hashtable c = new Hashtable();
   private static boolean d = true;
   private static Hashtable e = new Hashtable();
   private static a f = new a();
   private static Provider g = null;
   private static Provider h = new DefaultProvider();
   private static Hashtable i = null;
   public static int j;

   public static void enableAll() {
      if (g == null) {
         h.enableAll();
         if (j == 0) {
            return;
         }
      }

      g.enableAll();
   }

   public static void disableAll() {
      if (g == null) {
         h.disableAll();
         if (j == 0) {
            return;
         }
      }

      g.disableAll();
   }

   public static void setProvider(Provider var0) {
      g = var0;
   }

   public static void setProvider(String var0, Provider var1) {
      if (i == null) {
         i = new Hashtable();
      }

      i.put(var0, var1);
   }

   public static Provider getProvider() {
      return g;
   }

   public static Provider getProvider(String var0) {
      return i == null ? null : (Provider)i.get(var0);
   }

   public static void disableAPIs(String[] var0) {
      b = true;
      if (var0 != null) {
         int var1 = 0;

         while(var1 < var0.length) {
            c.put(var0[var1].toUpperCase(), new Boolean(true));
            ++var1;
            if (j != 0) {
               break;
            }
         }

      }
   }

   public static void enableAPIs(String[] var0) {
      b = false;
      if (var0 != null) {
         int var1 = 0;

         while(var1 < var0.length) {
            c.put(var0[var1].toUpperCase(), new Boolean(true));
            ++var1;
            if (j != 0) {
               break;
            }
         }

      }
   }

   public static void disableGroups(String[] var0) {
      d = true;
      if (var0 != null) {
         int var1 = 0;

         while(var1 < var0.length) {
            e.put(var0[var1].toUpperCase(), new Boolean(false));
            ++var1;
            if (j != 0) {
               break;
            }
         }

      }
   }

   public static void enableGroups(String[] var0) {
      d = false;
      if (var0 != null) {
         int var1 = 0;

         while(var1 < var0.length) {
            e.put(var0[var1].toUpperCase(), new Boolean(true));
            ++var1;
            if (j != 0) {
               break;
            }
         }

      }
   }

   public static void setApplicationName(String var0) {
      a = var0;
      if (var0 != null) {
         f.setProperty(a("V\r\u0006b"), getApplicationName());
         f.setProperty(a("V\r\u0006bMV\u0010\u0013"), getApplicationName());
         f.setProperty(a("V\r\u0006`BZ\u0018"), getApplicationName());
      }

   }

   public static String getApplicationName() {
      return a;
   }

   public static void setVariable(String var0, String var1) {
      f.setProperty(var0, var1);
   }

   public static String resolveVariables(String var0) {
      return f.resolveVars(var0);
   }

   public static Logger getInstance(String var0, String var1, String var2) {
      int var5 = j;
      if (g == null && i == null) {
         return h.getInstance(var2);
      } else {
         Boolean var3;
         label69: {
            if (var0 != null) {
               var3 = (Boolean)c.get(var0.toUpperCase());
               if (var3 == null && !b || var3 != null && !var3) {
                  return h.getInstance(var2);
               }

               if (var5 == 0) {
                  break label69;
               }
            }

            if (!b) {
               return h.getInstance(var2);
            }
         }

         label55: {
            if (var1 != null) {
               var3 = (Boolean)e.get(var1.toUpperCase());
               if (var3 == null && !d || var3 != null && !var3) {
                  return h.getInstance(var2);
               }

               if (var5 == 0) {
                  break label55;
               }
            }

            if (!d) {
               return h.getInstance(var2);
            }
         }

         Provider var6 = null;
         if (i != null && var0 != null) {
            var6 = (Provider)i.get(var0);
         }

         if (var6 == null) {
            var6 = g;
         }

         if (var6 == null) {
            return h.getInstance(var2);
         } else {
            Logger var4 = var6.getInstance(var2);
            var4.setApi(var0);
            var4.setGroup(var1);
            return var4;
         }
      }
   }

   public static Logger getInstance(String var0) {
      return getInstance((String)null, (String)null, var0);
   }

   public String getApi() {
      return null;
   }

   public void setApi(String var1) {
   }

   public String getGroup() {
      return null;
   }

   public void setGroup(String var1) {
   }

   public void init(String var1) {
   }

   public boolean isErrorEnabled() {
      return false;
   }

   public void error(Object var1) {
   }

   public void error(Object var1, Throwable var2) {
   }

   public boolean isWarnEnabled() {
      return false;
   }

   public void warn(Object var1) {
   }

   public void warn(Object var1, Throwable var2) {
   }

   public boolean isInfoEnabled() {
      return false;
   }

   public void info(Object var1) {
   }

   public void info(Object var1, Throwable var2) {
   }

   public boolean isConfigEnabled() {
      return this.isInfoEnabled();
   }

   public void config(Object var1) {
      this.info(var1);
   }

   public void config(Object var1, Throwable var2) {
      this.info(var1, var2);
   }

   public boolean isCommsEnabled() {
      return this.isDebugEnabled();
   }

   public void comms(Object var1) {
      this.debug(var1);
   }

   public void comms(Object var1, Throwable var2) {
      this.debug(var1, var2);
   }

   public boolean isDebugEnabled() {
      return false;
   }

   public void debug(Object var1) {
   }

   public void debug(Object var1, Throwable var2) {
   }

   public boolean isDetailedEnabled() {
      return this.isDebugEnabled();
   }

   public void detailed(Object var1) {
      this.debug(var1);
   }

   public void detailed(Object var1, Throwable var2) {
      this.debug(var1, var2);
   }

   public boolean isEnabled() {
      return false;
   }

   public void setLevel(int var1) {
   }

   public static Provider startLogging(String var0, int var1) {
      SimpleLogger.Provider var2 = new SimpleLogger.Provider(var0);
      var2.setLevel(var1);
      setProvider(var2);
      return var2;
   }

   static {
      f.setProperty(a("V\r\u0006b"), a("B\u0013\u001d`L@\u0013"));
      f.setProperty(a("V\r\u0006bMV\u0010\u0013"), a("B\u0013\u001d`L@\u0013"));
      f.setProperty(a("V\r\u0006`BZ\u0018"), a("B\u0013\u001d`L@\u0013"));
   }

   private static String a(String var0) {
      char[] var1 = var0.toCharArray();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         char var10002 = var1[var3];
         byte var10003;
         switch (var3 % 5) {
            case 0:
               var10003 = 55;
               break;
            case 1:
               var10003 = 125;
               break;
            case 2:
               var10003 = 118;
               break;
            case 3:
               var10003 = 14;
               break;
            default:
               var10003 = 35;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }

   public static class DefaultProvider implements Provider {
      private boolean a = true;
      private Logger b = new Logger();

      public Logger getInstance(String var1) {
         return this.b;
      }

      public void enableAll() {
         this.a = true;
      }

      public void disableAll() {
         this.a = false;
      }
   }

   public interface Provider {
      Logger getInstance(String var1);

      void enableAll();

      void disableAll();
   }
}
