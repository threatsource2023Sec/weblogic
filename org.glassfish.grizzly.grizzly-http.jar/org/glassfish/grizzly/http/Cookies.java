package org.glassfish.grizzly.http;

import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.glassfish.grizzly.Grizzly;
import org.glassfish.grizzly.http.util.BufferChunk;
import org.glassfish.grizzly.http.util.ByteChunk;
import org.glassfish.grizzly.http.util.CookieParserUtils;
import org.glassfish.grizzly.http.util.CookieUtils;
import org.glassfish.grizzly.http.util.DataChunk;
import org.glassfish.grizzly.http.util.Header;
import org.glassfish.grizzly.http.util.MimeHeaders;

public final class Cookies {
   private static final Cookie[] EMPTY_COOKIE_ARRAY = new Cookie[0];
   private static final Logger logger = Grizzly.logger(Cookies.class);
   private static final int INITIAL_SIZE = 4;
   private Cookie[] cookies = new Cookie[4];
   private Cookie[] processedCookies;
   private boolean isProcessed;
   private boolean isRequest;
   private MimeHeaders headers;
   private int nextUnusedCookieIndex = 0;
   private int storedCookieCount;
   static final char[] SEPARATORS = new char[]{'\t', ' ', '"', '\'', '(', ')', ',', ':', ';', '<', '=', '>', '?', '@', '[', '\\', ']', '{', '}'};
   static final boolean[] separators = new boolean[128];

   public boolean initialized() {
      return this.headers != null;
   }

   public Cookie[] get() {
      if (!this.isProcessed) {
         this.isProcessed = true;
         if (this.isRequest) {
            this.processClientCookies();
         } else {
            this.processServerCookies();
         }

         this.processedCookies = this.nextUnusedCookieIndex > 0 ? this.copyTo(new Cookie[this.nextUnusedCookieIndex]) : EMPTY_COOKIE_ARRAY;
      }

      return this.processedCookies;
   }

   public void setHeaders(MimeHeaders headers) {
      this.setHeaders(headers, true);
   }

   public void setHeaders(MimeHeaders headers, boolean isRequest) {
      this.headers = headers;
      this.isRequest = isRequest;
   }

   public Cookie getNextUnusedCookie() {
      if (this.nextUnusedCookieIndex < this.storedCookieCount) {
         return this.cookies[this.nextUnusedCookieIndex++];
      } else {
         Cookie cookie = new Cookie();
         if (this.nextUnusedCookieIndex == this.cookies.length) {
            Cookie[] temp = new Cookie[this.cookies.length + 4];
            System.arraycopy(this.cookies, 0, temp, 0, this.cookies.length);
            this.cookies = temp;
         }

         ++this.storedCookieCount;
         this.cookies[this.nextUnusedCookieIndex++] = cookie;
         return cookie;
      }
   }

   public void recycle() {
      for(int i = 0; i < this.nextUnusedCookieIndex; ++i) {
         this.cookies[i].recycle();
      }

      this.processedCookies = null;
      this.nextUnusedCookieIndex = 0;
      this.headers = null;
      this.isRequest = false;
      this.isProcessed = false;
   }

   private Cookie[] copyTo(Cookie[] destination) {
      if (this.nextUnusedCookieIndex > 0) {
         System.arraycopy(this.cookies, 0, destination, 0, this.nextUnusedCookieIndex);
      }

      return destination;
   }

