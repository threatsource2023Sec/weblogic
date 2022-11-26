package com.bea.core.repackaged.jdt.internal.compiler.lookup;

import com.bea.core.repackaged.jdt.internal.compiler.classfmt.ExternalAnnotationProvider;
import com.bea.core.repackaged.jdt.internal.compiler.env.IBinaryAnnotation;
import com.bea.core.repackaged.jdt.internal.compiler.env.ITypeAnnotationWalker;
import com.bea.core.repackaged.jdt.internal.compiler.util.Messages;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

class ExternalAnnotationSuperimposer extends TypeBindingVisitor {
   private ITypeAnnotationWalker currentWalker;
   private TypeBinding typeReplacement;
   private LookupEnvironment environment;
   private boolean isReplacing;

   public static void apply(SourceTypeBinding typeBinding, String externalAnnotationPath) {
      ZipFile zipFile = null;

      try {
         File annotationBase = new File(externalAnnotationPath);
         if (annotationBase.exists()) {
            String binaryTypeName = String.valueOf(typeBinding.constantPoolName());
            String relativeFileName = binaryTypeName.replace('.', '/') + ".eea";
            Object input;
            if (annotationBase.isDirectory()) {
               input = new FileInputStream(externalAnnotationPath + '/' + relativeFileName);
            } else {
               zipFile = new ZipFile(externalAnnotationPath);
               ZipEntry zipEntry = zipFile.getEntry(relativeFileName);
               if (zipEntry == null) {
                  return;
               }

               input = zipFile.getInputStream(zipEntry);
            }

            annotateType(typeBinding, new ExternalAnnotationProvider((InputStream)input, binaryTypeName), typeBinding.environment);
         }
      } catch (FileNotFoundException var18) {
      } catch (IOException var19) {
         typeBinding.scope.problemReporter().abortDueToInternalError(Messages.bind(Messages.abort_externaAnnotationFile, (Object[])(new String[]{String.valueOf(typeBinding.readableName()), externalAnnotationPath, var19.getMessage()})));
      } finally {
         if (zipFile != null) {
            try {
               zipFile.close();
            } catch (IOException var17) {
            }
         }

      }

   }

   static void annotateType(SourceTypeBinding binding, ExternalAnnotationProvider provider, LookupEnvironment environment) {
      ITypeAnnotationWalker typeWalker = provider.forTypeHeader(environment);
      if (typeWalker != null && typeWalker != ITypeAnnotationWalker.EMPTY_ANNOTATION_WALKER) {
         ExternalAnnotationSuperimposer visitor = new ExternalAnnotationSuperimposer(environment);
         TypeVariableBinding[] typeParameters = binding.typeVariables();

         for(int i = 0; i < typeParameters.length; ++i) {
            if (visitor.go(typeWalker.toTypeParameter(true, i))) {
               typeParameters[i] = (TypeVariableBinding)visitor.superimpose(typeParameters[i], TypeVariableBinding.class);
            }
         }
      }

      binding.externalAnnotationProvider = provider;
   }

   public static void annotateFieldBinding(FieldBinding field, ExternalAnnotationProvider provider, LookupEnvironment environment) {
      char[] fieldSignature = field.genericSignature();
      if (fieldSignature == null && field.type != null) {
         fieldSignature = field.type.signature();
      }

      ITypeAnnotationWalker walker = provider.forField(field.name, fieldSignature, environment);
      ExternalAnnotationSuperimposer visitor = new ExternalAnnotationSuperimposer(environment);
      if (visitor.go(walker)) {
         field.type = visitor.superimpose(field.type, TypeBinding.class);
      }

   }

