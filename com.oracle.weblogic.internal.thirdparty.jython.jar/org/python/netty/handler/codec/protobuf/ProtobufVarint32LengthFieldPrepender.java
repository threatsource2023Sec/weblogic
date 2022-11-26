package org.python.netty.handler.codec.protobuf;

import org.python.netty.buffer.ByteBuf;
import org.python.netty.channel.ChannelHandler;
import org.python.netty.channel.ChannelHandlerContext;
import org.python.netty.handler.codec.MessageToByteEncoder;

@ChannelHandler.Sharable
public class ProtobufVarint32LengthFieldPrepender extends MessageToByteEncoder {
   protected void encode(ChannelHandlerContext ctx, ByteBuf msg, ByteBuf out) throws Exception {
      int bodyLen = msg.readableBytes();
      int headerLen = computeRawVarint32Size(bodyLen);
      out.ensureWritable(headerLen + bodyLen);
      writeRawVarint32(out, bodyLen);
      out.writeBytes(msg, msg.readerIndex(), bodyLen);
   }

   static void writeRawVarint32(ByteBuf out, int value) {
      while((value & -128) != 0) {
         out.writeByte(value & 127 | 128);
         value >>>= 7;
      }

      out.writeByte(value);
   }

   static int computeRawVarint32Size(int value) {
      if ((value & -128) == 0) {
         return 1;
      } else if ((value & -16384) == 0) {
         return 2;
      } else if ((value & -2097152) == 0) {
         return 3;
      } else {
         return (value & -268435456) == 0 ? 4 : 5;
      }
   }
}
