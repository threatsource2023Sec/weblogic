package com.bea.core.repackaged.jdt.internal.compiler.lookup;

import com.bea.core.repackaged.jdt.core.compiler.CharOperation;

public class CaptureBinding18 extends CaptureBinding {
   TypeBinding[] upperBounds;
   private char[] originalName;
   private CaptureBinding18 prototype;
   int recursionLevel = 0;

   public CaptureBinding18(ReferenceBinding contextType, char[] sourceName, char[] originalName, int start, int end, int captureID, LookupEnvironment environment) {
      super(contextType, sourceName, start, end, captureID, environment);
      this.originalName = originalName;
      this.prototype = this;
   }

   private CaptureBinding18(CaptureBinding18 prototype) {
      super(prototype);
      this.sourceName = CharOperation.append(prototype.sourceName, '\'');
      this.originalName = prototype.originalName;
      this.upperBounds = prototype.upperBounds;
      this.prototype = prototype.prototype;
   }

   public boolean setUpperBounds(TypeBinding[] upperBounds, ReferenceBinding javaLangObject) {
      this.upperBounds = upperBounds;
      if (upperBounds.length > 0) {
         this.firstBound = upperBounds[0];
      }

      int numReferenceInterfaces = 0;
      if (!isConsistentIntersection(upperBounds)) {
         return false;
      } else {
         int idx;
         for(idx = 0; idx < upperBounds.length; ++idx) {
            TypeBinding aBound = upperBounds[idx];
            if (aBound instanceof ReferenceBinding) {
               if (this.superclass == null && aBound.isClass()) {
                  this.superclass = (ReferenceBinding)aBound;
               } else if (aBound.isInterface()) {
                  ++numReferenceInterfaces;
               }
            } else if (TypeBinding.equalsEquals(aBound.leafComponentType(), this)) {
               return false;
            }
         }

         this.superInterfaces = new ReferenceBinding[numReferenceInterfaces];
         idx = 0;

         for(int i = 0; i < upperBounds.length; ++i) {
            TypeBinding aBound = upperBounds[i];
            if (aBound.isInterface()) {
               this.superInterfaces[idx++] = (ReferenceBinding)aBound;
            }
         }

         if (this.superclass == null) {
            this.superclass = javaLangObject;
         }

         return true;
      }
   }

   public void initializeBounds(Scope scope, ParameterizedTypeBinding capturedParameterizedType) {
   }

   public TypeBinding clone(TypeBinding enclosingType) {
      return new CaptureBinding18(this);
   }

   public MethodBinding[] getMethods(char[] selector) {
      return this.upperBounds.length == 1 && this.upperBounds[0] instanceof ReferenceBinding ? ((ReferenceBinding)this.upperBounds[0]).getMethods(selector) : super.getMethods(selector);
   }

   public TypeBinding erasure() {
      if (this.upperBounds != null && this.upperBounds.length > 1) {
         ReferenceBinding[] erasures = new ReferenceBinding[this.upperBounds.length];
         boolean multipleErasures = false;

         for(int i = 0; i < this.upperBounds.length; ++i) {
            erasures[i] = (ReferenceBinding)this.upperBounds[i].erasure();
            if (i > 0 && TypeBinding.notEquals(erasures[0], erasures[i])) {
               multipleErasures = true;
            }
         }

         if (!multipleErasures) {
            return erasures[0];
         } else {
            return this.environment.createIntersectionType18(erasures);
         }
      } else {
         return (TypeBinding)(this.superclass == null ? this.environment.getType(TypeConstants.JAVA_LANG_OBJECT) : super.erasure());
      }
   }

   public boolean isEquivalentTo(TypeBinding otherType) {
      if (equalsEquals(this, otherType)) {
         return true;
      } else if (otherType == null) {
         return false;
      } else if (this.upperBounds == null) {
         return false;
      } else {
         for(int i = 0; i < this.upperBounds.length; ++i) {
            TypeBinding aBound = this.upperBounds[i];
            if (aBound != null && aBound.isArrayType()) {
               if (!aBound.isCompatibleWith(otherType)) {
                  return false;
               }
            } else {
               switch (otherType.kind()) {
                  case 516:
                  case 8196:
                     if (!((WildcardBinding)otherType).boundCheck(aBound)) {
                        return false;
                     }
               }
            }
         }

         return true;
      }
   }

