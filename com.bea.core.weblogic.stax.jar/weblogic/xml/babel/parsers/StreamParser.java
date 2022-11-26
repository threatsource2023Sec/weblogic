package weblogic.xml.babel.parsers;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.List;
import org.xml.sax.InputSource;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import weblogic.xml.babel.baseparser.BaseParser;
import weblogic.xml.babel.baseparser.CharDataElement;
import weblogic.xml.babel.baseparser.Element;
import weblogic.xml.babel.baseparser.EndElement;
import weblogic.xml.babel.baseparser.ParseException;
import weblogic.xml.babel.baseparser.PrefixMapping;
import weblogic.xml.babel.baseparser.ProcessingInstruction;
import weblogic.xml.babel.baseparser.SAXElementFactory;
import weblogic.xml.babel.baseparser.Space;
import weblogic.xml.babel.baseparser.StartElement;
import weblogic.xml.babel.baseparser.StreamElementFactory;
import weblogic.xml.babel.reader.XmlReader;
import weblogic.xml.babel.scanner.ScannerException;
import weblogicx.xml.stream.XMLEvent;

public class StreamParser extends BaseParser {
   StreamElementFactory streamElementFactory;
   SAXElementFactory saxElementFactory;
   StreamParserHandler streamParserHandler;

   public StreamParser(InputSource input, StreamParserHandler streamParserHandler) throws IOException, SAXException {
      try {
         this.streamElementFactory = new StreamElementFactory(this);
         this.saxElementFactory = new SAXElementFactory();
         this.streamParserHandler = streamParserHandler;
         this.init(input);
      } catch (ScannerException var5) {
         this.saxElementFactory = new SAXElementFactory();
         SAXElementFactory var10000 = this.saxElementFactory;
         SAXParseException spe = SAXElementFactory.createSAXParseException(var5, this.getLocator());
         throw spe;
      }
   }

   public StreamParser(InputStream stream, StreamParserHandler streamParserHandler) throws IOException, SAXException {
      try {
         this.streamElementFactory = new StreamElementFactory(this);
         this.saxElementFactory = new SAXElementFactory();
         this.streamParserHandler = streamParserHandler;
         this.init(XmlReader.createReader(stream));
      } catch (ScannerException var5) {
         this.saxElementFactory = new SAXElementFactory();
         SAXElementFactory var10000 = this.saxElementFactory;
         SAXParseException spe = SAXElementFactory.createSAXParseException(var5, this.getLocator());
         throw spe;
      }
   }

   public StreamParser(Reader reader, StreamParserHandler streamParserHandler) throws IOException, SAXException {
      try {
         this.streamElementFactory = new StreamElementFactory(this);
         this.saxElementFactory = new SAXElementFactory();
         this.streamParserHandler = streamParserHandler;
         this.init(reader);
      } catch (ScannerException var5) {
         this.saxElementFactory = new SAXElementFactory();
         SAXElementFactory var10000 = this.saxElementFactory;
         SAXParseException spe = SAXElementFactory.createSAXParseException(var5, this.getLocator());
         throw spe;
      }
   }

   protected void putNamespaceURI(String key, String value) throws SAXException {
      super.putNamespaceURI(key, value);
      this.streamParserHandler.put(this.streamElementFactory.createStartPrefixMappingEvent(key, value));
   }

   protected void removeNamespaceURI(List keys) throws SAXException {
      super.removeNamespaceURI(keys);
      int i = 0;

      for(int len = keys.size(); i < len; ++i) {
         PrefixMapping prefixMapping = (PrefixMapping)keys.get(i);
         if (prefixMapping.getUri() == null) {
            this.streamParserHandler.put(this.streamElementFactory.createEndPrefixMappingEvent(prefixMapping.getPrefix()));
         } else {
            this.streamParserHandler.put(this.streamElementFactory.createChangePrefixMappingEvent(prefixMapping));
         }
      }

   }

   public Locator getLocator() {
      SAXElementFactory var10000 = this.saxElementFactory;
      return SAXElementFactory.createLocator((BaseParser)this);
   }

   public void streamParseSome() throws SAXParseException, SAXException {
      SAXElementFactory var10000;
      SAXParseException spe;
      try {
         Element element = super.parseSome();
         switch (element.type) {
            case 0:
               this.streamParserHandler.put(this.streamElementFactory.createStartElementEvent((StartElement)element));
            case 1:
            case 2:
            case 6:
            default:
               break;
            case 3:
               this.streamParserHandler.put(this.streamElementFactory.createEndElementEvent((EndElement)element));
               break;
            case 4:
               this.streamParserHandler.put(this.streamElementFactory.createProcessingInstructionEvent((ProcessingInstruction)element));
               break;
            case 5:
               this.streamParserHandler.put(this.streamElementFactory.createTextEvent((CharDataElement)element));
               break;
            case 7:
               this.streamParserHandler.put(this.streamElementFactory.createTextEvent((Space)element));
         }

      } catch (ScannerException var3) {
         var10000 = this.saxElementFactory;
         spe = SAXElementFactory.createSAXParseException(var3, this.getLocator());
         var3.printTokenStack();
         var3.printErrorLine();
         this.streamParserHandler.put(this.streamElementFactory.createFatalErrorEvent(spe));
         throw spe;
      } catch (ParseException var4) {
         var10000 = this.saxElementFactory;
         spe = SAXElementFactory.createSAXParseException(var4, this.getLocator());
         this.streamParserHandler.put(this.streamElementFactory.createFatalErrorEvent(spe));
         throw spe;
      } catch (IOException var5) {
         var10000 = this.saxElementFactory;
         spe = SAXElementFactory.createSAXParseException((Exception)var5, this.getLocator());
         this.streamParserHandler.put(this.streamElementFactory.createFatalErrorEvent(spe));
         throw spe;
      }
   }

   public static void main(String[] args) {
      try {
         InputStream stream = new BufferedInputStream(new FileInputStream(args[0]));
         Reader reader = XmlReader.createReader(stream);
         StreamParser parser = new StreamParser(reader, new StreamParserHandler() {
            public boolean put(XMLEvent event) {
               System.out.println(event);
               return true;
            }
         });

         while(parser.hasNext()) {
            parser.streamParseSome();
         }
      } catch (SAXParseException var4) {
         System.out.println("----- SAXParseException ----");
         System.out.println(var4);
         var4.printStackTrace();
      } catch (Exception var5) {
         System.out.println("-----JAVA   ----");
         var5.printStackTrace();
      }

   }
}
