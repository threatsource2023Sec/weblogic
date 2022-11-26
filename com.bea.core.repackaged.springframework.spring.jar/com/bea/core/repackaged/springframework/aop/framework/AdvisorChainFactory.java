package com.bea.core.repackaged.springframework.aop.framework;

import com.bea.core.repackaged.springframework.lang.Nullable;
import java.lang.reflect.Method;
import java.util.List;

public interface AdvisorChainFactory {
   List getInterceptorsAndDynamicInterceptionAdvice(Advised var1, Method var2, @Nullable Class var3);
}
