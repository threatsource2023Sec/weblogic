package org.glassfish.hk2.xml.internal;

import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.glassfish.hk2.utilities.cache.CacheUtilities;
import org.glassfish.hk2.utilities.cache.Computable;
import org.glassfish.hk2.utilities.cache.ComputationErrorException;
import org.glassfish.hk2.utilities.cache.WeakCARCache;
import org.glassfish.hk2.utilities.reflection.Logger;
import org.glassfish.hk2.xml.jaxb.internal.BaseHK2JAXBBean;

public class UnkeyedDiff {
   private static final String UNKEYED_DEBUG_PROPERTY = "org.jvnet.hk2.properties.xml.unkeyed.debug";
   private static final boolean UNKEYED_DEBUG = (Boolean)AccessController.doPrivileged(new PrivilegedAction() {
      public Boolean run() {
         return Boolean.getBoolean("org.jvnet.hk2.properties.xml.unkeyed.debug");
      }
   });
   private final List legacyList;
   private final List proposedList;
   private final ParentedModel parentModel;
   private final BaseHK2JAXBBean parent;
   private boolean computed;
   private Differences finalSolution;
   private HashMap solution;
   private HashSet usedLegacy;
   private HashSet unusedLegacy;
   private HashMap proposedAdds;
   private HashMap quantumSolutions;

   public UnkeyedDiff(List legacy, List proposed, BaseHK2JAXBBean parent, ParentedModel parentModel) {
      this.computed = false;
      this.finalSolution = null;
      if (legacy == null) {
         legacy = Collections.emptyList();
      }

      if (proposed == null) {
         proposed = Collections.emptyList();
      }

      this.legacyList = new ArrayList(legacy);
      this.proposedList = new ArrayList(proposed);
      this.parent = parent;
      this.parentModel = parentModel;
   }

