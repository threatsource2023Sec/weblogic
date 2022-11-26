package com.oracle.xmlns.weblogic.persistenceConfiguration.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.oracle.xmlns.weblogic.persistenceConfiguration.GuiProfilingType;

public class GuiProfilingTypeImpl extends XmlComplexContentImpl implements GuiProfilingType {
   private static final long serialVersionUID = 1L;

   public GuiProfilingTypeImpl(SchemaType sType) {
      super(sType);
   }
}
