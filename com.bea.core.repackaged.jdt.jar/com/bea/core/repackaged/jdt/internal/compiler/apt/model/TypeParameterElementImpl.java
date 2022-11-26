package com.bea.core.repackaged.jdt.internal.compiler.apt.model;

import com.bea.core.repackaged.jdt.internal.compiler.apt.dispatch.BaseProcessingEnvImpl;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.AnnotationBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.LookupEnvironment;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.ReferenceBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.TypeBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.TypeVariableBinding;
import java.lang.annotation.Annotation;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ElementVisitor;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeParameterElement;
import javax.lang.model.type.TypeMirror;

public class TypeParameterElementImpl extends ElementImpl implements TypeParameterElement {
   private final Element _declaringElement;
   private List _bounds = null;

   TypeParameterElementImpl(BaseProcessingEnvImpl env, TypeVariableBinding binding, Element declaringElement) {
      super(env, binding);
      this._declaringElement = declaringElement;
   }

   TypeParameterElementImpl(BaseProcessingEnvImpl env, TypeVariableBinding binding) {
      super(env, binding);
      this._declaringElement = this._env.getFactory().newElement(binding.declaringElement);
   }

   public List getBounds() {
      if (this._bounds == null) {
         this._bounds = this.calculateBounds();
      }

      return this._bounds;
   }

   private List calculateBounds() {
      TypeVariableBinding typeVariableBinding = (TypeVariableBinding)this._binding;
      ReferenceBinding varSuperclass = typeVariableBinding.superclass();
      TypeBinding firstClassOrArrayBound = typeVariableBinding.firstBound;
      int boundsLength = 0;
      boolean isFirstBoundATypeVariable = false;
      if (firstClassOrArrayBound != null) {
         if (firstClassOrArrayBound.isTypeVariable()) {
            isFirstBoundATypeVariable = true;
         }

         if (TypeBinding.equalsEquals(firstClassOrArrayBound, varSuperclass)) {
            ++boundsLength;
            if (firstClassOrArrayBound.isTypeVariable()) {
               isFirstBoundATypeVariable = true;
            }
         } else if (firstClassOrArrayBound.isArrayType()) {
            ++boundsLength;
         } else {
            firstClassOrArrayBound = null;
         }
      }

      ReferenceBinding[] superinterfaces = typeVariableBinding.superInterfaces();
      int superinterfacesLength = 0;
      if (superinterfaces != null) {
         superinterfacesLength = superinterfaces.length;
         boundsLength += superinterfacesLength;
      }

      List typeBounds = new ArrayList(boundsLength);
      if (boundsLength != 0) {
         if (firstClassOrArrayBound != null) {
            TypeMirror typeBinding = this._env.getFactory().newTypeMirror(firstClassOrArrayBound);
            if (typeBinding == null) {
               return Collections.emptyList();
            }

            typeBounds.add(typeBinding);
         }

         if (superinterfaces != null && !isFirstBoundATypeVariable) {
            for(int i = 0; i < superinterfacesLength; ++i) {
               TypeMirror typeBinding = this._env.getFactory().newTypeMirror(superinterfaces[i]);
               if (typeBinding == null) {
                  return Collections.emptyList();
               }

               typeBounds.add(typeBinding);
            }
         }
      } else {
         typeBounds.add(this._env.getFactory().newTypeMirror(this._env.getLookupEnvironment().getType(LookupEnvironment.JAVA_LANG_OBJECT)));
      }

      return Collections.unmodifiableList(typeBounds);
   }

   public Element getGenericElement() {
      return this._declaringElement;
   }

   public Object accept(ElementVisitor v, Object p) {
      return v.visitTypeParameter(this, p);
   }

   protected AnnotationBinding[] getAnnotationBindings() {
      return ((TypeVariableBinding)this._binding).getTypeAnnotations();
   }

   private boolean shouldEmulateJavacBug() {
      if (this._env.getLookupEnvironment().globalOptions.emulateJavacBug8031744) {
         AnnotationBinding[] annotations = this.getAnnotationBindings();
         int i = 0;

         for(int length = annotations.length; i < length; ++i) {
            ReferenceBinding firstAnnotationType = annotations[i].getAnnotationType();

            for(int j = i + 1; j < length; ++j) {
               ReferenceBinding secondAnnotationType = annotations[j].getAnnotationType();
               if (firstAnnotationType == secondAnnotationType) {
                  return true;
               }
            }
         }
      }

      return false;
   }

   public List getAnnotationMirrors() {
      return this.shouldEmulateJavacBug() ? Collections.emptyList() : super.getAnnotationMirrors();
   }

   public Annotation[] getAnnotationsByType(Class annotationType) {
      return this.shouldEmulateJavacBug() ? (Annotation[])Array.newInstance(annotationType, 0) : super.getAnnotationsByType(annotationType);
   }

   public Annotation getAnnotation(Class annotationType) {
      return this.shouldEmulateJavacBug() ? null : super.getAnnotation(annotationType);
   }

   public List getEnclosedElements() {
      return Collections.emptyList();
   }

   public Element getEnclosingElement() {
      return this.getGenericElement();
   }

   public ElementKind getKind() {
      return ElementKind.TYPE_PARAMETER;
   }

   PackageElement getPackage() {
      return null;
   }

   public String toString() {
      return new String(this._binding.readableName());
   }
}
