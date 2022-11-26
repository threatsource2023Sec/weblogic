package org.python.netty.handler.codec.string;

import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.util.List;
import org.python.netty.buffer.ByteBufUtil;
import org.python.netty.channel.ChannelHandler;
import org.python.netty.channel.ChannelHandlerContext;
import org.python.netty.handler.codec.MessageToMessageEncoder;

@ChannelHandler.Sharable
public class StringEncoder extends MessageToMessageEncoder {
   private final Charset charset;

   public StringEncoder() {
      this(Charset.defaultCharset());
   }

   public StringEncoder(Charset charset) {
      if (charset == null) {
         throw new NullPointerException("charset");
      } else {
         this.charset = charset;
      }
   }

   protected void encode(ChannelHandlerContext ctx, CharSequence msg, List out) throws Exception {
      if (msg.length() != 0) {
         out.add(ByteBufUtil.encodeString(ctx.alloc(), CharBuffer.wrap(msg), this.charset));
      }
   }
}
