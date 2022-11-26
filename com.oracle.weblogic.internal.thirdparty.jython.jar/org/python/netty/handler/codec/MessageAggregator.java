package org.python.netty.handler.codec;

import java.util.List;
import org.python.netty.buffer.ByteBuf;
import org.python.netty.buffer.ByteBufHolder;
import org.python.netty.buffer.CompositeByteBuf;
import org.python.netty.buffer.Unpooled;
import org.python.netty.channel.ChannelFuture;
import org.python.netty.channel.ChannelFutureListener;
import org.python.netty.channel.ChannelHandlerContext;
import org.python.netty.channel.ChannelPipeline;
import org.python.netty.util.ReferenceCountUtil;

public abstract class MessageAggregator extends MessageToMessageDecoder {
   private static final int DEFAULT_MAX_COMPOSITEBUFFER_COMPONENTS = 1024;
   private final int maxContentLength;
   private ByteBufHolder currentMessage;
   private boolean handlingOversizedMessage;
   private int maxCumulationBufferComponents = 1024;
   private ChannelHandlerContext ctx;
   private ChannelFutureListener continueResponseWriteListener;

   protected MessageAggregator(int maxContentLength) {
      validateMaxContentLength(maxContentLength);
      this.maxContentLength = maxContentLength;
   }

   protected MessageAggregator(int maxContentLength, Class inboundMessageType) {
      super(inboundMessageType);
      validateMaxContentLength(maxContentLength);
      this.maxContentLength = maxContentLength;
   }

   private static void validateMaxContentLength(int maxContentLength) {
      if (maxContentLength < 0) {
         throw new IllegalArgumentException("maxContentLength: " + maxContentLength + " (expected: >= 0)");
      }
   }

   public boolean acceptInboundMessage(Object msg) throws Exception {
      if (!super.acceptInboundMessage(msg)) {
         return false;
      } else {
         return (this.isContentMessage(msg) || this.isStartMessage(msg)) && !this.isAggregated(msg);
      }
   }

   protected abstract boolean isStartMessage(Object var1) throws Exception;

   protected abstract boolean isContentMessage(Object var1) throws Exception;

   protected abstract boolean isLastContentMessage(ByteBufHolder var1) throws Exception;

   protected abstract boolean isAggregated(Object var1) throws Exception;

   public final int maxContentLength() {
      return this.maxContentLength;
   }

   public final int maxCumulationBufferComponents() {
      return this.maxCumulationBufferComponents;
   }

   public final void setMaxCumulationBufferComponents(int maxCumulationBufferComponents) {
      if (maxCumulationBufferComponents < 2) {
         throw new IllegalArgumentException("maxCumulationBufferComponents: " + maxCumulationBufferComponents + " (expected: >= 2)");
      } else if (this.ctx == null) {
         this.maxCumulationBufferComponents = maxCumulationBufferComponents;
      } else {
         throw new IllegalStateException("decoder properties cannot be changed once the decoder is added to a pipeline.");
      }
   }

   /** @deprecated */
   @Deprecated
   public final boolean isHandlingOversizedMessage() {
      return this.handlingOversizedMessage;
   }

   protected final ChannelHandlerContext ctx() {
      if (this.ctx == null) {
         throw new IllegalStateException("not added to a pipeline yet");
      } else {
         return this.ctx;
      }
   }