   private static List asList(Object[] a) {
      if (a == null) {
         return Collections.emptyList();
      } else {
         ArrayList retVal = new ArrayList(a.length);
         Object[] var2 = a;
         int var3 = a.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            Object o = var2[var4];
            retVal.add((BaseHK2JAXBBean)o);
         }

         return retVal;
      }
   }

   public UnkeyedDiff(Object[] legacy, Object[] proposed, BaseHK2JAXBBean parent, ParentedModel parentModel) {
      this(asList(legacy), asList(proposed), parent, parentModel);
   }

   public synchronized Differences compute() {
      if (this.computed) {
         return this.finalSolution;
      } else {
         Differences retVal = null;

         try {
            retVal = this.internalCompute();
         } finally {
            if (retVal != null) {
               this.finalSolution = retVal;
               this.computed = true;
            }

         }

         return retVal;
      }
   }

   private Differences internalCompute() {
      Differences retVal = new Differences();
      boolean needsChangeOfList = false;
      DifferenceTable table = new DifferenceTable();
      boolean diagonalChange = !table.calculateDiagonal();
      int proposedIndex;
      Differences.Difference d;
      if (!diagonalChange) {
         if (this.legacyList.size() == this.proposedList.size()) {
            return retVal;
         } else if (this.legacyList.size() < this.proposedList.size()) {
            for(proposedIndex = this.legacyList.size(); proposedIndex < this.proposedList.size(); ++proposedIndex) {
               Differences.Difference d = new Differences.Difference(this.parent);
               d.addAdd(this.parentModel.getChildXmlTag(), new Differences.AddData((BaseHK2JAXBBean)this.proposedList.get(proposedIndex), proposedIndex));
               retVal.addDifference(d);
            }

            return retVal;
         } else {
            for(proposedIndex = this.proposedList.size(); proposedIndex < this.legacyList.size(); ++proposedIndex) {
               String xmlTag = this.parentModel.getChildXmlTag();
               d = new Differences.Difference(this.parent);
               d.addRemove(xmlTag, new Differences.RemoveData(xmlTag, proposedIndex, (BaseHK2JAXBBean)this.legacyList.get(proposedIndex)));
               retVal.addDifference(d);
            }

            return retVal;
         }
      } else {
         this.initializeSolution();

         Differences diffs;
         for(proposedIndex = 0; proposedIndex < table.getDiagonalSize(); ++proposedIndex) {
            diffs = table.getDiff(proposedIndex, proposedIndex);
            if (diffs.getDifferences().isEmpty()) {
               this.addSolution(proposedIndex, proposedIndex, diffs);
            }
         }

         for(proposedIndex = 0; proposedIndex < this.proposedList.size(); ++proposedIndex) {
            if (this.solution.containsKey(proposedIndex)) {
               if (UNKEYED_DEBUG) {
                  Logger.getLogger().debug("Skipping proposedIndex " + proposedIndex + " since it has already has a solution");
               }
            } else {
               int currentBestDiffIndex = -1;
               Differences currentBestDiffs = null;

               Differences proposedAdd;
               for(int legacyIndex = 0; legacyIndex < this.legacyList.size(); ++legacyIndex) {
                  if (this.usedLegacy.contains(legacyIndex)) {
                     if (UNKEYED_DEBUG) {
                        Logger.getLogger().debug("Skipping legacyIndex " + legacyIndex + " for proposedIndex " + proposedIndex + " since it has already has already been used");
                     }
                  } else {
                     proposedAdd = table.getDiff(legacyIndex, proposedIndex);
                     if (currentBestDiffs == null || currentBestDiffs.getDifferenceCost() > proposedAdd.getDifferenceCost()) {
                        currentBestDiffs = proposedAdd;
                        currentBestDiffIndex = legacyIndex;
                        if (proposedAdd.getDifferences().isEmpty()) {
                           needsChangeOfList = true;
                           break;
                        }
                     }
                  }
               }

               Differences moveMeDifferences;
               Differences.Difference difference;
               if (currentBestDiffs == null) {
                  moveMeDifferences = new Differences();
                  difference = new Differences.Difference(this.parent);
                  difference.addAdd(this.parentModel.getChildXmlTag(), new Differences.AddData((BaseHK2JAXBBean)this.proposedList.get(proposedIndex), proposedIndex));
                  moveMeDifferences.addDifference(difference);
                  needsChangeOfList = true;
                  this.addSolution(-1, proposedIndex, moveMeDifferences);
               } else if (currentBestDiffs.getDifferences().isEmpty()) {
                  needsChangeOfList = true;
                  moveMeDifferences = new Differences();
                  difference = new Differences.Difference(this.parent);
                  difference.addMove(this.parentModel.getChildXmlTag(), new Differences.MoveData(currentBestDiffIndex, proposedIndex));
                  moveMeDifferences.addDifference(difference);
                  this.addSolution(currentBestDiffIndex, proposedIndex, moveMeDifferences);
               } else {
                  BaseHK2JAXBBean currentProposed = (BaseHK2JAXBBean)this.proposedList.get(proposedIndex);
                  proposedAdd = (Differences)this.proposedAdds.get(proposedIndex);
                  if (proposedAdd == null) {
                     proposedAdd = new Differences();
                     Differences.Difference d = new Differences.Difference(this.parent);
                     d.addAdd(this.parentModel.getChildXmlTag(), new Differences.AddData(currentProposed, proposedIndex));
                     proposedAdd.addDifference(d);
                     this.proposedAdds.put(proposedIndex, proposedAdd);
                  }

                  SchrodingerSolution sd = new SchrodingerSolution(currentBestDiffIndex, currentBestDiffs, proposedAdd);
                  this.quantumSolutions.put(proposedIndex, sd);
               }
            }
         }

         Iterator var13 = this.quantumSolutions.entrySet().iterator();

         while(var13.hasNext()) {
            Map.Entry entry = (Map.Entry)var13.next();
            int proposedIndex = (Integer)entry.getKey();
            SchrodingerSolution sd = (SchrodingerSolution)entry.getValue();
            int legacyIndex = sd.legacyIndexOfDiff;
            if (this.usedLegacy.contains(legacyIndex)) {
               needsChangeOfList = true;
               this.addSolution(-1, proposedIndex, sd.proposedAddDifference);
            } else {
               if (!this.unusedLegacy.contains(legacyIndex)) {
                  throw new AssertionError("Should not be able to get here");
               }

               BaseHK2JAXBBean legacy = (BaseHK2JAXBBean)this.legacyList.get(legacyIndex);
               int removeCost = Utilities.calculateAddCost(legacy);
               int totalAddCost = removeCost + sd.proposedAddDifference.getDifferenceCost();
               if (totalAddCost >= sd.legacyDifference.getDifferenceCost()) {
                  this.addSolution(legacyIndex, proposedIndex, sd.legacyDifference);
               } else {
                  needsChangeOfList = true;
                  this.addSolution(-1, proposedIndex, sd.proposedAddDifference);
               }
            }
         }

         var13 = this.solution.values().iterator();

         while(var13.hasNext()) {
            diffs = (Differences)var13.next();
            retVal.merge(diffs);
         }

         var13 = this.unusedLegacy.iterator();

         while(var13.hasNext()) {
            Integer legacyRemoveIndex = (Integer)var13.next();
            d = new Differences.Difference(this.parent);
            d.addRemove(this.parentModel.getChildXmlTag(), new Differences.RemoveData(this.parentModel.getChildXmlTag(), legacyRemoveIndex, (BaseHK2JAXBBean)this.legacyList.get(legacyRemoveIndex)));
            retVal.addDifference(d);
         }

         if (UNKEYED_DEBUG) {
            Logger.getLogger().debug("needsChangeOfList=" + needsChangeOfList + " with outcome " + retVal);
         }

         return retVal;
      }
   }

   private void initializeSolution() {
      this.solution = new HashMap();
      this.usedLegacy = new HashSet();
      this.unusedLegacy = new HashSet();

      for(int lcv = 0; lcv < this.legacyList.size(); ++lcv) {
         this.unusedLegacy.add(lcv);
      }

      this.proposedAdds = new HashMap();
      this.quantumSolutions = new HashMap();
   }

   private void addSolution(int legacyIndex, int proposedIndex, Differences minimum) {
      this.solution.put(proposedIndex, minimum);
      if (legacyIndex >= 0) {
         this.usedLegacy.add(legacyIndex);
         this.unusedLegacy.remove(legacyIndex);
      }

   }

   private static class SchrodingerSolution {
      private final int legacyIndexOfDiff;
      private final Differences legacyDifference;
      private final Differences proposedAddDifference;

      private SchrodingerSolution(int legacyIndexOfDiff, Differences legacyDifference, Differences proposedAddDifference) {
         this.legacyIndexOfDiff = legacyIndexOfDiff;
         this.legacyDifference = legacyDifference;
         this.proposedAddDifference = proposedAddDifference;
      }

      public String toString() {
         return "SchrodingerSolution(" + this.legacyIndexOfDiff + "," + this.legacyDifference + "," + this.proposedAddDifference + "," + System.identityHashCode(this) + ")";
      }

      // $FF: synthetic method
      SchrodingerSolution(int x0, Differences x1, Differences x2, Object x3) {
         this(x0, x1, x2);
      }
   }

   private static class TableKey {
      private final int legacyIndex;
      private final int proposedIndex;
      private final int hash;

      private TableKey(int legacyIndex, int proposedIndex) {
         this.legacyIndex = legacyIndex;
         this.proposedIndex = proposedIndex;
         this.hash = legacyIndex ^ proposedIndex;
      }

      public int hashCode() {
         return this.hash;
      }

      public boolean equals(Object o) {
         if (o == null) {
            return false;
         } else if (!(o instanceof TableKey)) {
            return false;
         } else {
            TableKey other = (TableKey)o;
            return other.legacyIndex == this.legacyIndex && other.proposedIndex == this.proposedIndex;
         }
      }

      public String toString() {
         return "TableKey(" + this.legacyIndex + "," + this.proposedIndex + "," + System.identityHashCode(this) + ")";
      }

      // $FF: synthetic method
      TableKey(int x0, int x1, Object x2) {
         this(x0, x1);
      }
   }

   private class DifferenceMaker implements Computable {
      private DifferenceMaker() {
      }

      public Differences compute(TableKey key) throws ComputationErrorException {
         BaseHK2JAXBBean legacyBean = (BaseHK2JAXBBean)UnkeyedDiff.this.legacyList.get(key.legacyIndex);
         BaseHK2JAXBBean proposedBean = (BaseHK2JAXBBean)UnkeyedDiff.this.proposedList.get(key.proposedIndex);
         Differences retVal = Utilities.getDiff(legacyBean, proposedBean);
         return retVal;
      }

      public String toString() {
         return "DifferenceMaker(" + System.identityHashCode(this) + ")";
      }

      // $FF: synthetic method
      DifferenceMaker(Object x1) {
         this();
      }
   }

   private class DifferenceTable {
      private final int min;
      private final WeakCARCache table;

      private DifferenceTable() {
         this.min = Math.min(UnkeyedDiff.this.legacyList.size(), UnkeyedDiff.this.proposedList.size());
         int tableSize = UnkeyedDiff.this.legacyList.size() * UnkeyedDiff.this.proposedList.size();
         this.table = CacheUtilities.createWeakCARCache(UnkeyedDiff.this.new DifferenceMaker(), tableSize, false);
      }

      private boolean calculateDiagonal() {
         boolean theSame = true;

         for(int lcv = 0; lcv < this.min; ++lcv) {
            TableKey diagonalKey = new TableKey(lcv, lcv);
            Differences differences = (Differences)this.table.compute(diagonalKey);
            if (!differences.getDifferences().isEmpty()) {
               theSame = false;
            }
         }

         return theSame;
      }

      private Differences getDiff(int legacyIndex, int proposedIndex) {
         return (Differences)this.table.compute(new TableKey(legacyIndex, proposedIndex));
      }

      private int getDiagonalSize() {
         return this.min;
      }

      public String toString() {
         return "DifferenceTable(min=" + this.min + " legacySize=" + UnkeyedDiff.this.legacyList.size() + " proposedSize=" + UnkeyedDiff.this.proposedList.size() + "," + System.identityHashCode(this) + ")";
      }

      // $FF: synthetic method
      DifferenceTable(Object x1) {
         this();
      }
   }
}
