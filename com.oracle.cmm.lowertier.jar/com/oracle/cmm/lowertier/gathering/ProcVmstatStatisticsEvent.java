package com.oracle.cmm.lowertier.gathering;

import com.oracle.jrockit.jfr.EventDefinition;
import com.oracle.jrockit.jfr.InstantEvent;
import com.oracle.jrockit.jfr.ValueDefinition;

@EventDefinition(
   name = "Proc Vmstat Statistics",
   description = "Statistics gathered for /proc/vmstat",
   path = "oracle/cmm/lowertier/Proc_Vmstat_Statistics",
   thread = false,
   stacktrace = false
)
public class ProcVmstatStatisticsEvent extends InstantEvent {
   @ValueDefinition(
      name = "previousTimestamp",
      description = ""
   )
   public long previousTimestamp;
   @ValueDefinition(
      name = "deltaSecs",
      description = ""
   )
   public double deltaSecs;
   @ValueDefinition(
      name = "pgpginPerSec",
      description = ""
   )
   public long pgpginPerSec;
   @ValueDefinition(
      name = "pgpginPerSecConsecutiveTrailingIntervalsOverZero",
      description = "The number of trailing consecutive intervals where pgpginPerSec was greater than 0"
   )
   public long pgpginPerSecConsecutiveTrailingIntervalsOverZero;
   @ValueDefinition(
      name = "pgpgoutPerSec ",
      description = ""
   )
   public long pgpgoutPerSec;
   @ValueDefinition(
      name = "pgpgoutPerSecConsecutiveTrailingIntervalsOverZero",
      description = "The number of trailing consecutive intervals where pgpgoutPerSec was greater than 0"
   )
   public long pgpgoutPerSecConsecutiveTrailingIntervalsOverZero;
   @ValueDefinition(
      name = "pswpinPerSec",
      description = ""
   )
   public long pswpinPerSec;
   @ValueDefinition(
      name = "pswpinPerSecConsecutiveTrailingIntervalsOverZero",
      description = "The number of trailing consecutive intervals where pswpinPerSec was greater than 0"
   )
   public long pswpinPerSecConsecutiveTrailingIntervalsOverZero;
   @ValueDefinition(
      name = "pswpoutPerSec ",
      description = ""
   )
   public long pswpoutPerSec;
   @ValueDefinition(
      name = "pswpoutPerSecConsecutiveTrailingIntervalsOverZero",
      description = "The number of trailing consecutive intervals where pswpoutPerSec was greater than 0"
   )
   public long pswpoutPerSecConsecutiveTrailingIntervalsOverZero;
   @ValueDefinition(
      name = "nrAnonPages",
      description = ""
   )
   public long nrAnonPages;
   @ValueDefinition(
      name = "nrMapped",
      description = ""
   )
   public long nrMapped;
   @ValueDefinition(
      name = "nrFilePages",
      description = ""
   )
   public long nrFilePages;
   @ValueDefinition(
      name = "nrSlab",
      description = ""
   )
   public long nrSlab;
   @ValueDefinition(
      name = "nrPageTablePages",
      description = ""
   )
   public long nrPageTablePages;
   @ValueDefinition(
      name = "nrDirty",
      description = ""
   )
   public long nrDirty;
   @ValueDefinition(
      name = "nrWriteback",
      description = ""
   )
   public long nrWriteback;
   @ValueDefinition(
      name = "nrUnstable",
      description = ""
   )
   public long nrUnstable;
   @ValueDefinition(
      name = "nrBounce",
      description = ""
   )
   public long nrBounce;
   @ValueDefinition(
      name = "numaHit",
      description = ""
   )
   public long numaHit;
   @ValueDefinition(
      name = "numaMiss",
      description = ""
   )
   public long numaMiss;
   @ValueDefinition(
      name = "numaForeign",
      description = ""
   )
   public long numaForeign;
   @ValueDefinition(
      name = "numaInterleave",
      description = ""
   )
   public long numaInterleave;
   @ValueDefinition(
      name = "numaLocal",
      description = ""
   )
   public long numaLocal;
   @ValueDefinition(
      name = "numaOther",
      description = ""
   )
   public long numaOther;
   @ValueDefinition(
      name = "pgpgin",
      description = ""
   )
   public long pgpgin;
   @ValueDefinition(
      name = "pgpgout",
      description = ""
   )
   public long pgpgout;
   @ValueDefinition(
      name = "pswpin",
      description = ""
   )
   public long pswpin;
   @ValueDefinition(
      name = "pswpout",
      description = ""
   )
   public long pswpout;
   @ValueDefinition(
      name = "pgallocDma",
      description = ""
   )
   public long pgallocDma;
   @ValueDefinition(
      name = "pgallocDma32",
      description = ""
   )
   public long pgallocDma32;
   @ValueDefinition(
      name = "pgallocNormal",
      description = ""
   )
   public long pgallocNormal;
   @ValueDefinition(
      name = "pgallocHigh",
      description = ""
   )
   public long pgallocHigh;
   @ValueDefinition(
      name = "pgfree",
      description = ""
   )
   public long pgfree;
   @ValueDefinition(
      name = "pgactivate",
      description = ""
   )
   public long pgactivate;
   @ValueDefinition(
      name = "pgdeactivate",
      description = ""
   )
   public long pgdeactivate;
   @ValueDefinition(
      name = "pgfault",
      description = ""
   )
   public long pgfault;
   @ValueDefinition(
      name = "pgmajfault",
      description = ""
   )
   public long pgmajfault;
   @ValueDefinition(
      name = "pgrefillDma",
      description = ""
   )
   public long pgrefillDma;
   @ValueDefinition(
      name = "pgrefillDma32",
      description = ""
   )
   public long pgrefillDma32;
   @ValueDefinition(
      name = "pgrefillNormal",
      description = ""
   )
   public long pgrefillNormal;
   @ValueDefinition(
      name = "pgrefillHigh",
      description = ""
   )
   public long pgrefillHigh;
   @ValueDefinition(
      name = "pgstealDma",
      description = ""
   )
   public long pgstealDma;
   @ValueDefinition(
      name = "pgstealDma32",
      description = ""
   )
   public long pgstealDma32;
   @ValueDefinition(
      name = "pgstealNormal",
      description = ""
   )
   public long pgstealNormal;
   @ValueDefinition(
      name = "pgstealHigh",
      description = ""
   )
   public long pgstealHigh;
   @ValueDefinition(
      name = "pgscanKswapdDma",
      description = ""
   )
   public long pgscanKswapdDma;
   @ValueDefinition(
      name = "pgscanKswapdDmaPerSec",
      description = ""
   )
   public long pgscanKswapdDmaPerSec;
   @ValueDefinition(
      name = "pgscanKswapdDma32",
      description = ""
   )
   public long pgscanKswapdDma32;
   @ValueDefinition(
      name = "pgscanKswapdDma32PerSec",
      description = ""
   )
   public long pgscanKswapdDma32PerSec;
   @ValueDefinition(
      name = "pgscanKswapdNormal",
      description = ""
   )
   public long pgscanKswapdNormal;
   @ValueDefinition(
      name = "pgscanKswapdNormalPerSec",
      description = ""
   )
   public long pgscanKswapdNormalPerSec;
   @ValueDefinition(
      name = "pgscanKswapdHigh",
      description = ""
   )
   public long pgscanKswapdHigh;
   @ValueDefinition(
      name = "pgscanKswapdHighPerSec",
      description = ""
   )
   public long pgscanKswapdHighPerSec;
   @ValueDefinition(
      name = "pgscanDirectDma",
      description = ""
   )
   public long pgscanDirectDma;
   @ValueDefinition(
      name = "pgscanDirectDmaPerSec",
      description = ""
   )
   public long pgscanDirectDmaPerSec;
   @ValueDefinition(
      name = "pgscanDirectDma32",
      description = ""
   )
   public long pgscanDirectDma32;
   @ValueDefinition(
      name = "pgscanDirectDma32PerSec",
      description = ""
   )
   public long pgscanDirectDma32PerSec;
   @ValueDefinition(
      name = "pgscanDirectNormal",
      description = ""
   )
   public long pgscanDirectNormal;
   @ValueDefinition(
      name = "pgscanDirectNormalPerSec",
      description = ""
   )
   public long pgscanDirectNormalPerSec;
   @ValueDefinition(
      name = "pgscanDirectHigh",
      description = ""
   )
   public long pgscanDirectHigh;
   @ValueDefinition(
      name = "pgscanDirectHighPerSec",
      description = ""
   )
   public long pgscanDirectHighPerSec;
   @ValueDefinition(
      name = "pginodesteal",
      description = ""
   )
   public long pginodesteal;
   @ValueDefinition(
      name = "slabsScanned",
      description = ""
   )
   public long slabsScanned;
   @ValueDefinition(
      name = "slabsScannedPerSec",
      description = ""
   )
   public long slabsScannedPerSec;
   @ValueDefinition(
      name = "kswapdSteal",
      description = ""
   )
   public long kswapdSteal;
   @ValueDefinition(
      name = "kswapdInodesteal",
      description = ""
   )
   public long kswapdInodesteal;
   @ValueDefinition(
      name = "pageoutrun",
      description = ""
   )
   public long pageoutrun;
   @ValueDefinition(
      name = "allocstall",
      description = ""
   )
   public long allocstall;
   @ValueDefinition(
      name = "pgrotated",
      description = ""
   )
   public long pgrotated;

