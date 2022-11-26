package org.glassfish.tyrus.container.jdk.client;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.glassfish.tyrus.core.TyrusUpgradeResponse;
import org.glassfish.tyrus.core.Utils;

class HttpResponseParser {
   private static final String ENCODING = "ISO-8859-1";
   private static final String LINE_SEPARATOR = "\r\n";
   private static final int BUFFER_STEP_SIZE = 256;
   static final int BUFFER_MAX_SIZE = 16384;
   private volatile boolean complete = false;
   private volatile ByteBuffer buffer;
   private volatile State findEndState;

   HttpResponseParser() {
      this.findEndState = HttpResponseParser.State.INIT;
      this.buffer = ByteBuffer.allocate(1024);
      this.buffer.flip();
   }

   TyrusUpgradeResponse parseUpgradeResponse() throws ParseException {
      String response = this.bufferToString();
      String[] tokens = response.split("\r\n");
      TyrusUpgradeResponse tyrusUpgradeResponse = new TyrusUpgradeResponse();
      this.parseFirstLine(tokens, tyrusUpgradeResponse);
      List lines = new LinkedList();
      lines.addAll(Arrays.asList(tokens).subList(1, tokens.length));
      Map headers = this.parseHeaders(lines);
      Iterator var6 = headers.entrySet().iterator();

      while(var6.hasNext()) {
         Map.Entry entry = (Map.Entry)var6.next();
         List values = (List)tyrusUpgradeResponse.getHeaders().get(entry.getKey());
         if (values == null) {
            tyrusUpgradeResponse.getHeaders().put(entry.getKey(), Utils.parseHeaderValue((String)entry.getValue()));
         } else {
            values.addAll(Utils.parseHeaderValue((String)entry.getValue()));
         }
      }

      return tyrusUpgradeResponse;
   }

   boolean isComplete() {
      return this.complete;
   }

   void appendData(ByteBuffer data) throws ParseException {
      if (this.buffer != null) {
         int responseEndPosition = this.getEndPosition(data);
         if (responseEndPosition == -1) {
            this.checkResponseSize(data);
            this.buffer = Utils.appendBuffers(this.buffer, data, 16384, 256);
         } else {
            int limit = data.limit();
            data.limit(responseEndPosition + 1);
            this.checkResponseSize(data);
            this.buffer = Utils.appendBuffers(this.buffer, data, 16384, 256);
            data.limit(limit);
            data.position(responseEndPosition + 1);
            this.complete = true;
         }
      }
   }

   private void checkResponseSize(ByteBuffer partToBeAppended) throws ParseException {
      if (this.buffer.remaining() + partToBeAppended.remaining() > 16384) {
         throw new ParseException("Upgrade response too big, sizes only up to 16384B are supported.");
      }
   }

   private void parseFirstLine(String[] responseLines, TyrusUpgradeResponse tyrusUpgradeResponse) throws ParseException {
      if (responseLines.length == 0) {
         throw new ParseException("Empty HTTP response");
      } else {
         String firstLine = responseLines[0];
         int versionEndIndex = firstLine.indexOf(32);
         if (versionEndIndex == -1) {
            throw new ParseException("Unexpected format of the first line of a HTTP response: " + firstLine);
         } else {
            int statusCodeEndIndex = firstLine.indexOf(32, versionEndIndex + 1);
            if (statusCodeEndIndex == -1) {
               throw new ParseException("Unexpected format of the first line of a HTTP response: " + firstLine);
            } else {
               String statusCode = firstLine.substring(versionEndIndex + 1, statusCodeEndIndex);
               String reasonPhrase = firstLine.substring(statusCodeEndIndex + 1);

               int status;
               try {
                  status = Integer.parseInt(statusCode);
               } catch (Exception var10) {
                  throw new ParseException("Invalid format of status code: " + statusCode);
               }

               tyrusUpgradeResponse.setStatus(status);
               tyrusUpgradeResponse.setReasonPhrase(reasonPhrase);
            }
         }
      }
   }

   private Map parseHeaders(List headerLines) {
      Map headers = new HashMap();
      Iterator var3 = headerLines.iterator();

      while(var3.hasNext()) {
         String headerLine = (String)var3.next();
         int separatorIndex = headerLine.indexOf(58);
         if (separatorIndex != -1) {
            String headerKey = headerLine.substring(0, separatorIndex);
            String headerValue = headerLine.substring(separatorIndex + 1);
            if (headers.containsKey(headerKey)) {
               headers.put(headerKey, (String)headers.get(headerKey) + ", " + headerValue);
            } else {
               headers.put(headerKey, headerValue);
            }
         }
      }

      return headers;
   }

   private String bufferToString() {
      byte[] bytes = Utils.getRemainingArray(this.buffer);

      try {
         String str = new String(bytes, "ISO-8859-1");
         return str;
      } catch (UnsupportedEncodingException var4) {
         throw new RuntimeException("Unsupported encodingISO-8859-1", var4);
      }
   }

   void destroy() {
      this.buffer = null;
   }

   void clear() {
      this.buffer.clear();
      this.buffer.flip();
      this.complete = false;
      this.findEndState = HttpResponseParser.State.INIT;
   }

   private int getEndPosition(ByteBuffer buffer) {
      byte[] bytes = buffer.array();

      for(int i = buffer.position(); i < buffer.limit(); ++i) {
         byte b = bytes[i];
         switch (this.findEndState) {
            case INIT:
               if (b == 13) {
                  this.findEndState = HttpResponseParser.State.R;
               }
               break;
            case R:
               if (b == 10) {
                  this.findEndState = HttpResponseParser.State.RN;
               } else {
                  this.findEndReset(b);
               }
               break;
            case RN:
               if (b == 13) {
                  this.findEndState = HttpResponseParser.State.RNR;
               } else {
                  this.findEndState = HttpResponseParser.State.INIT;
               }
               break;
            case RNR:
               if (b == 10) {
                  return i;
               }

               this.findEndReset(b);
         }
      }

      return -1;
   }

   private void findEndReset(byte b) {
      this.findEndState = HttpResponseParser.State.INIT;
      if (b == 13) {
         this.findEndState = HttpResponseParser.State.R;
      }

   }

   private static enum State {
      INIT,
      R,
      RN,
      RNR;
   }
}
