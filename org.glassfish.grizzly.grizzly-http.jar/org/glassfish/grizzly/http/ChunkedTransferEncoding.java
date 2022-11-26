package org.glassfish.grizzly.http;

import org.glassfish.grizzly.Buffer;
import org.glassfish.grizzly.filterchain.FilterChainContext;
import org.glassfish.grizzly.http.util.Ascii;
import org.glassfish.grizzly.http.util.HexUtils;
import org.glassfish.grizzly.http.util.HttpCodecUtils;
import org.glassfish.grizzly.http.util.MimeHeaders;
import org.glassfish.grizzly.memory.Buffers;
import org.glassfish.grizzly.memory.CompositeBuffer;
import org.glassfish.grizzly.memory.MemoryManager;
import org.glassfish.grizzly.memory.CompositeBuffer.DisposeOrder;
import org.glassfish.grizzly.utils.Charsets;

public final class ChunkedTransferEncoding implements TransferEncoding {
   private static final int MAX_HTTP_CHUNK_SIZE_LENGTH = 16;
   private static final long CHUNK_SIZE_OVERFLOW = 576460752303423487L;
   private static final int CHUNK_LENGTH_PARSED_STATE = 3;
   private static final byte[] LAST_CHUNK_CRLF_BYTES;
   private static final int[] DEC;
   private final int maxHeadersSize;

   public ChunkedTransferEncoding(int maxHeadersSize) {
      this.maxHeadersSize = maxHeadersSize;
   }

   public boolean wantDecode(HttpHeader httpPacket) {
      return httpPacket.isChunked();
   }

   public boolean wantEncode(HttpHeader httpPacket) {
      return httpPacket.isChunked();
   }

   public void prepareSerialize(FilterChainContext ctx, HttpHeader httpHeader, HttpContent content) {
      httpHeader.makeTransferEncodingHeader("chunked");
   }

   public ParsingResult parsePacket(FilterChainContext ctx, HttpHeader httpPacket, Buffer buffer) {
      HttpPacketParsing httpPacketParsing = (HttpPacketParsing)httpPacket;
      HttpCodecFilter.ContentParsingState contentParsingState = httpPacketParsing.getContentParsingState();
      boolean isLastChunk = contentParsingState.isLastChunk;
      if (!isLastChunk && contentParsingState.chunkRemainder <= 0L) {
         buffer = parseTrailerCRLF(httpPacketParsing, buffer);
         if (buffer == null) {
            return ParsingResult.create((HttpContent)null, (Buffer)null);
         }

         if (!parseHttpChunkLength(httpPacketParsing, buffer)) {
            if (isHeadRequest(httpPacket)) {
               return ParsingResult.create(httpPacket.httpTrailerBuilder().headers(contentParsingState.trailerHeaders).build(), (Buffer)null);
            }

            return ParsingResult.create((HttpContent)null, buffer, false);
         }
      } else {
         contentParsingState.chunkContentStart = 0;
      }

      int chunkContentStart = contentParsingState.chunkContentStart;
      if (contentParsingState.chunkLength == 0L) {
         if (!isLastChunk) {
            contentParsingState.isLastChunk = true;
            isLastChunk = true;
            this.initTrailerParsing(httpPacketParsing);
         }

         if (!this.parseLastChunkTrailer(ctx, httpPacket, httpPacketParsing, buffer)) {
            return ParsingResult.create((HttpContent)null, buffer);
         }

         chunkContentStart = httpPacketParsing.getHeaderParsingState().offset;
      }

      long thisPacketRemaining = contentParsingState.chunkRemainder;
      int contentAvailable = buffer.limit() - chunkContentStart;
      Buffer remainder = null;
      if ((long)contentAvailable > thisPacketRemaining) {
         remainder = buffer.split((int)((long)chunkContentStart + thisPacketRemaining));
         buffer.position(chunkContentStart);
      } else if (chunkContentStart > 0) {
         buffer.position(chunkContentStart);
      }

      if (isLastChunk) {
         return ParsingResult.create(httpPacket.httpTrailerBuilder().headers(contentParsingState.trailerHeaders).build(), remainder);
      } else {
         buffer.shrink();
         if (buffer.hasRemaining()) {
            contentParsingState.chunkRemainder -= (long)buffer.remaining();
         } else {
            buffer.tryDispose();
            buffer = Buffers.EMPTY_BUFFER;
         }

         return ParsingResult.create(httpPacket.httpContentBuilder().content(buffer).build(), remainder);
      }
   }

