package org.python.netty.handler.codec.marshalling;

import org.jboss.marshalling.Marshaller;
import org.python.netty.buffer.ByteBuf;
import org.python.netty.channel.ChannelHandler;
import org.python.netty.channel.ChannelHandlerContext;
import org.python.netty.handler.codec.MessageToByteEncoder;

@ChannelHandler.Sharable
public class MarshallingEncoder extends MessageToByteEncoder {
   private static final byte[] LENGTH_PLACEHOLDER = new byte[4];
   private final MarshallerProvider provider;

   public MarshallingEncoder(MarshallerProvider provider) {
      this.provider = provider;
   }

   protected void encode(ChannelHandlerContext ctx, Object msg, ByteBuf out) throws Exception {
      Marshaller marshaller = this.provider.getMarshaller(ctx);
      int lengthPos = out.writerIndex();
      out.writeBytes(LENGTH_PLACEHOLDER);
      ChannelBufferByteOutput output = new ChannelBufferByteOutput(out);
      marshaller.start(output);
      marshaller.writeObject(msg);
      marshaller.finish();
      marshaller.close();
      out.setInt(lengthPos, out.writerIndex() - lengthPos - 4);
   }
}
