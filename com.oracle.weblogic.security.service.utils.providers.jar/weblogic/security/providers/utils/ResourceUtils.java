package weblogic.security.providers.utils;

import com.bea.common.security.SecurityLogger;
import com.bea.common.security.utils.CSSPlatformProxy;
import java.lang.reflect.Method;
import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import weblogic.security.service.AdminResource;
import weblogic.security.service.ApplicationResource;
import weblogic.security.service.COMResource;
import weblogic.security.service.EISResource;
import weblogic.security.service.EJBResource;
import weblogic.security.service.InvalidParameterException;
import weblogic.security.service.JDBCResource;
import weblogic.security.service.JMSResource;
import weblogic.security.service.JMXResource;
import weblogic.security.service.JNDIResource;
import weblogic.security.service.ServerResource;
import weblogic.security.service.URLResource;
import weblogic.security.service.WebServiceResource;
import weblogic.security.service.WorkContextResource;
import weblogic.security.spi.Resource;
import weblogic.security.spi.SelfDescribingResourceV2;

public final class ResourceUtils {
   public static final String WILDCARD = "*";
   public static final String RESOURCE_TYPE = "ResourceType";
   public static final String EJB_COMPONENT = "EJB";
   public static final String WEBAPP_COMPONENT = "WebApp";
   public static final String CONNECTOR_COMPONENT = "Connector";
   public static final String WEBSERVICE_COMPONENT = "WebService";
   private static final char ESCAPE_CHAR = '\\';
   private static final char[] escapeChars = new char[]{'*', '\\'};
   private static final String KEY_SEPARATOR = ", ";
   private static final char ARRAY_SEPARATOR = ',';
   private static final String EMPTY_ARRAY_LIST = "{}";
   private static final String ARRAY_BEGIN = "{";
   private static final String ARRAY_END = "}";
   private static final String TYPE_ID = "type=";
   private static final String APP_SEARCH_ID = ", application=";
   private static final String APP_RESOURCE_KEY = "application";
   private static final String MODULE_RESOURCE_KEY = "module";
   private static final String CONTEXT_RESOURCE_KEY = "contextPath";
   private static final AdminResource ADM = new AdminResource((String)null, (String)null, (String)null);
   private static final ApplicationResource APP = new ApplicationResource((String)null);
   private static final COMResource COM = new COMResource((String)null, (String)null);
   private static final EISResource EIS = new EISResource((String)null, (String)null, (String)null, (String)null);
   private static final EJBResource EJB = new EJBResource((String)null, (String)null, (String)null, (String)null, (String)null, (String[])null);
   private static final JDBCResource JDBC = new JDBCResource((String)null, (String)null, (String)null, (String)null, (String)null);
   private static final JMSResource JMS = new JMSResource((String)null, (String)null, (String)null, (String)null, (String)null);
   private static final JMXResource JMX = new JMXResource((String)null, (String)null, (String)null, (String)null);
   private static final JNDIResource JNDI = new JNDIResource((String)null, (String[])null, (String)null);
   private static final ServerResource SVR = new ServerResource((String)null, (String)null, (String)null);
   private static final URLResource URL = new URLResource((String)null, (String)null, (String)null, (String)null, (String)null);
   private static final WebServiceResource WSS = new WebServiceResource((String)null, (String)null, (String)null, (String)null, (String[])null);
   private static final WorkContextResource WC = new WorkContextResource((String[])null, (String)null);
   private static HashMap ResourceIdInfoMap = new HashMap();

   public static String[] listRegisteredResourceTypes() {
      Set keys = ResourceIdInfoMap.keySet();
      return (String[])((String[])keys.toArray(new String[keys.size()]));
   }

   public static boolean isResourceTypeRegistered(Principal subject, ResourceIdInfo info) throws IllegalArgumentException {
      if (CSSPlatformProxy.getInstance().isOnWLS()) {
         try {
            Class securityServiceManager = Class.forName("weblogic.security.service.SecurityServiceManager");
            Method m = securityServiceManager.getMethod("checkKernelIdentity", Class.forName("weblogic.security.acl.internal.AuthenticatedSubject"));
            m.invoke((Object)null, subject);
         } catch (RuntimeException var4) {
            throw var4;
         } catch (Exception var5) {
            throw new RuntimeException(var5);
         }
      }

      if (info != null && info.getSelfDescribingResource() != null) {
         String type = info.getSelfDescribingResource().getType();
         if (type != null && type.length() != 0) {
            return ResourceIdInfoMap.containsKey(type);
         } else {
            throw new IllegalArgumentException(SecurityLogger.getInvalidResourceType(type));
         }
      } else {
         throw new IllegalArgumentException(SecurityLogger.getNoResourceType());
      }
   }

