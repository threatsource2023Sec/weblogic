package kodo.profile.gui;

import java.text.NumberFormat;
import java.util.HashSet;
import java.util.Iterator;
import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;
import kodo.profile.KodoRootInfo;
import kodo.profile.ProxyStats;
import kodo.profile.ResultListStats;
import org.apache.commons.collections.map.ListOrderedMap;

public class CollectionStatsTreeModel implements TreeModel {
   public static final int RL_SAMPLES = 0;
   public static final int RL_AVE_ACCESSED = 1;
   public static final int RL_AVE_ACCESSED_PCT = 2;
   public static final int RL_AVE_SIZE = 3;
   public static final int RL_CONTAINS_CALLED = 4;
   public static final int RL_SIZE_CALLED = 5;
   public static final int RL_INDEXOF_CALLED = 6;
   public static final int RL_COUNT = 7;
   public static final String RL_SAMPLES_PREFIX = "executed: ";
   public static final String RL_AVE_ACCESSED_PREFIX = "avg. accessed: ";
   public static final String RL_AVE_ACCESSED_PCT_PREFIX = "avg. % accessed: ";
   public static final String RL_AVE_SIZE_PREFIX = "avg. size: ";
   public static final String RL_CONTAINS_CALLED_PREFIX = "contains called: ";
   public static final String RL_SIZE_CALLED_PREFIX = "size called: ";
   public static final String RL_INDEXOF_CALLED_PREFIX = "indexOf called: ";
   public static final int P_SAMPLES = 0;
   public static final int P_AVE_ACCESSED = 1;
   public static final int P_AVE_ACCESSED_PCT = 2;
   public static final int P_AVE_SIZE = 3;
   public static final int P_CONTAINS_CALLED = 4;
   public static final int P_SIZE_CALLED = 5;
   public static final int P_INDEXOF_CALLED = 6;
   public static final int P_CLEAR_CALLED = 7;
   public static final int P_RETAIN_CALLED = 8;
   public static final int P_ADD_CALLED = 9;
   public static final int P_SET_CALLED = 10;
   public static final int P_REMOVE_CALLED = 11;
   public static final int P_COUNT = 12;
   public static final String P_SAMPLES_PREFIX = "executed: ";
   public static final String P_AVE_ACCESSED_PREFIX = "avg. accessed: ";
   public static final String P_AVE_ACCESSED_PCT_PREFIX = "avg. % accessed: ";
   public static final String P_AVE_SIZE_PREFIX = "avg. size: ";
   public static final String P_CONTAINS_CALLED_PREFIX = "contains called: ";
   public static final String P_SIZE_CALLED_PREFIX = "size called: ";
   public static final String P_INDEXOF_CALLED_PREFIX = "indexOf called: ";
   public static final String P_CLEAR_CALLED_PREFIX = "clear called: ";
   public static final String P_RETAIN_CALLED_PREFIX = "retain called: ";
   public static final String P_ADD_CALLED_PREFIX = "add called: ";
   public static final String P_SET_CALLED_PREFIX = "set called: ";
   public static final String P_REMOVE_CALLED_PREFIX = "remove called: ";
   private KodoRootInfo _root;
   private HashSet _listeners = new HashSet();
   private NumberFormat numFmt;

   public CollectionStatsTreeModel(KodoRootInfo root) {
      this._root = root;
      this.numFmt = NumberFormat.getInstance();
      this.numFmt.setMaximumFractionDigits(1);
      this.numFmt.setMinimumFractionDigits(1);
   }

   public void addTreeModelListener(TreeModelListener l) {
      this._listeners.add(l);
   }

   public void removeTreeModelListener(TreeModelListener l) {
      this._listeners.remove(l);
   }

