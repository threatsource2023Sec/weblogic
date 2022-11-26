package org.python.netty.handler.codec.string;

import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.util.List;
import org.python.netty.buffer.ByteBuf;
import org.python.netty.buffer.ByteBufUtil;
import org.python.netty.channel.ChannelHandler;
import org.python.netty.channel.ChannelHandlerContext;
import org.python.netty.handler.codec.MessageToMessageEncoder;
import org.python.netty.util.CharsetUtil;
import org.python.netty.util.internal.ObjectUtil;

@ChannelHandler.Sharable
public class LineEncoder extends MessageToMessageEncoder {
   private final Charset charset;
   private final byte[] lineSeparator;

   public LineEncoder() {
      this(LineSeparator.DEFAULT, CharsetUtil.UTF_8);
   }

   public LineEncoder(LineSeparator lineSeparator) {
      this(lineSeparator, CharsetUtil.UTF_8);
   }

   public LineEncoder(Charset charset) {
      this(LineSeparator.DEFAULT, charset);
   }

   public LineEncoder(LineSeparator lineSeparator, Charset charset) {
      this.charset = (Charset)ObjectUtil.checkNotNull(charset, "charset");
      this.lineSeparator = ((LineSeparator)ObjectUtil.checkNotNull(lineSeparator, "lineSeparator")).value().getBytes(charset);
   }

   protected void encode(ChannelHandlerContext ctx, CharSequence msg, List out) throws Exception {
      ByteBuf buffer = ByteBufUtil.encodeString(ctx.alloc(), CharBuffer.wrap(msg), this.charset, this.lineSeparator.length);
      buffer.writeBytes(this.lineSeparator);
      out.add(buffer);
   }
}
