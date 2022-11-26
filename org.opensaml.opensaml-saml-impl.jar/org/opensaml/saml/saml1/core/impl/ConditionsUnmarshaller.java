package org.opensaml.saml.saml1.core.impl;

import com.google.common.base.Strings;
import org.joda.time.DateTime;
import org.joda.time.chrono.ISOChronology;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.saml.common.AbstractSAMLObjectUnmarshaller;
import org.opensaml.saml.saml1.core.Condition;
import org.opensaml.saml.saml1.core.Conditions;
import org.w3c.dom.Attr;

public class ConditionsUnmarshaller extends AbstractSAMLObjectUnmarshaller {
   protected void processChildElement(XMLObject parentSAMLObject, XMLObject childSAMLObject) throws UnmarshallingException {
      Conditions conditions = (Conditions)parentSAMLObject;
      if (childSAMLObject instanceof Condition) {
         conditions.getConditions().add((Condition)childSAMLObject);
      } else {
         super.processChildElement(parentSAMLObject, childSAMLObject);
      }

   }

   protected void processAttribute(XMLObject samlObject, Attr attribute) throws UnmarshallingException {
      Conditions conditions = (Conditions)samlObject;
      if (attribute.getNamespaceURI() == null) {
         if ("NotBefore".equals(attribute.getLocalName()) && !Strings.isNullOrEmpty(attribute.getValue())) {
            conditions.setNotBefore(new DateTime(attribute.getValue(), ISOChronology.getInstanceUTC()));
         } else if ("NotOnOrAfter".equals(attribute.getLocalName()) && !Strings.isNullOrEmpty(attribute.getValue())) {
            conditions.setNotOnOrAfter(new DateTime(attribute.getValue(), ISOChronology.getInstanceUTC()));
         } else {
            super.processAttribute(samlObject, attribute);
         }
      } else {
         super.processAttribute(samlObject, attribute);
      }

   }
}
