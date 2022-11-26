package org.python.bouncycastle.cms.jcajce;

import org.python.bouncycastle.asn1.x509.AlgorithmIdentifier;

interface KeyMaterialGenerator {
   byte[] generateKDFMaterial(AlgorithmIdentifier var1, int var2, byte[] var3);
}
