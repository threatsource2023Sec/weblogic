package weblogic.i18ntools.internal;

import org.xml.sax.Locator;
import weblogic.i18n.tools.Config;
import weblogic.i18ntools.parser.L10nParserTextFormatter;

public final class I18nConfig extends Config {
   public void linecol(L10nParserTextFormatter fmt) {
      if (this.isDebug()) {
         Thread.dumpStack();
      }

      Locator locator = this.getLocator();
      if (locator != null) {
         this.warn(fmt.locator(locator.getLineNumber(), locator.getColumnNumber()));
      } else {
         this.warn(fmt.locator(1, 1));
      }

   }
}
