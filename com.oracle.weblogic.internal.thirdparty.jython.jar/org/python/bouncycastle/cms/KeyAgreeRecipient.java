package org.python.bouncycastle.cms;

import org.python.bouncycastle.asn1.ASN1OctetString;
import org.python.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.python.bouncycastle.asn1.x509.SubjectPublicKeyInfo;

public interface KeyAgreeRecipient extends Recipient {
   RecipientOperator getRecipientOperator(AlgorithmIdentifier var1, AlgorithmIdentifier var2, SubjectPublicKeyInfo var3, ASN1OctetString var4, byte[] var5) throws CMSException;

   AlgorithmIdentifier getPrivateKeyAlgorithmIdentifier();
}
