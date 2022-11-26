package org.glassfish.grizzly.http.server;

import java.util.Arrays;
import java.util.Set;
import org.glassfish.grizzly.http.CompressionConfig;
import org.glassfish.grizzly.http.EncodingFilter;
import org.glassfish.grizzly.http.HttpHeader;
import org.glassfish.grizzly.http.HttpRequestPacket;
import org.glassfish.grizzly.http.HttpResponsePacket;
import org.glassfish.grizzly.http.CompressionConfig.CompressionMode;
import org.glassfish.grizzly.http.util.DataChunk;
import org.glassfish.grizzly.http.util.Header;
import org.glassfish.grizzly.http.util.MimeHeaders;

public class CompressionEncodingFilter implements EncodingFilter {
   private final CompressionConfig compressionConfig;
   private final String[] aliases;

   public CompressionEncodingFilter(CompressionConfig compressionConfig, String[] aliases) {
      this.compressionConfig = new CompressionConfig(compressionConfig);
      this.aliases = (String[])Arrays.copyOf(aliases, aliases.length);
   }

   public CompressionEncodingFilter(CompressionConfig.CompressionModeI compressionMode, int compressionMinSize, String[] compressibleMimeTypes, String[] noCompressionUserAgents, String[] aliases) {
      this(compressionMode, compressionMinSize, compressibleMimeTypes, noCompressionUserAgents, aliases, false);
   }

   public CompressionEncodingFilter(CompressionConfig.CompressionModeI compressionMode, int compressionMinSize, String[] compressibleMimeTypes, String[] noCompressionUserAgents, String[] aliases, boolean enableDecompression) {
      CompressionConfig.CompressionMode mode;
      if (compressionMode instanceof CompressionConfig.CompressionMode) {
         mode = (CompressionConfig.CompressionMode)compressionMode;
      } else {
         assert compressionMode instanceof CompressionLevel;

         mode = ((CompressionLevel)compressionMode).normalize();
      }

      this.compressionConfig = new CompressionConfig(mode, compressionMinSize, (Set)null, (Set)null, enableDecompression);
      this.compressionConfig.setCompressibleMimeTypes(compressibleMimeTypes);
      this.compressionConfig.setNoCompressionUserAgents(noCompressionUserAgents);
      this.aliases = (String[])Arrays.copyOf(aliases, aliases.length);
   }

   public boolean applyEncoding(HttpHeader httpPacket) {
      if (httpPacket.isRequest()) {
         assert httpPacket instanceof HttpRequestPacket;

         return false;
      } else {
         assert httpPacket instanceof HttpResponsePacket;

         return canCompressHttpResponse((HttpResponsePacket)httpPacket, this.compressionConfig, this.aliases);
      }
   }

   public boolean applyDecoding(HttpHeader httpPacket) {
      if (!httpPacket.isRequest()) {
         return false;
      } else {
         assert httpPacket instanceof HttpRequestPacket;

         return canDecompressHttpRequest((HttpRequestPacket)httpPacket, this.compressionConfig, this.aliases);
      }
   }

   protected static boolean canCompressHttpResponse(HttpResponsePacket response, CompressionConfig compressionConfig, String[] aliases) {
      if (!response.getContentEncodings().isEmpty()) {
         return false;
      } else {
         MimeHeaders responseHeaders = response.getHeaders();
         DataChunk contentEncodingMB = responseHeaders.getValue(Header.ContentEncoding);
         if (contentEncodingMB != null && !contentEncodingMB.isNull()) {
            return false;
         } else if (!CompressionConfig.isClientSupportCompression(compressionConfig, response.getRequest(), aliases)) {
            return false;
         } else if (compressionConfig.getCompressionMode() == CompressionMode.FORCE) {
            response.setChunked(true);
            response.setContentLength(-1);
            return true;
         } else {
            long contentLength = response.getContentLength();
            if ((contentLength == -1L || contentLength >= (long)compressionConfig.getCompressionMinSize()) && compressionConfig.checkMimeType(response.getContentType())) {
               response.setChunked(true);
               response.setContentLength(-1);
               return true;
            } else {
               return false;
            }
         }
      }
   }

   protected static boolean canDecompressHttpRequest(HttpRequestPacket request, CompressionConfig config, String[] aliases) {
      if (!config.isDecompressionEnabled()) {
         return false;
      } else {
         String contentEncoding = request.getHeader(Header.ContentEncoding);
         if (contentEncoding == null) {
            return false;
         } else {
            contentEncoding = contentEncoding.trim();
            String[] var4 = aliases;
            int var5 = aliases.length;

            for(int var6 = 0; var6 < var5; ++var6) {
               String alias = var4[var6];
               if (alias.equals(contentEncoding)) {
                  return true;
               }
            }

            return false;
         }
      }
   }
}
