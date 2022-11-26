package com.bea.core.repackaged.aspectj.weaver.patterns;

import com.bea.core.repackaged.aspectj.bridge.IMessage;
import com.bea.core.repackaged.aspectj.bridge.ISourceLocation;
import com.bea.core.repackaged.aspectj.bridge.Message;
import com.bea.core.repackaged.aspectj.bridge.MessageUtil;
import com.bea.core.repackaged.aspectj.util.FileUtil;
import com.bea.core.repackaged.aspectj.util.FuzzyBoolean;
import com.bea.core.repackaged.aspectj.weaver.BCException;
import com.bea.core.repackaged.aspectj.weaver.BoundedReferenceType;
import com.bea.core.repackaged.aspectj.weaver.CompressingDataOutputStream;
import com.bea.core.repackaged.aspectj.weaver.IHasPosition;
import com.bea.core.repackaged.aspectj.weaver.ISourceContext;
import com.bea.core.repackaged.aspectj.weaver.ReferenceType;
import com.bea.core.repackaged.aspectj.weaver.ResolvedType;
import com.bea.core.repackaged.aspectj.weaver.TypeFactory;
import com.bea.core.repackaged.aspectj.weaver.TypeVariable;
import com.bea.core.repackaged.aspectj.weaver.TypeVariableReference;
import com.bea.core.repackaged.aspectj.weaver.UnresolvedType;
import com.bea.core.repackaged.aspectj.weaver.UnresolvedTypeVariableReferenceType;
import com.bea.core.repackaged.aspectj.weaver.VersionedDataInputStream;
import com.bea.core.repackaged.aspectj.weaver.WeaverMessages;
import com.bea.core.repackaged.aspectj.weaver.World;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

public class WildTypePattern extends TypePattern {
   private static final String GENERIC_WILDCARD_CHARACTER = "?";
   private static final String GENERIC_WILDCARD_SIGNATURE_CHARACTER = "*";
   private NamePattern[] namePatterns;
   private boolean failedResolution;
   int ellipsisCount;
   String[] importedPrefixes;
   String[] knownMatches;
   int dim;
   public static boolean boundscheckingoff = false;
   TypePattern upperBound;
   TypePattern[] additionalInterfaceBounds;
   TypePattern lowerBound;
   private boolean isGeneric;
   private static final byte VERSION = 1;

   WildTypePattern(NamePattern[] namePatterns, boolean includeSubtypes, int dim, boolean isVarArgs, TypePatternList typeParams) {
      super(includeSubtypes, isVarArgs, typeParams);
      this.failedResolution = false;
      this.isGeneric = true;
      this.namePatterns = namePatterns;
      this.dim = dim;
      this.ellipsisCount = 0;

      for(int i = 0; i < namePatterns.length; ++i) {
         if (namePatterns[i] == NamePattern.ELLIPSIS) {
            ++this.ellipsisCount;
         }
      }

      this.setLocation(namePatterns[0].getSourceContext(), namePatterns[0].getStart(), namePatterns[namePatterns.length - 1].getEnd());
   }

   public WildTypePattern(List names, boolean includeSubtypes, int dim) {
      this((NamePattern[])((NamePattern[])names.toArray(new NamePattern[names.size()])), includeSubtypes, dim, false, TypePatternList.EMPTY);
   }

   public WildTypePattern(List names, boolean includeSubtypes, int dim, int endPos) {
      this(names, includeSubtypes, dim);
      this.end = endPos;
   }

   public WildTypePattern(List names, boolean includeSubtypes, int dim, int endPos, boolean isVarArg) {
      this(names, includeSubtypes, dim);
      this.end = endPos;
      this.isVarArgs = isVarArg;
   }

   public WildTypePattern(List names, boolean includeSubtypes, int dim, int endPos, boolean isVarArg, TypePatternList typeParams, TypePattern upperBound, TypePattern[] additionalInterfaceBounds, TypePattern lowerBound) {
      this((NamePattern[])((NamePattern[])names.toArray(new NamePattern[names.size()])), includeSubtypes, dim, isVarArg, typeParams);
      this.end = endPos;
      this.upperBound = upperBound;
      this.lowerBound = lowerBound;
      this.additionalInterfaceBounds = additionalInterfaceBounds;
   }

   public WildTypePattern(List names, boolean includeSubtypes, int dim, int endPos, boolean isVarArg, TypePatternList typeParams) {
      this((NamePattern[])((NamePattern[])names.toArray(new NamePattern[names.size()])), includeSubtypes, dim, isVarArg, typeParams);
      this.end = endPos;
   }

   public NamePattern[] getNamePatterns() {
      return this.namePatterns;
   }

   public TypePattern getUpperBound() {
      return this.upperBound;
   }

   public TypePattern getLowerBound() {
      return this.lowerBound;
   }

   public TypePattern[] getAdditionalIntefaceBounds() {
      return this.additionalInterfaceBounds;
   }

   public void setIsVarArgs(boolean isVarArgs) {
      this.isVarArgs = isVarArgs;
      if (isVarArgs) {
         ++this.dim;
      }

   }

   protected boolean couldEverMatchSameTypesAs(TypePattern other) {
      if (super.couldEverMatchSameTypesAs(other)) {
         return true;
      } else {
         UnresolvedType otherType = other.getExactType();
         if (!ResolvedType.isMissing(otherType) && this.namePatterns.length > 0 && !this.namePatterns[0].matches(otherType.getName())) {
            return false;
         } else {
            if (other instanceof WildTypePattern) {
               WildTypePattern owtp = (WildTypePattern)other;
               String mySimpleName = this.namePatterns[0].maybeGetSimpleName();
               String yourSimpleName = owtp.namePatterns[0].maybeGetSimpleName();
               if (mySimpleName != null && yourSimpleName != null) {
                  return mySimpleName.startsWith(yourSimpleName) || yourSimpleName.startsWith(mySimpleName);
               }
            }

            return true;
         }
      }
   }

