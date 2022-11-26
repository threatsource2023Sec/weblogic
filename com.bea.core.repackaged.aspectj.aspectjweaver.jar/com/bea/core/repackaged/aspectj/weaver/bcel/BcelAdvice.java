package com.bea.core.repackaged.aspectj.weaver.bcel;

import com.bea.core.repackaged.aspectj.apache.bcel.classfile.LocalVariable;
import com.bea.core.repackaged.aspectj.apache.bcel.classfile.LocalVariableTable;
import com.bea.core.repackaged.aspectj.apache.bcel.generic.InstructionConstants;
import com.bea.core.repackaged.aspectj.apache.bcel.generic.InstructionFactory;
import com.bea.core.repackaged.aspectj.apache.bcel.generic.InstructionHandle;
import com.bea.core.repackaged.aspectj.apache.bcel.generic.InstructionList;
import com.bea.core.repackaged.aspectj.apache.bcel.generic.LineNumberTag;
import com.bea.core.repackaged.aspectj.apache.bcel.generic.LocalVariableTag;
import com.bea.core.repackaged.aspectj.bridge.ISourceLocation;
import com.bea.core.repackaged.aspectj.bridge.Message;
import com.bea.core.repackaged.aspectj.weaver.Advice;
import com.bea.core.repackaged.aspectj.weaver.AdviceKind;
import com.bea.core.repackaged.aspectj.weaver.AjAttribute;
import com.bea.core.repackaged.aspectj.weaver.BCException;
import com.bea.core.repackaged.aspectj.weaver.IEclipseSourceContext;
import com.bea.core.repackaged.aspectj.weaver.ISourceContext;
import com.bea.core.repackaged.aspectj.weaver.Member;
import com.bea.core.repackaged.aspectj.weaver.ReferenceType;
import com.bea.core.repackaged.aspectj.weaver.ReferenceTypeDelegate;
import com.bea.core.repackaged.aspectj.weaver.ResolvedMember;
import com.bea.core.repackaged.aspectj.weaver.ResolvedMemberImpl;
import com.bea.core.repackaged.aspectj.weaver.ResolvedType;
import com.bea.core.repackaged.aspectj.weaver.Shadow;
import com.bea.core.repackaged.aspectj.weaver.ShadowMunger;
import com.bea.core.repackaged.aspectj.weaver.UnresolvedType;
import com.bea.core.repackaged.aspectj.weaver.WeaverMessages;
import com.bea.core.repackaged.aspectj.weaver.World;
import com.bea.core.repackaged.aspectj.weaver.ast.Literal;
import com.bea.core.repackaged.aspectj.weaver.ast.Test;
import com.bea.core.repackaged.aspectj.weaver.patterns.ExactTypePattern;
import com.bea.core.repackaged.aspectj.weaver.patterns.ExposedState;
import com.bea.core.repackaged.aspectj.weaver.patterns.PerClause;
import com.bea.core.repackaged.aspectj.weaver.patterns.Pointcut;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;

class BcelAdvice extends Advice {
   private Test runtimeTest;
   private ExposedState exposedState;
   private int containsInvokedynamic;
   private Collection thrownExceptions;

   public BcelAdvice(AjAttribute.AdviceAttribute attribute, Pointcut pointcut, Member adviceSignature, ResolvedType concreteAspect) {
      super(attribute, pointcut, simplify(attribute.getKind(), adviceSignature));
      this.containsInvokedynamic = 0;
      this.thrownExceptions = null;
      this.concreteAspect = concreteAspect;
   }

   private static Member simplify(AdviceKind kind, Member adviceSignature) {
      if (adviceSignature != null) {
         UnresolvedType adviceDeclaringType = adviceSignature.getDeclaringType();
         if ((kind != AdviceKind.Around || adviceDeclaringType instanceof ResolvedType && ((ResolvedType)adviceDeclaringType).getWorld().isXnoInline()) && adviceSignature instanceof BcelMethod) {
            BcelMethod bm = (BcelMethod)adviceSignature;
            if (bm.getMethod() != null && bm.getMethod().getAnnotations() != null) {
               return adviceSignature;
            }

            ResolvedMemberImpl simplermember = new ResolvedMemberImpl(bm.getKind(), bm.getDeclaringType(), bm.getModifiers(), bm.getReturnType(), bm.getName(), bm.getParameterTypes());
            simplermember.setParameterNames(bm.getParameterNames());
            return simplermember;
         }
      }

      return adviceSignature;
   }

