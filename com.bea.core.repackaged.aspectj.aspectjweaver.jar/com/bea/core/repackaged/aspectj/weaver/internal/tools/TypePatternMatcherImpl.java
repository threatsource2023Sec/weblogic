package com.bea.core.repackaged.aspectj.weaver.internal.tools;

import com.bea.core.repackaged.aspectj.weaver.ResolvedType;
import com.bea.core.repackaged.aspectj.weaver.World;
import com.bea.core.repackaged.aspectj.weaver.patterns.TypePattern;
import com.bea.core.repackaged.aspectj.weaver.reflect.ReflectionBasedReferenceTypeDelegateFactory;
import com.bea.core.repackaged.aspectj.weaver.tools.TypePatternMatcher;

public class TypePatternMatcherImpl implements TypePatternMatcher {
   private final TypePattern pattern;
   private final World world;

   public TypePatternMatcherImpl(TypePattern pattern, World world) {
      this.pattern = pattern;
      this.world = world;
   }

   public boolean matches(Class aClass) {
      ResolvedType rt = ReflectionBasedReferenceTypeDelegateFactory.resolveTypeInWorld(aClass, this.world);
      return this.pattern.matchesStatically(rt);
   }
}
