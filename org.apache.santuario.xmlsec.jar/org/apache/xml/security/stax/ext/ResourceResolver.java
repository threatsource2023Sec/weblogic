package org.apache.xml.security.stax.ext;

import java.io.InputStream;
import org.apache.xml.security.exceptions.XMLSecurityException;
import org.apache.xml.security.stax.ext.stax.XMLSecStartElement;

public interface ResourceResolver {
   boolean isSameDocumentReference();

   boolean matches(XMLSecStartElement var1);

   InputStream getInputStreamFromExternalReference() throws XMLSecurityException;
}
