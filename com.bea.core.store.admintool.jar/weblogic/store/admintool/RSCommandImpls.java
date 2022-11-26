package weblogic.store.admintool;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.regex.Pattern;
import weblogic.store.io.file.ReplicatedStoreAdmin;

class RSCommandImpls {
   private static SimpleDateFormat dtfmt = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss a z");
   private static SimpleDateFormat hhfmt = new SimpleDateFormat("HHHHH:mm:ss");
   private static DecimalFormat indexFormatter = new DecimalFormat("###");
   private static DecimalFormat replicaFormatter = new DecimalFormat("#####");
   private static DecimalFormat memoryFormatter = new DecimalFormat("######");
   private static DecimalFormat percentageFormatter = new DecimalFormat("##.##");

   static void attachShutdown(StoreAdmin sa, int index, boolean force, boolean safe) throws IOException {
      Interpreter ip = sa.getInterpreter();
      if (sa.getAdminIF().rsAttachToDaemon(index) > 1) {
         throw new IOException("Failed to attach to daemon " + index);
      } else if (sa.getAdminIF().rsShutdownDaemon(index, force, safe) > 1) {
         throw new IOException("Failed to shutdown daemon " + index);
      }
   }

   private static void listRegionInfo(Interpreter ip, HashMap regions, String[] names, String[] daemons, String sortBy, String format) {
      if (regions.isEmpty()) {
         ip.info("There are no Regions exist that matched the list options");
      } else {
         new HashMap();
         HashMap filteredRegions1;
         if (daemons != null) {
            filteredRegions1 = filterRegionByDaemon(ip, daemons, regions);
            if (filteredRegions1.isEmpty()) {
               ip.info("There are no Regions exist on the daemons specified");
               return;
            }
         } else {
            filteredRegions1 = regions;
         }

         HashMap filteredRegions2 = filterRegionByName(names, filteredRegions1);
         if (regions.isEmpty()) {
            ip.info("There are no Regions exist that matched the list options");
         } else if (filteredRegions2.isEmpty()) {
            ip.info("There are no Regions exist that matched the list options");
         } else {
            listRegionInfo(ip, filteredRegions2, sortBy, format);
         }
      }
   }

   private static void deleteRegionInfo(StoreAdmin sa, HashMap regions, String[] names, boolean force) {
      Interpreter ip = sa.getInterpreter();
      if (regions.isEmpty()) {
         ip.info("There are no Regions exist that matched the rmr options");
      } else {
         HashMap filteredRegions1 = filterRegionByName(names, regions);
         if (filteredRegions1.isEmpty()) {
            ip.info("There are no Regions exist that matched the rmr options");
         } else {
            ArrayList skipped = new ArrayList();
            Iterator it = filteredRegions1.keySet().iterator();

            while(true) {
               while(it.hasNext()) {
                  String regionName = (String)it.next();
                  ReplicatedStoreAdmin.RegionInfo r = (ReplicatedStoreAdmin.RegionInfo)filteredRegions1.get(regionName);
                  if (!force && r.isOpen()) {
                     skipped.add(r);
                  } else {
                     try {
                        if (sa.getAdminIF().rsDeleteRegion(regionName, force) > 1) {
                           ip.error("Failed to delete region " + regionName);
                        }
                     } catch (IOException var11) {
                        ip.error("Failed to delete region " + regionName, var11);
                     }
                  }
               }

               if (!skipped.isEmpty()) {
                  ip.warn("Skipped deleting " + skipped.size() + " open regions.\n      You can use the \"-force\" parameter to force the delete of open regions.");
               }

               return;
            }
         }
      }
   }

   private static HashMap filterRegionByName(String[] names, HashMap riMap) {
      HashMap filteredMap = new HashMap();
      if (names != null && names.length != 0 && !names[0].equals("*")) {
         label58:
         for(int i = 0; i < names.length; ++i) {
            String name = names[i];
            boolean useStartsWith = false;
            if (name.charAt(0) != '*') {
               useStartsWith = true;
            }

            String[] parts = name.trim().toUpperCase().split("\\*");
            Iterator it = riMap.keySet().iterator();

            while(true) {
               String rgnName;
               String tmpName;
               do {
                  if (!it.hasNext()) {
                     continue label58;
                  }

                  rgnName = tmpName = (String)it.next();
               } while(riMap.get(rgnName) == null);

               boolean isMatch = false;

               for(int j = 0; j < parts.length; ++j) {
                  if (j == 0 && useStartsWith && !tmpName.startsWith(parts[j])) {
                     isMatch = false;
                     break;
                  }

                  int index = true;
                  int index;
                  if ((index = tmpName.indexOf(parts[j])) == -1) {
                     isMatch = false;
                     break;
                  }

                  isMatch = true;
                  tmpName = tmpName.substring(index + parts[j].length());
               }

               if (isMatch) {
                  filteredMap.put(rgnName, (ReplicatedStoreAdmin.RegionInfo)riMap.get(rgnName));
               }
            }
         }
      } else {
         filteredMap = riMap;
      }

      return filteredMap;
   }

