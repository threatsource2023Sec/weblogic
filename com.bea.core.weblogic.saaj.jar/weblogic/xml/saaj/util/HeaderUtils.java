package weblogic.xml.saaj.util;

import java.util.Iterator;
import javax.xml.soap.MimeHeader;
import javax.xml.soap.MimeHeaders;
import weblogic.xml.saaj.SOAPMessageImpl;

public class HeaderUtils {
   private static final String CONTENT_TYPE_HEADER = "Content-Type";

   public static String constructContentTypeHeader(String mime, String encoding, SOAPMessageImpl message, boolean isMTOM, boolean isSOAP12) {
      String type = isSOAP12 ? "application/soap+xml" : "text/xml";
      if (mime == null) {
         mime = isMTOM ? "application/xop+xml" : type;
      }

      StringBuffer ret = (new StringBuffer(mime)).append("; ").append("charset").append("=").append(encoding);
      if (isMTOM) {
         ret.append("; ").append("type=\"").append(type).append("\"");
         if (isSOAP12) {
            String action = (String)message.getProperty("weblogic.xml.saaj.action-parameter");
            action = action == null ? "" : action;
            ret.append("; action=\"").append(action).append("\"");
         }
      }

      return ret.toString();
   }

   public static String getContentType(MimeHeaders headers) {
      return getFirstMatchingHeader(headers, "Content-Type");
   }

   public static String getFirstMatchingHeader(MimeHeaders headers, String header_name) {
      assert headers != null;

      assert header_name != null;

      Iterator itr = headers.getAllHeaders();

      MimeHeader header;
      do {
         if (!itr.hasNext()) {
            return null;
         }

         header = (MimeHeader)itr.next();
      } while(!header_name.equalsIgnoreCase(header.getName()));

      return header.getValue();
   }
}
