package org.glassfish.grizzly.http;

import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.glassfish.grizzly.Buffer;
import org.glassfish.grizzly.Connection;
import org.glassfish.grizzly.Grizzly;
import org.glassfish.grizzly.filterchain.FilterChainContext;
import org.glassfish.grizzly.filterchain.NextAction;
import org.glassfish.grizzly.http.util.Ascii;
import org.glassfish.grizzly.http.util.BufferChunk;
import org.glassfish.grizzly.http.util.ByteChunk;
import org.glassfish.grizzly.http.util.CacheableDataChunk;
import org.glassfish.grizzly.http.util.DataChunk;
import org.glassfish.grizzly.http.util.Header;
import org.glassfish.grizzly.http.util.HttpCodecUtils;
import org.glassfish.grizzly.http.util.MimeHeaders;
import org.glassfish.grizzly.memory.Buffers;
import org.glassfish.grizzly.memory.CompositeBuffer;
import org.glassfish.grizzly.memory.MemoryManager;
import org.glassfish.grizzly.memory.CompositeBuffer.DisposeOrder;
import org.glassfish.grizzly.monitoring.DefaultMonitoringConfig;
import org.glassfish.grizzly.monitoring.MonitoringAware;
import org.glassfish.grizzly.monitoring.MonitoringConfig;
import org.glassfish.grizzly.monitoring.MonitoringUtils;
import org.glassfish.grizzly.ssl.SSLUtils;
import org.glassfish.grizzly.utils.ArraySet;
import org.glassfish.grizzly.utils.Charsets;

public abstract class HttpCodecFilter extends HttpBaseFilter implements MonitoringAware {
   public static final int DEFAULT_MAX_HTTP_PACKET_HEADER_SIZE = 8192;
   private static final Logger LOGGER = Grizzly.logger(HttpCodecFilter.class);
   private static final byte[] CHUNKED_ENCODING_BYTES;
   static final byte[] COLON_BYTES;
   static final byte[] CRLF_BYTES;
   protected static final byte[] CLOSE_BYTES;
   protected static final byte[] KEEPALIVE_BYTES;
   private final ArraySet transferEncodings = new ArraySet(TransferEncoding.class);
   protected final ArraySet contentEncodings = new ArraySet(ContentEncoding.class);
   protected final boolean chunkingEnabled;
   protected long maxPayloadRemainderToSkip = -1L;
   private boolean removeHandledContentEncodingHeaders = false;
   protected final DefaultMonitoringConfig monitoringConfig = new DefaultMonitoringConfig(HttpProbe.class) {
      public Object createManagementObject() {
         return HttpCodecFilter.this.createJmxManagementObject();
      }
   };
   protected final int maxHeadersSize;
   protected boolean preserveHeaderCase = Boolean.parseBoolean(System.getProperty("org.glassfish.grizzly.http.PRESERVE_HEADER_CASE", "false"));

   abstract boolean decodeInitialLineFromBuffer(FilterChainContext var1, HttpPacketParsing var2, HeaderParsingState var3, Buffer var4);

   abstract boolean decodeInitialLineFromBytes(FilterChainContext var1, HttpPacketParsing var2, HeaderParsingState var3, byte[] var4, int var5);

   abstract Buffer encodeInitialLine(HttpPacket var1, Buffer var2, MemoryManager var3);

   protected abstract boolean onHttpPacketParsed(HttpHeader var1, FilterChainContext var2);

   protected abstract boolean onHttpHeaderParsed(HttpHeader var1, Buffer var2, FilterChainContext var3);

   protected abstract void onInitialLineParsed(HttpHeader var1, FilterChainContext var2);

   protected abstract void onInitialLineEncoded(HttpHeader var1, FilterChainContext var2);

   protected abstract void onHttpHeadersParsed(HttpHeader var1, MimeHeaders var2, FilterChainContext var3);

   protected abstract void onHttpHeadersEncoded(HttpHeader var1, FilterChainContext var2);

   protected abstract void onHttpContentParsed(HttpContent var1, FilterChainContext var2);

   protected abstract void onHttpContentEncoded(HttpContent var1, FilterChainContext var2);

   protected abstract void onHttpHeaderError(HttpHeader var1, FilterChainContext var2, Throwable var3) throws IOException;

   protected abstract void onHttpContentError(HttpHeader var1, FilterChainContext var2, Throwable var3) throws IOException;

   public HttpCodecFilter(boolean chunkingEnabled, int maxHeadersSize) {
      this.maxHeadersSize = maxHeadersSize;
      this.chunkingEnabled = chunkingEnabled;
      this.transferEncodings.addAll(new TransferEncoding[]{new FixedLengthTransferEncoding(), new ChunkedTransferEncoding(maxHeadersSize)});
   }

   public long getMaxPayloadRemainderToSkip() {
      return this.maxPayloadRemainderToSkip;
   }

   public void setMaxPayloadRemainderToSkip(long maxPayloadRemainderToSkip) {
      this.maxPayloadRemainderToSkip = maxPayloadRemainderToSkip;
   }

   public boolean isPreserveHeaderCase() {
      return this.preserveHeaderCase;
   }

   public void setPreserveHeaderCase(boolean preserveHeaderCase) {
      this.preserveHeaderCase = preserveHeaderCase;
   }

   public TransferEncoding[] getTransferEncodings() {
      return (TransferEncoding[])this.transferEncodings.obtainArrayCopy();
   }

   public void addTransferEncoding(TransferEncoding transferEncoding) {
      this.transferEncodings.add(transferEncoding);
   }

   public boolean removeTransferEncoding(TransferEncoding transferEncoding) {
      return this.transferEncodings.remove(transferEncoding);
   }

   public ContentEncoding[] getContentEncodings() {
      return (ContentEncoding[])this.contentEncodings.obtainArrayCopy();
   }

   public void addContentEncoding(ContentEncoding contentEncoding) {
      this.contentEncodings.add(contentEncoding);
   }

   public boolean removeContentEncoding(ContentEncoding contentEncoding) {
      return this.contentEncodings.remove(contentEncoding);
   }

   protected boolean isChunkingEnabled() {
      return this.chunkingEnabled;
   }

   public boolean isRemoveHandledContentEncodingHeaders() {
      return this.removeHandledContentEncodingHeaders;
   }

   public void setRemoveHandledContentEncodingHeaders(boolean removeHandledContentEncodingHeaders) {
      this.removeHandledContentEncodingHeaders = removeHandledContentEncodingHeaders;
   }

