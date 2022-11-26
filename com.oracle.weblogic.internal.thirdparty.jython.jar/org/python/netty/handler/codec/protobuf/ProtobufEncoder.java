package org.python.netty.handler.codec.protobuf;

import java.util.List;
import org.python.google.protobuf.MessageLite;
import org.python.google.protobuf.MessageLiteOrBuilder;
import org.python.netty.buffer.Unpooled;
import org.python.netty.channel.ChannelHandler;
import org.python.netty.channel.ChannelHandlerContext;
import org.python.netty.handler.codec.MessageToMessageEncoder;

@ChannelHandler.Sharable
public class ProtobufEncoder extends MessageToMessageEncoder {
   protected void encode(ChannelHandlerContext ctx, MessageLiteOrBuilder msg, List out) throws Exception {
      if (msg instanceof MessageLite) {
         out.add(Unpooled.wrappedBuffer(((MessageLite)msg).toByteArray()));
      } else {
         if (msg instanceof MessageLite.Builder) {
            out.add(Unpooled.wrappedBuffer(((MessageLite.Builder)msg).build().toByteArray()));
         }

      }
   }
}
