package weblogic.security.jacc.simpleprovider;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.security.AccessController;
import java.security.PrivilegedAction;
import javax.security.jacc.PolicyContextException;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.management.DomainDir;
import weblogic.security.SecurityLogger;
import weblogic.security.SecurityRuntimeAccess;
import weblogic.utils.LocatorUtilities;

class PolicyWriter {
   private static DebugLogger jaccDebugLogger = DebugLogger.getDebugLogger("DebugSecurityJACCNonPolicy");
   private static final String REPOSITORY = "weblogic.jaccprovider.repository";
   private static final String REPOSITORY_DIR_NAME = "jacc";

   private PolicyWriter() {
   }

   protected static void createRepositoryDirectory() {
      File directory = new File(PolicyWriter.SecurityRuntimeAccessService.repository);
      directory = new File(directory.getAbsolutePath());
      if (!directory.exists()) {
         if (!directory.mkdirs()) {
            if (jaccDebugLogger.isDebugEnabled()) {
               jaccDebugLogger.debug("PolicyWriter unable to create: " + directory.toString());
            }

            throw new RuntimeException(SecurityLogger.getUnableToCreatePolicyWriterDirectory(directory.toString()));
         }

         if (jaccDebugLogger.isDebugEnabled()) {
            jaccDebugLogger.debug("PolicyWriter created JACC repository directory: " + directory.toString());
         }
      } else if (!directory.isDirectory()) {
         if (jaccDebugLogger.isDebugEnabled()) {
            jaccDebugLogger.debug("PolicyWriter Cannot create " + directory.toString() + ". Non directory file already exists with the same name. Please remove it");
         }

         throw new RuntimeException(SecurityLogger.getFileInTheWayOfDirectory(directory.toString()));
      }

   }

   protected static void createAppDirectory(String dirPath) {
      if (dirPath == null) {
         if (jaccDebugLogger.isDebugEnabled()) {
            jaccDebugLogger.debug("PolicyWriter Cannot create app directory because a null directory name was passed");
         }

         throw new AssertionError(SecurityLogger.getUnexpectedNullVariable("dirPath"));
      } else {
         File directory = new File(dirPath);
         directory = new File(directory.getAbsolutePath());
         if (!directory.exists()) {
            if (!directory.mkdirs()) {
               if (jaccDebugLogger.isDebugEnabled()) {
                  jaccDebugLogger.debug("PolicyWriter unable to create: " + directory.toString());
               }

               throw new RuntimeException(SecurityLogger.getUnableToCreatePolicyWriterDirectory(directory.toString()));
            }

            if (jaccDebugLogger.isDebugEnabled()) {
               jaccDebugLogger.debug("PolicyWriter created JACC repository directory: " + directory.toString());
            }
         } else {
            if (!directory.isDirectory()) {
               if (jaccDebugLogger.isDebugEnabled()) {
                  jaccDebugLogger.debug("PolicyWriter Cannot create " + directory.toString() + ". Non directory file already exists with the same name. Please remove it");
               }

               throw new RuntimeException(SecurityLogger.getFileInTheWayOfDirectory(directory.toString()));
            }

            String grantedFileName = new String(dirPath + File.separator + "granted.policy");
            File granted = new File(grantedFileName);
            if (granted.exists()) {
               if (!granted.delete()) {
                  if (jaccDebugLogger.isDebugEnabled()) {
                     jaccDebugLogger.debug("PolicyWriter.createAppDirectory unable to delete old: " + grantedFileName);
                  }

                  throw new RuntimeException("Unable to delete policy file: " + grantedFileName);
               }

               if (jaccDebugLogger.isDebugEnabled()) {
                  jaccDebugLogger.debug("PolicyWriter.createAppDirectory removed old " + grantedFileName);
               }
            }

            String excludedFileName = new String(dirPath + File.separator + "excluded.policy");
            File excluded = new File(excludedFileName);
            if (excluded.exists()) {
               if (!excluded.delete()) {
                  if (jaccDebugLogger.isDebugEnabled()) {
                     jaccDebugLogger.debug("PolicyWriter.createAppDirectory unable to delete old: " + excludedFileName);
                  }

                  throw new RuntimeException("Unable to delete policy file: " + excludedFileName);
               }

               if (jaccDebugLogger.isDebugEnabled()) {
                  jaccDebugLogger.debug("PolicyWriter.createAppDirectory removed old " + excludedFileName);
               }
            }
         }

      }
   }