   public final NextAction handleRead(FilterChainContext ctx, HttpHeader httpHeader) throws IOException {
      Buffer input = (Buffer)ctx.getMessage();
      Connection connection = ctx.getConnection();
      HttpProbeNotifier.notifyDataReceived(this, connection, input);
      HttpPacketParsing parsingState = httpHeader.getParsingState();
      boolean wasHeaderParsed = parsingState == null || parsingState.isHeaderParsed();
      if (!wasHeaderParsed) {
         try {
            assert parsingState != null;

            if (!this.decodeHttpPacket(ctx, parsingState, input)) {
               return ctx.getStopAction(input);
            }

            int headerSizeInBytes = input.position();
            if (httpHeader.isUpgrade()) {
               this.onIncomingUpgrade(ctx, httpHeader);
            }

            if (this.onHttpHeaderParsed(httpHeader, input, ctx)) {
               throw new IllegalStateException("Bad HTTP headers");
            }

            parsingState.setHeaderParsed(true);
            parsingState.getHeaderParsingState().recycle();
            Buffer remainder = input.hasRemaining() ? input.split(input.position()) : Buffers.EMPTY_BUFFER;
            httpHeader.setHeaderBuffer(input);
            input = remainder;
            if (httpHeader.isExpectContent()) {
               this.setTransferEncodingOnParsing(httpHeader);
               this.setContentEncodingsOnParsing(httpHeader);
            }

            HttpProbeNotifier.notifyHeaderParse(this, connection, httpHeader, headerSizeInBytes);
         } catch (Exception var10) {
            LOGGER.log(Level.FINE, "Error parsing HTTP header", var10);
            HttpProbeNotifier.notifyProbesError(this, connection, httpHeader, var10);
            this.onHttpHeaderError(httpHeader, ctx, var10);
            NextAction suspendAction = ctx.getSuspendAction();
            ctx.completeAndRecycle();
            return suspendAction;
         }
      }

      HttpContent emptyContent;
      if (!httpHeader.isExpectContent()) {
         this.onHttpPacketParsed(httpHeader, ctx);
         emptyContent = HttpContent.create(httpHeader, true);
         HttpProbeNotifier.notifyContentChunkParse(this, connection, emptyContent);
         ctx.setMessage(emptyContent);
         return input.remaining() > 0 ? ctx.getInvokeAction(input) : ctx.getInvokeAction();
      } else if (httpHeader.isIgnoreContentModifiers()) {
         emptyContent = HttpContent.create(httpHeader);
         emptyContent.setContent(input);
         ctx.setMessage(emptyContent);
         return ctx.getInvokeAction();
      } else {
         try {
            TransferEncoding transferEncoding = httpHeader.getTransferEncoding();
            if (transferEncoding != null) {
               return this.decodeWithTransferEncoding(ctx, httpHeader, input, wasHeaderParsed);
            }

            if (input.hasRemaining()) {
               HttpContent message = HttpContent.create(httpHeader);
               message.setContent(input);
               HttpContent decodedContent = this.decodeContent(ctx, message);
               if (decodedContent != null) {
                  if (httpHeader.isSkipRemainder()) {
                     if (!this.checkRemainderOverflow(httpHeader, decodedContent.getContent().remaining())) {
                        httpHeader.getProcessingState().getHttpContext().close();
                     }

                     return ctx.getStopAction();
                  }

                  HttpProbeNotifier.notifyContentChunkParse(this, connection, decodedContent);
                  ctx.setMessage(decodedContent);
                  return ctx.getInvokeAction();
               }
            }
         } catch (Exception var11) {
            LOGGER.log(Level.FINE, "Error parsing HTTP payload", var11);
            httpHeader.getProcessingState().setError(true);
            HttpProbeNotifier.notifyProbesError(this, connection, httpHeader, var11);
            this.onHttpContentError(httpHeader, ctx, var11);
            this.onHttpPacketParsed(httpHeader, ctx);
            HttpContent brokenContent = HttpBrokenContent.builder(httpHeader).error(var11).build();
            ctx.setMessage(brokenContent);
            return ctx.getInvokeAction();
         }

         if (!wasHeaderParsed) {
            emptyContent = HttpContent.create(httpHeader);
            HttpProbeNotifier.notifyContentChunkParse(this, connection, emptyContent);
            ctx.setMessage(emptyContent);
            return ctx.getInvokeAction();
         } else {
            return ctx.getStopAction();
         }
      }
   }

   protected boolean decodeHttpPacket(FilterChainContext ctx, HttpPacketParsing httpPacket, Buffer input) {
      return input.hasArray() ? this.decodeHttpPacketFromBytes(ctx, httpPacket, input) : this.decodeHttpPacketFromBuffer(ctx, httpPacket, input);
   }

   protected boolean decodeHttpPacketFromBytes(FilterChainContext ctx, HttpPacketParsing httpPacket, Buffer inputBuffer) {
      HeaderParsingState parsingState = httpPacket.getHeaderParsingState();
      parsingState.arrayOffset = inputBuffer.arrayOffset();
      int end = parsingState.arrayOffset + inputBuffer.limit();
      byte[] input = inputBuffer.array();
      switch (parsingState.state) {
         case 0:
            if (!this.decodeInitialLineFromBytes(ctx, httpPacket, parsingState, input, end)) {
               parsingState.checkOverflow(inputBuffer.limit(), "HTTP packet intial line is too large");
               return false;
            } else {
               ++parsingState.state;
            }
         case 1:
            if (!this.parseHeadersFromBytes((HttpHeader)httpPacket, httpPacket.getHeaders(), parsingState, input, end)) {
               parsingState.checkOverflow(inputBuffer.limit(), "HTTP packet header is too large");
               return false;
            } else {
               ++parsingState.state;
            }
         case 2:
            this.onHttpHeadersParsed((HttpHeader)httpPacket, httpPacket.getHeaders(), ctx);
            if (httpPacket.getHeaders().size() == 0) {
               ((HttpHeader)httpPacket).setExpectContent(false);
            }

            inputBuffer.position(parsingState.offset);
            return true;
         default:
            throw new IllegalStateException();
      }
   }

   protected boolean parseHeadersFromBytes(HttpHeader httpHeader, MimeHeaders mimeHeaders, HeaderParsingState parsingState, byte[] input, int end) {
      do {
         if (parsingState.subState == 0) {
            int eol = HttpCodecUtils.checkEOL(parsingState, input, end);
            if (eol == 0) {
               return true;
            }

            if (eol == -2) {
               return false;
            }
         }
      } while(this.parseHeaderFromBytes(httpHeader, mimeHeaders, parsingState, input, end));

      return false;
   }

