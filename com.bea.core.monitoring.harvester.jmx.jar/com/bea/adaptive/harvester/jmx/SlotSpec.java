package com.bea.adaptive.harvester.jmx;

import com.bea.adaptive.harvester.WatchedValues;

public class SlotSpec {
   private static HarvesterJMXTextTextFormatter mtf_base = HarvesterJMXTextTextFormatter.getInstance();
   private WatchedValues.Values slot;
   int wvid;

   protected SlotSpec(WatchedValues.Values slot, int wvid) {
      this.slot = slot;
      this.wvid = wvid;
   }

   public WatchedValues.Values getSlot() {
      return this.slot;
   }

   public int getWVID() {
      return this.wvid;
   }

   public int hashCode() {
      return this.slot.hashCode();
   }

   public boolean equals(Object o) {
      if (!(o instanceof SlotSpec)) {
         return false;
      } else {
         SlotSpec other = (SlotSpec)o;
         return this.slot.equals(other.slot);
      }
   }

   public String dump(String indent, SlotKey key) {
      StringBuffer buf = new StringBuffer(128);
      buf.append(indent + mtf_base.getMetricDataLabel() + ":").append(indent).append("  ").append(mtf_base.getTypeNameLabel()).append(":").append(key.getTypeName()).append("  ").append(mtf_base.getInstNameLabel()).append(":").append(key.getInstanceName()).append("  ").append(mtf_base.getAttrNameLabel()).append(":").append(key.getAttributeSpec().getName()).append("  ").append(mtf_base.getWVIDLabel()).append(":     ").append(this.wvid).append("  ").append(mtf_base.getSlotDataLabel()).append(":").append(this.slot.dump(indent + "    ", true, false));
      return buf.toString();
   }
}
