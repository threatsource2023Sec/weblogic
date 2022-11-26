package org.apache.xml.security.stax.ext;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;
import javax.xml.stream.XMLStreamException;
import org.apache.xml.security.exceptions.XMLSecurityException;
import org.apache.xml.security.stax.ext.stax.XMLSecEvent;

public interface Transformer {
   void setOutputStream(OutputStream var1) throws XMLSecurityException;

   void setTransformer(Transformer var1) throws XMLSecurityException;

   void setProperties(Map var1) throws XMLSecurityException;

   XMLSecurityConstants.TransformMethod getPreferredTransformMethod(XMLSecurityConstants.TransformMethod var1);

   void transform(XMLSecEvent var1) throws XMLStreamException;

   void transform(InputStream var1) throws XMLStreamException;

   void doFinal() throws XMLStreamException;
}