   protected boolean parseHeaderFromBytes(HttpHeader httpHeader, MimeHeaders mimeHeaders, HeaderParsingState parsingState, byte[] input, int end) {
      int arrayOffs = parsingState.arrayOffset;
      int packetLim = arrayOffs + parsingState.packetLimit;

      while(true) {
         int subState = parsingState.subState;
         int nonSpaceIdx;
         switch (subState) {
            case 0:
               parsingState.start = parsingState.offset;
               ++parsingState.subState;
            case 1:
               if (!this.parseHeaderName(httpHeader, mimeHeaders, parsingState, input, end)) {
                  return false;
               }

               ++parsingState.subState;
               parsingState.start = -1;
            case 2:
               nonSpaceIdx = HttpCodecUtils.skipSpaces(input, arrayOffs + parsingState.offset, end, packetLim) - arrayOffs;
               if (nonSpaceIdx < 0) {
                  parsingState.offset = end - arrayOffs;
                  return false;
               }

               ++parsingState.subState;
               parsingState.offset = nonSpaceIdx;
               if (parsingState.start == -1) {
                  parsingState.start = nonSpaceIdx;
                  parsingState.checkpoint = nonSpaceIdx;
                  parsingState.checkpoint2 = nonSpaceIdx;
               }
            case 3:
               nonSpaceIdx = parseHeaderValue(httpHeader, parsingState, input, end);
               if (nonSpaceIdx == -1) {
                  return false;
               }

               if (nonSpaceIdx != -2) {
                  parsingState.subState = 0;
                  parsingState.start = -1;
                  return true;
               }

               parsingState.subState = 2;
               break;
            default:
               throw new IllegalStateException();
         }
      }
   }

   protected boolean parseHeaderName(HttpHeader httpHeader, MimeHeaders mimeHeaders, HeaderParsingState parsingState, byte[] input, int end) {
      int arrayOffs = parsingState.arrayOffset;
      int limit = Math.min(end, arrayOffs + parsingState.packetLimit);
      int start = arrayOffs + parsingState.start;

      int offset;
      for(offset = arrayOffs + parsingState.offset; offset < limit; ++offset) {
         byte b = input[offset];
         if (b == 58) {
            parsingState.headerValueStorage = mimeHeaders.addValue(input, start, offset - start);
            parsingState.offset = offset + 1 - arrayOffs;
            finalizeKnownHeaderNames(httpHeader, parsingState, input, start, offset);
            return true;
         }

         if (b >= 65 && b <= 90) {
            if (!this.preserveHeaderCase) {
               b = (byte)(b + 32);
            }

            input[offset] = b;
         }
      }

      parsingState.offset = offset - arrayOffs;
      return false;
   }

   protected static int parseHeaderValue(HttpHeader httpHeader, HeaderParsingState parsingState, byte[] input, int end) {
      int arrayOffs = parsingState.arrayOffset;
      int limit = Math.min(end, arrayOffs + parsingState.packetLimit);
      int offset = arrayOffs + parsingState.offset;

      for(boolean hasShift = offset != arrayOffs + parsingState.checkpoint; offset < limit; ++offset) {
         byte b = input[offset];
         if (b != 13) {
            if (b == 10) {
               if (offset + 1 < limit) {
                  byte b2 = input[offset + 1];
                  if (b2 != 32 && b2 != 9) {
                     parsingState.offset = offset + 1 - arrayOffs;
                     finalizeKnownHeaderValues(httpHeader, parsingState, input, arrayOffs + parsingState.start, arrayOffs + parsingState.checkpoint2);
                     parsingState.headerValueStorage.setBytes(input, arrayOffs + parsingState.start, arrayOffs + parsingState.checkpoint2);
                     return 0;
                  }

                  input[arrayOffs + parsingState.checkpoint++] = b2;
                  parsingState.offset = offset + 2 - arrayOffs;
                  return -2;
               }

               parsingState.offset = offset - arrayOffs;
               return -1;
            }

            if (b == 32) {
               if (hasShift) {
                  input[arrayOffs + parsingState.checkpoint++] = b;
               } else {
                  ++parsingState.checkpoint;
               }
            } else {
               if (hasShift) {
                  input[arrayOffs + parsingState.checkpoint++] = b;
               } else {
                  ++parsingState.checkpoint;
               }

               parsingState.checkpoint2 = parsingState.checkpoint;
            }
         }
      }

      parsingState.offset = offset - arrayOffs;
      return -1;
   }

   private static void finalizeKnownHeaderNames(HttpHeader httpHeader, HeaderParsingState parsingState, byte[] input, int start, int end) {
      int size = end - start;
      if (size == Header.ContentLength.getLowerCaseBytes().length) {
         if (ByteChunk.equalsIgnoreCaseLowerCase(input, start, end, Header.ContentLength.getLowerCaseBytes())) {
            parsingState.isContentLengthHeader = true;
         }
      } else if (size == Header.TransferEncoding.getLowerCaseBytes().length) {
         if (ByteChunk.equalsIgnoreCaseLowerCase(input, start, end, Header.TransferEncoding.getLowerCaseBytes())) {
            parsingState.isTransferEncodingHeader = true;
         }
      } else if (size == Header.Upgrade.getLowerCaseBytes().length) {
         if (ByteChunk.equalsIgnoreCaseLowerCase(input, start, end, Header.Upgrade.getLowerCaseBytes())) {
            parsingState.isUpgradeHeader = true;
         }
      } else if (size == Header.Expect.getLowerCaseBytes().length && ByteChunk.equalsIgnoreCaseLowerCase(input, start, end, Header.Expect.getLowerCaseBytes())) {
         ((HttpRequestPacket)httpHeader).requiresAcknowledgement(true);
      }

   }

   private static void finalizeKnownHeaderValues(HttpHeader httpHeader, HeaderParsingState parsingState, byte[] input, int start, int end) {
      if (parsingState.isContentLengthHeader) {
         if (httpHeader.isChunked()) {
            parsingState.isContentLengthHeader = false;
            return;
         }

         long contentLengthLong = Ascii.parseLong(input, start, end - start);
         if (parsingState.contentLengthHeadersCount++ == 0) {
            httpHeader.setContentLengthLong(contentLengthLong);
         } else if (httpHeader.getContentLength() != contentLengthLong) {
            parsingState.contentLengthsDiffer = true;
         }

         parsingState.isContentLengthHeader = false;
      } else if (parsingState.isTransferEncodingHeader) {
         if (end - start >= CHUNKED_ENCODING_BYTES.length && ByteChunk.equalsIgnoreCaseLowerCase(input, start, start + CHUNKED_ENCODING_BYTES.length, CHUNKED_ENCODING_BYTES)) {
            httpHeader.setContentLengthLong(-1L);
            httpHeader.setChunked(true);
         }

         parsingState.isTransferEncodingHeader = false;
      } else if (parsingState.isUpgradeHeader) {
         httpHeader.getUpgradeDC().setBytes(input, start, end);
         parsingState.isUpgradeHeader = false;
      }

   }

