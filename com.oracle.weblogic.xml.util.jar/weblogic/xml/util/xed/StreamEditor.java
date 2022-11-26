package weblogic.xml.util.xed;

import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import weblogic.xml.stream.XMLEvent;
import weblogic.xml.stream.XMLInputStream;
import weblogic.xml.stream.XMLOutputStream;
import weblogic.xml.stream.XMLOutputStreamFactory;
import weblogic.xml.stream.XMLStreamException;
import weblogic.xml.xpath.XPathException;

public class StreamEditor {
   private static final boolean VERBOSE = true;

   public static void outputInsertions(Iterator i, XMLOutputStream os) throws XMLStreamException {
      while(i.hasNext()) {
         XMLInputStream s = (XMLInputStream)i.next();
         os.add(s);
      }

   }

   public static void operate(String sedFile, String xmlFile, XMLOutputStream os) throws XPathException, StreamEditorException, XMLStreamException, IOException {
      XEDParser parser = new XEDParser(new FileReader(sedFile));
      Operation operation = parser.parse();
      System.out.println("[Operation Loaded]");
      System.out.println(operation);
      operation.prepare();
      XMLInputStream stream = operation.getStream(xmlFile);

      while(stream.hasNext()) {
         XMLEvent e = stream.next();
         ArrayList pre = operation.getPre();
         ArrayList post = operation.getPost();
         ArrayList child = operation.getChild();
         if (e.isStartElement()) {
            outputInsertions(pre.iterator(), os);
         }

         if (!operation.needToDelete()) {
            os.add(e);
         }

         if (e.isStartElement()) {
            outputInsertions(child.iterator(), os);
         }

         if (e.isEndElement()) {
            outputInsertions(post.iterator(), os);
         }
      }

   }

   public static void main(String[] args) throws Exception {
      String sedFile = args[0];
      String xmlFile = args[1];
      String outFile = args[2];
      XMLOutputStreamFactory f = XMLOutputStreamFactory.newInstance();
      XMLOutputStream os = f.newOutputStream(new FileOutputStream(outFile));
      operate(sedFile, xmlFile, os);
      os.flush();
   }
}
