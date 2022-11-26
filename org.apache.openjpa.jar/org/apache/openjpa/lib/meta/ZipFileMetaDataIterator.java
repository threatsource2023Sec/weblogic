package org.apache.openjpa.lib.meta;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.util.Enumeration;
import java.util.NoSuchElementException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import org.apache.openjpa.lib.util.J2DoPrivHelper;

public class ZipFileMetaDataIterator implements MetaDataIterator, MetaDataFilter.Resource {
   private final ZipFile _file;
   private final MetaDataFilter _filter;
   private final Enumeration _entries;
   private ZipEntry _entry = null;
   private ZipEntry _last = null;

   public ZipFileMetaDataIterator(URL url, MetaDataFilter filter) throws IOException {
      if (url == null) {
         this._file = null;
      } else {
         URLConnection con = url.openConnection();
         con.setDefaultUseCaches(false);

         try {
            this._file = (ZipFile)AccessController.doPrivileged(J2DoPrivHelper.getContentAction(con));
         } catch (PrivilegedActionException var5) {
            throw (IOException)var5.getException();
         }
      }

      this._filter = filter;
      this._entries = this._file == null ? null : this._file.entries();
   }

   public ZipFileMetaDataIterator(ZipFile file, MetaDataFilter filter) {
      this._file = file;
      this._filter = filter;
      this._entries = file == null ? null : file.entries();
   }

   public boolean hasNext() throws IOException {
      if (this._entries == null) {
         return false;
      } else {
         while(this._entry == null && this._entries.hasMoreElements()) {
            this._entry = (ZipEntry)this._entries.nextElement();
            if (this._filter != null && !this._filter.matches(this)) {
               this._entry = null;
            }
         }

         return this._entry != null;
      }
   }

   public Object next() throws IOException {
      if (!this.hasNext()) {
         throw new NoSuchElementException();
      } else {
         String ret = this._entry.getName();
         this._last = this._entry;
         this._entry = null;
         return ret;
      }
   }

   public InputStream getInputStream() throws IOException {
      if (this._last == null) {
         throw new IllegalStateException();
      } else {
         return this._file.getInputStream(this._last);
      }
   }

   public File getFile() {
      if (this._last == null) {
         throw new IllegalStateException();
      } else {
         return null;
      }
   }

   public void close() {
      try {
         if (this._file != null) {
            this._file.close();
         }
      } catch (IOException var2) {
      }

   }

   public String getName() {
      return this._entry.getName();
   }

   public byte[] getContent() throws IOException {
      long size = this._entry.getSize();
      if (size == 0L) {
         return new byte[0];
      } else {
         InputStream in = this._file.getInputStream(this._entry);
         byte[] content;
         if (size < 0L) {
            ByteArrayOutputStream bout = new ByteArrayOutputStream();
            byte[] buf = new byte[1024];

            int r;
            while((r = in.read(buf)) != -1) {
               bout.write(buf, 0, r);
            }

            content = bout.toByteArray();
         } else {
            content = new byte[(int)size];

            int read;
            for(int offset = 0; (long)offset < size && (read = in.read(content, offset, (int)size - offset)) != -1; offset += read) {
            }
         }

         in.close();
         return content;
      }
   }
}
