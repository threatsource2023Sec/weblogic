package weblogic.utils.classfile;

import java.io.IOException;
import java.io.InputStream;
import weblogic.utils.classloaders.ClassFinder;
import weblogic.utils.classloaders.ClasspathClassFinder2;
import weblogic.utils.classloaders.Source;

public class ClassFileLoader {
   private static final boolean DEBUG = false;
   ClassFinder finder;
   ClassFileLoader parent;
   boolean throwClassNotFound;
   private static ClassFileLoader sysLoader;

   public ClassFileLoader(ClassFinder cf) {
      this.finder = cf;
   }

   public ClassFileBean loadClass(String orig) throws ClassNotFoundException {
      if (orig == null) {
         if (this.throwClassNotFound) {
            throw new ClassNotFoundException(orig);
         } else {
            return null;
         }
      } else {
         String name = orig.replace('.', '/');
         name = name + ".class";
         Source src = this.finder.getSource(name);
         ClassFileBean ret = null;
         if (src == null && this.parent != null) {
            ret = this.parent.loadClass(orig);
            if (ret == null && this.throwClassNotFound) {
               throw new ClassNotFoundException(orig);
            } else {
               return ret;
            }
         } else if (src == null) {
            if (this.throwClassNotFound) {
               throw new ClassNotFoundException(orig);
            } else {
               return null;
            }
         } else {
            InputStream is = null;

            try {
               is = src.getInputStream();
               ClassFile cf = new ClassFile(is);
               ret = new ClassFileBean(cf);
            } catch (BadBytecodesException var17) {
            } catch (IOException var18) {
            } finally {
               try {
                  if (is != null) {
                     is.close();
                  }
               } catch (IOException var16) {
               }

            }

            if (ret == null && this.throwClassNotFound) {
               throw new ClassNotFoundException(orig);
            } else {
               return ret;
            }
         }
      }
   }

   public ClassFileLoader getParent() {
      return this.parent;
   }

   public void setParent(ClassFileLoader cf) {
      this.parent = cf;
   }

   public boolean getThrowClassNotFound() {
      return this.throwClassNotFound;
   }

   public void setThrowClassNotFound(boolean b) {
      this.throwClassNotFound = b;
   }

   public static synchronized ClassFileLoader getSystem() {
      if (sysLoader == null) {
         sysLoader = new ClassFileLoader(new ClasspathClassFinder2());
      }

      return sysLoader;
   }
}
