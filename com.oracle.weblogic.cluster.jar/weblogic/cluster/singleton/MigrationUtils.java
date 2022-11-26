package weblogic.cluster.singleton;

import java.security.AccessController;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import weblogic.management.provider.ManagementService;
import weblogic.management.runtime.RuntimeMBeanDelegate;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;

class MigrationUtils {
   private static final int UNLIMITED = -1;
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());

   static int getServerMigrationHistorySize() {
      int size = -1;

      try {
         size = ManagementService.getRuntimeAccess(kernelId).getDomain().getServerMigrationHistorySize();
      } catch (Exception var2) {
      }

      return size;
   }

   static int getServiceMigrationHistorySize() {
      int size = -1;

      try {
         size = ManagementService.getRuntimeAccess(kernelId).getDomain().getServiceMigrationHistorySize();
      } catch (Exception var2) {
      }

      return size;
   }

   static List createServerMigrationHistoryList() {
      int historySize = getServerMigrationHistorySize();
      return (List)(historySize == -1 ? new ArrayList() : new LimitedList(historySize) {
         protected boolean isRemovable(MigrationDataRuntimeMBeanImpl mbean) {
            return mbean.getStatus() != 1;
         }
      });
   }

   static List createServiceMigrationHistoryList() {
      int historySize = getServiceMigrationHistorySize();
      return (List)(historySize == -1 ? new ArrayList() : new LimitedList(historySize) {
         protected boolean isRemovable(ServiceMigrationDataRuntimeMBeanImpl mbean) {
            return mbean.getStatus() != 1;
         }
      });
   }

   private abstract static class LimitedList extends LinkedList {
      private final int limit;

      public LimitedList(int limit) {
         this.limit = limit;
      }

      public boolean add(Object e) {
         boolean res = super.add(e);
         this.adjustSize();
         return res;
      }

      public void add(int index, Object e) {
         throw new UnsupportedOperationException();
      }

      public boolean addAll(Collection c) {
         Object[] a = (Object[])c.toArray();
         int collLength = a.length;
         if (collLength == 0) {
            return false;
         } else {
            Object[] var4 = a;
            int var5 = a.length;

            for(int var6 = 0; var6 < var5; ++var6) {
               Object e = var4[var6];
               this.add(e);
            }

            return true;
         }
      }

      public boolean addAll(int i, Collection c) {
         throw new UnsupportedOperationException();
      }

      public int size() {
         return this.adjustSize();
      }

      private int adjustSize() {
         int curSize = super.size();
         if (curSize > this.limit) {
            Iterator iter = super.iterator();

            while(curSize > this.limit && iter.hasNext()) {
               Object mbean = iter.next();
               if (this.isRemovable(mbean)) {
                  try {
                     ((RuntimeMBeanDelegate)mbean).unregister();
                  } catch (Exception var5) {
                     if (MigrationDebugLogger.isDebugEnabled()) {
                        MigrationDebugLogger.debug("An exception occurred while unregistering RuntimeMBean : " + var5, var5);
                     }
                  }

                  iter.remove();
                  --curSize;
               }
            }
         }

         return curSize;
      }

      protected abstract boolean isRemovable(Object var1);
   }
}
