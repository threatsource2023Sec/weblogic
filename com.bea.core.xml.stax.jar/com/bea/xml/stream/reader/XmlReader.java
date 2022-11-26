package com.bea.xml.stream.reader;

import java.io.ByteArrayInputStream;
import java.io.CharConversionException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PushbackInputStream;
import java.io.Reader;
import java.util.Hashtable;

public final class XmlReader extends Reader {
   private static final int MAXPUSHBACK = 512;
   private Reader in;
   private String assignedEncoding;
   private boolean closed;
   private static final Hashtable charsets = new Hashtable(31);

   public static Reader createReader(InputStream in) throws IOException {
      return new XmlReader(in);
   }

   public static Reader createReader(InputStream in, String encoding) throws IOException {
      if (encoding == null) {
         return new XmlReader(in);
      } else if (!"UTF-8".equalsIgnoreCase(encoding) && !"UTF8".equalsIgnoreCase(encoding)) {
         if (!"US-ASCII".equalsIgnoreCase(encoding) && !"ASCII".equalsIgnoreCase(encoding)) {
            return (Reader)("ISO-8859-1".equalsIgnoreCase(encoding) ? new Iso8859_1Reader(in) : new InputStreamReader(in, std2java(encoding)));
         } else {
            return new AsciiReader(in);
         }
      } else {
         return new Utf8Reader(in);
      }
   }

   private static String std2java(String encoding) {
      String temp = encoding.toUpperCase();
      temp = (String)charsets.get(temp);
      return temp != null ? temp : encoding;
   }

   public String getEncoding() {
      return this.assignedEncoding;
   }

   private XmlReader(InputStream stream) throws IOException {
      super(stream);
      PushbackInputStream pb = new PushbackInputStream(stream, 512);
      byte[] buf = new byte[4];
      int len = pb.read(buf);
      if (len > 0) {
         pb.unread(buf, 0, len);
      }

      if (len == 4) {
         label47:
         switch (buf[0] & 255) {
            case 0:
               if (buf[1] == 60 && buf[2] == 0 && buf[3] == 63) {
                  this.setEncoding(pb, "UnicodeBig");
                  return;
               }
               break;
            case 60:
               switch (buf[1] & 255) {
                  case 0:
                     if (buf[2] == 63 && buf[3] == 0) {
                        this.setEncoding(pb, "UnicodeLittle");
                        return;
                     }
                     break label47;
                  case 63:
                     if (buf[2] == 120 && buf[3] == 109) {
                        this.useEncodingDecl(pb, "UTF8");
                        return;
                     }
                  default:
                     break label47;
               }
            case 76:
               if (buf[1] == 111 && (255 & buf[2]) == 167 && (255 & buf[3]) == 148) {
                  this.useEncodingDecl(pb, "CP037");
                  return;
               }
               break;
            case 254:
               if ((buf[1] & 255) == 255) {
                  this.setEncoding(pb, "UTF-16");
                  return;
               }
               break;
            case 255:
               if ((buf[1] & 255) == 254) {
                  this.setEncoding(pb, "UTF-16");
                  return;
               }
         }
      }

      this.setEncoding(pb, "UTF-8");
   }

   private void useEncodingDecl(PushbackInputStream pb, String encoding) throws IOException {
      byte[] buffer = new byte[512];
      int len = pb.read(buffer, 0, buffer.length);
      pb.unread(buffer, 0, len);
      Reader r = new InputStreamReader(new ByteArrayInputStream(buffer, 4, len), encoding);
      if (r.read() != 108) {
         this.setEncoding(pb, "UTF-8");
      } else {
         StringBuffer buf = new StringBuffer();
         StringBuffer keyBuf = null;
         String key = null;
         boolean sawEq = false;
         char quoteChar = 0;
         boolean sawQuestion = false;

         int c;
         label130:
         for(int i = 0; i < 507 && (c = r.read()) != -1; ++i) {
            if (c != 32 && c != 9 && c != 10 && c != 13) {
               if (i == 0) {
                  break;
               }

               if (c == 63) {
                  sawQuestion = true;
               } else if (sawQuestion) {
                  if (c == 62) {
                     break;
                  }

                  sawQuestion = false;
               }

               if (key != null && sawEq) {
                  if (!Character.isWhitespace((char)c)) {
                     if (c == 34 || c == 39) {
                        if (quoteChar == 0) {
                           quoteChar = (char)c;
                           buf.setLength(0);
                           continue;
                        }

                        if (c == quoteChar) {
                           if ("encoding".equals(key)) {
                              this.assignedEncoding = buf.toString();

                              for(i = 0; i < this.assignedEncoding.length(); ++i) {
                                 int c = this.assignedEncoding.charAt(i);
                                 if ((c < 'A' || c > 'Z') && (c < 'a' || c > 'z') && (i == 0 || i <= 0 || c != '-' && (c < '0' || c > '9') && c != '.' && c != '_')) {
                                    break label130;
                                 }
                              }

                              this.setEncoding(pb, this.assignedEncoding);
                              return;
                           }

                           key = null;
                           continue;
                        }
                     }

                     buf.append((char)c);
                  }
               } else if (keyBuf == null) {
                  if (!Character.isWhitespace((char)c)) {
                     keyBuf = buf;
                     buf.setLength(0);
                     buf.append((char)c);
                     sawEq = false;
                  }
               } else if (Character.isWhitespace((char)c)) {
                  key = keyBuf.toString();
               } else if (c == 61) {
                  if (key == null) {
                     key = keyBuf.toString();
                  }

                  sawEq = true;
                  keyBuf = null;
                  quoteChar = 0;
               } else {
                  keyBuf.append((char)c);
               }
            }
         }

         this.setEncoding(pb, "UTF-8");
      }
   }

