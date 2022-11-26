package org.opensaml.xmlsec.encryption;

import javax.annotation.Nonnull;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.XMLObjectBuilder;

public interface XMLEncryptionBuilder extends XMLObjectBuilder {
   @Nonnull
   XMLObject buildObject();
}
