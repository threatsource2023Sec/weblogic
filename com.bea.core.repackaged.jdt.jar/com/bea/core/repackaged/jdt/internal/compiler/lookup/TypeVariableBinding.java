package com.bea.core.repackaged.jdt.internal.compiler.lookup;

import com.bea.core.repackaged.jdt.core.compiler.CharOperation;
import com.bea.core.repackaged.jdt.internal.compiler.ast.ASTNode;
import com.bea.core.repackaged.jdt.internal.compiler.ast.Annotation;
import com.bea.core.repackaged.jdt.internal.compiler.ast.Expression;
import com.bea.core.repackaged.jdt.internal.compiler.ast.NullAnnotationMatching;
import com.bea.core.repackaged.jdt.internal.compiler.ast.TypeParameter;
import com.bea.core.repackaged.jdt.internal.compiler.ast.TypeReference;
import com.bea.core.repackaged.jdt.internal.compiler.impl.CompilerOptions;
import java.util.Arrays;
import java.util.Set;

public class TypeVariableBinding extends ReferenceBinding {
   public Binding declaringElement;
   public int rank;
   public TypeBinding firstBound;
   public ReferenceBinding superclass;
   public ReferenceBinding[] superInterfaces;
   public char[] genericTypeSignature;
   LookupEnvironment environment;
   boolean inRecursiveFunction = false;
   boolean inRecursiveProjectionFunction = false;

   public TypeVariableBinding(char[] sourceName, Binding declaringElement, int rank, LookupEnvironment environment) {
      this.sourceName = sourceName;
      this.declaringElement = declaringElement;
      this.rank = rank;
      this.modifiers = 1073741825;
      this.tagBits |= 536870912L;
      this.environment = environment;
      this.typeBits = 134217728;
      this.computeId(environment);
   }

   protected TypeVariableBinding(char[] sourceName, LookupEnvironment environment) {
      this.sourceName = sourceName;
      this.modifiers = 1073741825;
      this.tagBits |= 536870912L;
      this.environment = environment;
      this.typeBits = 134217728;
   }

   public TypeVariableBinding(TypeVariableBinding prototype) {
      super(prototype);
      this.declaringElement = prototype.declaringElement;
      this.rank = prototype.rank;
      this.firstBound = prototype.firstBound;
      this.superclass = prototype.superclass;
      if (prototype.superInterfaces != null) {
         int len = prototype.superInterfaces.length;
         if (len > 0) {
            System.arraycopy(prototype.superInterfaces, 0, this.superInterfaces = new ReferenceBinding[len], 0, len);
         } else {
            this.superInterfaces = Binding.NO_SUPERINTERFACES;
         }
      }

      this.genericTypeSignature = prototype.genericTypeSignature;
      this.environment = prototype.environment;
      prototype.tagBits |= 8388608L;
      this.tagBits &= -8388609L;
   }

   public TypeConstants.BoundCheckStatus boundCheck(Substitution substitution, TypeBinding argumentType, Scope scope, ASTNode location) {
      TypeConstants.BoundCheckStatus code = this.internalBoundCheck(substitution, argumentType, scope, location);
      if (code == TypeConstants.BoundCheckStatus.MISMATCH && argumentType instanceof TypeVariableBinding && scope != null) {
         TypeBinding bound = ((TypeVariableBinding)argumentType).firstBound;
         if (bound instanceof ParameterizedTypeBinding) {
            TypeConstants.BoundCheckStatus code2 = this.boundCheck(substitution, bound.capture(scope, -1, -1), scope, location);
            return code.betterOf(code2);
         }
      }

      return code;
   }

