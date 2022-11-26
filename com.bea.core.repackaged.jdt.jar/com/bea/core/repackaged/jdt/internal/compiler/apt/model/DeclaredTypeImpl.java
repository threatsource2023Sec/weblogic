package com.bea.core.repackaged.jdt.internal.compiler.apt.model;

import com.bea.core.repackaged.jdt.internal.compiler.apt.dispatch.BaseProcessingEnvImpl;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.ParameterizedTypeBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.ReferenceBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.TypeBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.TypeVariableBinding;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.type.TypeVisitor;

public class DeclaredTypeImpl extends TypeMirrorImpl implements DeclaredType {
   private final ElementKind _elementKindHint;

   DeclaredTypeImpl(BaseProcessingEnvImpl env, ReferenceBinding binding) {
      super(env, binding);
      this._elementKindHint = null;
   }

   DeclaredTypeImpl(BaseProcessingEnvImpl env, ReferenceBinding binding, ElementKind elementKindHint) {
      super(env, binding);
      this._elementKindHint = elementKindHint;
   }

   public Element asElement() {
      TypeBinding prototype = null;
      if (this._binding instanceof TypeBinding) {
         prototype = ((TypeBinding)this._binding).prototype();
      }

      return prototype != null ? this._env.getFactory().newElement(prototype, this._elementKindHint) : this._env.getFactory().newElement((ReferenceBinding)this._binding, this._elementKindHint);
   }

   public TypeMirror getEnclosingType() {
      ReferenceBinding binding = (ReferenceBinding)this._binding;
      ReferenceBinding enclosingType = binding.enclosingType();
      return (TypeMirror)(enclosingType != null ? this._env.getFactory().newTypeMirror(enclosingType) : this._env.getFactory().getNoType(TypeKind.NONE));
   }

   public List getTypeArguments() {
      ReferenceBinding binding = (ReferenceBinding)this._binding;
      if (binding.isParameterizedType()) {
         ParameterizedTypeBinding ptb = (ParameterizedTypeBinding)this._binding;
         TypeBinding[] arguments = ptb.arguments;
         int length = arguments == null ? 0 : arguments.length;
         if (length == 0) {
            return Collections.emptyList();
         } else {
            List args = new ArrayList(length);
            TypeBinding[] var9 = arguments;
            int var8 = arguments.length;

            for(int var15 = 0; var15 < var8; ++var15) {
               TypeBinding arg = var9[var15];
               args.add(this._env.getFactory().newTypeMirror(arg));
            }

            return Collections.unmodifiableList(args);
         }
      } else if (!binding.isGenericType()) {
         return Collections.emptyList();
      } else {
         TypeVariableBinding[] typeVariables = binding.typeVariables();
         List args = new ArrayList(typeVariables.length);
         TypeVariableBinding[] var7 = typeVariables;
         int var6 = typeVariables.length;

         for(int var5 = 0; var5 < var6; ++var5) {
            TypeBinding arg = var7[var5];
            args.add(this._env.getFactory().newTypeMirror(arg));
         }

         return Collections.unmodifiableList(args);
      }
   }

   public Object accept(TypeVisitor v, Object p) {
      return v.visitDeclared(this, p);
   }

   public TypeKind getKind() {
      return TypeKind.DECLARED;
   }

   public String toString() {
      return new String(this._binding.readableName());
   }
}
