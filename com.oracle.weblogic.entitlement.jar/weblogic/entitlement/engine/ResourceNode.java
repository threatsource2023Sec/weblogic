package weblogic.entitlement.engine;

import weblogic.security.spi.Resource;

public interface ResourceNode {
   Resource getResource();

   String getName();

   ResourceNode getParent();

   String[] getNamePathToRoot();
}
