package com.oracle.injection.ejb;

import java.lang.reflect.Method;
import java.util.List;

public interface InterceptorMapping {
   String getInterceptorClass();

   List getAroundInvokeMethods();

   List getAroundTimeoutMethods();

   List getPostConstructMethods();

   Method getTargetMethod();
}
