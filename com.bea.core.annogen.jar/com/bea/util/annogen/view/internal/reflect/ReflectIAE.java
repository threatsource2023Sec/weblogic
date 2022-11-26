package com.bea.util.annogen.view.internal.reflect;

import com.bea.util.annogen.override.AnnoBeanSet;
import com.bea.util.annogen.view.internal.IndigenousAnnoExtractor;
import com.bea.util.annogen.view.internal.NullIAE;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public abstract class ReflectIAE implements IndigenousAnnoExtractor {
   public static IndigenousAnnoExtractor create(final Package x, final ReflectAnnogenTigerDelegate tiger) {
      return tiger == null ? NullIAE.getInstance() : new IndigenousAnnoExtractor() {
         public boolean extractIndigenousAnnotations(AnnoBeanSet out) {
            return tiger.extractAnnotations(out, x);
         }
      };
   }

   public static IndigenousAnnoExtractor create(final Class x, final ReflectAnnogenTigerDelegate tiger) {
      return tiger == null ? NullIAE.getInstance() : new IndigenousAnnoExtractor() {
         public boolean extractIndigenousAnnotations(AnnoBeanSet out) {
            return tiger.extractAnnotations(out, x);
         }
      };
   }

   public static IndigenousAnnoExtractor create(final Method x, final ReflectAnnogenTigerDelegate tiger) {
      return tiger == null ? NullIAE.getInstance() : new IndigenousAnnoExtractor() {
         public boolean extractIndigenousAnnotations(AnnoBeanSet out) {
            return tiger.extractAnnotations(out, x);
         }
      };
   }

   public static IndigenousAnnoExtractor create(final Constructor x, final ReflectAnnogenTigerDelegate tiger) {
      return tiger == null ? NullIAE.getInstance() : new IndigenousAnnoExtractor() {
         public boolean extractIndigenousAnnotations(AnnoBeanSet out) {
            return tiger.extractAnnotations(out, x);
         }
      };
   }

   public static IndigenousAnnoExtractor create(final Field x, final ReflectAnnogenTigerDelegate tiger) {
      return tiger == null ? NullIAE.getInstance() : new IndigenousAnnoExtractor() {
         public boolean extractIndigenousAnnotations(AnnoBeanSet out) {
            return tiger.extractAnnotations(out, x);
         }
      };
   }

   public static IndigenousAnnoExtractor create(final Constructor x, final int n, final ReflectAnnogenTigerDelegate tiger) {
      return tiger == null ? NullIAE.getInstance() : new IndigenousAnnoExtractor() {
         public boolean extractIndigenousAnnotations(AnnoBeanSet out) {
            return tiger.extractAnnotations(out, x, n);
         }
      };
   }

   public static IndigenousAnnoExtractor create(final Method x, final int n, final ReflectAnnogenTigerDelegate tiger) {
      return tiger == null ? NullIAE.getInstance() : new IndigenousAnnoExtractor() {
         public boolean extractIndigenousAnnotations(AnnoBeanSet out) {
            return tiger.extractAnnotations(out, x, n);
         }
      };
   }
}
