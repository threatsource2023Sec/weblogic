package com.oracle.wls.shaded.org.apache.xalan.xsltc.dom;

import com.oracle.wls.shaded.org.apache.xalan.xsltc.CollatorFactory;
import java.text.Collator;
import java.util.Locale;

public class CollatorFactoryBase implements CollatorFactory {
   public static final Locale DEFAULT_LOCALE = Locale.getDefault();
   public static final Collator DEFAULT_COLLATOR = Collator.getInstance();

   public Collator getCollator(String lang, String country) {
      return Collator.getInstance(new Locale(lang, country));
   }

   public Collator getCollator(Locale locale) {
      return locale == DEFAULT_LOCALE ? DEFAULT_COLLATOR : Collator.getInstance(locale);
   }
}
