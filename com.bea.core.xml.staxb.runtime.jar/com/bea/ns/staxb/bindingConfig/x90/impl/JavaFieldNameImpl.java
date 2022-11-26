package com.bea.ns.staxb.bindingConfig.x90.impl;

import com.bea.ns.staxb.bindingConfig.x90.JavaFieldName;
import com.bea.xbean.values.JavaStringHolderEx;
import com.bea.xml.SchemaType;

public class JavaFieldNameImpl extends JavaStringHolderEx implements JavaFieldName {
   private static final long serialVersionUID = 1L;

   public JavaFieldNameImpl(SchemaType sType) {
      super(sType, false);
   }

   protected JavaFieldNameImpl(SchemaType sType, boolean b) {
      super(sType, b);
   }
}
