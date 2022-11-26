package org.python.netty.channel;

import java.util.ArrayDeque;
import org.python.netty.buffer.ByteBuf;
import org.python.netty.buffer.CompositeByteBuf;
import org.python.netty.buffer.Unpooled;
import org.python.netty.util.ReferenceCountUtil;
import org.python.netty.util.internal.ObjectUtil;

public final class CoalescingBufferQueue {
   private final Channel channel;
   private final ArrayDeque bufAndListenerPairs;
   private int readableBytes;

   public CoalescingBufferQueue(Channel channel) {
      this(channel, 4);
   }

   public CoalescingBufferQueue(Channel channel, int initSize) {
      this.channel = (Channel)ObjectUtil.checkNotNull(channel, "channel");
      this.bufAndListenerPairs = new ArrayDeque(initSize);
   }

   public void add(ByteBuf buf) {
      this.add(buf, (ChannelFutureListener)null);
   }

   public void add(ByteBuf buf, ChannelPromise promise) {
      ObjectUtil.checkNotNull(promise, "promise");
      this.add(buf, (ChannelFutureListener)(promise.isVoid() ? null : new ChannelPromiseNotifier(new ChannelPromise[]{promise})));
   }

   public void add(ByteBuf buf, ChannelFutureListener listener) {
      ObjectUtil.checkNotNull(buf, "buf");
      if (this.readableBytes > Integer.MAX_VALUE - buf.readableBytes()) {
         throw new IllegalStateException("buffer queue length overflow: " + this.readableBytes + " + " + buf.readableBytes());
      } else {
         this.bufAndListenerPairs.add(buf);
         if (listener != null) {
            this.bufAndListenerPairs.add(listener);
         }

         this.readableBytes += buf.readableBytes();
      }
   }

   public ByteBuf remove(int bytes, ChannelPromise aggregatePromise) {
      if (bytes < 0) {
         throw new IllegalArgumentException("bytes (expected >= 0): " + bytes);
      } else {
         ObjectUtil.checkNotNull(aggregatePromise, "aggregatePromise");
         if (this.bufAndListenerPairs.isEmpty()) {
            return Unpooled.EMPTY_BUFFER;
         } else {
            bytes = Math.min(bytes, this.readableBytes);
            ByteBuf toReturn = null;

            while(true) {
               Object entry = this.bufAndListenerPairs.poll();
               if (entry == null) {
                  break;
               }

               if (entry instanceof ChannelFutureListener) {
                  aggregatePromise.addListener((ChannelFutureListener)entry);
               } else {
                  ByteBuf entryBuffer = (ByteBuf)entry;
                  if (entryBuffer.readableBytes() > bytes) {
                     this.bufAndListenerPairs.addFirst(entryBuffer);
                     if (bytes > 0) {
                        toReturn = this.compose(toReturn, entryBuffer.readRetainedSlice(bytes));
                        bytes = 0;
                     }
                     break;
                  }

                  toReturn = this.compose(toReturn, entryBuffer);
                  bytes -= entryBuffer.readableBytes();
               }
            }

            this.readableBytes -= bytes - bytes;

            assert this.readableBytes >= 0;

            return toReturn;
         }
      }
   }

   private ByteBuf compose(ByteBuf current, ByteBuf next) {
      if (current == null) {
         return next;
      } else {
         CompositeByteBuf composite;
         if (current instanceof CompositeByteBuf) {
            composite = (CompositeByteBuf)current;
            composite.addComponent(true, next);
            return composite;
         } else {
            composite = this.channel.alloc().compositeBuffer(this.bufAndListenerPairs.size() + 2);
            composite.addComponent(true, current);
            composite.addComponent(true, next);
            return composite;
         }
      }
   }

   public int readableBytes() {
      return this.readableBytes;
   }

   public boolean isEmpty() {
      return this.bufAndListenerPairs.isEmpty();
   }

   public void releaseAndFailAll(Throwable cause) {
      this.releaseAndCompleteAll(this.channel.newFailedFuture(cause));
   }

   private void releaseAndCompleteAll(ChannelFuture future) {
      this.readableBytes = 0;
      Throwable pending = null;

      while(true) {
         Object entry = this.bufAndListenerPairs.poll();
         if (entry == null) {
            if (pending != null) {
               throw new IllegalStateException(pending);
            }

            return;
         }

         try {
            if (entry instanceof ByteBuf) {
               ReferenceCountUtil.safeRelease(entry);
            } else {
               ((ChannelFutureListener)entry).operationComplete(future);
            }
         } catch (Throwable var5) {
            pending = var5;
         }
      }
   }

   public void copyTo(CoalescingBufferQueue dest) {
      dest.bufAndListenerPairs.addAll(this.bufAndListenerPairs);
      dest.readableBytes += this.readableBytes;
   }
}