   private void processClientCookies() {
      if (this.headers != null) {
         int pos = 0;

         while(pos >= 0) {
            pos = this.headers.indexOf(Header.Cookie, pos);
            if (pos < 0) {
               break;
            }

            DataChunk cookieValue = this.headers.getValue(pos);
            if (cookieValue != null && !cookieValue.isNull()) {
               if (cookieValue.getType() == DataChunk.Type.Bytes) {
                  if (logger.isLoggable(Level.FINE)) {
                     log("Parsing b[]: " + cookieValue.toString());
                  }

                  ByteChunk byteChunk = cookieValue.getByteChunk();
                  CookieParserUtils.parseClientCookies(this, byteChunk.getBuffer(), byteChunk.getStart(), byteChunk.getLength());
               } else if (cookieValue.getType() == DataChunk.Type.Buffer) {
                  if (logger.isLoggable(Level.FINE)) {
                     log("Parsing buffer: " + cookieValue.toString());
                  }

                  BufferChunk bufferChunk = cookieValue.getBufferChunk();
                  CookieParserUtils.parseClientCookies(this, bufferChunk.getBuffer(), bufferChunk.getStart(), bufferChunk.getLength());
               } else {
                  if (logger.isLoggable(Level.FINE)) {
                     log("Parsing string: " + cookieValue.toString());
                  }

                  String value = cookieValue.toString();
                  CookieParserUtils.parseClientCookies(this, value, CookieUtils.COOKIE_VERSION_ONE_STRICT_COMPLIANCE, CookieUtils.RFC_6265_SUPPORT_ENABLED);
               }

               ++pos;
            } else {
               ++pos;
            }
         }

      }
   }

   private void processServerCookies() {
      if (this.headers != null) {
         int pos = 0;

         while(pos >= 0) {
            pos = this.headers.indexOf(Header.SetCookie, pos);
            if (pos < 0) {
               break;
            }

            DataChunk cookieValue = this.headers.getValue(pos);
            if (cookieValue != null && !cookieValue.isNull()) {
               if (cookieValue.getType() == DataChunk.Type.Bytes) {
                  if (logger.isLoggable(Level.FINE)) {
                     log("Parsing b[]: " + cookieValue.toString());
                  }

                  ByteChunk byteChunk = cookieValue.getByteChunk();
                  CookieParserUtils.parseServerCookies(this, byteChunk.getBuffer(), byteChunk.getStart(), byteChunk.getLength(), CookieUtils.COOKIE_VERSION_ONE_STRICT_COMPLIANCE, CookieUtils.RFC_6265_SUPPORT_ENABLED);
               } else if (cookieValue.getType() == DataChunk.Type.Buffer) {
                  if (logger.isLoggable(Level.FINE)) {
                     log("Parsing b[]: " + cookieValue.toString());
                  }

                  BufferChunk bufferChunk = cookieValue.getBufferChunk();
                  CookieParserUtils.parseServerCookies(this, bufferChunk.getBuffer(), bufferChunk.getStart(), bufferChunk.getLength(), CookieUtils.COOKIE_VERSION_ONE_STRICT_COMPLIANCE, CookieUtils.RFC_6265_SUPPORT_ENABLED);
               } else {
                  if (logger.isLoggable(Level.FINE)) {
                     log("Parsing string: " + cookieValue.toString());
                  }

                  String value = cookieValue.toString();
                  CookieParserUtils.parseServerCookies(this, value, CookieUtils.COOKIE_VERSION_ONE_STRICT_COMPLIANCE, CookieUtils.RFC_6265_SUPPORT_ENABLED);
               }

               ++pos;
            } else {
               ++pos;
            }
         }

      }
   }

   public String toString() {
      return Arrays.toString(this.cookies);
   }

   private static void log(String s) {
      if (logger.isLoggable(Level.FINE)) {
         logger.log(Level.FINE, "Cookies: {0}", s);
      }

   }

   public Cookie findByName(String cookieName) {
      Cookie[] cookiesArray = this.get();
      Cookie[] var3 = cookiesArray;
      int var4 = cookiesArray.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         Cookie cookie = var3[var5];
         if (cookie.lazyNameEquals(cookieName)) {
            return cookie;
         }
      }

      return null;
   }

   static {
      int i;
      for(i = 0; i < 128; ++i) {
         separators[i] = false;
      }

      for(i = 0; i < SEPARATORS.length; ++i) {
         separators[SEPARATORS[i]] = true;
      }

   }
}
