package org.glassfish.grizzly.http;

import org.glassfish.grizzly.Buffer;
import org.glassfish.grizzly.Connection;
import org.glassfish.grizzly.TransformationResult;
import org.glassfish.grizzly.compression.lzma.LZMADecoder;
import org.glassfish.grizzly.compression.lzma.LZMAEncoder;

public class LZMAContentEncoding implements ContentEncoding {
   private static final String[] ALIASES = new String[]{"lzma"};
   public static final String NAME = "lzma";
   private final LZMADecoder decoder;
   private final LZMAEncoder encoder;
   private final EncodingFilter encodingFilter;

   public LZMAContentEncoding() {
      this((EncodingFilter)null);
   }

   public LZMAContentEncoding(EncodingFilter encodingFilter) {
      this.decoder = new LZMADecoder();
      this.encoder = new LZMAEncoder();
      if (encodingFilter != null) {
         this.encodingFilter = encodingFilter;
      } else {
         this.encodingFilter = new EncodingFilter() {
            public boolean applyEncoding(HttpHeader httpPacket) {
               return false;
            }

            public boolean applyDecoding(HttpHeader httpPacket) {
               return true;
            }
         };
      }

   }

   public String getName() {
      return "lzma";
   }

   public String[] getAliases() {
      return (String[])ALIASES.clone();
   }

   public static String[] getLzmaAliases() {
      return (String[])ALIASES.clone();
   }

   public boolean wantDecode(HttpHeader header) {
      return this.encodingFilter.applyDecoding(header);
   }

   public boolean wantEncode(HttpHeader header) {
      return this.encodingFilter.applyEncoding(header);
   }

   public ParsingResult decode(Connection connection, HttpContent httpContent) {
      HttpHeader httpHeader = httpContent.getHttpHeader();
      Buffer input = httpContent.getContent();
      TransformationResult result = this.decoder.transform(httpHeader, input);
      Buffer remainder = (Buffer)result.getExternalRemainder();
      if (remainder != null && remainder.hasRemaining()) {
         input.shrink();
      } else {
         input.tryDispose();
         remainder = null;
      }

      try {
         ParsingResult var7;
         switch (result.getStatus()) {
            case COMPLETE:
               httpContent.setContent((Buffer)result.getMessage());
               this.decoder.finish(httpHeader);
               var7 = ParsingResult.create(httpContent, remainder);
               return var7;
            case INCOMPLETE:
               var7 = ParsingResult.create((HttpContent)null, remainder);
               return var7;
            case ERROR:
               throw new IllegalStateException("LZMA decode error. Code: " + result.getErrorCode() + " Description: " + result.getErrorDescription());
            default:
               throw new IllegalStateException("Unexpected status: " + result.getStatus());
         }
      } finally {
         result.recycle();
      }
   }

   public HttpContent encode(Connection connection, HttpContent httpContent) {
      HttpHeader httpHeader = httpContent.getHttpHeader();
      Buffer input = httpContent.getContent();
      if (httpContent.isLast() && !input.hasRemaining()) {
         return httpContent;
      } else {
         TransformationResult result = this.encoder.transform(httpContent.getHttpHeader(), input);
         input.tryDispose();

         HttpContent var7;
         try {
            switch (result.getStatus()) {
               case COMPLETE:
                  this.encoder.finish(httpHeader);
               case INCOMPLETE:
                  break;
               case ERROR:
                  throw new IllegalStateException("LZMA encode error. Code: " + result.getErrorCode() + " Description: " + result.getErrorDescription());
               default:
                  throw new IllegalStateException("Unexpected status: " + result.getStatus());
            }

            Buffer encodedBuffer = (Buffer)result.getMessage();
            if (encodedBuffer != null) {
               httpContent.setContent(encodedBuffer);
               var7 = httpContent;
               return var7;
            }

            var7 = null;
         } finally {
            result.recycle();
         }

         return var7;
      }
   }

   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (o != null && this.getClass() == o.getClass()) {
         LZMAContentEncoding that;
         label45: {
            that = (LZMAContentEncoding)o;
            if (this.decoder != null) {
               if (this.decoder.equals(that.decoder)) {
                  break label45;
               }
            } else if (that.decoder == null) {
               break label45;
            }

            return false;
         }

         label38: {
            if (this.encoder != null) {
               if (this.encoder.equals(that.encoder)) {
                  break label38;
               }
            } else if (that.encoder == null) {
               break label38;
            }

            return false;
         }

         if (this.encodingFilter != null) {
            if (!this.encodingFilter.equals(that.encodingFilter)) {
               return false;
            }
         } else if (that.encodingFilter != null) {
            return false;
         }

         return true;
      } else {
         return false;
      }
   }

   public int hashCode() {
      int result = this.decoder != null ? this.decoder.hashCode() : 0;
      result = 31 * result + (this.encoder != null ? this.encoder.hashCode() : 0);
      result = 31 * result + (this.encodingFilter != null ? this.encodingFilter.hashCode() : 0);
      return result;
   }
}
