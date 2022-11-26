package weblogic.entitlement.rules;

import java.util.Locale;
import weblogic.i18ntools.L10nLookup;

public class Localizer {
   public static String getText(String textId, Locale locale) {
      weblogic.i18n.Localizer l = L10nLookup.getLocalizer(locale, "weblogic.entitlement.rules.PredicateTextLocalizer");
      return l.get(textId);
   }
}
