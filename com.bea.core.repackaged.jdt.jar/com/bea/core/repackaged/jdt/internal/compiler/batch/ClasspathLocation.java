package com.bea.core.repackaged.jdt.internal.compiler.batch;

import com.bea.core.repackaged.jdt.core.compiler.CharOperation;
import com.bea.core.repackaged.jdt.internal.compiler.env.AccessRestriction;
import com.bea.core.repackaged.jdt.internal.compiler.env.AccessRuleSet;
import com.bea.core.repackaged.jdt.internal.compiler.env.IModule;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.ModuleBinding;
import com.bea.core.repackaged.jdt.internal.compiler.util.SuffixConstants;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

public abstract class ClasspathLocation implements FileSystem.Classpath, SuffixConstants {
   public static final int SOURCE = 1;
   public static final int BINARY = 2;
   String path;
   char[] normalizedPath;
   public AccessRuleSet accessRuleSet;
   IModule module;
   protected boolean isAutoModule;
   public String destinationPath;

   protected ClasspathLocation(AccessRuleSet accessRuleSet, String destinationPath) {
      this.accessRuleSet = accessRuleSet;
      this.destinationPath = destinationPath;
   }

   protected AccessRestriction fetchAccessRestriction(String qualifiedBinaryFileName) {
      if (this.accessRuleSet == null) {
         return null;
      } else {
         char[] qualifiedTypeName = qualifiedBinaryFileName.substring(0, qualifiedBinaryFileName.length() - SUFFIX_CLASS.length).toCharArray();
         if (File.separatorChar == '\\') {
            CharOperation.replace(qualifiedTypeName, File.separatorChar, '/');
         }

         return this.accessRuleSet.getViolatedRestriction(qualifiedTypeName);
      }
   }

   public int getMode() {
      return 3;
   }

   public int hashCode() {
      int result = 1;
      result = 31 * result + this.getMode();
      result = 31 * result + (this.path == null ? 0 : this.path.hashCode());
      return result;
   }

   public boolean equals(Object obj) {
      if (this == obj) {
         return true;
      } else if (obj == null) {
         return false;
      } else if (this.getClass() != obj.getClass()) {
         return false;
      } else {
         ClasspathLocation other = (ClasspathLocation)obj;
         String localPath = this.getPath();
         String otherPath = other.getPath();
         if (localPath == null) {
            if (otherPath != null) {
               return false;
            }
         } else if (!localPath.equals(otherPath)) {
            return false;
         }

         return this.getMode() == other.getMode();
      }
   }

   public String getPath() {
      return this.path;
   }

   public String getDestinationPath() {
      return this.destinationPath;
   }

   public void acceptModule(IModule mod) {
      this.module = mod;
      this.isAutoModule = mod.isAutomatic();
   }

   public boolean isAutomaticModule() {
      return this.isAutoModule;
   }

   public Collection getModuleNames(Collection limitModules) {
      return this.getModuleNames(limitModules, (m) -> {
         return this.getModule(m.toCharArray());
      });
   }

   public Collection getModuleNames(Collection limitModules, Function getModule) {
      if (this.module != null) {
         String name = String.valueOf(this.module.name());
         return this.selectModules(Collections.singleton(name), limitModules, getModule);
      } else {
         return Collections.emptyList();
      }
   }

   protected Collection selectModules(Set modules, Collection limitModules, Function getModule) {
      Object rootModules;
      HashSet allModules;
      if (limitModules != null) {
         allModules = new HashSet(modules);
         allModules.retainAll(limitModules);
         rootModules = allModules;
      } else {
         rootModules = this.allModules(modules, (s) -> {
            return s;
         }, (m) -> {
            return this.getModule(m.toCharArray());
         });
      }

      allModules = new HashSet((Collection)rootModules);
      Iterator var7 = ((Collection)rootModules).iterator();

      while(var7.hasNext()) {
         String mod = (String)var7.next();
         this.addRequired(mod, allModules, getModule);
      }

      return allModules;
   }

   private void addRequired(String mod, Set allModules, Function getModule) {
      IModule iMod = this.getModule(mod.toCharArray());
      if (iMod != null) {
         IModule.IModuleReference[] var8;
         int var7 = (var8 = iMod.requires()).length;

         for(int var6 = 0; var6 < var7; ++var6) {
            IModule.IModuleReference requiredRef = var8[var6];
            IModule reqMod = (IModule)getModule.apply(new String(requiredRef.name()));
            if (reqMod != null) {
               String reqModName = String.valueOf(reqMod.name());
               if (allModules.add(reqModName)) {
                  this.addRequired(reqModName, allModules, getModule);
               }
            }
         }
      }

   }

   protected List allModules(Iterable allSystemModules, Function getModuleName, Function getModule) {
      List result = new ArrayList();
      Iterator var6 = allSystemModules.iterator();

      while(var6.hasNext()) {
         Object mod = (Object)var6.next();
         String moduleName = (String)getModuleName.apply(mod);
         result.add(moduleName);
      }

      return result;
   }

   public boolean isPackage(String qualifiedPackageName, String moduleName) {
      return this.getModulesDeclaringPackage(qualifiedPackageName, moduleName) != null;
   }

   protected char[][] singletonModuleNameIf(boolean condition) {
      if (!condition) {
         return null;
      } else {
         return this.module != null ? new char[][]{this.module.name()} : new char[][]{ModuleBinding.UNNAMED};
      }
   }

   public void reset() {
      this.module = null;
   }
}
