package com.bea.core.repackaged.jdt.internal.compiler.lookup;

import com.bea.core.repackaged.jdt.core.compiler.CharOperation;
import com.bea.core.repackaged.jdt.internal.compiler.ast.Annotation;
import com.bea.core.repackaged.jdt.internal.compiler.ast.Wildcard;
import com.bea.core.repackaged.jdt.internal.compiler.impl.CompilerOptions;
import java.util.List;
import java.util.Set;

public class WildcardBinding extends ReferenceBinding {
   public ReferenceBinding genericType;
   public int rank;
   public TypeBinding bound;
   public TypeBinding[] otherBounds;
   char[] genericSignature;
   public int boundKind;
   ReferenceBinding superclass;
   ReferenceBinding[] superInterfaces;
   TypeVariableBinding typeVariable;
   LookupEnvironment environment;
   boolean inRecursiveFunction = false;

   public WildcardBinding(ReferenceBinding genericType, int rank, TypeBinding bound, TypeBinding[] otherBounds, int boundKind, LookupEnvironment environment) {
      this.rank = rank;
      this.boundKind = boundKind;
      this.modifiers = 1073741825;
      this.environment = environment;
      this.initialize(genericType, bound, otherBounds);
      if (genericType instanceof UnresolvedReferenceBinding) {
         ((UnresolvedReferenceBinding)genericType).addWrapper(this, environment);
      }

      if (bound instanceof UnresolvedReferenceBinding) {
         ((UnresolvedReferenceBinding)bound).addWrapper(this, environment);
      }

      this.tagBits |= 16777216L;
      this.typeBits = 134217728;
   }

   TypeBinding bound() {
      return this.bound;
   }

   int boundKind() {
      return this.boundKind;
   }

   public TypeBinding allBounds() {
      if (this.otherBounds != null && this.otherBounds.length != 0) {
         ReferenceBinding[] allBounds = new ReferenceBinding[this.otherBounds.length + 1];

         try {
            allBounds[0] = (ReferenceBinding)this.bound;
            System.arraycopy(this.otherBounds, 0, allBounds, 1, this.otherBounds.length);
         } catch (ClassCastException var2) {
            return this.bound;
         } catch (ArrayStoreException var3) {
            return this.bound;
         }

         return this.environment.createIntersectionType18(allBounds);
      } else {
         return this.bound;
      }
   }

   public void setTypeAnnotations(AnnotationBinding[] annotations, boolean evalNullAnnotations) {
      this.tagBits |= 2097152L;
      if (annotations != null && annotations.length != 0) {
         this.typeAnnotations = annotations;
      }

      if (evalNullAnnotations) {
         this.evaluateNullAnnotations((Scope)null, (Wildcard)null);
      }

   }

   public void evaluateNullAnnotations(Scope scope, Wildcard wildcard) {
      long nullTagBits = this.determineNullBitsFromDeclaration(scope, wildcard);
      if (nullTagBits == 0L) {
         TypeVariableBinding typeVariable2 = this.typeVariable();
         if (typeVariable2 != null) {
            long typeVariableNullTagBits = typeVariable2.tagBits & 108086391056891904L;
            if (typeVariableNullTagBits != 0L) {
               nullTagBits = typeVariableNullTagBits;
            }
         }
      }

      if (nullTagBits != 0L) {
         this.tagBits = this.tagBits & -108086391056891905L | nullTagBits | 1048576L;
      }

   }

