package org.python.netty.handler.codec;

import java.util.List;
import org.python.netty.buffer.ByteBuf;
import org.python.netty.buffer.ByteBufAllocator;
import org.python.netty.buffer.CompositeByteBuf;
import org.python.netty.buffer.Unpooled;
import org.python.netty.channel.ChannelHandlerContext;
import org.python.netty.channel.ChannelInboundHandlerAdapter;
import org.python.netty.channel.socket.ChannelInputShutdownEvent;
import org.python.netty.util.internal.StringUtil;

public abstract class ByteToMessageDecoder extends ChannelInboundHandlerAdapter {
   public static final Cumulator MERGE_CUMULATOR = new Cumulator() {
      public ByteBuf cumulate(ByteBufAllocator alloc, ByteBuf cumulation, ByteBuf in) {
         ByteBuf buffer;
         if (cumulation.writerIndex() <= cumulation.maxCapacity() - in.readableBytes() && cumulation.refCnt() <= 1 && !cumulation.isReadOnly()) {
            buffer = cumulation;
         } else {
            buffer = ByteToMessageDecoder.expandCumulation(alloc, cumulation, in.readableBytes());
         }

         buffer.writeBytes(in);
         in.release();
         return buffer;
      }
   };
   public static final Cumulator COMPOSITE_CUMULATOR = new Cumulator() {
      public ByteBuf cumulate(ByteBufAllocator alloc, ByteBuf cumulation, ByteBuf in) {
         Object buffer;
         if (cumulation.refCnt() > 1) {
            buffer = ByteToMessageDecoder.expandCumulation(alloc, cumulation, in.readableBytes());
            ((ByteBuf)buffer).writeBytes(in);
            in.release();
         } else {
            CompositeByteBuf composite;
            if (cumulation instanceof CompositeByteBuf) {
               composite = (CompositeByteBuf)cumulation;
            } else {
               composite = alloc.compositeBuffer(Integer.MAX_VALUE);
               composite.addComponent(true, cumulation);
            }

            composite.addComponent(true, in);
            buffer = composite;
         }

         return (ByteBuf)buffer;
      }
   };
   private static final byte STATE_INIT = 0;
   private static final byte STATE_CALLING_CHILD_DECODE = 1;
   private static final byte STATE_HANDLER_REMOVED_PENDING = 2;
   ByteBuf cumulation;
   private Cumulator cumulator;
   private boolean singleDecode;
   private boolean decodeWasNull;
   private boolean first;
   private byte decodeState;
   private int discardAfterReads;
   private int numReads;

   protected ByteToMessageDecoder() {
      this.cumulator = MERGE_CUMULATOR;
      this.decodeState = 0;
      this.discardAfterReads = 16;
      this.ensureNotSharable();
   }

   public void setSingleDecode(boolean singleDecode) {
      this.singleDecode = singleDecode;
   }

   public boolean isSingleDecode() {
      return this.singleDecode;
   }

   public void setCumulator(Cumulator cumulator) {
      if (cumulator == null) {
         throw new NullPointerException("cumulator");
      } else {
         this.cumulator = cumulator;
      }
   }

   public void setDiscardAfterReads(int discardAfterReads) {
      if (discardAfterReads <= 0) {
         throw new IllegalArgumentException("discardAfterReads must be > 0");
      } else {
         this.discardAfterReads = discardAfterReads;
      }
   }

   protected int actualReadableBytes() {
      return this.internalBuffer().readableBytes();
   }

   protected ByteBuf internalBuffer() {
      return this.cumulation != null ? this.cumulation : Unpooled.EMPTY_BUFFER;
   }

