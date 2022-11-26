package org.glassfish.grizzly.http.util;

import java.util.Arrays;
import java.util.Iterator;
import org.glassfish.grizzly.Buffer;

public class MimeHeaders {
   private static final String[] INVALID_TRAILER_NAMES;
   public static final int MAX_NUM_HEADERS_UNBOUNDED = -1;
   public static final int MAX_NUM_HEADERS_DEFAULT = 100;
   public static final int DEFAULT_HEADER_SIZE = 8;
   public static DataChunk NOOP_CHUNK;
   private MimeHeaderField[] headers = new MimeHeaderField[8];
   private int count;
   private boolean marked;
   protected int mark;
   private int maxNumHeaders = 100;
   private final Iterable namesIterable = new Iterable() {
      public Iterator iterator() {
         return new NamesIterator(MimeHeaders.this, false);
      }
   };

   public void mark() {
      if (!this.marked) {
         this.marked = true;
         this.mark = this.count;
      }

   }

   public void recycle() {
      this.clear();
   }

   public void clear() {
      for(int i = 0; i < this.count; ++i) {
         this.headers[i].recycle();
      }

      this.count = 0;
      this.mark = 0;
      this.marked = false;
   }

   public String toString() {
      StringBuilder sb = new StringBuilder();
      sb.append("=== MimeHeaders ===\n");

      for(int i = 0; i < this.count; ++i) {
         sb.append(this.headers[i].nameB).append(" = ").append(this.headers[i].valueB).append('\n');
      }

      return sb.toString();
   }

   public void copyFrom(MimeHeaders source) {
      if (source != null && source.size() != 0) {
         this.maxNumHeaders = source.maxNumHeaders;
         this.count = source.count;
         if (this.headers.length < this.count) {
            MimeHeaderField[] tmp = new MimeHeaderField[this.count * 2];
            System.arraycopy(this.headers, 0, tmp, 0, this.headers.length);
            this.headers = tmp;
         }

         int i = 0;

         for(int len = source.count; i < len; ++i) {
            MimeHeaderField sourceField = source.headers[i];
            if (this.isValidName(sourceField.getName().toString())) {
               MimeHeaderField f = this.headers[i];
               if (f == null) {
                  f = new MimeHeaderField();
                  this.headers[i] = f;
               }

               if (sourceField.nameB.type == DataChunk.Type.Buffer) {
                  copyBufferChunk(sourceField.nameB, f.nameB);
               } else {
                  f.nameB.set(sourceField.nameB);
               }

               if (sourceField.valueB.type == DataChunk.Type.Buffer) {
                  copyBufferChunk(sourceField.valueB, f.valueB);
               } else {
                  f.valueB.set(sourceField.valueB);
               }
            }
         }

      }
   }

   private static void copyBufferChunk(DataChunk source, DataChunk dest) {
      BufferChunk bc = source.getBufferChunk();
      int l = bc.getLength();
      byte[] bytes = new byte[l];
      Buffer b = bc.getBuffer();
      int oldPos = b.position();

      try {
         b.position(bc.getStart());
         bc.getBuffer().get(bytes, 0, l);
         dest.setBytes(bytes);
      } finally {
         b.position(oldPos);
      }

   }

   public int size() {
      return this.count;
   }

   public int trailerSize() {
      return this.marked ? this.count - this.mark : 0;
   }

   public DataChunk getName(int n) {
      return n >= 0 && n < this.count ? this.headers[n].getName() : null;
   }

   public DataChunk getValue(int n) {
      return n >= 0 && n < this.count ? this.headers[n].getValue() : null;
   }

   public boolean isSerialized(int n) {
      if (n >= 0 && n < this.count) {
         MimeHeaderField field = this.headers[n];
         return field.isSerialized();
      } else {
         return false;
      }
   }

   public boolean setSerialized(int n, boolean newValue) {
      boolean value;
      if (n >= 0 && n < this.count) {
         MimeHeaderField field = this.headers[n];
         value = field.isSerialized();
         field.setSerialized(newValue);
      } else {
         value = true;
      }

      return value;
   }

   public int indexOf(String name, int fromIndex) {
      for(int i = fromIndex; i < this.count; ++i) {
         if (this.headers[i].getName().equalsIgnoreCase(name)) {
            return i;
         }
      }

      return -1;
   }

   public int indexOf(Header header, int fromIndex) {
      byte[] bytes = header.getLowerCaseBytes();

      for(int i = fromIndex; i < this.count; ++i) {
         if (this.headers[i].getName().equalsIgnoreCaseLowerCase(bytes)) {
            return i;
         }
      }

      return -1;
   }

