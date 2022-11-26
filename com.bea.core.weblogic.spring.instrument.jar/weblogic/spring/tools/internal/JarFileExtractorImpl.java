package weblogic.spring.tools.internal;

import java.io.File;
import java.io.IOException;
import weblogic.utils.jars.JarFileUtils;

public class JarFileExtractorImpl implements JarFileExtractor {
   public void extract(File jarFile, File dest) throws IOException {
      JarFileUtils.extract(jarFile, dest);
   }
}
