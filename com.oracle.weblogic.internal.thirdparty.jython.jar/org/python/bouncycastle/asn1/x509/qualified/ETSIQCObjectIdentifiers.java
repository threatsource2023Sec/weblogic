package org.python.bouncycastle.asn1.x509.qualified;

import org.python.bouncycastle.asn1.ASN1ObjectIdentifier;

public interface ETSIQCObjectIdentifiers {
   ASN1ObjectIdentifier id_etsi_qcs_QcCompliance = new ASN1ObjectIdentifier("0.4.0.1862.1.1");
   ASN1ObjectIdentifier id_etsi_qcs_LimiteValue = new ASN1ObjectIdentifier("0.4.0.1862.1.2");
   ASN1ObjectIdentifier id_etsi_qcs_RetentionPeriod = new ASN1ObjectIdentifier("0.4.0.1862.1.3");
   ASN1ObjectIdentifier id_etsi_qcs_QcSSCD = new ASN1ObjectIdentifier("0.4.0.1862.1.4");
   ASN1ObjectIdentifier id_etsi_qcs_QcPds = new ASN1ObjectIdentifier("0.4.0.1862.1.5");
   ASN1ObjectIdentifier id_etsi_qcs_QcType = new ASN1ObjectIdentifier("0.4.0.1862.1.6");
   ASN1ObjectIdentifier id_etsi_qct_esign = id_etsi_qcs_QcType.branch("1");
   ASN1ObjectIdentifier id_etsi_qct_eseal = id_etsi_qcs_QcType.branch("2");
   ASN1ObjectIdentifier id_etsi_qct_web = id_etsi_qcs_QcType.branch("3");
}