   public ShadowMunger concretize(ResolvedType fromType, World world, PerClause clause) {
      if (!world.areAllLintIgnored()) {
         this.suppressLintWarnings(world);
      }

      ShadowMunger ret = super.concretize(fromType, world, clause);
      if (!world.areAllLintIgnored()) {
         this.clearLintSuppressions(world, this.suppressedLintKinds);
      }

      IfFinder ifinder = new IfFinder();
      ret.getPointcut().accept(ifinder, (Object)null);
      boolean hasGuardTest = ifinder.hasIf && this.getKind() != AdviceKind.Around;
      boolean isAround = this.getKind() == AdviceKind.Around;
      if ((this.getExtraParameterFlags() & 2) != 0 && !isAround && !hasGuardTest && world.getLint().noGuardForLazyTjp.isEnabled()) {
         world.getLint().noGuardForLazyTjp.signal("", this.getSourceLocation());
      }

      return ret;
   }

   public ShadowMunger parameterizeWith(ResolvedType declaringType, Map typeVariableMap) {
      Pointcut pc = this.getPointcut().parameterizeWith(typeVariableMap, declaringType.getWorld());
      BcelAdvice ret = null;
      Member adviceSignature = this.signature;
      if (this.signature instanceof ResolvedMember && this.signature.getDeclaringType().isGenericType()) {
         adviceSignature = ((ResolvedMember)this.signature).parameterizedWith(declaringType.getTypeParameters(), declaringType, declaringType.isParameterizedType());
      }

      ret = new BcelAdvice(this.attribute, pc, (Member)adviceSignature, this.concreteAspect);
      return ret;
   }

   public boolean match(Shadow shadow, World world) {
      if (world.areAllLintIgnored()) {
         return super.match(shadow, world);
      } else {
         this.suppressLintWarnings(world);
         boolean ret = super.match(shadow, world);
         this.clearLintSuppressions(world, this.suppressedLintKinds);
         return ret;
      }
   }

   public void specializeOn(Shadow shadow) {
      if (this.getKind() == AdviceKind.Around) {
         ((BcelShadow)shadow).initializeForAroundClosure();
      }

      if (this.getKind() == null) {
         this.exposedState = new ExposedState(0);
      } else {
         if (this.getKind().isPerEntry()) {
            this.exposedState = new ExposedState(0);
         } else if (this.getKind().isCflow()) {
            this.exposedState = new ExposedState(this.nFreeVars);
         } else {
            if (this.getSignature() == null) {
               this.exposedState = new ExposedState(0);
               return;
            }

            this.exposedState = new ExposedState(this.getSignature());
         }

         World world = shadow.getIWorld();
         if (!world.areAllLintIgnored()) {
            this.suppressLintWarnings(world);
         }

         this.exposedState.setConcreteAspect(this.concreteAspect);
         this.runtimeTest = this.getPointcut().findResidue(shadow, this.exposedState);
         if (!world.areAllLintIgnored()) {
            this.clearLintSuppressions(world, this.suppressedLintKinds);
         }

         if (this.getKind() == AdviceKind.PerThisEntry) {
            shadow.getThisVar();
         } else if (this.getKind() == AdviceKind.PerTargetEntry) {
            shadow.getTargetVar();
         }

         if ((this.getExtraParameterFlags() & 4) != 0) {
            ((BcelShadow)shadow).getThisJoinPointStaticPartVar();
            ((BcelShadow)shadow).getEnclosingClass().warnOnAddedStaticInitializer(shadow, this.getSourceLocation());
         }

         if ((this.getExtraParameterFlags() & 2) != 0) {
            boolean hasGuardTest = this.runtimeTest != Literal.TRUE && this.getKind() != AdviceKind.Around;
            boolean isAround = this.getKind() == AdviceKind.Around;
            ((BcelShadow)shadow).requireThisJoinPoint(hasGuardTest, isAround);
            ((BcelShadow)shadow).getEnclosingClass().warnOnAddedStaticInitializer(shadow, this.getSourceLocation());
            if (!hasGuardTest && world.getLint().multipleAdviceStoppingLazyTjp.isEnabled()) {
               ((BcelShadow)shadow).addAdvicePreventingLazyTjp(this);
            }
         }

         if ((this.getExtraParameterFlags() & 8) != 0) {
            ((BcelShadow)shadow).getThisEnclosingJoinPointStaticPartVar();
            ((BcelShadow)shadow).getEnclosingClass().warnOnAddedStaticInitializer(shadow, this.getSourceLocation());
         }

      }
   }

