package org.opensaml.xmlsec.signature;

import javax.annotation.Nullable;
import org.opensaml.core.xml.XMLObject;

public interface SignableXMLObject extends XMLObject {
   boolean isSigned();

   @Nullable
   Signature getSignature();

   void setSignature(@Nullable Signature var1);
}
