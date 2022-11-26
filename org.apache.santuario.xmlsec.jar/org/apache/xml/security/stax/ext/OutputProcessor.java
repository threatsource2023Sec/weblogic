package org.apache.xml.security.stax.ext;

import java.util.Set;
import javax.xml.stream.XMLStreamException;
import org.apache.xml.security.exceptions.XMLSecurityException;
import org.apache.xml.security.stax.ext.stax.XMLSecEvent;

public interface OutputProcessor {
   void setXMLSecurityProperties(XMLSecurityProperties var1);

   void setAction(XMLSecurityConstants.Action var1);

   void init(OutputProcessorChain var1) throws XMLSecurityException;

   void addBeforeProcessor(Object var1);

   Set getBeforeProcessors();

   void addAfterProcessor(Object var1);

   Set getAfterProcessors();

   XMLSecurityConstants.Phase getPhase();

   void processNextEvent(XMLSecEvent var1, OutputProcessorChain var2) throws XMLStreamException, XMLSecurityException;

   void doFinal(OutputProcessorChain var1) throws XMLStreamException, XMLSecurityException;
}