   private boolean canInline(Shadow s) {
      if (this.attribute.isProceedInInners()) {
         return false;
      } else if (this.concreteAspect != null && !this.concreteAspect.isMissing()) {
         if (this.concreteAspect.getWorld().isXnoInline()) {
            return false;
         } else {
            BcelObjectType boType = BcelWorld.getBcelObjectType(this.concreteAspect);
            if (boType == null) {
               return false;
            } else {
               if (boType.javaClass.getMajor() == 52 && this.containsInvokedynamic == 0) {
                  this.containsInvokedynamic = 1;
                  LazyMethodGen lmg = boType.getLazyClassGen().getLazyMethodGen(this.signature.getName(), this.signature.getSignature(), true);
                  ResolvedType searchType = this.concreteAspect;

                  while(lmg == null) {
                     searchType = searchType.getSuperclass();
                     if (searchType == null) {
                        break;
                     }

                     ReferenceTypeDelegate rtd = ((ReferenceType)searchType).getDelegate();
                     if (rtd instanceof BcelObjectType) {
                        BcelObjectType bot = (BcelObjectType)rtd;
                        if (bot.javaClass.getMajor() < 52) {
                           break;
                        }

                        lmg = bot.getLazyClassGen().getLazyMethodGen(this.signature.getName(), this.signature.getSignature(), true);
                     }
                  }

                  if (lmg != null) {
                     InstructionList ilist = lmg.getBody();

                     for(InstructionHandle src = ilist.getStart(); src != null; src = src.getNext()) {
                        if (src.getInstruction().opcode == 186) {
                           this.containsInvokedynamic = 2;
                           break;
                        }
                     }
                  }
               }

               return this.containsInvokedynamic == 2 ? false : boType.getLazyClassGen().isWoven();
            }
         }
      } else {
         return false;
      }
   }

   private boolean aspectIsBroken() {
      if (this.concreteAspect instanceof ReferenceType) {
         ReferenceTypeDelegate rtDelegate = ((ReferenceType)this.concreteAspect).getDelegate();
         if (!(rtDelegate instanceof BcelObjectType)) {
            return true;
         }
      }

      return false;
   }

