package com.bea.core.repackaged.jdt.internal.compiler.ast;

import com.bea.core.repackaged.jdt.core.compiler.CharOperation;
import com.bea.core.repackaged.jdt.internal.compiler.ASTVisitor;
import com.bea.core.repackaged.jdt.internal.compiler.codegen.AnnotationContext;
import com.bea.core.repackaged.jdt.internal.compiler.flow.FlowContext;
import com.bea.core.repackaged.jdt.internal.compiler.flow.FlowInfo;
import com.bea.core.repackaged.jdt.internal.compiler.impl.CompilerOptions;
import com.bea.core.repackaged.jdt.internal.compiler.impl.Constant;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.AnnotationBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.ArrayBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.BlockScope;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.ClassScope;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.LocalVariableBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.LookupEnvironment;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.ProblemReferenceBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.ReferenceBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.Scope;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.Substitution;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.TypeBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.TypeConstants;
import java.util.ArrayList;
import java.util.List;

public abstract class TypeReference extends Expression {
   public static final TypeReference[] NO_TYPE_ARGUMENTS = new TypeReference[0];
   public Annotation[][] annotations = null;

   public static final TypeReference baseTypeReference(int baseType, int dim, Annotation[][] dimAnnotations) {
      if (dim == 0) {
         switch (baseType) {
            case 2:
               return new SingleTypeReference(TypeBinding.CHAR.simpleName, 0L);
            case 3:
               return new SingleTypeReference(TypeBinding.BYTE.simpleName, 0L);
            case 4:
               return new SingleTypeReference(TypeBinding.SHORT.simpleName, 0L);
            case 5:
               return new SingleTypeReference(TypeBinding.BOOLEAN.simpleName, 0L);
            case 6:
               return new SingleTypeReference(TypeBinding.VOID.simpleName, 0L);
            case 7:
            default:
               return new SingleTypeReference(TypeBinding.LONG.simpleName, 0L);
            case 8:
               return new SingleTypeReference(TypeBinding.DOUBLE.simpleName, 0L);
            case 9:
               return new SingleTypeReference(TypeBinding.FLOAT.simpleName, 0L);
            case 10:
               return new SingleTypeReference(TypeBinding.INT.simpleName, 0L);
         }
      } else {
         switch (baseType) {
            case 2:
               return new ArrayTypeReference(TypeBinding.CHAR.simpleName, dim, dimAnnotations, 0L);
            case 3:
               return new ArrayTypeReference(TypeBinding.BYTE.simpleName, dim, dimAnnotations, 0L);
            case 4:
               return new ArrayTypeReference(TypeBinding.SHORT.simpleName, dim, dimAnnotations, 0L);
            case 5:
               return new ArrayTypeReference(TypeBinding.BOOLEAN.simpleName, dim, dimAnnotations, 0L);
            case 6:
               return new ArrayTypeReference(TypeBinding.VOID.simpleName, dim, dimAnnotations, 0L);
            case 7:
            default:
               return new ArrayTypeReference(TypeBinding.LONG.simpleName, dim, dimAnnotations, 0L);
            case 8:
               return new ArrayTypeReference(TypeBinding.DOUBLE.simpleName, dim, dimAnnotations, 0L);
            case 9:
               return new ArrayTypeReference(TypeBinding.FLOAT.simpleName, dim, dimAnnotations, 0L);
            case 10:
               return new ArrayTypeReference(TypeBinding.INT.simpleName, dim, dimAnnotations, 0L);
         }
      }
   }

   public static final TypeReference baseTypeReference(int baseType, int dim) {
      return baseTypeReference(baseType, dim, (Annotation[][])null);
   }

   public void aboutToResolve(Scope scope) {
   }

   public FlowInfo analyseCode(BlockScope currentScope, FlowContext flowContext, FlowInfo flowInfo) {
      return flowInfo;
   }

   public void checkBounds(Scope scope) {
   }

   public abstract TypeReference augmentTypeWithAdditionalDimensions(int var1, Annotation[][] var2, boolean var3);

