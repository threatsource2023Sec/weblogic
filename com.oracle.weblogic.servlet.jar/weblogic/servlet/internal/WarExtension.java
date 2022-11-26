package weblogic.servlet.internal;

import java.io.File;
import java.io.IOException;
import weblogic.utils.classloaders.ClassFinder;

public interface WarExtension {
   String getName();

   File[] getRoots();

   ClassFinder getClassFinder() throws IOException;

   void remove();
}