   public static void registerResourceType(Principal subject, ResourceIdInfo info) throws IllegalArgumentException {
      boolean registered = false;
      String type = null;

      try {
         registered = isResourceTypeRegistered(subject, info);
         type = info.getSelfDescribingResource().getType();
      } catch (IllegalArgumentException var5) {
         throw var5;
      }

      if (registered) {
         throw new IllegalArgumentException(SecurityLogger.getResourceTypeAlreadyRegistered(type));
      } else {
         ResourceIdInfoMap.put(type, info);
      }
   }

   public static String getResourceIdFromMap(Map resourceData) throws IllegalArgumentException {
      if (resourceData != null && !resourceData.isEmpty()) {
         Object resType = resourceData.get("ResourceType");
         if (resType != null && resType instanceof String) {
            ResourceIdInfo info = (ResourceIdInfo)ResourceIdInfoMap.get((String)resType);
            if (info == null) {
               throw new IllegalArgumentException(SecurityLogger.getInvalidResourceType(resType.toString()));
            } else {
               return info.getResourceId(resourceData);
            }
         } else {
            throw new IllegalArgumentException(SecurityLogger.getNoResourceType());
         }
      } else {
         throw new IllegalArgumentException(SecurityLogger.getNoResourceData());
      }
   }

   public static Map getMapFromResourceId(String resourceId) throws IllegalArgumentException {
      if (resourceId != null && resourceId.length() != 0) {
         if (!resourceId.startsWith("type=")) {
            throw new IllegalArgumentException(SecurityLogger.getNoResourceType());
         } else {
            int start = "type=".length();
            int end = resourceId.length();
            int index = indexOf(resourceId, ", ", start);
            if (index == -1) {
               index = end;
            }

            String resType = resourceId.substring(start, index);
            ResourceIdInfo info = (ResourceIdInfo)ResourceIdInfoMap.get(resType);
            if (info == null) {
               throw new IllegalArgumentException(SecurityLogger.getInvalidResourceType(resType));
            } else {
               return info.getMap(resourceId);
            }
         }
      } else {
         throw new IllegalArgumentException(SecurityLogger.getNoResourceIdentifier());
      }
   }

   public static String[] getResourceKeyNames(String resourceType) throws IllegalArgumentException {
      if (resourceType != null && resourceType.length() != 0) {
         ResourceIdInfo info = (ResourceIdInfo)ResourceIdInfoMap.get(resourceType);
         if (info == null) {
            throw new IllegalArgumentException(SecurityLogger.getInvalidResourceType(resourceType));
         } else {
            return info.getKeyNames();
         }
      } else {
         throw new IllegalArgumentException(SecurityLogger.getNoResourceType());
      }
   }

   public static String[] getParentResourceIds(String resourceId) throws IllegalArgumentException {
      Map resMap = getMapFromResourceId(resourceId);
      return getParentResourceIdsFromMap(resMap);
   }

   public static String getResourceTypeNameFilter(String resType) throws IllegalArgumentException {
      if (resType != null && resType.length() != 0) {
         return escapeSearchChars("type=" + resType) + "*";
      } else {
         throw new IllegalArgumentException(SecurityLogger.getNoResourceType());
      }
   }

   public static SearchHelper getApplicationSearchHelper(String appName) throws IllegalArgumentException {
      if (appName != null && appName.length() != 0 && !"*".equals(appName)) {
         int wcIndex = indexOf(appName, "*", 0);
         boolean isWildcard = wcIndex >= 0;
         if (isWildcard && wcIndex != appName.length() - 1) {
            throw new IllegalArgumentException(SecurityLogger.getInvalidApplicationSearchName());
         } else {
            String searchData = "type=*, application=" + appName;
            if (isWildcard) {
               return new ResourceSearchHelper(searchData);
            } else {
               searchData = searchData + "*";
               return new ResourceSearchHelper(searchData, "application", unescapeChars(appName));
            }
         }
      } else {
         throw new IllegalArgumentException(SecurityLogger.getInvalidApplicationName());
      }
   }

