package com.bea.core.repackaged.jdt.internal.compiler.apt.model;

import com.bea.core.repackaged.jdt.core.compiler.CharOperation;
import com.bea.core.repackaged.jdt.internal.compiler.apt.dispatch.BaseProcessingEnvImpl;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.LookupEnvironment;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.MethodBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.ModuleBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.PackageBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.ReferenceBinding;
import com.bea.core.repackaged.jdt.internal.compiler.util.HashtableOfModule;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import javax.lang.model.AnnotatedConstruct;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.ModuleElement;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Elements.Origin;

public class ElementsImpl9 extends ElementsImpl {
   public ElementsImpl9(BaseProcessingEnvImpl env) {
      super(env);
   }

   public TypeElement getTypeElement(CharSequence name) {
      char[][] compoundName = CharOperation.splitOn('.', name.toString().toCharArray());
      Set allModuleElements = this.getAllModuleElements();
      Iterator var5 = allModuleElements.iterator();

      while(var5.hasNext()) {
         ModuleElement moduleElement = (ModuleElement)var5.next();
         TypeElement t = this.getTypeElement(compoundName, ((ModuleElementImpl)moduleElement).binding);
         if (t != null) {
            return t;
         }
      }

      return null;
   }

   public TypeElement getTypeElement(ModuleElement module, CharSequence name) {
      ModuleBinding mBinding = ((ModuleElementImpl)module).binding;
      char[][] compoundName = CharOperation.splitOn('.', name.toString().toCharArray());
      return this.getTypeElement(compoundName, mBinding);
   }

   private TypeElement getTypeElement(char[][] compoundName, ModuleBinding mBinding) {
      LookupEnvironment le = mBinding == null ? this._env.getLookupEnvironment() : mBinding.environment;
      ReferenceBinding binding = mBinding == null ? le.getType(compoundName) : le.getType(compoundName, mBinding);
      if (binding == null) {
         ReferenceBinding topLevelBinding = null;
         int topLevelSegments = compoundName.length;

         do {
            --topLevelSegments;
            if (topLevelSegments <= 0) {
               break;
            }

            char[][] topLevelName = new char[topLevelSegments][];

            for(int i = 0; i < topLevelSegments; ++i) {
               topLevelName[i] = compoundName[i];
            }

            topLevelBinding = le.getType(topLevelName);
         } while(topLevelBinding == null);

         if (topLevelBinding == null) {
            return null;
         }

         binding = topLevelBinding;

         for(int i = topLevelSegments; binding != null && i < compoundName.length; ++i) {
            binding = binding.getMemberType(compoundName[i]);
         }
      }

      if (binding == null) {
         return null;
      } else {
         return (binding.tagBits & 128L) != 0L ? null : new TypeElementImpl(this._env, binding, (ElementKind)null);
      }
   }

   public Elements.Origin getOrigin(Element e) {
      return Origin.EXPLICIT;
   }

   public Elements.Origin getOrigin(AnnotatedConstruct c, AnnotationMirror a) {
      return Origin.EXPLICIT;
   }

   public Elements.Origin getOrigin(ModuleElement m, ModuleElement.Directive directive) {
      return Origin.EXPLICIT;
   }

   public boolean isBridge(ExecutableElement e) {
      MethodBinding methodBinding = (MethodBinding)((ExecutableElementImpl)e)._binding;
      return methodBinding.isBridge();
   }

   public ModuleElement getModuleOf(Element elem) {
      if (elem instanceof ModuleElement) {
         return (ModuleElement)elem;
      } else {
         for(Element parent = elem.getEnclosingElement(); parent != null; parent = parent.getEnclosingElement()) {
            if (parent instanceof ModuleElement) {
               return (ModuleElement)parent;
            }
         }

         return null;
      }
   }

   public ModuleElement getModuleElement(CharSequence name) {
      LookupEnvironment lookup = this._env.getLookupEnvironment();
      ModuleBinding binding = lookup.getModule(name.length() == 0 ? ModuleBinding.UNNAMED : name.toString().toCharArray());
      return binding == null ? null : new ModuleElementImpl(this._env, binding);
   }

   public Set getAllModuleElements() {
      LookupEnvironment lookup = this._env.getLookupEnvironment();
      HashtableOfModule knownModules = lookup.knownModules;
      ModuleBinding[] modules = knownModules.valueTable;
      if (modules != null && modules.length != 0) {
         Set mods = new HashSet(modules.length);
         ModuleBinding[] var8 = modules;
         int var7 = modules.length;

         for(int var6 = 0; var6 < var7; ++var6) {
            ModuleBinding moduleBinding = var8[var6];
            if (moduleBinding != null) {
               ModuleElement element = (ModuleElement)this._env.getFactory().newElement(moduleBinding);
               mods.add(element);
            }
         }

         mods.add((ModuleElement)this._env.getFactory().newElement(lookup.UnNamedModule));
         return mods;
      } else {
         return Collections.emptySet();
      }
   }

   public PackageElement getPackageElement(ModuleElement module, CharSequence name) {
      ModuleBinding mBinding = ((ModuleElementImpl)module).binding;
      char[][] compoundName = CharOperation.splitOn('.', name.toString().toCharArray());
      PackageBinding p = null;
      if (mBinding != null) {
         int length = compoundName.length;
         if (length > 1) {
            char[][] parent = new char[compoundName.length - 1][];
            System.arraycopy(compoundName, 0, parent, 0, length - 1);
            p = mBinding.getPackage(parent, compoundName[length - 1]);
         } else {
            p = mBinding.getTopLevelPackage(compoundName[0]);
         }
      } else {
         p = this._env.getLookupEnvironment().createPackage(compoundName);
      }

      return p != null && p.isValidBinding() ? (PackageElement)this._env.getFactory().newElement(p) : null;
   }
}
