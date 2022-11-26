package org.python.netty.handler.codec.compression;

import java.util.List;
import java.util.zip.CRC32;
import java.util.zip.DataFormatException;
import java.util.zip.Inflater;
import org.python.netty.buffer.ByteBuf;
import org.python.netty.channel.ChannelHandlerContext;

public class JdkZlibDecoder extends ZlibDecoder {
   private static final int FHCRC = 2;
   private static final int FEXTRA = 4;
   private static final int FNAME = 8;
   private static final int FCOMMENT = 16;
   private static final int FRESERVED = 224;
   private Inflater inflater;
   private final byte[] dictionary;
   private final ByteBufChecksum crc;
   private GzipState gzipState;
   private int flags;
   private int xlen;
   private volatile boolean finished;
   private boolean decideZlibOrNone;

   public JdkZlibDecoder() {
      this(ZlibWrapper.ZLIB, (byte[])null);
   }

   public JdkZlibDecoder(byte[] dictionary) {
      this(ZlibWrapper.ZLIB, dictionary);
   }

   public JdkZlibDecoder(ZlibWrapper wrapper) {
      this(wrapper, (byte[])null);
   }

   private JdkZlibDecoder(ZlibWrapper wrapper, byte[] dictionary) {
      this.gzipState = JdkZlibDecoder.GzipState.HEADER_START;
      this.flags = -1;
      this.xlen = -1;
      if (wrapper == null) {
         throw new NullPointerException("wrapper");
      } else {
         switch (wrapper) {
            case GZIP:
               this.inflater = new Inflater(true);
               this.crc = ByteBufChecksum.wrapChecksum(new CRC32());
               break;
            case NONE:
               this.inflater = new Inflater(true);
               this.crc = null;
               break;
            case ZLIB:
               this.inflater = new Inflater();
               this.crc = null;
               break;
            case ZLIB_OR_NONE:
               this.decideZlibOrNone = true;
               this.crc = null;
               break;
            default:
               throw new IllegalArgumentException("Only GZIP or ZLIB is supported, but you used " + wrapper);
         }

         this.dictionary = dictionary;
      }
   }

   public boolean isClosed() {
      return this.finished;
   }

   protected void decode(ChannelHandlerContext ctx, ByteBuf in, List out) throws Exception {
      if (this.finished) {
         in.skipBytes(in.readableBytes());
      } else {
         int readableBytes = in.readableBytes();
         if (readableBytes != 0) {
            if (this.decideZlibOrNone) {
               if (readableBytes < 2) {
                  return;
               }

               boolean nowrap = !looksLikeZlib(in.getShort(in.readerIndex()));
               this.inflater = new Inflater(nowrap);
               this.decideZlibOrNone = false;
            }

            if (this.crc != null) {
               switch (this.gzipState) {
                  case FOOTER_START:
                     if (this.readGZIPFooter(in)) {
                        this.finished = true;
                     }

                     return;
                  default:
                     if (this.gzipState != JdkZlibDecoder.GzipState.HEADER_END && !this.readGZIPHeader(in)) {
                        return;
                     }

                     readableBytes = in.readableBytes();
               }
            }

            if (in.hasArray()) {
               this.inflater.setInput(in.array(), in.arrayOffset() + in.readerIndex(), readableBytes);
            } else {
               byte[] array = new byte[readableBytes];
               in.getBytes(in.readerIndex(), array);
               this.inflater.setInput(array);
            }

            int maxOutputLength = this.inflater.getRemaining() << 1;
            ByteBuf decompressed = ctx.alloc().heapBuffer(maxOutputLength);

            try {
               boolean readFooter = false;
               byte[] outArray = decompressed.array();

               while(true) {
                  if (!this.inflater.needsInput()) {
                     int writerIndex = decompressed.writerIndex();
                     int outIndex = decompressed.arrayOffset() + writerIndex;
                     int length = decompressed.writableBytes();
                     if (length == 0) {
                        out.add(decompressed);
                        decompressed = ctx.alloc().heapBuffer(maxOutputLength);
                        outArray = decompressed.array();
                        continue;
                     }

                     int outputLength = this.inflater.inflate(outArray, outIndex, length);
                     if (outputLength > 0) {
                        decompressed.writerIndex(writerIndex + outputLength);
                        if (this.crc != null) {
                           this.crc.update(outArray, outIndex, outputLength);
                        }
                     } else if (this.inflater.needsDictionary()) {
                        if (this.dictionary == null) {
                           throw new DecompressionException("decompression failure, unable to set dictionary as non was specified");
                        }

                        this.inflater.setDictionary(this.dictionary);
                     }

                     if (!this.inflater.finished()) {
                        continue;
                     }

                     if (this.crc == null) {
                        this.finished = true;
                     } else {
                        readFooter = true;
                     }
                  }

                  in.skipBytes(readableBytes - this.inflater.getRemaining());
                  if (!readFooter) {
                     break;
                  }

                  this.gzipState = JdkZlibDecoder.GzipState.FOOTER_START;
                  if (this.readGZIPFooter(in)) {
                     this.finished = true;
                  }
                  break;
               }
            } catch (DataFormatException var16) {
               throw new DecompressionException("decompression failure", var16);
            } finally {
               if (decompressed.isReadable()) {
                  out.add(decompressed);
               } else {
                  decompressed.release();
               }

            }

         }
      }
   }

