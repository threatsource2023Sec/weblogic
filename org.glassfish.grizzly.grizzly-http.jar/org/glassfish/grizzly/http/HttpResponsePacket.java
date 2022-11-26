package org.glassfish.grizzly.http;

import java.util.Iterator;
import java.util.Locale;
import org.glassfish.grizzly.Buffer;
import org.glassfish.grizzly.http.util.DataChunk;
import org.glassfish.grizzly.http.util.HttpStatus;
import org.glassfish.grizzly.http.util.MimeHeaders;

public abstract class HttpResponsePacket extends HttpHeader {
   private HttpRequestPacket request;
   private Locale locale;
   private String contentLanguage;
   protected HttpStatus httpStatus;
   private final DataChunk reasonPhraseC = DataChunk.newInstance();
   private boolean acknowledgment;
   private boolean allowCustomReasonPhrase = true;
   private boolean isHtmlEncodingCustomReasonPhrase = true;

   public static Builder builder(HttpRequestPacket request) {
      return (new Builder()).requestPacket(request);
   }

   protected HttpResponsePacket() {
   }

   public int getStatus() {
      return this.getHttpStatus().getStatusCode();
   }

   public HttpStatus getHttpStatus() {
      if (this.httpStatus == null) {
         this.httpStatus = HttpStatus.OK_200;
      }

      return this.httpStatus;
   }

   public void setStatus(int status) {
      this.httpStatus = HttpStatus.getHttpStatus(status);
   }

   public void setStatus(HttpStatus status) {
      this.httpStatus = status;
      this.reasonPhraseC.recycle();
   }

   public final boolean isAllowCustomReasonPhrase() {
      return this.allowCustomReasonPhrase;
   }

   public final void setAllowCustomReasonPhrase(boolean allowCustomReasonPhrase) {
      this.allowCustomReasonPhrase = allowCustomReasonPhrase;
   }

   public boolean isHtmlEncodingCustomReasonPhrase() {
      return this.isHtmlEncodingCustomReasonPhrase;
   }

   public void setHtmlEncodingCustomReasonPhrase(boolean isHtmlEncodingCustomReasonPhrase) {
      this.isHtmlEncodingCustomReasonPhrase = isHtmlEncodingCustomReasonPhrase;
   }

   public final DataChunk getReasonPhraseRawDC() {
      return this.reasonPhraseC;
   }

   public final DataChunk getReasonPhraseDC() {
      if (this.isCustomReasonPhraseSet()) {
         return this.reasonPhraseC;
      } else {
         this.reasonPhraseC.setBytes(this.httpStatus.getReasonPhraseBytes());
         return this.reasonPhraseC;
      }
   }

   public final String getReasonPhrase() {
      return this.getReasonPhraseDC().toString();
   }

   public void setReasonPhrase(String message) {
      this.reasonPhraseC.setString(message);
   }

   public void setReasonPhrase(Buffer reason) {
      this.reasonPhraseC.setBuffer(reason, reason.position(), reason.limit());
   }

   public final boolean isCustomReasonPhraseSet() {
      return this.allowCustomReasonPhrase && !this.reasonPhraseC.isNull();
   }

   public HttpRequestPacket getRequest() {
      return this.request;
   }

   public boolean isAcknowledgement() {
      return this.acknowledgment;
   }

   public void setAcknowledgement(boolean acknowledgement) {
      this.acknowledgment = acknowledgement;
   }

   public void acknowledged() {
      this.request.requiresAcknowledgement(false);
      this.acknowledgment = false;
      this.httpStatus = null;
      this.reasonPhraseC.recycle();
   }

   protected void reset() {
      this.httpStatus = null;
      this.acknowledgment = false;
      this.allowCustomReasonPhrase = true;
      this.isHtmlEncodingCustomReasonPhrase = true;
      this.reasonPhraseC.recycle();
      this.locale = null;
      this.contentLanguage = null;
      this.request = null;
      super.reset();
   }

   public final boolean isRequest() {
      return false;
   }

   public String toString() {
      StringBuilder sb = new StringBuilder(256);
      sb.append("HttpResponsePacket (\n  status=").append(this.getStatus()).append("\n  reason=").append(this.getReasonPhrase()).append("\n  protocol=").append(this.getProtocol().getProtocolString()).append("\n  content-length=").append(this.getContentLength()).append("\n  committed=").append(this.isCommitted()).append("\n  headers=[");
      MimeHeaders headersLocal = this.getHeaders();
      Iterator var3 = headersLocal.names().iterator();

      while(var3.hasNext()) {
         String name = (String)var3.next();
         Iterator var5 = headersLocal.values(name).iterator();

         while(var5.hasNext()) {
            String value = (String)var5.next();
            sb.append("\n      ").append(name).append('=').append(value);
         }
      }

      sb.append("]\n)");
      return sb.toString();
   }

   public Locale getLocale() {
      return this.locale;
   }

   public void setLocale(Locale locale) {
      if (locale != null) {
         this.locale = locale;
         this.contentLanguage = locale.getLanguage();
         if (this.contentLanguage != null && this.contentLanguage.length() > 0) {
            String country = locale.getCountry();
            if (country != null && country.length() > 0) {
               StringBuilder value = new StringBuilder(this.contentLanguage);
               value.append('-');
               value.append(country);
               this.contentLanguage = value.toString();
            }
         }

      }
   }

   public String getContentLanguage() {
      return this.contentLanguage;
   }

   public void setContentLanguage(String contentLanguage) {
      this.contentLanguage = contentLanguage;
   }

   public void setContentLengthLong(long contentLength) {
      this.setChunked(contentLength < 0L);
      super.setContentLengthLong(contentLength);
   }

   public void setRequest(HttpRequestPacket request) {
      this.request = request;
   }

   public static class Builder extends HttpHeader.Builder {
      protected Integer status;
      protected String reasonPhrase;
      protected HttpRequestPacket requestPacket;

      public Builder status(int status) {
         this.status = status;
         return this;
      }

      public Builder reasonPhrase(String reasonPhrase) {
         this.reasonPhrase = reasonPhrase;
         return this;
      }

      public Builder requestPacket(HttpRequestPacket requestPacket) {
         this.requestPacket = requestPacket;
         return this;
      }

      public final HttpResponsePacket build() {
         HttpResponsePacket responsePacket = (HttpResponsePacket)super.build();
         if (this.status != null) {
            responsePacket.setStatus(this.status);
         }

         if (this.reasonPhrase != null) {
            responsePacket.setReasonPhrase(this.reasonPhrase);
         }

         return responsePacket;
      }

      public void reset() {
         super.reset();
         this.status = null;
         this.reasonPhrase = null;
      }

      protected HttpHeader create() {
         if (this.requestPacket == null) {
            throw new IllegalStateException("Unable to create new HttpResponsePacket.  No HttpRequestPacket available.");
         } else {
            HttpResponsePacket responsePacket = this.requestPacket.getResponse();
            if (responsePacket == null) {
               responsePacket = HttpResponsePacketImpl.create();
               ((HttpResponsePacket)responsePacket).setRequest(this.requestPacket);
               ((HttpResponsePacket)responsePacket).setSecure(this.requestPacket.isSecure());
            }

            return (HttpHeader)responsePacket;
         }
      }
   }
}
