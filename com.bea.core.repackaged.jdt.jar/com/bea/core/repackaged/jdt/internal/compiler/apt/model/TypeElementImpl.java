package com.bea.core.repackaged.jdt.internal.compiler.apt.model;

import com.bea.core.repackaged.jdt.core.compiler.CharOperation;
import com.bea.core.repackaged.jdt.internal.compiler.apt.dispatch.BaseProcessingEnvImpl;
import com.bea.core.repackaged.jdt.internal.compiler.ast.FieldDeclaration;
import com.bea.core.repackaged.jdt.internal.compiler.ast.TypeDeclaration;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.AnnotationBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.Binding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.FieldBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.MethodBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.ReferenceBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.SourceTypeBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.TypeBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.TypeVariableBinding;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Set;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ElementVisitor;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Name;
import javax.lang.model.element.NestingKind;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;

public class TypeElementImpl extends ElementImpl implements TypeElement {
   private final ElementKind _kindHint;

   TypeElementImpl(BaseProcessingEnvImpl env, ReferenceBinding binding, ElementKind kindHint) {
      super(env, binding);
      this._kindHint = kindHint;
   }

   public Object accept(ElementVisitor v, Object p) {
      return v.visitType(this, p);
   }

   protected AnnotationBinding[] getAnnotationBindings() {
      return ((ReferenceBinding)this._binding).getAnnotations();
   }

   public List getEnclosedElements() {
      ReferenceBinding binding = (ReferenceBinding)this._binding;
      List enclosed = new ArrayList(binding.fieldCount() + binding.methods().length + binding.memberTypes().length);
      MethodBinding[] var6;
      int var5 = (var6 = binding.methods()).length;

      int var4;
      for(var4 = 0; var4 < var5; ++var4) {
         MethodBinding method = var6[var4];
         ExecutableElement executable = new ExecutableElementImpl(this._env, method);
         enclosed.add(executable);
      }

      FieldBinding[] var10;
      var5 = (var10 = binding.fields()).length;

      for(var4 = 0; var4 < var5; ++var4) {
         FieldBinding field = var10[var4];
         if (!field.isSynthetic()) {
            VariableElement variable = new VariableElementImpl(this._env, field);
            enclosed.add(variable);
         }
      }

      ReferenceBinding[] var11;
      var5 = (var11 = binding.memberTypes()).length;

      for(var4 = 0; var4 < var5; ++var4) {
         ReferenceBinding memberType = var11[var4];
         TypeElement type = new TypeElementImpl(this._env, memberType, (ElementKind)null);
         enclosed.add(type);
      }

      Collections.sort(enclosed, new SourceLocationComparator((SourceLocationComparator)null));
      return Collections.unmodifiableList(enclosed);
   }

   public Element getEnclosingElement() {
      ReferenceBinding binding = (ReferenceBinding)this._binding;
      ReferenceBinding enclosingType = binding.enclosingType();
      return (Element)(enclosingType == null ? this._env.getFactory().newPackageElement(binding.fPackage) : this._env.getFactory().newElement(binding.enclosingType()));
   }

   public String getFileName() {
      char[] name = ((ReferenceBinding)this._binding).getFileName();
      return name == null ? null : new String(name);
   }

   public List getInterfaces() {
      ReferenceBinding binding = (ReferenceBinding)this._binding;
      if (binding.superInterfaces() != null && binding.superInterfaces().length != 0) {
         List interfaces = new ArrayList(binding.superInterfaces().length);
         ReferenceBinding[] var6;
         int var5 = (var6 = binding.superInterfaces()).length;

         for(int var4 = 0; var4 < var5; ++var4) {
            ReferenceBinding interfaceBinding = var6[var4];
            TypeMirror interfaceType = this._env.getFactory().newTypeMirror(interfaceBinding);
            if (interfaceType.getKind() == TypeKind.ERROR) {
               if (this._env.getSourceVersion().compareTo(SourceVersion.RELEASE_6) > 0) {
                  interfaces.add(interfaceType);
               }
            } else {
               interfaces.add(interfaceType);
            }
         }

         return Collections.unmodifiableList(interfaces);
      } else {
         return Collections.emptyList();
      }
   }

