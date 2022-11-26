package org.glassfish.hk2.xml.internal;

import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.glassfish.hk2.xml.jaxb.internal.BaseHK2JAXBBean;

public class Differences {
   private Map differences = new LinkedHashMap();

   public void addDifference(Difference difference) {
      DifferenceKey addedKey = new DifferenceKey(difference.getSource());
      Difference existingDifference = (Difference)this.differences.get(addedKey);
      if (existingDifference == null) {
         this.differences.put(addedKey, difference);
      } else {
         existingDifference.merge(difference);
      }

   }

   public List getDifferences() {
      return new ArrayList(this.differences.values());
   }

   public int getDifferenceCost() {
      int retVal = 0;

      Difference d;
      for(Iterator var2 = this.differences.values().iterator(); var2.hasNext(); retVal += d.getSize()) {
         d = (Difference)var2.next();
      }

      return retVal;
   }

   public void merge(Differences diffs) {
      Iterator var2 = diffs.getDifferences().iterator();

      while(var2.hasNext()) {
         Difference diff = (Difference)var2.next();
         this.addDifference(diff);
      }

   }

   public String toString() {
      StringBuffer sb = new StringBuffer("Differences(num=" + this.differences.size() + ",cost=" + this.getDifferenceCost() + "\n");
      int lcv = 1;

      for(Iterator var3 = this.differences.values().iterator(); var3.hasNext(); ++lcv) {
         Difference d = (Difference)var3.next();
         BaseHK2JAXBBean source = d.getSource();
         String xmlPath = source._getXmlPath();
         String instanceName = source._getInstanceName();
         List events = d.getNonChildChanges();
         sb.append(lcv + ". Modified Bean sourcePath=" + xmlPath + " sourceInstance=" + instanceName + "\n");
         Iterator var9 = events.iterator();

         while(var9.hasNext()) {
            PropertyChangeEvent event = (PropertyChangeEvent)var9.next();
            sb.append("\tCHANGED: " + event.getPropertyName() + " from " + event.getOldValue() + " to " + event.getNewValue() + "\n");
         }

         Map childChanges = d.getChildChanges();
         Iterator var23 = childChanges.entrySet().iterator();

         while(var23.hasNext()) {
            Map.Entry childEntry = (Map.Entry)var23.next();
            String propertyName = (String)childEntry.getKey();
            sb.append("  CHANGED CHILD: " + propertyName + "\n");
            AddRemoveMoveDifference arm = (AddRemoveMoveDifference)childEntry.getValue();
            Iterator var14 = arm.getAdds().iterator();

            String addedXmlPath;
            while(var14.hasNext()) {
               AddData ad = (AddData)var14.next();
               BaseHK2JAXBBean added = ad.getToAdd();
               int index = ad.getIndex();
               String addedXmlPath = added._getXmlPath();
               addedXmlPath = added._getInstanceName();
               sb.append("    ADDED: addedPath=" + addedXmlPath + " addedInstanceName=" + addedXmlPath + " addedIndex=" + index + "\n");
            }

            var14 = arm.getRemoves().iterator();

            while(var14.hasNext()) {
               RemoveData rd = (RemoveData)var14.next();
               String removedXmlPath = rd.getChild()._getXmlPath();
               String removedInstanceName = rd.getChild()._getInstanceName();
               sb.append("    REMOVED: removedPath=" + removedXmlPath + " removedInstanceName=" + removedInstanceName + "\n");
            }

            var14 = arm.getMoves().iterator();

            while(var14.hasNext()) {
               MoveData md = (MoveData)var14.next();
               sb.append("    MOVED: oldIndex=" + md.getOldIndex() + " newIndex=" + md.getNewIndex() + "\n");
            }

            var14 = arm.getDirectReplaces().iterator();

            while(var14.hasNext()) {
               AddRemoveData ard = (AddRemoveData)var14.next();
               AddData ad = ard.getAdd();
               RemoveData rd = ard.getRemove();
               BaseHK2JAXBBean added = ad.getToAdd();
               addedXmlPath = added._getXmlPath();
               String addedInstanceName = added._getInstanceName();
               String removedInstanceName = rd.getChild()._getInstanceName();
               sb.append("    DIRECT_REPLACEMENT: modifiedPath=" + addedXmlPath + " addedInstanceName=" + addedInstanceName + " removedInstanceName=" + removedInstanceName + "\n");
            }
         }
      }

      return sb.toString() + "\n," + System.identityHashCode(this) + ")";
   }

   private static class DifferenceKey {
      private final String xmlPath;
      private final String instanceName;
      private final int hash;