   private TypeConstants.BoundCheckStatus internalBoundCheck(Substitution substitution, TypeBinding argumentType, Scope scope, ASTNode location) {
      if (argumentType != TypeBinding.NULL && !TypeBinding.equalsEquals(argumentType, this)) {
         boolean hasSubstitution = substitution != null;
         if (!(argumentType instanceof ReferenceBinding) && !argumentType.isArrayType()) {
            return TypeConstants.BoundCheckStatus.MISMATCH;
         } else if (this.superclass == null) {
            return TypeConstants.BoundCheckStatus.OK;
         } else {
            TypeConstants.BoundCheckStatus nullStatus = TypeConstants.BoundCheckStatus.OK;
            boolean checkNullAnnotations = scope.environment().usesNullTypeAnnotations() && (location == null || (location.bits & '耀') == 0);
            TypeBinding wildcardBound;
            if (argumentType.kind() != 516) {
               boolean unchecked = false;
               if (this.superclass.id != 1) {
                  TypeBinding substitutedSuperType = hasSubstitution ? Scope.substitute(substitution, (TypeBinding)this.superclass) : this.superclass;
                  if (TypeBinding.notEquals((TypeBinding)substitutedSuperType, argumentType)) {
                     if (!argumentType.isCompatibleWith((TypeBinding)substitutedSuperType, scope)) {
                        return TypeConstants.BoundCheckStatus.MISMATCH;
                     }

                     wildcardBound = argumentType.findSuperTypeOriginatingFrom((TypeBinding)substitutedSuperType);
                     if (wildcardBound != null && wildcardBound.isRawType() && ((TypeBinding)substitutedSuperType).isBoundParameterizedType()) {
                        unchecked = true;
                     }
                  }

                  if (checkNullAnnotations) {
                     nullStatus = this.nullBoundCheck(scope, argumentType, (TypeBinding)substitutedSuperType, substitution, location, nullStatus);
                  }
               }

               int i = 0;

               for(int length = this.superInterfaces.length; i < length; ++i) {
                  TypeBinding substitutedSuperType = hasSubstitution ? Scope.substitute(substitution, (TypeBinding)this.superInterfaces[i]) : this.superInterfaces[i];
                  if (TypeBinding.notEquals((TypeBinding)substitutedSuperType, argumentType)) {
                     if (!argumentType.isCompatibleWith((TypeBinding)substitutedSuperType, scope)) {
                        return TypeConstants.BoundCheckStatus.MISMATCH;
                     }

                     TypeBinding match = argumentType.findSuperTypeOriginatingFrom((TypeBinding)substitutedSuperType);
                     if (match != null && match.isRawType() && ((TypeBinding)substitutedSuperType).isBoundParameterizedType()) {
                        unchecked = true;
                     }
                  }

                  if (checkNullAnnotations) {
                     nullStatus = this.nullBoundCheck(scope, argumentType, (TypeBinding)substitutedSuperType, substitution, location, nullStatus);
                  }
               }

               if (checkNullAnnotations && nullStatus != TypeConstants.BoundCheckStatus.NULL_PROBLEM) {
                  long nullBits = this.tagBits & 108086391056891904L;
                  if (nullBits != 0L && nullBits != (argumentType.tagBits & 108086391056891904L)) {
                     if (location != null) {
                        scope.problemReporter().nullityMismatchTypeArgument(this, argumentType, location);
                     }

                     nullStatus = TypeConstants.BoundCheckStatus.NULL_PROBLEM;
                  }
               }

               return unchecked ? TypeConstants.BoundCheckStatus.UNCHECKED : (nullStatus != null ? nullStatus : TypeConstants.BoundCheckStatus.OK);
            } else {
               WildcardBinding wildcard = (WildcardBinding)argumentType;
               TypeBinding match;
               switch (wildcard.boundKind) {
                  case 0:
                     if (checkNullAnnotations && argumentType.hasNullTypeAnnotations()) {
                        return this.nullBoundCheck(scope, argumentType, (TypeBinding)null, substitution, location, nullStatus);
                     }
                     break;
                  case 1:
                     boolean checkedAsOK = false;
                     wildcardBound = wildcard.bound;
                     if (TypeBinding.equalsEquals(wildcardBound, this)) {
                        checkedAsOK = true;
                     }

                     boolean isArrayBound = wildcardBound.isArrayType();
                     if (!wildcardBound.isInterface()) {
                        TypeBinding substitutedSuperType = hasSubstitution ? Scope.substitute(substitution, (TypeBinding)this.superclass) : this.superclass;
                        if (!checkedAsOK && ((TypeBinding)substitutedSuperType).id != 1) {
                           if (isArrayBound) {
                              if (!wildcardBound.isCompatibleWith((TypeBinding)substitutedSuperType, scope)) {
                                 return TypeConstants.BoundCheckStatus.MISMATCH;
                              }
                           } else {
                              match = wildcardBound.findSuperTypeOriginatingFrom((TypeBinding)substitutedSuperType);
                              if (match != null) {
                                 if (((TypeBinding)substitutedSuperType).isProvablyDistinct(match)) {
                                    return TypeConstants.BoundCheckStatus.MISMATCH;
                                 }
                              } else {
                                 match = ((TypeBinding)substitutedSuperType).findSuperTypeOriginatingFrom(wildcardBound);
                                 if (match != null) {
                                    if (match.isProvablyDistinct(wildcardBound)) {
                                       return TypeConstants.BoundCheckStatus.MISMATCH;
                                    }
                                 } else {
                                    if (this.denotesRelevantSuperClass(wildcardBound) && this.denotesRelevantSuperClass((TypeBinding)substitutedSuperType)) {
                                       return TypeConstants.BoundCheckStatus.MISMATCH;
                                    }

                                    if (Scope.greaterLowerBound(new TypeBinding[]{(TypeBinding)substitutedSuperType, wildcardBound}, scope, this.environment) == null) {
                                       return TypeConstants.BoundCheckStatus.MISMATCH;
                                    }
                                 }
                              }
                           }
                        }

                        if (checkNullAnnotations && argumentType.hasNullTypeAnnotations()) {
                           nullStatus = this.nullBoundCheck(scope, argumentType, (TypeBinding)substitutedSuperType, substitution, location, nullStatus);
                        }
                     }

                     boolean mustImplement = isArrayBound || ((ReferenceBinding)wildcardBound).isFinal();
                     int i = 0;

                     for(int length = this.superInterfaces.length; i < length; ++i) {
                        TypeBinding substitutedSuperType = hasSubstitution ? Scope.substitute(substitution, (TypeBinding)this.superInterfaces[i]) : this.superInterfaces[i];
                        if (!checkedAsOK) {
                           if (isArrayBound) {
                              if (!wildcardBound.isCompatibleWith((TypeBinding)substitutedSuperType, scope)) {
                                 return TypeConstants.BoundCheckStatus.MISMATCH;
                              }
                           } else {
                              TypeBinding match = wildcardBound.findSuperTypeOriginatingFrom((TypeBinding)substitutedSuperType);
                              if (match != null) {
                                 if (((TypeBinding)substitutedSuperType).isProvablyDistinct(match)) {
                                    return TypeConstants.BoundCheckStatus.MISMATCH;
                                 }
                              } else if (mustImplement) {
                                 return TypeConstants.BoundCheckStatus.MISMATCH;
                              }
                           }
                        }

                        if (checkNullAnnotations && argumentType.hasNullTypeAnnotations()) {
                           nullStatus = this.nullBoundCheck(scope, argumentType, (TypeBinding)substitutedSuperType, substitution, location, nullStatus);
                        }
                     }

                     if (nullStatus != null) {
                        return nullStatus;
                     }
                     break;
                  case 2:
                     if (wildcard.bound.isTypeVariable() && ((TypeVariableBinding)wildcard.bound).superclass.id == 1) {
                        return this.nullBoundCheck(scope, argumentType, (TypeBinding)null, substitution, location, nullStatus);
                     }

                     match = wildcard.bound;
                     if (checkNullAnnotations && this.environment.containsNullTypeAnnotation(wildcard.typeAnnotations)) {
                        match = this.environment.createAnnotatedType(match.withoutToplevelNullAnnotation(), wildcard.getTypeAnnotations());
                     }

                     TypeConstants.BoundCheckStatus status = this.boundCheck(substitution, match, scope, (ASTNode)null);
                     if (status == TypeConstants.BoundCheckStatus.NULL_PROBLEM && location != null) {
                        scope.problemReporter().nullityMismatchTypeArgument(this, wildcard, location);
                     }

                     return status;
               }

               return TypeConstants.BoundCheckStatus.OK;
            }
         }
      } else {
         return TypeConstants.BoundCheckStatus.OK;
      }
   }

