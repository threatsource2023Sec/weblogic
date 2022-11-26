package org.glassfish.grizzly.http;

import java.io.IOException;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import org.glassfish.grizzly.Buffer;
import org.glassfish.grizzly.Connection;
import org.glassfish.grizzly.Grizzly;
import org.glassfish.grizzly.ThreadCache;
import org.glassfish.grizzly.attributes.Attribute;
import org.glassfish.grizzly.filterchain.FilterChainContext;
import org.glassfish.grizzly.filterchain.FilterChainEvent;
import org.glassfish.grizzly.filterchain.NextAction;
import org.glassfish.grizzly.http.util.Ascii;
import org.glassfish.grizzly.http.util.DataChunk;
import org.glassfish.grizzly.http.util.Header;
import org.glassfish.grizzly.http.util.HttpCodecUtils;
import org.glassfish.grizzly.http.util.HttpStatus;
import org.glassfish.grizzly.http.util.MimeHeaders;
import org.glassfish.grizzly.memory.MemoryManager;

public class HttpClientFilter extends HttpCodecFilter {
   private final Attribute httpRequestQueueAttr;
   private final Attribute httpResponseInProcessAttr;

   public HttpClientFilter() {
      this(8192);
   }

   public HttpClientFilter(int maxHeadersSize) {
      super(true, maxHeadersSize);
      this.httpResponseInProcessAttr = Grizzly.DEFAULT_ATTRIBUTE_BUILDER.createAttribute("HttpClientFilter.httpResponse");
      this.httpRequestQueueAttr = Grizzly.DEFAULT_ATTRIBUTE_BUILDER.createAttribute("HttpClientFilter.httpRequest");
      this.contentEncodings.add(new GZipContentEncoding());
      this.contentEncodings.add(new LZMAContentEncoding());
   }

   public NextAction handleWrite(FilterChainContext ctx) throws IOException {
      Connection c = ctx.getConnection();
      Object message = ctx.getMessage();
      if (HttpPacket.isHttp(message)) {
         assert message instanceof HttpPacket;

         HttpHeader header = ((HttpPacket)message).getHttpHeader();
         if (!header.isCommitted() && header.isRequest()) {
            assert header instanceof HttpRequestPacket;

            this.getRequestQueue(c).offer((HttpRequestPacket)header);
         }
      }

      return super.handleWrite(ctx);
   }

   public NextAction handleRead(FilterChainContext ctx) throws IOException {
      Connection connection = ctx.getConnection();
      HttpResponsePacket httpResponse = (HttpResponsePacket)this.httpResponseInProcessAttr.get(connection);
      if (httpResponse == null) {
         httpResponse = this.createHttpResponse(ctx);
         this.httpResponseInProcessAttr.set(connection, httpResponse);
      }

      HttpRequestPacket request = ((HttpResponsePacket)httpResponse).getRequest();
      HttpContext httpCtx;
      if (request != null) {
         httpCtx = request.getProcessingState().getHttpContext();
         if (httpCtx == null) {
            httpCtx = HttpContext.newInstance(connection, connection, connection, request);
            request.getProcessingState().setHttpContext(httpCtx);
         }
      } else {
         httpCtx = HttpContext.newInstance(connection, connection, connection, (HttpRequestPacket)null);
      }

      httpCtx.attach(ctx);
      return this.handleRead(ctx, (HttpHeader)httpResponse);
   }

   public NextAction handleEvent(FilterChainContext ctx, FilterChainEvent event) throws IOException {
      if (event.type() == HttpEvents.ChangePacketInProgressEvent.TYPE) {
         HttpResponsePacket responsePacket = (HttpResponsePacket)((HttpEvents.ChangePacketInProgressEvent)event).getPacket();
         this.httpResponseInProcessAttr.set(responsePacket.getProcessingState().getHttpContext(), responsePacket);
         return ctx.getStopAction();
      } else {
         return super.handleEvent(ctx, event);
      }
   }

   private ClientHttpResponseImpl createHttpResponse(FilterChainContext ctx) {
      Buffer input = (Buffer)ctx.getMessage();
      Connection connection = ctx.getConnection();
      ClientHttpResponseImpl httpResponse = HttpClientFilter.ClientHttpResponseImpl.create();
      HttpRequestPacket httpRequest = (HttpRequestPacket)this.getRequestQueue(connection).poll();
      httpResponse.setRequest(httpRequest);
      httpResponse.initialize(this, input.position(), this.maxHeadersSize, -1);
      httpResponse.setSecure(isSecure(connection));
      if (httpRequest != null) {
         try {
            Protocol protocol = httpRequest.getProtocol();
            if (Protocol.HTTP_2_0.equals((Object)protocol)) {
               httpResponse.setProtocol(httpRequest.getProtocol());
               httpResponse.setStatus(HttpStatus.OK_200);
               httpResponse.setExpectContent(true);
               httpResponse.setHeaderParsed(true);
            }
         } catch (IllegalStateException var7) {
         }
      }

      return httpResponse;
   }

