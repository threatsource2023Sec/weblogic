package weblogicx.xml.stream;

import org.xml.sax.Locator;
import org.xml.sax.SAXException;

public class SubElementEventStream extends SubEventStream {
   private boolean done = false;
   private int startTags = 0;
   private int endTags = 0;

   protected SubElementEventStream(XMLEventStreamBase parent, Locator locator) throws SAXException {
      this.init(parent, locator);
      if (!this.hasStartElement()) {
         throw new SAXException("No sub elements present");
      }
   }

   public boolean put(XMLEvent e) {
      if (!this.done && e != null) {
         if (e instanceof StartElementEvent) {
            ++this.startTags;
         } else if (e instanceof EndElementEvent) {
            ++this.endTags;
            if (this.startTags <= this.endTags) {
               this.done = true;
            }
         }

         super.put(e);
      }

      return !this.done;
   }

   protected boolean parseSome() throws SAXException {
      try {
         return this.done ? true : super.parseSome();
      } catch (SAXException var2) {
         throw var2;
      } catch (Exception var3) {
         var3.printStackTrace();
         throw new SAXException("Exception parsing: " + var3);
      }
   }
}
