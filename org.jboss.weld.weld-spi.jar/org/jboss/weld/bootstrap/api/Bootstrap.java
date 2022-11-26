package org.jboss.weld.bootstrap.api;

import java.net.URL;
import org.jboss.weld.bootstrap.spi.BeanDeploymentArchive;
import org.jboss.weld.bootstrap.spi.BeansXml;
import org.jboss.weld.bootstrap.spi.Deployment;
import org.jboss.weld.manager.api.WeldManager;

public interface Bootstrap {
   Bootstrap startContainer(Environment var1, Deployment var2);

   Bootstrap startInitialization();

   Bootstrap deployBeans();

   Bootstrap validateBeans();

   Bootstrap endInitialization();

   void shutdown();

   WeldManager getManager(BeanDeploymentArchive var1);

   BeansXml parse(URL var1);

   BeansXml parse(Iterable var1);

   BeansXml parse(Iterable var1, boolean var2);

   Iterable loadExtensions(ClassLoader var1);
}
