package weblogic.xml.xmlnode;

import java.io.File;
import weblogic.xml.babel.stream.XMLOutputStreamBase;
import weblogic.xml.babel.stream.XMLWriter;
import weblogic.xml.stream.XMLInputStream;
import weblogic.xml.stream.XMLInputStreamFactory;
import weblogic.xml.stream.XMLOutputStream;
import weblogic.xml.stream.events.Name;

public class XMLNodeOutputStream extends XMLOutputStreamBase implements XMLOutputStream {
   public XMLNodeOutputStream(XMLNode node) {
      this.xmlWriter = new XMLNodeWriter(node);
   }

   public XMLNodeOutputStream() {
   }

   public XMLNodeOutputStream(XMLWriter writer) {
      this.xmlWriter = writer;
   }

   public static void main(String[] args) throws Exception {
      XMLNode node = new XMLNode();
      node.setName(new Name("test-name"));
      XMLNodeOutputStream output = new XMLNodeOutputStream(node);
      XMLInputStream stream = XMLInputStreamFactory.newInstance().newInputStream(new File(args[0]));
      output.add(stream);
      output.flush();
      XMLInputStream stream2 = node.stream();

      while(stream2.hasNext()) {
         System.out.println("Node: " + stream2.next());
      }

   }
}
