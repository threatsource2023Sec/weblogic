package weblogic.xml.babel.examples.interpreter;

import org.xml.sax.SAXException;
import weblogicx.xml.stream.EndElementEvent;
import weblogicx.xml.stream.StartElementEvent;
import weblogicx.xml.stream.TextEvent;
import weblogicx.xml.stream.XMLEvent;
import weblogicx.xml.stream.XMLEventStream;

public class ExpressionReader {
   public static final int STARTEVENT = 1;
   public static final int ENDEVENT = 2;
   public static final int TEXTEVENT = 3;
   public static final int OTHEREVENT = 4;
   private XMLEvent xmlEvent;
   private XMLEventStream stream;

   public XMLEvent getEvent() {
      return this.xmlEvent;
   }

   public ExpressionReader(XMLEventStream stream) throws SAXException {
      this.stream = stream;
      this.read();
   }

   public boolean read(int type) throws SAXException {
      this.read();
      if (this.getType(this.xmlEvent) != type) {
         throw new SAXException("Unexpected Event");
      } else {
         return true;
      }
   }

   public boolean read(int type, String name) throws SAXException {
      this.read();
      if (this.getType(this.xmlEvent) != type) {
         throw new SAXException("Unexpected Element:" + this.xmlEvent);
      } else {
         return true;
      }
   }

   public boolean read() throws SAXException {
      for(this.xmlEvent = this.stream.next(); this.getType(this.xmlEvent) == 4 && this.stream.hasNext(); this.xmlEvent = this.stream.next()) {
      }

      return true;
   }

   public void skipSpace() throws SAXException {
      while(this.getType(this.xmlEvent) == 3 && this.stream.hasNext()) {
         this.xmlEvent = this.stream.next();
      }

   }

   public int getType(XMLEvent event) {
      if (event instanceof StartElementEvent) {
         return 1;
      } else if (event instanceof EndElementEvent) {
         return 2;
      } else {
         return event instanceof TextEvent ? 3 : 4;
      }
   }
}
