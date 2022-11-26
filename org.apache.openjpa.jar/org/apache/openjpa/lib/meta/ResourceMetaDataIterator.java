package org.apache.openjpa.lib.meta;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLDecoder;
import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.NoSuchElementException;
import org.apache.openjpa.lib.util.J2DoPrivHelper;
import org.apache.openjpa.lib.util.MultiClassLoader;

public class ResourceMetaDataIterator implements MetaDataIterator {
   private List _urls;
   private int _url;

   public ResourceMetaDataIterator(String rsrc) throws IOException {
      this(rsrc, (ClassLoader)null);
   }

   public ResourceMetaDataIterator(String rsrc, ClassLoader loader) throws IOException {
      this._urls = null;
      this._url = -1;
      if (loader == null) {
         MultiClassLoader multi = (MultiClassLoader)AccessController.doPrivileged(J2DoPrivHelper.newMultiClassLoaderAction());
         multi.addClassLoader(MultiClassLoader.SYSTEM_LOADER);
         multi.addClassLoader(MultiClassLoader.THREAD_LOADER);
         multi.addClassLoader(this.getClass().getClassLoader());
         loader = multi;
      }

      try {
         for(Enumeration e = (Enumeration)AccessController.doPrivileged(J2DoPrivHelper.getResourcesAction((ClassLoader)loader, rsrc)); e.hasMoreElements(); this._urls.add(e.nextElement())) {
            if (this._urls == null) {
               this._urls = new ArrayList(3);
            }
         }

      } catch (PrivilegedActionException var4) {
         throw (IOException)var4.getException();
      }
   }

   public boolean hasNext() {
      return this._urls != null && this._url + 1 < this._urls.size();
   }

   public Object next() {
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
