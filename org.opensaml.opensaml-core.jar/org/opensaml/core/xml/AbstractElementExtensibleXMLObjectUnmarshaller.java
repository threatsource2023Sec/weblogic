package org.opensaml.core.xml;

import javax.annotation.Nonnull;
import org.opensaml.core.xml.io.AbstractXMLObjectUnmarshaller;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.w3c.dom.Attr;

public abstract class AbstractElementExtensibleXMLObjectUnmarshaller extends AbstractXMLObjectUnmarshaller {
   protected void processChildElement(@Nonnull XMLObject parentXMLObject, @Nonnull XMLObject childXMLObject) throws UnmarshallingException {
      ElementExtensibleXMLObject any = (ElementExtensibleXMLObject)parentXMLObject;
      any.getUnknownXMLObjects().add(childXMLObject);
   }

   protected void processAttribute(@Nonnull XMLObject xmlObject, @Nonnull Attr attribute) throws UnmarshallingException {
   }

   protected void processElementContent(@Nonnull XMLObject xmlObject, @Nonnull String elementContent) {
   }
}