   private TypeConstants.BoundCheckStatus nullBoundCheck(Scope scope, TypeBinding argumentType, TypeBinding substitutedSuperType, Substitution substitution, ASTNode location, TypeConstants.BoundCheckStatus previousStatus) {
      if (NullAnnotationMatching.analyse(this, argumentType, substitutedSuperType, substitution, -1, (Expression)null, NullAnnotationMatching.CheckMode.BOUND_CHECK).isAnyMismatch()) {
         if (location != null) {
            scope.problemReporter().nullityMismatchTypeArgument(this, argumentType, location);
         }

         return TypeConstants.BoundCheckStatus.NULL_PROBLEM;
      } else {
         return previousStatus;
      }
   }

   boolean denotesRelevantSuperClass(TypeBinding type) {
      if (!type.isTypeVariable() && !type.isInterface() && type.id != 1) {
         return true;
      } else {
         ReferenceBinding aSuperClass = type.superclass();
         return aSuperClass != null && aSuperClass.id != 1 && !aSuperClass.isTypeVariable();
      }
   }

   public int boundsCount() {
      if (this.firstBound == null) {
         return 0;
      } else {
         return this.firstBound.isInterface() ? this.superInterfaces.length : this.superInterfaces.length + 1;
      }
   }

   public boolean canBeInstantiated() {
      return false;
   }

   public void collectSubstitutes(Scope scope, TypeBinding actualType, InferenceContext inferenceContext, int constraint) {
      if (this.declaringElement == inferenceContext.genericMethod) {
         switch (actualType.kind()) {
            case 132:
               if (actualType == TypeBinding.NULL) {
                  return;
               } else {
                  TypeBinding boxedType = scope.environment().computeBoxingType(actualType);
                  if (boxedType == actualType) {
                     return;
                  } else {
                     actualType = boxedType;
                  }
               }
            default:
               byte variableConstraint;
               switch (constraint) {
                  case 0:
                     variableConstraint = 0;
                     break;
                  case 1:
                     variableConstraint = 2;
                     break;
                  default:
                     variableConstraint = 1;
               }

               inferenceContext.recordSubstitute(this, actualType, variableConstraint);
               return;
            case 516:
            case 65540:
         }
      }
   }

   public char[] computeUniqueKey(boolean isLeaf) {
      StringBuffer buffer = new StringBuffer();
      Binding declaring = this.declaringElement;
      if (!isLeaf && declaring.kind() == 8) {
         MethodBinding methodBinding = (MethodBinding)declaring;
         ReferenceBinding declaringClass = methodBinding.declaringClass;
         buffer.append(declaringClass.computeUniqueKey(false));
         buffer.append(':');
         MethodBinding[] methods = declaringClass.methods();
         if (methods != null) {
            int i = 0;

            for(int length = methods.length; i < length; ++i) {
               MethodBinding binding = methods[i];
               if (binding == methodBinding) {
                  buffer.append(i);
                  break;
               }
            }
         }
      } else {
         buffer.append(declaring.computeUniqueKey(false));
         buffer.append(':');
      }

      buffer.append(this.genericTypeSignature());
      int length = buffer.length();
      char[] uniqueKey = new char[length];
      buffer.getChars(0, length, uniqueKey, 0);
      return uniqueKey;
   }

   public char[] constantPoolName() {
      return this.firstBound != null ? this.firstBound.constantPoolName() : this.superclass.constantPoolName();
   }