   protected boolean onHttpPacketParsed(HttpHeader httpHeader, FilterChainContext ctx) {
      Connection connection = ctx.getConnection();
      this.clearResponse(connection);
      return false;
   }

   protected boolean onHttpHeaderParsed(HttpHeader httpHeader, Buffer buffer, FilterChainContext ctx) {
      ClientHttpResponseImpl response = (ClientHttpResponseImpl)httpHeader;
      HttpRequestPacket request = response.getRequest();
      int statusCode = response.getStatus();
      boolean noContent = statusCode == 204 || statusCode == 205 || statusCode == 304 || request != null && request.isHeadRequest();
      response.setExpectContent(!noContent);
      if (request != null) {
         response.getProcessingState().setKeepAlive(checkKeepAlive(response));
      }

      return false;
   }

   protected void onHttpHeaderError(HttpHeader httpHeader, FilterChainContext ctx, Throwable t) throws IOException {
      throw new IllegalStateException(t);
   }

   protected void onHttpContentError(HttpHeader httpHeader, FilterChainContext ctx, Throwable t) throws IOException {
      httpHeader.setContentBroken(true);
      throw new IllegalStateException(t);
   }

   protected void onInitialLineParsed(HttpHeader httpHeader, FilterChainContext ctx) {
   }

   protected void onInitialLineEncoded(HttpHeader header, FilterChainContext ctx) {
   }

   protected void onHttpHeadersParsed(HttpHeader httpHeader, MimeHeaders headers, FilterChainContext ctx) {
   }

   protected void onHttpHeadersEncoded(HttpHeader httpHeader, FilterChainContext ctx) {
   }

   protected void onHttpContentParsed(HttpContent content, FilterChainContext ctx) {
   }

   protected void onHttpContentEncoded(HttpContent content, FilterChainContext ctx) {
   }

   protected final void clearResponse(Connection connection) {
      this.httpResponseInProcessAttr.remove(connection);
   }

   protected Buffer encodeHttpPacket(FilterChainContext ctx, HttpPacket input) {
      boolean isHeaderPacket = input.isHeader();
      HttpHeader header;
      HttpContent content;
      if (isHeaderPacket) {
         header = (HttpHeader)input;
         content = null;
      } else {
         content = (HttpContent)input;
         header = content.getHttpHeader();
      }

      HttpRequestPacket request = (HttpRequestPacket)header;
      if (!request.isCommitted()) {
         prepareRequest(request);
      }

      return super.encodeHttpPacket(ctx, header, content, false);
   }

   final boolean decodeInitialLineFromBytes(FilterChainContext ctx, HttpPacketParsing httpPacket, HttpCodecFilter.HeaderParsingState parsingState, byte[] input, int end) {
      HttpResponsePacket httpResponse = (HttpResponsePacket)httpPacket;
      int arrayOffs = parsingState.arrayOffset;
      int packetLimit = arrayOffs + parsingState.packetLimit;

      while(true) {
         int subState = parsingState.subState;
         int nonSpaceIdx;
         switch (subState) {
            case 0:
               nonSpaceIdx = HttpCodecUtils.findSpace(input, arrayOffs + parsingState.offset, end, packetLimit);
               if (nonSpaceIdx == -1) {
                  parsingState.offset = end - arrayOffs;
                  return false;
               }

               httpResponse.getProtocolDC().setBytes(input, arrayOffs + parsingState.start, nonSpaceIdx);
               parsingState.start = -1;
               parsingState.offset = nonSpaceIdx - arrayOffs;
               ++parsingState.subState;
            case 1:
               nonSpaceIdx = HttpCodecUtils.skipSpaces(input, arrayOffs + parsingState.offset, end, packetLimit) - arrayOffs;
               if (nonSpaceIdx < 0) {
                  parsingState.offset = end - arrayOffs;
                  return false;
               }

               parsingState.start = nonSpaceIdx;
               parsingState.offset = nonSpaceIdx + 1;
               ++parsingState.subState;
            case 2:
               if (parsingState.offset + 3 > end - arrayOffs) {
                  return false;
               }

               httpResponse.setStatus(Ascii.parseInt((byte[])input, arrayOffs + parsingState.start, 3));
               parsingState.start = -1;
               parsingState.offset += 3;
               ++parsingState.subState;
            case 3:
               nonSpaceIdx = HttpCodecUtils.skipSpaces(input, arrayOffs + parsingState.offset, end, packetLimit) - arrayOffs;
               if (nonSpaceIdx < 0) {
                  parsingState.offset = end - arrayOffs;
                  return false;
               }

               parsingState.start = nonSpaceIdx;
               parsingState.offset = nonSpaceIdx;
               ++parsingState.subState;
            case 4:
               if (!HttpCodecUtils.findEOL(parsingState, input, end)) {
                  parsingState.offset = end - arrayOffs;
                  return false;
               }

               httpResponse.getReasonPhraseRawDC().setBytes(input, arrayOffs + parsingState.start, arrayOffs + parsingState.checkpoint);
               parsingState.subState = 0;
               parsingState.start = -1;
               parsingState.checkpoint = -1;
               this.onInitialLineParsed(httpResponse, ctx);
               if (httpResponse.getStatus() != 100) {
                  return true;
               }

               parsingState.offset += 2;
               parsingState.start = parsingState.offset;
               if (parsingState.start >= end) {
                  return false;
               }

               parsingState.subState = 0;
               break;
            default:
               throw new IllegalStateException();
         }
      }
   }

