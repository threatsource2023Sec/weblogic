package com.bea.core.repackaged.aspectj.weaver;

import com.bea.core.repackaged.aspectj.bridge.IMessage;
import com.bea.core.repackaged.aspectj.bridge.ISourceLocation;
import com.bea.core.repackaged.aspectj.bridge.MessageUtil;
import com.bea.core.repackaged.aspectj.util.PartialOrder;
import com.bea.core.repackaged.aspectj.util.TypeSafeEnum;
import com.bea.core.repackaged.aspectj.weaver.ast.Var;
import java.io.DataInputStream;
import java.io.IOException;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public abstract class Shadow {
   private static int nextShadowID = 100;
   private final Kind kind;
   private final Member signature;
   private Member matchingSignature;
   private ResolvedMember resolvedSignature;
   protected final Shadow enclosingShadow;
   protected List mungers = Collections.emptyList();
   public int shadowId;
   public static String METHOD_EXECUTION = "method-execution";
   public static String METHOD_CALL = "method-call";
   public static String CONSTRUCTOR_EXECUTION = "constructor-execution";
   public static String CONSTRUCTOR_CALL = "constructor-call";
   public static String FIELD_GET = "field-get";
   public static String FIELD_SET = "field-set";
   public static String STATICINITIALIZATION = "staticinitialization";
   public static String PREINITIALIZATION = "preinitialization";
   public static String INITIALIZATION = "initialization";
   public static String EXCEPTION_HANDLER = "exception-handler";
   public static String SYNCHRONIZATION_LOCK = "lock";
   public static String SYNCHRONIZATION_UNLOCK = "unlock";
   public static String ADVICE_EXECUTION = "adviceexecution";
   public static final Kind MethodCall;
   public static final Kind ConstructorCall;
   public static final Kind MethodExecution;
   public static final Kind ConstructorExecution;
   public static final Kind FieldGet;
   public static final Kind FieldSet;
   public static final Kind StaticInitialization;
   public static final Kind PreInitialization;
   public static final Kind AdviceExecution;
   public static final Kind Initialization;
   public static final Kind ExceptionHandler;
   public static final Kind SynchronizationLock;
   public static final Kind SynchronizationUnlock;
   public static final int MethodCallBit = 2;
   public static final int ConstructorCallBit = 4;
   public static final int MethodExecutionBit = 8;
   public static final int ConstructorExecutionBit = 16;
   public static final int FieldGetBit = 32;
   public static final int FieldSetBit = 64;
   public static final int StaticInitializationBit = 128;
   public static final int PreInitializationBit = 256;
   public static final int AdviceExecutionBit = 512;
   public static final int InitializationBit = 1024;
   public static final int ExceptionHandlerBit = 2048;
   public static final int SynchronizationLockBit = 4096;
   public static final int SynchronizationUnlockBit = 8192;
   public static final int MAX_SHADOW_KIND = 13;
   public static final Kind[] SHADOW_KINDS;
   public static final int ALL_SHADOW_KINDS_BITS;
   public static final int NO_SHADOW_KINDS_BITS;

   protected Shadow(Kind kind, Member signature, Shadow enclosingShadow) {
      this.shadowId = nextShadowID++;
      this.kind = kind;
      this.signature = signature;
      this.enclosingShadow = enclosingShadow;
   }

   public abstract World getIWorld();

   public List getMungers() {
      return this.mungers;
   }

   public final boolean hasThis() {
      if (this.getKind().neverHasThis()) {
         return false;
      } else if (this.getKind().isEnclosingKind()) {
         return !Modifier.isStatic(this.getSignature().getModifiers());
      } else {
         return this.enclosingShadow == null ? false : this.enclosingShadow.hasThis();
      }
   }

   public final UnresolvedType getThisType() {
      if (!this.hasThis()) {
         throw new IllegalStateException("no this");
      } else {
         return this.getKind().isEnclosingKind() ? this.getSignature().getDeclaringType() : this.enclosingShadow.getThisType();
      }
   }

   public abstract Var getThisVar();

   public final boolean hasTarget() {
      if (this.getKind().neverHasTarget()) {
         return false;
      } else if (this.getKind().isTargetSameAsThis()) {
         return this.hasThis();
      } else {
         return !Modifier.isStatic(this.getSignature().getModifiers());
      }
   }

   public final UnresolvedType getTargetType() {
      if (!this.hasTarget()) {
         throw new IllegalStateException("no target");
      } else {
         return this.getSignature().getDeclaringType();
      }
   }

   public abstract Var getTargetVar();

   public UnresolvedType[] getArgTypes() {
      return this.getKind() == FieldSet ? new UnresolvedType[]{this.getSignature().getReturnType()} : this.getSignature().getParameterTypes();
   }

   public boolean isShadowForArrayConstructionJoinpoint() {
      return this.getKind() == ConstructorCall && this.signature.getDeclaringType().isArray();
   }

   public boolean isShadowForMonitor() {
      return this.getKind() == SynchronizationLock || this.getKind() == SynchronizationUnlock;
   }

   public ResolvedType[] getArgumentTypesForArrayConstructionShadow() {
      String s = this.signature.getDeclaringType().getSignature();
      int pos = s.indexOf("[");
      int dims = 1;

      while(pos < s.length()) {
         ++pos;
         if (pos < s.length()) {
            dims += s.charAt(pos) == '[' ? 1 : 0;
         }
      }

      ResolvedType intType = UnresolvedType.INT.resolve(this.getIWorld());
      if (dims == 1) {
         return new ResolvedType[]{intType};
      } else {
         ResolvedType[] someInts = new ResolvedType[dims];

         for(int i = 0; i < dims; ++i) {
            someInts[i] = intType;
         }

         return someInts;
      }
   }

   public UnresolvedType[] getGenericArgTypes() {
      if (this.isShadowForArrayConstructionJoinpoint()) {
         return this.getArgumentTypesForArrayConstructionShadow();
      } else if (this.isShadowForMonitor()) {
         return UnresolvedType.ARRAY_WITH_JUST_OBJECT;
      } else {
         return this.getKind() == FieldSet ? new UnresolvedType[]{this.getResolvedSignature().getGenericReturnType()} : this.getResolvedSignature().getGenericParameterTypes();
      }
   }

   public UnresolvedType getArgType(int arg) {
      return this.getKind() == FieldSet ? this.getSignature().getReturnType() : this.getSignature().getParameterTypes()[arg];
   }

   public int getArgCount() {
      return this.getKind() == FieldSet ? 1 : this.getSignature().getParameterTypes().length;
   }

   public abstract UnresolvedType getEnclosingType();

   public abstract Var getArgVar(int var1);

   public abstract Var getThisJoinPointVar();

   public abstract Var getThisJoinPointStaticPartVar();

   public abstract Var getThisEnclosingJoinPointStaticPartVar();

   public abstract Var getThisAspectInstanceVar(ResolvedType var1);

   public abstract Var getKindedAnnotationVar(UnresolvedType var1);

   public abstract Var getWithinAnnotationVar(UnresolvedType var1);

   public abstract Var getWithinCodeAnnotationVar(UnresolvedType var1);

   public abstract Var getThisAnnotationVar(UnresolvedType var1);

   public abstract Var getTargetAnnotationVar(UnresolvedType var1);

   public abstract Var getArgAnnotationVar(int var1, UnresolvedType var2);

   public abstract Member getEnclosingCodeSignature();

   public Kind getKind() {
      return this.kind;
   }

   public Member getSignature() {
      return this.signature;
   }

   public Member getMatchingSignature() {
      return this.matchingSignature != null ? this.matchingSignature : this.signature;
   }

   public void setMatchingSignature(Member member) {
      this.matchingSignature = member;
   }

   public ResolvedMember getResolvedSignature() {
      if (this.resolvedSignature == null) {
         this.resolvedSignature = this.signature.resolve(this.getIWorld());
      }

      return this.resolvedSignature;
   }

   public UnresolvedType getReturnType() {
      if (this.kind == ConstructorCall) {
         return this.getSignature().getDeclaringType();
      } else if (this.kind == FieldSet) {
         return UnresolvedType.VOID;
      } else {
         return this.kind != SynchronizationLock && this.kind != SynchronizationUnlock ? this.getResolvedSignature().getGenericReturnType() : UnresolvedType.VOID;
      }
   }

   public static int howMany(int i) {
      int count = 0;

      for(int j = 0; j < SHADOW_KINDS.length; ++j) {
         if ((i & SHADOW_KINDS[j].bit) != 0) {
            ++count;
         }
      }

      return count;
   }

   protected boolean checkMunger(ShadowMunger munger) {
      if (munger.mustCheckExceptions()) {
         Iterator i = munger.getThrownExceptions().iterator();

         while(i.hasNext()) {
            if (!this.checkCanThrow(munger, (ResolvedType)i.next())) {
               return false;
            }
         }
      }

      return true;
   }

   protected boolean checkCanThrow(ShadowMunger munger, ResolvedType resolvedTypeX) {
      if (this.getKind() == ExceptionHandler) {
         return true;
      } else {
         if (!this.isDeclaredException(resolvedTypeX, this.getSignature())) {
            this.getIWorld().showMessage(IMessage.ERROR, WeaverMessages.format("cantThrowChecked", resolvedTypeX, this), this.getSourceLocation(), munger.getSourceLocation());
         }

         return true;
      }
   }

   private boolean isDeclaredException(ResolvedType resolvedTypeX, Member member) {
      ResolvedType[] excs = this.getIWorld().resolve(member.getExceptions(this.getIWorld()));
      int i = 0;

      for(int len = excs.length; i < len; ++i) {
         if (excs[i].isAssignableFrom(resolvedTypeX)) {
            return true;
         }
      }

      return false;
   }

   public void addMunger(ShadowMunger munger) {
      if (this.checkMunger(munger)) {
         if (this.mungers == Collections.EMPTY_LIST) {
            this.mungers = new ArrayList();
         }

         this.mungers.add(munger);
      }

   }

   public final void implement() {
      this.sortMungers();
      if (this.mungers != null) {
         this.prepareForMungers();
         this.implementMungers();
      }
   }

   private void sortMungers() {
      List sorted = PartialOrder.sort(this.mungers);
      this.possiblyReportUnorderedAdvice(sorted);
      if (sorted == null) {
         Iterator i$ = this.mungers.iterator();

         while(i$.hasNext()) {
            ShadowMunger m = (ShadowMunger)i$.next();
            this.getIWorld().getMessageHandler().handleMessage(MessageUtil.error(WeaverMessages.format("circularDependency", this), m.getSourceLocation()));
         }
      }

      this.mungers = sorted;
   }

   private void possiblyReportUnorderedAdvice(List sorted) {
      if (sorted != null && this.getIWorld().getLint().unorderedAdviceAtShadow.isEnabled() && this.mungers.size() > 1) {
         Set clashingAspects = new HashSet();
         int max = this.mungers.size();

         for(int i = max - 1; i >= 0; --i) {
            for(int j = 0; j < i; ++j) {
               Object a = this.mungers.get(i);
               Object b = this.mungers.get(j);
               if (a instanceof Advice && b instanceof Advice) {
                  Advice adviceA = (Advice)a;
                  Advice adviceB = (Advice)b;
                  if (!adviceA.concreteAspect.equals(adviceB.concreteAspect)) {
                     AdviceKind adviceKindA = adviceA.getKind();
                     AdviceKind adviceKindB = adviceB.getKind();
                     if (adviceKindA.getKey() < 6 && adviceKindB.getKey() < 6 && adviceKindA.getPrecedence() == adviceKindB.getPrecedence()) {
                        Integer order = this.getIWorld().getPrecedenceIfAny(adviceA.concreteAspect, adviceB.concreteAspect);
                        if (order != null && order.equals(new Integer(0))) {
                           String key = adviceA.getDeclaringAspect() + ":" + adviceB.getDeclaringAspect();
                           String possibleExistingKey = adviceB.getDeclaringAspect() + ":" + adviceA.getDeclaringAspect();
                           if (!clashingAspects.contains(possibleExistingKey)) {
                              clashingAspects.add(key);
                           }
                        }
                     }
                  }
               }
            }
         }

         Iterator iter = clashingAspects.iterator();

         while(iter.hasNext()) {
            String element = (String)iter.next();
            String aspect1 = element.substring(0, element.indexOf(":"));
            String aspect2 = element.substring(element.indexOf(":") + 1);
            this.getIWorld().getLint().unorderedAdviceAtShadow.signal(new String[]{this.toString(), aspect1, aspect2}, this.getSourceLocation(), (ISourceLocation[])null);
         }
      }

   }

   protected void prepareForMungers() {
      throw new RuntimeException("Generic shadows cannot be prepared");
   }

   private void implementMungers() {
      World world = this.getIWorld();
      Iterator i$ = this.mungers.iterator();

      while(i$.hasNext()) {
         ShadowMunger munger = (ShadowMunger)i$.next();
         if (munger.implementOn(this)) {
            world.reportMatch(munger, this);
         }
      }

   }

   public abstract ISourceLocation getSourceLocation();

   public String toString() {
      return this.getKind() + "(" + this.getSignature() + ")";
   }

   public String toResolvedString(World world) {
      StringBuffer sb = new StringBuffer();
      sb.append(this.getKind());
      sb.append("(");
      Member m = this.getSignature();
      if (m == null) {
         sb.append("<<missing signature>>");
      } else {
         ResolvedMember rm = world.resolve(m);
         if (rm == null) {
            sb.append("<<unresolvableMember:").append(m).append(">>");
         } else {
            String genString = rm.toGenericString();
            if (genString == null) {
               sb.append("<<unableToGetGenericStringFor:").append(rm).append(">>");
            } else {
               sb.append(genString);
            }
         }
      }

      sb.append(")");
      return sb.toString();
   }

   public static Set toSet(int i) {
      Set results = new HashSet();

      for(int j = 0; j < SHADOW_KINDS.length; ++j) {
         Kind k = SHADOW_KINDS[j];
         if (k.isSet(i)) {
            results.add(k);
         }
      }

      return results;
   }

   static {
      MethodCall = new Kind(METHOD_CALL, 1, true);
      ConstructorCall = new Kind(CONSTRUCTOR_CALL, 2, true);
      MethodExecution = new Kind(METHOD_EXECUTION, 3, false);
      ConstructorExecution = new Kind(CONSTRUCTOR_EXECUTION, 4, false);
      FieldGet = new Kind(FIELD_GET, 5, true);
      FieldSet = new Kind(FIELD_SET, 6, true);
      StaticInitialization = new Kind(STATICINITIALIZATION, 7, false);
      PreInitialization = new Kind(PREINITIALIZATION, 8, false);
      AdviceExecution = new Kind(ADVICE_EXECUTION, 9, false);
      Initialization = new Kind(INITIALIZATION, 10, false);
      ExceptionHandler = new Kind(EXCEPTION_HANDLER, 11, true);
      SynchronizationLock = new Kind(SYNCHRONIZATION_LOCK, 12, true);
      SynchronizationUnlock = new Kind(SYNCHRONIZATION_UNLOCK, 13, true);
      SHADOW_KINDS = new Kind[]{MethodCall, ConstructorCall, MethodExecution, ConstructorExecution, FieldGet, FieldSet, StaticInitialization, PreInitialization, AdviceExecution, Initialization, ExceptionHandler, SynchronizationLock, SynchronizationUnlock};
      ALL_SHADOW_KINDS_BITS = 16382;
      NO_SHADOW_KINDS_BITS = 0;
   }

   public static final class Kind extends TypeSafeEnum {
      public int bit;
      private static final int hasReturnValueFlag = 558;
      private static final int isEnclosingKindFlag = 1688;
      private static final int isTargetSameAsThisFlag = 1944;
      private static final int neverHasTargetFlag = 14724;
      private static final int neverHasThisFlag = 384;

      public Kind(String name, int key, boolean argsOnStack) {
         super(name, key);
         this.bit = 1 << key;
      }

      public String toLegalJavaIdentifier() {
         return this.getName().replace('-', '_');
      }

      public boolean argsOnStack() {
         return !this.isTargetSameAsThis();
      }

      public boolean allowsExtraction() {
         return true;
      }

      public boolean isSet(int i) {
         return (i & this.bit) != 0;
      }

      public boolean hasHighPriorityExceptions() {
         return !this.isTargetSameAsThis();
      }

      public boolean hasReturnValue() {
         return (this.bit & 558) != 0;
      }

      public boolean isEnclosingKind() {
         return (this.bit & 1688) != 0;
      }

      public boolean isTargetSameAsThis() {
         return (this.bit & 1944) != 0;
      }

      public boolean neverHasTarget() {
         return (this.bit & 14724) != 0;
      }

      public boolean neverHasThis() {
         return (this.bit & 384) != 0;
      }

      public String getSimpleName() {
         int dash = this.getName().lastIndexOf(45);
         return dash == -1 ? this.getName() : this.getName().substring(dash + 1);
      }

      public static Kind read(DataInputStream s) throws IOException {
         int key = s.readByte();
         switch (key) {
            case 1:
               return Shadow.MethodCall;
            case 2:
               return Shadow.ConstructorCall;
            case 3:
               return Shadow.MethodExecution;
            case 4:
               return Shadow.ConstructorExecution;
            case 5:
               return Shadow.FieldGet;
            case 6:
               return Shadow.FieldSet;
            case 7:
               return Shadow.StaticInitialization;
            case 8:
               return Shadow.PreInitialization;
            case 9:
               return Shadow.AdviceExecution;
            case 10:
               return Shadow.Initialization;
            case 11:
               return Shadow.ExceptionHandler;
            case 12:
               return Shadow.SynchronizationLock;
            case 13:
               return Shadow.SynchronizationUnlock;
            default:
               throw new BCException("unknown kind: " + key);
         }
      }
   }
}
