package weblogic.application.compiler.utils;

import java.io.File;
import weblogic.application.compiler.ToolsEnvironment;

public class DefaultToolsEnvironment implements ToolsEnvironment {
   public File getTemporaryDirectory() {
      return new File(System.getProperty("java.io.tmpdir"));
   }
}
