package org.opensaml.soap.common;

import org.opensaml.core.xml.XMLObjectBuilder;

public interface SOAPObjectBuilder extends XMLObjectBuilder {
   SOAPObject buildObject();
}
