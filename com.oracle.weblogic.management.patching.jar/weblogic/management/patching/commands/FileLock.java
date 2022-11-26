package weblogic.management.patching.commands;

import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import weblogic.management.patching.PatchingDebugLogger;

public class FileLock {
   public void createFileLock(String tmpUpdateScript) throws Exception {
      Path fileLck = this.getPath(tmpUpdateScript);

      try {
         Files.createFile(fileLck);
      } catch (FileAlreadyExistsException var4) {
         if (PatchingDebugLogger.isDebugEnabled()) {
            PatchingDebugLogger.debug("The file already exists", var4);
         }

         throw new CommandException(PatchingMessageTextFormatter.getInstance().getFileAlreadyExists(fileLck.toString(), var4));
      }
   }

   public void removeFileLock(String tmpUpdateScript) throws Exception {
      Path fileLck = this.getPath(tmpUpdateScript);

      try {
         Files.delete(fileLck);
      } catch (NoSuchFileException var4) {
         if (PatchingDebugLogger.isDebugEnabled()) {
            PatchingDebugLogger.debug("The file does not exist", var4);
         }
      }

   }

   public Path getPath(String tmpUpdateScript) throws Exception {
      if (tmpUpdateScript == null) {
         throw new IllegalArgumentException(PatchingMessageTextFormatter.getInstance().getTmpUpdateScriptNotSet());
      } else {
         return Paths.get(tmpUpdateScript + ".lck");
      }
   }
}