   public static void annotateMethodBinding(MethodBinding method, ExternalAnnotationProvider provider, LookupEnvironment environment) {
      char[] methodSignature = method.genericSignature();
      if (methodSignature == null) {
         methodSignature = method.signature();
      }

      ITypeAnnotationWalker walker = provider.forMethod(method.selector, methodSignature, environment);
      if (walker != null && walker != ITypeAnnotationWalker.EMPTY_ANNOTATION_WALKER) {
         ExternalAnnotationSuperimposer visitor = new ExternalAnnotationSuperimposer(environment);
         TypeVariableBinding[] typeParams = method.typeVariables;

         for(short i = 0; i < typeParams.length; ++i) {
            if (visitor.go(walker.toTypeParameter(false, i))) {
               typeParams[i] = (TypeVariableBinding)visitor.superimpose(typeParams[i], TypeVariableBinding.class);
            }
         }

         if (!method.isConstructor() && visitor.go(walker.toMethodReturn())) {
            method.returnType = visitor.superimpose(method.returnType, TypeBinding.class);
         }

         TypeBinding[] parameters = method.parameters;

         for(short i = 0; i < parameters.length; ++i) {
            if (visitor.go(walker.toMethodParameter(i))) {
               parameters[i] = visitor.superimpose(parameters[i], TypeBinding.class);
            }
         }
      }

   }

   ExternalAnnotationSuperimposer(LookupEnvironment environment) {
      this.environment = environment;
   }

   private ExternalAnnotationSuperimposer(TypeBinding typeReplacement, boolean isReplacing, ITypeAnnotationWalker walker) {
      this.typeReplacement = typeReplacement;
      this.isReplacing = isReplacing;
      this.currentWalker = walker;
   }

   private ExternalAnnotationSuperimposer snapshot() {
      ExternalAnnotationSuperimposer memento = new ExternalAnnotationSuperimposer(this.typeReplacement, this.isReplacing, this.currentWalker);
      this.typeReplacement = null;
      this.isReplacing = false;
      return memento;
   }

   private void restore(ExternalAnnotationSuperimposer memento) {
      this.isReplacing = memento.isReplacing;
      this.currentWalker = memento.currentWalker;
   }

   boolean go(ITypeAnnotationWalker walker) {
      this.reset();
      this.typeReplacement = null;
      this.isReplacing = false;
      this.currentWalker = walker;
      return walker != ITypeAnnotationWalker.EMPTY_ANNOTATION_WALKER;
   }

   TypeBinding superimpose(TypeBinding type, Class cl) {
      TypeBindingVisitor.visit(this, (TypeBinding)type);
      return cl.isInstance(this.typeReplacement) ? (TypeBinding)cl.cast(this.typeReplacement) : type;
   }

   private TypeBinding goAndSuperimpose(ITypeAnnotationWalker walker, TypeBinding type) {
      if (walker == ITypeAnnotationWalker.EMPTY_ANNOTATION_WALKER) {
         return type;
      } else {
         this.currentWalker = walker;
         TypeBindingVisitor.visit(this, (TypeBinding)type);
         if (this.typeReplacement == null) {
            return type;
         } else {
            this.isReplacing = true;
            TypeBinding answer = this.typeReplacement;
            this.typeReplacement = null;
            return answer;
         }
      }
   }

   public boolean visit(ArrayBinding arrayBinding) {
      ExternalAnnotationSuperimposer memento = this.snapshot();

      try {
         int dims = arrayBinding.dimensions;
         AnnotationBinding[][] annotsOnDims = new AnnotationBinding[dims][];
         ITypeAnnotationWalker walker = this.currentWalker;

         for(int i = 0; i < dims; ++i) {
            IBinaryAnnotation[] binaryAnnotations = walker.getAnnotationsAtCursor(arrayBinding.id, false);
            if (binaryAnnotations != ITypeAnnotationWalker.NO_ANNOTATIONS) {
               annotsOnDims[i] = BinaryTypeBinding.createAnnotations(binaryAnnotations, this.environment, (char[][][])null);
               this.isReplacing = true;
            } else {
               annotsOnDims[i] = Binding.NO_ANNOTATIONS;
            }

            walker = walker.toNextArrayDimension();
         }

         TypeBinding leafComponentType = this.goAndSuperimpose(walker, arrayBinding.leafComponentType());
         if (this.isReplacing) {
            this.typeReplacement = this.environment.createArrayType(leafComponentType, dims, AnnotatableTypeSystem.flattenedAnnotations(annotsOnDims));
         }
      } finally {
         this.restore(memento);
      }

      return false;
   }

