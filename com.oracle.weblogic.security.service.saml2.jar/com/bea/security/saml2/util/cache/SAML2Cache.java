package com.bea.security.saml2.util.cache;

import java.util.Date;

public interface SAML2Cache {
   void put(Object var1, Object var2) throws SAML2CacheException;

   void put(Object var1, Object var2, Date var3) throws SAML2CacheException;

   Object get(Object var1) throws SAML2CacheException;

   Object remove(Object var1) throws SAML2CacheException;

   void clear() throws SAML2CacheException;

   int getCacheSize() throws SAML2CacheException;

   void configUpdated(int var1, int var2) throws SAML2CacheException;
}
