package org.glassfish.grizzly.http;

import java.util.Arrays;
import java.util.Collections;
import java.util.Set;
import org.glassfish.grizzly.http.util.DataChunk;
import org.glassfish.grizzly.http.util.Header;
import org.glassfish.grizzly.http.util.HttpUtils;
import org.glassfish.grizzly.utils.ArraySet;

public final class CompressionConfig {
   private CompressionMode compressionMode;
   private int compressionMinSize;
   private final ArraySet compressibleMimeTypes;
   private final ArraySet noCompressionUserAgents;
   private boolean decompressionEnabled;

   public CompressionConfig() {
      this.compressibleMimeTypes = new ArraySet(String.class);
      this.noCompressionUserAgents = new ArraySet(String.class);
      this.compressionMode = CompressionConfig.CompressionMode.OFF;
   }

   public CompressionConfig(CompressionConfig compression) {
      this.compressibleMimeTypes = new ArraySet(String.class);
      this.noCompressionUserAgents = new ArraySet(String.class);
      this.set(compression);
   }

   public CompressionConfig(CompressionMode compressionMode, int compressionMinSize, Set compressibleMimeTypes, Set noCompressionUserAgents) {
      this(compressionMode, compressionMinSize, compressibleMimeTypes, noCompressionUserAgents, false);
   }

   public CompressionConfig(CompressionMode compressionMode, int compressionMinSize, Set compressibleMimeTypes, Set noCompressionUserAgents, boolean decompressionEnabled) {
      this.compressibleMimeTypes = new ArraySet(String.class);
      this.noCompressionUserAgents = new ArraySet(String.class);
      this.setCompressionMode(compressionMode);
      this.setCompressionMinSize(compressionMinSize);
      this.setCompressibleMimeTypes(compressibleMimeTypes);
      this.setNoCompressionUserAgents(noCompressionUserAgents);
      this.setDecompressionEnabled(decompressionEnabled);
   }

   public void set(CompressionConfig compression) {
      this.compressionMode = compression.compressionMode;
      this.compressionMinSize = compression.compressionMinSize;
      this.setCompressibleMimeTypes((Set)compression.compressibleMimeTypes);
      this.setNoCompressionUserAgents((Set)compression.noCompressionUserAgents);
      this.decompressionEnabled = compression.isDecompressionEnabled();
   }

   public CompressionMode getCompressionMode() {
      return this.compressionMode;
   }

   public void setCompressionMode(CompressionMode mode) {
      this.compressionMode = mode != null ? mode : CompressionConfig.CompressionMode.OFF;
   }

   public int getCompressionMinSize() {
      return this.compressionMinSize;
   }

   public void setCompressionMinSize(int compressionMinSize) {
      this.compressionMinSize = compressionMinSize;
   }

   /** @deprecated */
   @Deprecated
   public Set getCompressableMimeTypes() {
      return this.getCompressibleMimeTypes();
   }

   /** @deprecated */
   @Deprecated
   public void setCompressableMimeTypes(Set compressibleMimeTypes) {
      this.setCompressibleMimeTypes(compressibleMimeTypes);
   }

   /** @deprecated */
   @Deprecated
   public void setCompressableMimeTypes(String... compressibleMimeTypes) {
      this.setCompressibleMimeTypes(compressibleMimeTypes);
   }

   public Set getCompressibleMimeTypes() {
      return Collections.unmodifiableSet(this.compressibleMimeTypes);
   }

   public void setCompressibleMimeTypes(Set compressibleMimeTypes) {
      this.compressibleMimeTypes.clear();
      if (compressibleMimeTypes != null && !compressibleMimeTypes.isEmpty()) {
         this.compressibleMimeTypes.addAll(compressibleMimeTypes);
      }

   }

   public void setCompressibleMimeTypes(String... compressibleMimeTypes) {
      this.compressibleMimeTypes.clear();
      if (compressibleMimeTypes.length > 0) {
         this.compressibleMimeTypes.addAll(compressibleMimeTypes);
      }

   }

   public Set getNoCompressionUserAgents() {
      return Collections.unmodifiableSet(this.noCompressionUserAgents);
   }

   public void setNoCompressionUserAgents(Set noCompressionUserAgents) {
      this.noCompressionUserAgents.clear();
      if (noCompressionUserAgents != null && !noCompressionUserAgents.isEmpty()) {
         this.noCompressionUserAgents.addAll(noCompressionUserAgents);
      }

   }