   public static char[][] splitNames(String s, boolean convertDollar) {
      List ret = new ArrayList();
      int startIndex = 0;

      while(true) {
         int breakIndex = s.indexOf(46, startIndex);
         if (convertDollar && breakIndex == -1) {
            breakIndex = s.indexOf(36, startIndex);
         }

         if (breakIndex == -1) {
            ret.add(s.substring(startIndex).toCharArray());
            return (char[][])ret.toArray(new char[ret.size()][]);
         }

         char[] name = s.substring(startIndex, breakIndex).toCharArray();
         ret.add(name);
         startIndex = breakIndex + 1;
      }
   }

   protected boolean matchesExactly(ResolvedType type) {
      return this.matchesExactly(type, type);
   }

   protected boolean matchesExactly(ResolvedType type, ResolvedType annotatedType) {
      String targetTypeName = type.getName();
      this.annotationPattern.resolve(type.getWorld());
      return this.matchesExactlyByName(targetTypeName, type.isAnonymous(), type.isNested()) && this.matchesParameters(type, STATIC) && this.matchesBounds(type, STATIC) && this.annotationPattern.matches(annotatedType, type.temporaryAnnotationTypes).alwaysTrue();
   }

   private boolean matchesParameters(ResolvedType aType, TypePattern.MatchKind staticOrDynamic) {
      if (!this.isGeneric && this.typeParameters.size() > 0) {
         return !aType.isParameterizedType() ? false : this.typeParameters.matches(aType.getResolvedTypeParameters(), staticOrDynamic).alwaysTrue();
      } else {
         return true;
      }
   }

   private boolean matchesBounds(ResolvedType aType, TypePattern.MatchKind staticOrDynamic) {
      if (!(aType instanceof BoundedReferenceType)) {
         return true;
      } else {
         BoundedReferenceType boundedRT = (BoundedReferenceType)aType;
         if (this.upperBound == null && boundedRT.getUpperBound() != null && !boundedRT.getUpperBound().getName().equals(UnresolvedType.OBJECT.getName())) {
            return false;
         } else if (this.lowerBound == null && boundedRT.getLowerBound() != null) {
            return false;
         } else if (this.upperBound != null) {
            if (aType.isGenericWildcard() && boundedRT.isSuper()) {
               return false;
            } else {
               return boundedRT.getUpperBound() == null ? false : this.upperBound.matches((ResolvedType)boundedRT.getUpperBound(), staticOrDynamic).alwaysTrue();
            }
         } else if (this.lowerBound != null) {
            return boundedRT.isGenericWildcard() && boundedRT.isSuper() ? this.lowerBound.matches((ResolvedType)boundedRT.getLowerBound(), staticOrDynamic).alwaysTrue() : false;
         } else {
            return true;
         }
      }
   }

   public int getDimensions() {
      return this.dim;
   }

   public boolean isArray() {
      return this.dim > 1;
   }

   private boolean matchesExactlyByName(String targetTypeName, boolean isAnonymous, boolean isNested) {
      if (targetTypeName.indexOf(60) != -1) {
         targetTypeName = targetTypeName.substring(0, targetTypeName.indexOf(60));
      }

      if (targetTypeName.startsWith("?")) {
         targetTypeName = "?";
      }

      if (this.knownMatches == null && this.importedPrefixes == null) {
         return this.innerMatchesExactly(targetTypeName, isAnonymous, isNested);
      } else {
         int i;
         int len;
         if (this.isNamePatternStar()) {
            i = 0;
            if (this.dim > 0) {
               while((len = targetTypeName.indexOf(91)) != -1) {
                  ++i;
                  targetTypeName = targetTypeName.substring(len + 1);
               }

               if (i == this.dim) {
                  return true;
               }

               return false;
            }
         }

         String prefix;
         if (this.namePatterns.length == 1) {
            if (isAnonymous) {
               return false;
            }

            i = 0;

            for(len = this.knownMatches.length; i < len; ++i) {
               if (this.knownMatches[i].equals(targetTypeName)) {
                  return true;
               }
            }
         } else {
            i = 0;

            for(len = this.knownMatches.length; i < len; ++i) {
               prefix = this.knownMatches[i];
               if (targetTypeName.startsWith(prefix) && targetTypeName.length() > prefix.length() && targetTypeName.charAt(prefix.length()) == '$') {
                  int pos = this.lastIndexOfDotOrDollar(prefix);
                  if (this.innerMatchesExactly(targetTypeName.substring(pos + 1), isAnonymous, isNested)) {
                     return true;
                  }
               }
            }
         }

         i = 0;

         for(len = this.importedPrefixes.length; i < len; ++i) {
            prefix = this.importedPrefixes[i];
            if (targetTypeName.startsWith(prefix) && this.innerMatchesExactly(targetTypeName.substring(prefix.length()), isAnonymous, isNested)) {
               return true;
            }
         }

         return this.innerMatchesExactly(targetTypeName, isAnonymous, isNested);
      }
   }

   private int lastIndexOfDotOrDollar(String string) {
      for(int pos = string.length() - 1; pos > -1; --pos) {
         char ch = string.charAt(pos);
         if (ch == '.' || ch == '$') {
            return pos;
         }
      }

      return -1;
   }

