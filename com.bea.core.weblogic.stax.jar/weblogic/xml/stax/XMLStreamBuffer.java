package weblogic.xml.stax;

import java.io.FileInputStream;
import java.util.List;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

public class XMLStreamBuffer extends XMLStreamPlayer {
   private List states;
   private int index = 0;

   public XMLStreamBuffer(List states) {
      if (states == null) {
         throw new IllegalArgumentException("List of states may not be null");
      } else {
         this.states = states;

         try {
            this.advance();
         } catch (XMLStreamException var3) {
            this.setState((EventState)null);
         }

      }
   }

   protected void advance() throws XMLStreamException {
      if (this.index < this.states.size()) {
         this.setState((EventState)this.states.get(this.index));
      }

      ++this.index;
   }

   public boolean hasNext() throws XMLStreamException {
      return this.index <= this.states.size();
   }

   public String toString() {
      return XMLStreamReaderBase.printEvent(this);
   }

   public static void main(String[] args) throws Exception {
      SAXParserFactory spf = SAXParserFactory.newInstance();
      spf.setNamespaceAware(true);
      XMLReader xmlReader = spf.newSAXParser().getXMLReader();
      SAXHandlerContext context = new SAXHandlerContext();
      xmlReader.setContentHandler(context);
      xmlReader.parse(new InputSource(new FileInputStream(args[0])));
      XMLStreamBuffer buffer = new XMLStreamBuffer(context.getStates());
      XMLOutputFactory xmlof = XMLOutputFactory.newInstance();
      XMLStreamWriter xmlw = xmlof.createXMLStreamWriter(System.out);
      ReaderToWriter rtow = new ReaderToWriter(xmlw);

      while(buffer.hasNext()) {
         rtow.write(buffer);
         buffer.next();
      }

      xmlw.flush();
   }
}
