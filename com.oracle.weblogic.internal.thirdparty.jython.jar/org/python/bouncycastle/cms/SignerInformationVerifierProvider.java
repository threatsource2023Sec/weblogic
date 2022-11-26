package org.python.bouncycastle.cms;

import org.python.bouncycastle.operator.OperatorCreationException;

public interface SignerInformationVerifierProvider {
   SignerInformationVerifier get(SignerId var1) throws OperatorCreationException;
}
