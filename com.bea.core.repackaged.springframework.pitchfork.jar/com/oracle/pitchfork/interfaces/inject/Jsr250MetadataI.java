package com.oracle.pitchfork.interfaces.inject;

import com.oracle.pitchfork.interfaces.ContextDataProvider;
import java.lang.reflect.Method;
import java.util.List;
import javax.ejb.Timer;

public interface Jsr250MetadataI {
   String getComponentName();

   Class getComponentClass();

   void registerLifecycleEventCallbackMethod(LifecycleEvent var1, Method var2);

   void invokeLifecycleMethods(Object var1, LifecycleEvent var2);

   void invokeTimeoutMethod(Object var1, Method var2, Timer var3);

   List getInjections();

   void inject(Object var1);

   DeploymentUnitMetadataI getDeploymentUnitMetadata();

   void refresh();

   List getLifecycleEventCallbackMethod(LifecycleEvent var1);

   void setTimeoutMethod(Method var1);

   Method getTimeoutMethod();

   void setHasScheduledTimeouts(boolean var1);

   boolean hasScheduledTimeouts();

   ContextDataProvider getContextDataProvider();

   void registerContextDataProvider(ContextDataProvider var1);

   Object resolve(InjectionI var1);
}