   protected Annotation[][] getMergedAnnotationsOnDimensions(int additionalDimensions, Annotation[][] additionalAnnotations) {
      Annotation[][] annotationsOnDimensions = this.getAnnotationsOnDimensions(true);
      int dimensions = this.dimensions();
      if (annotationsOnDimensions == null && additionalAnnotations == null) {
         return null;
      } else {
         int totalDimensions = dimensions + additionalDimensions;
         Annotation[][] mergedAnnotations = new Annotation[totalDimensions][];
         int i;
         if (annotationsOnDimensions != null) {
            for(i = 0; i < dimensions; ++i) {
               mergedAnnotations[i] = annotationsOnDimensions[i];
            }
         }

         if (additionalAnnotations != null) {
            i = dimensions;

            for(int j = 0; i < totalDimensions; ++j) {
               mergedAnnotations[i] = additionalAnnotations[j];
               ++i;
            }
         }

         return mergedAnnotations;
      }
   }

   public int dimensions() {
      return 0;
   }

   public int extraDimensions() {
      return 0;
   }

   public AnnotationContext[] getAllAnnotationContexts(int targetType) {
      List allAnnotationContexts = new ArrayList();
      AnnotationCollector collector = new AnnotationCollector(this, targetType, allAnnotationContexts);
      this.traverse(collector, (BlockScope)null);
      return (AnnotationContext[])allAnnotationContexts.toArray(new AnnotationContext[allAnnotationContexts.size()]);
   }

   public void getAllAnnotationContexts(int targetType, int info, List allAnnotationContexts) {
      AnnotationCollector collector = new AnnotationCollector(this, targetType, info, allAnnotationContexts);
      this.traverse(collector, (BlockScope)null);
   }

   public void getAllAnnotationContexts(int targetType, int info, List allAnnotationContexts, Annotation[] se7Annotations) {
      AnnotationCollector collector = new AnnotationCollector(this, targetType, info, allAnnotationContexts);
      int i = 0;

      for(int length = se7Annotations == null ? 0 : se7Annotations.length; i < length; ++i) {
         Annotation annotation = se7Annotations[i];
         annotation.traverse(collector, (BlockScope)null);
      }

      this.traverse(collector, (BlockScope)null);
   }

   public void getAllAnnotationContexts(int targetType, int info, List allAnnotationContexts, Annotation[][] annotationsOnDimensions, int dimensions) {
      AnnotationCollector collector = new AnnotationCollector(this, targetType, info, allAnnotationContexts, annotationsOnDimensions, dimensions);
      this.traverse(collector, (BlockScope)null);
      if (annotationsOnDimensions != null) {
         int i = 0;

         for(int max = annotationsOnDimensions.length; i < max; ++i) {
            Annotation[] annotationsOnDimension = annotationsOnDimensions[i];
            if (annotationsOnDimension != null) {
               int j = 0;

               for(int max2 = annotationsOnDimension.length; j < max2; ++j) {
                  annotationsOnDimension[j].traverse(collector, (BlockScope)null);
               }
            }
         }
      }

   }

   public void getAllAnnotationContexts(int targetType, int info, int typeIndex, List allAnnotationContexts) {
      AnnotationCollector collector = new AnnotationCollector(this, targetType, info, typeIndex, allAnnotationContexts);
      this.traverse(collector, (BlockScope)null);
   }

   public void getAllAnnotationContexts(int targetType, List allAnnotationContexts) {
      AnnotationCollector collector = new AnnotationCollector(this, targetType, allAnnotationContexts);
      this.traverse(collector, (BlockScope)null);
   }

   public Annotation[][] getAnnotationsOnDimensions() {
      return this.getAnnotationsOnDimensions(false);
   }

   public TypeReference[][] getTypeArguments() {
      return null;
   }

   public Annotation[][] getAnnotationsOnDimensions(boolean useSourceOrder) {
      return null;
   }

   public void setAnnotationsOnDimensions(Annotation[][] annotationsOnDimensions) {
   }

   public abstract char[] getLastToken();

   public char[][] getParameterizedTypeName() {
      return this.getTypeName();
   }

   protected abstract TypeBinding getTypeBinding(Scope var1);

   public abstract char[][] getTypeName();