   public boolean contains(Header header) {
      return this.indexOf((Header)header, 0) >= 0;
   }

   public boolean contains(String header) {
      return this.indexOf((String)header, 0) >= 0;
   }

   public Iterable names() {
      return this.namesIterable;
   }

   public Iterable trailerNames() {
      return new Iterable() {
         public Iterator iterator() {
            return new NamesIterator(MimeHeaders.this, true);
         }
      };
   }

   public Iterable values(final String name) {
      return new Iterable() {
         public Iterator iterator() {
            return new ValuesIterator(MimeHeaders.this, name, false);
         }
      };
   }

   public Iterable values(Header name) {
      return this.values(name.toString());
   }

   public Iterable trailerValues(final String name) {
      return new Iterable() {
         public Iterator iterator() {
            return new ValuesIterator(MimeHeaders.this, name, true);
         }
      };
   }

   public Iterable trailerValues(Header name) {
      return this.trailerValues(name.toString());
   }

   private MimeHeaderField createHeader() {
      if (this.maxNumHeaders >= 0 && this.count == this.maxNumHeaders) {
         throw new MaxHeaderCountExceededException();
      } else {
         int len = this.headers.length;
         if (this.count >= len) {
            int newCount = this.count * 2;
            if (this.maxNumHeaders >= 0 && newCount > this.maxNumHeaders) {
               newCount = this.maxNumHeaders;
            }

            MimeHeaderField[] tmp = new MimeHeaderField[newCount];
            System.arraycopy(this.headers, 0, tmp, 0, len);
            this.headers = tmp;
         }

         MimeHeaderField mh;
         if ((mh = this.headers[this.count]) == null) {
            this.headers[this.count] = mh = new MimeHeaderField();
         }

         ++this.count;
         return mh;
      }
   }

   public DataChunk addValue(String name) {
      if (!this.isValidName(name)) {
         return NOOP_CHUNK;
      } else {
         MimeHeaderField mh = this.createHeader();
         mh.getName().setString(name);
         return mh.getValue();
      }
   }

   public DataChunk addValue(Header header) {
      if (!this.isValidName(header)) {
         return NOOP_CHUNK;
      } else {
         MimeHeaderField mh = this.createHeader();
         mh.getName().setBytes(header.toByteArray());
         return mh.getValue();
      }
   }

   public DataChunk addValue(byte[] buffer, int startN, int len) {
      if (!this.isValidName(buffer)) {
         return NOOP_CHUNK;
      } else {
         MimeHeaderField mhf = this.createHeader();
         mhf.getName().setBytes(buffer, startN, startN + len);
         return mhf.getValue();
      }
   }

   public DataChunk addValue(Buffer buffer, int startN, int len) {
      if (!this.isValidName(buffer)) {
         return NOOP_CHUNK;
      } else {
         MimeHeaderField mhf = this.createHeader();
         mhf.getName().setBuffer(buffer, startN, startN + len);
         return mhf.getValue();
      }
   }

   public DataChunk setValue(String name) {
      if (!this.isValidName(name)) {
         return NOOP_CHUNK;
      } else {
         for(int i = 0; i < this.count; ++i) {
            if (this.headers[i].getName().equalsIgnoreCase(name)) {
               for(int j = i + 1; j < this.count; ++j) {
                  if (this.headers[j].getName().equalsIgnoreCase(name)) {
                     this.removeHeader(j--);
                  }
               }

               return this.headers[i].getValue();
            }
         }

         MimeHeaderField mh = this.createHeader();
         mh.getName().setString(name);
         return mh.getValue();
      }
   }

   public DataChunk setValue(Header header) {
      if (!this.isValidName(header)) {
         return NOOP_CHUNK;
      } else {
         byte[] bytes = header.getLowerCaseBytes();

         for(int i = 0; i < this.count; ++i) {
            if (this.headers[i].getName().equalsIgnoreCaseLowerCase(bytes)) {
               for(int j = i + 1; j < this.count; ++j) {
                  if (this.headers[j].getName().equalsIgnoreCaseLowerCase(bytes)) {
                     this.removeHeader(j--);
                  }
               }

               return this.headers[i].getValue();
            }
         }

         MimeHeaderField mh = this.createHeader();
         mh.getName().setBytes(header.toByteArray());
         return mh.getValue();
      }
   }

   public DataChunk getValue(String name) {
      for(int i = 0; i < this.count; ++i) {
         if (this.headers[i].getName().equalsIgnoreCase(name)) {
            return this.headers[i].getValue();
         }
      }

      return null;
   }

