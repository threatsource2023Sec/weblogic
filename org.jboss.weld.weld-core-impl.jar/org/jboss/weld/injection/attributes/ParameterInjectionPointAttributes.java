package org.jboss.weld.injection.attributes;

import javax.enterprise.inject.spi.AnnotatedParameter;

public interface ParameterInjectionPointAttributes extends WeldInjectionPointAttributes {
   AnnotatedParameter getAnnotated();
}
