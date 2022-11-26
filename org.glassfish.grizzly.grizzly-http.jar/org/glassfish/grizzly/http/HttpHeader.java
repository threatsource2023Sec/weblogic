package org.glassfish.grizzly.http;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.glassfish.grizzly.Buffer;
import org.glassfish.grizzly.Grizzly;
import org.glassfish.grizzly.attributes.AttributeHolder;
import org.glassfish.grizzly.attributes.AttributeStorage;
import org.glassfish.grizzly.http.util.ContentType;
import org.glassfish.grizzly.http.util.DataChunk;
import org.glassfish.grizzly.http.util.Header;
import org.glassfish.grizzly.http.util.HeaderValue;
import org.glassfish.grizzly.http.util.HttpUtils;
import org.glassfish.grizzly.http.util.MimeHeaders;
import org.glassfish.grizzly.utils.Charsets;

public abstract class HttpHeader extends HttpPacket implements MimeHeadersPacket, AttributeStorage {
   private static final byte[] CHUNKED_ENCODING_BYTES;
   protected boolean isCommitted;
   protected final MimeHeaders headers;
   protected final DataChunk protocolC;
   protected Protocol parsedProtocol;
   protected boolean isChunked;
   private final byte[] tmpContentLengthBuffer;
   private final byte[] tmpHeaderEncodingBuffer;
   protected long contentLength;
   protected final ContentType.SettableContentType contentType;
   protected boolean isExpectContent;
   protected boolean isSkipRemainder;
   protected boolean isContentBroken;
   protected boolean secure;
   private boolean isIgnoreContentModifiers;
   protected final DataChunk upgrade;
   private TransferEncoding transferEncoding;
   private final List contentEncodings;
   private boolean isContentEncodingsSelected;
   private final AttributeHolder attributes;
   private AttributeHolder activeAttributes;
   Buffer headerBuffer;
   private boolean chunkingAllowed;

   public HttpHeader() {
      this(new MimeHeaders());
   }

   protected HttpHeader(MimeHeaders headers) {
      this.protocolC = DataChunk.newInstance();
      this.tmpContentLengthBuffer = new byte[20];
      this.tmpHeaderEncodingBuffer = new byte[512];
      this.contentLength = -1L;
      this.contentType = ContentType.newSettableContentType();
      this.isExpectContent = true;
      this.upgrade = DataChunk.newInstance();
      this.contentEncodings = new ArrayList(2);
      this.attributes = Grizzly.DEFAULT_ATTRIBUTE_BUILDER.createUnsafeAttributeHolder();
      this.headers = headers;
   }

   void setHeaderBuffer(Buffer headerBuffer) {
      this.headerBuffer = headerBuffer;
   }

   public AttributeHolder getAttributes() {
      if (this.activeAttributes == null) {
         this.activeAttributes = this.attributes;
      }

      return this.activeAttributes;
   }

   public abstract boolean isRequest();

   public final boolean isHeader() {
      return true;
   }

   public HttpHeader getHttpHeader() {
      return this;
   }

   public abstract ProcessingState getProcessingState();

   protected HttpPacketParsing getParsingState() {
      return null;
   }

   protected void addContentEncoding(ContentEncoding contentEncoding) {
      this.contentEncodings.add(contentEncoding);
   }

   protected List getContentEncodings(boolean isModifiable) {
      return isModifiable ? this.contentEncodings : Collections.unmodifiableList(this.contentEncodings);
   }

   public List getContentEncodings() {
      return this.getContentEncodings(false);
   }

   protected final boolean isContentEncodingsSelected() {
      return this.isContentEncodingsSelected;
   }

   protected final void setContentEncodingsSelected(boolean isContentEncodingsSelected) {
      this.isContentEncodingsSelected = isContentEncodingsSelected;
   }

   public TransferEncoding getTransferEncoding() {
      return this.transferEncoding;
   }

   protected void setTransferEncoding(TransferEncoding transferEncoding) {
      this.transferEncoding = transferEncoding;
   }

   public boolean isChunked() {
      return this.isChunked;
   }

   public void setChunked(boolean isChunked) {
      if (this.getProtocol().compareTo(Protocol.HTTP_1_1) >= 0) {
         this.isChunked = isChunked;
         if (isChunked) {
            this.headers.removeHeader(Header.ContentLength);
         }
      } else {
         this.isChunked = false;
      }

   }

   public boolean isExpectContent() {
      return this.isExpectContent;
   }

   public void setExpectContent(boolean isExpectContent) {
      this.isExpectContent = isExpectContent;
   }

