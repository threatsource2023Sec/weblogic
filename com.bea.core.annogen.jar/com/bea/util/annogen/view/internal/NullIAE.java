package com.bea.util.annogen.view.internal;

import com.bea.util.annogen.override.AnnoBeanSet;

public final class NullIAE implements IndigenousAnnoExtractor {
   private static final IndigenousAnnoExtractor INSTANCE = new NullIAE();

   public static final IndigenousAnnoExtractor getInstance() {
      return INSTANCE;
   }

   private NullIAE() {
   }

   public boolean extractIndigenousAnnotations(AnnoBeanSet out) {
      return false;
   }
}
