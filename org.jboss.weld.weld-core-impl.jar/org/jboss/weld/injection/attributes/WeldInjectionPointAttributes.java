package org.jboss.weld.injection.attributes;

import java.lang.annotation.Annotation;
import javax.enterprise.inject.spi.InjectionPoint;

public interface WeldInjectionPointAttributes extends InjectionPoint {
   Annotation getQualifier(Class var1);
}
