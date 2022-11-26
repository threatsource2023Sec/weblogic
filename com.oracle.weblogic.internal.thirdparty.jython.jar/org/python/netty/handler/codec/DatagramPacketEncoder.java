package org.python.netty.handler.codec;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.List;
import org.python.netty.buffer.ByteBuf;
import org.python.netty.channel.AddressedEnvelope;
import org.python.netty.channel.ChannelHandlerContext;
import org.python.netty.channel.ChannelPromise;
import org.python.netty.channel.socket.DatagramPacket;
import org.python.netty.util.internal.ObjectUtil;
import org.python.netty.util.internal.StringUtil;

public class DatagramPacketEncoder extends MessageToMessageEncoder {
   private final MessageToMessageEncoder encoder;

   public DatagramPacketEncoder(MessageToMessageEncoder encoder) {
      this.encoder = (MessageToMessageEncoder)ObjectUtil.checkNotNull(encoder, "encoder");
   }

   public boolean acceptOutboundMessage(Object msg) throws Exception {
      if (!super.acceptOutboundMessage(msg)) {
         return false;
      } else {
         AddressedEnvelope envelope = (AddressedEnvelope)msg;
         return this.encoder.acceptOutboundMessage(envelope.content()) && envelope.sender() instanceof InetSocketAddress && envelope.recipient() instanceof InetSocketAddress;
      }
   }

   protected void encode(ChannelHandlerContext ctx, AddressedEnvelope msg, List out) throws Exception {
      assert out.isEmpty();

      this.encoder.encode(ctx, msg.content(), out);
      if (out.size() != 1) {
         throw new EncoderException(StringUtil.simpleClassName((Object)this.encoder) + " must produce only one message.");
      } else {
         Object content = out.get(0);
         if (content instanceof ByteBuf) {
            out.set(0, new DatagramPacket((ByteBuf)content, (InetSocketAddress)msg.recipient(), (InetSocketAddress)msg.sender()));
         } else {
            throw new EncoderException(StringUtil.simpleClassName((Object)this.encoder) + " must produce only ByteBuf.");
         }
      }
   }

   public void bind(ChannelHandlerContext ctx, SocketAddress localAddress, ChannelPromise promise) throws Exception {
      this.encoder.bind(ctx, localAddress, promise);
   }

   public void connect(ChannelHandlerContext ctx, SocketAddress remoteAddress, SocketAddress localAddress, ChannelPromise promise) throws Exception {
      this.encoder.connect(ctx, remoteAddress, localAddress, promise);
   }

   public void disconnect(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
      this.encoder.disconnect(ctx, promise);
   }

   public void close(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
      this.encoder.close(ctx, promise);
   }

   public void deregister(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
      this.encoder.deregister(ctx, promise);
   }

   public void read(ChannelHandlerContext ctx) throws Exception {
      this.encoder.read(ctx);
   }

   public void flush(ChannelHandlerContext ctx) throws Exception {
      this.encoder.flush(ctx);
   }

   public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
      this.encoder.handlerAdded(ctx);
   }

   public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
      this.encoder.handlerRemoved(ctx);
   }

   public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
      this.encoder.exceptionCaught(ctx, cause);
   }

   public boolean isSharable() {
      return this.encoder.isSharable();
   }
}
