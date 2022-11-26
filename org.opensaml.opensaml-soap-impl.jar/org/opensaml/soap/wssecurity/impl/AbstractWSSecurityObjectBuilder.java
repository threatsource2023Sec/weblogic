package org.opensaml.soap.wssecurity.impl;

import org.opensaml.core.xml.AbstractXMLObjectBuilder;
import org.opensaml.soap.wssecurity.WSSecurityObject;
import org.opensaml.soap.wssecurity.WSSecurityObjectBuilder;

public abstract class AbstractWSSecurityObjectBuilder extends AbstractXMLObjectBuilder implements WSSecurityObjectBuilder {
   public abstract WSSecurityObject buildObject();
}
