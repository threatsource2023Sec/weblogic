package org.jboss.weld.construction.api;

import java.util.Map;
import javax.enterprise.inject.spi.AnnotatedConstructor;

public interface AroundConstructCallback {
   Object aroundConstruct(ConstructionHandle var1, AnnotatedConstructor var2, Object[] var3, Map var4) throws Exception;
}
