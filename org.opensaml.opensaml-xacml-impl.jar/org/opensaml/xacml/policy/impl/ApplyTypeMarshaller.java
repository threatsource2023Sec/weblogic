package org.opensaml.xacml.policy.impl;

import com.google.common.base.Strings;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.MarshallingException;
import org.opensaml.xacml.impl.AbstractXACMLObjectMarshaller;
import org.opensaml.xacml.policy.ApplyType;
import org.w3c.dom.Element;

public class ApplyTypeMarshaller extends AbstractXACMLObjectMarshaller {
   protected void marshallAttributes(XMLObject xmlObject, Element domElement) throws MarshallingException {
      ApplyType applyType = (ApplyType)xmlObject;
      if (!Strings.isNullOrEmpty(applyType.getFunctionId())) {
         domElement.setAttributeNS((String)null, "FunctionId", applyType.getFunctionId());
      }

   }
}
