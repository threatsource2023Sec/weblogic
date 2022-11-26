package org.glassfish.grizzly.http.util;

import java.util.HashMap;
import java.util.Map;
import org.glassfish.grizzly.http.HttpResponsePacket;
import org.glassfish.grizzly.memory.Buffers;
import org.glassfish.grizzly.memory.MemoryManager;
import org.glassfish.grizzly.utils.Charsets;

public class HttpStatus {
   private static final Map statusMessages = new HashMap();
   public static final HttpStatus CONINTUE_100 = register(100, "Continue");
   public static final HttpStatus SWITCHING_PROTOCOLS_101 = register(101, "Switching Protocols");
   public static final HttpStatus WEB_SOCKET_PROTOCOL_HANDSHAKE_101 = register(101, "Web Socket Protocol Handshake");
   public static final HttpStatus OK_200 = register(200, "OK");
   public static final HttpStatus CREATED_201 = register(201, "Created");
   public static final HttpStatus ACCEPTED_202 = register(202, "Accepted");
   public static final HttpStatus NON_AUTHORATIVE_INFORMATION_203 = register(203, "Not-Authoritative Information");
   public static final HttpStatus NO_CONTENT_204 = register(204, "No Content");
   public static final HttpStatus RESET_CONTENT_205 = register(205, "Reset Content");
   public static final HttpStatus PARTIAL_CONTENT_206 = register(206, "Partial Content");
   public static final HttpStatus MULTIPLE_CHOICES_300 = register(300, "Multiple Choices");
   public static final HttpStatus MOVED_PERMANENTLY_301 = register(301, "Moved Permanently");
   public static final HttpStatus FOUND_302 = register(302, "Found");
   public static final HttpStatus SEE_OTHER_303 = register(303, "See Other");
   public static final HttpStatus NOT_MODIFIED_304 = register(304, "Not Modified");
   public static final HttpStatus USE_PROXY_305 = register(305, "Use Proxy");
   public static final HttpStatus TEMPORARY_REDIRECT_307 = register(307, "Temporary Redirect");
   public static final HttpStatus PERMANENT_REDIRECT_308 = register(308, "Permanent Redirect");
   public static final HttpStatus BAD_REQUEST_400 = register(400, "Bad Request");
   public static final HttpStatus UNAUTHORIZED_401 = register(401, "Unauthorized");
   public static final HttpStatus PAYMENT_REQUIRED_402 = register(402, "Payment Required");
   public static final HttpStatus FORBIDDEN_403 = register(403, "Forbidden");
   public static final HttpStatus NOT_FOUND_404 = register(404, "Not Found");
   public static final HttpStatus METHOD_NOT_ALLOWED_405 = register(405, "Method Not Allowed");
   public static final HttpStatus NOT_ACCEPTABLE_406 = register(406, "Not Acceptable");
   public static final HttpStatus PROXY_AUTHENTICATION_REQUIRED_407 = register(407, "Proxy Authentication Required");
   public static final HttpStatus REQUEST_TIMEOUT_408 = register(408, "Request Timeout");
   public static final HttpStatus CONFLICT_409 = register(409, "Conflict");
   public static final HttpStatus GONE_410 = register(410, "Gone");
   public static final HttpStatus LENGTH_REQUIRED_411 = register(411, "Length Required");
   public static final HttpStatus PRECONDITION_FAILED_412 = register(412, "Precondition Failed");
   public static final HttpStatus REQUEST_ENTITY_TOO_LARGE_413 = register(413, "Request Entity Too Large");
   public static final HttpStatus REQUEST_URI_TOO_LONG_414 = register(414, "Request-URI Too Long");
   public static final HttpStatus UNSUPPORTED_MEDIA_TYPE_415 = register(415, "Unsupported Media Type");
   public static final HttpStatus REQUEST_RANGE_NOT_SATISFIABLE_416 = register(416, "Request Range Not Satisfiable");
   public static final HttpStatus EXPECTATION_FAILED_417 = register(417, "Expectation Failed");
   public static final HttpStatus MISDIRECTED_REQUEST = register(421, "Misdirected Request");
   public static final HttpStatus REQUEST_HEADER_FIELDS_TOO_LARGE = register(431, "Request Header Fields Too Large");
   public static final HttpStatus INTERNAL_SERVER_ERROR_500 = register(500, "Internal Server Error");
   public static final HttpStatus NOT_IMPLEMENTED_501 = register(501, "Not Implemented");
   public static final HttpStatus BAD_GATEWAY_502 = register(502, "Bad Gateway");
   public static final HttpStatus SERVICE_UNAVAILABLE_503 = register(503, "Service Unavailable");
   public static final HttpStatus GATEWAY_TIMEOUT_504 = register(504, "Gateway Timeout");
   public static final HttpStatus HTTP_VERSION_NOT_SUPPORTED_505 = register(505, "HTTP Version Not Supported");
   private final int status;
   private final String reasonPhrase;
   private final byte[] reasonPhraseBytes;
   private final byte[] statusBytes;

   private static HttpStatus register(int statusCode, String reasonPhrase) {
      HttpStatus httpStatus = newHttpStatus(statusCode, reasonPhrase);
      statusMessages.put(statusCode, httpStatus);
      return httpStatus;
   }

   public static HttpStatus newHttpStatus(int statusCode, String reasonPhrase) {
      return new HttpStatus(statusCode, reasonPhrase);
   }

   public static HttpStatus getHttpStatus(int statusCode) {
      HttpStatus status = (HttpStatus)statusMessages.get(statusCode);
      if (status == null) {
         status = new HttpStatus(statusCode, "CUSTOM");
      }

      return status;
   }

   private HttpStatus(int status, String reasonPhrase) {
      this.status = status;
      this.reasonPhrase = reasonPhrase;
      this.reasonPhraseBytes = reasonPhrase.getBytes(Charsets.ASCII_CHARSET);
      this.statusBytes = Integer.toString(status).getBytes(Charsets.ASCII_CHARSET);
   }

   public boolean statusMatches(int status) {
      return status == this.status;
   }

   public int getStatusCode() {
      return this.status;
   }

   public byte[] getStatusBytes() {
      return this.statusBytes;
   }

   public String getReasonPhrase() {
      return this.reasonPhrase;
   }

   public byte[] getReasonPhraseBytes() {
      return this.reasonPhraseBytes;
   }

   public void setValues(HttpResponsePacket response) {
      response.setStatus(this);
      response.setReasonPhrase(Buffers.wrap((MemoryManager)null, this.reasonPhraseBytes));
   }
}
