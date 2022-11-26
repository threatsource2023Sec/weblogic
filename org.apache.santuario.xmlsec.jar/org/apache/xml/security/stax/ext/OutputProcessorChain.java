package org.apache.xml.security.stax.ext;

import java.util.List;
import javax.xml.stream.XMLStreamException;
import org.apache.xml.security.exceptions.XMLSecurityException;
import org.apache.xml.security.stax.ext.stax.XMLSecEvent;
import org.apache.xml.security.stax.ext.stax.XMLSecStartElement;

public interface OutputProcessorChain extends ProcessorChain {
   void addProcessor(OutputProcessor var1);

   void removeProcessor(OutputProcessor var1);

   List getProcessors();

   OutboundSecurityContext getSecurityContext();

   DocumentContext getDocumentContext();

   OutputProcessorChain createSubChain(OutputProcessor var1) throws XMLStreamException, XMLSecurityException;

   OutputProcessorChain createSubChain(OutputProcessor var1, XMLSecStartElement var2) throws XMLStreamException, XMLSecurityException;

   void processEvent(XMLSecEvent var1) throws XMLStreamException, XMLSecurityException;
}
