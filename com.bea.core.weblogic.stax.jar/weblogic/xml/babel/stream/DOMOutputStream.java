package weblogic.xml.babel.stream;

import java.io.File;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentFragment;
import weblogic.apache.xerces.dom.DocumentImpl;
import weblogic.xml.stream.XMLInputStream;
import weblogic.xml.stream.XMLInputStreamFactory;
import weblogic.xml.stream.XMLOutputStream;

public class DOMOutputStream extends XMLOutputStreamBase {
   public DOMOutputStream(Document document) {
      this.xmlWriter = new DOMNodeWriter(document);
   }

   public DOMOutputStream(Document document, DocumentFragment documentFragment) {
      this.xmlWriter = new DOMNodeWriter(document, documentFragment);
   }

   public DOMOutputStream() {
   }

   public DOMOutputStream(XMLWriter writer) {
      this.xmlWriter = writer;
   }

   public static void main(String[] args) throws Exception {
      Document doc = new DocumentImpl();
      XMLOutputStream output = new DOMOutputStream(doc);
      XMLInputStream stream = XMLInputStreamFactory.newInstance().newInputStream(new File(args[0]));
      if (stream.peek().isStartDocument()) {
         System.out.println("removing: " + stream.next());
      }

      output.add(stream);
      output.flush();
      DOMInputStream.printNode(doc);
   }
}
