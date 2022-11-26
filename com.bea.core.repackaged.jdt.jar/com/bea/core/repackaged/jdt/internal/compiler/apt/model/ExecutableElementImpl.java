package com.bea.core.repackaged.jdt.internal.compiler.apt.model;

import com.bea.core.repackaged.jdt.core.compiler.CharOperation;
import com.bea.core.repackaged.jdt.internal.compiler.apt.dispatch.BaseProcessingEnvImpl;
import com.bea.core.repackaged.jdt.internal.compiler.ast.AbstractMethodDeclaration;
import com.bea.core.repackaged.jdt.internal.compiler.ast.Argument;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.AnnotationBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.AnnotationHolder;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.AptBinaryLocalVariableBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.LookupEnvironment;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.MethodBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.MethodVerifier;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.ReferenceBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.TypeBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.TypeConstants;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.TypeVariableBinding;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ElementVisitor;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Name;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeMirror;

public class ExecutableElementImpl extends ElementImpl implements ExecutableElement {
   private Name _name = null;

   ExecutableElementImpl(BaseProcessingEnvImpl env, MethodBinding binding) {
      super(env, binding);
   }

   public Object accept(ElementVisitor v, Object p) {
      return v.visitExecutable(this, p);
   }

   protected AnnotationBinding[] getAnnotationBindings() {
      return ((MethodBinding)this._binding).getAnnotations();
   }

   public AnnotationValue getDefaultValue() {
      MethodBinding binding = (MethodBinding)this._binding;
      Object defaultValue = binding.getDefaultValue();
      return defaultValue != null ? new AnnotationMemberValue(this._env, defaultValue, binding) : null;
   }

   public List getEnclosedElements() {
      return Collections.emptyList();
   }

   public Element getEnclosingElement() {
      MethodBinding binding = (MethodBinding)this._binding;
      return binding.declaringClass == null ? null : this._env.getFactory().newElement(binding.declaringClass);
   }

   public String getFileName() {
      ReferenceBinding dc = ((MethodBinding)this._binding).declaringClass;
      char[] name = dc.getFileName();
      return name == null ? null : new String(name);
   }

   public ElementKind getKind() {
      MethodBinding binding = (MethodBinding)this._binding;
      if (binding.isConstructor()) {
         return ElementKind.CONSTRUCTOR;
      } else if (CharOperation.equals(binding.selector, TypeConstants.CLINIT)) {
         return ElementKind.STATIC_INIT;
      } else {
         return CharOperation.equals(binding.selector, TypeConstants.INIT) ? ElementKind.INSTANCE_INIT : ElementKind.METHOD;
      }
   }

   public Set getModifiers() {
      MethodBinding binding = (MethodBinding)this._binding;
      return Factory.getModifiers(binding.modifiers, this.getKind());
   }

   PackageElement getPackage() {
      MethodBinding binding = (MethodBinding)this._binding;
      return binding.declaringClass == null ? null : this._env.getFactory().newPackageElement(binding.declaringClass.fPackage);
   }

   public List getParameters() {
      MethodBinding binding = (MethodBinding)this._binding;
      int length = binding.parameters == null ? 0 : binding.parameters.length;
      if (length == 0) {
         return Collections.emptyList();
      } else {
         AbstractMethodDeclaration methodDeclaration = binding.sourceMethod();
         List params = new ArrayList(length);
         int i;
         if (methodDeclaration != null) {
            Argument[] var8;
            i = (var8 = methodDeclaration.arguments).length;

            for(int var6 = 0; var6 < i; ++var6) {
               Argument argument = var8[var6];
               VariableElement param = new VariableElementImpl(this._env, argument.binding);
               params.add(param);
            }
         } else {
            AnnotationBinding[][] parameterAnnotationBindings = null;
            AnnotationHolder annotationHolder = binding.declaringClass.retrieveAnnotationHolder(binding, false);
            if (annotationHolder != null) {
               parameterAnnotationBindings = annotationHolder.getParameterAnnotations();
            }

            i = 0;
            TypeBinding[] var11;
            int var10 = (var11 = binding.parameters).length;

            for(int var17 = 0; var17 < var10; ++var17) {
               TypeBinding typeBinding = var11[var17];
               char[] name = binding.parameterNames.length > i ? binding.parameterNames[i] : null;
               if (name == null) {
                  StringBuilder builder = new StringBuilder("arg");
                  builder.append(i);
                  name = String.valueOf(builder).toCharArray();
               }

               VariableElement param = new VariableElementImpl(this._env, new AptBinaryLocalVariableBinding(name, typeBinding, 0, parameterAnnotationBindings != null ? parameterAnnotationBindings[i] : null, binding));
               params.add(param);
               ++i;
            }
         }

         return Collections.unmodifiableList(params);
      }
   }

