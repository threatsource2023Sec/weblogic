package com.bea.core.repackaged.aspectj.weaver.patterns;

import com.bea.core.repackaged.aspectj.bridge.IMessage;
import com.bea.core.repackaged.aspectj.bridge.ISourceLocation;
import com.bea.core.repackaged.aspectj.util.FuzzyBoolean;
import com.bea.core.repackaged.aspectj.weaver.BCException;
import com.bea.core.repackaged.aspectj.weaver.CompressingDataOutputStream;
import com.bea.core.repackaged.aspectj.weaver.ISourceContext;
import com.bea.core.repackaged.aspectj.weaver.IntMap;
import com.bea.core.repackaged.aspectj.weaver.ResolvedType;
import com.bea.core.repackaged.aspectj.weaver.Shadow;
import com.bea.core.repackaged.aspectj.weaver.UnresolvedType;
import com.bea.core.repackaged.aspectj.weaver.VersionedDataInputStream;
import com.bea.core.repackaged.aspectj.weaver.WeaverMessages;
import com.bea.core.repackaged.aspectj.weaver.World;
import com.bea.core.repackaged.aspectj.weaver.ast.Literal;
import com.bea.core.repackaged.aspectj.weaver.ast.Test;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class ArgsPointcut extends NameBindingPointcut {
   private static final String ASPECTJ_JP_SIGNATURE_PREFIX = "Lorg/aspectj/lang/JoinPoint";
   private static final String ASPECTJ_SYNTHETIC_SIGNATURE_PREFIX = "Lorg/aspectj/runtime/internal/";
   private TypePatternList arguments;
   private String stringRepresentation;

   public ArgsPointcut(TypePatternList arguments) {
      this.arguments = arguments;
      this.pointcutKind = 4;
      this.stringRepresentation = "args" + arguments.toString() + "";
   }

   public TypePatternList getArguments() {
      return this.arguments;
   }

   public Pointcut parameterizeWith(Map typeVariableMap, World w) {
      ArgsPointcut ret = new ArgsPointcut(this.arguments.parameterizeWith(typeVariableMap, w));
      ret.copyLocationFrom(this);
      return ret;
   }

   public int couldMatchKinds() {
      return Shadow.ALL_SHADOW_KINDS_BITS;
   }

   public FuzzyBoolean fastMatch(FastMatchInfo type) {
      return FuzzyBoolean.MAYBE;
   }

   protected FuzzyBoolean matchInternal(Shadow shadow) {
      ResolvedType[] argumentsToMatchAgainst = this.getArgumentsToMatchAgainst(shadow);
      FuzzyBoolean ret = this.arguments.matches(argumentsToMatchAgainst, TypePattern.DYNAMIC);
      return ret;
   }

   private ResolvedType[] getArgumentsToMatchAgainst(Shadow shadow) {
      if (shadow.isShadowForArrayConstructionJoinpoint()) {
         return shadow.getArgumentTypesForArrayConstructionShadow();
      } else {
         ResolvedType[] argumentsToMatchAgainst = shadow.getIWorld().resolve(shadow.getGenericArgTypes());
         int numExtraArgs;
         if (shadow.getKind() == Shadow.AdviceExecution) {
            numExtraArgs = 0;

            int newArgLength;
            for(newArgLength = 0; newArgLength < argumentsToMatchAgainst.length; ++newArgLength) {
               String argumentSignature = argumentsToMatchAgainst[newArgLength].getSignature();
               if (!argumentSignature.startsWith("Lorg/aspectj/lang/JoinPoint") && !argumentSignature.startsWith("Lorg/aspectj/runtime/internal/")) {
                  numExtraArgs = 0;
               } else {
                  ++numExtraArgs;
               }
            }

            if (numExtraArgs > 0) {
               newArgLength = argumentsToMatchAgainst.length - numExtraArgs;
               ResolvedType[] argsSubset = new ResolvedType[newArgLength];
               System.arraycopy(argumentsToMatchAgainst, 0, argsSubset, 0, newArgLength);
               argumentsToMatchAgainst = argsSubset;
            }
         } else if (shadow.getKind() == Shadow.ConstructorExecution && shadow.getMatchingSignature().getParameterTypes().length < argumentsToMatchAgainst.length) {
            numExtraArgs = shadow.getMatchingSignature().getParameterTypes().length;
            ResolvedType[] argsSubset = new ResolvedType[numExtraArgs];
            System.arraycopy(argumentsToMatchAgainst, 0, argsSubset, 0, numExtraArgs);
            argumentsToMatchAgainst = argsSubset;
         }

         return argumentsToMatchAgainst;
      }
   }

   public List getBindingAnnotationTypePatterns() {
      return Collections.emptyList();
   }

   public List getBindingTypePatterns() {
      List l = new ArrayList();
      TypePattern[] pats = this.arguments.getTypePatterns();

      for(int i = 0; i < pats.length; ++i) {
         if (pats[i] instanceof BindingTypePattern) {
            l.add((BindingTypePattern)pats[i]);
         }
      }

      return l;
   }

   public void write(CompressingDataOutputStream s) throws IOException {
      s.writeByte(4);
      this.arguments.write(s);
      this.writeLocation(s);
   }

   public static Pointcut read(VersionedDataInputStream s, ISourceContext context) throws IOException {
      ArgsPointcut ret = new ArgsPointcut(TypePatternList.read(s, context));
      ret.readLocation(context, s);
      return ret;
   }

   public boolean equals(Object other) {
      if (!(other instanceof ArgsPointcut)) {
         return false;
      } else {
         ArgsPointcut o = (ArgsPointcut)other;
         return o.arguments.equals(this.arguments);
      }
   }

   public int hashCode() {
      return this.arguments.hashCode();
   }

   public void resolveBindings(IScope scope, Bindings bindings) {
      this.arguments.resolveBindings(scope, bindings, true, true);
      if (this.arguments.ellipsisCount > 1) {
         scope.message(IMessage.ERROR, this, "uses more than one .. in args (compiler limitation)");
      }

   }

   public void postRead(ResolvedType enclosingType) {
      this.arguments.postRead(enclosingType);
   }

   public Pointcut concretize1(ResolvedType inAspect, ResolvedType declaringType, IntMap bindings) {
      if (this.isDeclare(bindings.getEnclosingAdvice())) {
         inAspect.getWorld().showMessage(IMessage.ERROR, WeaverMessages.format("argsInDeclare"), bindings.getEnclosingAdvice().getSourceLocation(), (ISourceLocation)null);
         return Pointcut.makeMatchesNothing(Pointcut.CONCRETE);
      } else {
         TypePatternList args = this.arguments.resolveReferences(bindings);
         if (inAspect.crosscuttingMembers != null) {
            inAspect.crosscuttingMembers.exposeTypes(args.getExactTypes());
         }

         Pointcut ret = new ArgsPointcut(args);
         ret.copyLocationFrom(this);
         return ret;
      }
   }

   private Test findResidueNoEllipsis(Shadow shadow, ExposedState state, TypePattern[] patterns) {
      ResolvedType[] argumentsToMatchAgainst = this.getArgumentsToMatchAgainst(shadow);
      int len = argumentsToMatchAgainst.length;
      if (patterns.length != len) {
         return Literal.FALSE;
      } else {
         Test ret = Literal.TRUE;

         for(int i = 0; i < len; ++i) {
            UnresolvedType argType = shadow.getGenericArgTypes()[i];
            TypePattern type = patterns[i];
            ResolvedType argRTX = shadow.getIWorld().resolve(argType, true);
            if (!(type instanceof BindingTypePattern)) {
               if (argRTX.isMissing()) {
                  shadow.getIWorld().getLint().cantFindType.signal(new String[]{WeaverMessages.format("cftArgType", argType.getName())}, shadow.getSourceLocation(), new ISourceLocation[]{this.getSourceLocation()});
               }

               if (type.matchesInstanceof(argRTX).alwaysTrue()) {
                  continue;
               }
            }

            World world = shadow.getIWorld();
            ResolvedType typeToExpose = type.getExactType().resolve(world);
            if (typeToExpose.isParameterizedType()) {
               boolean inDoubt = type.matchesInstanceof(argRTX) == FuzzyBoolean.MAYBE;
               if (inDoubt && world.getLint().uncheckedArgument.isEnabled()) {
                  String uncheckedMatchWith = typeToExpose.getSimpleBaseName();
                  if (argRTX.isParameterizedType() && argRTX.getRawType() == typeToExpose.getRawType()) {
                     uncheckedMatchWith = argRTX.getSimpleName();
                  }

                  if (!this.isUncheckedArgumentWarningSuppressed()) {
                     world.getLint().uncheckedArgument.signal(new String[]{typeToExpose.getSimpleName(), uncheckedMatchWith, typeToExpose.getSimpleBaseName(), shadow.toResolvedString(world)}, this.getSourceLocation(), new ISourceLocation[]{shadow.getSourceLocation()});
                  }
               }
            }

            ret = Test.makeAnd((Test)ret, this.exposeStateForVar(shadow.getArgVar(i), type, state, shadow.getIWorld()));
         }

         return (Test)ret;
      }
   }

   private boolean isUncheckedArgumentWarningSuppressed() {
      return false;
   }

   protected Test findResidueInternal(Shadow shadow, ExposedState state) {
      ResolvedType[] argsToMatch = this.getArgumentsToMatchAgainst(shadow);
      if (this.arguments.matches(argsToMatch, TypePattern.DYNAMIC).alwaysFalse()) {
         return Literal.FALSE;
      } else {
         int ellipsisCount = this.arguments.ellipsisCount;
         if (ellipsisCount == 0) {
            return this.findResidueNoEllipsis(shadow, state, this.arguments.getTypePatterns());
         } else if (ellipsisCount != 1) {
            throw new BCException("unimplemented");
         } else {
            TypePattern[] patternsWithEllipsis = this.arguments.getTypePatterns();
            TypePattern[] patternsWithoutEllipsis = new TypePattern[argsToMatch.length];
            int lenWithEllipsis = patternsWithEllipsis.length;
            int lenWithoutEllipsis = patternsWithoutEllipsis.length;
            int indexWithEllipsis = 0;
            int indexWithoutEllipsis = 0;

            while(true) {
               while(indexWithoutEllipsis < lenWithoutEllipsis) {
                  TypePattern p = patternsWithEllipsis[indexWithEllipsis++];
                  if (p == TypePattern.ELLIPSIS) {
                     for(int newLenWithoutEllipsis = lenWithoutEllipsis - (lenWithEllipsis - indexWithEllipsis); indexWithoutEllipsis < newLenWithoutEllipsis; patternsWithoutEllipsis[indexWithoutEllipsis++] = TypePattern.ANY) {
                     }
                  } else {
                     patternsWithoutEllipsis[indexWithoutEllipsis++] = p;
                  }
               }

               return this.findResidueNoEllipsis(shadow, state, patternsWithoutEllipsis);
            }
         }
      }
   }

   public String toString() {
      return this.stringRepresentation;
   }

   public Object accept(PatternNodeVisitor visitor, Object data) {
      return visitor.visit(this, data);
   }
}
