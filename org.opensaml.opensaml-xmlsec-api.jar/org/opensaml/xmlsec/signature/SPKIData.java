package org.opensaml.xmlsec.signature;

import java.util.List;
import javax.annotation.Nonnull;
import javax.xml.namespace.QName;
import org.opensaml.core.xml.XMLObject;

public interface SPKIData extends XMLObject {
   String DEFAULT_ELEMENT_LOCAL_NAME = "SPKIData";
   QName DEFAULT_ELEMENT_NAME = new QName("http://www.w3.org/2000/09/xmldsig#", "SPKIData", "ds");
   String TYPE_LOCAL_NAME = "SPKIDataType";
   QName TYPE_NAME = new QName("http://www.w3.org/2000/09/xmldsig#", "SPKIDataType", "ds");

   @Nonnull
   List getXMLObjects();

   @Nonnull
   List getXMLObjects(@Nonnull QName var1);

   @Nonnull
   List getSPKISexps();
}
