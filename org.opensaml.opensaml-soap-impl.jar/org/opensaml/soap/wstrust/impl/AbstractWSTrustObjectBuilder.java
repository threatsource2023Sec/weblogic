package org.opensaml.soap.wstrust.impl;

import org.opensaml.core.xml.AbstractXMLObjectBuilder;
import org.opensaml.soap.wstrust.WSTrustObject;
import org.opensaml.soap.wstrust.WSTrustObjectBuilder;

public abstract class AbstractWSTrustObjectBuilder extends AbstractXMLObjectBuilder implements WSTrustObjectBuilder {
   public abstract WSTrustObject buildObject();
}
