package weblogic.servlet.http2;

import java.util.ArrayList;
import java.util.List;
import weblogic.utils.http.AbstractHttpRequestParser;
import weblogic.utils.http.HttpRequestParseException;
import weblogic.utils.http.RequestParser;

public class Http2RequestParser extends AbstractHttpRequestParser implements RequestParser {
   public void parse(byte[] buf, int bufLen) throws HttpRequestParseException {
      this.buf = buf;
      this.bufLen = bufLen;
      this.consumeSpaces();
      int queryStrIndex = -1;
      List pathParamIndexes = null;
      int uriBegin = this.pos;

      boolean unsafeSymbolsInUri;
      for(unsafeSymbolsInUri = false; this.pos < bufLen && !this.isCRLF(); ++this.pos) {
         if (buf[this.pos] == 63 && queryStrIndex == -1) {
            queryStrIndex = this.pos;
         } else if (buf[this.pos] == 59 && queryStrIndex == -1) {
            if (pathParamIndexes == null) {
               pathParamIndexes = new ArrayList();
            }

            pathParamIndexes.add(this.pos);
         } else if (buf[this.pos] == 60 && queryStrIndex == -1) {
            unsafeSymbolsInUri = true;
         }
      }

      int uriEnd = this.pos < bufLen - 1 ? this.pos : bufLen - 1;
      this.originalUriLength = uriEnd - uriBegin + 1;
      if (queryStrIndex == -1) {
         this.decodeURI(uriBegin, pathParamIndexes, uriEnd + 1);
      } else {
         this.decodeURI(uriBegin, pathParamIndexes, queryStrIndex);
         this.queryStringStart = queryStrIndex + 1;
         this.queryStringLength = uriEnd - queryStrIndex;
      }

      if (unsafeSymbolsInUri) {
         throw new HttpRequestParseException("Request contains XSS script", this);
      }
   }

   protected void checkOverflow(int count) {
   }

   public void reset() {
      super.reset();
   }

   public String getProtocol() {
      return "HTTP/2";
   }

   public boolean isProtocolVersion_1_1() {
      return false;
   }

   public boolean isProtocolVersion_2() {
      return true;
   }

   public boolean isKeepAlive() {
      return true;
   }
}
