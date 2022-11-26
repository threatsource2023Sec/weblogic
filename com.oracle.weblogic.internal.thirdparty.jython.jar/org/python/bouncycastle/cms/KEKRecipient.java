package org.python.bouncycastle.cms;

import org.python.bouncycastle.asn1.x509.AlgorithmIdentifier;

public interface KEKRecipient extends Recipient {
   RecipientOperator getRecipientOperator(AlgorithmIdentifier var1, AlgorithmIdentifier var2, byte[] var3) throws CMSException;
}
