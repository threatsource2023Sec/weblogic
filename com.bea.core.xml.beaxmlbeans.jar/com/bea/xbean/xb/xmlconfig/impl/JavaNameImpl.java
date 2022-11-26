package com.bea.xbean.xb.xmlconfig.impl;

import com.bea.xbean.values.JavaStringHolderEx;
import com.bea.xbean.xb.xmlconfig.JavaName;
import com.bea.xml.SchemaType;

public class JavaNameImpl extends JavaStringHolderEx implements JavaName {
   public JavaNameImpl(SchemaType sType) {
      super(sType, false);
   }

   protected JavaNameImpl(SchemaType sType, boolean b) {
      super(sType, b);
   }
}