   public static SearchHelper getComponentSearchHelper(String compName, String compType, String appName) throws IllegalArgumentException {
      if (compName != null && compName.length() != 0 && !"*".equals(compName)) {
         int wcIndex = indexOf(compName, "*", 0);
         boolean isWildcard = wcIndex >= 0;
         if (isWildcard && wcIndex != compName.length() - 1) {
            throw new IllegalArgumentException(SecurityLogger.getInvalidComponentSearchName());
         } else if (compType != null && compType.length() != 0) {
            if (appName != null && appName.length() != 0) {
               String componentType = "type=";
               boolean lowercase = false;
               String componentKey;
               if (compType.equals("EJB")) {
                  componentType = componentType + EJB.getType();
                  componentKey = "module";
               } else if (compType.equals("WebApp")) {
                  componentType = componentType + URL.getType();
                  componentKey = "contextPath";
                  lowercase = URL.mappingToLowerCase();
               } else if (compType.equals("WebService")) {
                  componentType = componentType + WSS.getType();
                  componentKey = "contextPath";
               } else {
                  if (!compType.equals("Connector")) {
                     throw new IllegalArgumentException(SecurityLogger.getInvalidComponentType(compType));
                  }

                  componentType = componentType + EIS.getType();
                  componentKey = "module";
               }

               String theCompName = compName;
               if (lowercase) {
                  theCompName = compName.toLowerCase();
               }

               String searchData = componentType + ", application=" + escapeSearchChars(appName) + ", " + componentKey + "=" + theCompName;
               if (isWildcard) {
                  return new ResourceSearchHelper(searchData);
               } else {
                  searchData = searchData + "*";
                  return new ResourceSearchHelper(searchData, componentKey, unescapeChars(theCompName));
               }
            } else {
               throw new IllegalArgumentException(SecurityLogger.getInvalidApplicationName());
            }
         } else {
            throw new IllegalArgumentException(SecurityLogger.getNoComponentType());
         }
      } else {
         throw new IllegalArgumentException(SecurityLogger.getInvalidComponentName());
      }
   }

   public static SearchHelper getChildSearchHelper(String resourceId) throws IllegalArgumentException {
      getMapFromResourceId(resourceId);
      return new ChildSearchHelper(resourceId);
   }

   public static ResourceIdInfo getResourceIdInfo(String resType) {
      return (ResourceIdInfo)ResourceIdInfoMap.get(resType);
   }

   public static SearchHelper getRepeatingActionsSearchHelper(String resourceId) throws IllegalArgumentException {
      Map resMap = getMapFromResourceId(resourceId);
      String resType = (String)resMap.get("ResourceType");
      ResourceIdInfo info = (ResourceIdInfo)ResourceIdInfoMap.get(resType);
      int actionIndex = info.getSelfDescribingResource().getRepeatingFieldIndex();
      if (actionIndex == -1) {
         return null;
      } else {
         String[] keyNames = info.getKeyNames();
         String actionKey = keyNames[actionIndex];
         return resMap.containsKey(actionKey) ? null : new ChildSearchHelper(resourceId, actionKey);
      }
   }

   public static String getResourceIdNameFilter(String resourceId) throws IllegalArgumentException {
      Map resMap = getMapFromResourceId(resourceId);
      return escapeSearchChars(resourceId);
   }

   public static Resource getScopedResource(String resourceId) throws IllegalArgumentException {
      if (resourceId != null && resourceId.length() != 0) {
         Map resMap = getMapFromResourceId(resourceId);
         return getResourceFromMap(resMap);
      } else {
         return null;
      }
   }

   public static String escapeSearchChars(String searchData) {
      String result = searchData;
      if (searchData != null) {
         int k = 0;
         char[] name = searchData.toCharArray();
         char[] out = new char[name.length * 2];

         for(int i = 0; i < name.length; ++i) {
            if (name[i] == escapeChars[0] || name[i] == escapeChars[1]) {
               out[k++] = '\\';
            }

            out[k++] = name[i];
         }

         if (k != name.length) {
            result = new String(out, 0, k);
         }
      }

      return result;
   }