      private DifferenceKey(BaseHK2JAXBBean bean) {
         this.xmlPath = bean._getXmlPath();
         this.instanceName = bean._getInstanceName();
         this.hash = this.xmlPath.hashCode() ^ this.instanceName.hashCode();
      }

      public int hashCode() {
         return this.hash;
      }

      public boolean equals(Object o) {
         if (o == null) {
            return false;
         } else if (!(o instanceof DifferenceKey)) {
            return false;
         } else {
            DifferenceKey other = (DifferenceKey)o;
            return this.xmlPath.equals(other.xmlPath) && this.instanceName.equals(other.instanceName);
         }
      }

      public String toString() {
         return "DifferenceKey(" + this.xmlPath + "," + this.instanceName + "," + this.hash + "," + System.identityHashCode(this) + ")";
      }

      // $FF: synthetic method
      DifferenceKey(BaseHK2JAXBBean x0, Object x1) {
         this(x0);
      }
   }

   public static class MoveData {
      private final int oldIndex;
      private final int newIndex;

      public MoveData(int oldIndex, int newIndex) {
         this.oldIndex = oldIndex;
         this.newIndex = newIndex;
      }

      public MoveData(MoveData copyMe) {
         this(copyMe.oldIndex, copyMe.newIndex);
      }

      public int getOldIndex() {
         return this.oldIndex;
      }

      public int getNewIndex() {
         return this.newIndex;
      }

      public String toString() {
         return "MoveData(" + this.oldIndex + "," + this.newIndex + "," + System.identityHashCode(this) + ")";
      }
   }

   public static class RemoveData {
      private final String childProperty;
      private final String childKey;
      private final int index;
      private final BaseHK2JAXBBean child;

      public RemoveData(String childProperty, BaseHK2JAXBBean child) {
         this(childProperty, (String)null, -1, child);
      }

      public RemoveData(String childProperty, String childKey, BaseHK2JAXBBean child) {
         this(childProperty, childKey, -1, child);
      }

      public RemoveData(String childProperty, int index, BaseHK2JAXBBean child) {
         this(childProperty, (String)null, index, child);
      }

      public RemoveData(RemoveData copyMe) {
         this(copyMe.childProperty, copyMe.childKey, copyMe.index, copyMe.child);
      }

      private RemoveData(String childProperty, String childKey, int index, BaseHK2JAXBBean child) {
         this.childProperty = childProperty;
         this.childKey = childKey;
         this.index = index;
         this.child = child;
      }

      public String getChildProperty() {
         return this.childProperty;
      }

      public String getChildKey() {
         return this.childKey;
      }

      public int getIndex() {
         return this.index;
      }

      public BaseHK2JAXBBean getChild() {
         return this.child;
      }

      public String toString() {
         return "RemoveData(" + this.childProperty + "," + this.childKey + "," + this.index + "," + this.child + "," + System.identityHashCode(this) + ")";
      }
   }

   public static class AddData {
      private final BaseHK2JAXBBean toAdd;
      private final int index;

      public AddData(BaseHK2JAXBBean toAdd, int index) {
         this.toAdd = toAdd;
         this.index = index;
      }

      public AddData(AddData copyMe) {
         this.toAdd = copyMe.toAdd;
         this.index = copyMe.index;
      }

      public BaseHK2JAXBBean getToAdd() {
         return this.toAdd;
      }

      public int getIndex() {
         return this.index;
      }

      public String toString() {
         return "AddData(" + this.toAdd + "," + this.index + "," + System.identityHashCode(this) + ")";
      }
   }

   public static class AddRemoveMoveDifference {
      private final List adds = new ArrayList();
      private final List removes = new ArrayList();
      private final List moves = new ArrayList();
      private final List directReplace = new ArrayList();

      private void addAdd(AddData add) {
         this.adds.add(add);
      }

      private void addRemove(RemoveData remove) {
         this.removes.add(remove);
      }

      private void addMove(MoveData move) {
         this.moves.add(move);
      }

      private void addDirectReplace(AddRemoveData dr) {
         this.directReplace.add(dr);
      }

      public List getAdds() {
         return this.adds;
      }

      public List getRemoves() {
         return this.removes;
      }

      public List getMoves() {
         return this.moves;
      }

      public List getDirectReplaces() {
         return this.directReplace;
      }

      public boolean requiresListChange() {
         return !this.adds.isEmpty() || !this.removes.isEmpty() || !this.moves.isEmpty() || !this.directReplace.isEmpty();
      }

