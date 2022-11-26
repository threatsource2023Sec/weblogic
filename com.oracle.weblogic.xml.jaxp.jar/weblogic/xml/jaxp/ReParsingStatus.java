package weblogic.xml.jaxp;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class ReParsingStatus {
   public boolean inLastReParsing = false;
   public boolean usedCache = false;
   public boolean usedRegistryResolver = false;
   public InputSource lastEntity = null;
   public SAXException error = null;

   public void prepareReParsing() {
      this.inLastReParsing = false;
      this.usedCache = false;
      this.usedRegistryResolver = false;
      this.error = null;
   }

   public boolean isReParsing() {
      return this.lastEntity != null;
   }

   public boolean needReParse() {
      return !this.inLastReParsing && this.error != null && this.usedRegistryResolver && this.usedCache;
   }
}
