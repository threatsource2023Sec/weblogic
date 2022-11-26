package weblogic.security.utils;

import java.util.ArrayList;
import java.util.HashSet;
import weblogic.invocation.PartitionTable;
import weblogic.security.service.ContextElement;
import weblogic.security.service.ContextHandler;

public final class ResourceIDDContextWrapper implements ContextHandler {
   private static HashSet keys = new HashSet();
   boolean isResourceIddSet = false;
   private ContextHandler delegate;
   private String adminIdd;
   private String resourceIdd;

   public ResourceIDDContextWrapper() {
      this.initialize((ContextHandler)null, true);
   }

   public ResourceIDDContextWrapper(ContextHandler delegate) {
      this.initialize(delegate, true);
   }

   public ResourceIDDContextWrapper(boolean useAdminIdentityDomain) {
      if (!useAdminIdentityDomain) {
         this.initialize((ContextHandler)null, true);
      } else {
         this.initialize((ContextHandler)null, false);
         this.setResourcePartition((String)null);
      }

   }

   public ResourceIDDContextWrapper(ContextHandler delegate, boolean useComponentInvocationContext) {
      this.initialize(delegate, useComponentInvocationContext);
   }

   private void initialize(ContextHandler delegate, boolean useCIC) {
      this.delegate = delegate;
      this.adminIdd = PartitionUtils.getAdminIdentityDomain();
      if (useCIC) {
         this.resourceIdd = PartitionUtils.getCurrentIdentityDomain();
         this.isResourceIddSet = true;
      }

   }

   public synchronized void setResourcePartition(String partitionName) {
      if (this.isResourceIddSet) {
         throw new IllegalStateException("Resource owner is already established");
      } else {
         if (partitionName != null) {
            this.resourceIdd = PartitionUtils.getPrimaryIdentityDomain(partitionName);
            if (this.resourceIdd == null && partitionName.equals(PartitionTable.getInstance().getGlobalPartitionName())) {
               this.resourceIdd = PartitionUtils.getAdminIdentityDomain();
            }
         } else {
            this.resourceIdd = PartitionUtils.getAdminIdentityDomain();
         }

         this.isResourceIddSet = true;
      }
   }

   public synchronized void setResourceIdentityDomain(String resourceIdentityDomain) {
      if (this.isResourceIddSet) {
         throw new IllegalStateException("Resource owner is already established");
      } else {
         this.resourceIdd = resourceIdentityDomain;
         this.isResourceIddSet = true;
      }
   }

   public synchronized boolean isResourceIdentityDomainSet() {
      return this.isResourceIddSet;
   }

   public int size() {
      int combinedSize = keys.size();
      if (this.delegate != null) {
         combinedSize += this.delegate.size();
      }

      return combinedSize;
   }

   public String[] getNames() {
      String[] combinedNames = null;
      String[] delegateNames = null;
      String[] wrapperNames = (String[])keys.toArray(new String[0]);
      if (this.delegate != null) {
         delegateNames = this.delegate.getNames();
      }

      if (delegateNames != null && delegateNames.length > 0) {
         int wrapperLength = wrapperNames.length;
         int delegateLength = delegateNames.length;
         combinedNames = new String[wrapperLength + delegateLength];
         System.arraycopy(wrapperNames, 0, combinedNames, 0, wrapperLength);
         System.arraycopy(delegateNames, 0, combinedNames, wrapperLength, delegateLength);
      } else {
         combinedNames = wrapperNames;
      }

      return combinedNames;
   }

   public Object getValue(String name) {
      if (keys.contains(name)) {
         switch (name) {
            case "com.oracle.contextelement.security.ResourceIdentityDomain":
               if (!this.isResourceIddSet) {
                  throw new IllegalStateException("Resource Identity Domain not available");
               }

               return this.resourceIdd;
            case "com.oracle.contextelement.security.AdminIdentityDomain":
               return this.adminIdd;
            default:
               return null;
         }
      } else {
         return this.delegate != null ? this.delegate.getValue(name) : null;
      }
   }

   public ContextElement[] getValues(String[] names) {
      ArrayList values = new ArrayList();
      if (names != null && names.length > 0) {
         String[] var3 = names;
         int var4 = names.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            String name = var3[var5];
            Object value = this.getValue(name);
            if (value != null) {
               values.add(new ContextElement(name, value));
            }
         }
      }

      return (ContextElement[])values.toArray(new ContextElement[values.size()]);
   }

   static {
      keys.add("com.oracle.contextelement.security.ResourceIdentityDomain");
      keys.add("com.oracle.contextelement.security.AdminIdentityDomain");
   }
}
