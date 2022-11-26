package weblogic.deploy.api.model.internal;

import java.io.File;
import java.io.IOException;
import javax.enterprise.deploy.shared.ModuleType;
import javax.enterprise.deploy.spi.exceptions.InvalidModuleException;
import org.jvnet.hk2.annotations.Service;
import weblogic.application.compiler.deploymentview.EditableDeployableObject;
import weblogic.application.compiler.deploymentview.EditableJ2eeApplicationObject;
import weblogic.application.compiler.deploymentview.EditableScaApplicationObject;
import weblogic.deploy.api.internal.utils.AppMerger;
import weblogic.deploy.api.internal.utils.ConfigHelper;
import weblogic.deploy.api.internal.utils.EarMerger;
import weblogic.deploy.api.internal.utils.LibrarySpec;
import weblogic.deploy.api.model.WebLogicDeployableObject;
import weblogic.deploy.api.model.WebLogicDeployableObjectFactory;
import weblogic.deploy.api.model.sca.internal.WebLogicScaApplicationObject;

@Service
public class WebLogicDeployableObjectFactoryImpl implements WebLogicDeployableObjectFactory {
   private File app;
   private File appRoot;
   private File plan;
   private File plandir;
   private String lightWeightAppName = null;
   private boolean beanScaffoldingEnabled = false;

   public WebLogicDeployableObjectFactoryImpl() {
   }

   public WebLogicDeployableObjectFactoryImpl(File app, File appRoot, File plan, File plandir, LibrarySpec[] libraries) {
      this.app = app;
      this.appRoot = appRoot;
      this.plan = plan;
      this.plandir = plandir;
   }

   public EditableDeployableObject createDeployableObject(String uri, String altdd, ModuleType mt) throws IOException {
      return new WebLogicDeployableObjectImpl(new File(uri), mt, (WebLogicDeployableObject)null, uri, altdd, this.appRoot, this.plan, this.plandir);
   }

   public EditableJ2eeApplicationObject createApplicationObject() throws IOException {
      return new WebLogicJ2eeApplicationObjectImpl(this.app, this.appRoot, this.plan, this.plandir);
   }

   public EditableScaApplicationObject createScaApplicationObject() throws IOException {
      return new WebLogicScaApplicationObject(this.app, this.appRoot, this.plan, this.plandir);
   }

   public WebLogicDeployableObject createDeployableObject(File app) throws IOException, InvalidModuleException {
      ConfigHelper.checkParam("File", app);
      return this.createDeployableObject(app, (File)null, (File)null, (File)null, (LibrarySpec[])null);
   }

   public WebLogicDeployableObject createDeployableObject(File app, File appRoot) throws IOException, InvalidModuleException {
      return this.createDeployableObject(app, appRoot, (File)null, (File)null, (LibrarySpec[])null);
   }

   public WebLogicDeployableObject createLazyDeployableObject(File app, File appRoot, File plan, File plandir, LibrarySpec[] libraries) throws IOException, InvalidModuleException {
      return this.createDeployableObject(app, appRoot, plan, plandir, libraries, true);
   }

   public WebLogicDeployableObject createDeployableObject(File app, File appRoot, File plan, File plandir, LibrarySpec[] libraries) throws IOException, InvalidModuleException {
      return this.createDeployableObject(app, appRoot, plan, plandir, libraries, false);
   }

   private WebLogicDeployableObject createDeployableObject(File app, File appRoot, File plan, File plandir, LibrarySpec[] libraries, boolean basicView) throws IOException, InvalidModuleException {
      this.app = app;
      this.appRoot = appRoot;
      this.plan = plan;
      this.plandir = plandir;
      AppMerger appMerger = new EarMerger();
      WebLogicDeployableObject dobj = appMerger.getMergedApp(app, plan, plandir, libraries, this, basicView, this.lightWeightAppName, this.beanScaffoldingEnabled);
      dobj.setAppMerge(appMerger.getAppMerge());
      return dobj;
   }

   public void setLightWeightAppName(String appName) {
      this.lightWeightAppName = appName;
   }

   public void enableBeanScaffolding() {
      this.beanScaffoldingEnabled = true;
   }
}
