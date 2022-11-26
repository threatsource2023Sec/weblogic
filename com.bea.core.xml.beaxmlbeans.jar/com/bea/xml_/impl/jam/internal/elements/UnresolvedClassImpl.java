package com.bea.xml_.impl.jam.internal.elements;

import com.bea.xml_.impl.jam.JClass;
import com.bea.xml_.impl.jam.JPackage;

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
