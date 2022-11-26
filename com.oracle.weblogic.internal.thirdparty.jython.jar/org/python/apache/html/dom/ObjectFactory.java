package org.python.apache.html.dom;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

final class ObjectFactory {
   private static final String DEFAULT_PROPERTIES_FILENAME = "xerces.properties";
   private static final boolean DEBUG = isDebugEnabled();
   private static final int DEFAULT_LINE_LENGTH = 80;
   private static Properties fXercesProperties = null;
   private static long fLastModified = -1L;
   // $FF: synthetic field
   static Class class$org$apache$html$dom$ObjectFactory;

   static Object createObject(String var0, String var1) throws ConfigurationError {
      return createObject(var0, (String)null, var1);
   }

   static Object createObject(String var0, String var1, String var2) throws ConfigurationError {
      if (DEBUG) {
         debugPrintln("debug is on");
      }

      ClassLoader var3 = findClassLoader();

      String var4;
      try {
         var4 = SecuritySupport.getSystemProperty(var0);
         if (var4 != null && var4.length() > 0) {
            if (DEBUG) {
               debugPrintln("found system property, value=" + var4);
            }

            return newInstance(var4, var3, true);
         }
      } catch (SecurityException var46) {
      }

      var4 = null;
      if (var1 == null) {
         File var5 = null;
         boolean var6 = false;

         try {
            String var7 = SecuritySupport.getSystemProperty("java.home");
            var1 = var7 + File.separator + "lib" + File.separator + "xerces.properties";
            var5 = new File(var1);
            var6 = SecuritySupport.getFileExists(var5);
         } catch (SecurityException var45) {
            fLastModified = -1L;
            fXercesProperties = null;
         }

         Class var53 = class$org$apache$html$dom$ObjectFactory == null ? (class$org$apache$html$dom$ObjectFactory = class$("org.python.apache.html.dom.ObjectFactory")) : class$org$apache$html$dom$ObjectFactory;
         synchronized(var53) {
            boolean var8 = false;
            FileInputStream var9 = null;

            try {
               if (fLastModified >= 0L) {
                  if (var6 && fLastModified < (fLastModified = SecuritySupport.getLastModified(var5))) {
                     var8 = true;
                  } else if (!var6) {
                     fLastModified = -1L;
                     fXercesProperties = null;
                  }
               } else if (var6) {
                  var8 = true;
                  fLastModified = SecuritySupport.getLastModified(var5);
               }

               if (var8) {
                  fXercesProperties = new Properties();
                  var9 = SecuritySupport.getFileInputStream(var5);
                  fXercesProperties.load(var9);
               }
            } catch (Exception var48) {
               fXercesProperties = null;
               fLastModified = -1L;
            } finally {
               if (var9 != null) {
                  try {
                     var9.close();
                  } catch (IOException var43) {
                  }
               }

            }
         }

         if (fXercesProperties != null) {
            var4 = fXercesProperties.getProperty(var0);
         }
      } else {
         FileInputStream var51 = null;

         try {
            var51 = SecuritySupport.getFileInputStream(new File(var1));
            Properties var52 = new Properties();
            var52.load(var51);
            var4 = var52.getProperty(var0);
         } catch (Exception var44) {
         } finally {
            if (var51 != null) {
               try {
                  var51.close();
               } catch (IOException var42) {
               }
            }

         }
      }

      if (var4 != null) {
         if (DEBUG) {
            debugPrintln("found in " + var1 + ", value=" + var4);
         }

         return newInstance(var4, var3, true);
      } else {
         Object var54 = findJarServiceProvider(var0);
         if (var54 != null) {
            return var54;
         } else if (var2 == null) {
            throw new ConfigurationError("Provider for " + var0 + " cannot be found", (Exception)null);
         } else {
            if (DEBUG) {
               debugPrintln("using fallback, value=" + var2);
            }

            return newInstance(var2, var3, true);
         }
      }
   }

   private static boolean isDebugEnabled() {
      try {
         String var0 = SecuritySupport.getSystemProperty("xerces.debug");
         return var0 != null && !"false".equals(var0);
      } catch (SecurityException var1) {
         return false;
      }
   }

   private static void debugPrintln(String var0) {
      if (DEBUG) {
         System.err.println("XERCES: " + var0);
      }

   }