   private static HashMap filterRegionByDaemon(Interpreter ip, String[] daemons, HashMap riMap) {
      HashMap filteredMap = new HashMap();

      for(int i = 0; i < daemons.length; ++i) {
         int daemonId = -1;

         try {
            daemonId = Integer.parseInt(daemons[i]);
         } catch (NumberFormatException var13) {
            ip.error("Invalid daemon index: " + daemons[i]);
         }

         String did = String.format("%03d", daemonId);
         List lRegions = new ArrayList(riMap.values());
         ListIterator it = lRegions.listIterator();

         while(it.hasNext()) {
            ReplicatedStoreAdmin.RegionInfo ri = (ReplicatedStoreAdmin.RegionInfo)it.next();
            String nodeList = parseNodes(ri.getNodes());
            String[] nodes = nodeList.split(" ");

            for(int j = 0; j < nodes.length; ++j) {
               if (did.equals(nodes[j])) {
                  filteredMap.put(ri.getName(), ri);
               }
            }
         }
      }

      return filteredMap;
   }

   private static void listRegionInfo(Interpreter ip, HashMap filtered, String sortBy, String format) {
      List list;
      if (sortBy.equals("name")) {
         list = sortRegionsByName(filtered);
      } else if (sortBy.equals("time")) {
         list = sortRegionsByAccessTime(filtered);
      } else {
         list = sortRegionsBySize(filtered);
      }

      if (format.equals("record")) {
         listRegionsAsRecords(ip, list);
      } else {
         listRegionsAsTable(ip, list);
      }

   }

   private static void listRegionsAsTable(Interpreter ip, List list) {
      printRegionTableHeader(ip);
      Iterator var2 = list.iterator();

      while(var2.hasNext()) {
         ReplicatedStoreAdmin.RegionInfo ri = (ReplicatedStoreAdmin.RegionInfo)var2.next();
         printRegionRow(ip, ri);
      }

      ip.message("");
   }

   private static void listRegionsAsRecords(Interpreter ip, List list) {
      printRegionRecordHeader(ip);
      Iterator var2 = list.iterator();

      while(var2.hasNext()) {
         ReplicatedStoreAdmin.RegionInfo ri = (ReplicatedStoreAdmin.RegionInfo)var2.next();
         printRegionRecord(ip, ri);
      }

   }

   private static List sortRegionsByName(HashMap riMap) {
      List sortedRegions = new ArrayList(riMap.values());
      Collections.sort(sortedRegions, new Comparator() {
         public int compare(ReplicatedStoreAdmin.RegionInfo r1, ReplicatedStoreAdmin.RegionInfo r2) {
            return r1.getName().compareTo(r2.getName());
         }
      });
      return sortedRegions;
   }

   private static List sortRegionsBySize(HashMap riMap) {
      List sortedRegions = new ArrayList(riMap.values());
      Collections.sort(sortedRegions, new Comparator() {
         public int compare(ReplicatedStoreAdmin.RegionInfo r1, ReplicatedStoreAdmin.RegionInfo r2) {
            return r1.getSize() - r2.getSize();
         }
      });
      return sortedRegions;
   }

   private static List sortRegionsByUsed(HashMap riMap) {
      List sortedRegions = new ArrayList(riMap.values());
      Collections.sort(sortedRegions, new Comparator() {
         public int compare(ReplicatedStoreAdmin.RegionInfo r1, ReplicatedStoreAdmin.RegionInfo r2) {
            return r1.getUsed() - r2.getUsed();
         }
      });
      return sortedRegions;
   }

   private static List sortRegionsByModificationTime(HashMap riMap) {
      List sortedRegions = new ArrayList(riMap.values());
      Collections.sort(sortedRegions, new Comparator() {
         public int compare(ReplicatedStoreAdmin.RegionInfo r1, ReplicatedStoreAdmin.RegionInfo r2) {
            return (int)r1.getModificationTime() - (int)r2.getModificationTime();
         }
      });
      return sortedRegions;
   }

   private static List sortRegionsByAccessTime(HashMap riMap) {
      List sortedRegions = new ArrayList(riMap.values());
      Collections.sort(sortedRegions, new Comparator() {
         public int compare(ReplicatedStoreAdmin.RegionInfo r1, ReplicatedStoreAdmin.RegionInfo r2) {
            return (int)r1.getAccessTime() - (int)r2.getAccessTime();
         }
      });
      return sortedRegions;
   }

   private static List sortRegionsByCreationime(HashMap riMap) {
      List sortedRegions = new ArrayList(riMap.values());
      Collections.sort(sortedRegions, new Comparator() {
         public int compare(ReplicatedStoreAdmin.RegionInfo r1, ReplicatedStoreAdmin.RegionInfo r2) {
            return (int)r1.getCreationTime() - (int)r2.getCreationTime();
         }
      });
      return sortedRegions;
   }

   private static void printCurrentTimeHeader(Interpreter ip) {
      ip.message("");
      ip.message("Current Time: " + dtfmt.format(new Date(System.currentTimeMillis())));
      ip.message("");
   }

   private static void printRegionTableHeader(Interpreter ip) {
      printCurrentTimeHeader(ip);
      ip.message("Size    Pri Rplca Open Last Modified Time         Region Name");
      ip.message("MB      Dmn Count      YYYY-MM-DD HH:mm:ss");
      ip.message("------- --- ----- ---- -------------------------- ------------------------------");
   }

