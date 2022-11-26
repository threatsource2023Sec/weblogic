package com.solarmetric.profile;

import com.solarmetric.manage.SampleStatistic;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.Iterator;
import org.apache.commons.collections.map.ListOrderedMap;

public class Node implements Serializable {
   private static final long serialVersionUID = 1L;
   private ListOrderedMap _children = new ListOrderedMap();
   private Node _parent;
   private EventInfo _info;
   private SampleStatistic _stat;

   public Node(EventInfo info, Node parent) {
      this._info = info;
      this._parent = parent;
      this._stat = new SampleStatistic(this._info.getName(), "Profiling Statistic", "ms");
      this._parent = parent;
   }

   public EventInfo getInfo() {
      return this._info;
   }

   public SampleStatistic getStatistic() {
      return this._stat;
   }

   public Node getChild(EventInfo childInfo) {
      return (Node)this._children.get(childInfo);
   }

   public Node addChild(EventInfo childInfo) {
      Node child = (Node)this._children.get(childInfo);
      if (child == null) {
         child = new Node(childInfo, this);
         this._children.put(childInfo, child);
      }

      return child;
   }

   public void updateStatistic(double time) {
      this._stat.setValue(time);
   }

   public Node getParent() {
      return this._parent;
   }

   public Node getRoot() {
      return this._parent != null ? this._parent.getRoot() : this;
   }

   public ListOrderedMap getChildren() {
      return this._children;
   }

   protected void reset() {
      this._stat.reset();
      if (this._info instanceof ResettableEventInfo) {
         ((ResettableEventInfo)this._info).resetEventInfo();
      }

      Iterator iter = this._children.values().iterator();

      while(iter.hasNext()) {
         ((Node)iter.next()).reset();
      }

   }

   public String toString() {
      return this.toString(true);
   }

   public String toString(boolean percents) {
      boolean showPercentRelToRoot = true;
      DecimalFormat digitsFormat = new DecimalFormat();
      digitsFormat.setMaximumFractionDigits(0);
      double total = this._stat.getSampleSum();
      double divisorTotal = Double.NaN;
      if (this.getParent() != null) {
         if (showPercentRelToRoot) {
            divisorTotal = this.getRoot().getStatistic().getSampleSum();
         } else {
            divisorTotal = this.getParent().getStatistic().getSampleSum();
         }
      }

      StringBuffer buf = new StringBuffer();
      buf.append(this._info.getName());
      if (!Double.isNaN(total)) {
         buf.append(": ");
         buf.append(digitsFormat.format(total));
         buf.append(" ms");
         if (percents && !Double.isNaN(divisorTotal) && divisorTotal > 0.0) {
            buf.append(", ");
            buf.append(digitsFormat.format(total / divisorTotal * 100.0));
            buf.append("%");
         }
      }

      return buf.toString();
   }

   public boolean equals(Object o) {
      if (!(o instanceof Node)) {
         return false;
      } else {
         Node n = (Node)o;
         return n.getInfo().equals(this._info);
      }
   }

   public int hashCode() {
      return this._info.hashCode();
   }
}