   public Object getChild(Object parent, int index) {
      if (parent == this._root) {
         ListOrderedMap statsMap = this._root.getResultListStatsMap();
         if (index < statsMap.size()) {
            return statsMap.getValue(index);
         }

         index -= statsMap.size();
         statsMap = this._root.getProxyStatsMap();
         if (index < statsMap.size()) {
            return statsMap.getValue(index);
         }
      }

      StringBuffer buf;
      if (parent instanceof ResultListStats) {
         ResultListStats stats = (ResultListStats)parent;
         buf = new StringBuffer();
         switch (index) {
            case 0:
               buf.append("executed: ").append(stats.getSamples());
               return buf.toString();
            case 1:
               buf.append("avg. accessed: ").append(this.numFmt.format((double)stats.getAccessed() / (double)stats.getSamples()));
               buf.append(" (").append(stats.getAccessed()).append(" / ").append(stats.getSamples()).append(")");
               return buf.toString();
            case 2:
               buf.append("avg. % accessed: ").append(this.numFmt.format((double)stats.getAccessedKnownSize() * 100.0 / (double)stats.getSize()));
               buf.append("% (").append(stats.getSizeSamples()).append(" samples)");
               return buf.toString();
            case 3:
               buf.append("avg. size: ").append(this.numFmt.format((double)stats.getSize() / (double)stats.getSizeSamples()));
               buf.append(" (").append(stats.getSizeSamples()).append(" samples)");
               return buf.toString();
            case 4:
               buf.append("contains called: ").append(stats.getContainsCalled());
               buf.append(" (").append(this.numFmt.format((double)stats.getContainsCalled() * 100.0 / (double)stats.getSamples()));
               buf.append("%)");
               return buf.toString();
            case 5:
               buf.append("size called: ").append(stats.getSizeCalled());
               buf.append(" (").append(this.numFmt.format((double)stats.getSizeCalled() * 100.0 / (double)stats.getSamples()));
               buf.append("%)");
               return buf.toString();
            case 6:
               buf.append("indexOf called: ").append(stats.getIndexOfCalled());
               buf.append(" (").append(this.numFmt.format((double)stats.getIndexOfCalled() * 100.0 / (double)stats.getSamples()));
               buf.append("%)");
               return buf.toString();
         }
      }

      if (parent instanceof ProxyStats) {
         ProxyStats stats = (ProxyStats)parent;
         buf = new StringBuffer();
         switch (index) {
            case 0:
               buf.append("executed: ").append(stats.getSamples());
               return buf.toString();
            case 1:
               buf.append("avg. accessed: ").append(this.numFmt.format((double)stats.getAccessed() / (double)stats.getSamples()));
               buf.append(" (").append(stats.getAccessed()).append(" / ").append(stats.getSamples()).append(")");
               return buf.toString();
            case 2:
               buf.append("avg. % accessed: ").append(this.numFmt.format((double)stats.getAccessedKnownSize() * 100.0 / (double)stats.getSize()));
               buf.append("% (").append(stats.getSizeSamples()).append(" samples)");
               return buf.toString();
            case 3:
               buf.append("avg. size: ").append(this.numFmt.format((double)stats.getSize() / (double)stats.getSizeSamples()));
               buf.append(" (").append(stats.getSizeSamples()).append(" samples)");
               return buf.toString();
            case 4:
               buf.append("contains called: ").append(stats.getContainsCalled());
               buf.append(" (").append(this.numFmt.format((double)stats.getContainsCalled() * 100.0 / (double)stats.getSamples()));
               buf.append("%)");
               return buf.toString();
            case 5:
               buf.append("size called: ").append(stats.getSizeCalled());
               buf.append(" (").append(this.numFmt.format((double)stats.getSizeCalled() * 100.0 / (double)stats.getSamples()));
               buf.append("%)");
               return buf.toString();
            case 6:
               buf.append("indexOf called: ").append(stats.getIndexOfCalled());
               buf.append(" (").append(this.numFmt.format((double)stats.getIndexOfCalled() * 100.0 / (double)stats.getSamples()));
               buf.append("%)");
               return buf.toString();
            case 7:
               buf.append("clear called: ").append(stats.getClearCalled());
               buf.append(" (").append(this.numFmt.format((double)stats.getClearCalled() * 100.0 / (double)stats.getSamples()));
               buf.append("%)");
               return buf.toString();
            case 8:
               buf.append("retain called: ").append(stats.getRetainCalled());
               buf.append(" (").append(this.numFmt.format((double)stats.getRetainCalled() * 100.0 / (double)stats.getSamples()));
               buf.append("%)");
               return buf.toString();
            case 9:
               buf.append("add called: ").append(stats.getAddCalled());
               buf.append(" (").append(this.numFmt.format((double)stats.getAddCalled() * 100.0 / (double)stats.getSamples()));
               buf.append("%)");
               return buf.toString();
            case 10:
               buf.append("set called: ").append(stats.getSetCalled());
               buf.append(" (").append(this.numFmt.format((double)stats.getSetCalled() * 100.0 / (double)stats.getSamples()));
               buf.append("%)");
               return buf.toString();
            case 11:
               buf.append("remove called: ").append(stats.getRemoveCalled());
               buf.append(" (").append(this.numFmt.format((double)stats.getRemoveCalled() * 100.0 / (double)stats.getSamples()));
               buf.append("%)");
               return buf.toString();
         }
      }

      return null;
   }