   private static void printRegionRow(Interpreter ip, ReplicatedStoreAdmin.RegionInfo region) {
      ip.message(String.format("%6s %3s %s %4s %s %s", convertBytesToMB((long)region.getSize(), true), region.isOpen() ? String.valueOf(String.format("%03d", region.getPrimary())) : "-1", countReplicas(region.getNodes()), region.isOpen() ? "   Y" : "   N", String.valueOf(dtfmt.format(new Date(new Long(region.getModificationTime() / 1000L)))), String.valueOf(region.getName())));
   }

   private static void printRegionRecordHeader(Interpreter ip) {
      printCurrentTimeHeader(ip);
   }

   private static void printRegionRecord(Interpreter ip, ReplicatedStoreAdmin.RegionInfo region) {
      ip.message(String.format("                                    Name: %s\n                                    Open: %s\n                               Size (MB): %s\n                               Used (MB): %s\n                        Block Size Bytes: %d\n     Creation Time (YYYY-MM-DD HH:MM:ss): %s\nLast Modified Time (YYYY-MM-DD HH:MM:ss): %s\n      Creation Time (micros since epoch): %s\n Last Modified Time (micros since epoch): %s\n                    Daemons (hex bitask): 0x%x\n                 Daemons (numeric index): %s\n              Primary Daemon (-1==!open): %s\n                  Primary PID(-1==!open): %d\nPrimary Process Node IP (0.0.0.0==!open): %s\n", String.valueOf(region.getName()), region.isOpen() ? "TRUE" : "FALSE", convertBytesToMB((long)region.getSize(), false), convertBytesToMB((long)region.getUsed(), false), region.getBlockSize(), String.valueOf(dtfmt.format(new Date(region.getCreationTime() / 1000L))), String.valueOf(dtfmt.format(new Date(region.getModificationTime() / 1000L))), String.valueOf(region.getCreationTime()), String.valueOf(region.getModificationTime()), region.getNodes(), String.valueOf(parseNodes(region.getNodes())), region.isOpen() ? String.valueOf(String.format("%03d", region.getPrimary())) : "-1", region.isOpen() ? region.getPid() : -1, String.valueOf(region.isOpen() ? region.getIp() : "0.0.0.0")));
   }

   private static String convertBytesToMB(long bytes, boolean format) {
      long value = bytes / 1048576L;
      String result = (new Long(value)).toString();
      if (format) {
         result = String.format("%7s", memoryFormatter.format(value));
      }

      return result;
   }

   private static String convertMillisToElapsedTime(long millis) {
      long s = millis / 1000L;
      long m = s / 60L;
      s %= 60L;
      long h = m / 60L;
      m %= 60L;
      return String.format("%5d:%02d:%02d", h, m, s);
   }

   private static String parseNodes(long nodes) {
      String indices = "";

      for(long i = 0L; i <= nodes - 1L; ++i) {
         if ((nodes >> (int)i & 1L) == 1L) {
            if (indices.isEmpty()) {
               indices = indices + String.format("%03d", i);
            } else {
               indices = indices + " " + String.format("%03d", i);
            }
         }
      }

      return indices;
   }

   private static String countReplicas(long nodes) {
      int count = 0;

      for(long i = 0L; i <= nodes - 1L; ++i) {
         if ((nodes >> (int)i & 1L) == 1L) {
            ++count;
         }
      }

      String replicas = String.format("%5s", replicaFormatter.format((long)count));
      return replicas;
   }

   private static void listDaemonInfo(Interpreter ip, HashMap daemons, int localDaemonIndex, String scope, String sortBy, String format) {
      if (daemons.isEmpty()) {
         ip.error("There are no Daemons exist that matched the list options");
      } else {
         HashMap filteredMap = filterDaemons(ip, localDaemonIndex, scope, daemons);
         if (filteredMap.isEmpty()) {
            ip.error("There are no Daemons exist that matched the list options");
         } else {
            listDaemonInfo(ip, filteredMap, sortBy, format);
         }
      }
   }

   private static void listDaemonInfo(Interpreter ip, HashMap daemons, int localDaemonIndex, String[] indices, String sortBy, String format) {
      if (daemons.isEmpty()) {
         ip.error("There are no Daemons exist that matched the list options");
      } else {
         HashMap filteredMap = filterDaemons(ip, localDaemonIndex, indices, daemons);
         if (filteredMap.isEmpty()) {
            ip.error("There are no Daemons exist that matched the list options");
         } else {
            listDaemonInfo(ip, filteredMap, sortBy, format);
         }
      }
   }

   private static HashMap filterDaemons(Interpreter ip, int localDaemonIndex, String[] indices, HashMap diMap) {
      HashMap filteredMap = new HashMap();

      for(int i = 0; i < indices.length; ++i) {
         int daemonIndex = -1;

         try {
            daemonIndex = Integer.parseInt(indices[i]);
         } catch (NumberFormatException var8) {
            ip.error("Invalid daemon index: " + indices[i]);
         }

         String did = String.format("%03d", daemonIndex);
         if (diMap.get(did) != null) {
            filteredMap.put(did, diMap.get(did));
         } else {
            ip.info("No daemon with Index " + daemonIndex + " found");
         }
      }

      return filteredMap;
   }

