package weblogic.xml.jaxp;

import java.lang.reflect.Method;
import java.util.MissingResourceException;
import weblogic.utils.Debug;

public class Utils {
   private static final boolean debug = Boolean.getBoolean("weblogic.xml.debug");

   public static synchronized Object getDelegate(String[] factories) {
      Object delegate = null;
      Exception exception = null;
      String concatExceptions = "";
      String concatFactories = "";
      Thread thisThread = null;
      ClassLoader tccl = null;

      for(int i = 0; i < factories.length; ++i) {
         boolean setTCCL = false;
         String value = null;

         try {
            if (factories[i].startsWith("javax")) {
               try {
                  value = System.getProperty(factories[i]);
                  if (value != null) {
                     System.clearProperty(factories[i]);
                  }
               } catch (Exception var24) {
               }

               thisThread = Thread.currentThread();
               tccl = thisThread.getContextClassLoader();

               try {
                  Class.forName("jdk.xml.internal.JdkXmlUtils");
               } catch (Throwable var23) {
               }

               setTCCL = true;
               thisThread.setContextClassLoader(platformClassLoader());
               Class cls = Class.forName(factories[i]);
               if (factories[i].indexOf("SchemaFactory") != -1) {
                  Class[] c = new Class[]{String.class};
                  Method m = cls.getMethod("newInstance", c);
                  delegate = m.invoke(cls, "http://www.w3.org/2001/XMLSchema");
               } else {
                  Method m = cls.getMethod("newInstance", (Class[])null);
                  delegate = m.invoke(cls);
               }
            } else {
               delegate = Class.forName(factories[i]).newInstance();
            }
         } catch (Exception var25) {
            concatExceptions = concatExceptions + var25.toString() + "\n";
            concatFactories = concatFactories + factories[i] + "\n";
         } finally {
            if (setTCCL) {
               thisThread.setContextClassLoader(tccl);
            }

            if (value != null) {
               try {
                  System.setProperty(factories[i], value);
               } catch (Exception var22) {
               }
            }

         }

         if (delegate != null) {
            break;
         }
      }

      if (delegate == null) {
         throw new MissingResourceException("Could not instantiate factory delegate, got exception(s):\n" + concatExceptions, "class", concatFactories);
      } else {
         return delegate;
      }
   }

   public static ClassLoader platformClassLoader() {
      ClassLoader parent;
      for(ClassLoader loader = ClassLoader.getSystemClassLoader(); loader != null; loader = parent) {
         parent = loader.getParent();
         if (parent == null) {
            return loader;
         }
      }

      String failMessage = "Unable to determine platform/extension class loader";
      if (debug) {
         Debug.say(failMessage);
      }

      throw new InternalError(failMessage);
   }
}
