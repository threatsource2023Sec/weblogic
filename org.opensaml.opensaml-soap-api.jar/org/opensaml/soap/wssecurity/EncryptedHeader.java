package org.opensaml.soap.wssecurity;

import javax.xml.namespace.QName;
import org.opensaml.soap.soap11.ActorBearing;
import org.opensaml.soap.soap11.MustUnderstandBearing;
import org.opensaml.soap.soap12.RelayBearing;
import org.opensaml.soap.soap12.RoleBearing;
import org.opensaml.xmlsec.encryption.EncryptedData;

public interface EncryptedHeader extends IdBearing, MustUnderstandBearing, ActorBearing, org.opensaml.soap.soap12.MustUnderstandBearing, RoleBearing, RelayBearing, WSSecurityObject {
   String ELEMENT_LOCAL_NAME = "EncryptedHeader";
   QName ELEMENT_NAME = new QName("http://docs.oasis-open.org/wss/oasis-wss-wssecurity-secext-1.1.xsd", "EncryptedHeader", "wsse11");
   String TYPE_LOCAL_NAME = "EncryptedHeaderType";
   QName TYPE_NAME = new QName("http://docs.oasis-open.org/wss/oasis-wss-wssecurity-secext-1.1.xsd", "EncryptedHeaderType", "wsse11");

   EncryptedData getEncryptedData();

   void setEncryptedData(EncryptedData var1);
}