   private static HashMap filterDaemons(Interpreter ip, int localDaemonIndex, String scope, HashMap diMap) {
      HashMap filteredMap = new HashMap();
      if (scope.equalsIgnoreCase("all")) {
         filteredMap = diMap;
      } else if (scope.equalsIgnoreCase("local")) {
         String did = String.format("%03d", localDaemonIndex);
         if (diMap.get(did) != null) {
            filteredMap.put(did, diMap.get(did));
         } else {
            ip.info("No local daemon with Index " + localDaemonIndex + " found");
         }
      }

      return filteredMap;
   }

   private static void listDaemonInfo(Interpreter ip, HashMap filtered, String sortBy, String format) {
      List list;
      if (sortBy.equals("name")) {
         list = sortDaemonsByName(filtered);
      } else if (sortBy.equals("time")) {
         list = sortDaemonsByStartTime(filtered);
      } else {
         list = sortDaemonsByMemoryUsage(filtered);
      }

      if (format.equals("record")) {
         listDaemonsAsRecords(ip, list);
      } else {
         listDaemonsAsTable(ip, list);
      }

   }

   private static void listDaemonsAsTable(Interpreter ip, List list) {
      printDaemonTableHeader(ip);
      Iterator var2 = list.iterator();

      while(var2.hasNext()) {
         ReplicatedStoreAdmin.DaemonInfo di = (ReplicatedStoreAdmin.DaemonInfo)var2.next();
         printDaemonRow(ip, di);
      }

      ip.message("");
   }

   private static void listDaemonAsTable(Interpreter ip, ReplicatedStoreAdmin.DaemonInfo di) {
      printDaemonTableHeader(ip);
      printDaemonRow(ip, di);
   }

   private static void listDaemonsAsRecords(Interpreter ip, List list) {
      printDaemonRecordHeader(ip);
      Iterator var2 = list.iterator();

      while(var2.hasNext()) {
         ReplicatedStoreAdmin.DaemonInfo di = (ReplicatedStoreAdmin.DaemonInfo)var2.next();
         printDaemonRecord(ip, di);
      }

   }

   private static void listDaemonAsRecord(Interpreter ip, ReplicatedStoreAdmin.DaemonInfo di) {
      printDaemonRecord(ip, di);
   }

   private static List sortDaemonsByName(HashMap diMap) {
      List sortedDaemons = new ArrayList(diMap.values());
      Collections.sort(sortedDaemons, new Comparator() {
         public int compare(ReplicatedStoreAdmin.DaemonInfo d1, ReplicatedStoreAdmin.DaemonInfo d2) {
            return d1.getId().compareTo(d2.getId());
         }
      });
      return sortedDaemons;
   }

   private static List sortDaemonsByStartTime(HashMap diMap) {
      List sortedDaemons = new ArrayList(diMap.values());
      Collections.sort(sortedDaemons, new Comparator() {
         public int compare(ReplicatedStoreAdmin.DaemonInfo d1, ReplicatedStoreAdmin.DaemonInfo d2) {
            return (int)d1.getStartTime() - (int)d2.getStartTime();
         }
      });
      return sortedDaemons;
   }

   private static List sortDaemonsByMemoryUsage(HashMap diMap) {
      List sortedDaemons = new ArrayList(diMap.values());
      Collections.sort(sortedDaemons, new Comparator() {
         public int compare(ReplicatedStoreAdmin.DaemonInfo d1, ReplicatedStoreAdmin.DaemonInfo d2) {
            return (int)d1.getUsedMemory() - (int)d2.getUsedMemory();
         }
      });
      return sortedDaemons;
   }

   private static void printDaemonTableHeader(Interpreter ip) {
      ip.message("");
      ip.message("Current Time: " + dtfmt.format(new Date(System.currentTimeMillis())));
      ip.message("");
      ip.message("Idx IP              Port   Up          Rgns   Rgns   Rgns   Rgns   Mem     Mem ");
      ip.message("    Address                Time        Closed Open   Open   Total  Used    Used");
      ip.message("                           HHHHH:MM:SS        Prima  Rplca         MB      %   ");
      ip.message("--- --------------- ------ ----------- ------ ------ ------ ------ ------- -----");
   }

   private static void printDaemonRow(Interpreter ip, ReplicatedStoreAdmin.DaemonInfo daemon) {
      float percentUsed = (float)daemon.getUsedMemory() / (float)daemon.getTotalMemory() * 100.0F;
      String percentUsedString = String.format("%5s", percentageFormatter.format(percentUsed >= 0.0F ? (double)percentUsed : 0.0));
      ip.message(String.format("%3s %15s %6d %s %6d %6d %6d %6d %s %s", String.valueOf(daemon.getId()), String.valueOf(daemon.getIp()), daemon.getPort(), daemon.getStatus().equals("UP") ? convertMillisToElapsedTime((daemon.getCurrentTime() - daemon.getStartTime()) / 1000L) : "Not_Running", daemon.getNumberOfStores() - daemon.getNumberOfOpens(), daemon.getNumberOfLocalOpens(), daemon.getNumberOfStores() - daemon.getNumberOfLocalOpens(), daemon.getNumberOfStores(), convertBytesToMB(daemon.getUsedMemory(), true), percentUsedString));
   }

   private static void printDaemonRecordHeader(Interpreter ip) {
      printCurrentTimeHeader(ip);
   }

