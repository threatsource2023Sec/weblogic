package weblogic.application.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.zip.ZipEntry;
import weblogic.utils.jars.VirtualJarFile;

public class VirtualJarFileMetaDataIterator implements MetaDataIterator, MetaDataFilter.Resource {
   private final VirtualJarFile _vjf;
   private final MetaDataFilter _filter;
   private final Iterator _entries;
   private ZipEntry _entry = null;
   private List _matches = null;
   private ZipEntry _last = null;

   public VirtualJarFileMetaDataIterator(VirtualJarFile vjf, MetaDataFilter filter) {
      this._vjf = vjf;
      this._filter = filter;
      this._entries = this._vjf == null ? null : this._vjf.entries();
   }

   public boolean hasNext() throws IOException {
      if (this._entries == null) {
         return false;
      } else {
         while(this._entry == null && this._entries.hasNext()) {
            this._entry = (ZipEntry)this._entries.next();
            if (this._filter != null) {
               this._matches = this._filter.matches(this);
               if (this._matches == null) {
                  this._entry = null;
               }
            }
         }

         return this._entry != null;
      }
   }

   public MetaDataIterator.MetaData next() throws IOException {
      if (!this.hasNext()) {
         throw new NoSuchElementException();
      } else {
         MetaDataIterator.MetaData ret = new MetaDataImpl(this._entry.getName(), this._matches);
         this._last = this._entry;
         this._entry = null;
         this._matches = null;
         return ret;
      }
   }

   public InputStream getInputStream() throws IOException {
      if (this._last == null) {
         throw new IllegalStateException();
      } else {
         return this._vjf.getInputStream(this._last);
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
   }

   public String getName() {
      return this._entry.getName();
   }

   public byte[] getContent() throws IOException {
      long size = this._entry.getSize();
      if (size == 0L) {
         return new byte[0];
      } else {
         InputStream in = this._vjf.getInputStream(this._entry);
         ByteArrayOutputStream bout = new ByteArrayOutputStream();
         byte[] buf = new byte[1024];
         byte[] content;
         int readnumber;
         if (size >= 0L) {
            int readnumber = false;

            while((long)(readnumber = in.read(buf)) < size) {
               size -= (long)readnumber;
               bout.write(buf, 0, readnumber);
            }

            bout.write(buf, 0, readnumber);
            content = bout.toByteArray();
         } else {
            while((readnumber = in.read(buf)) != -1) {
               bout.write(buf, 0, readnumber);
            }

            content = bout.toByteArray();
         }

         bout.close();
         in.close();
         return content;
      }
   }

   private class MetaDataImpl implements MetaDataIterator.MetaData {
      private final String name;
      private final List annotations;

      private MetaDataImpl(String name, List annotations) {
         this.name = name;
         this.annotations = annotations;
      }

      public String getName() {
         return this.name;
      }

      public List getAnnotations() {
         return this.annotations;
      }

      // $FF: synthetic method
      MetaDataImpl(String x1, List x2, Object x3) {
         this(x1, x2);
      }
   }
}