   protected boolean decodeHttpPacketFromBuffer(FilterChainContext ctx, HttpPacketParsing httpPacket, Buffer input) {
      HeaderParsingState parsingState = httpPacket.getHeaderParsingState();
      switch (parsingState.state) {
         case 0:
            if (!this.decodeInitialLineFromBuffer(ctx, httpPacket, parsingState, input)) {
               parsingState.checkOverflow(input.limit(), "HTTP packet intial line is too large");
               return false;
            } else {
               ++parsingState.state;
            }
         case 1:
            if (!this.parseHeadersFromBuffer((HttpHeader)httpPacket, httpPacket.getHeaders(), parsingState, input)) {
               parsingState.checkOverflow(input.limit(), "HTTP packet header is too large");
               return false;
            } else {
               ++parsingState.state;
            }
         case 2:
            this.onHttpHeadersParsed((HttpHeader)httpPacket, httpPacket.getHeaders(), ctx);
            if (httpPacket.getHeaders().size() == 0) {
               ((HttpHeader)httpPacket).setExpectContent(false);
            }

            input.position(parsingState.offset);
            return true;
         default:
            throw new IllegalStateException();
      }
   }

   protected boolean parseHeadersFromBuffer(HttpHeader httpHeader, MimeHeaders mimeHeaders, HeaderParsingState parsingState, Buffer input) {
      do {
         if (parsingState.subState == 0) {
            int eol = HttpCodecUtils.checkEOL(parsingState, input);
            if (eol == 0) {
               return true;
            }

            if (eol == -2) {
               return false;
            }
         }
      } while(this.parseHeaderFromBuffer(httpHeader, mimeHeaders, parsingState, input));

      return false;
   }

   protected boolean parseHeaderFromBuffer(HttpHeader httpHeader, MimeHeaders mimeHeaders, HeaderParsingState parsingState, Buffer input) {
      while(true) {
         int subState = parsingState.subState;
         int nonSpaceIdx;
         switch (subState) {
            case 0:
               parsingState.start = parsingState.offset;
               ++parsingState.subState;
            case 1:
               if (!this.parseHeaderName(httpHeader, mimeHeaders, parsingState, input)) {
                  return false;
               }

               ++parsingState.subState;
               parsingState.start = -1;
            case 2:
               nonSpaceIdx = HttpCodecUtils.skipSpaces(input, parsingState.offset, parsingState.packetLimit);
               if (nonSpaceIdx == -1) {
                  parsingState.offset = input.limit();
                  return false;
               }

               ++parsingState.subState;
               parsingState.offset = nonSpaceIdx;
               if (parsingState.start == -1) {
                  parsingState.start = nonSpaceIdx;
                  parsingState.checkpoint = nonSpaceIdx;
                  parsingState.checkpoint2 = nonSpaceIdx;
               }
            case 3:
               nonSpaceIdx = parseHeaderValue(httpHeader, parsingState, input);
               if (nonSpaceIdx == -1) {
                  return false;
               }

               if (nonSpaceIdx == -2) {
                  parsingState.subState = 2;
                  break;
               }

               parsingState.subState = 0;
               parsingState.start = -1;
               return true;
            default:
               throw new IllegalStateException();
         }
      }
   }

   protected boolean parseHeaderName(HttpHeader httpHeader, MimeHeaders mimeHeaders, HeaderParsingState parsingState, Buffer input) {
      int limit = Math.min(input.limit(), parsingState.packetLimit);
      int start = parsingState.start;

      int offset;
      for(offset = parsingState.offset; offset < limit; ++offset) {
         byte b = input.get(offset);
         if (b == 58) {
            parsingState.headerValueStorage = mimeHeaders.addValue(input, start, offset - start);
            parsingState.offset = offset + 1;
            finalizeKnownHeaderNames(httpHeader, parsingState, input, start, offset);
            return true;
         }

         if (b >= 65 && b <= 90) {
            if (!this.preserveHeaderCase) {
               b = (byte)(b + 32);
            }

            input.put(offset, b);
         }
      }

      parsingState.offset = offset;
      return false;
   }

   protected static int parseHeaderValue(HttpHeader httpHeader, HeaderParsingState parsingState, Buffer input) {
      int limit = Math.min(input.limit(), parsingState.packetLimit);
      int offset = parsingState.offset;

      for(boolean hasShift = offset != parsingState.checkpoint; offset < limit; ++offset) {
         byte b = input.get(offset);
         if (b != 13) {
            if (b == 10) {
               if (offset + 1 < limit) {
                  byte b2 = input.get(offset + 1);
                  if (b2 != 32 && b2 != 9) {
                     parsingState.offset = offset + 1;
                     finalizeKnownHeaderValues(httpHeader, parsingState, input, parsingState.start, parsingState.checkpoint2);
                     parsingState.headerValueStorage.setBuffer(input, parsingState.start, parsingState.checkpoint2);
                     return 0;
                  }

                  input.put(parsingState.checkpoint++, b2);
                  parsingState.offset = offset + 2;
                  return -2;
               }

               parsingState.offset = offset;
               return -1;
            }

            if (b == 32) {
               if (hasShift) {
                  input.put(parsingState.checkpoint++, b);
               } else {
                  ++parsingState.checkpoint;
               }
            } else {
               if (hasShift) {
                  input.put(parsingState.checkpoint++, b);
               } else {
                  ++parsingState.checkpoint;
               }

               parsingState.checkpoint2 = parsingState.checkpoint;
            }
         }
      }

      parsingState.offset = offset;
      return -1;
   }

   private static void finalizeKnownHeaderNames(HttpHeader httpHeader, HeaderParsingState parsingState, Buffer input, int start, int end) {
      int size = end - start;
      if (size == Header.ContentLength.getLowerCaseBytes().length) {
         if (BufferChunk.equalsIgnoreCaseLowerCase(input, start, end, Header.ContentLength.getLowerCaseBytes())) {
            parsingState.isContentLengthHeader = true;
         }
      } else if (size == Header.TransferEncoding.getLowerCaseBytes().length) {
         if (BufferChunk.equalsIgnoreCaseLowerCase(input, start, end, Header.TransferEncoding.getLowerCaseBytes())) {
            parsingState.isTransferEncodingHeader = true;
         }
      } else if (size == Header.Upgrade.getLowerCaseBytes().length) {
         if (BufferChunk.equalsIgnoreCaseLowerCase(input, start, end, Header.Upgrade.getLowerCaseBytes())) {
            parsingState.isUpgradeHeader = true;
         }
      } else if (size == Header.Expect.getLowerCaseBytes().length && BufferChunk.equalsIgnoreCaseLowerCase(input, start, end, Header.Expect.getLowerCaseBytes())) {
         ((HttpRequestPacket)httpHeader).requiresAcknowledgement(true);
      }

   }

