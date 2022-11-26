package com.bea.core.repackaged.jdt.internal.compiler.lookup;

import com.bea.core.repackaged.jdt.core.compiler.CharOperation;
import com.bea.core.repackaged.jdt.internal.compiler.ast.ASTNode;
import com.bea.core.repackaged.jdt.internal.compiler.ast.AbstractMethodDeclaration;
import com.bea.core.repackaged.jdt.internal.compiler.ast.Annotation;
import com.bea.core.repackaged.jdt.internal.compiler.ast.FakedTrackingVariable;
import com.bea.core.repackaged.jdt.internal.compiler.ast.Initializer;
import com.bea.core.repackaged.jdt.internal.compiler.ast.LambdaExpression;
import com.bea.core.repackaged.jdt.internal.compiler.ast.LocalDeclaration;
import com.bea.core.repackaged.jdt.internal.compiler.ast.TypeDeclaration;
import com.bea.core.repackaged.jdt.internal.compiler.impl.Constant;
import com.bea.core.repackaged.jdt.internal.compiler.impl.ReferenceContext;
import java.util.HashSet;
import java.util.Set;

public class LocalVariableBinding extends VariableBinding {
   public int resolvedPosition;
   public static final int UNUSED = 0;
   public static final int USED = 1;
   public static final int FAKE_USED = 2;
   public int useFlag;
   public BlockScope declaringScope;
   public LocalDeclaration declaration;
   public int[] initializationPCs;
   public int initializationCount;
   public FakedTrackingVariable closeTracker;
   public Set uninitializedInMethod;

   public LocalVariableBinding(char[] name, TypeBinding type, int modifiers, boolean isArgument) {
      super(name, type, modifiers, isArgument ? Constant.NotAConstant : null);
      this.initializationCount = 0;
      if (isArgument) {
         this.tagBits |= 1024L;
      }

      this.tagBits |= 2048L;
   }

   public LocalVariableBinding(LocalDeclaration declaration, TypeBinding type, int modifiers, boolean isArgument) {
      this(declaration.name, type, modifiers, isArgument);
      this.declaration = declaration;
   }

   public LocalVariableBinding(LocalDeclaration declaration, TypeBinding type, int modifiers, MethodScope declaringScope) {
      this(declaration, type, modifiers, true);
      this.declaringScope = declaringScope;
   }

   public final int kind() {
      return 2;
   }

   public char[] computeUniqueKey(boolean isLeaf) {
      StringBuffer buffer = new StringBuffer();
      BlockScope scope = this.declaringScope;
      int occurenceCount = 0;
      int i;
      LocalVariableBinding[] params;
      if (scope != null) {
         MethodScope methodScope = scope instanceof MethodScope ? (MethodScope)scope : scope.enclosingMethodScope();
         ReferenceContext referenceContext = methodScope.referenceContext;
         MethodBinding methodBinding;
         if (referenceContext instanceof AbstractMethodDeclaration) {
            methodBinding = ((AbstractMethodDeclaration)referenceContext).binding;
            if (methodBinding != null) {
               buffer.append(methodBinding.computeUniqueKey(false));
            }
         } else if (referenceContext instanceof TypeDeclaration) {
            TypeBinding typeBinding = ((TypeDeclaration)referenceContext).binding;
            if (typeBinding != null) {
               buffer.append(typeBinding.computeUniqueKey(false));
            }
         } else if (referenceContext instanceof LambdaExpression) {
            methodBinding = ((LambdaExpression)referenceContext).binding;
            if (methodBinding != null) {
               buffer.append(methodBinding.computeUniqueKey(false));
            }
         }

         this.getScopeKey(scope, buffer);
         params = scope.locals;

         for(i = 0; i < scope.localIndex; ++i) {
            LocalVariableBinding local = params[i];
            if (CharOperation.equals(this.name, local.name)) {
               if (this == local) {
                  break;
               }

               ++occurenceCount;
            }
         }
      }

      buffer.append('#');
      buffer.append(this.name);
      boolean addParameterRank = this.isParameter() && this.declaringScope != null;
      int pos;
      if (occurenceCount > 0 || addParameterRank) {
         buffer.append('#');
         buffer.append(occurenceCount);
         if (addParameterRank) {
            pos = -1;
            params = this.declaringScope.locals;

            for(i = 0; i < params.length; ++i) {
               if (params[i] == this) {
                  pos = i;
                  break;
               }
            }

            if (pos > -1) {
               buffer.append('#');
               buffer.append(pos);
            }
         }
      }

      pos = buffer.length();
      char[] uniqueKey = new char[pos];
      buffer.getChars(0, pos, uniqueKey, 0);
      return uniqueKey;
   }

