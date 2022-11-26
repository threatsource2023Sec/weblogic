package org.apache.xml.security.stax.ext.stax;

import javax.xml.stream.events.EndDocument;

public interface XMLSecEndDocument extends XMLSecEvent, EndDocument {
   XMLSecEndDocument asEndEndDocument();
}
