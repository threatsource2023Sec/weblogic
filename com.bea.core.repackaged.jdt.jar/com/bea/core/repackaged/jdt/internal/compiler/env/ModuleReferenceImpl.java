package com.bea.core.repackaged.jdt.internal.compiler.env;

import com.bea.core.repackaged.jdt.core.compiler.CharOperation;

public class ModuleReferenceImpl implements IModule.IModuleReference {
   public char[] name;
   public int modifiers;

   public char[] name() {
      return this.name;
   }

   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (!(o instanceof IModule.IModuleReference)) {
         return false;
      } else {
         IModule.IModuleReference mod = (IModule.IModuleReference)o;
         return this.modifiers != mod.getModifiers() ? false : CharOperation.equals(this.name, mod.name());
      }
   }

   public int hashCode() {
      return CharOperation.hashCode(this.name);
   }

   public int getModifiers() {
      return this.modifiers;
   }
}
