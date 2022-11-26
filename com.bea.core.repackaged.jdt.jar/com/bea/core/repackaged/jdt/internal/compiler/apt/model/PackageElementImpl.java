package com.bea.core.repackaged.jdt.internal.compiler.apt.model;

import com.bea.core.repackaged.jdt.core.compiler.CharOperation;
import com.bea.core.repackaged.jdt.internal.compiler.apt.dispatch.BaseProcessingEnvImpl;
import com.bea.core.repackaged.jdt.internal.compiler.batch.FileSystem;
import com.bea.core.repackaged.jdt.internal.compiler.env.INameEnvironment;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.AnnotationBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.LookupEnvironment;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.ModuleBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.PackageBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.ReferenceBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.TypeConstants;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ElementVisitor;
import javax.lang.model.element.Name;
import javax.lang.model.element.PackageElement;

public class PackageElementImpl extends ElementImpl implements PackageElement {
   PackageElementImpl(BaseProcessingEnvImpl env, PackageBinding binding) {
      super(env, binding);
   }

   public Object accept(ElementVisitor v, Object p) {
      return v.visitPackage(this, p);
   }

   protected AnnotationBinding[] getAnnotationBindings() {
      PackageBinding packageBinding = (PackageBinding)this._binding;
      char[][] compoundName = CharOperation.arrayConcat(packageBinding.compoundName, TypeConstants.PACKAGE_INFO_NAME);
      ReferenceBinding type = this._env.getLookupEnvironment().getType(compoundName);
      AnnotationBinding[] annotations = null;
      if (type != null && type.isValidBinding()) {
         annotations = type.getAnnotations();
      }

      return annotations;
   }

   public List getEnclosedElements() {
      PackageBinding binding = (PackageBinding)this._binding;
      LookupEnvironment environment = binding.environment;
      char[][][] typeNames = null;
      INameEnvironment nameEnvironment = binding.environment.nameEnvironment;
      if (nameEnvironment instanceof FileSystem) {
         typeNames = ((FileSystem)nameEnvironment).findTypeNames(binding.compoundName);
      }

      HashSet set = new HashSet();
      Set types = new HashSet();
      int var9;
      Element newElement;
      if (typeNames != null) {
         char[][][] var10 = typeNames;
         var9 = typeNames.length;

         for(int var8 = 0; var8 < var9; ++var8) {
            char[][] typeName = var10[var8];
            if (typeName != null) {
               ReferenceBinding type = environment.getType(typeName);
               if (type != null && type.isValidBinding()) {
                  newElement = this._env.getFactory().newElement(type);
                  if (newElement.getKind() != ElementKind.PACKAGE) {
                     set.add(newElement);
                     types.add(type);
                  }
               }
            }
         }
      }

      if (binding.knownTypes != null) {
         ReferenceBinding[] knownTypes = binding.knownTypes.valueTable;
         ReferenceBinding[] var17 = knownTypes;
         int var16 = knownTypes.length;

         for(var9 = 0; var9 < var16; ++var9) {
            ReferenceBinding referenceBinding = var17[var9];
            if (referenceBinding != null && referenceBinding.isValidBinding() && referenceBinding.enclosingType() == null && !types.contains(referenceBinding)) {
               newElement = this._env.getFactory().newElement(referenceBinding);
               if (newElement.getKind() != ElementKind.PACKAGE) {
                  set.add(newElement);
               }
            }
         }
      }

      ArrayList list = new ArrayList(set.size());
      list.addAll(set);
      return Collections.unmodifiableList(list);
   }

   public Element getEnclosingElement() {
      if (super._env.getCompiler().options.sourceLevel < 3473408L) {
         return null;
      } else {
         PackageBinding pBinding = (PackageBinding)this._binding;
         ModuleBinding module = pBinding.enclosingModule;
         return module == null ? null : new ModuleElementImpl(this._env, module);
      }
   }

   public ElementKind getKind() {
      return ElementKind.PACKAGE;
   }

   PackageElement getPackage() {
      return this;
   }

   public Name getSimpleName() {
      char[][] compoundName = ((PackageBinding)this._binding).compoundName;
      int length = compoundName.length;
      return length == 0 ? new NameImpl(CharOperation.NO_CHAR) : new NameImpl(compoundName[length - 1]);
   }

   public Name getQualifiedName() {
      return new NameImpl(CharOperation.concatWith(((PackageBinding)this._binding).compoundName, '.'));
   }

   public boolean isUnnamed() {
      PackageBinding binding = (PackageBinding)this._binding;
      return binding.compoundName == CharOperation.NO_CHAR_CHAR;
   }
}
