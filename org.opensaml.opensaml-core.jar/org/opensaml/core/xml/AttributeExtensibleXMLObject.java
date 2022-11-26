package org.opensaml.core.xml;

import javax.annotation.Nonnull;
import org.opensaml.core.xml.util.AttributeMap;

public interface AttributeExtensibleXMLObject extends XMLObject {
   @Nonnull
   AttributeMap getUnknownAttributes();
}