   public TypeBinding clone(TypeBinding enclosingType) {
      return new TypeVariableBinding(this);
   }

   public String annotatedDebugName() {
      StringBuffer buffer = new StringBuffer(10);
      buffer.append(super.annotatedDebugName());
      if (!this.inRecursiveFunction) {
         this.inRecursiveFunction = true;

         try {
            if (this.superclass != null && TypeBinding.equalsEquals(this.firstBound, this.superclass)) {
               buffer.append(" extends ").append(this.superclass.annotatedDebugName());
            }

            if (this.superInterfaces != null && this.superInterfaces != Binding.NO_SUPERINTERFACES) {
               if (TypeBinding.notEquals(this.firstBound, this.superclass)) {
                  buffer.append(" extends ");
               }

               int i = 0;

               for(int length = this.superInterfaces.length; i < length; ++i) {
                  if (i > 0 || TypeBinding.equalsEquals(this.firstBound, this.superclass)) {
                     buffer.append(" & ");
                  }

                  buffer.append(this.superInterfaces[i].annotatedDebugName());
               }
            }
         } finally {
            this.inRecursiveFunction = false;
         }
      }

      return buffer.toString();
   }

   public String debugName() {
      return this.hasTypeAnnotations() ? super.annotatedDebugName() : new String(this.sourceName);
   }

   public TypeBinding erasure() {
      return (TypeBinding)(this.firstBound != null ? this.firstBound.erasure() : this.superclass);
   }

   public char[] genericSignature() {
      StringBuffer sig = new StringBuffer(10);
      sig.append(this.sourceName).append(':');
      int interfaceLength = this.superInterfaces == null ? 0 : this.superInterfaces.length;
      if ((interfaceLength == 0 || TypeBinding.equalsEquals(this.firstBound, this.superclass)) && this.superclass != null) {
         sig.append(this.superclass.genericTypeSignature());
      }

      int sigLength;
      for(sigLength = 0; sigLength < interfaceLength; ++sigLength) {
         sig.append(':').append(this.superInterfaces[sigLength].genericTypeSignature());
      }

      sigLength = sig.length();
      char[] genericSignature = new char[sigLength];
      sig.getChars(0, sigLength, genericSignature, 0);
      return genericSignature;
   }

   public char[] genericTypeSignature() {
      return this.genericTypeSignature != null ? this.genericTypeSignature : (this.genericTypeSignature = CharOperation.concat('T', this.sourceName, ';'));
   }

   TypeBound[] getTypeBounds(InferenceVariable variable, InferenceSubstitution theta) {
      int n = this.boundsCount();
      if (n == 0) {
         return NO_TYPE_BOUNDS;
      } else {
         TypeBound[] bounds = new TypeBound[n];
         int idx = 0;
         if (!this.firstBound.isInterface()) {
            bounds[idx++] = TypeBound.createBoundOrDependency(theta, this.firstBound, variable);
         }

         for(int i = 0; i < this.superInterfaces.length; ++i) {
            bounds[idx++] = TypeBound.createBoundOrDependency(theta, this.superInterfaces[i], variable);
         }

         return bounds;
      }
   }

   boolean hasOnlyRawBounds() {
      if (this.superclass != null && TypeBinding.equalsEquals(this.firstBound, this.superclass) && !this.superclass.isRawType()) {
         return false;
      } else {
         if (this.superInterfaces != null) {
            int i = 0;

            for(int l = this.superInterfaces.length; i < l; ++i) {
               if (!this.superInterfaces[i].isRawType()) {
                  return false;
               }
            }
         }

         return true;
      }
   }

   public boolean hasTypeBit(int bit) {
      if (this.typeBits == 134217728) {
         this.typeBits = 0;
         if (this.superclass != null && this.superclass.hasTypeBit(-134217729)) {
            this.typeBits |= this.superclass.typeBits & 1811;
         }

         if (this.superInterfaces != null) {
            int i = 0;

            for(int l = this.superInterfaces.length; i < l; ++i) {
               if (this.superInterfaces[i].hasTypeBit(-134217729)) {
                  this.typeBits |= this.superInterfaces[i].typeBits & 1811;
               }
            }
         }
      }

      return (this.typeBits & bit) != 0;
   }

   public boolean isErasureBoundTo(TypeBinding type) {
      if (TypeBinding.equalsEquals(this.superclass.erasure(), type)) {
         return true;
      } else {
         int i = 0;

         for(int length = this.superInterfaces.length; i < length; ++i) {
            if (TypeBinding.equalsEquals(this.superInterfaces[i].erasure(), type)) {
               return true;
            }
         }

         return false;
      }
   }

   public boolean isHierarchyConnected() {
      return (this.modifiers & 33554432) == 0;
   }

   public boolean isInterchangeableWith(TypeVariableBinding otherVariable, Substitution substitute) {
      if (TypeBinding.equalsEquals(this, otherVariable)) {
         return true;
      } else {
         int length = this.superInterfaces.length;
         if (length != otherVariable.superInterfaces.length) {
            return false;
         } else if (TypeBinding.notEquals(this.superclass, Scope.substitute(substitute, (TypeBinding)otherVariable.superclass))) {
            return false;
         } else {
            label36:
            for(int i = 0; i < length; ++i) {
               TypeBinding superType = Scope.substitute(substitute, (TypeBinding)otherVariable.superInterfaces[i]);

               for(int j = 0; j < length; ++j) {
                  if (TypeBinding.equalsEquals(superType, this.superInterfaces[j])) {
                     continue label36;
                  }
               }

               return false;
            }

            return true;
         }
      }
   }

