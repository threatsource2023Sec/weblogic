package javax.enterprise.deploy.spi;

import java.io.File;
import java.io.InputStream;
import java.util.Locale;
import javax.enterprise.deploy.model.DeployableObject;
import javax.enterprise.deploy.shared.DConfigBeanVersionType;
import javax.enterprise.deploy.shared.ModuleType;
import javax.enterprise.deploy.spi.exceptions.DConfigBeanVersionUnsupportedException;
import javax.enterprise.deploy.spi.exceptions.InvalidModuleException;
import javax.enterprise.deploy.spi.exceptions.TargetException;
import javax.enterprise.deploy.spi.status.ProgressObject;

public interface DeploymentManager {
   Target[] getTargets() throws IllegalStateException;

   TargetModuleID[] getRunningModules(ModuleType var1, Target[] var2) throws TargetException, IllegalStateException;

   TargetModuleID[] getNonRunningModules(ModuleType var1, Target[] var2) throws TargetException, IllegalStateException;

   TargetModuleID[] getAvailableModules(ModuleType var1, Target[] var2) throws TargetException, IllegalStateException;

   DeploymentConfiguration createConfiguration(DeployableObject var1) throws InvalidModuleException;

   ProgressObject distribute(Target[] var1, File var2, File var3) throws IllegalStateException;

   /** @deprecated */
   ProgressObject distribute(Target[] var1, InputStream var2, InputStream var3) throws IllegalStateException;

   ProgressObject distribute(Target[] var1, ModuleType var2, InputStream var3, InputStream var4) throws IllegalStateException;

   ProgressObject start(TargetModuleID[] var1) throws IllegalStateException;

   ProgressObject stop(TargetModuleID[] var1) throws IllegalStateException;

   ProgressObject undeploy(TargetModuleID[] var1) throws IllegalStateException;

   boolean isRedeploySupported();

   ProgressObject redeploy(TargetModuleID[] var1, File var2, File var3) throws UnsupportedOperationException, IllegalStateException;

   ProgressObject redeploy(TargetModuleID[] var1, InputStream var2, InputStream var3) throws UnsupportedOperationException, IllegalStateException;

   void release();

   Locale getDefaultLocale();

   Locale getCurrentLocale();

   void setLocale(Locale var1) throws UnsupportedOperationException;

   Locale[] getSupportedLocales();

   boolean isLocaleSupported(Locale var1);

   DConfigBeanVersionType getDConfigBeanVersion();

   boolean isDConfigBeanVersionSupported(DConfigBeanVersionType var1);

   void setDConfigBeanVersion(DConfigBeanVersionType var1) throws DConfigBeanVersionUnsupportedException;
}