   public long determineNullBitsFromDeclaration(Scope scope, Wildcard wildcard) {
      long nullTagBits = 0L;
      AnnotationBinding[] annotations = this.typeAnnotations;
      if (annotations != null) {
         int i = 0;

         for(int length = annotations.length; i < length; ++i) {
            AnnotationBinding annotation = annotations[i];
            if (annotation != null) {
               Annotation annotation1;
               if (annotation.type.hasNullBit(64)) {
                  if ((nullTagBits & 72057594037927936L) == 0L) {
                     nullTagBits |= 36028797018963968L;
                  } else if (wildcard != null) {
                     annotation1 = wildcard.findAnnotation(36028797018963968L);
                     if (annotation1 != null) {
                        scope.problemReporter().contradictoryNullAnnotations(annotation1);
                     }
                  }
               } else if (annotation.type.hasNullBit(32)) {
                  if ((nullTagBits & 36028797018963968L) == 0L) {
                     nullTagBits |= 72057594037927936L;
                  } else if (wildcard != null) {
                     annotation1 = wildcard.findAnnotation(72057594037927936L);
                     if (annotation1 != null) {
                        scope.problemReporter().contradictoryNullAnnotations(annotation1);
                     }
                  }
               }
            }
         }
      }

      if (this.bound != null && this.bound.isValidBinding()) {
         long boundNullTagBits = this.bound.tagBits & 108086391056891904L;
         if (boundNullTagBits != 0L) {
            Annotation annotation;
            TypeBinding newBound;
            if (this.boundKind == 2) {
               if ((boundNullTagBits & 36028797018963968L) != 0L) {
                  if (nullTagBits == 0L) {
                     nullTagBits = 36028797018963968L;
                  } else if (wildcard != null && (nullTagBits & 72057594037927936L) != 0L) {
                     annotation = wildcard.bound.findAnnotation(boundNullTagBits);
                     if (annotation == null) {
                        newBound = this.bound.withoutToplevelNullAnnotation();
                        this.bound = newBound;
                        wildcard.bound.resolvedType = newBound;
                     } else {
                        scope.problemReporter().contradictoryNullAnnotationsOnBounds(annotation, nullTagBits);
                     }
                  }
               }
            } else {
               if ((boundNullTagBits & 72057594037927936L) != 0L) {
                  if (nullTagBits == 0L) {
                     nullTagBits = 72057594037927936L;
                  } else if (wildcard != null && (nullTagBits & 36028797018963968L) != 0L) {
                     annotation = wildcard.bound.findAnnotation(boundNullTagBits);
                     if (annotation == null) {
                        newBound = this.bound.withoutToplevelNullAnnotation();
                        this.bound = newBound;
                        wildcard.bound.resolvedType = newBound;
                     } else {
                        scope.problemReporter().contradictoryNullAnnotationsOnBounds(annotation, nullTagBits);
                     }
                  }
               }

               if (nullTagBits == 0L && this.otherBounds != null) {
                  int i = 0;

                  for(int length = this.otherBounds.length; i < length; ++i) {
                     if ((this.otherBounds[i].tagBits & 72057594037927936L) != 0L) {
                        nullTagBits = 72057594037927936L;
                        break;
                     }
                  }
               }
            }
         }
      }

      return nullTagBits;
   }

   public ReferenceBinding actualType() {
      return this.genericType;
   }

   TypeBinding[] additionalBounds() {
      return this.otherBounds;
   }

   public int kind() {
      return this.otherBounds == null ? 516 : 8196;
   }

   public boolean boundCheck(TypeBinding argumentType) {
      switch (this.boundKind) {
         case 0:
            return true;
         case 1:
            if (!argumentType.isCompatibleWith(this.bound)) {
               return false;
            } else {
               int i = 0;

               for(int length = this.otherBounds == null ? 0 : this.otherBounds.length; i < length; ++i) {
                  if (!argumentType.isCompatibleWith(this.otherBounds[i])) {
                     return false;
                  }
               }

               return true;
            }
         default:
            return argumentType.isCompatibleWith(this.bound);
      }
   }

   public boolean canBeInstantiated() {
      return false;
   }

   public List collectMissingTypes(List missingTypes) {
      if ((this.tagBits & 128L) != 0L) {
         missingTypes = this.bound.collectMissingTypes(missingTypes);
      }

      return missingTypes;
   }

