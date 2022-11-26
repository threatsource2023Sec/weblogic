package org.python.netty.handler.codec.protobuf;

import java.util.List;
import org.python.google.protobuf.nano.CodedOutputByteBufferNano;
import org.python.google.protobuf.nano.MessageNano;
import org.python.netty.buffer.ByteBuf;
import org.python.netty.channel.ChannelHandler;
import org.python.netty.channel.ChannelHandlerContext;
import org.python.netty.handler.codec.MessageToMessageEncoder;

@ChannelHandler.Sharable
public class ProtobufEncoderNano extends MessageToMessageEncoder {
   protected void encode(ChannelHandlerContext ctx, MessageNano msg, List out) throws Exception {
      int size = msg.getSerializedSize();
      ByteBuf buffer = ctx.alloc().heapBuffer(size, size);
      byte[] array = buffer.array();
      CodedOutputByteBufferNano cobbn = CodedOutputByteBufferNano.newInstance(array, buffer.arrayOffset(), buffer.capacity());
      msg.writeTo(cobbn);
      buffer.writerIndex(size);
      out.add(buffer);
   }
}