   public boolean isSubtypeOf(TypeBinding other, boolean simulatingBugJDK8026527) {
      if (this.isSubTypeOfRTL(other)) {
         return true;
      } else if (this.firstBound != null && this.firstBound.isSubtypeOf(other, simulatingBugJDK8026527)) {
         return true;
      } else if (this.superclass != null && this.superclass.isSubtypeOf(other, simulatingBugJDK8026527)) {
         return true;
      } else {
         if (this.superInterfaces != null) {
            int i = 0;

            for(int l = this.superInterfaces.length; i < l; ++i) {
               if (this.superInterfaces[i].isSubtypeOf(other, false)) {
                  return true;
               }
            }
         }

         return other.id == 1;
      }
   }

   public boolean enterRecursiveFunction() {
      if (this.inRecursiveFunction) {
         return false;
      } else {
         this.inRecursiveFunction = true;
         return true;
      }
   }

   public void exitRecursiveFunction() {
      this.inRecursiveFunction = false;
   }

   public boolean enterRecursiveProjectionFunction() {
      if (this.inRecursiveProjectionFunction) {
         return false;
      } else {
         this.inRecursiveProjectionFunction = true;
         return true;
      }
   }

   public void exitRecursiveProjectionFunction() {
      this.inRecursiveProjectionFunction = false;
   }

   public boolean isProperType(boolean admitCapture18) {
      if (this.inRecursiveFunction) {
         return true;
      } else {
         this.inRecursiveFunction = true;

         try {
            if (this.superclass != null && !this.superclass.isProperType(admitCapture18)) {
               return false;
            }

            if (this.superInterfaces != null) {
               int i = 0;

               for(int l = this.superInterfaces.length; i < l; ++i) {
                  if (!this.superInterfaces[i].isProperType(admitCapture18)) {
                     return false;
                  }
               }
            }
         } finally {
            this.inRecursiveFunction = false;
         }

         return true;
      }
   }

   TypeBinding substituteInferenceVariable(InferenceVariable var, TypeBinding substituteType) {
      if (this.inRecursiveFunction) {
         return this;
      } else {
         this.inRecursiveFunction = true;

         TypeVariableBinding var10;
         try {
            boolean haveSubstitution = false;
            ReferenceBinding currentSuperclass = this.superclass;
            if (currentSuperclass != null) {
               currentSuperclass = (ReferenceBinding)currentSuperclass.substituteInferenceVariable(var, substituteType);
               haveSubstitution |= TypeBinding.notEquals(currentSuperclass, this.superclass);
            }

            ReferenceBinding[] currentSuperInterfaces = null;
            if (this.superInterfaces != null) {
               int length = this.superInterfaces.length;
               if (haveSubstitution) {
                  System.arraycopy(this.superInterfaces, 0, currentSuperInterfaces = new ReferenceBinding[length], 0, length);
               }

               for(int i = 0; i < length; ++i) {
                  ReferenceBinding currentSuperInterface = this.superInterfaces[i];
                  if (currentSuperInterface != null) {
                     currentSuperInterface = (ReferenceBinding)currentSuperInterface.substituteInferenceVariable(var, substituteType);
                     if (TypeBinding.notEquals(currentSuperInterface, this.superInterfaces[i])) {
                        if (currentSuperInterfaces == null) {
                           System.arraycopy(this.superInterfaces, 0, currentSuperInterfaces = new ReferenceBinding[length], 0, length);
                        }

                        currentSuperInterfaces[i] = currentSuperInterface;
                        haveSubstitution = true;
                     }
                  }
               }
            }

            if (!haveSubstitution) {
               var10 = this;
               return var10;
            }

            TypeVariableBinding newVar = new TypeVariableBinding(this.sourceName, this.declaringElement, this.rank, this.environment);
            newVar.superclass = currentSuperclass;
            newVar.superInterfaces = currentSuperInterfaces;
            newVar.tagBits = this.tagBits;
            var10 = newVar;
         } finally {
            this.inRecursiveFunction = false;
         }

         return var10;
      }
   }

   public boolean isTypeVariable() {
      return true;
   }

   public int kind() {
      return 4100;
   }

   public boolean mentionsAny(TypeBinding[] parameters, int idx) {
      if (this.inRecursiveFunction) {
         return false;
      } else {
         this.inRecursiveFunction = true;

         try {
            if (!super.mentionsAny(parameters, idx)) {
               if (this.superclass != null && this.superclass.mentionsAny(parameters, idx)) {
                  return true;
               }

               if (this.superInterfaces == null) {
                  return false;
               }

               for(int j = 0; j < this.superInterfaces.length; ++j) {
                  if (this.superInterfaces[j].mentionsAny(parameters, idx)) {
                     return true;
                  }
               }

               return false;
            }
         } finally {
            this.inRecursiveFunction = false;
         }

         return true;
      }
   }

