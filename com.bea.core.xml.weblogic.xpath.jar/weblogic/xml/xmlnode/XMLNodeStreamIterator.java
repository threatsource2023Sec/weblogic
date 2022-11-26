package weblogic.xml.xmlnode;

import java.io.FileInputStream;
import weblogic.xml.stream.XMLEvent;
import weblogic.xml.stream.XMLInputStream;
import weblogic.xml.stream.XMLInputStreamFactory;
import weblogic.xml.stream.util.TypeFilter;

public class XMLNodeStreamIterator {
   XMLNodeIterator baseIterator;

   public XMLNodeStreamIterator(XMLNode node) {
      this.baseIterator = new XMLNodeIterator(node);
   }

   public static void main(String[] args) throws Exception {
      XMLInputStreamFactory factory = XMLInputStreamFactory.newInstance();
      TypeFilter filter = new TypeFilter(22);
      XMLInputStream stream = factory.newInputStream(new FileInputStream(args[0]), filter);
      XMLNode node = new XMLNode();
      node.read(stream);
      XMLNodeStreamIterator i = new XMLNodeStreamIterator(node);

      while(i.hasNext()) {
         System.out.println(i.next());
      }

   }

   public boolean hasNext() {
      return this.baseIterator.hasNext();
   }

   public XMLEvent next() {
      XMLNode node = this.baseIterator.next();
      if (node.isTextNode() && node.getText() != null) {
         return node.createCharacterData();
      } else {
         return (XMLEvent)(node.isEndNode() ? node.createEndElement() : node.createStartElement());
      }
   }
}
