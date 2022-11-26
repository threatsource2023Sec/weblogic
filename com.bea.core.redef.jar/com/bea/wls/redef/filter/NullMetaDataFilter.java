package com.bea.wls.redef.filter;

import com.bea.wls.redef.ConstructorMetaData;
import com.bea.wls.redef.FieldMetaData;
import com.bea.wls.redef.MethodMetaData;

public class NullMetaDataFilter implements MetaDataFilter {
   public static final NullMetaDataFilter NULL_FILTER = new NullMetaDataFilter();

   private NullMetaDataFilter() {
   }

   public boolean eval(FieldMetaData field) {
      return true;
   }

   public boolean eval(ConstructorMetaData cons) {
      return true;
   }

   public boolean eval(MethodMetaData method) {
      return true;
   }
}
