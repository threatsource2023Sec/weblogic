package weblogic.xml.babel.adapters;

import java.io.IOException;
import java.util.Iterator;
import org.xml.sax.SAXException;
import weblogic.xml.babel.baseparser.Attribute;
import weblogic.xml.babel.baseparser.CharDataElement;
import weblogic.xml.babel.baseparser.CommentElement;
import weblogic.xml.babel.baseparser.Element;
import weblogic.xml.babel.baseparser.EndElement;
import weblogic.xml.babel.baseparser.ParseException;
import weblogic.xml.babel.baseparser.PrefixMapping;
import weblogic.xml.babel.baseparser.ProcessingInstruction;
import weblogic.xml.babel.baseparser.Space;
import weblogic.xml.babel.baseparser.StartElement;
import weblogic.xml.babel.scanner.ScannerException;
import weblogic.xml.stream.XMLName;
import weblogic.xml.stream.XMLStreamException;
import weblogic.xml.stream.events.AttributeImpl;
import weblogic.xml.stream.events.ChangePrefixMappingEvent;
import weblogic.xml.stream.events.CharacterDataEvent;
import weblogic.xml.stream.events.CommentEvent;
import weblogic.xml.stream.events.EndElementEvent;
import weblogic.xml.stream.events.EndPrefixMappingEvent;
import weblogic.xml.stream.events.LocationImpl;
import weblogic.xml.stream.events.Name;
import weblogic.xml.stream.events.ProcessingInstructionEvent;
import weblogic.xml.stream.events.SpaceEvent;
import weblogic.xml.stream.events.StartElementEvent;
import weblogic.xml.stream.events.StartPrefixMappingEvent;

public class XMLInputStreamElementFactory extends BaseFactory implements ElementFactory {
   public LocationImpl getLocation(Element element) {
      return new LocationImpl(element.getColumn(), element.getLine(), this.baseparser.getPublicId(), this.baseparser.getSystemId());
   }

   public XMLName getName(Element element) {
      return new Name(element.getURI(), element.getLocalName(), element.getPrefix());
   }

   public Object create(CharDataElement charDataElement) {
      return charDataElement.isSpace() ? new SpaceEvent(charDataElement.getContent(), this.getLocation(charDataElement)) : new CharacterDataEvent(charDataElement.getContent(), this.getLocation(charDataElement));
   }

   public Object create(StartElement startElement) {
      StartElementEvent startElementEvent = new StartElementEvent(this.getName(startElement), this.getLocation(startElement));
      Iterator it = startElement.getAttributes().iterator();

      while(true) {
         while(it.hasNext()) {
            Attribute attribute = (Attribute)it.next();
            AttributeImpl impl = new AttributeImpl(this.getName(attribute), attribute.getValue(), "CDATA");
            if (!attribute.isNameSpaceDeclaration() && !attribute.declaresDefaultNameSpace()) {
               startElementEvent.addAttribute(impl);
            } else {
               startElementEvent.addNamespace(impl);
            }
         }

         startElementEvent.setNamespaceMap(this.baseparser.getNameSpaceMap());
         return startElementEvent;
      }
   }

   public Object create(Space space) {
      return new SpaceEvent(space.getContent(), this.getLocation(space));
   }

   public Object create(CommentElement commentElement) {
      return new CommentEvent(commentElement.getContent(), this.getLocation(commentElement));
   }

   public Object create(ProcessingInstruction processingInstruction) {
      return new ProcessingInstructionEvent(this.getName(processingInstruction), processingInstruction.getData());
   }

   public Object create(EndElement endElement) {
      return new EndElementEvent(this.getName(endElement), this.getLocation(endElement));
   }

   public Object create(String prefix, String uri) {
      return new StartPrefixMappingEvent(prefix, uri);
   }

   public Object create(String prefix) {
      return new EndPrefixMappingEvent(prefix);
   }

   public Object create(PrefixMapping prefixMapping) {
      return new ChangePrefixMappingEvent(prefixMapping.getOldUri(), prefixMapping.getUri(), prefixMapping.getPrefix());
   }

   public Throwable create(ParseException parseException) {
      return new XMLStreamException(parseException);
   }

   public Throwable create(ScannerException scannerException) {
      return new XMLStreamException(scannerException);
   }

   public Throwable create(SAXException saxException) {
      return new XMLStreamException(saxException);
   }

   public Throwable create(IOException ioException) {
      return new XMLStreamException(ioException);
   }
}