   public static String unescapeChars(String data) {
      String result = data;
      if (data != null && data.indexOf(92) >= 0) {
         int k = -1;
         char[] name = data.toCharArray();

         for(int i = 0; i < name.length; ++i) {
            if (name[i] == '\\') {
               if (k == -1) {
                  k = i;
               }

               ++i;
               if (i < name.length) {
                  name[k++] = name[i];
               }
            } else if (k >= 0) {
               name[k++] = name[i];
            }
         }

         if (k >= 0) {
            result = new String(name, 0, k);
         }
      }

      return result;
   }

   private static Resource getResourceFromMap(Map resourceMap) {
      if (resourceMap != null && !resourceMap.isEmpty()) {
         Object resType = resourceMap.get("ResourceType");
         if (resType != null && resType instanceof String) {
            ResourceIdInfo info = (ResourceIdInfo)ResourceIdInfoMap.get((String)resType);
            return info == null ? null : info.getResource(resourceMap);
         } else {
            return null;
         }
      } else {
         return null;
      }
   }

   private static String[] getParentResourceIdsFromMap(Map resourceMap) {
      if (resourceMap != null && !resourceMap.isEmpty()) {
         Object resType = resourceMap.get("ResourceType");
         if (resType != null && resType instanceof String) {
            ResourceIdInfo info = (ResourceIdInfo)ResourceIdInfoMap.get((String)resType);
            return info == null ? null : info.getParentResourceIds(resourceMap);
         } else {
            return null;
         }
      } else {
         return null;
      }
   }

   private static int indexOf(String src, String str, int fromIndex) {
      int index;
      for(index = src.indexOf(str, fromIndex); index >= 0 && isEscapedCharAt(src, index); index = src.indexOf(str, index + 1)) {
      }

      return index;
   }

   private static boolean isEscapedCharAt(String str, int index) {
      --index;
      return index >= 0 && str.charAt(index) == '\\' && !isEscapedCharAt(str, index);
   }

   static {
      ResourceIdInfoMap.put(ADM.getType(), new AdminResourceIdCreator(ADM));
      ResourceIdInfoMap.put(APP.getType(), new ApplicationResourceIdCreator(APP));
      ResourceIdInfoMap.put(COM.getType(), new COMResourceIdCreator(COM));
      ResourceIdInfoMap.put(EIS.getType(), new EISResourceIdCreator(EIS));
      ResourceIdInfoMap.put(EJB.getType(), new EJBResourceIdCreator(EJB));
      ResourceIdInfoMap.put(JDBC.getType(), new JDBCResourceIdCreator(JDBC));
      ResourceIdInfoMap.put(JMS.getType(), new JMSResourceIdCreator(JMS));
      ResourceIdInfoMap.put(JMX.getType(), new JMXResourceIdCreator(JMX));
      ResourceIdInfoMap.put(JNDI.getType(), new JNDIResourceIdCreator(JNDI));
      ResourceIdInfoMap.put(SVR.getType(), new ServerResourceIdCreator(SVR));
      ResourceIdInfoMap.put(URL.getType(), new URLResourceIdCreator(URL));
      ResourceIdInfoMap.put(WSS.getType(), new WebServiceResourceIdCreator(WSS));
      ResourceIdInfoMap.put(WC.getType(), new WorkContextResourceIdCreator(WC));
   }

   private static final class WorkContextResourceIdCreator extends ResourceIdCreator {
      protected WorkContextResourceIdCreator(WorkContextResource resource) {
         super(resource);
      }

      public Resource getResource(Map resMap) throws IllegalArgumentException {
         try {
            return new WorkContextResource((String[])((String[])resMap.get(this.keys[0])), (String)resMap.get(this.keys[1]));
         } catch (InvalidParameterException var3) {
            throw new IllegalArgumentException(var3.getMessage());
         } catch (ClassCastException var4) {
            throw new IllegalArgumentException(SecurityLogger.getInvalidDataTypeForResourceKey("WorkContext"));
         }
      }
   }

   private static final class WebServiceResourceIdCreator extends ResourceIdCreator {
      protected WebServiceResourceIdCreator(WebServiceResource resource) {
         super(resource);
      }

      public Resource getResource(Map resMap) throws IllegalArgumentException {
         try {
            return new WebServiceResource((String)resMap.get(this.keys[0]), (String)resMap.get(this.keys[1]), (String)resMap.get(this.keys[2]), (String)resMap.get(this.keys[3]), (String[])((String[])resMap.get(this.keys[4])));
         } catch (InvalidParameterException var3) {
            throw new IllegalArgumentException(var3.getMessage());
         } catch (ClassCastException var4) {
            throw new IllegalArgumentException(SecurityLogger.getInvalidDataTypeForResourceKey("WebService"));
         }
      }
   }

