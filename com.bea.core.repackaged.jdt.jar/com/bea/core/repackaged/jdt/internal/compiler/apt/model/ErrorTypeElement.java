package com.bea.core.repackaged.jdt.internal.compiler.apt.model;

import com.bea.core.repackaged.jdt.core.compiler.CharOperation;
import com.bea.core.repackaged.jdt.internal.compiler.apt.dispatch.BaseProcessingEnvImpl;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.ReferenceBinding;
import java.lang.annotation.Annotation;
import java.lang.reflect.Array;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Name;
import javax.lang.model.element.NestingKind;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;

public class ErrorTypeElement extends TypeElementImpl {
   ErrorTypeElement(BaseProcessingEnvImpl env, ReferenceBinding binding) {
      super(env, binding, (ElementKind)null);
   }

   public List getInterfaces() {
      return Collections.emptyList();
   }

   public NestingKind getNestingKind() {
      return NestingKind.TOP_LEVEL;
   }

   public Name getQualifiedName() {
      ReferenceBinding binding = (ReferenceBinding)this._binding;
      char[] qName;
      if (binding.isMemberType()) {
         qName = CharOperation.concatWith(binding.enclosingType().compoundName, binding.sourceName, '.');
         CharOperation.replace(qName, '$', '.');
      } else {
         qName = CharOperation.concatWith(binding.compoundName, '.');
      }

      return new NameImpl(qName);
   }

   public TypeMirror getSuperclass() {
      return this._env.getFactory().getNoType(TypeKind.NONE);
   }

   public List getTypeParameters() {
      return Collections.emptyList();
   }

   public TypeMirror asType() {
      return this._env.getFactory().getErrorType((ReferenceBinding)this._binding);
   }

   public Annotation getAnnotation(Class annotationType) {
      return null;
   }

   public List getAnnotationMirrors() {
      return Collections.emptyList();
   }

   public Annotation[] getAnnotationsByType(Class annotationType) {
      return (Annotation[])Array.newInstance(annotationType, 0);
   }

   public List getEnclosedElements() {
      return Collections.emptyList();
   }

   public Element getEnclosingElement() {
      return this._env.getFactory().newPackageElement(this._env.getLookupEnvironment().defaultPackage);
   }

   public ElementKind getKind() {
      return ElementKind.CLASS;
   }

   public Set getModifiers() {
      return Collections.emptySet();
   }

   public Name getSimpleName() {
      ReferenceBinding binding = (ReferenceBinding)this._binding;
      return new NameImpl(binding.sourceName());
   }
}
