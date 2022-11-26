package org.apache.xml.security.stax.ext.stax;

import javax.xml.stream.events.StartDocument;

public interface XMLSecStartDocument extends XMLSecEvent, StartDocument {
   XMLSecStartDocument asStartDocument();
}