   private static void finalizeKnownHeaderValues(HttpHeader httpHeader, HeaderParsingState parsingState, Buffer input, int start, int end) {
      if (parsingState.isContentLengthHeader) {
         if (httpHeader.isChunked()) {
            parsingState.isContentLengthHeader = false;
            return;
         }

         long contentLengthLong = Ascii.parseLong(input, start, end - start);
         if (parsingState.contentLengthHeadersCount++ == 0) {
            httpHeader.setContentLengthLong(contentLengthLong);
         } else if (httpHeader.getContentLength() != contentLengthLong) {
            parsingState.contentLengthsDiffer = true;
         }

         parsingState.isContentLengthHeader = false;
      } else if (parsingState.isTransferEncodingHeader) {
         if (BufferChunk.startsWith(input, start, end, CHUNKED_ENCODING_BYTES)) {
            httpHeader.setContentLengthLong(-1L);
            httpHeader.setChunked(true);
         }

         parsingState.isTransferEncodingHeader = false;
      } else if (parsingState.isUpgradeHeader) {
         httpHeader.getUpgradeDC().setBuffer(input, start, end);
         parsingState.isUpgradeHeader = false;
      }

   }

   private NextAction decodeWithTransferEncoding(FilterChainContext ctx, HttpHeader httpHeader, Buffer input, boolean wasHeaderParsed) throws IOException {
      Connection connection = ctx.getConnection();
      ParsingResult result = this.parseWithTransferEncoding(ctx, httpHeader, input);
      HttpContent httpContent = result.getHttpContent();
      Buffer remainderBuffer = result.getRemainderBuffer();
      boolean sendHeaderUpstream = result.isSendHeaderUpstream();
      boolean hasRemainder = remainderBuffer != null && remainderBuffer.hasRemaining();
      result.recycle();
      boolean isLast = !httpHeader.isExpectContent();
      HttpContent decodedContent;
      if (httpContent != null) {
         if (httpContent.isLast()) {
            isLast = true;
            httpHeader.setExpectContent(false);
         }

         if (httpHeader.isSkipRemainder()) {
            if (isLast) {
               this.onHttpPacketParsed(httpHeader, ctx);
               if (!httpHeader.getProcessingState().isStayAlive()) {
                  httpHeader.getProcessingState().getHttpContext().close();
                  return ctx.getStopAction();
               }

               if (remainderBuffer != null) {
                  ctx.setMessage(remainderBuffer);
                  return ctx.getRerunFilterAction();
               }

               return ctx.getStopAction();
            }

            if (!this.checkRemainderOverflow(httpHeader, httpContent.getContent().remaining())) {
               httpHeader.getProcessingState().getHttpContext().close();
            } else if (remainderBuffer != null) {
               ctx.setMessage(remainderBuffer);
               return ctx.getRerunFilterAction();
            }

            return ctx.getStopAction();
         }

         decodedContent = this.decodeContent(ctx, httpContent);
         if (isLast) {
            this.onHttpPacketParsed(httpHeader, ctx);
         }

         if (decodedContent != null) {
            HttpProbeNotifier.notifyContentChunkParse(this, connection, decodedContent);
            ctx.setMessage(decodedContent);
            return ctx.getInvokeAction(hasRemainder ? remainderBuffer : null);
         }

         if (hasRemainder) {
            HttpContent emptyContent = HttpContent.create(httpHeader, isLast);
            HttpProbeNotifier.notifyContentChunkParse(this, connection, emptyContent);
            ctx.setMessage(emptyContent);
            return ctx.getInvokeAction(remainderBuffer);
         }
      }

      if (wasHeaderParsed && !isLast) {
         return ctx.getStopAction(hasRemainder ? remainderBuffer : null);
      } else if (sendHeaderUpstream) {
         decodedContent = HttpContent.create(httpHeader, isLast);
         HttpProbeNotifier.notifyContentChunkParse(this, connection, decodedContent);
         ctx.setMessage(decodedContent);
         return ctx.getInvokeAction(hasRemainder ? remainderBuffer : null);
      } else {
         return ctx.getStopAction(hasRemainder ? remainderBuffer : null);
      }
   }

   final HttpContent decodeContent(FilterChainContext ctx, HttpContent httpContent) {
      if (httpContent.getContent().hasRemaining() && !this.isResponseToHeadRequest(httpContent.getHttpHeader())) {
         Connection connection = ctx.getConnection();
         MemoryManager memoryManager = connection.getMemoryManager();
         HttpHeader httpHeader = httpContent.getHttpHeader();
         ContentParsingState parsingState = ((HttpPacketParsing)httpHeader).getContentParsingState();
         List encodings = httpHeader.getContentEncodings(true);
         int encodingsNum = encodings.size();

         for(int i = 0; i < encodingsNum; ++i) {
            ContentEncoding encoding = (ContentEncoding)encodings.get(i);
            HttpProbeNotifier.notifyContentEncodingParse(this, connection, httpHeader, httpContent.getContent(), encoding);
            Buffer oldRemainder = parsingState.removeContentDecodingRemainder(i);
            if (oldRemainder != null) {
               Buffer newChunk = httpContent.getContent();
               httpContent.setContent(Buffers.appendBuffers(memoryManager, oldRemainder, newChunk));
            }

            ParsingResult result = encoding.decode(connection, httpContent);
            Buffer newRemainder = result.getRemainderBuffer();
            if (newRemainder != null) {
               parsingState.setContentDecodingRemainder(i, newRemainder);
            }

            HttpContent decodedContent = result.getHttpContent();
            result.recycle();
            if (decodedContent == null) {
               httpContent.recycle();
               return null;
            }

            HttpProbeNotifier.notifyContentEncodingParseResult(this, connection, httpHeader, decodedContent.getContent(), encoding);
            httpContent = decodedContent;
         }

         this.onHttpContentParsed(httpContent, ctx);
         return httpContent;
      } else if (httpContent.isLast()) {
         return httpContent;
      } else {
         httpContent.recycle();
         return null;
      }
   }

