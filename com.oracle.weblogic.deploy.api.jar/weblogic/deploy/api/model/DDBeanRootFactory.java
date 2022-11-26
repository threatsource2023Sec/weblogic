package weblogic.deploy.api.model;

import javax.enterprise.deploy.shared.ModuleType;
import org.jvnet.hk2.annotations.Contract;
import weblogic.descriptor.DescriptorBean;

@Contract
public interface DDBeanRootFactory {
   WebLogicDDBeanRoot create(String var1, WebLogicDeployableObject var2, ModuleType var3, DescriptorBean var4, boolean var5);

   WebLogicDDBeanRoot create(String var1, WebLogicDeployableObject var2, ModuleType var3);
}
