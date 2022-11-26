package org.opensaml.xacml.ctx.impl;

import net.shibboleth.utilities.java.support.xml.ElementSupport;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.MarshallingException;
import org.opensaml.xacml.ctx.DecisionType;
import org.opensaml.xacml.impl.AbstractXACMLObjectMarshaller;
import org.w3c.dom.Element;

public class DecisionTypeMarshaller extends AbstractXACMLObjectMarshaller {
   protected void marshallElementContent(XMLObject samlObject, Element domElement) throws MarshallingException {
      DecisionType decision = (DecisionType)samlObject;
      ElementSupport.appendTextContent(domElement, decision.getDecision().toString());
   }
}
