package com.bea.common.ldap;

public interface ObjectIdConverter {
   DistinguishedNameId convertObjectId(Object var1);

   Object convertDistinguishedNameId(DistinguishedNameId var1);
}
