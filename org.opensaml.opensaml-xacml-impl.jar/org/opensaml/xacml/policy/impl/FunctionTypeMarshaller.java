package org.opensaml.xacml.policy.impl;

import com.google.common.base.Strings;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.MarshallingException;
import org.opensaml.xacml.impl.AbstractXACMLObjectMarshaller;
import org.opensaml.xacml.policy.FunctionType;
import org.w3c.dom.Element;

public class FunctionTypeMarshaller extends AbstractXACMLObjectMarshaller {
   protected void marshallAttributes(XMLObject xmlObject, Element domElement) throws MarshallingException {
      FunctionType functionType = (FunctionType)xmlObject;
      if (!Strings.isNullOrEmpty(functionType.getFunctionId())) {
         domElement.setAttributeNS((String)null, "FunctionId", functionType.getFunctionId());
      }

   }
}