   public final void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
      if (this.decodeState == 1) {
         this.decodeState = 2;
      } else {
         ByteBuf buf = this.cumulation;
         if (buf != null) {
            this.cumulation = null;
            int readable = buf.readableBytes();
            if (readable > 0) {
               ByteBuf bytes = buf.readBytes(readable);
               buf.release();
               ctx.fireChannelRead(bytes);
            } else {
               buf.release();
            }

            this.numReads = 0;
            ctx.fireChannelReadComplete();
         }

         this.handlerRemoved0(ctx);
      }
   }

   protected void handlerRemoved0(ChannelHandlerContext ctx) throws Exception {
   }

   public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
      if (msg instanceof ByteBuf) {
         CodecOutputList out = CodecOutputList.newInstance();
         boolean var10 = false;

         try {
            var10 = true;
            ByteBuf data = (ByteBuf)msg;
            this.first = this.cumulation == null;
            if (this.first) {
               this.cumulation = data;
            } else {
               this.cumulation = this.cumulator.cumulate(ctx.alloc(), this.cumulation, data);
            }

            this.callDecode(ctx, this.cumulation, out);
            var10 = false;
         } catch (DecoderException var11) {
            throw var11;
         } catch (Throwable var12) {
            throw new DecoderException(var12);
         } finally {
            if (var10) {
               if (this.cumulation != null && !this.cumulation.isReadable()) {
                  this.numReads = 0;
                  this.cumulation.release();
                  this.cumulation = null;
               } else if (++this.numReads >= this.discardAfterReads) {
                  this.numReads = 0;
                  this.discardSomeReadBytes();
               }

               int size = out.size();
               this.decodeWasNull = !out.insertSinceRecycled();
               fireChannelRead(ctx, out, size);
               out.recycle();
            }
         }

         if (this.cumulation != null && !this.cumulation.isReadable()) {
            this.numReads = 0;
            this.cumulation.release();
            this.cumulation = null;
         } else if (++this.numReads >= this.discardAfterReads) {
            this.numReads = 0;
            this.discardSomeReadBytes();
         }

         int size = out.size();
         this.decodeWasNull = !out.insertSinceRecycled();
         fireChannelRead(ctx, out, size);
         out.recycle();
      } else {
         ctx.fireChannelRead(msg);
      }

   }

   static void fireChannelRead(ChannelHandlerContext ctx, List msgs, int numElements) {
      if (msgs instanceof CodecOutputList) {
         fireChannelRead(ctx, (CodecOutputList)msgs, numElements);
      } else {
         for(int i = 0; i < numElements; ++i) {
            ctx.fireChannelRead(msgs.get(i));
         }
      }

   }

   static void fireChannelRead(ChannelHandlerContext ctx, CodecOutputList msgs, int numElements) {
      for(int i = 0; i < numElements; ++i) {
         ctx.fireChannelRead(msgs.getUnsafe(i));
      }

   }

   public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
      this.numReads = 0;
      this.discardSomeReadBytes();
      if (this.decodeWasNull) {
         this.decodeWasNull = false;
         if (!ctx.channel().config().isAutoRead()) {
            ctx.read();
         }
      }

      ctx.fireChannelReadComplete();
   }

   protected final void discardSomeReadBytes() {
      if (this.cumulation != null && !this.first && this.cumulation.refCnt() == 1) {
         this.cumulation.discardSomeReadBytes();
      }

   }

   public void channelInactive(ChannelHandlerContext ctx) throws Exception {
      this.channelInputClosed(ctx, true);
   }

   public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
      if (evt instanceof ChannelInputShutdownEvent) {
         this.channelInputClosed(ctx, false);
      }

      super.userEventTriggered(ctx, evt);
   }

   private void channelInputClosed(ChannelHandlerContext ctx, boolean callChannelInactive) throws Exception {
      CodecOutputList out = CodecOutputList.newInstance();
      boolean var24 = false;

      try {
         var24 = true;
         this.channelInputClosed(ctx, out);
         var24 = false;
      } catch (DecoderException var25) {
         throw var25;
      } catch (Exception var26) {
         throw new DecoderException(var26);
      } finally {
         if (var24) {
            try {
               if (this.cumulation != null) {
                  this.cumulation.release();
                  this.cumulation = null;
               }

               int size = out.size();
               fireChannelRead(ctx, out, size);
               if (size > 0) {
                  ctx.fireChannelReadComplete();
               }

               if (callChannelInactive) {
                  ctx.fireChannelInactive();
               }
            } finally {
               out.recycle();
            }

         }
      }

      try {
         if (this.cumulation != null) {
            this.cumulation.release();
            this.cumulation = null;
         }

         int size = out.size();
         fireChannelRead(ctx, out, size);
         if (size > 0) {
            ctx.fireChannelReadComplete();
         }

         if (callChannelInactive) {
            ctx.fireChannelInactive();
         }
      } finally {
         out.recycle();
      }

   }

   void channelInputClosed(ChannelHandlerContext ctx, List out) throws Exception {
      if (this.cumulation != null) {
         this.callDecode(ctx, this.cumulation, out);
         this.decodeLast(ctx, this.cumulation, out);
      } else {
         this.decodeLast(ctx, Unpooled.EMPTY_BUFFER, out);
      }

   }

   protected void callDecode(ChannelHandlerContext ctx, ByteBuf in, List out) {
      try {
         while(true) {
            if (in.isReadable()) {
               int outSize = out.size();
               if (outSize > 0) {
                  fireChannelRead(ctx, out, outSize);
                  out.clear();
                  if (ctx.isRemoved()) {
                     return;
                  }

                  outSize = 0;
               }

               int oldInputLength = in.readableBytes();
               this.decodeRemovalReentryProtection(ctx, in, out);
               if (!ctx.isRemoved()) {
                  if (outSize == out.size()) {
                     if (oldInputLength != in.readableBytes()) {
                        continue;
                     }
                  } else {
                     if (oldInputLength == in.readableBytes()) {
                        throw new DecoderException(StringUtil.simpleClassName(this.getClass()) + ".decode() did not read anything but decoded a message.");
                     }

                     if (!this.isSingleDecode()) {
                        continue;
                     }
                  }
               }
            }

            return;
         }
      } catch (DecoderException var6) {
         throw var6;
      } catch (Throwable var7) {
         throw new DecoderException(var7);
      }
   }

   protected abstract void decode(ChannelHandlerContext var1, ByteBuf var2, List var3) throws Exception;

   final void decodeRemovalReentryProtection(ChannelHandlerContext ctx, ByteBuf in, List out) throws Exception {
      this.decodeState = 1;
      boolean var8 = false;

      try {
         var8 = true;
         this.decode(ctx, in, out);
         var8 = false;
      } finally {
         if (var8) {
            boolean removePending = this.decodeState == 2;
            this.decodeState = 0;
            if (removePending) {
               this.handlerRemoved(ctx);
            }

         }
      }

      boolean removePending = this.decodeState == 2;
      this.decodeState = 0;
      if (removePending) {
         this.handlerRemoved(ctx);
      }

   }

   protected void decodeLast(ChannelHandlerContext ctx, ByteBuf in, List out) throws Exception {
      if (in.isReadable()) {
         this.decodeRemovalReentryProtection(ctx, in, out);
      }

   }

   static ByteBuf expandCumulation(ByteBufAllocator alloc, ByteBuf cumulation, int readable) {
      ByteBuf oldCumulation = cumulation;
      cumulation = alloc.buffer(cumulation.readableBytes() + readable);
      cumulation.writeBytes(oldCumulation);
      oldCumulation.release();
      return cumulation;
   }

   public interface Cumulator {
      ByteBuf cumulate(ByteBufAllocator var1, ByteBuf var2, ByteBuf var3);
   }
}
