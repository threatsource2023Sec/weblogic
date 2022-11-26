package org.glassfish.grizzly.http.util;

import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;
import org.glassfish.grizzly.utils.Charsets;

public enum Header {
   Accept("Accept"),
   AcceptCharset("Accept-Charset"),
   AcceptEncoding("Accept-Encoding"),
   AcceptRanges("Accept-Ranges"),
   Age("Age"),
   Allow("Allow"),
   Authorization("Authorization"),
   CacheControl("Cache-Control"),
   Cookie("Cookie"),
   Connection("Connection"),
   ContentDisposition("Content-Disposition"),
   ContentEncoding("Content-Encoding"),
   ContentLanguage("Content-Language"),
   ContentLength("Content-Length"),
   ContentLocation("Content-Location"),
   ContentMD5("Content-MD5"),
   ContentRange("Content-Range"),
   ContentType("Content-Type"),
   Date("Date"),
   ETag("ETag"),
   Expect("Expect"),
   Expires("Expires"),
   From("From"),
   Host("Host"),
   IfMatch("If-Match"),
   IfModifiedSince("If-Modified-Since"),
   IfNoneMatch("If-None-Match"),
   IfRange("If-Range"),
   IfUnmodifiedSince("If-Unmodified-Since"),
   KeepAlive("Keep-Alive"),
   LastModified("Last-Modified"),
   Location("Location"),
   MaxForwards("Max-Forwards"),
   Pragma("Pragma"),
   ProxyAuthenticate("Proxy-Authenticate"),
   ProxyAuthorization("Proxy-Authorization"),
   ProxyConnection("Proxy-Connection"),
   Range("Range"),
   Referer("Referer"),
   RetryAfter("Retry-After"),
   Server("Server"),
   SetCookie("Set-Cookie"),
   TE("TE"),
   Trailer("Trailer"),
   TransferEncoding("Transfer-Encoding"),
   Upgrade("Upgrade"),
   UserAgent("User-Agent"),
   Vary("Vary"),
   Via("Via"),
   Warnings("Warning"),
   WWWAuthenticate("WWW-Authenticate"),
   XPoweredBy("X-Powered-By"),
   HTTP2Settings("HTTP2-Settings");

   private static final Map VALUES = new TreeMap(String.CASE_INSENSITIVE_ORDER);
   private final byte[] headerNameBytes;
   private final byte[] headerNameLowerCaseBytes;
   private final String headerName;
   private final String headerNameLowerCase;
   private final int length;

   private Header(String headerName) {
      this.headerName = headerName;
      this.headerNameBytes = headerName.getBytes(Charsets.ASCII_CHARSET);
      this.headerNameLowerCase = headerName.toLowerCase(Locale.ENGLISH);
      this.headerNameLowerCaseBytes = this.headerNameLowerCase.getBytes(Charsets.ASCII_CHARSET);
      this.length = this.headerNameBytes.length;
   }

   public final byte[] getBytes() {
      return this.headerNameBytes;
   }

   public final String getLowerCase() {
      return this.headerNameLowerCase;
   }

   public final byte[] getLowerCaseBytes() {
      return this.headerNameLowerCaseBytes;
   }

   public final int getLength() {
      return this.length;
   }

   public final String toString() {
      return this.headerName;
   }

   public final byte[] toByteArray() {
      return this.headerNameBytes;
   }

   public static Header find(String name) {
      return name != null && !name.isEmpty() ? (Header)VALUES.get(name) : null;
   }

   static {
      Header[] var0 = values();
      int var1 = var0.length;

      for(int var2 = 0; var2 < var1; ++var2) {
         Header h = var0[var2];
         VALUES.put(h.toString(), h);
      }

   }
}
