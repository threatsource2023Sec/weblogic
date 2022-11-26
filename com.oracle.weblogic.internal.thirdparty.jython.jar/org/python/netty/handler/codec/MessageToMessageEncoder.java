package org.python.netty.handler.codec;

import java.util.List;
import org.python.netty.channel.ChannelHandlerContext;
import org.python.netty.channel.ChannelOutboundHandlerAdapter;
import org.python.netty.channel.ChannelPromise;
import org.python.netty.util.ReferenceCountUtil;
import org.python.netty.util.internal.StringUtil;
import org.python.netty.util.internal.TypeParameterMatcher;

public abstract class MessageToMessageEncoder extends ChannelOutboundHandlerAdapter {
   private final TypeParameterMatcher matcher;

   protected MessageToMessageEncoder() {
      this.matcher = TypeParameterMatcher.find(this, MessageToMessageEncoder.class, "I");
   }

   protected MessageToMessageEncoder(Class outboundMessageType) {
      this.matcher = TypeParameterMatcher.get(outboundMessageType);
   }

   public boolean acceptOutboundMessage(Object msg) throws Exception {
      return this.matcher.match(msg);
   }

   public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
      CodecOutputList out = null;
      boolean var20 = false;

      try {
         var20 = true;
         if (this.acceptOutboundMessage(msg)) {
            out = CodecOutputList.newInstance();
            Object cast = msg;

            try {
               this.encode(ctx, cast, out);
            } finally {
               ReferenceCountUtil.release(msg);
            }

            if (out.isEmpty()) {
               out.recycle();
               out = null;
               throw new EncoderException(StringUtil.simpleClassName((Object)this) + " must produce at least one message.");
            }

            var20 = false;
         } else {
            ctx.write(msg, promise);
            var20 = false;
         }
      } catch (EncoderException var26) {
         throw var26;
      } catch (Throwable var27) {
         throw new EncoderException(var27);
      } finally {
         if (var20) {
            if (out != null) {
               int sizeMinusOne = out.size() - 1;
               if (sizeMinusOne == 0) {
                  ctx.write(out.get(0), promise);
               } else if (sizeMinusOne > 0) {
                  ChannelPromise voidPromise = ctx.voidPromise();
                  boolean isVoidPromise = promise == voidPromise;

                  for(int i = 0; i < sizeMinusOne; ++i) {
                     ChannelPromise p;
                     if (isVoidPromise) {
                        p = voidPromise;
                     } else {
                        p = ctx.newPromise();
                     }

                     ctx.write(out.getUnsafe(i), p);
                  }

                  ctx.write(out.getUnsafe(sizeMinusOne), promise);
               }

               out.recycle();
            }

         }
      }

      if (out != null) {
         int sizeMinusOne = out.size() - 1;
         if (sizeMinusOne == 0) {
            ctx.write(out.get(0), promise);
         } else if (sizeMinusOne > 0) {
            ChannelPromise voidPromise = ctx.voidPromise();
            boolean isVoidPromise = promise == voidPromise;

            for(int i = 0; i < sizeMinusOne; ++i) {
               ChannelPromise p;
               if (isVoidPromise) {
                  p = voidPromise;
               } else {
                  p = ctx.newPromise();
               }

               ctx.write(out.getUnsafe(i), p);
            }

            ctx.write(out.getUnsafe(sizeMinusOne), promise);
         }

         out.recycle();
      }

   }

   protected abstract void encode(ChannelHandlerContext var1, Object var2, List var3) throws Exception;
}