   void collectInferenceVariables(Set variables) {
      if (!this.inRecursiveFunction) {
         this.inRecursiveFunction = true;

         try {
            if (this.superclass != null) {
               this.superclass.collectInferenceVariables(variables);
            }

            if (this.superInterfaces != null) {
               for(int j = 0; j < this.superInterfaces.length; ++j) {
                  this.superInterfaces[j].collectInferenceVariables(variables);
               }
            }
         } finally {
            this.inRecursiveFunction = false;
         }

      }
   }

   public TypeBinding[] otherUpperBounds() {
      if (this.firstBound == null) {
         return Binding.NO_TYPES;
      } else if (TypeBinding.equalsEquals(this.firstBound, this.superclass)) {
         return this.superInterfaces;
      } else {
         int otherLength = this.superInterfaces.length - 1;
         if (otherLength > 0) {
            TypeBinding[] otherBounds;
            System.arraycopy(this.superInterfaces, 1, otherBounds = new TypeBinding[otherLength], 0, otherLength);
            return otherBounds;
         } else {
            return Binding.NO_TYPES;
         }
      }
   }

   public char[] readableName() {
      return this.sourceName;
   }

   ReferenceBinding resolve() {
      if ((this.modifiers & 33554432) == 0) {
         return this;
      } else {
         long nullTagBits = this.tagBits & 108086391056891904L;
         TypeBinding oldSuperclass = this.superclass;
         TypeBinding oldFirstInterface = null;
         if (this.superclass != null) {
            ReferenceBinding resolveType = (ReferenceBinding)BinaryTypeBinding.resolveType(this.superclass, this.environment, true);
            this.tagBits |= resolveType.tagBits & 2048L;
            long superNullTagBits = resolveType.tagBits & 108086391056891904L;
            if (superNullTagBits != 0L && nullTagBits == 0L && (superNullTagBits & 72057594037927936L) != 0L) {
               nullTagBits = superNullTagBits;
            }

            this.setSuperClass(resolveType);
         }

         ReferenceBinding[] interfaces = this.superInterfaces;
         int length;
         if ((length = interfaces.length) != 0) {
            oldFirstInterface = interfaces[0];
            int i = length;

            while(true) {
               --i;
               if (i < 0) {
                  break;
               }

               ReferenceBinding resolveType = (ReferenceBinding)BinaryTypeBinding.resolveType(interfaces[i], this.environment, true);
               this.tagBits |= resolveType.tagBits & 2048L;
               long superNullTagBits = resolveType.tagBits & 108086391056891904L;
               if (superNullTagBits != 0L && nullTagBits == 0L && (superNullTagBits & 72057594037927936L) != 0L) {
                  nullTagBits = superNullTagBits;
               }

               interfaces[i] = resolveType;
            }
         }

         if (nullTagBits != 0L) {
            this.tagBits |= nullTagBits | 1048576L;
         }

         if (this.firstBound != null) {
            if (TypeBinding.equalsEquals(this.firstBound, oldSuperclass)) {
               this.setFirstBound(this.superclass);
            } else if (TypeBinding.equalsEquals(this.firstBound, oldFirstInterface)) {
               this.setFirstBound(interfaces[0]);
            }
         }

         this.modifiers &= -33554433;
         return this;
      }
   }

   public void setTypeAnnotations(AnnotationBinding[] annotations, boolean evalNullAnnotations) {
      if (this.getClass() == TypeVariableBinding.class) {
         this.environment.typeSystem.forceRegisterAsDerived(this);
      } else {
         this.environment.getUnannotatedType(this);
      }

      super.setTypeAnnotations(annotations, evalNullAnnotations);
   }

   public char[] shortReadableName() {
      return this.readableName();
   }

   public ReferenceBinding superclass() {
      return this.superclass;
   }

   public ReferenceBinding[] superInterfaces() {
      return this.superInterfaces;
   }

   public String toString() {
      if (this.hasTypeAnnotations()) {
         return this.annotatedDebugName();
      } else {
         StringBuffer buffer = new StringBuffer(10);
         buffer.append('<').append(this.sourceName);
         if (this.superclass != null && TypeBinding.equalsEquals(this.firstBound, this.superclass)) {
            buffer.append(" extends ").append(this.superclass.debugName());
         }

         if (this.superInterfaces != null && this.superInterfaces != Binding.NO_SUPERINTERFACES) {
            if (TypeBinding.notEquals(this.firstBound, this.superclass)) {
               buffer.append(" extends ");
            }

            int i = 0;

            for(int length = this.superInterfaces.length; i < length; ++i) {
               if (i > 0 || TypeBinding.equalsEquals(this.firstBound, this.superclass)) {
                  buffer.append(" & ");
               }

               buffer.append(this.superInterfaces[i].debugName());
            }
         }

         buffer.append('>');
         return buffer.toString();
      }
   }

