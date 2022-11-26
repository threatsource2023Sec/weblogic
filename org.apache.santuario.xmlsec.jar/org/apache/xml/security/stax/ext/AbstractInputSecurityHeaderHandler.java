package org.apache.xml.security.stax.ext;

import java.util.ArrayList;
import java.util.Deque;
import java.util.Iterator;
import java.util.List;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import org.apache.xml.security.exceptions.XMLSecurityException;
import org.apache.xml.security.stax.ext.stax.XMLSecEvent;
import org.apache.xml.security.stax.impl.XMLSecurityEventReader;

public abstract class AbstractInputSecurityHeaderHandler implements XMLSecurityHeaderHandler {
   protected Object parseStructure(Deque eventDeque, int index, XMLSecurityProperties securityProperties) throws XMLSecurityException {
      try {
         Unmarshaller unmarshaller = XMLSecurityConstants.getJaxbUnmarshaller(securityProperties.isDisableSchemaValidation());
         return unmarshaller.unmarshal(new XMLSecurityEventReader(eventDeque, index));
      } catch (JAXBException var5) {
         if (var5.getCause() != null && var5.getCause() instanceof Exception) {
            throw new XMLSecurityException((Exception)var5.getCause());
         } else {
            throw new XMLSecurityException(var5);
         }
      }
   }

   protected List getElementPath(Deque eventDeque) throws XMLSecurityException {
      XMLSecEvent xmlSecEvent = (XMLSecEvent)eventDeque.peek();
      return xmlSecEvent.getElementPath();
   }

   protected XMLSecEvent getResponsibleStartXMLEvent(Deque eventDeque, int index) {
      Iterator xmlSecEventIterator = eventDeque.descendingIterator();
      int curIdx = 0;

      while(curIdx++ < index) {
         xmlSecEventIterator.next();
      }

      return (XMLSecEvent)xmlSecEventIterator.next();
   }

   protected List getResponsibleXMLSecEvents(Deque xmlSecEvents, int index) {
      List xmlSecEventList = new ArrayList();
      Iterator xmlSecEventIterator = xmlSecEvents.descendingIterator();
      int curIdx = 0;

      while(curIdx++ < index && xmlSecEventIterator.hasNext()) {
         xmlSecEventIterator.next();
      }

      while(xmlSecEventIterator.hasNext()) {
         xmlSecEventList.add((XMLSecEvent)xmlSecEventIterator.next());
      }

      return xmlSecEventList;
   }
}