   public DataChunk getValue(Header header) {
      byte[] bytes = header.getLowerCaseBytes();

      for(int i = 0; i < this.count; ++i) {
         if (this.headers[i].getName().equalsIgnoreCaseLowerCase(bytes)) {
            return this.headers[i].getValue();
         }
      }

      return null;
   }

   public String getHeader(String name) {
      DataChunk mh = this.getValue(name);
      return mh != null ? mh.toString() : null;
   }

   public String getHeader(Header header) {
      DataChunk mh = this.getValue(header);
      return mh != null ? mh.toString() : null;
   }

   public void removeHeader(String name) {
      for(int i = 0; i < this.count; ++i) {
         if (this.headers[i].getName().equalsIgnoreCase(name)) {
            this.removeHeader(i--);
         }
      }

   }

   public void removeHeader(Header header) {
      for(int i = 0; i < this.count; ++i) {
         if (this.headers[i].getName().equalsIgnoreCase(header.getBytes())) {
            this.removeHeader(i--);
         }
      }

   }

   public void removeHeader(String name, String str) {
      for(int i = 0; i < this.count; ++i) {
         if (this.headers[i].getName().equalsIgnoreCase(name) && this.getValue(i) != null && this.getValue(i).toString() != null && this.getValue(i).toString().contains(str)) {
            this.removeHeader(i--);
         }
      }

   }

   public void removeHeaderMatches(String name, String regex) {
      for(int i = 0; i < this.count; ++i) {
         if (this.headers[i].getName().equalsIgnoreCase(name) && this.getValue(i) != null && this.getValue(i).toString() != null && this.getValue(i).toString().matches(regex)) {
            this.removeHeader(i--);
         }
      }

   }

   public void removeHeaderMatches(Header header, String regex) {
      for(int i = 0; i < this.count; ++i) {
         if (this.headers[i].getName().equalsIgnoreCaseLowerCase(header.getLowerCaseBytes()) && this.getValue(i) != null && this.getValue(i).toString() != null && this.getValue(i).toString().matches(regex)) {
            this.removeHeader(i--);
         }
      }

   }

   void removeHeader(int idx) {
      MimeHeaderField mh = this.headers[idx];
      mh.recycle();
      this.headers[idx] = this.headers[this.count - 1];
      this.headers[this.count - 1] = mh;
      --this.count;
   }

   public void setMaxNumHeaders(int maxNumHeaders) {
      this.maxNumHeaders = maxNumHeaders;
   }

   public int getMaxNumHeaders() {
      return this.maxNumHeaders;
   }

   private boolean isValidName(String name) {
      return !this.marked || Arrays.binarySearch(INVALID_TRAILER_NAMES, name.toLowerCase()) < 0;
   }

   private boolean isValidName(Header name) {
      return !this.marked || Arrays.binarySearch(INVALID_TRAILER_NAMES, name.getLowerCase()) < 0;
   }

   private boolean isValidName(byte[] name) {
      return !this.marked || Arrays.binarySearch(INVALID_TRAILER_NAMES, (new String(name)).toLowerCase()) < 0;
   }

   private boolean isValidName(Buffer name) {
      return !this.marked || Arrays.binarySearch(INVALID_TRAILER_NAMES, name.toStringContent().toLowerCase()) < 0;
   }

   static {
      INVALID_TRAILER_NAMES = new String[]{Header.CacheControl.getLowerCase(), Header.Expect.getLowerCase(), Header.Host.getLowerCase(), Header.MaxForwards.getLowerCase(), Header.Pragma.getLowerCase(), Header.Range.getLowerCase(), Header.TE.getLowerCase(), Header.SetCookie.getLowerCase(), Header.Authorization.getLowerCase(), Header.WWWAuthenticate.getLowerCase(), Header.ProxyAuthenticate.getLowerCase(), Header.ProxyAuthorization.getLowerCase(), Header.Age.getLowerCase(), Header.Date.getLowerCase(), Header.Location.getLowerCase(), Header.RetryAfter.getLowerCase(), Header.Vary.getLowerCase(), Header.Warnings.getLowerCase(), Header.IfMatch.getLowerCase(), Header.IfNoneMatch.getLowerCase(), Header.IfModifiedSince.getLowerCase(), Header.IfUnmodifiedSince.getLowerCase(), Header.IfRange.getLowerCase()};
      Arrays.sort(INVALID_TRAILER_NAMES);
      NOOP_CHUNK = new DataChunk.Immutable((DataChunk)null);
   }

   public class MaxHeaderCountExceededException extends IllegalStateException {
      public MaxHeaderCountExceededException() {
         super("Illegal attempt to exceed the configured maximum number of headers: " + MimeHeaders.this.maxNumHeaders);
      }
   }
}
