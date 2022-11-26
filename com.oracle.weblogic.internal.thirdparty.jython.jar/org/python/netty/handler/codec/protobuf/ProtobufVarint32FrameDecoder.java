package org.python.netty.handler.codec.protobuf;

import java.util.List;
import org.python.netty.buffer.ByteBuf;
import org.python.netty.channel.ChannelHandlerContext;
import org.python.netty.handler.codec.ByteToMessageDecoder;
import org.python.netty.handler.codec.CorruptedFrameException;

public class ProtobufVarint32FrameDecoder extends ByteToMessageDecoder {
   protected void decode(ChannelHandlerContext ctx, ByteBuf in, List out) throws Exception {
      in.markReaderIndex();
      int preIndex = in.readerIndex();
      int length = readRawVarint32(in);
      if (preIndex != in.readerIndex()) {
         if (length < 0) {
            throw new CorruptedFrameException("negative length: " + length);
         } else {
            if (in.readableBytes() < length) {
               in.resetReaderIndex();
            } else {
               out.add(in.readRetainedSlice(length));
            }

         }
      }
   }

   private static int readRawVarint32(ByteBuf buffer) {
      if (!buffer.isReadable()) {
         return 0;
      } else {
         buffer.markReaderIndex();
         byte tmp = buffer.readByte();
         if (tmp >= 0) {
            return tmp;
         } else {
            int result = tmp & 127;
            if (!buffer.isReadable()) {
               buffer.resetReaderIndex();
               return 0;
            } else {
               if ((tmp = buffer.readByte()) >= 0) {
                  result |= tmp << 7;
               } else {
                  result |= (tmp & 127) << 7;
                  if (!buffer.isReadable()) {
                     buffer.resetReaderIndex();
                     return 0;
                  }

                  if ((tmp = buffer.readByte()) >= 0) {
                     result |= tmp << 14;
                  } else {
                     result |= (tmp & 127) << 14;
                     if (!buffer.isReadable()) {
                        buffer.resetReaderIndex();
                        return 0;
                     }

                     if ((tmp = buffer.readByte()) >= 0) {
                        result |= tmp << 21;
                     } else {
                        result |= (tmp & 127) << 21;
                        if (!buffer.isReadable()) {
                           buffer.resetReaderIndex();
                           return 0;
                        }

                        result |= (tmp = buffer.readByte()) << 28;
                        if (tmp < 0) {
                           throw new CorruptedFrameException("malformed varint.");
                        }
                     }
                  }
               }

               return result;
            }
         }
      }
   }
}
