package org.apache.xmlbeans.impl.piccolo.xml;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PushbackInputStream;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import org.apache.xmlbeans.impl.piccolo.io.IllegalCharException;

public final class XMLStreamReader extends XMLInputReader {
   private static final int BYTE_BUFFER_SIZE = 8192;
   private XMLDecoder decoder;
   private int minBytesPerChar;
   private int maxBytesPerChar;
   private InputStream in;
   private int[] decodeResult;
   private String encoding;
   private boolean useDeclaredEncoding;
   private boolean rewindDeclaration;
   private char[] cbuf;
   private byte[] bbuf;
   private int cbufPos;
   private int cbufEnd;
   private int bbufPos;
   private int bbufEnd;
   private boolean eofReached;
   private static final int MAX_XML_DECL_CHARS = 100;
   private FastStreamDecoder fastStreamDecoder;
   private JavaStreamDecoder javaStreamDecoder;
   private XMLStreamDecoder activeStreamDecoder;
   private static HashMap charsetTable = new HashMap(31);

   public XMLStreamReader() {
      this.decodeResult = new int[2];
      this.cbuf = new char[100];
      this.bbuf = new byte[8192];
      this.fastStreamDecoder = new FastStreamDecoder();
      this.javaStreamDecoder = null;
   }

   public XMLStreamReader(InputStream in, boolean rewindDeclaration) throws IOException {
      this(in, (String)null, rewindDeclaration);
   }

   public XMLStreamReader(InputStream in, String encoding, boolean rewindDeclaration) throws IOException {
      this.decodeResult = new int[2];
      this.cbuf = new char[100];
      this.bbuf = new byte[8192];
      this.fastStreamDecoder = new FastStreamDecoder();
      this.javaStreamDecoder = null;
      this.reset(in, encoding, rewindDeclaration);
   }

   public void reset(InputStream in, String encoding, boolean rewindDeclaration) throws IOException {
      super.resetInput();
      this.in = in;
      this.eofReached = false;
      this.rewindDeclaration = rewindDeclaration;
      this.useDeclaredEncoding = false;
      this.bbufPos = this.bbufEnd = 0;
      this.cbufPos = this.cbufEnd = 0;
      this.activeStreamDecoder = this.fastStreamDecoder;
      this.fillByteBuffer(true);
      if (encoding != null) {
         this.encoding = this.getJavaCharset(encoding);
         if (this.encoding.equals("Unicode")) {
            this.encoding = this.guessEncoding();
            if (this.encoding == null || !this.encoding.equals("UnicodeLittle")) {
               this.encoding = "UnicodeBig";
            }
         }
      } else {
         this.encoding = this.guessEncoding();
         if (this.encoding == null) {
            this.useDeclaredEncoding = true;
            this.encoding = "UTF-8";
         }
      }

      this.setEncoding(this.encoding);
      this.processXMLDecl();
   }

   public String getEncoding() {
      return this.encoding;
   }

   public void close() throws IOException {
      this.eofReached = true;
      this.bbufPos = this.bbufEnd = this.cbufPos = this.cbufEnd = 0;
      if (this.in != null) {
         this.in.close();
      }

   }

   public void reset() throws IOException {
      super.resetInput();
      this.in.reset();
      this.bbufPos = this.bbufEnd = this.cbufPos = this.cbufEnd = 0;
   }

   public void mark(int readAheadLimit) throws IOException {
      throw new UnsupportedOperationException("mark() not supported");
   }

   public boolean markSupported() {
      return false;
   }

   public int read() throws IOException {
      return this.activeStreamDecoder.read();
   }

   public int read(char[] destbuf) throws IOException {
      return this.read(destbuf, 0, destbuf.length);
   }

   public int read(char[] destbuf, int off, int len) throws IOException {
      return this.activeStreamDecoder.read(destbuf, off, len);
   }

   public boolean ready() throws IOException {
      return this.activeStreamDecoder.ready();
   }

   public long skip(long n) throws IOException {
      return this.activeStreamDecoder.skip(n);
   }

   private void setEncoding(String encoding) throws IOException {
      try {
         this.encoding = encoding;
         this.decoder = XMLDecoderFactory.createDecoder(encoding);
         this.minBytesPerChar = this.decoder.minBytesPerChar();
         this.maxBytesPerChar = this.decoder.maxBytesPerChar();
      } catch (UnsupportedEncodingException var3) {
         if (this.javaStreamDecoder == null) {
            this.javaStreamDecoder = new JavaStreamDecoder();
         }

         this.activeStreamDecoder = this.javaStreamDecoder;
      }

      this.activeStreamDecoder.reset();
   }

