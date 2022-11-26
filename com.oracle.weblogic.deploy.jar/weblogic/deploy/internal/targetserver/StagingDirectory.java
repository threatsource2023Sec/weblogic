package weblogic.deploy.internal.targetserver;

import java.io.File;
import java.io.IOException;
import weblogic.deploy.api.internal.utils.InstallDir;
import weblogic.management.deploy.internal.DeployerRuntimeLogger;

public final class StagingDirectory {
   private InstallDir root;
   private String dir;
   private boolean hasPlan;
   private boolean hasAltDescriptor;

   public StagingDirectory(String plan, String altDescriptor, String name, String stagingDir) throws IOException {
      this.assertNameIsNotNull(name);
      this.assertDirIsNotNull(stagingDir);
      this.hasPlan = plan != null;
      this.hasAltDescriptor = altDescriptor != null;
      this.dir = stagingDir;
      this.root = new InstallDir(name, this.dir);
      this.root.setAppDir(new File(this.dir));
      if (plan != null) {
         this.root.setPlan(new File(this.root.getConfigDir(), (new File(plan)).getName()));
      }

      if (altDescriptor != null) {
         this.root.setAltAppDD(new File(this.root.getAltDDDir(), (new File(altDescriptor)).getName()));
      }

   }

   private void assertDirIsNotNull(String dir) {
      String msg = DeployerRuntimeLogger.nullStagingDirectory();
      if (dir == null) {
         throw new IllegalArgumentException(msg);
      }
   }

   private void assertNameIsNotNull(String name) {
      String msg = DeployerRuntimeLogger.nullName();
      if (name == null) {
         throw new IllegalArgumentException(msg);
      }
   }

   public String getRoot() {
      return this.dir;
   }

   public String getSource() {
      return this.root.getArchive().getAbsolutePath();
   }

   public String getPlan() {
      return this.hasPlan ? this.root.getPlan().getAbsolutePath() : null;
   }

   public String getPlanDir() {
      return this.hasPlan ? this.root.getConfigDir().getAbsolutePath() : null;
   }

   public String getAltDescriptor() {
      return this.hasAltDescriptor ? this.root.getAltAppDD().getAbsolutePath() : null;
   }

   public String getAltDescriptorDir() {
      return this.hasAltDescriptor ? this.root.getAltDDDir().getAbsolutePath() : null;
   }
}
