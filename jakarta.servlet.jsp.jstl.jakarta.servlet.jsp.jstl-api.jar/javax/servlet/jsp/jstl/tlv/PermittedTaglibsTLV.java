package javax.servlet.jsp.jstl.tlv;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;
import javax.servlet.jsp.tagext.PageData;
import javax.servlet.jsp.tagext.TagLibraryValidator;
import javax.servlet.jsp.tagext.ValidationMessage;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class PermittedTaglibsTLV extends TagLibraryValidator {
   private final String PERMITTED_TAGLIBS_PARAM = "permittedTaglibs";
   private final String JSP_ROOT_URI = "http://java.sun.com/JSP/Page";
   private final String JSP_ROOT_NAME = "root";
   private final String JSP_ROOT_QN = "jsp:root";
   private Set permittedTaglibs;
   private boolean failed;
   private String uri;

   public PermittedTaglibsTLV() {
      this.init();
   }

   private void init() {
      this.permittedTaglibs = null;
      this.failed = false;
   }

   public void release() {
      super.release();
      this.init();
   }

   public synchronized ValidationMessage[] validate(String prefix, String uri, PageData page) {
      try {
         this.uri = uri;
         this.permittedTaglibs = this.readConfiguration();
         DefaultHandler h = new PermittedTaglibsHandler();
         SAXParserFactory f = SAXParserFactory.newInstance();
         f.setValidating(true);
         SAXParser p = f.newSAXParser();
         p.parse(page.getInputStream(), h);
         return this.failed ? this.vmFromString("taglib " + prefix + " (" + uri + ") allows only the following taglibs to be imported: " + this.permittedTaglibs) : null;
      } catch (SAXException var7) {
         return this.vmFromString(var7.toString());
      } catch (ParserConfigurationException var8) {
         return this.vmFromString(var8.toString());
      } catch (IOException var9) {
         return this.vmFromString(var9.toString());
      }
   }

   private Set readConfiguration() {
      Set s = new HashSet();
      String uris = (String)this.getInitParameters().get("permittedTaglibs");
      StringTokenizer st = new StringTokenizer(uris);

      while(st.hasMoreTokens()) {
         s.add(st.nextToken());
      }

      return s;
   }

   private ValidationMessage[] vmFromString(String message) {
      return new ValidationMessage[]{new ValidationMessage((String)null, message)};
   }

   private class PermittedTaglibsHandler extends DefaultHandler {
      private PermittedTaglibsHandler() {
      }

      public void startElement(String ns, String ln, String qn, Attributes a) {
         if (qn.equals("jsp:root") || ns.equals("http://java.sun.com/JSP/Page") && ln.equals("root")) {
            for(int i = 0; i < a.getLength(); ++i) {
               String name = a.getQName(i);
               if (name.startsWith("xmlns:") && !name.equals("xmlns:jsp")) {
                  String value = a.getValue(i);
                  if (!value.equals(PermittedTaglibsTLV.this.uri) && !PermittedTaglibsTLV.this.permittedTaglibs.contains(value)) {
                     PermittedTaglibsTLV.this.failed = true;
                  }
               }
            }

         }
      }

      // $FF: synthetic method
      PermittedTaglibsHandler(Object x1) {
         this();
      }
   }
}
