package org.apache.xml.security.stax.ext;

import java.util.List;
import javax.xml.stream.XMLStreamException;
import org.apache.xml.security.exceptions.XMLSecurityException;
import org.apache.xml.security.stax.ext.stax.XMLSecEvent;

public interface InputProcessorChain extends ProcessorChain {
   void addProcessor(InputProcessor var1);

   void removeProcessor(InputProcessor var1);

   List getProcessors();

   InboundSecurityContext getSecurityContext();

   DocumentContext getDocumentContext();

   InputProcessorChain createSubChain(InputProcessor var1) throws XMLStreamException, XMLSecurityException;

   InputProcessorChain createSubChain(InputProcessor var1, boolean var2) throws XMLStreamException, XMLSecurityException;

   XMLSecEvent processHeaderEvent() throws XMLStreamException, XMLSecurityException;

   XMLSecEvent processEvent() throws XMLStreamException, XMLSecurityException;
}
