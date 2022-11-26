package org.apache.openjpa.lib.meta;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLDecoder;
import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.NoSuchElementException;
import org.apache.openjpa.lib.util.J2DoPrivHelper;
import org.apache.openjpa.lib.util.MultiClassLoader;
import serp.util.Strings;

public class ClassMetaDataIterator implements MetaDataIterator {
   private final ClassLoader _loader;
   private final List _locs;
   private int _loc;
   private final List _urls;
   private int _url;

   public ClassMetaDataIterator(Class cls, String suffix, boolean topDown) {
      this(cls, suffix, (ClassLoader)null, topDown);
   }

   public ClassMetaDataIterator(Class cls, String suffix, ClassLoader loader, boolean topDown) {
      this._loc = -1;
      this._urls = new ArrayList(3);
      this._url = -1;
      if (cls == null || !cls.isPrimitive() && !cls.getName().startsWith("java.") && !cls.getName().startsWith("javax.")) {
         if (loader == null) {
            MultiClassLoader multi = (MultiClassLoader)AccessController.doPrivileged(J2DoPrivHelper.newMultiClassLoaderAction());
            multi.addClassLoader(MultiClassLoader.SYSTEM_LOADER);
            multi.addClassLoader(MultiClassLoader.THREAD_LOADER);
            multi.addClassLoader(this.getClass().getClassLoader());
            if (cls != null) {
               ClassLoader clsLoader = (ClassLoader)AccessController.doPrivileged(J2DoPrivHelper.getClassLoaderAction(cls));
               if (clsLoader != null) {
                  multi.addClassLoader(clsLoader);
               }
            }

            loader = multi;
         }

         this._loader = (ClassLoader)loader;
         this._locs = new ArrayList();
         this._locs.add("META-INF/package" + suffix);
         this._locs.add("WEB-INF/package" + suffix);
         this._locs.add("package" + suffix);
         if (!topDown) {
            this._locs.add("system" + suffix);
         }

         if (cls != null) {
            String pkg = Strings.getPackageName(cls).replace('.', '/');
            if (pkg.length() > 0) {
               int start = 0;
               String upPath = "";

               int idx;
               do {
                  idx = pkg.indexOf(47, start);
                  String pkgName;
                  String path;
                  if (idx == -1) {
                     pkgName = start == 0 ? pkg : pkg.substring(start);
                     path = pkg + "/";
                  } else {
                     pkgName = pkg.substring(start, idx);
                     path = pkg.substring(0, idx + 1);
                  }

                  this._locs.add(path + "package" + suffix);
                  this._locs.add(path + pkgName + suffix);
                  this._locs.add(upPath + pkgName + suffix);
                  if (idx == -1) {
                     this._locs.add(path + Strings.getClassName(cls) + suffix);
                  }

                  start = idx + 1;
                  upPath = path;
               } while(idx != -1);
            } else {
               this._locs.add(cls.getName() + suffix);
            }
         }

         if (topDown) {
            this._locs.add("system" + suffix);
         } else {
            Collections.reverse(this._locs);
         }

      } else {
         this._loader = null;
         this._locs = Collections.EMPTY_LIST;
      }
   }

   public boolean hasNext() throws IOException {
      label26:
      while(true) {
         if (this._url + 1 >= this._urls.size()) {
            if (++this._loc >= this._locs.size()) {
               return false;
            }

            this._url = -1;
            this._urls.clear();

            Enumeration e;
            try {
               e = (Enumeration)AccessController.doPrivileged(J2DoPrivHelper.getResourcesAction(this._loader, (String)this._locs.get(this._loc)));
            } catch (PrivilegedActionException var3) {
               throw (IOException)var3.getException();
            }

            while(true) {
               if (!e.hasMoreElements()) {
                  continue label26;
               }

               this._urls.add(e.nextElement());
            }
         }

         return true;
      }
   }

   public Object next() throws IOException {
      if (!this.hasNext()) {
         throw new NoSuchElementException();
      } else {
         return this._urls.get(++this._url);
      }
   }

   public InputStream getInputStream() throws IOException {
      if (this._url != -1 && this._url < this._urls.size()) {
         try {
            return (InputStream)AccessController.doPrivileged(J2DoPrivHelper.openStreamAction((URL)this._urls.get(this._url)));
         } catch (PrivilegedActionException var2) {
            throw (IOException)var2.getException();
         }
      } else {
         throw new IllegalStateException();
      }
   }

   public File getFile() throws IOException {
      if (this._url != -1 && this._url < this._urls.size()) {
         File file = new File(URLDecoder.decode(((URL)this._urls.get(this._url)).getFile()));
         return (Boolean)AccessController.doPrivileged(J2DoPrivHelper.existsAction(file)) ? file : null;
      } else {
         throw new IllegalStateException();
      }
   }

   public void close() {
   }
}
