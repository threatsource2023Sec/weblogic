package weblogic.application.utils;

import java.io.File;
import java.util.List;
import java.util.Map;

public interface ApplicationHasher {
   void addLibrariesOrApplication(File var1, File... var2) throws IllegalStateException, IllegalArgumentException;

   List getFilesInHash();

   List getExplodedFilesInHash();

   Map getIndividualHashes();

   String finishHash() throws IllegalStateException;

   boolean isFinished() throws IllegalStateException;
}
