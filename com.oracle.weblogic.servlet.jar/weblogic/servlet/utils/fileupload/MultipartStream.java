package weblogic.servlet.utils.fileupload;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

public class MultipartStream {
   public static final byte CR = 13;
   public static final byte LF = 10;
   public static final byte DASH = 45;
   public static final int HEADER_PART_SIZE_MAX = 10240;
   protected static final int DEFAULT_BUFSIZE = 4096;
   protected static final byte[] HEADER_SEPARATOR = new byte[]{13, 10, 13, 10};
   protected static final byte[] FIELD_SEPARATOR = new byte[]{13, 10};
   protected static final byte[] STREAM_TERMINATOR = new byte[]{45, 45};
   protected static final byte[] BOUNDARY_PREFIX = new byte[]{13, 10, 45, 45};
   private final InputStream input;
   private int boundaryLength;
   private int keepRegion;
   private byte[] boundary;
   private final int bufSize;
   private final byte[] buffer;
   private int head;
   private int tail;
   private String headerEncoding;
   private final ProgressNotifier notifier;

   MultipartStream(InputStream input, byte[] boundary, int bufSize, ProgressNotifier pNotifier) {
      this.input = input;
      this.bufSize = bufSize;
      this.buffer = new byte[bufSize];
      this.notifier = pNotifier;
      this.boundaryLength = boundary.length + BOUNDARY_PREFIX.length;
      if (bufSize < this.boundaryLength + 1) {
         throw new BoundaryTooLongException("The boundary length is too long for parsing.");
      } else {
         this.boundary = new byte[boundary.length + BOUNDARY_PREFIX.length];
         this.keepRegion = this.boundary.length;
         System.arraycopy(BOUNDARY_PREFIX, 0, this.boundary, 0, BOUNDARY_PREFIX.length);
         System.arraycopy(boundary, 0, this.boundary, BOUNDARY_PREFIX.length, boundary.length);
         this.head = 0;
         this.tail = 0;
      }
   }

   MultipartStream(InputStream input, byte[] boundary, ProgressNotifier pNotifier) {
      this(input, boundary, 4096, pNotifier);
   }

   public String getHeaderEncoding() {
      return this.headerEncoding;
   }

   public void setHeaderEncoding(String encoding) {
      this.headerEncoding = encoding;
   }

   public byte readByte() throws IOException {
      if (this.head == this.tail) {
         this.head = 0;
         this.tail = this.input.read(this.buffer, this.head, this.bufSize);
         if (this.tail == -1) {
            throw new IOException("No more data is available");
         }

         if (this.notifier != null) {
            this.notifier.noteBytesRead(this.tail);
         }
      }

      return this.buffer[this.head++];
   }

   public boolean readBoundary() throws MalformedStreamException {
      byte[] marker = new byte[2];
      boolean nextChunk = false;
      this.head += this.boundaryLength;

      try {
         marker[0] = this.readByte();
         if (marker[0] == 10) {
            return true;
         } else {
            marker[1] = this.readByte();
            if (arrayequals(marker, STREAM_TERMINATOR, 2)) {
               nextChunk = false;
            } else {
               if (!arrayequals(marker, FIELD_SEPARATOR, 2)) {
                  throw new MalformedStreamException("Unexpected characters follow a boundary");
               }

               nextChunk = true;
            }

            return nextChunk;
         }
      } catch (IOException var4) {
         throw new MalformedStreamException("Stream ended unexpectedly");
      }
   }

   public void setBoundary(byte[] boundary) throws IllegalBoundaryException {
      if (boundary.length != this.boundaryLength - BOUNDARY_PREFIX.length) {
         throw new IllegalBoundaryException("The length of a boundary token can not be changed");
      } else {
         System.arraycopy(boundary, 0, this.boundary, BOUNDARY_PREFIX.length, boundary.length);
      }
   }

   public String readHeaders() throws MalformedStreamException {
      int i = 0;
      java.io.ByteArrayOutputStream baos = new java.io.ByteArrayOutputStream();

      byte b;
      for(int size = 0; i < HEADER_SEPARATOR.length; baos.write(b)) {
         try {
            b = this.readByte();
         } catch (IOException var8) {
            throw new MalformedStreamException("Stream ended unexpectedly");
         }

         ++size;
         if (size > 10240) {
            throw new MalformedStreamException("Header section has more than 10240 bytes (maybe it is not properly terminated)");
         }

         if (b == HEADER_SEPARATOR[i]) {
            ++i;
         } else {
            i = 0;
         }
      }

      String headers = null;
      if (this.headerEncoding != null) {
         try {
            headers = baos.toString(this.headerEncoding);
         } catch (UnsupportedEncodingException var7) {
            headers = baos.toString();
         }
      } else {
         headers = baos.toString();
      }

      return headers;
   }

   public int readBodyData(OutputStream output) throws MalformedStreamException, IOException {
      InputStream istream = this.newInputStream();
      return (int)Streams.copy(istream, output, false);
   }

   ItemInputStream newInputStream() {
      return new ItemInputStream();
   }

   public int discardBodyData() throws MalformedStreamException, IOException {
      return this.readBodyData((OutputStream)null);
   }

   public boolean skipPreamble() throws IOException {
      System.arraycopy(this.boundary, 2, this.boundary, 0, this.boundary.length - 2);
      this.boundaryLength = this.boundary.length - 2;

      boolean var2;
      try {
         this.discardBodyData();
         boolean var1 = this.readBoundary();
         return var1;
      } catch (MalformedStreamException var6) {
         var2 = false;
      } finally {
         System.arraycopy(this.boundary, 0, this.boundary, 2, this.boundary.length - 2);
         this.boundaryLength = this.boundary.length;
         this.boundary[0] = 13;
         this.boundary[1] = 10;
      }

      return var2;
   }

