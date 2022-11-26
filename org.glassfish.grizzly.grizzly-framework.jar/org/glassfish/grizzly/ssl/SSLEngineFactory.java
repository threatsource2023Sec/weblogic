package org.glassfish.grizzly.ssl;

import javax.net.ssl.SSLEngine;

public interface SSLEngineFactory {
   SSLEngine createSSLEngine(String var1, int var2);
}
