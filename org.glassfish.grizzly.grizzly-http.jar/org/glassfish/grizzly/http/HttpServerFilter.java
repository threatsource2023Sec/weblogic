package org.glassfish.grizzly.http;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import org.glassfish.grizzly.Buffer;
import org.glassfish.grizzly.Connection;
import org.glassfish.grizzly.Grizzly;
import org.glassfish.grizzly.ThreadCache;
import org.glassfish.grizzly.attributes.Attribute;
import org.glassfish.grizzly.filterchain.FilterChainContext;
import org.glassfish.grizzly.filterchain.FilterChainEvent;
import org.glassfish.grizzly.filterchain.NextAction;
import org.glassfish.grizzly.http.util.Constants;
import org.glassfish.grizzly.http.util.ContentType;
import org.glassfish.grizzly.http.util.DataChunk;
import org.glassfish.grizzly.http.util.FastHttpDateFormat;
import org.glassfish.grizzly.http.util.Header;
import org.glassfish.grizzly.http.util.HttpCodecUtils;
import org.glassfish.grizzly.http.util.HttpStatus;
import org.glassfish.grizzly.http.util.HttpUtils;
import org.glassfish.grizzly.http.util.MimeHeaders;
import org.glassfish.grizzly.memory.MemoryManager;
import org.glassfish.grizzly.utils.DelayedExecutor;

public class HttpServerFilter extends HttpCodecFilter {
   public static final String HTTP_SERVER_REQUEST_ATTR_NAME = HttpServerFilter.class.getName() + ".HttpRequest";
   public static final FilterChainEvent RESPONSE_COMPLETE_EVENT = new HttpEvents.ResponseCompleteEvent();
   private final Attribute httpRequestInProcessAttr;
   private final Attribute keepAliveContextAttr;
   private final DelayedExecutor.DelayQueue keepAliveQueue;
   private final KeepAlive keepAlive;
   private String defaultResponseContentType;
   private byte[] defaultResponseContentTypeBytes;
   private byte[] defaultResponseContentTypeBytesNoCharset;
   private final boolean allowKeepAlive;
   private final int maxRequestHeaders;
   private final int maxResponseHeaders;
   private boolean allowPayloadForUndefinedHttpMethods;

   /** @deprecated */
   @Deprecated
   public HttpServerFilter() {
      this(true, 8192, (KeepAlive)null, (DelayedExecutor)null);
   }

   /** @deprecated */
   @Deprecated
   public HttpServerFilter(boolean chunkingEnabled, int maxHeadersSize, KeepAlive keepAlive, DelayedExecutor executor) {
      this(chunkingEnabled, maxHeadersSize, Constants.DEFAULT_RESPONSE_TYPE, keepAlive, executor);
   }

   /** @deprecated */
   @Deprecated
   public HttpServerFilter(boolean chunkingEnabled, int maxHeadersSize, String defaultResponseContentType, KeepAlive keepAlive, DelayedExecutor executor) {
      this(chunkingEnabled, maxHeadersSize, defaultResponseContentType, keepAlive, executor, 100, 100);
   }

   /** @deprecated */
   @Deprecated
   public HttpServerFilter(boolean chunkingEnabled, int maxHeadersSize, String defaultResponseContentType, KeepAlive keepAlive, DelayedExecutor executor, int maxRequestHeaders, int maxResponseHeaders) {
      super(chunkingEnabled, maxHeadersSize);
      this.httpRequestInProcessAttr = Grizzly.DEFAULT_ATTRIBUTE_BUILDER.createAttribute(HTTP_SERVER_REQUEST_ATTR_NAME);
      this.keepAliveContextAttr = Grizzly.DEFAULT_ATTRIBUTE_BUILDER.createAttribute("HttpServerFilter.KeepAliveContext");
      this.keepAliveQueue = executor != null ? executor.createDelayQueue(new KeepAliveWorker(keepAlive), new KeepAliveResolver()) : null;
      this.allowKeepAlive = keepAlive != null;
      this.keepAlive = this.allowKeepAlive ? new KeepAlive(keepAlive) : null;
      if (defaultResponseContentType != null && !defaultResponseContentType.isEmpty()) {
         this.setDefaultResponseContentType(defaultResponseContentType);
      }

      this.maxRequestHeaders = maxRequestHeaders;
      this.maxResponseHeaders = maxResponseHeaders;
   }

