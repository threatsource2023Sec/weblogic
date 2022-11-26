package com.oracle.pitchfork.intercept;

import com.oracle.pitchfork.interfaces.intercept.InterceptorMetadataI;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.util.Map;

public interface InterceptorExclusionManager {
   Map getExclusions();

   void setExcludeClassInterceptors(AnnotatedElement var1);

   void setExcludeDefaultInterceptors(AnnotatedElement var1);

   boolean isExcluded(Method var1, Class var2, InterceptorMetadataI var3);
}