   public boolean implementOn(Shadow s) {
      this.hasMatchedAtLeastOnce = true;
      if (this.aspectIsBroken()) {
         return false;
      } else {
         BcelShadow shadow = (BcelShadow)s;
         Member sig;
         if (!shadow.getWorld().isIgnoringUnusedDeclaredThrownException() && !this.getThrownExceptions().isEmpty()) {
            sig = shadow.getSignature();
            if (sig instanceof BcelMethod) {
               this.removeUnnecessaryProblems((BcelMethod)sig, ((BcelMethod)sig).getDeclarationLineNumber());
            } else {
               ResolvedMember resolvedMember = shadow.getSignature().resolve(shadow.getWorld());
               if (resolvedMember instanceof BcelMethod && shadow.getEnclosingShadow() instanceof BcelShadow) {
                  Member enclosingMember = shadow.getEnclosingShadow().getSignature();
                  if (enclosingMember instanceof BcelMethod) {
                     this.removeUnnecessaryProblems((BcelMethod)enclosingMember, ((BcelMethod)resolvedMember).getDeclarationLineNumber());
                  }
               }
            }
         }

         if (shadow.getIWorld().isJoinpointSynchronizationEnabled() && shadow.getKind() == Shadow.MethodExecution && (s.getSignature().getModifiers() & 32) != 0) {
            shadow.getIWorld().getLint().advisingSynchronizedMethods.signal(new String[]{shadow.toString()}, shadow.getSourceLocation(), new ISourceLocation[]{this.getSourceLocation()});
         }

         if (this.runtimeTest == Literal.FALSE) {
            sig = shadow.getSignature();
            if (sig.getArity() == 0 && shadow.getKind() == Shadow.MethodCall && sig.getName().charAt(0) == 'c' && sig.getReturnType().equals(ResolvedType.OBJECT) && sig.getName().equals("clone")) {
               return false;
            }
         }

         if (this.getKind() == AdviceKind.Before) {
            shadow.weaveBefore(this);
         } else if (this.getKind() == AdviceKind.AfterReturning) {
            shadow.weaveAfterReturning(this);
         } else if (this.getKind() == AdviceKind.AfterThrowing) {
            UnresolvedType catchType = this.hasExtraParameter() ? this.getExtraParameterType() : UnresolvedType.THROWABLE;
            shadow.weaveAfterThrowing(this, catchType);
         } else if (this.getKind() == AdviceKind.After) {
            shadow.weaveAfter(this);
         } else if (this.getKind() == AdviceKind.Around) {
            LazyClassGen enclosingClass = shadow.getEnclosingClass();
            if (enclosingClass != null && enclosingClass.isInterface() && shadow.getEnclosingMethod().getName().charAt(0) == '<') {
               shadow.getWorld().getLint().cannotAdviseJoinpointInInterfaceWithAroundAdvice.signal(shadow.toString(), shadow.getSourceLocation());
               return false;
            }

            if (!this.canInline(s)) {
               shadow.weaveAroundClosure(this, this.hasDynamicTests());
            } else {
               shadow.weaveAroundInline(this, this.hasDynamicTests());
            }
         } else if (this.getKind() == AdviceKind.InterInitializer) {
            shadow.weaveAfterReturning(this);
         } else if (this.getKind().isCflow()) {
            shadow.weaveCflowEntry(this, this.getSignature());
         } else if (this.getKind() == AdviceKind.PerThisEntry) {
            shadow.weavePerObjectEntry(this, (BcelVar)shadow.getThisVar());
         } else if (this.getKind() == AdviceKind.PerTargetEntry) {
            shadow.weavePerObjectEntry(this, (BcelVar)shadow.getTargetVar());
         } else if (this.getKind() == AdviceKind.Softener) {
            shadow.weaveSoftener(this, ((ExactTypePattern)this.exceptionType).getType());
         } else {
            if (this.getKind() != AdviceKind.PerTypeWithinEntry) {
               throw new BCException("unimplemented kind: " + this.getKind());
            }

            shadow.weavePerTypeWithinAspectInitialization(this, shadow.getEnclosingType());
         }

         return true;
      }
   }

   private void removeUnnecessaryProblems(BcelMethod method, int problemLineNumber) {
      ISourceContext sourceContext = method.getSourceContext();
      if (sourceContext instanceof IEclipseSourceContext) {
         ((IEclipseSourceContext)sourceContext).removeUnnecessaryProblems(method, problemLineNumber);
      }

   }