   public String getDefaultResponseContentType() {
      return this.defaultResponseContentType;
   }

   public final void setDefaultResponseContentType(String contentType) {
      this.defaultResponseContentType = contentType;
      if (contentType != null) {
         this.defaultResponseContentTypeBytes = HttpCodecUtils.toCheckedByteArray(contentType);
         this.defaultResponseContentTypeBytesNoCharset = ContentType.removeCharset(this.defaultResponseContentTypeBytes);
      } else {
         this.defaultResponseContentTypeBytes = this.defaultResponseContentTypeBytesNoCharset = null;
      }

   }

   public boolean isAllowPayloadForUndefinedHttpMethods() {
      return this.allowPayloadForUndefinedHttpMethods;
   }

   public void setAllowPayloadForUndefinedHttpMethods(boolean allowPayloadForUndefinedHttpMethods) {
      this.allowPayloadForUndefinedHttpMethods = allowPayloadForUndefinedHttpMethods;
   }

   public NextAction handleRead(FilterChainContext ctx) throws IOException {
      Buffer input = (Buffer)ctx.getMessage();
      Connection connection = ctx.getConnection();
      ServerHttpRequestImpl httpRequest = (ServerHttpRequestImpl)this.httpRequestInProcessAttr.get(connection);
      if (httpRequest == null) {
         boolean isSecureLocal = isSecure(connection);
         httpRequest = HttpServerFilter.ServerHttpRequestImpl.create();
         httpRequest.initialize(connection, this, input.position(), this.maxHeadersSize, this.maxRequestHeaders);
         httpRequest.setSecure(isSecureLocal);
         HttpResponsePacket response = httpRequest.getResponse();
         response.setSecure(isSecureLocal);
         response.getHeaders().setMaxNumHeaders(this.maxResponseHeaders);
         httpRequest.setResponse(response);
         response.setRequest(httpRequest);
         HttpContext httpContext = HttpContext.newInstance(connection, connection, connection, httpRequest).attach(ctx);
         httpRequest.getProcessingState().setHttpContext(httpContext);
         if (this.allowKeepAlive) {
            KeepAliveContext keepAliveContext = (KeepAliveContext)this.keepAliveContextAttr.get(httpContext);
            if (keepAliveContext == null) {
               keepAliveContext = new KeepAliveContext(connection);
               this.keepAliveContextAttr.set(httpContext, keepAliveContext);
            } else if (this.keepAliveQueue != null) {
               this.keepAliveQueue.remove(keepAliveContext);
            }

            int requestsProcessed = keepAliveContext.requestsProcessed;
            if (requestsProcessed > 0) {
               KeepAlive.notifyProbesHit(this.keepAlive, connection, requestsProcessed);
            }
         }

         this.httpRequestInProcessAttr.set(httpContext, httpRequest);
      } else {
         if (httpRequest.isContentBroken()) {
            return ctx.getStopAction();
         }

         httpRequest.getProcessingState().getHttpContext().attach(ctx);
      }

      return this.handleRead(ctx, httpRequest);
   }