   private boolean innerMatchesExactly(String s, boolean isAnonymous, boolean convertDollar) {
      List ret = new ArrayList();
      int startIndex = 0;

      while(true) {
         int namesLength = s.indexOf(46, startIndex);
         if (convertDollar && namesLength == -1) {
            namesLength = s.indexOf(36, startIndex);
         }

         if (namesLength == -1) {
            ret.add(s.substring(startIndex).toCharArray());
            namesLength = ret.size();
            int patternsLength = this.namePatterns.length;
            int namesIndex = 0;
            int patternsIndex = 0;
            if (!this.namePatterns[patternsLength - 1].isAny() && isAnonymous) {
               return false;
            }

            if (this.ellipsisCount == 0) {
               if (namesLength != patternsLength) {
                  return false;
               }

               do {
                  if (patternsIndex >= patternsLength) {
                     return true;
                  }
               } while(this.namePatterns[patternsIndex++].matches((char[])ret.get(namesIndex++)));

               return false;
            }

            if (this.ellipsisCount == 1) {
               if (namesLength < patternsLength - 1) {
                  return false;
               }

               while(patternsIndex < patternsLength) {
                  NamePattern p = this.namePatterns[patternsIndex++];
                  if (p == NamePattern.ELLIPSIS) {
                     namesIndex = namesLength - (patternsLength - patternsIndex);
                  } else if (!p.matches((char[])ret.get(namesIndex++))) {
                     return false;
                  }
               }

               return true;
            }

            boolean b = outOfStar(this.namePatterns, (char[][])ret.toArray(new char[ret.size()][]), 0, 0, patternsLength - this.ellipsisCount, namesLength, this.ellipsisCount);
            return b;
         }

         char[] name = s.substring(startIndex, namesLength).toCharArray();
         ret.add(name);
         startIndex = namesLength + 1;
      }
   }

   private static boolean outOfStar(NamePattern[] pattern, char[][] target, int pi, int ti, int pLeft, int tLeft, int starsLeft) {
      if (pLeft > tLeft) {
         return false;
      } else {
         while(tLeft != 0) {
            if (pLeft == 0) {
               return starsLeft > 0;
            }

            if (pattern[pi] == NamePattern.ELLIPSIS) {
               return inStar(pattern, target, pi + 1, ti, pLeft, tLeft, starsLeft - 1);
            }

            if (!pattern[pi].matches(target[ti])) {
               return false;
            }

            ++pi;
            ++ti;
            --pLeft;
            --tLeft;
         }

         return true;
      }
   }

   private static boolean inStar(NamePattern[] pattern, char[][] target, int pi, int ti, int pLeft, int tLeft, int starsLeft) {
      NamePattern patternChar;
      for(patternChar = pattern[pi]; patternChar == NamePattern.ELLIPSIS; patternChar = pattern[pi]) {
         --starsLeft;
         ++pi;
      }

      while(pLeft <= tLeft) {
         if (patternChar.matches(target[ti]) && outOfStar(pattern, target, pi + 1, ti + 1, pLeft - 1, tLeft - 1, starsLeft)) {
            return true;
         }

         ++ti;
         --tLeft;
      }

      return false;
   }

   public FuzzyBoolean matchesInstanceof(ResolvedType type) {
      if (this.maybeGetSimpleName() != null) {
         return FuzzyBoolean.NO;
      } else {
         type.getWorld().getMessageHandler().handleMessage(new Message("can't do instanceof matching on patterns with wildcards", IMessage.ERROR, (Throwable)null, this.getSourceLocation()));
         return FuzzyBoolean.NO;
      }
   }

   public NamePattern extractName() {
      if (!this.isIncludeSubtypes() && !this.isVarArgs() && !this.isArray() && this.typeParameters.size() <= 0) {
         int len = this.namePatterns.length;
         if (len == 1 && !this.annotationPattern.isAny()) {
            return null;
         } else {
            NamePattern ret = this.namePatterns[len - 1];
            NamePattern[] newNames = new NamePattern[len - 1];
            System.arraycopy(this.namePatterns, 0, newNames, 0, len - 1);
            this.namePatterns = newNames;
            return ret;
         }
      } else {
         return null;
      }
   }

   public boolean maybeExtractName(String string) {
      int len = this.namePatterns.length;
      NamePattern ret = this.namePatterns[len - 1];
      String simple = ret.maybeGetSimpleName();
      if (simple != null && simple.equals(string)) {
         this.extractName();
         return true;
      } else {
         return false;
      }
   }

   public String maybeGetSimpleName() {
      return this.namePatterns.length == 1 ? this.namePatterns[0].maybeGetSimpleName() : null;
   }

   public String maybeGetCleanName() {
      if (this.namePatterns.length == 0) {
         throw new RuntimeException("bad name: " + this.namePatterns);
      } else {
         StringBuffer buf = new StringBuffer();
         int i = 0;

         for(int len = this.namePatterns.length; i < len; ++i) {
            NamePattern p = this.namePatterns[i];
            String simpleName = p.maybeGetSimpleName();
            if (simpleName == null) {
               return null;
            }

            if (i > 0) {
               buf.append(".");
            }

            buf.append(simpleName);
         }

         return buf.toString();
      }
   }

