package weblogic.management.configuration;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public final class JNDIValidator {
   public static void validateJNDINames(RMCFactoryMBean[] mbeanList) throws IllegalArgumentException {
      if (mbeanList != null && mbeanList.length > 1) {
         RMCFactoryMBean[] var1 = mbeanList;
         int var2 = mbeanList.length;

         for(int var3 = 0; var3 < var2; ++var3) {
            RMCFactoryMBean jndiMBean = var1[var3];
            validateJNDIName(jndiMBean, mbeanList);
         }
      }

   }

   public static void validateJNDIName(RMCFactoryMBean jndiMBean, RMCFactoryMBean[] mbeanList) throws IllegalArgumentException {
      if (shouldCompare(jndiMBean)) {
         RMCFactoryMBean[] var2 = mbeanList;
         int var3 = mbeanList.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            RMCFactoryMBean mbeanSibling = var2[var4];
            if (hasDuplicateJNDIName(jndiMBean, mbeanSibling)) {
               String sameTarget = getSameTarget(jndiMBean, mbeanSibling);
               if (sameTarget != null) {
                  throw new IllegalArgumentException(ManagementConfigValidatorsTextFormatter.getInstance().getJndiNameConflictMsg(beanType(jndiMBean), jndiMBean.getJNDIName(), sameTarget));
               }
            }
         }
      }

   }

   private static String[] getCompressedServers(DeploymentMBean targetsMBean) {
      return getCompressedServers(targetsMBean.getTargets());
   }

   private static boolean shouldCompare(RMCFactoryMBean hasTestable) {
      String jndiName = hasTestable.getJNDIName();
      TargetMBean[] targets = hasTestable.getTargets();
      return hasTestable.getName() != null && jndiName != null && jndiName.length() > 0 && targets != null && targets.length > 0;
   }

   private static String getSameTarget(RMCFactoryMBean serverMBean, RMCFactoryMBean siblingMBean) {
      String[] serverTargets = getCompressedServers(serverMBean.getTargets());
      String[] siblingTargets = getCompressedServers(siblingMBean.getTargets());
      String sameTarget = null;
      if (hasServers(serverTargets) && hasServers(siblingTargets)) {
         String[] var5 = serverTargets;
         int var6 = serverTargets.length;

         for(int var7 = 0; var7 < var6; ++var7) {
            String serverTarget = var5[var7];
            String[] var9 = siblingTargets;
            int var10 = siblingTargets.length;

            for(int var11 = 0; var11 < var10; ++var11) {
               String siblingTarget = var9[var11];
               if (serverTarget.equals(siblingTarget)) {
                  sameTarget = serverTarget;
                  break;
               }
            }
         }
      }

      return sameTarget;
   }

   private static boolean hasDuplicateJNDIName(RMCFactoryMBean testable, RMCFactoryMBean sibling) {
      return !testable.getName().equals(sibling.getName()) && testable.getJNDIName().equals(sibling.getJNDIName());
   }

   private static String[] getCompressedServers(TargetMBean[] targets) {
      String[] result = null;
      if (targets != null) {
         Set serverNameSet = new HashSet();
         TargetMBean[] var3 = targets;
         int var4 = targets.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            TargetMBean target = var3[var5];
            Iterator var7 = target.getServerNames().iterator();

            while(var7.hasNext()) {
               Object serverName = var7.next();
               if (serverName instanceof String) {
                  serverNameSet.add((String)serverName);
               }
            }
         }

         result = (String[])serverNameSet.toArray(new String[serverNameSet.size()]);
      }

      return result;
   }

   private static boolean hasServers(String[] serverSet) {
      return serverSet != null && serverSet.length > 0;
   }

   private static String beanType(RMCFactoryMBean testable) {
      String mbeanType = testable.getClass().getSimpleName();
      int endIdx = mbeanType.lastIndexOf("MBean");
      if (endIdx >= 0) {
         mbeanType = mbeanType.substring(0, endIdx);
      }

      return mbeanType;
   }
}
