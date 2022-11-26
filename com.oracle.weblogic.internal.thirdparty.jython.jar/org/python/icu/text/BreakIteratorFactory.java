package org.python.icu.text;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.text.CharacterIterator;
import java.util.Locale;
import java.util.MissingResourceException;
import org.python.icu.impl.Assert;
import org.python.icu.impl.ICUBinary;
import org.python.icu.impl.ICULocaleService;
import org.python.icu.impl.ICUResourceBundle;
import org.python.icu.impl.ICUService;
import org.python.icu.util.ULocale;

final class BreakIteratorFactory extends BreakIterator.BreakIteratorServiceShim {
   static final ICULocaleService service = new BFService();
   private static final String[] KIND_NAMES = new String[]{"grapheme", "word", "line", "sentence", "title"};

   public Object registerInstance(BreakIterator iter, ULocale locale, int kind) {
      iter.setText((CharacterIterator)(new java.text.StringCharacterIterator("")));
      return service.registerObject(iter, locale, kind);
   }

   public boolean unregister(Object key) {
      return service.isDefault() ? false : service.unregisterFactory((ICUService.Factory)key);
   }

   public Locale[] getAvailableLocales() {
      return service == null ? ICUResourceBundle.getAvailableLocales() : service.getAvailableLocales();
   }

   public ULocale[] getAvailableULocales() {
      return service == null ? ICUResourceBundle.getAvailableULocales() : service.getAvailableULocales();
   }

   public BreakIterator createBreakIterator(ULocale locale, int kind) {
      if (service.isDefault()) {
         return createBreakInstance(locale, kind);
      } else {
         ULocale[] actualLoc = new ULocale[1];
         BreakIterator iter = (BreakIterator)service.get(locale, kind, actualLoc);
         iter.setLocale(actualLoc[0], actualLoc[0]);
         return iter;
      }
   }

   private static BreakIterator createBreakInstance(ULocale locale, int kind) {
      RuleBasedBreakIterator iter = null;
      ICUResourceBundle rb = ICUResourceBundle.getBundleInstance("org/python/icu/impl/data/icudt59b/brkitr", locale, ICUResourceBundle.OpenType.LOCALE_ROOT);
      ByteBuffer bytes = null;
      String typeKeyExt = null;
      String lbKeyValue;
      if (kind == 2) {
         lbKeyValue = locale.getKeywordValue("lb");
         if (lbKeyValue != null && (lbKeyValue.equals("strict") || lbKeyValue.equals("normal") || lbKeyValue.equals("loose"))) {
            typeKeyExt = "_" + lbKeyValue;
         }
      }

      String ssKeyword;
      try {
         lbKeyValue = typeKeyExt == null ? KIND_NAMES[kind] : KIND_NAMES[kind] + typeKeyExt;
         ssKeyword = rb.getStringWithFallback("boundaries/" + lbKeyValue);
         String rulesFileName = "brkitr/" + ssKeyword;
         bytes = ICUBinary.getData(rulesFileName);
      } catch (Exception var10) {
         throw new MissingResourceException(var10.toString(), "", "");
      }

      try {
         iter = RuleBasedBreakIterator.getInstanceFromCompiledRules(bytes);
      } catch (IOException var9) {
         Assert.fail((Exception)var9);
      }

      ULocale uloc = ULocale.forLocale(rb.getLocale());
      iter.setLocale(uloc, uloc);
      iter.setBreakType(kind);
      if (kind == 3) {
         ssKeyword = locale.getKeywordValue("ss");
         if (ssKeyword != null && ssKeyword.equals("standard")) {
            ULocale base = new ULocale(locale.getBaseName());
            return FilteredBreakIteratorBuilder.createInstance(base).build(iter);
         }
      }

      return iter;
   }

   private static class BFService extends ICULocaleService {
      BFService() {
         super("BreakIterator");

         class RBBreakIteratorFactory extends ICULocaleService.ICUResourceBundleFactory {
            protected Object handleCreate(ULocale loc, int kind, ICUService srvc) {
               return BreakIteratorFactory.createBreakInstance(loc, kind);
            }
         }

         this.registerFactory(new RBBreakIteratorFactory());
         this.markDefault();
      }

      public String validateFallbackLocale() {
         return "";
      }
   }
}
