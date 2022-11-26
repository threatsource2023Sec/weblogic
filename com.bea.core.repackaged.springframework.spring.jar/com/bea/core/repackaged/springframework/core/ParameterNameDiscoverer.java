package com.bea.core.repackaged.springframework.core;

import com.bea.core.repackaged.springframework.lang.Nullable;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

public interface ParameterNameDiscoverer {
   @Nullable
   String[] getParameterNames(Method var1);

   @Nullable
   String[] getParameterNames(Constructor var1);
}
