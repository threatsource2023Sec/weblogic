package com.bea.core.repackaged.aspectj.weaver.patterns;

import com.bea.core.repackaged.aspectj.bridge.MessageUtil;
import com.bea.core.repackaged.aspectj.util.FuzzyBoolean;
import com.bea.core.repackaged.aspectj.weaver.BCException;
import com.bea.core.repackaged.aspectj.weaver.ISourceContext;
import com.bea.core.repackaged.aspectj.weaver.IntMap;
import com.bea.core.repackaged.aspectj.weaver.ResolvedType;
import com.bea.core.repackaged.aspectj.weaver.TypeVariableReference;
import com.bea.core.repackaged.aspectj.weaver.UnresolvedType;
import com.bea.core.repackaged.aspectj.weaver.VersionedDataInputStream;
import com.bea.core.repackaged.aspectj.weaver.WeaverMessages;
import com.bea.core.repackaged.aspectj.weaver.World;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

public abstract class TypePattern extends PatternNode {
   public static final MatchKind STATIC = new MatchKind("STATIC");
   public static final MatchKind DYNAMIC = new MatchKind("DYNAMIC");
   public static final TypePattern ELLIPSIS = new EllipsisTypePattern();
   public static final TypePattern ANY = new AnyTypePattern();
   public static final TypePattern NO = new NoTypePattern();
   protected boolean includeSubtypes;
   protected boolean isVarArgs;
   protected AnnotationTypePattern annotationPattern;
   protected TypePatternList typeParameters;
   public static final byte WILD = 1;
   public static final byte EXACT = 2;
   public static final byte BINDING = 3;
   public static final byte ELLIPSIS_KEY = 4;
   public static final byte ANY_KEY = 5;
   public static final byte NOT = 6;
   public static final byte OR = 7;
   public static final byte AND = 8;
   public static final byte NO_KEY = 9;
   public static final byte ANY_WITH_ANNO = 10;
   public static final byte HAS_MEMBER = 11;
   public static final byte TYPE_CATEGORY = 12;

   protected TypePattern(boolean includeSubtypes, boolean isVarArgs, TypePatternList typeParams) {
      this.isVarArgs = false;
      this.annotationPattern = AnnotationTypePattern.ANY;
      this.typeParameters = TypePatternList.EMPTY;
      this.includeSubtypes = includeSubtypes;
      this.isVarArgs = isVarArgs;
      this.typeParameters = typeParams == null ? TypePatternList.EMPTY : typeParams;
   }

   protected TypePattern(boolean includeSubtypes, boolean isVarArgs) {
      this(includeSubtypes, isVarArgs, (TypePatternList)null);
   }

   public AnnotationTypePattern getAnnotationPattern() {
      return this.annotationPattern;
   }

   public boolean isVarArgs() {
      return this.isVarArgs;
   }

   public boolean isStarAnnotation() {
      return this.annotationPattern == AnnotationTypePattern.ANY;
   }

   public boolean isArray() {
      return false;
   }

   protected TypePattern(boolean includeSubtypes) {
      this(includeSubtypes, false);
   }

   public void setAnnotationTypePattern(AnnotationTypePattern annPatt) {
      this.annotationPattern = annPatt;
   }

   public void setTypeParameters(TypePatternList typeParams) {
      this.typeParameters = typeParams;
   }

   public TypePatternList getTypeParameters() {
      return this.typeParameters;
   }

   public void setIsVarArgs(boolean isVarArgs) {
      this.isVarArgs = isVarArgs;
   }

   protected boolean couldEverMatchSameTypesAs(TypePattern other) {
      if (!this.includeSubtypes && !other.includeSubtypes) {
         if (this.annotationPattern != AnnotationTypePattern.ANY) {
            return true;
         } else {
            return other.annotationPattern != AnnotationTypePattern.ANY;
         }
      } else {
         return true;
      }
   }

   public boolean matchesStatically(ResolvedType type) {
      return this.includeSubtypes ? this.matchesSubtypes(type) : this.matchesExactly(type);
   }

   public abstract FuzzyBoolean matchesInstanceof(ResolvedType var1);

   public final FuzzyBoolean matches(ResolvedType type, MatchKind kind) {
      if (type.isMissing()) {
         return FuzzyBoolean.NO;
      } else if (kind == STATIC) {
         return FuzzyBoolean.fromBoolean(this.matchesStatically(type));
      } else if (kind == DYNAMIC) {
         return this.matchesInstanceof(type);
      } else {
         throw new IllegalArgumentException("kind must be DYNAMIC or STATIC");
      }
   }

