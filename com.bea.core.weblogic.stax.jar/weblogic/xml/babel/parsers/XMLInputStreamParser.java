package weblogic.xml.babel.parsers;

import java.io.IOException;
import java.io.Reader;
import java.util.List;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import weblogic.xml.babel.adapters.ElementConsumer;
import weblogic.xml.babel.adapters.ElementFactory;
import weblogic.xml.babel.baseparser.BaseParser;
import weblogic.xml.babel.baseparser.Element;
import weblogic.xml.babel.baseparser.ParseException;
import weblogic.xml.babel.baseparser.PrefixMapping;
import weblogic.xml.babel.baseparser.ProcessingInstruction;
import weblogic.xml.babel.scanner.ScannerException;
import weblogic.xml.stream.XMLStreamException;
import weblogic.xml.stream.events.EndDocumentEvent;
import weblogic.xml.stream.events.StartDocumentEvent;

public class XMLInputStreamParser extends BaseParser implements XMLStreamParser {
   protected boolean reachedEOF = false;
   protected ElementFactory inputStreamFactory;
   protected ElementConsumer inputStreamConsumer;
   private List ns2remove;

   public XMLInputStreamParser(InputSource input, ElementFactory factory, ElementConsumer consumer) throws IOException, XMLStreamException {
      try {
         this.inputStreamFactory = factory;
         this.inputStreamFactory.setBaseParser(this);
         this.inputStreamConsumer = consumer;
         this.init(input);
         this.primeXMLDecl();
      } catch (ScannerException var5) {
         throw (XMLStreamException)this.inputStreamFactory.create(var5);
      }
   }

   public void recycleStream(Reader input) throws XMLStreamException {
      try {
         this.reachedEOF = false;
         this.recycle(input);
      } catch (ScannerException var3) {
         throw (XMLStreamException)this.inputStreamFactory.create(var3);
      } catch (IOException var4) {
         throw new XMLStreamException(var4);
      }

      this.primeXMLDecl();
   }

   private void primeXMLDecl() throws XMLStreamException {
      try {
         Element firstElement = this.internalParseSome();
         if (firstElement != null && firstElement.type != 9) {
            StartDocumentEvent startDocumentEvent = new StartDocumentEvent();
            if (firstElement.type == 4) {
               ProcessingInstruction pi = (ProcessingInstruction)firstElement;
               if (pi.isXMLDecl()) {
                  startDocumentEvent.setVersion(pi.getVersion());
                  startDocumentEvent.setEncoding(pi.getEncoding());
                  startDocumentEvent.setStandalone(pi.getStandalone());
                  this.inputStreamConsumer.addFirst(startDocumentEvent);
               } else {
                  this.inputStreamConsumer.addFirst(startDocumentEvent);
                  this.inputStreamConsumer.add(this.inputStreamFactory.dispatch(firstElement));
               }
            } else {
               this.inputStreamConsumer.addFirst(startDocumentEvent);
               this.inputStreamConsumer.add(this.inputStreamFactory.dispatch(firstElement));
            }

         } else {
            this.reachedEOF = true;
         }
      } catch (IOException var4) {
         throw new XMLStreamException(var4);
      }
   }

   protected void putNamespaceURI(String key, String value) throws SAXException {
      super.putNamespaceURI(key, value);
      this.inputStreamConsumer.add(this.inputStreamFactory.create(key, value));
   }

   protected void removeNamespaceURI(List keys) throws SAXException {
      super.removeNamespaceURI(keys);
      this.cacheNamespaces(keys);
   }

   public boolean hasNext() throws XMLStreamException {
      if (this.reachedEOF) {
         return false;
      } else {
         boolean val = !this.getCurrentToken().isEOF();
         if (!val && !this.reachedEOF) {
            if (this.stack.size() > 0) {
               throw new XMLStreamException("Unbalanced ELEMENT: " + this.stack.pop());
            } else {
               this.reachedEOF = true;
               this.inputStreamConsumer.add(new EndDocumentEvent());
               return true;
            }
         } else {
            return val;
         }
      }
   }

   protected Element internalParseSome() throws XMLStreamException {
      try {
         Element element = super.parseSome();
         return element;
      } catch (ScannerException var2) {
         throw (XMLStreamException)this.inputStreamFactory.create(var2);
      } catch (ParseException var3) {
         throw (XMLStreamException)this.inputStreamFactory.create(var3);
      } catch (IOException var4) {
         throw (XMLStreamException)this.inputStreamFactory.create(var4);
      }
   }

   public boolean streamParseSome() throws XMLStreamException {
      Element element = this.internalParseSome();
      if (element != null) {
         this.inputStreamConsumer.add(this.inputStreamFactory.dispatch(element));
         if (element.type == 3) {
            this.removeCachedNamespaces();
         }

         return true;
      } else {
         return false;
      }
   }

   private void removeCachedNamespaces() {
      if (this.ns2remove != null) {
         int i = 0;

         for(int len = this.ns2remove.size(); i < len; ++i) {
            PrefixMapping prefixMapping = (PrefixMapping)this.ns2remove.get(i);
            if (prefixMapping.getUri() == null) {
               this.inputStreamConsumer.add(this.inputStreamFactory.create(prefixMapping.getPrefix()));
            } else {
               this.inputStreamConsumer.add(this.inputStreamFactory.create(prefixMapping));
            }
         }
      }

      this.ns2remove = null;
   }

   private void cacheNamespaces(List keys) {
      this.ns2remove = keys;
   }
}