   void populate(ProcVmstat procVmstat) {
      this.nrAnonPages = procVmstat.nrAnonPages;
      this.nrMapped = procVmstat.nrMapped;
      this.nrFilePages = procVmstat.nrFilePages;
      this.nrSlab = procVmstat.nrSlab;
      this.nrPageTablePages = procVmstat.nrPageTablePages;
      this.nrDirty = procVmstat.nrDirty;
      this.nrWriteback = procVmstat.nrWriteback;
      this.nrUnstable = procVmstat.nrUnstable;
      this.nrBounce = procVmstat.nrBounce;
      this.numaHit = procVmstat.numaHit;
      this.numaMiss = procVmstat.numaMiss;
      this.numaForeign = procVmstat.numaForeign;
      this.numaInterleave = procVmstat.numaInterleave;
      this.numaLocal = procVmstat.numaLocal;
      this.numaOther = procVmstat.numaOther;
      this.pgpgin = procVmstat.pgpgin;
      this.pgpginPerSec = procVmstat.pgpginPerSec;
      this.pgpginPerSecConsecutiveTrailingIntervalsOverZero = procVmstat.pgpginPerSecConsecutiveTrailingIntervalsOverZero;
      this.pgpgout = procVmstat.pgpgout;
      this.pgpgoutPerSec = procVmstat.pgpgoutPerSec;
      this.pgpgoutPerSecConsecutiveTrailingIntervalsOverZero = procVmstat.pgpgoutPerSecConsecutiveTrailingIntervalsOverZero;
      this.pswpin = procVmstat.pswpin;
      this.pswpinPerSec = procVmstat.pswpinPerSec;
      this.pswpinPerSecConsecutiveTrailingIntervalsOverZero = procVmstat.pswpinPerSecConsecutiveTrailingIntervalsOverZero;
      this.pswpout = procVmstat.pswpout;
      this.pswpoutPerSec = procVmstat.pswpoutPerSec;
      this.pswpoutPerSecConsecutiveTrailingIntervalsOverZero = procVmstat.pswpoutPerSecConsecutiveTrailingIntervalsOverZero;
      this.pgallocDma = procVmstat.pgallocDma;
      this.pgallocDma32 = procVmstat.pgallocDma32;
      this.pgallocNormal = procVmstat.pgallocNormal;
      this.pgallocHigh = procVmstat.pgallocHigh;
      this.pgfree = procVmstat.pgfree;
      this.pgactivate = procVmstat.pgactivate;
      this.pgdeactivate = procVmstat.pgdeactivate;
      this.pgfault = procVmstat.pgfault;
      this.pgmajfault = procVmstat.pgmajfault;
      this.pgrefillDma = procVmstat.pgrefillDma;
      this.pgrefillDma32 = procVmstat.pgrefillDma32;
      this.pgrefillNormal = procVmstat.pgrefillNormal;
      this.pgrefillHigh = procVmstat.pgrefillHigh;
      this.pgstealDma = procVmstat.pgstealDma;
      this.pgstealDma32 = procVmstat.pgstealDma32;
      this.pgstealNormal = procVmstat.pgstealNormal;
      this.pgstealHigh = procVmstat.pgstealHigh;
      this.pgscanKswapdDma = procVmstat.pgscanKswapdDma;
      this.pgscanKswapdDmaPerSec = procVmstat.pgscanKswapdDmaPerSec;
      this.pgscanKswapdDma32 = procVmstat.pgscanKswapdDma32;
      this.pgscanKswapdDma32PerSec = procVmstat.pgscanKswapdDma32PerSec;
      this.pgscanKswapdNormal = procVmstat.pgscanKswapdNormal;
      this.pgscanKswapdNormalPerSec = procVmstat.pgscanKswapdNormalPerSec;
      this.pgscanKswapdHigh = procVmstat.pgscanKswapdHigh;
      this.pgscanKswapdHighPerSec = procVmstat.pgscanKswapdHighPerSec;
      this.pgscanDirectDma = procVmstat.pgscanDirectDma;
      this.pgscanDirectDmaPerSec = procVmstat.pgscanDirectDmaPerSec;
      this.pgscanDirectDma32 = procVmstat.pgscanDirectDma32;
      this.pgscanDirectDma32PerSec = procVmstat.pgscanDirectDma32PerSec;
      this.pgscanDirectNormal = procVmstat.pgscanDirectNormal;
      this.pgscanDirectNormalPerSec = procVmstat.pgscanDirectNormalPerSec;
      this.pgscanDirectHigh = procVmstat.pgscanDirectHigh;
      this.pgscanDirectHighPerSec = procVmstat.pgscanDirectHighPerSec;
      this.pginodesteal = procVmstat.pginodesteal;
      this.slabsScanned = procVmstat.slabsScanned;
      this.slabsScannedPerSec = procVmstat.slabsScannedPerSec;
      this.kswapdSteal = procVmstat.kswapdSteal;
      this.kswapdInodesteal = procVmstat.kswapdInodesteal;
      this.pageoutrun = procVmstat.pageoutrun;
      this.allocstall = procVmstat.allocstall;
      this.pgrotated = procVmstat.pgrotated;
   }
}
