package org.glassfish.grizzly.http.util;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import org.glassfish.grizzly.Buffer;
import org.glassfish.grizzly.Connection;
import org.glassfish.grizzly.http.HttpCodecFilter;
import org.glassfish.grizzly.http.HttpRequestPacket;
import org.glassfish.grizzly.memory.MemoryManager;

public class HttpCodecUtils {
   static final byte[] EMPTY_ARRAY = new byte[0];
   private static final int[] DEC = HexUtils.getDecBytes();

   public static void parseHost(DataChunk hostDC, DataChunk serverNameDC, HttpRequestPacket request) {
      if (hostDC == null) {
         Connection connection = request.getConnection();
         request.setServerPort(((InetSocketAddress)connection.getLocalAddress()).getPort());
         InetAddress localAddress = ((InetSocketAddress)connection.getLocalAddress()).getAddress();
         request.setLocalHost(localAddress.getHostName());
         serverNameDC.setString(localAddress.getHostName());
      } else {
         int valueS;
         int valueL;
         int colonPos;
         boolean ipv6;
         boolean bracketClosed;
         int port;
         int mult;
         int i;
         int charValue;
         if (hostDC.getType() == DataChunk.Type.Bytes) {
            ByteChunk valueBC = hostDC.getByteChunk();
            valueS = valueBC.getStart();
            valueL = valueBC.getEnd() - valueS;
            colonPos = -1;
            byte[] valueB = valueBC.getBuffer();
            ipv6 = valueB[valueS] == 91;
            bracketClosed = false;

            for(port = 0; port < valueL; ++port) {
               mult = valueB[port + valueS];
               if (mult == 93) {
                  bracketClosed = true;
               } else if (mult == 58 && (!ipv6 || bracketClosed)) {
                  colonPos = port;
                  break;
               }
            }

            if (colonPos < 0) {
               if (!request.isSecure()) {
                  request.setServerPort(80);
               } else {
                  request.setServerPort(443);
               }

               serverNameDC.setBytes(valueB, valueS, valueS + valueL);
            } else {
               serverNameDC.setBytes(valueB, valueS, valueS + colonPos);
               port = 0;
               mult = 1;

               for(i = valueL - 1; i > colonPos; --i) {
                  charValue = DEC[valueB[i + valueS]];
                  if (charValue == -1) {
                     throw new IllegalStateException(String.format("Host header %s contained a non-decimal value in the port definition.", hostDC.toString()));
                  }

                  port += charValue * mult;
                  mult = 10 * mult;
               }

               request.setServerPort(port);
            }
         } else {
            BufferChunk valueBC = hostDC.getBufferChunk();
            valueS = valueBC.getStart();
            valueL = valueBC.getEnd() - valueS;
            colonPos = -1;
            Buffer valueB = valueBC.getBuffer();
            ipv6 = valueB.get(valueS) == 91;
            bracketClosed = false;

            for(port = 0; port < valueL; ++port) {
               byte b = valueB.get(port + valueS);
               if (b == 93) {
                  bracketClosed = true;
               } else if (b == 58 && (!ipv6 || bracketClosed)) {
                  colonPos = port;
                  break;
               }
            }

            if (colonPos < 0) {
               if (!request.isSecure()) {
                  request.setServerPort(80);
               } else {
                  request.setServerPort(443);
               }

               serverNameDC.setBuffer(valueB, valueS, valueS + valueL);
            } else {
               serverNameDC.setBuffer(valueB, valueS, valueS + colonPos);
               port = 0;
               mult = 1;

               for(i = valueL - 1; i > colonPos; --i) {
                  charValue = DEC[valueB.get(i + valueS)];
                  if (charValue == -1) {
                     throw new IllegalStateException(String.format("Host header %s contained a non-decimal value in the port definition.", hostDC.toString()));
                  }

                  port += charValue * mult;
                  mult = 10 * mult;
               }

               request.setServerPort(port);
            }
         }

      }
   }

   public static int checkEOL(HttpCodecFilter.HeaderParsingState parsingState, Buffer input) {
      int offset = parsingState.offset;
      int avail = input.limit() - offset;
      byte b1;
      byte b2;
      if (avail >= 2) {
         short s = input.getShort(offset);
         b1 = (byte)(s >>> 8);
         b2 = (byte)(s & 255);
      } else {
         if (avail != 1) {
            return -2;
         }

         b1 = input.get(offset);
         b2 = -1;
      }

      return checkCRLF(parsingState, b1, b2);
   }