   protected static void writeGrantStatements(String appDirName, String fileType, String grantStatements) throws PolicyContextException {
      if (appDirName == null) {
         if (jaccDebugLogger.isDebugEnabled()) {
            jaccDebugLogger.debug("PolicyWriter:writeGrantStatements null appDirName");
         }

      } else if (fileType == null) {
         if (jaccDebugLogger.isDebugEnabled()) {
            jaccDebugLogger.debug("PolicyWriter:writeGrantStatements null fileType");
         }

      } else if (grantStatements == null) {
         if (jaccDebugLogger.isDebugEnabled()) {
            jaccDebugLogger.debug("PolicyWriter:writeGrantStatements null grantStatements");
         }

      } else {
         FileOutputStream fos = null;
         String filename = appDirName + File.separator + fileType + ".policy";

         try {
            fos = new FileOutputStream(filename);
            if (jaccDebugLogger.isDebugEnabled()) {
               jaccDebugLogger.debug("PolicyWriter:writeGrantStatements opened: " + filename);
            }
         } catch (FileNotFoundException var16) {
            if (jaccDebugLogger.isDebugEnabled()) {
               jaccDebugLogger.debug("PolicyWriter:policy file can't be be found (filename may be too long): " + filename + " " + var16);
            }

            throw new PolicyContextException(SecurityLogger.getCannotOpenPolicyFile(filename, var16));
         }

         try {
            writeStatements(filename, fos, grantStatements);
         } catch (IOException var14) {
            if (jaccDebugLogger.isDebugEnabled()) {
               jaccDebugLogger.debug("PolicyWriter:cannot write to policy file: " + filename + " " + var14);
            }

            throw new PolicyContextException(SecurityLogger.getCannotWriteToPolicyFile(filename, var14));
         } finally {
            try {
               fos.close();
               if (jaccDebugLogger.isDebugEnabled()) {
                  jaccDebugLogger.debug("PolicyWriter:writeGrantStatements closed:" + filename);
               }

               fos = null;
            } catch (IOException var13) {
               if (jaccDebugLogger.isDebugEnabled()) {
                  jaccDebugLogger.debug("PolicyWriter:writeGrantStatements Caught an IOException while trying to close " + filename + " after an earlier exception.");
               }
            }

         }

      }
   }

   private static void writeStatements(String fileName, FileOutputStream fileOutputStream, String grantStatements) throws IOException {
      fileOutputStream.write(grantStatements.getBytes());
      if (jaccDebugLogger.isDebugEnabled()) {
         jaccDebugLogger.debug("PolicyWriter:writeStatements wrote to : " + fileName + " the following grant statements: " + grantStatements);
      }

   }

   protected static String generateAppDirectoryFileName(String contextID) {
      StringBuffer stringBuffer = new StringBuffer(PolicyWriter.SecurityRuntimeAccessService.repository + File.separator + contextID);
      if (jaccDebugLogger.isDebugEnabled()) {
         jaccDebugLogger.debug("PolicyWriter generated app directory file name: " + stringBuffer.toString());
      }

      return stringBuffer.toString();
   }

   protected static void deletePolicyFiles(String appDirName) throws IOException {
      if (appDirName != null) {
         File excludedFile = new File(appDirName + File.separator + "excluded.policy");
         URL excludedUrl = excludedFile.toURL();
         File grantedFile = new File(appDirName + File.separator + "granted.policy");
         URL grantedUrl = grantedFile.toURL();
         if (excludedFile.exists()) {
            excludedFile.delete();
            if (jaccDebugLogger.isDebugEnabled()) {
               jaccDebugLogger.debug("PolicyWriter delete removed policy file " + excludedFile.getAbsolutePath());
            }
         }

         grantedFile.delete();
         if (jaccDebugLogger.isDebugEnabled()) {
            jaccDebugLogger.debug("PolicyWriter delete removed policy file " + grantedFile.getAbsolutePath());
         }

         deleteFile(appDirName);
      }
   }

   protected static void deleteFile(String fileName) throws IOException {
      if (fileName != null) {
         File file = new File(fileName);
         if (file.exists()) {
            file.delete();
            if (jaccDebugLogger.isDebugEnabled()) {
               jaccDebugLogger.debug("PolicyWriter delete removed file " + file.getAbsolutePath());
            }
         }

      }
   }

   private static final class SecurityRuntimeAccessService {
      private static final String repository;

      static {
         SecurityRuntimeAccess runtimeAccess = (SecurityRuntimeAccess)AccessController.doPrivileged(new PrivilegedAction() {
            public SecurityRuntimeAccess run() {
               return (SecurityRuntimeAccess)LocatorUtilities.getService(SecurityRuntimeAccess.class);
            }
         });
         String defaultRepository = DomainDir.getTempDirForServer(runtimeAccess.getServerName()) + File.separator + "jacc";
         repository = System.getProperty("weblogic.jaccprovider.repository", defaultRepository);
      }
   }
}
