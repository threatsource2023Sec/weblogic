package org.glassfish.grizzly.http.util;

import java.util.Arrays;
import org.glassfish.grizzly.utils.Charsets;

public class ContentType {
   private static final String CHARSET_STRING = ";charset=";
   private static final byte[] CHARSET_BYTES;
   private String unparsedContentType;
   private boolean isParsed = true;
   private String compiledContentType;
   private byte[] compiledContentTypeArray;
   private String mimeType;
   private String characterEncoding;
   private String quotedCharsetValue;
   private boolean isCharsetSet;
   private byte[] array = new byte[32];
   private int len = -1;

   public static SettableContentType newSettableContentType() {
      return new SettableContentType();
   }

   public static ContentType newContentType(String contentType) {
      return new ContentType(contentType);
   }

   public static ContentType newContentType(String mimeType, String characterEncoding) {
      ContentType ct = new ContentType();
      ct.setMimeType(mimeType);
      ct.setCharacterEncoding(characterEncoding);
      return ct;
   }

   ContentType() {
   }

   ContentType(String contentType) {
      this.set(contentType);
   }

   public ContentType prepare() {
      this.getByteArray();
      return this;
   }

   public boolean isSet() {
      return this.isMimeTypeSet() || this.quotedCharsetValue != null;
   }

   public boolean isMimeTypeSet() {
      return !this.isParsed || this.mimeType != null;
   }

   public String getMimeType() {
      if (!this.isParsed) {
         this.parse();
      }

      return this.mimeType;
   }

   protected void setMimeType(String mimeType) {
      if (!this.isParsed) {
         this.parse();
      }

      this.mimeType = mimeType;
      this.compiledContentType = !this.isCharsetSet ? mimeType : null;
      this.compiledContentTypeArray = null;
   }

   public String getCharacterEncoding() {
      if (!this.isParsed) {
         this.parse();
      }

      return this.characterEncoding;
   }

   protected void setCharacterEncoding(String charset) {
      if (!this.isParsed) {
         this.parse();
      }

      this.quotedCharsetValue = charset;
      this.isCharsetSet = charset != null;
      if (this.isCharsetSet) {
         this.compiledContentType = null;
         this.characterEncoding = charset.replace('"', ' ').trim();
      } else {
         this.compiledContentType = this.mimeType;
         this.characterEncoding = null;
      }

      this.compiledContentTypeArray = null;
   }

   public int getArrayLen() {
      return this.len;
   }

   public byte[] getByteArray() {
      if (this.compiledContentTypeArray != null) {
         return this.compiledContentTypeArray;
      } else if (this.compiledContentType != null) {
         this.checkArray(this.compiledContentType.length());
         this.compiledContentTypeArray = HttpCodecUtils.toCheckedByteArray(this.compiledContentType, this.array, 0);
         this.len = this.compiledContentType.length();
         return this.compiledContentTypeArray;
      } else {
         if (!this.isParsed) {
            if (this.quotedCharsetValue == null) {
               this.checkArray(this.unparsedContentType.length());
               this.compiledContentTypeArray = HttpCodecUtils.toCheckedByteArray(this.unparsedContentType, this.array, 0);
               return this.compiledContentTypeArray;
            }

            this.parse();
         }

         if (this.mimeType == null) {
            return HttpCodecUtils.EMPTY_ARRAY;
         } else {
            int mtsz = this.mimeType.length();
            if (this.isCharsetSet) {
               int qcssz = this.quotedCharsetValue.length();
               int len = mtsz + qcssz + CHARSET_BYTES.length;
               this.checkArray(len);
               HttpCodecUtils.toCheckedByteArray(this.mimeType, this.array, 0);
               System.arraycopy(CHARSET_BYTES, 0, this.array, mtsz, CHARSET_BYTES.length);
               int offs = mtsz + CHARSET_BYTES.length;
               HttpCodecUtils.toCheckedByteArray(this.quotedCharsetValue, this.array, offs);
               this.len = len;
            } else {
               this.checkArray(mtsz);
               HttpCodecUtils.toCheckedByteArray(this.mimeType, this.array, 0);
               this.len = mtsz;
            }

            this.compiledContentTypeArray = this.array;
            return this.compiledContentTypeArray;
         }
      }
   }

   public String get() {
      if (this.compiledContentType != null) {
         return this.compiledContentType;
      } else {
         if (!this.isParsed) {
            this.parse();
         }

         String ret = this.mimeType;
         if (ret != null && this.isCharsetSet) {
            StringBuilder sb = new StringBuilder(ret.length() + this.quotedCharsetValue.length() + 9);
            ret = sb.append(ret).append(";charset=").append(this.quotedCharsetValue).toString();
         }

         this.compiledContentType = ret;
         return ret;
      }
   }

   protected void set(String contentType) {
      if (this.unparsedContentType != null) {
         this.parse();
      }

      this.unparsedContentType = contentType;
      this.isParsed = contentType == null;
      this.mimeType = null;
      this.compiledContentType = !this.isCharsetSet ? contentType : null;
      this.compiledContentTypeArray = null;
   }

   protected void set(ContentType contentType) {
      if (contentType == null) {
         this.set((String)null);
      } else {
         this.unparsedContentType = contentType.unparsedContentType;
         this.isParsed = contentType.isParsed;
         this.mimeType = contentType.mimeType;
         this.characterEncoding = contentType.characterEncoding;
         this.quotedCharsetValue = contentType.quotedCharsetValue;
         this.isCharsetSet = contentType.isCharsetSet;
         this.compiledContentType = contentType.compiledContentType;
         this.compiledContentTypeArray = contentType.compiledContentTypeArray;
         this.array = contentType.array;
         this.len = contentType.len;
      }
   }