   public TypePattern parameterizeWith(Map typeVariableMap, World w) {
      NamePattern[] newNamePatterns = new NamePattern[this.namePatterns.length];

      for(int i = 0; i < this.namePatterns.length; ++i) {
         newNamePatterns[i] = this.namePatterns[i];
      }

      if (newNamePatterns.length == 1) {
         String simpleName = newNamePatterns[0].maybeGetSimpleName();
         if (simpleName != null && typeVariableMap.containsKey(simpleName)) {
            String newName = ((ReferenceType)typeVariableMap.get(simpleName)).getName().replace('$', '.');
            StringTokenizer strTok = new StringTokenizer(newName, ".");
            newNamePatterns = new NamePattern[strTok.countTokens()];

            for(int index = 0; strTok.hasMoreTokens(); newNamePatterns[index++] = new NamePattern(strTok.nextToken())) {
            }
         }
      }

      WildTypePattern ret = new WildTypePattern(newNamePatterns, this.includeSubtypes, this.dim, this.isVarArgs, this.typeParameters.parameterizeWith(typeVariableMap, w));
      ret.annotationPattern = this.annotationPattern.parameterizeWith(typeVariableMap, w);
      if (this.additionalInterfaceBounds == null) {
         ret.additionalInterfaceBounds = null;
      } else {
         ret.additionalInterfaceBounds = new TypePattern[this.additionalInterfaceBounds.length];

         for(int i = 0; i < this.additionalInterfaceBounds.length; ++i) {
            ret.additionalInterfaceBounds[i] = this.additionalInterfaceBounds[i].parameterizeWith(typeVariableMap, w);
         }
      }

      ret.upperBound = this.upperBound != null ? this.upperBound.parameterizeWith(typeVariableMap, w) : null;
      ret.lowerBound = this.lowerBound != null ? this.lowerBound.parameterizeWith(typeVariableMap, w) : null;
      ret.isGeneric = this.isGeneric;
      ret.knownMatches = this.knownMatches;
      ret.importedPrefixes = this.importedPrefixes;
      ret.copyLocationFrom(this);
      return ret;
   }

   public TypePattern resolveBindings(IScope scope, Bindings bindings, boolean allowBinding, boolean requireExactType) {
      TypePattern anyPattern;
      if (this.isNamePatternStar()) {
         anyPattern = this.maybeResolveToAnyPattern(scope, bindings, allowBinding, requireExactType);
         if (anyPattern != null) {
            if (requireExactType) {
               scope.getWorld().getMessageHandler().handleMessage(MessageUtil.error(WeaverMessages.format("wildcardTypePatternNotAllowed"), this.getSourceLocation()));
               return NO;
            }

            return anyPattern;
         }
      }

      anyPattern = this.maybeResolveToBindingTypePattern(scope, bindings, allowBinding, requireExactType);
      if (anyPattern != null) {
         return anyPattern;
      } else {
         this.annotationPattern = this.annotationPattern.resolveBindings(scope, bindings, allowBinding);
         if (this.typeParameters != null && this.typeParameters.size() > 0) {
            this.typeParameters.resolveBindings(scope, bindings, allowBinding, requireExactType);
            this.isGeneric = false;
         }

         if (this.upperBound != null) {
            this.upperBound = this.upperBound.resolveBindings(scope, bindings, allowBinding, requireExactType);
         }

         if (this.lowerBound != null) {
            this.lowerBound = this.lowerBound.resolveBindings(scope, bindings, allowBinding, requireExactType);
         }

         String fullyQualifiedName = this.maybeGetCleanName();
         if (fullyQualifiedName != null) {
            return this.resolveBindingsFromFullyQualifiedTypeName(fullyQualifiedName, scope, bindings, allowBinding, requireExactType);
         } else if (requireExactType) {
            scope.getWorld().getMessageHandler().handleMessage(MessageUtil.error(WeaverMessages.format("wildcardTypePatternNotAllowed"), this.getSourceLocation()));
            return NO;
         } else {
            this.importedPrefixes = scope.getImportedPrefixes();
            this.knownMatches = this.preMatch(scope.getImportedNames());
            return this;
         }
      }
   }

   private TypePattern maybeResolveToAnyPattern(IScope scope, Bindings bindings, boolean allowBinding, boolean requireExactType) {
      if (this.annotationPattern == AnnotationTypePattern.ANY) {
         if (this.dim == 0 && !this.isVarArgs && this.upperBound == null && this.lowerBound == null && (this.additionalInterfaceBounds == null || this.additionalInterfaceBounds.length == 0)) {
            return TypePattern.ANY;
         }
      } else if (!this.isVarArgs) {
         this.annotationPattern = this.annotationPattern.resolveBindings(scope, bindings, allowBinding);
         AnyWithAnnotationTypePattern ret = new AnyWithAnnotationTypePattern(this.annotationPattern);
         ret.setLocation(this.sourceContext, this.start, this.end);
         return ret;
      }

      return null;
   }

   private TypePattern maybeResolveToBindingTypePattern(IScope scope, Bindings bindings, boolean allowBinding, boolean requireExactType) {
      String simpleName = this.maybeGetSimpleName();
      if (simpleName != null) {
         FormalBinding formalBinding = scope.lookupFormal(simpleName);
         if (formalBinding != null) {
            if (bindings == null) {
               scope.message(IMessage.ERROR, this, "negation doesn't allow binding");
               return this;
            }

            if (!allowBinding) {
               scope.message(IMessage.ERROR, this, "name binding only allowed in target, this, and args pcds");
               return this;
            }

            BindingTypePattern binding = new BindingTypePattern(formalBinding, this.isVarArgs);
            binding.copyLocationFrom(this);
            bindings.register(binding, scope);
            return binding;
         }
      }

      return null;
   }

