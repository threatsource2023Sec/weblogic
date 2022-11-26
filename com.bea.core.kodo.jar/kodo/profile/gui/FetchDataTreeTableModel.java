package kodo.profile.gui;

import com.solarmetric.ide.ui.treetable.AbstractTreeTableModel;
import com.solarmetric.ide.ui.treetable.TreeTableModel;
import java.text.DecimalFormat;
import kodo.profile.FetchStats;
import kodo.profile.KodoRootInfo;
import kodo.profile.ProfilingClassMetaData;
import kodo.profile.ProfilingFieldMetaData;

public class FetchDataTreeTableModel extends AbstractTreeTableModel {
   private KodoRootInfo _kinfo;
   private DecimalFormat _nodigits;
   private String[] columnNames = new String[]{"Field Name", "Total", "Fetched (% Used)", "Unfetched (% Used)"};
   private double overloadThreshold = 0.5;
   private double underloadThreshold = 0.5;
   public static final int NONE = 0;
   public static final int OVERLOADED = 1;
   public static final int UNDERLOADED = 2;

   public FetchDataTreeTableModel(KodoRootInfo kinfo) {
      super(kinfo);
      this._kinfo = kinfo;
      this._nodigits = new DecimalFormat();
      this._nodigits.setMaximumFractionDigits(0);
   }

   public int getColumnCount() {
      return this.columnNames.length;
   }

   public String getColumnName(int col) {
      return this.columnNames[col];
   }

   public int getMarkingAt(Object node, int col) {
      if (node instanceof KodoRootInfo) {
         return 0;
      } else if (node instanceof ProfilingClassMetaData) {
         return 0;
      } else if (!(node instanceof ProfilingFieldMetaData)) {
         return 0;
      } else {
         ProfilingFieldMetaData fmd = (ProfilingFieldMetaData)node;
         FetchStats stats = (FetchStats)this._kinfo.getFetchStatsMap().get(fmd);
         if (stats == null) {
            return 0;
         } else {
            int accessed = stats.getAccessed();
            if (accessed == -1) {
               return 0;
            } else {
               int initLoaded = stats.getInitLoaded();
               int instantiated = stats.getInstantiated();
               int faulted = stats.getFaulted();
               int initLoadNotUsed = initLoaded - (accessed - faulted);
               int notLoaded = instantiated - initLoaded;
               double overloaded = 0.0;
               if ((double)initLoaded != 0.0) {
                  overloaded = (double)initLoadNotUsed / (double)initLoaded;
               }

               double underloaded = 0.0;
               if ((double)notLoaded != 0.0) {
                  underloaded = (double)faulted / (double)notLoaded;
               }

               int retVal = 0;
               if (overloaded > this.overloadThreshold) {
                  retVal |= 1;
               }

               if (underloaded > this.underloadThreshold) {
                  retVal |= 2;
               }

               return retVal;
            }
         }
      }
   }

   public Object getValueAt(Object node, int col) {
      if (node instanceof KodoRootInfo) {
         return col == 0 ? ((KodoRootInfo)node).getName() : "";
      } else if (node instanceof ProfilingClassMetaData) {
         return col == 0 ? ((ProfilingClassMetaData)node).getName() : "";
      } else if (!(node instanceof ProfilingFieldMetaData)) {
         return "";
      } else {
         ProfilingFieldMetaData fmd = (ProfilingFieldMetaData)node;
         FetchStats stats = (FetchStats)this._kinfo.getFetchStatsMap().get(fmd);
         if (stats == null) {
            return "-";
         } else {
            int accessed = stats.getAccessed();
            int initLoaded = stats.getInitLoaded();
            int instantiated = stats.getInstantiated();
            int faulted = stats.getFaulted();
            int initLoadUsed = accessed - faulted;
            int notLoaded = instantiated - initLoaded;
            switch (col) {
               case 0:
                  return fmd.getName();
               case 1:
                  return String.valueOf(instantiated);
               case 2:
                  if (accessed == -1) {
                     return initLoaded + " (???%)";
                  } else {
                     if (initLoaded == 0) {
                        return initLoaded + " (-%)";
                     }

                     return initLoaded + " (" + this._nodigits.format(100.0 * (double)initLoadUsed / (double)initLoaded) + "%)";
                  }
               case 3:
                  if (accessed == -1) {
                     return notLoaded + " (???%)";
                  } else {
                     if (notLoaded == 0) {
                        return notLoaded + " (-%)";
                     }

                     return notLoaded + " (" + this._nodigits.format(100.0 * (double)faulted / (double)notLoaded) + "%)";
                  }
               default:
                  return "";
            }
         }
      }
   }

   public Class getColumnClass(int col) {
      return col == 0 ? TreeTableModel.class : String.class;
   }

   public Object getRoot() {
      return this._kinfo;
   }

   public boolean isLeaf(Object node) {
      return node instanceof ProfilingFieldMetaData;
   }

   public Object getChild(Object parent, int index) {
      if (parent instanceof KodoRootInfo) {
         return this._kinfo.getMetaSet().get(index);
      } else {
         return parent instanceof ProfilingClassMetaData ? ((ProfilingClassMetaData)parent).getFields()[index] : null;
      }
   }

   public int getChildCount(Object parent) {
      if (parent instanceof KodoRootInfo) {
         return this._kinfo.getMetaSet().size();
      } else {
         return parent instanceof ProfilingClassMetaData ? ((ProfilingClassMetaData)parent).getFields().length : 0;
      }
   }

   public int getIndexOfChild(Object parent, Object child) {
      if (parent instanceof KodoRootInfo) {
         return this._kinfo.getMetaSet().indexOf(child);
      } else {
         return child instanceof ProfilingFieldMetaData ? ((ProfilingFieldMetaData)child).getIndex() : -1;
      }
   }

   public void fireUpdate() {
      this.fireTreeStructureChanged(this, new Object[]{this._kinfo}, new int[0], new Object[0]);
   }
}
