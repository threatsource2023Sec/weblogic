package weblogic.security;

import java.util.Map;
import weblogic.security.utils.ResourceUtils;

public class ResourceId {
   public static final String RESOURCE_TYPE = "ResourceType";

   public static String getResourceIdFromMap(Map resourceData) throws IllegalArgumentException {
      return ResourceUtils.getResourceIdFromMap(resourceData);
   }

   public static Map getMapFromResourceId(String resourceId) throws IllegalArgumentException {
      return ResourceUtils.getMapFromResourceId(resourceId);
   }

   public static String[] getResourceKeyNames(String resourceType) throws IllegalArgumentException {
      return ResourceUtils.getResourceKeyNames(resourceType);
   }

   public static String[] getParentResourceIds(String resourceId) throws IllegalArgumentException {
      return ResourceUtils.getParentResourceIds(resourceId);
   }
}
