package com.bea.core.repackaged.jdt.internal.compiler.apt.model;

import com.bea.core.repackaged.jdt.internal.compiler.apt.dispatch.BaseProcessingEnvImpl;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.AnnotationBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.Binding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.ReferenceBinding;
import java.lang.annotation.Annotation;
import java.lang.annotation.Inherited;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Name;
import javax.lang.model.element.PackageElement;
import javax.lang.model.type.TypeMirror;

public abstract class ElementImpl implements Element, IElementInfo {
   public final BaseProcessingEnvImpl _env;
   public final Binding _binding;

   protected ElementImpl(BaseProcessingEnvImpl env, Binding binding) {
      this._env = env;
      this._binding = binding;
   }

   public TypeMirror asType() {
      return this._env.getFactory().newTypeMirror(this._binding);
   }

   protected abstract AnnotationBinding[] getAnnotationBindings();

   public final AnnotationBinding[] getPackedAnnotationBindings() {
      return Factory.getPackedAnnotationBindings(this.getAnnotationBindings());
   }

   public Annotation getAnnotation(Class annotationClass) {
      Annotation annotation = this._env.getFactory().getAnnotation(this.getPackedAnnotationBindings(), annotationClass);
      if (annotation == null && this.getKind() == ElementKind.CLASS && annotationClass.getAnnotation(Inherited.class) != null) {
         ElementImpl superClass = (ElementImpl)this._env.getFactory().newElement(((ReferenceBinding)this._binding).superclass());
         return superClass == null ? null : superClass.getAnnotation(annotationClass);
      } else {
         return annotation;
      }
   }

   public List getAnnotationMirrors() {
      return this._env.getFactory().getAnnotationMirrors(this.getPackedAnnotationBindings());
   }

   public Annotation[] getAnnotationsByType(Class annotationType) {
      Annotation[] annotations = this._env.getFactory().getAnnotationsByType(Factory.getUnpackedAnnotationBindings(this.getPackedAnnotationBindings()), annotationType);
      if (annotations.length == 0 && this.getKind() == ElementKind.CLASS && annotationType.getAnnotation(Inherited.class) != null) {
         ElementImpl superClass = (ElementImpl)this._env.getFactory().newElement(((ReferenceBinding)this._binding).superclass());
         return superClass == null ? annotations : superClass.getAnnotationsByType(annotationType);
      } else {
         return annotations;
      }
   }

   public Set getModifiers() {
      return Collections.emptySet();
   }

   public Name getSimpleName() {
      return new NameImpl(this._binding.shortReadableName());
   }

   public int hashCode() {
      return this._binding.hashCode();
   }

   public boolean equals(Object obj) {
      if (this == obj) {
         return true;
      } else if (obj == null) {
         return false;
      } else if (this.getClass() != obj.getClass()) {
         return false;
      } else {
         ElementImpl other = (ElementImpl)obj;
         if (this._binding == null) {
            if (other._binding != null) {
               return false;
            }
         } else if (this._binding != other._binding) {
            return false;
         }

         return true;
      }
   }

   public String toString() {
      return this._binding.toString();
   }

   public String getFileName() {
      return null;
   }

   PackageElement getPackage() {
      return null;
   }

   public boolean hides(Element hidden) {
      return false;
   }
}
