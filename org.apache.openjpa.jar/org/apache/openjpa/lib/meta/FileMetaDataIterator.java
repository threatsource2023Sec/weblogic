package org.apache.openjpa.lib.meta;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.NoSuchElementException;
import org.apache.openjpa.lib.util.J2DoPrivHelper;
import org.apache.openjpa.lib.util.Localizer;

public class FileMetaDataIterator implements MetaDataIterator {
   private static final long SCAN_LIMIT = 100000L;
   private static final Localizer _loc = Localizer.forPackage(FileMetaDataIterator.class);
   private final Iterator _itr;
   private File _file = null;

   public FileMetaDataIterator(File file) {
      this._itr = Collections.singleton(file).iterator();
   }

   public FileMetaDataIterator(File dir, MetaDataFilter filter) throws IOException {
      if (dir == null) {
         this._itr = null;
      } else {
         Collection metas = new ArrayList();
         FileResource rsrc = filter == null ? null : new FileResource();
         this.scan(dir, filter, rsrc, metas, 0);
         this._itr = metas.iterator();
      }

   }

   private int scan(File file, MetaDataFilter filter, FileResource rsrc, Collection metas, int scanned) throws IOException {
      if ((long)scanned > 100000L) {
         throw new IllegalStateException(_loc.get("too-many-files", (Object)String.valueOf(100000L)).getMessage());
      } else {
         ++scanned;
         if (filter == null) {
            metas.add(file);
         } else {
            rsrc.setFile(file);
            if (filter.matches(rsrc)) {
               metas.add(file);
            } else {
               File[] files = (File[])((File[])AccessController.doPrivileged(J2DoPrivHelper.listFilesAction(file)));
               if (files != null) {
                  for(int i = 0; i < files.length; ++i) {
                     scanned = this.scan(files[i], filter, rsrc, metas, scanned);
                  }
               }
            }
         }

         return scanned;
      }
   }

   public boolean hasNext() {
      return this._itr != null && this._itr.hasNext();
   }

   public Object next() throws IOException {
      if (this._itr == null) {
         throw new NoSuchElementException();
      } else {
         this._file = (File)this._itr.next();

         try {
            File f = (File)AccessController.doPrivileged(J2DoPrivHelper.getAbsoluteFileAction(this._file));
            return AccessController.doPrivileged(J2DoPrivHelper.toURLAction(f));
         } catch (PrivilegedActionException var2) {
            throw (MalformedURLException)var2.getException();
         }
      }
   }

   public InputStream getInputStream() throws IOException {
      if (this._file == null) {
         throw new IllegalStateException();
      } else {
         FileInputStream fis = null;

         try {
            fis = (FileInputStream)AccessController.doPrivileged(J2DoPrivHelper.newFileInputStreamAction(this._file));
            return fis;
         } catch (PrivilegedActionException var3) {
            throw (FileNotFoundException)var3.getException();
         }
      }
   }

   public File getFile() {
      if (this._file == null) {
         throw new IllegalStateException();
      } else {
         return this._file;
      }
   }

   public void close() {
   }

   private static class FileResource implements MetaDataFilter.Resource {
      private File _file;

      private FileResource() {
         this._file = null;
      }

      public void setFile(File file) {
         this._file = file;
      }

      public String getName() {
         return this._file.getName();
      }

      public byte[] getContent() throws IOException {
         long len = (Long)AccessController.doPrivileged(J2DoPrivHelper.lengthAction(this._file));
         FileInputStream fin = null;

         try {
            fin = (FileInputStream)AccessController.doPrivileged(J2DoPrivHelper.newFileInputStreamAction(this._file));
         } catch (PrivilegedActionException var15) {
            throw (FileNotFoundException)var15.getException();
         }

         byte[] var18;
         try {
            byte[] content;
            if (len > 0L && len <= 2147483647L) {
               content = new byte[(int)len];

               int r;
               for(int o = 0; o < content.length && (r = fin.read(content, o, content.length - o)) != -1; o += r) {
               }
            } else {
               ByteArrayOutputStream bout = new ByteArrayOutputStream();
               byte[] buf = new byte[1024];

               int r;
               while((r = fin.read(buf)) != -1) {
                  bout.write(buf, 0, r);
               }

               content = bout.toByteArray();
            }

            var18 = content;
         } finally {
            try {
               fin.close();
            } catch (IOException var14) {
            }

         }

         return var18;
      }

      // $FF: synthetic method
      FileResource(Object x0) {
         this();
      }
   }
}
