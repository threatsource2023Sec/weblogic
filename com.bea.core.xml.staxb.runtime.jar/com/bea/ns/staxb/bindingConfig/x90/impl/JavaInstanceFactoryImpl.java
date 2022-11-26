package com.bea.ns.staxb.bindingConfig.x90.impl;

import com.bea.ns.staxb.bindingConfig.x90.JavaInstanceFactory;
import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;

public class JavaInstanceFactoryImpl extends XmlComplexContentImpl implements JavaInstanceFactory {
   private static final long serialVersionUID = 1L;

   public JavaInstanceFactoryImpl(SchemaType sType) {
      super(sType);
   }
}
