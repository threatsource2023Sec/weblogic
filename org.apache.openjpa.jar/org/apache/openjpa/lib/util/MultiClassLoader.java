package org.apache.openjpa.lib.util;

import java.io.IOException;
import java.net.URL;
import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

public class MultiClassLoader extends ClassLoader {
   public static final ClassLoader THREAD_LOADER = null;
   public static final ClassLoader SYSTEM_LOADER = (ClassLoader)AccessController.doPrivileged(J2DoPrivHelper.getSystemClassLoaderAction());
   private List _loaders = new ArrayList(5);

   public MultiClassLoader() {
      super((ClassLoader)null);
   }

   public MultiClassLoader(MultiClassLoader other) {
      super((ClassLoader)null);
      this.addClassLoaders(other);
   }

   public boolean containsClassLoader(ClassLoader loader) {
      return this._loaders.contains(loader);
   }

   public ClassLoader[] getClassLoaders() {
      ClassLoader[] loaders = new ClassLoader[this.size()];
      Iterator itr = this._loaders.iterator();

      for(int i = 0; i < loaders.length; ++i) {
         ClassLoader loader = (ClassLoader)itr.next();
         if (loader == THREAD_LOADER) {
            loader = (ClassLoader)AccessController.doPrivileged(J2DoPrivHelper.getContextClassLoaderAction());
         }

         loaders[i] = loader;
      }

      return loaders;
   }

   public ClassLoader getClassLoader(int index) {
      ClassLoader loader = (ClassLoader)this._loaders.get(index);
      if (loader == THREAD_LOADER) {
         loader = (ClassLoader)AccessController.doPrivileged(J2DoPrivHelper.getContextClassLoaderAction());
      }

      return loader;
   }

   public boolean addClassLoader(ClassLoader loader) {
      return this._loaders.contains(loader) ? false : this._loaders.add(loader);
   }

   public boolean addClassLoader(int index, ClassLoader loader) {
      if (this._loaders.contains(loader)) {
         return false;
      } else {
         this._loaders.add(index, loader);
         return true;
      }
   }

   public void setClassLoaders(MultiClassLoader multi) {
      this.clear();
      this.addClassLoaders(multi);
   }

   public boolean addClassLoaders(int index, MultiClassLoader multi) {
      if (multi == null) {
         return false;
      } else {
         boolean added = false;
         Iterator itr = multi._loaders.iterator();

         while(itr.hasNext()) {
            if (this.addClassLoader(index, (ClassLoader)itr.next())) {
               ++index;
               added = true;
            }
         }

         return added;
      }
   }

   public boolean addClassLoaders(MultiClassLoader multi) {
      if (multi == null) {
         return false;
      } else {
         boolean added = false;

         for(Iterator itr = multi._loaders.iterator(); itr.hasNext(); added = this.addClassLoader((ClassLoader)itr.next()) || added) {
         }

         return added;
      }
   }

   public boolean removeClassLoader(ClassLoader loader) {
      return this._loaders.remove(loader);
   }

   public void clear() {
      this._loaders.clear();
   }

   public int size() {
      return this._loaders.size();
   }

   public boolean isEmpty() {
      return this._loaders.isEmpty();
   }

   protected Class findClass(String name) throws ClassNotFoundException {
      Iterator itr = this._loaders.iterator();

      while(itr.hasNext()) {
         ClassLoader loader = (ClassLoader)itr.next();
         if (loader == THREAD_LOADER) {
            loader = (ClassLoader)AccessController.doPrivileged(J2DoPrivHelper.getContextClassLoaderAction());
         }

         try {
            return Class.forName(name, false, loader);
         } catch (Throwable var5) {
         }
      }

      throw new ClassNotFoundException(name);
   }

   protected URL findResource(String name) {
      Iterator itr = this._loaders.iterator();

      while(itr.hasNext()) {
         ClassLoader loader = (ClassLoader)itr.next();
         if (loader == THREAD_LOADER) {
            loader = (ClassLoader)AccessController.doPrivileged(J2DoPrivHelper.getContextClassLoaderAction());
         }

         if (loader != null) {
            URL rsrc = (URL)AccessController.doPrivileged(J2DoPrivHelper.getResourceAction(loader, name));
            if (rsrc != null) {
               return rsrc;
            }
         }
      }

      return null;
   }

   protected Enumeration findResources(String name) throws IOException {
      Vector all = new Vector();
      Iterator itr = this._loaders.iterator();

      while(true) {
         ClassLoader loader;
         do {
            if (!itr.hasNext()) {
               return all.elements();
            }

            loader = (ClassLoader)itr.next();
            if (loader == THREAD_LOADER) {
               loader = (ClassLoader)AccessController.doPrivileged(J2DoPrivHelper.getContextClassLoaderAction());
            }
         } while(loader == null);

         try {
            Enumeration rsrcs = (Enumeration)AccessController.doPrivileged(J2DoPrivHelper.getResourcesAction(loader, name));

            while(rsrcs.hasMoreElements()) {
               Object rsrc = rsrcs.nextElement();
               if (!all.contains(rsrc)) {
                  all.addElement(rsrc);
               }
            }
         } catch (PrivilegedActionException var8) {
            throw (IOException)var8.getException();
         }
      }
   }

   public boolean equals(Object other) {
      if (other == this) {
         return true;
      } else {
         return !(other instanceof MultiClassLoader) ? false : ((MultiClassLoader)other)._loaders.equals(this._loaders);
      }
   }

   public int hashCode() {
      return this._loaders.hashCode();
   }
}
