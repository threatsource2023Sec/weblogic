package org.opensaml.xmlsec.encryption;

import java.util.List;
import javax.annotation.Nonnull;
import javax.xml.namespace.QName;
import org.opensaml.core.xml.XMLObject;

public interface Transforms extends XMLObject {
   String DEFAULT_ELEMENT_LOCAL_NAME = "Transforms";
   QName DEFAULT_ELEMENT_NAME = new QName("http://www.w3.org/2001/04/xmlenc#", "Transforms", "xenc");
   String TYPE_LOCAL_NAME = "TransformsType";
   QName TYPE_NAME = new QName("http://www.w3.org/2001/04/xmlenc#", "TransformsType", "xenc");

   @Nonnull
   List getTransforms();
}
