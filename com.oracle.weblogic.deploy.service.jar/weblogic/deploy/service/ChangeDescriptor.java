package weblogic.deploy.service;

import java.io.Externalizable;
import java.io.Serializable;

public interface ChangeDescriptor extends Externalizable {
   String ADD = "add";
   String DELETE = "delete";
   String UPDATE = "update";
   String DATA = "data";
   String IDENTITY_CONFIG = "config";
   String IDENTITY_EXTERNAL = "external";
   String IDENTITY_NON_WLS = "non-wls";

   String getChangeOperation();

   String getChangeTarget();

   String getChangeSource();

   Serializable getVersion();

   Serializable getData();

   String getIdentity();
}
