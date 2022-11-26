package org.apache.xmlbeans.impl.xb.xmlconfig.impl;

import org.apache.xmlbeans.SchemaType;
import org.apache.xmlbeans.impl.values.JavaStringHolderEx;
import org.apache.xmlbeans.impl.xb.xmlconfig.JavaName;

public class JavaNameImpl extends JavaStringHolderEx implements JavaName {
   public JavaNameImpl(SchemaType sType) {
      super(sType, false);
   }

   protected JavaNameImpl(SchemaType sType, boolean b) {
      super(sType, b);
   }
}
