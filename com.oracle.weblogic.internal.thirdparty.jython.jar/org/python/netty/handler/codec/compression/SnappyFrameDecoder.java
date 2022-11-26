package org.python.netty.handler.codec.compression;

import java.util.List;
import org.python.netty.buffer.ByteBuf;
import org.python.netty.channel.ChannelHandlerContext;
import org.python.netty.handler.codec.ByteToMessageDecoder;

public class SnappyFrameDecoder extends ByteToMessageDecoder {
   private static final int SNAPPY_IDENTIFIER_LEN = 6;
   private static final int MAX_UNCOMPRESSED_DATA_SIZE = 65540;
   private final Snappy snappy;
   private final boolean validateChecksums;
   private boolean started;
   private boolean corrupted;

   public SnappyFrameDecoder() {
      this(false);
   }

   public SnappyFrameDecoder(boolean validateChecksums) {
      this.snappy = new Snappy();
      this.validateChecksums = validateChecksums;
   }

   protected void decode(ChannelHandlerContext ctx, ByteBuf in, List out) throws Exception {
      if (this.corrupted) {
         in.skipBytes(in.readableBytes());
      } else {
         try {
            int idx = in.readerIndex();
            int inSize = in.readableBytes();
            if (inSize >= 4) {
               int chunkTypeVal = in.getUnsignedByte(idx);
               ChunkType chunkType = mapChunkType((byte)chunkTypeVal);
               int chunkLength = in.getUnsignedMediumLE(idx + 1);
               int checksum;
               switch (chunkType) {
                  case STREAM_IDENTIFIER:
                     if (chunkLength != 6) {
                        throw new DecompressionException("Unexpected length of stream identifier: " + chunkLength);
                     }

                     if (inSize >= 10) {
                        in.skipBytes(4);
                        int offset = in.readerIndex();
                        in.skipBytes(6);
                        checkByte(in.getByte(offset++), (byte)115);
                        checkByte(in.getByte(offset++), (byte)78);
                        checkByte(in.getByte(offset++), (byte)97);
                        checkByte(in.getByte(offset++), (byte)80);
                        checkByte(in.getByte(offset++), (byte)112);
                        checkByte(in.getByte(offset), (byte)89);
                        this.started = true;
                     }
                     break;
                  case RESERVED_SKIPPABLE:
                     if (!this.started) {
                        throw new DecompressionException("Received RESERVED_SKIPPABLE tag before STREAM_IDENTIFIER");
                     }

                     if (inSize < 4 + chunkLength) {
                        return;
                     }

                     in.skipBytes(4 + chunkLength);
                     break;
                  case RESERVED_UNSKIPPABLE:
                     throw new DecompressionException("Found reserved unskippable chunk type: 0x" + Integer.toHexString(chunkTypeVal));
                  case UNCOMPRESSED_DATA:
                     if (!this.started) {
                        throw new DecompressionException("Received UNCOMPRESSED_DATA tag before STREAM_IDENTIFIER");
                     }

                     if (chunkLength > 65540) {
                        throw new DecompressionException("Received UNCOMPRESSED_DATA larger than 65540 bytes");
                     }

                     if (inSize < 4 + chunkLength) {
                        return;
                     }

                     in.skipBytes(4);
                     if (this.validateChecksums) {
                        checksum = in.readIntLE();
                        Snappy.validateChecksum(checksum, in, in.readerIndex(), chunkLength - 4);
                     } else {
                        in.skipBytes(4);
                     }

                     out.add(in.readRetainedSlice(chunkLength - 4));
                     break;
                  case COMPRESSED_DATA:
                     if (!this.started) {
                        throw new DecompressionException("Received COMPRESSED_DATA tag before STREAM_IDENTIFIER");
                     }

                     if (inSize < 4 + chunkLength) {
                        return;
                     }

                     in.skipBytes(4);
                     checksum = in.readIntLE();
                     ByteBuf uncompressed = ctx.alloc().buffer();

                     try {
                        if (this.validateChecksums) {
                           int oldWriterIndex = in.writerIndex();

                           try {
                              in.writerIndex(in.readerIndex() + chunkLength - 4);
                              this.snappy.decode(in, uncompressed);
                           } finally {
                              in.writerIndex(oldWriterIndex);
                           }

                           Snappy.validateChecksum(checksum, uncompressed, 0, uncompressed.writerIndex());
                        } else {
                           this.snappy.decode(in.readSlice(chunkLength - 4), uncompressed);
                        }

                        out.add(uncompressed);
                        uncompressed = null;
                     } finally {
                        if (uncompressed != null) {
                           uncompressed.release();
                        }

                     }

                     this.snappy.reset();
               }

            }
         } catch (Exception var23) {
            this.corrupted = true;
            throw var23;
         }
      }
   }

   private static void checkByte(byte actual, byte expect) {
      if (actual != expect) {
         throw new DecompressionException("Unexpected stream identifier contents. Mismatched snappy protocol version?");
      }
   }

   private static ChunkType mapChunkType(byte type) {
      if (type == 0) {
         return SnappyFrameDecoder.ChunkType.COMPRESSED_DATA;
      } else if (type == 1) {
         return SnappyFrameDecoder.ChunkType.UNCOMPRESSED_DATA;
      } else if (type == -1) {
         return SnappyFrameDecoder.ChunkType.STREAM_IDENTIFIER;
      } else {
         return (type & 128) == 128 ? SnappyFrameDecoder.ChunkType.RESERVED_SKIPPABLE : SnappyFrameDecoder.ChunkType.RESERVED_UNSKIPPABLE;
      }
   }

   private static enum ChunkType {
      STREAM_IDENTIFIER,
      COMPRESSED_DATA,
      UNCOMPRESSED_DATA,
      RESERVED_UNSKIPPABLE,
      RESERVED_SKIPPABLE;
   }
}
