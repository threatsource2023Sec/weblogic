package com.bea.core.repackaged.jdt.internal.compiler.impl;

import com.bea.core.repackaged.jdt.internal.compiler.env.AccessRestriction;
import com.bea.core.repackaged.jdt.internal.compiler.env.IBinaryType;
import com.bea.core.repackaged.jdt.internal.compiler.env.ICompilationUnit;
import com.bea.core.repackaged.jdt.internal.compiler.env.IModule;
import com.bea.core.repackaged.jdt.internal.compiler.env.ISourceModule;
import com.bea.core.repackaged.jdt.internal.compiler.env.ISourceType;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.BinaryModuleBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.LookupEnvironment;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.PackageBinding;
import com.bea.core.repackaged.jdt.internal.compiler.problem.AbortCompilation;

public interface ITypeRequestor {
   void accept(IBinaryType var1, PackageBinding var2, AccessRestriction var3);

   void accept(ICompilationUnit var1, AccessRestriction var2);

   void accept(ISourceType[] var1, PackageBinding var2, AccessRestriction var3);

   default void accept(IModule module, LookupEnvironment environment) {
      if (module instanceof ISourceModule) {
         try {
            ICompilationUnit compilationUnit = ((ISourceModule)module).getCompilationUnit();
            if (compilationUnit != null) {
               this.accept((ICompilationUnit)compilationUnit, (AccessRestriction)null);
            }
         } catch (AbortCompilation var4) {
         }
      } else {
         BinaryModuleBinding.create(module, environment);
      }

   }
}
