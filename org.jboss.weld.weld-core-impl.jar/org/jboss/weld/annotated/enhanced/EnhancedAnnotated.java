package org.jboss.weld.annotated.enhanced;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.Set;
import javax.enterprise.context.NormalScope;
import javax.enterprise.inject.Stereotype;
import javax.enterprise.inject.spi.Annotated;
import javax.inject.Qualifier;
import javax.inject.Scope;
import javax.interceptor.InterceptorBinding;
import org.jboss.weld.util.collections.Arrays2;

public interface EnhancedAnnotated extends Annotated {
   Set MAPPED_METAANNOTATIONS = Arrays2.asSet(Qualifier.class, Stereotype.class, Scope.class, NormalScope.class, InterceptorBinding.class);
   Set MAPPED_DECLARED_METAANNOTATIONS = Arrays2.asSet(Scope.class, NormalScope.class);

   Set getMetaAnnotations(Class var1);

   /** @deprecated */
   @Deprecated
   Set getQualifiers();

   /** @deprecated */
   @Deprecated
   Annotation[] getBindingsAsArray();

   Set getInterfaceClosure();

   Class getJavaClass();

   Type[] getActualTypeArguments();

   boolean isStatic();

   boolean isGeneric();

   boolean isFinal();

   boolean isPublic();

   boolean isPrivate();

   boolean isPackagePrivate();

   Package getPackage();

   String getName();

   boolean isParameterizedType();

   boolean isPrimitive();

   Annotated slim();
}
