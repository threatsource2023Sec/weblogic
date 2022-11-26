package weblogic.xml.babel.stream;

import weblogic.utils.collections.CircularQueue;
import weblogic.xml.babel.adapters.ElementConsumer;
import weblogic.xml.babel.baseparser.SAXElementFactory;
import weblogic.xml.stream.StartElement;
import weblogic.xml.stream.XMLEvent;
import weblogic.xml.stream.XMLInputStream;
import weblogic.xml.stream.XMLStreamException;
import weblogic.xml.stream.events.NullEvent;

public class SubXMLInputStream extends XMLInputStreamBase implements XMLInputStream, ElementConsumer {
   protected XMLInputStreamBase parent;
   protected int startTags;
   protected int endTags;

   public SubXMLInputStream(XMLInputStreamBase parent) {
      this.elementQ = new CircularQueue(8);
      this.startTags = 0;
      this.endTags = 0;
      this.open = true;
      this.init(parent);
   }

   protected void init(XMLInputStreamBase parent) {
      this.parent = parent;
      parent.addChild(this);
   }

   public boolean empty() {
      return this.elementQ.isEmpty();
   }

   public boolean hasNext() throws XMLStreamException {
      if (!this.empty()) {
         return true;
      } else if (!this.open) {
         return false;
      } else {
         return this.parseSome();
      }
   }

   public XMLEvent next() throws XMLStreamException {
      if (this.needsMore()) {
         this.parseSome();
      }

      return this.get();
   }

   public boolean add(Object obj) {
      XMLEvent e = (XMLEvent)obj;
      if (debugSubStream) {
         System.out.println("ADD SubXMLInputStreamBaseCallback[" + e + "][" + this.startTags + "][" + this.endTags + "]");
      }

      if (this.open && obj != null) {
         switch (e.getType()) {
            case 2:
               ++this.startTags;
               break;
            case 4:
               ++this.endTags;
               if (this.startTags <= this.endTags) {
                  this.open = false;
               }
               break;
            case 128:
            case 512:
               this.open = false;
         }

         if (debugSubStream) {
            System.out.println("Added to child[" + this.open + "]");
         }

         super.add(obj);
      }

      return this.open;
   }

   public boolean parseSome() throws XMLStreamException {
      if (debugSubStream) {
         System.out.println("Parsing Some in Sub Stream");
      }

      return this.parent.parseSome();
   }

   public XMLEvent peek() throws XMLStreamException {
      if (!this.elementQ.isEmpty()) {
         return (XMLEvent)this.elementQ.peek();
      } else {
         if (debugSubStream) {
            System.out.println("SubXMLinputStream:peek(0)");
         }

         if (this.parseSome() && !this.elementQ.isEmpty()) {
            return (XMLEvent)this.elementQ.peek();
         } else {
            if (debugSubStream) {
               System.out.println("SubXMLinputStream:peek(1)");
            }

            if (this.parseSome() && !this.elementQ.isEmpty()) {
               return (XMLEvent)this.elementQ.peek();
            } else {
               if (debugSubStream) {
                  System.out.println("SubXMLinputStream:peek(FAIL)");
               }

               return new NullEvent();
            }
         }
      }
   }

   public void close() throws XMLStreamException {
      this.parent.removeChild(this);
      super.close();
   }

   public static void main(String[] args) throws Exception {
      XMLInputStreamBase input = new XMLInputStreamBase();
      input.open(SAXElementFactory.createInputSource(args[0]));
      input.skip(2);
      input.next();
      XMLInputStream subInput = input.getSubStream();
      System.out.println("GETTING MULTIPLE STREAMS");

      while(subInput.skip(2)) {
         StartElement elementEvent = (StartElement)subInput.next();
         System.out.print("START EVENT: " + elementEvent.getTypeAsString() + " [");
         System.out.print(elementEvent);
         System.out.print("][");
         System.out.print(elementEvent.getNamespaceUri("a"));
         System.out.print("][");
         System.out.print(elementEvent.getNamespaceUri("o"));
         System.out.println("]");
      }

      subInput.close();
      subInput = input.getSubStream();
      System.out.println("GETTING MULTIPLE STREAMS");

      XMLEvent elementEvent;
      while(subInput.skip(4)) {
         elementEvent = subInput.next();
         System.out.print("END EVENT: " + elementEvent.getTypeAsString() + " [");
         System.out.print(elementEvent);
         System.out.println("]");
      }

      subInput.close();
      System.out.println("CHECKING IF STREAM REWOUND");

      while(input.hasNext()) {
         elementEvent = input.next();
         System.out.print("CACHED EVENT: " + elementEvent.getTypeAsString() + " [");
         System.out.print(elementEvent);
         System.out.println("]");
      }

   }
}
