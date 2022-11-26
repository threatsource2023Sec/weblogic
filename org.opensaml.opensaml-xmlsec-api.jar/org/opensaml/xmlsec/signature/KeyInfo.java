package org.opensaml.xmlsec.signature;

import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.xml.namespace.QName;
import org.opensaml.core.xml.XMLObject;

public interface KeyInfo extends XMLObject {
   String DEFAULT_ELEMENT_LOCAL_NAME = "KeyInfo";
   QName DEFAULT_ELEMENT_NAME = new QName("http://www.w3.org/2000/09/xmldsig#", "KeyInfo", "ds");
   String TYPE_LOCAL_NAME = "KeyInfoType";
   QName TYPE_NAME = new QName("http://www.w3.org/2000/09/xmldsig#", "KeyInfoType", "ds");
   String ID_ATTRIB_NAME = "Id";

   @Nullable
   String getID();

   void setID(@Nullable String var1);

   @Nonnull
   List getXMLObjects();

   @Nonnull
   List getXMLObjects(@Nonnull QName var1);

   @Nonnull
   List getKeyNames();

   @Nonnull
   List getKeyValues();

   @Nonnull
   List getDEREncodedKeyValues();

   @Nonnull
   List getRetrievalMethods();

   @Nonnull
   List getKeyInfoReferences();

   @Nonnull
   List getX509Datas();

   @Nonnull
   List getPGPDatas();

   @Nonnull
   List getSPKIDatas();

   @Nonnull
   List getMgmtDatas();

   @Nonnull
   List getAgreementMethods();

   @Nonnull
   List getEncryptedKeys();
}
