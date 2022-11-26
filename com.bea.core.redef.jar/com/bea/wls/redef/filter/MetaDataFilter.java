package com.bea.wls.redef.filter;

import com.bea.wls.redef.ConstructorMetaData;
import com.bea.wls.redef.FieldMetaData;
import com.bea.wls.redef.MethodMetaData;

public interface MetaDataFilter {
   boolean eval(FieldMetaData var1);

   boolean eval(ConstructorMetaData var1);

   boolean eval(MethodMetaData var1);
}
