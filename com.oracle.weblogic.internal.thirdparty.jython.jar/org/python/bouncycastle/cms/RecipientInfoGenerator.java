package org.python.bouncycastle.cms;

import org.python.bouncycastle.asn1.cms.RecipientInfo;
import org.python.bouncycastle.operator.GenericKey;

public interface RecipientInfoGenerator {
   RecipientInfo generate(GenericKey var1) throws CMSException;
}
