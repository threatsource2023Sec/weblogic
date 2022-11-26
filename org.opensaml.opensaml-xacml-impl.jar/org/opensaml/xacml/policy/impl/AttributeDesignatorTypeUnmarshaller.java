package org.opensaml.xacml.policy.impl;

import net.shibboleth.utilities.java.support.primitive.StringSupport;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.core.xml.schema.XSBooleanValue;
import org.opensaml.xacml.impl.AbstractXACMLObjectUnmarshaller;
import org.opensaml.xacml.policy.AttributeDesignatorType;
import org.w3c.dom.Attr;

public class AttributeDesignatorTypeUnmarshaller extends AbstractXACMLObjectUnmarshaller {
   protected void processAttribute(XMLObject xmlObject, Attr attribute) throws UnmarshallingException {
      AttributeDesignatorType attributeDesignatorType = (AttributeDesignatorType)xmlObject;
      if (attribute.getLocalName().equals("AttributeId")) {
         attributeDesignatorType.setAttributeId(StringSupport.trimOrNull(attribute.getValue()));
      } else if (attribute.getLocalName().equals("DataType")) {
         attributeDesignatorType.setDataType(StringSupport.trimOrNull(attribute.getValue()));
      } else if (attribute.getLocalName().equals("Issuer")) {
         attributeDesignatorType.setIssuer(StringSupport.trimOrNull(attribute.getValue()));
      } else if (attribute.getLocalName().equals("MustBePresent")) {
         if (!"True".equals(attribute.getValue()) && !"true".equals(attribute.getValue())) {
            attributeDesignatorType.setMustBePresentXSBoolean(XSBooleanValue.valueOf("0"));
         } else {
            attributeDesignatorType.setMustBePresentXSBoolean(XSBooleanValue.valueOf("1"));
         }
      } else {
         super.processAttribute(xmlObject, attribute);
      }

   }
}
