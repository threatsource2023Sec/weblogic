package com.bea.core.repackaged.jdt.internal.compiler.lookup;

public class InnerEmulationDependency {
   public BlockScope scope;
   public boolean wasEnclosingInstanceSupplied;

   public InnerEmulationDependency(BlockScope scope, boolean wasEnclosingInstanceSupplied) {
      this.scope = scope;
      this.wasEnclosingInstanceSupplied = wasEnclosingInstanceSupplied;
   }
}
