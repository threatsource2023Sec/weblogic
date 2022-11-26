package org.opensaml.saml.saml2.ecp.impl;

import javax.xml.namespace.QName;
import net.shibboleth.utilities.java.support.xml.QNameSupport;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.core.xml.schema.XSBooleanValue;
import org.opensaml.saml.common.AbstractSAMLObjectUnmarshaller;
import org.opensaml.saml.saml2.core.SubjectConfirmationData;
import org.opensaml.saml.saml2.ecp.SubjectConfirmation;
import org.w3c.dom.Attr;

public class SubjectConfirmationUnmarshaller extends AbstractSAMLObjectUnmarshaller {
   protected void processChildElement(XMLObject parentObject, XMLObject childObject) throws UnmarshallingException {
      SubjectConfirmation sc = (SubjectConfirmation)parentObject;
      if (childObject instanceof SubjectConfirmationData) {
         sc.setSubjectConfirmationData((SubjectConfirmationData)childObject);
      } else {
         super.processChildElement(parentObject, childObject);
      }

   }

   protected void processAttribute(XMLObject samlObject, Attr attribute) throws UnmarshallingException {
      SubjectConfirmation sc = (SubjectConfirmation)samlObject;
      QName attrName = QNameSupport.getNodeQName(attribute);
      if (SubjectConfirmation.SOAP11_MUST_UNDERSTAND_ATTR_NAME.equals(attrName)) {
         sc.setSOAP11MustUnderstand(XSBooleanValue.valueOf(attribute.getValue()));
      } else if (SubjectConfirmation.SOAP11_ACTOR_ATTR_NAME.equals(attrName)) {
         sc.setSOAP11Actor(attribute.getValue());
      } else if (attribute.getLocalName().equals("Method")) {
         sc.setMethod(attribute.getValue());
      } else {
         super.processAttribute(samlObject, attribute);
      }

   }
}
