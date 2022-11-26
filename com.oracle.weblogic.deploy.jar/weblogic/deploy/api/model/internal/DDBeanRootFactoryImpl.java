package weblogic.deploy.api.model.internal;

import javax.enterprise.deploy.shared.ModuleType;
import org.jvnet.hk2.annotations.Service;
import weblogic.deploy.api.model.DDBeanRootFactory;
import weblogic.deploy.api.model.WebLogicDDBeanRoot;
import weblogic.deploy.api.model.WebLogicDeployableObject;
import weblogic.descriptor.DescriptorBean;

@Service
public class DDBeanRootFactoryImpl implements DDBeanRootFactory {
   public WebLogicDDBeanRoot create(String altdd, WebLogicDeployableObject dObject, ModuleType moduleType, DescriptorBean db, boolean schemaBased) {
      return new DDBeanRootImpl(altdd, dObject, moduleType, db, schemaBased);
   }

   public WebLogicDDBeanRoot create(String altdd, WebLogicDeployableObject dObject, ModuleType moduleType) {
      return new DDBeanRootImpl(altdd, dObject, moduleType);
   }
}
