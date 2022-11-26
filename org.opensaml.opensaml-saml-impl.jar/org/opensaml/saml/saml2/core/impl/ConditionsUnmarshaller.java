package org.opensaml.saml.saml2.core.impl;

import com.google.common.base.Strings;
import org.joda.time.DateTime;
import org.joda.time.chrono.ISOChronology;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.saml.common.AbstractSAMLObjectUnmarshaller;
import org.opensaml.saml.saml2.core.Condition;
import org.opensaml.saml.saml2.core.Conditions;
import org.w3c.dom.Attr;

public class ConditionsUnmarshaller extends AbstractSAMLObjectUnmarshaller {
   protected void processChildElement(XMLObject parentObject, XMLObject childObject) throws UnmarshallingException {
      Conditions conditions = (Conditions)parentObject;
      if (childObject instanceof Condition) {
         conditions.getConditions().add((Condition)childObject);
      } else {
         super.processChildElement(parentObject, childObject);
      }

   }

   protected void processAttribute(XMLObject samlObject, Attr attribute) throws UnmarshallingException {
      Conditions conditions = (Conditions)samlObject;
      if (attribute.getNamespaceURI() == null) {
         if (attribute.getLocalName().equals("NotBefore") && !Strings.isNullOrEmpty(attribute.getValue())) {
            conditions.setNotBefore(new DateTime(attribute.getValue(), ISOChronology.getInstanceUTC()));
         } else if (attribute.getLocalName().equals("NotOnOrAfter") && !Strings.isNullOrEmpty(attribute.getValue())) {
            conditions.setNotOnOrAfter(new DateTime(attribute.getValue(), ISOChronology.getInstanceUTC()));
         } else {
            super.processAttribute(samlObject, attribute);
         }
      } else {
         super.processAttribute(samlObject, attribute);
      }

   }
}
