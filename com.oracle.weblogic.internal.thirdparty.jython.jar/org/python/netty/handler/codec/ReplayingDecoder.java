package org.python.netty.handler.codec;

import java.util.List;
import org.python.netty.buffer.ByteBuf;
import org.python.netty.buffer.Unpooled;
import org.python.netty.channel.ChannelHandlerContext;
import org.python.netty.util.Signal;
import org.python.netty.util.internal.StringUtil;

public abstract class ReplayingDecoder extends ByteToMessageDecoder {
   static final Signal REPLAY = Signal.valueOf(ReplayingDecoder.class, "REPLAY");
   private final ReplayingDecoderByteBuf replayable;
   private Object state;
   private int checkpoint;

   protected ReplayingDecoder() {
      this((Object)null);
   }

   protected ReplayingDecoder(Object initialState) {
      this.replayable = new ReplayingDecoderByteBuf();
      this.checkpoint = -1;
      this.state = initialState;
   }

   protected void checkpoint() {
      this.checkpoint = this.internalBuffer().readerIndex();
   }

   protected void checkpoint(Object state) {
      this.checkpoint();
      this.state(state);
   }

   protected Object state() {
      return this.state;
   }

   protected Object state(Object newState) {
      Object oldState = this.state;
      this.state = newState;
      return oldState;
   }

   final void channelInputClosed(ChannelHandlerContext ctx, List out) throws Exception {
      try {
         this.replayable.terminate();
         if (this.cumulation != null) {
            this.callDecode(ctx, this.internalBuffer(), out);
            this.decodeLast(ctx, this.replayable, out);
         } else {
            this.replayable.setCumulation(Unpooled.EMPTY_BUFFER);
            this.decodeLast(ctx, this.replayable, out);
         }
      } catch (Signal var4) {
         var4.expect(REPLAY);
      }

   }

   protected void callDecode(ChannelHandlerContext ctx, ByteBuf in, List out) {
      this.replayable.setCumulation(in);

      try {
         while(in.isReadable()) {
            int oldReaderIndex = this.checkpoint = in.readerIndex();
            int outSize = out.size();
            if (outSize > 0) {
               fireChannelRead(ctx, out, outSize);
               out.clear();
               if (ctx.isRemoved()) {
                  break;
               }

               outSize = 0;
            }

            Object oldState = this.state;
            int oldInputLength = in.readableBytes();

            try {
               this.decodeRemovalReentryProtection(ctx, this.replayable, out);
               if (ctx.isRemoved()) {
                  break;
               }

               if (outSize == out.size()) {
                  if (oldInputLength == in.readableBytes() && oldState == this.state) {
                     throw new DecoderException(StringUtil.simpleClassName(this.getClass()) + ".decode() must consume the inbound data or change its state if it did not decode anything.");
                  }
                  continue;
               }
            } catch (Signal var10) {
               var10.expect(REPLAY);
               if (!ctx.isRemoved()) {
                  int checkpoint = this.checkpoint;
                  if (checkpoint >= 0) {
                     in.readerIndex(checkpoint);
                  }
               }
               break;
            }

            if (oldReaderIndex == in.readerIndex() && oldState == this.state) {
               throw new DecoderException(StringUtil.simpleClassName(this.getClass()) + ".decode() method must consume the inbound data or change its state if it decoded something.");
            }

            if (this.isSingleDecode()) {
               break;
            }
         }

      } catch (DecoderException var11) {
         throw var11;
      } catch (Throwable var12) {
         throw new DecoderException(var12);
      }
   }
}
