package weblogic.persistence;

import java.io.File;
import javax.persistence.spi.PersistenceUnitInfo;
import weblogic.j2ee.descriptor.wl.DeploymentPlanBean;
import weblogic.utils.classloaders.GenericClassLoader;
import weblogic.utils.compiler.ToolFailureException;
import weblogic.utils.jars.VirtualJarFile;

public abstract class PersistenceUnitViewer extends AbstractPersistenceUnitRegistry {
   private static final boolean DISABLE_PU_VIEWER = Boolean.getBoolean("weblogic.deployment.disablePersistenceViewer");

   public PersistenceUnitViewer(GenericClassLoader cl, String moduleId, File configDir, DeploymentPlanBean planBean) {
      super(cl, moduleId, configDir, planBean);
   }

   public abstract void loadDescriptors() throws ToolFailureException;

   public PersistenceUnitInfo getPersistenceUnit(String name) {
      throw new UnsupportedOperationException("This class is for descriptor viewing only");
   }

   protected void throwLoadException(Exception e) throws ToolFailureException {
      throw new ToolFailureException("Unable to load persistence descriptor", e);
   }

   public static class EntryViewer extends PersistenceUnitViewer {
      private final VirtualJarFile vjf;

      public EntryViewer(VirtualJarFile vjf, String moduleId, File configDir, DeploymentPlanBean planBean) {
         super((GenericClassLoader)null, moduleId, configDir, planBean);
         this.vjf = vjf;
      }

      public void loadDescriptors() throws ToolFailureException {
         if (!PersistenceUnitViewer.DISABLE_PU_VIEWER) {
            try {
               this.loadPersistenceDescriptor(this.vjf, false, (File)null);
            } catch (Exception var2) {
               this.throwLoadException(var2);
            }

         }
      }
   }

   public static class ResourceViewer extends PersistenceUnitViewer {
      public ResourceViewer(GenericClassLoader cl, String moduleId, File configDir, DeploymentPlanBean planBean) {
         super(cl, moduleId, configDir, planBean);
      }

      public void loadDescriptors() throws ToolFailureException {
         if (!PersistenceUnitViewer.DISABLE_PU_VIEWER) {
            try {
               this.loadPersistenceDescriptors(false);
            } catch (Exception var2) {
               this.throwLoadException(var2);
            }

         }
      }
   }
}