   protected abstract boolean matchesExactly(ResolvedType var1);

   protected abstract boolean matchesExactly(ResolvedType var1, ResolvedType var2);

   protected boolean matchesSubtypes(ResolvedType type) {
      if (this.matchesExactly((ResolvedType)type)) {
         return true;
      } else {
         Iterator typesIterator = null;
         if (((ResolvedType)type).isTypeVariableReference()) {
            typesIterator = ((TypeVariableReference)type).getTypeVariable().getFirstBound().resolve(((ResolvedType)type).getWorld()).getDirectSupertypes();
         } else {
            if (((ResolvedType)type).isRawType()) {
               type = ((ResolvedType)type).getGenericType();
            }

            typesIterator = ((ResolvedType)type).getDirectSupertypes();
         }

         Iterator i = typesIterator;

         ResolvedType superType;
         do {
            if (!i.hasNext()) {
               return false;
            }

            superType = (ResolvedType)i.next();
         } while(!this.matchesSubtypes(superType, (ResolvedType)type));

         return true;
      }
   }

   protected boolean matchesSubtypes(ResolvedType superType, ResolvedType annotatedType) {
      if (this.matchesExactly((ResolvedType)superType, annotatedType)) {
         return true;
      } else {
         if (((ResolvedType)superType).isParameterizedType() || ((ResolvedType)superType).isRawType()) {
            superType = ((ResolvedType)superType).getGenericType();
         }

         Iterator i = ((ResolvedType)superType).getDirectSupertypes();

         ResolvedType superSuperType;
         do {
            if (!i.hasNext()) {
               return false;
            }

            superSuperType = (ResolvedType)i.next();
         } while(!this.matchesSubtypes(superSuperType, annotatedType));

         return true;
      }
   }

   public UnresolvedType resolveExactType(IScope scope, Bindings bindings) {
      TypePattern p = this.resolveBindings(scope, bindings, false, true);
      return (UnresolvedType)(!(p instanceof ExactTypePattern) ? ResolvedType.MISSING : ((ExactTypePattern)p).getType());
   }

   public UnresolvedType getExactType() {
      return (UnresolvedType)(this instanceof ExactTypePattern ? ((ExactTypePattern)this).getType() : ResolvedType.MISSING);
   }

   protected TypePattern notExactType(IScope s) {
      s.getMessageHandler().handleMessage(MessageUtil.error(WeaverMessages.format("exactTypePatternRequired"), this.getSourceLocation()));
      return NO;
   }

   public TypePattern resolveBindings(IScope scope, Bindings bindings, boolean allowBinding, boolean requireExactType) {
      this.annotationPattern = this.annotationPattern.resolveBindings(scope, bindings, allowBinding);
      return this;
   }

   public void resolve(World world) {
      this.annotationPattern.resolve(world);
   }

   public abstract TypePattern parameterizeWith(Map var1, World var2);

   public void postRead(ResolvedType enclosingType) {
   }

   public boolean isEllipsis() {
      return false;
   }

   public boolean isStar() {
      return false;
   }

   public TypePattern remapAdviceFormals(IntMap bindings) {
      return this;
   }

   public static TypePattern read(VersionedDataInputStream s, ISourceContext context) throws IOException {
      byte key = s.readByte();
      switch (key) {
         case 1:
            return WildTypePattern.read(s, context);
         case 2:
            return ExactTypePattern.read(s, context);
         case 3:
            return BindingTypePattern.read(s, context);
         case 4:
            return ELLIPSIS;
         case 5:
            return ANY;
         case 6:
            return NotTypePattern.read(s, context);
         case 7:
            return OrTypePattern.read(s, context);
         case 8:
            return AndTypePattern.read(s, context);
         case 9:
            return NO;
         case 10:
            return AnyWithAnnotationTypePattern.read(s, context);
         case 11:
            return HasMemberTypePattern.read(s, context);
         case 12:
            return TypeCategoryTypePattern.read(s, context);
         default:
            throw new BCException("unknown TypePattern kind: " + key);
      }
   }

   public boolean isIncludeSubtypes() {
      return this.includeSubtypes;
   }

   public boolean isBangVoid() {
      return false;
   }

   public boolean isVoid() {
      return false;
   }

   public boolean hasFailedResolution() {
      return false;
   }

   public static class MatchKind {
      private String name;

      public MatchKind(String name) {
         this.name = name;
      }

      public String toString() {
         return this.name;
      }
   }
}
