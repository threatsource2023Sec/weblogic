package org.python.netty.handler.codec;

import java.util.List;
import org.python.netty.channel.ChannelDuplexHandler;
import org.python.netty.channel.ChannelHandlerContext;
import org.python.netty.channel.ChannelPromise;
import org.python.netty.util.internal.TypeParameterMatcher;

public abstract class MessageToMessageCodec extends ChannelDuplexHandler {
   private final MessageToMessageEncoder encoder = new MessageToMessageEncoder() {
      public boolean acceptOutboundMessage(Object msg) throws Exception {
         return MessageToMessageCodec.this.acceptOutboundMessage(msg);
      }

      protected void encode(ChannelHandlerContext ctx, Object msg, List out) throws Exception {
         MessageToMessageCodec.this.encode(ctx, msg, out);
      }
   };
   private final MessageToMessageDecoder decoder = new MessageToMessageDecoder() {
      public boolean acceptInboundMessage(Object msg) throws Exception {
         return MessageToMessageCodec.this.acceptInboundMessage(msg);
      }

      protected void decode(ChannelHandlerContext ctx, Object msg, List out) throws Exception {
         MessageToMessageCodec.this.decode(ctx, msg, out);
      }
   };
   private final TypeParameterMatcher inboundMsgMatcher;
   private final TypeParameterMatcher outboundMsgMatcher;

   protected MessageToMessageCodec() {
      this.inboundMsgMatcher = TypeParameterMatcher.find(this, MessageToMessageCodec.class, "INBOUND_IN");
      this.outboundMsgMatcher = TypeParameterMatcher.find(this, MessageToMessageCodec.class, "OUTBOUND_IN");
   }

   protected MessageToMessageCodec(Class inboundMessageType, Class outboundMessageType) {
      this.inboundMsgMatcher = TypeParameterMatcher.get(inboundMessageType);
      this.outboundMsgMatcher = TypeParameterMatcher.get(outboundMessageType);
   }

   public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
      this.decoder.channelRead(ctx, msg);
   }

   public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
      this.encoder.write(ctx, msg, promise);
   }

   public boolean acceptInboundMessage(Object msg) throws Exception {
      return this.inboundMsgMatcher.match(msg);
   }

   public boolean acceptOutboundMessage(Object msg) throws Exception {
      return this.outboundMsgMatcher.match(msg);
   }

   protected abstract void encode(ChannelHandlerContext var1, Object var2, List var3) throws Exception;

   protected abstract void decode(ChannelHandlerContext var1, Object var2, List var3) throws Exception;
}
