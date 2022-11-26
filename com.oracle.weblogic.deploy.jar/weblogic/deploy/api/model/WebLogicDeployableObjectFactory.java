package weblogic.deploy.api.model;

import java.io.File;
import java.io.IOException;
import javax.enterprise.deploy.spi.exceptions.InvalidModuleException;
import org.jvnet.hk2.annotations.Contract;
import weblogic.application.compiler.deploymentview.EditableDeployableObjectFactory;
import weblogic.deploy.api.internal.utils.LibrarySpec;

@Contract
public interface WebLogicDeployableObjectFactory extends EditableDeployableObjectFactory {
   WebLogicDeployableObject createDeployableObject(File var1) throws IOException, InvalidModuleException;

   WebLogicDeployableObject createDeployableObject(File var1, File var2) throws IOException, InvalidModuleException;

   WebLogicDeployableObject createDeployableObject(File var1, File var2, File var3, File var4, LibrarySpec[] var5) throws IOException, InvalidModuleException;

   WebLogicDeployableObject createLazyDeployableObject(File var1, File var2, File var3, File var4, LibrarySpec[] var5) throws IOException, InvalidModuleException;
}
