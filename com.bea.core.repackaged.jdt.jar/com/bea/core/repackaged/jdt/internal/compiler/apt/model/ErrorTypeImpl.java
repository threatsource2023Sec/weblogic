package com.bea.core.repackaged.jdt.internal.compiler.apt.model;

import com.bea.core.repackaged.jdt.internal.compiler.apt.dispatch.BaseProcessingEnvImpl;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.ParameterizedTypeBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.ReferenceBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.TypeBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.TypeVariableBinding;
import java.lang.annotation.Annotation;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.lang.model.element.Element;
import javax.lang.model.type.ErrorType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.type.TypeVisitor;

public class ErrorTypeImpl extends DeclaredTypeImpl implements ErrorType {
   ErrorTypeImpl(BaseProcessingEnvImpl env, ReferenceBinding binding) {
      super(env, binding);
   }

   public Element asElement() {
      return this._env.getFactory().newElement((ReferenceBinding)this._binding);
   }

   public TypeMirror getEnclosingType() {
      return NoTypeImpl.NO_TYPE_NONE;
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
      return v.visitError(this, p);
   }

   public List getAnnotationMirrors() {
      return Factory.EMPTY_ANNOTATION_MIRRORS;
   }

   public Annotation getAnnotation(Class annotationType) {
      return null;
   }

   public Annotation[] getAnnotationsByType(Class annotationType) {
      return (Annotation[])Array.newInstance(annotationType, 0);
   }

   public TypeKind getKind() {
      return TypeKind.ERROR;
   }

   public String toString() {
      return new String(this._binding.readableName());
   }
}
