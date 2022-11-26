package com.bea.core.repackaged.jdt.internal.compiler.apt.model;

import com.bea.core.repackaged.jdt.internal.compiler.apt.dispatch.BaseProcessingEnvImpl;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.AnnotationBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.Binding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.TypeBinding;
import java.lang.annotation.Annotation;
import java.lang.reflect.Array;
import java.util.List;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.type.TypeVisitor;

public class TypeMirrorImpl implements TypeMirror {
   protected final BaseProcessingEnvImpl _env;
   protected final Binding _binding;

   TypeMirrorImpl(BaseProcessingEnvImpl env, Binding binding) {
      this._env = env;
      this._binding = binding;
   }

   Binding binding() {
      return this._binding;
   }

   public Object accept(TypeVisitor v, Object p) {
      return v.visit(this, p);
   }

   public TypeKind getKind() {
      switch (this._binding.kind()) {
         case 1:
         case 2:
         case 3:
         case 32:
            throw new IllegalArgumentException("Invalid binding kind: " + this._binding.kind());
         default:
            return null;
      }
   }

   public String toString() {
      return new String(this._binding.readableName());
   }

   public int hashCode() {
      int result = 1;
      result = 31 * result + (this._binding == null ? 0 : this._binding.hashCode());
      return result;
   }

   public boolean equals(Object obj) {
      if (this == obj) {
         return true;
      } else if (!(obj instanceof TypeMirrorImpl)) {
         return false;
      } else {
         TypeMirrorImpl other = (TypeMirrorImpl)obj;
         return this._binding == other._binding;
      }
   }

   public final AnnotationBinding[] getPackedAnnotationBindings() {
      return Factory.getPackedAnnotationBindings(this.getAnnotationBindings());
   }

   protected AnnotationBinding[] getAnnotationBindings() {
      return ((TypeBinding)this._binding).getTypeAnnotations();
   }

   public List getAnnotationMirrors() {
      return this._env == null ? Factory.EMPTY_ANNOTATION_MIRRORS : this._env.getFactory().getAnnotationMirrors(this.getPackedAnnotationBindings());
   }

   public Annotation getAnnotation(Class annotationType) {
      return this._env == null ? null : this._env.getFactory().getAnnotation(this.getPackedAnnotationBindings(), annotationType);
   }

   public Annotation[] getAnnotationsByType(Class annotationType) {
      return this._env == null ? (Annotation[])Array.newInstance(annotationType, 0) : this._env.getFactory().getAnnotationsByType(Factory.getUnpackedAnnotationBindings(this.getPackedAnnotationBindings()), annotationType);
   }
}
