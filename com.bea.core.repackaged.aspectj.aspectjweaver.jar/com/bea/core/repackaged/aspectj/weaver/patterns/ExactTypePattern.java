package com.bea.core.repackaged.aspectj.weaver.patterns;

import com.bea.core.repackaged.aspectj.util.FuzzyBoolean;
import com.bea.core.repackaged.aspectj.weaver.BCException;
import com.bea.core.repackaged.aspectj.weaver.CompressingDataOutputStream;
import com.bea.core.repackaged.aspectj.weaver.ISourceContext;
import com.bea.core.repackaged.aspectj.weaver.ResolvedType;
import com.bea.core.repackaged.aspectj.weaver.TypeVariableReference;
import com.bea.core.repackaged.aspectj.weaver.TypeVariableReferenceType;
import com.bea.core.repackaged.aspectj.weaver.UnresolvedType;
import com.bea.core.repackaged.aspectj.weaver.VersionedDataInputStream;
import com.bea.core.repackaged.aspectj.weaver.World;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ExactTypePattern extends TypePattern {
   protected UnresolvedType type;
   protected transient ResolvedType resolvedType;
   public boolean checked = false;
   public boolean isVoid = false;
   public static final Map primitiveTypesMap = new HashMap();
   public static final Map boxedPrimitivesMap;
   private static final Map boxedTypesMap;
   private static final byte EXACT_VERSION = 1;

   protected boolean matchesSubtypes(ResolvedType type) {
      boolean match = super.matchesSubtypes(type);
      if (match) {
         return match;
      } else if (type.isArray() && this.type.isArray()) {
         ResolvedType componentType = type.getComponentType().resolve(type.getWorld());
         UnresolvedType newPatternType = this.type.getComponentType();
         ExactTypePattern etp = new ExactTypePattern(newPatternType, this.includeSubtypes, false);
         return etp.matchesSubtypes(componentType, type);
      } else {
         return match;
      }
   }

   public ExactTypePattern(UnresolvedType type, boolean includeSubtypes, boolean isVarArgs) {
      super(includeSubtypes, isVarArgs);
      this.type = type;
   }

   public boolean isArray() {
      return this.type.isArray();
   }

   protected boolean couldEverMatchSameTypesAs(TypePattern other) {
      if (super.couldEverMatchSameTypesAs(other)) {
         return true;
      } else {
         UnresolvedType otherType = other.getExactType();
         if (!ResolvedType.isMissing(otherType)) {
            return this.type.equals(otherType);
         } else {
            if (other instanceof WildTypePattern) {
               WildTypePattern owtp = (WildTypePattern)other;
               String yourSimpleNamePrefix = owtp.getNamePatterns()[0].maybeGetSimpleName();
               if (yourSimpleNamePrefix != null) {
                  return this.type.getName().startsWith(yourSimpleNamePrefix);
               }
            }

            return true;
         }
      }
   }

   protected boolean matchesExactly(ResolvedType matchType) {
      boolean typeMatch = this.type.equals(matchType);
      if (!typeMatch && (matchType.isParameterizedType() || matchType.isGenericType())) {
         typeMatch = this.type.equals(matchType.getRawType());
      }

      if (!typeMatch && matchType.isTypeVariableReference()) {
         typeMatch = this.matchesTypeVariable((TypeVariableReferenceType)matchType);
      }

      if (!typeMatch) {
         return false;
      } else {
         this.annotationPattern.resolve(matchType.getWorld());
         boolean annMatch = false;
         if (matchType.temporaryAnnotationTypes != null) {
            annMatch = this.annotationPattern.matches(matchType, matchType.temporaryAnnotationTypes).alwaysTrue();
         } else {
            annMatch = this.annotationPattern.matches(matchType).alwaysTrue();
         }

         return typeMatch && annMatch;
      }
   }

   private boolean matchesTypeVariable(TypeVariableReferenceType matchType) {
      return this.type.equals(matchType.getTypeVariable().getFirstBound());
   }

   protected boolean matchesExactly(ResolvedType matchType, ResolvedType annotatedType) {
      boolean typeMatch = this.type.equals(matchType);
      if (!typeMatch && (matchType.isParameterizedType() || matchType.isGenericType())) {
         typeMatch = this.type.equals(matchType.getRawType());
      }

      if (!typeMatch && matchType.isTypeVariableReference()) {
         typeMatch = this.matchesTypeVariable((TypeVariableReferenceType)matchType);
      }

      this.annotationPattern.resolve(matchType.getWorld());
      boolean annMatch = false;
      if (annotatedType.temporaryAnnotationTypes != null) {
         annMatch = this.annotationPattern.matches(annotatedType, annotatedType.temporaryAnnotationTypes).alwaysTrue();
      } else {
         annMatch = this.annotationPattern.matches(annotatedType).alwaysTrue();
      }

      return typeMatch && annMatch;
   }

   public UnresolvedType getType() {
      return this.type;
   }

   public ResolvedType getResolvedExactType(World world) {
      if (this.resolvedType == null) {
         this.resolvedType = world.resolve(this.type);
      }

      return this.resolvedType;
   }

   public boolean isVoid() {
      if (!this.checked) {
         this.isVoid = this.type.getSignature().equals("V");
         this.checked = true;
      }

      return this.isVoid;
   }

   public FuzzyBoolean matchesInstanceof(ResolvedType matchType) {
      this.annotationPattern.resolve(matchType.getWorld());
      if (this.type.equals(ResolvedType.OBJECT)) {
         return FuzzyBoolean.YES.and(this.annotationPattern.matches(matchType));
      } else {
         ResolvedType resolvedType = this.type.resolve(matchType.getWorld());
         if (resolvedType.isAssignableFrom(matchType)) {
            return FuzzyBoolean.YES.and(this.annotationPattern.matches(matchType));
         } else if (this.type.isPrimitiveType()) {
            return FuzzyBoolean.NO;
         } else {
            return matchType.isCoerceableFrom(this.type.resolve(matchType.getWorld())) ? FuzzyBoolean.MAYBE : FuzzyBoolean.NO;
         }
      }
   }

   public boolean equals(Object other) {
      if (!(other instanceof ExactTypePattern)) {
         return false;
      } else if (other instanceof BindingTypePattern) {
         return false;
      } else {
         ExactTypePattern o = (ExactTypePattern)other;
         if (this.includeSubtypes != o.includeSubtypes) {
            return false;
         } else if (this.isVarArgs != o.isVarArgs) {
            return false;
         } else if (!this.typeParameters.equals(o.typeParameters)) {
            return false;
         } else {
            return o.type.equals(this.type) && o.annotationPattern.equals(this.annotationPattern);
         }
      }
   }

   public int hashCode() {
      int result = 17;
      result = 37 * result + this.type.hashCode();
      result = 37 * result + (new Boolean(this.includeSubtypes)).hashCode();
      result = 37 * result + (new Boolean(this.isVarArgs)).hashCode();
      result = 37 * result + this.typeParameters.hashCode();
      result = 37 * result + this.annotationPattern.hashCode();
      return result;
   }

   public void write(CompressingDataOutputStream out) throws IOException {
      out.writeByte(2);
      out.writeByte(1);
      out.writeCompressedSignature(this.type.getSignature());
      out.writeBoolean(this.includeSubtypes);
      out.writeBoolean(this.isVarArgs);
      this.annotationPattern.write(out);
      this.typeParameters.write(out);
      this.writeLocation(out);
   }

   public static TypePattern read(VersionedDataInputStream s, ISourceContext context) throws IOException {
      return s.getMajorVersion() >= 2 ? readTypePattern150(s, context) : readTypePatternOldStyle(s, context);
   }

   public static TypePattern readTypePattern150(VersionedDataInputStream s, ISourceContext context) throws IOException {
      byte version = s.readByte();
      if (version > 1) {
         throw new BCException("ExactTypePattern was written by a more recent version of AspectJ");
      } else {
         TypePattern ret = new ExactTypePattern(s.isAtLeast169() ? s.readSignatureAsUnresolvedType() : UnresolvedType.read(s), s.readBoolean(), s.readBoolean());
         ret.setAnnotationTypePattern(AnnotationTypePattern.read(s, context));
         ret.setTypeParameters(TypePatternList.read(s, context));
         ret.readLocation(context, s);
         return ret;
      }
   }

   public static TypePattern readTypePatternOldStyle(DataInputStream s, ISourceContext context) throws IOException {
      TypePattern ret = new ExactTypePattern(UnresolvedType.read(s), s.readBoolean(), false);
      ret.readLocation(context, s);
      return ret;
   }

   public String toString() {
      StringBuffer buff = new StringBuffer();
      if (this.annotationPattern != AnnotationTypePattern.ANY) {
         buff.append('(');
         buff.append(this.annotationPattern.toString());
         buff.append(' ');
      }

      String typeString = this.type.toString();
      if (this.isVarArgs) {
         typeString = typeString.substring(0, typeString.lastIndexOf(91));
      }

      buff.append(typeString);
      if (this.includeSubtypes) {
         buff.append('+');
      }

      if (this.isVarArgs) {
         buff.append("...");
      }

      if (this.annotationPattern != AnnotationTypePattern.ANY) {
         buff.append(')');
      }

      return buff.toString();
   }

   public TypePattern resolveBindings(IScope scope, Bindings bindings, boolean allowBinding, boolean requireExactType) {
      throw new BCException("trying to re-resolve");
   }

   public TypePattern parameterizeWith(Map typeVariableMap, World w) {
      UnresolvedType newType = this.type;
      if (this.type.isTypeVariableReference()) {
         TypeVariableReference t = (TypeVariableReference)this.type;
         String key = t.getTypeVariable().getName();
         if (typeVariableMap.containsKey(key)) {
            newType = (UnresolvedType)typeVariableMap.get(key);
         }
      } else if (this.type.isParameterizedType()) {
         newType = w.resolve(this.type).parameterize(typeVariableMap);
      }

      ExactTypePattern ret = new ExactTypePattern(newType, this.includeSubtypes, this.isVarArgs);
      ret.annotationPattern = this.annotationPattern.parameterizeWith(typeVariableMap, w);
      ret.copyLocationFrom(this);
      return ret;
   }

   public Object accept(PatternNodeVisitor visitor, Object data) {
      return visitor.visit(this, data);
   }

   static {
      primitiveTypesMap.put("int", Integer.TYPE);
      primitiveTypesMap.put("short", Short.TYPE);
      primitiveTypesMap.put("long", Long.TYPE);
      primitiveTypesMap.put("byte", Byte.TYPE);
      primitiveTypesMap.put("char", Character.TYPE);
      primitiveTypesMap.put("float", Float.TYPE);
      primitiveTypesMap.put("double", Double.TYPE);
      boxedPrimitivesMap = new HashMap();
      boxedPrimitivesMap.put("java.lang.Integer", Integer.class);
      boxedPrimitivesMap.put("java.lang.Short", Short.class);
      boxedPrimitivesMap.put("java.lang.Long", Long.class);
      boxedPrimitivesMap.put("java.lang.Byte", Byte.class);
      boxedPrimitivesMap.put("java.lang.Character", Character.class);
      boxedPrimitivesMap.put("java.lang.Float", Float.class);
      boxedPrimitivesMap.put("java.lang.Double", Double.class);
      boxedTypesMap = new HashMap();
      boxedTypesMap.put("int", Integer.class);
      boxedTypesMap.put("short", Short.class);
      boxedTypesMap.put("long", Long.class);
      boxedTypesMap.put("byte", Byte.class);
      boxedTypesMap.put("char", Character.class);
      boxedTypesMap.put("float", Float.class);
      boxedTypesMap.put("double", Double.class);
   }
}
