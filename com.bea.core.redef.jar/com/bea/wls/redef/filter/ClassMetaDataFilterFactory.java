package com.bea.wls.redef.filter;

import java.util.HashSet;
import java.util.Set;
import serp.bytecode.lowlevel.ConstantPoolTable;
import weblogic.diagnostics.debug.DebugLogger;

public class ClassMetaDataFilterFactory implements MetaDataFilterFactory {
   private static final DebugLogger DEBUG = DebugLogger.getDebugLogger("DebugClassRedef");
   private final Set excludes = new HashSet();

   public ClassMetaDataFilterFactory(Set excludeClasses) {
      this.excludes.addAll(excludeClasses);
      if (DEBUG.isDebugEnabled()) {
         DEBUG.debug("ClassMetaDataFilterFactory created with : " + this.excludes);
      }

   }

   public MetaDataFilter newFilter(String clsName, Class prevCls, MetaDataFilter prev, ConstantPoolTable table, byte[] bytes) {
      if (this.excludes.contains(clsName)) {
         if (DEBUG.isDebugEnabled()) {
            DEBUG.debug("Excluding Class '" + clsName + "'  via ClassMetaDataFilterFactory");
         }

         return null;
      } else {
         return (MetaDataFilter)(prev != null ? prev : NullMetaDataFilter.NULL_FILTER);
      }
   }
}