   protected TypeBinding internalResolveType(Scope scope, int location) {
      this.constant = Constant.NotAConstant;
      if (this.resolvedType != null) {
         if (this.resolvedType.isValidBinding()) {
            return this.resolvedType;
         } else {
            switch (this.resolvedType.problemId()) {
               case 1:
               case 2:
               case 5:
                  TypeBinding type = this.resolvedType.closestMatch();
                  if (type == null) {
                     return null;
                  }

                  return scope.environment().convertToRawType(type, false);
               case 3:
               case 4:
               default:
                  return null;
            }
         }
      } else {
         TypeBinding type = this.resolvedType = this.getTypeBinding(scope);
         if (type == null) {
            return null;
         } else {
            boolean hasError;
            if (hasError = !type.isValidBinding()) {
               if (this.isTypeNameVar(scope)) {
                  this.reportVarIsNotAllowedHere(scope);
               } else {
                  this.reportInvalidType(scope);
               }

               switch (type.problemId()) {
                  case 1:
                  case 2:
                  case 5:
                     type = type.closestMatch();
                     if (type == null) {
                        return null;
                     }
                     break;
                  case 3:
                  case 4:
                  default:
                     return null;
               }
            }

            if (type.isArrayType() && ((ArrayBinding)type).leafComponentType == TypeBinding.VOID) {
               scope.problemReporter().cannotAllocateVoidArray(this);
               return null;
            } else {
               if (!(this instanceof QualifiedTypeReference) && this.isTypeUseDeprecated(type, scope)) {
                  this.reportDeprecatedType(type, scope);
               }

               type = scope.environment().convertToRawType(type, false);
               if (type.leafComponentType().isRawType() && (this.bits & 1073741824) == 0 && scope.compilerOptions().getSeverity(536936448) != 256) {
                  scope.problemReporter().rawTypeReference(this, type);
               }

               if (hasError) {
                  this.resolveAnnotations(scope, 0);
                  return type;
               } else {
                  this.resolvedType = type;
                  this.resolveAnnotations(scope, location);
                  return this.resolvedType;
               }
            }
         }
      }
   }

   public boolean isTypeReference() {
      return true;
   }

   public boolean isWildcard() {
      return false;
   }

   public boolean isUnionType() {
      return false;
   }

   public boolean isVarargs() {
      return (this.bits & 16384) != 0;
   }

   public boolean isParameterizedTypeReference() {
      return false;
   }

   protected void reportDeprecatedType(TypeBinding type, Scope scope, int index) {
      scope.problemReporter().deprecatedType(type, this, index);
   }

   protected void reportDeprecatedType(TypeBinding type, Scope scope) {
      scope.problemReporter().deprecatedType(type, this, Integer.MAX_VALUE);
   }

   protected void reportInvalidType(Scope scope) {
      scope.problemReporter().invalidType(this, this.resolvedType);
   }

   protected void reportVarIsNotAllowedHere(Scope scope) {
      scope.problemReporter().varIsNotAllowedHere(this);
   }

   public TypeBinding resolveSuperType(ClassScope scope) {
      TypeBinding superType = this.resolveType(scope);
      if (superType == null) {
         return null;
      } else if (superType.isTypeVariable()) {
         if (this.resolvedType.isValidBinding()) {
            this.resolvedType = new ProblemReferenceBinding(this.getTypeName(), (ReferenceBinding)this.resolvedType, 9);
            this.reportInvalidType(scope);
         }

         return null;
      } else {
         return superType;
      }
   }

   public final TypeBinding resolveType(BlockScope blockScope) {
      return this.resolveType(blockScope, true);
   }

   public TypeBinding resolveType(BlockScope scope, boolean checkBounds) {
      return this.resolveType(scope, checkBounds, 0);
   }

   public TypeBinding resolveType(BlockScope scope, boolean checkBounds, int location) {
      return this.internalResolveType(scope, location);
   }

   public TypeBinding resolveType(ClassScope scope) {
      return this.resolveType(scope, 0);
   }

   public TypeBinding resolveType(ClassScope scope, int location) {
      return this.internalResolveType(scope, location);
   }

