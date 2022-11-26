package weblogic.servlet.internal;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import weblogic.utils.StringUtils;
import weblogic.utils.classloaders.Source;
import weblogic.utils.io.Chunk;

public abstract class ByteRangeHandler {
   protected static final String RANGE = "Range";
   protected Source source;
   protected String contentType;

   public ByteRangeHandler(Source src, String cType) {
      this.source = src;
      this.contentType = cType;
   }

   public abstract void sendRangeData(HttpServletResponse var1) throws IOException;

   public static ByteRangeHandler makeInstance(Source src, HttpServletRequest req, String cType) {
      long length = src.length();
      String type = cType == null ? "text/plain" : cType;
      Enumeration headers = req.getHeaders("Range");
      if (HTTPDebugLogger.isEnabled()) {
         Enumeration enum_ = req.getHeaders("Range");

         while(enum_.hasMoreElements()) {
            p("[Range :]" + enum_.nextElement());
         }
      }

      if (headers == null) {
         return null;
      } else {
         List rangeList = parseByteRanges(headers, length);
         if (rangeList != null && rangeList.size() != 0) {
            if (rangeList.size() == 1) {
               ByteRangeInfo info = (ByteRangeInfo)rangeList.get(0);
               ByteRangeHandler handler = new SingleByteRangeHandler(src, type, info);
               return handler;
            } else if (rangeList.size() > 1) {
               String name = req.getHeader("Request-Range");
               ByteRangeHandler handler = new MultipleByteRangeHandler(src, type, rangeList);
               if (name != null) {
                  ((MultipleByteRangeHandler)handler).setRequestRange(true);
               }

               return handler;
            } else {
               return null;
            }
         } else {
            return new UnsatisfiableRangeHandler(src, type);
         }
      }
   }

   public void write(InputStream is, OutputStream os, long length) throws IOException {
      if (HTTPDebugLogger.isEnabled()) {
         this.pp("write (is, os, length) length: " + length);
      }

      try {
         ((ServletOutputStreamImpl)os).writeStream(is, (int)length);
      } catch (ClassCastException var6) {
         this.writeDirectly(is, os, length);
      }

   }

   private void writeDirectly(InputStream is, OutputStream os, long length) throws IOException {
      Chunk c = Chunk.getChunk();
      int len = (int)length;
      int read = false;

      try {
         while(len > 0) {
            if (c.end == Chunk.CHUNK_SIZE) {
               os.write(c.buf, 0, c.end);
               c.end = 0;
            }

            int toRead = Math.min(len, Chunk.CHUNK_SIZE - c.end);
            int read;
            if ((read = is.read(c.buf, c.end, toRead)) == -1) {
               break;
            }

            c.end += read;
            len -= read;
         }

         if (c.end > 0) {
            os.write(c.buf, 0, c.end);
            len -= c.end;
         }

         if (len > 0) {
            throw new IOException("Failed to read '" + len + "' bytes from InputStream");
         }
      } finally {
         Chunk.releaseChunk(c);
      }

   }

   public static List parseByteRanges(Enumeration headers, long size) {
      List list = new ArrayList();

      while(headers.hasMoreElements()) {
         String value = (String)headers.nextElement();
         String[] tokens = StringUtils.splitCompletely(value, "=,");

         for(int i = 0; i < tokens.length; ++i) {
            if (HTTPDebugLogger.isEnabled()) {
               p(tokens[i]);
            }

            try {
               long toIndex = -1L;
               long fromIndex = -1L;
               String token = tokens[i].trim();
               int id = token.indexOf(45);
               if (id < 0) {
                  if (!"bytes".equals(token)) {
                     if (HTTPDebugLogger.isEnabled()) {
                        p("Warn! bad range header " + token);
                     }
                     break;
                  }

                  if (HTTPDebugLogger.isEnabled()) {
                     p(" bytes ");
                  }
               } else {
                  ByteRangeInfo info;
                  if (id == 0) {
                     if (HTTPDebugLogger.isEnabled()) {
                        p("Only last-byte-pos set " + token);
                     }

                     if (id + 1 == token.length()) {
                        if (HTTPDebugLogger.isEnabled()) {
                           p("Warn!: Bad range header!");
                        }
                     } else {
                        toIndex = Long.parseLong(token.substring(id + 1));
                        info = makeRangeInfo(-1L, toIndex, size);
                        if (info != null) {
                           list.add(info);
                        }
                     }
                  } else if (id + 1 < token.length()) {
                     fromIndex = Long.parseLong(token.substring(0, id));
                     toIndex = Long.parseLong(token.substring(id + 1));
                     info = makeRangeInfo(fromIndex, toIndex, size);
                     if (info != null) {
                        list.add(info);
                     }
                  } else {
                     fromIndex = Long.parseLong(token.substring(0, id));
                     toIndex = size - 1L;
                     info = makeRangeInfo(fromIndex, toIndex, size);
                     if (info != null) {
                        list.add(info);
                     }
                  }
               }
            } catch (NumberFormatException var14) {
            }
         }
      }

      return list;
   }

   private static ByteRangeInfo makeRangeInfo(long from, long to, long size) {
      if (from == -1L && to == -1L) {
         return null;
      } else if (from >= 0L && to >= 0L && from > to) {
         return null;
      } else {
         if (to > size) {
            to = size - 1L;
         }

         return from < size ? new ByteRangeInfo(from, to, size) : null;
      }
   }

   static void p(String out) {
      HTTPDebugLogger.debug("[ByteRangeHandler]" + out);
   }

   protected void pp(String out) {
      HTTPDebugLogger.debug("[" + this.getClass().getName() + "]" + out);
   }

   static class ByteRangeInfo {
      long fromIndex;
      long toIndex;
      long total;

