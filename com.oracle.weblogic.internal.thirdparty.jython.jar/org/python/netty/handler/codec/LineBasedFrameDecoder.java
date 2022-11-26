package org.python.netty.handler.codec;

import java.util.List;
import org.python.netty.buffer.ByteBuf;
import org.python.netty.channel.ChannelHandlerContext;
import org.python.netty.util.ByteProcessor;

public class LineBasedFrameDecoder extends ByteToMessageDecoder {
   private final int maxLength;
   private final boolean failFast;
   private final boolean stripDelimiter;
   private boolean discarding;
   private int discardedBytes;

   public LineBasedFrameDecoder(int maxLength) {
      this(maxLength, true, false);
   }

   public LineBasedFrameDecoder(int maxLength, boolean stripDelimiter, boolean failFast) {
      this.maxLength = maxLength;
      this.failFast = failFast;
      this.stripDelimiter = stripDelimiter;
   }

   protected final void decode(ChannelHandlerContext ctx, ByteBuf in, List out) throws Exception {
      Object decoded = this.decode(ctx, in);
      if (decoded != null) {
         out.add(decoded);
      }

   }

   protected Object decode(ChannelHandlerContext ctx, ByteBuf buffer) throws Exception {
      int eol = findEndOfLine(buffer);
      int length;
      int length;
      if (!this.discarding) {
         if (eol >= 0) {
            length = eol - buffer.readerIndex();
            int delimLength = buffer.getByte(eol) == 13 ? 2 : 1;
            if (length > this.maxLength) {
               buffer.readerIndex(eol + delimLength);
               this.fail(ctx, length);
               return null;
            } else {
               ByteBuf frame;
               if (this.stripDelimiter) {
                  frame = buffer.readRetainedSlice(length);
                  buffer.skipBytes(delimLength);
               } else {
                  frame = buffer.readRetainedSlice(length + delimLength);
               }

               return frame;
            }
         } else {
            length = buffer.readableBytes();
            if (length > this.maxLength) {
               this.discardedBytes = length;
               buffer.readerIndex(buffer.writerIndex());
               this.discarding = true;
               if (this.failFast) {
                  this.fail(ctx, "over " + this.discardedBytes);
               }
            }

            return null;
         }
      } else {
         if (eol >= 0) {
            length = this.discardedBytes + eol - buffer.readerIndex();
            length = buffer.getByte(eol) == 13 ? 2 : 1;
            buffer.readerIndex(eol + length);
            this.discardedBytes = 0;
            this.discarding = false;
            if (!this.failFast) {
               this.fail(ctx, length);
            }
         } else {
            this.discardedBytes += buffer.readableBytes();
            buffer.readerIndex(buffer.writerIndex());
         }

         return null;
      }
   }

   private void fail(ChannelHandlerContext ctx, int length) {
      this.fail(ctx, String.valueOf(length));
   }

   private void fail(ChannelHandlerContext ctx, String length) {
      ctx.fireExceptionCaught(new TooLongFrameException("frame length (" + length + ") exceeds the allowed maximum (" + this.maxLength + ')'));
   }

   private static int findEndOfLine(ByteBuf buffer) {
      int i = buffer.forEachByte(ByteProcessor.FIND_LF);
      if (i > 0 && buffer.getByte(i - 1) == 13) {
         --i;
      }

      return i;
   }
}