   private void setEncoding(InputStream stream, String encoding) throws IOException {
      this.assignedEncoding = encoding;
      this.in = createReader(stream, encoding);
   }

   public int read(char[] buf, int off, int len) throws IOException {
      if (this.closed) {
         return -1;
      } else {
         int val = this.in.read(buf, off, len);
         if (val == -1) {
            this.close();
         }

         return val;
      }
   }

   public int read() throws IOException {
      if (this.closed) {
         throw new IOException("closed");
      } else {
         int val = this.in.read();
         if (val == -1) {
            this.close();
         }

         return val;
      }
   }

   public boolean markSupported() {
      return this.in == null ? false : this.in.markSupported();
   }

   public void mark(int value) throws IOException {
      if (this.in != null) {
         this.in.mark(value);
      }

   }

   public void reset() throws IOException {
      if (this.in != null) {
         this.in.reset();
      }

   }

   public long skip(long value) throws IOException {
      return this.in == null ? 0L : this.in.skip(value);
   }

   public boolean ready() throws IOException {
      return this.in == null ? false : this.in.ready();
   }

   public void close() throws IOException {
      if (!this.closed) {
         this.in.close();
         this.in = null;
         this.closed = true;
      }
   }

   static {
      charsets.put("UTF-16", "Unicode");
      charsets.put("ISO-10646-UCS-2", "Unicode");
      charsets.put("EBCDIC-CP-US", "cp037");
      charsets.put("EBCDIC-CP-CA", "cp037");
      charsets.put("EBCDIC-CP-NL", "cp037");
      charsets.put("EBCDIC-CP-WT", "cp037");
      charsets.put("EBCDIC-CP-DK", "cp277");
      charsets.put("EBCDIC-CP-NO", "cp277");
      charsets.put("EBCDIC-CP-FI", "cp278");
      charsets.put("EBCDIC-CP-SE", "cp278");
      charsets.put("EBCDIC-CP-IT", "cp280");
      charsets.put("EBCDIC-CP-ES", "cp284");
      charsets.put("EBCDIC-CP-GB", "cp285");
      charsets.put("EBCDIC-CP-FR", "cp297");
      charsets.put("EBCDIC-CP-AR1", "cp420");
      charsets.put("EBCDIC-CP-HE", "cp424");
      charsets.put("EBCDIC-CP-BE", "cp500");
      charsets.put("EBCDIC-CP-CH", "cp500");
      charsets.put("EBCDIC-CP-ROECE", "cp870");
      charsets.put("EBCDIC-CP-YU", "cp870");
      charsets.put("EBCDIC-CP-IS", "cp871");
      charsets.put("EBCDIC-CP-AR2", "cp918");
   }

   static final class Iso8859_1Reader extends BaseReader {
      Iso8859_1Reader(InputStream in) {
         super(in);
      }

      public int read(char[] buf, int offset, int len) throws IOException {
         if (this.instream == null) {
            return -1;
         } else if (offset + len <= buf.length && offset >= 0) {
            int i;
            for(i = 0; i < len; ++i) {
               if (this.start >= this.finish) {
                  this.start = 0;
                  this.finish = this.instream.read(this.buffer, 0, this.buffer.length);
                  if (this.finish <= 0) {
                     if (this.finish <= 0) {
                        this.close();
                     }
                     break;
                  }
               }

               buf[offset + i] = (char)(255 & this.buffer[this.start++]);
            }

            return i == 0 && this.finish <= 0 ? -1 : i;
         } else {
            throw new ArrayIndexOutOfBoundsException();
         }
      }
   }

   static final class AsciiReader extends BaseReader {
      AsciiReader(InputStream in) {
         super(in);
      }

