package weblogic.cacheprovider.coherence.security;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import weblogic.management.security.ResourceIdInfo;
import weblogic.security.SecurityLogger;
import weblogic.security.service.InvalidParameterException;
import weblogic.security.service.ResourceBase;
import weblogic.security.spi.Resource;
import weblogic.security.spi.SelfDescribingResourceV2;

public final class GridResource extends ResourceBase {
   public static final String CACHE = "cache";
   public static final String SERVICE = "service";
   public static final String JOIN = "join";
   public static final String CREATE = "create";
   public static final String DESTROY = "destroy";
   private static final String[] KEYS = new String[]{"cluster", "category", "name", "action"};
   private static final long serialVersionUID = 1L;
   private static final GridResource GRID = new GridResource((String)null, (String)null, (String)null, (String)null);
   private static final GridResourceIdCreator GRID_RES_CREATOR;

   public GridResource(String cluster, String category, String name, String action) {
      this.init(new String[]{cluster, category, name, action}, 0L);
   }

   private GridResource(String[] values, int length) {
      this.init(values, length, 0L);
   }

   public String getType() {
      return "<grid>";
   }

   public String[] getKeys() {
      return KEYS;
   }

   protected Resource makeParent() {
      return this.length > 0 ? new GridResource(this.values, this.length - 1) : null;
   }

   public String getClusterName() {
      return this.length > 0 ? this.values[0] : null;
   }

   public String getCategoryName() {
      return this.length > 1 ? this.values[1] : null;
   }

   public String getResourceName() {
      return this.length > 2 ? this.values[2] : null;
   }

   public String getActionName() {
      return this.length > 3 ? this.values[3] : null;
   }

   public static ResourceIdInfo getResourceIdInfo() {
      return GRID_RES_CREATOR;
   }

   static {
      GRID_RES_CREATOR = new GridResourceIdCreator(GRID);
   }

   private abstract static class ResourceIdCreator implements ResourceIdInfo {
      private static final String RESOURCE_TYPE = "ResourceType";
      private static final char ESCAPE_CHAR = '\\';
      private static final String KEY_SEPARATOR = ", ";
      private static final char ARRAY_SEPARATOR = ',';
      private static final String EMPTY_ARRAY_LIST = "{}";
      private static final String ARRAY_BEGIN = "{";
      private static final String ARRAY_END = "}";
      private static final String TYPE_ID = "type=";
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
                  int index = indexOf(resourceId, ", ", start);
                  if (index == -1) {
                     throw new IllegalArgumentException(SecurityLogger.getNoResourceKeysFound());
                  } else {
                     int valEnd = start;

                     for(int i = 0; i < this.keys.length; ++i) {
                        String keyName = ", " + this.keys[i] + "=";
                        int key = indexOf(resourceId, keyName, valEnd);
                        if (key != -1 || !this.resource.isTransitiveField(this.keys[i])) {
                           if (key == -1 && this.repeatingIndex != -1 && i >= this.repeatingEndIndex && i < this.repeatingIndex) {
                              keyName = ", " + this.keys[this.repeatingIndex] + "=";
                              key = indexOf(resourceId, keyName, valEnd);
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

                           valEnd = indexOf(resourceId, ", ", val);
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
            return fieldType != 3 ? unescapeChars(value) : this.getArrayValue(value);
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
                        arrayValues[i] = unescapeChars(arrayValues[i]);
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

      private static String unescapeChars(String data) {
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
   }

   private static final class GridResourceIdCreator extends ResourceIdCreator {
      protected GridResourceIdCreator(GridResource resource) {
         super(resource);
      }

      public Resource getResource(Map resMap) throws IllegalArgumentException {
         try {
            return new GridResource((String)resMap.get(this.keys[0]), (String)resMap.get(this.keys[1]), (String)resMap.get(this.keys[2]), (String)resMap.get(this.keys[3]));
         } catch (InvalidParameterException var3) {
            throw new IllegalArgumentException(var3.getMessage());
         } catch (ClassCastException var4) {
            throw new IllegalArgumentException(SecurityLogger.getInvalidDataTypeForResourceKey("Grid"));
         }
      }
   }
}
