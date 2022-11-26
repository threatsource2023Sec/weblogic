package com.bea.core.repackaged.aspectj.weaver.patterns;

import com.bea.core.repackaged.aspectj.bridge.IMessage;
import com.bea.core.repackaged.aspectj.bridge.ISourceLocation;
import com.bea.core.repackaged.aspectj.bridge.MessageUtil;
import com.bea.core.repackaged.aspectj.util.FuzzyBoolean;
import com.bea.core.repackaged.aspectj.weaver.Advice;
import com.bea.core.repackaged.aspectj.weaver.AjcMemberMaker;
import com.bea.core.repackaged.aspectj.weaver.BCException;
import com.bea.core.repackaged.aspectj.weaver.CompressingDataOutputStream;
import com.bea.core.repackaged.aspectj.weaver.ISourceContext;
import com.bea.core.repackaged.aspectj.weaver.IntMap;
import com.bea.core.repackaged.aspectj.weaver.ResolvedMember;
import com.bea.core.repackaged.aspectj.weaver.ResolvedMemberImpl;
import com.bea.core.repackaged.aspectj.weaver.ResolvedPointcutDefinition;
import com.bea.core.repackaged.aspectj.weaver.ResolvedType;
import com.bea.core.repackaged.aspectj.weaver.Shadow;
import com.bea.core.repackaged.aspectj.weaver.ShadowMunger;
import com.bea.core.repackaged.aspectj.weaver.UnresolvedType;
import com.bea.core.repackaged.aspectj.weaver.VersionedDataInputStream;
import com.bea.core.repackaged.aspectj.weaver.WeaverMessages;
import com.bea.core.repackaged.aspectj.weaver.World;
import com.bea.core.repackaged.aspectj.weaver.ast.Expr;
import com.bea.core.repackaged.aspectj.weaver.ast.Literal;
import com.bea.core.repackaged.aspectj.weaver.ast.Test;
import com.bea.core.repackaged.aspectj.weaver.ast.Var;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class IfPointcut extends Pointcut {
   public ResolvedMember testMethod;
   public int extraParameterFlags;
   private final String enclosingPointcutHint;
   public Pointcut residueSource;
   int baseArgsCount;
   private boolean findingResidue = false;
   private int ifLastMatchedShadowId;
   private Test ifLastMatchedShadowResidue;
   private IfPointcut partiallyConcretized = null;

   public IfPointcut(ResolvedMember testMethod, int extraParameterFlags) {
      this.testMethod = testMethod;
      this.extraParameterFlags = extraParameterFlags;
      this.pointcutKind = 9;
      this.enclosingPointcutHint = null;
   }

   public IfPointcut(String enclosingPointcutHint) {
      this.pointcutKind = 9;
      this.enclosingPointcutHint = enclosingPointcutHint;
      this.testMethod = null;
      this.extraParameterFlags = -1;
   }

   public int couldMatchKinds() {
      return Shadow.ALL_SHADOW_KINDS_BITS;
   }

   public FuzzyBoolean fastMatch(FastMatchInfo type) {
      return FuzzyBoolean.MAYBE;
   }

   protected FuzzyBoolean matchInternal(Shadow shadow) {
      if ((this.extraParameterFlags & 16) != 0) {
         return (this.extraParameterFlags & 32) != 0 ? FuzzyBoolean.YES : FuzzyBoolean.NO;
      } else {
         return FuzzyBoolean.MAYBE;
      }
   }

   public boolean alwaysFalse() {
      return false;
   }

   public boolean alwaysTrue() {
      return false;
   }

   public Pointcut getResidueSource() {
      return this.residueSource;
   }

   public void write(CompressingDataOutputStream s) throws IOException {
      s.writeByte(9);
      s.writeBoolean(this.testMethod != null);
      if (this.testMethod != null) {
         this.testMethod.write(s);
      }

      s.writeByte(this.extraParameterFlags);
      this.writeLocation(s);
   }

   public static Pointcut read(VersionedDataInputStream s, ISourceContext context) throws IOException {
      boolean hasTestMethod = s.readBoolean();
      ResolvedMember resolvedTestMethod = null;
      if (hasTestMethod) {
         resolvedTestMethod = ResolvedMemberImpl.readResolvedMember(s, context);
      }

      IfPointcut ret = new IfPointcut(resolvedTestMethod, s.readByte());
      ret.readLocation(context, s);
      return ret;
   }

   public void resolveBindings(IScope scope, Bindings bindings) {
   }

   public boolean equals(Object other) {
      if (!(other instanceof IfPointcut)) {
         return false;
      } else {
         IfPointcut o = (IfPointcut)other;
         if (o.testMethod == null) {
            return this.testMethod == null;
         } else {
            return o.testMethod.equals(this.testMethod);
         }
      }
   }

   public int hashCode() {
      int result = 17;
      result = 37 * result + this.testMethod.hashCode();
      return result;
   }

   public String toString() {
      return this.extraParameterFlags < 0 ? "if()" : "if(" + this.testMethod + ")";
   }

   protected Test findResidueInternal(Shadow shadow, ExposedState state) {
      if (this.findingResidue) {
         return Literal.TRUE;
      } else {
         this.findingResidue = true;

         try {
            Test ret;
            if (shadow.shadowId == this.ifLastMatchedShadowId) {
               ret = this.ifLastMatchedShadowResidue;
               return ret;
            } else {
               Test ret = Literal.TRUE;
               List args = new ArrayList();
               Var v;
               if (this.extraParameterFlags >= 0) {
                  if ((this.extraParameterFlags & 16) != 0) {
                     Literal ret;
                     Literal var18;
                     if ((this.extraParameterFlags & 32) != 0) {
                        ret = Literal.TRUE;
                        this.ifLastMatchedShadowId = shadow.shadowId;
                        this.ifLastMatchedShadowResidue = ret;
                        var18 = ret;
                        return var18;
                     }

                     ret = Literal.FALSE;
                     this.ifLastMatchedShadowId = shadow.shadowId;
                     this.ifLastMatchedShadowResidue = ret;
                     var18 = ret;
                     return var18;
                  }

                  if (this.baseArgsCount > 0) {
                     ExposedState myState = new ExposedState(this.baseArgsCount);
                     myState.setConcreteAspect(state.getConcreteAspect());
                     this.residueSource.findResidue(shadow, myState);
                     UnresolvedType[] pTypes = this.testMethod == null ? null : this.testMethod.getParameterTypes();
                     if (pTypes != null && this.baseArgsCount > pTypes.length) {
                        throw new BCException("Unexpected problem with testMethod " + this.testMethod + ": expecting " + this.baseArgsCount + " arguments");
                     }

                     for(int i = 0; i < this.baseArgsCount; ++i) {
                        v = myState.get(i);
                        if (v != null) {
                           args.add(v);
                           ret = Test.makeAnd((Test)ret, Test.makeInstanceof(v, pTypes[i].resolve(shadow.getIWorld())));
                        }
                     }
                  }

                  if ((this.extraParameterFlags & 2) != 0) {
                     args.add(shadow.getThisJoinPointVar());
                  }

                  if ((this.extraParameterFlags & 4) != 0) {
                     args.add(shadow.getThisJoinPointStaticPartVar());
                  }

                  if ((this.extraParameterFlags & 8) != 0) {
                     args.add(shadow.getThisEnclosingJoinPointStaticPartVar());
                  }

                  if ((this.extraParameterFlags & 64) != 0) {
                     args.add(shadow.getThisAspectInstanceVar(state.getConcreteAspect()));
                  }
               } else {
                  int currentStateIndex = 0;

                  for(int i = 0; i < this.testMethod.getParameterTypes().length; ++i) {
                     String argSignature = this.testMethod.getParameterTypes()[i].getSignature();
                     if (AjcMemberMaker.TYPEX_JOINPOINT.getSignature().equals(argSignature)) {
                        args.add(shadow.getThisJoinPointVar());
                     } else if (AjcMemberMaker.TYPEX_PROCEEDINGJOINPOINT.getSignature().equals(argSignature)) {
                        args.add(shadow.getThisJoinPointVar());
                     } else if (AjcMemberMaker.TYPEX_STATICJOINPOINT.getSignature().equals(argSignature)) {
                        args.add(shadow.getThisJoinPointStaticPartVar());
                     } else if (AjcMemberMaker.TYPEX_ENCLOSINGSTATICJOINPOINT.getSignature().equals(argSignature)) {
                        args.add(shadow.getThisEnclosingJoinPointStaticPartVar());
                     } else {
                        if (state.size() == 0 || currentStateIndex > state.size()) {
                           String[] paramNames = this.testMethod.getParameterNames();
                           StringBuffer errorParameter = new StringBuffer();
                           if (paramNames != null) {
                              errorParameter.append(this.testMethod.getParameterTypes()[i].getName()).append(" ");
                              errorParameter.append(paramNames[i]);
                              shadow.getIWorld().getMessageHandler().handleMessage(MessageUtil.error("Missing binding for if() pointcut method.  Parameter " + (i + 1) + "(" + errorParameter.toString() + ") must be bound - even in reference pointcuts  (compiler limitation)", this.testMethod.getSourceLocation()));
                           } else {
                              shadow.getIWorld().getMessageHandler().handleMessage(MessageUtil.error("Missing binding for if() pointcut method.  Parameter " + (i + 1) + " must be bound - even in reference pointcuts (compiler limitation)", this.testMethod.getSourceLocation()));
                           }

                           Literal var10 = Literal.TRUE;
                           return var10;
                        }

                        for(v = state.get(currentStateIndex++); v == null && currentStateIndex < state.size(); v = state.get(currentStateIndex++)) {
                        }

                        args.add(v);
                        ret = Test.makeAnd((Test)ret, Test.makeInstanceof(v, this.testMethod.getParameterTypes()[i].resolve(shadow.getIWorld())));
                     }
                  }
               }

               ret = Test.makeAnd((Test)ret, Test.makeCall(this.testMethod, (Expr[])((Expr[])args.toArray(new Expr[args.size()]))));
               this.ifLastMatchedShadowId = shadow.shadowId;
               this.ifLastMatchedShadowResidue = ret;
               Test var17 = ret;
               return var17;
            }
         } finally {
            this.findingResidue = false;
         }
      }
   }

   protected boolean shouldCopyLocationForConcretize() {
      return false;
   }

   public Pointcut concretize1(ResolvedType inAspect, ResolvedType declaringType, IntMap bindings) {
      if (this.isDeclare(bindings.getEnclosingAdvice())) {
         inAspect.getWorld().showMessage(IMessage.ERROR, WeaverMessages.format("ifInDeclare"), bindings.getEnclosingAdvice().getSourceLocation(), (ISourceLocation)null);
         return Pointcut.makeMatchesNothing(Pointcut.CONCRETE);
      } else if (this.partiallyConcretized != null) {
         return this.partiallyConcretized;
      } else {
         ResolvedPointcutDefinition def;
         IfPointcut ret;
         if (this.extraParameterFlags < 0 && this.testMethod == null) {
            def = bindings.peekEnclosingDefinition();
            if (def == null) {
               this.testMethod = inAspect.getWorld().resolve(bindings.getAdviceSignature());
            } else {
               ResolvedType aspect = inAspect.getWorld().resolve(def.getDeclaringType());
               Iterator memberIter = aspect.getMethods(true, true);

               while(memberIter.hasNext()) {
                  ResolvedMember method = (ResolvedMember)memberIter.next();
                  if (def.getName().equals(method.getName()) && def.getParameterTypes().length == method.getParameterTypes().length) {
                     boolean sameSig = true;

                     for(int j = 0; j < method.getParameterTypes().length; ++j) {
                        UnresolvedType argJ = method.getParameterTypes()[j];
                        if (!argJ.equals(def.getParameterTypes()[j])) {
                           sameSig = false;
                           break;
                        }
                     }

                     if (sameSig) {
                        this.testMethod = method;
                        break;
                     }
                  }
               }

               if (this.testMethod == null) {
                  inAspect.getWorld().showMessage(IMessage.ERROR, "Cannot find if() body from '" + def.toString() + "' for '" + this.enclosingPointcutHint + "'", this.getSourceLocation(), (ISourceLocation)null);
                  return Pointcut.makeMatchesNothing(Pointcut.CONCRETE);
               }
            }

            ret = new IfPointcut(this.enclosingPointcutHint);
            ret.testMethod = this.testMethod;
         } else {
            ret = new IfPointcut(this.testMethod, this.extraParameterFlags);
         }

         ret.copyLocationFrom(this);
         this.partiallyConcretized = ret;
         if (bindings.directlyInAdvice() && bindings.getEnclosingAdvice() == null) {
            inAspect.getWorld().showMessage(IMessage.ERROR, WeaverMessages.format("ifInPerClause"), this.getSourceLocation(), (ISourceLocation)null);
            return Pointcut.makeMatchesNothing(Pointcut.CONCRETE);
         } else {
            if (bindings.directlyInAdvice()) {
               ShadowMunger advice = bindings.getEnclosingAdvice();
               if (advice instanceof Advice) {
                  ret.baseArgsCount = ((Advice)advice).getBaseParameterCount();
               } else {
                  ret.baseArgsCount = 0;
               }

               ret.residueSource = advice.getPointcut().concretize(inAspect, inAspect, ret.baseArgsCount, advice);
            } else {
               def = bindings.peekEnclosingDefinition();
               if (def == CflowPointcut.CFLOW_MARKER) {
                  inAspect.getWorld().showMessage(IMessage.ERROR, WeaverMessages.format("ifLexicallyInCflow"), this.getSourceLocation(), (ISourceLocation)null);
                  return Pointcut.makeMatchesNothing(Pointcut.CONCRETE);
               }

               ret.baseArgsCount = def.getParameterTypes().length;
               if (ret.extraParameterFlags < 0) {
                  ret.baseArgsCount = 0;

                  for(int i = 0; i < this.testMethod.getParameterTypes().length; ++i) {
                     String argSignature = this.testMethod.getParameterTypes()[i].getSignature();
                     if (!AjcMemberMaker.TYPEX_JOINPOINT.getSignature().equals(argSignature) && !AjcMemberMaker.TYPEX_PROCEEDINGJOINPOINT.getSignature().equals(argSignature) && !AjcMemberMaker.TYPEX_STATICJOINPOINT.getSignature().equals(argSignature) && !AjcMemberMaker.TYPEX_ENCLOSINGSTATICJOINPOINT.getSignature().equals(argSignature)) {
                        ++ret.baseArgsCount;
                     }
                  }
               }

               IntMap newBindings = IntMap.idMap(ret.baseArgsCount);
               newBindings.copyContext(bindings);
               ret.residueSource = def.getPointcut().concretize(inAspect, declaringType, newBindings);
            }

            return ret;
         }
      }
   }

   public Pointcut parameterizeWith(Map typeVariableMap, World w) {
      return this;
   }

   public static IfPointcut makeIfFalsePointcut(Pointcut.State state) {
      IfPointcut ret = new IfFalsePointcut();
      ret.state = state;
      return ret;
   }

   public Object accept(PatternNodeVisitor visitor, Object data) {
      return visitor.visit(this, data);
   }

   public static IfPointcut makeIfTruePointcut(Pointcut.State state) {
      IfPointcut ret = new IfTruePointcut();
      ret.state = state;
      return ret;
   }

   public void setAlways(boolean matches) {
      this.extraParameterFlags |= 16;
      if (matches) {
         this.extraParameterFlags |= 32;
      }

   }

   public static class IfTruePointcut extends IfPointcut {
      public IfTruePointcut() {
         super((ResolvedMember)null, 0);
         this.pointcutKind = 14;
      }

      public boolean alwaysTrue() {
         return true;
      }

      protected Test findResidueInternal(Shadow shadow, ExposedState state) {
         return Literal.TRUE;
      }

      public FuzzyBoolean fastMatch(FastMatchInfo type) {
         return FuzzyBoolean.YES;
      }

      protected FuzzyBoolean matchInternal(Shadow shadow) {
         return FuzzyBoolean.YES;
      }

      public void resolveBindings(IScope scope, Bindings bindings) {
      }

      public void postRead(ResolvedType enclosingType) {
      }

      public Pointcut concretize1(ResolvedType inAspect, ResolvedType declaringType, IntMap bindings) {
         if (this.isDeclare(bindings.getEnclosingAdvice())) {
            inAspect.getWorld().showMessage(IMessage.ERROR, WeaverMessages.format("ifInDeclare"), bindings.getEnclosingAdvice().getSourceLocation(), (ISourceLocation)null);
            return Pointcut.makeMatchesNothing(Pointcut.CONCRETE);
         } else {
            return makeIfTruePointcut(this.state);
         }
      }

      public void write(CompressingDataOutputStream s) throws IOException {
         s.writeByte(14);
      }

      public int hashCode() {
         int result = 37;
         return result;
      }

      public String toString() {
         return "if(true)";
      }
   }

   public static class IfFalsePointcut extends IfPointcut {
      public IfFalsePointcut() {
         super((ResolvedMember)null, 0);
         this.pointcutKind = 15;
      }

      public int couldMatchKinds() {
         return Shadow.NO_SHADOW_KINDS_BITS;
      }

      public boolean alwaysFalse() {
         return true;
      }

      protected Test findResidueInternal(Shadow shadow, ExposedState state) {
         return Literal.FALSE;
      }

      public FuzzyBoolean fastMatch(FastMatchInfo type) {
         return FuzzyBoolean.NO;
      }

      protected FuzzyBoolean matchInternal(Shadow shadow) {
         return FuzzyBoolean.NO;
      }

      public void resolveBindings(IScope scope, Bindings bindings) {
      }

      public void postRead(ResolvedType enclosingType) {
      }

      public Pointcut concretize1(ResolvedType inAspect, ResolvedType declaringType, IntMap bindings) {
         if (this.isDeclare(bindings.getEnclosingAdvice())) {
            inAspect.getWorld().showMessage(IMessage.ERROR, WeaverMessages.format("ifInDeclare"), bindings.getEnclosingAdvice().getSourceLocation(), (ISourceLocation)null);
            return Pointcut.makeMatchesNothing(Pointcut.CONCRETE);
         } else {
            return makeIfFalsePointcut(this.state);
         }
      }

      public void write(CompressingDataOutputStream s) throws IOException {
         s.writeByte(15);
      }

      public int hashCode() {
         int result = 17;
         return result;
      }

      public String toString() {
         return "if(false)";
      }
   }
}
