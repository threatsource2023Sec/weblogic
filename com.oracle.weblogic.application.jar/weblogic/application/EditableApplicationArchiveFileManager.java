package weblogic.application;

import java.io.File;
import java.io.IOException;
import weblogic.application.archive.ApplicationArchive;

public class EditableApplicationArchiveFileManager extends ApplicationArchiveFileManager {
   public EditableApplicationArchiveFileManager(ApplicationArchive application) {
      super(application);
   }

   public void registerLink(String uri, String path) throws IOException {
      File f = new File(path);
      if (!f.exists()) {
         throw new IOException("Cannot register link for uri '" + uri + "'.  " + path + " does not exist.");
      } else {
         this.application.registerMapping(uri, f);
      }
   }
}
