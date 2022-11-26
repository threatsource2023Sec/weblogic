package org.python.netty.handler.codec.bytes;

import java.util.List;
import org.python.netty.buffer.Unpooled;
import org.python.netty.channel.ChannelHandler;
import org.python.netty.channel.ChannelHandlerContext;
import org.python.netty.handler.codec.MessageToMessageEncoder;

@ChannelHandler.Sharable
public class ByteArrayEncoder extends MessageToMessageEncoder {
   protected void encode(ChannelHandlerContext ctx, byte[] msg, List out) throws Exception {
      out.add(Unpooled.wrappedBuffer(msg));
   }
}