   private static final class URLResourceIdCreator extends ResourceIdCreator {
      protected URLResourceIdCreator(URLResource resource) {
         super(resource);
      }

      public Resource getResource(Map resMap) throws IllegalArgumentException {
         try {
            return new URLResource((String)resMap.get(this.keys[0]), (String)resMap.get(this.keys[1]), (String)resMap.get(this.keys[2]), (String)resMap.get(this.keys[3]), (String)resMap.get(this.keys[4]));
         } catch (InvalidParameterException var3) {
            throw new IllegalArgumentException(var3.getMessage());
         } catch (ClassCastException var4) {
            throw new IllegalArgumentException(SecurityLogger.getInvalidDataTypeForResourceKey("URL"));
         }
      }
   }

   private static final class ServerResourceIdCreator extends ResourceIdCreator {
      protected ServerResourceIdCreator(ServerResource resource) {
         super(resource);
      }

      public Resource getResource(Map resMap) throws IllegalArgumentException {
         try {
            return new ServerResource((String)resMap.get(this.keys[0]), (String)resMap.get(this.keys[1]), (String)resMap.get(this.keys[2]));
         } catch (InvalidParameterException var3) {
            throw new IllegalArgumentException(var3.getMessage());
         } catch (ClassCastException var4) {
            throw new IllegalArgumentException(SecurityLogger.getInvalidDataTypeForResourceKey("Server"));
         }
      }
   }

   private static final class JNDIResourceIdCreator extends ResourceIdCreator {
      protected JNDIResourceIdCreator(JNDIResource resource) {
         super(resource);
      }

      public Resource getResource(Map resMap) throws IllegalArgumentException {
         try {
            return new JNDIResource((String)resMap.get(this.keys[0]), (String[])((String[])resMap.get(this.keys[1])), (String)resMap.get(this.keys[2]));
         } catch (InvalidParameterException var3) {
            throw new IllegalArgumentException(var3.getMessage());
         } catch (ClassCastException var4) {
            throw new IllegalArgumentException(SecurityLogger.getInvalidDataTypeForResourceKey("JNDI"));
         }
      }
   }

   private static final class JMXResourceIdCreator extends ResourceIdCreator {
      protected JMXResourceIdCreator(JMXResource resource) {
         super(resource);
      }

      public Resource getResource(Map resMap) throws IllegalArgumentException {
         try {
            return new JMXResource((String)resMap.get(this.keys[0]), (String)resMap.get(this.keys[1]), (String)resMap.get(this.keys[2]), (String)resMap.get(this.keys[3]));
         } catch (InvalidParameterException var3) {
            throw new IllegalArgumentException(var3.getMessage());
         } catch (ClassCastException var4) {
            throw new IllegalArgumentException(SecurityLogger.getInvalidDataTypeForResourceKey("JMX"));
         }
      }
   }

   private static final class JMSResourceIdCreator extends ResourceIdCreator {
      protected JMSResourceIdCreator(JMSResource resource) {
         super(resource);
      }

      public Resource getResource(Map resMap) throws IllegalArgumentException {
         try {
            return new JMSResource((String)resMap.get(this.keys[0]), (String)resMap.get(this.keys[1]), (String)resMap.get(this.keys[2]), (String)resMap.get(this.keys[3]), (String)resMap.get(this.keys[4]));
         } catch (InvalidParameterException var3) {
            throw new IllegalArgumentException(var3.getMessage());
         } catch (ClassCastException var4) {
            throw new IllegalArgumentException(SecurityLogger.getInvalidDataTypeForResourceKey("JMS"));
         }
      }
   }

   private static final class JDBCResourceIdCreator extends ResourceIdCreator {
      protected JDBCResourceIdCreator(JDBCResource resource) {
         super(resource);
      }

      public Resource getResource(Map resMap) throws IllegalArgumentException {
         try {
            return new JDBCResource((String)resMap.get(this.keys[0]), (String)resMap.get(this.keys[1]), (String)resMap.get(this.keys[2]), (String)resMap.get(this.keys[3]), (String)resMap.get(this.keys[4]));
         } catch (InvalidParameterException var3) {
            throw new IllegalArgumentException(var3.getMessage());
         } catch (ClassCastException var4) {
            throw new IllegalArgumentException(SecurityLogger.getInvalidDataTypeForResourceKey("JDBC"));
         }
      }
   }

