package org.python.netty.handler.codec.marshalling;

import org.jboss.marshalling.Marshaller;
import org.python.netty.buffer.ByteBuf;
import org.python.netty.channel.ChannelHandler;
import org.python.netty.channel.ChannelHandlerContext;
import org.python.netty.handler.codec.MessageToByteEncoder;

@ChannelHandler.Sharable
public class CompatibleMarshallingEncoder extends MessageToByteEncoder {
   private final MarshallerProvider provider;

   public CompatibleMarshallingEncoder(MarshallerProvider provider) {
      this.provider = provider;
   }

   protected void encode(ChannelHandlerContext ctx, Object msg, ByteBuf out) throws Exception {
      Marshaller marshaller = this.provider.getMarshaller(ctx);
      marshaller.start(new ChannelBufferByteOutput(out));
      marshaller.writeObject(msg);
      marshaller.finish();
      marshaller.close();
   }
}
