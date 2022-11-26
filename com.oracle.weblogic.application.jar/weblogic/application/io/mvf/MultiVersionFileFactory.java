package weblogic.application.io.mvf;

import java.io.File;
import java.io.IOException;
import weblogic.application.io.mvf.internal.MultiVersionFileImpl;
import weblogic.j2ee.J2EELogger;

public enum MultiVersionFileFactory {
   instance;

   public MultiVersionFile createMultiVersionApplicationFile(String sourcePath) {
      File sourceFile = new File(sourcePath);
      if (sourceFile.isDirectory()) {
         try {
            return new MultiVersionFileImpl(sourceFile);
         } catch (IOException var4) {
            J2EELogger.logExceptionInMultiVersionFileCreation(sourceFile.getName(), sourcePath, var4);
            return null;
         }
      } else {
         return null;
      }
   }
}