      public ByteRangeInfo(long first, long last, long size) {
         this.fromIndex = first;
         this.toIndex = last;
         this.total = size;
      }

      public String toString() {
         return "[ByteRangeInfo] fromIndex: " + this.fromIndex + " toIndex : " + this.toIndex;
      }

      public long getFromIndex() {
         if (this.fromIndex < 0L) {
            long t = this.total - this.toIndex;
            if (t < 0L) {
               t = 0L;
            }

            return t;
         } else {
            return this.fromIndex;
         }
      }

      public long getToIndex() {
         if (this.fromIndex < 0L) {
            return this.total - 1L;
         } else {
            return this.toIndex >= 0L && this.toIndex < this.total ? this.toIndex : this.total - 1L;
         }
      }

      public String toHeader() {
         return "bytes " + this.getFromIndex() + '-' + this.getToIndex() + '/' + this.total;
      }
   }

   static class UnsatisfiableRangeHandler extends ByteRangeHandler {
      UnsatisfiableRangeHandler(Source src, String cType) {
         super(src, cType);
      }

      public void sendRangeData(HttpServletResponse response) throws IOException {
         InputStream is = null;

         try {
            long length = this.source.length();
            response.setStatus(416);
            if (response instanceof ServletResponseImpl) {
               ((ServletResponseImpl)response).setHeaderInternal("Content-Range", "bytes */" + length);
            } else {
               response.setHeader("Content-Range", "bytes */" + length);
            }

            if (length != -1L) {
               response.setContentLength((int)length);
            }

            if (this.contentType != null) {
               response.setContentType(this.contentType);
            }

            is = this.source.getInputStream();
            this.write(is, response.getOutputStream(), (long)((int)length));
         } finally {
            if (is != null) {
               is.close();
            }

         }

      }
   }

   static class SingleByteRangeHandler extends ByteRangeHandler {
      ByteRangeInfo info;

      SingleByteRangeHandler(Source src, String cType, ByteRangeInfo info) {
         super(src, cType);
         this.info = info;
      }

      public void sendRangeData(HttpServletResponse response) throws IOException {
         InputStream is = null;

         try {
            if (HTTPDebugLogger.isEnabled()) {
               this.pp("sendRangeData");
            }

            long length = this.info.getToIndex() - this.info.getFromIndex() + 1L;
            response.setStatus(206);
            if (response instanceof ServletResponseImpl) {
               ((ServletResponseImpl)response).setHeaderInternal("Content-Range", this.info.toHeader());
            } else {
               response.setHeader("Content-Range", this.info.toHeader());
            }

            if (HTTPDebugLogger.isEnabled()) {
               this.pp("Content-Type: " + this.contentType);
            }

            response.setContentType(this.contentType);
            if (HTTPDebugLogger.isEnabled()) {
               this.pp("Content-Range: " + this.info.toHeader());
            }

            OutputStream out = response.getOutputStream();
            is = this.source.getInputStream();
            long fromIndex = this.info.getFromIndex();
            if (fromIndex > 0L) {
               is.skip(fromIndex);
            }

            this.write(is, out, length);
         } finally {
            if (is != null) {
               is.close();
            }

         }

      }
   }

   static class MultipleByteRangeHandler extends ByteRangeHandler {
      static final String SEPERATOR = "--";
      static final String CRLF = "\r\n";
      private String boundary;
      List rangeList;
      boolean requestRange;

      MultipleByteRangeHandler(Source src, String cType, List range) {
         super(src, cType);
         this.rangeList = range;
         this.boundary = System.currentTimeMillis() + "WLS";
      }

      public void setRequestRange(boolean b) {
         this.requestRange = b;
      }

      public boolean hasRequestRange() {
         return this.requestRange;
      }

      public void sendRangeData(HttpServletResponse response) throws IOException {
         OutputStream outputStream = response.getOutputStream();
         response.setStatus(206);
         String cType = null;
         if (this.hasRequestRange()) {
            cType = "multipart/x-byteranges; boundary=";
         } else {
            cType = "multipart/byteranges; boundary=";
         }

         response.addHeader("Content-Type", cType + this.boundary);
         InputStream is = this.source.getInputStream();
         if (is == null) {
            response.sendError(404);
         } else {
            Iterator i = this.rangeList.iterator();
            long count = 0L;

            while(i.hasNext()) {
               ByteRangeInfo info = (ByteRangeInfo)i.next();
               long start = info.getFromIndex();
               long length = info.getToIndex() - info.getFromIndex() + 1L;
               String header = this.getStartRange(info);
               outputStream.write(header.getBytes());
               if (count < start) {
                  is.skip(start - count);
               } else if (count > start) {
                  if (is != null) {
                     is.close();
                  }

                  is = this.source.getInputStream();
                  is.skip(start);
               }

               count = start + length;
               if (HTTPDebugLogger.isEnabled()) {
                  this.pp("fromIndex : " + info.getFromIndex() + " toIndex :" + info.getToIndex() + " total : " + length);
               }

               this.write(is, outputStream, length);
               outputStream.write(this.getEndRangeHeader().getBytes());
            }

            if (is != null) {
               is.close();
            }

            outputStream.write(this.getFinalRangeHeader().getBytes());
         }
      }

      private String getStartRange(ByteRangeInfo info) {
         return "--" + this.boundary + "\r\n" + "Content-Type: " + this.contentType + "\r\n" + "Content-Range: " + info.toHeader() + "\r\n" + "\r\n";
      }

      private String getEndRangeHeader() {
         return "\r\n";
      }

      private String getFinalRangeHeader() {
         return "--" + this.boundary + "--" + "\r\n";
      }
   }
}
