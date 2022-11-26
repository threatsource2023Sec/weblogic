package weblogic.management.partition.admin;

import java.security.AccessController;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import javax.inject.Singleton;
import org.jvnet.hk2.annotations.Service;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.configuration.PartitionMBean;
import weblogic.management.configuration.TargetMBean;
import weblogic.management.configuration.VirtualTargetMBean;
import weblogic.management.provider.ManagementService;
import weblogic.management.utils.PartitionUtils;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.utils.annotation.Secure;

@Service
@Singleton
@Secure
public class WorkingVirtualTargetManagerImpl implements WorkingVirtualTargetManager {
   private static DebugLogger debugLogger = DebugLogger.getDebugLogger("DebugConfigurationRuntime");
   private ConcurrentMap virtualTargetMBeanMap = new ConcurrentHashMap();
   private ConcurrentMap workingVirtualTargetMBeanMap = new ConcurrentHashMap();
   private static AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());

   public VirtualTargetMBean[] getWorkingVirtualTargets(PartitionMBean partitionMBean) {
      if (debugLogger.isDebugEnabled()) {
         this.debug("get WorkingVirtualTargets for Partition = " + partitionMBean.getName());
      }

      if (this.workingVirtualTargetMBeanMap.containsKey(partitionMBean.getName())) {
         if (debugLogger.isDebugEnabled()) {
            this.debug("Found Working-VT in Map.");
         }

         List workingVirtualTargetMBeans = (List)this.workingVirtualTargetMBeanMap.get(partitionMBean.getName());
         return workingVirtualTargetMBeans != null && workingVirtualTargetMBeans.size() > 0 ? (VirtualTargetMBean[])workingVirtualTargetMBeans.toArray(new VirtualTargetMBean[workingVirtualTargetMBeans.size()]) : null;
      } else {
         if (debugLogger.isDebugEnabled()) {
            this.debug("No Working-VT found. Fallback to use original VT.");
         }

         TargetMBean[] targetMBeans = partitionMBean.getAvailableTargets();
         List workingVirtualTargetMBeans = new ArrayList();
         TargetMBean[] var4 = targetMBeans;
         int var5 = targetMBeans.length;

         for(int var6 = 0; var6 < var5; ++var6) {
            TargetMBean targetMBean = var4[var6];
            if (targetMBean instanceof VirtualTargetMBean) {
               workingVirtualTargetMBeans.add((VirtualTargetMBean)targetMBean);
            }
         }

         return (VirtualTargetMBean[])workingVirtualTargetMBeans.toArray(new VirtualTargetMBean[workingVirtualTargetMBeans.size()]);
      }
   }

   public VirtualTargetMBean lookupWorkingVirtualTarget(VirtualTargetMBean virtualTargetMBean) {
      if (debugLogger.isDebugEnabled()) {
         this.debug("lookupWorkingVirtualTarget for VT = " + virtualTargetMBean.getName());
      }

      if (this.virtualTargetMBeanMap.containsKey(virtualTargetMBean.getName())) {
         if (debugLogger.isDebugEnabled()) {
            this.debug("Found Working-VT in Map.");
         }

         return (VirtualTargetMBean)this.virtualTargetMBeanMap.get(virtualTargetMBean.getName());
      } else {
         if (debugLogger.isDebugEnabled()) {
            this.debug("No Working-VT found. Fallback to use original VT.");
         }

         return virtualTargetMBean;
      }
   }

   public void initialize(String partitionName) {
      if (debugLogger.isDebugEnabled()) {
         this.debug("Initializing Working-VT Map for Partition = " + partitionName);
      }

      DomainMBean domainMBean = ManagementService.getRuntimeAccess(kernelId).getDomain();
      PartitionMBean partitionMBean = domainMBean.lookupPartition(partitionName);
      if (partitionMBean != null) {
         List workingVirtualTargetMBeans = Collections.synchronizedList(new ArrayList());
         this.workingVirtualTargetMBeanMap.put(partitionName, workingVirtualTargetMBeans);
         TargetMBean[] availableVTs = partitionMBean.getAvailableTargets();
         TargetMBean[] var6 = availableVTs;
         int var7 = availableVTs.length;

         for(int var8 = 0; var8 < var7; ++var8) {
            TargetMBean vt = var6[var8];
            if (vt instanceof VirtualTargetMBean) {
               VirtualTargetMBean vtClone = this.getVirtualTargetClone((VirtualTargetMBean)vt, domainMBean);
               if (debugLogger.isDebugEnabled()) {
                  this.debug("initialize()::Adding VT " + vtClone.getName() + "::" + vtClone.getUriPrefix() + " to virtualTargetMBeanMap");
                  this.debug("initialize()::Adding VT " + vtClone.getName() + "::" + vtClone.getUriPrefix() + " to workingVirtualTargets for Partition " + partitionMBean.getName());
               }

               this.virtualTargetMBeanMap.put(vt.getName(), vtClone);
               workingVirtualTargetMBeans.add(vtClone);
            }
         }
      } else if (debugLogger.isDebugEnabled()) {
         this.debug("initialize()::Not found Partition " + partitionName);
      }

      if (debugLogger.isDebugEnabled()) {
         this.debug("[DONE] Initializing Working-VT Map for Partition = " + partitionName);
      }

   }

   private VirtualTargetMBean getVirtualTargetClone(VirtualTargetMBean vtMBean, DomainMBean domainMBean) {
      try {
         VirtualTargetMBean vtClone = (VirtualTargetMBean)this.getClone(vtMBean, vtMBean.getParentBean());
         TargetMBean[] targetClones = new TargetMBean[vtMBean.getTargets().length];
         int i = 0;
         TargetMBean[] var6 = vtMBean.getTargets();
         int var7 = var6.length;

         for(int var8 = 0; var8 < var7; ++var8) {
            TargetMBean tgt = var6[var8];
            targetClones[i++] = (TargetMBean)this.getClone(tgt, domainMBean);
         }

         vtClone.setTargets(targetClones);
         return vtClone;
      } catch (Exception var10) {
         var10.printStackTrace();
         throw new RuntimeException(var10);
      }
   }

   private DescriptorBean getClone(DescriptorBean original, DescriptorBean parent) {
      DescriptorBean clone = this.abstractify(original).clone(this.abstractify(parent));
      return clone;
   }

   public void cleanup(String partitionName) {
      if (debugLogger.isDebugEnabled()) {
         this.debug("Performing cleanup for Partition = " + partitionName);
      }

      DomainMBean domainBean = ManagementService.getRuntimeAccess(kernelId).getDomain();
      PartitionMBean partitionMBean = domainBean.lookupPartition(partitionName);
      if (partitionMBean != null) {
         List workingVirtualTargets = (List)this.workingVirtualTargetMBeanMap.get(partitionMBean.getName());
         if (workingVirtualTargets != null) {
            VirtualTargetMBean vt;
            for(Iterator var5 = workingVirtualTargets.iterator(); var5.hasNext(); this.virtualTargetMBeanMap.remove(vt.getName())) {
               vt = (VirtualTargetMBean)var5.next();
               if (debugLogger.isDebugEnabled()) {
                  this.debug("cleanup()::Removing VT " + vt.getName() + "::" + vt.getUriPrefix() + " from virtualTargetMBeanMap");
               }
            }

            if (debugLogger.isDebugEnabled()) {
               this.debug("cleanup()::Removing all Working-VT for Partition " + partitionMBean.getName());
            }

            this.workingVirtualTargetMBeanMap.remove(partitionMBean.getName());
         }
      } else if (debugLogger.isDebugEnabled()) {
         this.debug("cleanup()::Not found Partition " + partitionName);
      }

      if (debugLogger.isDebugEnabled()) {
         this.debug("[DONE] cleanup for Partition = " + partitionName);
      }

   }

   public void addVirtualTarget(String partitionName, VirtualTargetMBean vt) {
      this.workingVirtualTargetMBeanMap.putIfAbsent(partitionName, Collections.synchronizedList(new ArrayList()));
      List workingVirtualTargetMBeans = (List)this.workingVirtualTargetMBeanMap.get(partitionName);
      VirtualTargetMBean vtClone = this.getVirtualTargetClone(vt, PartitionUtils.getDomain(vt));
      if (debugLogger.isDebugEnabled()) {
         this.debug("addVirtualTarget()::Adding VT " + vtClone.getName() + " to virtualTargetMBeanMap");
         this.debug("addVirtualTarget()::Adding VT " + vtClone.getName() + " to workingVirtualTargets for Partition " + partitionName);
      }

      this.virtualTargetMBeanMap.put(vt.getName(), vtClone);
      workingVirtualTargetMBeans.add(vtClone);
   }

   public void removeVirtualTarget(String partitionName, VirtualTargetMBean vt) {
      VirtualTargetMBean workingVT = (VirtualTargetMBean)this.virtualTargetMBeanMap.get(vt.getName());
      if (workingVT != null) {
         if (debugLogger.isDebugEnabled()) {
            this.debug("removeVirtualTarget()::Removing VT " + workingVT.getName() + " from virtualTargetMBeanMap");
            this.debug("removeVirtualTarget()::Removing VT " + workingVT.getName() + " from workingVirtualTargets for Partition " + partitionName);
         }

         this.virtualTargetMBeanMap.remove(vt.getName());
         List workingVirtualTargetMBeans = (List)this.workingVirtualTargetMBeanMap.get(partitionName);
         if (workingVirtualTargetMBeans != null) {
            workingVirtualTargetMBeans.remove(workingVT);
         }
      }

   }

   private void debug(String msg) {
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug("[DEBUG]::[WorkingVirtualTargetManagerImpl]::" + msg);
      }

   }

   private AbstractDescriptorBean abstractify(DescriptorBean bean) {
      if (bean instanceof AbstractDescriptorBean) {
         return (AbstractDescriptorBean)bean;
      } else {
         throw new RuntimeException("Bean of type " + bean.getClass().getName() + " expected to be AbstractDescriptorBean but was not");
      }
   }
}
