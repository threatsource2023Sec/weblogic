package org.apache.xml.security.stax.ext;

import java.util.Set;
import javax.xml.stream.XMLStreamException;
import org.apache.xml.security.exceptions.XMLSecurityException;
import org.apache.xml.security.stax.ext.stax.XMLSecEvent;

public interface InputProcessor {
   void addBeforeProcessor(Object var1);

   Set getBeforeProcessors();

   void addAfterProcessor(Object var1);

   Set getAfterProcessors();

   XMLSecurityConstants.Phase getPhase();

   XMLSecEvent processNextHeaderEvent(InputProcessorChain var1) throws XMLStreamException, XMLSecurityException;

   XMLSecEvent processNextEvent(InputProcessorChain var1) throws XMLStreamException, XMLSecurityException;

   void doFinal(InputProcessorChain var1) throws XMLStreamException, XMLSecurityException;
}
