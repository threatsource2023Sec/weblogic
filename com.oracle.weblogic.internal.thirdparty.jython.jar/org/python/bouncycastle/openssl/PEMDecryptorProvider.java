package org.python.bouncycastle.openssl;

import org.python.bouncycastle.operator.OperatorCreationException;

public interface PEMDecryptorProvider {
   PEMDecryptor get(String var1) throws OperatorCreationException;
}
