package weblogicx.xml.stream.helpers;

import org.xml.sax.ContentHandler;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import weblogicx.xml.stream.ElementEvent;
import weblogicx.xml.stream.EndElementEvent;
import weblogicx.xml.stream.EndPrefixMappingEvent;
import weblogicx.xml.stream.ErrorEvent;
import weblogicx.xml.stream.ExceptionEvent;
import weblogicx.xml.stream.FatalErrorEvent;
import weblogicx.xml.stream.PrefixMappingEvent;
import weblogicx.xml.stream.StartElementEvent;
import weblogicx.xml.stream.StartPrefixMappingEvent;
import weblogicx.xml.stream.TextEvent;
import weblogicx.xml.stream.WarningEvent;
import weblogicx.xml.stream.XMLEvent;
import weblogicx.xml.stream.XMLEventStream;

public class SAXProcessor {
   public static void process(XMLEventStream xes, ContentHandler ch) throws SAXException {
      process(xes, ch, (ErrorHandler)null);
   }

   public static void process(XMLEventStream xes, ContentHandler ch, ErrorHandler eh) throws SAXException {
      int startTags = 0;
      int endTags = 0;
      boolean started = false;
      if (!xes.hasStartElement()) {
         throw new SAXException("Could not process document, no more elements");
      } else {
         while(xes.hasNext()) {
            XMLEvent e = xes.next();
            if (!started) {
               ch.setDocumentLocator(e.getLocator());
               ch.startDocument();
               started = true;
            }

            if (e instanceof ElementEvent) {
               if (e instanceof StartElementEvent) {
                  ++startTags;
                  StartElementEvent see = (StartElementEvent)e;
                  ch.startElement(see.getNamespaceURI(), see.getName(), see.getQualifiedName(), see.getAttributes());
               } else if (e instanceof EndElementEvent) {
                  ++endTags;
                  EndElementEvent eee = (EndElementEvent)e;
                  ch.endElement(eee.getNamespaceURI(), eee.getName(), eee.getQualifiedName());
                  if (endTags == startTags) {
                     break;
                  }
               }
            } else if (e instanceof TextEvent) {
               TextEvent te = (TextEvent)e;
               char[] chars = te.getText().toCharArray();
               ch.characters(chars, 0, chars.length);
            } else if (e instanceof PrefixMappingEvent) {
               if (e instanceof StartPrefixMappingEvent) {
                  StartPrefixMappingEvent pme = (StartPrefixMappingEvent)e;
                  ch.startPrefixMapping(pme.getPrefix(), pme.getURI());
               } else if (e instanceof EndPrefixMappingEvent) {
                  EndPrefixMappingEvent pme = (EndPrefixMappingEvent)e;
                  ch.endPrefixMapping(pme.getPrefix());
               }
            } else if (eh != null && e instanceof ExceptionEvent) {
               ExceptionEvent ee = (ExceptionEvent)e;
               SAXParseException saxpe = ee.getException();
               if (e instanceof ErrorEvent) {
                  eh.error(saxpe);
               } else if (e instanceof FatalErrorEvent) {
                  eh.fatalError(saxpe);
               } else if (e instanceof WarningEvent) {
                  eh.warning(saxpe);
               }
            }
         }

         ch.endDocument();
      }
   }
}
