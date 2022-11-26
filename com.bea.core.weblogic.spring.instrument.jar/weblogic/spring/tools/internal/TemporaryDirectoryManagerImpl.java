package weblogic.spring.tools.internal;

import java.io.File;
import org.apache.commons.lang.RandomStringUtils;
import weblogic.utils.FileUtils;

public class TemporaryDirectoryManagerImpl implements TemporaryDirectoryManager {
   private static final String BASE_DIR = System.getProperty("java.io.tmpdir");
   private static final String SEPARATOR = System.getProperty("file.separator");
   private final String temporaryDirectory = this.initializeTemporaryDirectory();

   public TemporaryDirectoryManagerImpl() {
      File dir = new File(this.temporaryDirectory);
      dir.mkdir();
   }

   public String getTemporaryDirectory() {
      return this.temporaryDirectory;
   }

   public void cleanup() {
      File dir = new File(this.temporaryDirectory);
      FileUtils.remove(dir);
   }

   private String initializeTemporaryDirectory() {
      String dirName = "SI-" + RandomStringUtils.randomAlphanumeric(5);
      return BASE_DIR.endsWith(SEPARATOR) ? BASE_DIR + dirName : BASE_DIR + SEPARATOR + dirName;
   }
}
