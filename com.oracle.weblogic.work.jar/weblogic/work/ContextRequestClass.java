package weblogic.work;

import java.security.AccessController;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import weblogic.security.SubjectUtils;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.security.spi.WLSGroup;
import weblogic.security.subject.AbstractSubject;
import weblogic.security.subject.SubjectManager;

final class ContextRequestClass extends ServiceClassSupport {
   private static final AbstractSubject kernelId = (AbstractSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private String name;
   private RequestClass defaultRequestClass;
   private ConcurrentHashMap groupMap;
   private ConcurrentHashMap usernameMap;
   private final SubjectManager subjectManager;

   public ContextRequestClass(String name) {
      this(name, (PartitionFairShare)null);
   }

   public ContextRequestClass(String name, PartitionFairShare partitionFairShare) {
      super(name, partitionFairShare);
      this.groupMap = new ConcurrentHashMap();
      this.usernameMap = new ConcurrentHashMap();
      this.subjectManager = SubjectManager.getSubjectManager();
      this.name = name;
      this.defaultRequestClass = new FairShareRequestClass(name, partitionFairShare);
   }

   public void addUser(String userName, RequestClass requestClass) {
      this.usernameMap.put(userName, requestClass);
   }

   public void addGroup(String groupName, RequestClass requestClass) {
      this.groupMap.put(groupName, requestClass);
   }

   public RequestClass getEffective(AuthenticatedSubject sub) {
      AuthenticatedSubject subject = sub;
      if (sub == null) {
         subject = (AuthenticatedSubject)this.subjectManager.getCurrentSubject(kernelId);
      }

      if (subject == null) {
         return this.defaultRequestClass;
      } else {
         RequestClass requestClass = (RequestClass)this.usernameMap.get(SubjectUtils.getUsername(subject));
         if (requestClass != null) {
            return requestClass;
         } else {
            Set principals = subject.getPrincipals();
            if (principals == null) {
               return this.defaultRequestClass;
            } else {
               Iterator i = principals.iterator();

               while(i.hasNext()) {
                  Object principal = i.next();
                  if (principal instanceof WLSGroup) {
                     requestClass = (RequestClass)this.groupMap.get(((WLSGroup)principal).getName());
                     if (requestClass != null) {
                        return requestClass;
                     }
                  }
               }

               requestClass = (RequestClass)this.groupMap.get("everyone");
               if (requestClass != null) {
                  return requestClass;
               } else {
                  return this.defaultRequestClass;
               }
            }
         }
      }
   }

   public String getName() {
      return this.name;
   }

   public void timeElapsed(long elapsedTime, ServiceClassesStats stats) {
   }

   public void cleanup() {
      super.cleanup();
      this.defaultRequestClass.cleanup();
   }

   ServiceClassSupport createCopy(PartitionFairShare partitionFairShare) {
      ContextRequestClass copy = new ContextRequestClass(this.name, partitionFairShare);

      Iterator var3;
      Map.Entry entry;
      Object rc;
      for(var3 = this.usernameMap.entrySet().iterator(); var3.hasNext(); copy.addUser((String)entry.getKey(), (RequestClass)rc)) {
         entry = (Map.Entry)var3.next();
         rc = (RequestClass)entry.getValue();
         if (rc instanceof ServiceClassSupport) {
            rc = ((ServiceClassSupport)rc).createCopy(partitionFairShare);
         }
      }

      for(var3 = this.groupMap.entrySet().iterator(); var3.hasNext(); copy.addGroup((String)entry.getKey(), (RequestClass)rc)) {
         entry = (Map.Entry)var3.next();
         rc = (RequestClass)entry.getValue();
         if (rc instanceof ServiceClassSupport) {
            rc = ((ServiceClassSupport)rc).createCopy(partitionFairShare);
         }
      }

      return copy;
   }

   RequestClass getDefaultRequestClass() {
      return this.defaultRequestClass;
   }

   Map getUsernameMap() {
      return this.usernameMap;
   }

   Map getGroupMap() {
      return this.groupMap;
   }
}
