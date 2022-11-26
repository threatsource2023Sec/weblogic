package com.bea.core.repackaged.aspectj.weaver.internal.tools;

import com.bea.core.repackaged.aspectj.util.FuzzyBoolean;
import com.bea.core.repackaged.aspectj.weaver.CompressingDataOutputStream;
import com.bea.core.repackaged.aspectj.weaver.IntMap;
import com.bea.core.repackaged.aspectj.weaver.ReferenceType;
import com.bea.core.repackaged.aspectj.weaver.ReferenceTypeDelegate;
import com.bea.core.repackaged.aspectj.weaver.ResolvedType;
import com.bea.core.repackaged.aspectj.weaver.Shadow;
import com.bea.core.repackaged.aspectj.weaver.World;
import com.bea.core.repackaged.aspectj.weaver.ast.Literal;
import com.bea.core.repackaged.aspectj.weaver.ast.Test;
import com.bea.core.repackaged.aspectj.weaver.patterns.Bindings;
import com.bea.core.repackaged.aspectj.weaver.patterns.ExposedState;
import com.bea.core.repackaged.aspectj.weaver.patterns.FastMatchInfo;
import com.bea.core.repackaged.aspectj.weaver.patterns.IScope;
import com.bea.core.repackaged.aspectj.weaver.patterns.PatternNodeVisitor;
import com.bea.core.repackaged.aspectj.weaver.patterns.Pointcut;
import com.bea.core.repackaged.aspectj.weaver.reflect.ReflectionBasedReferenceTypeDelegate;
import com.bea.core.repackaged.aspectj.weaver.reflect.ReflectionFastMatchInfo;
import com.bea.core.repackaged.aspectj.weaver.reflect.ReflectionShadow;
import com.bea.core.repackaged.aspectj.weaver.reflect.ReflectionWorld;
import com.bea.core.repackaged.aspectj.weaver.tools.ContextBasedMatcher;
import com.bea.core.repackaged.aspectj.weaver.tools.MatchingContext;
import java.io.IOException;
import java.util.Map;

public class PointcutDesignatorHandlerBasedPointcut extends Pointcut {
   private final ContextBasedMatcher matcher;
   private final World world;

   public PointcutDesignatorHandlerBasedPointcut(ContextBasedMatcher expr, World world) {
      this.matcher = expr;
      this.world = world;
   }

   public byte getPointcutKind() {
      return 22;
   }

   public FuzzyBoolean fastMatch(FastMatchInfo info) {
      if (!(info instanceof ReflectionFastMatchInfo)) {
         throw new IllegalStateException("Can only match user-extension pcds against Reflection FastMatchInfo objects");
      } else if (!(this.world instanceof ReflectionWorld)) {
         throw new IllegalStateException("Can only match user-extension pcds with a ReflectionWorld");
      } else {
         Class clazz = null;

         try {
            clazz = Class.forName(info.getType().getName(), false, ((ReflectionWorld)this.world).getClassLoader());
         } catch (ClassNotFoundException var5) {
            if (info.getType() instanceof ReferenceType) {
               ReferenceTypeDelegate rtd = ((ReferenceType)info.getType()).getDelegate();
               if (rtd instanceof ReflectionBasedReferenceTypeDelegate) {
                  clazz = ((ReflectionBasedReferenceTypeDelegate)rtd).getClazz();
               }
            }
         }

         return clazz == null ? FuzzyBoolean.MAYBE : FuzzyBoolean.fromBoolean(this.matcher.couldMatchJoinPointsInType(clazz, ((ReflectionFastMatchInfo)info).getMatchingContext()));
      }
   }

   public int couldMatchKinds() {
      return Shadow.ALL_SHADOW_KINDS_BITS;
   }

   protected FuzzyBoolean matchInternal(Shadow shadow) {
      if (shadow instanceof ReflectionShadow) {
         MatchingContext context = ((ReflectionShadow)shadow).getMatchingContext();
         com.bea.core.repackaged.aspectj.weaver.tools.FuzzyBoolean match = this.matcher.matchesStatically(context);
         if (match == com.bea.core.repackaged.aspectj.weaver.tools.FuzzyBoolean.MAYBE) {
            return FuzzyBoolean.MAYBE;
         }

         if (match == com.bea.core.repackaged.aspectj.weaver.tools.FuzzyBoolean.YES) {
            return FuzzyBoolean.YES;
         }

         if (match == com.bea.core.repackaged.aspectj.weaver.tools.FuzzyBoolean.NO) {
            return FuzzyBoolean.NO;
         }
      }

      throw new IllegalStateException("Can only match user-extension pcds against Reflection shadows (not BCEL)");
   }

   protected void resolveBindings(IScope scope, Bindings bindings) {
   }

   protected Pointcut concretize1(ResolvedType inAspect, ResolvedType declaringType, IntMap bindings) {
      return this;
   }

   protected Test findResidueInternal(Shadow shadow, ExposedState state) {
      if (!this.matcher.mayNeedDynamicTest()) {
         return Literal.TRUE;
      } else {
         this.matchInternal(shadow);
         return new MatchingContextBasedTest(this.matcher);
      }
   }

   public Pointcut parameterizeWith(Map typeVariableMap, World w) {
      return this;
   }

   public void write(CompressingDataOutputStream s) throws IOException {
      throw new UnsupportedOperationException("can't write custom pointcut designator expressions to stream");
   }

   public Object accept(PatternNodeVisitor visitor, Object data) {
      return data;
   }
}