   private void parse() {
      this.isParsed = true;
      String type = this.unparsedContentType;
      boolean hasCharset = false;
      int semicolonIndex = -1;

      int index;
      for(index = type.indexOf(59); index != -1; index = type.indexOf(59, index)) {
         int len = type.length();

         for(semicolonIndex = index++; index < len && type.charAt(index) == ' '; ++index) {
         }

         if (index + 8 < len && type.charAt(index) == 'c' && type.charAt(index + 1) == 'h' && type.charAt(index + 2) == 'a' && type.charAt(index + 3) == 'r' && type.charAt(index + 4) == 's' && type.charAt(index + 5) == 'e' && type.charAt(index + 6) == 't' && type.charAt(index + 7) == '=') {
            hasCharset = true;
            break;
         }
      }

      if (!hasCharset) {
         this.mimeType = type;
      } else {
         this.mimeType = type.substring(0, semicolonIndex);
         String tail = type.substring(index + 8);
         int nextParam = tail.indexOf(59);
         String charsetValue;
         if (nextParam != -1) {
            this.mimeType = this.mimeType + tail.substring(nextParam);
            charsetValue = tail.substring(0, nextParam);
         } else {
            charsetValue = tail;
         }

         if (charsetValue != null && charsetValue.length() > 0) {
            this.isCharsetSet = true;
            this.quotedCharsetValue = charsetValue;
            this.characterEncoding = charsetValue.replace('"', ' ').trim();
         }

      }
   }

   public void serializeToDataChunk(DataChunk dc) {
      if (this.compiledContentTypeArray != null) {
         dc.setBytes(this.compiledContentTypeArray, 0, this.len);
      } else if (this.compiledContentType != null) {
         dc.setString(this.compiledContentType);
      } else {
         dc.setBytes(this.getByteArray(), 0, this.len);
      }
   }

   protected void reset() {
      this.unparsedContentType = null;
      this.compiledContentType = null;
      this.quotedCharsetValue = null;
      this.characterEncoding = null;
      this.compiledContentTypeArray = null;
      this.mimeType = null;
      this.isCharsetSet = false;
      this.isParsed = true;
      this.len = -1;
   }

   public String toString() {
      return this.get();
   }

   public static String getCharsetFromContentType(String contentType) {
      if (contentType == null) {
         return null;
      } else {
         int start = contentType.indexOf("charset=");
         if (start < 0) {
            return null;
         } else {
            String encoding = contentType.substring(start + 8);
            int end = encoding.indexOf(59);
            if (end >= 0) {
               encoding = encoding.substring(0, end);
            }

            encoding = encoding.trim();
            if (encoding.length() > 2 && encoding.startsWith("\"") && encoding.endsWith("\"")) {
               encoding = encoding.substring(1, encoding.length() - 1);
            }

            return encoding.trim();
         }
      }
   }

   public static byte[] removeCharset(byte[] contentType) {
      int ctLen = contentType.length;
      boolean hasCharset = false;
      int semicolon1Index = -1;
      int semicolon2Index = -1;

      for(int index = ByteChunk.indexOf((byte[])contentType, 0, ctLen, (char)';'); index != -1; index = ByteChunk.indexOf(contentType, index, ctLen, ';')) {
         for(semicolon1Index = index++; index < ctLen && contentType[index] == 32; ++index) {
         }

         if (index + 8 < ctLen && contentType[index] == 99 && contentType[index + 1] == 104 && contentType[index + 2] == 97 && contentType[index + 3] == 114 && contentType[index + 4] == 115 && contentType[index + 5] == 101 && contentType[index + 6] == 116 && contentType[index + 7] == 61) {
            semicolon2Index = ByteChunk.indexOf(contentType, index + 8, ctLen, ';');
            hasCharset = true;
            break;
         }
      }

      if (!hasCharset) {
         return contentType;
      } else {
         byte[] array;
         if (semicolon2Index == -1) {
            array = Arrays.copyOf(contentType, semicolon1Index);
         } else {
            array = new byte[ctLen - (semicolon2Index - semicolon1Index)];
            System.arraycopy(contentType, 0, array, 0, semicolon1Index);
            System.arraycopy(contentType, semicolon2Index, array, semicolon1Index, ctLen - semicolon2Index);
         }

         return array;
      }
   }

   public static byte[] compose(byte[] mimeType, String charset) {
      int csLen = charset.length();
      int additionalLen = CHARSET_BYTES.length + csLen;
      byte[] contentType = Arrays.copyOf(mimeType, mimeType.length + additionalLen);
      System.arraycopy(CHARSET_BYTES, 0, contentType, mimeType.length, CHARSET_BYTES.length);
      return HttpCodecUtils.toCheckedByteArray(charset, contentType, mimeType.length + CHARSET_BYTES.length);
   }

   private void checkArray(int len) {
      if (len > this.array.length) {
         this.array = new byte[len << 1];
      }

   }

   static {
      CHARSET_BYTES = ";charset=".getBytes(Charsets.ASCII_CHARSET);
   }

   public static final class SettableContentType extends ContentType {
      SettableContentType() {
      }

      public void reset() {
         super.reset();
      }

      public void set(ContentType contentType) {
         super.set(contentType);
      }

      public void set(String type) {
         super.set(type);
      }

      public void setCharacterEncoding(String charset) {
         super.setCharacterEncoding(charset);
      }

      public void setMimeType(String mimeType) {
         super.setMimeType(mimeType);
      }
   }
}
