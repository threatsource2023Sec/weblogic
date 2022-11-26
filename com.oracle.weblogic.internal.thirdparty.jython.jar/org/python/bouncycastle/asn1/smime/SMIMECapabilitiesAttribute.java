package org.python.bouncycastle.asn1.smime;

import org.python.bouncycastle.asn1.DERSequence;
import org.python.bouncycastle.asn1.DERSet;
import org.python.bouncycastle.asn1.cms.Attribute;

public class SMIMECapabilitiesAttribute extends Attribute {
   public SMIMECapabilitiesAttribute(SMIMECapabilityVector var1) {
      super(SMIMEAttributes.smimeCapabilities, new DERSet(new DERSequence(var1.toASN1EncodableVector())));
   }
}
