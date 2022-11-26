package com.bea.core.repackaged.jdt.internal.compiler.lookup;

import com.bea.core.repackaged.jdt.internal.compiler.ast.ASTNode;
import com.bea.core.repackaged.jdt.internal.compiler.ast.Annotation;
import com.bea.core.repackaged.jdt.internal.compiler.ast.ModuleDeclaration;
import com.bea.core.repackaged.jdt.internal.compiler.util.SimpleLookupTable;
import java.util.Collection;
import java.util.HashMap;
import java.util.function.IntFunction;
import java.util.stream.Stream;

public class SourceModuleBinding extends ModuleBinding {
   public final CompilationUnitScope scope;

   public SourceModuleBinding(char[] moduleName, CompilationUnitScope scope, LookupEnvironment rootEnv) {
      super(moduleName);
      rootEnv.knownModules.put(moduleName, this);
      this.environment = new LookupEnvironment(rootEnv, this);
      this.scope = scope;
      scope.environment = this.environment;
   }

   public void setRequires(ModuleBinding[] requires, ModuleBinding[] requiresTransitive) {
      ModuleBinding javaBase = this.environment.javaBaseModule();
      this.requires = (ModuleBinding[])this.merge(this.requires, requires, javaBase, (var0) -> {
         return new ModuleBinding[var0];
      });
      this.requiresTransitive = (ModuleBinding[])this.merge(this.requiresTransitive, requiresTransitive, (Object)null, (var0) -> {
         return new ModuleBinding[var0];
      });
   }

   public void setUses(TypeBinding[] uses) {
      this.uses = (TypeBinding[])this.merge(this.uses, uses, (Object)null, (var0) -> {
         return new TypeBinding[var0];
      });
   }

   public TypeBinding[] getUses() {
      this.resolveTypes();
      return super.getUses();
   }

   public TypeBinding[] getServices() {
      this.resolveTypes();
      return super.getServices();
   }

   public TypeBinding[] getImplementations(TypeBinding binding) {
      this.resolveTypes();
      return super.getImplementations(binding);
   }

   private void resolveTypes() {
      if (this.scope != null) {
         ModuleDeclaration ast = this.scope.referenceCompilationUnit().moduleDeclaration;
         if (ast != null) {
            ast.resolveTypeDirectives(this.scope);
         }
      }

   }

   public void setServices(TypeBinding[] services) {
      this.services = (TypeBinding[])this.merge(this.services, services, (Object)null, (var0) -> {
         return new TypeBinding[var0];
      });
   }

   public void setImplementations(TypeBinding infBinding, Collection resolvedImplementations) {
      if (this.implementations == null) {
         this.implementations = new HashMap();
      }

      this.implementations.put(infBinding, (TypeBinding[])resolvedImplementations.toArray(new TypeBinding[resolvedImplementations.size()]));
   }

   private Object[] merge(Object[] one, Object[] two, Object extra, IntFunction supplier) {
      if (one.length == 0 && extra == null) {
         return two.length > 0 ? two : one;
      } else {
         int len0 = extra == null ? 0 : 1;
         int len1 = one.length;
         int len2 = two.length;
         Object[] result = (Object[])supplier.apply(len0 + len1 + len2);
         if (extra != null) {
            result[0] = extra;
         }

         System.arraycopy(one, 0, result, len0, len1);
         System.arraycopy(two, 0, result, len0 + len1, len2);
         return result;
      }
   }

   Stream getRequiredModules(boolean transitiveOnly) {
      this.scope.referenceContext.moduleDeclaration.resolveModuleDirectives(this.scope);
      return super.getRequiredModules(transitiveOnly);
   }

   public ModuleBinding[] getAllRequiredModules() {
      this.scope.referenceContext.moduleDeclaration.resolveModuleDirectives(this.scope);
      return super.getAllRequiredModules();
   }

   public PackageBinding[] getExports() {
      this.scope.referenceContext.moduleDeclaration.resolvePackageDirectives(this.scope);
      return super.getExports();
   }

   public PackageBinding[] getOpens() {
      this.scope.referenceContext.moduleDeclaration.resolvePackageDirectives(this.scope);
      return super.getOpens();
   }

   public long getAnnotationTagBits() {
      this.ensureAnnotationsResolved();
      return this.tagBits;
   }

   protected void ensureAnnotationsResolved() {
      if ((this.tagBits & 8589934592L) == 0L && this.scope != null) {
         ModuleDeclaration module = this.scope.referenceContext.moduleDeclaration;
         ASTNode.resolveAnnotations(module.scope, (Annotation[])module.annotations, (Binding)this);
         if ((this.tagBits & 70368744177664L) != 0L) {
            this.modifiers |= 1048576;
            this.tagBits |= 17179869184L;
         }

         this.tagBits |= 8589934592L;
      }

   }

   public AnnotationBinding[] getAnnotations() {
      this.ensureAnnotationsResolved();
      return this.retrieveAnnotations(this);
   }

   SimpleLookupTable storedAnnotations(boolean forceInitialize, boolean forceStore) {
      if (this.scope != null) {
         SimpleLookupTable annotationTable = super.storedAnnotations(forceInitialize, forceStore);
         if (annotationTable != null) {
            this.scope.referenceCompilationUnit().compilationResult.hasAnnotations = true;
         }

         return annotationTable;
      } else {
         return null;
      }
   }
}
