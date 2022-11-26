package com.oracle.wls.shaded.org.apache.xalan.xsltc.compiler;

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
            ClassLoader cl = null;

            try {
               cl = Thread.currentThread().getContextClassLoader();
            } catch (SecurityException var3) {
            }

            return cl;
         }
      });
   }

   static ClassLoader getSystemClassLoader() {
      return (ClassLoader)AccessController.doPrivileged(new PrivilegedAction() {
         public Object run() {
            ClassLoader cl = null;

            try {
               cl = ClassLoader.getSystemClassLoader();
            } catch (SecurityException var3) {
            }

            return cl;
         }
      });
   }

   static ClassLoader getParentClassLoader(final ClassLoader cl) {
      return (ClassLoader)AccessController.doPrivileged(new PrivilegedAction() {
         public Object run() {
            ClassLoader parent = null;

            try {
               parent = cl.getParent();
            } catch (SecurityException var3) {
            }

            return parent == cl ? null : parent;
         }
      });
   }

   static String getSystemProperty(final String propName) {
      return (String)AccessController.doPrivileged(new PrivilegedAction() {
         public Object run() {
            return System.getProperty(propName);
         }
      });
   }

   static FileInputStream getFileInputStream(final File file) throws FileNotFoundException {
      try {
         return (FileInputStream)AccessController.doPrivileged(new PrivilegedExceptionAction() {
            public Object run() throws FileNotFoundException {
               return new FileInputStream(file);
            }
         });
      } catch (PrivilegedActionException var2) {
         throw (FileNotFoundException)var2.getException();
      }
   }

   static InputStream getResourceAsStream(final ClassLoader cl, final String name) {
      return (InputStream)AccessController.doPrivileged(new PrivilegedAction() {
         public Object run() {
            InputStream ris;
            if (cl == null) {
               ris = ClassLoader.getSystemResourceAsStream(name);
            } else {
               ris = cl.getResourceAsStream(name);
            }

            return ris;
         }
      });
   }

   static boolean getFileExists(final File f) {
      return (Boolean)AccessController.doPrivileged(new PrivilegedAction() {
         public Object run() {
            return f.exists() ? Boolean.TRUE : Boolean.FALSE;
         }
      });
   }

   static long getLastModified(final File f) {
      return (Long)AccessController.doPrivileged(new PrivilegedAction() {
         public Object run() {
            return new Long(f.lastModified());
         }
      });
   }

   private SecuritySupport() {
   }
}