   private static final class EJBResourceIdCreator extends ResourceIdCreator {
      protected EJBResourceIdCreator(EJBResource resource) {
         super(resource);
      }

      public Resource getResource(Map resMap) throws IllegalArgumentException {
         try {
            return new EJBResource((String)resMap.get(this.keys[0]), (String)resMap.get(this.keys[1]), (String)resMap.get(this.keys[2]), (String)resMap.get(this.keys[3]), (String)resMap.get(this.keys[4]), (String[])((String[])resMap.get(this.keys[5])));
         } catch (InvalidParameterException var3) {
            throw new IllegalArgumentException(var3.getMessage());
         } catch (ClassCastException var4) {
            throw new IllegalArgumentException(SecurityLogger.getInvalidDataTypeForResourceKey("EJB"));
         }
      }
   }

   private static final class EISResourceIdCreator extends ResourceIdCreator {
      protected EISResourceIdCreator(EISResource resource) {
         super(resource);
      }

      public Resource getResource(Map resMap) throws IllegalArgumentException {
         try {
            return new EISResource((String)resMap.get(this.keys[0]), (String)resMap.get(this.keys[1]), (String)resMap.get(this.keys[2]), (String)resMap.get(this.keys[3]));
         } catch (InvalidParameterException var3) {
            throw new IllegalArgumentException(var3.getMessage());
         } catch (ClassCastException var4) {
            throw new IllegalArgumentException(SecurityLogger.getInvalidDataTypeForResourceKey("EIS"));
         }
      }
   }

   private static final class COMResourceIdCreator extends ResourceIdCreator {
      protected COMResourceIdCreator(COMResource resource) {
         super(resource);
      }

      public Resource getResource(Map resMap) throws IllegalArgumentException {
         try {
            return new COMResource((String)resMap.get(this.keys[0]), (String)resMap.get(this.keys[1]));
         } catch (InvalidParameterException var3) {
            throw new IllegalArgumentException(var3.getMessage());
         } catch (ClassCastException var4) {
            throw new IllegalArgumentException(SecurityLogger.getInvalidDataTypeForResourceKey("COM"));
         }
      }
   }

   private static final class ApplicationResourceIdCreator extends ResourceIdCreator {
      protected ApplicationResourceIdCreator(ApplicationResource resource) {
         super(resource);
      }

      public Resource getResource(Map resMap) throws IllegalArgumentException {
         try {
            return new ApplicationResource((String)resMap.get(this.keys[0]));
         } catch (InvalidParameterException var3) {
            throw new IllegalArgumentException(var3.getMessage());
         } catch (ClassCastException var4) {
            throw new IllegalArgumentException(SecurityLogger.getInvalidDataTypeForResourceKey("Application"));
         }
      }
   }

   private static final class AdminResourceIdCreator extends ResourceIdCreator {
      protected AdminResourceIdCreator(AdminResource resource) {
         super(resource);
      }

      public Resource getResource(Map resMap) throws IllegalArgumentException {
         try {
            return new AdminResource((String)resMap.get(this.keys[0]), (String)resMap.get(this.keys[1]), (String)resMap.get(this.keys[2]));
         } catch (InvalidParameterException var3) {
            throw new IllegalArgumentException(var3.getMessage());
         } catch (ClassCastException var4) {
            throw new IllegalArgumentException(SecurityLogger.getInvalidDataTypeForResourceKey("Admin"));
         }
      }
   }

   private abstract static class ResourceIdCreator implements ResourceIdInfo {
      protected SelfDescribingResourceV2 resource;
      protected String type;
      protected String[] keys;
      protected HashSet keySet;
      protected int repeatingIndex;
      protected int repeatingEndIndex;

      protected ResourceIdCreator(SelfDescribingResourceV2 resource) {
         this.resource = resource;
         this.type = resource.getType();
         this.keys = resource.getKeys();
         this.repeatingIndex = resource.getRepeatingFieldIndex();
         this.repeatingEndIndex = resource.getRepeatingFieldTerminatingIndex();
         this.keySet = new HashSet(this.keys.length + 1);

         for(int i = 0; i < this.keys.length; ++i) {
            this.keySet.add(this.keys[i]);
         }

         this.keySet.add("ResourceType");
      }