      public int getSize() {
         return this.adds.size() + this.removes.size() + this.moves.size() + this.directReplace.size();
      }

      public int getNewSize(int oldSize) {
         return oldSize - this.removes.size() + this.adds.size();
      }

      public String toString() {
         return "AddRemoveMoveDifference(" + this.adds + "," + this.removes + "," + this.moves + "," + this.directReplace + "," + System.identityHashCode(this) + ")";
      }
   }

   public static class AddRemoveData {
      private final AddData add;
      private final RemoveData remove;

      private AddRemoveData(AddData add, RemoveData remove) {
         this.add = add;
         this.remove = remove;
      }

      public AddData getAdd() {
         return this.add;
      }

      public RemoveData getRemove() {
         return this.remove;
      }

      public String toString() {
         return "AddRemoveData(" + this.add + "," + this.remove + "," + System.identityHashCode(this) + ")";
      }

      // $FF: synthetic method
      AddRemoveData(AddData x0, RemoveData x1, Object x2) {
         this(x0, x1);
      }
   }

   public static class Difference {
      private final BaseHK2JAXBBean source;
      private final List nonChildChanges = new LinkedList();
      private final Map childChanges = new LinkedHashMap();

      public Difference(BaseHK2JAXBBean source) {
         this.source = source;
      }

      public BaseHK2JAXBBean getSource() {
         return this.source;
      }

      public void addNonChildChange(PropertyChangeEvent evt) {
         this.nonChildChanges.add(evt);
      }

      public List getNonChildChanges() {
         return this.nonChildChanges;
      }

      public Map getChildChanges() {
         return this.childChanges;
      }

      private AddRemoveMoveDifference getARMDiff(String propName) {
         AddRemoveMoveDifference field = (AddRemoveMoveDifference)this.childChanges.get(propName);
         if (field == null) {
            field = new AddRemoveMoveDifference();
            this.childChanges.put(propName, field);
         }

         return field;
      }

      public void addAdd(String propName, AddData toAdd) {
         AddRemoveMoveDifference arm = this.getARMDiff(propName);
         arm.addAdd(toAdd);
      }

      public void addAdd(String propName, BaseHK2JAXBBean toAdd, int index) {
         this.addAdd(propName, new AddData(toAdd, index));
      }

      public void addMove(String propName, MoveData md) {
         AddRemoveMoveDifference arm = this.getARMDiff(propName);
         arm.addMove(md);
      }

      public void addRemove(String propName, RemoveData removeData) {
         AddRemoveMoveDifference arm = this.getARMDiff(propName);
         arm.addRemove(removeData);
      }

      public void addDirectReplace(String propName, BaseHK2JAXBBean toAdd, RemoveData removeData) {
         AddRemoveMoveDifference arm = this.getARMDiff(propName);
         arm.addDirectReplace(new AddRemoveData(new AddData(toAdd, -1), removeData));
      }

      private void merge(Difference mergeMe) {
         Iterator var2 = mergeMe.getNonChildChanges().iterator();

         while(var2.hasNext()) {
            PropertyChangeEvent pce = (PropertyChangeEvent)var2.next();
            this.addNonChildChange(pce);
         }

         Map childChanges = mergeMe.getChildChanges();
         Iterator var10 = childChanges.entrySet().iterator();

         while(var10.hasNext()) {
            Map.Entry childEntry = (Map.Entry)var10.next();
            String propertyName = (String)childEntry.getKey();
            AddRemoveMoveDifference arm = (AddRemoveMoveDifference)childEntry.getValue();
            Iterator var7 = arm.getAdds().iterator();

            while(var7.hasNext()) {
               AddData add = (AddData)var7.next();
               this.addAdd(propertyName, new AddData(add));
            }

            var7 = arm.getRemoves().iterator();

            while(var7.hasNext()) {
               RemoveData remove = (RemoveData)var7.next();
               this.addRemove(propertyName, new RemoveData(remove));
            }

            var7 = arm.getMoves().iterator();

            while(var7.hasNext()) {
               MoveData move = (MoveData)var7.next();
               this.addMove(propertyName, new MoveData(move));
            }
         }

      }

      public boolean isDirty() {
         return !this.nonChildChanges.isEmpty() || !this.childChanges.isEmpty();
      }

      public boolean hasChildChanges() {
         return !this.childChanges.isEmpty();
      }

      public int getSize() {
         int retVal = this.nonChildChanges.size();
         retVal += this.childChanges.size();
         return retVal;
      }

      public String toString() {
         return "Difference(" + this.source + "," + System.identityHashCode(this) + ")";
      }
   }
}
