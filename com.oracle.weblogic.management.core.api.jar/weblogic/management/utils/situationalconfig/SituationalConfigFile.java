package weblogic.management.utils.situationalconfig;

import java.io.File;

public interface SituationalConfigFile {
   File getFile();

   boolean expired();

   SituationalConfigDirectives getSituationalConfigDirectives() throws Exception;
}
