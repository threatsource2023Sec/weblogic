package com.bea.ns.staxb.bindingConfig.x90.impl;

import com.bea.ns.staxb.bindingConfig.x90.JavaClassName;
import com.bea.xbean.values.JavaStringHolderEx;
import com.bea.xml.SchemaType;

public class JavaClassNameImpl extends JavaStringHolderEx implements JavaClassName {
   private static final long serialVersionUID = 1L;

   public JavaClassNameImpl(SchemaType sType) {
      super(sType, false);
   }

   protected JavaClassNameImpl(SchemaType sType, boolean b) {
      super(sType, b);
   }
}