   public ElementKind getKind() {
      if (this._kindHint != null) {
         return this._kindHint;
      } else {
         ReferenceBinding refBinding = (ReferenceBinding)this._binding;
         if (refBinding.isEnum()) {
            return ElementKind.ENUM;
         } else if (refBinding.isAnnotationType()) {
            return ElementKind.ANNOTATION_TYPE;
         } else if (refBinding.isInterface()) {
            return ElementKind.INTERFACE;
         } else if (refBinding.isClass()) {
            return ElementKind.CLASS;
         } else {
            throw new IllegalArgumentException("TypeElement " + new String(refBinding.shortReadableName()) + " has unexpected attributes " + refBinding.modifiers);
         }
      }
   }

   public Set getModifiers() {
      ReferenceBinding refBinding = (ReferenceBinding)this._binding;
      int modifiers = refBinding.modifiers;
      if (refBinding.isInterface() && refBinding.isNestedType()) {
         modifiers |= 8;
      }

      return Factory.getModifiers(modifiers, this.getKind(), refBinding.isBinaryBinding());
   }

   public NestingKind getNestingKind() {
      ReferenceBinding refBinding = (ReferenceBinding)this._binding;
      if (refBinding.isAnonymousType()) {
         return NestingKind.ANONYMOUS;
      } else if (refBinding.isLocalType()) {
         return NestingKind.LOCAL;
      } else {
         return refBinding.isMemberType() ? NestingKind.MEMBER : NestingKind.TOP_LEVEL;
      }
   }

