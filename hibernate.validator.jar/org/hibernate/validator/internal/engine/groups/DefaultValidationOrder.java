package org.hibernate.validator.internal.engine.groups;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.validation.GroupDefinitionException;
import org.hibernate.validator.internal.util.CollectionHelper;
import org.hibernate.validator.internal.util.logging.Log;
import org.hibernate.validator.internal.util.logging.LoggerFactory;

public final class DefaultValidationOrder implements ValidationOrder {
   private static final Log LOG = LoggerFactory.make(MethodHandles.lookup());
   private List groupList;
   private Map sequenceMap;

   public Iterator getGroupIterator() {
      return this.groupList == null ? Collections.emptyIterator() : this.groupList.iterator();
   }

   public Iterator getSequenceIterator() {
      return this.sequenceMap == null ? Collections.emptyIterator() : this.sequenceMap.values().iterator();
   }

   public void insertGroup(Group group) {
      if (this.groupList == null) {
         this.groupList = new ArrayList(5);
      }

      if (!this.groupList.contains(group)) {
         this.groupList.add(group);
      }

   }

   public void insertSequence(Sequence sequence) {
      if (sequence != null) {
         if (this.sequenceMap == null) {
            this.sequenceMap = CollectionHelper.newHashMap(5);
         }

         this.sequenceMap.putIfAbsent(sequence.getDefiningClass(), sequence);
      }
   }

   public String toString() {
      return "ValidationOrder{groupList=" + this.groupList + ", sequenceMap=" + this.sequenceMap + '}';
   }

   public void assertDefaultGroupSequenceIsExpandable(List defaultGroupSequence) throws GroupDefinitionException {
      if (this.sequenceMap != null) {
         Iterator var2 = this.sequenceMap.entrySet().iterator();

         while(var2.hasNext()) {
            Map.Entry entry = (Map.Entry)var2.next();
            List sequenceGroups = ((Sequence)entry.getValue()).getComposingGroups();
            int defaultGroupIndex = sequenceGroups.indexOf(Group.DEFAULT_GROUP);
            if (defaultGroupIndex != -1) {
               List defaultGroupList = this.buildTempGroupList(defaultGroupSequence);
               this.ensureDefaultGroupSequenceIsExpandable(sequenceGroups, defaultGroupList, defaultGroupIndex);
            }
         }

      }
   }

   private void ensureDefaultGroupSequenceIsExpandable(List groupList, List defaultGroupList, int defaultGroupIndex) {
      for(int i = 0; i < defaultGroupList.size(); ++i) {
         Group group = (Group)defaultGroupList.get(i);
         if (!Group.DEFAULT_GROUP.equals(group)) {
            int index = groupList.indexOf(group);
            if (index != -1 && (i != 0 || index != defaultGroupIndex - 1) && (i != defaultGroupList.size() - 1 || index != defaultGroupIndex + 1)) {
               throw LOG.getUnableToExpandDefaultGroupListException(defaultGroupList, groupList);
            }
         }
      }

   }

   private List buildTempGroupList(List defaultGroupSequence) {
      List groups = new ArrayList();
      Iterator var3 = defaultGroupSequence.iterator();

      while(var3.hasNext()) {
         Class clazz = (Class)var3.next();
         Group g = new Group(clazz);
         groups.add(g);
      }

      return groups;
   }
}