   public boolean visit(BaseTypeBinding baseTypeBinding) {
      return false;
   }

   public boolean visit(IntersectionTypeBinding18 intersectionTypeBinding18) {
      return false;
   }

   public boolean visit(ParameterizedTypeBinding parameterizedTypeBinding) {
      ExternalAnnotationSuperimposer memento = this.snapshot();

      try {
         IBinaryAnnotation[] binaryAnnotations = this.currentWalker.getAnnotationsAtCursor(parameterizedTypeBinding.id, false);
         AnnotationBinding[] annotations = Binding.NO_ANNOTATIONS;
         if (binaryAnnotations != ITypeAnnotationWalker.NO_ANNOTATIONS) {
            annotations = BinaryTypeBinding.createAnnotations(binaryAnnotations, this.environment, (char[][][])null);
            this.isReplacing = true;
         }

         TypeBinding[] typeArguments = parameterizedTypeBinding.typeArguments();
         TypeBinding[] newArguments = new TypeBinding[typeArguments.length];

         for(int i = 0; i < typeArguments.length; ++i) {
            newArguments[i] = this.goAndSuperimpose(memento.currentWalker.toTypeArgument(i), typeArguments[i]);
         }

         if (this.isReplacing) {
            this.typeReplacement = this.environment.createParameterizedType(parameterizedTypeBinding.genericType(), newArguments, parameterizedTypeBinding.enclosingType(), annotations);
         }
      } finally {
         this.restore(memento);
      }

      return false;
   }

   public boolean visit(RawTypeBinding rawTypeBinding) {
      return this.visit((ReferenceBinding)rawTypeBinding);
   }

   public boolean visit(ReferenceBinding referenceBinding) {
      IBinaryAnnotation[] binaryAnnotations = this.currentWalker.getAnnotationsAtCursor(referenceBinding.id, false);
      if (binaryAnnotations != ITypeAnnotationWalker.NO_ANNOTATIONS) {
         this.typeReplacement = this.environment.createAnnotatedType(referenceBinding, (AnnotationBinding[])BinaryTypeBinding.createAnnotations(binaryAnnotations, this.environment, (char[][][])null));
      }

      return false;
   }

   public boolean visit(TypeVariableBinding typeVariable) {
      return this.visit((ReferenceBinding)typeVariable);
   }

   public boolean visit(WildcardBinding wildcardBinding) {
      TypeBinding bound = wildcardBinding.bound;
      ExternalAnnotationSuperimposer memento = this.snapshot();

      try {
         if (bound != null) {
            bound = this.goAndSuperimpose(memento.currentWalker.toWildcardBound(), bound);
         }

         IBinaryAnnotation[] binaryAnnotations = memento.currentWalker.getAnnotationsAtCursor(-1, false);
         if (this.isReplacing || binaryAnnotations != ITypeAnnotationWalker.NO_ANNOTATIONS) {
            TypeBinding[] otherBounds = wildcardBinding.otherBounds;
            if (binaryAnnotations != ITypeAnnotationWalker.NO_ANNOTATIONS) {
               AnnotationBinding[] annotations = BinaryTypeBinding.createAnnotations(binaryAnnotations, this.environment, (char[][][])null);
               this.typeReplacement = this.environment.createWildcard(wildcardBinding.genericType, wildcardBinding.rank, bound, otherBounds, wildcardBinding.boundKind, annotations);
            } else {
               this.typeReplacement = this.environment.createWildcard(wildcardBinding.genericType, wildcardBinding.rank, bound, otherBounds, wildcardBinding.boundKind);
            }
         }
      } finally {
         this.restore(memento);
      }

      return false;
   }
}
