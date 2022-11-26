package weblogicx.xml.stream;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.xml.sax.InputSource;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import weblogic.utils.UnsyncCircularQueue;

public abstract class XMLEventStreamBase implements XMLEventStream {
   private List xess;
   protected Map prefixMap;
   protected boolean throwOnError;
   protected boolean throwOnWarning;
   protected Locator locator;
   protected boolean running;
   protected UnsyncCircularQueue q = new UnsyncCircularQueue(8, 1073741824);

   protected void addXES(XMLEventStreamBase xes) {
      if (this.xess == null) {
         this.xess = new ArrayList();
      }

      this.xess.add(xes);
   }

   protected boolean removeXES(XMLEventStream xes) {
      return this.xess != null ? this.xess.remove(xes) : false;
   }

   public boolean put(XMLEvent e) {
      if (e != null) {
         this.q.put(e);
         if (this.xess != null) {
            Iterator iter = this.xess.iterator();

            while(iter.hasNext()) {
               XMLEventStreamBase xes = (XMLEventStreamBase)iter.next();
               XMLEvent f = (XMLEvent)e.clone();
               f.setPrefixMap(xes.prefixMap);
               if (!xes.put(f)) {
                  iter.remove();
               }
            }
         }
      }

      return true;
   }

   public XMLEventStream getSubStream() throws SAXException {
      return new SubEventStream(this, this.locator);
   }

   public XMLEventStream getSubElementStream() throws SAXException {
      return new SubElementEventStream(this, this.locator);
   }

   public void startDocument(InputSource is) throws SAXException, IOException {
      if (this.running) {
         throw new SAXException("Already parsing document, if you want to start a new document, call endDocument first.");
      }
   }

   public void endDocument(boolean flush) throws SAXException, IOException {
      this.running = false;
      if (flush) {
         while(this.hasNext()) {
            this.next();
         }
      }

      if (this.xess != null) {
         Iterator iter = this.xess.iterator();

         while(iter.hasNext()) {
            XMLEventStreamBase xes = (XMLEventStreamBase)iter.next();
            xes.endDocument(flush);
            iter.remove();
         }
      }

   }

   public XMLEvent next() throws SAXException, SAXParseException {
      if (this.hasNext()) {
         XMLEvent e = (XMLEvent)this.q.get();
         if (e instanceof PrefixMappingEvent) {
            if (this.prefixMap == null) {
               this.prefixMap = new HashMap();
            }

            if (e instanceof ChangePrefixMappingEvent) {
               ChangePrefixMappingEvent spme = (ChangePrefixMappingEvent)e;
               String olduri = (String)this.prefixMap.put(spme.getPrefix(), spme.getURI());
               if (!spme.getOldURI().equals(olduri)) {
                  throw new SAXException("Parser state corrupt, old uri was " + olduri + " but event says it was " + spme.getOldURI());
               }
            } else if (e instanceof StartPrefixMappingEvent) {
               StartPrefixMappingEvent spme = (StartPrefixMappingEvent)e;
               this.prefixMap.put(spme.getPrefix(), spme.getURI());
            } else if (e instanceof EndPrefixMappingEvent) {
               EndPrefixMappingEvent epme = (EndPrefixMappingEvent)e;
               this.prefixMap.remove(epme.getPrefix());
            }
         }

         e.setPrefixMap(this.prefixMap);
         if (e instanceof ExceptionEvent) {
            ExceptionEvent ee = (ExceptionEvent)e;
            throw ee.getException();
         } else {
            return e;
         }
      } else {
         throw new SAXException("No more SAX events. Call hasNext() before next to ensure there are more.");
      }
   }

   public boolean hasNext() throws SAXException {
      boolean done;
      for(done = false; !done && this.q.empty(); done = this.parseSome()) {
      }

      return !done;
   }

   public XMLEvent peek() throws SAXException, SAXParseException {
      return this.hasNext() ? (XMLEvent)this.q.peek() : null;
   }

   public StartElementEvent peekElement() throws SAXException, SAXParseException {
      return this.hasStartElement() ? (StartElementEvent)this.q.peek() : null;
   }

   public StartElementEvent startElement(String name) throws SAXException {
      return this.hasStartElement(name) ? (StartElementEvent)this.next() : null;
   }