   private TypePattern resolveBindingsFromFullyQualifiedTypeName(String fullyQualifiedName, IScope scope, Bindings bindings, boolean allowBinding, boolean requireExactType) {
      ResolvedType resolvedTypeInTheWorld = null;
      resolvedTypeInTheWorld = this.lookupTypeInWorldIncludingPrefixes(scope.getWorld(), fullyQualifiedName, scope.getImportedPrefixes());
      Object type;
      if (resolvedTypeInTheWorld.isGenericWildcard()) {
         type = resolvedTypeInTheWorld;
      } else {
         type = this.lookupTypeInScope(scope, fullyQualifiedName, this);
      }

      return type instanceof ResolvedType && ((ResolvedType)type).isMissing() ? this.resolveBindingsForMissingType(resolvedTypeInTheWorld, fullyQualifiedName, scope, bindings, allowBinding, requireExactType) : this.resolveBindingsForExactType(scope, (UnresolvedType)type, fullyQualifiedName, requireExactType);
   }

   private UnresolvedType lookupTypeInScope(IScope scope, String typeName, IHasPosition location) {
      UnresolvedType type;
      int lastDot;
      for(type = null; ResolvedType.isMissing(type = scope.lookupType(typeName, location)); typeName = typeName.substring(0, lastDot) + '$' + typeName.substring(lastDot + 1)) {
         lastDot = typeName.lastIndexOf(46);
         if (lastDot == -1) {
            break;
         }
      }

      return type;
   }

   private ResolvedType lookupTypeInWorldIncludingPrefixes(World world, String typeName, String[] prefixes) {
      ResolvedType ret = this.lookupTypeInWorld(world, typeName);
      if (!ret.isMissing()) {
         return ret;
      } else {
         ResolvedType retWithPrefix = ret;

         for(int counter = 0; retWithPrefix.isMissing() && counter < prefixes.length; ++counter) {
            retWithPrefix = this.lookupTypeInWorld(world, prefixes[counter] + typeName);
         }

         return !retWithPrefix.isMissing() ? retWithPrefix : ret;
      }
   }

   private ResolvedType lookupTypeInWorld(World world, String typeName) {
      UnresolvedType ut = UnresolvedType.forName(typeName);

      ResolvedType ret;
      for(ret = world.resolve(ut, true); ret.isMissing(); ret = world.resolve(UnresolvedType.forName(typeName), true)) {
         int lastDot = typeName.lastIndexOf(46);
         if (lastDot == -1) {
            break;
         }

         typeName = typeName.substring(0, lastDot) + '$' + typeName.substring(lastDot + 1);
      }

      return ret;
   }

   private TypePattern resolveBindingsForExactType(IScope scope, UnresolvedType aType, String fullyQualifiedName, boolean requireExactType) {
      TypePattern ret = null;
      if (aType.isTypeVariableReference()) {
         ret = this.resolveBindingsForTypeVariable(scope, (UnresolvedTypeVariableReferenceType)aType);
      } else if (this.typeParameters.size() > 0) {
         ret = this.resolveParameterizedType(scope, aType, requireExactType);
      } else if (this.upperBound == null && this.lowerBound == null) {
         if (this.dim != 0) {
            aType = UnresolvedType.makeArray(aType, this.dim);
         }

         ret = new ExactTypePattern(aType, this.includeSubtypes, this.isVarArgs);
      } else {
         ret = this.resolveGenericWildcard(scope, aType);
      }

      ((TypePattern)ret).setAnnotationTypePattern(this.annotationPattern);
      ((TypePattern)ret).copyLocationFrom(this);
      return (TypePattern)ret;
   }

   private TypePattern resolveGenericWildcard(IScope scope, UnresolvedType aType) {
      if (!aType.getSignature().equals("*")) {
         throw new IllegalStateException("Can only have bounds for a generic wildcard");
      } else {
         boolean canBeExact = true;
         if (this.upperBound != null && ResolvedType.isMissing(this.upperBound.getExactType())) {
            canBeExact = false;
         }

         if (this.lowerBound != null && ResolvedType.isMissing(this.lowerBound.getExactType())) {
            canBeExact = false;
         }

         if (canBeExact) {
            ResolvedType type = null;
            ReferenceType upper;
            if (this.upperBound != null) {
               if (this.upperBound.isIncludeSubtypes()) {
                  canBeExact = false;
               } else {
                  upper = (ReferenceType)this.upperBound.getExactType().resolve(scope.getWorld());
                  type = new BoundedReferenceType(upper, true, scope.getWorld());
               }
            } else if (this.lowerBound.isIncludeSubtypes()) {
               canBeExact = false;
            } else {
               upper = (ReferenceType)this.lowerBound.getExactType().resolve(scope.getWorld());
               type = new BoundedReferenceType(upper, false, scope.getWorld());
            }

            if (canBeExact) {
               return new ExactTypePattern(type, this.includeSubtypes, this.isVarArgs);
            }
         }

         this.importedPrefixes = scope.getImportedPrefixes();
         this.knownMatches = this.preMatch(scope.getImportedNames());
         return this;
      }
   }

