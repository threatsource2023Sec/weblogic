package org.python.netty.handler.codec.protobuf;

import java.util.List;
import org.python.google.protobuf.nano.MessageNano;
import org.python.netty.buffer.ByteBuf;
import org.python.netty.channel.ChannelHandler;
import org.python.netty.channel.ChannelHandlerContext;
import org.python.netty.handler.codec.MessageToMessageDecoder;
import org.python.netty.util.internal.ObjectUtil;

@ChannelHandler.Sharable
public class ProtobufDecoderNano extends MessageToMessageDecoder {
   private final Class clazz;

   public ProtobufDecoderNano(Class clazz) {
      this.clazz = (Class)ObjectUtil.checkNotNull(clazz, "You must provide a Class");
   }

   protected void decode(ChannelHandlerContext ctx, ByteBuf msg, List out) throws Exception {
      int length = msg.readableBytes();
      byte[] array;
      int offset;
      if (msg.hasArray()) {
         array = msg.array();
         offset = msg.arrayOffset() + msg.readerIndex();
      } else {
         array = new byte[length];
         msg.getBytes(msg.readerIndex(), (byte[])array, 0, length);
         offset = 0;
      }

      MessageNano prototype = (MessageNano)this.clazz.newInstance();
      out.add(MessageNano.mergeFrom(prototype, array, offset, length));
   }
}
