package org.opensaml.xacml.impl;

import org.opensaml.core.xml.AbstractXMLObjectBuilder;
import org.opensaml.xacml.XACMLObject;
import org.opensaml.xacml.XACMLObjectBuilder;

public abstract class AbstractXACMLObjectBuilder extends AbstractXMLObjectBuilder implements XACMLObjectBuilder {
   public abstract XACMLObject buildObject();
}