      public SelfDescribingResourceV2 getSelfDescribingResource() {
         return this.resource;
      }

      public String[] getKeyNames() {
         return this.keys;
      }

      public void validateKeys(Map resMap) throws IllegalArgumentException {
         if (resMap != null && !resMap.isEmpty()) {
            Object resType = resMap.get("ResourceType");
            if (resType != null && resType instanceof String) {
               if (!this.type.equals((String)resType)) {
                  throw new IllegalArgumentException(SecurityLogger.getExpectedResourceType(this.type));
               } else {
                  Iterator iter = resMap.keySet().iterator();

                  while(iter.hasNext()) {
                     Object key = iter.next();
                     if (!this.keySet.contains(key)) {
                        throw new IllegalArgumentException(SecurityLogger.getUnknownResourceKey(key.toString()));
                     }

                     if (key instanceof String) {
                        int fieldType = this.resource.getFieldType((String)key);
                        if (fieldType == 3) {
                           Object value = resMap.get(key);
                           if (value instanceof String) {
                              resMap.put(key, this.getArrayValue((String)value));
                           }
                        }
                     }
                  }

               }
            } else {
               throw new IllegalArgumentException(SecurityLogger.getNoResourceType());
            }
         } else {
            throw new IllegalArgumentException(SecurityLogger.getNoResourceData());
         }
      }

      public String getResourceId(Map resMap) throws IllegalArgumentException {
         this.validateKeys(resMap);
         Resource res = this.getResource(resMap);
         return res.toString();
      }

      public String[] getParentResourceIds(Map resMap) throws IllegalArgumentException {
         Resource res = this.getResource(resMap);
         Resource parent = res.getParentResource();
         if (parent == null) {
            return new String[0];
         } else {
            ArrayList parentIds;
            for(parentIds = new ArrayList(); parent != null; parent = parent.getParentResource()) {
               parentIds.add(parent.toString());
            }

            return (String[])((String[])parentIds.toArray(new String[parentIds.size()]));
         }
      }

      public abstract Resource getResource(Map var1) throws IllegalArgumentException;

      public Map getMap(String resourceId) throws IllegalArgumentException {
         Map result = new HashMap();
         if (resourceId != null && resourceId.length() != 0) {
            String typeId = "type=" + this.type;
            if (!resourceId.startsWith(typeId)) {
               throw new IllegalArgumentException(SecurityLogger.getExpectedResourceType(this.type));
            } else {
               result.put("ResourceType", this.type);
               int start = typeId.length();
               int end = resourceId.length();
               if (start >= end) {
                  return result;
               } else {
                  int index = ResourceUtils.indexOf(resourceId, ", ", start);
                  if (index == -1) {
                     throw new IllegalArgumentException(SecurityLogger.getNoResourceKeysFound());
                  } else {
                     int valEnd = start;

                     for(int i = 0; i < this.keys.length; ++i) {
                        String keyName = ", " + this.keys[i] + "=";
                        int key = ResourceUtils.indexOf(resourceId, keyName, valEnd);
                        if (key != -1 || !this.resource.isTransitiveField(this.keys[i])) {
                           if (key == -1 && this.repeatingIndex != -1 && i >= this.repeatingEndIndex && i < this.repeatingIndex) {
                              keyName = ", " + this.keys[this.repeatingIndex] + "=";
                              key = ResourceUtils.indexOf(resourceId, keyName, valEnd);
                              i = this.repeatingIndex;
                           }

                           if (key == -1 || key != valEnd) {
                              throw new IllegalArgumentException(SecurityLogger.getExpectedResourceKey(this.keys[i]));
                           }

                           int val = key + keyName.length();
                           if (val >= end) {
                              result.put(this.keys[i], (Object)null);
                              return result;
                           }

                           valEnd = ResourceUtils.indexOf(resourceId, ", ", val);
                           if (valEnd == -1) {
                              valEnd = end;
                           }

                           Object keyVal = null;
                           if (valEnd > val) {
                              keyVal = this.getValueForKey(this.keys[i], resourceId.substring(val, valEnd));
                           }

                           result.put(this.keys[i], keyVal);
                           if (valEnd >= end) {
                              return result;
                           }
                        }
                     }

                     if (valEnd < end) {
                        throw new IllegalArgumentException(SecurityLogger.getUnexpectedResourceIdData(resourceId.substring(valEnd, end)));
                     } else {
                        return result;
                     }
                  }
               }
            }
         } else {
            throw new IllegalArgumentException(SecurityLogger.getNoResourceIdentifier());
         }
      }