   public void setNoCompressionUserAgents(String... noCompressionUserAgents) {
      this.noCompressionUserAgents.clear();
      if (noCompressionUserAgents.length > 0) {
         this.noCompressionUserAgents.addAll(noCompressionUserAgents);
      }

   }

   public boolean isDecompressionEnabled() {
      return this.decompressionEnabled;
   }

   public void setDecompressionEnabled(boolean decompressionEnabled) {
      this.decompressionEnabled = decompressionEnabled;
   }

   public static boolean isClientSupportCompression(CompressionConfig compressionConfig, HttpRequestPacket request, String[] aliases) {
      CompressionMode mode = compressionConfig.getCompressionMode();
      switch (mode) {
         case OFF:
            return false;
         default:
            if (Protocol.HTTP_1_1 != request.getProtocol()) {
               return false;
            } else if (!isClientSupportContentEncoding(request, aliases)) {
               return false;
            } else {
               return mode == CompressionConfig.CompressionMode.FORCE || compressionConfig.checkUserAgent(request);
            }
      }
   }

   public boolean checkUserAgent(HttpRequestPacket request) {
      if (!this.noCompressionUserAgents.isEmpty()) {
         DataChunk userAgentValueDC = request.getHeaders().getValue(Header.UserAgent);
         if (userAgentValueDC != null && indexOf((String[])this.noCompressionUserAgents.getArray(), userAgentValueDC) != -1) {
            return false;
         }
      }

      return true;
   }

   public boolean checkMimeType(String contentType) {
      return this.compressibleMimeTypes.isEmpty() || indexOfStartsWith((String[])this.compressibleMimeTypes.getArray(), contentType) != -1;
   }

   private static boolean isClientSupportContentEncoding(HttpRequestPacket request, String[] aliases) {
      DataChunk acceptEncodingDC = request.getHeaders().getValue(Header.AcceptEncoding);
      if (acceptEncodingDC == null) {
         return false;
      } else {
         String alias = null;
         int idx = -1;
         int qvalueStart = 0;

         int commaIdx;
         for(commaIdx = aliases.length; qvalueStart < commaIdx; ++qvalueStart) {
            alias = aliases[qvalueStart];
            idx = acceptEncodingDC.indexOf(alias, 0);
            if (idx != -1) {
               break;
            }
         }

         if (idx == -1) {
            return false;
         } else {
            assert alias != null;

            qvalueStart = acceptEncodingDC.indexOf(';', idx + alias.length());
            if (qvalueStart != -1) {
               qvalueStart = acceptEncodingDC.indexOf('=', qvalueStart);
               commaIdx = acceptEncodingDC.indexOf(',', qvalueStart);
               int qvalueEnd = commaIdx != -1 ? commaIdx : acceptEncodingDC.getLength();
               if (HttpUtils.convertQValueToFloat(acceptEncodingDC, qvalueStart + 1, qvalueEnd) == 0.0F) {
                  return false;
               }
            }

            return true;
         }
      }
   }

   private static int indexOf(String[] aliases, DataChunk dc) {
      if (dc != null && !dc.isNull()) {
         for(int i = 0; i < aliases.length; ++i) {
            String alias = aliases[i];
            if (dc.indexOf(alias, 0) != -1) {
               return i;
            }
         }

         return -1;
      } else {
         return -1;
      }
   }

   private static int indexOfStartsWith(String[] aliases, String s) {
      if (s != null && s.length() != 0) {
         for(int i = 0; i < aliases.length; ++i) {
            String alias = aliases[i];
            if (s.startsWith(alias)) {
               return i;
            }
         }

         return -1;
      } else {
         return -1;
      }
   }

   public static enum CompressionMode implements CompressionModeI {
      OFF,
      ON,
      FORCE;

      public static CompressionMode fromString(String mode) {
         if ("on".equalsIgnoreCase(mode)) {
            return ON;
         } else if ("force".equalsIgnoreCase(mode)) {
            return FORCE;
         } else if ("off".equalsIgnoreCase(mode)) {
            return OFF;
         } else {
            throw new IllegalArgumentException("Compression mode is not recognized. Supported modes: " + Arrays.toString(values()));
         }
      }
   }

   public interface CompressionModeI {
   }
}
