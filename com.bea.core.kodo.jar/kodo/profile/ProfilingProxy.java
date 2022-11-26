package kodo.profile;

import org.apache.openjpa.util.Proxy;

public interface ProfilingProxy extends Proxy {
   int size();

   ProfilingProxyStats getStats();
}