   public Buffer serializePacket(FilterChainContext ctx, HttpContent httpContent) {
      return encodeHttpChunk(ctx.getMemoryManager(), httpContent, httpContent.isLast());
   }

   private void initTrailerParsing(HttpPacketParsing httpPacket) {
      HttpCodecFilter.HeaderParsingState headerParsingState = httpPacket.getHeaderParsingState();
      HttpCodecFilter.ContentParsingState contentParsingState = httpPacket.getContentParsingState();
      headerParsingState.subState = 0;
      int start = contentParsingState.chunkContentStart;
      headerParsingState.start = start;
      headerParsingState.offset = start;
      headerParsingState.packetLimit = start + this.maxHeadersSize;
   }

   private boolean parseLastChunkTrailer(FilterChainContext ctx, HttpHeader httpHeader, HttpPacketParsing httpPacket, Buffer input) {
      HttpCodecFilter.HeaderParsingState headerParsingState = httpPacket.getHeaderParsingState();
      HttpCodecFilter.ContentParsingState contentParsingState = httpPacket.getContentParsingState();
      HttpCodecFilter filter = headerParsingState.codecFilter;
      boolean result = filter.parseHeadersFromBuffer(httpHeader, contentParsingState.trailerHeaders, headerParsingState, input);
      if (result) {
         if (contentParsingState.trailerHeaders.size() > 0) {
            filter.onHttpHeadersParsed(httpHeader, contentParsingState.trailerHeaders, ctx);
         }
      } else {
         headerParsingState.checkOverflow(input.limit(), "The chunked encoding trailer header is too large");
      }

      return result;
   }

   private static boolean parseHttpChunkLength(HttpPacketParsing httpPacket, Buffer input) {
      HttpCodecFilter.HeaderParsingState parsingState = httpPacket.getHeaderParsingState();

      int offset;
      label56:
      while(true) {
         switch (parsingState.state) {
            case 0:
               offset = input.position();
               parsingState.start = offset;
               parsingState.offset = offset;
               parsingState.packetLimit = offset + 16;
            case 1:
               offset = HttpCodecUtils.skipSpaces(input, parsingState.offset, parsingState.packetLimit);
               if (offset == -1) {
                  parsingState.offset = input.limit();
                  parsingState.state = 1;
                  parsingState.checkOverflow(input.limit(), "The chunked encoding length prefix is too large");
                  return false;
               }

               parsingState.offset = offset;
               parsingState.state = 2;
            case 2:
               break label56;
         }
      }

      offset = parsingState.offset;
      int limit = Math.min(parsingState.packetLimit, input.limit());

      long value;
      for(value = parsingState.parsingNumericValue; offset < limit; ++offset) {
         byte b = input.get(offset);
         if (!HttpCodecUtils.isSpaceOrTab(b) && b != 13 && b != 59) {
            if (b == 10) {
               HttpCodecFilter.ContentParsingState contentParsingState = httpPacket.getContentParsingState();
               contentParsingState.chunkContentStart = offset + 1;
               contentParsingState.chunkLength = value;
               contentParsingState.chunkRemainder = value;
               parsingState.state = 3;
               return true;
            }

            if (parsingState.checkpoint != -1) {
               throw new HttpBrokenContentException("Unexpected HTTP chunk header");
            }

            if (DEC[b & 255] == -1 || !checkOverflow(value)) {
               throw new HttpBrokenContentException("Invalid byte representing a hex value within a chunk length encountered : " + b);
            }

            value = (value << 4) + (long)DEC[b & 255];
         } else {
            parsingState.checkpoint = offset;
         }
      }

      parsingState.parsingNumericValue = value;
      parsingState.offset = limit;
      parsingState.checkOverflow(limit, "The chunked encoding length prefix is too large");
      return false;
   }