   final boolean decodeInitialLineFromBytes(FilterChainContext ctx, HttpPacketParsing httpPacket, HttpCodecFilter.HeaderParsingState parsingState, byte[] input, int end) {
      ServerHttpRequestImpl httpRequest = (ServerHttpRequestImpl)httpPacket;
      int arrayOffs = parsingState.arrayOffset;
      int reqLimit = arrayOffs + parsingState.packetLimit;
      int subState = parsingState.subState;
      int nonSpaceIdx;
      switch (subState) {
         case 0:
            nonSpaceIdx = HttpCodecUtils.findSpace(input, arrayOffs + parsingState.offset, end, reqLimit);
            if (nonSpaceIdx == -1) {
               parsingState.offset = end - arrayOffs;
               return false;
            } else {
               httpRequest.getMethodDC().setBytes(input, arrayOffs + parsingState.start, nonSpaceIdx);
               parsingState.start = -1;
               parsingState.offset = nonSpaceIdx - arrayOffs;
               ++parsingState.subState;
            }
         case 1:
            nonSpaceIdx = HttpCodecUtils.skipSpaces(input, arrayOffs + parsingState.offset, end, reqLimit) - arrayOffs;
            if (nonSpaceIdx < 0) {
               parsingState.offset = end - arrayOffs;
               return false;
            } else {
               parsingState.start = nonSpaceIdx;
               parsingState.offset = nonSpaceIdx + 1;
               ++parsingState.subState;
            }
         case 2:
            if (!parseRequestURI(httpRequest, parsingState, input, end)) {
               return false;
            }
         case 3:
            nonSpaceIdx = HttpCodecUtils.skipSpaces(input, arrayOffs + parsingState.offset, end, reqLimit) - arrayOffs;
            if (nonSpaceIdx < 0) {
               parsingState.offset = end - arrayOffs;
               return false;
            } else {
               parsingState.start = nonSpaceIdx;
               parsingState.offset = nonSpaceIdx;
               ++parsingState.subState;
            }
         case 4:
            if (!HttpCodecUtils.findEOL(parsingState, input, end)) {
               parsingState.offset = end - arrayOffs;
               return false;
            }

            if (parsingState.checkpoint > parsingState.start) {
               httpRequest.getProtocolDC().setBytes(input, arrayOffs + parsingState.start, arrayOffs + parsingState.checkpoint);
            } else {
               httpRequest.getProtocolDC().setString("");
            }

            parsingState.subState = 0;
            parsingState.start = -1;
            parsingState.checkpoint = -1;
            this.onInitialLineParsed(httpRequest, ctx);
            return true;
         default:
            throw new IllegalStateException();
      }
   }

   private static boolean parseRequestURI(ServerHttpRequestImpl httpRequest, HttpCodecFilter.HeaderParsingState state, byte[] input, int end) {
      int arrayOffs = state.arrayOffset;
      int limit = Math.min(end, arrayOffs + state.packetLimit);
      int offset = arrayOffs + state.offset;
      boolean found = false;

      int b;
      while(offset < limit) {
         b = input[offset];
         if (b != 32 && b != 9) {
            if (b != 13 && b != 10) {
               if (b == 63 && state.checkpoint == -1) {
                  state.checkpoint = offset - arrayOffs;
               }

               ++offset;
               continue;
            }

            found = true;
            break;
         }

         found = true;
         break;
      }

      if (found) {
         b = offset;
         if (state.checkpoint != -1) {
            b = arrayOffs + state.checkpoint;
            httpRequest.getQueryStringDC().setBytes(input, b + 1, offset);
         }

         httpRequest.getRequestURIRef().init(input, arrayOffs + state.start, b);
         state.start = -1;
         state.checkpoint = -1;
         ++state.subState;
      }

      state.offset = offset - arrayOffs;
      return found;
   }