   public static boolean arrayequals(byte[] a, byte[] b, int count) {
      for(int i = 0; i < count; ++i) {
         if (a[i] != b[i]) {
            return false;
         }
      }

      return true;
   }

   protected int findByte(byte value, int pos) {
      for(int i = pos; i < this.tail; ++i) {
         if (this.buffer[i] == value) {
            return i;
         }
      }

      return -1;
   }

   protected int findSeparator() {
      int match = 0;
      int maxpos = this.tail - this.boundaryLength;
      int first = this.head;

      while(true) {
         if (first <= maxpos && match != this.boundaryLength) {
            first = this.findByte(this.boundary[0], first);
            if (first != -1 && first <= maxpos) {
               for(match = 1; match < this.boundaryLength && this.buffer[first + match] == this.boundary[match]; ++match) {
               }

               ++first;
               continue;
            }

            return -1;
         }

         if (match == this.boundaryLength) {
            return first - 1;
         }

         return -1;
      }
   }

   public class ItemInputStream extends InputStream {
      private long total;
      private int pad;
      private int pos;
      private boolean closed;
      private static final int BYTE_POSITIVE_OFFSET = 256;

      ItemInputStream() {
         this.findSeparator();
      }

      private void findSeparator() {
         this.pos = MultipartStream.this.findSeparator();
         if (this.pos == -1) {
            if (MultipartStream.this.tail - MultipartStream.this.head > MultipartStream.this.keepRegion) {
               this.pad = MultipartStream.this.keepRegion;
            } else {
               this.pad = MultipartStream.this.tail - MultipartStream.this.head;
            }
         }

      }

      public long getBytesRead() {
         return this.total;
      }

      public int available() throws IOException {
         return this.pos == -1 ? MultipartStream.this.tail - MultipartStream.this.head - this.pad : this.pos - MultipartStream.this.head;
      }

      public int read() throws IOException {
         if (this.available() == 0 && this.makeAvailable() == 0) {
            return -1;
         } else {
            ++this.total;
            int b = MultipartStream.this.buffer[MultipartStream.this.head++];
            return b >= 0 ? b : b + 256;
         }
      }

      public int read(byte[] b, int off, int len) throws IOException {
         if (len == 0) {
            return 0;
         } else {
            int res = this.available();
            if (res == 0) {
               res = this.makeAvailable();
               if (res == 0) {
                  return -1;
               }
            }

            res = Math.min(res, len);
            System.arraycopy(MultipartStream.this.buffer, MultipartStream.this.head, b, off, res);
            MultipartStream.this.head = MultipartStream.this.head + res;
            this.total += (long)res;
            return res;
         }
      }

      public void close() throws IOException {
         this.close(false);
      }

      public void close(boolean pCloseUnderlying) throws IOException {
         if (!this.closed) {
            if (pCloseUnderlying) {
               this.closed = true;
               MultipartStream.this.input.close();
            } else {
               while(true) {
                  int av = this.available();
                  if (av == 0) {
                     av = this.makeAvailable();
                     if (av == 0) {
                        break;
                     }
                  }

                  this.skip((long)av);
               }
            }

            this.closed = true;
         }
      }

      public long skip(long bytes) throws IOException {
         int av = this.available();
         if (av == 0) {
            av = this.makeAvailable();
            if (av == 0) {
               return 0L;
            }
         }

         long res = Math.min((long)av, bytes);
         MultipartStream.this.head = (int)((long)MultipartStream.this.head + res);
         return res;
      }

      private int makeAvailable() throws IOException {
         if (this.pos != -1) {
            return 0;
         } else {
            this.total += (long)(MultipartStream.this.tail - MultipartStream.this.head - this.pad);
            System.arraycopy(MultipartStream.this.buffer, MultipartStream.this.tail - this.pad, MultipartStream.this.buffer, 0, this.pad);
            MultipartStream.this.head = 0;
            MultipartStream.this.tail = this.pad;

            int av;
            do {
               int bytesRead = MultipartStream.this.input.read(MultipartStream.this.buffer, MultipartStream.this.tail, MultipartStream.this.bufSize - MultipartStream.this.tail);
               if (bytesRead == -1) {
                  String msg = "Stream ended unexpectedly";
                  throw new MalformedStreamException("Stream ended unexpectedly");
               }

               if (MultipartStream.this.notifier != null) {
                  MultipartStream.this.notifier.noteBytesRead(bytesRead);
               }

               MultipartStream.this.tail = MultipartStream.this.tail + bytesRead;
               this.findSeparator();
               av = this.available();
            } while(av <= 0 && this.pos == -1);

            return av;
         }
      }

      public boolean isClosed() {
         return this.closed;
      }
   }

   public static class IllegalBoundaryException extends IOException {
      public IllegalBoundaryException() {
      }

      public IllegalBoundaryException(String message) {
         super(message);
      }
   }

   public static class MalformedStreamException extends IOException {
      public MalformedStreamException() {
      }

      public MalformedStreamException(String message) {
         super(message);
      }
   }

   static class ProgressNotifier {
      private final ProgressListener listener;
      private final long contentLength;
      private long bytesRead;
      private int items;

      ProgressNotifier(ProgressListener pListener, long pContentLength) {
         this.listener = pListener;
         this.contentLength = pContentLength;
      }

      void noteBytesRead(int pBytes) {
         this.bytesRead += (long)pBytes;
         this.notifyListener();
      }

      void noteItem() {
         ++this.items;
      }

      private void notifyListener() {
         if (this.listener != null) {
            this.listener.update(this.bytesRead, this.contentLength, this.items);
         }

      }
   }
}
