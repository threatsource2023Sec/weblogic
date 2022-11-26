package com.oracle.xmlns.weblogic.persistenceConfiguration.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.oracle.xmlns.weblogic.persistenceConfiguration.ProxyManagerType;

public class ProxyManagerTypeImpl extends XmlComplexContentImpl implements ProxyManagerType {
   private static final long serialVersionUID = 1L;

   public ProxyManagerTypeImpl(SchemaType sType) {
      super(sType);
   }
}