   public void collectSubstitutes(Scope scope, TypeBinding actualType, InferenceContext inferenceContext, int constraint) {
      if ((this.tagBits & 536870912L) != 0L) {
         if (actualType != TypeBinding.NULL && ((TypeBinding)actualType).kind() != 65540) {
            if (((TypeBinding)actualType).isCapture()) {
               CaptureBinding capture = (CaptureBinding)actualType;
               actualType = capture.wildcard;
            }

            int i;
            int length;
            int length;
            WildcardBinding actualWildcard;
            WildcardBinding actualIntersection;
            label205:
            switch (constraint) {
               case 0:
                  switch (this.boundKind) {
                     case 0:
                     default:
                        return;
                     case 1:
                        switch (((TypeBinding)actualType).kind()) {
                           case 516:
                              actualWildcard = (WildcardBinding)actualType;
                              switch (actualWildcard.boundKind) {
                                 case 0:
                                 case 2:
                                 default:
                                    return;
                                 case 1:
                                    this.bound.collectSubstitutes(scope, actualWildcard.bound, inferenceContext, 0);
                                    i = 0;

                                    for(length = actualWildcard.otherBounds == null ? 0 : actualWildcard.otherBounds.length; i < length; ++i) {
                                       this.bound.collectSubstitutes(scope, actualWildcard.otherBounds[i], inferenceContext, 0);
                                    }

                                    return;
                              }
                           case 8196:
                              actualIntersection = (WildcardBinding)actualType;
                              this.bound.collectSubstitutes(scope, actualIntersection.bound, inferenceContext, 0);
                              length = 0;

                              for(length = actualIntersection.otherBounds == null ? 0 : actualIntersection.otherBounds.length; length < length; ++length) {
                                 this.bound.collectSubstitutes(scope, actualIntersection.otherBounds[length], inferenceContext, 0);
                              }
                              break label205;
                           default:
                              return;
                        }
                     case 2:
                        switch (((TypeBinding)actualType).kind()) {
                           case 516:
                              actualWildcard = (WildcardBinding)actualType;
                              switch (actualWildcard.boundKind) {
                                 case 0:
                                 case 1:
                                 default:
                                    return;
                                 case 2:
                                    this.bound.collectSubstitutes(scope, actualWildcard.bound, inferenceContext, 0);
                                    i = 0;

                                    for(length = actualWildcard.otherBounds == null ? 0 : actualWildcard.otherBounds.length; i < length; ++i) {
                                       this.bound.collectSubstitutes(scope, actualWildcard.otherBounds[i], inferenceContext, 0);
                                    }
                                    break label205;
                              }
                           case 8196:
                           default:
                              return;
                        }
                  }
               case 1:
                  switch (this.boundKind) {
                     case 0:
                     default:
                        return;
                     case 1:
                        switch (((TypeBinding)actualType).kind()) {
                           case 516:
                              actualWildcard = (WildcardBinding)actualType;
                              switch (actualWildcard.boundKind) {
                                 case 0:
                                 case 2:
                                 default:
                                    return;
                                 case 1:
                                    this.bound.collectSubstitutes(scope, actualWildcard.bound, inferenceContext, 1);
                                    return;
                              }
                           case 8196:
                              actualIntersection = (WildcardBinding)actualType;
                              this.bound.collectSubstitutes(scope, actualIntersection.bound, inferenceContext, 1);
                              length = 0;

                              for(length = actualIntersection.otherBounds.length; length < length; ++length) {
                                 this.bound.collectSubstitutes(scope, actualIntersection.otherBounds[length], inferenceContext, 1);
                              }

                              return;
                           default:
                              this.bound.collectSubstitutes(scope, (TypeBinding)actualType, inferenceContext, 1);
                              return;
                        }
                     case 2:
                        switch (((TypeBinding)actualType).kind()) {
                           case 516:
                              actualWildcard = (WildcardBinding)actualType;
                              switch (actualWildcard.boundKind) {
                                 case 0:
                                 case 1:
                                 default:
                                    return;
                                 case 2:
                                    this.bound.collectSubstitutes(scope, actualWildcard.bound, inferenceContext, 2);
                                    i = 0;

                                    for(length = actualWildcard.otherBounds == null ? 0 : actualWildcard.otherBounds.length; i < length; ++i) {
                                       this.bound.collectSubstitutes(scope, actualWildcard.otherBounds[i], inferenceContext, 2);
                                    }
                                    break label205;
                              }
                           case 8196:
                              return;
                           default:
                              this.bound.collectSubstitutes(scope, (TypeBinding)actualType, inferenceContext, 2);
                              return;
                        }
                  }
               case 2:
                  label152:
                  switch (this.boundKind) {
                     case 0:
                     default:
                        break;
                     case 1:
                        switch (((TypeBinding)actualType).kind()) {
                           case 516:
                              actualWildcard = (WildcardBinding)actualType;
                              switch (actualWildcard.boundKind) {
                                 case 0:
                                 case 2:
                                 default:
                                    return;
                                 case 1:
                                    this.bound.collectSubstitutes(scope, actualWildcard.bound, inferenceContext, 2);
                                    i = 0;

                                    for(length = actualWildcard.otherBounds == null ? 0 : actualWildcard.otherBounds.length; i < length; ++i) {
                                       this.bound.collectSubstitutes(scope, actualWildcard.otherBounds[i], inferenceContext, 2);
                                    }

                                    return;
                              }
                           case 8196:
                              actualIntersection = (WildcardBinding)actualType;
                              this.bound.collectSubstitutes(scope, actualIntersection.bound, inferenceContext, 2);
                              length = 0;

                              for(length = actualIntersection.otherBounds == null ? 0 : actualIntersection.otherBounds.length; length < length; ++length) {
                                 this.bound.collectSubstitutes(scope, actualIntersection.otherBounds[length], inferenceContext, 2);
                              }
                              break label152;
                           default:
                              return;
                        }
                     case 2:
                        switch (((TypeBinding)actualType).kind()) {
                           case 516:
                              actualWildcard = (WildcardBinding)actualType;
                              switch (actualWildcard.boundKind) {
                                 case 0:
                                 case 1:
                                 default:
                                    break;
                                 case 2:
                                    this.bound.collectSubstitutes(scope, actualWildcard.bound, inferenceContext, 2);
                                    i = 0;

                                    for(length = actualWildcard.otherBounds == null ? 0 : actualWildcard.otherBounds.length; i < length; ++i) {
                                       this.bound.collectSubstitutes(scope, actualWildcard.otherBounds[i], inferenceContext, 2);
                                    }
                              }
                           case 8196:
                        }
                  }
            }

         }
      }
   }