   static ClassLoader findClassLoader() throws ConfigurationError {
      ClassLoader var0 = SecuritySupport.getContextClassLoader();
      ClassLoader var1 = SecuritySupport.getSystemClassLoader();

      ClassLoader var2;
      for(var2 = var1; var0 != var2; var2 = SecuritySupport.getParentClassLoader(var2)) {
         if (var2 == null) {
            return var0;
         }
      }

      ClassLoader var3 = (class$org$apache$html$dom$ObjectFactory == null ? (class$org$apache$html$dom$ObjectFactory = class$("org.python.apache.html.dom.ObjectFactory")) : class$org$apache$html$dom$ObjectFactory).getClassLoader();

      for(var2 = var1; var3 != var2; var2 = SecuritySupport.getParentClassLoader(var2)) {
         if (var2 == null) {
            return var3;
         }
      }

      return var1;
   }

   static Object newInstance(String var0, ClassLoader var1, boolean var2) throws ConfigurationError {
      try {
         Class var3 = findProviderClass(var0, var1, var2);
         Object var4 = var3.newInstance();
         if (DEBUG) {
            debugPrintln("created new instance of " + var3 + " using ClassLoader: " + var1);
         }

         return var4;
      } catch (ClassNotFoundException var5) {
         throw new ConfigurationError("Provider " + var0 + " not found", var5);
      } catch (Exception var6) {
         throw new ConfigurationError("Provider " + var0 + " could not be instantiated: " + var6, var6);
      }
   }

   static Class findProviderClass(String var0, ClassLoader var1, boolean var2) throws ClassNotFoundException, ConfigurationError {
      SecurityManager var3 = System.getSecurityManager();
      if (var3 != null) {
         int var4 = var0.lastIndexOf(".");
         String var5 = var0;
         if (var4 != -1) {
            var5 = var0.substring(0, var4);
         }

         var3.checkPackageAccess(var5);
      }

      Class var8;
      if (var1 == null) {
         var8 = Class.forName(var0);
      } else {
         try {
            var8 = var1.loadClass(var0);
         } catch (ClassNotFoundException var7) {
            if (!var2) {
               throw var7;
            }

            ClassLoader var6 = (class$org$apache$html$dom$ObjectFactory == null ? (class$org$apache$html$dom$ObjectFactory = class$("org.python.apache.html.dom.ObjectFactory")) : class$org$apache$html$dom$ObjectFactory).getClassLoader();
            if (var6 == null) {
               var8 = Class.forName(var0);
            } else {
               if (var1 == var6) {
                  throw var7;
               }

               var8 = var6.loadClass(var0);
            }
         }
      }

      return var8;
   }

   private static Object findJarServiceProvider(String var0) throws ConfigurationError {
      String var1 = "META-INF/services/" + var0;
      InputStream var2 = null;
      ClassLoader var3 = findClassLoader();
      var2 = SecuritySupport.getResourceAsStream(var3, var1);
      if (var2 == null) {
         ClassLoader var4 = (class$org$apache$html$dom$ObjectFactory == null ? (class$org$apache$html$dom$ObjectFactory = class$("org.python.apache.html.dom.ObjectFactory")) : class$org$apache$html$dom$ObjectFactory).getClassLoader();
         if (var3 != var4) {
            var3 = var4;
            var2 = SecuritySupport.getResourceAsStream(var4, var1);
         }
      }

      if (var2 == null) {
         return null;
      } else {
         if (DEBUG) {
            debugPrintln("found jar resource=" + var1 + " using ClassLoader: " + var3);
         }

         BufferedReader var21;
         try {
            var21 = new BufferedReader(new InputStreamReader(var2, "UTF-8"), 80);
         } catch (UnsupportedEncodingException var18) {
            var21 = new BufferedReader(new InputStreamReader(var2), 80);
         }

         String var5 = null;

         label133: {
            Object var7;
            try {
               var5 = var21.readLine();
               break label133;
            } catch (IOException var19) {
               var7 = null;
            } finally {
               try {
                  var21.close();
               } catch (IOException var17) {
               }

            }

            return var7;
         }

         if (var5 != null && !"".equals(var5)) {
            if (DEBUG) {
               debugPrintln("found in resource, value=" + var5);
            }

            return newInstance(var5, var3, false);
         } else {
            return null;
         }
      }
   }

   // $FF: synthetic method
   static Class class$(String var0) {
      try {
         return Class.forName(var0);
      } catch (ClassNotFoundException var2) {
         throw new NoClassDefFoundError(var2.getMessage());
      }
   }

   static final class ConfigurationError extends Error {
      static final long serialVersionUID = 2646822752226280048L;
      private Exception exception;

      ConfigurationError(String var1, Exception var2) {
         super(var1);
         this.exception = var2;
      }

      Exception getException() {
         return this.exception;
      }
   }
}
