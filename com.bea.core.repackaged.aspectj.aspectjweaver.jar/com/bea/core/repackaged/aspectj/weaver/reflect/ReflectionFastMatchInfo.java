package com.bea.core.repackaged.aspectj.weaver.reflect;

import com.bea.core.repackaged.aspectj.weaver.ResolvedType;
import com.bea.core.repackaged.aspectj.weaver.Shadow;
import com.bea.core.repackaged.aspectj.weaver.World;
import com.bea.core.repackaged.aspectj.weaver.patterns.FastMatchInfo;
import com.bea.core.repackaged.aspectj.weaver.tools.MatchingContext;

public class ReflectionFastMatchInfo extends FastMatchInfo {
   private final MatchingContext context;

   public ReflectionFastMatchInfo(ResolvedType type, Shadow.Kind kind, MatchingContext context, World world) {
      super(type, kind, world);
      this.context = context;
   }

   public MatchingContext getMatchingContext() {
      return this.context;
   }
}
