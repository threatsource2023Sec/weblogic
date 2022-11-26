package org.glassfish.grizzly.http;

import org.glassfish.grizzly.Buffer;
import org.glassfish.grizzly.filterchain.FilterChainContext;

public final class FixedLengthTransferEncoding implements TransferEncoding {
   public boolean wantDecode(HttpHeader httpPacket) {
      long contentLength = httpPacket.getContentLength();
      return contentLength != -1L;
   }

   public boolean wantEncode(HttpHeader httpPacket) {
      long contentLength = httpPacket.getContentLength();
      return contentLength != -1L;
   }

   public void prepareSerialize(FilterChainContext ctx, HttpHeader httpHeader, HttpContent httpContent) {
      int defaultContentLength = httpContent != null ? httpContent.getContent().remaining() : -1;
      httpHeader.makeContentLengthHeader((long)defaultContentLength);
   }

   public ParsingResult parsePacket(FilterChainContext ctx, HttpHeader httpPacket, Buffer input) {
      HttpPacketParsing httpPacketParsing = (HttpPacketParsing)httpPacket;
      HttpCodecFilter.ContentParsingState contentParsingState = httpPacketParsing.getContentParsingState();
      if (contentParsingState.chunkRemainder == -1L) {
         contentParsingState.chunkRemainder = httpPacket.getContentLength();
      }

      Buffer remainder = null;
      long thisPacketRemaining = contentParsingState.chunkRemainder;
      int available = input.remaining();
      if ((long)available > thisPacketRemaining) {
         remainder = input.slice((int)((long)input.position() + thisPacketRemaining), input.limit());
         input.limit((int)((long)input.position() + thisPacketRemaining));
      }

      contentParsingState.chunkRemainder -= (long)input.remaining();
      boolean isLast = contentParsingState.chunkRemainder == 0L;
      return ParsingResult.create(httpPacket.httpContentBuilder().content(input).last(isLast).build(), remainder);
   }

   public Buffer serializePacket(FilterChainContext ctx, HttpContent httpContent) {
      return httpContent.getContent();
   }
}
