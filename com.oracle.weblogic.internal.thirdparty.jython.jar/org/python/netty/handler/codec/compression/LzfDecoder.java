package org.python.netty.handler.codec.compression;

import com.ning.compress.BufferRecycler;
import com.ning.compress.lzf.ChunkDecoder;
import com.ning.compress.lzf.util.ChunkDecoderFactory;
import java.util.List;
import org.python.netty.buffer.ByteBuf;
import org.python.netty.channel.ChannelHandlerContext;
import org.python.netty.handler.codec.ByteToMessageDecoder;

public class LzfDecoder extends ByteToMessageDecoder {
   private State currentState;
   private static final short MAGIC_NUMBER = 23126;
   private ChunkDecoder decoder;
   private BufferRecycler recycler;
   private int chunkLength;
   private int originalLength;
   private boolean isCompressed;

   public LzfDecoder() {
      this(false);
   }

   public LzfDecoder(boolean safeInstance) {
      this.currentState = LzfDecoder.State.INIT_BLOCK;
      this.decoder = safeInstance ? ChunkDecoderFactory.safeInstance() : ChunkDecoderFactory.optimalInstance();
      this.recycler = BufferRecycler.instance();
   }

   protected void decode(ChannelHandlerContext ctx, ByteBuf in, List out) throws Exception {
      try {
         switch (this.currentState) {
            case INIT_BLOCK:
               if (in.readableBytes() < 5) {
                  break;
               }

               int magic = in.readUnsignedShort();
               if (magic != 23126) {
                  throw new DecompressionException("unexpected block identifier");
               }

               int type = in.readByte();
               switch (type) {
                  case 0:
                     this.isCompressed = false;
                     this.currentState = LzfDecoder.State.DECOMPRESS_DATA;
                     break;
                  case 1:
                     this.isCompressed = true;
                     this.currentState = LzfDecoder.State.INIT_ORIGINAL_LENGTH;
                     break;
                  default:
                     throw new DecompressionException(String.format("unknown type of chunk: %d (expected: %d or %d)", Integer.valueOf(type), 0, 1));
               }

               this.chunkLength = in.readUnsignedShort();
               if (type != 1) {
                  break;
               }
            case INIT_ORIGINAL_LENGTH:
               if (in.readableBytes() < 2) {
                  break;
               }

               this.originalLength = in.readUnsignedShort();
               this.currentState = LzfDecoder.State.DECOMPRESS_DATA;
            case DECOMPRESS_DATA:
               int chunkLength = this.chunkLength;
               if (in.readableBytes() >= chunkLength) {
                  int originalLength = this.originalLength;
                  if (this.isCompressed) {
                     int idx = in.readerIndex();
                     byte[] inputArray;
                     int inPos;
                     if (in.hasArray()) {
                        inputArray = in.array();
                        inPos = in.arrayOffset() + idx;
                     } else {
                        inputArray = this.recycler.allocInputBuffer(chunkLength);
                        in.getBytes(idx, (byte[])inputArray, 0, chunkLength);
                        inPos = 0;
                     }

                     ByteBuf uncompressed = ctx.alloc().heapBuffer(originalLength, originalLength);
                     byte[] outputArray = uncompressed.array();
                     int outPos = uncompressed.arrayOffset() + uncompressed.writerIndex();
                     boolean success = false;

                     try {
                        this.decoder.decodeChunk(inputArray, inPos, outputArray, outPos, outPos + originalLength);
                        uncompressed.writerIndex(uncompressed.writerIndex() + originalLength);
                        out.add(uncompressed);
                        in.skipBytes(chunkLength);
                        success = true;
                     } finally {
                        if (!success) {
                           uncompressed.release();
                        }

                     }

                     if (!in.hasArray()) {
                        this.recycler.releaseInputBuffer(inputArray);
                     }
                  } else if (chunkLength > 0) {
                     out.add(in.readRetainedSlice(chunkLength));
                  }

                  this.currentState = LzfDecoder.State.INIT_BLOCK;
               }
               break;
            case CORRUPTED:
               in.skipBytes(in.readableBytes());
               break;
            default:
               throw new IllegalStateException();
         }

      } catch (Exception var19) {
         this.currentState = LzfDecoder.State.CORRUPTED;
         this.decoder = null;
         this.recycler = null;
         throw var19;
      }
   }

   private static enum State {
      INIT_BLOCK,
      INIT_ORIGINAL_LENGTH,
      DECOMPRESS_DATA,
      CORRUPTED;
   }
}