   public AnnotationBinding[] getAnnotations() {
      if (this.declaringScope != null) {
         SourceTypeBinding sourceType = this.declaringScope.enclosingSourceType();
         if (sourceType == null) {
            return Binding.NO_ANNOTATIONS;
         } else {
            if ((this.tagBits & 8589934592L) == 0L && (this.tagBits & 1024L) != 0L && this.declaration != null) {
               Annotation[] annotationNodes = this.declaration.annotations;
               if (annotationNodes != null) {
                  ASTNode.resolveAnnotations(this.declaringScope, annotationNodes, this, true);
               }
            }

            return sourceType.retrieveAnnotations(this);
         }
      } else {
         if ((this.tagBits & 8589934592L) != 0L) {
            if (this.declaration == null) {
               return Binding.NO_ANNOTATIONS;
            }

            Annotation[] annotations = this.declaration.annotations;
            if (annotations != null) {
               int length = annotations.length;
               AnnotationBinding[] annotationBindings = new AnnotationBinding[length];

               for(int i = 0; i < length; ++i) {
                  AnnotationBinding compilerAnnotation = annotations[i].getCompilerAnnotation();
                  if (compilerAnnotation == null) {
                     return Binding.NO_ANNOTATIONS;
                  }

                  annotationBindings[i] = compilerAnnotation;
               }

               return annotationBindings;
            }
         }

         return Binding.NO_ANNOTATIONS;
      }
   }

   private void getScopeKey(BlockScope scope, StringBuffer buffer) {
      int scopeIndex = scope.scopeIndex();
      if (scopeIndex != -1) {
         this.getScopeKey((BlockScope)scope.parent, buffer);
         buffer.append('#');
         buffer.append(scopeIndex);
      }

   }

   public boolean isSecret() {
      return this.declaration == null && (this.tagBits & 1024L) == 0L;
   }

   public void recordInitializationEndPC(int pc) {
      if (this.initializationPCs[(this.initializationCount - 1 << 1) + 1] == -1) {
         this.initializationPCs[(this.initializationCount - 1 << 1) + 1] = pc;
      }

   }

   public void recordInitializationStartPC(int pc) {
      if (this.initializationPCs != null) {
         int previousEndPC;
         if (this.initializationCount > 0) {
            previousEndPC = this.initializationPCs[(this.initializationCount - 1 << 1) + 1];
            if (previousEndPC == -1) {
               return;
            }

            if (previousEndPC == pc) {
               this.initializationPCs[(this.initializationCount - 1 << 1) + 1] = -1;
               return;
            }
         }

         previousEndPC = this.initializationCount << 1;
         if (previousEndPC == this.initializationPCs.length) {
            System.arraycopy(this.initializationPCs, 0, this.initializationPCs = new int[this.initializationCount << 2], 0, previousEndPC);
         }

         this.initializationPCs[previousEndPC] = pc;
         this.initializationPCs[previousEndPC + 1] = -1;
         ++this.initializationCount;
      }
   }

   public void setAnnotations(AnnotationBinding[] annotations, Scope scope, boolean forceStore) {
      if (scope != null) {
         SourceTypeBinding sourceType = scope.enclosingSourceType();
         if (sourceType != null) {
            sourceType.storeAnnotations(this, annotations, forceStore);
         }

      }
   }

   public void resetInitializations() {
      this.initializationCount = 0;
      this.initializationPCs = null;
   }

   public String toString() {
      String s = super.toString();
      switch (this.useFlag) {
         case 0:
            s = s + "[pos: unused]";
            break;
         case 1:
            s = s + "[pos: " + this.resolvedPosition + "]";
            break;
         case 2:
            s = s + "[pos: fake_used]";
      }

      s = s + "[id:" + this.id + "]";
      if (this.initializationCount > 0) {
         s = s + "[pc: ";

         for(int i = 0; i < this.initializationCount; ++i) {
            if (i > 0) {
               s = s + ", ";
            }

            s = s + String.valueOf(this.initializationPCs[i << 1]) + "-" + (this.initializationPCs[(i << 1) + 1] == -1 ? "?" : String.valueOf(this.initializationPCs[(i << 1) + 1]));
         }

         s = s + "]";
      }

      return s;
   }

   public boolean isParameter() {
      return (this.tagBits & 1024L) != 0L;
   }

   public boolean isCatchParameter() {
      return false;
   }

   public MethodBinding getEnclosingMethod() {
      BlockScope blockScope = this.declaringScope;
      if (blockScope != null) {
         ReferenceContext referenceContext = blockScope.referenceContext();
         if (referenceContext instanceof Initializer) {
            return null;
         }

         if (referenceContext instanceof AbstractMethodDeclaration) {
            return ((AbstractMethodDeclaration)referenceContext).binding;
         }
      }

      return null;
   }

   public void markInitialized() {
   }

   public void markReferenced() {
   }

   public boolean isUninitializedIn(Scope scope) {
      return this.uninitializedInMethod != null ? this.uninitializedInMethod.contains(scope.methodScope()) : false;
   }

   public void markAsUninitializedIn(Scope scope) {
      if (this.uninitializedInMethod == null) {
         this.uninitializedInMethod = new HashSet();
      }

      this.uninitializedInMethod.add(scope.methodScope());
   }
}
