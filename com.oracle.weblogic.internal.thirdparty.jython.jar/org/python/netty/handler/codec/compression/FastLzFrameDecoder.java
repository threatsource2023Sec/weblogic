package org.python.netty.handler.codec.compression;

import java.util.List;
import java.util.zip.Adler32;
import java.util.zip.Checksum;
import org.python.netty.buffer.ByteBuf;
import org.python.netty.channel.ChannelHandlerContext;
import org.python.netty.handler.codec.ByteToMessageDecoder;
import org.python.netty.util.internal.EmptyArrays;

public class FastLzFrameDecoder extends ByteToMessageDecoder {
   private State currentState;
   private final Checksum checksum;
   private int chunkLength;
   private int originalLength;
   private boolean isCompressed;
   private boolean hasChecksum;
   private int currentChecksum;

   public FastLzFrameDecoder() {
      this(false);
   }

   public FastLzFrameDecoder(boolean validateChecksums) {
      this(validateChecksums ? new Adler32() : null);
   }

   public FastLzFrameDecoder(Checksum checksum) {
      this.currentState = FastLzFrameDecoder.State.INIT_BLOCK;
      this.checksum = checksum;
   }

   protected void decode(ChannelHandlerContext ctx, ByteBuf in, List out) throws Exception {
      try {
         switch (this.currentState) {
            case INIT_BLOCK:
               if (in.readableBytes() < 4) {
                  break;
               }

               int magic = in.readUnsignedMedium();
               if (magic != 4607066) {
                  throw new DecompressionException("unexpected block identifier");
               }

               byte options = in.readByte();
               this.isCompressed = (options & 1) == 1;
               this.hasChecksum = (options & 16) == 16;
               this.currentState = FastLzFrameDecoder.State.INIT_BLOCK_PARAMS;
            case INIT_BLOCK_PARAMS:
               if (in.readableBytes() < 2 + (this.isCompressed ? 2 : 0) + (this.hasChecksum ? 4 : 0)) {
                  break;
               }

               this.currentChecksum = this.hasChecksum ? in.readInt() : 0;
               this.chunkLength = in.readUnsignedShort();
               this.originalLength = this.isCompressed ? in.readUnsignedShort() : this.chunkLength;
               this.currentState = FastLzFrameDecoder.State.DECOMPRESS_DATA;
            case DECOMPRESS_DATA:
               int chunkLength = this.chunkLength;
               if (in.readableBytes() >= chunkLength) {
                  int idx = in.readerIndex();
                  int originalLength = this.originalLength;
                  ByteBuf uncompressed;
                  byte[] output;
                  int outputPtr;
                  if (originalLength != 0) {
                     uncompressed = ctx.alloc().heapBuffer(originalLength, originalLength);
                     output = uncompressed.array();
                     outputPtr = uncompressed.arrayOffset() + uncompressed.writerIndex();
                  } else {
                     uncompressed = null;
                     output = EmptyArrays.EMPTY_BYTES;
                     outputPtr = 0;
                  }

                  boolean success = false;

                  try {
                     int checksumResult;
                     if (this.isCompressed) {
                        byte[] input;
                        if (in.hasArray()) {
                           input = in.array();
                           checksumResult = in.arrayOffset() + idx;
                        } else {
                           input = new byte[chunkLength];
                           in.getBytes(idx, input);
                           checksumResult = 0;
                        }

                        int decompressedBytes = FastLz.decompress(input, checksumResult, chunkLength, output, outputPtr, originalLength);
                        if (originalLength != decompressedBytes) {
                           throw new DecompressionException(String.format("stream corrupted: originalLength(%d) and actual length(%d) mismatch", originalLength, decompressedBytes));
                        }
                     } else {
                        in.getBytes(idx, output, outputPtr, chunkLength);
                     }

                     Checksum checksum = this.checksum;
                     if (this.hasChecksum && checksum != null) {
                        checksum.reset();
                        checksum.update(output, outputPtr, originalLength);
                        checksumResult = (int)checksum.getValue();
                        if (checksumResult != this.currentChecksum) {
                           throw new DecompressionException(String.format("stream corrupted: mismatching checksum: %d (expected: %d)", checksumResult, this.currentChecksum));
                        }
                     }

                     if (uncompressed != null) {
                        uncompressed.writerIndex(uncompressed.writerIndex() + originalLength);
                        out.add(uncompressed);
                     }

                     in.skipBytes(chunkLength);
                     this.currentState = FastLzFrameDecoder.State.INIT_BLOCK;
                     success = true;
                  } finally {
                     if (!success) {
                        uncompressed.release();
                     }

                  }
               }
               break;
            case CORRUPTED:
               in.skipBytes(in.readableBytes());
               break;
            default:
               throw new IllegalStateException();
         }

      } catch (Exception var20) {
         this.currentState = FastLzFrameDecoder.State.CORRUPTED;
         throw var20;
      }
   }

   private static enum State {
      INIT_BLOCK,
      INIT_BLOCK_PARAMS,
      DECOMPRESS_DATA,
      CORRUPTED;
   }
}
