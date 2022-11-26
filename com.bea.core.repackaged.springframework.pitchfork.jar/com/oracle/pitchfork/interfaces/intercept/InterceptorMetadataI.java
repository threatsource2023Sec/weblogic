package com.oracle.pitchfork.interfaces.intercept;

import com.oracle.pitchfork.interfaces.inject.Jsr250MetadataI;
import com.oracle.pitchfork.interfaces.inject.LifecycleEvent;
import java.lang.reflect.Method;
import java.util.List;

public interface InterceptorMetadataI extends Jsr250MetadataI {
   boolean isClassInterceptor();

   boolean isDefaultInterceptor();

   boolean isConstructorInterceptor();

   boolean isSystemInterceptor();

   void setDefaultInterceptor(boolean var1);

   void setClassInterceptor(boolean var1);

   void setConstructorInterceptor(boolean var1);

   void setExternallyCreatedInterceptor(Object var1, Object var2);

   void registerLifecycleEventListenerMethod(LifecycleEvent var1, Method var2);

   Method getMatchingMethod();

   List getAroundInvokeMethods();

   List getAroundTimeoutMethods();

   List getLifecycleEventListenerMethod(LifecycleEvent var1);

   Object getAndClearExternallyCreatedInterceptor(Object var1);

   Method getScheduledTimeoutMethod();

   void setScheduledTimeoutMethod(Method var1);
}
