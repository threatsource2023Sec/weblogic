package org.glassfish.grizzly.http;

import org.glassfish.grizzly.Buffer;
import org.glassfish.grizzly.Connection;

final class HttpProbeNotifier {
   static void notifyDataReceived(HttpCodecFilter httpFilter, Connection connection, Buffer buffer) {
      HttpProbe[] probes = (HttpProbe[])httpFilter.monitoringConfig.getProbesUnsafe();
      if (probes != null) {
         HttpProbe[] var4 = probes;
         int var5 = probes.length;

         for(int var6 = 0; var6 < var5; ++var6) {
            HttpProbe probe = var4[var6];
            probe.onDataReceivedEvent(connection, buffer);
         }
      }

   }

   static void notifyDataSent(HttpCodecFilter httpFilter, Connection connection, Buffer buffer) {
      HttpProbe[] probes = (HttpProbe[])httpFilter.monitoringConfig.getProbesUnsafe();
      if (probes != null) {
         HttpProbe[] var4 = probes;
         int var5 = probes.length;

         for(int var6 = 0; var6 < var5; ++var6) {
            HttpProbe probe = var4[var6];
            probe.onDataSentEvent(connection, buffer);
         }
      }

   }

   static void notifyHeaderParse(HttpCodecFilter httpFilter, Connection connection, HttpHeader header, int size) {
      HttpProbe[] probes = (HttpProbe[])httpFilter.monitoringConfig.getProbesUnsafe();
      if (probes != null) {
         HttpProbe[] var5 = probes;
         int var6 = probes.length;

         for(int var7 = 0; var7 < var6; ++var7) {
            HttpProbe probe = var5[var7];
            probe.onHeaderParseEvent(connection, header, size);
         }
      }

   }

   static void notifyHeaderSerialize(HttpCodecFilter httpFilter, Connection connection, HttpHeader header, Buffer buffer) {
      HttpProbe[] probes = (HttpProbe[])httpFilter.monitoringConfig.getProbesUnsafe();
      if (probes != null) {
         HttpProbe[] var5 = probes;
         int var6 = probes.length;

         for(int var7 = 0; var7 < var6; ++var7) {
            HttpProbe probe = var5[var7];
            probe.onHeaderSerializeEvent(connection, header, buffer);
         }
      }

   }

   static void notifyContentChunkParse(HttpCodecFilter httpFilter, Connection connection, HttpContent content) {
      HttpProbe[] probes = (HttpProbe[])httpFilter.monitoringConfig.getProbesUnsafe();
      if (probes != null) {
         HttpProbe[] var4 = probes;
         int var5 = probes.length;

         for(int var6 = 0; var6 < var5; ++var6) {
            HttpProbe probe = var4[var6];
            probe.onContentChunkParseEvent(connection, content);
         }
      }

   }

   static void notifyContentChunkSerialize(HttpCodecFilter httpFilter, Connection connection, HttpContent content) {
      HttpProbe[] probes = (HttpProbe[])httpFilter.monitoringConfig.getProbesUnsafe();
      if (probes != null) {
         HttpProbe[] var4 = probes;
         int var5 = probes.length;

         for(int var6 = 0; var6 < var5; ++var6) {
            HttpProbe probe = var4[var6];
            probe.onContentChunkSerializeEvent(connection, content);
         }
      }

   }

   static void notifyContentEncodingParse(HttpCodecFilter httpFilter, Connection connection, HttpHeader header, Buffer buffer, ContentEncoding contentEncoding) {
      HttpProbe[] probes = (HttpProbe[])httpFilter.monitoringConfig.getProbesUnsafe();
      if (probes != null) {
         HttpProbe[] var6 = probes;
         int var7 = probes.length;

         for(int var8 = 0; var8 < var7; ++var8) {
            HttpProbe probe = var6[var8];
            probe.onContentEncodingParseEvent(connection, header, buffer, contentEncoding);
         }
      }

   }

   static void notifyContentEncodingParseResult(HttpCodecFilter httpFilter, Connection connection, HttpHeader header, Buffer result, ContentEncoding contentEncoding) {
      HttpProbe[] probes = (HttpProbe[])httpFilter.monitoringConfig.getProbesUnsafe();
      if (probes != null) {
         HttpProbe[] var6 = probes;
         int var7 = probes.length;

         for(int var8 = 0; var8 < var7; ++var8) {
            HttpProbe probe = var6[var8];
            probe.onContentEncodingSerializeResultEvent(connection, header, result, contentEncoding);
         }
      }

   }

   static void notifyContentEncodingSerialize(HttpCodecFilter httpFilter, Connection connection, HttpHeader header, Buffer buffer, ContentEncoding contentEncoding) {
      HttpProbe[] probes = (HttpProbe[])httpFilter.monitoringConfig.getProbesUnsafe();
      if (probes != null) {
         HttpProbe[] var6 = probes;
         int var7 = probes.length;

         for(int var8 = 0; var8 < var7; ++var8) {
            HttpProbe probe = var6[var8];
            probe.onContentEncodingSerializeEvent(connection, header, buffer, contentEncoding);
         }
      }

   }

   static void notifyContentEncodingSerializeResult(HttpCodecFilter httpFilter, Connection connection, HttpHeader header, Buffer result, ContentEncoding contentEncoding) {
      HttpProbe[] probes = (HttpProbe[])httpFilter.monitoringConfig.getProbesUnsafe();
      if (probes != null) {
         HttpProbe[] var6 = probes;
         int var7 = probes.length;

         for(int var8 = 0; var8 < var7; ++var8) {
            HttpProbe probe = var6[var8];
            probe.onContentEncodingSerializeResultEvent(connection, header, result, contentEncoding);
         }
      }

   }

   static void notifyTransferEncodingParse(HttpCodecFilter httpFilter, Connection connection, HttpHeader header, Buffer buffer, TransferEncoding transferEncoding) {
      HttpProbe[] probes = (HttpProbe[])httpFilter.monitoringConfig.getProbesUnsafe();
      if (probes != null) {
         HttpProbe[] var6 = probes;
         int var7 = probes.length;

         for(int var8 = 0; var8 < var7; ++var8) {
            HttpProbe probe = var6[var8];
            probe.onTransferEncodingParseEvent(connection, header, buffer, transferEncoding);
         }
      }

   }

   static void notifyTransferEncodingSerialize(HttpCodecFilter httpFilter, Connection connection, HttpHeader header, Buffer buffer, TransferEncoding transferEncoding) {
      HttpProbe[] probes = (HttpProbe[])httpFilter.monitoringConfig.getProbesUnsafe();
      if (probes != null) {
         HttpProbe[] var6 = probes;
         int var7 = probes.length;

         for(int var8 = 0; var8 < var7; ++var8) {
            HttpProbe probe = var6[var8];
            probe.onTransferEncodingSerializeEvent(connection, header, buffer, transferEncoding);
         }
      }

   }

   static void notifyProbesError(HttpCodecFilter httpFilter, Connection connection, HttpPacket httpPacket, Throwable error) {
      HttpProbe[] probes = (HttpProbe[])httpFilter.monitoringConfig.getProbesUnsafe();
      if (probes != null) {
         if (error == null) {
            error = new IllegalStateException("Error in HTTP semantics");
         }

         HttpProbe[] var5 = probes;
         int var6 = probes.length;

         for(int var7 = 0; var7 < var6; ++var7) {
            HttpProbe probe = var5[var7];
            probe.onErrorEvent(connection, httpPacket, (Throwable)error);
         }
      }

   }
}