   private TypePattern resolveParameterizedType(IScope scope, UnresolvedType aType, boolean requireExactType) {
      ResolvedType rt = aType.resolve(scope.getWorld());
      if (!this.verifyTypeParameters((ResolvedType)rt, scope, requireExactType)) {
         return TypePattern.NO;
      } else if (!this.typeParameters.areAllExactWithNoSubtypesAllowed()) {
         this.importedPrefixes = scope.getImportedPrefixes();
         this.knownMatches = this.preMatch(scope.getImportedNames());
         return this;
      } else {
         TypePattern[] typePats = this.typeParameters.getTypePatterns();
         UnresolvedType[] typeParameterTypes = new UnresolvedType[typePats.length];

         for(int i = 0; i < typeParameterTypes.length; ++i) {
            typeParameterTypes[i] = ((ExactTypePattern)typePats[i]).getExactType();
         }

         if (((ResolvedType)rt).isParameterizedType()) {
            rt = ((ResolvedType)rt).getGenericType();
         }

         ResolvedType type = TypeFactory.createParameterizedType((ResolvedType)rt, typeParameterTypes, scope.getWorld());
         if (this.isGeneric) {
            type = ((ResolvedType)type).getGenericType();
         }

         if (this.dim != 0) {
            type = ResolvedType.makeArray((ResolvedType)type, this.dim);
         }

         return new ExactTypePattern((UnresolvedType)type, this.includeSubtypes, this.isVarArgs);
      }
   }

   private TypePattern resolveBindingsForMissingType(ResolvedType typeFoundInWholeWorldSearch, String nameWeLookedFor, IScope scope, Bindings bindings, boolean allowBinding, boolean requireExactType) {
      if (requireExactType) {
         if (!allowBinding) {
            scope.getWorld().getMessageHandler().handleMessage(MessageUtil.error(WeaverMessages.format("cantBindType", nameWeLookedFor), this.getSourceLocation()));
         } else if (scope.getWorld().getLint().invalidAbsoluteTypeName.isEnabled()) {
            scope.getWorld().getLint().invalidAbsoluteTypeName.signal(nameWeLookedFor, this.getSourceLocation());
         }

         return NO;
      } else {
         if (scope.getWorld().getLint().invalidAbsoluteTypeName.isEnabled() && typeFoundInWholeWorldSearch.isMissing()) {
            scope.getWorld().getLint().invalidAbsoluteTypeName.signal(nameWeLookedFor, this.getSourceLocation());
            this.failedResolution = true;
         }

         this.importedPrefixes = scope.getImportedPrefixes();
         this.knownMatches = this.preMatch(scope.getImportedNames());
         return this;
      }
   }

   private TypePattern resolveBindingsForTypeVariable(IScope scope, UnresolvedTypeVariableReferenceType tvrType) {
      Bindings emptyBindings = new Bindings(0);
      if (this.upperBound != null) {
         this.upperBound = this.upperBound.resolveBindings(scope, emptyBindings, false, false);
      }

      if (this.lowerBound != null) {
         this.lowerBound = this.lowerBound.resolveBindings(scope, emptyBindings, false, false);
      }

      int i;
      if (this.additionalInterfaceBounds != null) {
         TypePattern[] resolvedIfBounds = new TypePattern[this.additionalInterfaceBounds.length];

         for(i = 0; i < resolvedIfBounds.length; ++i) {
            resolvedIfBounds[i] = this.additionalInterfaceBounds[i].resolveBindings(scope, emptyBindings, false, false);
         }

         this.additionalInterfaceBounds = resolvedIfBounds;
      }

      if (this.upperBound == null && this.lowerBound == null && this.additionalInterfaceBounds == null) {
         ResolvedType rType = tvrType.resolve(scope.getWorld());
         if (this.dim != 0) {
            rType = ResolvedType.makeArray(rType, this.dim);
         }

         return new ExactTypePattern(rType, this.includeSubtypes, this.isVarArgs);
      } else {
         boolean canCreateExactTypePattern = true;
         if (this.upperBound != null && ResolvedType.isMissing(this.upperBound.getExactType())) {
            canCreateExactTypePattern = false;
         }

         if (this.lowerBound != null && ResolvedType.isMissing(this.lowerBound.getExactType())) {
            canCreateExactTypePattern = false;
         }

         if (this.additionalInterfaceBounds != null) {
            for(i = 0; i < this.additionalInterfaceBounds.length; ++i) {
               if (ResolvedType.isMissing(this.additionalInterfaceBounds[i].getExactType())) {
                  canCreateExactTypePattern = false;
               }
            }
         }

         if (canCreateExactTypePattern) {
            TypeVariable tv = tvrType.getTypeVariable();
            if (this.upperBound != null) {
               tv.setSuperclass(this.upperBound.getExactType());
            }

            if (this.additionalInterfaceBounds != null) {
               UnresolvedType[] ifBounds = new UnresolvedType[this.additionalInterfaceBounds.length];

               for(int i = 0; i < ifBounds.length; ++i) {
                  ifBounds[i] = this.additionalInterfaceBounds[i].getExactType();
               }

               tv.setAdditionalInterfaceBounds(ifBounds);
            }

            ResolvedType rType = tvrType.resolve(scope.getWorld());
            if (this.dim != 0) {
               rType = ResolvedType.makeArray(rType, this.dim);
            }

            return new ExactTypePattern(rType, this.includeSubtypes, this.isVarArgs);
         } else {
            return this;
         }
      }
   }

