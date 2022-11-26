package weblogicx.xml.stream;

import java.util.HashMap;
import java.util.Map;
import org.xml.sax.InputSource;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;

public class SubEventStream extends XMLEventStreamBase {
   protected XMLEventStreamBase parent;

   protected SubEventStream() throws SAXException {
   }

   protected SubEventStream(XMLEventStreamBase parent, Locator locator) throws SAXException {
      this.init(parent, locator);
   }

   protected void init(XMLEventStreamBase parent, Locator locator) throws SAXException {
      this.running = true;
      if (parent.prefixMap != null) {
         this.prefixMap = (Map)((HashMap)parent.prefixMap).clone();
      }

      this.parent = parent;
      this.setDocumentLocator(locator);
      int size = parent.q.size();

      for(int i = 0; i < size; ++i) {
         XMLEvent e = (XMLEvent)parent.q.get();
         parent.q.put(e);
         XMLEvent f = (XMLEvent)e.clone();
         this.put(f);
      }

      parent.addXES(this);
   }

   public void startDocument(InputSource is) throws SAXException {
      throw new SAXException("Cannot start a sub sream");
   }

   public void endDocument(boolean flush) throws SAXException {
      this.running = false;
      if (flush) {
         while(this.hasNext()) {
            this.next();
         }
      }

      this.parent.removeXES(this);
   }

   protected boolean parseSome() throws SAXException {
      try {
         return this.parent.parseSome();
      } catch (SAXException var2) {
         throw var2;
      } catch (Exception var3) {
         throw new SAXException("Exception parsing: " + var3);
      }
   }
}
