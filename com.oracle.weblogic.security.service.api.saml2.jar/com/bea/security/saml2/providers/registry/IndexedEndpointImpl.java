package com.bea.security.saml2.providers.registry;

import java.io.Serializable;

public class IndexedEndpointImpl extends EndpointImpl implements IndexedEndpoint, Serializable {
   private int index;
   private boolean isDefault;
   private boolean defaultSet;

   public boolean isDefaultSet() {
      return this.defaultSet;
   }

   public boolean isDefault() {
      return this.isDefault;
   }

   public void setDefault(boolean isDefault) {
      this.isDefault = isDefault;
      this.defaultSet = true;
   }

   public void unsetDefault() {
      this.isDefault = false;
      this.defaultSet = false;
   }

   public int getIndex() {
      return this.index;
   }

   public void setIndex(int index) {
      this.index = index;
   }
}
