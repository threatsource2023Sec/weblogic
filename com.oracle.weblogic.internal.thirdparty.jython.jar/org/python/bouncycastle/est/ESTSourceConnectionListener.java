package org.python.bouncycastle.est;

import java.io.IOException;

public interface ESTSourceConnectionListener {
   ESTRequest onConnection(Source var1, ESTRequest var2) throws IOException;
}
