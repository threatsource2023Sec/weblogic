package com.bea.core.repackaged.aspectj.weaver.reflect;

import com.bea.core.repackaged.aspectj.weaver.AnnotationAJ;
import com.bea.core.repackaged.aspectj.weaver.ResolvedType;
import com.bea.core.repackaged.aspectj.weaver.UnresolvedType;
import com.bea.core.repackaged.aspectj.weaver.World;
import java.lang.reflect.Member;
import java.util.Set;

public interface AnnotationFinder {
   void setClassLoader(ClassLoader var1);

   void setWorld(World var1);

   Object getAnnotation(ResolvedType var1, Object var2);

   Object getAnnotationFromMember(ResolvedType var1, Member var2);

   AnnotationAJ getAnnotationOfType(UnresolvedType var1, Member var2);

   String getAnnotationDefaultValue(Member var1);

   Object getAnnotationFromClass(ResolvedType var1, Class var2);

   Set getAnnotations(Member var1);

   ResolvedType[][] getParameterAnnotationTypes(Member var1);
}
