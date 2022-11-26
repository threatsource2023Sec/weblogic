package org.apache.openjpa.lib.meta;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.NoSuchElementException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class ZipStreamMetaDataIterator implements MetaDataIterator, MetaDataFilter.Resource {
   private final ZipInputStream _stream;
   private final MetaDataFilter _filter;
   private ZipEntry _entry = null;
   private ZipEntry _last = null;
   private byte[] _buf = null;

   public ZipStreamMetaDataIterator(ZipInputStream stream, MetaDataFilter filter) {
      this._stream = stream;
      this._filter = filter;
   }

   public boolean hasNext() throws IOException {
      if (this._stream == null) {
         return false;
      } else if (this._entry != null) {
         return true;
      } else {
         if (this._buf == null && this._last != null) {
            this._stream.closeEntry();
         }

         this._last = null;
         this._buf = null;

         ZipEntry entry;
         while(this._entry == null && (entry = this._stream.getNextEntry()) != null) {
            this._entry = entry;
            if (this._filter != null && !this._filter.matches(this)) {
               this._entry = null;
               this._stream.closeEntry();
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

   public InputStream getInputStream() {
      if (this._last == null) {
         throw new IllegalStateException();
      } else {
         return (InputStream)(this._buf != null ? new ByteArrayInputStream(this._buf) : new NoCloseInputStream());
      }
   }

   public File getFile() {
      return null;
   }

   public void close() {
      try {
         this._stream.close();
      } catch (IOException var2) {
      }

   }

   public String getName() {
      return this._entry.getName();
   }

   public byte[] getContent() throws IOException {
      ByteArrayOutputStream bout = new ByteArrayOutputStream();
      byte[] buf = new byte[1024];

      int r;
      while((r = this._stream.read(buf)) != -1) {
         bout.write(buf, 0, r);
      }

      this._buf = bout.toByteArray();
      this._stream.closeEntry();
      return this._buf;
   }

   private class NoCloseInputStream extends InputStream {
      private NoCloseInputStream() {
      }

      public int available() throws IOException {
         return ZipStreamMetaDataIterator.this._stream.available();
      }

      public int read() throws IOException {
         return ZipStreamMetaDataIterator.this._stream.read();
      }

      public int read(byte[] b, int off, int len) throws IOException {
         return ZipStreamMetaDataIterator.this._stream.read(b, off, len);
      }

      public void close() {
      }

      // $FF: synthetic method
      NoCloseInputStream(Object x1) {
         this();
      }
   }
}
