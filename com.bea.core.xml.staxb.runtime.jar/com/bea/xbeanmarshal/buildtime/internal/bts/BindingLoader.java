package com.bea.xbeanmarshal.buildtime.internal.bts;

import java.util.Collection;

public interface BindingLoader {
   BindingType getBindingType(BindingTypeName var1);

   BindingTypeName lookupPojoFor(XmlTypeName var1);

   BindingTypeName lookupTypeFor(JavaTypeName var1);

   BindingTypeName lookupElementFor(JavaTypeName var1);

   Collection bindingTypes();
}
