package weblogic.i18ntools.parser;

import java.io.InputStream;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;

public final class LocaleEntityResolver implements EntityResolver {
   private L10nParserTextFormatter fmt = LocaleCatalogParser.getFmt();

   public InputSource resolveEntity(String publicId, String systemId) {
      InputSource dtd = null;
      InputStream is = this.getClass().getResourceAsStream("/weblogic/msgcat/l10n_msgcat.dtd");
      if (is != null) {
         dtd = new InputSource(is);
      } else {
         System.out.println(this.fmt.noEntity("/weblogic/msgcat/l10n_msgcat.dtd", systemId));
      }

      return dtd;
   }
}