   PackageElement getPackage() {
      ReferenceBinding binding = (ReferenceBinding)this._binding;
      return this._env.getFactory().newPackageElement(binding.fPackage);
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

   public Name getSimpleName() {
      ReferenceBinding binding = (ReferenceBinding)this._binding;
      return new NameImpl(binding.sourceName());
   }

   public TypeMirror getSuperclass() {
      ReferenceBinding binding = (ReferenceBinding)this._binding;
      ReferenceBinding superBinding = binding.superclass();
      return (TypeMirror)(superBinding != null && !binding.isInterface() ? this._env.getFactory().newTypeMirror(superBinding) : this._env.getFactory().getNoType(TypeKind.NONE));
   }

   public List getTypeParameters() {
      ReferenceBinding binding = (ReferenceBinding)this._binding;
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
      if (!(hidden instanceof TypeElementImpl)) {
         return false;
      } else {
         ReferenceBinding hiddenBinding = (ReferenceBinding)((TypeElementImpl)hidden)._binding;
         if (hiddenBinding.isPrivate()) {
            return false;
         } else {
            ReferenceBinding hiderBinding = (ReferenceBinding)this._binding;
            if (TypeBinding.equalsEquals(hiddenBinding, hiderBinding)) {
               return false;
            } else if (hiddenBinding.isMemberType() && hiderBinding.isMemberType()) {
               if (!CharOperation.equals(hiddenBinding.sourceName, hiderBinding.sourceName)) {
                  return false;
               } else {
                  return hiderBinding.enclosingType().findSuperTypeOriginatingFrom(hiddenBinding.enclosingType()) != null;
               }
            } else {
               return false;
            }
         }
      }
   }

   public String toString() {
      ReferenceBinding binding = (ReferenceBinding)this._binding;
      char[] concatWith = CharOperation.concatWith(binding.compoundName, '.');
      if (binding.isNestedType()) {
         CharOperation.replace(concatWith, '$', '.');
         return new String(concatWith);
      } else {
         return new String(concatWith);
      }
   }

   private static final class SourceLocationComparator implements Comparator {
      private final IdentityHashMap sourceStartCache;
      // $FF: synthetic field
      private static int[] $SWITCH_TABLE$javax$lang$model$element$ElementKind;

      private SourceLocationComparator() {
         this.sourceStartCache = new IdentityHashMap();
      }

      public int compare(Element o1, Element o2) {
         ElementImpl e1 = (ElementImpl)o1;
         ElementImpl e2 = (ElementImpl)o2;
         return this.getSourceStart(e1) - this.getSourceStart(e2);
      }

      private int getSourceStart(ElementImpl e) {
         Integer value = (Integer)this.sourceStartCache.get(e);
         if (value == null) {
            value = this.determineSourceStart(e);
            this.sourceStartCache.put(e, value);
         }

         return value;
      }

      private int determineSourceStart(ElementImpl e) {
         Binding binding;
         switch (e.getKind()) {
            case ENUM:
            case CLASS:
            case ANNOTATION_TYPE:
            case INTERFACE:
               TypeElementImpl typeElementImpl = (TypeElementImpl)e;
               Binding typeBinding = typeElementImpl._binding;
               if (typeBinding instanceof SourceTypeBinding) {
                  SourceTypeBinding sourceTypeBinding = (SourceTypeBinding)typeBinding;
                  TypeDeclaration typeDeclaration = (TypeDeclaration)sourceTypeBinding.scope.referenceContext();
                  return typeDeclaration.sourceStart;
               }
               break;
            case ENUM_CONSTANT:
            case FIELD:
               VariableElementImpl variableElementImpl = (VariableElementImpl)e;
               binding = variableElementImpl._binding;
               if (binding instanceof FieldBinding) {
                  FieldBinding fieldBinding = (FieldBinding)binding;
                  FieldDeclaration fieldDeclaration = fieldBinding.sourceField();
                  if (fieldDeclaration != null) {
                     return fieldDeclaration.sourceStart;
                  }
               }
            case PARAMETER:
            case LOCAL_VARIABLE:
            case EXCEPTION_PARAMETER:
            default:
               break;
            case METHOD:
            case CONSTRUCTOR:
               ExecutableElementImpl executableElementImpl = (ExecutableElementImpl)e;
               binding = executableElementImpl._binding;
               if (binding instanceof MethodBinding) {
                  MethodBinding methodBinding = (MethodBinding)binding;
                  return methodBinding.sourceStart();
               }
         }

         return -1;
      }

      // $FF: synthetic method
      static int[] $SWITCH_TABLE$javax$lang$model$element$ElementKind() {
         int[] var10000 = $SWITCH_TABLE$javax$lang$model$element$ElementKind;
         if (var10000 != null) {
            return var10000;
         } else {
            int[] var0 = new int[ElementKind.values().length];

            try {
               var0[ElementKind.ANNOTATION_TYPE.ordinal()] = 4;
            } catch (NoSuchFieldError var18) {
            }

            try {
               var0[ElementKind.CLASS.ordinal()] = 3;
            } catch (NoSuchFieldError var17) {
            }

            try {
               var0[ElementKind.CONSTRUCTOR.ordinal()] = 12;
            } catch (NoSuchFieldError var16) {
            }

            try {
               var0[ElementKind.ENUM.ordinal()] = 2;
            } catch (NoSuchFieldError var15) {
            }

            try {
               var0[ElementKind.ENUM_CONSTANT.ordinal()] = 6;
            } catch (NoSuchFieldError var14) {
            }

            try {
               var0[ElementKind.EXCEPTION_PARAMETER.ordinal()] = 10;
            } catch (NoSuchFieldError var13) {
            }

            try {
               var0[ElementKind.FIELD.ordinal()] = 7;
            } catch (NoSuchFieldError var12) {
            }

            try {
               var0[ElementKind.INSTANCE_INIT.ordinal()] = 14;
            } catch (NoSuchFieldError var11) {
            }

            try {
               var0[ElementKind.INTERFACE.ordinal()] = 5;
            } catch (NoSuchFieldError var10) {
            }

            try {
               var0[ElementKind.LOCAL_VARIABLE.ordinal()] = 9;
            } catch (NoSuchFieldError var9) {
            }

            try {
               var0[ElementKind.METHOD.ordinal()] = 11;
            } catch (NoSuchFieldError var8) {
            }

            try {
               var0[ElementKind.MODULE.ordinal()] = 18;
            } catch (NoSuchFieldError var7) {
            }

            try {
               var0[ElementKind.OTHER.ordinal()] = 16;
            } catch (NoSuchFieldError var6) {
            }

            try {
               var0[ElementKind.PACKAGE.ordinal()] = 1;
            } catch (NoSuchFieldError var5) {
            }

            try {
               var0[ElementKind.PARAMETER.ordinal()] = 8;
            } catch (NoSuchFieldError var4) {
            }

            try {
               var0[ElementKind.RESOURCE_VARIABLE.ordinal()] = 17;
            } catch (NoSuchFieldError var3) {
            }

            try {
               var0[ElementKind.STATIC_INIT.ordinal()] = 13;
            } catch (NoSuchFieldError var2) {
            }

            try {
               var0[ElementKind.TYPE_PARAMETER.ordinal()] = 15;
            } catch (NoSuchFieldError var1) {
            }

            $SWITCH_TABLE$javax$lang$model$element$ElementKind = var0;
            return var0;
         }
      }

      // $FF: synthetic method
      SourceLocationComparator(SourceLocationComparator var1) {
         this();
      }
   }
}
