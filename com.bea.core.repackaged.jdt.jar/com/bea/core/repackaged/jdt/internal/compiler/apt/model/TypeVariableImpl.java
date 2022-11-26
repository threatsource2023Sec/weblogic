package com.bea.core.repackaged.jdt.internal.compiler.apt.model;

import com.bea.core.repackaged.jdt.internal.compiler.apt.dispatch.BaseProcessingEnvImpl;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.ReferenceBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.TypeBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.TypeVariableBinding;
import javax.lang.model.element.Element;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.type.TypeVariable;
import javax.lang.model.type.TypeVisitor;

public class TypeVariableImpl extends TypeMirrorImpl implements TypeVariable {
   TypeVariableImpl(BaseProcessingEnvImpl env, TypeVariableBinding binding) {
      super(env, binding);
   }

   public Element asElement() {
      return this._env.getFactory().newElement(this._binding);
   }

   public TypeMirror getLowerBound() {
      return this._env.getFactory().getNullType();
   }

   public TypeMirror getUpperBound() {
      TypeVariableBinding typeVariableBinding = (TypeVariableBinding)this._binding;
      TypeBinding firstBound = typeVariableBinding.firstBound;
      ReferenceBinding[] superInterfaces = typeVariableBinding.superInterfaces;
      if (firstBound != null && superInterfaces.length != 0) {
         return firstBound != null && superInterfaces.length == 1 && TypeBinding.equalsEquals(superInterfaces[0], firstBound) ? this._env.getFactory().newTypeMirror(typeVariableBinding.upperBound()) : this._env.getFactory().newTypeMirror((TypeVariableBinding)this._binding);
      } else {
         return this._env.getFactory().newTypeMirror(typeVariableBinding.upperBound());
      }
   }

   public Object accept(TypeVisitor v, Object p) {
      return v.visitTypeVariable(this, p);
   }

   public TypeKind getKind() {
      return TypeKind.TYPEVAR;
   }
}
