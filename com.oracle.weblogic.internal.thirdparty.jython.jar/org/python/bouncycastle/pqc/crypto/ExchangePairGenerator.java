package org.python.bouncycastle.pqc.crypto;

import org.python.bouncycastle.crypto.params.AsymmetricKeyParameter;

public interface ExchangePairGenerator {
   /** @deprecated */
   ExchangePair GenerateExchange(AsymmetricKeyParameter var1);

   ExchangePair generateExchange(AsymmetricKeyParameter var1);
}
