package org.opensaml.xacml.policy.impl;

import net.shibboleth.utilities.java.support.primitive.StringSupport;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.core.xml.schema.XSBooleanValue;
import org.opensaml.xacml.impl.AbstractXACMLObjectUnmarshaller;
import org.opensaml.xacml.policy.AttributeSelectorType;
import org.w3c.dom.Attr;

public class AttributeSelectorTypeUnmarshaller extends AbstractXACMLObjectUnmarshaller {
   protected void processAttribute(XMLObject xmlObject, Attr attribute) throws UnmarshallingException {
      AttributeSelectorType attributeSelectorType = (AttributeSelectorType)xmlObject;
      if (attribute.getLocalName().equals("RequestContextPath")) {
         attributeSelectorType.setRequestContextPath(StringSupport.trimOrNull(attribute.getValue()));
      } else if (attribute.getLocalName().equals("DataType")) {
         attributeSelectorType.setDataType(StringSupport.trimOrNull(attribute.getValue()));
      } else if (attribute.getLocalName().equals("MustBePresent")) {
         if (!"True".equals(attribute.getValue()) && !"true".equals(attribute.getValue())) {
            attributeSelectorType.setMustBePresentXSBoolean(XSBooleanValue.valueOf("0"));
         } else {
            attributeSelectorType.setMustBePresentXSBoolean(XSBooleanValue.valueOf("1"));
         }
      } else {
         super.processAttribute(xmlObject, attribute);
      }

   }
}
