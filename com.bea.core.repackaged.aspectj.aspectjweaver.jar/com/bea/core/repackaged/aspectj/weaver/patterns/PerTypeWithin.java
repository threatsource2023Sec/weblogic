package com.bea.core.repackaged.aspectj.weaver.patterns;

import com.bea.core.repackaged.aspectj.bridge.IMessage;
import com.bea.core.repackaged.aspectj.bridge.ISourceLocation;
import com.bea.core.repackaged.aspectj.bridge.Message;
import com.bea.core.repackaged.aspectj.util.FuzzyBoolean;
import com.bea.core.repackaged.aspectj.weaver.Advice;
import com.bea.core.repackaged.aspectj.weaver.AjcMemberMaker;
import com.bea.core.repackaged.aspectj.weaver.CompressingDataOutputStream;
import com.bea.core.repackaged.aspectj.weaver.ISourceContext;
import com.bea.core.repackaged.aspectj.weaver.Member;
import com.bea.core.repackaged.aspectj.weaver.PerTypeWithinTargetTypeMunger;
import com.bea.core.repackaged.aspectj.weaver.ResolvedType;
import com.bea.core.repackaged.aspectj.weaver.ResolvedTypeMunger;
import com.bea.core.repackaged.aspectj.weaver.Shadow;
import com.bea.core.repackaged.aspectj.weaver.VersionedDataInputStream;
import com.bea.core.repackaged.aspectj.weaver.World;
import com.bea.core.repackaged.aspectj.weaver.ast.Expr;
import com.bea.core.repackaged.aspectj.weaver.ast.Literal;
import com.bea.core.repackaged.aspectj.weaver.ast.Test;
import java.io.IOException;
import java.util.Map;

public class PerTypeWithin extends PerClause {
   private TypePattern typePattern;
   private static final int kindSet;

   public TypePattern getTypePattern() {
      return this.typePattern;
   }

   public PerTypeWithin(TypePattern p) {
      this.typePattern = p;
   }

   public Object accept(PatternNodeVisitor visitor, Object data) {
      return visitor.visit(this, data);
   }

   public int couldMatchKinds() {
      return kindSet;
   }

   public Pointcut parameterizeWith(Map typeVariableMap, World w) {
      PerTypeWithin ret = new PerTypeWithin(this.typePattern.parameterizeWith(typeVariableMap, w));
      ret.copyLocationFrom(this);
      return ret;
   }

   public FuzzyBoolean fastMatch(FastMatchInfo info) {
      return this.typePattern.annotationPattern instanceof AnyAnnotationTypePattern ? this.isWithinType(info.getType()) : FuzzyBoolean.MAYBE;
   }

   protected FuzzyBoolean matchInternal(Shadow shadow) {
      ResolvedType enclosingType = shadow.getIWorld().resolve(shadow.getEnclosingType(), true);
      if (enclosingType.isMissing()) {
         IMessage msg = new Message("Cant find type pertypewithin matching...", shadow.getSourceLocation(), true, new ISourceLocation[]{this.getSourceLocation()});
         shadow.getIWorld().getMessageHandler().handleMessage(msg);
      }

      if (enclosingType.isInterface()) {
         return FuzzyBoolean.NO;
      } else {
         this.typePattern.resolve(shadow.getIWorld());
         return this.isWithinType(enclosingType);
      }
   }

   public void resolveBindings(IScope scope, Bindings bindings) {
      this.typePattern = this.typePattern.resolveBindings(scope, bindings, false, false);
   }

   protected Test findResidueInternal(Shadow shadow, ExposedState state) {
      Expr myInstance = Expr.makeCallExpr(AjcMemberMaker.perTypeWithinLocalAspectOf(shadow.getEnclosingType(), this.inAspect), Expr.NONE, this.inAspect);
      state.setAspectInstance(myInstance);
      return this.match(shadow).alwaysTrue() ? Literal.TRUE : Literal.FALSE;
   }

   public PerClause concretize(ResolvedType inAspect) {
      PerTypeWithin ret = new PerTypeWithin(this.typePattern);
      ret.copyLocationFrom(this);
      ret.inAspect = inAspect;
      if (inAspect.isAbstract()) {
         return ret;
      } else {
         World world = inAspect.getWorld();
         SignaturePattern sigpat = new SignaturePattern(Member.STATIC_INITIALIZATION, ModifiersPattern.ANY, TypePattern.ANY, TypePattern.ANY, NamePattern.ANY, TypePatternList.ANY, ThrowsPattern.ANY, AnnotationTypePattern.ANY);
         Pointcut staticInitStar = new KindedPointcut(Shadow.StaticInitialization, sigpat);
         Pointcut withinTp = new WithinPointcut(this.typePattern);
         Pointcut andPcut = new AndPointcut(staticInitStar, withinTp);
         inAspect.crosscuttingMembers.addConcreteShadowMunger(Advice.makePerTypeWithinEntry(world, andPcut, inAspect));
         ResolvedTypeMunger munger = new PerTypeWithinTargetTypeMunger(inAspect, ret);
         inAspect.crosscuttingMembers.addTypeMunger(world.getWeavingSupport().concreteTypeMunger(munger, inAspect));
         if (inAspect.isAnnotationStyleAspect() && !inAspect.isAbstract()) {
            inAspect.crosscuttingMembers.addLateTypeMunger(world.getWeavingSupport().makePerClauseAspect(inAspect, this.getKind()));
         }

         if (inAspect.isAnnotationStyleAspect() && !world.isXnoInline()) {
            inAspect.crosscuttingMembers.addTypeMunger(world.getWeavingSupport().createAccessForInlineMunger(inAspect));
         }

         return ret;
      }
   }

   public void write(CompressingDataOutputStream s) throws IOException {
      PERTYPEWITHIN.write(s);
      this.typePattern.write(s);
      this.writeLocation(s);
   }

   public static PerClause readPerClause(VersionedDataInputStream s, ISourceContext context) throws IOException {
      PerClause ret = new PerTypeWithin(TypePattern.read(s, context));
      ret.readLocation(context, s);
      return ret;
   }

   public PerClause.Kind getKind() {
      return PERTYPEWITHIN;
   }

   public String toString() {
      return "pertypewithin(" + this.typePattern + ")";
   }

   public String toDeclarationString() {
      return this.toString();
   }

   private FuzzyBoolean isWithinType(ResolvedType type) {
      while(type != null) {
         if (this.typePattern.matchesStatically(type)) {
            return FuzzyBoolean.YES;
         }

         type = type.getDeclaringType();
      }

      return FuzzyBoolean.NO;
   }

   public boolean equals(Object other) {
      if (!(other instanceof PerTypeWithin)) {
         return false;
      } else {
         boolean var10000;
         label38: {
            label27: {
               PerTypeWithin pc = (PerTypeWithin)other;
               if (pc.inAspect == null) {
                  if (this.inAspect != null) {
                     break label27;
                  }
               } else if (!pc.inAspect.equals(this.inAspect)) {
                  break label27;
               }

               if (pc.typePattern == null) {
                  if (this.typePattern == null) {
                     break label38;
                  }
               } else if (pc.typePattern.equals(this.typePattern)) {
                  break label38;
               }
            }

            var10000 = false;
            return var10000;
         }

         var10000 = true;
         return var10000;
      }
   }

   public int hashCode() {
      int result = 17;
      result = 37 * result + (this.inAspect == null ? 0 : this.inAspect.hashCode());
      result = 37 * result + (this.typePattern == null ? 0 : this.typePattern.hashCode());
      return result;
   }

   static {
      kindSet = Shadow.ALL_SHADOW_KINDS_BITS;
   }
}
