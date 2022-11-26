package com.bea.core.repackaged.jdt.internal.compiler.apt.model;

import com.bea.core.repackaged.jdt.internal.compiler.apt.dispatch.BaseProcessingEnvImpl;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.TypeBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.WildcardBinding;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.type.TypeVisitor;
import javax.lang.model.type.WildcardType;

public class WildcardTypeImpl extends TypeMirrorImpl implements WildcardType {
   WildcardTypeImpl(BaseProcessingEnvImpl env, WildcardBinding binding) {
      super(env, binding);
   }

   public TypeMirror getExtendsBound() {
      WildcardBinding wildcardBinding = (WildcardBinding)this._binding;
      if (wildcardBinding.boundKind != 1) {
         return null;
      } else {
         TypeBinding bound = wildcardBinding.bound;
         return bound == null ? null : this._env.getFactory().newTypeMirror(bound);
      }
   }

   public TypeKind getKind() {
      return TypeKind.WILDCARD;
   }

   public TypeMirror getSuperBound() {
      WildcardBinding wildcardBinding = (WildcardBinding)this._binding;
      if (wildcardBinding.boundKind != 2) {
         return null;
      } else {
         TypeBinding bound = wildcardBinding.bound;
         return bound == null ? null : this._env.getFactory().newTypeMirror(bound);
      }
   }

   public Object accept(TypeVisitor v, Object p) {
      return v.visitWildcard(this, p);
   }
}
