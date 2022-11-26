package weblogic.nodemanager.server;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public interface NMProcessInfo {
   List specifyCmdLine(Properties var1);

   Map specifyEnvironmentValues(Properties var1);

   File specifyWorkingDir(Properties var1);

   File specifyOutputFile(Properties var1);
}
