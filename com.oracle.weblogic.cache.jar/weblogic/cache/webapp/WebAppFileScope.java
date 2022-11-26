package weblogic.cache.webapp;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.naming.NamingException;
import javax.servlet.ServletContext;
import weblogic.cache.CacheException;
import weblogic.persist.TxIndexedFile;
import weblogic.persist.TxIndexedFileStub;

public class WebAppFileScope extends ServletContextAttributeScope {
   private static final Map caches = new HashMap();
   private static boolean DEBUG = false;
   private TxIndexedFile file;
   private boolean inited;

   public WebAppFileScope() {
   }

   public WebAppFileScope(ServletContext context) {
      super(context);
   }

   public void setAttribute(String key, Object value) throws CacheException {
      this.init();
      if (key.endsWith(".lock")) {
         super.setAttribute("filescope-" + key, value);
      } else {
         try {
            this.file.store(key, value);
         } catch (IOException var4) {
            throw new CacheException("Could not write to file store", var4);
         }
      }

   }

   public Object getAttribute(String key) throws CacheException {
      this.init();
      return key.endsWith(".lock") ? super.getAttribute("filescope-" + key) : this.file.retrieve(key);
   }

   public void removeAttribute(String key) throws CacheException {
      this.init();
      if (key.endsWith(".lock")) {
         super.removeAttribute("filescope-" + key);
      } else {
         try {
            this.file.store(key, (Object)null);
         } catch (IOException var3) {
            throw new CacheException("Could not write to file store", var3);
         }
      }

   }

   public Iterator getAttributeNames() throws CacheException {
      this.init();
      final Enumeration e = this.file.keys();
      List list = new ArrayList();
      final Iterator i = super.getAttributeNames();

      while(i.hasNext()) {
         String key = (String)i.next();
         if (key.startsWith("filescope-") && key.endsWith(".lock")) {
            list.add(key);
         }
      }

      i = list.iterator();
      return new Iterator() {
         public boolean hasNext() {
            return e.hasMoreElements() || i.hasNext();
         }

         public Object next() {
            return e.hasMoreElements() ? e.nextElement() : i.next();
         }

         public void remove() {
         }
      };
   }

   private void init() throws CacheException {
      Class var1 = WebAppFileScope.class;
      synchronized(WebAppFileScope.class) {
         if (!this.inited) {
            if (this.file != null) {
               this.file.shutdown();
            }

            File tempDir = (File)this.getContext().getAttribute("javax.servlet.context.tempdir");
            String path = tempDir.getAbsolutePath();
            this.file = (TxIndexedFile)caches.get(path);
            if (this.file == null) {
               try {
                  this.file = new TxIndexedFileStub("cache", path, path);
                  caches.put(path, this.file);
               } catch (NamingException var6) {
                  throw new CacheException("Could not create file store", var6);
               }

               if (DEBUG) {
                  p("WebAppFileScope.init:" + path);
               }
            }

            this.inited = true;
         }

      }
   }

   public static synchronized void destroy(ServletContext sc) {
      if (sc != null) {
         File tempDir = (File)sc.getAttribute("javax.servlet.context.tempdir");
         if (tempDir != null) {
            String path = tempDir.getAbsolutePath();
            Object stub = caches.get(path);
            if (stub != null && stub instanceof TxIndexedFileStub) {
               ((TxIndexedFileStub)stub).shutdown();
               caches.remove(path);
               if (DEBUG) {
                  p("WebAppFileScope.destroy:" + path);
               }

            }
         }
      }
   }

   private static void p(String s) {
      System.out.println("<WebAppFileScope> " + s);
   }
}
