package com.bea.httppubsub.internal;

import com.bea.httppubsub.Client;
import java.io.UnsupportedEncodingException;
import weblogic.servlet.http.RequestResponseKey;

public class LongPollingTransport extends AbstractTransport {
   public static final String CONTENT_TYPE = "text/json";
   public static final String COMMENTED_CONTENT_TYPE = "text/json-comment-filtered";
   public static final String COMMENT_HEADER = "/*[";
   public static final String HEADER = "[";
   public static final String COMMENT_TAILER = "]*/";
   public static final String TAILER = "]";
   public static final String SEPERATOR = ",";
   private static byte[] COMMENT_HEADER_BYTES;
   private static byte[] HEADER_BYTES;
   private static byte[] COMMENT_TAILER_BYTES;
   private static byte[] TAILER_BYTES;
   private static byte[] SEPERATOR_BYTES;

   public LongPollingTransport(RequestResponseKey rrk, String cookiePath) {
      super(rrk, cookiePath);
   }

   protected Client.ConnectionType getConnectionType() {
      return Client.ConnectionType.LONGPOLLING;
   }

   protected String getHeader() {
      return this.isCommentFiltered ? "/*[" : "[";
   }

   protected String getTailer() {
      return this.isCommentFiltered ? "]*/" : "]";
   }

   protected String getSeperator() {
      return ",";
   }

   protected byte[] getHeaderBytes() {
      return this.isCommentFiltered ? COMMENT_HEADER_BYTES : HEADER_BYTES;
   }

   protected byte[] getTailerBytes() {
      return this.isCommentFiltered ? COMMENT_TAILER_BYTES : TAILER_BYTES;
   }

   protected byte[] getSeperatorBytes() {
      return SEPERATOR_BYTES;
   }

   protected String getResponseContentType() {
      return this.isCommentFiltered ? "text/json-comment-filtered" : "text/json";
   }

   static {
      try {
         String encoding = "ISO-8859-1";
         COMMENT_HEADER_BYTES = "/*[".getBytes(encoding);
         HEADER_BYTES = "[".getBytes(encoding);
         COMMENT_TAILER_BYTES = "]*/".getBytes(encoding);
         TAILER_BYTES = "]".getBytes(encoding);
         SEPERATOR_BYTES = ",".getBytes(encoding);
      } catch (UnsupportedEncodingException var1) {
      }

   }
}
