package com.bea.core.repackaged.jdt.internal.compiler.apt.dispatch;

import com.bea.core.repackaged.jdt.internal.compiler.apt.model.Factory;
import com.bea.core.repackaged.jdt.internal.compiler.apt.model.TypeElementImpl;
import com.bea.core.repackaged.jdt.internal.compiler.apt.util.ManyToMany;
import com.bea.core.repackaged.jdt.internal.compiler.ast.CompilationUnitDeclaration;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.AnnotationBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.Binding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.FieldBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.MethodBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.ModuleBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.ParameterizedTypeBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.ReferenceBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.SourceTypeBinding;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.ElementFilter;

public class RoundEnvImpl implements RoundEnvironment {
   private final BaseProcessingEnvImpl _processingEnv;
   private final boolean _isLastRound;
   private final CompilationUnitDeclaration[] _units;
   private final ManyToMany _annoToUnit;
   private final ReferenceBinding[] _binaryTypes;
   private final Factory _factory;
   private Set _rootElements = null;

   public RoundEnvImpl(CompilationUnitDeclaration[] units, ReferenceBinding[] binaryTypeBindings, boolean isLastRound, BaseProcessingEnvImpl env) {
      this._processingEnv = env;
      this._isLastRound = isLastRound;
      this._units = units;
      this._factory = this._processingEnv.getFactory();
      AnnotationDiscoveryVisitor visitor = new AnnotationDiscoveryVisitor(this._processingEnv);
      if (this._units != null) {
         CompilationUnitDeclaration[] var9;
         int var8 = (var9 = this._units).length;

         for(int var7 = 0; var7 < var8; ++var7) {
            CompilationUnitDeclaration unit = var9[var7];
            unit.scope.environment.suppressImportErrors = true;
            unit.traverse(visitor, unit.scope);
            unit.scope.environment.suppressImportErrors = false;
         }
      }

      this._annoToUnit = visitor._annoToElement;
      if (binaryTypeBindings != null) {
         this.collectAnnotations(binaryTypeBindings);
      }

      this._binaryTypes = binaryTypeBindings;
   }

   private void collectAnnotations(ReferenceBinding[] referenceBindings) {
      ReferenceBinding[] var5 = referenceBindings;
      int var4 = referenceBindings.length;

      for(int var3 = 0; var3 < var4; ++var3) {
         ReferenceBinding referenceBinding = var5[var3];
         if (referenceBinding instanceof ParameterizedTypeBinding) {
            referenceBinding = ((ParameterizedTypeBinding)referenceBinding).genericType();
         }

         AnnotationBinding[] annotationBindings = Factory.getPackedAnnotationBindings(referenceBinding.getAnnotations());
         AnnotationBinding[] var10 = annotationBindings;
         int var9 = annotationBindings.length;

         for(int var8 = 0; var8 < var9; ++var8) {
            AnnotationBinding annotationBinding = var10[var8];
            TypeElement anno = (TypeElement)this._factory.newElement(annotationBinding.getAnnotationType());
            Element element = this._factory.newElement(referenceBinding);
            this._annoToUnit.put(anno, element);
         }

         FieldBinding[] fieldBindings = referenceBinding.fields();
         FieldBinding[] var25 = fieldBindings;
         int var23 = fieldBindings.length;

         int var14;
         for(var9 = 0; var9 < var23; ++var9) {
            FieldBinding fieldBinding = var25[var9];
            annotationBindings = Factory.getPackedAnnotationBindings(fieldBinding.getAnnotations());
            AnnotationBinding[] var15 = annotationBindings;
            var14 = annotationBindings.length;

            for(int var13 = 0; var13 < var14; ++var13) {
               AnnotationBinding annotationBinding = var15[var13];
               TypeElement anno = (TypeElement)this._factory.newElement(annotationBinding.getAnnotationType());
               Element element = this._factory.newElement(fieldBinding);
               this._annoToUnit.put(anno, element);
            }
         }

         MethodBinding[] methodBindings = referenceBinding.methods();
         MethodBinding[] var28 = methodBindings;
         int var26 = methodBindings.length;

         for(var23 = 0; var23 < var26; ++var23) {
            MethodBinding methodBinding = var28[var23];
            annotationBindings = Factory.getPackedAnnotationBindings(methodBinding.getAnnotations());
            AnnotationBinding[] var31 = annotationBindings;
            int var30 = annotationBindings.length;

            for(var14 = 0; var14 < var30; ++var14) {
               AnnotationBinding annotationBinding = var31[var14];
               TypeElement anno = (TypeElement)this._factory.newElement(annotationBinding.getAnnotationType());
               Element element = this._factory.newElement(methodBinding);
               this._annoToUnit.put(anno, element);
            }
         }

         ReferenceBinding[] memberTypes = referenceBinding.memberTypes();
         this.collectAnnotations(memberTypes);
      }

   }

