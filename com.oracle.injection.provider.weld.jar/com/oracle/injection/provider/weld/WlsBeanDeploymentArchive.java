package com.oracle.injection.provider.weld;

import java.util.Collection;
import org.jboss.weld.bootstrap.spi.BeanDeploymentArchive;

public interface WlsBeanDeploymentArchive extends BeanDeploymentArchive {
   ClassLoader getBdaClassLoader();

   Collection getComponentClassesForProcessInjectionTarget();
}
