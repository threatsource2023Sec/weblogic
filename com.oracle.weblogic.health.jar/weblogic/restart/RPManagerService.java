package weblogic.restart;

import java.security.AccessController;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import javax.inject.Named;
import org.jvnet.hk2.annotations.Service;
import weblogic.invocation.ComponentInvocationContext;
import weblogic.invocation.ComponentInvocationContextManager;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;

@Service
@Named
public class RPManagerService implements RPManager {
   private Map allGroups = new ConcurrentHashMap();
   private Map servicesInAllGroup = new ConcurrentHashMap();
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private static final ComponentInvocationContextManager cicManager;

   public void addRPGroup(String name, int secondsBetweenRestarts, int numberOfRestartAttempts) throws RPException {
      ConcurrentHashMap groups = (ConcurrentHashMap)this.findOrCreateGroupsByCIC();
      if (RPDebug.isDebugEnabled()) {
         RPDebug.debug("RPManager create RPGroup: RPGroup=" + name + ", SecondsBetweenRestarts=" + secondsBetweenRestarts + ", NumberOfRestartAttempts=" + numberOfRestartAttempts);
      }

      RPServiceGroup oldGroup = (RPServiceGroup)groups.putIfAbsent(name, new RPServiceGroup(name, secondsBetweenRestarts, numberOfRestartAttempts));
      if (oldGroup != null) {
         if (RPDebug.isDebugEnabled()) {
            RPDebug.debug("RPManager add RPGroup: RPGroup=" + name + " already exsits");
         }

         throw new RPException("RPManager add RPGroup: RPGroup=" + name + " already exsits");
      } else {
         RPServiceGroup newGroup = (RPServiceGroup)groups.get(name);
         Set serviceSet = this.findOrCreateSeviceInAllGroupsByCIC();
         Iterator var8 = serviceSet.iterator();

         while(var8.hasNext()) {
            RPService service = (RPService)var8.next();
            newGroup.addService(service);
         }

      }
   }

   public void removeRPGroup(String name) throws RPException {
      Map groups = this.findOrCreateGroupsByCIC();
      if (groups.remove(name) == null) {
         throw new RPException("RPGroup=" + name + " doesn't exist");
      } else {
         if (RPDebug.isDebugEnabled()) {
            RPDebug.debug("RPManager Remove RPGroup=" + name + " from RPManagerService");
         }

      }
   }

   public void addServiceToGroup(String name, RPService service) throws RPException {
      if (RPDebug.isDebugEnabled()) {
         RPDebug.debug("RPManager addServiceToGroup: RPGroup=" + name + ", serviceName=" + service.getName() + ", serviceOrder=" + service.getOrder());
      }

      if (name == null) {
         this.addSeviceInAllGroups(service);
      } else {
         Map groups = this.findOrCreateGroupsByCIC();
         RPServiceGroup theGroup = (RPServiceGroup)groups.get(name);
         if (theGroup == null) {
            if (RPDebug.isDebugEnabled()) {
               RPDebug.debug("RPManager addServiceToGroup: RPGroup=" + name + " doesnot exsit. Please add Group firstly");
            }

            throw new RPException("RPManager addServiceToGroup: RPGroup=" + name + " doesnot exsit");
         } else {
            theGroup.addService(service);
         }
      }
   }

   public void removeServiceFromGroup(String name, RPService service) {
      if (RPDebug.isDebugEnabled()) {
         RPDebug.debug("RPManager removeServiceFromGroup: RPGroup=" + name + ", serviceName=" + service.getName());
      }

      if (name == null) {
         this.removeSeviceInAllGroups(service);
      } else {
         Map groups = this.findOrCreateGroupsByCIC();
         RPServiceGroup theGroup = (RPServiceGroup)groups.get(name);
         if (theGroup != null) {
            theGroup.removeService(service);
         }

      }
   }

   public Set getServicesFromGroup(String name) throws RPException {
      Map groups = this.findOrCreateGroupsByCIC();
      RPServiceGroup theGroup = (RPServiceGroup)groups.get(name);
      return theGroup == null ? null : theGroup.getServices();
   }

   public Map getGroups() {
      return this.findOrCreateGroupsByCIC();
   }

   private void addSeviceInAllGroups(RPService service) {
      Set serviceSet = this.findOrCreateSeviceInAllGroupsByCIC();
      serviceSet.add(service);
      Iterator var3 = this.findOrCreateGroupsByCIC().values().iterator();

      while(var3.hasNext()) {
         RPServiceGroup group = (RPServiceGroup)var3.next();
         group.addService(service);
      }

   }

   private void removeSeviceInAllGroups(RPService service) {
      Set serviceSet = this.findOrCreateSeviceInAllGroupsByCIC();
      serviceSet.remove(service);
      Iterator var3 = this.findOrCreateGroupsByCIC().values().iterator();

      while(var3.hasNext()) {
         RPServiceGroup group = (RPServiceGroup)var3.next();
         group.removeService(service);
      }

   }

   private Map findOrCreateGroupsByCIC() {
      String partitionName = getPartition(cicManager.getCurrentComponentInvocationContext());
      ((ConcurrentHashMap)this.allGroups).putIfAbsent(partitionName, new ConcurrentHashMap());
      return (Map)this.allGroups.get(partitionName);
   }

   private Set findOrCreateSeviceInAllGroupsByCIC() {
      String partitionName = getPartition(cicManager.getCurrentComponentInvocationContext());
      ((ConcurrentHashMap)this.servicesInAllGroup).putIfAbsent(partitionName, Collections.synchronizedSet(new HashSet()));
      return (Set)this.servicesInAllGroup.get(partitionName);
   }

   static String getPartition(ComponentInvocationContext cic) {
      if (cic == null) {
         return "DOMAIN";
      } else {
         String partitionName = cic.getPartitionName();
         return partitionName == null ? "DOMAIN" : partitionName;
      }
   }

   static {
      cicManager = ComponentInvocationContextManager.getInstance(kernelId);
   }
}
