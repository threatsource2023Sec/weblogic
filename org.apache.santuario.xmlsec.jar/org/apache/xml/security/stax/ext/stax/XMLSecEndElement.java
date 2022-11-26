package org.apache.xml.security.stax.ext.stax;

import javax.xml.stream.events.EndElement;

public interface XMLSecEndElement extends XMLSecEvent, EndElement {
   XMLSecEndElement asEndElement();
}
