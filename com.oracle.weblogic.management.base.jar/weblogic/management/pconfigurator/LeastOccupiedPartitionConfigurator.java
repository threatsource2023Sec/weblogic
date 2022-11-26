package weblogic.management.pconfigurator;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import org.jvnet.hk2.annotations.Service;
import weblogic.descriptor.DescriptorMacroSubstitutor;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.management.configuration.ClusterMBean;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.configuration.LeastOccupiedPartitionConfiguratorMBean;
import weblogic.management.configuration.PartitionMBean;
import weblogic.management.configuration.TargetMBean;
import weblogic.management.provider.internal.DescriptorBeanCount;
import weblogic.management.provider.internal.PartitionMacroSubstitutor;
import weblogic.management.provider.internal.PartitionTemplateException;

@Service(
   name = "least-occupied-target"
)
public class LeastOccupiedPartitionConfigurator implements PartitionConfigurator {
   public static final String SERVICE_NAME = "least-occupied-target";
   private static final DescriptorMacroSubstitutor MACRO_SUBSTITUTOR = new PartitionMacroSubstitutor();
   private static final DebugLogger DEBUG_LOGGER = DebugLogger.getDebugLogger("DebugConfigurationRuntime");

   private DomainMBean getDomain(LeastOccupiedPartitionConfiguratorMBean config) {
      return (DomainMBean)config.getParentBean().getParentBean();
   }

   protected static boolean matchTags(boolean matchAll, String[] tags, String[] refs) {
      List refList = Arrays.asList(refs);
      boolean res = true;
      if (!matchAll) {
         res = false;
      }

      String[] var5 = tags;
      int var6 = tags.length;

      for(int var7 = 0; var7 < var6; ++var7) {
         String tag = var5[var7];
         boolean curMatch = refList.contains(tag);
         if (matchAll) {
            res = res && curMatch;
         } else {
            res = res || curMatch;
         }
      }

      return res;
   }

   protected String performMacroSubstitution(PartitionMBean partition, String macro) {
      if (partition == null) {
         throw new IllegalArgumentException("partition cannot be null.");
      } else {
         return MACRO_SUBSTITUTOR.performMacroSubstitution(macro, partition);
      }
   }

   public TargetMBean[] selectTargets(LeastOccupiedPartitionConfiguratorMBean config) {
      String[] tags = config.getQueryTags();
      boolean matchAll = config.isMustContainAllQueryTags();
      if (tags != null && tags.length != 0) {
         DomainMBean domain = this.getDomain(config);
         if (domain.getClusters() != null && domain.getClusters().length != 0) {
            List count = new LinkedList();
            ClusterMBean[] var6 = this.getDomain(config).getClusters();
            int var7 = var6.length;

            for(int var8 = 0; var8 < var7; ++var8) {
               ClusterMBean c = var6[var8];
               if (matchTags(matchAll, tags, c.getTags())) {
                  count.add(new DescriptorBeanCount(c, 0));
               }
            }

            Collections.sort(count, new Comparator() {
               public int compare(DescriptorBeanCount o1, DescriptorBeanCount o2) {
                  return o1.getCount() - o2.getCount();
               }
            });
            TargetMBean[] targets = null;
            if (!count.isEmpty()) {
               targets = new TargetMBean[]{(ClusterMBean)((DescriptorBeanCount)count.get(0)).getCounted()};
            }

            return targets;
         } else {
            debug("skipping target selection as there is no cluster in the domain");
            return null;
         }
      } else {
         throw new IllegalArgumentException("no query tags provided");
      }
   }

   public void configure(PartitionMBean partition, LeastOccupiedPartitionConfiguratorMBean config) throws PartitionTemplateException {
      if (config == null) {
         throw new IllegalArgumentException("the supplied " + LeastOccupiedPartitionConfiguratorMBean.class.getSimpleName() + " is null");
      } else if (partition == null) {
         throw new IllegalArgumentException("The supplied partition is null");
      }
   }

   public Class getPartitionConfiguratorMBeanIface() {
      return LeastOccupiedPartitionConfiguratorMBean.class;
   }

   private static void debug(String msg) {
      if (DEBUG_LOGGER.isDebugEnabled()) {
         DEBUG_LOGGER.debug(LeastOccupiedPartitionConfigurator.class.getSimpleName() + " - " + msg);
      }

   }
}
