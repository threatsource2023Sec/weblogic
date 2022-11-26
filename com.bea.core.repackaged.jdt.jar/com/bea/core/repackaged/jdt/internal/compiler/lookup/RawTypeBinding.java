package com.bea.core.repackaged.jdt.internal.compiler.lookup;

import com.bea.core.repackaged.jdt.core.compiler.CharOperation;
import java.util.Set;

public class RawTypeBinding extends ParameterizedTypeBinding {
   public RawTypeBinding(ReferenceBinding type, ReferenceBinding enclosingType, LookupEnvironment environment) {
      super(type, (TypeBinding[])null, enclosingType, environment);
      this.tagBits &= -129L;
      ParameterizedTypeBinding parameterizedTypeBinding;
      if ((type.tagBits & 128L) != 0L) {
         if (type instanceof MissingTypeBinding) {
            this.tagBits |= 128L;
         } else if (type instanceof ParameterizedTypeBinding) {
            parameterizedTypeBinding = (ParameterizedTypeBinding)type;
            if (parameterizedTypeBinding.genericType() instanceof MissingTypeBinding) {
               this.tagBits |= 128L;
            }
         }
      }

      if (enclosingType != null && (enclosingType.tagBits & 128L) != 0L) {
         if (enclosingType instanceof MissingTypeBinding) {
            this.tagBits |= 128L;
         } else if (enclosingType instanceof ParameterizedTypeBinding) {
            parameterizedTypeBinding = (ParameterizedTypeBinding)enclosingType;
            if (parameterizedTypeBinding.genericType() instanceof MissingTypeBinding) {
               this.tagBits |= 128L;
            }
         }
      }

      if (enclosingType == null || !this.hasEnclosingInstanceContext() || (enclosingType.modifiers & 1073741824) == 0) {
         this.modifiers &= -1073741825;
      }

   }

   public char[] computeUniqueKey(boolean isLeaf) {
      StringBuffer sig = new StringBuffer(10);
      if (!this.isMemberType() || !this.enclosingType().isParameterizedType() && !this.enclosingType().isRawType()) {
         sig.append(this.genericType().computeUniqueKey(false));
         sig.insert(sig.length() - 1, "<>");
      } else {
         char[] typeSig;
         if (!this.hasEnclosingInstanceContext()) {
            typeSig = this.enclosingType().signature();
            sig.append(typeSig, 0, typeSig.length - 1);
            sig.append('$');
         } else {
            typeSig = this.enclosingType().computeUniqueKey(false);
            sig.append(typeSig, 0, typeSig.length - 1);
            sig.append('.');
         }

         sig.append(this.sourceName());
         if (this.genericType().typeVariables() != Binding.NO_TYPE_VARIABLES) {
            sig.append('<').append('>');
         }

         sig.append(';');
      }

      int sigLength = sig.length();
      char[] uniqueKey = new char[sigLength];
      sig.getChars(0, sigLength, uniqueKey, 0);
      return uniqueKey;
   }

   public TypeBinding clone(TypeBinding outerType) {
      return new RawTypeBinding(this.actualType(), (ReferenceBinding)outerType, this.environment);
   }

   public TypeBinding withoutToplevelNullAnnotation() {
      if (!this.hasNullTypeAnnotations()) {
         return this;
      } else {
         ReferenceBinding unannotatedGenericType = (ReferenceBinding)this.environment.getUnannotatedType(this.genericType());
         AnnotationBinding[] newAnnotations = this.environment.filterNullTypeAnnotations(this.typeAnnotations);
         return this.environment.createRawType(unannotatedGenericType, this.enclosingType(), newAnnotations);
      }
   }

   public ParameterizedMethodBinding createParameterizedMethod(MethodBinding originalMethod) {
      return (ParameterizedMethodBinding)(originalMethod.typeVariables != Binding.NO_TYPE_VARIABLES && !originalMethod.isStatic() ? this.environment.createParameterizedGenericMethod(originalMethod, this) : super.createParameterizedMethod(originalMethod));
   }

   public boolean isParameterizedType() {
      return false;
   }

   public int kind() {
      return 1028;
   }

   public String debugName() {
      if (this.hasTypeAnnotations()) {
         return this.annotatedDebugName();
      } else {
         StringBuffer nameBuffer = new StringBuffer(10);
         nameBuffer.append(this.actualType().sourceName()).append("#RAW");
         return nameBuffer.toString();
      }
   }

   public String annotatedDebugName() {
      StringBuffer buffer = new StringBuffer(super.annotatedDebugName());
      buffer.append("#RAW");
      return buffer.toString();
   }

   public char[] genericTypeSignature() {
      if (this.genericTypeSignature == null) {
         if ((this.modifiers & 1073741824) == 0) {
            this.genericTypeSignature = this.genericType().signature();
         } else {
            StringBuffer sig = new StringBuffer(10);
            if (this.isMemberType() && this.hasEnclosingInstanceContext()) {
               ReferenceBinding enclosing = this.enclosingType();
               char[] typeSig = enclosing.genericTypeSignature();
               sig.append(typeSig, 0, typeSig.length - 1);
               if ((enclosing.modifiers & 1073741824) != 0) {
                  sig.append('.');
               } else {
                  sig.append('$');
               }

               sig.append(this.sourceName());
            } else {
               char[] typeSig = this.genericType().signature();
               sig.append(typeSig, 0, typeSig.length - 1);
            }

            sig.append(';');
            int sigLength = sig.length();
            this.genericTypeSignature = new char[sigLength];
            sig.getChars(0, sigLength, this.genericTypeSignature, 0);
         }
      }

      return this.genericTypeSignature;
   }