   private static void printDaemonRecord(Interpreter ip, ReplicatedStoreAdmin.DaemonInfo daemon) {
      ip.message(String.format("                              Index : %s\n                             Status : %s\n                  Reachable over IB : %s\n                         IP Address : %s\n                               Port : %d\n            Shared Memory Key (hex) : 0x%x\n        Shared Memory Key (decimal) : %d\n Startup Time (YYYY-MM-DD HH:MM:ss) : %s\n Current Time (YYYY-MM-DD HH:MM:ss) : %s\n               Up Time (HHHH:MM:ss) : %s\n  Startup Time (micros since epoch) : %s\n  Current Time (micros since epoch) : %s\n                   Up Time (micros) : %s\n       Total Configured Memory (MB) : %s\n          Current Memory Usage (MB) : %s\n           Number of Regions Closed : %d\nNumber of Regions Opened as Primary : %d\nNumber of Regions Opened as Replica : %d\n    Total Number of Regions Managed : %d\n    Number of Resilvers in Progress : %d\n       Number of Daemons in Cluster : %d\n", String.valueOf(daemon.getId()), daemon.getStatus(), daemon.getReachable(), String.valueOf(daemon.getIp()), daemon.getPort(), daemon.getShmKey(), daemon.getShmKey(), daemon.getStatus().equals("UP") ? String.valueOf(dtfmt.format(new Date(daemon.getStartTime() / 1000L))) : "Not_Running", daemon.getStatus().equals("UP") ? String.valueOf(dtfmt.format(new Date(daemon.getCurrentTime() / 1000L))) : "Not_Running", daemon.getStatus().equals("UP") ? String.valueOf(hhfmt.format(new Date((daemon.getCurrentTime() - daemon.getStartTime()) / 1000L))) : "Not_Running", String.valueOf(daemon.getStartTime()), String.valueOf(daemon.getCurrentTime()), String.valueOf(daemon.getCurrentTime() - daemon.getStartTime()), convertBytesToMB(daemon.getTotalMemory(), false), convertBytesToMB(daemon.getUsedMemory(), false), daemon.getNumberOfOpens() - daemon.getNumberOfLocalOpens(), daemon.getNumberOfLocalOpens(), daemon.getNumberOfStores() - daemon.getNumberOfLocalOpens(), daemon.getNumberOfStores(), daemon.getNumberOfResilvers(), daemon.getNumberOfDaemons()));
   }

   private static String[] parseRegionNameExpression(String exp) {
      String pattern = ".*";
      String sep1 = ",";
      return exp.split(sep1);
   }

   private static ArrayList parseIndices(String s) throws Exception {
      ArrayList ret = null;
      int pos = 0;

      while(true) {
         while(true) {
            int indexA = parseIndex(s, pos);
            if (ret == null) {
               ret = new ArrayList();
            }

            ++pos;
            ret.add(Integer.toString(indexA));
            if (pos == s.length()) {
               return ret;
            }

            if (s.charAt(pos) == ',') {
               ++pos;
            } else if (s.charAt(pos) == '-') {
               ++pos;
               int indexB = parseIndex(s, pos);
               if (indexB < indexA) {
                  throw new Exception("range is reversed : " + s);
               }

               ++indexA;

               while(indexA <= indexB) {
                  ++pos;
                  ret.add(Integer.toString(indexA));
                  ++indexA;
               }

               if (pos >= s.length()) {
                  return ret;
               }

               if (s.charAt(pos) != ',') {
                  throw new Exception("bad index: " + s);
               }

               ++pos;
            }
         }
      }
   }

   private static int parseIndex(String s, int pos) throws Exception {
      skipWhiteSpace(s, pos);
      if (s.length() <= pos) {
         throw new Exception("index expected: " + s);
      } else {
         char c = s.charAt(pos);
         if (c >= '0' && c <= '9') {
            ++pos;

            int ret;
            for(ret = c - 48; pos != s.length() && (c = s.charAt(pos)) >= '0' && c <= '9'; ret = ret * 10 + (c - 48)) {
               ++pos;
            }

            skipWhiteSpace(s, pos);
            return ret;
         } else {
            throw new Exception("index expected: " + s);
         }
      }
   }

   private static void skipWhiteSpace(String s, int pos) {
      while(true) {
         if (s.length() > pos) {
            char c = s.charAt(pos);
            ++pos;
            if (c == ' ' || c == '\t') {
               continue;
            }
         }

         return;
      }
   }

   static class ReplicatedStoreAdminUtil {
      static String CFG_FILE_NAME = "rs_daemons.cfg";

      static String validateAndReturnCfgFile(String dirName, StoreAdmin sa) {
         Interpreter ip = sa.getInterpreter();
         File dir = new File(dirName);
         if (dir.exists() && dir.isDirectory()) {
            String[] fllist = dir.list(new FilenameFilter() {
               public boolean accept(File _dir, String _name) {
                  return Pattern.matches(RSCommandImpls.ReplicatedStoreAdminUtil.CFG_FILE_NAME, _name);
               }
            });
            if (fllist != null && fllist.length != 0 && fllist.length <= 1) {
               return dirName + File.separator + fllist[0];
            } else {
               ip.error("The -dir parameter must reference a Replicated Store Global Directory \n       that contains a \"rs_daemons.cfg\" file. If -dir was not specified,\n       the current directory is assumed to be the Global Directory.");
               return null;
            }
         } else {
            ip.error("Replicated Store Global Directory [" + dirName + "] does not exist");
            return null;
         }
      }
   }