   private int fillByteBuffer(boolean inReset) throws IOException {
      int bytesLeft = this.bbufEnd - this.bbufPos;
      if (bytesLeft > 0) {
         System.arraycopy(this.bbuf, this.bbufPos, this.bbuf, 0, bytesLeft);
      }

      this.bbufPos = 0;
      this.bbufEnd = bytesLeft;
      int bytesRead = false;
      int totalBytesRead = 0;

      while(this.bbufEnd < 8192 && totalBytesRead < 160) {
         int bytesRead;
         if ((bytesRead = this.in.read(this.bbuf, this.bbufEnd, 8192 - this.bbufEnd)) != -1) {
            this.bbufEnd += bytesRead;
         }

         totalBytesRead += bytesRead;
         if (bytesRead == -1) {
            this.eofReached = true;
            break;
         }

         if (!inReset) {
            break;
         }
      }

      return totalBytesRead;
   }

   private String getJavaCharset(String charset) {
      if (charset == null) {
         return null;
      } else {
         String xlated = (String)charsetTable.get(charset.toUpperCase());
         return xlated != null ? xlated : charset;
      }
   }

   private String guessEncoding() {
      if (this.bbufEnd < 4) {
         return null;
      } else {
         switch (this.bbuf[0]) {
            case -17:
               if (this.bbuf[1] == -69 && this.bbuf[2] == -65) {
                  this.bbufPos = 3;
                  return "UTF-8";
               }

               return null;
            case -2:
               if (this.bbuf[1] == -1) {
                  if (this.bbuf[2] == 0 && this.bbuf[3] == 0) {
                     this.bbufPos = 4;
                     return "UCS-4";
                  }

                  this.bbufPos = 2;
                  return "UnicodeBig";
               }

               return null;
            case -1:
               if (this.bbuf[1] == -2) {
                  if (this.bbuf[2] == 0 && this.bbuf[3] == 0) {
                     this.bbufPos = 4;
                     return "UCS-4";
                  }

                  this.bbufPos = 2;
                  return "UnicodeLittle";
               }

               return null;
            case 0:
               switch (this.bbuf[1]) {
                  case 0:
                     if (this.bbuf[2] == -2 && this.bbuf[3] == -1) {
                        this.bbufPos = 4;
                        return "UCS-4";
                     } else if (this.bbuf[2] == -1 && this.bbuf[3] == -2) {
                        this.bbufPos = 4;
                        return "UCS-4";
                     } else {
                        if ((this.bbuf[2] != 60 || this.bbuf[3] != 0) && (this.bbuf[2] != 0 || this.bbuf[3] != 60)) {
                           return null;
                        }

                        return "UCS-4";
                     }
                  case 60:
                     if (this.bbuf[2] == 0 && this.bbuf[3] == 63) {
                        return "UnicodeBigUnmarked";
                     } else {
                        if (this.bbuf[2] == 0 && this.bbuf[3] == 0) {
                           return "UCS-4";
                        }

                        return null;
                     }
                  default:
                     return null;
               }
            case 60:
               switch (this.bbuf[1]) {
                  case 0:
                     if (this.bbuf[2] == 63 && this.bbuf[3] == 0) {
                        return "UnicodeLittleUnmarked";
                     } else {
                        if (this.bbuf[2] == 0 && this.bbuf[3] == 0) {
                           return "UCS-4";
                        }

                        return null;
                     }
                  case 63:
                     if (this.bbuf[2] == 120 && this.bbuf[3] == 109) {
                        this.useDeclaredEncoding = true;
                        return "UTF-8";
                     }

                     return null;
                  default:
                     return null;
               }
            case 76:
               if (this.bbuf[1] == 111 && this.bbuf[2] == -89 && this.bbuf[3] == -108) {
                  this.useDeclaredEncoding = true;
                  return "Cp037";
               }

               return null;
            default:
               this.useDeclaredEncoding = true;
               return null;
         }
      }
   }

