package weblogic.xml.babel.stream;

import java.util.ArrayList;
import org.w3c.dom.Comment;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.ProcessingInstruction;
import org.xml.sax.InputSource;
import weblogic.apache.xerces.parsers.DOMParser;
import weblogic.utils.collections.CircularQueue;
import weblogic.xml.babel.adapters.DOMAdapter;
import weblogic.xml.babel.baseparser.SAXElementFactory;
import weblogic.xml.stream.XMLEvent;
import weblogic.xml.stream.XMLInputStream;
import weblogic.xml.stream.XMLInputStreamFactory;
import weblogic.xml.stream.XMLStreamException;
import weblogic.xml.stream.events.NullEvent;

public class DOMInputStream extends XMLInputStreamBase {
   protected Node node;
   protected DOMAdapter adapter;

   public DOMInputStream() {
      this.open = false;
      this.children = new ArrayList();
      this.adapter = new DOMAdapter(this);
   }

   public void open(InputSource inputSource) throws XMLStreamException {
      throw new XMLStreamException("Not Implemented");
   }

   public void open(Node node) throws XMLStreamException {
      this.open = true;
      this.elementQ = new CircularQueue(8);
      this.adapter.adapt(node);
   }

   public boolean parseSome() throws XMLStreamException {
      return false;
   }

   public boolean hasNext() throws XMLStreamException {
      return this.open && !this.elementQ.isEmpty();
   }

   public XMLEvent peek() throws XMLStreamException {
      return (XMLEvent)(this.hasNext() ? (XMLEvent)this.elementQ.peek() : new NullEvent());
   }

   public static void printNode(Node node) {
      switch (node.getNodeType()) {
         case 1:
            String name = node.getNodeName();
            System.out.print("<" + name);
            NamedNodeMap attributes = node.getAttributes();

            for(int i = 0; i < attributes.getLength(); ++i) {
               Node current = attributes.item(i);
               System.out.print(" " + current.getNodeName() + "=\"" + current.getNodeValue() + "\"");
            }

            System.out.print(">");
            NodeList children = node.getChildNodes();
            if (children != null) {
               for(int i = 0; i < children.getLength(); ++i) {
                  printNode(children.item(i));
               }
            }

            System.out.print("</" + name + ">");
         case 2:
         case 5:
         case 6:
         case 10:
         default:
            break;
         case 3:
         case 4:
            System.out.print(node.getNodeValue());
            break;
         case 7:
            ProcessingInstruction pi = (ProcessingInstruction)node;
            System.out.print("<?" + pi.getTarget() + " " + pi.getData() + "?>");
            break;
         case 8:
            System.out.println("<!--" + ((Comment)node).getData() + "-->");
            break;
         case 9:
            System.out.println("<xml version=\"1.0\">");
            Document doc = (Document)node;
            printNode(doc.getDocumentElement());
            break;
         case 11:
            NodeList l = node.getChildNodes();
            int j = l.getLength();

            for(int i = 0; i < j; ++i) {
               printNode(l.item(i));
            }
      }

   }

   public static void main(String[] args) throws Exception {
      DOMInputStream input = new DOMInputStream();
      DOMParser parser = new DOMParser();
      parser.setFeature("http://apache.org/xml/features/dom/defer-node-expansion", false);
      parser.setFeature("http://xml.org/sax/features/validation", false);
      parser.setFeature("http://xml.org/sax/features/namespaces", true);
      parser.setFeature("http://apache.org/xml/features/validation/schema", true);
      parser.parse(SAXElementFactory.createInputSource(args[0]));
      Document doc = parser.getDocument();
      input.open((Node)doc);

      while(input.hasNext()) {
         XMLEvent elementEvent = input.next();
         System.out.print("EVENT: " + elementEvent.getTypeAsString() + " [");
         System.out.print(elementEvent);
         System.out.println("]");
      }

      System.out.println("Echoed dom");
      XMLInputStream stream = XMLInputStreamFactory.newInstance().newInputStream((Node)doc);

      while(stream.hasNext()) {
         XMLEvent e = stream.next();
         System.out.print("EVENT:[ " + e + "]");
      }

   }
}