   public char[] nullAnnotatedReadableName(CompilerOptions options, boolean shortNames) {
      StringBuffer nameBuffer = new StringBuffer(10);
      this.appendNullAnnotation(nameBuffer, options);
      nameBuffer.append(this.sourceName());
      int i;
      if (!this.inRecursiveFunction) {
         this.inRecursiveFunction = true;

         try {
            if (this.superclass != null && TypeBinding.equalsEquals(this.firstBound, this.superclass)) {
               nameBuffer.append(" extends ").append(this.superclass.nullAnnotatedReadableName(options, shortNames));
            }

            if (this.superInterfaces != null && this.superInterfaces != Binding.NO_SUPERINTERFACES) {
               if (TypeBinding.notEquals(this.firstBound, this.superclass)) {
                  nameBuffer.append(" extends ");
               }

               i = 0;

               for(int length = this.superInterfaces.length; i < length; ++i) {
                  if (i > 0 || TypeBinding.equalsEquals(this.firstBound, this.superclass)) {
                     nameBuffer.append(" & ");
                  }

                  nameBuffer.append(this.superInterfaces[i].nullAnnotatedReadableName(options, shortNames));
               }
            }
         } finally {
            this.inRecursiveFunction = false;
         }
      }

      i = nameBuffer.length();
      char[] readableName = new char[i];
      nameBuffer.getChars(0, i, readableName, 0);
      return readableName;
   }

   protected void appendNullAnnotation(StringBuffer nameBuffer, CompilerOptions options) {
      int oldSize = nameBuffer.length();
      super.appendNullAnnotation(nameBuffer, options);
      if (oldSize == nameBuffer.length() && this.hasNullTypeAnnotations()) {
         TypeVariableBinding[] typeVariables = null;
         if (this.declaringElement instanceof ReferenceBinding) {
            typeVariables = ((ReferenceBinding)this.declaringElement).typeVariables();
         } else if (this.declaringElement instanceof MethodBinding) {
            typeVariables = ((MethodBinding)this.declaringElement).typeVariables();
         }

         if (typeVariables != null && typeVariables.length > this.rank) {
            TypeVariableBinding prototype = typeVariables[this.rank];
            if (prototype != this) {
               prototype.appendNullAnnotation(nameBuffer, options);
            }
         }
      }

   }

   public TypeBinding unannotated() {
      return (TypeBinding)(this.hasTypeAnnotations() ? this.environment.getUnannotatedType(this) : this);
   }

   public TypeBinding withoutToplevelNullAnnotation() {
      if (!this.hasNullTypeAnnotations()) {
         return this;
      } else {
         TypeBinding unannotated = this.environment.getUnannotatedType(this);
         AnnotationBinding[] newAnnotations = this.environment.filterNullTypeAnnotations(this.typeAnnotations);
         return newAnnotations.length > 0 ? this.environment.createAnnotatedType(unannotated, newAnnotations) : unannotated;
      }
   }

   public TypeBinding upperBound() {
      return (TypeBinding)(this.firstBound != null ? this.firstBound : this.superclass);
   }

   public TypeBinding[] allUpperBounds() {
      if (this.superclass == null) {
         return this.superInterfaces;
      } else if (this.superInterfaces != null && this.superInterfaces.length != 0) {
         int nInterfaces = this.superInterfaces.length;
         TypeBinding[] all = (TypeBinding[])Arrays.copyOf(this.superInterfaces, nInterfaces + 1);
         all[nInterfaces] = this.superclass;
         return all;
      } else {
         return new TypeBinding[]{this.superclass};
      }
   }

   public void evaluateNullAnnotations(Scope scope, TypeParameter parameter) {
      long nullTagBits = NullAnnotationMatching.validNullTagBits(this.tagBits);
      if (this.firstBound != null && this.firstBound.isValidBinding()) {
         long superNullTagBits = NullAnnotationMatching.validNullTagBits(this.firstBound.tagBits);
         if (superNullTagBits != 0L) {
            if (nullTagBits == 0L) {
               if ((superNullTagBits & 72057594037927936L) != 0L) {
                  nullTagBits = superNullTagBits;
               }
            } else if (superNullTagBits != nullTagBits && parameter != null) {
               this.firstBound = this.nullMismatchOnBound(parameter, this.firstBound, superNullTagBits, nullTagBits, scope);
            }
         }
      }

      ReferenceBinding[] interfaces = this.superInterfaces;
      int length;
      if (interfaces != null && (length = interfaces.length) != 0) {
         int i = length;

         while(true) {
            --i;
            if (i < 0) {
               break;
            }

            ReferenceBinding resolveType = interfaces[i];
            long superNullTagBits = NullAnnotationMatching.validNullTagBits(resolveType.tagBits);
            if (superNullTagBits != 0L) {
               if (nullTagBits == 0L) {
                  if ((superNullTagBits & 72057594037927936L) != 0L) {
                     nullTagBits = superNullTagBits;
                  }
               } else if (superNullTagBits != nullTagBits && parameter != null) {
                  interfaces[i] = (ReferenceBinding)this.nullMismatchOnBound(parameter, resolveType, superNullTagBits, nullTagBits, scope);
               }
            }
         }
      }

      if (nullTagBits != 0L) {
         this.tagBits |= nullTagBits | 1048576L;
      }

   }

   private TypeBinding nullMismatchOnBound(TypeParameter parameter, TypeBinding boundType, long superNullTagBits, long nullTagBits, Scope scope) {
      TypeReference bound = this.findBound(boundType, parameter);
      Annotation ann = bound.findAnnotation(superNullTagBits);
      if (ann != null) {
         scope.problemReporter().contradictoryNullAnnotationsOnBounds(ann, nullTagBits);
         this.tagBits &= -108086391056891905L;
         return boundType;
      } else {
         return boundType.withoutToplevelNullAnnotation();
      }
   }