   private void processXMLDecl() throws IOException {
      int initialBBufPos = this.bbufPos;
      this.decoder.decodeXMLDecl(this.bbuf, this.bbufPos, this.bbufEnd - this.bbufPos, this.cbuf, this.cbufPos, this.cbuf.length, this.decodeResult);
      this.bbufPos += this.decodeResult[0];
      this.cbufEnd = this.decodeResult[1];
      int numCharsParsed = this.parseXMLDeclaration(this.cbuf, 0, this.cbufEnd);
      if (numCharsParsed > 0) {
         String declaredEncoding = this.getJavaCharset(this.getXMLDeclaredEncoding());
         if (!this.rewindDeclaration) {
            this.cbufPos += numCharsParsed;
         }

         if (this.useDeclaredEncoding && declaredEncoding != null && !declaredEncoding.equalsIgnoreCase(this.encoding)) {
            this.cbufPos = this.cbufEnd = 0;
            this.decoder.reset();
            if (this.rewindDeclaration) {
               this.bbufPos = initialBBufPos;
            } else {
               this.bbufPos = numCharsParsed * this.minBytesPerChar;
            }

            this.setEncoding(declaredEncoding);
         }
      }

   }

   // $FF: synthetic method
   static int access$114(XMLStreamReader x0, long x1) {
      return x0.cbufPos = (int)((long)x0.cbufPos + x1);
   }