   public TypeMirror getReturnType() {
      MethodBinding binding = (MethodBinding)this._binding;
      return binding.returnType == null ? null : this._env.getFactory().newTypeMirror(binding.returnType);
   }

   public Name getSimpleName() {
      MethodBinding binding = (MethodBinding)this._binding;
      if (this._name == null) {
         this._name = new NameImpl(binding.selector);
      }

      return this._name;
   }

   public List getThrownTypes() {
      MethodBinding binding = (MethodBinding)this._binding;
      if (binding.thrownExceptions.length == 0) {
         return Collections.emptyList();
      } else {
         List list = new ArrayList(binding.thrownExceptions.length);
         ReferenceBinding[] var6;
         int var5 = (var6 = binding.thrownExceptions).length;

         for(int var4 = 0; var4 < var5; ++var4) {
            ReferenceBinding exception = var6[var4];
            list.add(this._env.getFactory().newTypeMirror(exception));
         }

         return list;
      }
   }

   public List getTypeParameters() {
      MethodBinding binding = (MethodBinding)this._binding;
      TypeVariableBinding[] variables = binding.typeVariables();
      if (variables.length == 0) {
         return Collections.emptyList();
      } else {
         List params = new ArrayList(variables.length);
         TypeVariableBinding[] var7 = variables;
         int var6 = variables.length;

         for(int var5 = 0; var5 < var6; ++var5) {
            TypeVariableBinding variable = var7[var5];
            params.add(this._env.getFactory().newTypeParameterElement(variable, this));
         }

         return Collections.unmodifiableList(params);
      }
   }

   public boolean hides(Element hidden) {
      if (!(hidden instanceof ExecutableElementImpl)) {
         return false;
      } else {
         MethodBinding hiderBinding = (MethodBinding)this._binding;
         MethodBinding hiddenBinding = (MethodBinding)((ExecutableElementImpl)hidden)._binding;
         if (hiderBinding == hiddenBinding) {
            return false;
         } else if (hiddenBinding.isPrivate()) {
            return false;
         } else if (hiderBinding.isStatic() && hiddenBinding.isStatic()) {
            if (!CharOperation.equals(hiddenBinding.selector, hiderBinding.selector)) {
               return false;
            } else if (!this._env.getLookupEnvironment().methodVerifier().isMethodSubsignature(hiderBinding, hiddenBinding)) {
               return false;
            } else {
               return hiderBinding.declaringClass.findSuperTypeOriginatingFrom(hiddenBinding.declaringClass) != null;
            }
         } else {
            return false;
         }
      }
   }

   public boolean isVarArgs() {
      return ((MethodBinding)this._binding).isVarargs();
   }

   public boolean overrides(ExecutableElement overridden, TypeElement type) {
      MethodBinding overriddenBinding = (MethodBinding)((ExecutableElementImpl)overridden)._binding;
      ReferenceBinding overriderContext = (ReferenceBinding)((TypeElementImpl)type)._binding;
      if ((MethodBinding)this._binding != overriddenBinding && !overriddenBinding.isStatic() && !overriddenBinding.isPrivate() && !((MethodBinding)this._binding).isStatic()) {
         char[] selector = ((MethodBinding)this._binding).selector;
         if (!CharOperation.equals(selector, overriddenBinding.selector)) {
            return false;
         } else if (overriderContext.findSuperTypeOriginatingFrom(((MethodBinding)this._binding).declaringClass) == null && ((MethodBinding)this._binding).declaringClass.findSuperTypeOriginatingFrom(overriderContext) == null) {
            return false;
         } else {
            MethodBinding overriderBinding = new MethodBinding((MethodBinding)this._binding, overriderContext);
            if (overriderBinding.isPrivate()) {
               return false;
            } else {
               TypeBinding match = overriderBinding.declaringClass.findSuperTypeOriginatingFrom(overriddenBinding.declaringClass);
               if (!(match instanceof ReferenceBinding)) {
                  return false;
               } else {
                  MethodBinding[] superMethods = ((ReferenceBinding)match).getMethods(selector);
                  LookupEnvironment lookupEnvironment = this._env.getLookupEnvironment();
                  if (lookupEnvironment == null) {
                     return false;
                  } else {
                     MethodVerifier methodVerifier = lookupEnvironment.methodVerifier();
                     int i = 0;

                     for(int length = superMethods.length; i < length; ++i) {
                        if (superMethods[i].original() == overriddenBinding) {
                           return methodVerifier.doesMethodOverride(overriderBinding, superMethods[i]);
                        }
                     }

                     return false;
                  }
               }
            }
         }
      } else {
         return false;
      }
   }

   public TypeMirror getReceiverType() {
      return this._env.getFactory().getReceiverType((MethodBinding)this._binding);
   }

   public boolean isDefault() {
      return this._binding != null ? ((MethodBinding)this._binding).isDefaultMethod() : false;
   }
}