   private Collection collectCheckedExceptions(UnresolvedType[] excs) {
      if (excs != null && excs.length != 0) {
         Collection ret = new ArrayList();
         World world = this.concreteAspect.getWorld();
         ResolvedType runtimeException = world.getCoreType(UnresolvedType.RUNTIME_EXCEPTION);
         ResolvedType error = world.getCoreType(UnresolvedType.ERROR);
         int i = 0;

         for(int len = excs.length; i < len; ++i) {
            ResolvedType t = world.resolve(excs[i], true);
            if (t.isMissing()) {
               world.getLint().cantFindType.signal(WeaverMessages.format("cftExceptionType", excs[i].getName()), this.getSourceLocation());
            }

            if (!runtimeException.isAssignableFrom(t) && !error.isAssignableFrom(t)) {
               ret.add(t);
            }
         }

         return ret;
      } else {
         return Collections.emptyList();
      }
   }

   public Collection getThrownExceptions() {
      if (this.thrownExceptions == null) {
         if (this.concreteAspect == null || this.concreteAspect.getWorld() == null || !this.getKind().isAfter() && this.getKind() != AdviceKind.Before && this.getKind() != AdviceKind.Around) {
            this.thrownExceptions = Collections.emptyList();
         } else {
            World world = this.concreteAspect.getWorld();
            ResolvedMember m = world.resolve(this.signature);
            if (m == null) {
               this.thrownExceptions = Collections.emptyList();
            } else {
               this.thrownExceptions = this.collectCheckedExceptions(m.getExceptions());
            }
         }
      }

      return this.thrownExceptions;
   }

   public boolean mustCheckExceptions() {
      if (this.getConcreteAspect() == null) {
         return true;
      } else {
         return !this.getConcreteAspect().isAnnotationStyleAspect();
      }
   }

   public boolean hasDynamicTests() {
      return this.runtimeTest != null && this.runtimeTest != Literal.TRUE;
   }

   InstructionList getAdviceInstructions(BcelShadow s, BcelVar extraArgVar, InstructionHandle ifNoAdvice) {
      InstructionFactory fact = s.getFactory();
      BcelWorld world = s.getWorld();
      InstructionList il = new InstructionList();
      if (this.hasExtraParameter() && this.getKind() == AdviceKind.AfterReturning) {
         UnresolvedType extraParameterType = this.getExtraParameterType();
         if (!extraParameterType.equals(UnresolvedType.OBJECT) && !extraParameterType.isPrimitiveType()) {
            il.append(BcelRenderer.renderTest(fact, world, Test.makeInstanceof(extraArgVar, this.getExtraParameterType().resolve(world)), (InstructionHandle)null, ifNoAdvice, (InstructionHandle)null));
         }
      }

      il.append(this.getAdviceArgSetup(s, extraArgVar, (InstructionList)null));
      il.append(this.getNonTestAdviceInstructions(s));
      InstructionHandle ifYesAdvice = il.getStart();
      il.insert(this.getTestInstructions(s, ifYesAdvice, ifNoAdvice, ifYesAdvice));
      if (s.getKind() == Shadow.MethodExecution && this.getKind() == AdviceKind.Before) {
         int lineNumber = false;
         int lineNumber = s.getEnclosingMethod().getMemberView().getLineNumberOfFirstInstruction();
         InstructionHandle start = il.getStart();
         if (lineNumber > 0) {
            start.addTargeter(new LineNumberTag(lineNumber));
         }

         LocalVariableTable lvt = s.getEnclosingMethod().getMemberView().getMethod().getLocalVariableTable();
         if (lvt != null) {
            LocalVariable[] lvTable = lvt.getLocalVariableTable();

            for(int i = 0; i < lvTable.length; ++i) {
               LocalVariable lv = lvTable[i];
               if (lv.getStartPC() == 0) {
                  start.addTargeter(new LocalVariableTag(lv.getSignature(), lv.getName(), lv.getIndex(), 0));
               }
            }
         }
      }

      return il;
   }