   public boolean isSkipRemainder() {
      return this.isSkipRemainder;
   }

   public void setSkipRemainder(boolean isSkipRemainder) {
      this.isSkipRemainder = isSkipRemainder;
   }

   public boolean isContentBroken() {
      return this.isContentBroken;
   }

   public void setContentBroken(boolean isBroken) {
      this.isContentBroken = isBroken;
   }

   public final String getUpgrade() {
      return this.upgrade.toString();
   }

   public DataChunk getUpgradeDC() {
      return this.upgrade;
   }

   public final void setUpgrade(String upgrade) {
      this.upgrade.setString(upgrade);
   }

   public boolean isUpgrade() {
      return !this.getUpgradeDC().isNull();
   }

   protected void makeUpgradeHeader() {
      if (!this.upgrade.isNull()) {
         this.headers.setValue(Header.Upgrade).set(this.upgrade);
      }

   }

   public boolean isIgnoreContentModifiers() {
      return this.isIgnoreContentModifiers;
   }

   public void setIgnoreContentModifiers(boolean isIgnoreContentModifiers) {
      this.isIgnoreContentModifiers = isIgnoreContentModifiers;
   }

   protected void makeContentLengthHeader(long defaultLength) {
      int start;
      if (this.contentLength != -1L) {
         start = HttpUtils.longToBuffer(this.contentLength, this.tmpContentLengthBuffer);
         this.headers.setValue(Header.ContentLength).setBytes(this.tmpContentLengthBuffer, start, this.tmpContentLengthBuffer.length);
      } else if (defaultLength != -1L) {
         start = HttpUtils.longToBuffer(defaultLength, this.tmpContentLengthBuffer);
         int idx = this.headers.indexOf((Header)Header.ContentLength, 0);
         if (idx == -1) {
            this.headers.addValue(Header.ContentLength).setBytes(this.tmpContentLengthBuffer, start, this.tmpContentLengthBuffer.length);
         } else if (this.headers.getValue(idx).isNull()) {
            this.headers.getValue(idx).setBytes(this.tmpContentLengthBuffer, start, this.tmpContentLengthBuffer.length);
         }
      }

   }

   public long getContentLength() {
      return this.contentLength;
   }

   public void setContentLength(int len) {
      this.setContentLengthLong((long)len);
   }

   public void setContentLengthLong(long contentLength) {
      this.contentLength = contentLength;
      boolean negativeLength = contentLength < 0L;
      if (negativeLength) {
         this.headers.removeHeader(Header.ContentLength);
      }

   }

   public boolean isCommitted() {
      return this.isCommitted;
   }

   public void setCommitted(boolean isCommitted) {
      this.isCommitted = isCommitted;
   }

   protected void makeTransferEncodingHeader(String defaultValue) {
      int idx = this.headers.indexOf((Header)Header.TransferEncoding, 0);
      if (idx == -1) {
         this.headers.addValue(Header.TransferEncoding).setBytes(CHUNKED_ENCODING_BYTES);
      }

   }

   protected void extractContentEncoding(DataChunk value) {
      int idx = this.headers.indexOf((Header)Header.ContentEncoding, 0);
      if (idx != -1) {
         this.headers.setSerialized(idx, true);
         value.set(this.headers.getValue(idx));
      }

   }

   public String getCharacterEncoding() {
      return this.contentType.getCharacterEncoding();
   }

   public void setCharacterEncoding(String charset) {
      if (!this.isCommitted()) {
         this.contentType.setCharacterEncoding(charset);
      }
   }

   public boolean isChunkingAllowed() {
      return this.chunkingAllowed;
   }

   public void setChunkingAllowed(boolean chunkingAllowed) {
      this.chunkingAllowed = chunkingAllowed;
   }

   public boolean isContentTypeSet() {
      return this.contentType.isMimeTypeSet() || this.headers.getValue(Header.ContentType) != null;
   }

   public String getContentType() {
      return this.contentType.get();
   }

   public void setContentType(String contentType) {
      this.contentType.set(contentType);
   }

   public void setContentType(ContentType contentType) {
      this.contentType.set(contentType);
   }

   protected ContentType getContentTypeHolder() {
      return this.contentType;
   }

   public MimeHeaders getHeaders() {
      return this.headers;
   }

   public String getHeader(String name) {
      if (name != null && !name.isEmpty()) {
         String result = this.handleGetSpecialHeader(name);
         return result != null ? result : this.headers.getHeader(name);
      } else {
         return null;
      }
   }