   protected void decode(final ChannelHandlerContext ctx, Object msg, List out) throws Exception {
      ByteBufHolder aggregated;
      if (this.isStartMessage(msg)) {
         this.handlingOversizedMessage = false;
         if (this.currentMessage != null) {
            this.currentMessage.release();
            this.currentMessage = null;
            throw new MessageAggregationException();
         }

         Object continueResponse = this.newContinueResponse(msg, this.maxContentLength, ctx.pipeline());
         if (continueResponse != null) {
            ChannelFutureListener listener = this.continueResponseWriteListener;
            if (listener == null) {
               this.continueResponseWriteListener = listener = new ChannelFutureListener() {
                  public void operationComplete(ChannelFuture future) throws Exception {
                     if (!future.isSuccess()) {
                        ctx.fireExceptionCaught(future.cause());
                     }

                  }
               };
            }

            boolean closeAfterWrite = this.closeAfterContinueResponse(continueResponse);
            this.handlingOversizedMessage = this.ignoreContentAfterContinueResponse(continueResponse);
            ChannelFuture future = ctx.writeAndFlush(continueResponse).addListener(listener);
            if (closeAfterWrite) {
               future.addListener(ChannelFutureListener.CLOSE);
               return;
            }

            if (this.handlingOversizedMessage) {
               return;
            }
         } else if (this.isContentLengthInvalid(msg, this.maxContentLength)) {
            this.invokeHandleOversizedMessage(ctx, msg);
            return;
         }

         if (msg instanceof DecoderResultProvider && !((DecoderResultProvider)msg).decoderResult().isSuccess()) {
            if (msg instanceof ByteBufHolder && ((ByteBufHolder)msg).content().isReadable()) {
               aggregated = this.beginAggregation(msg, ((ByteBufHolder)msg).content().retain());
            } else {
               aggregated = this.beginAggregation(msg, Unpooled.EMPTY_BUFFER);
            }

            this.finishAggregation(aggregated);
            out.add(aggregated);
            return;
         }

         CompositeByteBuf content = ctx.alloc().compositeBuffer(this.maxCumulationBufferComponents);
         if (msg instanceof ByteBufHolder) {
            appendPartialContent(content, ((ByteBufHolder)msg).content());
         }

         this.currentMessage = this.beginAggregation(msg, content);
      } else {
         if (!this.isContentMessage(msg)) {
            throw new MessageAggregationException();
         }

         if (this.currentMessage == null) {
            return;
         }

         CompositeByteBuf content = (CompositeByteBuf)this.currentMessage.content();
         ByteBufHolder m = (ByteBufHolder)msg;
         if (content.readableBytes() > this.maxContentLength - m.content().readableBytes()) {
            aggregated = this.currentMessage;
            this.invokeHandleOversizedMessage(ctx, aggregated);
            return;
         }

         appendPartialContent(content, m.content());
         this.aggregate(this.currentMessage, m);
         boolean last;
         if (m instanceof DecoderResultProvider) {
            DecoderResult decoderResult = ((DecoderResultProvider)m).decoderResult();
            if (!decoderResult.isSuccess()) {
               if (this.currentMessage instanceof DecoderResultProvider) {
                  ((DecoderResultProvider)this.currentMessage).setDecoderResult(DecoderResult.failure(decoderResult.cause()));
               }

               last = true;
            } else {
               last = this.isLastContentMessage(m);
            }
         } else {
            last = this.isLastContentMessage(m);
         }

         if (last) {
            this.finishAggregation(this.currentMessage);
            out.add(this.currentMessage);
            this.currentMessage = null;
         }
      }

   }

   private static void appendPartialContent(CompositeByteBuf content, ByteBuf partialContent) {
      if (partialContent.isReadable()) {
         content.addComponent(true, partialContent.retain());
      }

   }

   protected abstract boolean isContentLengthInvalid(Object var1, int var2) throws Exception;

   protected abstract Object newContinueResponse(Object var1, int var2, ChannelPipeline var3) throws Exception;

   protected abstract boolean closeAfterContinueResponse(Object var1) throws Exception;

   protected abstract boolean ignoreContentAfterContinueResponse(Object var1) throws Exception;

   protected abstract ByteBufHolder beginAggregation(Object var1, ByteBuf var2) throws Exception;

   protected void aggregate(ByteBufHolder aggregated, ByteBufHolder content) throws Exception {
   }

   protected void finishAggregation(ByteBufHolder aggregated) throws Exception {
   }

   private void invokeHandleOversizedMessage(ChannelHandlerContext ctx, Object oversized) throws Exception {
      this.handlingOversizedMessage = true;
      this.currentMessage = null;

      try {
         this.handleOversizedMessage(ctx, oversized);
      } finally {
         ReferenceCountUtil.release(oversized);
      }

   }

   protected void handleOversizedMessage(ChannelHandlerContext ctx, Object oversized) throws Exception {
      ctx.fireExceptionCaught(new TooLongFrameException("content length exceeded " + this.maxContentLength() + " bytes."));
   }

   public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
      if (this.currentMessage != null && !ctx.channel().config().isAutoRead()) {
         ctx.read();
      }

      ctx.fireChannelReadComplete();
   }

   public void channelInactive(ChannelHandlerContext ctx) throws Exception {
      try {
         super.channelInactive(ctx);
      } finally {
         this.releaseCurrentMessage();
      }

   }

   public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
      this.ctx = ctx;
   }

   public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
      try {
         super.handlerRemoved(ctx);
      } finally {
         this.releaseCurrentMessage();
      }

   }

   private void releaseCurrentMessage() {
      if (this.currentMessage != null) {
         this.currentMessage.release();
         this.currentMessage = null;
         this.handlingOversizedMessage = false;
      }

   }
}
