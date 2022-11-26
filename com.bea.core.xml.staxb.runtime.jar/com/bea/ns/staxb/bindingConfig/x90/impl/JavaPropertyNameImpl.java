package com.bea.ns.staxb.bindingConfig.x90.impl;

import com.bea.ns.staxb.bindingConfig.x90.JavaPropertyName;
import com.bea.xbean.values.JavaStringHolderEx;
import com.bea.xml.SchemaType;

public class JavaPropertyNameImpl extends JavaStringHolderEx implements JavaPropertyName {
   private static final long serialVersionUID = 1L;

   public JavaPropertyNameImpl(SchemaType sType) {
      super(sType, false);
   }

   protected JavaPropertyNameImpl(SchemaType sType, boolean b) {
      super(sType, b);
   }
}