   static {
      charsetTable.put("EBCDIC-CP-US", "Cp037");
      charsetTable.put("EBCDIC-CP-CA", "Cp037");
      charsetTable.put("EBCDIC-CP-NL", "Cp037");
      charsetTable.put("EBCDIC-CP-WT", "Cp037");
      charsetTable.put("EBCDIC-CP-DK", "Cp277");
      charsetTable.put("EBCDIC-CP-NO", "Cp277");
      charsetTable.put("EBCDIC-CP-FI", "Cp278");
      charsetTable.put("EBCDIC-CP-SE", "Cp278");
      charsetTable.put("EBCDIC-CP-IT", "Cp280");
      charsetTable.put("EBCDIC-CP-ES", "Cp284");
      charsetTable.put("EBCDIC-CP-GB", "Cp285");
      charsetTable.put("EBCDIC-CP-FR", "Cp297");
      charsetTable.put("EBCDIC-CP-AR1", "Cp420");
      charsetTable.put("EBCDIC-CP-GR", "Cp423");
      charsetTable.put("EBCDIC-CP-HE", "Cp424");
      charsetTable.put("EBCDIC-CP-BE", "Cp500");
      charsetTable.put("EBCDIC-CP-CH", "Cp500");
      charsetTable.put("EBCDIC-CP-ROECE", "Cp870");
      charsetTable.put("EBCDIC-CP-YU", "Cp870");
      charsetTable.put("EBCDIC-CP-IS", "Cp871");
      charsetTable.put("EBCDIC-CP-TR", "Cp905");
      charsetTable.put("EBCDIC-CP-AR2", "Cp918");
      charsetTable.put("UTF-16", "Unicode");
      charsetTable.put("ISO-10646-UCS-2", "Unicode");
      charsetTable.put("ANSI_X3.4-1986", "ASCII");
      charsetTable.put("ASCII", "ASCII");
      charsetTable.put("CP367", "ASCII");
      charsetTable.put("CSASCII", "ASCII");
      charsetTable.put("IBM-367", "ASCII");
      charsetTable.put("IBM367", "ASCII");
      charsetTable.put("ISO-IR-6", "ASCII");
      charsetTable.put("ISO646-US", "ASCII");
      charsetTable.put("ISO_646.IRV:1991", "ASCII");
      charsetTable.put("US", "ASCII");
      charsetTable.put("US-ASCII", "ASCII");
      charsetTable.put("BIG5", "BIG5");
      charsetTable.put("CSBIG5", "BIG5");
      charsetTable.put("CP037", "CP037");
      charsetTable.put("CSIBM037", "CP037");
      charsetTable.put("IBM-37", "CP037");
      charsetTable.put("IBM037", "CP037");
      charsetTable.put("CP1026", "CP1026");
      charsetTable.put("CSIBM1026", "CP1026");
      charsetTable.put("IBM-1026", "CP1026");
      charsetTable.put("IBM1026", "CP1026");
      charsetTable.put("CP1047", "CP1047");
      charsetTable.put("IBM-1047", "CP1047");
      charsetTable.put("IBM1047", "CP1047");
      charsetTable.put("CCSID01140", "CP1140");
      charsetTable.put("CP01140", "CP1140");
      charsetTable.put("IBM-1140", "CP1140");
      charsetTable.put("IBM01140", "CP1140");
      charsetTable.put("CCSID01141", "CP1141");
      charsetTable.put("CP01141", "CP1141");
      charsetTable.put("IBM-1141", "CP1141");
      charsetTable.put("IBM01141", "CP1141");
      charsetTable.put("CCSID01142", "CP1142");
      charsetTable.put("CP01142", "CP1142");
      charsetTable.put("IBM-1142", "CP1142");
      charsetTable.put("IBM01142", "CP1142");
      charsetTable.put("CCSID01143", "CP1143");
      charsetTable.put("CP01143", "CP1143");
      charsetTable.put("IBM-1143", "CP1143");
      charsetTable.put("IBM01143", "CP1143");
      charsetTable.put("CCSID01144", "CP1144");
      charsetTable.put("CP01144", "CP1144");
      charsetTable.put("IBM-1144", "CP1144");
      charsetTable.put("IBM01144", "CP1144");
      charsetTable.put("CCSID01145", "CP1145");
      charsetTable.put("CP01145", "CP1145");
      charsetTable.put("IBM-1145", "CP1145");
      charsetTable.put("IBM01145", "CP1145");
      charsetTable.put("CCSID01146", "CP1146");
      charsetTable.put("CP01146", "CP1146");
      charsetTable.put("IBM-1146", "CP1146");
      charsetTable.put("IBM01146", "CP1146");
      charsetTable.put("CCSID01147", "CP1147");
      charsetTable.put("CP01147", "CP1147");
      charsetTable.put("IBM-1147", "CP1147");
      charsetTable.put("IBM01147", "CP1147");
      charsetTable.put("CCSID01148", "CP1148");
      charsetTable.put("CP01148", "CP1148");
      charsetTable.put("IBM-1148", "CP1148");
      charsetTable.put("IBM01148", "CP1148");
      charsetTable.put("CCSID01149", "CP1149");
      charsetTable.put("CP01149", "CP1149");
      charsetTable.put("IBM-1149", "CP1149");
      charsetTable.put("IBM01149", "CP1149");
      charsetTable.put("WINDOWS-1250", "CP1250");
      charsetTable.put("WINDOWS-1251", "CP1251");
      charsetTable.put("WINDOWS-1252", "CP1252");
      charsetTable.put("WINDOWS-1253", "CP1253");
      charsetTable.put("WINDOWS-1254", "CP1254");
      charsetTable.put("WINDOWS-1255", "CP1255");
      charsetTable.put("WINDOWS-1256", "CP1256");
      charsetTable.put("WINDOWS-1257", "CP1257");
      charsetTable.put("WINDOWS-1258", "CP1258");
      charsetTable.put("CP273", "CP273");
      charsetTable.put("CSIBM273", "CP273");
      charsetTable.put("IBM-273", "CP273");
      charsetTable.put("IBM273", "CP273");
      charsetTable.put("CP277", "CP277");
      charsetTable.put("CSIBM277", "CP277");
      charsetTable.put("IBM-277", "CP277");
      charsetTable.put("IBM277", "CP277");
      charsetTable.put("CP278", "CP278");
      charsetTable.put("CSIBM278", "CP278");
      charsetTable.put("IBM-278", "CP278");
      charsetTable.put("IBM278", "CP278");
      charsetTable.put("CP280", "CP280");
      charsetTable.put("CSIBM280", "CP280");
      charsetTable.put("IBM-280", "CP280");
      charsetTable.put("IBM280", "CP280");
      charsetTable.put("CP284", "CP284");
      charsetTable.put("CSIBM284", "CP284");
      charsetTable.put("IBM-284", "CP284");
      charsetTable.put("IBM284", "CP284");
      charsetTable.put("CP285", "CP285");
      charsetTable.put("CSIBM285", "CP285");
      charsetTable.put("IBM-285", "CP285");
      charsetTable.put("IBM285", "CP285");
      charsetTable.put("CP290", "CP290");
      charsetTable.put("CSIBM290", "CP290");
      charsetTable.put("EBCDIC-JP-KANA", "CP290");
      charsetTable.put("IBM-290", "CP290");
      charsetTable.put("IBM290", "CP290");
      charsetTable.put("CP297", "CP297");
      charsetTable.put("CSIBM297", "CP297");
      charsetTable.put("IBM-297", "CP297");
      charsetTable.put("IBM297", "CP297");
      charsetTable.put("CP420", "CP420");
      charsetTable.put("CSIBM420", "CP420");
      charsetTable.put("IBM-420", "CP420");
      charsetTable.put("IBM420", "CP420");
      charsetTable.put("CP424", "CP424");
      charsetTable.put("CSIBM424", "CP424");
      charsetTable.put("IBM-424", "CP424");
      charsetTable.put("IBM424", "CP424");
      charsetTable.put("437", "CP437");
      charsetTable.put("CP437", "CP437");
      charsetTable.put("CSPC8CODEPAGE437", "CP437");
      charsetTable.put("IBM-437", "CP437");
      charsetTable.put("IBM437", "CP437");
      charsetTable.put("CP500", "CP500");
      charsetTable.put("CSIBM500", "CP500");
      charsetTable.put("IBM-500", "CP500");
      charsetTable.put("IBM500", "CP500");
      charsetTable.put("CP775", "CP775");
      charsetTable.put("CSPC775BALTIC", "CP775");
      charsetTable.put("IBM-775", "CP775");
      charsetTable.put("IBM775", "CP775");
      charsetTable.put("850", "CP850");
      charsetTable.put("CP850", "CP850");
      charsetTable.put("CSPC850MULTILINGUAL", "CP850");
      charsetTable.put("IBM-850", "CP850");
      charsetTable.put("IBM850", "CP850");
      charsetTable.put("852", "CP852");
      charsetTable.put("CP852", "CP852");
      charsetTable.put("CSPCP852", "CP852");
      charsetTable.put("IBM-852", "CP852");
      charsetTable.put("IBM852", "CP852");
      charsetTable.put("855", "CP855");
      charsetTable.put("CP855", "CP855");
      charsetTable.put("CSIBM855", "CP855");
      charsetTable.put("IBM-855", "CP855");
      charsetTable.put("IBM855", "CP855");
      charsetTable.put("857", "CP857");
      charsetTable.put("CP857", "CP857");
      charsetTable.put("CSIBM857", "CP857");
      charsetTable.put("IBM-857", "CP857");
      charsetTable.put("IBM857", "CP857");
      charsetTable.put("CCSID00858", "CP858");
      charsetTable.put("CP00858", "CP858");
      charsetTable.put("IBM-858", "CP858");
      charsetTable.put("IBM00858", "CP858");
      charsetTable.put("860", "CP860");
      charsetTable.put("CP860", "CP860");
      charsetTable.put("CSIBM860", "CP860");
      charsetTable.put("IBM-860", "CP860");
      charsetTable.put("IBM860", "CP860");
      charsetTable.put("861", "CP861");
      charsetTable.put("CP-IS", "CP861");
      charsetTable.put("CP861", "CP861");
      charsetTable.put("CSIBM861", "CP861");
      charsetTable.put("IBM-861", "CP861");
      charsetTable.put("IBM861", "CP861");
      charsetTable.put("862", "CP862");
      charsetTable.put("CP862", "CP862");
      charsetTable.put("CSPC862LATINHEBREW", "CP862");
      charsetTable.put("IBM-862", "CP862");
      charsetTable.put("IBM862", "CP862");
      charsetTable.put("863", "CP863");
      charsetTable.put("CP863", "CP863");
      charsetTable.put("CSIBM863", "CP863");
      charsetTable.put("IBM-863", "CP863");
      charsetTable.put("IBM863", "CP863");
      charsetTable.put("CP864", "CP864");
      charsetTable.put("CSIBM864", "CP864");
      charsetTable.put("IBM-864", "CP864");
      charsetTable.put("IBM864", "CP864");
      charsetTable.put("865", "CP865");
      charsetTable.put("CP865", "CP865");
      charsetTable.put("CSIBM865", "CP865");
      charsetTable.put("IBM-865", "CP865");
      charsetTable.put("IBM865", "CP865");
      charsetTable.put("866", "CP866");
      charsetTable.put("CP866", "CP866");
      charsetTable.put("CSIBM866", "CP866");
      charsetTable.put("IBM-866", "CP866");
      charsetTable.put("IBM866", "CP866");
      charsetTable.put("CP-AR", "CP868");
      charsetTable.put("CP868", "CP868");
      charsetTable.put("CSIBM868", "CP868");
      charsetTable.put("IBM-868", "CP868");
      charsetTable.put("IBM868", "CP868");
      charsetTable.put("CP-GR", "CP869");
      charsetTable.put("CP869", "CP869");
      charsetTable.put("CSIBM869", "CP869");
      charsetTable.put("IBM-869", "CP869");
      charsetTable.put("IBM869", "CP869");
      charsetTable.put("CP870", "CP870");
      charsetTable.put("CSIBM870", "CP870");
      charsetTable.put("IBM-870", "CP870");
      charsetTable.put("IBM870", "CP870");
      charsetTable.put("CP871", "CP871");
      charsetTable.put("CSIBM871", "CP871");
      charsetTable.put("IBM-871", "CP871");
      charsetTable.put("IBM871", "CP871");
      charsetTable.put("CP918", "CP918");
      charsetTable.put("CSIBM918", "CP918");
      charsetTable.put("IBM-918", "CP918");
      charsetTable.put("IBM918", "CP918");
      charsetTable.put("CCSID00924", "CP924");
      charsetTable.put("CP00924", "CP924");
      charsetTable.put("EBCDIC-LATIN9--EURO", "CP924");
      charsetTable.put("IBM-924", "CP924");
      charsetTable.put("IBM00924", "CP924");
      charsetTable.put("CSEUCPKDFMTJAPANESE", "EUCJIS");
      charsetTable.put("EUC-JP", "EUCJIS");
      charsetTable.put("EXTENDED_UNIX_CODE_PACKED_FORMAT_FOR_JAPANESE", "EUCJIS");
      charsetTable.put("GB18030", "GB18030");
      charsetTable.put("CSGB2312", "GB2312");
      charsetTable.put("GB2312", "GB2312");
      charsetTable.put("ISO-2022-CN", "ISO2022CN");
      charsetTable.put("CSISO2022KR", "ISO2022KR");
      charsetTable.put("ISO-2022-KR", "ISO2022KR");
      charsetTable.put("CP819", "ISO8859_1");
      charsetTable.put("CSISOLATIN1", "ISO8859_1");
      charsetTable.put("IBM-819", "ISO8859_1");
      charsetTable.put("IBM819", "ISO8859_1");
      charsetTable.put("ISO-8859-1", "ISO8859_1");
      charsetTable.put("ISO-IR-100", "ISO8859_1");
      charsetTable.put("ISO_8859-1", "ISO8859_1");
      charsetTable.put("L1", "ISO8859_1");
      charsetTable.put("LATIN1", "ISO8859_1");
      charsetTable.put("CSISOLATIN2", "ISO8859_2");
      charsetTable.put("ISO-8859-2", "ISO8859_2");
      charsetTable.put("ISO-IR-101", "ISO8859_2");
      charsetTable.put("ISO_8859-2", "ISO8859_2");
      charsetTable.put("L2", "ISO8859_2");
      charsetTable.put("LATIN2", "ISO8859_2");
      charsetTable.put("CSISOLATIN3", "ISO8859_3");
      charsetTable.put("ISO-8859-3", "ISO8859_3");
      charsetTable.put("ISO-IR-109", "ISO8859_3");
      charsetTable.put("ISO_8859-3", "ISO8859_3");
      charsetTable.put("L3", "ISO8859_3");
      charsetTable.put("LATIN3", "ISO8859_3");
      charsetTable.put("CSISOLATIN4", "ISO8859_4");
      charsetTable.put("ISO-8859-4", "ISO8859_4");
      charsetTable.put("ISO-IR-110", "ISO8859_4");
      charsetTable.put("ISO_8859-4", "ISO8859_4");
      charsetTable.put("L4", "ISO8859_4");
      charsetTable.put("LATIN4", "ISO8859_4");
      charsetTable.put("CSISOLATINCYRILLIC", "ISO8859_5");
      charsetTable.put("CYRILLIC", "ISO8859_5");
      charsetTable.put("ISO-8859-5", "ISO8859_5");
      charsetTable.put("ISO-IR-144", "ISO8859_5");
      charsetTable.put("ISO_8859-5", "ISO8859_5");
      charsetTable.put("ARABIC", "ISO8859_6");
      charsetTable.put("ASMO-708", "ISO8859_6");
      charsetTable.put("CSISOLATINARABIC", "ISO8859_6");
      charsetTable.put("ECMA-114", "ISO8859_6");
      charsetTable.put("ISO-8859-6", "ISO8859_6");
      charsetTable.put("ISO-IR-127", "ISO8859_6");
      charsetTable.put("ISO_8859-6", "ISO8859_6");
      charsetTable.put("CSISOLATINGREEK", "ISO8859_7");
      charsetTable.put("ECMA-118", "ISO8859_7");
      charsetTable.put("ELOT_928", "ISO8859_7");
      charsetTable.put("GREEK", "ISO8859_7");
      charsetTable.put("GREEK8", "ISO8859_7");
      charsetTable.put("ISO-8859-7", "ISO8859_7");
      charsetTable.put("ISO-IR-126", "ISO8859_7");
      charsetTable.put("ISO_8859-7", "ISO8859_7");
      charsetTable.put("CSISOLATINHEBREW", "ISO8859_8");
      charsetTable.put("HEBREW", "ISO8859_8");
      charsetTable.put("ISO-8859-8", "ISO8859_8");
      charsetTable.put("ISO-8859-8-I", "ISO8859_8");
      charsetTable.put("ISO-IR-138", "ISO8859_8");
      charsetTable.put("ISO_8859-8", "ISO8859_8");
      charsetTable.put("CSISOLATIN5", "ISO8859_9");
      charsetTable.put("ISO-8859-9", "ISO8859_9");
      charsetTable.put("ISO-IR-148", "ISO8859_9");
      charsetTable.put("ISO_8859-9", "ISO8859_9");
      charsetTable.put("L5", "ISO8859_9");
      charsetTable.put("LATIN5", "ISO8859_9");
      charsetTable.put("CSISO2022JP", "JIS");
      charsetTable.put("ISO-2022-JP", "JIS");
      charsetTable.put("CSISO13JISC6220JP", "JIS0201");
      charsetTable.put("X0201", "JIS0201");
      charsetTable.put("CSISO87JISX0208", "JIS0208");
      charsetTable.put("ISO-IR-87", "JIS0208");
      charsetTable.put("X0208", "JIS0208");
      charsetTable.put("X0208DBIJIS_X0208-1983", "JIS0208");
      charsetTable.put("CSISO159JISX02121990", "JIS0212");
      charsetTable.put("ISO-IR-159", "JIS0212");
      charsetTable.put("X0212", "JIS0212");
      charsetTable.put("CSKOI8R", "KOI8_R");
      charsetTable.put("KOI8-R", "KOI8_R");
      charsetTable.put("EUC-KR", "KSC5601");
      charsetTable.put("CSWINDOWS31J", "MS932");
      charsetTable.put("WINDOWS-31J", "MS932");
      charsetTable.put("CSSHIFTJIS", "SJIS");
      charsetTable.put("MS_KANJI", "SJIS");
      charsetTable.put("SHIFT_JIS", "SJIS");
      charsetTable.put("TIS-620", "TIS620");
      charsetTable.put("UTF-16BE", "UNICODEBIG");
      charsetTable.put("UTF-16LE", "UNICODELITTLE");
      charsetTable.put("UTF-8", "UTF8");
   }

