package com.bea.wls.redef.filter;

import serp.bytecode.lowlevel.ConstantPoolTable;

public class NullMetaDataFilterFactory implements MetaDataFilterFactory {
   public static final NullMetaDataFilterFactory NULL_FACTORY = new NullMetaDataFilterFactory();

   private NullMetaDataFilterFactory() {
   }

   public MetaDataFilter newFilter(String clsName, Class prevCls, MetaDataFilter prev, ConstantPoolTable table, byte[] bytes) {
      return (MetaDataFilter)(prev != null ? prev : NullMetaDataFilter.NULL_FILTER);
   }
}
