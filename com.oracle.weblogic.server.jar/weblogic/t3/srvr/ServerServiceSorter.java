package weblogic.t3.srvr;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.TreeSet;
import javax.inject.Inject;
import org.glassfish.hk2.api.ActiveDescriptor;
import org.glassfish.hk2.api.ServiceHandle;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.runlevel.Sorter;
import org.jvnet.hk2.annotations.Service;

@Service
public class ServerServiceSorter implements Sorter {
   private static final int BEFORE = -1;
   private static final int EQUAL = 0;
   private static final int AFTER = 1;
   private final HandleComparator comparator;

   @Inject
   private ServerServiceSorter(ServiceLocator locator) {
      this.comparator = new HandleComparator(locator);
   }

   public List sort(List arg0) {
      TreeSet sorter = new TreeSet(this.comparator);
      sorter.addAll(arg0);
      ArrayList retVal = new ArrayList(sorter);
      return retVal;
   }

   private static class HandleComparator implements Comparator {
      private final ServiceLocator locator;

      private HandleComparator(ServiceLocator locator) {
         this.locator = locator;
      }

      public int compare(ServiceHandle o1, ServiceHandle o2) {
         ActiveDescriptor a1 = o1.getActiveDescriptor();
         ActiveDescriptor a2 = o2.getActiveDescriptor();
         if (a1.getRanking() > a2.getRanking()) {
            return -1;
         } else if (a1.getRanking() < a2.getRanking()) {
            return 1;
         } else {
            if (!a1.isReified()) {
               a1 = this.locator.reifyDescriptor(a1);
            }

            if (!a2.isReified()) {
               a2 = this.locator.reifyDescriptor(a2);
            }

            if (a1.getInjectees().size() < a2.getInjectees().size()) {
               return -1;
            } else if (a1.getInjectees().size() > a2.getInjectees().size()) {
               return 1;
            } else if (a1.getLocatorId() > a2.getLocatorId()) {
               return -1;
            } else if (a1.getLocatorId() < a2.getLocatorId()) {
               return 1;
            } else if (a1.getServiceId() < a2.getServiceId()) {
               return -1;
            } else {
               return a1.getServiceId() > a2.getServiceId() ? 1 : 0;
            }
         }
      }

      // $FF: synthetic method
      HandleComparator(ServiceLocator x0, Object x1) {
         this(x0);
      }
   }
}
