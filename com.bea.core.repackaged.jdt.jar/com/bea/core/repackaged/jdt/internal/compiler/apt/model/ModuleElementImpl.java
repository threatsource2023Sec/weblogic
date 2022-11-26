package com.bea.core.repackaged.jdt.internal.compiler.apt.model;

import com.bea.core.repackaged.jdt.internal.compiler.apt.dispatch.BaseProcessingEnvImpl;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.AnnotationBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.ModuleBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.PackageBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.SplitPackageBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.TypeBinding;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ElementVisitor;
import javax.lang.model.element.ModuleElement;
import javax.lang.model.element.Name;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.ModuleElement.DirectiveKind;

public class ModuleElementImpl extends ElementImpl implements ModuleElement {
   ModuleBinding binding;
   private List directives;
   private static List EMPTY_DIRECTIVES = Collections.emptyList();

   ModuleElementImpl(BaseProcessingEnvImpl env, ModuleBinding binding) {
      super(env, binding);
      this.binding = binding;
   }

   private PackageBinding getModulesPackageBinding(PackageBinding binding) {
      return binding instanceof SplitPackageBinding ? ((SplitPackageBinding)binding).getIncarnation(this.binding) : binding;
   }

   public ElementKind getKind() {
      return ElementKind.MODULE;
   }

   public Set getModifiers() {
      int modifiers = this.binding.modifiers;
      return Factory.getModifiers(modifiers, this.getKind(), false);
   }

   public Name getQualifiedName() {
      return new NameImpl(this.binding.moduleName);
   }

   public Name getSimpleName() {
      return new NameImpl(this.binding.moduleName);
   }

   public List getEnclosedElements() {
      ModuleBinding module = this.binding;
      PackageBinding[] packs = module.declaredPackages.valueTable;
      Set unique = new HashSet();
      PackageBinding[] var7 = packs;
      int var6 = packs.length;

      PackageBinding pBinding;
      int var5;
      for(var5 = 0; var5 < var6; ++var5) {
         pBinding = var7[var5];
         if (pBinding != null) {
            if (pBinding instanceof SplitPackageBinding) {
               Iterator var9 = ((SplitPackageBinding)pBinding).incarnations.iterator();

               while(var9.hasNext()) {
                  PackageBinding incarnation = (PackageBinding)var9.next();
                  if (incarnation.enclosingModule == module && incarnation.hasCompilationUnit(true)) {
                     unique.add(this.getModulesPackageBinding(pBinding));
                  }
               }
            } else if (pBinding.hasCompilationUnit(true)) {
               unique.add(this.getModulesPackageBinding(pBinding));
            }
         }
      }

      if (module.isUnnamed()) {
         pBinding = module.environment.defaultPackage;
         if (pBinding != null && pBinding.hasCompilationUnit(true)) {
            unique.add(pBinding);
         }
      } else {
         packs = this.binding.getExports();
         var7 = packs;
         var6 = packs.length;

         for(var5 = 0; var5 < var6; ++var5) {
            pBinding = var7[var5];
            unique.add(this.getModulesPackageBinding(pBinding));
         }

         packs = this.binding.getOpens();
         var7 = packs;
         var6 = packs.length;

         for(var5 = 0; var5 < var6; ++var5) {
            pBinding = var7[var5];
            unique.add(this.getModulesPackageBinding(pBinding));
         }
      }

      List enclosed = new ArrayList(unique.size());
      Iterator var11 = unique.iterator();

      while(var11.hasNext()) {
         PackageBinding p = (PackageBinding)var11.next();
         PackageElement pElement = (PackageElement)this._env.getFactory().newElement(p);
         enclosed.add(pElement);
      }

      return Collections.unmodifiableList(enclosed);
   }

   public boolean isOpen() {
      return (this.binding.modifiers & 32) != 0;
   }

   public boolean isUnnamed() {
      return this.binding.moduleName.length == 0;
   }