   public String getHeader(Header header) {
      if (header == null) {
         return null;
      } else {
         String result = this.handleGetSpecialHeader(header);
         return result != null ? result : this.headers.getHeader(header);
      }
   }

   public void setHeader(String name, String value) {
      if (name != null && value != null && !name.isEmpty()) {
         if (!this.handleSetSpecialHeaders(name, value)) {
            this.headers.setValue(name).setString(value);
         }
      }
   }

   public void setHeader(String name, HeaderValue value) {
      if (name != null && value != null && !name.isEmpty() && value.isSet()) {
         if (!this.handleSetSpecialHeaders(name, value)) {
            value.serializeToDataChunk(this.headers.setValue(name));
         }
      }
   }

   public void setHeader(Header header, String value) {
      if (header != null && value != null) {
         if (!this.handleSetSpecialHeaders(header, value)) {
            this.headers.setValue(header).setString(value);
         }
      }
   }

   public void setHeader(Header header, HeaderValue value) {
      if (header != null && value != null && value.isSet()) {
         if (!this.handleSetSpecialHeaders(header, value)) {
            value.serializeToDataChunk(this.headers.setValue(header));
         }
      }
   }

   public void addHeader(String name, String value) {
      if (name != null && value != null && !name.isEmpty()) {
         if (!this.handleSetSpecialHeaders(name, value)) {
            this.headers.addValue(name).setString(value);
         }
      }
   }

   public void addHeader(String name, HeaderValue value) {
      if (name != null && value != null && !name.isEmpty() && value.isSet()) {
         if (!this.handleSetSpecialHeaders(name, value)) {
            value.serializeToDataChunk(this.headers.setValue(name));
         }
      }
   }

   public void addHeader(Header header, String value) {
      if (header != null && value != null) {
         if (!this.handleSetSpecialHeaders(header, value)) {
            this.headers.addValue(header).setString(value);
         }
      }
   }

   public void addHeader(Header header, HeaderValue value) {
      if (header != null && value != null && value.isSet()) {
         if (!this.handleSetSpecialHeaders(header, value)) {
            value.serializeToDataChunk(this.headers.setValue(header));
         }
      }
   }

   public boolean containsHeader(String name) {
      if (name != null && !name.isEmpty()) {
         String result = this.handleGetSpecialHeader(name);
         return result != null || this.headers.getHeader(name) != null;
      } else {
         return false;
      }
   }

   public boolean containsHeader(Header header) {
      if (header == null) {
         return false;
      } else {
         String result = this.handleGetSpecialHeader(header);
         return result != null || this.headers.getHeader(header) != null;
      }
   }

   public DataChunk getProtocolDC() {
      this.parsedProtocol = null;
      return this.protocolC;
   }

   public String getProtocolString() {
      return this.parsedProtocol == null ? this.getProtocolDC().toString() : this.parsedProtocol.getProtocolString();
   }

   public Protocol getProtocol() {
      if (this.parsedProtocol != null) {
         return this.parsedProtocol;
      } else {
         this.parsedProtocol = Protocol.valueOf(this.protocolC);
         return this.parsedProtocol;
      }
   }

   public void setProtocol(Protocol protocol) {
      this.parsedProtocol = protocol;
   }

   public boolean isSecure() {
      return this.secure;
   }

   public void setSecure(boolean secure) {
      this.secure = secure;
   }

   public final HttpContent.Builder httpContentBuilder() {
      return HttpContent.builder(this);
   }

   public HttpTrailer.Builder httpTrailerBuilder() {
      return HttpTrailer.builder(this);
   }

   protected void reset() {
      this.isContentEncodingsSelected = false;
      this.secure = false;
      this.isSkipRemainder = false;
      this.isContentBroken = false;
      if (this.activeAttributes != null) {
         this.activeAttributes.recycle();
         this.activeAttributes = null;
      }

      this.protocolC.recycle();
      this.parsedProtocol = null;
      this.contentEncodings.clear();
      this.headers.clear();
      this.isCommitted = false;
      this.isChunked = false;
      this.contentLength = -1L;
      this.contentType.reset();
      this.chunkingAllowed = false;
      this.transferEncoding = null;
      this.isExpectContent = true;
      this.upgrade.recycle();
      this.isIgnoreContentModifiers = false;
      if (this.headerBuffer != null) {
         this.headerBuffer.dispose();
         this.headerBuffer = null;
      }

   }

   public void recycle() {
      this.reset();
   }

