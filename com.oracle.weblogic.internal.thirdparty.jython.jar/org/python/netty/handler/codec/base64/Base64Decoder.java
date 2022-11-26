package org.python.netty.handler.codec.base64;

import java.util.List;
import org.python.netty.buffer.ByteBuf;
import org.python.netty.channel.ChannelHandler;
import org.python.netty.channel.ChannelHandlerContext;
import org.python.netty.handler.codec.MessageToMessageDecoder;

@ChannelHandler.Sharable
public class Base64Decoder extends MessageToMessageDecoder {
   private final Base64Dialect dialect;

   public Base64Decoder() {
      this(Base64Dialect.STANDARD);
   }

   public Base64Decoder(Base64Dialect dialect) {
      if (dialect == null) {
         throw new NullPointerException("dialect");
      } else {
         this.dialect = dialect;
      }
   }

   protected void decode(ChannelHandlerContext ctx, ByteBuf msg, List out) throws Exception {
      out.add(Base64.decode(msg, msg.readerIndex(), msg.readableBytes(), this.dialect));
   }
}
