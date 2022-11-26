package weblogic.connector.external;

import java.io.File;
import weblogic.connector.configuration.AdditionalAnnotatedClassesProvider;
import weblogic.connector.deploy.RarArchive;
import weblogic.j2ee.descriptor.wl.DeploymentPlanBean;
import weblogic.utils.classloaders.GenericClassLoader;

public interface RAComplianceChecker {
   boolean validate(GenericClassLoader var1, RarArchive var2, File var3, File var4, DeploymentPlanBean var5, boolean var6, AdditionalAnnotatedClassesProvider var7) throws RAComplianceException;

   RAValidationInfo validate(String var1, RAInfo var2, GenericClassLoader var3) throws RAComplianceException;
}
