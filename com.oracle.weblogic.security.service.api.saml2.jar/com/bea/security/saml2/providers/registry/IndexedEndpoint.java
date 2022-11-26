package com.bea.security.saml2.providers.registry;

public interface IndexedEndpoint extends Endpoint {
   int getIndex();

   void setIndex(int var1);

   boolean isDefault();

   void setDefault(boolean var1);

   boolean isDefaultSet();

   void unsetDefault();
}
