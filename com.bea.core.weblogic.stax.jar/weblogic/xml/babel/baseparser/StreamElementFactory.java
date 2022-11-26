package weblogic.xml.babel.baseparser;

import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import weblogicx.xml.stream.ChangePrefixMappingEvent;
import weblogicx.xml.stream.EndElementEvent;
import weblogicx.xml.stream.EndPrefixMappingEvent;
import weblogicx.xml.stream.FatalErrorEvent;
import weblogicx.xml.stream.ProcessingInstructionEvent;
import weblogicx.xml.stream.StartElementEvent;
import weblogicx.xml.stream.StartPrefixMappingEvent;
import weblogicx.xml.stream.TextEvent;

public class StreamElementFactory {
   private SAXElementFactory saxElementFactory = new SAXElementFactory();
   private BaseParser parser;

   public StreamElementFactory(BaseParser parser) {
      this.parser = parser;
   }

   public StartElementEvent createStartElementEvent(StartElement startElement) throws SAXException {
      SAXElementFactory var10003 = this.saxElementFactory;
      Locator var2 = SAXElementFactory.createLocator((Element)startElement);
      String var10004 = startElement.name;
      String var10005 = SAXElementFactory.nullToEmptyString(startElement.uri);
      String var10006 = startElement.getRawName();
      SAXElementFactory var10007 = this.saxElementFactory;
      return new StartElementEvent(startElement, var2, var10004, var10005, var10006, SAXElementFactory.createAttributes(startElement.attributes));
   }

   public EndElementEvent createEndElementEvent(EndElement endElement) {
      SAXElementFactory var10003 = this.saxElementFactory;
      return new EndElementEvent(endElement, SAXElementFactory.createLocator((Element)endElement), endElement.name, SAXElementFactory.nullToEmptyString(endElement.uri), endElement.getRawName());
   }

   public TextEvent createTextEvent(CharDataElement charDataElement) {
      SAXElementFactory var10003 = this.saxElementFactory;
      return new TextEvent(charDataElement, SAXElementFactory.createLocator((Element)charDataElement), charDataElement.toString());
   }

   public TextEvent createTextEvent(Space spaceElement) {
      SAXElementFactory var10003 = this.saxElementFactory;
      return new TextEvent(spaceElement, SAXElementFactory.createLocator((Element)spaceElement), spaceElement.toString());
   }

   public StartPrefixMappingEvent createStartPrefixMappingEvent(String prefix, String uri) {
      SAXElementFactory var10003 = this.saxElementFactory;
      return new StartPrefixMappingEvent(this, SAXElementFactory.createLocator(this.parser), prefix, uri);
   }

   public EndPrefixMappingEvent createEndPrefixMappingEvent(String prefix) {
      SAXElementFactory var10003 = this.saxElementFactory;
      return new EndPrefixMappingEvent(this, SAXElementFactory.createLocator(this.parser), prefix);
   }

   public ChangePrefixMappingEvent createChangePrefixMappingEvent(PrefixMapping prefixMapping) {
      SAXElementFactory var10003 = this.saxElementFactory;
      return new ChangePrefixMappingEvent(this, SAXElementFactory.createLocator(this.parser), prefixMapping.getPrefix(), prefixMapping.getUri(), prefixMapping.getOldUri());
   }

   public ProcessingInstructionEvent createProcessingInstructionEvent(ProcessingInstruction processingInstruction) {
      SAXElementFactory var10003 = this.saxElementFactory;
      return new ProcessingInstructionEvent(processingInstruction, SAXElementFactory.createLocator((Element)processingInstruction), processingInstruction.getName(), processingInstruction.getTarget());
   }

   public FatalErrorEvent createFatalErrorEvent(SAXParseException spe) {
      SAXElementFactory var10003 = this.saxElementFactory;
      return new FatalErrorEvent(spe, SAXElementFactory.createLocator(this.parser), spe);
   }
}
