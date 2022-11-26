package org.python.netty.handler.ssl;

import java.util.List;
import java.util.Set;

public interface CipherSuiteFilter {
   String[] filterCipherSuites(Iterable var1, List var2, Set var3);
}