   public InstructionList getAdviceArgSetup(BcelShadow shadow, BcelVar extraVar, InstructionList closureInstantiation) {
      InstructionFactory fact = shadow.getFactory();
      BcelWorld world = shadow.getWorld();
      InstructionList il = new InstructionList();
      if (this.exposedState.getAspectInstance() != null) {
         il.append(BcelRenderer.renderExpr(fact, world, this.exposedState.getAspectInstance()));
      }

      boolean x = this.getDeclaringAspect().resolve(world).isAnnotationStyleAspect();
      boolean isAnnotationStyleAspect = this.getConcreteAspect() != null && this.getConcreteAspect().isAnnotationStyleAspect() && x;
      boolean previousIsClosure = false;
      int i = 0;

      for(int len = this.exposedState.size(); i < len; ++i) {
         if (!this.exposedState.isErroneousVar(i)) {
            BcelVar v = (BcelVar)this.exposedState.get(i);
            if (v == null) {
               if (isAnnotationStyleAspect) {
                  if ("Lorg/aspectj/lang/ProceedingJoinPoint;".equals(this.getSignature().getParameterTypes()[i].getSignature())) {
                     if (this.getKind() != AdviceKind.Around) {
                        previousIsClosure = false;
                        this.getConcreteAspect().getWorld().getMessageHandler().handleMessage(new Message("use of ProceedingJoinPoint is allowed only on around advice (arg " + i + " in " + this.toString() + ")", this.getSourceLocation(), true));
                        il.append(InstructionConstants.ACONST_NULL);
                     } else if (previousIsClosure) {
                        il.append(InstructionConstants.DUP);
                     } else {
                        previousIsClosure = true;
                        il.append(closureInstantiation.copy());
                     }
                  } else if ("Lorg/aspectj/lang/JoinPoint$StaticPart;".equals(this.getSignature().getParameterTypes()[i].getSignature())) {
                     previousIsClosure = false;
                     if ((this.getExtraParameterFlags() & 4) != 0) {
                        shadow.getThisJoinPointStaticPartBcelVar().appendLoad(il, fact);
                     }
                  } else if ("Lorg/aspectj/lang/JoinPoint;".equals(this.getSignature().getParameterTypes()[i].getSignature())) {
                     previousIsClosure = false;
                     if ((this.getExtraParameterFlags() & 2) != 0) {
                        il.append(shadow.loadThisJoinPoint());
                     }
                  } else if ("Lorg/aspectj/lang/JoinPoint$EnclosingStaticPart;".equals(this.getSignature().getParameterTypes()[i].getSignature())) {
                     previousIsClosure = false;
                     if ((this.getExtraParameterFlags() & 8) != 0) {
                        shadow.getThisEnclosingJoinPointStaticPartBcelVar().appendLoad(il, fact);
                     }
                  } else if (this.hasExtraParameter()) {
                     previousIsClosure = false;
                     extraVar.appendLoadAndConvert(il, fact, this.getExtraParameterType().resolve(world));
                  } else {
                     previousIsClosure = false;
                     this.getConcreteAspect().getWorld().getMessageHandler().handleMessage(new Message("use of ProceedingJoinPoint is allowed only on around advice (arg " + i + " in " + this.toString() + ")", this.getSourceLocation(), true));
                     il.append(InstructionConstants.ACONST_NULL);
                  }
               }
            } else {
               UnresolvedType desiredTy = this.getBindingParameterTypes()[i];
               v.appendLoadAndConvert(il, fact, desiredTy.resolve(world));
            }
         }
      }

      if (!isAnnotationStyleAspect) {
         if (this.getKind() == AdviceKind.Around) {
            il.append(closureInstantiation);
         } else if (this.hasExtraParameter()) {
            extraVar.appendLoadAndConvert(il, fact, this.getExtraParameterType().resolve(world));
         }

         if ((this.getExtraParameterFlags() & 4) != 0) {
            shadow.getThisJoinPointStaticPartBcelVar().appendLoad(il, fact);
         }

         if ((this.getExtraParameterFlags() & 2) != 0) {
            il.append(shadow.loadThisJoinPoint());
         }

         if ((this.getExtraParameterFlags() & 8) != 0) {
            shadow.getThisEnclosingJoinPointStaticPartBcelVar().appendLoad(il, fact);
         }
      }

      return il;
   }

   public InstructionList getNonTestAdviceInstructions(BcelShadow shadow) {
      return new InstructionList(Utility.createInvoke(shadow.getFactory(), shadow.getWorld(), this.getOriginalSignature()));
   }

