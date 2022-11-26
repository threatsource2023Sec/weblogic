package com.bea.xml_.impl.jam.internal.elements;

import com.bea.xml_.impl.jam.JClass;

public final class VoidClassImpl extends BuiltinClassImpl {
   private static final String SIMPLE_NAME = "void";

   public static boolean isVoid(String fd) {
      return fd.equals("void");
   }

   public VoidClassImpl(ElementContext ctx) {
      super(ctx);
      super.reallySetSimpleName("void");
   }

   public boolean isVoidType() {
      return true;
   }

   public boolean isAssignableFrom(JClass c) {
      return false;
   }
}