   public int getChildCount(Object parent) {
      if (parent == this._root) {
         ListOrderedMap resultListStatsMap = this._root.getResultListStatsMap();
         ListOrderedMap proxyStatsMap = this._root.getProxyStatsMap();
         return resultListStatsMap.size() + proxyStatsMap.size();
      } else if (parent instanceof ResultListStats) {
         return 7;
      } else {
         return parent instanceof ProxyStats ? 12 : 0;
      }
   }

   public int getIndexOfChild(Object parent, Object child) {
      if (parent == this._root) {
         ListOrderedMap statsMap = this._root.getResultListStatsMap();
         return statsMap.indexOf(child);
      } else {
         String str;
         if (parent instanceof ResultListStats && child instanceof String) {
            str = (String)child;
            if (str.startsWith("executed: ")) {
               return 0;
            }

            if (str.startsWith("avg. accessed: ")) {
               return 1;
            }

            if (str.startsWith("avg. % accessed: ")) {
               return 2;
            }

            if (str.startsWith("avg. size: ")) {
               return 3;
            }

            if (str.startsWith("contains called: ")) {
               return 4;
            }

            if (str.startsWith("size called: ")) {
               return 5;
            }

            if (str.startsWith("indexOf called: ")) {
               return 6;
            }
         }

         if (parent instanceof ProxyStats && child instanceof String) {
            str = (String)child;
            if (str.startsWith("executed: ")) {
               return 0;
            }

            if (str.startsWith("avg. accessed: ")) {
               return 1;
            }

            if (str.startsWith("avg. % accessed: ")) {
               return 2;
            }

            if (str.startsWith("avg. size: ")) {
               return 3;
            }

            if (str.startsWith("contains called: ")) {
               return 4;
            }

            if (str.startsWith("size called: ")) {
               return 5;
            }

            if (str.startsWith("indexOf called: ")) {
               return 6;
            }

            if (str.startsWith("clear called: ")) {
               return 7;
            }

            if (str.startsWith("retain called: ")) {
               return 8;
            }

            if (str.startsWith("add called: ")) {
               return 9;
            }

            if (str.startsWith("set called: ")) {
               return 10;
            }

            if (str.startsWith("remove called: ")) {
               return 11;
            }
         }

         return -1;
      }
   }

   public Object getRoot() {
      return this._root;
   }

   public boolean isLeaf(Object node) {
      if (node == this._root) {
         return false;
      } else if (node instanceof ResultListStats) {
         return false;
      } else {
         return !(node instanceof ProxyStats);
      }
   }

   public void valueForPathChanged(TreePath path, Object newValue) {
   }

   public void fireUpdate() {
      Iterator i = this._listeners.iterator();

      while(i.hasNext()) {
         TreeModelListener l = (TreeModelListener)i.next();
         l.treeStructureChanged(new TreeModelEvent(this, new Object[]{this._root}, new int[0], new Object[0]));
      }

   }
}