      private Object getValueForKey(String key, String value) throws IllegalArgumentException {
         if (value != null && value.length() != 0) {
            int fieldType = this.resource.getFieldType(key);
            return fieldType != 3 ? ResourceUtils.unescapeChars(value) : this.getArrayValue(value);
         } else {
            return null;
         }
      }

      private String[] getArrayValue(String value) throws IllegalArgumentException {
         if (value != null && value.length() != 0) {
            if (value.equals("{}")) {
               return new String[0];
            } else if (value.startsWith("{") && value.endsWith("}")) {
               if (value.charAt(value.length() - 2) == '\\') {
                  throw new IllegalArgumentException(SecurityLogger.getUnexpectedResourceKeyArrayValue(value));
               } else {
                  int start = 1;
                  ArrayList list = new ArrayList();
                  char[] val = value.toCharArray();

                  for(int i = 1; i < val.length - 1; ++i) {
                     if (val[i] == ',' && val[i - 1] != '\\') {
                        String arrayVal = value.substring(start, i);
                        if (arrayVal == null || arrayVal.length() == 0) {
                           throw new IllegalArgumentException(SecurityLogger.getEmptyArrayValueFound());
                        }

                        list.add(arrayVal);
                        start = i + 1;
                     }
                  }

                  String arrayVal = value.substring(start, val.length - 1);
                  if (arrayVal != null && arrayVal.length() != 0) {
                     list.add(arrayVal);
                     String[] arrayValues = (String[])((String[])list.toArray(new String[list.size()]));

                     for(int i = 0; i < arrayValues.length; ++i) {
                        arrayValues[i] = ResourceUtils.unescapeChars(arrayValues[i]);
                     }

                     return arrayValues;
                  } else {
                     throw new IllegalArgumentException(SecurityLogger.getEmptyArrayValueFound());
                  }
               }
            } else {
               throw new IllegalArgumentException(SecurityLogger.getUnexpectedResourceKeyArrayValue(value));
            }
         } else {
            return null;
         }
      }
   }

   private static final class ResourceSearchHelper implements SearchHelper {
      private String nameFilter;
      private String key;
      private String value;

      public ResourceSearchHelper(String nameFilter) {
         this(nameFilter, (String)null, (String)null);
      }

      public ResourceSearchHelper(String nameFilter, String key, String value) {
         this.nameFilter = null;
         this.key = null;
         this.value = null;
         this.nameFilter = nameFilter == null ? "*" : nameFilter;
         this.key = key;
         this.value = value;
      }

      public String getNameFilter() {
         return this.nameFilter;
      }

      public boolean isValid(String resourceId) {
         if (this.key == null) {
            return true;
         } else if (resourceId != null && resourceId.length() != 0) {
            String attrStr = ", " + this.key + '=' + (this.value == null ? "" : this.value);
            int attrStart = ResourceUtils.indexOf(resourceId, attrStr, 0);
            if (attrStart < 0) {
               return false;
            } else {
               int attrEnd = attrStart + attrStr.length();
               return attrEnd == resourceId.length() || resourceId.indexOf(", ", attrEnd) == attrEnd;
            }
         } else {
            return false;
         }
      }

      public String toString() {
         return "ResourceSearchHelper, nameFilter=" + this.nameFilter + ", key=" + this.key + ", value=" + this.value;
      }
   }

   private static final class ChildSearchHelper implements SearchHelper {
      private String childIdStart;

      public ChildSearchHelper(String resourceId) {
         this.childIdStart = resourceId + ", ";
      }

      public ChildSearchHelper(String resourceId, String keyName) {
         this.childIdStart = resourceId + ", " + keyName + "=";
      }

      public String getNameFilter() {
         return ResourceUtils.escapeSearchChars(this.childIdStart) + "*";
      }

      public boolean isValid(String resourceId) {
         return resourceId.startsWith(this.childIdStart) && ResourceUtils.indexOf(resourceId, ", ", this.childIdStart.length() + 1) < 0;
      }

      public String toString() {
         return "ChildSearchHelper, childIdStart=" + this.childIdStart;
      }
   }

   public interface SearchHelper {
      String getNameFilter();

      boolean isValid(String var1);
   }
}