   final boolean decodeInitialLineFromBuffer(FilterChainContext ctx, HttpPacketParsing httpPacket, HttpCodecFilter.HeaderParsingState parsingState, Buffer input) {
      HttpResponsePacket httpResponse = (HttpResponsePacket)httpPacket;
      int packetLimit = parsingState.packetLimit;
      int subState = parsingState.subState;
      int nonSpaceIdx;
      switch (subState) {
         case 0:
            nonSpaceIdx = HttpCodecUtils.findSpace(input, parsingState.offset, packetLimit);
            if (nonSpaceIdx == -1) {
               parsingState.offset = input.limit();
               return false;
            } else {
               httpResponse.getProtocolDC().setBuffer(input, parsingState.start, nonSpaceIdx);
               parsingState.start = -1;
               parsingState.offset = nonSpaceIdx;
               ++parsingState.subState;
            }
         case 1:
            nonSpaceIdx = HttpCodecUtils.skipSpaces(input, parsingState.offset, packetLimit);
            if (nonSpaceIdx == -1) {
               parsingState.offset = input.limit();
               return false;
            } else {
               parsingState.start = nonSpaceIdx;
               parsingState.offset = nonSpaceIdx + 1;
               ++parsingState.subState;
            }
         case 2:
            if (parsingState.offset + 3 > input.limit()) {
               return false;
            } else {
               httpResponse.setStatus(Ascii.parseInt((Buffer)input, parsingState.start, 3));
               parsingState.start = -1;
               parsingState.offset += 3;
               ++parsingState.subState;
            }
         case 3:
            nonSpaceIdx = HttpCodecUtils.skipSpaces(input, parsingState.offset, packetLimit);
            if (nonSpaceIdx == -1) {
               parsingState.offset = input.limit();
               return false;
            } else {
               parsingState.start = nonSpaceIdx;
               parsingState.offset = nonSpaceIdx;
               ++parsingState.subState;
            }
         case 4:
            if (!HttpCodecUtils.findEOL(parsingState, input)) {
               parsingState.offset = input.limit();
               return false;
            } else {
               httpResponse.getReasonPhraseRawDC().setBuffer(input, parsingState.start, parsingState.checkpoint);
               parsingState.subState = 0;
               parsingState.start = -1;
               parsingState.checkpoint = -1;
               this.onInitialLineParsed(httpResponse, ctx);
               if (httpResponse.getStatus() == 100) {
                  parsingState.offset += 2;
                  parsingState.start = 0;
                  input.position(parsingState.offset);
                  input.shrink();
                  parsingState.offset = 0;
                  return false;
               }

               return true;
            }
         default:
            throw new IllegalStateException();
      }
   }

   Buffer encodeInitialLine(HttpPacket httpPacket, Buffer output, MemoryManager memoryManager) {
      HttpRequestPacket httpRequest = (HttpRequestPacket)httpPacket;
      byte[] tempEncodingBuffer = httpRequest.getTempHeaderEncodingBuffer();
      output = HttpCodecUtils.put(memoryManager, output, tempEncodingBuffer, httpRequest.getMethodDC());
      output = HttpCodecUtils.put(memoryManager, output, (byte)32);
      output = HttpCodecUtils.put(memoryManager, output, tempEncodingBuffer, httpRequest.getRequestURIRef().getRequestURIBC());
      if (!httpRequest.getQueryStringDC().isNull()) {
         output = HttpCodecUtils.put(memoryManager, output, (byte)63);
         output = HttpCodecUtils.put(memoryManager, output, tempEncodingBuffer, httpRequest.getQueryStringDC());
      }

      output = HttpCodecUtils.put(memoryManager, output, (byte)32);
      output = HttpCodecUtils.put(memoryManager, output, tempEncodingBuffer, httpRequest.getProtocolString());
      return output;
   }

