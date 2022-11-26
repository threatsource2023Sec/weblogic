package com.bea.core.repackaged.aspectj.weaver.patterns;

import com.bea.core.repackaged.aspectj.bridge.ISourceLocation;
import com.bea.core.repackaged.aspectj.util.FuzzyBoolean;
import com.bea.core.repackaged.aspectj.weaver.AjcMemberMaker;
import com.bea.core.repackaged.aspectj.weaver.AnnotationTargetKind;
import com.bea.core.repackaged.aspectj.weaver.CompressingDataOutputStream;
import com.bea.core.repackaged.aspectj.weaver.ConcreteTypeMunger;
import com.bea.core.repackaged.aspectj.weaver.ISourceContext;
import com.bea.core.repackaged.aspectj.weaver.JoinPointSignature;
import com.bea.core.repackaged.aspectj.weaver.JoinPointSignatureIterator;
import com.bea.core.repackaged.aspectj.weaver.Member;
import com.bea.core.repackaged.aspectj.weaver.MemberKind;
import com.bea.core.repackaged.aspectj.weaver.NewFieldTypeMunger;
import com.bea.core.repackaged.aspectj.weaver.ResolvableTypeList;
import com.bea.core.repackaged.aspectj.weaver.ResolvedMember;
import com.bea.core.repackaged.aspectj.weaver.ResolvedType;
import com.bea.core.repackaged.aspectj.weaver.UnresolvedType;
import com.bea.core.repackaged.aspectj.weaver.VersionedDataInputStream;
import com.bea.core.repackaged.aspectj.weaver.World;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class SignaturePattern extends PatternNode implements ISignaturePattern {
   private MemberKind kind;
   private ModifiersPattern modifiers;
   private TypePattern returnType;
   private TypePattern declaringType;
   private NamePattern name;
   private TypePatternList parameterTypes;
   private int bits = 0;
   private static final int PARAMETER_ANNOTATION_MATCHING = 1;
   private static final int CHECKED_FOR_PARAMETER_ANNOTATION_MATCHING = 2;
   private ThrowsPattern throwsPattern;
   private AnnotationTypePattern annotationPattern;
   private transient int hashcode = -1;
   private transient boolean isExactDeclaringTypePattern = false;

   public SignaturePattern(MemberKind kind, ModifiersPattern modifiers, TypePattern returnType, TypePattern declaringType, NamePattern name, TypePatternList parameterTypes, ThrowsPattern throwsPattern, AnnotationTypePattern annotationPattern) {
      this.kind = kind;
      this.modifiers = modifiers;
      this.returnType = returnType;
      this.name = name;
      this.declaringType = declaringType;
      this.parameterTypes = parameterTypes;
      this.throwsPattern = throwsPattern;
      this.annotationPattern = annotationPattern;
      this.isExactDeclaringTypePattern = declaringType instanceof ExactTypePattern;
   }

   public SignaturePattern resolveBindings(IScope scope, Bindings bindings) {
      if (this.returnType != null) {
         this.returnType = this.returnType.resolveBindings(scope, bindings, false, false);
         this.checkForIncorrectTargetKind(this.returnType, scope, false);
      }

      if (this.declaringType != null) {
         this.declaringType = this.declaringType.resolveBindings(scope, bindings, false, false);
         this.checkForIncorrectTargetKind(this.declaringType, scope, false);
         this.isExactDeclaringTypePattern = this.declaringType instanceof ExactTypePattern;
      }

      if (this.parameterTypes != null) {
         this.parameterTypes = this.parameterTypes.resolveBindings(scope, bindings, false, false);
         this.checkForIncorrectTargetKind(this.parameterTypes, scope, false, true);
      }

      if (this.throwsPattern != null) {
         this.throwsPattern = this.throwsPattern.resolveBindings(scope, bindings);
         if (this.throwsPattern.getForbidden().getTypePatterns().length > 0 || this.throwsPattern.getRequired().getTypePatterns().length > 0) {
            this.checkForIncorrectTargetKind(this.throwsPattern, scope, false);
         }
      }

      if (this.annotationPattern != null) {
         this.annotationPattern = this.annotationPattern.resolveBindings(scope, bindings, false);
         this.checkForIncorrectTargetKind(this.annotationPattern, scope, true);
      }

      this.hashcode = -1;
      return this;
   }

   private void checkForIncorrectTargetKind(PatternNode patternNode, IScope scope, boolean targetsOtherThanTypeAllowed) {
      this.checkForIncorrectTargetKind(patternNode, scope, targetsOtherThanTypeAllowed, false);
   }

   private void checkForIncorrectTargetKind(PatternNode patternNode, IScope scope, boolean targetsOtherThanTypeAllowed, boolean parameterTargettingAnnotationsAllowed) {
      if (scope.getWorld().isInJava5Mode() && scope.getWorld().getLint().unmatchedTargetKind != null && !(patternNode instanceof AnyTypePattern)) {
         if (patternNode instanceof ExactAnnotationTypePattern) {
            ResolvedType resolvedType = ((ExactAnnotationTypePattern)patternNode).getAnnotationType().resolve(scope.getWorld());
            AnnotationTargetKind[] targetKinds;
            if (targetsOtherThanTypeAllowed) {
               targetKinds = resolvedType.getAnnotationTargetKinds();
               if (targetKinds == null) {
                  return;
               }

               this.reportUnmatchedTargetKindMessage(targetKinds, patternNode, scope, true);
            } else if (!targetsOtherThanTypeAllowed && !resolvedType.canAnnotationTargetType()) {
               targetKinds = resolvedType.getAnnotationTargetKinds();
               if (targetKinds == null) {
                  return;
               }

               this.reportUnmatchedTargetKindMessage(targetKinds, patternNode, scope, false);
            }
         } else {
            TypePatternVisitor visitor = new TypePatternVisitor(scope, targetsOtherThanTypeAllowed, parameterTargettingAnnotationsAllowed);
            patternNode.traverse(visitor, (Object)null);
            if (visitor.containedIncorrectTargetKind()) {
               Set keys = visitor.getIncorrectTargetKinds().keySet();
               Iterator iter = keys.iterator();

               while(iter.hasNext()) {
                  PatternNode node = (PatternNode)iter.next();
                  AnnotationTargetKind[] targetKinds = (AnnotationTargetKind[])visitor.getIncorrectTargetKinds().get(node);
                  this.reportUnmatchedTargetKindMessage(targetKinds, node, scope, false);
               }
            }
         }

      }
   }

   private void reportUnmatchedTargetKindMessage(AnnotationTargetKind[] annotationTargetKinds, PatternNode node, IScope scope, boolean checkMatchesMemberKindName) {
      StringBuffer targetNames = new StringBuffer("{");

      for(int i = 0; i < annotationTargetKinds.length; ++i) {
         AnnotationTargetKind targetKind = annotationTargetKinds[i];
         if (checkMatchesMemberKindName && this.kind.getName().equals(targetKind.getName())) {
            return;
         }

         if (i < annotationTargetKinds.length - 1) {
            targetNames.append("ElementType." + targetKind.getName() + ",");
         } else {
            targetNames.append("ElementType." + targetKind.getName() + "}");
         }
      }

      scope.getWorld().getLint().unmatchedTargetKind.signal(new String[]{node.toString(), targetNames.toString()}, this.getSourceLocation(), new ISourceLocation[0]);
   }

   public void postRead(ResolvedType enclosingType) {
      if (this.returnType != null) {
         this.returnType.postRead(enclosingType);
      }

      if (this.declaringType != null) {
         this.declaringType.postRead(enclosingType);
      }

      if (this.parameterTypes != null) {
         this.parameterTypes.postRead(enclosingType);
      }

   }

   public SignaturePattern parameterizeWith(Map typeVariableMap, World w) {
      SignaturePattern ret = new SignaturePattern(this.kind, this.modifiers, this.returnType.parameterizeWith(typeVariableMap, w), this.declaringType.parameterizeWith(typeVariableMap, w), this.name, this.parameterTypes.parameterizeWith(typeVariableMap, w), this.throwsPattern.parameterizeWith(typeVariableMap, w), this.annotationPattern.parameterizeWith(typeVariableMap, w));
      ret.copyLocationFrom(this);
      return ret;
   }

   public boolean matches(Member joinPointSignature, World world, boolean allowBridgeMethods) {
      if (joinPointSignature == null) {
         return false;
      } else if (this.kind != joinPointSignature.getKind()) {
         return false;
      } else if (this.kind == Member.ADVICE) {
         return true;
      } else {
         boolean subjectMatch = true;
         boolean wantsAnnotationMatch = this.wantToMatchAnnotationPattern();
         JoinPointSignatureIterator candidateMatches = joinPointSignature.getJoinPointSignatures(world);

         do {
            if (!candidateMatches.hasNext()) {
               return false;
            }

            JoinPointSignature aSig = candidateMatches.next();
            FuzzyBoolean matchResult = this.matchesExactly(aSig, world, allowBridgeMethods, subjectMatch);
            if (matchResult.alwaysTrue()) {
               return true;
            }

            if (matchResult.alwaysFalse()) {
               return false;
            }

            subjectMatch = false;
         } while(!wantsAnnotationMatch);

         return false;
      }
   }

   private FuzzyBoolean matchesExactly(JoinPointSignature aMember, World inAWorld, boolean allowBridgeMethods, boolean subjectMatch) {
      if (aMember.isBridgeMethod() && !allowBridgeMethods) {
         return FuzzyBoolean.MAYBE;
      } else if (subjectMatch && !this.modifiers.matches(aMember.getModifiers())) {
         return FuzzyBoolean.NO;
      } else {
         FuzzyBoolean matchesIgnoringAnnotations = FuzzyBoolean.YES;
         if (this.kind == Member.STATIC_INITIALIZATION) {
            matchesIgnoringAnnotations = this.matchesExactlyStaticInitialization(aMember, inAWorld);
         } else if (this.kind == Member.FIELD) {
            matchesIgnoringAnnotations = this.matchesExactlyField(aMember, inAWorld);
         } else if (this.kind == Member.METHOD) {
            matchesIgnoringAnnotations = this.matchesExactlyMethod(aMember, inAWorld, subjectMatch);
         } else if (this.kind == Member.CONSTRUCTOR) {
            matchesIgnoringAnnotations = this.matchesExactlyConstructor(aMember, inAWorld);
         }

         if (matchesIgnoringAnnotations.alwaysFalse()) {
            return FuzzyBoolean.NO;
         } else if (subjectMatch) {
            return !this.matchesAnnotations(aMember, inAWorld).alwaysTrue() ? FuzzyBoolean.NO : matchesIgnoringAnnotations;
         } else {
            return this.annotationPattern instanceof AnyAnnotationTypePattern ? matchesIgnoringAnnotations : FuzzyBoolean.NO;
         }
      }
   }

   private boolean wantToMatchAnnotationPattern() {
      return !(this.annotationPattern instanceof AnyAnnotationTypePattern);
   }

   private FuzzyBoolean matchesExactlyStaticInitialization(JoinPointSignature aMember, World world) {
      return FuzzyBoolean.fromBoolean(this.declaringType.matchesStatically(aMember.getDeclaringType().resolve(world)));
   }

   private FuzzyBoolean matchesExactlyField(JoinPointSignature aField, World world) {
      if (!this.name.matches(aField.getName())) {
         return FuzzyBoolean.NO;
      } else {
         ResolvedType fieldDeclaringType = aField.getDeclaringType().resolve(world);
         if (!this.declaringType.matchesStatically(fieldDeclaringType)) {
            return FuzzyBoolean.MAYBE;
         } else {
            return !this.returnType.matchesStatically(aField.getReturnType().resolve(world)) && !this.returnType.matchesStatically(aField.getGenericReturnType().resolve(world)) ? FuzzyBoolean.MAYBE : FuzzyBoolean.YES;
         }
      }
   }

   private boolean parametersCannotMatch(JoinPointSignature methodJoinpoint) {
      if (methodJoinpoint.isVarargsMethod()) {
         return false;
      } else {
         int patternParameterCount = this.parameterTypes.size();
         if (patternParameterCount == 0 || this.parameterTypes.ellipsisCount == 0) {
            boolean equalCount = patternParameterCount == methodJoinpoint.getParameterTypes().length;
            if (patternParameterCount == 0 && !equalCount) {
               return true;
            }

            if (this.parameterTypes.ellipsisCount == 0 && !equalCount) {
               if (patternParameterCount > 0 && this.parameterTypes.get(patternParameterCount - 1).isVarArgs()) {
                  return false;
               }

               return true;
            }
         }

         return false;
      }
   }

   private FuzzyBoolean matchesExactlyMethod(JoinPointSignature aMethod, World world, boolean subjectMatch) {
      if (this.parametersCannotMatch(aMethod)) {
         return FuzzyBoolean.NO;
      } else if (!this.name.matches(aMethod.getName())) {
         return FuzzyBoolean.NO;
      } else if (subjectMatch && !this.throwsPattern.matches(aMethod.getExceptions(), world)) {
         return FuzzyBoolean.NO;
      } else if (!this.declaringType.isStar() && !this.declaringType.matchesStatically(aMethod.getDeclaringType().resolve(world))) {
         return FuzzyBoolean.MAYBE;
      } else {
         if (!this.returnType.isStar()) {
            boolean b = this.returnType.isBangVoid();
            String s;
            if (b) {
               s = aMethod.getReturnType().getSignature();
               if (s.length() == 1 && s.charAt(0) == 'V') {
                  return FuzzyBoolean.NO;
               }
            } else if (this.returnType.isVoid()) {
               s = aMethod.getReturnType().getSignature();
               if (s.length() != 1 || s.charAt(0) != 'V') {
                  return FuzzyBoolean.NO;
               }
            } else if (!this.returnType.matchesStatically(aMethod.getReturnType().resolve(world)) && !this.returnType.matchesStatically(aMethod.getGenericReturnType().resolve(world))) {
               return FuzzyBoolean.MAYBE;
            }
         }

         if (this.parameterTypes.size() == 1 && this.parameterTypes.get(0).isEllipsis()) {
            return FuzzyBoolean.YES;
         } else if (!this.parameterTypes.canMatchSignatureWithNParameters(aMethod.getParameterTypes().length)) {
            return FuzzyBoolean.NO;
         } else {
            ResolvableTypeList rtl = new ResolvableTypeList(world, aMethod.getParameterTypes());
            ResolvedType[][] parameterAnnotationTypes = (ResolvedType[][])null;
            if (this.isMatchingParameterAnnotations()) {
               parameterAnnotationTypes = aMethod.getParameterAnnotationTypes();
               if (parameterAnnotationTypes != null && parameterAnnotationTypes.length == 0) {
                  parameterAnnotationTypes = (ResolvedType[][])null;
               }
            }

            if (!this.parameterTypes.matches(rtl, TypePattern.STATIC, parameterAnnotationTypes).alwaysTrue() && !this.parameterTypes.matches(new ResolvableTypeList(world, aMethod.getGenericParameterTypes()), TypePattern.STATIC, parameterAnnotationTypes).alwaysTrue()) {
               return FuzzyBoolean.MAYBE;
            } else if (!this.matchesVarArgs(aMethod, world)) {
               return FuzzyBoolean.MAYBE;
            } else {
               return FuzzyBoolean.YES;
            }
         }
      }
   }

   private boolean isMatchingParameterAnnotations() {
      if ((this.bits & 2) == 0) {
         this.bits |= 2;
         int tp = 0;

         for(int max = this.parameterTypes.size(); tp < max; ++tp) {
            TypePattern typePattern = this.parameterTypes.get(tp);
            if (this.isParameterAnnotationMatching(typePattern)) {
               this.bits |= 1;
            }
         }
      }

      return (this.bits & 1) != 0;
   }

   private boolean isParameterAnnotationMatching(TypePattern tp) {
      if (tp instanceof OrTypePattern) {
         OrTypePattern orAtp = (OrTypePattern)tp;
         return this.isParameterAnnotationMatching(orAtp.getLeft()) || this.isParameterAnnotationMatching(orAtp.getRight());
      } else if (!(tp instanceof AndTypePattern)) {
         if (tp instanceof NotTypePattern) {
            NotTypePattern notAtp = (NotTypePattern)tp;
            return this.isParameterAnnotationMatching(notAtp.getNegatedPattern());
         } else {
            AnnotationTypePattern atp = tp.getAnnotationPattern();
            return this.isParameterAnnotationMatching(atp);
         }
      } else {
         AndTypePattern andAtp = (AndTypePattern)tp;
         return this.isParameterAnnotationMatching(andAtp.getLeft()) || this.isParameterAnnotationMatching(andAtp.getRight());
      }
   }

   private boolean isParameterAnnotationMatching(AnnotationTypePattern tp) {
      if (tp instanceof OrAnnotationTypePattern) {
         OrAnnotationTypePattern orAtp = (OrAnnotationTypePattern)tp;
         return this.isParameterAnnotationMatching(orAtp.getLeft()) || this.isParameterAnnotationMatching(orAtp.getRight());
      } else if (!(tp instanceof AndAnnotationTypePattern)) {
         if (tp instanceof NotAnnotationTypePattern) {
            NotAnnotationTypePattern notAtp = (NotAnnotationTypePattern)tp;
            return this.isParameterAnnotationMatching(notAtp.negatedPattern);
         } else {
            return tp.isForParameterAnnotationMatch();
         }
      } else {
         AndAnnotationTypePattern andAtp = (AndAnnotationTypePattern)tp;
         return this.isParameterAnnotationMatching(andAtp.getLeft()) || this.isParameterAnnotationMatching(andAtp.getRight());
      }
   }

   private FuzzyBoolean matchesExactlyConstructor(JoinPointSignature aConstructor, World world) {
      if (!this.declaringType.matchesStatically(aConstructor.getDeclaringType().resolve(world))) {
         return FuzzyBoolean.NO;
      } else if (!this.parameterTypes.canMatchSignatureWithNParameters(aConstructor.getParameterTypes().length)) {
         return FuzzyBoolean.NO;
      } else {
         ResolvedType[] resolvedParameters = world.resolve(aConstructor.getParameterTypes());
         ResolvedType[][] parameterAnnotationTypes = aConstructor.getParameterAnnotationTypes();
         if (parameterAnnotationTypes == null || parameterAnnotationTypes.length == 0) {
            parameterAnnotationTypes = (ResolvedType[][])null;
         }

         if (!this.parameterTypes.matches(resolvedParameters, TypePattern.STATIC, parameterAnnotationTypes).alwaysTrue() && !this.parameterTypes.matches(world.resolve(aConstructor.getGenericParameterTypes()), TypePattern.STATIC).alwaysTrue()) {
            return FuzzyBoolean.MAYBE;
         } else if (!this.matchesVarArgs(aConstructor, world)) {
            return FuzzyBoolean.NO;
         } else {
            return !this.throwsPattern.matches(aConstructor.getExceptions(), world) ? FuzzyBoolean.NO : FuzzyBoolean.YES;
         }
      }
   }

   private boolean matchesVarArgs(JoinPointSignature aMethodOrConstructor, World inAWorld) {
      if (this.parameterTypes.size() == 0) {
         return true;
      } else {
         TypePattern lastPattern = this.parameterTypes.get(this.parameterTypes.size() - 1);
         boolean canMatchVarArgsSignature = lastPattern.isStar() || lastPattern.isVarArgs() || lastPattern == TypePattern.ELLIPSIS;
         if (aMethodOrConstructor.isVarargsMethod()) {
            if (!canMatchVarArgsSignature) {
               inAWorld.getLint().cantMatchArrayTypeOnVarargs.signal(aMethodOrConstructor.toString(), this.getSourceLocation());
               return false;
            }
         } else if (lastPattern.isVarArgs()) {
            return false;
         }

         return true;
      }
   }

   private FuzzyBoolean matchesAnnotations(ResolvedMember member, World world) {
      if (member == null) {
         return FuzzyBoolean.NO;
      } else {
         this.annotationPattern.resolve(world);
         if (this.annotationPattern instanceof AnyAnnotationTypePattern) {
            return FuzzyBoolean.YES;
         } else {
            if (member.isAnnotatedElsewhere() && member.getKind() == Member.FIELD) {
               List mungers = member.getDeclaringType().resolve(world).getInterTypeMungers();
               Iterator i$ = mungers.iterator();

               while(i$.hasNext()) {
                  ConcreteTypeMunger typeMunger = (ConcreteTypeMunger)i$.next();
                  if (typeMunger.getMunger() instanceof NewFieldTypeMunger) {
                     ResolvedMember fakerm = typeMunger.getSignature();
                     ResolvedMember ajcMethod = AjcMemberMaker.interFieldInitializer(fakerm, typeMunger.getAspectType());
                     ResolvedMember rmm = this.findMethod(typeMunger.getAspectType(), ajcMethod);
                     if (fakerm.equals(member)) {
                        member = rmm;
                     }
                  }
               }
            }

            return this.annotationPattern.matches(member).alwaysTrue() ? FuzzyBoolean.YES : FuzzyBoolean.NO;
         }
      }
   }

   private ResolvedMember findMethod(ResolvedType aspectType, ResolvedMember ajcMethod) {
      ResolvedMember[] decMethods = aspectType.getDeclaredMethods();

      for(int i = 0; i < decMethods.length; ++i) {
         ResolvedMember member = decMethods[i];
         if (member.equals(ajcMethod)) {
            return member;
         }
      }

      return null;
   }

   public boolean declaringTypeMatchAllowingForCovariance(Member member, UnresolvedType shadowDeclaringType, World world, TypePattern returnTypePattern, ResolvedType sigReturn) {
      ResolvedType onType = shadowDeclaringType.resolve(world);
      if (this.declaringType.matchesStatically(onType) && returnTypePattern.matchesStatically(sigReturn)) {
         return true;
      } else {
         Collection declaringTypes = member.getDeclaringTypes(world);
         boolean checkReturnType = true;
         Iterator i$ = declaringTypes.iterator();

         while(i$.hasNext()) {
            ResolvedType type = (ResolvedType)i$.next();
            if (this.declaringType.matchesStatically(type)) {
               if (!checkReturnType) {
                  return true;
               }

               ResolvedMember rm = type.lookupMethod(member);
               if (rm == null) {
                  rm = type.lookupMethodInITDs(member);
               }

               if (rm != null) {
                  UnresolvedType returnTypeX = rm.getReturnType();
                  ResolvedType returnType = returnTypeX.resolve(world);
                  if (returnTypePattern.matchesStatically(returnType)) {
                     return true;
                  }
               }
            }
         }

         return false;
      }
   }

   public NamePattern getName() {
      return this.name;
   }

   public TypePattern getDeclaringType() {
      return this.declaringType;
   }

   public MemberKind getKind() {
      return this.kind;
   }

   public String toString() {
      StringBuffer buf = new StringBuffer();
      if (this.annotationPattern != AnnotationTypePattern.ANY) {
         buf.append(this.annotationPattern.toString());
         buf.append(' ');
      }

      if (this.modifiers != ModifiersPattern.ANY) {
         buf.append(this.modifiers.toString());
         buf.append(' ');
      }

      if (this.kind == Member.STATIC_INITIALIZATION) {
         buf.append(this.declaringType.toString());
         buf.append(".<clinit>()");
      } else if (this.kind == Member.HANDLER) {
         buf.append("handler(");
         buf.append(this.parameterTypes.get(0));
         buf.append(")");
      } else {
         if (this.kind != Member.CONSTRUCTOR) {
            buf.append(this.returnType.toString());
            buf.append(' ');
         }

         if (this.declaringType != TypePattern.ANY) {
            buf.append(this.declaringType.toString());
            buf.append('.');
         }

         if (this.kind == Member.CONSTRUCTOR) {
            buf.append("new");
         } else {
            buf.append(this.name.toString());
         }

         if (this.kind == Member.METHOD || this.kind == Member.CONSTRUCTOR) {
            buf.append(this.parameterTypes.toString());
         }
      }

      return buf.toString();
   }

   public boolean equals(Object other) {
      if (!(other instanceof SignaturePattern)) {
         return false;
      } else {
         SignaturePattern o = (SignaturePattern)other;
         return o.kind.equals(this.kind) && o.modifiers.equals(this.modifiers) && o.returnType.equals(this.returnType) && o.declaringType.equals(this.declaringType) && o.name.equals(this.name) && o.parameterTypes.equals(this.parameterTypes) && o.throwsPattern.equals(this.throwsPattern) && o.annotationPattern.equals(this.annotationPattern);
      }
   }

   public int hashCode() {
      if (this.hashcode == -1) {
         this.hashcode = 17;
         this.hashcode = 37 * this.hashcode + this.kind.hashCode();
         this.hashcode = 37 * this.hashcode + this.modifiers.hashCode();
         this.hashcode = 37 * this.hashcode + this.returnType.hashCode();
         this.hashcode = 37 * this.hashcode + this.declaringType.hashCode();
         this.hashcode = 37 * this.hashcode + this.name.hashCode();
         this.hashcode = 37 * this.hashcode + this.parameterTypes.hashCode();
         this.hashcode = 37 * this.hashcode + this.throwsPattern.hashCode();
         this.hashcode = 37 * this.hashcode + this.annotationPattern.hashCode();
      }

      return this.hashcode;
   }

   public void write(CompressingDataOutputStream s) throws IOException {
      this.kind.write(s);
      this.modifiers.write(s);
      this.returnType.write(s);
      this.declaringType.write(s);
      this.name.write(s);
      this.parameterTypes.write(s);
      this.throwsPattern.write(s);
      this.annotationPattern.write(s);
      this.writeLocation(s);
   }

   public static SignaturePattern read(VersionedDataInputStream s, ISourceContext context) throws IOException {
      MemberKind kind = MemberKind.read(s);
      ModifiersPattern modifiers = ModifiersPattern.read(s);
      TypePattern returnType = TypePattern.read(s, context);
      TypePattern declaringType = TypePattern.read(s, context);
      NamePattern name = NamePattern.read(s);
      TypePatternList parameterTypes = TypePatternList.read(s, context);
      ThrowsPattern throwsPattern = ThrowsPattern.read(s, context);
      AnnotationTypePattern annotationPattern = AnnotationTypePattern.ANY;
      if (s.getMajorVersion() >= 2) {
         annotationPattern = AnnotationTypePattern.read(s, context);
      }

      SignaturePattern ret = new SignaturePattern(kind, modifiers, returnType, declaringType, name, parameterTypes, throwsPattern, annotationPattern);
      ret.readLocation(context, s);
      return ret;
   }

   public ModifiersPattern getModifiers() {
      return this.modifiers;
   }

   public TypePatternList getParameterTypes() {
      return this.parameterTypes;
   }

   public TypePattern getReturnType() {
      return this.returnType;
   }

   public ThrowsPattern getThrowsPattern() {
      return this.throwsPattern;
   }

   public AnnotationTypePattern getAnnotationPattern() {
      return this.annotationPattern;
   }

   public boolean isStarAnnotation() {
      return this.annotationPattern == AnnotationTypePattern.ANY;
   }

   public Object accept(PatternNodeVisitor visitor, Object data) {
      return visitor.visit(this, data);
   }

   public boolean isExactDeclaringTypePattern() {
      return this.isExactDeclaringTypePattern;
   }

   public boolean isMatchOnAnyName() {
      return this.getName().isAny();
   }

   public List getExactDeclaringTypes() {
      if (this.declaringType instanceof ExactTypePattern) {
         List l = new ArrayList();
         l.add((ExactTypePattern)this.declaringType);
         return l;
      } else {
         return Collections.emptyList();
      }
   }

   public boolean couldEverMatch(ResolvedType type) {
      return this.declaringType.matches(type, TypePattern.STATIC).maybeTrue();
   }

   private class TypePatternVisitor extends AbstractPatternNodeVisitor {
      private IScope scope;
      private Map incorrectTargetKinds = new HashMap();
      private boolean targetsOtherThanTypeAllowed;
      private boolean parameterTargettingAnnotationsAllowed;

      public TypePatternVisitor(IScope scope, boolean targetsOtherThanTypeAllowed, boolean parameterTargettingAnnotationsAllowed) {
         this.scope = scope;
         this.targetsOtherThanTypeAllowed = targetsOtherThanTypeAllowed;
         this.parameterTargettingAnnotationsAllowed = parameterTargettingAnnotationsAllowed;
      }

      public Object visit(WildAnnotationTypePattern node, Object data) {
         node.getTypePattern().accept(this, data);
         return node;
      }

      public Object visit(ExactAnnotationTypePattern node, Object data) {
         ResolvedType resolvedType = node.getAnnotationType().resolve(this.scope.getWorld());
         AnnotationTargetKind[] targetKinds;
         if (!this.targetsOtherThanTypeAllowed) {
            if (!this.targetsOtherThanTypeAllowed && !resolvedType.canAnnotationTargetType()) {
               targetKinds = resolvedType.getAnnotationTargetKinds();
               if (targetKinds == null) {
                  return data;
               }

               if (this.parameterTargettingAnnotationsAllowed) {
                  for(int ix = 0; ix < targetKinds.length; ++ix) {
                     AnnotationTargetKind annotationTargetKind = targetKinds[ix];
                     if (annotationTargetKind.getName().equals("PARAMETER") && node.isForParameterAnnotationMatch()) {
                        return data;
                     }
                  }
               }

               this.incorrectTargetKinds.put(node, targetKinds);
            }
         } else {
            targetKinds = resolvedType.getAnnotationTargetKinds();
            if (targetKinds == null) {
               return data;
            }

            List incorrectTargets = new ArrayList();
            int i = 0;

            while(true) {
               if (i >= targetKinds.length) {
                  if (incorrectTargets.isEmpty()) {
                     return data;
                  }

                  AnnotationTargetKind[] kinds = new AnnotationTargetKind[incorrectTargets.size()];
                  this.incorrectTargetKinds.put(node, incorrectTargets.toArray(kinds));
                  break;
               }

               if (targetKinds[i].getName().equals(SignaturePattern.this.kind.getName()) || targetKinds[i].getName().equals("PARAMETER") && node.isForParameterAnnotationMatch()) {
                  return data;
               }

               incorrectTargets.add(targetKinds[i]);
               ++i;
            }
         }

         return data;
      }

      public Object visit(ExactTypePattern node, Object data) {
         ExactAnnotationTypePattern eatp = new ExactAnnotationTypePattern(node.getExactType().resolve(this.scope.getWorld()), (Map)null);
         eatp.accept(this, data);
         return data;
      }

      public Object visit(AndTypePattern node, Object data) {
         node.getLeft().accept(this, data);
         node.getRight().accept(this, data);
         return node;
      }

      public Object visit(OrTypePattern node, Object data) {
         node.getLeft().accept(this, data);
         node.getRight().accept(this, data);
         return node;
      }

      public Object visit(AnyWithAnnotationTypePattern node, Object data) {
         node.getAnnotationPattern().accept(this, data);
         return node;
      }

      public boolean containedIncorrectTargetKind() {
         return this.incorrectTargetKinds.size() != 0;
      }

      public Map getIncorrectTargetKinds() {
         return this.incorrectTargetKinds;
      }
   }
}