   private final String handleGetSpecialHeader(String name) {
      return isSpecialHeader(name) ? this.getValueBasedOnHeader(name) : null;
   }

   private final String handleGetSpecialHeader(Header header) {
      return isSpecialHeader(header.toString()) ? this.getValueBasedOnHeader(header) : null;
   }

   private final boolean handleSetSpecialHeaders(String name, String value) {
      return isSpecialHeaderSet(name) && this.setValueBasedOnHeader(name, value);
   }

   private final boolean handleSetSpecialHeaders(String name, HeaderValue value) {
      return isSpecialHeaderSet(name) && this.setValueBasedOnHeader(name, value.get());
   }

   private final boolean handleSetSpecialHeaders(Header header, String value) {
      return isSpecialHeaderSet(header.toString()) && this.setValueBasedOnHeader(header, value);
   }

   private final boolean handleSetSpecialHeaders(Header header, HeaderValue value) {
      return isSpecialHeaderSet(header.toString()) && this.setValueBasedOnHeader(header, value.get());
   }

   private static boolean isSpecialHeader(String name) {
      return isSpecialHeader(name.charAt(0));
   }

   private static boolean isSpecialHeaderSet(String name) {
      char c = name.charAt(0);
      return isSpecialHeader(c) || c == 'T' || c == 't';
   }

   private static boolean isSpecialHeader(char c) {
      return c == 'C' || c == 'c' || c == 'U' || c == 'u';
   }

   public byte[] getTempHeaderEncodingBuffer() {
      return this.tmpHeaderEncodingBuffer;
   }

   private String getValueBasedOnHeader(Header header) {
      if (Header.ContentType.equals(header)) {
         String value = this.getContentType();
         if (value != null) {
            return value;
         }
      } else if (Header.ContentLength.equals(header)) {
         long value = this.getContentLength();
         if (value >= 0L) {
            return Long.toString(value);
         }
      } else if (Header.Upgrade.equals(header)) {
         return this.getUpgrade();
      }

      return null;
   }

   private String getValueBasedOnHeader(String name) {
      if (Header.ContentType.toString().equalsIgnoreCase(name)) {
         String value = this.getContentType();
         if (value != null) {
            return value;
         }
      } else if (Header.ContentLength.toString().equalsIgnoreCase(name)) {
         long value = this.getContentLength();
         if (value >= 0L) {
            return Long.toString(value);
         }
      } else if (Header.Upgrade.toString().equalsIgnoreCase(name)) {
         return this.getUpgrade();
      }

      return null;
   }

   private boolean setValueBasedOnHeader(String name, String value) {
      if (Header.ContentType.toString().equalsIgnoreCase(name)) {
         this.setContentType(value);
         return true;
      } else if (Header.ContentLength.toString().equalsIgnoreCase(name)) {
         this.headers.removeHeader(Header.TransferEncoding);
         this.setChunked(false);
         return this.setContentLenth(value);
      } else {
         if (Header.Upgrade.toString().equalsIgnoreCase(name)) {
            this.setUpgrade(value);
         } else if (Header.TransferEncoding.toString().equalsIgnoreCase(name)) {
            if ("chunked".equalsIgnoreCase(value)) {
               this.setContentLengthLong(-1L);
               this.setChunked(true);
            }

            return true;
         }

         return false;
      }
   }

   private boolean setValueBasedOnHeader(Header header, String value) {
      if (Header.ContentType.equals(header)) {
         this.setContentType(value);
         return true;
      } else if (Header.ContentLength.equals(header)) {
         this.headers.removeHeader(Header.TransferEncoding);
         this.setChunked(false);
         return this.setContentLenth(value);
      } else {
         if (Header.Upgrade.equals(header)) {
            this.setUpgrade(value);
         } else if (Header.TransferEncoding.equals(header)) {
            if ("chunked".equalsIgnoreCase(value)) {
               this.setContentLengthLong(-1L);
               this.setChunked(true);
            }

            return true;
         }

         return false;
      }
   }

   private boolean setContentLenth(String value) {
      try {
         long cLL = Long.parseLong(value);
         this.setContentLengthLong(cLL);
         return true;
      } catch (NumberFormatException var4) {
         return false;
      }
   }

   protected void flushSpecialHeaders() {
      if (this.contentLength >= 0L) {
         this.headers.setValue(Header.ContentLength).setString(String.valueOf(this.contentLength));
      }

      String ct = this.getContentType();
      if (ct != null) {
         this.headers.setValue(Header.ContentType).setString(String.valueOf(ct));
      }

      if (!this.upgrade.isNull()) {
         this.headers.setValue(Header.Upgrade).setString(this.upgrade.toString());
      }

   }