   public NextAction handleWrite(FilterChainContext ctx) throws IOException {
      Object message = ctx.getMessage();
      if (HttpPacket.isHttp(message)) {
         HttpPacket input = (HttpPacket)ctx.getMessage();
         Connection connection = ctx.getConnection();

         try {
            Buffer output = this.encodeHttpPacket(ctx, input);
            if (output != null) {
               HttpProbeNotifier.notifyDataSent(this, connection, output);
               ctx.setMessage(output);
               return ctx.getInvokeAction();
            } else {
               return ctx.getStopAction();
            }
         } catch (RuntimeException var6) {
            HttpProbeNotifier.notifyProbesError(this, connection, input, var6);
            throw var6;
         }
      } else {
         return ctx.getInvokeAction();
      }
   }

   protected void onIncomingUpgrade(FilterChainContext ctx, HttpHeader httpHeader) {
      httpHeader.setIgnoreContentModifiers(true);
      ctx.notifyUpstream(HttpEvents.createIncomingUpgradeEvent(httpHeader));
   }

   protected void onOutgoingUpgrade(FilterChainContext ctx, HttpHeader httpHeader) {
      httpHeader.setIgnoreContentModifiers(true);
      ctx.notifyUpstream(HttpEvents.createOutgoingUpgradeEvent(httpHeader));
   }

   protected Buffer encodeHttpPacket(FilterChainContext ctx, HttpPacket input) {
      boolean isHeader = input.isHeader();
      HttpContent httpContent;
      HttpHeader httpHeader;
      if (isHeader) {
         httpContent = null;
         httpHeader = (HttpHeader)input;
      } else {
         httpContent = (HttpContent)input;
         httpHeader = httpContent.getHttpHeader();
      }

      return this.encodeHttpPacket(ctx, httpHeader, httpContent, false);
   }

   protected final Buffer encodeHttpPacket(FilterChainContext ctx, HttpHeader httpHeader, HttpContent httpContent, boolean isContentAlreadyEncoded) {
      Connection connection = ctx.getConnection();
      MemoryManager memoryManager = ctx.getMemoryManager();
      Buffer encodedBuffer = null;
      if (!httpHeader.isCommitted()) {
         if (httpHeader.isUpgrade()) {
            this.onOutgoingUpgrade(ctx, httpHeader);
         }

         if (!httpHeader.isRequest()) {
            HttpResponsePacket response = (HttpResponsePacket)httpHeader;
            if (response.isAcknowledgement()) {
               encodedBuffer = memoryManager.allocate(128);
               encodedBuffer = this.encodeInitialLine(httpHeader, encodedBuffer, memoryManager);
               encodedBuffer = HttpCodecUtils.put(memoryManager, encodedBuffer, CRLF_BYTES);
               encodedBuffer = HttpCodecUtils.put(memoryManager, encodedBuffer, CRLF_BYTES);
               this.onInitialLineEncoded(httpHeader, ctx);
               encodedBuffer.trim();
               encodedBuffer.allowBufferDispose(true);
               HttpProbeNotifier.notifyHeaderSerialize(this, connection, httpHeader, encodedBuffer);
               response.acknowledged();
               return encodedBuffer;
            }
         }

         if (httpHeader.isExpectContent()) {
            this.setContentEncodingsOnSerializing(httpHeader);
            this.setTransferEncodingOnSerializing(ctx, httpHeader, httpContent);
         }

         encodedBuffer = memoryManager.allocateAtLeast(2048);
         encodedBuffer = this.encodeInitialLine(httpHeader, encodedBuffer, memoryManager);
         encodedBuffer = HttpCodecUtils.put(memoryManager, encodedBuffer, CRLF_BYTES);
         this.onInitialLineEncoded(httpHeader, ctx);
         encodedBuffer = encodeKnownHeaders(memoryManager, encodedBuffer, httpHeader);
         MimeHeaders mimeHeaders = httpHeader.getHeaders();
         byte[] tempEncodingBuffer = httpHeader.getTempHeaderEncodingBuffer();
         encodedBuffer = encodeMimeHeaders(memoryManager, encodedBuffer, mimeHeaders, tempEncodingBuffer);
         this.onHttpHeadersEncoded(httpHeader, ctx);
         encodedBuffer = HttpCodecUtils.put(memoryManager, encodedBuffer, CRLF_BYTES);
         encodedBuffer.trim();
         encodedBuffer.allowBufferDispose(true);
         httpHeader.setCommitted(true);
         HttpProbeNotifier.notifyHeaderSerialize(this, connection, httpHeader, encodedBuffer);
      }

      if (httpContent != null && httpHeader.isExpectContent()) {
         HttpProbeNotifier.notifyContentChunkSerialize(this, connection, httpContent);
         HttpContent encodedHttpContent = isContentAlreadyEncoded ? httpContent : this.encodeContent(connection, httpContent);
         if (encodedHttpContent == null) {
            return encodedBuffer;
         }

         TransferEncoding contentEncoder = httpHeader.getTransferEncoding();
         Buffer content = this.serializeWithTransferEncoding(ctx, encodedHttpContent, contentEncoder);
         this.onHttpContentEncoded(encodedHttpContent, ctx);
         if (content != null && content.hasRemaining()) {
            encodedBuffer = Buffers.appendBuffers(memoryManager, encodedBuffer, content);
         }

         if (encodedBuffer != null && encodedBuffer.isComposite()) {
            encodedBuffer.allowBufferDispose(true);
            ((CompositeBuffer)encodedBuffer).disposeOrder(DisposeOrder.FIRST_TO_LAST);
         }
      }

      return encodedBuffer;
   }

   protected static Buffer encodeKnownHeaders(MemoryManager memoryManager, Buffer buffer, HttpHeader httpHeader) {
      CacheableDataChunk name = CacheableDataChunk.create();
      CacheableDataChunk value = CacheableDataChunk.create();
      List packetContentEncodings = httpHeader.getContentEncodings(true);
      boolean hasContentEncodings = !packetContentEncodings.isEmpty();
      if (hasContentEncodings) {
         buffer = encodeContentEncodingHeader(memoryManager, buffer, httpHeader, name, value);
      }

      name.recycle();
      value.recycle();
      httpHeader.makeUpgradeHeader();
      return buffer;
   }

   private static Buffer encodeContentEncodingHeader(MemoryManager memoryManager, Buffer buffer, HttpHeader httpHeader, CacheableDataChunk name, CacheableDataChunk value) {
      List packetContentEncodings = httpHeader.getContentEncodings(true);
      name.setBytes(Header.ContentEncoding.toByteArray());
      value.reset();
      httpHeader.extractContentEncoding(value);
      boolean needComma = !value.isNull();
      byte[] tempBuffer = httpHeader.getTempHeaderEncodingBuffer();
      buffer = encodeMimeHeader(memoryManager, buffer, name, value, tempBuffer, false);

      for(Iterator var8 = packetContentEncodings.iterator(); var8.hasNext(); needComma = true) {
         ContentEncoding encoding = (ContentEncoding)var8.next();
         if (needComma) {
            buffer = HttpCodecUtils.put(memoryManager, buffer, (byte)44);
         }

         buffer = HttpCodecUtils.put(memoryManager, buffer, tempBuffer, encoding.getName());
      }

      buffer = HttpCodecUtils.put(memoryManager, buffer, CRLF_BYTES);
      return buffer;
   }

