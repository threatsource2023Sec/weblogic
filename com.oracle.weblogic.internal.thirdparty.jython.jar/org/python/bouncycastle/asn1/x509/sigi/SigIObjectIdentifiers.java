package org.python.bouncycastle.asn1.x509.sigi;

import org.python.bouncycastle.asn1.ASN1ObjectIdentifier;

public interface SigIObjectIdentifiers {
   ASN1ObjectIdentifier id_sigi = new ASN1ObjectIdentifier("1.3.36.8");
   ASN1ObjectIdentifier id_sigi_kp = new ASN1ObjectIdentifier("1.3.36.8.2");
   ASN1ObjectIdentifier id_sigi_cp = new ASN1ObjectIdentifier("1.3.36.8.1");
   ASN1ObjectIdentifier id_sigi_on = new ASN1ObjectIdentifier("1.3.36.8.4");
   ASN1ObjectIdentifier id_sigi_kp_directoryService = new ASN1ObjectIdentifier("1.3.36.8.2.1");
   ASN1ObjectIdentifier id_sigi_on_personalData = new ASN1ObjectIdentifier("1.3.36.8.4.1");
   ASN1ObjectIdentifier id_sigi_cp_sigconform = new ASN1ObjectIdentifier("1.3.36.8.1.1");
}