   public Element getEnclosingElement() {
      return null;
   }

   public List getDirectives() {
      if (this.isUnnamed()) {
         return EMPTY_DIRECTIVES;
      } else {
         if (this.directives == null) {
            this.directives = new ArrayList();
         }

         PackageBinding[] packs = this.binding.getExports();
         PackageBinding[] var5 = packs;
         int var4 = packs.length;

         for(int var3 = 0; var3 < var4; ++var3) {
            PackageBinding exp = var5[var3];
            exp = this.getModulesPackageBinding(exp);
            this.directives.add(new ExportsDirectiveImpl(exp));
         }

         Set transitive = new HashSet();
         ModuleBinding[] var6;
         int var13 = (var6 = this.binding.getRequiresTransitive()).length;

         for(var4 = 0; var4 < var13; ++var4) {
            ModuleBinding mBinding = var6[var4];
            transitive.add(mBinding);
         }

         ModuleBinding[] required = this.binding.getRequires();
         ModuleBinding[] var7 = required;
         int var15 = required.length;

         for(var13 = 0; var13 < var15; ++var13) {
            ModuleBinding mBinding = var7[var13];
            if (transitive.contains(mBinding)) {
               this.directives.add(new RequiresDirectiveImpl(mBinding, true));
            } else {
               this.directives.add(new RequiresDirectiveImpl(mBinding, false));
            }
         }

         TypeBinding[] tBindings = this.binding.getUses();
         TypeBinding[] var8 = tBindings;
         int var18 = tBindings.length;

         TypeBinding tBinding;
         for(var15 = 0; var15 < var18; ++var15) {
            tBinding = var8[var15];
            this.directives.add(new UsesDirectiveImpl(tBinding));
         }

         tBindings = this.binding.getServices();
         var8 = tBindings;
         var18 = tBindings.length;

         for(var15 = 0; var15 < var18; ++var15) {
            tBinding = var8[var15];
            this.directives.add(new ProvidesDirectiveImpl(tBinding));
         }

         packs = this.binding.getOpens();
         PackageBinding[] var19 = packs;
         var18 = packs.length;

         for(var15 = 0; var15 < var18; ++var15) {
            PackageBinding exp = var19[var15];
            exp = this.getModulesPackageBinding(exp);
            this.directives.add(new OpensDirectiveImpl(exp));
         }

         return this.directives;
      }
   }

   public Object accept(ElementVisitor visitor, Object param) {
      return visitor.visitModule(this, param);
   }

   protected AnnotationBinding[] getAnnotationBindings() {
      return ((ModuleBinding)this._binding).getAnnotations();
   }

   class ExportsDirectiveImpl extends PackageDirectiveImpl implements ModuleElement.ExportsDirective {
      ExportsDirectiveImpl(PackageBinding pBinding) {
         super(pBinding);
      }

      public Object accept(ModuleElement.DirectiveVisitor visitor, Object param) {
         return visitor.visitExports(this, param);
      }

      public ModuleElement.DirectiveKind getKind() {
         return DirectiveKind.EXPORTS;
      }

      public PackageElement getPackage() {
         return ModuleElementImpl.this._env.getFactory().newPackageElement(this.binding);
      }

      public List getTargetModules() {
         return this.targets != null ? this.targets : this.getTargetModules(ModuleElementImpl.this.binding.getExportRestrictions(this.binding));
      }
   }

   class OpensDirectiveImpl extends PackageDirectiveImpl implements ModuleElement.OpensDirective {
      OpensDirectiveImpl(PackageBinding pBinding) {
         super(pBinding);
      }

      public Object accept(ModuleElement.DirectiveVisitor visitor, Object param) {
         return visitor.visitOpens(this, param);
      }

      public ModuleElement.DirectiveKind getKind() {
         return DirectiveKind.OPENS;
      }

      public List getTargetModules() {
         return this.targets != null ? this.targets : this.getTargetModules(ModuleElementImpl.this.binding.getOpenRestrictions(this.binding));
      }
   }

