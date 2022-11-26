package org.opensaml.core.xml.io;

import javax.annotation.Nonnull;
import org.opensaml.core.xml.XMLObject;
import org.w3c.dom.Element;

public interface Unmarshaller {
   @Nonnull
   XMLObject unmarshall(@Nonnull Element var1) throws UnmarshallingException;
}
