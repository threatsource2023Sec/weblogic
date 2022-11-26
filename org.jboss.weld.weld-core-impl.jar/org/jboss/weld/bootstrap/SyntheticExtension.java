package org.jboss.weld.bootstrap;

import java.util.Collection;
import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.inject.spi.Extension;

public interface SyntheticExtension extends Extension {
   Collection getObservers();

   default void initialize(BeanManager beanManager) {
   }
}
