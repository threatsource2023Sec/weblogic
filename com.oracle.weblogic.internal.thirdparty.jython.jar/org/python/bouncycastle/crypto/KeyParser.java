package org.python.bouncycastle.crypto;

import java.io.IOException;
import java.io.InputStream;
import org.python.bouncycastle.crypto.params.AsymmetricKeyParameter;

public interface KeyParser {
   AsymmetricKeyParameter readKey(InputStream var1) throws IOException;
}
