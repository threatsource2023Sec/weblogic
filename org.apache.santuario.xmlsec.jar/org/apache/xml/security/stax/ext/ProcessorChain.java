package org.apache.xml.security.stax.ext;

import javax.xml.stream.XMLStreamException;
import org.apache.xml.security.exceptions.XMLSecurityException;

public interface ProcessorChain {
   void reset();

   void doFinal() throws XMLStreamException, XMLSecurityException;
}
