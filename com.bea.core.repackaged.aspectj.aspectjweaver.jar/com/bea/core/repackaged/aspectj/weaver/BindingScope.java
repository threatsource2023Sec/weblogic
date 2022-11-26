package com.bea.core.repackaged.aspectj.weaver;

import com.bea.core.repackaged.aspectj.bridge.ISourceLocation;
import com.bea.core.repackaged.aspectj.weaver.patterns.FormalBinding;
import com.bea.core.repackaged.aspectj.weaver.patterns.SimpleScope;

public class BindingScope extends SimpleScope {
   private final ResolvedType enclosingType;
   private final ISourceContext sourceContext;
   private boolean importsUpdated = false;

   public BindingScope(ResolvedType type, ISourceContext sourceContext, FormalBinding[] bindings) {
      super(type.getWorld(), bindings);
      this.enclosingType = type;
      this.sourceContext = sourceContext;
   }

   public ResolvedType getEnclosingType() {
      return this.enclosingType;
   }

   public ISourceLocation makeSourceLocation(IHasPosition location) {
      return this.sourceContext.makeSourceLocation(location);
   }

   public UnresolvedType lookupType(String name, IHasPosition location) {
      if (this.enclosingType != null && !this.importsUpdated) {
         String pkgName = this.enclosingType.getPackageName();
         if (pkgName != null && !pkgName.equals("")) {
            String[] existingImports = this.getImportedPrefixes();
            String pkgNameWithDot = pkgName.concat(".");
            boolean found = false;
            String[] newImports = existingImports;
            int len$ = existingImports.length;

            for(int i$ = 0; i$ < len$; ++i$) {
               String existingImport = newImports[i$];
               if (existingImport.equals(pkgNameWithDot)) {
                  found = true;
                  break;
               }
            }

            if (!found) {
               newImports = new String[existingImports.length + 1];
               System.arraycopy(existingImports, 0, newImports, 0, existingImports.length);
               newImports[existingImports.length] = pkgNameWithDot;
               this.setImportedPrefixes(newImports);
            }
         }

         this.importsUpdated = true;
      }

      return super.lookupType(name, location);
   }
}