   protected void handlerRemoved0(ChannelHandlerContext ctx) throws Exception {
      super.handlerRemoved0(ctx);
      if (this.inflater != null) {
         this.inflater.end();
      }

   }

   private boolean readGZIPHeader(ByteBuf in) {
      short b;
      switch (this.gzipState) {
         case HEADER_START:
            if (in.readableBytes() < 10) {
               return false;
            }

            int magic0 = in.readByte();
            int magic1 = in.readByte();
            if (magic0 != 31) {
               throw new DecompressionException("Input is not in the GZIP format");
            }

            this.crc.update(magic0);
            this.crc.update(magic1);
            int method = in.readUnsignedByte();
            if (method != 8) {
               throw new DecompressionException("Unsupported compression method " + method + " in the GZIP header");
            }

            this.crc.update(method);
            this.flags = in.readUnsignedByte();
            this.crc.update(this.flags);
            if ((this.flags & 224) != 0) {
               throw new DecompressionException("Reserved flags are set in the GZIP header");
            }

            this.crc.update(in, in.readerIndex(), 4);
            in.skipBytes(4);
            this.crc.update(in.readUnsignedByte());
            this.crc.update(in.readUnsignedByte());
            this.gzipState = JdkZlibDecoder.GzipState.FLG_READ;
         case FLG_READ:
            if ((this.flags & 4) != 0) {
               if (in.readableBytes() < 2) {
                  return false;
               }

               b = in.readUnsignedByte();
               int xlen2 = in.readUnsignedByte();
               this.crc.update(b);
               this.crc.update(xlen2);
               this.xlen |= b << 8 | xlen2;
            }

            this.gzipState = JdkZlibDecoder.GzipState.XLEN_READ;
         case XLEN_READ:
            if (this.xlen != -1) {
               if (in.readableBytes() < this.xlen) {
                  return false;
               }

               this.crc.update(in, in.readerIndex(), this.xlen);
               in.skipBytes(this.xlen);
            }

            this.gzipState = JdkZlibDecoder.GzipState.SKIP_FNAME;
         case SKIP_FNAME:
            if ((this.flags & 8) != 0) {
               if (!in.isReadable()) {
                  return false;
               }

               do {
                  b = in.readUnsignedByte();
                  this.crc.update(b);
               } while(b != 0 && in.isReadable());
            }

            this.gzipState = JdkZlibDecoder.GzipState.SKIP_COMMENT;
         case SKIP_COMMENT:
            if ((this.flags & 16) != 0) {
               if (!in.isReadable()) {
                  return false;
               }

               do {
                  b = in.readUnsignedByte();
                  this.crc.update(b);
               } while(b != 0 && in.isReadable());
            }

            this.gzipState = JdkZlibDecoder.GzipState.PROCESS_FHCRC;
         case PROCESS_FHCRC:
            break;
         case HEADER_END:
            return true;
         default:
            throw new IllegalStateException();
      }

      if ((this.flags & 2) != 0) {
         if (in.readableBytes() < 4) {
            return false;
         }

         this.verifyCrc(in);
      }

      this.crc.reset();
      this.gzipState = JdkZlibDecoder.GzipState.HEADER_END;
      return true;
   }

   private boolean readGZIPFooter(ByteBuf buf) {
      if (buf.readableBytes() < 8) {
         return false;
      } else {
         this.verifyCrc(buf);
         int dataLength = 0;

         int readLength;
         for(readLength = 0; readLength < 4; ++readLength) {
            dataLength |= buf.readUnsignedByte() << readLength * 8;
         }

         readLength = this.inflater.getTotalOut();
         if (dataLength != readLength) {
            throw new DecompressionException("Number of bytes mismatch. Expected: " + dataLength + ", Got: " + readLength);
         } else {
            return true;
         }
      }
   }

   private void verifyCrc(ByteBuf in) {
      long crcValue = 0L;

      for(int i = 0; i < 4; ++i) {
         crcValue |= (long)in.readUnsignedByte() << i * 8;
      }

      long readCrc = this.crc.getValue();
      if (crcValue != readCrc) {
         throw new DecompressionException("CRC value mismatch. Expected: " + crcValue + ", Got: " + readCrc);
      }
   }

   private static boolean looksLikeZlib(short cmf_flg) {
      return (cmf_flg & 30720) == 30720 && cmf_flg % 31 == 0;
   }

   private static enum GzipState {
      HEADER_START,
      HEADER_END,
      FLG_READ,
      XLEN_READ,
      SKIP_FNAME,
      SKIP_COMMENT,
      PROCESS_FHCRC,
      FOOTER_START;
   }
}
