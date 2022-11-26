package weblogic.management;

import java.beans.BeanInfo;
import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import weblogic.management.configuration.ResourceGroupMBean;
import weblogic.management.configuration.SingleTargetOnly;
import weblogic.management.configuration.TargetMBean;
import weblogic.management.provider.ManagementService;
import weblogic.management.provider.beaninfo.BeanInfoAccess;

public final class ResourceGroupMultitargetingUtils {
   private static final BeanInfoAccess beanInfoAccess = ManagementService.getBeanInfoAccess();

   private ResourceGroupMultitargetingUtils() {
   }

   public static List findBlacklistedResources(ResourceGroupMBean rgMBean) throws IOException, InvocationTargetException, IllegalAccessException {
      assert rgMBean != null;

      BeanInfo beanInfo = getBeanInfo(rgMBean);

      assert beanInfo != null;

      List blackListedResources = new ArrayList();
      PropertyDescriptor[] var3 = beanInfo.getPropertyDescriptors();
      int var4 = var3.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         PropertyDescriptor propDesc = var3[var5];
         Method readMethod = propDesc.getReadMethod();
         Object retObject = null;
         if (readMethod.getReturnType().isArray()) {
            Object array = null;
            array = readMethod.invoke(rgMBean);

            for(int i = 0; i < Array.getLength(array); ++i) {
               retObject = Array.get(array, i);
               if (retObject != null) {
                  blackListedResources = checkAnnotation((List)blackListedResources, retObject);
               }
            }
         }
      }

      return (List)blackListedResources;
   }

   public static String sharePhysicalServer(TargetMBean[] targets, TargetMBean targetToCompare) {
      new HashSet();
      Set serverNamesToCompare = targetToCompare.getServerNames();
      TargetMBean[] var3 = targets;
      int var4 = targets.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         TargetMBean tg = var3[var5];
         if (!tg.getName().equals(targetToCompare.getName())) {
            new HashSet();
            Set sNames = tg.getServerNames();
            if (hasCommon(serverNamesToCompare, sNames)) {
               return tg.getName();
            }
         }
      }

      return null;
   }

   private static List checkAnnotation(List list, Object obj) {
      Class[] interfaces = obj.getClass().getInterfaces();

      for(int j = 0; j < interfaces.length; ++j) {
         if (interfaces[j].getAnnotation(SingleTargetOnly.class) != null) {
            String mb = interfaces[j].getSimpleName().replace("MBean", "");
            if (!list.contains(mb)) {
               list.add(mb);
            }
         }
      }

      return list;
   }

   private static BeanInfo getBeanInfo(Object obj) {
      return beanInfoAccess.getBeanInfoForInstance(obj, false, (String)null);
   }

   private static boolean hasCommon(Set set1, Set set2) {
      Iterator var2 = set1.iterator();

      String str;
      do {
         if (!var2.hasNext()) {
            return false;
         }

         str = (String)var2.next();
      } while(!set2.contains(str));

      return true;
   }
}
