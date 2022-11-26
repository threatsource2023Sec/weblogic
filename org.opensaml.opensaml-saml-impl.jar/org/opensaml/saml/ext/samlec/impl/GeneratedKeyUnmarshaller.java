package org.opensaml.saml.ext.samlec.impl;

import javax.xml.namespace.QName;
import net.shibboleth.utilities.java.support.xml.QNameSupport;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.core.xml.schema.XSBooleanValue;
import org.opensaml.core.xml.schema.impl.XSBase64BinaryUnmarshaller;
import org.opensaml.saml.ext.samlec.GeneratedKey;
import org.w3c.dom.Attr;

public class GeneratedKeyUnmarshaller extends XSBase64BinaryUnmarshaller {
   protected void processAttribute(XMLObject samlObject, Attr attribute) throws UnmarshallingException {
      GeneratedKey key = (GeneratedKey)samlObject;
      QName attrName = QNameSupport.getNodeQName(attribute);
      if (GeneratedKey.SOAP11_MUST_UNDERSTAND_ATTR_NAME.equals(attrName)) {
         key.setSOAP11MustUnderstand(XSBooleanValue.valueOf(attribute.getValue()));
      } else if (GeneratedKey.SOAP11_ACTOR_ATTR_NAME.equals(attrName)) {
         key.setSOAP11Actor(attribute.getValue());
      } else {
         super.processAttribute(samlObject, attribute);
      }

   }
}
