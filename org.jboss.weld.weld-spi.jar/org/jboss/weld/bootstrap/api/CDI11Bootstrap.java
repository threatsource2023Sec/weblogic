package org.jboss.weld.bootstrap.api;

import org.jboss.weld.bootstrap.spi.Deployment;

public interface CDI11Bootstrap extends Bootstrap {
   TypeDiscoveryConfiguration startExtensions(Iterable var1);

   Bootstrap startContainer(String var1, Environment var2, Deployment var3);
}
