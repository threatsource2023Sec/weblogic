package org.python.apache.xerces.dom;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;

final class SecuritySupport {
   static ClassLoader getContextClassLoader() {
      return (ClassLoader)AccessController.doPrivileged(new PrivilegedAction() {
         public Object run() {
            ClassLoader var1 = null;

            try {
               var1 = Thread.currentThread().getContextClassLoader();
            } catch (SecurityException var3) {
            }

            return var1;
         }
      });
   }

   static ClassLoader getSystemClassLoader() {
      return (ClassLoader)AccessController.doPrivileged(new PrivilegedAction() {
         public Object run() {
            ClassLoader var1 = null;

            try {
               var1 = ClassLoader.getSystemClassLoader();
            } catch (SecurityException var3) {
            }

            return var1;
         }
      });
   }

   static ClassLoader getParentClassLoader(final ClassLoader var0) {
      return (ClassLoader)AccessController.doPrivileged(new PrivilegedAction() {
         public Object run() {
            ClassLoader var1 = null;

            try {
               var1 = var0.getParent();
            } catch (SecurityException var3) {
            }

            return var1 == var0 ? null : var1;
         }
      });
   }

   static String getSystemProperty(final String var0) {
      return (String)AccessController.doPrivileged(new PrivilegedAction() {
         public Object run() {
            return System.getProperty(var0);
         }
      });
   }

   static FileInputStream getFileInputStream(final File var0) throws FileNotFoundException {
      try {
         return (FileInputStream)AccessController.doPrivileged(new PrivilegedExceptionAction() {
            public Object run() throws FileNotFoundException {
               return new FileInputStream(var0);
            }
         });
      } catch (PrivilegedActionException var2) {
         throw (FileNotFoundException)var2.getException();
      }
   }

   static InputStream getResourceAsStream(final ClassLoader var0, final String var1) {
      return (InputStream)AccessController.doPrivileged(new PrivilegedAction() {
         public Object run() {
            InputStream var1x;
            if (var0 == null) {
               var1x = ClassLoader.getSystemResourceAsStream(var1);
            } else {
               var1x = var0.getResourceAsStream(var1);
            }

            return var1x;
         }
      });
   }

   static boolean getFileExists(final File var0) {
      return (Boolean)AccessController.doPrivileged(new PrivilegedAction() {
         public Object run() {
            return var0.exists() ? Boolean.TRUE : Boolean.FALSE;
         }
      });
   }

   static long getLastModified(final File var0) {
      return (Long)AccessController.doPrivileged(new PrivilegedAction() {
         public Object run() {
            return new Long(var0.lastModified());
         }
      });
   }

   private SecuritySupport() {
   }
}
