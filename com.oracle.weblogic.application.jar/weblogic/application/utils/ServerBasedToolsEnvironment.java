package weblogic.application.utils;

import java.io.File;
import weblogic.application.compiler.ToolsEnvironment;

public class ServerBasedToolsEnvironment implements ToolsEnvironment {
   public File getTemporaryDirectory() {
      return PathUtils.getTempDir();
   }
}