   private static boolean checkOverflow(long value) {
      return value <= 576460752303423487L;
   }

   private static boolean isHeadRequest(HttpHeader header) {
      HttpRequestPacket request = header.isRequest() ? (HttpRequestPacket)header : ((HttpResponsePacket)header).getRequest();
      return request.isHeadRequest();
   }

   private static Buffer parseTrailerCRLF(HttpPacketParsing httpPacket, Buffer input) {
      HttpCodecFilter.HeaderParsingState parsingState = httpPacket.getHeaderParsingState();
      if (parsingState.state == 3) {
         do {
            if (!input.hasRemaining()) {
               return null;
            }
         } while(input.get() != 10);

         parsingState.recycle();
         if (input.hasRemaining()) {
            return input.slice();
         } else {
            return null;
         }
      } else {
         return input;
      }
   }

   private static Buffer encodeHttpChunk(MemoryManager memoryManager, HttpContent httpContent, boolean isLastChunk) {
      Buffer content = httpContent.getContent();
      Buffer httpChunkBuffer = memoryManager.allocate(16);
      int chunkSize = content.remaining();
      Ascii.intToHexString(httpChunkBuffer, chunkSize);
      httpChunkBuffer = HttpCodecUtils.put(memoryManager, httpChunkBuffer, HttpCodecFilter.CRLF_BYTES);
      httpChunkBuffer.trim();
      httpChunkBuffer.allowBufferDispose(true);
      boolean hasContent = chunkSize > 0;
      if (hasContent) {
         httpChunkBuffer = Buffers.appendBuffers(memoryManager, httpChunkBuffer, content);
         if (httpChunkBuffer.isComposite()) {
            httpChunkBuffer.allowBufferDispose(true);
            ((CompositeBuffer)httpChunkBuffer).allowInternalBuffersDispose(true);
            ((CompositeBuffer)httpChunkBuffer).disposeOrder(DisposeOrder.FIRST_TO_LAST);
         }
      }

      Buffer httpChunkTrailer;
      if (!isLastChunk) {
         httpChunkTrailer = memoryManager.allocate(2);
      } else {
         boolean isTrailer = HttpTrailer.isTrailer(httpContent) && ((HttpTrailer)httpContent).getHeaders().size() > 0;
         if (!isTrailer) {
            httpChunkTrailer = memoryManager.allocate(8);
         } else {
            httpChunkTrailer = memoryManager.allocate(256);
         }

         if (hasContent) {
            httpChunkTrailer = HttpCodecUtils.put(memoryManager, httpChunkTrailer, HttpCodecFilter.CRLF_BYTES);
            httpChunkTrailer = HttpCodecUtils.put(memoryManager, httpChunkTrailer, LAST_CHUNK_CRLF_BYTES);
         }

         if (isTrailer) {
            HttpTrailer httpTrailer = (HttpTrailer)httpContent;
            MimeHeaders mimeHeaders = httpTrailer.getHeaders();
            httpChunkTrailer = HttpCodecFilter.encodeMimeHeaders(memoryManager, httpChunkTrailer, mimeHeaders, httpContent.getHttpHeader().getTempHeaderEncodingBuffer());
         }
      }

      httpChunkTrailer = HttpCodecUtils.put(memoryManager, httpChunkTrailer, HttpCodecFilter.CRLF_BYTES);
      httpChunkTrailer.trim();
      httpChunkTrailer.allowBufferDispose(true);
      return Buffers.appendBuffers(memoryManager, httpChunkBuffer, httpChunkTrailer);
   }

   static {
      LAST_CHUNK_CRLF_BYTES = "0\r\n".getBytes(Charsets.ASCII_CHARSET);
      DEC = HexUtils.getDecBytes();
   }
}