   static class CommandRsHelp extends CommandDefs.Command {
      public void run() {
         if (this.args.length < 2) {
            this.cmdType.manPage(this.ip, this.sa.getAdvancedMode());
         } else {
            CommandDefs.CommandType ct = CommandDefs.getCommandType(this.args[1]);
            if (ct != null && (this.sa.getAdvancedMode() || !ct.isAdvanced())) {
               ct.manPage(this.ip, this.sa.getAdvancedMode());
               return;
            }

            this.ip.error("Unknown command : " + this.args[1]);
         }

         this.ip.message("");
         Interpreter var10000 = this.ip;
         StringBuilder var10001 = new StringBuilder();
         Interpreter var10002 = this.ip;
         var10000.message(var10001.append("  ").append("Available Commands:").toString());
         CommandDefs.CommandType[] cta = CommandDefs.getAllRsCommandTypes();

         for(int i = 0; i < cta.length; ++i) {
            cta[i].describe(this.ip, this.sa.getAdvancedMode());
         }

         this.ip.message("");
      }
   }

   static class CommandRsRegionsRm extends CommandDefs.Command {
      public void run() {
         try {
            if (!this.sa.getAdminIF().isAttached()) {
               this.ip.error("Currently not attached to RS Daemon Cluster");
               return;
            }
         } catch (IOException var5) {
            this.ip.error("Failed to determine isAttached to RS Daemon Cluster");
            return;
         }

         String[] regionExp = this.getOptionalValues();
         boolean force = this.getParamVal(CommandDefs.CommandParam.FORCE) != null;

         try {
            if (regionExp != null && regionExp.length != 0 || this.ip.confirm("No region name was specified. Are you sure, you want to delete all Regions")) {
               HashMap regions = this.sa.getAdminIF().rsListGlobalRegions();
               RSCommandImpls.deleteRegionInfo(this.sa, regions, regionExp, force);
               this.sa.getAdminIF().rsClean();
            }
         } catch (IOException var4) {
            this.ip.error("Failed to list region matching to " + regionExp, var4);
         }
      }
   }

   static class CommandRsRegionsLs extends CommandDefs.Command {
      static final String DEFAULT_DAEMON = "all";
      static final String DEFAULT_SORT_ORDER = "name";
      static final String DEFAULT_FORMAT = "table";

      public void run() {
         try {
            if (!this.sa.getAdminIF().isAttached()) {
               this.ip.error("Currently not attached to RS Daemon Cluster");
               return;
            }
         } catch (IOException var10) {
            this.ip.error("Failed to determine isAttached to RS Daemon Cluster");
            return;
         }

         String[] regionExp = this.getOptionalValues();
         String daemonScope = this.getParamVal(CommandDefs.CommandParam.DAEMON);
         if (daemonScope == null) {
            daemonScope = "all";
         }

         String sortOrder = this.getParamVal(CommandDefs.CommandParam.SORT);
         if (sortOrder == null) {
            sortOrder = "name";
         }

         String format = this.getParamVal(CommandDefs.CommandParam.FORMAT);
         if (format == null) {
            format = "table";
         }

         if (!format.equals("table") && !format.equals("record")) {
            this.ip.error("Invalid format option specified");
         } else {
            try {
               HashMap regions = null;
               ArrayList indices = new ArrayList();
               if (daemonScope.equalsIgnoreCase("all")) {
                  regions = this.sa.getAdminIF().rsListGlobalRegions();
               } else if (daemonScope.equalsIgnoreCase("local")) {
                  regions = this.sa.getAdminIF().rsListGlobalRegions();
                  int currentDaemon = this.sa.getAdminIF().rsGetAttachedDaemonIndex();
                  indices.add((new Integer(currentDaemon)).toString());
               } else {
                  regions = this.sa.getAdminIF().rsListGlobalRegions();

                  try {
                     indices = RSCommandImpls.parseIndices(daemonScope);
                  } catch (Exception var8) {
                     this.ip.error("Invalid -daemon value " + daemonScope + ", " + var8.getMessage());
                     return;
                  }
               }

               String[] daemons = null;
               if (indices != null && !indices.isEmpty()) {
                  daemons = (String[])indices.toArray(new String[indices.size()]);
                  Arrays.sort(daemons, 0, daemons.length);
               }

               RSCommandImpls.listRegionInfo(this.ip, regions, regionExp, daemons, sortOrder, format);
               this.sa.getAdminIF().rsClean();
            } catch (IOException var9) {
               this.ip.error("Failed to list region matching to " + regionExp, var9);
            }
         }
      }
   }

   static class CommandRsDaemonsShutdown extends CommandDefs.Command {
      static final String DEFAULT_DAEMON = "all";