      public int read(char[] buf, int offset, int len) throws IOException {
         if (this.instream == null) {
            return -1;
         } else if (offset + len <= buf.length && offset >= 0) {
            int i;
            for(i = 0; i < len; ++i) {
               if (this.start >= this.finish) {
                  this.start = 0;
                  this.finish = this.instream.read(this.buffer, 0, this.buffer.length);
                  if (this.finish <= 0) {
                     if (this.finish <= 0) {
                        this.close();
                     }
                     break;
                  }
               }

               int c = this.buffer[this.start++];
               if ((c & 128) != 0) {
                  throw new CharConversionException("Illegal ASCII character, 0x" + Integer.toHexString(c & 255));
               }

               buf[offset + i] = (char)c;
            }

            return i == 0 && this.finish <= 0 ? -1 : i;
         } else {
            throw new ArrayIndexOutOfBoundsException();
         }
      }
   }

   static final class Utf8Reader extends BaseReader {
      private char nextChar;

      Utf8Reader(InputStream stream) {
         super(stream);
      }

      public int read(char[] buf, int offset, int len) throws IOException {
         int i = 0;
         int c = 0;
         if (len <= 0) {
            return 0;
         } else if (offset + len <= buf.length && offset >= 0) {
            if (this.nextChar != 0) {
               buf[offset + i++] = this.nextChar;
               this.nextChar = 0;
            }

            while(i < len) {
               if (this.finish <= this.start) {
                  if (this.instream == null) {
                     c = -1;
                     break;
                  }

                  this.start = 0;
                  this.finish = this.instream.read(this.buffer, 0, this.buffer.length);
                  if (this.finish <= 0) {
                     this.close();
                     c = -1;
                     break;
                  }
               }

               c = this.buffer[this.start] & 255;
               if ((c & 128) == 0) {
                  ++this.start;
                  buf[offset + i++] = (char)c;
               } else {
                  int off = this.start;

                  try {
                     if ((this.buffer[off] & 224) == 192) {
                        c = (this.buffer[off++] & 31) << 6;
                        c += this.buffer[off++] & 63;
                     } else if ((this.buffer[off] & 240) == 224) {
                        c = (this.buffer[off++] & 15) << 12;
                        c += (this.buffer[off++] & 63) << 6;
                        c += this.buffer[off++] & 63;
                     } else {
                        if ((this.buffer[off] & 248) != 240) {
                           throw new CharConversionException("Unconvertible UTF-8 character beginning with 0x" + Integer.toHexString(this.buffer[this.start] & 255));
                        }

                        c = (this.buffer[off++] & 7) << 18;
                        c += (this.buffer[off++] & 63) << 12;
                        c += (this.buffer[off++] & 63) << 6;
                        c += this.buffer[off++] & 63;
                        if (c > 1114111) {
                           throw new CharConversionException("UTF-8 encoding of character 0x00" + Integer.toHexString(c) + " can't be converted to Unicode.");
                        }

                        if (c > 65535) {
                           c -= 65536;
                           this.nextChar = (char)('\udc00' + (c & 1023));
                           c = '\ud800' + (c >> 10);
                        }
                     }
                  } catch (ArrayIndexOutOfBoundsException var8) {
                     c = 0;
                  }

                  if (off > this.finish) {
                     System.arraycopy(this.buffer, this.start, this.buffer, 0, this.finish - this.start);
                     this.finish -= this.start;
                     this.start = 0;
                     off = this.instream.read(this.buffer, this.finish, this.buffer.length - this.finish);
                     if (off < 0) {
                        this.close();
                        throw new CharConversionException("Partial UTF-8 char");
                     }

                     this.finish += off;
                  } else {
                     ++this.start;

                     while(this.start < off) {
                        if ((this.buffer[this.start] & 192) != 128) {
                           this.close();
                           throw new CharConversionException("Malformed UTF-8 char -- is an XML encoding declaration missing?");
                        }

                        ++this.start;
                     }

                     buf[offset + i++] = (char)c;
                     if (this.nextChar != 0 && i < len) {
                        buf[offset + i++] = this.nextChar;
                        this.nextChar = 0;
                     }
                  }
               }
            }

            if (i > 0) {
               return i;
            } else {
               return c == -1 ? -1 : 0;
            }
         } else {
            throw new ArrayIndexOutOfBoundsException();
         }
      }
   }

   abstract static class BaseReader extends Reader {
      protected InputStream instream;
      protected byte[] buffer;
      protected int start;
      protected int finish;

      BaseReader(InputStream stream) {
         super(stream);
         this.instream = stream;
         this.buffer = new byte[8192];
      }

      public boolean ready() throws IOException {
         return this.instream == null || this.finish - this.start > 0 || this.instream.available() != 0;
      }

      public void close() throws IOException {
         if (this.instream != null) {
            this.instream.close();
            this.start = this.finish = 0;
            this.buffer = null;
            this.instream = null;
         }

      }
   }
}
