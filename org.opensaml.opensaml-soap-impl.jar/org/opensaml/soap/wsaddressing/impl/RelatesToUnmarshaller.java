package org.opensaml.soap.wsaddressing.impl;

import net.shibboleth.utilities.java.support.primitive.StringSupport;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.core.xml.schema.impl.XSURIUnmarshaller;
import org.opensaml.core.xml.util.XMLObjectSupport;
import org.opensaml.soap.wsaddressing.RelatesTo;
import org.w3c.dom.Attr;

public class RelatesToUnmarshaller extends XSURIUnmarshaller {
   protected void processAttribute(XMLObject xmlObject, Attr attribute) throws UnmarshallingException {
      RelatesTo relatesTo = (RelatesTo)xmlObject;
      if ("RelationshipType".equals(attribute.getLocalName())) {
         relatesTo.setRelationshipType(StringSupport.trimOrNull(attribute.getValue()));
      } else {
         XMLObjectSupport.unmarshallToAttributeMap(relatesTo.getUnknownAttributes(), attribute);
      }

   }
}