   public char[] computeUniqueKey(boolean isLeaf) {
      char[] genericTypeKey = this.genericType.computeUniqueKey(false);
      char[] rankComponent = ('{' + String.valueOf(this.rank) + '}').toCharArray();
      char[] wildCardKey;
      switch (this.boundKind) {
         case 0:
            wildCardKey = TypeConstants.WILDCARD_STAR;
            break;
         case 1:
            wildCardKey = CharOperation.concat(TypeConstants.WILDCARD_PLUS, this.bound.computeUniqueKey(false));
            break;
         default:
            wildCardKey = CharOperation.concat(TypeConstants.WILDCARD_MINUS, this.bound.computeUniqueKey(false));
      }

      return CharOperation.concat(genericTypeKey, rankComponent, wildCardKey);
   }

   public char[] constantPoolName() {
      return this.erasure().constantPoolName();
   }

   public TypeBinding clone(TypeBinding immaterial) {
      return new WildcardBinding(this.genericType, this.rank, this.bound, this.otherBounds, this.boundKind, this.environment);
   }

   public String annotatedDebugName() {
      StringBuffer buffer = new StringBuffer(16);
      AnnotationBinding[] annotations = this.getTypeAnnotations();
      int i = 0;

      int length;
      for(length = annotations == null ? 0 : annotations.length; i < length; ++i) {
         buffer.append(annotations[i]);
         buffer.append(' ');
      }

      switch (this.boundKind) {
         case 0:
            return buffer.append(TypeConstants.WILDCARD_NAME).toString();
         case 1:
            if (this.otherBounds == null) {
               return buffer.append(CharOperation.concat(TypeConstants.WILDCARD_NAME, TypeConstants.WILDCARD_EXTENDS, this.bound.annotatedDebugName().toCharArray())).toString();
            }

            buffer.append(this.bound.annotatedDebugName());
            i = 0;

            for(length = this.otherBounds.length; i < length; ++i) {
               buffer.append(" & ").append(this.otherBounds[i].annotatedDebugName());
            }

            return buffer.toString();
         default:
            return buffer.append(CharOperation.concat(TypeConstants.WILDCARD_NAME, TypeConstants.WILDCARD_SUPER, this.bound.annotatedDebugName().toCharArray())).toString();
      }
   }

   public String debugName() {
      return this.toString();
   }