   public TypeBinding resolveTypeArgument(BlockScope blockScope, ReferenceBinding genericType, int rank) {
      return this.resolveType(blockScope, true, 64);
   }

   public TypeBinding resolveTypeArgument(ClassScope classScope, ReferenceBinding genericType, int rank) {
      ReferenceBinding ref = classScope.referenceContext.binding;
      boolean pauseHierarchyCheck = false;

      TypeBinding var7;
      try {
         if (ref.isHierarchyBeingConnected()) {
            pauseHierarchyCheck = (ref.tagBits & 524288L) == 0L;
            ref.tagBits |= 524288L;
         }

         var7 = this.resolveType(classScope, 64);
      } finally {
         if (pauseHierarchyCheck) {
            ref.tagBits &= -524289L;
         }

      }

      return var7;
   }

   public abstract void traverse(ASTVisitor var1, BlockScope var2);

   public abstract void traverse(ASTVisitor var1, ClassScope var2);

   protected void resolveAnnotations(Scope scope, int location) {
      Annotation[][] annotationsOnDimensions = this.getAnnotationsOnDimensions();
      if (this.annotations != null || annotationsOnDimensions != null) {
         BlockScope resolutionScope = Scope.typeAnnotationsResolutionScope(scope);
         if (resolutionScope != null) {
            int dimensions = this.dimensions();
            if (this.annotations != null) {
               TypeBinding leafComponentType = this.resolvedType.leafComponentType();
               leafComponentType = resolveAnnotations(resolutionScope, this.annotations, leafComponentType);
               this.resolvedType = (TypeBinding)(dimensions > 0 ? scope.environment().createArrayType(leafComponentType, dimensions) : leafComponentType);
            }

            if (annotationsOnDimensions != null) {
               this.resolvedType = resolveAnnotations(resolutionScope, annotationsOnDimensions, this.resolvedType);
               if (this.resolvedType instanceof ArrayBinding) {
                  long[] nullTagBitsPerDimension = ((ArrayBinding)this.resolvedType).nullTagBitsPerDimension;
                  if (nullTagBitsPerDimension != null) {
                     for(int i = 0; i < dimensions; ++i) {
                        if ((nullTagBitsPerDimension[i] & 108086391056891904L) == 108086391056891904L) {
                           scope.problemReporter().contradictoryNullAnnotations(annotationsOnDimensions[i]);
                           nullTagBitsPerDimension[i] = 0L;
                        }
                     }
                  }
               }
            }
         }
      }

      if (scope.compilerOptions().isAnnotationBasedNullAnalysisEnabled && this.resolvedType != null && (this.resolvedType.tagBits & 108086391056891904L) == 0L && !this.resolvedType.isTypeVariable() && !this.resolvedType.isWildcard() && location != 0 && scope.hasDefaultNullnessFor(location, this.sourceStart)) {
         if (location == 256 && this.resolvedType.id == 1) {
            scope.problemReporter().implicitObjectBoundNoNullDefault(this);
         } else {
            LookupEnvironment environment = scope.environment();
            AnnotationBinding[] annots = new AnnotationBinding[]{environment.getNonNullAnnotation()};
            this.resolvedType = environment.createAnnotatedType(this.resolvedType, annots);
         }
      }

   }

   public int getAnnotatableLevels() {
      return 1;
   }

   protected void checkIllegalNullAnnotations(Scope scope, TypeReference[] typeArguments) {
      if (scope.environment().usesNullTypeAnnotations() && typeArguments != null) {
         for(int i = 0; i < typeArguments.length; ++i) {
            TypeReference arg = typeArguments[i];
            if (arg.resolvedType != null) {
               arg.checkIllegalNullAnnotation(scope);
            }
         }
      }

   }

   protected void checkNullConstraints(Scope scope, Substitution substitution, TypeBinding[] variables, int rank) {
      if (variables != null && variables.length > rank) {
         TypeBinding variable = variables[rank];
         if (variable.hasNullTypeAnnotations() && NullAnnotationMatching.analyse(variable, this.resolvedType, (TypeBinding)null, substitution, -1, (Expression)null, NullAnnotationMatching.CheckMode.BOUND_CHECK).isAnyMismatch()) {
            scope.problemReporter().nullityMismatchTypeArgument(variable, this.resolvedType, this);
         }
      }

      this.checkIllegalNullAnnotation(scope);
   }

