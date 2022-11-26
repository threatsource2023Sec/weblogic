package org.python.netty.handler.codec;

import java.util.List;
import org.python.netty.buffer.ByteBuf;
import org.python.netty.channel.ChannelDuplexHandler;
import org.python.netty.channel.ChannelHandlerContext;
import org.python.netty.channel.ChannelPromise;
import org.python.netty.util.internal.TypeParameterMatcher;

public abstract class ByteToMessageCodec extends ChannelDuplexHandler {
   private final TypeParameterMatcher outboundMsgMatcher;
   private final MessageToByteEncoder encoder;
   private final ByteToMessageDecoder decoder;

   protected ByteToMessageCodec() {
      this(true);
   }

   protected ByteToMessageCodec(Class outboundMessageType) {
      this(outboundMessageType, true);
   }

   protected ByteToMessageCodec(boolean preferDirect) {
      this.decoder = new ByteToMessageDecoder() {
         public void decode(ChannelHandlerContext ctx, ByteBuf in, List out) throws Exception {
            ByteToMessageCodec.this.decode(ctx, in, out);
         }

         protected void decodeLast(ChannelHandlerContext ctx, ByteBuf in, List out) throws Exception {
            ByteToMessageCodec.this.decodeLast(ctx, in, out);
         }
      };
      this.ensureNotSharable();
      this.outboundMsgMatcher = TypeParameterMatcher.find(this, ByteToMessageCodec.class, "I");
      this.encoder = new Encoder(preferDirect);
   }

   protected ByteToMessageCodec(Class outboundMessageType, boolean preferDirect) {
      this.decoder = new ByteToMessageDecoder() {
         public void decode(ChannelHandlerContext ctx, ByteBuf in, List out) throws Exception {
            ByteToMessageCodec.this.decode(ctx, in, out);
         }

         protected void decodeLast(ChannelHandlerContext ctx, ByteBuf in, List out) throws Exception {
            ByteToMessageCodec.this.decodeLast(ctx, in, out);
         }
      };
      this.ensureNotSharable();
      this.outboundMsgMatcher = TypeParameterMatcher.get(outboundMessageType);
      this.encoder = new Encoder(preferDirect);
   }

   public boolean acceptOutboundMessage(Object msg) throws Exception {
      return this.outboundMsgMatcher.match(msg);
   }

   public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
      this.decoder.channelRead(ctx, msg);
   }

   public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
      this.encoder.write(ctx, msg, promise);
   }

   public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
      this.decoder.channelReadComplete(ctx);
   }

   public void channelInactive(ChannelHandlerContext ctx) throws Exception {
      this.decoder.channelInactive(ctx);
   }

   public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
      try {
         this.decoder.handlerAdded(ctx);
      } finally {
         this.encoder.handlerAdded(ctx);
      }

   }

   public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
      try {
         this.decoder.handlerRemoved(ctx);
      } finally {
         this.encoder.handlerRemoved(ctx);
      }

   }

   protected abstract void encode(ChannelHandlerContext var1, Object var2, ByteBuf var3) throws Exception;

   protected abstract void decode(ChannelHandlerContext var1, ByteBuf var2, List var3) throws Exception;

   protected void decodeLast(ChannelHandlerContext ctx, ByteBuf in, List out) throws Exception {
      if (in.isReadable()) {
         this.decode(ctx, in, out);
      }

   }

   private final class Encoder extends MessageToByteEncoder {
      Encoder(boolean preferDirect) {
         super(preferDirect);
      }

      public boolean acceptOutboundMessage(Object msg) throws Exception {
         return ByteToMessageCodec.this.acceptOutboundMessage(msg);
      }

      protected void encode(ChannelHandlerContext ctx, Object msg, ByteBuf out) throws Exception {
         ByteToMessageCodec.this.encode(ctx, msg, out);
      }
   }
}