   protected static Buffer encodeMimeHeaders(MemoryManager memoryManager, Buffer buffer, MimeHeaders mimeHeaders, byte[] tempEncodingBuffer) {
      int mimeHeadersNum = mimeHeaders.size();

      for(int i = 0; i < mimeHeadersNum; ++i) {
         if (!mimeHeaders.setSerialized(i, true)) {
            DataChunk value = mimeHeaders.getValue(i);
            if (!value.isNull()) {
               buffer = encodeMimeHeader(memoryManager, buffer, mimeHeaders.getName(i), value, tempEncodingBuffer, true);
            }
         }
      }

      return buffer;
   }

   protected static Buffer encodeMimeHeader(MemoryManager memoryManager, Buffer buffer, DataChunk name, DataChunk value, byte[] tempBuffer, boolean encodeLastCRLF) {
      buffer = HttpCodecUtils.put(memoryManager, buffer, tempBuffer, name);
      buffer = HttpCodecUtils.put(memoryManager, buffer, COLON_BYTES);
      buffer = HttpCodecUtils.put(memoryManager, buffer, tempBuffer, value);
      if (encodeLastCRLF) {
         buffer = HttpCodecUtils.put(memoryManager, buffer, CRLF_BYTES);
      }

      return buffer;
   }

   final void setTransferEncodingOnParsing(HttpHeader httpHeader) {
      if (!httpHeader.isIgnoreContentModifiers()) {
         TransferEncoding[] encodings = (TransferEncoding[])this.transferEncodings.getArray();
         if (encodings != null) {
            TransferEncoding[] var3 = encodings;
            int var4 = encodings.length;

            for(int var5 = 0; var5 < var4; ++var5) {
               TransferEncoding encoding = var3[var5];
               if (encoding.wantDecode(httpHeader)) {
                  httpHeader.setTransferEncoding(encoding);
                  return;
               }
            }

         }
      }
   }

   final void setTransferEncodingOnSerializing(FilterChainContext ctx, HttpHeader httpHeader, HttpContent httpContent) {
      if (!httpHeader.isIgnoreContentModifiers()) {
         TransferEncoding[] encodings = (TransferEncoding[])this.transferEncodings.getArray();
         if (encodings != null) {
            TransferEncoding[] var5 = encodings;
            int var6 = encodings.length;

            for(int var7 = 0; var7 < var6; ++var7) {
               TransferEncoding encoding = var5[var7];
               if (encoding.wantEncode(httpHeader)) {
                  encoding.prepareSerialize(ctx, httpHeader, httpContent);
                  httpHeader.setTransferEncoding(encoding);
                  return;
               }
            }

         }
      }
   }

   final HttpContent encodeContent(Connection connection, HttpContent httpContent) {
      HttpHeader httpHeader = httpContent.getHttpHeader();
      List encodings = httpHeader.getContentEncodings(true);
      int i = 0;

      for(int len = encodings.size(); i < len; ++i) {
         ContentEncoding encoding = (ContentEncoding)encodings.get(i);
         HttpProbeNotifier.notifyContentEncodingSerialize(this, connection, httpHeader, httpContent.getContent(), encoding);
         HttpContent encodedContent = encoding.encode(connection, httpContent);
         if (encodedContent == null) {
            httpContent.recycle();
            return null;
         }

         HttpProbeNotifier.notifyContentEncodingSerializeResult(this, connection, httpHeader, encodedContent.getContent(), encoding);
         httpContent = encodedContent;
      }

      return httpContent;
   }

   final void setContentEncodingsOnParsing(HttpHeader httpHeader) {
      if (!httpHeader.isIgnoreContentModifiers()) {
         DataChunk bc = httpHeader.getHeaders().getValue(Header.ContentEncoding);
         if (bc != null) {
            List encodings = httpHeader.getContentEncodings(true);
            int currentIdx = 0;
            int endMatchDecoded = -1;

            int commaIdx;
            do {
               commaIdx = bc.indexOf(',', currentIdx);
               int endMatch = commaIdx >= 0 ? commaIdx : bc.getLength();
               ContentEncoding ce = this.lookupContentEncoding(bc, currentIdx, endMatch);
               if (ce == null || !ce.wantDecode(httpHeader)) {
                  break;
               }

               endMatchDecoded = endMatch;
               encodings.add(0, ce);
               currentIdx = commaIdx + 1;
            } while(commaIdx >= 0);

            if (this.removeHandledContentEncodingHeaders) {
               if (endMatchDecoded < bc.getLength()) {
                  ++endMatchDecoded;
               }

               bc.setStart(bc.getStart() + endMatchDecoded);
               if (bc.getLength() != 0) {
                  bc.trimLeft();
               }

               if (bc.getLength() == 0) {
                  httpHeader.getHeaders().removeHeader(Header.ContentEncoding);
               }
            }
         }

      }
   }

   final void setContentEncodingsOnSerializing(HttpHeader httpHeader) {
      if (!httpHeader.isIgnoreContentModifiers()) {
         if (!httpHeader.isContentEncodingsSelected()) {
            httpHeader.setContentEncodingsSelected(true);
            ContentEncoding[] encodingsLibrary = (ContentEncoding[])this.contentEncodings.getArray();
            if (encodingsLibrary != null) {
               DataChunk bc = httpHeader.getHeaders().getValue(Header.ContentEncoding);
               boolean isSomeEncodingApplied = bc != null && bc.getLength() > 0;
               if (isSomeEncodingApplied && bc.equals("identity")) {
                  httpHeader.getHeaders().removeHeader(Header.ContentEncoding);
               } else {
                  List httpPacketEncoders = httpHeader.getContentEncodings(true);
                  ContentEncoding[] var6 = encodingsLibrary;
                  int var7 = encodingsLibrary.length;

                  for(int var8 = 0; var8 < var7; ++var8) {
                     ContentEncoding encoding = var6[var8];
                     if ((!isSomeEncodingApplied || !lookupAlias(encoding, bc, 0)) && encoding.wantEncode(httpHeader)) {
                        httpPacketEncoders.add(encoding);
                     }
                  }

               }
            }
         }
      }
   }

