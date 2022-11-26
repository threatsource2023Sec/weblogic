package org.python.bouncycastle.util;

import java.io.IOException;

public interface Encodable {
   byte[] getEncoded() throws IOException;
}
