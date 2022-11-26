package weblogic.xml.util.xed;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import weblogic.xml.stream.XMLInputStream;
import weblogic.xml.stream.XMLInputStreamFactory;
import weblogic.xml.stream.XMLStreamException;
import weblogic.xml.xpath.StreamXPath;
import weblogic.xml.xpath.XPathException;
import weblogic.xml.xpath.XPathStreamFactory;

public class Operation {
   private ArrayList commands = new ArrayList();
   private XPathStreamFactory factory = new XPathStreamFactory();

   public void add(Command c) {
      this.commands.add(c);
   }

   public ArrayList getPre() throws XMLStreamException {
      ArrayList result = new ArrayList();
      Iterator i = this.commands.iterator();

      while(i.hasNext()) {
         Command c = (Command)i.next();
         if (c.isInsertBefore()) {
            XMLInputStream s = c.getResult();
            if (s != null) {
               result.add(s);
            }
         }
      }

      return result;
   }

   public ArrayList getChild() throws XMLStreamException {
      ArrayList result = new ArrayList();
      Iterator i = this.commands.iterator();

      while(i.hasNext()) {
         Command c = (Command)i.next();
         if (c.isInsertChild()) {
            XMLInputStream s = c.getResult();
            if (s != null) {
               result.add(s);
            }
         }
      }

      return result;
   }

   public ArrayList getPost() throws XMLStreamException {
      ArrayList result = new ArrayList();
      Iterator i = this.commands.iterator();

      while(i.hasNext()) {
         Command c = (Command)i.next();
         if (c.isInsertAfter()) {
            XMLInputStream s = c.getResult();
            if (s != null) {
               result.add(s);
            }
         }
      }

      return result;
   }

   public boolean needToDelete() {
      Iterator i = this.commands.iterator();

      Command c;
      do {
         if (!i.hasNext()) {
            return false;
         }

         c = (Command)i.next();
      } while(!c.isDelete() || !((Delete)c).wasHit());

      return true;
   }

   public ArrayList getCommands() {
      return this.commands;
   }

   public void prepare() throws XPathException, XMLStreamException {
      Iterator i = this.commands.iterator();

      while(i.hasNext()) {
         Command c = (Command)i.next();
         Controller controller = new Controller(new Context(), c);
         StreamXPath xpath = new StreamXPath(c.getXPath());
         this.factory.install(xpath, controller);
      }

   }

   public XMLInputStream getStream(String file) throws XPathException, XMLStreamException {
      XMLInputStreamFactory streamFactory = XMLInputStreamFactory.newInstance();
      XMLInputStream stream = streamFactory.newInputStream(new File(file));
      return this.factory.createStream(stream);
   }

   public String toString() {
      StringBuffer b = new StringBuffer();
      Iterator i = this.commands.iterator();
      b.append("-------[\n");

      while(i.hasNext()) {
         b.append("[" + i.next() + "]\n");
      }

      b.append("\n]-------\n");
      return b.toString();
   }
}
