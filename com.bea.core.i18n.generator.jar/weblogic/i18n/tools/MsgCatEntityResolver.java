package weblogic.i18n.tools;

import java.io.InputStream;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;

public final class MsgCatEntityResolver implements EntityResolver {
   public InputSource resolveEntity(String publicId, String systemId) {
      InputSource dtd = null;
      InputStream is = this.getClass().getResourceAsStream("/weblogic/msgcat/msgcat.dtd");
      if (is != null) {
         dtd = new InputSource(is);
      }

      return dtd;
   }
}
