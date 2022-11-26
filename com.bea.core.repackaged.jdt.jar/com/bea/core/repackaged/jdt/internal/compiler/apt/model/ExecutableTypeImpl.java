package com.bea.core.repackaged.jdt.internal.compiler.apt.model;

import com.bea.core.repackaged.jdt.internal.compiler.apt.dispatch.BaseProcessingEnvImpl;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.AnnotationBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.MethodBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.ReferenceBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.TypeBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.TypeVariableBinding;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.lang.model.type.ExecutableType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.type.TypeVariable;
import javax.lang.model.type.TypeVisitor;

public class ExecutableTypeImpl extends TypeMirrorImpl implements ExecutableType {
   ExecutableTypeImpl(BaseProcessingEnvImpl env, MethodBinding binding) {
      super(env, binding);
   }

   public List getParameterTypes() {
      MethodBinding binding = (MethodBinding)this._binding;
      TypeBinding[] parameters = binding.parameters;
      int length = parameters.length;
      boolean isEnumConstructor = binding.isConstructor() && binding.declaringClass.isEnum() && binding.declaringClass.isBinaryBinding() && (binding.modifiers & 1073741824) == 0;
      ArrayList list;
      if (isEnumConstructor) {
         list = new ArrayList();

         for(int i = 0; i < length; ++i) {
            list.add(this._env.getFactory().newTypeMirror(parameters[i]));
         }

         return Collections.unmodifiableList(list);
      } else if (length == 0) {
         return Collections.emptyList();
      } else {
         list = new ArrayList();
         TypeBinding[] var9 = parameters;
         int var8 = parameters.length;

         for(int var7 = 0; var7 < var8; ++var7) {
            TypeBinding typeBinding = var9[var7];
            list.add(this._env.getFactory().newTypeMirror(typeBinding));
         }

         return Collections.unmodifiableList(list);
      }
   }

   public TypeMirror getReturnType() {
      return this._env.getFactory().newTypeMirror(((MethodBinding)this._binding).returnType);
   }

   protected AnnotationBinding[] getAnnotationBindings() {
      return ((MethodBinding)this._binding).returnType.getTypeAnnotations();
   }

   public List getThrownTypes() {
      ArrayList list = new ArrayList();
      ReferenceBinding[] thrownExceptions = ((MethodBinding)this._binding).thrownExceptions;
      if (thrownExceptions.length != 0) {
         ReferenceBinding[] var6 = thrownExceptions;
         int var5 = thrownExceptions.length;

         for(int var4 = 0; var4 < var5; ++var4) {
            ReferenceBinding referenceBinding = var6[var4];
            list.add(this._env.getFactory().newTypeMirror(referenceBinding));
         }
      }

      return Collections.unmodifiableList(list);
   }

   public List getTypeVariables() {
      ArrayList list = new ArrayList();
      TypeVariableBinding[] typeVariables = ((MethodBinding)this._binding).typeVariables();
      if (typeVariables.length != 0) {
         TypeVariableBinding[] var6 = typeVariables;
         int var5 = typeVariables.length;

         for(int var4 = 0; var4 < var5; ++var4) {
            TypeVariableBinding typeVariableBinding = var6[var4];
            list.add((TypeVariable)this._env.getFactory().newTypeMirror(typeVariableBinding));
         }
      }

      return Collections.unmodifiableList(list);
   }

   public Object accept(TypeVisitor v, Object p) {
      return v.visitExecutable(this, p);
   }

   public TypeKind getKind() {
      return TypeKind.EXECUTABLE;
   }

   public TypeMirror getReceiverType() {
      return this._env.getFactory().getReceiverType((MethodBinding)this._binding);
   }

   public String toString() {
      return new String(((MethodBinding)this._binding).returnType.readableName());
   }
}
