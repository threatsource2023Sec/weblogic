package org.opensaml.saml.saml2.core.impl;

import com.google.common.base.Strings;
import org.joda.time.DateTime;
import org.joda.time.chrono.ISOChronology;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.saml.common.AbstractSAMLObjectUnmarshaller;
import org.opensaml.saml.saml2.core.SubjectConfirmationData;
import org.w3c.dom.Attr;

public class SubjectConfirmationDataUnmarshaller extends AbstractSAMLObjectUnmarshaller {
   protected void processChildElement(XMLObject parentSAMLObject, XMLObject childSAMLObject) throws UnmarshallingException {
      SubjectConfirmationData subjectCD = (SubjectConfirmationData)parentSAMLObject;
      subjectCD.getUnknownXMLObjects().add(childSAMLObject);
   }

   protected void processAttribute(XMLObject samlObject, Attr attribute) throws UnmarshallingException {
      SubjectConfirmationData subjectCD = (SubjectConfirmationData)samlObject;
      if (attribute.getNamespaceURI() == null) {
         if (attribute.getLocalName().equals("NotBefore") && !Strings.isNullOrEmpty(attribute.getValue())) {
            subjectCD.setNotBefore(new DateTime(attribute.getValue(), ISOChronology.getInstanceUTC()));
         } else if (attribute.getLocalName().equals("NotOnOrAfter") && !Strings.isNullOrEmpty(attribute.getValue())) {
            subjectCD.setNotOnOrAfter(new DateTime(attribute.getValue(), ISOChronology.getInstanceUTC()));
         } else if (attribute.getLocalName().equals("Recipient")) {
            subjectCD.setRecipient(attribute.getValue());
         } else if (attribute.getLocalName().equals("InResponseTo")) {
            subjectCD.setInResponseTo(attribute.getValue());
         } else if (attribute.getLocalName().equals("Address")) {
            subjectCD.setAddress(attribute.getValue());
         } else {
            super.processAttribute(samlObject, attribute);
         }
      } else {
         this.processUnknownAttribute(subjectCD, attribute);
      }

   }
}
