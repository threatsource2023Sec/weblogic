package org.opensaml.xmlsec.signature;

import javax.annotation.Nonnull;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.XMLObjectBuilder;

public interface XMLSignatureBuilder extends XMLObjectBuilder {
   @Nonnull
   XMLObject buildObject();
}
