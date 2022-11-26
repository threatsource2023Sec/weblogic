package com.bea.core.repackaged.aspectj.weaver;

import com.bea.core.repackaged.aspectj.bridge.ISourceLocation;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class JoinPointSignature implements ResolvedMember {
   public static final JoinPointSignature[] EMPTY_ARRAY = new JoinPointSignature[0];
   private ResolvedMember realMember;
   private ResolvedType substituteDeclaringType;

   public JoinPointSignature(ResolvedMember backing, ResolvedType aType) {
      this.realMember = backing;
      this.substituteDeclaringType = aType;
   }

   public UnresolvedType getDeclaringType() {
      return this.substituteDeclaringType;
   }

   public int getModifiers(World world) {
      return this.realMember.getModifiers(world);
   }

   public int getModifiers() {
      return this.realMember.getModifiers();
   }

   public UnresolvedType[] getExceptions(World world) {
      return this.realMember.getExceptions(world);
   }

   public UnresolvedType[] getExceptions() {
      return this.realMember.getExceptions();
   }

   public ShadowMunger getAssociatedShadowMunger() {
      return this.realMember.getAssociatedShadowMunger();
   }

   public boolean isAjSynthetic() {
      return this.realMember.isAjSynthetic();
   }

   public boolean hasAnnotation(UnresolvedType ofType) {
      return this.realMember.hasAnnotation(ofType);
   }

   public ResolvedType[] getAnnotationTypes() {
      return this.realMember.getAnnotationTypes();
   }

   public AnnotationAJ getAnnotationOfType(UnresolvedType ofType) {
      return this.realMember.getAnnotationOfType(ofType);
   }

   public void setAnnotationTypes(ResolvedType[] annotationtypes) {
      this.realMember.setAnnotationTypes(annotationtypes);
   }

   public void setAnnotations(AnnotationAJ[] annotations) {
      this.realMember.setAnnotations(annotations);
   }

   public void addAnnotation(AnnotationAJ annotation) {
      this.realMember.addAnnotation(annotation);
   }

   public boolean isBridgeMethod() {
      return this.realMember.isBridgeMethod();
   }

   public boolean isVarargsMethod() {
      return this.realMember.isVarargsMethod();
   }

   public boolean isSynthetic() {
      return this.realMember.isSynthetic();
   }

   public void write(CompressingDataOutputStream s) throws IOException {
      this.realMember.write(s);
   }

   public ISourceContext getSourceContext(World world) {
      return this.realMember.getSourceContext(world);
   }

   public String[] getParameterNames() {
      return this.realMember.getParameterNames();
   }

   public void setParameterNames(String[] names) {
      this.realMember.setParameterNames(names);
   }

   public String[] getParameterNames(World world) {
      return this.realMember.getParameterNames(world);
   }

   public AjAttribute.EffectiveSignatureAttribute getEffectiveSignature() {
      return this.realMember.getEffectiveSignature();
   }

   public ISourceLocation getSourceLocation() {
      return this.realMember.getSourceLocation();
   }

   public int getEnd() {
      return this.realMember.getEnd();
   }

   public ISourceContext getSourceContext() {
      return this.realMember.getSourceContext();
   }

   public int getStart() {
      return this.realMember.getStart();
   }

   public void setPosition(int sourceStart, int sourceEnd) {
      this.realMember.setPosition(sourceStart, sourceEnd);
   }

   public void setSourceContext(ISourceContext sourceContext) {
      this.realMember.setSourceContext(sourceContext);
   }

   public boolean isAbstract() {
      return this.realMember.isAbstract();
   }

   public boolean isPublic() {
      return this.realMember.isPublic();
   }

   public boolean isDefault() {
      return this.realMember.isDefault();
   }

   public boolean isVisible(ResolvedType fromType) {
      return this.realMember.isVisible(fromType);
   }

   public void setCheckedExceptions(UnresolvedType[] checkedExceptions) {
      this.realMember.setCheckedExceptions(checkedExceptions);
   }

   public void setAnnotatedElsewhere(boolean b) {
      this.realMember.setAnnotatedElsewhere(b);
   }

   public boolean isAnnotatedElsewhere() {
      return this.realMember.isAnnotatedElsewhere();
   }

   public UnresolvedType getGenericReturnType() {
      return this.realMember.getGenericReturnType();
   }

   public UnresolvedType[] getGenericParameterTypes() {
      return this.realMember.getGenericParameterTypes();
   }

   public ResolvedMemberImpl parameterizedWith(UnresolvedType[] typeParameters, ResolvedType newDeclaringType, boolean isParameterized) {
      return this.realMember.parameterizedWith(typeParameters, newDeclaringType, isParameterized);
   }

   public ResolvedMemberImpl parameterizedWith(UnresolvedType[] typeParameters, ResolvedType newDeclaringType, boolean isParameterized, List aliases) {
      return this.realMember.parameterizedWith(typeParameters, newDeclaringType, isParameterized, aliases);
   }

   public void setTypeVariables(TypeVariable[] types) {
      this.realMember.setTypeVariables(types);
   }

   public TypeVariable[] getTypeVariables() {
      return this.realMember.getTypeVariables();
   }

   public TypeVariable getTypeVariableNamed(String name) {
      return this.realMember.getTypeVariableNamed(name);
   }

   public boolean matches(ResolvedMember aCandidateMatch, boolean ignoreGenerics) {
      return this.realMember.matches(aCandidateMatch, ignoreGenerics);
   }

   public ResolvedMember resolve(World world) {
      return this.realMember.resolve(world);
   }

   public int compareTo(Member other) {
      return this.realMember.compareTo(other);
   }

   public MemberKind getKind() {
      return this.realMember.getKind();
   }

   public UnresolvedType getReturnType() {
      return this.realMember.getReturnType();
   }

   public UnresolvedType getType() {
      return this.realMember.getType();
   }

   public String getName() {
      return this.realMember.getName();
   }

   public UnresolvedType[] getParameterTypes() {
      return this.realMember.getParameterTypes();
   }

   public AnnotationAJ[][] getParameterAnnotations() {
      return this.realMember.getParameterAnnotations();
   }

   public ResolvedType[][] getParameterAnnotationTypes() {
      return this.realMember.getParameterAnnotationTypes();
   }

   public String getSignature() {
      return this.realMember.getSignature();
   }

   public int getArity() {
      return this.realMember.getArity();
   }

   public String getParameterSignature() {
      return this.realMember.getParameterSignature();
   }

   public boolean isCompatibleWith(Member am) {
      return this.realMember.isCompatibleWith(am);
   }

   public boolean canBeParameterized() {
      return this.realMember.canBeParameterized();
   }

   public AnnotationAJ[] getAnnotations() {
      return this.realMember.getAnnotations();
   }

   public Collection getDeclaringTypes(World world) {
      throw new UnsupportedOperationException("Adrian doesn't think you should be calling this...");
   }

   public JoinPointSignatureIterator getJoinPointSignatures(World world) {
      return this.realMember.getJoinPointSignatures(world);
   }

   public String toString() {
      StringBuffer buf = new StringBuffer();
      buf.append(this.getReturnType().getName());
      buf.append(' ');
      buf.append(this.getDeclaringType().getName());
      buf.append('.');
      buf.append(this.getName());
      if (this.getKind() != FIELD) {
         buf.append("(");
         UnresolvedType[] parameterTypes = this.getParameterTypes();
         if (parameterTypes.length != 0) {
            buf.append(parameterTypes[0]);
            int i = 1;

            for(int len = parameterTypes.length; i < len; ++i) {
               buf.append(", ");
               buf.append(parameterTypes[i].getName());
            }
         }

         buf.append(")");
      }

      return buf.toString();
   }

   public String toGenericString() {
      return this.realMember.toGenericString();
   }

   public String toDebugString() {
      return this.realMember.toDebugString();
   }

   public boolean equals(Object obj) {
      if (!(obj instanceof JoinPointSignature)) {
         return false;
      } else {
         JoinPointSignature other = (JoinPointSignature)obj;
         if (!this.realMember.equals(other.realMember)) {
            return false;
         } else {
            return this.substituteDeclaringType.equals(other.substituteDeclaringType);
         }
      }
   }

   public int hashCode() {
      return 17 + 37 * this.realMember.hashCode() + 37 * this.substituteDeclaringType.hashCode();
   }

   public boolean hasBackingGenericMember() {
      return this.realMember.hasBackingGenericMember();
   }

   public ResolvedMember getBackingGenericMember() {
      return this.realMember.getBackingGenericMember();
   }

   public void evictWeavingState() {
      this.realMember.evictWeavingState();
   }

   public ResolvedMember parameterizedWith(Map m, World w) {
      return this.realMember.parameterizedWith(m, w);
   }

   public String getAnnotationDefaultValue() {
      return this.realMember.getAnnotationDefaultValue();
   }

   public String getParameterSignatureErased() {
      return this.realMember.getParameterSignatureErased();
   }

   public String getSignatureErased() {
      return this.realMember.getSignatureErased();
   }

   public boolean isDefaultConstructor() {
      return this.realMember.isDefaultConstructor();
   }

   public boolean equalsApartFromDeclaringType(Object other) {
      return this.realMember.equalsApartFromDeclaringType(other);
   }
}