   protected void checkIllegalNullAnnotation(Scope scope) {
      if (this.resolvedType.leafComponentType().isBaseType() && this.hasNullTypeAnnotation(TypeReference.AnnotationPosition.LEAF_TYPE)) {
         scope.problemReporter().illegalAnnotationForBaseType(this, this.annotations[0], this.resolvedType.tagBits & 108086391056891904L);
      }

   }

   public Annotation findAnnotation(long nullTagBits) {
      if (this.annotations != null) {
         Annotation[] innerAnnotations = this.annotations[this.annotations.length - 1];
         if (innerAnnotations != null) {
            int annBit = nullTagBits == 72057594037927936L ? 32 : 64;

            for(int i = 0; i < innerAnnotations.length; ++i) {
               if (innerAnnotations[i] != null && innerAnnotations[i].hasNullBit(annBit)) {
                  return innerAnnotations[i];
               }
            }
         }
      }

      return null;
   }

   public boolean hasNullTypeAnnotation(AnnotationPosition position) {
      if (this.annotations != null) {
         Annotation[] someAnnotations;
         if (position == TypeReference.AnnotationPosition.MAIN_TYPE) {
            someAnnotations = this.annotations[this.annotations.length - 1];
            return containsNullAnnotation(someAnnotations);
         }

         Annotation[][] var5;
         int var4 = (var5 = this.annotations).length;

         for(int var3 = 0; var3 < var4; ++var3) {
            someAnnotations = var5[var3];
            if (containsNullAnnotation(someAnnotations)) {
               return true;
            }
         }
      }

      return false;
   }

   public static boolean containsNullAnnotation(Annotation[] annotations) {
      if (annotations != null) {
         for(int i = 0; i < annotations.length; ++i) {
            if (annotations[i] != null && annotations[i].hasNullBit(96)) {
               return true;
            }
         }
      }

      return false;
   }

   public TypeReference[] getTypeReferences() {
      return new TypeReference[]{this};
   }

   public boolean isBaseTypeReference() {
      return false;
   }

   public boolean isTypeNameVar(Scope scope) {
      CompilerOptions compilerOptions = scope != null ? scope.compilerOptions() : null;
      if (compilerOptions != null && compilerOptions.sourceLevel < 3538944L) {
         return false;
      } else {
         char[][] typeName = this.getTypeName();
         return typeName.length == 1 && CharOperation.equals(typeName[0], TypeConstants.VAR);
      }
   }

   static class AnnotationCollector extends ASTVisitor {
      List annotationContexts;
      Expression typeReference;
      int targetType;
      int info = 0;
      int info2 = 0;
      LocalVariableBinding localVariable;
      Annotation[][] annotationsOnDimensions;
      int dimensions;
      Wildcard currentWildcard;

      public AnnotationCollector(TypeParameter typeParameter, int targetType, int typeParameterIndex, List annotationContexts) {
         this.annotationContexts = annotationContexts;
         this.typeReference = typeParameter.type;
         this.targetType = targetType;
         this.info = typeParameterIndex;
      }

      public AnnotationCollector(LocalDeclaration localDeclaration, int targetType, LocalVariableBinding localVariable, List annotationContexts) {
         this.annotationContexts = annotationContexts;
         this.typeReference = localDeclaration.type;
         this.targetType = targetType;
         this.localVariable = localVariable;
      }

      public AnnotationCollector(LocalDeclaration localDeclaration, int targetType, int parameterIndex, List annotationContexts) {
         this.annotationContexts = annotationContexts;
         this.typeReference = localDeclaration.type;
         this.targetType = targetType;
         this.info = parameterIndex;
      }

      public AnnotationCollector(TypeReference typeReference, int targetType, List annotationContexts) {
         this.annotationContexts = annotationContexts;
         this.typeReference = typeReference;
         this.targetType = targetType;
      }

