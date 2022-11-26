package org.python.netty.handler.codec.compression;

import java.util.List;
import org.python.netty.buffer.ByteBuf;
import org.python.netty.channel.ChannelHandlerContext;
import org.python.netty.handler.codec.ByteToMessageDecoder;

public class Bzip2Decoder extends ByteToMessageDecoder {
   private State currentState;
   private final Bzip2BitReader reader;
   private Bzip2BlockDecompressor blockDecompressor;
   private Bzip2HuffmanStageDecoder huffmanStageDecoder;
   private int blockSize;
   private int blockCRC;
   private int streamCRC;

   public Bzip2Decoder() {
      this.currentState = Bzip2Decoder.State.INIT;
      this.reader = new Bzip2BitReader();
   }

   protected void decode(ChannelHandlerContext ctx, ByteBuf in, List out) throws Exception {
      if (in.isReadable()) {
         Bzip2BitReader reader = this.reader;
         reader.setByteBuf(in);

         while(true) {
            Bzip2BlockDecompressor blockDecompressor;
            label538: {
               int i;
               int j;
               Bzip2HuffmanStageDecoder huffmanStageDecoder;
               label539: {
                  int k;
                  label525:
                  while(true) {
                     switch (this.currentState) {
                        case INIT:
                           if (in.readableBytes() < 4) {
                              return;
                           }

                           int magicNumber = in.readUnsignedMedium();
                           if (magicNumber != 4348520) {
                              throw new DecompressionException("Unexpected stream identifier contents. Mismatched bzip2 protocol version?");
                           }

                           int blockSize = in.readByte() - 48;
                           if (blockSize < 1 || blockSize > 9) {
                              throw new DecompressionException("block size is invalid");
                           }

                           this.blockSize = blockSize * 100000;
                           this.streamCRC = 0;
                           this.currentState = Bzip2Decoder.State.INIT_BLOCK;
                        case INIT_BLOCK:
                           if (!reader.hasReadableBytes(10)) {
                              return;
                           }

                           int magic1 = reader.readBits(24);
                           int magic2 = reader.readBits(24);
                           if (magic1 == 1536581 && magic2 == 3690640) {
                              int storedCombinedCRC = reader.readInt();
                              if (storedCombinedCRC != this.streamCRC) {
                                 throw new DecompressionException("stream CRC error");
                              }

                              this.currentState = Bzip2Decoder.State.EOF;
                              break;
                           } else {
                              if (magic1 != 3227993 || magic2 != 2511705) {
                                 throw new DecompressionException("bad block header");
                              }

                              this.blockCRC = reader.readInt();
                              this.currentState = Bzip2Decoder.State.INIT_BLOCK_PARAMS;
                           }
                        case INIT_BLOCK_PARAMS:
                           if (!reader.hasReadableBits(25)) {
                              return;
                           }

                           boolean blockRandomised = reader.readBoolean();
                           int bwtStartPointer = reader.readBits(24);
                           this.blockDecompressor = new Bzip2BlockDecompressor(this.blockSize, this.blockCRC, blockRandomised, bwtStartPointer, reader);
                           this.currentState = Bzip2Decoder.State.RECEIVE_HUFFMAN_USED_MAP;
                        case RECEIVE_HUFFMAN_USED_MAP:
                           if (!reader.hasReadableBits(16)) {
                              return;
                           }

                           this.blockDecompressor.huffmanInUse16 = reader.readBits(16);
                           this.currentState = Bzip2Decoder.State.RECEIVE_HUFFMAN_USED_BITMAPS;
                        case RECEIVE_HUFFMAN_USED_BITMAPS:
                           blockDecompressor = this.blockDecompressor;
                           int inUse16 = blockDecompressor.huffmanInUse16;
                           int bitNumber = Integer.bitCount(inUse16);
                           byte[] huffmanSymbolMap = blockDecompressor.huffmanSymbolMap;
                           if (!reader.hasReadableBits(bitNumber * 16 + 3)) {
                              return;
                           }

                           int huffmanSymbolCount = 0;
                           if (bitNumber > 0) {
                              for(i = 0; i < 16; ++i) {
                                 if ((inUse16 & '耀' >>> i) != 0) {
                                    j = 0;

                                    for(k = i << 4; j < 16; ++k) {
                                       if (reader.readBoolean()) {
                                          huffmanSymbolMap[huffmanSymbolCount++] = (byte)k;
                                       }

                                       ++j;
                                    }
                                 }
                              }
                           }

                           blockDecompressor.huffmanEndOfBlockSymbol = huffmanSymbolCount + 1;
                           i = reader.readBits(3);
                           if (i < 2 || i > 6) {
                              throw new DecompressionException("incorrect huffman groups number");
                           }

                           j = huffmanSymbolCount + 2;
                           if (j > 258) {
                              throw new DecompressionException("incorrect alphabet size");
                           }

                           this.huffmanStageDecoder = new Bzip2HuffmanStageDecoder(reader, i, j);
                           this.currentState = Bzip2Decoder.State.RECEIVE_SELECTORS_NUMBER;
                        case RECEIVE_SELECTORS_NUMBER:
                           if (!reader.hasReadableBits(15)) {
                              return;
                           }

                           k = reader.readBits(15);
                           if (k < 1 || k > 18002) {
                              throw new DecompressionException("incorrect selectors number");
                           }

                           this.huffmanStageDecoder.selectors = new byte[k];
                           this.currentState = Bzip2Decoder.State.RECEIVE_SELECTORS;
                        case RECEIVE_SELECTORS:
                           break label525;
                        case RECEIVE_HUFFMAN_LENGTH:
                           break label539;
                        case DECODE_HUFFMAN_DATA:
                           break label538;
                        case EOF:
                           in.skipBytes(in.readableBytes());
                           return;
                        default:
                           throw new IllegalStateException();
                     }
                  }

                  huffmanStageDecoder = this.huffmanStageDecoder;
                  byte[] selectors = huffmanStageDecoder.selectors;
                  k = selectors.length;
                  Bzip2MoveToFrontTable tableMtf = huffmanStageDecoder.tableMTF;

                  for(int currSelector = huffmanStageDecoder.currentSelector; currSelector < k; ++currSelector) {
                     if (!reader.hasReadableBits(6)) {
                        huffmanStageDecoder.currentSelector = currSelector;
                        return;
                     }

                     int index;
                     for(index = 0; reader.readBoolean(); ++index) {
                     }

                     selectors[currSelector] = tableMtf.indexToFront(index);
                  }

                  this.currentState = Bzip2Decoder.State.RECEIVE_HUFFMAN_LENGTH;
               }

               huffmanStageDecoder = this.huffmanStageDecoder;
               i = huffmanStageDecoder.totalTables;
               byte[][] codeLength = huffmanStageDecoder.tableCodeLengths;
               j = huffmanStageDecoder.alphabetSize;
               int currLength = huffmanStageDecoder.currentLength;
               int currAlpha = 0;
               boolean modifyLength = huffmanStageDecoder.modifyLength;
               boolean saveStateAndReturn = false;

               int currGroup;
               label453:
               for(currGroup = huffmanStageDecoder.currentGroup; currGroup < i; ++currGroup) {
                  if (!reader.hasReadableBits(5)) {
                     saveStateAndReturn = true;
                     break;
                  }

                  if (currLength < 0) {
                     currLength = reader.readBits(5);
                  }

                  for(currAlpha = huffmanStageDecoder.currentAlpha; currAlpha < j; ++currAlpha) {
                     if (!reader.isReadable()) {
                        saveStateAndReturn = true;
                        break label453;
                     }

                     while(modifyLength || reader.readBoolean()) {
                        if (!reader.isReadable()) {
                           modifyLength = true;
                           saveStateAndReturn = true;
                           break label453;
                        }

                        currLength += reader.readBoolean() ? -1 : 1;
                        modifyLength = false;
                        if (!reader.isReadable()) {
                           saveStateAndReturn = true;
                           break label453;
                        }
                     }

                     codeLength[currGroup][currAlpha] = (byte)currLength;
                  }

                  currLength = -1;
                  currAlpha = huffmanStageDecoder.currentAlpha = 0;
                  modifyLength = false;
               }

               if (saveStateAndReturn) {
                  huffmanStageDecoder.currentGroup = currGroup;
                  huffmanStageDecoder.currentLength = currLength;
                  huffmanStageDecoder.currentAlpha = currAlpha;
                  huffmanStageDecoder.modifyLength = modifyLength;
                  return;
               }

               huffmanStageDecoder.createHuffmanDecodingTables();
               this.currentState = Bzip2Decoder.State.DECODE_HUFFMAN_DATA;
            }

            blockDecompressor = this.blockDecompressor;
            int oldReaderIndex = in.readerIndex();
            boolean decoded = blockDecompressor.decodeHuffmanData(this.huffmanStageDecoder);
            if (!decoded) {
               return;
            }

            if (in.readerIndex() == oldReaderIndex && in.isReadable()) {
               reader.refill();
            }

            int blockLength = blockDecompressor.blockLength();
            ByteBuf uncompressed = ctx.alloc().buffer(blockLength);
            boolean success = false;

            try {
               int uncByte;
               while((uncByte = blockDecompressor.read()) >= 0) {
                  uncompressed.writeByte(uncByte);
               }

               int currentBlockCRC = blockDecompressor.checkCRC();
               this.streamCRC = (this.streamCRC << 1 | this.streamCRC >>> 31) ^ currentBlockCRC;
               out.add(uncompressed);
               success = true;
            } finally {
               if (!success) {
                  uncompressed.release();
               }

            }

            this.currentState = Bzip2Decoder.State.INIT_BLOCK;
         }
      }
   }

   public boolean isClosed() {
      return this.currentState == Bzip2Decoder.State.EOF;
   }

   private static enum State {
      INIT,
      INIT_BLOCK,
      INIT_BLOCK_PARAMS,
      RECEIVE_HUFFMAN_USED_MAP,
      RECEIVE_HUFFMAN_USED_BITMAPS,
      RECEIVE_SELECTORS_NUMBER,
      RECEIVE_SELECTORS,
      RECEIVE_HUFFMAN_LENGTH,
      DECODE_HUFFMAN_DATA,
      EOF;
   }
}
