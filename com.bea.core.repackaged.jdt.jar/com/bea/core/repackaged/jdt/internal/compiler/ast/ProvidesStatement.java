package com.bea.core.repackaged.jdt.internal.compiler.ast;

import com.bea.core.repackaged.jdt.internal.compiler.lookup.Binding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.BlockScope;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.MethodBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.ModuleBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.ProblemReferenceBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.ReferenceBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.Scope;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.TypeBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.TypeConstants;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ProvidesStatement extends ModuleStatement {
   public TypeReference serviceInterface;
   public TypeReference[] implementations;

   public boolean resolve(BlockScope scope) {
      ModuleDeclaration module = scope.referenceCompilationUnit().moduleDeclaration;
      ModuleBinding src = module.binding;
      TypeBinding infBinding = this.serviceInterface.resolveType(scope);
      boolean hasErrors = false;
      if (infBinding != null && infBinding.isValidBinding()) {
         if (!infBinding.isClass() && !infBinding.isInterface() && !infBinding.isAnnotationType()) {
            scope.problemReporter().invalidServiceRef(8389924, this.serviceInterface);
         }

         ReferenceBinding intf = (ReferenceBinding)this.serviceInterface.resolvedType;
         Set impls = new HashSet();

         for(int i = 0; i < this.implementations.length; ++i) {
            ReferenceBinding impl = (ReferenceBinding)this.implementations[i].resolveType(scope);
            if (impl != null && impl.isValidBinding() && impl.canBeSeenBy((Scope)scope)) {
               if (!impls.add(impl)) {
                  scope.problemReporter().duplicateTypeReference(8389912, this.implementations[i]);
               } else {
                  int problemId = 0;
                  ModuleBinding declaringModule = impl.module();
                  if (declaringModule != src) {
                     problemId = 16778526;
                  } else if (!impl.isClass() && !impl.isInterface()) {
                     problemId = 8389925;
                  } else if (impl.isNestedType() && !impl.isStatic()) {
                     problemId = 16778525;
                  } else {
                     MethodBinding provider = impl.getExactMethod(TypeConstants.PROVIDER, Binding.NO_PARAMETERS, scope.compilationUnitScope());
                     if (provider != null && (!provider.isValidBinding() || !provider.isPublic() || !provider.isStatic())) {
                        provider = null;
                     }

                     TypeBinding implType = impl;
                     if (provider != null) {
                        implType = provider.returnType;
                        if (implType instanceof ReferenceBinding && !((TypeBinding)implType).canBeSeenBy(scope)) {
                           ReferenceBinding referenceBinding = (ReferenceBinding)implType;
                           scope.problemReporter().invalidType(this.implementations[i], new ProblemReferenceBinding(referenceBinding.compoundName, referenceBinding, 2));
                           hasErrors = true;
                        }
                     } else if (impl.isAbstract()) {
                        problemId = 16778522;
                     } else {
                        MethodBinding defaultConstructor = impl.getExactConstructor(Binding.NO_PARAMETERS);
                        if (defaultConstructor != null && defaultConstructor.isValidBinding()) {
                           if (!defaultConstructor.isPublic()) {
                              problemId = 16778524;
                           }
                        } else {
                           problemId = 16778523;
                        }
                     }

                     if (((TypeBinding)implType).findSuperTypeOriginatingFrom(intf) == null) {
                        scope.problemReporter().typeMismatchError((TypeBinding)implType, (TypeBinding)intf, (ASTNode)this.implementations[i], (ASTNode)null);
                        hasErrors = true;
                     }
                  }

                  if (problemId != 0) {
                     scope.problemReporter().invalidServiceRef(problemId, this.implementations[i]);
                     hasErrors = true;
                  }
               }
            } else {
               hasErrors = true;
            }
         }

         return hasErrors;
      } else {
         return false;
      }
   }

   public List getResolvedImplementations() {
      List resolved = new ArrayList();
      if (this.implementations != null) {
         TypeReference[] var5;
         int var4 = (var5 = this.implementations).length;

         for(int var3 = 0; var3 < var4; ++var3) {
            TypeReference implRef = var5[var3];
            TypeBinding one = implRef.resolvedType;
            if (one != null) {
               resolved.add(one);
            }
         }
      }

      return resolved;
   }

   public StringBuffer print(int indent, StringBuffer output) {
      printIndent(indent, output);
      output.append("provides ");
      this.serviceInterface.print(0, output);
      output.append(" with ");

      for(int i = 0; i < this.implementations.length; ++i) {
         this.implementations[i].print(0, output);
         if (i < this.implementations.length - 1) {
            output.append(", ");
         }
      }

      output.append(";");
      return output;
   }
}