   private Queue getRequestQueue(Connection c) {
      Queue q = (Queue)this.httpRequestQueueAttr.get(c);
      if (q == null) {
         q = new ConcurrentLinkedQueue();
         this.httpRequestQueueAttr.set(c, q);
      }

      return (Queue)q;
   }

   private static void prepareRequest(HttpRequestPacket request) {
      String contentType = request.getContentType();
      if (contentType != null) {
         request.getHeaders().setValue(Header.ContentType).setString(contentType);
      }

   }

   private static boolean checkKeepAlive(HttpResponsePacket response) {
      int statusCode = response.getStatus();
      boolean isExpectContent = response.isExpectContent();
      boolean keepAlive = !statusDropsConnection(statusCode) || !isExpectContent || !response.isChunked() || response.getContentLength() == -1L;
      if (keepAlive) {
         DataChunk cVal = response.getHeaders().getValue(Header.Connection);
         if (response.getProtocol().compareTo(Protocol.HTTP_1_1) < 0) {
            keepAlive = cVal != null && cVal.equalsIgnoreCase(KEEPALIVE_BYTES);
         } else {
            keepAlive = cVal == null || !cVal.equalsIgnoreCase(CLOSE_BYTES);
         }
      }

      return keepAlive;
   }

   private static final class ClientHttpResponseImpl extends HttpResponsePacket implements HttpPacketParsing {
      private static final ThreadCache.CachedTypeIndex CACHE_IDX = ThreadCache.obtainIndex(ClientHttpResponseImpl.class, 16);
      private boolean contentTypeParsed;
      private boolean isHeaderParsed;
      private final HttpCodecFilter.HeaderParsingState headerParsingState = new HttpCodecFilter.HeaderParsingState();
      private final HttpCodecFilter.ContentParsingState contentParsingState = new HttpCodecFilter.ContentParsingState();

      public static ClientHttpResponseImpl create() {
         ClientHttpResponseImpl httpResponseImpl = (ClientHttpResponseImpl)ThreadCache.takeFromCache(CACHE_IDX);
         return httpResponseImpl != null ? httpResponseImpl : new ClientHttpResponseImpl();
      }

      public void initialize(HttpCodecFilter filter, int initialOffset, int maxHeaderSize, int maxNumberOfHeaders) {
         this.headerParsingState.initialize(filter, initialOffset, maxHeaderSize);
         this.headers.setMaxNumHeaders(maxNumberOfHeaders);
         this.contentParsingState.trailerHeaders.setMaxNumHeaders(maxNumberOfHeaders);
      }

      public String getCharacterEncoding() {
         if (!this.contentTypeParsed) {
            this.parseContentTypeHeader();
         }

         return super.getCharacterEncoding();
      }

      public void setCharacterEncoding(String charset) {
         if (!this.contentTypeParsed) {
            this.parseContentTypeHeader();
         }

         super.setCharacterEncoding(charset);
      }

      public String getContentType() {
         if (!this.contentTypeParsed) {
            this.parseContentTypeHeader();
         }

         return super.getContentType();
      }

      private void parseContentTypeHeader() {
         this.contentTypeParsed = true;
         if (!this.contentType.isSet()) {
            DataChunk dc = this.headers.getValue(Header.ContentType);
            if (dc != null && !dc.isNull()) {
               this.setContentType(dc.toString());
            }
         }

      }

      protected HttpPacketParsing getParsingState() {
         return this;
      }

      public HttpCodecFilter.HeaderParsingState getHeaderParsingState() {
         return this.headerParsingState;
      }

      public HttpCodecFilter.ContentParsingState getContentParsingState() {
         return this.contentParsingState;
      }

      public ProcessingState getProcessingState() {
         return this.getRequest().getProcessingState();
      }

      public boolean isHeaderParsed() {
         return this.isHeaderParsed;
      }

      public void setHeaderParsed(boolean isHeaderParsed) {
         this.isHeaderParsed = isHeaderParsed;
      }

      protected void reset() {
         this.contentTypeParsed = false;
         this.isHeaderParsed = false;
         this.headerParsingState.recycle();
         this.contentParsingState.recycle();
         super.reset();
      }

      public void recycle() {
         if (!this.getRequest().isExpectContent()) {
            this.reset();
            ThreadCache.putToCache(CACHE_IDX, this);
         }
      }
   }
}
