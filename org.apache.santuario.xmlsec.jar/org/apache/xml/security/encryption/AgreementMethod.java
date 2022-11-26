package org.apache.xml.security.encryption;

import java.util.Iterator;
import org.apache.xml.security.keys.KeyInfo;
import org.w3c.dom.Element;

public interface AgreementMethod {
   byte[] getKANonce();

   void setKANonce(byte[] var1);

   Iterator getAgreementMethodInformation();

   void addAgreementMethodInformation(Element var1);

   void revoveAgreementMethodInformation(Element var1);

   KeyInfo getOriginatorKeyInfo();

   void setOriginatorKeyInfo(KeyInfo var1);

   KeyInfo getRecipientKeyInfo();

   void setRecipientKeyInfo(KeyInfo var1);

   String getAlgorithm();
}
