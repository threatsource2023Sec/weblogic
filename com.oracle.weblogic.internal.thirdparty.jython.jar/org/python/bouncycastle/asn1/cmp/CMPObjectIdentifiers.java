package org.python.bouncycastle.asn1.cmp;

import org.python.bouncycastle.asn1.ASN1ObjectIdentifier;

public interface CMPObjectIdentifiers {
   ASN1ObjectIdentifier passwordBasedMac = new ASN1ObjectIdentifier("1.2.840.113533.7.66.13");
   ASN1ObjectIdentifier dhBasedMac = new ASN1ObjectIdentifier("1.2.840.113533.7.66.30");
   ASN1ObjectIdentifier it_caProtEncCert = new ASN1ObjectIdentifier("1.3.6.1.5.5.7.4.1");
   ASN1ObjectIdentifier it_signKeyPairTypes = new ASN1ObjectIdentifier("1.3.6.1.5.5.7.4.2");
   ASN1ObjectIdentifier it_encKeyPairTypes = new ASN1ObjectIdentifier("1.3.6.1.5.5.7.4.3");
   ASN1ObjectIdentifier it_preferredSymAlg = new ASN1ObjectIdentifier("1.3.6.1.5.5.7.4.4");
   ASN1ObjectIdentifier it_caKeyUpdateInfo = new ASN1ObjectIdentifier("1.3.6.1.5.5.7.4.5");
   ASN1ObjectIdentifier it_currentCRL = new ASN1ObjectIdentifier("1.3.6.1.5.5.7.4.6");
   ASN1ObjectIdentifier it_unsupportedOIDs = new ASN1ObjectIdentifier("1.3.6.1.5.5.7.4.7");
   ASN1ObjectIdentifier it_keyPairParamReq = new ASN1ObjectIdentifier("1.3.6.1.5.5.7.4.10");
   ASN1ObjectIdentifier it_keyPairParamRep = new ASN1ObjectIdentifier("1.3.6.1.5.5.7.4.11");
   ASN1ObjectIdentifier it_revPassphrase = new ASN1ObjectIdentifier("1.3.6.1.5.5.7.4.12");
   ASN1ObjectIdentifier it_implicitConfirm = new ASN1ObjectIdentifier("1.3.6.1.5.5.7.4.13");
   ASN1ObjectIdentifier it_confirmWaitTime = new ASN1ObjectIdentifier("1.3.6.1.5.5.7.4.14");
   ASN1ObjectIdentifier it_origPKIMessage = new ASN1ObjectIdentifier("1.3.6.1.5.5.7.4.15");
   ASN1ObjectIdentifier it_suppLangTags = new ASN1ObjectIdentifier("1.3.6.1.5.5.7.4.16");
   ASN1ObjectIdentifier id_pkip = new ASN1ObjectIdentifier("1.3.6.1.5.5.7.5");
   ASN1ObjectIdentifier id_regCtrl = new ASN1ObjectIdentifier("1.3.6.1.5.5.7.5.1");
   ASN1ObjectIdentifier id_regInfo = new ASN1ObjectIdentifier("1.3.6.1.5.5.7.5.2");
   ASN1ObjectIdentifier regCtrl_regToken = new ASN1ObjectIdentifier("1.3.6.1.5.5.7.5.1.1");
   ASN1ObjectIdentifier regCtrl_authenticator = new ASN1ObjectIdentifier("1.3.6.1.5.5.7.5.1.2");
   ASN1ObjectIdentifier regCtrl_pkiPublicationInfo = new ASN1ObjectIdentifier("1.3.6.1.5.5.7.5.1.3");
   ASN1ObjectIdentifier regCtrl_pkiArchiveOptions = new ASN1ObjectIdentifier("1.3.6.1.5.5.7.5.1.4");
   ASN1ObjectIdentifier regCtrl_oldCertID = new ASN1ObjectIdentifier("1.3.6.1.5.5.7.5.1.5");
   ASN1ObjectIdentifier regCtrl_protocolEncrKey = new ASN1ObjectIdentifier("1.3.6.1.5.5.7.5.1.6");
   ASN1ObjectIdentifier regCtrl_altCertTemplate = new ASN1ObjectIdentifier("1.3.6.1.5.5.7.5.1.7");
   ASN1ObjectIdentifier regInfo_utf8Pairs = new ASN1ObjectIdentifier("1.3.6.1.5.5.7.5.2.1");
   ASN1ObjectIdentifier regInfo_certReq = new ASN1ObjectIdentifier("1.3.6.1.5.5.7.5.2.2");
   ASN1ObjectIdentifier ct_encKeyWithID = new ASN1ObjectIdentifier("1.2.840.113549.1.9.16.1.21");
}
