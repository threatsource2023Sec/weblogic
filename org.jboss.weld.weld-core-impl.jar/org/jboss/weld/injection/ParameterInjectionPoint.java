package org.jboss.weld.injection;

import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.AnnotatedParameter;
import org.jboss.weld.injection.attributes.WeldInjectionPointAttributes;
import org.jboss.weld.manager.BeanManagerImpl;

public interface ParameterInjectionPoint extends WeldInjectionPointAttributes {
   AnnotatedParameter getAnnotated();

   Object getValueToInject(BeanManagerImpl var1, CreationalContext var2);
}
