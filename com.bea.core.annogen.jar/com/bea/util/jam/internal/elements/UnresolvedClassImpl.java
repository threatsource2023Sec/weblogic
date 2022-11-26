package com.bea.util.jam.internal.elements;

import com.bea.util.jam.JClass;
import com.bea.util.jam.JPackage;

public final class UnresolvedClassImpl extends BuiltinClassImpl {
   private String mPackageName;

   public UnresolvedClassImpl(String packageName, String simpleName, ElementContext ctx) {
      super(ctx);
      if (packageName == null) {
         throw new IllegalArgumentException("null pkg");
      } else {
         this.mPackageName = packageName;
         this.reallySetSimpleName(simpleName);
      }
   }

   public String getQualifiedName() {
      return (this.mPackageName.length() > 0 ? this.mPackageName + '.' : "") + this.mSimpleName;
   }

   public String getFieldDescriptor() {
      return this.getQualifiedName();
   }

   public JPackage getContainingPackage() {
      return null;
   }

   public boolean isAssignableFrom(JClass c) {
      return false;
   }

   public boolean isUnresolvedType() {
      return true;
   }
}
