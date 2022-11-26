package weblogic.xml.babel.stream;

import java.io.File;
import org.xml.sax.ContentHandler;
import weblogic.xml.stream.XMLInputStream;
import weblogic.xml.stream.XMLInputStreamFactory;
import weblogic.xml.stream.XMLOutputStream;
import weblogic.xml.util.DebugHandler;

public class SAXOutputStream extends XMLOutputStreamBase {
   public SAXOutputStream(ContentHandler handler) {
      this.xmlWriter = new SAXEventWriter(handler);
   }

   public SAXOutputStream() {
   }

   public SAXOutputStream(XMLWriter writer) {
      this.xmlWriter = writer;
   }

   public static void main(String[] args) throws Exception {
      XMLInputStream stream = XMLInputStreamFactory.newInstance().newInputStream(new File(args[0]));
      XMLOutputStream output = new SAXOutputStream(new DebugHandler());
      output.add(stream);
      output.flush();
   }
}