   private class JavaStreamDecoder implements XMLStreamDecoder {
      private Reader reader;
      char[] oneCharBuffer = new char[1];
      boolean sawCR;

      public JavaStreamDecoder() throws IOException {
      }

      public void reset() throws IOException {
         this.sawCR = false;
         if (XMLStreamReader.this.bbufEnd - XMLStreamReader.this.bbufPos > 0) {
            PushbackInputStream pbIn = new PushbackInputStream(XMLStreamReader.this.in, XMLStreamReader.this.bbufEnd - XMLStreamReader.this.bbufPos);
            pbIn.unread(XMLStreamReader.this.bbuf, XMLStreamReader.this.bbufPos, XMLStreamReader.this.bbufEnd - XMLStreamReader.this.bbufPos);
            this.reader = new InputStreamReader(pbIn, XMLStreamReader.this.encoding);
         } else {
            this.reader = new InputStreamReader(XMLStreamReader.this.in, XMLStreamReader.this.encoding);
         }

      }

      public int read() throws IOException {
         int c;
         do {
            c = this.read(this.oneCharBuffer, 0, 1);
            if (c > 0) {
               return this.oneCharBuffer[0];
            }
         } while(c >= 0);

         return c;
      }

      public int read(char[] destbuf, int off, int len) throws IOException {
         int numChars = this.reader.read(destbuf, off, len);
         int outpos = off;
         if (numChars < 0) {
            return numChars;
         } else {
            for(int i = 0; i < numChars; ++i) {
               int inpos = i + off;
               char c = destbuf[inpos];
               if (c >= ' ') {
                  if (c > '\ud7ff' && (c < '\ue000' || c > '�') && (c < 65536 || c > 1114111)) {
                     throw new IllegalCharException("Illegal XML Character: 0x" + Integer.toHexString(c));
                  }

                  this.sawCR = false;
                  if (inpos != outpos) {
                     destbuf[outpos] = c;
                  }

                  ++outpos;
               } else {
                  switch (c) {
                     case '\t':
                        destbuf[outpos++] = '\t';
                        break;
                     case '\n':
                        if (this.sawCR) {
                           this.sawCR = false;
                        } else {
                           destbuf[outpos++] = '\n';
                        }
                        break;
                     case '\u000b':
                     case '\f':
                     default:
                        System.out.println("Char: " + c + " [" + c + "]");
                        throw new IllegalCharException("Illegal XML character: 0x" + Integer.toHexString(c));
                     case '\r':
                        this.sawCR = true;
                        destbuf[outpos++] = '\n';
                  }
               }
            }

            return outpos - off;
         }
      }

