package weblogic.management.patching.agent;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.StringTokenizer;

public abstract class ZdtAgent {
   public static final String ZDT_AGENT_JAR_NAME = "com.oracle.weblogic.zdt.agent.jar";
   public static final String PATCHING_DIR;
   protected static final String DELIMITER = ",";
   protected ZdtAgentAction action;
   protected String domainDirectoryPath;
   protected String extensionJars;
   protected ZdtAgentOutputHandler outputHandler;

   ZdtAgent(ZdtAgentAction action) {
      this.action = action;
   }

   public void execRequest() throws Exception {
      if (this.action != null) {
         switch (this.action) {
            case CHECK_PREREQUISITES:
               this.checkPrerequisites();
               break;
            case EXTRACT:
               this.extract();
               break;
            case PREPARE_FOR_UPDATE:
               this.prepare();
               break;
            case UPDATE:
               this.update();
               break;
            case VALIDATE_UPDATE:
               this.validate();
               break;
            case CLEANUP:
               this.cleanupExtensionScripts();
               break;
            default:
               throw new IllegalArgumentException("TODO: Must specify valid action to execReqeust");
         }

      } else {
         throw new IllegalArgumentException("TODO: Must specify valid action to execReqeust");
      }
   }

   public abstract void checkPrerequisites() throws Exception;

   public abstract void prepare() throws Exception;

   public abstract void update() throws Exception;

   public abstract void validate() throws Exception;

   public void extract() throws Exception {
      this.outputHandler.info("Starting extract");
      if (this.extensionJars != null) {
         StringTokenizer st = new StringTokenizer(this.extensionJars, ",");
         String destinationDir = this.domainDirectoryPath + PATCHING_DIR;

         while(st.hasMoreTokens()) {
            String nextJar = st.nextToken();

            try {
               PlatformUtils.extractJar(Paths.get(this.domainDirectoryPath), nextJar, destinationDir);
               this.outputHandler.info("Call to PlatformUtils.extractJar() completed successfully");
            } catch (IOException var5) {
               this.outputHandler.error("Caught IOException while extracting jar file = " + nextJar + " to destination directory = " + destinationDir + " with exception: " + var5.getMessage());
               throw var5;
            }
         }
      }

      this.outputHandler.info("Call to extract completed successfully");
   }

   public void cleanupExtensionScripts() throws Exception {
      this.outputHandler.info("Starting cleanup of extension artifacts");
      if (this.extensionJars != null) {
         StringTokenizer st = new StringTokenizer(this.extensionJars, ",");
         String destinationDir = this.domainDirectoryPath + PATCHING_DIR;

         while(st.hasMoreTokens()) {
            String nextJar = st.nextToken();

            try {
               PlatformUtils.deleteExtensionScripts(Paths.get(this.domainDirectoryPath), nextJar, destinationDir, this.outputHandler);
               this.outputHandler.info("Call to PlatformUtils.deleteExtensionScripts() completed successfully");
            } catch (IOException var5) {
               this.outputHandler.error("Caught IOException while deleting extension scripts: " + var5.getMessage());
               throw var5;
            }
         }
      }

      this.outputHandler.info("Call to cleanup of extension artifacts completed successfully");
   }

   static {
      PATCHING_DIR = PlatformUtils.fileSeparator + "bin" + PlatformUtils.fileSeparator + "patching";
   }
}
