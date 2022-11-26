package com.bea.core.repackaged.jdt.internal.compiler.batch;

import com.bea.core.repackaged.jdt.core.compiler.CharOperation;
import com.bea.core.repackaged.jdt.internal.compiler.ast.ExportsStatement;
import com.bea.core.repackaged.jdt.internal.compiler.ast.ModuleDeclaration;
import com.bea.core.repackaged.jdt.internal.compiler.ast.ModuleReference;
import com.bea.core.repackaged.jdt.internal.compiler.ast.OpensStatement;
import com.bea.core.repackaged.jdt.internal.compiler.ast.ProvidesStatement;
import com.bea.core.repackaged.jdt.internal.compiler.ast.RequiresStatement;
import com.bea.core.repackaged.jdt.internal.compiler.ast.TypeReference;
import com.bea.core.repackaged.jdt.internal.compiler.ast.UsesStatement;
import com.bea.core.repackaged.jdt.internal.compiler.env.ICompilationUnit;
import com.bea.core.repackaged.jdt.internal.compiler.env.IModule;
import com.bea.core.repackaged.jdt.internal.compiler.env.IModulePathEntry;
import com.bea.core.repackaged.jdt.internal.compiler.env.ISourceModule;
import com.bea.core.repackaged.jdt.internal.compiler.env.ModuleReferenceImpl;
import com.bea.core.repackaged.jdt.internal.compiler.env.PackageExportImpl;
import java.util.Arrays;

public class BasicModule implements ISourceModule {
   boolean isAutomodule;
   private boolean isOpen = false;
   char[] name;
   IModule.IModuleReference[] requires;
   IModule.IPackageExport[] exports;
   char[][] uses;
   Service[] provides;
   IModule.IPackageExport[] opens;
   private ICompilationUnit compilationUnit;

   private static PackageExportImpl createPackageExport(ExportsStatement[] refs, int i) {
      ExportsStatement ref = refs[i];
      PackageExportImpl exp = new PackageExportImpl();
      exp.pack = ref.pkgName;
      ModuleReference[] imp = ref.targets;
      if (imp != null) {
         exp.exportedTo = new char[imp.length][];

         for(int j = 0; j < imp.length; ++j) {
            exp.exportedTo = imp[j].tokens;
         }
      }

      return exp;
   }

   private static Service createService(TypeReference service, TypeReference[] with) {
      Service ser = new Service();
      ser.provides = CharOperation.concatWith(service.getTypeName(), '.');
      ser.with = new char[with.length][];

      for(int i = 0; i < with.length; ++i) {
         ser.with[i] = CharOperation.concatWith(with[i].getTypeName(), '.');
      }

      return ser;
   }

   private static PackageExportImpl createPackageOpen(OpensStatement ref) {
      PackageExportImpl exp = new PackageExportImpl();
      exp.pack = ref.pkgName;
      ModuleReference[] imp = ref.targets;
      if (imp != null) {
         exp.exportedTo = new char[imp.length][];

         for(int j = 0; j < imp.length; ++j) {
            exp.exportedTo = imp[j].tokens;
         }
      }

      return exp;
   }

