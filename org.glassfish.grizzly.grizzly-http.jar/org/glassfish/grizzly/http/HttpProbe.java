package org.glassfish.grizzly.http;

import org.glassfish.grizzly.Buffer;
import org.glassfish.grizzly.Connection;

public interface HttpProbe {
   void onDataReceivedEvent(Connection var1, Buffer var2);

   void onDataSentEvent(Connection var1, Buffer var2);

   void onHeaderParseEvent(Connection var1, HttpHeader var2, int var3);

   void onHeaderSerializeEvent(Connection var1, HttpHeader var2, Buffer var3);

   void onContentChunkParseEvent(Connection var1, HttpContent var2);

   void onContentChunkSerializeEvent(Connection var1, HttpContent var2);

   void onContentEncodingParseEvent(Connection var1, HttpHeader var2, Buffer var3, ContentEncoding var4);

   void onContentEncodingParseResultEvent(Connection var1, HttpHeader var2, Buffer var3, ContentEncoding var4);

   void onContentEncodingSerializeEvent(Connection var1, HttpHeader var2, Buffer var3, ContentEncoding var4);

   void onContentEncodingSerializeResultEvent(Connection var1, HttpHeader var2, Buffer var3, ContentEncoding var4);

   void onTransferEncodingParseEvent(Connection var1, HttpHeader var2, Buffer var3, TransferEncoding var4);

   void onTransferEncodingSerializeEvent(Connection var1, HttpHeader var2, Buffer var3, TransferEncoding var4);

   void onErrorEvent(Connection var1, HttpPacket var2, Throwable var3);

   public static class Adapter implements HttpProbe {
      public void onDataReceivedEvent(Connection connection, Buffer buffer) {
      }

      public void onDataSentEvent(Connection connection, Buffer buffer) {
      }

      public void onHeaderParseEvent(Connection connection, HttpHeader header, int size) {
      }

      public void onHeaderSerializeEvent(Connection connection, HttpHeader header, Buffer buffer) {
      }

      public void onContentChunkParseEvent(Connection connection, HttpContent content) {
      }

      public void onContentChunkSerializeEvent(Connection connection, HttpContent content) {
      }

      public void onContentEncodingParseEvent(Connection connection, HttpHeader header, Buffer buffer, ContentEncoding contentEncoding) {
      }

      public void onContentEncodingParseResultEvent(Connection connection, HttpHeader header, Buffer result, ContentEncoding contentEncoding) {
      }

      public void onContentEncodingSerializeEvent(Connection connection, HttpHeader header, Buffer buffer, ContentEncoding contentEncoding) {
      }

      public void onContentEncodingSerializeResultEvent(Connection connection, HttpHeader header, Buffer result, ContentEncoding contentEncoding) {
      }

      public void onTransferEncodingParseEvent(Connection connection, HttpHeader header, Buffer buffer, TransferEncoding transferEncoding) {
      }

      public void onTransferEncodingSerializeEvent(Connection connection, HttpHeader header, Buffer buffer, TransferEncoding transferEncoding) {
      }

      public void onErrorEvent(Connection connection, HttpPacket httpPacket, Throwable error) {
      }
   }
}
