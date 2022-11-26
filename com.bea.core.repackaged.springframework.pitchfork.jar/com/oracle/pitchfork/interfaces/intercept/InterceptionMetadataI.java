package com.oracle.pitchfork.interfaces.intercept;

import com.oracle.pitchfork.intercept.PointcutMatch;
import com.oracle.pitchfork.interfaces.BeanCreator;
import com.oracle.pitchfork.interfaces.inject.Jsr250MetadataI;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.jboss.weld.bootstrap.spi.BeanDiscoveryMode;

public interface InterceptionMetadataI extends Jsr250MetadataI {
   void registerSelfInterceptorMethod(Method var1);

   void registerSelfTimeoutInterceptorMethod(Method var1);

   void setBeanControlInterface(Class var1);

   void setBeanControlInterfaceMethods(Set var1);

   void setExcludeClassInterceptors(AnnotatedElement var1);

   void setExcludeDefaultInterceptors(AnnotatedElement var1);

   void registerInterceptorMetadata(InterceptorMetadataI var1);

   void registerInterceptorOrder(PointcutMatch var1, InterceptorMetadataI var2);

   void addBusinessInterface(Class var1);

   List findBusinessInterfacesFromClassOrAnnotation(Class var1);

   Object createProxyIfNecessary(Object var1, Map var2);

   List getInterceptorMetadata();

   BeanCreator getBeanCreator();

   List getAroundConstructMetadatas();

   Object constructBean(BeanCreator var1, List var2, Map var3);

   void registerContainerControlInterface(Class var1);

   Set getContainerControlInterfaces();

   void setIsMessageDrivenBean(boolean var1);

   void setIsCDIEnabled(boolean var1);

   void setBeanDiscoveryMode(BeanDiscoveryMode var1);
}
