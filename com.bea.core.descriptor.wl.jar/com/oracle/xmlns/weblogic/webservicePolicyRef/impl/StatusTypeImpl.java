package com.oracle.xmlns.weblogic.webservicePolicyRef.impl;

import com.bea.xbean.values.JavaStringEnumerationHolderEx;
import com.bea.xml.SchemaType;
import com.oracle.xmlns.weblogic.webservicePolicyRef.StatusType;

public class StatusTypeImpl extends JavaStringEnumerationHolderEx implements StatusType {
   private static final long serialVersionUID = 1L;

   public StatusTypeImpl(SchemaType sType) {
      super(sType, false);
   }

   protected StatusTypeImpl(SchemaType sType, boolean b) {
      super(sType, b);
   }
}
