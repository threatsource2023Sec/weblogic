package com.oracle.xmlns.weblogic.persistenceConfiguration.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.oracle.xmlns.weblogic.persistenceConfiguration.OrphanedKeyActionType;

public class OrphanedKeyActionTypeImpl extends XmlComplexContentImpl implements OrphanedKeyActionType {
   private static final long serialVersionUID = 1L;

   public OrphanedKeyActionTypeImpl(SchemaType sType) {
      super(sType);
   }
}
