package org.opensaml.soap.wspolicy.impl;

import org.opensaml.core.xml.AbstractXMLObjectBuilder;
import org.opensaml.soap.wspolicy.WSPolicyObject;
import org.opensaml.soap.wspolicy.WSPolicyObjectBuilder;

public abstract class AbstractWSPolicyObjectBuilder extends AbstractXMLObjectBuilder implements WSPolicyObjectBuilder {
   public abstract WSPolicyObject buildObject();
}