      public boolean ready() throws IOException {
         return this.reader.ready();
      }

      public long skip(long n) throws IOException {
         long skipped;
         for(skipped = 0L; skipped < n; skipped += (long)XMLStreamReader.this.cbufEnd) {
            XMLStreamReader.this.cbufEnd = this.read(XMLStreamReader.this.cbuf, 0, (int)Math.min(n, 100L));
            if (XMLStreamReader.this.cbufEnd <= 0) {
               return skipped;
            }
         }

         return skipped;
      }
   }

   private class FastStreamDecoder implements XMLStreamDecoder {
      public FastStreamDecoder() {
      }

      public void reset() {
      }

      public int read() throws IOException {
         if (XMLStreamReader.this.cbufEnd - XMLStreamReader.this.cbufPos > 0) {
            return XMLStreamReader.this.cbuf[XMLStreamReader.this.cbufPos++];
         } else {
            XMLStreamReader.this.cbufPos = XMLStreamReader.this.cbufEnd = 0;
            XMLStreamReader.this.cbufEnd = this.read(XMLStreamReader.this.cbuf, XMLStreamReader.this.cbufPos, 100);
            return XMLStreamReader.this.cbufEnd > 0 ? XMLStreamReader.this.cbuf[XMLStreamReader.this.cbufPos++] : -1;
         }
      }