      public AnnotationCollector(Expression typeReference, int targetType, int info, List annotationContexts) {
         this.annotationContexts = annotationContexts;
         this.typeReference = typeReference;
         this.info = info;
         this.targetType = targetType;
      }

      public AnnotationCollector(TypeReference typeReference, int targetType, int info, int typeIndex, List annotationContexts) {
         this.annotationContexts = annotationContexts;
         this.typeReference = typeReference;
         this.info = info;
         this.targetType = targetType;
         this.info2 = typeIndex;
      }

      public AnnotationCollector(TypeReference typeReference, int targetType, int info, List annotationContexts, Annotation[][] annotationsOnDimensions, int dimensions) {
         this.annotationContexts = annotationContexts;
         this.typeReference = typeReference;
         this.info = info;
         this.targetType = targetType;
         this.annotationsOnDimensions = annotationsOnDimensions;
         this.dimensions = dimensions;
      }

      private boolean internalVisit(Annotation annotation) {
         AnnotationContext annotationContext = null;
         if (annotation.isRuntimeTypeInvisible()) {
            annotationContext = new AnnotationContext(annotation, this.typeReference, this.targetType, 2);
         } else if (annotation.isRuntimeTypeVisible()) {
            annotationContext = new AnnotationContext(annotation, this.typeReference, this.targetType, 1);
         }

         if (annotationContext != null) {
            annotationContext.wildcard = this.currentWildcard;
            switch (this.targetType) {
               case 0:
               case 1:
               case 16:
               case 22:
               case 23:
               case 66:
               case 67:
               case 68:
               case 69:
               case 70:
                  annotationContext.info = this.info;
                  break;
               case 17:
               case 18:
               case 71:
               case 72:
               case 73:
               case 74:
               case 75:
                  annotationContext.info2 = this.info2;
                  annotationContext.info = this.info;
               case 19:
               case 20:
               case 21:
               default:
                  break;
               case 64:
               case 65:
                  annotationContext.variableBinding = this.localVariable;
            }

            this.annotationContexts.add(annotationContext);
         }

         return true;
      }

      public boolean visit(MarkerAnnotation annotation, BlockScope scope) {
         return this.internalVisit(annotation);
      }

      public boolean visit(NormalAnnotation annotation, BlockScope scope) {
         return this.internalVisit(annotation);
      }

      public boolean visit(SingleMemberAnnotation annotation, BlockScope scope) {
         return this.internalVisit(annotation);
      }

      public boolean visit(Wildcard wildcard, BlockScope scope) {
         this.currentWildcard = wildcard;
         return true;
      }

      public boolean visit(Argument argument, BlockScope scope) {
         if ((argument.bits & 536870912) == 0) {
            return true;
         } else {
            int i = 0;

            for(int max = this.localVariable.initializationCount; i < max; ++i) {
               int startPC = this.localVariable.initializationPCs[i << 1];
               int endPC = this.localVariable.initializationPCs[(i << 1) + 1];
               if (startPC != endPC) {
                  return true;
               }
            }

            return false;
         }
      }

      public boolean visit(Argument argument, ClassScope scope) {
         if ((argument.bits & 536870912) == 0) {
            return true;
         } else {
            int i = 0;

            for(int max = this.localVariable.initializationCount; i < max; ++i) {
               int startPC = this.localVariable.initializationPCs[i << 1];
               int endPC = this.localVariable.initializationPCs[(i << 1) + 1];
               if (startPC != endPC) {
                  return true;
               }
            }

            return false;
         }
      }

      public boolean visit(LocalDeclaration localDeclaration, BlockScope scope) {
         int i = 0;

         for(int max = this.localVariable.initializationCount; i < max; ++i) {
            int startPC = this.localVariable.initializationPCs[i << 1];
            int endPC = this.localVariable.initializationPCs[(i << 1) + 1];
            if (startPC != endPC) {
               return true;
            }
         }

         return false;
      }

      public void endVisit(Wildcard wildcard, BlockScope scope) {
         this.currentWildcard = null;
      }
   }

   public static enum AnnotationPosition {
      MAIN_TYPE,
      LEAF_TYPE,
      ANY;
   }
}