   public BasicModule(ModuleDeclaration descriptor, IModulePathEntry root) {
      this.compilationUnit = descriptor.compilationResult().compilationUnit;
      this.name = descriptor.moduleName;
      int i;
      if (descriptor.requiresCount > 0) {
         RequiresStatement[] refs = descriptor.requires;
         this.requires = new ModuleReferenceImpl[refs.length];

         for(i = 0; i < refs.length; ++i) {
            ModuleReferenceImpl ref = new ModuleReferenceImpl();
            ref.name = CharOperation.concatWith(refs[i].module.tokens, '.');
            ref.modifiers = refs[i].modifiers;
            this.requires[i] = ref;
         }
      } else {
         this.requires = new ModuleReferenceImpl[0];
      }

      PackageExportImpl exp;
      if (descriptor.exportsCount > 0) {
         ExportsStatement[] refs = descriptor.exports;
         this.exports = new PackageExportImpl[refs.length];

         for(i = 0; i < refs.length; ++i) {
            exp = createPackageExport(refs, i);
            this.exports[i] = exp;
         }
      } else {
         this.exports = new PackageExportImpl[0];
      }

      if (descriptor.usesCount > 0) {
         UsesStatement[] u = descriptor.uses;
         this.uses = new char[u.length][];

         for(i = 0; i < u.length; ++i) {
            this.uses[i] = CharOperation.concatWith(u[i].serviceInterface.getTypeName(), '.');
         }
      }

      if (descriptor.servicesCount > 0) {
         ProvidesStatement[] services = descriptor.services;
         this.provides = new Service[descriptor.servicesCount];

         for(i = 0; i < descriptor.servicesCount; ++i) {
            this.provides[i] = createService(services[i].serviceInterface, services[i].implementations);
         }
      }

      if (descriptor.opensCount > 0) {
         OpensStatement[] refs = descriptor.opens;
         this.opens = new PackageExportImpl[refs.length];

         for(i = 0; i < refs.length; ++i) {
            exp = createPackageOpen(refs[i]);
            this.opens[i] = exp;
         }
      } else {
         this.opens = new PackageExportImpl[0];
      }

      this.isAutomodule = false;
      this.isOpen = descriptor.isOpen();
   }

   public ICompilationUnit getCompilationUnit() {
      return this.compilationUnit;
   }

   public char[] name() {
      return this.name;
   }

   public IModule.IModuleReference[] requires() {
      return this.requires;
   }

   public IModule.IPackageExport[] exports() {
      return this.exports;
   }

   public char[][] uses() {
      return this.uses;
   }

   public IModule.IService[] provides() {
      return this.provides;
   }

   public IModule.IPackageExport[] opens() {
      return this.opens;
   }

   public boolean isAutomatic() {
      return this.isAutomodule;
   }

   public boolean isOpen() {
      return this.isOpen;
   }

   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (!(o instanceof IModule)) {
         return false;
      } else {
         IModule mod = (IModule)o;
         return !CharOperation.equals(this.name, mod.name()) ? false : Arrays.equals(this.requires, mod.requires());
      }
   }

   public int hashCode() {
      int result = 17;
      int c = CharOperation.hashCode(this.name);
      result = 31 * result + c;
      c = Arrays.hashCode(this.requires);
      result = 31 * result + c;
      return result;
   }

   public String toString() {
      StringBuffer buffer = new StringBuffer(this.getClass().getName());
      this.toStringContent(buffer);
      return buffer.toString();
   }

   protected void toStringContent(StringBuffer buffer) {
      buffer.append("\nmodule ");
      buffer.append(this.name).append(' ');
      buffer.append('{').append('\n');
      int i;
      if (this.requires != null) {
         for(i = 0; i < this.requires.length; ++i) {
            buffer.append("\trequires ");
            if (this.requires[i].isTransitive()) {
               buffer.append(" public ");
            }

            buffer.append(this.requires[i].name());
            buffer.append(';').append('\n');
         }
      }

      if (this.exports != null) {
         buffer.append('\n');

         for(i = 0; i < this.exports.length; ++i) {
            buffer.append("\texports ");
            buffer.append(this.exports[i].toString());
         }
      }

      int var3;
      int var4;
      if (this.uses != null) {
         buffer.append('\n');
         char[][] var5;
         var4 = (var5 = this.uses).length;

         for(var3 = 0; var3 < var4; ++var3) {
            char[] cs = var5[var3];
            buffer.append(cs);
            buffer.append(';').append('\n');
         }
      }

      if (this.provides != null) {
         buffer.append('\n');
         Service[] var8;
         var4 = (var8 = this.provides).length;

         for(var3 = 0; var3 < var4; ++var3) {
            Service ser = var8[var3];
            buffer.append(ser.toString());
         }
      }

      buffer.append('\n').append('}').toString();
   }

   static class Service implements IModule.IService {
      char[] provides;
      char[][] with;

      public char[] name() {
         return this.provides;
      }

      public char[][] with() {
         return this.with;
      }

      public String toString() {
         StringBuffer buffer = new StringBuffer();
         buffer.append("provides");
         buffer.append(this.provides);
         buffer.append(" with ");
         buffer.append(this.with);
         buffer.append(';');
         return buffer.toString();
      }
   }
}
