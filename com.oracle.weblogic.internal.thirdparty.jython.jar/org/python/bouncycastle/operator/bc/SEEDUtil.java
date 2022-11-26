package org.python.bouncycastle.operator.bc;

import org.python.bouncycastle.asn1.kisa.KISAObjectIdentifiers;
import org.python.bouncycastle.asn1.x509.AlgorithmIdentifier;

class SEEDUtil {
   static AlgorithmIdentifier determineKeyEncAlg() {
      return new AlgorithmIdentifier(KISAObjectIdentifiers.id_npki_app_cmsSeed_wrap);
   }
}
