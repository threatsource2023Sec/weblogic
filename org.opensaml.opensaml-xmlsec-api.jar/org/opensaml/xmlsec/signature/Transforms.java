package org.opensaml.xmlsec.signature;

import java.util.List;
import javax.annotation.Nonnull;
import javax.xml.namespace.QName;
import org.opensaml.core.xml.XMLObject;

public interface Transforms extends XMLObject {
   String DEFAULT_ELEMENT_LOCAL_NAME = "Transforms";
   QName DEFAULT_ELEMENT_NAME = new QName("http://www.w3.org/2000/09/xmldsig#", "Transforms", "ds");
   String TYPE_LOCAL_NAME = "TransformsType";
   QName TYPE_NAME = new QName("http://www.w3.org/2000/09/xmldsig#", "TransformsType", "ds");

   @Nonnull
   List getTransforms();
}
