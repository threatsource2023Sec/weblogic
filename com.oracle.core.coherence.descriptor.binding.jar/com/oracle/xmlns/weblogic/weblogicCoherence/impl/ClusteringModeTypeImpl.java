package com.oracle.xmlns.weblogic.weblogicCoherence.impl;

import com.bea.xbean.values.JavaStringEnumerationHolderEx;
import com.bea.xml.SchemaType;
import com.oracle.xmlns.weblogic.weblogicCoherence.ClusteringModeType;

public class ClusteringModeTypeImpl extends JavaStringEnumerationHolderEx implements ClusteringModeType {
   private static final long serialVersionUID = 1L;

   public ClusteringModeTypeImpl(SchemaType sType) {
      super(sType, false);
   }

   protected ClusteringModeTypeImpl(SchemaType sType, boolean b) {
      super(sType, b);
   }
}
