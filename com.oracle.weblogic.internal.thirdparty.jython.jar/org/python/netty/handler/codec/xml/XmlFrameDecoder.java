package org.python.netty.handler.codec.xml;

import java.util.List;
import org.python.netty.buffer.ByteBuf;
import org.python.netty.channel.ChannelHandlerContext;
import org.python.netty.handler.codec.ByteToMessageDecoder;
import org.python.netty.handler.codec.CorruptedFrameException;
import org.python.netty.handler.codec.TooLongFrameException;

public class XmlFrameDecoder extends ByteToMessageDecoder {
   private final int maxFrameLength;

   public XmlFrameDecoder(int maxFrameLength) {
      if (maxFrameLength < 1) {
         throw new IllegalArgumentException("maxFrameLength must be a positive int");
      } else {
         this.maxFrameLength = maxFrameLength;
      }
   }

   protected void decode(ChannelHandlerContext ctx, ByteBuf in, List out) throws Exception {
      boolean openingBracketFound = false;
      boolean atLeastOneXmlElementFound = false;
      boolean inCDATASection = false;
      long openBracketsCount = 0L;
      int length = 0;
      int leadingWhiteSpaceCount = 0;
      int bufferLength = in.writerIndex();
      if (bufferLength > this.maxFrameLength) {
         in.skipBytes(in.readableBytes());
         this.fail((long)bufferLength);
      } else {
         int i;
         int readByte;
         for(i = in.readerIndex(); i < bufferLength; ++i) {
            readByte = in.getByte(i);
            if (!openingBracketFound && Character.isWhitespace(readByte)) {
               ++leadingWhiteSpaceCount;
            } else {
               if (!openingBracketFound && readByte != 60) {
                  fail(ctx);
                  in.skipBytes(in.readableBytes());
                  return;
               }

               byte peekBehindByte;
               if (!inCDATASection && readByte == 60) {
                  openingBracketFound = true;
                  if (i < bufferLength - 1) {
                     peekBehindByte = in.getByte(i + 1);
                     if (peekBehindByte == 47) {
                        for(int peekFurtherAheadIndex = i + 2; peekFurtherAheadIndex <= bufferLength - 1; ++peekFurtherAheadIndex) {
                           if (in.getByte(peekFurtherAheadIndex) == 62) {
                              --openBracketsCount;
                              break;
                           }
                        }
                     } else if (isValidStartCharForXmlElement(peekBehindByte)) {
                        atLeastOneXmlElementFound = true;
                        ++openBracketsCount;
                     } else if (peekBehindByte == 33) {
                        if (isCommentBlockStart(in, i)) {
                           ++openBracketsCount;
                        } else if (isCDATABlockStart(in, i)) {
                           ++openBracketsCount;
                           inCDATASection = true;
                        }
                     } else if (peekBehindByte == 63) {
                        ++openBracketsCount;
                     }
                  }
               } else if (!inCDATASection && readByte == 47) {
                  if (i < bufferLength - 1 && in.getByte(i + 1) == 62) {
                     --openBracketsCount;
                  }
               } else if (readByte == 62) {
                  length = i + 1;
                  if (i - 1 > -1) {
                     peekBehindByte = in.getByte(i - 1);
                     if (!inCDATASection) {
                        if (peekBehindByte == 63) {
                           --openBracketsCount;
                        } else if (peekBehindByte == 45 && i - 2 > -1 && in.getByte(i - 2) == 45) {
                           --openBracketsCount;
                        }
                     } else if (peekBehindByte == 93 && i - 2 > -1 && in.getByte(i - 2) == 93) {
                        --openBracketsCount;
                        inCDATASection = false;
                     }
                  }

                  if (atLeastOneXmlElementFound && openBracketsCount == 0L) {
                     break;
                  }
               }
            }
         }

         i = in.readerIndex();
         readByte = length - i;
         if (openBracketsCount == 0L && readByte > 0) {
            if (i + readByte >= bufferLength) {
               readByte = in.readableBytes();
            }

            ByteBuf frame = extractFrame(in, i + leadingWhiteSpaceCount, readByte - leadingWhiteSpaceCount);
            in.skipBytes(readByte);
            out.add(frame);
         }

      }
   }

   private void fail(long frameLength) {
      if (frameLength > 0L) {
         throw new TooLongFrameException("frame length exceeds " + this.maxFrameLength + ": " + frameLength + " - discarded");
      } else {
         throw new TooLongFrameException("frame length exceeds " + this.maxFrameLength + " - discarding");
      }
   }

   private static void fail(ChannelHandlerContext ctx) {
      ctx.fireExceptionCaught(new CorruptedFrameException("frame contains content before the xml starts"));
   }

   private static ByteBuf extractFrame(ByteBuf buffer, int index, int length) {
      return buffer.copy(index, length);
   }

   private static boolean isValidStartCharForXmlElement(byte b) {
      return b >= 97 && b <= 122 || b >= 65 && b <= 90 || b == 58 || b == 95;
   }

   private static boolean isCommentBlockStart(ByteBuf in, int i) {
      return i < in.writerIndex() - 3 && in.getByte(i + 2) == 45 && in.getByte(i + 3) == 45;
   }

   private static boolean isCDATABlockStart(ByteBuf in, int i) {
      return i < in.writerIndex() - 8 && in.getByte(i + 2) == 91 && in.getByte(i + 3) == 67 && in.getByte(i + 4) == 68 && in.getByte(i + 5) == 65 && in.getByte(i + 6) == 84 && in.getByte(i + 7) == 65 && in.getByte(i + 8) == 91;
   }
}
