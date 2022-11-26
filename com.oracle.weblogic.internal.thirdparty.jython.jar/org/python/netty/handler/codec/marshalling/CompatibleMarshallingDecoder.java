package org.python.netty.handler.codec.marshalling;

import java.util.List;
import org.jboss.marshalling.ByteInput;
import org.jboss.marshalling.Unmarshaller;
import org.python.netty.buffer.ByteBuf;
import org.python.netty.channel.ChannelHandlerContext;
import org.python.netty.handler.codec.ReplayingDecoder;
import org.python.netty.handler.codec.TooLongFrameException;

public class CompatibleMarshallingDecoder extends ReplayingDecoder {
   protected final UnmarshallerProvider provider;
   protected final int maxObjectSize;
   private boolean discardingTooLongFrame;

   public CompatibleMarshallingDecoder(UnmarshallerProvider provider, int maxObjectSize) {
      this.provider = provider;
      this.maxObjectSize = maxObjectSize;
   }

   protected void decode(ChannelHandlerContext ctx, ByteBuf buffer, List out) throws Exception {
      if (this.discardingTooLongFrame) {
         buffer.skipBytes(this.actualReadableBytes());
         this.checkpoint();
      } else {
         Unmarshaller unmarshaller = this.provider.getUnmarshaller(ctx);
         ByteInput input = new ChannelBufferByteInput(buffer);
         if (this.maxObjectSize != Integer.MAX_VALUE) {
            input = new LimitingByteInput((ByteInput)input, (long)this.maxObjectSize);
         }

         try {
            unmarshaller.start((ByteInput)input);
            Object obj = unmarshaller.readObject();
            unmarshaller.finish();
            out.add(obj);
         } catch (LimitingByteInput.TooBigObjectException var10) {
            this.discardingTooLongFrame = true;
            throw new TooLongFrameException();
         } finally {
            unmarshaller.close();
         }

      }
   }

   protected void decodeLast(ChannelHandlerContext ctx, ByteBuf buffer, List out) throws Exception {
      switch (buffer.readableBytes()) {
         case 0:
            return;
         case 1:
            if (buffer.getByte(buffer.readerIndex()) == 121) {
               buffer.skipBytes(1);
               return;
            }
         default:
            this.decode(ctx, buffer, out);
      }
   }

   public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
      if (cause instanceof TooLongFrameException) {
         ctx.close();
      } else {
         super.exceptionCaught(ctx, cause);
      }

   }
}
