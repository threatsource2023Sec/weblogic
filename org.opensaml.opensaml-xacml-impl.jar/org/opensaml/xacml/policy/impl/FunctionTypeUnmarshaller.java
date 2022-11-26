package org.opensaml.xacml.policy.impl;

import net.shibboleth.utilities.java.support.primitive.StringSupport;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.xacml.impl.AbstractXACMLObjectUnmarshaller;
import org.opensaml.xacml.policy.FunctionType;
import org.w3c.dom.Attr;

public class FunctionTypeUnmarshaller extends AbstractXACMLObjectUnmarshaller {
   protected void processAttribute(XMLObject xmlObject, Attr attribute) throws UnmarshallingException {
      if (attribute.getLocalName().equals("FunctionId")) {
         FunctionType functionType = (FunctionType)xmlObject;
         functionType.setFunctionId(StringSupport.trimOrNull(attribute.getValue()));
      } else {
         super.processAttribute(xmlObject, attribute);
      }

   }
}
