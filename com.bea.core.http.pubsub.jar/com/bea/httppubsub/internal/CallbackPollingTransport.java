package com.bea.httppubsub.internal;

import com.bea.httppubsub.Client;
import java.io.UnsupportedEncodingException;
import weblogic.servlet.http.RequestResponseKey;

public class CallbackPollingTransport extends AbstractTransport {
   public static final String CONTENT_TYPE = "text/javascript";
   public static final String DEFAULT_JSON_PARAMETER = "jsonpcallback";
   public static final String HEADER = "([";
   public static final String TAILER = "])";
   public static final String SEPERATOR = ",";
   private static byte[] TAILER_BYTES;
   private static byte[] SEPERATOR_BYTES;
   private String jsonParameter;

   public CallbackPollingTransport(RequestResponseKey rrk, String jsonParameter, String cookiePath) {
      super(rrk, cookiePath);
      this.jsonParameter = jsonParameter == null ? "jsonpcallback" : jsonParameter;
   }

   protected Client.ConnectionType getConnectionType() {
      return Client.ConnectionType.CALLBACKPOLLING;
   }

   protected String getHeader() {
      return this.jsonParameter + "([";
   }

   protected String getTailer() {
      return "])";
   }

   protected String getSeperator() {
      return ",";
   }

   protected String getResponseContentType() {
      return "text/javascript";
   }

   protected byte[] getHeaderBytes() {
      try {
         return this.getHeader().getBytes("ISO-8859-1");
      } catch (UnsupportedEncodingException var2) {
         return null;
      }
   }

   protected byte[] getTailerBytes() {
      return TAILER_BYTES;
   }

   protected byte[] getSeperatorBytes() {
      return SEPERATOR_BYTES;
   }

   static {
      try {
         String encoding = "ISO-8859-1";
         TAILER_BYTES = "])".getBytes(encoding);
         SEPERATOR_BYTES = ",".getBytes(encoding);
      } catch (UnsupportedEncodingException var1) {
      }

   }
}
