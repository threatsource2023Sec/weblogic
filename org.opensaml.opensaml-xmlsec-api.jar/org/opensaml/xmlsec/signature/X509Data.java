package org.opensaml.xmlsec.signature;

import java.util.List;
import javax.annotation.Nonnull;
import javax.xml.namespace.QName;
import org.opensaml.core.xml.XMLObject;

public interface X509Data extends XMLObject {
   String DEFAULT_ELEMENT_LOCAL_NAME = "X509Data";
   QName DEFAULT_ELEMENT_NAME = new QName("http://www.w3.org/2000/09/xmldsig#", "X509Data", "ds");
   String TYPE_LOCAL_NAME = "X509DataType";
   QName TYPE_NAME = new QName("http://www.w3.org/2000/09/xmldsig#", "X509DataType", "ds");

   @Nonnull
   List getXMLObjects();

   @Nonnull
   List getXMLObjects(@Nonnull QName var1);

   @Nonnull
   List getX509IssuerSerials();

   @Nonnull
   List getX509SKIs();

   @Nonnull
   List getX509SubjectNames();

   @Nonnull
   List getX509Certificates();

   @Nonnull
   List getX509CRLs();

   @Nonnull
   List getX509Digests();
}
