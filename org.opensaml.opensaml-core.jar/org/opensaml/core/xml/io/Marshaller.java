package org.opensaml.core.xml.io;

import javax.annotation.Nonnull;
import org.opensaml.core.xml.XMLObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public interface Marshaller {
   @Nonnull
   Element marshall(@Nonnull XMLObject var1) throws MarshallingException;

   @Nonnull
   Element marshall(@Nonnull XMLObject var1, @Nonnull Document var2) throws MarshallingException;

   @Nonnull
   Element marshall(@Nonnull XMLObject var1, @Nonnull Element var2) throws MarshallingException;
}
