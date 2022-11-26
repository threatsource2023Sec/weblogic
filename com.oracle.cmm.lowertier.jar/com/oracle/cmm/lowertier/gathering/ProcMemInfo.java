package com.oracle.cmm.lowertier.gathering;

import com.oracle.cmm.lowertier.jfr.FlightRecorderManager;
import com.oracle.cmm.lowertier.pressure.EvaluationManager;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ProcMemInfo implements StatisticsGatherer {
   private static final Logger LOGGER = Logger.getLogger(ProcMemInfo.class.getPackage().getName());
   private static final String KB = "kB";
   private static final String MB = "mB";
   private static final String GB = "gB";
   private static final String MEMTOTAL = "MemTotal";
   private static final String MEMFREE = "MemFree";
   private static final String BUFFERS = "Buffers";
   private static final String CACHED = "Cached";
   private static final String SWAPCACHED = "SwapCached";
   private static final String ACTIVE = "Active";
   private static final String INACTIVE = "Inactive";
   private static final String HIGHTOTAL = "HighTotal";
   private static final String HIGHFREE = "HighFree";
   private static final String LOWTOTAL = "LowTotal";
   private static final String LOWFREE = "LowFree";
   private static final String SWAPTOTAL = "SwapTotal";
   private static final String SWAPFREE = "SwapFree";
   private static final String DIRTY = "Dirty";
   private static final String WRITEBACK = "Writeback";
   private static final String ANONPAGES = "AnonPages";
   private static final String MAPPED = "Mapped";
   private static final String SLAB = "Slab";
   private static final String PAGETABLES = "PageTables";
   private static final String NFSUNSTABLE = "NFS_Unstable";
   private static final String BOUNCE = "Bounce";
   private static final String COMMITLIMIT = "CommitLimit";
   private static final String COMMITTEDAS = "Committed_AS";
   private static final String VMALLOCTOTAL = "VmallocTotal";
   private static final String VMALLOCUSED = "VmallocUsed";
   private static final String VMALLOCCHUNK = "VmallocChunk";
   private static final String HUGEPAGESTOTAL = "HugePages_Total";
   private static final String HUGEPAGESFREE = "HugePages_Free";
   private static final String HUGEPAGESRSVD = "HugePages_Rsvd";
   private static final String HUGEPAGESIZE = "Hugepagesize";
   private boolean memInfoFound = false;
   long memTotal = -1L;
   long memFree = -1L;
   long buffers = -1L;
   long cached = -1L;
   long swapCached = -1L;
   long active = -1L;
   long inactive = -1L;
   long highTotal = -1L;
   long highFree = -1L;
   long lowTotal = -1L;
   long lowFree = -1L;
   long swapTotal = -1L;
   long swapFree = -1L;
   long dirty = -1L;
   long writeBack = -1L;
   long anonPages = -1L;
   long mapped = -1L;
   long slab = -1L;
   long pageTables = -1L;
   long nfsUnstable = -1L;
   long bounce = -1L;
   long commitLimit = -1L;
   long committedAS = -1L;
   long vmallocTotal = -1L;
   long vmallocUsed = -1L;
   long vmallocChunk = -1L;
   long hugePagesTotal = -1L;
   long hugePagesFree = -1L;
   long hugePagesRsvd = -1L;
   long hugePagesize = -1L;

   public long getMemTotal() {
      return this.memTotal;
   }

   public long getMemFree() {
      return this.memFree;
   }

   public long getBuffers() {
      return this.buffers;
   }

   public long getCached() {
      return this.cached;
   }

   public long getSwapCached() {
      return this.swapCached;
   }

   public long getActive() {
      return this.active;
   }

   public long getInactive() {
      return this.inactive;
   }

   public long getHighTotal() {
      return this.highTotal;
   }

   public long getHighFree() {
      return this.highFree;
   }

   public long getLowTotal() {
      return this.lowTotal;
   }

   public long getLowFree() {
      return this.lowFree;
   }

   public long getSwapTotal() {
      return this.swapTotal;
   }

   public long getSwapFree() {
      return this.swapFree;
   }

   public long getDirty() {
      return this.dirty;
   }

   public long getWriteBack() {
      return this.writeBack;
   }

   public long getAnonPages() {
      return this.anonPages;
   }

   public long getMapped() {
      return this.mapped;
   }

   public long getSlab() {
      return this.slab;
   }

   public long getPageTables() {
      return this.pageTables;
   }

   public long getNFSUnstable() {
      return this.nfsUnstable;
   }

   public long getBounce() {
      return this.bounce;
   }

   public long getCommitLimit() {
      return this.commitLimit;
   }

   public long getCommittedAS() {
      return this.committedAS;
   }

   public long getVmallocTotal() {
      return this.vmallocTotal;
   }

   public long getVmallocUsed() {
      return this.vmallocUsed;
   }

   public long getVmallocChunk() {
      return this.vmallocChunk;
   }

   public long getHugePagesTotal() {
      return this.hugePagesTotal;
   }

   public long getHugePagesFree() {
      return this.hugePagesFree;
   }

   public long getHugePagesRsvd() {
      return this.hugePagesRsvd;
   }

   public long getHugePagesize() {
      return this.hugePagesize;
   }

   public ProcMemInfo() {
      try {
         File memInfoFile = new File("/proc/meminfo");
         if (memInfoFile.exists() && memInfoFile.canRead()) {
            this.memInfoFound = true;
            if (this.refresh() < 1) {
               this.memInfoFound = false;
            }
         } else {
            if (LOGGER.isLoggable(Level.FINER)) {
               LOGGER.finer("/proc/meminfo not found or was found and wasn't readable");
            }

         }
      } catch (IOException var2) {
         if (LOGGER.isLoggable(Level.FINER)) {
            var2.printStackTrace();
         }

         this.memInfoFound = false;
      }
   }

   public void initialize(String initialValue) {
      if (this.memInfoFound) {
         EvaluationManager.getEvaluationManager().addJFREventClass(ProcMemInfoStatisticsEvent.class);
      }

   }

   public String ELBeanName() {
      return "procMemInfo";
   }

   public void gatherStatistics() {
      try {
         this.refresh();
         if (FlightRecorderManager.Factory.getInstance().isEventGenerationEnabled(3)) {
            this.generateJFREvent();
         }
      } catch (IOException var2) {
      }

   }

   private void generateJFREvent() {
      ProcMemInfoStatisticsEvent event = new ProcMemInfoStatisticsEvent();
      event.populate(this);
      event.commit();
   }

   private int refresh() throws IOException {
      if (!this.memInfoFound) {
         return 0;
      } else {
         int numSet = 0;
         BufferedReader reader = null;

         try {
            reader = new BufferedReader(new FileReader("/proc/meminfo"));

            String inputLine;
            while((inputLine = reader.readLine()) != null) {
               int length = inputLine.length();
               if (length != 0) {
                  String[] parts = inputLine.split(":");
                  if (parts.length == 2 && parts[0].length() != 0 && parts[1].length() != 0) {
                     String name = parts[0];
                     String[] moreParts = parts[1].trim().split(" ");
                     if (moreParts.length > 2) {
                        if (LOGGER.isLoggable(Level.FINER)) {
                           LOGGER.finer("Skipping(2): " + inputLine);
                        }
                     } else {
                        long value = 0L;

                        try {
                           value = Long.parseLong(moreParts[0]);
                        } catch (NumberFormatException var16) {
                           if (LOGGER.isLoggable(Level.FINER)) {
                              LOGGER.finer("Skipping(3): " + inputLine);
                           }
                           continue;
                        } catch (Exception var17) {
                           if (LOGGER.isLoggable(Level.FINER)) {
                              var17.printStackTrace();
                              LOGGER.finer("Skipping(3b): " + inputLine);
                           }
                           continue;
                        }

                        long multiplier = 1L;
                        if (moreParts.length == 2) {
                           multiplier = this.determineMultiplier(moreParts[1]);
                        }

                        if (multiplier < 1L) {
                           if (LOGGER.isLoggable(Level.FINER)) {
                              LOGGER.finer("Skipping(4): " + inputLine);
                           }
                        } else {
                           value *= multiplier;
                           this.setNamedValue(name, value);
                           ++numSet;
                        }
                     }
                  } else if (LOGGER.isLoggable(Level.FINER)) {
                     LOGGER.finer("Skipping(1): " + inputLine);
                  }
               }
            }
         } finally {
            if (reader != null) {
               reader.close();
            }

         }

         return numSet;
      }
   }

   private long determineMultiplier(String suffix) {
      if (suffix != null && suffix.length() != 0) {
         if ("kB".equalsIgnoreCase(suffix)) {
            return 1024L;
         } else if ("mB".equalsIgnoreCase(suffix)) {
            return 1048576L;
         } else if ("gB".equalsIgnoreCase(suffix)) {
            return 1073741824L;
         } else {
            if (LOGGER.isLoggable(Level.FINER)) {
               LOGGER.finer("suffix didn't match one that we are handling: " + suffix);
            }

            return -1L;
         }
      } else {
         return 1L;
      }
   }

   private void setNamedValue(String name, long value) {
      if ("MemTotal".equalsIgnoreCase(name)) {
         this.memTotal = value;
      } else if ("MemFree".equalsIgnoreCase(name)) {
         this.memFree = value;
      } else if ("Buffers".equalsIgnoreCase(name)) {
         this.buffers = value;
      } else if ("Cached".equalsIgnoreCase(name)) {
         this.cached = value;
      } else if ("SwapCached".equalsIgnoreCase(name)) {
         this.swapCached = value;
      } else if ("Active".equalsIgnoreCase(name)) {
         this.active = value;
      } else if ("Inactive".equalsIgnoreCase(name)) {
         this.inactive = value;
      } else if ("HighTotal".equalsIgnoreCase(name)) {
         this.highTotal = value;
      } else if ("HighFree".equalsIgnoreCase(name)) {
         this.highFree = value;
      } else if ("LowTotal".equalsIgnoreCase(name)) {
         this.lowTotal = value;
      } else if ("LowFree".equalsIgnoreCase(name)) {
         this.lowFree = value;
      } else if ("SwapTotal".equalsIgnoreCase(name)) {
         this.swapTotal = value;
      } else if ("SwapFree".equalsIgnoreCase(name)) {
         this.swapFree = value;
      } else if ("Dirty".equalsIgnoreCase(name)) {
         this.dirty = value;
      } else if ("Writeback".equalsIgnoreCase(name)) {
         this.writeBack = value;
      } else if ("AnonPages".equalsIgnoreCase(name)) {
         this.anonPages = value;
      } else if ("Mapped".equalsIgnoreCase(name)) {
         this.mapped = value;
      } else if ("Slab".equalsIgnoreCase(name)) {
         this.slab = value;
      } else if ("PageTables".equalsIgnoreCase(name)) {
         this.pageTables = value;
      } else if ("NFS_Unstable".equalsIgnoreCase(name)) {
         this.nfsUnstable = value;
      } else if ("Bounce".equalsIgnoreCase(name)) {
         this.bounce = value;
      } else if ("CommitLimit".equalsIgnoreCase(name)) {
         this.commitLimit = value;
      } else if ("Committed_AS".equalsIgnoreCase(name)) {
         this.committedAS = value;
      } else if ("VmallocTotal".equalsIgnoreCase(name)) {
         this.vmallocTotal = value;
      } else if ("VmallocUsed".equalsIgnoreCase(name)) {
         this.vmallocUsed = value;
      } else if ("VmallocChunk".equalsIgnoreCase(name)) {
         this.vmallocChunk = value;
      } else if ("HugePages_Total".equalsIgnoreCase(name)) {
         this.hugePagesTotal = value;
      } else if ("HugePages_Free".equalsIgnoreCase(name)) {
         this.hugePagesFree = value;
      } else if ("HugePages_Rsvd".equalsIgnoreCase(name)) {
         this.hugePagesRsvd = value;
      } else if ("Hugepagesize".equalsIgnoreCase(name)) {
         this.hugePagesize = value;
      } else {
         if (LOGGER.isLoggable(Level.FINER)) {
            LOGGER.finer("unknown metric value: " + name + " : " + value);
         }

      }
   }

   public String toString() {
      if (!this.memInfoFound) {
         return "/proc/meminfo was not found";
      } else {
         StringBuffer sb = new StringBuffer();
         sb.append("memTotal (kB):     " + this.memTotal / 1024L);
         sb.append("\nmemFree (kB):      " + this.memFree / 1024L);
         sb.append("\nbuffers (kB):      " + this.buffers / 1024L);
         sb.append("\ncached (kB):       " + this.cached / 1024L);
         sb.append("\nswapCached (kB):   " + this.swapCached / 1024L);
         sb.append("\nactive (kB):       " + this.active / 1024L);
         sb.append("\ninactive (kB):     " + this.inactive / 1024L);
         sb.append("\nhighTotal (kB):    " + this.highTotal / 1024L);
         sb.append("\nhighFree (kB):     " + this.highFree / 1024L);
         sb.append("\nlowTotal (kB):     " + this.lowTotal / 1024L);
         sb.append("\nlowFree (kB):      " + this.lowFree / 1024L);
         sb.append("\nswapTotal (kB):    " + this.swapTotal / 1024L);
         sb.append("\nswapFree (kB):     " + this.swapFree / 1024L);
         sb.append("\ndirty (kB):        " + this.dirty / 1024L);
         sb.append("\nwriteBack (kB):    " + this.writeBack / 1024L);
         sb.append("\nanonPages (kB):    " + this.anonPages / 1024L);
         sb.append("\nmapped (kB):       " + this.mapped / 1024L);
         sb.append("\nslab (kB):         " + this.slab / 1024L);
         sb.append("\npageTables (kB):   " + this.pageTables / 1024L);
         sb.append("\nnfsUnstable (kB):  " + this.nfsUnstable / 1024L);
         sb.append("\nbounce (kB):       " + this.bounce / 1024L);
         sb.append("\ncommitLimit (kB):  " + this.commitLimit / 1024L);
         sb.append("\ncommittedAS (kB):  " + this.committedAS / 1024L);
         sb.append("\nvmallocTotal (kB): " + this.vmallocTotal / 1024L);
         sb.append("\nvmallocUsed (kB):  " + this.vmallocUsed / 1024L);
         sb.append("\nvmallocChunk (kB): " + this.vmallocChunk / 1024L);
         sb.append("\nhugePagesTotal:    " + this.hugePagesTotal);
         sb.append("\nhugePagesFree:     " + this.hugePagesFree);
         sb.append("\nhugePagesRsvd:     " + this.hugePagesRsvd);
         sb.append("\nhugePagesize (kB): " + this.hugePagesize / 1024L);
         return sb.toString();
      }
   }

   public static void main(String[] args) {
      try {
         ProcMemInfo memInfo = new ProcMemInfo();
         memInfo.refresh();
         LOGGER.info("/proc/meminfo:\n" + memInfo.toString());
      } catch (IOException var2) {
         if (LOGGER.isLoggable(Level.FINER)) {
            var2.printStackTrace();
         }
      }

   }
}
