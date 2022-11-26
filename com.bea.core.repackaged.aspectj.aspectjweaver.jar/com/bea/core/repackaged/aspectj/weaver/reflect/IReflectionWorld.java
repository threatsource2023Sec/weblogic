package com.bea.core.repackaged.aspectj.weaver.reflect;

import com.bea.core.repackaged.aspectj.weaver.ResolvedType;

public interface IReflectionWorld {
   AnnotationFinder getAnnotationFinder();

   ResolvedType resolve(Class var1);
}