   public static int checkEOL(HttpCodecFilter.HeaderParsingState parsingState, byte[] input, int end) {
      int arrayOffs = parsingState.arrayOffset;
      int offset = arrayOffs + parsingState.offset;
      int avail = Math.min(parsingState.packetLimit + arrayOffs, end) - offset;
      byte b1;
      byte b2;
      if (avail >= 2) {
         b1 = input[offset];
         b2 = input[offset + 1];
      } else {
         if (avail != 1) {
            return -2;
         }

         b1 = input[offset];
         b2 = -1;
      }

      return checkCRLF(parsingState, b1, b2);
   }

   public static boolean findEOL(HttpCodecFilter.HeaderParsingState state, Buffer input) {
      int offset = state.offset;

      for(int limit = Math.min(input.limit(), state.packetLimit); offset < limit; ++offset) {
         byte b = input.get(offset);
         if (b == 13) {
            state.checkpoint = offset;
         } else if (b == 10) {
            if (state.checkpoint == -1) {
               state.checkpoint = offset;
            }

            state.offset = offset + 1;
            return true;
         }
      }

      state.offset = offset;
      return false;
   }

   public static boolean findEOL(HttpCodecFilter.HeaderParsingState state, byte[] input, int end) {
      int arrayOffs = state.arrayOffset;
      int offset = arrayOffs + state.offset;

      for(int limit = Math.min(end, arrayOffs + state.packetLimit); offset < limit; ++offset) {
         byte b = input[offset];
         if (b == 13) {
            state.checkpoint = offset - arrayOffs;
         } else if (b == 10) {
            if (state.checkpoint == -1) {
               state.checkpoint = offset - arrayOffs;
            }

            state.offset = offset + 1 - arrayOffs;
            return true;
         }
      }

      state.offset = offset - arrayOffs;
      return false;
   }

   public static int findSpace(Buffer input, int offset, int packetLimit) {
      for(int limit = Math.min(input.limit(), packetLimit); offset < limit; ++offset) {
         byte b = input.get(offset);
         if (isSpaceOrTab(b)) {
            return offset;
         }
      }

      return -1;
   }

   public static int findSpace(byte[] input, int offset, int end, int packetLimit) {
      for(int limit = Math.min(end, packetLimit); offset < limit; ++offset) {
         byte b = input[offset];
         if (isSpaceOrTab(b)) {
            return offset;
         }
      }

      return -1;
   }

   public static int skipSpaces(Buffer input, int offset, int packetLimit) {
      for(int limit = Math.min(input.limit(), packetLimit); offset < limit; ++offset) {
         byte b = input.get(offset);
         if (isNotSpaceAndTab(b)) {
            return offset;
         }
      }

      return -1;
   }

   public static int skipSpaces(byte[] input, int offset, int end, int packetLimit) {
      for(int limit = Math.min(end, packetLimit); offset < limit; ++offset) {
         byte b = input[offset];
         if (isNotSpaceAndTab(b)) {
            return offset;
         }
      }

      return -1;
   }

   public static int indexOf(Buffer input, int offset, byte b, int packetLimit) {
      for(int limit = Math.min(input.limit(), packetLimit); offset < limit; ++offset) {
         byte currentByte = input.get(offset);
         if (currentByte == b) {
            return offset;
         }
      }

      return -1;
   }

   public static Buffer getLongAsBuffer(MemoryManager memoryManager, long length) {
      Buffer b = memoryManager.allocate(20);
      b.allowBufferDispose(true);
      HttpUtils.longToBuffer(length, b);
      return b;
   }

   public static Buffer put(MemoryManager memoryManager, Buffer dstBuffer, byte[] tempBuffer, DataChunk chunk) {
      if (chunk.isNull()) {
         return dstBuffer;
      } else if (chunk.getType() == DataChunk.Type.Bytes) {
         ByteChunk byteChunk = chunk.getByteChunk();
         return put(memoryManager, dstBuffer, byteChunk.getBuffer(), byteChunk.getStart(), byteChunk.getLength());
      } else if (chunk.getType() == DataChunk.Type.Buffer) {
         BufferChunk bc = chunk.getBufferChunk();
         int length = bc.getLength();
         dstBuffer = checkAndResizeIfNeeded(memoryManager, dstBuffer, length);
         dstBuffer.put(bc.getBuffer(), bc.getStart(), length);
         return dstBuffer;
      } else {
         return put(memoryManager, dstBuffer, tempBuffer, chunk.toString());
      }
   }

