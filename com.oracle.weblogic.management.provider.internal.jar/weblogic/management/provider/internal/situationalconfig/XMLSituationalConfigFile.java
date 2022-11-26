package weblogic.management.provider.internal.situationalconfig;

import java.io.File;
import weblogic.management.utils.situationalconfig.SituationalConfigDirectives;
import weblogic.management.utils.situationalconfig.SituationalConfigFile;

public class XMLSituationalConfigFile implements SituationalConfigFile {
   private File file;
   private boolean expired;

   public XMLSituationalConfigFile(File situationalConfigFile) {
      this.file = situationalConfigFile;
   }

   public File getFile() {
      return this.file;
   }

   public boolean expired() {
      return this.expired;
   }

   public void setExpired(boolean ex) {
      this.expired = ex;
   }

   public String toString() {
      return "XMLSituationalConfigFile[" + this.file + "]";
   }

   public SituationalConfigDirectives getSituationalConfigDirectives() throws Exception {
      return this.file.exists() ? new XMLSituationalConfigDirectives(this.file) : null;
   }
}
