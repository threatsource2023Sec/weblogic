package weblogic.utils.classloaders;

import java.text.MessageFormat;
import java.util.Locale;
import weblogic.i18n.Localizer;
import weblogic.i18n.logging.BaseTextFormatter;
import weblogic.i18ntools.L10nLookup;

public class ClassLoadersTextTextFormatter extends BaseTextFormatter {
   private Localizer l10n;

   public ClassLoadersTextTextFormatter() {
      this.l10n = L10nLookup.getLocalizer(Locale.getDefault(), "weblogic.utils.classloaders.ClassLoadersTextTextLocalizer", ClassLoadersTextTextFormatter.class.getClassLoader());
   }

   public ClassLoadersTextTextFormatter(Locale l) {
      this.l10n = L10nLookup.getLocalizer(l, "weblogic.utils.classloaders.ClassLoadersTextTextLocalizer", ClassLoadersTextTextFormatter.class.getClassLoader());
   }

   public static ClassLoadersTextTextFormatter getInstance() {
      return new ClassLoadersTextTextFormatter();
   }

   public static ClassLoadersTextTextFormatter getInstance(Locale l) {
      return new ClassLoadersTextTextFormatter(l);
   }

   public String wrongCompilerVersion(String arg0) {
      String id = "wrongCompilerVersion";
      String subsystem = "Class Loaders";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String unexpectedClassFormatError(String arg0) {
      String id = "unexpectedClassFormatError";
      String subsystem = "Class Loaders";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }
}
