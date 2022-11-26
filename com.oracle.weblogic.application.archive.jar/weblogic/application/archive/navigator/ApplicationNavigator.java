package weblogic.application.archive.navigator;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;

public interface ApplicationNavigator {
   weblogic.application.archive.collage.Node getNode(String... var1);

   Iterable listNode(String[] var1, FileFilter var2);

   Iterable find(String[] var1, FileFilter var2);

   File getWriteableRootDir();

   void resetWritableRootDir(File var1);

   void registerMapping(String[] var1, File var2) throws IOException;

   String getName();
}
