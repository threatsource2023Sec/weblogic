package com.bea.core.repackaged.jdt.internal.compiler.apt.model;

import com.bea.core.repackaged.jdt.internal.compiler.apt.dispatch.BaseProcessingEnvImpl;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.AnnotationBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.ArrayBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.Binding;
import javax.lang.model.type.ArrayType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.type.TypeVisitor;

public class ArrayTypeImpl extends TypeMirrorImpl implements ArrayType {
   ArrayTypeImpl(BaseProcessingEnvImpl env, ArrayBinding binding) {
      super(env, binding);
   }

   public TypeMirror getComponentType() {
      return this._env.getFactory().newTypeMirror(((ArrayBinding)this._binding).elementsType());
   }

   public Object accept(TypeVisitor v, Object p) {
      return v.visitArray(this, p);
   }

   protected AnnotationBinding[] getAnnotationBindings() {
      AnnotationBinding[] oldies = ((ArrayBinding)this._binding).getTypeAnnotations();
      AnnotationBinding[] newbies = Binding.NO_ANNOTATIONS;
      int i = 0;

      for(int length = oldies == null ? 0 : oldies.length; i < length; ++i) {
         if (oldies[i] == null) {
            System.arraycopy(oldies, 0, newbies = new AnnotationBinding[i], 0, i);
            return newbies;
         }
      }

      return newbies;
   }

   public TypeKind getKind() {
      return TypeKind.ARRAY;
   }
}
