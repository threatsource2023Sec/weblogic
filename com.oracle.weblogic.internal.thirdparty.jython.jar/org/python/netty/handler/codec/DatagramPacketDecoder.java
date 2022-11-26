package org.python.netty.handler.codec;

import java.util.List;
import org.python.netty.channel.ChannelHandlerContext;
import org.python.netty.channel.socket.DatagramPacket;
import org.python.netty.util.internal.ObjectUtil;

public class DatagramPacketDecoder extends MessageToMessageDecoder {
   private final MessageToMessageDecoder decoder;

   public DatagramPacketDecoder(MessageToMessageDecoder decoder) {
      this.decoder = (MessageToMessageDecoder)ObjectUtil.checkNotNull(decoder, "decoder");
   }

   public boolean acceptInboundMessage(Object msg) throws Exception {
      return msg instanceof DatagramPacket ? this.decoder.acceptInboundMessage(((DatagramPacket)msg).content()) : false;
   }

   protected void decode(ChannelHandlerContext ctx, DatagramPacket msg, List out) throws Exception {
      this.decoder.decode(ctx, msg.content(), out);
   }

   public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
      this.decoder.channelRegistered(ctx);
   }

   public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
      this.decoder.channelUnregistered(ctx);
   }

   public void channelActive(ChannelHandlerContext ctx) throws Exception {
      this.decoder.channelActive(ctx);
   }

   public void channelInactive(ChannelHandlerContext ctx) throws Exception {
      this.decoder.channelInactive(ctx);
   }

   public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
      this.decoder.channelReadComplete(ctx);
   }

   public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
      this.decoder.userEventTriggered(ctx, evt);
   }

   public void channelWritabilityChanged(ChannelHandlerContext ctx) throws Exception {
      this.decoder.channelWritabilityChanged(ctx);
   }

   public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
      this.decoder.exceptionCaught(ctx, cause);
   }

   public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
      this.decoder.handlerAdded(ctx);
   }

   public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
      this.decoder.handlerRemoved(ctx);
   }

   public boolean isSharable() {
      return this.decoder.isSharable();
   }
}
