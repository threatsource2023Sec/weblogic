package org.opensaml.core.xml;

import java.util.List;
import javax.annotation.Nonnull;
import javax.xml.namespace.QName;

public interface ElementExtensibleXMLObject extends XMLObject {
   @Nonnull
   List getUnknownXMLObjects();

   @Nonnull
   List getUnknownXMLObjects(@Nonnull QName var1);
}
