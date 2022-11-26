package weblogic.xml.dtdc;

import org.xml.sax.AttributeList;
import org.xml.sax.DocumentHandler;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;

public class NullHandler implements DocumentHandler {
   final boolean verbose;
   private static int depth = 0;

   public NullHandler() {
      this.verbose = false;
   }

   public NullHandler(boolean verbose) {
      this.verbose = verbose;
   }

   public void setDocumentLocator(Locator locator) {
   }

   public void startDocument() throws SAXException {
   }

   public void endDocument() throws SAXException {
   }

   public void ignorableWhitespace(char[] ch, int start, int length) throws SAXException {
   }

   public void characters(char[] ch, int start, int length) throws SAXException {
   }

   public void processingInstruction(String target, String data) throws SAXException {
   }

   public void startElement(String name, AttributeList atts) throws SAXException {
      if (this.verbose) {
         format("Start: " + name);
         ++depth;
      }

   }

   public void endElement(String name) throws SAXException {
      if (this.verbose) {
         --depth;
         format("End:   " + name);
      }

   }

   private static void format(String line) {
      StringBuffer sb = new StringBuffer();

      for(int i = depth; i > 0; --i) {
         sb.append("  ");
      }

      System.out.println(sb.append(line));
   }
}
