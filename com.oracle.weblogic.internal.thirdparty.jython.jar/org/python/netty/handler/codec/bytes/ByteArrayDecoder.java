package org.python.netty.handler.codec.bytes;

import java.util.List;
import org.python.netty.buffer.ByteBuf;
import org.python.netty.channel.ChannelHandlerContext;
import org.python.netty.handler.codec.MessageToMessageDecoder;

public class ByteArrayDecoder extends MessageToMessageDecoder {
   protected void decode(ChannelHandlerContext ctx, ByteBuf msg, List out) throws Exception {
      byte[] array = new byte[msg.readableBytes()];
      msg.getBytes(0, (byte[])array);
      out.add(array);
   }
}
