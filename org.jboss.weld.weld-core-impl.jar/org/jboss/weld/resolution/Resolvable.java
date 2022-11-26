package org.jboss.weld.resolution;

import java.util.Set;
import javax.enterprise.inject.spi.Bean;

public interface Resolvable {
   Set getQualifiers();

   Set getTypes();

   Class getJavaClass();

   Bean getDeclaringBean();

   boolean isDelegate();
}