   abstract class PackageDirectiveImpl {
      PackageBinding binding;
      List targets;

      PackageDirectiveImpl(PackageBinding pBinding) {
         this.binding = pBinding;
      }

      public PackageElement getPackage() {
         return ModuleElementImpl.this._env.getFactory().newPackageElement(this.binding);
      }

      public List getTargetModules(String[] restrictions) {
         if (this.targets != null) {
            return this.targets;
         } else if (restrictions.length == 0) {
            return this.targets = null;
         } else {
            List targets = new ArrayList(restrictions.length);
            String[] var6 = restrictions;
            int var5 = restrictions.length;

            for(int var4 = 0; var4 < var5; ++var4) {
               String string = var6[var4];
               ModuleBinding target = ModuleElementImpl.this.binding.environment.getModule(string.toCharArray());
               if (target != null) {
                  ModuleElement element = (ModuleElement)ModuleElementImpl.this._env.getFactory().newElement(target);
                  targets.add(element);
               }
            }

            return this.targets = Collections.unmodifiableList(targets);
         }
      }
   }

   class ProvidesDirectiveImpl implements ModuleElement.ProvidesDirective {
      TypeBinding service;
      public List implementations;

      ProvidesDirectiveImpl(TypeBinding service) {
         this.service = service;
      }

      public Object accept(ModuleElement.DirectiveVisitor visitor, Object param) {
         return visitor.visitProvides(this, param);
      }

      public ModuleElement.DirectiveKind getKind() {
         return DirectiveKind.PROVIDES;
      }

      public List getImplementations() {
         if (this.implementations != null) {
            return this.implementations;
         } else {
            TypeBinding[] implementations2 = ModuleElementImpl.this.binding.getImplementations(this.service);
            if (implementations2.length == 0) {
               return this.implementations = Collections.emptyList();
            } else {
               List list = new ArrayList(implementations2.length);
               Factory factory = ModuleElementImpl.this._env.getFactory();
               TypeBinding[] var7 = implementations2;
               int var6 = implementations2.length;

               for(int var5 = 0; var5 < var6; ++var5) {
                  TypeBinding type = var7[var5];
                  TypeElement element = (TypeElement)factory.newElement(type);
                  list.add(element);
               }

               return Collections.unmodifiableList(list);
            }
         }
      }

      public TypeElement getService() {
         return (TypeElement)ModuleElementImpl.this._env.getFactory().newElement(this.service);
      }
   }

   class RequiresDirectiveImpl implements ModuleElement.RequiresDirective {
      ModuleBinding dependency;
      boolean transitive;

      RequiresDirectiveImpl(ModuleBinding dependency, boolean transitive) {
         this.dependency = dependency;
         this.transitive = transitive;
      }

      public Object accept(ModuleElement.DirectiveVisitor visitor, Object param) {
         return visitor.visitRequires(this, param);
      }

      public ModuleElement.DirectiveKind getKind() {
         return DirectiveKind.REQUIRES;
      }

      public ModuleElement getDependency() {
         return (ModuleElement)ModuleElementImpl.this._env.getFactory().newElement(this.dependency, ElementKind.MODULE);
      }

      public boolean isStatic() {
         return false;
      }

      public boolean isTransitive() {
         return this.transitive;
      }
   }

   class UsesDirectiveImpl implements ModuleElement.UsesDirective {
      TypeBinding binding = null;

      UsesDirectiveImpl(TypeBinding binding) {
         this.binding = binding;
      }

      public Object accept(ModuleElement.DirectiveVisitor visitor, Object param) {
         return visitor.visitUses(this, param);
      }

      public ModuleElement.DirectiveKind getKind() {
         return DirectiveKind.USES;
      }

      public TypeElement getService() {
         return (TypeElement)ModuleElementImpl.this._env.getFactory().newElement(this.binding);
      }
   }
}
