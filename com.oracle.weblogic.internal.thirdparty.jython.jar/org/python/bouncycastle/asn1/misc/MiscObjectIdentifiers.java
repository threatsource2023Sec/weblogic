package org.python.bouncycastle.asn1.misc;

import org.python.bouncycastle.asn1.ASN1ObjectIdentifier;

public interface MiscObjectIdentifiers {
   ASN1ObjectIdentifier netscape = new ASN1ObjectIdentifier("2.16.840.1.113730.1");
   ASN1ObjectIdentifier netscapeCertType = netscape.branch("1");
   ASN1ObjectIdentifier netscapeBaseURL = netscape.branch("2");
   ASN1ObjectIdentifier netscapeRevocationURL = netscape.branch("3");
   ASN1ObjectIdentifier netscapeCARevocationURL = netscape.branch("4");
   ASN1ObjectIdentifier netscapeRenewalURL = netscape.branch("7");
   ASN1ObjectIdentifier netscapeCApolicyURL = netscape.branch("8");
   ASN1ObjectIdentifier netscapeSSLServerName = netscape.branch("12");
   ASN1ObjectIdentifier netscapeCertComment = netscape.branch("13");
   ASN1ObjectIdentifier verisign = new ASN1ObjectIdentifier("2.16.840.1.113733.1");
   ASN1ObjectIdentifier verisignCzagExtension = verisign.branch("6.3");
   ASN1ObjectIdentifier verisignPrivate_6_9 = verisign.branch("6.9");
   ASN1ObjectIdentifier verisignOnSiteJurisdictionHash = verisign.branch("6.11");
   ASN1ObjectIdentifier verisignBitString_6_13 = verisign.branch("6.13");
   ASN1ObjectIdentifier verisignDnbDunsNumber = verisign.branch("6.15");
   ASN1ObjectIdentifier verisignIssStrongCrypto = verisign.branch("8.1");
   ASN1ObjectIdentifier novell = new ASN1ObjectIdentifier("2.16.840.1.113719");
   ASN1ObjectIdentifier novellSecurityAttribs = novell.branch("1.9.4.1");
   ASN1ObjectIdentifier entrust = new ASN1ObjectIdentifier("1.2.840.113533.7");
   ASN1ObjectIdentifier entrustVersionExtension = entrust.branch("65.0");
   ASN1ObjectIdentifier cast5CBC = entrust.branch("66.10");
   ASN1ObjectIdentifier as_sys_sec_alg_ideaCBC = new ASN1ObjectIdentifier("1.3.6.1.4.1.188.7.1.1.2");
   ASN1ObjectIdentifier cryptlib = new ASN1ObjectIdentifier("1.3.6.1.4.1.3029");
   ASN1ObjectIdentifier cryptlib_algorithm = cryptlib.branch("1");
   ASN1ObjectIdentifier cryptlib_algorithm_blowfish_ECB = cryptlib_algorithm.branch("1.1");
   ASN1ObjectIdentifier cryptlib_algorithm_blowfish_CBC = cryptlib_algorithm.branch("1.2");
   ASN1ObjectIdentifier cryptlib_algorithm_blowfish_CFB = cryptlib_algorithm.branch("1.3");
   ASN1ObjectIdentifier cryptlib_algorithm_blowfish_OFB = cryptlib_algorithm.branch("1.4");
   ASN1ObjectIdentifier blake2 = new ASN1ObjectIdentifier("1.3.6.1.4.1.1722.12.2");
   ASN1ObjectIdentifier id_blake2b160 = blake2.branch("1.5");
   ASN1ObjectIdentifier id_blake2b256 = blake2.branch("1.8");
   ASN1ObjectIdentifier id_blake2b384 = blake2.branch("1.12");
   ASN1ObjectIdentifier id_blake2b512 = blake2.branch("1.16");
}