   public boolean isCompatibleWith(TypeBinding otherType, Scope captureScope) {
      if (TypeBinding.equalsEquals(this, otherType)) {
         return true;
      } else if (this.inRecursiveFunction) {
         return true;
      } else {
         this.inRecursiveFunction = true;

         try {
            if (this.upperBounds == null) {
               return false;
            } else {
               int length = this.upperBounds.length;
               int rightKind = otherType.kind();
               TypeBinding[] rightIntersectingTypes = null;
               if (rightKind == 8196 && otherType.boundKind() == 1) {
                  TypeBinding allRightBounds = ((WildcardBinding)otherType).allBounds();
                  if (allRightBounds instanceof IntersectionTypeBinding18) {
                     rightIntersectingTypes = ((IntersectionTypeBinding18)allRightBounds).intersectingTypes;
                  }
               } else if (rightKind == 32772) {
                  rightIntersectingTypes = ((IntersectionTypeBinding18)otherType).intersectingTypes;
               }

               if (rightIntersectingTypes != null) {
                  ReferenceBinding[] var9 = rightIntersectingTypes;
                  int var8 = rightIntersectingTypes.length;
                  int var7 = 0;

                  label133:
                  while(var7 < var8) {
                     TypeBinding required = var9[var7];
                     TypeBinding[] var13;
                     int var12 = (var13 = this.upperBounds).length;

                     for(int var11 = 0; var11 < var12; ++var11) {
                        TypeBinding provided = var13[var11];
                        if (provided.isCompatibleWith(required, captureScope)) {
                           ++var7;
                           continue label133;
                        }
                     }

                     return false;
                  }

                  return true;
               } else {
                  for(int i = 0; i < length; ++i) {
                     if (this.upperBounds[i].isCompatibleWith(otherType, captureScope)) {
                        return true;
                     }
                  }

                  return false;
               }
            }
         } finally {
            this.inRecursiveFunction = false;
         }
      }
   }

   public TypeBinding findSuperTypeOriginatingFrom(TypeBinding otherType) {
      if (this.upperBounds != null && this.upperBounds.length > 1) {
         for(int i = 0; i < this.upperBounds.length; ++i) {
            TypeBinding candidate = this.upperBounds[i].findSuperTypeOriginatingFrom(otherType);
            if (candidate != null) {
               return candidate;
            }
         }
      }

      return super.findSuperTypeOriginatingFrom(otherType);
   }

   TypeBinding substituteInferenceVariable(InferenceVariable var, TypeBinding substituteType) {
      if (this.inRecursiveFunction) {
         return this;
      } else {
         this.inRecursiveFunction = true;

         CaptureBinding18 var11;
         try {
            boolean haveSubstitution = false;
            ReferenceBinding currentSuperclass = this.superclass;
            if (currentSuperclass != null) {
               currentSuperclass = (ReferenceBinding)currentSuperclass.substituteInferenceVariable(var, substituteType);
               haveSubstitution |= TypeBinding.notEquals(currentSuperclass, this.superclass);
            }

            ReferenceBinding[] currentSuperInterfaces = null;
            int length;
            if (this.superInterfaces != null) {
               int length = this.superInterfaces.length;
               if (haveSubstitution) {
                  System.arraycopy(this.superInterfaces, 0, currentSuperInterfaces = new ReferenceBinding[length], 0, length);
               }

               for(length = 0; length < length; ++length) {
                  ReferenceBinding currentSuperInterface = this.superInterfaces[length];
                  if (currentSuperInterface != null) {
                     currentSuperInterface = (ReferenceBinding)currentSuperInterface.substituteInferenceVariable(var, substituteType);
                     if (TypeBinding.notEquals(currentSuperInterface, this.superInterfaces[length])) {
                        if (currentSuperInterfaces == null) {
                           System.arraycopy(this.superInterfaces, 0, currentSuperInterfaces = new ReferenceBinding[length], 0, length);
                        }

                        currentSuperInterfaces[length] = currentSuperInterface;
                        haveSubstitution = true;
                     }
                  }
               }
            }

            TypeBinding[] currentUpperBounds = null;
            if (this.upperBounds != null) {
               length = this.upperBounds.length;
               if (haveSubstitution) {
                  System.arraycopy(this.upperBounds, 0, currentUpperBounds = new TypeBinding[length], 0, length);
               }

               for(int i = 0; i < length; ++i) {
                  TypeBinding currentBound = this.upperBounds[i];
                  if (currentBound != null) {
                     currentBound = currentBound.substituteInferenceVariable(var, substituteType);
                     if (TypeBinding.notEquals(currentBound, this.upperBounds[i])) {
                        if (currentUpperBounds == null) {
                           System.arraycopy(this.upperBounds, 0, currentUpperBounds = new TypeBinding[length], 0, length);
                        }

                        currentUpperBounds[i] = currentBound;
                        haveSubstitution = true;
                     }
                  }
               }
            }

            TypeBinding currentFirstBound = null;
            if (this.firstBound != null) {
               currentFirstBound = this.firstBound.substituteInferenceVariable(var, substituteType);
               haveSubstitution |= TypeBinding.notEquals(this.firstBound, currentFirstBound);
            }

            if (haveSubstitution) {
               final CaptureBinding18 newCapture = (CaptureBinding18)this.clone(this.enclosingType());
               newCapture.tagBits = this.tagBits;
               Substitution substitution = new Substitution() {
                  public TypeBinding substitute(TypeVariableBinding typeVariable) {
                     return (TypeBinding)(typeVariable == CaptureBinding18.this ? newCapture : typeVariable);
                  }

                  public boolean isRawSubstitution() {
                     return false;
                  }

                  public LookupEnvironment environment() {
                     return CaptureBinding18.this.environment;
                  }
               };
               if (currentFirstBound != null) {
                  newCapture.firstBound = Scope.substitute(substitution, currentFirstBound);
               }

               newCapture.superclass = (ReferenceBinding)Scope.substitute(substitution, (TypeBinding)currentSuperclass);
               newCapture.superInterfaces = Scope.substitute(substitution, currentSuperInterfaces);
               newCapture.upperBounds = Scope.substitute(substitution, currentUpperBounds);
               var11 = newCapture;
               return var11;
            }

            var11 = this;
         } finally {
            this.inRecursiveFunction = false;
         }

         return var11;
      }
   }

