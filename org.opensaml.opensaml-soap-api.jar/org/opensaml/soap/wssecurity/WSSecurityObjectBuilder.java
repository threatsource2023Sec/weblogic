package org.opensaml.soap.wssecurity;

import org.opensaml.core.xml.XMLObjectBuilder;

public interface WSSecurityObjectBuilder extends XMLObjectBuilder {
   WSSecurityObject buildObject();
}
