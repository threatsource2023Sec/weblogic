package org.opensaml.xmlsec.signature;

import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.xml.namespace.QName;
import org.opensaml.core.xml.XMLObject;

public interface Transform extends XMLObject {
   String DEFAULT_ELEMENT_LOCAL_NAME = "Transform";
   QName DEFAULT_ELEMENT_NAME = new QName("http://www.w3.org/2000/09/xmldsig#", "Transform", "ds");
   String TYPE_LOCAL_NAME = "TransformType";
   QName TYPE_NAME = new QName("http://www.w3.org/2000/09/xmldsig#", "TransformType", "ds");
   String ALGORITHM_ATTRIB_NAME = "Algorithm";

   @Nullable
   String getAlgorithm();

   void setAlgorithm(@Nullable String var1);

   @Nonnull
   List getXMLObjects(@Nonnull QName var1);

   @Nonnull
   List getXPaths();

   @Nonnull
   List getAllChildren();
}
