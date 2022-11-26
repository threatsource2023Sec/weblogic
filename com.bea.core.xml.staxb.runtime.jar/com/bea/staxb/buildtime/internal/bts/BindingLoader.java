package com.bea.staxb.buildtime.internal.bts;

public interface BindingLoader {
   BindingType getBindingType(BindingTypeName var1);

   BindingTypeName lookupPojoFor(XmlTypeName var1);

   BindingTypeName lookupXmlObjectFor(XmlTypeName var1);

   BindingTypeName lookupTypeFor(JavaTypeName var1);

   BindingTypeName lookupElementFor(JavaTypeName var1);
}