   public TypeBinding erasure() {
      if (this.otherBounds == null) {
         if (this.boundKind == 1) {
            return this.bound.erasure();
         } else {
            TypeVariableBinding var = this.typeVariable();
            return (TypeBinding)(var != null ? var.erasure() : this.genericType);
         }
      } else {
         return this.bound.id == 1 ? this.otherBounds[0].erasure() : this.bound.erasure();
      }
   }

   public char[] genericTypeSignature() {
      if (this.genericSignature == null) {
         switch (this.boundKind) {
            case 0:
               this.genericSignature = TypeConstants.WILDCARD_STAR;
               break;
            case 1:
               this.genericSignature = CharOperation.concat(TypeConstants.WILDCARD_PLUS, this.bound.genericTypeSignature());
               break;
            default:
               this.genericSignature = CharOperation.concat(TypeConstants.WILDCARD_MINUS, this.bound.genericTypeSignature());
         }
      }

      return this.genericSignature;
   }

   public int hashCode() {
      return this.genericType.hashCode();
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

   void initialize(ReferenceBinding someGenericType, TypeBinding someBound, TypeBinding[] someOtherBounds) {
      this.genericType = someGenericType;
      this.bound = someBound;
      this.otherBounds = someOtherBounds;
      if (someGenericType != null) {
         this.fPackage = someGenericType.getPackage();
      }

      if (someBound != null) {
         this.tagBits |= someBound.tagBits & 2305843009751615616L;
      }

      if (someOtherBounds != null) {
         int i = 0;

         for(int max = someOtherBounds.length; i < max; ++i) {
            TypeBinding someOtherBound = someOtherBounds[i];
            this.tagBits |= someOtherBound.tagBits & 2305843009214744576L;
         }
      }

   }

   public boolean isSuperclassOf(ReferenceBinding otherType) {
      if (this.boundKind == 2) {
         if (this.bound instanceof ReferenceBinding) {
            return ((ReferenceBinding)this.bound).isSuperclassOf(otherType);
         } else {
            return otherType.id == 1;
         }
      } else {
         return false;
      }
   }

   public boolean isIntersectionType() {
      return this.otherBounds != null;
   }

   public ReferenceBinding[] getIntersectingTypes() {
      if (this.isIntersectionType()) {
         ReferenceBinding[] allBounds = new ReferenceBinding[this.otherBounds.length + 1];

         try {
            allBounds[0] = (ReferenceBinding)this.bound;
            System.arraycopy(this.otherBounds, 0, allBounds, 1, this.otherBounds.length);
            return allBounds;
         } catch (ClassCastException var2) {
            return null;
         } catch (ArrayStoreException var3) {
            return null;
         }
      } else {
         return null;
      }
   }

   public boolean isHierarchyConnected() {
      return this.superclass != null && this.superInterfaces != null;
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

   public boolean isProperType(boolean admitCapture18) {
      if (this.inRecursiveFunction) {
         return true;
      } else {
         this.inRecursiveFunction = true;

         try {
            if (this.bound != null && !this.bound.isProperType(admitCapture18)) {
               return false;
            }

            if (this.superclass == null || this.superclass.isProperType(admitCapture18)) {
               if (this.superInterfaces == null) {
                  return true;
               }

               int i = 0;

               for(int l = this.superInterfaces.length; i < l; ++i) {
                  if (!this.superInterfaces[i].isProperType(admitCapture18)) {
                     return false;
                  }
               }

               return true;
            }
         } finally {
            this.inRecursiveFunction = false;
         }

         return false;
      }
   }

   TypeBinding substituteInferenceVariable(InferenceVariable var, TypeBinding substituteType) {
      boolean haveSubstitution = false;
      TypeBinding currentBound = this.bound;
      if (currentBound != null) {
         currentBound = currentBound.substituteInferenceVariable(var, substituteType);
         haveSubstitution |= TypeBinding.notEquals(currentBound, this.bound);
      }

      TypeBinding[] currentOtherBounds = null;
      if (this.otherBounds != null) {
         int length = this.otherBounds.length;
         if (haveSubstitution) {
            System.arraycopy(this.otherBounds, 0, currentOtherBounds = new ReferenceBinding[length], 0, length);
         }

         for(int i = 0; i < length; ++i) {
            TypeBinding currentOtherBound = this.otherBounds[i];
            if (currentOtherBound != null) {
               currentOtherBound = currentOtherBound.substituteInferenceVariable(var, substituteType);
               if (TypeBinding.notEquals(currentOtherBound, this.otherBounds[i])) {
                  if (currentOtherBounds == null) {
                     System.arraycopy(this.otherBounds, 0, currentOtherBounds = new ReferenceBinding[length], 0, length);
                  }

                  currentOtherBounds[i] = currentOtherBound;
               }
            }
         }
      }

      haveSubstitution |= currentOtherBounds != null;
      return haveSubstitution ? this.environment.createWildcard(this.genericType, this.rank, currentBound, currentOtherBounds, this.boundKind) : this;
   }

   public boolean isUnboundWildcard() {
      return this.boundKind == 0;
   }

   public boolean isWildcard() {
      return true;
   }

   int rank() {
      return this.rank;
   }

   public char[] readableName() {
      switch (this.boundKind) {
         case 0:
            return TypeConstants.WILDCARD_NAME;
         case 1:
            if (this.otherBounds == null) {
               return CharOperation.concat(TypeConstants.WILDCARD_NAME, TypeConstants.WILDCARD_EXTENDS, this.bound.readableName());
            }

            StringBuffer buffer = new StringBuffer(10);
            buffer.append(this.bound.readableName());
            int length = 0;

            for(int length = this.otherBounds.length; length < length; ++length) {
               buffer.append('&').append(this.otherBounds[length].readableName());
            }

            char[] result = new char[length = buffer.length()];
            buffer.getChars(0, length, result, 0);
            return result;
         default:
            return CharOperation.concat(TypeConstants.WILDCARD_NAME, TypeConstants.WILDCARD_SUPER, this.bound.readableName());
      }
   }

   public char[] nullAnnotatedReadableName(CompilerOptions options, boolean shortNames) {
      StringBuffer buffer;
      int i;
      buffer = new StringBuffer(10);
      this.appendNullAnnotation(buffer, options);
      label20:
      switch (this.boundKind) {
         case 0:
            buffer.append(TypeConstants.WILDCARD_NAME);
            break;
         case 1:
            if (this.otherBounds == null) {
               buffer.append(TypeConstants.WILDCARD_NAME).append(TypeConstants.WILDCARD_EXTENDS);
               buffer.append(this.bound.nullAnnotatedReadableName(options, shortNames));
               break;
            } else {
               buffer.append(this.bound.nullAnnotatedReadableName(options, shortNames));
               i = 0;
               int length = this.otherBounds.length;

               while(true) {
                  if (i >= length) {
                     break label20;
                  }

                  buffer.append('&').append(this.otherBounds[i].nullAnnotatedReadableName(options, shortNames));
                  ++i;
               }
            }
         default:
            buffer.append(TypeConstants.WILDCARD_NAME).append(TypeConstants.WILDCARD_SUPER).append(this.bound.nullAnnotatedReadableName(options, shortNames));
      }

      char[] result = new char[i = buffer.length()];
      buffer.getChars(0, i, result, 0);
      return result;
   }

   ReferenceBinding resolve() {
      if ((this.tagBits & 16777216L) == 0L) {
         return this;
      } else {
         this.tagBits &= -16777217L;
         BinaryTypeBinding.resolveType(this.genericType, this.environment, false);
         TypeBinding resolveType;
         label28:
         switch (this.boundKind) {
            case 0:
            default:
               break;
            case 1:
               resolveType = BinaryTypeBinding.resolveType(this.bound, this.environment, true);
               this.bound = resolveType;
               this.tagBits |= resolveType.tagBits & 2048L | 2305843009213693952L;
               int i = 0;
               int length = this.otherBounds == null ? 0 : this.otherBounds.length;

               while(true) {
                  if (i >= length) {
                     break label28;
                  }

                  resolveType = BinaryTypeBinding.resolveType(this.otherBounds[i], this.environment, true);
                  this.otherBounds[i] = resolveType;
                  this.tagBits |= resolveType.tagBits & 2048L | 2305843009213693952L;
                  ++i;
               }
            case 2:
               resolveType = BinaryTypeBinding.resolveType(this.bound, this.environment, true);
               this.bound = resolveType;
               this.tagBits |= resolveType.tagBits & 2048L | 2305843009213693952L;
         }

         if (this.environment.usesNullTypeAnnotations()) {
            this.evaluateNullAnnotations((Scope)null, (Wildcard)null);
         }

         return this;
      }
   }

   public char[] shortReadableName() {
      switch (this.boundKind) {
         case 0:
            return TypeConstants.WILDCARD_NAME;
         case 1:
            if (this.otherBounds == null) {
               return CharOperation.concat(TypeConstants.WILDCARD_NAME, TypeConstants.WILDCARD_EXTENDS, this.bound.shortReadableName());
            }

            StringBuffer buffer = new StringBuffer(10);
            buffer.append(this.bound.shortReadableName());
            int length = 0;

            for(int length = this.otherBounds.length; length < length; ++length) {
               buffer.append('&').append(this.otherBounds[length].shortReadableName());
            }

            char[] result = new char[length = buffer.length()];
            buffer.getChars(0, length, result, 0);
            return result;
         default:
            return CharOperation.concat(TypeConstants.WILDCARD_NAME, TypeConstants.WILDCARD_SUPER, this.bound.shortReadableName());
      }
   }

   public char[] signature() {
      if (this.signature == null) {
         switch (this.boundKind) {
            case 1:
               return this.bound.signature();
            default:
               return this.typeVariable().signature();
         }
      } else {
         return this.signature;
      }
   }

   public char[] sourceName() {
      switch (this.boundKind) {
         case 0:
            return TypeConstants.WILDCARD_NAME;
         case 1:
            return CharOperation.concat(TypeConstants.WILDCARD_NAME, TypeConstants.WILDCARD_EXTENDS, this.bound.sourceName());
         default:
            return CharOperation.concat(TypeConstants.WILDCARD_NAME, TypeConstants.WILDCARD_SUPER, this.bound.sourceName());
      }
   }

   public ReferenceBinding superclass() {
      if (this.superclass == null) {
         TypeBinding superType = null;
         if (this.boundKind == 1 && !this.bound.isInterface()) {
            superType = this.bound;
         } else {
            TypeVariableBinding variable = this.typeVariable();
            if (variable != null) {
               superType = variable.firstBound;
            }
         }

         this.superclass = superType instanceof ReferenceBinding && !superType.isInterface() ? (ReferenceBinding)superType : this.environment.getResolvedJavaBaseType(TypeConstants.JAVA_LANG_OBJECT, (Scope)null);
      }

      return this.superclass;
   }

   public ReferenceBinding[] superInterfaces() {
      if (this.superInterfaces == null) {
         if (this.typeVariable() != null) {
            this.superInterfaces = this.typeVariable.superInterfaces();
         } else {
            this.superInterfaces = Binding.NO_SUPERINTERFACES;
         }

         if (this.boundKind == 1) {
            int length;
            if (this.bound.isInterface()) {
               length = this.superInterfaces.length;
               System.arraycopy(this.superInterfaces, 0, this.superInterfaces = new ReferenceBinding[length + 1], 1, length);
               this.superInterfaces[0] = (ReferenceBinding)this.bound;
            }

            if (this.otherBounds != null) {
               length = this.superInterfaces.length;
               int otherLength = this.otherBounds.length;
               System.arraycopy(this.superInterfaces, 0, this.superInterfaces = new ReferenceBinding[length + otherLength], 0, length);

               for(int i = 0; i < otherLength; ++i) {
                  this.superInterfaces[length + i] = (ReferenceBinding)this.otherBounds[i];
               }
            }
         }
      }

      return this.superInterfaces;
   }

   public void swapUnresolved(UnresolvedReferenceBinding unresolvedType, ReferenceBinding resolvedType, LookupEnvironment env) {
      boolean affected = false;
      if (this.genericType == unresolvedType) {
         this.genericType = resolvedType;
         affected = true;
      }

      if (this.bound == unresolvedType) {
         this.bound = env.convertUnresolvedBinaryToRawType(resolvedType);
         affected = true;
      }

      if (this.otherBounds != null) {
         int i = 0;

         for(int length = this.otherBounds.length; i < length; ++i) {
            if (this.otherBounds[i] == unresolvedType) {
               this.otherBounds[i] = env.convertUnresolvedBinaryToRawType(resolvedType);
               affected = true;
            }
         }
      }

      if (affected) {
         this.initialize(this.genericType, this.bound, this.otherBounds);
      }

   }

   public String toString() {
      if (this.hasTypeAnnotations()) {
         return this.annotatedDebugName();
      } else {
         switch (this.boundKind) {
            case 0:
               return new String(TypeConstants.WILDCARD_NAME);
            case 1:
               if (this.otherBounds == null) {
                  return new String(CharOperation.concat(TypeConstants.WILDCARD_NAME, TypeConstants.WILDCARD_EXTENDS, this.bound.debugName().toCharArray()));
               }

               StringBuffer buffer = new StringBuffer(this.bound.debugName());
               int i = 0;

               for(int length = this.otherBounds.length; i < length; ++i) {
                  buffer.append('&').append(this.otherBounds[i].debugName());
               }

               return buffer.toString();
            default:
               return new String(CharOperation.concat(TypeConstants.WILDCARD_NAME, TypeConstants.WILDCARD_SUPER, this.bound.debugName().toCharArray()));
         }
      }
   }

   public TypeVariableBinding typeVariable() {
      if (this.typeVariable == null) {
         TypeVariableBinding[] typeVariables = this.genericType.typeVariables();
         if (this.rank < typeVariables.length) {
            this.typeVariable = typeVariables[this.rank];
         }
      }

      return this.typeVariable;
   }

   public TypeBinding unannotated() {
      return (TypeBinding)(this.hasTypeAnnotations() ? this.environment.getUnannotatedType(this) : this);
   }

   public TypeBinding withoutToplevelNullAnnotation() {
      if (!this.hasNullTypeAnnotations()) {
         return this;
      } else {
         AnnotationBinding[] newAnnotations = this.environment.filterNullTypeAnnotations(this.getTypeAnnotations());
         return this.environment.createWildcard(this.genericType, this.rank, this.bound, this.otherBounds, this.boundKind, newAnnotations);
      }
   }

   public TypeBinding uncapture(Scope scope) {
      if ((this.tagBits & 2305843009213693952L) == 0L) {
         return this;
      } else {
         TypeBinding freeBound = this.bound != null ? this.bound.uncapture(scope) : null;
         int length = 0;
         TypeBinding[] freeOtherBounds = this.otherBounds == null ? null : new TypeBinding[length = this.otherBounds.length];

         for(int i = 0; i < length; ++i) {
            freeOtherBounds[i] = this.otherBounds[i] == null ? null : this.otherBounds[i].uncapture(scope);
         }

         return scope.environment().createWildcard(this.genericType, this.rank, freeBound, freeOtherBounds, this.boundKind, this.getTypeAnnotations());
      }
   }

   void collectInferenceVariables(Set variables) {
      if (this.bound != null) {
         this.bound.collectInferenceVariables(variables);
      }

      if (this.otherBounds != null) {
         int i = 0;

         for(int length = this.otherBounds.length; i < length; ++i) {
            this.otherBounds[i].collectInferenceVariables(variables);
         }
      }

   }

   public boolean mentionsAny(TypeBinding[] parameters, int idx) {
      if (this.inRecursiveFunction) {
         return false;
      } else {
         this.inRecursiveFunction = true;

         try {
            if (!super.mentionsAny(parameters, idx)) {
               if (this.bound != null && this.bound.mentionsAny(parameters, -1)) {
                  return true;
               }

               if (this.otherBounds == null) {
                  return false;
               }

               int i = 0;

               for(int length = this.otherBounds.length; i < length; ++i) {
                  if (this.otherBounds[i].mentionsAny(parameters, -1)) {
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

   public boolean acceptsNonNullDefault() {
      return false;
   }

   public long updateTagBits() {
      if (!this.inRecursiveFunction) {
         this.inRecursiveFunction = true;

         try {
            if (this.bound != null) {
               this.tagBits |= this.bound.updateTagBits();
            }

            if (this.otherBounds != null) {
               int i = 0;

               for(int length = this.otherBounds.length; i < length; ++i) {
                  this.tagBits |= this.otherBounds[i].updateTagBits();
               }
            }
         } finally {
            this.inRecursiveFunction = false;
         }
      }

      return super.updateTagBits();
   }
}
