package org.opensaml.soap.wsaddressing.impl;

import org.opensaml.core.xml.AbstractXMLObjectBuilder;
import org.opensaml.soap.wsaddressing.WSAddressingObject;
import org.opensaml.soap.wsaddressing.WSAddressingObjectBuilder;

public abstract class AbstractWSAddressingObjectBuilder extends AbstractXMLObjectBuilder implements WSAddressingObjectBuilder {
   public abstract WSAddressingObject buildObject();
}