      public int read(char[] destbuf, int off, int len) throws IOException {
         int charsRead = 0;
         if (XMLStreamReader.this.cbufEnd - XMLStreamReader.this.cbufPos > 0) {
            int numToRead = Math.min(XMLStreamReader.this.cbufEnd - XMLStreamReader.this.cbufPos, len - charsRead);
            if (numToRead > 0) {
               System.arraycopy(XMLStreamReader.this.cbuf, XMLStreamReader.this.cbufPos, destbuf, off, numToRead);
               charsRead += numToRead;
               XMLStreamReader.this.cbufPos = numToRead;
            }
         }

         if (charsRead < len) {
            if (XMLStreamReader.this.bbufEnd - XMLStreamReader.this.bbufPos < XMLStreamReader.this.maxBytesPerChar) {
               XMLStreamReader.this.fillByteBuffer(false);
               if (XMLStreamReader.this.bbufEnd - XMLStreamReader.this.bbufPos < XMLStreamReader.this.minBytesPerChar) {
                  return charsRead <= 0 ? -1 : charsRead;
               }
            }

            XMLStreamReader.this.decoder.decode(XMLStreamReader.this.bbuf, XMLStreamReader.this.bbufPos, XMLStreamReader.this.bbufEnd - XMLStreamReader.this.bbufPos, destbuf, off + charsRead, len - charsRead, XMLStreamReader.this.decodeResult);
            XMLStreamReader.this.bbufPos = XMLStreamReader.this.decodeResult[0];
            charsRead += XMLStreamReader.this.decodeResult[1];
         }

         return charsRead == 0 && XMLStreamReader.this.eofReached ? -1 : charsRead;
      }

