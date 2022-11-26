package com.oracle.pitchfork.interfaces;

import com.oracle.pitchfork.interfaces.inject.DeploymentUnitMetadataI;
import com.oracle.pitchfork.interfaces.inject.InjectionI;
import com.oracle.pitchfork.interfaces.inject.Jsr250MetadataI;
import com.oracle.pitchfork.interfaces.intercept.InterceptionMetadataI;
import com.oracle.pitchfork.interfaces.intercept.InterceptorMetadataI;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

public interface PitchforkUtils {
   void acceptClassLoader(ClassLoader var1);

   void clearClassLoader(ClassLoader var1);

   InjectionI createMethodInjection(Method var1, String var2, Class var3, boolean var4);

   InjectionI createFieldInjection(Field var1, String var2, Class var3, boolean var4);

   InterceptorMetadataI createInterceptorMetadata(Class var1, List var2, List var3, Method var4);

   Jsr250MetadataI createJ2eeClientInjectionMetadata(String var1, Class var2, DeploymentUnitMetadataI var3);

   InterceptionMetadataI createEjbProxyMetadata(String var1, Class var2, DeploymentUnitMetadataI var3, boolean var4);

   EjbComponentCreatorBroker createEjbComponentCreatorBroker();

   WebComponentContributorBroker createWebComponentContributorBroker();

   WSEEComponentContributorBroker createWSEEComponentContributorBroker();

   ManagedBeanContributorBroker createManagedBeanComponentContributorBroker();

   Class forName(String var1, ClassLoader var2) throws ClassNotFoundException;
}