   public boolean isProperType(boolean admitCapture18) {
      if (!admitCapture18) {
         return false;
      } else if (this.inRecursiveFunction) {
         return true;
      } else {
         this.inRecursiveFunction = true;

         try {
            if (this.lowerBound != null && !this.lowerBound.isProperType(admitCapture18)) {
               return false;
            } else if (this.upperBounds == null) {
               return true;
            } else {
               for(int i = 0; i < this.upperBounds.length; ++i) {
                  if (!this.upperBounds[i].isProperType(admitCapture18)) {
                     return false;
                  }
               }

               return true;
            }
         } finally {
            this.inRecursiveFunction = false;
         }
      }
   }

   public char[] readableName() {
      if (this.lowerBound == null && this.firstBound != null) {
         if (this.prototype.recursionLevel >= 2) {
            return this.originalName;
         } else {
            try {
               ++this.prototype.recursionLevel;
               char[] var5;
               if (this.upperBounds == null || this.upperBounds.length <= 1) {
                  var5 = this.firstBound.readableName();
                  return var5;
               } else {
                  StringBuffer sb = new StringBuffer();
                  sb.append(this.upperBounds[0].readableName());

                  int len;
                  for(len = 1; len < this.upperBounds.length; ++len) {
                     sb.append('&').append(this.upperBounds[len].readableName());
                  }

                  len = sb.length();
                  char[] name = new char[len];
                  sb.getChars(0, len, name, 0);
                  var5 = name;
                  return var5;
               }
            } finally {
               --this.prototype.recursionLevel;
            }
         }
      } else {
         return super.readableName();
      }
   }

   public char[] shortReadableName() {
      if (this.lowerBound == null && this.firstBound != null) {
         if (this.prototype.recursionLevel >= 2) {
            return this.originalName;
         } else {
            char[] var5;
            try {
               ++this.prototype.recursionLevel;
               if (this.upperBounds != null && this.upperBounds.length > 1) {
                  StringBuffer sb = new StringBuffer();
                  sb.append(this.upperBounds[0].shortReadableName());

                  int len;
                  for(len = 1; len < this.upperBounds.length; ++len) {
                     sb.append('&').append(this.upperBounds[len].shortReadableName());
                  }

                  len = sb.length();
                  char[] name = new char[len];
                  sb.getChars(0, len, name, 0);
                  var5 = name;
                  return var5;
               }

               var5 = this.firstBound.shortReadableName();
            } finally {
               --this.prototype.recursionLevel;
            }

            return var5;
         }
      } else {
         return super.shortReadableName();
      }
   }

   public TypeBinding uncapture(Scope scope) {
      return this;
   }

   public char[] computeUniqueKey(boolean isLeaf) {
      StringBuffer buffer = new StringBuffer();
      buffer.append(TypeConstants.CAPTURE18);
      buffer.append('{').append(this.end).append('#').append(this.captureID).append('}');
      buffer.append(';');
      int length = buffer.length();
      char[] uniqueKey = new char[length];
      buffer.getChars(0, length, uniqueKey, 0);
      return uniqueKey;
   }
}
