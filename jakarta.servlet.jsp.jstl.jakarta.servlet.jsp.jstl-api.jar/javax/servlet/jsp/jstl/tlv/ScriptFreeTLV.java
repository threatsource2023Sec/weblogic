package javax.servlet.jsp.jstl.tlv;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import javax.servlet.jsp.tagext.PageData;
import javax.servlet.jsp.tagext.TagLibraryValidator;
import javax.servlet.jsp.tagext.ValidationMessage;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class ScriptFreeTLV extends TagLibraryValidator {
   private boolean allowDeclarations = false;
   private boolean allowScriptlets = false;
   private boolean allowExpressions = false;
   private boolean allowRTExpressions = false;
   private SAXParserFactory factory = SAXParserFactory.newInstance();

   public ScriptFreeTLV() {
      this.factory.setValidating(false);
      this.factory.setNamespaceAware(true);
   }

   public void setInitParameters(Map initParms) {
      super.setInitParameters(initParms);
      String declarationsParm = (String)initParms.get("allowDeclarations");
      String scriptletsParm = (String)initParms.get("allowScriptlets");
      String expressionsParm = (String)initParms.get("allowExpressions");
      String rtExpressionsParm = (String)initParms.get("allowRTExpressions");
      this.allowDeclarations = "true".equalsIgnoreCase(declarationsParm);
      this.allowScriptlets = "true".equalsIgnoreCase(scriptletsParm);
      this.allowExpressions = "true".equalsIgnoreCase(expressionsParm);
      this.allowRTExpressions = "true".equalsIgnoreCase(rtExpressionsParm);
   }

   public ValidationMessage[] validate(String prefix, String uri, PageData page) {
      InputStream in = null;
      MyContentHandler handler = new MyContentHandler();

      ValidationMessage[] var8;
      try {
         SAXParser parser;
         synchronized(this.factory) {
            parser = this.factory.newSAXParser();
         }

         in = page.getInputStream();
         parser.parse(in, handler);
         return handler.reportResults();
      } catch (ParserConfigurationException var24) {
         var8 = vmFromString(var24.toString());
         return var8;
      } catch (SAXException var25) {
         var8 = vmFromString(var25.toString());
         return var8;
      } catch (IOException var26) {
         var8 = vmFromString(var26.toString());
      } finally {
         if (in != null) {
            try {
               in.close();
            } catch (IOException var22) {
            }
         }

      }

      return var8;
   }

   private static ValidationMessage[] vmFromString(String message) {
      return new ValidationMessage[]{new ValidationMessage((String)null, message)};
   }

   private class MyContentHandler extends DefaultHandler {
      private int declarationCount;
      private int scriptletCount;
      private int expressionCount;
      private int rtExpressionCount;

      private MyContentHandler() {
         this.declarationCount = 0;
         this.scriptletCount = 0;
         this.expressionCount = 0;
         this.rtExpressionCount = 0;
      }

      public void startElement(String namespaceUri, String localName, String qualifiedName, Attributes atts) {
         if (!ScriptFreeTLV.this.allowDeclarations && qualifiedName.equals("jsp:declaration")) {
            ++this.declarationCount;
         } else if (!ScriptFreeTLV.this.allowScriptlets && qualifiedName.equals("jsp:scriptlet")) {
            ++this.scriptletCount;
         } else if (!ScriptFreeTLV.this.allowExpressions && qualifiedName.equals("jsp:expression")) {
            ++this.expressionCount;
         }

         if (!ScriptFreeTLV.this.allowRTExpressions) {
            this.countRTExpressions(atts);
         }

      }

      private void countRTExpressions(Attributes atts) {
         int stop = atts.getLength();

         for(int i = 0; i < stop; ++i) {
            String attval = atts.getValue(i);
            if (attval.startsWith("%=") && attval.endsWith("%")) {
               ++this.rtExpressionCount;
            }
         }

      }

      public ValidationMessage[] reportResults() {
         if (this.declarationCount + this.scriptletCount + this.expressionCount + this.rtExpressionCount > 0) {
            StringBuffer results = new StringBuffer("JSP page contains ");
            boolean first = true;
            if (this.declarationCount > 0) {
               results.append(Integer.toString(this.declarationCount));
               results.append(" declaration");
               if (this.declarationCount > 1) {
                  results.append('s');
               }

               first = false;
            }

            if (this.scriptletCount > 0) {
               if (!first) {
                  results.append(", ");
               }

               results.append(Integer.toString(this.scriptletCount));
               results.append(" scriptlet");
               if (this.scriptletCount > 1) {
                  results.append('s');
               }

               first = false;
            }

            if (this.expressionCount > 0) {
               if (!first) {
                  results.append(", ");
               }

               results.append(Integer.toString(this.expressionCount));
               results.append(" expression");
               if (this.expressionCount > 1) {
                  results.append('s');
               }

               first = false;
            }

            if (this.rtExpressionCount > 0) {
               if (!first) {
                  results.append(", ");
               }

               results.append(Integer.toString(this.rtExpressionCount));
               results.append(" request-time attribute value");
               if (this.rtExpressionCount > 1) {
                  results.append('s');
               }

               first = false;
            }

            results.append(".");
            return ScriptFreeTLV.vmFromString(results.toString());
         } else {
            return null;
         }
      }

      // $FF: synthetic method
      MyContentHandler(Object x1) {
         this();
      }
   }
}
