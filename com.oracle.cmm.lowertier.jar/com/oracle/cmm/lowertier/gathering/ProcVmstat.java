package com.oracle.cmm.lowertier.gathering;

import com.oracle.cmm.lowertier.jfr.FlightRecorderManager;
import com.oracle.cmm.lowertier.pressure.EvaluationManager;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ProcVmstat implements StatisticsGatherer {
   private static final Logger LOGGER = Logger.getLogger(ProcVmstat.class.getPackage().getName());
   private static final String NR_ANON_PAGES = "nr_anon_pages";
   private static final String NR_MAPPED = "nr_mapped";
   private static final String NR_FILE_PAGES = "nr_file_pages";
   private static final String NR_SLAB = "nr_slab";
   private static final String NR_PAGE_TABLE_PAGES = "nr_page_table_pages";
   private static final String NR_DIRTY = "nr_dirty";
   private static final String NR_WRITEBACK = "nr_writeback";
   private static final String NR_UNSTABLE = "nr_unstable";
   private static final String NR_BOUNCE = "nr_bounce";
   private static final String NUMA_HIT = "numa_hit";
   private static final String NUMA_MISS = "numa_miss";
   private static final String NUMA_FOREIGN = "numa_foreign";
   private static final String NUMA_INTERLEAVE = "numa_interleave";
   private static final String NUMA_LOCAL = "numa_local";
   private static final String NUMA_OTHER = "numa_other";
   private static final String PGPGIN = "pgpgin";
   private static final String PGPGOUT = "pgpgout";
   private static final String PSWPIN = "pswpin";
   private static final String PSWPOUT = "pswpout";
   private static final String PGALLOC_DMA = "pgalloc_dma";
   private static final String PGALLOC_DMA32 = "pgalloc_dma32";
   private static final String PGALLOC_NORMAL = "pgalloc_normal";
   private static final String PGALLOC_HIGH = "pgalloc_high";
   private static final String PGFREE = "pgfree";
   private static final String PGACTIVATE = "pgactivate";
   private static final String PGDEACTIVATE = "pgdeactivate";
   private static final String PGFAULT = "pgfault";
   private static final String PGMAJFAULT = "pgmajfault";
   private static final String PGREFILL_DMA = "pgrefill_dma";
   private static final String PGREFILL_DMA32 = "pgrefill_dma32";
   private static final String PGREFILL_NORMAL = "pgrefill_normal";
   private static final String PGREFILL_HIGH = "pgrefill_high";
   private static final String PGSTEAL_DMA = "pgsteal_dma";
   private static final String PGSTEAL_DMA32 = "pgsteal_dma32";
   private static final String PGSTEAL_NORMAL = "pgsteal_normal";
   private static final String PGSTEAL_HIGH = "pgsteal_high";
   private static final String PGSCAN_KSWAPD_DMA = "pgscan_kswapd_dma";
   private static final String PGSCAN_KSWAPD_DMA32 = "pgscan_kswapd_dma32";
   private static final String PGSCAN_KSWAPD_NORMAL = "pgscan_kswapd_normal";
   private static final String PGSCAN_KSWAPD_HIGH = "pgscan_kswapd_high";
   private static final String PGSCAN_DIRECT_DMA = "pgscan_direct_dma";
   private static final String PGSCAN_DIRECT_DMA32 = "pgscan_direct_dma32";
   private static final String PGSCAN_DIRECT_NORMAL = "pgscan_direct_normal";
   private static final String PGSCAN_DIRECT_HIGH = "pgscan_direct_high";
   private static final String PGINODESTEAL = "pginodesteal";
   private static final String SLABS_SCANNED = "slabs_scanned";
   private static final String KSWAPD_STEAL = "kswapd_steal";
   private static final String KSWAPD_INODESTEAL = "kswapd_inodesteal";
   private static final String PAGEOUTRUN = "pageoutrun";
   private static final String ALLOCSTALL = "allocstall";
   private static final String PGROTATED = "pgrotated";
   private boolean vmstatFound = false;
   long previousTimestamp = -1L;
   double deltaSecs;
   long pgpginPerSec = -1L;
   long pgpgoutPerSec = -1L;
   long pswpinPerSec = -1L;
   long pswpoutPerSec = -1L;
   long pgscanKswapdDmaPerSec = -1L;
   long pgscanKswapdDma32PerSec = -1L;
   long pgscanKswapdNormalPerSec = -1L;
   long pgscanKswapdHighPerSec = -1L;
   long pgscanDirectDmaPerSec = -1L;
   long pgscanDirectDma32PerSec = -1L;
   long pgscanDirectNormalPerSec = -1L;
   long pgscanDirectHighPerSec = -1L;
   long slabsScannedPerSec = -1L;
   long nrAnonPages = -1L;
   long nrMapped = -1L;
   long nrFilePages = -1L;
   long nrSlab = -1L;
   long nrPageTablePages = -1L;
   long nrDirty = -1L;
   long nrWriteback = -1L;
   long nrUnstable = -1L;
   long nrBounce = -1L;
   long numaHit = -1L;
   long numaMiss = -1L;
   long numaForeign = -1L;
   long numaInterleave = -1L;
   long numaLocal = -1L;
   long numaOther = -1L;
   long pgpgin = -1L;
   long pgpgout = -1L;
   long pswpin = -1L;
   long pswpout = -1L;
   long pgallocDma = -1L;
   long pgallocDma32 = -1L;
   long pgallocNormal = -1L;
   long pgallocHigh = -1L;
   long pgfree = -1L;
   long pgactivate = -1L;
   long pgdeactivate = -1L;
   long pgfault = -1L;
   long pgmajfault = -1L;
   long pgrefillDma = -1L;
   long pgrefillDma32 = -1L;
   long pgrefillNormal = -1L;
   long pgrefillHigh = -1L;
   long pgstealDma = -1L;
   long pgstealDma32 = -1L;
   long pgstealNormal = -1L;
   long pgstealHigh = -1L;
   long pgscanKswapdDma = -1L;
   long pgscanKswapdDma32 = -1L;
   long pgscanKswapdNormal = -1L;
   long pgscanKswapdHigh = -1L;
   long pgscanDirectDma = -1L;
   long pgscanDirectDma32 = -1L;
   long pgscanDirectNormal = -1L;
   long pgscanDirectHigh = -1L;
   long pginodesteal = -1L;
   long slabsScanned = -1L;
   long kswapdSteal = -1L;
   long kswapdInodesteal = -1L;
   long pageoutrun = -1L;
   long allocstall = -1L;
   long pgrotated = -1L;
   long pgpginPerSecConsecutiveTrailingIntervalsOverZero = 0L;
   long pgpgoutPerSecConsecutiveTrailingIntervalsOverZero = 0L;
   long pswpinPerSecConsecutiveTrailingIntervalsOverZero = 0L;
   long pswpoutPerSecConsecutiveTrailingIntervalsOverZero = 0L;

   public long getnrAnonPages() {
      return this.nrAnonPages;
   }

   public long getnrMapped() {
      return this.nrMapped;
   }

   public long getnrFilePages() {
      return this.nrFilePages;
   }

   public long getnrSlab() {
      return this.nrSlab;
   }

   public long getnrPageTablePages() {
      return this.nrPageTablePages;
   }

   public long getnrDirty() {
      return this.nrDirty;
   }

   public long getnrWriteback() {
      return this.nrWriteback;
   }

   public long getnrUnstable() {
      return this.nrUnstable;
   }

   public long getnrBounce() {
      return this.nrBounce;
   }

   public long getnumaHit() {
      return this.numaHit;
   }

   public long getnumaMiss() {
      return this.numaMiss;
   }

   public long getnumaForeign() {
      return this.numaForeign;
   }

   public long getnumaInterleave() {
      return this.numaInterleave;
   }

   public long getnumaLocal() {
      return this.numaLocal;
   }

   public long getnumaOther() {
      return this.numaOther;
   }

   public long getpgpgin() {
      return this.pgpgin;
   }

   public long getpgpginPerSec() {
      return this.pgpginPerSec;
   }

   public long getpgpginPerSecConsecutiveTrailingIntervalsOverZero() {
      return this.pgpginPerSecConsecutiveTrailingIntervalsOverZero;
   }

   public long getpgpgout() {
      return this.pgpgout;
   }

   public long getpgpgoutPerSec() {
      return this.pgpgoutPerSec;
   }

   public long getpgpgoutPerSecConsecutiveTrailingIntervalsOverZero() {
      return this.pgpgoutPerSecConsecutiveTrailingIntervalsOverZero;
   }

   public long getpswpin() {
      return this.pswpin;
   }

   public long getpswpinPerSec() {
      return this.pswpinPerSec;
   }

   public long getpswpinPerSecConsecutiveTrailingIntervalsOverZero() {
      return this.pswpinPerSecConsecutiveTrailingIntervalsOverZero;
   }

   public long getpswpout() {
      return this.pswpout;
   }

   public long getpswpoutPerSec() {
      return this.pswpoutPerSec;
   }

   public long getpswpoutPerSecConsecutiveTrailingIntervalsOverZero() {
      return this.pswpoutPerSecConsecutiveTrailingIntervalsOverZero;
   }

   public long getpgallocDma() {
      return this.pgallocDma;
   }

   public long getpgallocDma32() {
      return this.pgallocDma32;
   }

   public long getpgallocNormal() {
      return this.pgallocNormal;
   }

   public long getpgallocHigh() {
      return this.pgallocHigh;
   }

   public long getpgfree() {
      return this.pgfree;
   }

   public long getpgactivate() {
      return this.pgactivate;
   }

   public long getpgdeactivate() {
      return this.pgdeactivate;
   }

   public long getpgfault() {
      return this.pgfault;
   }

   public long getpgmajfault() {
      return this.pgmajfault;
   }

   public long getpgrefillDma() {
      return this.pgrefillDma;
   }

   public long getpgrefillDma32() {
      return this.pgrefillDma32;
   }

   public long getpgrefillNormal() {
      return this.pgrefillNormal;
   }

   public long getpgrefillHigh() {
      return this.pgrefillHigh;
   }

   public long getpgstealDma() {
      return this.pgstealDma;
   }

   public long getpgstealDma32() {
      return this.pgstealDma32;
   }

   public long getpgstealNormal() {
      return this.pgstealNormal;
   }

   public long getpgstealHigh() {
      return this.pgstealHigh;
   }

   public long getpgscanKswapdDma() {
      return this.pgscanKswapdDma;
   }

   public long getpgscanKswapdDmaPerSec() {
      return this.pgscanKswapdDmaPerSec;
   }

   public long getpgscanKswapdDma32() {
      return this.pgscanKswapdDma32;
   }

   public long getpgscanKswapdDma32PerSec() {
      return this.pgscanKswapdDma32PerSec;
   }

   public long getpgscanKswapdNormal() {
      return this.pgscanKswapdNormal;
   }

   public long getpgscanKswapdNormalPerSec() {
      return this.pgscanKswapdNormalPerSec;
   }

   public long getpgscanKswapdHigh() {
      return this.pgscanKswapdHigh;
   }

   public long getpgscanKswapdHighPerSec() {
      return this.pgscanKswapdHighPerSec;
   }

   public long getpgscanDirectDma() {
      return this.pgscanDirectDma;
   }

   public long getpgscanDirectDmaPerSec() {
      return this.pgscanDirectDmaPerSec;
   }

   public long getpgscanDirectDma32() {
      return this.pgscanDirectDma32;
   }

   public long getpgscanDirectDma32PerSec() {
      return this.pgscanDirectDma32PerSec;
   }

   public long getpgscanDirectNormal() {
      return this.pgscanDirectNormal;
   }

   public long getpgscanDirectNormalPerSec() {
      return this.pgscanDirectNormalPerSec;
   }

   public long getpgscanDirectHigh() {
      return this.pgscanDirectHigh;
   }

   public long getpgscanDirectHighPerSec() {
      return this.pgscanDirectHighPerSec;
   }

   public long getpginodesteal() {
      return this.pginodesteal;
   }

   public long getslabsScanned() {
      return this.slabsScanned;
   }

   public long getslabsScannedPerSec() {
      return this.slabsScannedPerSec;
   }

   public long getkswapdSteal() {
      return this.kswapdSteal;
   }

   public long getkswapdInodesteal() {
      return this.kswapdInodesteal;
   }

   public long getpageoutrun() {
      return this.pageoutrun;
   }

   public long getallocstall() {
      return this.allocstall;
   }

   public long getpgrotated() {
      return this.pgrotated;
   }

   public double getDeltaSecs() {
      return this.deltaSecs;
   }

   public ProcVmstat() {
      try {
         File vmstatFile = new File("/proc/vmstat");
         if (vmstatFile.exists() && vmstatFile.canRead()) {
            this.vmstatFound = true;
            if (this.refresh() < 1) {
               this.vmstatFound = false;
            }
         } else {
            if (LOGGER.isLoggable(Level.FINER)) {
               LOGGER.finer("/proc/vmstat not found or was found and wasn't readable");
            }

         }
      } catch (IOException var2) {
         if (LOGGER.isLoggable(Level.FINER)) {
            var2.printStackTrace();
         }

         this.vmstatFound = false;
      }
   }

   public void initialize(String initialValue) {
      if (this.vmstatFound) {
         EvaluationManager.getEvaluationManager().addJFREventClass(ProcVmstatStatisticsEvent.class);
      }

   }

   public String ELBeanName() {
      return "procVmstat";
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
      ProcVmstatStatisticsEvent event = new ProcVmstatStatisticsEvent();
      event.populate(this);
      event.commit();
   }

   private int refresh() throws IOException {
      if (!this.vmstatFound) {
         return 0;
      } else {
         long currentTimestamp = System.currentTimeMillis();
         if (this.previousTimestamp == -1L) {
            this.deltaSecs = 0.0;
         } else {
            this.deltaSecs = (double)(currentTimestamp - this.previousTimestamp) / 1000.0;
         }

         this.previousTimestamp = currentTimestamp;
         int numSet = 0;
         BufferedReader reader = null;

         try {
            reader = new BufferedReader(new FileReader("/proc/vmstat"));

            while(true) {
               String[] parts;
               long value;
               label138:
               while(true) {
                  while(true) {
                     String inputLine;
                     int length;
                     do {
                        if ((inputLine = reader.readLine()) == null) {
                           return numSet;
                        }

                        length = inputLine.length();
                     } while(length == 0);

                     parts = inputLine.split(" ");
                     if (parts.length == 2 && parts[0].length() != 0 && parts[1].length() != 0) {
                        value = 0L;

                        try {
                           value = Long.parseLong(parts[1]);
                           break label138;
                        } catch (NumberFormatException var15) {
                           if (LOGGER.isLoggable(Level.FINER)) {
                              LOGGER.finer("Skipping(3): " + inputLine);
                           }
                        } catch (Exception var16) {
                           if (LOGGER.isLoggable(Level.FINER)) {
                              var16.printStackTrace();
                              LOGGER.finer("Skipping(3b): " + inputLine);
                           }
                        }
                     } else if (LOGGER.isLoggable(Level.FINER)) {
                        LOGGER.finer("Skipping(1): " + inputLine);
                     }
                  }
               }

               this.setNamedValue(parts[0], value);
               ++numSet;
            }
         } finally {
            if (reader != null) {
               reader.close();
            }

         }
      }
   }

   private void setNamedValue(String name, long value) {
      if ("nr_anon_pages".equalsIgnoreCase(name)) {
         this.nrAnonPages = value;
      } else if ("nr_mapped".equalsIgnoreCase(name)) {
         this.nrMapped = value;
      } else if ("nr_file_pages".equalsIgnoreCase(name)) {
         this.nrFilePages = value;
      } else if ("nr_slab".equalsIgnoreCase(name)) {
         this.nrSlab = value;
      } else if ("nr_page_table_pages".equalsIgnoreCase(name)) {
         this.nrPageTablePages = value;
      } else if ("nr_dirty".equalsIgnoreCase(name)) {
         this.nrDirty = value;
      } else if ("nr_writeback".equalsIgnoreCase(name)) {
         this.nrWriteback = value;
      } else if ("nr_unstable".equalsIgnoreCase(name)) {
         this.nrUnstable = value;
      } else if ("nr_bounce".equalsIgnoreCase(name)) {
         this.nrBounce = value;
      } else if ("numa_hit".equalsIgnoreCase(name)) {
         this.numaHit = value;
      } else if ("numa_miss".equalsIgnoreCase(name)) {
         this.numaMiss = value;
      } else if ("numa_foreign".equalsIgnoreCase(name)) {
         this.numaForeign = value;
      } else if ("numa_interleave".equalsIgnoreCase(name)) {
         this.numaInterleave = value;
      } else if ("numa_local".equalsIgnoreCase(name)) {
         this.numaLocal = value;
      } else if ("numa_other".equalsIgnoreCase(name)) {
         this.numaOther = value;
      } else {
         long deltaValue;
         if ("pgpgin".equalsIgnoreCase(name)) {
            if (this.pgpgin == -1L) {
               this.pgpginPerSec = 0L;
            } else if (this.deltaSecs > 0.0) {
               deltaValue = value - this.pgpgin;
               this.pgpginPerSec = (long)((double)deltaValue / this.deltaSecs);
               if (this.pgpginPerSec > 0L) {
                  ++this.pgpginPerSecConsecutiveTrailingIntervalsOverZero;
               } else {
                  this.pgpginPerSecConsecutiveTrailingIntervalsOverZero = 0L;
               }
            } else {
               this.pgpginPerSec = -1L;
            }

            this.pgpgin = value;
         } else if ("pgpgout".equalsIgnoreCase(name)) {
            if (this.pgpgout == -1L) {
               this.pgpgoutPerSec = 0L;
            } else if (this.deltaSecs > 0.0) {
               deltaValue = value - this.pgpgout;
               this.pgpgoutPerSec = (long)((double)deltaValue / this.deltaSecs);
               if (this.pgpgoutPerSec > 0L) {
                  ++this.pgpgoutPerSecConsecutiveTrailingIntervalsOverZero;
               } else {
                  this.pgpgoutPerSecConsecutiveTrailingIntervalsOverZero = 0L;
               }
            } else {
               this.pgpgoutPerSec = -1L;
            }

            this.pgpgout = value;
         } else if ("pswpin".equalsIgnoreCase(name)) {
            if (this.pswpin == -1L) {
               this.pswpinPerSec = 0L;
            } else if (this.deltaSecs > 0.0) {
               deltaValue = value - this.pswpin;
               this.pswpinPerSec = (long)((double)deltaValue / this.deltaSecs);
               if (this.pswpinPerSec > 0L) {
                  ++this.pswpinPerSecConsecutiveTrailingIntervalsOverZero;
               } else {
                  this.pswpinPerSecConsecutiveTrailingIntervalsOverZero = 0L;
               }
            } else {
               this.pswpinPerSec = -1L;
            }

            this.pswpin = value;
         } else if ("pswpout".equalsIgnoreCase(name)) {
            if (this.pswpout == -1L) {
               this.pswpoutPerSec = 0L;
            } else if (this.deltaSecs > 0.0) {
               deltaValue = value - this.pswpout;
               this.pswpoutPerSec = (long)((double)deltaValue / this.deltaSecs);
               if (this.pswpoutPerSec > 0L) {
                  ++this.pswpoutPerSecConsecutiveTrailingIntervalsOverZero;
               } else {
                  this.pswpoutPerSecConsecutiveTrailingIntervalsOverZero = 0L;
               }
            } else {
               this.pswpoutPerSec = -1L;
            }

            this.pswpout = value;
         } else if ("pgalloc_dma".equalsIgnoreCase(name)) {
            this.pgallocDma = value;
         } else if ("pgalloc_dma32".equalsIgnoreCase(name)) {
            this.pgallocDma32 = value;
         } else if ("pgalloc_normal".equalsIgnoreCase(name)) {
            this.pgallocNormal = value;
         } else if ("pgalloc_high".equalsIgnoreCase(name)) {
            this.pgallocHigh = value;
         } else if ("pgfree".equalsIgnoreCase(name)) {
            this.pgfree = value;
         } else if ("pgactivate".equalsIgnoreCase(name)) {
            this.pgactivate = value;
         } else if ("pgdeactivate".equalsIgnoreCase(name)) {
            this.pgdeactivate = value;
         } else if ("pgfault".equalsIgnoreCase(name)) {
            this.pgfault = value;
         } else if ("pgmajfault".equalsIgnoreCase(name)) {
            this.pgmajfault = value;
         } else if ("pgrefill_dma".equalsIgnoreCase(name)) {
            this.pgrefillDma = value;
         } else if ("pgrefill_dma32".equalsIgnoreCase(name)) {
            this.pgrefillDma32 = value;
         } else if ("pgrefill_normal".equalsIgnoreCase(name)) {
            this.pgrefillNormal = value;
         } else if ("pgrefill_high".equalsIgnoreCase(name)) {
            this.pgrefillHigh = value;
         } else if ("pgsteal_dma".equalsIgnoreCase(name)) {
            this.pgstealDma = value;
         } else if ("pgsteal_dma32".equalsIgnoreCase(name)) {
            this.pgstealDma32 = value;
         } else if ("pgsteal_normal".equalsIgnoreCase(name)) {
            this.pgstealNormal = value;
         } else if ("pgsteal_high".equalsIgnoreCase(name)) {
            this.pgstealHigh = value;
         } else if ("pgscan_kswapd_dma".equalsIgnoreCase(name)) {
            if (this.pgscanKswapdDma == -1L) {
               this.pgscanKswapdDmaPerSec = 0L;
            } else if (this.deltaSecs > 0.0) {
               deltaValue = value - this.pgscanKswapdDma;
               this.pgscanKswapdDmaPerSec = (long)((double)deltaValue / this.deltaSecs);
            } else {
               this.pgscanKswapdDmaPerSec = -1L;
            }

            this.pgscanKswapdDma = value;
         } else if ("pgscan_kswapd_dma32".equalsIgnoreCase(name)) {
            if (this.pgscanKswapdDma32 == -1L) {
               this.pgscanKswapdDma32PerSec = 0L;
            } else if (this.deltaSecs > 0.0) {
               deltaValue = value - this.pgscanKswapdDma32;
               this.pgscanKswapdDma32PerSec = (long)((double)deltaValue / this.deltaSecs);
            } else {
               this.pgscanKswapdDma32PerSec = -1L;
            }

            this.pgscanKswapdDma32 = value;
         } else if ("pgscan_kswapd_normal".equalsIgnoreCase(name)) {
            if (this.pgscanKswapdNormal == -1L) {
               this.pgscanKswapdNormalPerSec = 0L;
            } else if (this.deltaSecs > 0.0) {
               deltaValue = value - this.pgscanKswapdNormal;
               this.pgscanKswapdNormalPerSec = (long)((double)deltaValue / this.deltaSecs);
            } else {
               this.pgscanKswapdNormalPerSec = -1L;
            }

            this.pgscanKswapdNormal = value;
         } else if ("pgscan_kswapd_high".equalsIgnoreCase(name)) {
            if (this.pgscanKswapdHigh == -1L) {
               this.pgscanKswapdHighPerSec = 0L;
            } else if (this.deltaSecs > 0.0) {
               deltaValue = value - this.pgscanKswapdHigh;
               this.pgscanKswapdHighPerSec = (long)((double)deltaValue / this.deltaSecs);
            } else {
               this.pgscanKswapdHighPerSec = -1L;
            }

            this.pgscanKswapdHigh = value;
         } else if ("pgscan_direct_dma".equalsIgnoreCase(name)) {
            if (this.pgscanDirectDma == -1L) {
               this.pgscanDirectDmaPerSec = 0L;
            } else if (this.deltaSecs > 0.0) {
               deltaValue = value - this.pgscanDirectDma;
               this.pgscanDirectDmaPerSec = (long)((double)deltaValue / this.deltaSecs);
            } else {
               this.pgscanDirectDmaPerSec = -1L;
            }

            this.pgscanDirectDma = value;
         } else if ("pgscan_direct_dma32".equalsIgnoreCase(name)) {
            if (this.pgscanDirectDma32 == -1L) {
               this.pgscanDirectDma32PerSec = 0L;
            } else if (this.deltaSecs > 0.0) {
               deltaValue = value - this.pgscanDirectDma32;
               this.pgscanDirectDma32PerSec = (long)((double)deltaValue / this.deltaSecs);
            } else {
               this.pgscanDirectDma32PerSec = -1L;
            }

            this.pgscanDirectDma32 = value;
         } else if ("pgscan_direct_normal".equalsIgnoreCase(name)) {
            if (this.pgscanDirectNormal == -1L) {
               this.pgscanDirectNormalPerSec = 0L;
            } else if (this.deltaSecs > 0.0) {
               deltaValue = value - this.pgscanDirectNormal;
               this.pgscanDirectNormalPerSec = (long)((double)deltaValue / this.deltaSecs);
            } else {
               this.pgscanDirectNormalPerSec = -1L;
            }

            this.pgscanDirectNormal = value;
         } else if ("pgscan_direct_high".equalsIgnoreCase(name)) {
            if (this.pgscanDirectHigh == -1L) {
               this.pgscanDirectHighPerSec = 0L;
            } else if (this.deltaSecs > 0.0) {
               deltaValue = value - this.pgscanDirectHigh;
               this.pgscanDirectHighPerSec = (long)((double)deltaValue / this.deltaSecs);
            } else {
               this.pgscanDirectHighPerSec = -1L;
            }

            this.pgscanDirectHigh = value;
         } else if ("pginodesteal".equalsIgnoreCase(name)) {
            this.pginodesteal = value;
         } else if ("slabs_scanned".equalsIgnoreCase(name)) {
            if (this.slabsScanned == -1L) {
               this.slabsScannedPerSec = 0L;
            } else if (this.deltaSecs > 0.0) {
               deltaValue = value - this.slabsScanned;
               this.slabsScannedPerSec = (long)((double)deltaValue / this.deltaSecs);
            } else {
               this.slabsScannedPerSec = -1L;
            }

            this.slabsScanned = value;
         } else if ("kswapd_steal".equalsIgnoreCase(name)) {
            this.kswapdSteal = value;
         } else if ("kswapd_inodesteal".equalsIgnoreCase(name)) {
            this.kswapdInodesteal = value;
         } else if ("pageoutrun".equalsIgnoreCase(name)) {
            this.pageoutrun = value;
         } else if ("allocstall".equalsIgnoreCase(name)) {
            this.allocstall = value;
         } else if ("pgrotated".equalsIgnoreCase(name)) {
            this.pgrotated = value;
         } else {
            if (LOGGER.isLoggable(Level.FINER)) {
               LOGGER.finer("unknown metric value: " + name + " : " + value);
            }

         }
      }
   }

   public String toString() {
      if (!this.vmstatFound) {
         return "/proc/vmstat was not found";
      } else {
         StringBuffer sb = new StringBuffer();
         sb.append("nrAnonPages:              " + this.nrAnonPages);
         sb.append("\nnrMapped:                 " + this.nrMapped);
         sb.append("\nnrFilePages:              " + this.nrFilePages);
         sb.append("\nnrSlab:                   " + this.nrSlab);
         sb.append("\nnrPageTablePages:         " + this.nrPageTablePages);
         sb.append("\nnrDirty:                  " + this.nrDirty);
         sb.append("\nnrWriteback:              " + this.nrWriteback);
         sb.append("\nnrUnstable:               " + this.nrUnstable);
         sb.append("\nnrBounce:                 " + this.nrBounce);
         sb.append("\nnumaHit:                  " + this.numaHit);
         sb.append("\nnumaMiss:                 " + this.numaMiss);
         sb.append("\nnumaForeign:              " + this.numaForeign);
         sb.append("\nnumaInterleave:           " + this.numaInterleave);
         sb.append("\nnumaLocal:                " + this.numaLocal);
         sb.append("\nnumaOther:                " + this.numaOther);
         sb.append("\npgpgin:                   " + this.pgpgin);
         sb.append("\npgpginPerSec:             " + this.pgpginPerSec);
         sb.append("\npgpgout:                  " + this.pgpgout);
         sb.append("\npgpgoutPerSec:            " + this.pgpgoutPerSec);
         sb.append("\npswpin:                   " + this.pswpin);
         sb.append("\npswpinPerSec:             " + this.pswpinPerSec);
         sb.append("\npswpout:                  " + this.pswpout);
         sb.append("\npswpoutPerSec:            " + this.pswpoutPerSec);
         sb.append("\npgallocDma:               " + this.pgallocDma);
         sb.append("\npgallocDma32:             " + this.pgallocDma32);
         sb.append("\npgallocNormal:            " + this.pgallocNormal);
         sb.append("\npgallocHigh:              " + this.pgallocHigh);
         sb.append("\npgfree:                   " + this.pgfree);
         sb.append("\npgactivate:               " + this.pgactivate);
         sb.append("\npgdeactivate:             " + this.pgdeactivate);
         sb.append("\npgfault:                  " + this.pgfault);
         sb.append("\npgmajfault:               " + this.pgmajfault);
         sb.append("\npgrefillDma:              " + this.pgrefillDma);
         sb.append("\npgrefillDma32:            " + this.pgrefillDma32);
         sb.append("\npgrefillNormal:           " + this.pgrefillNormal);
         sb.append("\npgrefillHigh:             " + this.pgrefillHigh);
         sb.append("\npgstealDma:               " + this.pgstealDma);
         sb.append("\npgstealDma32:             " + this.pgstealDma32);
         sb.append("\npgstealNormal:            " + this.pgstealNormal);
         sb.append("\npgstealHigh:              " + this.pgstealHigh);
         sb.append("\npgscanKswapdDma:          " + this.pgscanKswapdDma);
         sb.append("\npgscanKswapdDmaPerSec:    " + this.pgscanKswapdDmaPerSec);
         sb.append("\npgscanKswapdDma32:        " + this.pgscanKswapdDma32);
         sb.append("\npgscanKswapdDma32PerSec:  " + this.pgscanKswapdDma32PerSec);
         sb.append("\npgscanKswapdNormal:       " + this.pgscanKswapdNormal);
         sb.append("\npgscanKswapdNormalPerSec: " + this.pgscanKswapdNormalPerSec);
         sb.append("\npgscanKswapdHigh:         " + this.pgscanKswapdHigh);
         sb.append("\npgscanKswapdHighPerSec:   " + this.pgscanKswapdHighPerSec);
         sb.append("\npgscanDirectDma:          " + this.pgscanDirectDma);
         sb.append("\npgscanDirectDmaPerSec:    " + this.pgscanDirectDmaPerSec);
         sb.append("\npgscanDirectDma32:        " + this.pgscanDirectDma32);
         sb.append("\npgscanDirectDma32PerSec:  " + this.pgscanDirectDma32PerSec);
         sb.append("\npgscanDirectNormal:       " + this.pgscanDirectNormal);
         sb.append("\npgscanDirectNormalPerSec: " + this.pgscanDirectNormalPerSec);
         sb.append("\npgscanDirectHigh:         " + this.pgscanDirectHigh);
         sb.append("\npgscanDirectHighPerSec:   " + this.pgscanDirectHighPerSec);
         sb.append("\npginodesteal:             " + this.pginodesteal);
         sb.append("\nslabsScanned:             " + this.slabsScanned);
         sb.append("\nslabsScannedPerSec:       " + this.slabsScannedPerSec);
         sb.append("\nkswapdSteal:              " + this.kswapdSteal);
         sb.append("\nkswapdInodesteal:         " + this.kswapdInodesteal);
         sb.append("\npageoutrun:               " + this.pageoutrun);
         sb.append("\nallocstall:               " + this.allocstall);
         sb.append("\npgrotated:                " + this.pgrotated);
         return sb.toString();
      }
   }

   public static void main(String[] args) {
      try {
         ProcVmstat vmstat = new ProcVmstat();
         vmstat.refresh();
         LOGGER.info("/proc/vmstat:\n" + vmstat.toString());
      } catch (IOException var2) {
         if (LOGGER.isLoggable(Level.FINER)) {
            var2.printStackTrace();
         }
      }

   }
}