   static {
      CHUNKED_ENCODING_BYTES = "chunked".getBytes(Charsets.ASCII_CHARSET);
   }

   public abstract static class Builder {
      protected Protocol protocol;
      protected String protocolString;
      protected Boolean chunked;
      protected Long contentLength;
      protected String contentType;
      protected String upgrade;
      protected MimeHeaders mimeHeaders;

      public final Builder protocol(Protocol protocol) {
         this.protocol = protocol;
         this.protocolString = null;
         return this;
      }

      public final Builder protocol(String protocolString) {
         this.protocolString = protocolString;
         this.protocol = null;
         return this;
      }

      public final Builder chunked(boolean chunked) {
         this.chunked = chunked;
         this.contentLength = null;
         return this;
      }

      public final Builder contentLength(long contentLength) {
         this.contentLength = contentLength;
         this.chunked = null;
         return this;
      }

      public final Builder contentType(String contentType) {
         this.contentType = contentType;
         return this;
      }

      public final Builder upgrade(String upgrade) {
         this.upgrade = upgrade;
         return this;
      }

      public final Builder header(String name, String value) {
         if (this.mimeHeaders == null) {
            this.mimeHeaders = new MimeHeaders();
         }

         this.mimeHeaders.addValue(name).setString(value);
         this.handleSpecialHeaderAdd(name, value);
         return this;
      }

      public final Builder removeHeader(String header) {
         if (this.mimeHeaders != null) {
            this.mimeHeaders.removeHeader(header);
            this.handleSpecialHeaderRemove(header);
         }

         return this;
      }

      public final Builder header(Header header, String value) {
         if (this.mimeHeaders == null) {
            this.mimeHeaders = new MimeHeaders();
         }

         this.mimeHeaders.addValue(header).setString(value);
         this.handleSpecialHeaderAdd(header.toString(), value);
         return this;
      }

      public final Builder removeHeader(Header header) {
         if (this.mimeHeaders != null) {
            this.mimeHeaders.removeHeader(header);
            this.handleSpecialHeaderRemove(header.toString());
         }

         return this;
      }

      public final Builder maxNumHeaders(int maxHeaders) {
         if (this.mimeHeaders == null) {
            this.mimeHeaders = new MimeHeaders();
         }

         this.mimeHeaders.setMaxNumHeaders(maxHeaders);
         return this;
      }

      public HttpHeader build() {
         HttpHeader httpHeader = this.create();
         if (this.protocol != null) {
            httpHeader.setProtocol(this.protocol);
         }

         if (this.protocolString != null) {
            httpHeader.protocolC.setString(this.protocolString);
         }

         if (this.chunked != null) {
            httpHeader.setChunked(this.chunked);
         }

         if (this.contentLength != null) {
            httpHeader.setContentLengthLong(this.contentLength);
         }

         if (this.contentType != null) {
            httpHeader.setContentType(this.contentType);
         }

         if (this.upgrade != null) {
            httpHeader.setUpgrade(this.upgrade);
         }

         if (this.mimeHeaders != null && this.mimeHeaders.size() > 0) {
            httpHeader.getHeaders().copyFrom(this.mimeHeaders);
         }

         return httpHeader;
      }

      public void reset() {
         this.protocol = null;
         this.protocolString = null;
         this.chunked = null;
         this.contentLength = null;
         this.contentType = null;
         this.upgrade = null;
         this.mimeHeaders.recycle();
      }

      protected abstract HttpHeader create();

      private void handleSpecialHeaderAdd(String name, String value) {
         char c = name.charAt(0);
         boolean isC = c == 'c' || c == 'C';
         if (isC && Header.ContentLength.toString().equals(name)) {
            this.contentLength = Long.parseLong(value);
         } else {
            boolean isU = c == 'u' || c == 'U';
            if (isU && Header.Upgrade.toString().equals(name)) {
               this.upgrade = value;
            }

         }
      }

      private void handleSpecialHeaderRemove(String name) {
         char c = name.charAt(0);
         boolean isC = c == 'c' || c == 'C';
         if (isC && Header.ContentLength.toString().equals(name)) {
            this.contentLength = null;
         } else {
            boolean isU = c == 'u' || c == 'U';
            if (isU && Header.Upgrade.toString().equals(name)) {
               this.upgrade = null;
            }

         }
      }
   }
}