   private boolean verifyTypeParameters(ResolvedType baseType, IScope scope, boolean requireExactType) {
      ResolvedType genericType = baseType.getGenericType();
      if (genericType == null) {
         scope.message(MessageUtil.warn(WeaverMessages.format("notAGenericType", baseType.getName()), this.getSourceLocation()));
         return false;
      } else {
         int minRequiredTypeParameters = this.typeParameters.size();
         boolean foundEllipsis = false;
         TypePattern[] typeParamPatterns = this.typeParameters.getTypePatterns();

         for(int i = 0; i < typeParamPatterns.length; ++i) {
            if (typeParamPatterns[i] instanceof WildTypePattern) {
               WildTypePattern wtp = (WildTypePattern)typeParamPatterns[i];
               if (wtp.ellipsisCount > 0) {
                  foundEllipsis = true;
                  --minRequiredTypeParameters;
               }
            }
         }

         TypeVariable[] tvs = genericType.getTypeVariables();
         if (tvs.length < minRequiredTypeParameters || !foundEllipsis && minRequiredTypeParameters != tvs.length) {
            String msg = WeaverMessages.format("incorrectNumberOfTypeArguments", genericType.getName(), new Integer(tvs.length));
            if (requireExactType) {
               scope.message(MessageUtil.error(msg, this.getSourceLocation()));
            } else {
               scope.message(MessageUtil.warn(msg, this.getSourceLocation()));
            }

            return false;
         } else {
            if (!boundscheckingoff) {
               VerifyBoundsForTypePattern verification = new VerifyBoundsForTypePattern(scope, genericType, requireExactType, this.typeParameters, this.getSourceLocation());
               scope.getWorld().getCrosscuttingMembersSet().recordNecessaryCheck(verification);
            }

            return true;
         }
      }
   }

   public boolean isStar() {
      boolean annPatternStar = this.annotationPattern == AnnotationTypePattern.ANY;
      return this.isNamePatternStar() && annPatternStar && this.dim == 0;
   }

   private boolean isNamePatternStar() {
      return this.namePatterns.length == 1 && this.namePatterns[0].isAny();
   }

   private String[] preMatch(String[] possibleMatches) {
      List ret = new ArrayList();
      int i = 0;

      for(int len = possibleMatches.length; i < len; ++i) {
         char[][] names = splitNames(possibleMatches[i], true);
         if (this.namePatterns[0].matches(names[names.length - 1])) {
            ret.add(possibleMatches[i]);
         } else if (possibleMatches[i].indexOf("$") != -1) {
            names = splitNames(possibleMatches[i], false);
            if (this.namePatterns[0].matches(names[names.length - 1])) {
               ret.add(possibleMatches[i]);
            }
         }
      }

      return (String[])ret.toArray(new String[ret.size()]);
   }

   public String toString() {
      StringBuffer buf = new StringBuffer();
      if (this.annotationPattern != AnnotationTypePattern.ANY) {
         buf.append('(');
         buf.append(this.annotationPattern.toString());
         buf.append(' ');
      }

      int i = 0;

      for(int len = this.namePatterns.length; i < len; ++i) {
         NamePattern name = this.namePatterns[i];
         if (name == null) {
            buf.append(".");
         } else {
            if (i > 0) {
               buf.append(".");
            }

            buf.append(name.toString());
         }
      }

      if (this.upperBound != null) {
         buf.append(" extends ");
         buf.append(this.upperBound.toString());
      }

      if (this.lowerBound != null) {
         buf.append(" super ");
         buf.append(this.lowerBound.toString());
      }

      if (this.typeParameters != null && this.typeParameters.size() != 0) {
         buf.append("<");
         buf.append(this.typeParameters.toString());
         buf.append(">");
      }

      if (this.includeSubtypes) {
         buf.append('+');
      }

      if (this.isVarArgs) {
         buf.append("...");
      }

      if (this.annotationPattern != AnnotationTypePattern.ANY) {
         buf.append(')');
      }

      return buf.toString();
   }

   public boolean equals(Object other) {
      if (!(other instanceof WildTypePattern)) {
         return false;
      } else {
         WildTypePattern o = (WildTypePattern)other;
         int len = o.namePatterns.length;
         if (len != this.namePatterns.length) {
            return false;
         } else if (this.includeSubtypes != o.includeSubtypes) {
            return false;
         } else if (this.dim != o.dim) {
            return false;
         } else if (this.isVarArgs != o.isVarArgs) {
            return false;
         } else {
            if (this.upperBound != null) {
               if (o.upperBound == null) {
                  return false;
               }

               if (!this.upperBound.equals(o.upperBound)) {
                  return false;
               }
            } else if (o.upperBound != null) {
               return false;
            }

            if (this.lowerBound != null) {
               if (o.lowerBound == null) {
                  return false;
               }

               if (!this.lowerBound.equals(o.lowerBound)) {
                  return false;
               }
            } else if (o.lowerBound != null) {
               return false;
            }

            if (!this.typeParameters.equals(o.typeParameters)) {
               return false;
            } else {
               for(int i = 0; i < len; ++i) {
                  if (!o.namePatterns[i].equals(this.namePatterns[i])) {
                     return false;
                  }
               }

               return o.annotationPattern.equals(this.annotationPattern);
            }
         }
      }
   }

   public int hashCode() {
      int result = 17;
      int i = 0;

      for(int len = this.namePatterns.length; i < len; ++i) {
         result = 37 * result + this.namePatterns[i].hashCode();
      }

      result = 37 * result + this.annotationPattern.hashCode();
      if (this.upperBound != null) {
         result = 37 * result + this.upperBound.hashCode();
      }

      if (this.lowerBound != null) {
         result = 37 * result + this.lowerBound.hashCode();
      }

      return result;
   }