   final boolean decodeInitialLineFromBuffer(FilterChainContext ctx, HttpPacketParsing httpPacket, HttpCodecFilter.HeaderParsingState parsingState, Buffer input) {
      ServerHttpRequestImpl httpRequest = (ServerHttpRequestImpl)httpPacket;
      int reqLimit = parsingState.packetLimit;
      int subState = parsingState.subState;
      int nonSpaceIdx;
      switch (subState) {
         case 0:
            nonSpaceIdx = HttpCodecUtils.findSpace(input, parsingState.offset, reqLimit);
            if (nonSpaceIdx == -1) {
               parsingState.offset = input.limit();
               return false;
            } else {
               httpRequest.getMethodDC().setBuffer(input, parsingState.start, nonSpaceIdx);
               parsingState.start = -1;
               parsingState.offset = nonSpaceIdx;
               ++parsingState.subState;
            }
         case 1:
            nonSpaceIdx = HttpCodecUtils.skipSpaces(input, parsingState.offset, reqLimit);
            if (nonSpaceIdx == -1) {
               parsingState.offset = input.limit();
               return false;
            } else {
               parsingState.start = nonSpaceIdx;
               parsingState.offset = nonSpaceIdx + 1;
               ++parsingState.subState;
            }
         case 2:
            if (!parseRequestURI(httpRequest, parsingState, input)) {
               return false;
            }
         case 3:
            nonSpaceIdx = HttpCodecUtils.skipSpaces(input, parsingState.offset, reqLimit);
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
            }

            if (parsingState.checkpoint > parsingState.start) {
               httpRequest.getProtocolDC().setBuffer(input, parsingState.start, parsingState.checkpoint);
            } else {
               httpRequest.getProtocolDC().setString("");
            }

            parsingState.subState = 0;
            parsingState.start = -1;
            parsingState.checkpoint = -1;
            this.onInitialLineParsed(httpRequest, ctx);
            return true;
         default:
            throw new IllegalStateException();
      }
   }

   private static boolean parseRequestURI(ServerHttpRequestImpl httpRequest, HttpCodecFilter.HeaderParsingState state, Buffer input) {
      int limit = Math.min(input.limit(), state.packetLimit);
      int offset = state.offset;
      boolean found = false;

      int b;
      while(offset < limit) {
         b = input.get(offset);
         if (b != 32 && b != 9) {
            if (b != 13 && b != 10) {
               if (b == 63 && state.checkpoint == -1) {
                  state.checkpoint = offset;
               }

               ++offset;
               continue;
            }

            found = true;
            break;
         }

         found = true;
         break;
      }

      if (found) {
         b = offset;
         if (state.checkpoint != -1) {
            b = state.checkpoint;
            httpRequest.getQueryStringDC().setBuffer(input, state.checkpoint + 1, offset);
         }

         httpRequest.getRequestURIRef().init(input, state.start, b);
         state.start = -1;
         state.checkpoint = -1;
         ++state.subState;
      }

      state.offset = offset;
      return found;
   }

   protected boolean onHttpHeaderParsed(HttpHeader httpHeader, Buffer buffer, FilterChainContext ctx) {
      ServerHttpRequestImpl request = (ServerHttpRequestImpl)httpHeader;
      this.prepareRequest(request, buffer.hasRemaining());
      return request.getProcessingState().error;
   }

   private void prepareRequest(ServerHttpRequestImpl request, boolean hasReadyContent) {
      ProcessingState state = request.getProcessingState();
      HttpResponsePacket response = request.getResponse();

      Protocol protocol;
      try {
         protocol = request.getProtocol();
      } catch (IllegalStateException var14) {
         state.error = true;
         HttpStatus.HTTP_VERSION_NOT_SUPPORTED_505.setValues(response);
         protocol = Protocol.HTTP_1_1;
         request.setProtocol(protocol);
         return;
      }

      request.getResponse().setChunkingAllowed(this.isChunkingEnabled());
      if (request.getHeaderParsingState().contentLengthsDiffer) {
         request.getProcessingState().error = true;
      } else {
         MimeHeaders headers = request.getHeaders();
         DataChunk hostDC = null;
         DataChunk uriBC = request.getRequestURIRef().getRequestURIBC();
         if (uriBC.startsWithIgnoreCase("http", 0)) {
            int pos = uriBC.indexOf("://", 4);
            int uriBCStart = uriBC.getStart();
            if (pos != -1) {
               int slashPos = uriBC.indexOf('/', pos + 3);
               if (slashPos == -1) {
                  slashPos = uriBC.getLength();
                  uriBC.setStart(uriBCStart + pos + 1);
                  uriBC.setEnd(uriBCStart + pos + 2);
               } else {
                  uriBC.setStart(uriBCStart + slashPos);
                  uriBC.setEnd(uriBC.getEnd());
               }

               hostDC = headers.setValue(Header.Host);
               hostDC.set(uriBC, uriBCStart + pos + 3, uriBCStart + slashPos);
            }
         }

         if (hostDC == null) {
            hostDC = headers.getValue(Header.Host);
         }

         boolean isHttp11 = protocol == Protocol.HTTP_1_1;
         if (isHttp11 && (hostDC == null || hostDC.isNull())) {
            state.error = true;
         } else {
            request.unparsedHostC = hostDC;
            if (!request.isIgnoreContentModifiers()) {
               Method method = request.getMethod();
               Method.PayloadExpectation payloadExpectation = method.getPayloadExpectation();
               if (payloadExpectation != Method.PayloadExpectation.NOT_ALLOWED) {
                  boolean hasPayload = request.getContentLength() > 0L || request.isChunked();
                  if (hasPayload && payloadExpectation == Method.PayloadExpectation.UNDEFINED && !this.allowPayloadForUndefinedHttpMethods) {
                     state.error = true;
                     HttpStatus.BAD_REQUEST_400.setValues(response);
                     return;
                  }

                  request.setExpectContent(hasPayload);
               } else {
                  request.setExpectContent(method == Method.CONNECT || method == Method.PRI);
               }

               if (method == Method.CONNECT) {
                  state.keepAlive = false;
               } else {
                  DataChunk connectionValueDC = headers.getValue(Header.Connection);
                  boolean isConnectionClose = connectionValueDC != null && connectionValueDC.equalsIgnoreCaseLowerCase(CLOSE_BYTES);
                  if (!isConnectionClose) {
                     state.keepAlive = this.allowKeepAlive && (isHttp11 || connectionValueDC != null && connectionValueDC.equalsIgnoreCaseLowerCase(KEEPALIVE_BYTES));
                  }
               }

               if (request.requiresAcknowledgement()) {
                  request.requiresAcknowledgement(isHttp11 && !hasReadyContent);
               }

            }
         }
      }
   }

   protected final boolean onHttpPacketParsed(HttpHeader httpHeader, FilterChainContext ctx) {
      ServerHttpRequestImpl request = (ServerHttpRequestImpl)httpHeader;
      boolean error = request.getProcessingState().error;
      if (!error) {
         this.httpRequestInProcessAttr.remove(ctx.getConnection());
      }

      return error;
   }

   protected void onInitialLineParsed(HttpHeader httpHeader, FilterChainContext ctx) {
   }

   protected void onHttpHeadersParsed(HttpHeader httpHeader, MimeHeaders headers, FilterChainContext ctx) {
   }

   protected void onHttpContentParsed(HttpContent content, FilterChainContext ctx) {
   }

   protected void onHttpHeaderError(HttpHeader httpHeader, FilterChainContext ctx, Throwable t) throws IOException {
      ServerHttpRequestImpl request = (ServerHttpRequestImpl)httpHeader;
      HttpResponsePacket response = request.getResponse();
      this.sendBadRequestResponse(ctx, response);
   }

   protected void onHttpContentError(HttpHeader httpHeader, FilterChainContext ctx, Throwable t) throws IOException {
      ServerHttpRequestImpl request = (ServerHttpRequestImpl)httpHeader;
      HttpResponsePacket response = request.getResponse();
      if (!response.isCommitted()) {
         this.sendBadRequestResponse(ctx, response);
      }

      httpHeader.setContentBroken(true);
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

      boolean wasContentAlreadyEncoded = false;
      HttpResponsePacket response = (HttpResponsePacket)header;
      if (!response.isCommitted()) {
         HttpContent encodedHttpContent = this.prepareResponse(ctx, response.getRequest(), response, content);
         if (encodedHttpContent != null) {
            content = encodedHttpContent;
            wasContentAlreadyEncoded = true;
         }
      }

      Buffer encoded = super.encodeHttpPacket(ctx, header, content, wasContentAlreadyEncoded);
      if (!isHeaderPacket) {
         input.recycle();
      }

      return encoded;
   }

   private HttpContent prepareResponse(FilterChainContext ctx, HttpRequestPacket request, HttpResponsePacket response, HttpContent httpContent) {
      if (!request.isIgnoreContentModifiers() && !response.isIgnoreContentModifiers()) {
         Protocol requestProtocol = request.getProtocol();
         if (requestProtocol == Protocol.HTTP_0_9) {
            return null;
         } else {
            boolean entityBody = true;
            int statusCode = response.getStatus();
            boolean is204 = HttpStatus.NO_CONTENT_204.statusMatches(statusCode);
            if (is204 || HttpStatus.RESET_CONTENT_205.statusMatches(statusCode) || HttpStatus.NOT_MODIFIED_304.statusMatches(statusCode)) {
               entityBody = false;
               response.setExpectContent(false);
               if (is204) {
                  response.setTransferEncoding((TransferEncoding)null);
                  response.getHeaders().removeHeader(Header.TransferEncoding);
               }
            }

            boolean isHttp11OrHigher = requestProtocol.compareTo(Protocol.HTTP_1_1) >= 0;
            HttpContent encodedHttpContent = null;
            Method method = request.getMethod();
            if (!Method.CONNECT.equals(method)) {
               if (entityBody) {
                  this.setContentEncodingsOnSerializing(response);
                  if (response.getContentLength() == -1L && !response.isChunked()) {
                     if (httpContent != null && httpContent.isLast()) {
                        if (!response.getContentEncodings(true).isEmpty()) {
                           encodedHttpContent = this.encodeContent(ctx.getConnection(), httpContent);
                        }

                        response.setContentLength(httpContent.getContent().remaining());
                     } else if (this.chunkingEnabled && isHttp11OrHigher) {
                        response.setChunked(true);
                     }
                  }
               }

               if (Method.HEAD.equals(method)) {
                  response.setExpectContent(false);
                  this.setContentEncodingsOnSerializing(response);
                  this.setTransferEncodingOnSerializing(ctx, response, httpContent);
               }
            } else {
               response.setContentEncodingsSelected(true);
               response.setContentLength(-1);
               response.setChunked(false);
            }

            MimeHeaders headers = response.getHeaders();
            DataChunk dc;
            if (!entityBody) {
               response.setContentLength(-1);
            } else {
               String contentLanguage = response.getContentLanguage();
               if (contentLanguage != null) {
                  headers.setValue(Header.ContentLanguage).setString(contentLanguage);
               }

               ContentType contentType = response.getContentTypeHolder();
               if (contentType.isMimeTypeSet()) {
                  dc = headers.setValue(Header.ContentType);
                  if (dc.isNull()) {
                     contentType.serializeToDataChunk(dc);
                  }
               } else if (this.defaultResponseContentType != null) {
                  dc = headers.setValue(Header.ContentType);
                  if (dc.isNull()) {
                     String ce = response.getCharacterEncoding();
                     if (ce == null) {
                        dc.setBytes(this.defaultResponseContentTypeBytes);
                     } else {
                        byte[] array = ContentType.compose(this.defaultResponseContentTypeBytesNoCharset, ce);
                        dc.setBytes(array);
                     }
                  }
               }
            }

            if (!response.containsHeader(Header.Date)) {
               response.getHeaders().addValue(Header.Date).setBytes(FastHttpDateFormat.getCurrentDateBytes());
            }

            ProcessingState state = response.getProcessingState();
            boolean isHttp11 = requestProtocol == Protocol.HTTP_1_1;
            if (state.keepAlive) {
               if (entityBody && !isHttp11 && response.getContentLength() == -1L) {
                  state.keepAlive = false;
               } else if (entityBody && !response.isChunked() && response.getContentLength() == -1L) {
                  state.keepAlive = false;
               } else if (!this.checkKeepAliveRequestsCount(state.getHttpContext())) {
                  state.keepAlive = false;
               } else {
                  dc = headers.getValue(Header.Connection);
                  if (dc != null && !dc.isNull() && dc.equalsIgnoreCase(CLOSE_BYTES)) {
                     state.keepAlive = false;
                  }
               }

               state.keepAlive = state.keepAlive && !statusDropsConnection(response.getStatus());
            }

            if (!state.keepAlive) {
               headers.setValue(Header.Connection).setBytes(CLOSE_BYTES);
            } else if (!isHttp11 && !state.error) {
               headers.setValue(Header.Connection).setBytes(KEEPALIVE_BYTES);
            }

            return encodedHttpContent;
         }
      } else {
         return httpContent;
      }
   }

   Buffer encodeInitialLine(HttpPacket httpPacket, Buffer output, MemoryManager memoryManager) {
      HttpResponsePacket httpResponse = (HttpResponsePacket)httpPacket;
      output = HttpCodecUtils.put(memoryManager, output, httpResponse.getProtocol().getProtocolBytes());
      output = HttpCodecUtils.put(memoryManager, output, (byte)32);
      output = HttpCodecUtils.put(memoryManager, output, httpResponse.getHttpStatus().getStatusBytes());
      output = HttpCodecUtils.put(memoryManager, output, (byte)32);
      if (httpResponse.isCustomReasonPhraseSet()) {
         DataChunk customReasonPhrase = httpResponse.isHtmlEncodingCustomReasonPhrase() ? HttpUtils.filter(httpResponse.getReasonPhraseDC()) : HttpUtils.filterNonPrintableCharacters(httpResponse.getReasonPhraseDC());
         output = HttpCodecUtils.put(memoryManager, output, httpResponse.getTempHeaderEncodingBuffer(), customReasonPhrase);
      } else {
         output = HttpCodecUtils.put(memoryManager, output, httpResponse.getHttpStatus().getReasonPhraseBytes());
      }

      return output;
   }

   protected void onInitialLineEncoded(HttpHeader header, FilterChainContext ctx) {
   }

   protected void onHttpHeadersEncoded(HttpHeader httpHeader, FilterChainContext ctx) {
   }

   protected void onHttpContentEncoded(HttpContent content, FilterChainContext ctx) {
   }

   public NextAction handleEvent(FilterChainContext ctx, FilterChainEvent event) throws IOException {
      if (event.type() == HttpEvents.ResponseCompleteEvent.TYPE) {
         if (ctx.getConnection().isOpen()) {
            HttpContext context = HttpContext.get(ctx);
            HttpRequestPacket httpRequest = context.getRequest();
            if (this.allowKeepAlive) {
               if (this.keepAliveQueue != null) {
                  KeepAliveContext keepAliveContext = (KeepAliveContext)this.keepAliveContextAttr.get(context);
                  if (keepAliveContext != null) {
                     this.keepAliveQueue.add(keepAliveContext, (long)this.keepAlive.getIdleTimeoutInSeconds(), TimeUnit.SECONDS);
                  }
               }

               boolean isStayAlive = httpRequest.getProcessingState().isKeepAlive();
               this.processResponseComplete(ctx, httpRequest, isStayAlive);
            } else {
               this.processResponseComplete(ctx, httpRequest, false);
            }
         }

         return ctx.getStopAction();
      } else {
         return ctx.getInvokeAction();
      }
   }

   public NextAction handleClose(FilterChainContext ctx) throws IOException {
      ServerHttpRequestImpl httpRequest = (ServerHttpRequestImpl)this.httpRequestInProcessAttr.get(ctx.getConnection());
      if (httpRequest != null && !httpRequest.isContentBroken() && httpRequest.isExpectContent() && httpRequest.getTransferEncoding() == null) {
         httpRequest.setExpectContent(false);
         this.onHttpPacketParsed(httpRequest, ctx);
      }

      return ctx.getInvokeAction();
   }

   private void processResponseComplete(FilterChainContext ctx, HttpRequestPacket httpRequest, boolean isStayAlive) throws IOException {
      if (httpRequest.isUpgrade()) {
         httpRequest.getProcessingState().getHttpContext().close();
      } else {
         if (httpRequest.isExpectContent()) {
            if (!httpRequest.isContentBroken() && this.checkContentLengthRemainder(httpRequest)) {
               httpRequest.setSkipRemainder(true);
            } else {
               httpRequest.setExpectContent(false);
               this.onHttpPacketParsed(httpRequest, ctx);
               httpRequest.getProcessingState().getHttpContext().close();
            }
         } else if (!isStayAlive) {
            httpRequest.getProcessingState().getHttpContext().close();
         }

      }
   }

   protected HttpContent customizeErrorResponse(HttpResponsePacket response) {
      response.setContentLength(0);
      return HttpContent.builder(response).last(true).build();
   }

   private boolean checkKeepAliveRequestsCount(HttpContext httpContext) {
      if (!this.allowKeepAlive) {
         return false;
      } else {
         KeepAliveContext keepAliveContext = (KeepAliveContext)this.keepAliveContextAttr.get(httpContext);
         int requestsProcessed = keepAliveContext.requestsProcessed++;
         int maxRequestCount = this.keepAlive.getMaxRequestsCount();
         boolean isKeepAlive = maxRequestCount == -1 || keepAliveContext.requestsProcessed <= maxRequestCount;
         if (requestsProcessed == 0) {
            if (isKeepAlive) {
               KeepAlive.notifyProbesConnectionAccepted(this.keepAlive, keepAliveContext.connection);
            } else {
               KeepAlive.notifyProbesRefused(this.keepAlive, keepAliveContext.connection);
            }
         }

         return isKeepAlive;
      }
   }

   private void sendBadRequestResponse(FilterChainContext ctx, HttpResponsePacket response) {
      if (response.getHttpStatus().getStatusCode() < 400) {
         HttpStatus.BAD_REQUEST_400.setValues(response);
      }

      this.commitAndCloseAsError(ctx, response);
   }

   private void commitAndCloseAsError(FilterChainContext ctx, HttpResponsePacket response) {
      HttpContent errorHttpResponse = this.customizeErrorResponse(response);
      Buffer resBuf = this.encodeHttpPacket(ctx, errorHttpResponse);
      ctx.write(resBuf);
      response.getProcessingState().getHttpContext().close();
   }

   private boolean checkContentLengthRemainder(HttpRequestPacket httpRequest) {
      return this.maxPayloadRemainderToSkip < 0L || httpRequest.getContentLength() <= 0L || ((HttpPacketParsing)httpRequest).getContentParsingState().chunkRemainder <= this.maxPayloadRemainderToSkip;
   }

   private static final class ServerHttpRequestImpl extends HttpRequestPacket implements HttpPacketParsing {
      private static final ThreadCache.CachedTypeIndex CACHE_IDX = ThreadCache.obtainIndex(ServerHttpRequestImpl.class, 16);
      private boolean contentTypeParsed;
      private boolean isHeaderParsed;
      private final HttpCodecFilter.HeaderParsingState headerParsingState = new HttpCodecFilter.HeaderParsingState();
      private final HttpCodecFilter.ContentParsingState contentParsingState = new HttpCodecFilter.ContentParsingState();
      private final ProcessingState processingState = new ProcessingState();
      private final HttpResponsePacket finalHttpResponse = new HttpResponsePacketImpl();

      public static ServerHttpRequestImpl create() {
         ServerHttpRequestImpl httpRequestImpl = (ServerHttpRequestImpl)ThreadCache.takeFromCache(CACHE_IDX);
         return httpRequestImpl != null ? httpRequestImpl : new ServerHttpRequestImpl();
      }

      private ServerHttpRequestImpl() {
         this.isExpectContent = true;
      }

      public void initialize(Connection connection, HttpCodecFilter filter, int initialOffset, int maxHeaderSize, int maxNumberOfHeaders) {
         this.headerParsingState.initialize(filter, initialOffset, maxHeaderSize);
         this.contentParsingState.trailerHeaders.setMaxNumHeaders(maxNumberOfHeaders);
         this.headers.setMaxNumHeaders(maxNumberOfHeaders);
         this.finalHttpResponse.setProtocol(Protocol.HTTP_1_1);
         this.setResponse(this.finalHttpResponse);
         this.setConnection(connection);
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

      public ProcessingState getProcessingState() {
         return this.processingState;
      }

      public HttpCodecFilter.HeaderParsingState getHeaderParsingState() {
         return this.headerParsingState;
      }

      public HttpCodecFilter.ContentParsingState getContentParsingState() {
         return this.contentParsingState;
      }

      public boolean isHeaderParsed() {
         return this.isHeaderParsed;
      }

      public void setHeaderParsed(boolean isHeaderParsed) {
         if (isHeaderParsed && this.isExpectContent() && !this.isChunked) {
            this.contentParsingState.chunkRemainder = this.getContentLength();
         }

         this.isHeaderParsed = isHeaderParsed;
      }

      protected HttpPacketParsing getParsingState() {
         return this;
      }

      protected void reset() {
         this.contentTypeParsed = false;
         this.isHeaderParsed = false;
         this.headerParsingState.recycle();
         this.contentParsingState.recycle();
         this.processingState.recycle();
         super.reset();
      }

      public void recycle() {
         if (!this.isExpectContent()) {
            this.reset();
            ThreadCache.putToCache(CACHE_IDX, this);
         }
      }
   }

   private static class KeepAliveResolver implements DelayedExecutor.Resolver {
      private KeepAliveResolver() {
      }

      public boolean removeTimeout(KeepAliveContext context) {
         if (context.keepAliveTimeoutMillis != -1L) {
            context.keepAliveTimeoutMillis = -1L;
            return true;
         } else {
            return false;
         }
      }

      public long getTimeoutMillis(KeepAliveContext element) {
         return element.keepAliveTimeoutMillis;
      }

      public void setTimeoutMillis(KeepAliveContext element, long timeoutMillis) {
         element.keepAliveTimeoutMillis = timeoutMillis;
      }

      // $FF: synthetic method
      KeepAliveResolver(Object x0) {
         this();
      }
   }

   private static class KeepAliveWorker implements DelayedExecutor.Worker {
      private final KeepAlive keepAlive;

      public KeepAliveWorker(KeepAlive keepAlive) {
         this.keepAlive = keepAlive;
      }

      public boolean doWork(KeepAliveContext context) {
         KeepAlive.notifyProbesTimeout(this.keepAlive, context.connection);
         context.connection.closeSilently();
         return true;
      }
   }

   private static class KeepAliveContext {
      private final Connection connection;
      private volatile long keepAliveTimeoutMillis = -1L;
      private int requestsProcessed;

      public KeepAliveContext(Connection connection) {
         this.connection = connection;
      }
   }
}
