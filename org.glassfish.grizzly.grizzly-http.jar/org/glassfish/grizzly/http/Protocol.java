package org.glassfish.grizzly.http;

import java.io.UnsupportedEncodingException;
import org.glassfish.grizzly.Buffer;
import org.glassfish.grizzly.http.util.BufferChunk;
import org.glassfish.grizzly.http.util.ByteChunk;
import org.glassfish.grizzly.http.util.DataChunk;
import org.glassfish.grizzly.utils.Charsets;

public enum Protocol {
   HTTP_0_9(0, 9),
   HTTP_1_0(1, 0),
   HTTP_1_1(1, 1),
   HTTP_2_0(2, 0);

   private final String protocolString;
   private final int majorVersion;
   private final int minorVersion;
   private final byte[] protocolBytes;

   public static Protocol valueOf(byte[] protocolBytes, int offset, int len) {
      if (len == 0) {
         return HTTP_0_9;
      } else if (equals(HTTP_1_1, protocolBytes, offset, len)) {
         return HTTP_1_1;
      } else if (equals(HTTP_1_0, protocolBytes, offset, len)) {
         return HTTP_1_0;
      } else if (equals(HTTP_2_0, protocolBytes, offset, len)) {
         return HTTP_2_0;
      } else if (equals(HTTP_0_9, protocolBytes, offset, len)) {
         return HTTP_0_9;
      } else {
         throw new IllegalStateException("Unknown protocol " + new String(protocolBytes, offset, len, Charsets.ASCII_CHARSET));
      }
   }

   public static Protocol valueOf(Buffer protocolBuffer, int offset, int len) {
      if (len == 0) {
         return HTTP_0_9;
      } else if (equals(HTTP_1_1, protocolBuffer, offset, len)) {
         return HTTP_1_1;
      } else if (equals(HTTP_1_0, protocolBuffer, offset, len)) {
         return HTTP_1_0;
      } else if (equals(HTTP_2_0, protocolBuffer, offset, len)) {
         return HTTP_2_0;
      } else if (equals(HTTP_0_9, protocolBuffer, offset, len)) {
         return HTTP_0_9;
      } else {
         throw new IllegalStateException("Unknown protocol " + protocolBuffer.toStringContent(Charsets.ASCII_CHARSET, offset, len));
      }
   }

   public static Protocol valueOf(DataChunk protocolC) {
      if (protocolC.getLength() == 0) {
         return HTTP_0_9;
      } else if (protocolC.equals(HTTP_1_1.getProtocolBytes())) {
         return HTTP_1_1;
      } else if (protocolC.equals(HTTP_1_0.getProtocolBytes())) {
         return HTTP_1_0;
      } else if (protocolC.equals(HTTP_2_0.getProtocolBytes())) {
         return HTTP_2_0;
      } else if (protocolC.equals(HTTP_0_9.getProtocolBytes())) {
         return HTTP_0_9;
      } else {
         throw new IllegalStateException("Unknown protocol " + protocolC.toString());
      }
   }

   private static boolean equals(Protocol protocol, byte[] protocolBytes, int offset, int len) {
      byte[] knownProtocolBytes = protocol.getProtocolBytes();
      return ByteChunk.equals(knownProtocolBytes, 0, knownProtocolBytes.length, protocolBytes, offset, len);
   }

   private static boolean equals(Protocol protocol, Buffer protocolBuffer, int offset, int len) {
      byte[] knownProtocolBytes = protocol.getProtocolBytes();
      return BufferChunk.equals(knownProtocolBytes, 0, knownProtocolBytes.length, protocolBuffer, offset, len);
   }

   private Protocol(int majorVersion, int minorVersion) {
      this.majorVersion = majorVersion;
      this.minorVersion = minorVersion;
      this.protocolString = "HTTP/" + majorVersion + '.' + minorVersion;

      byte[] protocolBytes0;
      try {
         protocolBytes0 = this.protocolString.getBytes("US-ASCII");
      } catch (UnsupportedEncodingException var7) {
         protocolBytes0 = this.protocolString.getBytes(Charsets.ASCII_CHARSET);
      }

      this.protocolBytes = protocolBytes0;
   }

   public int getMajorVersion() {
      return this.majorVersion;
   }

   public int getMinorVersion() {
      return this.minorVersion;
   }

   public String getProtocolString() {
      return this.protocolString;
   }

   public byte[] getProtocolBytes() {
      return this.protocolBytes;
   }

   public boolean equals(String s) {
      return s != null && ByteChunk.equals(this.protocolBytes, 0, this.protocolBytes.length, s);
   }

   public boolean equals(DataChunk protocolC) {
      return protocolC != null && !protocolC.isNull() && protocolC.equals(this.protocolBytes);
   }
}