   public Member getOriginalSignature() {
      Member sig = this.getSignature();
      if (sig instanceof ResolvedMember) {
         ResolvedMember rsig = (ResolvedMember)sig;
         if (rsig.hasBackingGenericMember()) {
            return rsig.getBackingGenericMember();
         }
      }

      return sig;
   }

   public InstructionList getTestInstructions(BcelShadow shadow, InstructionHandle sk, InstructionHandle fk, InstructionHandle next) {
      return BcelRenderer.renderTest(shadow.getFactory(), shadow.getWorld(), this.runtimeTest, sk, fk, next);
   }

   public int compareTo(Object other) {
      if (!(other instanceof BcelAdvice)) {
         return 0;
      } else {
         BcelAdvice o = (BcelAdvice)other;
         if (this.kind.getPrecedence() != o.kind.getPrecedence()) {
            return this.kind.getPrecedence() > o.kind.getPrecedence() ? 1 : -1;
         } else if (this.kind.isCflow()) {
            boolean isBelow = this.kind == AdviceKind.CflowBelowEntry;
            if (this.innerCflowEntries.contains(o)) {
               return isBelow ? 1 : -1;
            } else if (o.innerCflowEntries.contains(this)) {
               return isBelow ? -1 : 1;
            } else {
               return 0;
            }
         } else if (!this.kind.isPerEntry() && this.kind != AdviceKind.Softener) {
            World world = this.concreteAspect.getWorld();
            int ret = this.concreteAspect.getWorld().compareByPrecedence(this.concreteAspect, o.concreteAspect);
            if (ret != 0) {
               return ret;
            } else {
               ResolvedType declaringAspect = this.getDeclaringAspect().resolve(world);
               ResolvedType o_declaringAspect = o.getDeclaringAspect().resolve(world);
               if (declaringAspect == o_declaringAspect) {
                  if (!this.kind.isAfter() && !o.kind.isAfter()) {
                     return this.getStart() < o.getStart() ? 1 : -1;
                  } else {
                     return this.getStart() < o.getStart() ? -1 : 1;
                  }
               } else if (declaringAspect.isAssignableFrom(o_declaringAspect)) {
                  return -1;
               } else {
                  return o_declaringAspect.isAssignableFrom(declaringAspect) ? 1 : 0;
               }
            }
         } else {
            return 0;
         }
      }
   }

   public BcelVar[] getExposedStateAsBcelVars(boolean isAround) {
      if (isAround && this.getConcreteAspect() != null && this.getConcreteAspect().isAnnotationStyleAspect()) {
         return BcelVar.NONE;
      } else if (this.exposedState == null) {
         return BcelVar.NONE;
      } else {
         int len = this.exposedState.vars.length;
         BcelVar[] ret = new BcelVar[len];

         for(int i = 0; i < len; ++i) {
            ret[i] = (BcelVar)this.exposedState.vars[i];
         }

         return ret;
      }
   }

   protected void suppressLintWarnings(World inWorld) {
      if (this.suppressedLintKinds == null) {
         if (!(this.signature instanceof BcelMethod)) {
            this.suppressedLintKinds = Collections.emptyList();
            return;
         }

         this.suppressedLintKinds = Utility.getSuppressedWarnings(this.signature.getAnnotations(), inWorld.getLint());
      }

      inWorld.getLint().suppressKinds(this.suppressedLintKinds);
   }

   protected void clearLintSuppressions(World inWorld, Collection toClear) {
      inWorld.getLint().clearSuppressions(toClear);
   }

   public BcelAdvice(AdviceKind kind, Pointcut pointcut, Member signature, int extraArgumentFlags, int start, int end, ISourceContext sourceContext, ResolvedType concreteAspect) {
      this(new AjAttribute.AdviceAttribute(kind, pointcut, extraArgumentFlags, start, end, sourceContext), pointcut, signature, concreteAspect);
      this.thrownExceptions = Collections.emptyList();
   }
}
