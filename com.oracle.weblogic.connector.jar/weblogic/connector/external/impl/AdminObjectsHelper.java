package weblogic.connector.external.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import weblogic.connector.utils.ArrayUtils;
import weblogic.connector.utils.StringUtils;
import weblogic.j2ee.descriptor.AdminObjectBean;
import weblogic.j2ee.descriptor.ConfigPropertyBean;
import weblogic.j2ee.descriptor.ConnectorBean;

public class AdminObjectsHelper {
   private final AdminObjectBean[] adminObjs;

   public AdminObjectsHelper(ConnectorBean connBean) {
      AdminObjectBean[] adminObjects = connBean.getResourceAdapter().getAdminObjects();
      if (adminObjects != null) {
         this.adminObjs = adminObjects;
      } else {
         this.adminObjs = new AdminObjectBean[0];
      }

   }

   AdminObjectBean getMappingAdminObjectBean(String adminObjInterface, String adminObjClass) {
      List result = this.getMappingAdminObjectBeans(adminObjInterface, adminObjClass);
      return result.size() == 1 ? (AdminObjectBean)result.get(0) : null;
   }

   List getMappingAdminObjectBeans(String adminObjInterface, String adminObjClass) {
      boolean matchClass = StringUtils.notEmpty(adminObjClass);
      boolean matchInterface = StringUtils.notEmpty(adminObjInterface);
      ArrayList result = new ArrayList();
      AdminObjectBean[] var6 = this.adminObjs;
      int var7 = var6.length;

      for(int var8 = 0; var8 < var7; ++var8) {
         AdminObjectBean adminObjInRa = var6[var8];
         if (matchInterface && matchClass) {
            if (adminObjInterface.equals(adminObjInRa.getAdminObjectInterface()) && adminObjClass.equals(adminObjInRa.getAdminObjectClass())) {
               result.add(adminObjInRa);
               break;
            }
         } else if (matchInterface && adminObjInterface.equals(adminObjInRa.getAdminObjectInterface())) {
            result.add(adminObjInRa);
         } else if (matchClass && adminObjClass.equals(adminObjInRa.getAdminObjectClass())) {
            result.add(adminObjInRa);
         }
      }

      return result;
   }

   public static Map toRAPropMap(ConfigPropertyBean[] beans) {
      Map beanMap = new HashMap();
      ArrayUtils.putInMap(beans, new ArrayUtils.KeyLocator() {
         public String getKey(ConfigPropertyBean entry) {
            return entry.getConfigPropertyName();
         }
      }, beanMap);
      return beanMap;
   }

   public ConfigPropertyBean[] getRAAdminProperties(String adminObjInterface, String adminObjClass) {
      AdminObjectBean adminObjectBean = this.getMappingAdminObjectBean(adminObjInterface, adminObjClass);
      return adminObjectBean != null ? adminObjectBean.getConfigProperties() : new ConfigPropertyBean[0];
   }
}