   public static Buffer put(MemoryManager memoryManager, Buffer dstBuffer, byte[] tempBuffer, String s) {
      int size = s.length();
      dstBuffer = checkAndResizeIfNeeded(memoryManager, dstBuffer, size);
      if (dstBuffer.hasArray()) {
         byte[] array = dstBuffer.array();
         int arrayOffs = dstBuffer.arrayOffset();
         int pos = arrayOffs + dstBuffer.position();

         for(int i = 0; i < size; ++i) {
            byte b = (byte)s.charAt(i);
            array[pos++] = isNonPrintableUsAscii(b) ? 32 : b;
         }

         dstBuffer.position(pos - arrayOffs);
      } else {
         fastAsciiEncode(s, tempBuffer, dstBuffer);
      }

      return dstBuffer;
   }

   public static Buffer put(MemoryManager memoryManager, Buffer dstBuffer, byte[] array) {
      return put(memoryManager, dstBuffer, array, 0, array.length);
   }

   public static Buffer put(MemoryManager memoryManager, Buffer dstBuffer, byte[] array, int off, int len) {
      dstBuffer = checkAndResizeIfNeeded(memoryManager, dstBuffer, len);
      dstBuffer.put(array, off, len);
      return dstBuffer;
   }

   public static Buffer put(MemoryManager memoryManager, Buffer dstBuffer, Buffer buffer) {
      int addSize = buffer.remaining();
      dstBuffer = checkAndResizeIfNeeded(memoryManager, dstBuffer, addSize);
      dstBuffer.put(buffer);
      return dstBuffer;
   }

   public static Buffer put(MemoryManager memoryManager, Buffer dstBuffer, byte value) {
      if (!dstBuffer.hasRemaining()) {
         dstBuffer = resizeBuffer(memoryManager, dstBuffer, 1);
      }

      dstBuffer.put(value);
      return dstBuffer;
   }

   public static Buffer resizeBuffer(MemoryManager memoryManager, Buffer buffer, int grow) {
      return memoryManager.reallocate(buffer, Math.max(buffer.capacity() + grow, buffer.capacity() * 3 / 2 + 1));
   }

   public static boolean isNotSpaceAndTab(byte b) {
      return b != 32 && b != 9;
   }

   public static boolean isSpaceOrTab(byte b) {
      return b == 32 || b == 9;
   }

   public static byte[] toCheckedByteArray(CharSequence s) {
      byte[] array = new byte[s.length()];
      return toCheckedByteArray(s, array, 0);
   }

   public static byte[] toCheckedByteArray(CharSequence s, byte[] dstArray, int arrayOffs) {
      if (dstArray == null) {
         throw new NullPointerException();
      } else {
         int strLen = s.length();
         if (arrayOffs + strLen > dstArray.length) {
            throw new IllegalArgumentException("Not enough space in the array");
         } else {
            for(int i = 0; i < strLen; ++i) {
               int c = s.charAt(i);
               dstArray[i + arrayOffs] = isNonPrintableUsAscii(c) ? 32 : (byte)c;
            }

            return dstArray;
         }
      }
   }

   public static boolean isNonPrintableUsAscii(int ub) {
      return ub <= 31 && ub != 9 || ub >= 127;
   }

   private static void fastAsciiEncode(String s, byte[] tempBuffer, Buffer dstBuffer) {
      int totalLen = s.length();
      if (tempBuffer == null) {
         tempBuffer = new byte[totalLen];
      }

      int count = 0;

      while(count < totalLen) {
         int len = Math.min(totalLen - count, tempBuffer.length);

         for(int i = 0; i < len; ++i) {
            int c = s.charAt(count);
            tempBuffer[i] = isNonPrintableUsAscii(c) ? 32 : (byte)c;
            ++count;
         }

         dstBuffer.put(tempBuffer, 0, len);
      }

   }

   private static int checkCRLF(HttpCodecFilter.HeaderParsingState parsingState, byte b1, byte b2) {
      if (b1 == 13) {
         if (b2 == 10) {
            parsingState.offset += 2;
            return 0;
         }

         if (b2 == -1) {
            return -2;
         }
      } else if (b1 == 10) {
         ++parsingState.offset;
         return 0;
      }

      return -1;
   }

   private static Buffer checkAndResizeIfNeeded(MemoryManager memoryManager, Buffer dstBuffer, int length) {
      if (dstBuffer.remaining() < length) {
         dstBuffer = resizeBuffer(memoryManager, dstBuffer, length);
      }

      return dstBuffer;
   }
}
