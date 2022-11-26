package weblogic.management.patching.commands;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import weblogic.management.patching.PatchingDebugLogger;
import weblogic.management.patching.agent.PlatformUtils;
import weblogic.management.workflow.command.AbstractCommand;
import weblogic.management.workflow.command.CommandRevertInterface;
import weblogic.management.workflow.command.SharedState;

public class ExtensionJarExtractorCommand extends AbstractCommand implements CommandRevertInterface {
   private static final long serialVersionUID = -5700401497446144933L;
   @SharedState
   public transient String extensionJarLocation;
   @SharedState
   public transient String jarExtractionDir;

   public boolean execute() throws Exception {
      String className = this.getClass().getName();
      String workflowId = this.getContext().getWorkflowId();
      String logTarget = this.getAdminServerName();
      PatchingLogger.logExecutingStep(workflowId, className, logTarget);
      File extensionJarFile = new File(this.extensionJarLocation);
      JarFile extensionJar = new JarFile(extensionJarFile);
      Throwable var6 = null;

      try {
         String destinationDir = this.jarExtractionDir;
         File destination = new File(destinationDir);
         if (!destination.exists()) {
            destination.mkdir();
         }

         Enumeration enums = extensionJar.entries();

         while(enums.hasMoreElements()) {
            JarEntry entry = (JarEntry)enums.nextElement();
            String fileName = destinationDir + PlatformUtils.fileSeparator + entry.getName();
            File f = new File(fileName);
            if (!fileName.endsWith(PlatformUtils.fileSeparator) && f.getParent().endsWith(PlatformUtils.getPlatformString())) {
               String fName = destinationDir + PlatformUtils.fileSeparator + f.getName();
               File file = new File(fName);
               InputStream inputStream = extensionJar.getInputStream(entry);
               FileOutputStream fileOutputStream = new FileOutputStream(file);

               try {
                  while(inputStream.available() > 0) {
                     fileOutputStream.write(inputStream.read());
                  }

                  file.setExecutable(true);
                  file.setReadable(true);
               } catch (IOException var40) {
                  CommandException ce = new CommandException(PatchingMessageTextFormatter.getInstance().getWriteContentsToFileError(fileName, fName, var40.getMessage()));
                  PatchingLogger.logFailedStep(workflowId, className, logTarget, ce.initCause(var40));
                  throw ce;
               } finally {
                  try {
                     if (inputStream != null) {
                        inputStream.close();
                     }

                     if (fileOutputStream != null) {
                        fileOutputStream.close();
                     }
                  } catch (IOException var39) {
                  }

               }
            }
         }
      } catch (Throwable var42) {
         var6 = var42;
         throw var42;
      } finally {
         if (extensionJar != null) {
            if (var6 != null) {
               try {
                  extensionJar.close();
               } catch (Throwable var38) {
                  var6.addSuppressed(var38);
               }
            } else {
               extensionJar.close();
            }
         }

      }

      PatchingLogger.logCompletedStep(workflowId, className, logTarget);
      return true;
   }

   public boolean revert() throws Exception {
      String className = this.getClass().getName();
      String workflowId = this.getContext().getWorkflowId();
      String logTarget = this.getAdminServerName();
      PatchingLogger.logRevertingStep(workflowId, className, logTarget);
      File extensionJarFile = new File(this.extensionJarLocation);
      JarFile extensionJar = new JarFile(extensionJarFile);
      Throwable var6 = null;

      try {
         String destinationDir = this.jarExtractionDir;
         File destination = new File(destinationDir);
         if (!destination.exists()) {
            destination.mkdir();
         }

         Enumeration enums = extensionJar.entries();

         while(enums.hasMoreElements()) {
            JarEntry entry = (JarEntry)enums.nextElement();
            String fileName = destinationDir + PlatformUtils.fileSeparator + entry.getName();
            File f = new File(fileName);
            if (!fileName.endsWith(PlatformUtils.fileSeparator) && f.getParent().endsWith(PlatformUtils.getPlatformString())) {
               String fName = destinationDir + PlatformUtils.fileSeparator + f.getName();

               try {
                  File file = new File(fName);
                  if (file.exists()) {
                     if (PatchingDebugLogger.isDebugEnabled()) {
                        PatchingDebugLogger.debug("Deleting file : " + fName);
                     }

                     file.delete();
                  }
               } catch (Exception var24) {
                  CommandException ce = new CommandException(PatchingMessageTextFormatter.getInstance().getDeleteFileError(fName, var24.getMessage()));
                  PatchingLogger.logFailedRevertStep(workflowId, className, logTarget, ce.initCause(var24));
                  throw ce;
               }
            }
         }
      } catch (Throwable var25) {
         var6 = var25;
         throw var25;
      } finally {
         if (extensionJar != null) {
            if (var6 != null) {
               try {
                  extensionJar.close();
               } catch (Throwable var23) {
                  var6.addSuppressed(var23);
               }
            } else {
               extensionJar.close();
            }
         }

      }

      PatchingLogger.logCompletedRevertStep(workflowId, className, logTarget);
      return true;
   }

   private String getAdminServerName() {
      ServerUtils serverUtils = new ServerUtils();
      String adminServerName = serverUtils.getServerMBean().getName();
      return adminServerName;
   }
}
