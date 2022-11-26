package weblogic.t3.srvr;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;
import javax.inject.Singleton;
import org.glassfish.hk2.api.ActiveDescriptor;
import org.glassfish.hk2.api.ServiceHandle;
import org.glassfish.hk2.runlevel.Sorter;
import weblogic.diagnostics.debug.DebugLogger;

@Singleton
public class ListServiceSorter implements Sorter, Comparator {
   private static final DebugLogger debugSLCWLDF = DebugLogger.getDebugLogger("DebugServerLifeCycle");
   private static final String ALPHABETIC = "alphabetic";
   private static final String REVERSE = "reverse_alphabetic";
   private static final String RANDOM = "random";
   private static final String UNLISTED_POLICY = System.getProperty("com.oracle.weblogic.debug.unlistedServicePolicy", "alphabetic");
   private final Map hierarchy = new HashMap();
   private final boolean alphabetic;
   private final boolean reverse;
   private final RandomServiceSorter randomService;

   public ListServiceSorter(String filename) throws IOException {
      File file = new File(filename);
      if (file.exists() && file.canRead()) {
         BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));

         try {
            int lcv = 0;

            String line;
            while((line = reader.readLine()) != null) {
               line = line.trim();
               if (!line.isEmpty() && !line.startsWith("#") && !line.startsWith("!") && !line.startsWith("&") && !line.startsWith("\\") && !line.startsWith("@")) {
                  this.hierarchy.put(line, lcv++);
               }
            }
         } finally {
            reader.close();
         }

         if ("alphabetic".equals(UNLISTED_POLICY)) {
            this.alphabetic = true;
            this.reverse = false;
            this.randomService = null;
         } else if ("reverse_alphabetic".equals(UNLISTED_POLICY)) {
            this.alphabetic = true;
            this.reverse = true;
            this.randomService = null;
         } else {
            if (!"random".equals(UNLISTED_POLICY)) {
               throw new IllegalArgumentException("Uknown policy for com.oracle.weblogic.debug.unlistedServicePolicy: " + UNLISTED_POLICY);
            }

            this.alphabetic = false;
            this.reverse = false;
            this.randomService = new RandomServiceSorter();
         }

         if (debugSLCWLDF.isDebugEnabled()) {
            debugSLCWLDF.debug("Hk2 is using a service list " + file.getAbsolutePath() + " with the following policy for services not in the list:" + UNLISTED_POLICY);
         }

      } else {
         throw new IOException("Cannot find or WebLogic Startup Service List file " + file.getAbsolutePath());
      }
   }

   public List sort(List descriptors) {
      TreeSet sortedList = new TreeSet(this);
      if (this.alphabetic) {
         sortedList.addAll(descriptors);
         return new ArrayList(sortedList);
      } else {
         ArrayList forRandomizer = new ArrayList(descriptors.size());
         Iterator var4 = descriptors.iterator();

         while(var4.hasNext()) {
            ServiceHandle handle = (ServiceHandle)var4.next();
            ActiveDescriptor ad = handle.getActiveDescriptor();
            String service = ad.getImplementation();
            if (this.hierarchy.containsKey(service)) {
               sortedList.add(handle);
            } else {
               forRandomizer.add(handle);
            }
         }

         List randomized = this.randomService.sort(forRandomizer);
         ArrayList retVal = new ArrayList(descriptors.size());
         retVal.addAll(sortedList);
         retVal.addAll(randomized);
         return retVal;
      }
   }

   private final int getServiceNumber(String service) {
      Integer serviceNumber = (Integer)this.hierarchy.get(service);
      return serviceNumber == null ? Integer.MAX_VALUE : serviceNumber;
   }

   public int compare(ServiceHandle o1, ServiceHandle o2) {
      ActiveDescriptor ad1 = o1.getActiveDescriptor();
      ActiveDescriptor ad2 = o2.getActiveDescriptor();
      String s1 = ad1.getImplementation();
      String s2 = ad2.getImplementation();
      int c1 = this.getServiceNumber(s1);
      int c2 = this.getServiceNumber(s2);
      if (c1 == c2) {
         return !this.reverse ? ad1.getImplementation().compareTo(ad2.getImplementation()) : ad2.getImplementation().compareTo(ad1.getImplementation());
      } else {
         return c1 - c2;
      }
   }
}
