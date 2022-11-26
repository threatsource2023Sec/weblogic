package com.bea.util.annogen.override;

import com.bea.util.annogen.override.internal.StoredAnnoOverriderImpl;

public interface StoredAnnoOverrider extends AnnoOverrider {
   AnnoBeanSet findOrCreateStoredAnnoSetFor(ElementId var1);

   public static class Factory {
      public static StoredAnnoOverrider create() {
         return new StoredAnnoOverriderImpl();
      }
   }
}