   public Set getRootAnnotations() {
      return Collections.unmodifiableSet(this._annoToUnit.getKeySet());
   }

   public boolean errorRaised() {
      return this._processingEnv.errorRaised();
   }

   public Set getElementsAnnotatedWith(TypeElement a) {
      if (a.getKind() != ElementKind.ANNOTATION_TYPE) {
         throw new IllegalArgumentException("Argument must represent an annotation type");
      } else {
         Binding annoBinding = ((TypeElementImpl)a)._binding;
         if (0L == (annoBinding.getAnnotationTagBits() & 281474976710656L)) {
            return Collections.unmodifiableSet(this._annoToUnit.getValues(a));
         } else {
            Set annotatedElements = new HashSet(this._annoToUnit.getValues(a));
            ReferenceBinding annoTypeBinding = (ReferenceBinding)annoBinding;
            Iterator var6 = ElementFilter.typesIn(this.getRootElements()).iterator();

            while(var6.hasNext()) {
               TypeElement element = (TypeElement)var6.next();
               ReferenceBinding typeBinding = (ReferenceBinding)((TypeElementImpl)element)._binding;
               this.addAnnotatedElements(annoTypeBinding, typeBinding, annotatedElements);
            }

            return Collections.unmodifiableSet(annotatedElements);
         }
      }
   }

   private void addAnnotatedElements(ReferenceBinding anno, ReferenceBinding type, Set result) {
      if (type.isClass() && this.inheritsAnno(type, anno)) {
         result.add(this._factory.newElement(type));
      }

      ReferenceBinding[] var7;
      int var6 = (var7 = type.memberTypes()).length;

      for(int var5 = 0; var5 < var6; ++var5) {
         ReferenceBinding element = var7[var5];
         this.addAnnotatedElements(anno, element, result);
      }

   }

   private boolean inheritsAnno(ReferenceBinding element, ReferenceBinding anno) {
      ReferenceBinding searchedElement = element;

      do {
         if (searchedElement instanceof ParameterizedTypeBinding) {
            searchedElement = ((ParameterizedTypeBinding)searchedElement).genericType();
         }

         AnnotationBinding[] annos = Factory.getPackedAnnotationBindings(searchedElement.getAnnotations());
         AnnotationBinding[] var8 = annos;
         int var7 = annos.length;

         for(int var6 = 0; var6 < var7; ++var6) {
            AnnotationBinding annoBinding = var8[var6];
            if (annoBinding.getAnnotationType() == anno) {
               return true;
            }
         }
      } while((searchedElement = searchedElement.superclass()) != null);

      return false;
   }

   public Set getElementsAnnotatedWith(Class a) {
      String canonicalName = a.getCanonicalName();
      if (canonicalName == null) {
         throw new IllegalArgumentException("Argument must represent an annotation type");
      } else {
         TypeElement annoType = this._processingEnv.getElementUtils().getTypeElement(canonicalName);
         return annoType == null ? Collections.emptySet() : this.getElementsAnnotatedWith(annoType);
      }
   }

   public Set getRootElements() {
      if (this._units == null) {
         return Collections.emptySet();
      } else {
         if (this._rootElements == null) {
            Set elements = new HashSet(this._units.length);
            CompilationUnitDeclaration[] var5;
            int var4 = (var5 = this._units).length;

            int var3;
            Element element;
            for(var3 = 0; var3 < var4; ++var3) {
               CompilationUnitDeclaration unit = var5[var3];
               if (unit.moduleDeclaration != null && unit.moduleDeclaration.binding != null) {
                  element = this._factory.newElement(unit.moduleDeclaration.binding);
                  elements.add(element);
               } else if (unit.scope != null && unit.scope.topLevelTypes != null) {
                  SourceTypeBinding[] var9;
                  int var8 = (var9 = unit.scope.topLevelTypes).length;

                  for(int var7 = 0; var7 < var8; ++var7) {
                     SourceTypeBinding binding = var9[var7];
                     Element element = this._factory.newElement(binding);
                     if (element == null) {
                        throw new IllegalArgumentException("Top-level type binding could not be converted to element: " + binding);
                     }

                     elements.add(element);
                  }
               }
            }

            if (this._binaryTypes != null) {
               ReferenceBinding[] var12;
               var4 = (var12 = this._binaryTypes).length;

               for(var3 = 0; var3 < var4; ++var3) {
                  ReferenceBinding typeBinding = var12[var3];
                  element = this._factory.newElement(typeBinding);
                  if (element == null) {
                     throw new IllegalArgumentException("Top-level type binding could not be converted to element: " + typeBinding);
                  }

                  elements.add(element);
                  ModuleBinding binding = typeBinding.module();
                  if (binding != null) {
                     Element m = this._factory.newElement(binding);
                     elements.add(m);
                  }
               }
            }

            this._rootElements = elements;
         }

         return this._rootElements;
      }
   }

   public boolean processingOver() {
      return this._isLastRound;
   }
}
