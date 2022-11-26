package weblogic.management.security;

import java.util.Map;
import weblogic.security.spi.Resource;
import weblogic.security.spi.SelfDescribingResourceV2;

public interface ResourceIdInfo {
   SelfDescribingResourceV2 getSelfDescribingResource();

   String[] getKeyNames();

   String getResourceId(Map var1) throws IllegalArgumentException;

   String[] getParentResourceIds(Map var1) throws IllegalArgumentException;

   Resource getResource(Map var1) throws IllegalArgumentException;

   Map getMap(String var1) throws IllegalArgumentException;
}