   private ContentEncoding lookupContentEncoding(DataChunk bc, int startIdx, int endIdx) {
      ContentEncoding[] encodings = (ContentEncoding[])this.contentEncodings.getArray();
      if (encodings != null) {
         ContentEncoding[] var5 = encodings;
         int var6 = encodings.length;

         for(int var7 = 0; var7 < var6; ++var7) {
            ContentEncoding encoding = var5[var7];
            if (lookupAlias(encoding, bc, startIdx)) {
               return encoding;
            }
         }
      }

      return null;
   }

   private ParsingResult parseWithTransferEncoding(FilterChainContext ctx, HttpHeader httpHeader, Buffer input) {
      TransferEncoding encoding = httpHeader.getTransferEncoding();
      HttpProbeNotifier.notifyTransferEncodingParse(this, ctx.getConnection(), httpHeader, input, encoding);
      return encoding.parsePacket(ctx, httpHeader, input);
   }

   private Buffer serializeWithTransferEncoding(FilterChainContext ctx, HttpContent httpContent, TransferEncoding encoding) {
      if (encoding != null) {
         HttpProbeNotifier.notifyTransferEncodingSerialize(this, ctx.getConnection(), httpContent.getHttpHeader(), httpContent.getContent(), encoding);
         return encoding.serializePacket(ctx, httpContent);
      } else {
         return httpContent.getContent();
      }
   }

   private static boolean lookupAlias(ContentEncoding encoding, DataChunk aliasBuffer, int startIdx) {
      String[] aliases = encoding.getAliases();
      String[] var4 = aliases;
      int var5 = aliases.length;

      for(int var6 = 0; var6 < var5; ++var6) {
         String alias = var4[var6];
         int aliasLen = alias.length();

         for(int i = 0; i < aliasLen; ++i) {
            if (aliasBuffer.startsWithIgnoreCase(alias, startIdx)) {
               return true;
            }
         }
      }

      return false;
   }

   protected static boolean isSecure(Connection connection) {
      return SSLUtils.getSSLEngine(connection) != null;
   }

   protected static boolean statusDropsConnection(int status) {
      return status == 400 || status == 408 || status == 411 || status == 413 || status == 414 || status == 417 || status == 500 || status == 503 || status == 501 || status == 505;
   }

   private boolean checkRemainderOverflow(HttpHeader httpHeader, int payloadChunkSize) {
      if (this.maxPayloadRemainderToSkip < 0L) {
         return true;
      } else {
         ContentParsingState parsingState = ((HttpPacketParsing)httpHeader).getContentParsingState();
         long newSize = parsingState.remainderBytesRead += (long)payloadChunkSize;
         return newSize <= this.maxPayloadRemainderToSkip;
      }
   }

   public MonitoringConfig getMonitoringConfig() {
      return this.monitoringConfig;
   }

   protected Object createJmxManagementObject() {
      return MonitoringUtils.loadJmxObject("org.glassfish.grizzly.http.jmx.HttpCodecFilter", this, HttpCodecFilter.class);
   }

   private boolean isResponseToHeadRequest(HttpHeader header) {
      if (header.isRequest()) {
         return false;
      } else {
         HttpRequestPacket request = ((HttpResponsePacket)header).getRequest();
         return request.isHeadRequest();
      }
   }

   static {
      CHUNKED_ENCODING_BYTES = "chunked".getBytes(Charsets.ASCII_CHARSET);
      COLON_BYTES = new byte[]{58, 32};
      CRLF_BYTES = new byte[]{13, 10};
      CLOSE_BYTES = new byte[]{99, 108, 111, 115, 101};
      KEEPALIVE_BYTES = new byte[]{107, 101, 101, 112, 45, 97, 108, 105, 118, 101};
   }

   public static final class ContentParsingState {
      public boolean isLastChunk;
      public int chunkContentStart = -1;
      public long chunkLength = -1L;
      public long chunkRemainder = -1L;
      public long remainderBytesRead;
      public final MimeHeaders trailerHeaders = new MimeHeaders();
      private Buffer[] contentDecodingRemainders = new Buffer[1];

      public ContentParsingState() {
         this.trailerHeaders.mark();
      }

      public void recycle() {
         this.isLastChunk = false;
         this.chunkContentStart = -1;
         this.chunkLength = -1L;
         this.chunkRemainder = -1L;
         this.remainderBytesRead = 0L;
         this.trailerHeaders.recycle();
         this.trailerHeaders.mark();
         this.contentDecodingRemainders = null;
      }

      private Buffer removeContentDecodingRemainder(int i) {
         if (this.contentDecodingRemainders != null && i < this.contentDecodingRemainders.length) {
            Buffer remainder = this.contentDecodingRemainders[i];
            this.contentDecodingRemainders[i] = null;
            return remainder;
         } else {
            return null;
         }
      }

      private void setContentDecodingRemainder(int i, Buffer remainder) {
         if (this.contentDecodingRemainders == null) {
            this.contentDecodingRemainders = new Buffer[i + 1];
         } else if (i >= this.contentDecodingRemainders.length) {
            this.contentDecodingRemainders = (Buffer[])Arrays.copyOf(this.contentDecodingRemainders, i + 1);
         }

         this.contentDecodingRemainders[i] = remainder;
      }
   }

   public static final class HeaderParsingState {
      public int packetLimit;
      public int state;
      public int subState;
      public int start;
      public int offset;
      public int checkpoint = -1;
      public int checkpoint2 = -1;
      public int arrayOffset;
      public DataChunk headerValueStorage;
      public HttpCodecFilter codecFilter;
      public long parsingNumericValue;
      public boolean isContentLengthHeader;
      public int contentLengthHeadersCount;
      public boolean contentLengthsDiffer;
      public boolean isTransferEncodingHeader;
      public boolean isUpgradeHeader;

      public void initialize(HttpCodecFilter codecFilter, int initialOffset, int maxHeaderSize) {
         this.codecFilter = codecFilter;
         this.offset = initialOffset;
         this.packetLimit = this.offset + maxHeaderSize;
      }

      public void set(int state, int subState, int start, int offset) {
         this.state = state;
         this.subState = subState;
         this.start = start;
         this.offset = offset;
      }

      public void recycle() {
         this.state = 0;
         this.subState = 0;
         this.start = 0;
         this.offset = 0;
         this.checkpoint = -1;
         this.checkpoint2 = -1;
         this.headerValueStorage = null;
         this.parsingNumericValue = 0L;
         this.contentLengthHeadersCount = 0;
         this.contentLengthsDiffer = false;
      }

      public final void checkOverflow(int pos, String errorDescriptionIfOverflow) {
         if (pos >= this.packetLimit) {
            throw new IllegalStateException(errorDescriptionIfOverflow);
         }
      }
   }
}