   public void write(CompressingDataOutputStream s) throws IOException {
      s.writeByte(1);
      s.writeByte(1);
      s.writeShort(this.namePatterns.length);

      int i;
      for(i = 0; i < this.namePatterns.length; ++i) {
         this.namePatterns[i].write(s);
      }

      s.writeBoolean(this.includeSubtypes);
      s.writeInt(this.dim);
      s.writeBoolean(this.isVarArgs);
      this.typeParameters.write(s);
      FileUtil.writeStringArray(this.knownMatches, s);
      FileUtil.writeStringArray(this.importedPrefixes, s);
      this.writeLocation(s);
      this.annotationPattern.write(s);
      s.writeBoolean(this.isGeneric);
      s.writeBoolean(this.upperBound != null);
      if (this.upperBound != null) {
         this.upperBound.write(s);
      }

      s.writeBoolean(this.lowerBound != null);
      if (this.lowerBound != null) {
         this.lowerBound.write(s);
      }

      s.writeInt(this.additionalInterfaceBounds == null ? 0 : this.additionalInterfaceBounds.length);
      if (this.additionalInterfaceBounds != null) {
         for(i = 0; i < this.additionalInterfaceBounds.length; ++i) {
            this.additionalInterfaceBounds[i].write(s);
         }
      }

   }

   public static TypePattern read(VersionedDataInputStream s, ISourceContext context) throws IOException {
      return s.getMajorVersion() >= 2 ? readTypePattern150(s, context) : readTypePatternOldStyle(s, context);
   }

   public static TypePattern readTypePattern150(VersionedDataInputStream s, ISourceContext context) throws IOException {
      byte version = s.readByte();
      if (version > 1) {
         throw new BCException("WildTypePattern was written by a more recent version of AspectJ, cannot read");
      } else {
         int len = s.readShort();
         NamePattern[] namePatterns = new NamePattern[len];

         for(int i = 0; i < len; ++i) {
            namePatterns[i] = NamePattern.read(s);
         }

         boolean includeSubtypes = s.readBoolean();
         int dim = s.readInt();
         boolean varArg = s.readBoolean();
         TypePatternList typeParams = TypePatternList.read(s, context);
         WildTypePattern ret = new WildTypePattern(namePatterns, includeSubtypes, dim, varArg, typeParams);
         ret.knownMatches = FileUtil.readStringArray(s);
         ret.importedPrefixes = FileUtil.readStringArray(s);
         ret.readLocation(context, s);
         ret.setAnnotationTypePattern(AnnotationTypePattern.read(s, context));
         ret.isGeneric = s.readBoolean();
         if (s.readBoolean()) {
            ret.upperBound = TypePattern.read(s, context);
         }

         if (s.readBoolean()) {
            ret.lowerBound = TypePattern.read(s, context);
         }

         int numIfBounds = s.readInt();
         if (numIfBounds > 0) {
            ret.additionalInterfaceBounds = new TypePattern[numIfBounds];

            for(int i = 0; i < numIfBounds; ++i) {
               ret.additionalInterfaceBounds[i] = TypePattern.read(s, context);
            }
         }

         return ret;
      }
   }

   public static TypePattern readTypePatternOldStyle(VersionedDataInputStream s, ISourceContext context) throws IOException {
      int len = s.readShort();
      NamePattern[] namePatterns = new NamePattern[len];

      for(int i = 0; i < len; ++i) {
         namePatterns[i] = NamePattern.read(s);
      }

      boolean includeSubtypes = s.readBoolean();
      int dim = s.readInt();
      WildTypePattern ret = new WildTypePattern(namePatterns, includeSubtypes, dim, false, (TypePatternList)null);
      ret.knownMatches = FileUtil.readStringArray(s);
      ret.importedPrefixes = FileUtil.readStringArray(s);
      ret.readLocation(context, s);
      return ret;
   }

   public Object accept(PatternNodeVisitor visitor, Object data) {
      return visitor.visit(this, data);
   }

   public boolean hasFailedResolution() {
      return this.failedResolution;
   }

   static class VerifyBoundsForTypePattern implements IVerificationRequired {
      private final IScope scope;
      private final ResolvedType genericType;
      private final boolean requireExactType;
      private TypePatternList typeParameters;
      private final ISourceLocation sLoc;

      public VerifyBoundsForTypePattern(IScope scope, ResolvedType genericType, boolean requireExactType, TypePatternList typeParameters, ISourceLocation sLoc) {
         this.typeParameters = TypePatternList.EMPTY;
         this.scope = scope;
         this.genericType = genericType;
         this.requireExactType = requireExactType;
         this.typeParameters = typeParameters;
         this.sLoc = sLoc;
      }

      public void verify() {
         TypeVariable[] tvs = this.genericType.getTypeVariables();
         TypePattern[] typeParamPatterns = this.typeParameters.getTypePatterns();
         if (this.typeParameters.areAllExactWithNoSubtypesAllowed()) {
            for(int i = 0; i < tvs.length; ++i) {
               UnresolvedType ut = typeParamPatterns[i].getExactType();
               boolean continueCheck = true;
               if (ut.isTypeVariableReference()) {
                  continueCheck = false;
               }

               if (continueCheck && !tvs[i].canBeBoundTo(ut.resolve(this.scope.getWorld()))) {
                  String parameterName = ut.getName();
                  if (ut.isTypeVariableReference()) {
                     parameterName = ((TypeVariableReference)ut).getTypeVariable().getDisplayName();
                  }

                  String msg = WeaverMessages.format("violatesTypeVariableBounds", parameterName, new Integer(i + 1), tvs[i].getDisplayName(), this.genericType.getName());
                  if (this.requireExactType) {
                     this.scope.message(MessageUtil.error(msg, this.sLoc));
                  } else {
                     this.scope.message(MessageUtil.warn(msg, this.sLoc));
                  }
               }
            }
         }

      }
   }
}
