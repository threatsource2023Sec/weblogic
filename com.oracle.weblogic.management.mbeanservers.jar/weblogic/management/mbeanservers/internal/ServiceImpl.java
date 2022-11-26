package weblogic.management.mbeanservers.internal;

import weblogic.management.mbeanservers.Service;
import weblogic.management.provider.BaseServiceImpl;

public class ServiceImpl extends BaseServiceImpl implements Service {
   public ServiceImpl(String name, String type, Service parent, String parentAttribute) {
      super(name, type, parent, parentAttribute);
   }

   public ServiceImpl(String name, String type, Service parent) {
      this(name, type, parent, (String)null);
   }
}