      public boolean ready() throws IOException {
         return XMLStreamReader.this.cbufEnd - XMLStreamReader.this.cbufPos > 0 || XMLStreamReader.this.bbufEnd - XMLStreamReader.this.bbufPos > XMLStreamReader.this.maxBytesPerChar || XMLStreamReader.this.in.available() > 0;
      }

      public long skip(long n) throws IOException {
         long skipped = 0L;
         if (XMLStreamReader.this.cbufEnd - XMLStreamReader.this.cbufPos > 0) {
            skipped = Math.min((long)XMLStreamReader.this.cbufEnd - (long)XMLStreamReader.this.cbufPos, n);
            XMLStreamReader.access$114(XMLStreamReader.this, skipped);
         }

         while(skipped < n) {
            XMLStreamReader.this.cbufPos = 0;
            XMLStreamReader.this.cbufEnd = this.read(XMLStreamReader.this.cbuf, 0, 100);
            if (XMLStreamReader.this.cbufEnd <= 0) {
               XMLStreamReader.this.cbufEnd = 0;
               return skipped;
            }

            XMLStreamReader.this.cbufPos = (int)Math.min((long)XMLStreamReader.this.cbufEnd, n - skipped);
            skipped += (long)XMLStreamReader.this.cbufPos;
         }

         return skipped;
      }
   }

   private interface XMLStreamDecoder {
      int read() throws IOException;

      int read(char[] var1, int var2, int var3) throws IOException;

      boolean ready() throws IOException;

      long skip(long var1) throws IOException;

      void reset() throws IOException;
   }
}