   private TypeReference findBound(TypeBinding bound, TypeParameter parameter) {
      if (parameter.type != null && TypeBinding.equalsEquals(parameter.type.resolvedType, bound)) {
         return parameter.type;
      } else {
         TypeReference[] bounds = parameter.bounds;
         if (bounds != null) {
            for(int i = 0; i < bounds.length; ++i) {
               if (TypeBinding.equalsEquals(bounds[i].resolvedType, bound)) {
                  return bounds[i];
               }
            }
         }

         return null;
      }
   }

   public TypeBinding setFirstBound(TypeBinding firstBound) {
      this.firstBound = firstBound;
      if ((this.tagBits & 8388608L) != 0L) {
         TypeBinding[] annotatedTypes = this.getDerivedTypesForDeferredInitialization();
         int i = 0;

         for(int length = annotatedTypes == null ? 0 : annotatedTypes.length; i < length; ++i) {
            TypeVariableBinding annotatedType = (TypeVariableBinding)annotatedTypes[i];
            annotatedType.firstBound = firstBound;
         }
      }

      if (firstBound != null && firstBound.hasNullTypeAnnotations()) {
         this.tagBits |= 1048576L;
      }

      return firstBound;
   }

   public ReferenceBinding setSuperClass(ReferenceBinding superclass) {
      this.superclass = superclass;
      if ((this.tagBits & 8388608L) != 0L) {
         TypeBinding[] annotatedTypes = this.getDerivedTypesForDeferredInitialization();
         int i = 0;

         for(int length = annotatedTypes == null ? 0 : annotatedTypes.length; i < length; ++i) {
            TypeVariableBinding annotatedType = (TypeVariableBinding)annotatedTypes[i];
            annotatedType.superclass = superclass;
         }
      }

      return superclass;
   }

   public ReferenceBinding[] setSuperInterfaces(ReferenceBinding[] superInterfaces) {
      this.superInterfaces = superInterfaces;
      if ((this.tagBits & 8388608L) != 0L) {
         TypeBinding[] annotatedTypes = this.getDerivedTypesForDeferredInitialization();
         int i = 0;

         for(int length = annotatedTypes == null ? 0 : annotatedTypes.length; i < length; ++i) {
            TypeVariableBinding annotatedType = (TypeVariableBinding)annotatedTypes[i];
            annotatedType.superInterfaces = superInterfaces;
         }
      }

      return superInterfaces;
   }

   protected TypeBinding[] getDerivedTypesForDeferredInitialization() {
      return this.environment.getAnnotatedTypes(this);
   }

   public TypeBinding combineTypeAnnotations(TypeBinding substitute) {
      if (this.hasTypeAnnotations()) {
         if (this.hasRelevantTypeUseNullAnnotations()) {
            substitute = substitute.withoutToplevelNullAnnotation();
         }

         if (this.typeAnnotations != Binding.NO_ANNOTATIONS) {
            return this.environment.createAnnotatedType(substitute, this.typeAnnotations);
         }
      }

      return substitute;
   }

   private boolean hasRelevantTypeUseNullAnnotations() {
      TypeVariableBinding[] parameters;
      if (this.declaringElement instanceof ReferenceBinding) {
         parameters = ((ReferenceBinding)this.declaringElement).original().typeVariables();
      } else {
         if (!(this.declaringElement instanceof MethodBinding)) {
            throw new IllegalStateException("Unexpected declaring element:" + String.valueOf(this.declaringElement.readableName()));
         }

         parameters = ((MethodBinding)this.declaringElement).original().typeVariables;
      }

      TypeVariableBinding parameter = parameters[this.rank];
      long currentNullBits = this.tagBits & 108086391056891904L;
      long declarationNullBits = parameter.tagBits & 108086391056891904L;
      return (currentNullBits & ~declarationNullBits) != 0L;
   }

   public boolean acceptsNonNullDefault() {
      return false;
   }

   public long updateTagBits() {
      if (!this.inRecursiveFunction) {
         this.inRecursiveFunction = true;

         try {
            if (this.superclass != null) {
               this.tagBits |= this.superclass.updateTagBits();
            }

            if (this.superInterfaces != null) {
               ReferenceBinding[] var4;
               int var3 = (var4 = this.superInterfaces).length;

               for(int var2 = 0; var2 < var3; ++var2) {
                  TypeBinding superIfc = var4[var2];
                  this.tagBits |= superIfc.updateTagBits();
               }
            }
         } finally {
            this.inRecursiveFunction = false;
         }
      }

      return super.updateTagBits();
   }

   public boolean isFreeTypeVariable() {
      return this.environment.usesNullTypeAnnotations() && this.environment.globalOptions.pessimisticNullAnalysisForFreeTypeVariablesEnabled && (this.tagBits & 108086391056891904L) == 0L;
   }

   public ReferenceBinding upwardsProjection(Scope scope, TypeBinding[] mentionedTypeVariables) {
      return this;
   }

   public ReferenceBinding downwardsProjection(Scope scope, TypeBinding[] mentionedTypeVariables) {
      return this;
   }
}
