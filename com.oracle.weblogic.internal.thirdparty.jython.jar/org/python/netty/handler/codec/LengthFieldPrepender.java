package org.python.netty.handler.codec;

import java.nio.ByteOrder;
import java.util.List;
import org.python.netty.buffer.ByteBuf;
import org.python.netty.channel.ChannelHandler;
import org.python.netty.channel.ChannelHandlerContext;
import org.python.netty.util.internal.ObjectUtil;

@ChannelHandler.Sharable
public class LengthFieldPrepender extends MessageToMessageEncoder {
   private final ByteOrder byteOrder;
   private final int lengthFieldLength;
   private final boolean lengthIncludesLengthFieldLength;
   private final int lengthAdjustment;

   public LengthFieldPrepender(int lengthFieldLength) {
      this(lengthFieldLength, false);
   }

   public LengthFieldPrepender(int lengthFieldLength, boolean lengthIncludesLengthFieldLength) {
      this(lengthFieldLength, 0, lengthIncludesLengthFieldLength);
   }

   public LengthFieldPrepender(int lengthFieldLength, int lengthAdjustment) {
      this(lengthFieldLength, lengthAdjustment, false);
   }

   public LengthFieldPrepender(int lengthFieldLength, int lengthAdjustment, boolean lengthIncludesLengthFieldLength) {
      this(ByteOrder.BIG_ENDIAN, lengthFieldLength, lengthAdjustment, lengthIncludesLengthFieldLength);
   }

   public LengthFieldPrepender(ByteOrder byteOrder, int lengthFieldLength, int lengthAdjustment, boolean lengthIncludesLengthFieldLength) {
      if (lengthFieldLength != 1 && lengthFieldLength != 2 && lengthFieldLength != 3 && lengthFieldLength != 4 && lengthFieldLength != 8) {
         throw new IllegalArgumentException("lengthFieldLength must be either 1, 2, 3, 4, or 8: " + lengthFieldLength);
      } else {
         ObjectUtil.checkNotNull(byteOrder, "byteOrder");
         this.byteOrder = byteOrder;
         this.lengthFieldLength = lengthFieldLength;
         this.lengthIncludesLengthFieldLength = lengthIncludesLengthFieldLength;
         this.lengthAdjustment = lengthAdjustment;
      }
   }

   protected void encode(ChannelHandlerContext ctx, ByteBuf msg, List out) throws Exception {
      int length = msg.readableBytes() + this.lengthAdjustment;
      if (this.lengthIncludesLengthFieldLength) {
         length += this.lengthFieldLength;
      }

      if (length < 0) {
         throw new IllegalArgumentException("Adjusted frame length (" + length + ") is less than zero");
      } else {
         switch (this.lengthFieldLength) {
            case 1:
               if (length >= 256) {
                  throw new IllegalArgumentException("length does not fit into a byte: " + length);
               }

               out.add(ctx.alloc().buffer(1).order(this.byteOrder).writeByte((byte)length));
               break;
            case 2:
               if (length >= 65536) {
                  throw new IllegalArgumentException("length does not fit into a short integer: " + length);
               }

               out.add(ctx.alloc().buffer(2).order(this.byteOrder).writeShort((short)length));
               break;
            case 3:
               if (length >= 16777216) {
                  throw new IllegalArgumentException("length does not fit into a medium integer: " + length);
               }

               out.add(ctx.alloc().buffer(3).order(this.byteOrder).writeMedium(length));
               break;
            case 4:
               out.add(ctx.alloc().buffer(4).order(this.byteOrder).writeInt(length));
               break;
            case 5:
            case 6:
            case 7:
            default:
               throw new Error("should not reach here");
            case 8:
               out.add(ctx.alloc().buffer(8).order(this.byteOrder).writeLong((long)length));
         }

         out.add(msg.retain());
      }
   }
}
