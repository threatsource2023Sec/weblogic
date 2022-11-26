package weblogic.application;

import java.io.File;
import java.io.IOException;
import weblogic.application.library.IllegalSpecVersionTypeException;
import weblogic.utils.classloaders.MultiClassFinder;
import weblogic.utils.jars.VirtualJarFile;

public interface DeployableObjectInfo extends Module {
   String getAltDD();

   void populateViewFinders(File var1, String var2, boolean var3, VirtualJarFile var4, SplitDirectoryInfo var5, MultiClassFinder var6, MultiClassFinder var7) throws IOException, IllegalSpecVersionTypeException;
}
