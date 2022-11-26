package org.python.netty.handler.codec.base64;

import java.util.List;
import org.python.netty.buffer.ByteBuf;
import org.python.netty.channel.ChannelHandler;
import org.python.netty.channel.ChannelHandlerContext;
import org.python.netty.handler.codec.MessageToMessageEncoder;

@ChannelHandler.Sharable
public class Base64Encoder extends MessageToMessageEncoder {
   private final boolean breakLines;
   private final Base64Dialect dialect;

   public Base64Encoder() {
      this(true);
   }

   public Base64Encoder(boolean breakLines) {
      this(breakLines, Base64Dialect.STANDARD);
   }

   public Base64Encoder(boolean breakLines, Base64Dialect dialect) {
      if (dialect == null) {
         throw new NullPointerException("dialect");
      } else {
         this.breakLines = breakLines;
         this.dialect = dialect;
      }
   }

   protected void encode(ChannelHandlerContext ctx, ByteBuf msg, List out) throws Exception {
      out.add(Base64.encode(msg, msg.readerIndex(), msg.readableBytes(), this.breakLines, this.dialect));
   }
}
