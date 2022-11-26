package weblogic.xml.babel.stream;

import java.io.IOException;
import java.util.ArrayList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import weblogic.utils.collections.CircularQueue;
import weblogic.xml.babel.adapters.SAXAdapter;
import weblogic.xml.stream.XMLEvent;
import weblogic.xml.stream.XMLStreamException;
import weblogic.xml.stream.events.NullEvent;
import weblogic.xml.stream.util.XMLPullReader;

public class SAXInputStream extends XMLInputStreamBase {
   private XMLPullReader inputStream;
   private SAXAdapter adapter;

   public SAXInputStream(XMLPullReader inputStream) {
      this.open = false;
      this.children = new ArrayList();
      this.inputStream = inputStream;
      this.adapter = new SAXAdapter(this);
      this.inputStream.setContentHandler(this.adapter);
      this.inputStream.setDTDHandler(this.adapter);
      this.inputStream.setErrorHandler(this.adapter);
   }

   public void open(InputSource inputSource) throws XMLStreamException {
      this.elementQ = new CircularQueue(8);

      try {
         this.inputStream.parseSomeSetup(inputSource);
         this.inputStream.parseSome();
         this.open = true;
      } catch (IOException var3) {
         throw new XMLStreamException("Unable to instantiate the stream, the error was: " + var3.getMessage());
      } catch (SAXException var4) {
         throw new XMLStreamException("Unable to instantiate the stream, the error was: " + var4.getMessage());
      }
   }

   public boolean parseSome() throws XMLStreamException {
      boolean hasMore = true;

      do {
         try {
            hasMore = this.inputStream.parseSome();
         } catch (SAXException var3) {
            throw new XMLStreamException(var3);
         }
      } while(hasMore && this.elementQ.isEmpty());

      return hasMore;
   }

   public boolean hasNext() throws XMLStreamException {
      if (!this.open) {
         return false;
      } else if (!this.elementQ.isEmpty()) {
         return true;
      } else if (this.inputStream != null && this.parseSome()) {
         return true;
      } else {
         return !this.elementQ.isEmpty();
      }
   }

   public XMLEvent peek() throws XMLStreamException {
      return (XMLEvent)(this.hasNext() ? (XMLEvent)this.elementQ.peek() : new NullEvent());
   }

   public void close() throws XMLStreamException {
      super.close();
   }
}
