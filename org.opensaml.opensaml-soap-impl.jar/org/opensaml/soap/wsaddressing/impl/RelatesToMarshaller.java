package org.opensaml.soap.wsaddressing.impl;

import net.shibboleth.utilities.java.support.primitive.StringSupport;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.MarshallingException;
import org.opensaml.core.xml.schema.impl.XSURIMarshaller;
import org.opensaml.core.xml.util.XMLObjectSupport;
import org.opensaml.soap.wsaddressing.RelatesTo;
import org.w3c.dom.Element;

public class RelatesToMarshaller extends XSURIMarshaller {
   protected void marshallAttributes(XMLObject xmlObject, Element domElement) throws MarshallingException {
      RelatesTo relatesTo = (RelatesTo)xmlObject;
      String relationshipType = StringSupport.trimOrNull(relatesTo.getRelationshipType());
      if (relationshipType != null) {
         domElement.setAttributeNS((String)null, "RelationshipType", relationshipType);
      }

      XMLObjectSupport.marshallAttributeMap(relatesTo.getUnknownAttributes(), domElement);
   }
}
