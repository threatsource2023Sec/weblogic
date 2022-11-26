package weblogic.xml.babel.parsers;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import weblogic.xml.babel.baseparser.SAXElementFactory;
import weblogic.xml.stream.XMLStreamException;
import weblogicx.xml.stream.XMLEvent;
import weblogicx.xml.stream.XMLEventStream;
import weblogicx.xml.stream.XMLEventStreamBase;

public class BabelXMLEventStream extends XMLEventStreamBase implements StreamParserHandler {
   private InputSource inputSource;
   private StreamParser parser;

   public void startDocument(InputSource inputSource) throws SAXException, IOException {
      super.startDocument(inputSource);
      this.inputSource = inputSource;
      this.parser = new StreamParser(inputSource, this);
      this.running = true;
   }

   public void endDocument(boolean flush) throws SAXException, IOException {
      super.endDocument(flush);
      InputStream inputstream = this.inputSource.getByteStream();
      if (inputstream != null) {
         inputstream.close();
      }

      Reader reader = this.inputSource.getCharacterStream();
      if (reader != null) {
         reader.close();
      }

   }

   protected boolean parseSome() throws SAXException {
      try {
         if (!this.parser.hasNext()) {
            return true;
         }
      } catch (XMLStreamException var2) {
         throw new SAXException(var2);
      }

      this.parser.streamParseSome();
      return false;
   }

   public static void main(String[] args) throws Exception {
      XMLEventStream xes = new BabelXMLEventStream();
      xes.startDocument(SAXElementFactory.createInputSource(args[0]));

      while(xes.hasNext()) {
         XMLEvent event = xes.next();
         System.out.println(event);
      }

   }
}