      public void run() {
         try {
            if (!this.sa.getAdminIF().isAttached()) {
               this.ip.error("Currently not attached to RS Daemon Cluster");
               return;
            }
         } catch (IOException var18) {
            this.ip.error("Failed to determine isAttached to RS Daemon Cluster");
            return;
         }

         String daemonScope = this.getParamVal(CommandDefs.CommandParam.DAEMON);
         if (daemonScope != null && !daemonScope.trim().isEmpty()) {
            boolean force = this.getParamVal(CommandDefs.CommandParam.FORCE) != null;
            boolean safe = this.getParamVal(CommandDefs.CommandParam.SAFE) != null;
            if (force && safe) {
               this.ip.error("You cannot specify both '-force' and '-safe' options together for daemon shutdown.");
            } else {
               if (!force && !safe) {
                  safe = true;
               }

               boolean shutdownCurrentDaemon = false;
               boolean detached = false;

               try {
                  HashMap daemonInfos = this.sa.getAdminIF().rsListDaemons();
                  int currentlyAttachedDaemonIndex = this.sa.getAdminIF().rsGetAttachedDaemonIndex();
                  String currentlyAttachedDaemonIndexAsString = String.format("%03d", currentlyAttachedDaemonIndex);
                  ArrayList daemonsToShutdown = new ArrayList();
                  if (daemonScope.trim().toLowerCase().equals("all")) {
                     if (!this.ip.confirm("Are you sure, you want to shutdown all Daemons")) {
                        return;
                     }

                     Iterator it = daemonInfos.keySet().iterator();

                     while(it.hasNext()) {
                        String did = (String)it.next();
                        if (!((ReplicatedStoreAdmin.DaemonInfo)daemonInfos.get(did)).getStatus().equalsIgnoreCase("UP")) {
                           this.ip.info("Skipped Daemon " + did + ", is already down");
                        } else {
                           daemonsToShutdown.add(did);
                        }
                     }
                  } else if (daemonScope.equalsIgnoreCase("local")) {
                     shutdownCurrentDaemon = true;
                  } else {
                     try {
                        ArrayList ids = RSCommandImpls.parseIndices(daemonScope);
                        Iterator it = ids.listIterator();

                        while(it.hasNext()) {
                           String did = String.format("%03d", Integer.parseInt((String)it.next()));
                           ReplicatedStoreAdmin.DaemonInfo di = (ReplicatedStoreAdmin.DaemonInfo)daemonInfos.get(did);
                           if (di != null) {
                              if (!((ReplicatedStoreAdmin.DaemonInfo)daemonInfos.get(did)).getStatus().equalsIgnoreCase("UP")) {
                                 this.ip.info("Skipped Daemon " + did + ", is already down");
                              } else {
                                 daemonsToShutdown.add(did);
                              }
                           } else {
                              this.ip.info("Skipped Daemon " + did + ", does not exist");
                           }
                        }
                     } catch (Exception var19) {
                        this.ip.error("Invalid -daemon value " + daemonScope + ", " + var19.getMessage());
                        return;
                     }
                  }

                  String[] daemons = (String[])daemonsToShutdown.toArray(new String[daemonsToShutdown.size()]);

                  for(int i = 0; i < daemons.length; ++i) {
                     int daemonIndex = Integer.parseInt(daemons[i]);
                     if (daemonIndex == currentlyAttachedDaemonIndex) {
                        shutdownCurrentDaemon = true;
                     } else {
                        try {
                           RSCommandImpls.attachShutdown(this.sa, daemonIndex, force, safe);
                           if (!detached) {
                              detached = true;
                           }

                           this.ip.info("Daemon " + String.format("%03d", daemonIndex) + " is shutdown.");
                        } catch (IOException var17) {
                           this.ip.error("Failed to shutdown daemon " + daemonIndex, var17);
                           return;
                        }
                     }
                  }

                  if (shutdownCurrentDaemon) {
                     try {
                        RSCommandImpls.attachShutdown(this.sa, currentlyAttachedDaemonIndex, force, safe);
                     } catch (IOException var16) {
                        this.ip.error("Failed to shutdown local daemon " + currentlyAttachedDaemonIndex, var16);
                        return;
                     }

                     this.ip.info("Local Daemon " + currentlyAttachedDaemonIndexAsString + " is shutdown.");
                     this.ip.warn("You are now detached from RS Daemon Cluster and need to use\n      \"rsattach\" command again to attach to a running Daemon\n      for further management of a RS Daemon Cluster.");
                     if (!detached) {
                        detached = true;
                     }

                     try {
                        this.sa.getAdminIF().rsDetach();
                     } catch (IOException var15) {
                        this.ip.error("Failed to detach from RS Daemon Cluster", var15);
                        return;
                     }
                  }

                  if (!shutdownCurrentDaemon && detached) {
                     try {
                        if (this.sa.getAdminIF().rsAttachToDaemon(currentlyAttachedDaemonIndex) > 1) {
                           this.ip.error("Failed to attach back to local to daemon " + currentlyAttachedDaemonIndexAsString);
                           return;
                        }

                        detached = false;
                     } catch (IOException var14) {
                        this.ip.error("Failed to attach back to local daemon " + currentlyAttachedDaemonIndex, var14);
                        return;
                     }
                  }
               } catch (IOException var20) {
                  this.ip.error("Failed to shutdown daemon " + daemonScope, var20);
                  return;
               }

               if (shutdownCurrentDaemon && detached) {
                  String[] cmdPromptArgs = new String[]{"-prompt", this.ip.prevPromptVal};
                  this.ip.prompt = this.ip.setupPrompt("-prompt", cmdPromptArgs, this.ip.interpName);
               }

            }
         } else {
            this.ip.error("You must specify '-daemon' option to identify the daemons that need to be shutdown.");
         }
      }
   }

