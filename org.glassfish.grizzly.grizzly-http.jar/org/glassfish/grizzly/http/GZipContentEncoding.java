package org.glassfish.grizzly.http;

import org.glassfish.grizzly.Buffer;
import org.glassfish.grizzly.Connection;
import org.glassfish.grizzly.TransformationResult;
import org.glassfish.grizzly.compression.zip.GZipDecoder;
import org.glassfish.grizzly.compression.zip.GZipEncoder;
import org.glassfish.grizzly.memory.Buffers;

public class GZipContentEncoding implements ContentEncoding {
   public static final int DEFAULT_IN_BUFFER_SIZE = 512;
   public static final int DEFAULT_OUT_BUFFER_SIZE = 512;
   private static final String[] ALIASES = new String[]{"gzip", "deflate"};
   public static final String NAME = "gzip";
   private final GZipDecoder decoder;
   private final GZipEncoder encoder;
   private final EncodingFilter encoderFilter;

   public GZipContentEncoding() {
      this(512, 512);
   }

   public GZipContentEncoding(int inBufferSize, int outBufferSize) {
      this(inBufferSize, outBufferSize, (EncodingFilter)null);
   }

   public GZipContentEncoding(int inBufferSize, int outBufferSize, EncodingFilter encoderFilter) {
      this.decoder = new GZipDecoder(inBufferSize);
      this.encoder = new GZipEncoder(outBufferSize);
      if (encoderFilter != null) {
         this.encoderFilter = encoderFilter;
      } else {
         this.encoderFilter = new EncodingFilter() {
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
      return "gzip";
   }

   public String[] getAliases() {
      return (String[])ALIASES.clone();
   }

   public static String[] getGzipAliases() {
      return (String[])ALIASES.clone();
   }

   public final boolean wantDecode(HttpHeader header) {
      return this.encoderFilter.applyDecoding(header);
   }

   public final boolean wantEncode(HttpHeader header) {
      return this.encoderFilter.applyEncoding(header);
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
               var7 = ParsingResult.create(httpContent, remainder);
               return var7;
            case INCOMPLETE:
               var7 = ParsingResult.create((HttpContent)null, remainder);
               return var7;
            case ERROR:
               throw new IllegalStateException("GZip decode error. Code: " + result.getErrorCode() + " Description: " + result.getErrorDescription());
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
      boolean isLast = httpContent.isLast();
      if (!isLast && !input.hasRemaining()) {
         return httpContent;
      } else {
         TransformationResult result = this.encoder.transform(httpHeader, input);
         input.tryDispose();

         try {
            switch (result.getStatus()) {
               case COMPLETE:
               case INCOMPLETE:
                  Buffer encodedBuffer = (Buffer)result.getMessage();
                  Buffer finishBuffer;
                  if (isLast) {
                     finishBuffer = this.encoder.finish(httpHeader);
                     encodedBuffer = Buffers.appendBuffers(connection.getMemoryManager(), encodedBuffer, finishBuffer);
                  }

                  if (encodedBuffer == null) {
                     finishBuffer = null;
                     return finishBuffer;
                  }

                  httpContent.setContent(encodedBuffer);
                  HttpContent var12 = httpContent;
                  return var12;
               case ERROR:
                  throw new IllegalStateException("GZip decode error. Code: " + result.getErrorCode() + " Description: " + result.getErrorDescription());
               default:
                  throw new IllegalStateException("Unexpected status: " + result.getStatus());
            }
         } finally {
            result.recycle();
         }
      }
   }

   public boolean equals(Object obj) {
      if (obj == null) {
         return false;
      } else if (this.getClass() != obj.getClass()) {
         return false;
      } else {
         GZipContentEncoding other = (GZipContentEncoding)obj;
         return this.getName().equals(other.getName());
      }
   }

   public int hashCode() {
      int hash = 3;
      hash = 53 * hash + this.getName().hashCode();
      return hash;
   }
}
