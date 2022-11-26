package org.python.netty.handler.codec.string;

import java.nio.charset.Charset;
import java.util.List;
import org.python.netty.buffer.ByteBuf;
import org.python.netty.channel.ChannelHandler;
import org.python.netty.channel.ChannelHandlerContext;
import org.python.netty.handler.codec.MessageToMessageDecoder;

@ChannelHandler.Sharable
public class StringDecoder extends MessageToMessageDecoder {
   private final Charset charset;

   public StringDecoder() {
      this(Charset.defaultCharset());
   }

   public StringDecoder(Charset charset) {
      if (charset == null) {
         throw new NullPointerException("charset");
      } else {
         this.charset = charset;
      }
   }

   protected void decode(ChannelHandlerContext ctx, ByteBuf msg, List out) throws Exception {
      out.add(msg.toString(this.charset));
   }
}