   public boolean isEquivalentTo(TypeBinding otherType) {
      if (!equalsEquals(this, otherType) && !equalsEquals(this.erasure(), otherType)) {
         if (otherType == null) {
            return false;
         } else {
            switch (otherType.kind()) {
               case 260:
               case 1028:
               case 2052:
                  return TypeBinding.equalsEquals(this.erasure(), otherType.erasure());
               case 516:
               case 8196:
                  return ((WildcardBinding)otherType).boundCheck(this);
               default:
                  return false;
            }
         }
      } else {
         return true;
      }
   }

   public boolean isProvablyDistinct(TypeBinding otherType) {
      if (!TypeBinding.equalsEquals(this, otherType) && !TypeBinding.equalsEquals(this.erasure(), otherType)) {
         if (otherType == null) {
            return true;
         } else {
            switch (otherType.kind()) {
               case 260:
               case 1028:
               case 2052:
                  return TypeBinding.notEquals(this.erasure(), otherType.erasure());
               default:
                  return true;
            }
         }
      } else {
         return false;
      }
   }

   public boolean isSubtypeOf(TypeBinding right, boolean simulatingBugJDK8026527) {
      if (simulatingBugJDK8026527) {
         right = this.environment.convertToRawType(right.erasure(), false);
      }

      return super.isSubtypeOf(right, simulatingBugJDK8026527);
   }

   public boolean isProperType(boolean admitCapture18) {
      TypeBinding actualType = this.actualType();
      return actualType != null && actualType.isProperType(admitCapture18);
   }

   protected void initializeArguments() {
      TypeVariableBinding[] typeVariables = this.genericType().typeVariables();
      int length = typeVariables.length;
      TypeBinding[] typeArguments = new TypeBinding[length];

      for(int i = 0; i < length; ++i) {
         typeArguments[i] = this.environment.convertToRawType(typeVariables[i].erasure(), false);
      }

      this.arguments = typeArguments;
   }

   public ParameterizedTypeBinding capture(Scope scope, int start, int end) {
      return this;
   }

   public TypeBinding uncapture(Scope scope) {
      return this;
   }

   TypeBinding substituteInferenceVariable(InferenceVariable var, TypeBinding substituteType) {
      return this;
   }

   public MethodBinding getSingleAbstractMethod(Scope scope, boolean replaceWildcards) {
      int index = replaceWildcards ? 0 : 1;
      if (this.singleAbstractMethod != null) {
         if (this.singleAbstractMethod[index] != null) {
            return this.singleAbstractMethod[index];
         }
      } else {
         this.singleAbstractMethod = new MethodBinding[2];
      }

      ReferenceBinding genericType = this.genericType();
      MethodBinding theAbstractMethod = genericType.getSingleAbstractMethod(scope, replaceWildcards);
      if (theAbstractMethod != null && theAbstractMethod.isValidBinding()) {
         ReferenceBinding declaringType = (ReferenceBinding)scope.environment().convertToRawType(genericType, true);
         declaringType = (ReferenceBinding)declaringType.findSuperTypeOriginatingFrom(theAbstractMethod.declaringClass);
         MethodBinding[] choices = declaringType.getMethods(theAbstractMethod.selector);
         int i = 0;

         for(int length = choices.length; i < length; ++i) {
            MethodBinding method = choices[i];
            if (method.isAbstract() && !method.redeclaresPublicObjectMethod(scope)) {
               this.singleAbstractMethod[index] = method;
               break;
            }
         }

         return this.singleAbstractMethod[index];
      } else {
         return this.singleAbstractMethod[index] = theAbstractMethod;
      }
   }

   public boolean mentionsAny(TypeBinding[] parameters, int idx) {
      return false;
   }

   public char[] readableName(boolean showGenerics) {
      char[] readableName;
      if (this.isMemberType()) {
         readableName = CharOperation.concat(this.enclosingType().readableName(showGenerics && this.hasEnclosingInstanceContext()), this.sourceName, '.');
      } else {
         readableName = CharOperation.concatWith(this.actualType().compoundName, '.');
      }

      return readableName;
   }

   public char[] shortReadableName(boolean showGenerics) {
      char[] shortReadableName;
      if (this.isMemberType()) {
         shortReadableName = CharOperation.concat(this.enclosingType().shortReadableName(showGenerics && this.hasEnclosingInstanceContext()), this.sourceName, '.');
      } else {
         shortReadableName = this.actualType().sourceName;
      }

      return shortReadableName;
   }

   void collectInferenceVariables(Set variables) {
   }

   public ReferenceBinding upwardsProjection(Scope scope, TypeBinding[] mentionedTypeVariables) {
      return this;
   }

   public ReferenceBinding downwardsProjection(Scope scope, TypeBinding[] mentionedTypeVariables) {
      return this;
   }

   public ReferenceBinding enclosingType() {
      return this.enclosingType;
   }
}
