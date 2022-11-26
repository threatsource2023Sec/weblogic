package weblogic.spring.tools.internal;

import java.io.File;
import java.io.IOException;
import weblogic.spring.tools.SpringInstrumentException;
import weblogic.utils.jars.JarFileUtils;

public class JarFileCreatorImpl implements JarFileCreator {
   public void create(File dir, String dest) throws SpringInstrumentException {
      try {
         JarFileUtils.createJarFileFromDirectory(dest, dir);
      } catch (IOException var4) {
         throw new SpringInstrumentException(var4.getMessage(), var4);
      }
   }
}