   public StartElementEvent startElement(String name, String namespace) throws SAXException {
      return this.hasStartElement(name, namespace) ? (StartElementEvent)this.next() : null;
   }

   public boolean hasStartElement(String name, String namespace) throws SAXException {
      XMLEvent var4;
      for(; this.hasStartElement(); var4 = this.next()) {
         StartElementEvent see = (StartElementEvent)this.q.peek();
         if (see.getName().equals(name)) {
            if (namespace == null) {
               return true;
            }

            if (namespace.equals(see.getNamespaceURI())) {
               return true;
            }
         }
      }

      return false;
   }

   public boolean hasStartElement(String name) throws SAXException {
      return this.hasStartElement(name, (String)null);
   }

   public boolean nextElementIs(String name, String namespace) throws SAXException {
      if (this.hasStartElement()) {
         StartElementEvent see = (StartElementEvent)this.q.peek();
         if (see.getName().equals(name)) {
            if (namespace == null) {
               return true;
            }

            if (namespace.equals(see.getNamespaceURI())) {
               return true;
            }
         }
      }

      return false;
   }

   public boolean nextElementIs(String name) throws SAXException {
      return this.nextElementIs(name, (String)null);
   }

   public EndElementEvent endElement(String name, String namespace) throws SAXException {
      int startCount = 0;

      while(this.hasNext()) {
         XMLEvent e = this.next();
         if (e == null) {
            break;
         }

         if (e instanceof StartElementEvent && ((StartElementEvent)e).getName().equals(name)) {
            if (namespace == null) {
               ++startCount;
            } else if (namespace.equals(((StartElementEvent)e).getNamespaceURI())) {
               ++startCount;
            }
         }

         if (e instanceof EndElementEvent && ((EndElementEvent)e).getName().equals(name)) {
            if (namespace == null) {
               if (startCount == 0) {
                  return (EndElementEvent)e;
               }

               --startCount;
            } else if (namespace.equals(((EndElementEvent)e).getNamespaceURI())) {
               if (startCount == 0) {
                  return (EndElementEvent)e;
               }

               --startCount;
            }
         }
      }

      return null;
   }

   public EndElementEvent endElement(String name) throws SAXException {
      return this.endElement(name, (String)null);
   }

   public StartElementEvent startElement() throws SAXException {
      return this.hasStartElement() ? (StartElementEvent)this.next() : null;
   }

   public boolean hasStartElement() throws SAXException {
      while(this.hasNext()) {
         XMLEvent e = (XMLEvent)this.q.peek();
         if (e instanceof StartElementEvent) {
            return true;
         }

         XMLEvent var2 = this.next();
      }

      return false;
   }

   public EndElementEvent endElement() throws SAXException {
      while(true) {
         if (this.hasNext()) {
            XMLEvent e = this.next();
            if (!(e instanceof EndElementEvent)) {
               continue;
            }

            return (EndElementEvent)e;
         }

         return null;
      }
   }

   public String getText() throws SAXException {
      StringBuffer sb = new StringBuffer();

      while(this.hasNext()) {
         XMLEvent e = this.next();
         if (!(e instanceof TextEvent)) {
            break;
         }

         TextEvent te = (TextEvent)e;
         sb.append(te.getText());
      }

      return sb.toString();
   }

   public void setThrowExceptionOnRecoverableError(boolean flag) {
      this.throwOnError = flag;
   }

   public void setThrowExceptionOnWarning(boolean flag) {
      this.throwOnWarning = flag;
   }

   public EndElementEvent popElement() throws SAXException {
      int startTags = 0;
      int endTags = 0;
      ElementEvent ee = null;

      while(this.hasNext()) {
         XMLEvent e = this.next();
         if (e instanceof ElementEvent) {
            ee = (ElementEvent)e;
            if (e instanceof StartElementEvent) {
               ++startTags;
            } else if (e instanceof EndElementEvent) {
               ++endTags;
               if (endTags > startTags) {
                  return (EndElementEvent)e;
               }
            }
         }
      }

      throw new SAXException("Stream consumed without unmatched end element, last element was: " + ee);
   }

   public EndElementEvent skipElement() throws SAXException {
      return this.startElement() != null ? this.skipElement() : null;
   }

   protected abstract boolean parseSome() throws SAXException;

   public void setDocumentLocator(Locator locator) {
      this.locator = locator;
   }
}