   static class CommandRsDaemonsLs extends CommandDefs.Command {
      static final String DEFAULT_DAEMON = "all";
      static final String DEFAULT_SORT_ORDER = "daemon";
      static final String DEFAULT_FORMAT = "table";

      public void run() {
         try {
            if (!this.sa.getAdminIF().isAttached()) {
               this.ip.error("Currently not attached to RS Daemon Cluster");
               return;
            }
         } catch (IOException var10) {
            this.ip.error("Failed to determine isAttached to RS Daemon Cluster");
            return;
         }

         String daemonScope = this.getParamVal(CommandDefs.CommandParam.DAEMON);
         if (daemonScope == null) {
            daemonScope = "all";
         }

         String sortOrder = this.getParamVal(CommandDefs.CommandParam.SORT);
         if (sortOrder == null) {
            sortOrder = "daemon";
         }

         String format = this.getParamVal(CommandDefs.CommandParam.FORMAT);
         if (format == null) {
            format = "table";
         } else if (!format.equals("table") && !format.equals("record")) {
            this.ip.error("Invalid format option specified");
            return;
         }

         try {
            int localDaemonIndex = this.sa.getAdminIF().rsGetAttachedDaemonIndex();
            HashMap daemonInfos = this.sa.getAdminIF().rsListDaemons();
            if (!daemonScope.equalsIgnoreCase("all") && !daemonScope.equalsIgnoreCase("local")) {
               new ArrayList();

               ArrayList indices;
               try {
                  indices = RSCommandImpls.parseIndices(daemonScope);
               } catch (Exception var8) {
                  this.ip.error("Invalid -daemon value " + daemonScope + ", " + var8.getMessage());
                  return;
               }

               String[] daemons = (String[])indices.toArray(new String[indices.size()]);
               RSCommandImpls.listDaemonInfo(this.ip, daemonInfos, localDaemonIndex, daemons, sortOrder, format);
            } else {
               RSCommandImpls.listDaemonInfo(this.ip, daemonInfos, localDaemonIndex, daemonScope, sortOrder, format);
            }

            this.sa.getAdminIF().rsClean();
         } catch (IOException var9) {
            this.ip.error("Failed to list daemon " + daemonScope, var9);
         }
      }
   }

   static class CommandRsDetach extends CommandDefs.Command {
      public void run() {
         try {
            if (!this.sa.getAdminIF().isAttached()) {
               this.ip.error("Currently not attached to RS Daemon Cluster");
               return;
            }
         } catch (IOException var3) {
            this.ip.error("Failed to determine isAttached to RS Daemon Cluster");
            return;
         }

         try {
            this.sa.getAdminIF().rsDetach();
         } catch (IOException var2) {
            this.ip.error("Failed to detach from RS Daemon Cluster", var2);
            return;
         }

         this.ip.info("Detached from RS Daemon Cluster.");
         String[] cmdPromptArgs = new String[]{"-prompt", this.ip.prevPromptVal};
         this.ip.prompt = this.ip.setupPrompt("-prompt", cmdPromptArgs, this.ip.interpName);
      }
   }

   static class CommandRsAttach extends CommandDefs.Command {
      static final int DEFAULT_LOCALINDEX = 0;

      public void run() {
         try {
            if (this.sa.getAdminIF().isAttached()) {
               this.ip.info("Already attached to RS Daemon Cluster");
               return;
            }
         } catch (IOException var7) {
            this.ip.error("Failed to determine isAttached to RS Daemon Cluster");
            return;
         }

         String dirName = this.getParamVal(CommandDefs.CommandParam.DIR);
         if (dirName != null && !dirName.equals(".")) {
            dirName = Paths.get(dirName).toAbsolutePath().toString();
         } else {
            dirName = Paths.get("").toAbsolutePath().toString();
         }

         String cfgFileName = RSCommandImpls.ReplicatedStoreAdminUtil.validateAndReturnCfgFile(dirName, this.sa);
         if (cfgFileName != null) {
            int localIndex = 0;
            if (this.getParamVal(CommandDefs.CommandParam.LOCALINDEX) != null) {
               localIndex = Integer.parseInt(this.getParamVal(CommandDefs.CommandParam.LOCALINDEX));
            }

            try {
               if (this.sa.getAdminIF().rsAttach(dirName, cfgFileName, localIndex) > 1) {
                  this.ip.error("Failed to attach to RS Daemon Cluster");
                  return;
               }
            } catch (IOException var8) {
               this.ip.error("Failed to attach to RS Daemon Cluster", var8);
               return;
            }

            int cad = -1;

            try {
               cad = this.sa.getAdminIF().rsGetAttachedDaemonIndex();
            } catch (IOException var6) {
            }

            this.ip.info("Attached to Daemon " + String.format("%03d", cad) + " with RS Global Dir: [" + dirName + "]");
            String[] cmdPromptArgs = new String[]{"-prompt", this.ip.promptVal + ":[RS]"};
            this.ip.prompt = this.ip.setupPrompt("-prompt", cmdPromptArgs, this.ip.interpName);
         }
      }
   }
}
