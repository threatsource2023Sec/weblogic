package org.opensaml.xacml.policy.impl;

import net.shibboleth.utilities.java.support.primitive.StringSupport;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.core.xml.schema.impl.XSStringUnmarshaller;
import org.opensaml.xacml.policy.IdReferenceType;
import org.w3c.dom.Attr;

public class IdReferenceTypeUnmarshaller extends XSStringUnmarshaller {
   protected void processAttribute(XMLObject xmlObject, Attr attribute) throws UnmarshallingException {
      IdReferenceType idReferenceType = (IdReferenceType)xmlObject;
      if (attribute.getLocalName().equals("EarliestVersion")) {
         idReferenceType.setEarliestVersion(StringSupport.trimOrNull(attribute.getValue()));
      } else if (attribute.getLocalName().equals("LatestVersion")) {
         idReferenceType.setLatestVersion(StringSupport.trimOrNull(attribute.getValue()));
      } else if (attribute.getLocalName().equals("Version")) {
         idReferenceType.setVersion(StringSupport.trimOrNull(attribute.getValue()));
      } else {
         super.processAttribute(xmlObject, attribute);
      }

   }
}
