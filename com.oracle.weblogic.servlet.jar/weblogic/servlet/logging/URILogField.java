package weblogic.servlet.logging;

import weblogic.utils.http.HttpReasonPhraseCoder;

public final class URILogField implements LogField {
   private int type;

   URILogField(String pfx, String id) {
      if ("uri".equals(id)) {
         this.type = 9;
      } else if ("uri-stem".equals(id)) {
         this.type = 10;
      } else if ("uri-query".equals(id)) {
         this.type = 11;
      } else if ("method".equals(id)) {
         this.type = 8;
      } else if ("status".equals(id)) {
         this.type = 7;
      } else if ("comment".equals(id)) {
         this.type = 12;
      } else if ("authuser".equals(id)) {
         this.type = 15;
      } else {
         this.type = 0;
      }

   }

   public void logField(HttpAccountingInfo metrics, FormatStringBuffer buff) {
      String uri = null;
      String queryString = null;
      switch (this.type) {
         case 0:
            buff.appendValueOrDash((String)null);
         case 1:
         case 2:
         case 3:
         case 4:
         case 5:
         case 6:
         case 13:
         case 14:
         default:
            break;
         case 7:
            buff.appendValueOrDash(String.valueOf(metrics.getResponseStatusCode()));
            break;
         case 8:
            buff.appendValueOrDash(metrics.getMethod());
            break;
         case 9:
            String url = null;
            uri = metrics.getRequestURI();
            if (uri != null) {
               if (uri.length() < LoggerUtils.MAX_LOGGING_URI_LENGTH && (queryString = metrics.getQueryString()) != null && queryString.length() > 0) {
                  url = LoggerUtils.encodeAndTruncate(uri + "?" + queryString);
               } else {
                  url = LoggerUtils.encodeAndTruncate(uri);
               }
            }

            buff.append(url);
            break;
         case 10:
            buff.append(LoggerUtils.encodeAndTruncate(metrics.getRequestURI()));
            break;
         case 11:
            buff.append(LoggerUtils.encodeAndTruncate(metrics.getQueryString()));
            break;
         case 12:
            buff.appendQuotedValueOrDash(HttpReasonPhraseCoder.getReasonPhrase(metrics.getResponseStatusCode()));
            break;
         case 15:
            buff.appendValueOrDash(String.valueOf(metrics.getRemoteUser()));
      }

   }
}
