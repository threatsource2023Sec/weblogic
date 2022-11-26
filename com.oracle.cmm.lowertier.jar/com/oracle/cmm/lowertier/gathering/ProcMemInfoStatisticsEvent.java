package com.oracle.cmm.lowertier.gathering;

import com.oracle.jrockit.jfr.EventDefinition;
import com.oracle.jrockit.jfr.InstantEvent;
import com.oracle.jrockit.jfr.ValueDefinition;

@EventDefinition(
   name = "Proc Mem Info Statistics",
   description = "Statistics gathered for /proc/meminfo",
   path = "oracle/cmm/lowertier/Proc_Mem_Info_Statistics",
   thread = false,
   stacktrace = false
)
public class ProcMemInfoStatisticsEvent extends InstantEvent {
   @ValueDefinition(
      name = "MemTotal",
      description = ""
   )
   public long memTotal;
   @ValueDefinition(
      name = "MemFree",
      description = ""
   )
   public long memFree;
   @ValueDefinition(
      name = "Buffers",
      description = ""
   )
   public long buffers;
   @ValueDefinition(
      name = "Cached",
      description = ""
   )
   public long cached;
   @ValueDefinition(
      name = "SwapCached",
      description = ""
   )
   public long swapCached;
   @ValueDefinition(
      name = "Active",
      description = ""
   )
   public long active;
   @ValueDefinition(
      name = "Inactive",
      description = ""
   )
   public long inactive;
   @ValueDefinition(
      name = "HighTotal",
      description = ""
   )
   public long highTotal;
   @ValueDefinition(
      name = "HighFree",
      description = ""
   )
   public long highFree;
   @ValueDefinition(
      name = "LowTotal",
      description = ""
   )
   public long lowTotal;
   @ValueDefinition(
      name = "LowFree",
      description = ""
   )
   public long lowFree;
   @ValueDefinition(
      name = "SwapTotal",
      description = ""
   )
   public long swapTotal;
   @ValueDefinition(
      name = "SwapFree",
      description = ""
   )
   public long swapFree;
   @ValueDefinition(
      name = "Dirty",
      description = ""
   )
   public long dirty;
   @ValueDefinition(
      name = "WriteBack",
      description = ""
   )
   public long writeBack;
   @ValueDefinition(
      name = "AnonPages",
      description = ""
   )
   public long anonPages;
   @ValueDefinition(
      name = "Mapped",
      description = ""
   )
   public long mapped;
   @ValueDefinition(
      name = "Slab",
      description = ""
   )
   public long slab;
   @ValueDefinition(
      name = "PageTables",
      description = ""
   )
   public long pageTables;
   @ValueDefinition(
      name = "NfsUnstable",
      description = ""
   )
   public long nfsUnstable;
   @ValueDefinition(
      name = "Bounce",
      description = ""
   )
   public long bounce;
   @ValueDefinition(
      name = "CommitLimit",
      description = ""
   )
   public long commitLimit;
   @ValueDefinition(
      name = "CommittedAS",
      description = ""
   )
   public long committedAS;
   @ValueDefinition(
      name = "VmallocTotal",
      description = ""
   )
   public long vmallocTotal;
   @ValueDefinition(
      name = "VmallocUsed",
      description = ""
   )
   public long vmallocUsed;
   @ValueDefinition(
      name = "VmallocChunk",
      description = ""
   )
   public long vmallocChunk;
   @ValueDefinition(
      name = "HugePagesTotal",
      description = ""
   )
   public long hugePagesTotal;
   @ValueDefinition(
      name = "HugePagesFree",
      description = ""
   )
   public long hugePagesFree;
   @ValueDefinition(
      name = "HugePagesRsvd",
      description = ""
   )
   public long hugePagesRsvd;
   @ValueDefinition(
      name = "HugePagesize",
      description = ""
   )
   public long hugePagesize;

   void populate(ProcMemInfo procMemInfo) {
      this.memTotal = procMemInfo.memTotal / 1024L;
      this.memFree = procMemInfo.memFree / 1024L;
      this.buffers = procMemInfo.buffers / 1024L;
      this.cached = procMemInfo.cached / 1024L;
      this.swapCached = procMemInfo.swapCached / 1024L;
      this.active = procMemInfo.active / 1024L;
      this.inactive = procMemInfo.inactive / 1024L;
      this.highTotal = procMemInfo.highTotal / 1024L;
      this.highFree = procMemInfo.highFree / 1024L;
      this.lowTotal = procMemInfo.lowTotal / 1024L;
      this.lowFree = procMemInfo.lowFree / 1024L;
      this.swapTotal = procMemInfo.swapTotal / 1024L;
      this.swapFree = procMemInfo.swapFree / 1024L;
      this.dirty = procMemInfo.dirty / 1024L;
      this.writeBack = procMemInfo.writeBack / 1024L;
      this.anonPages = procMemInfo.anonPages / 1024L;
      this.mapped = procMemInfo.mapped / 1024L;
      this.slab = procMemInfo.slab / 1024L;
      this.pageTables = procMemInfo.pageTables / 1024L;
      this.nfsUnstable = procMemInfo.nfsUnstable / 1024L;
      this.bounce = procMemInfo.bounce / 1024L;
      this.commitLimit = procMemInfo.commitLimit / 1024L;
      this.committedAS = procMemInfo.committedAS / 1024L;
      this.vmallocTotal = procMemInfo.vmallocTotal / 1024L;
      this.vmallocUsed = procMemInfo.vmallocUsed / 1024L;
      this.vmallocChunk = procMemInfo.vmallocChunk / 1024L;
      this.hugePagesTotal = procMemInfo.hugePagesTotal;
      this.hugePagesFree = procMemInfo.hugePagesFree;
      this.hugePagesRsvd = procMemInfo.hugePagesRsvd;
      this.hugePagesize = procMemInfo.hugePagesize / 1024L;
   }
}
