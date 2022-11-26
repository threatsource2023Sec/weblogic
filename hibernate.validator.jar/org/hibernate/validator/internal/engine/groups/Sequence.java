package org.hibernate.validator.internal.engine.groups;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.validation.GroupSequence;
import javax.validation.groups.Default;
import org.hibernate.validator.internal.util.logging.Log;
import org.hibernate.validator.internal.util.logging.LoggerFactory;

public class Sequence implements Iterable {
   public static Sequence DEFAULT = new Sequence();
   private static final Log LOG = LoggerFactory.make(MethodHandles.lookup());
   private final Class sequence;
   private List groups;
   private List expandedGroups;

   private Sequence() {
      this.sequence = Default.class;
      this.groups = Collections.singletonList(Group.DEFAULT_GROUP);
      this.expandedGroups = Collections.singletonList(new GroupWithInheritance(Collections.singleton(Group.DEFAULT_GROUP)));
   }

   public Sequence(Class sequence, List groups) {
      this.groups = groups;
      this.sequence = sequence;
   }

   public List getComposingGroups() {
      return this.groups;
   }

   public Class getDefiningClass() {
      return this.sequence;
   }

   public void expandInheritedGroups() {
      if (this.expandedGroups == null) {
         this.expandedGroups = new ArrayList();
         ArrayList tmpGroups = new ArrayList();
         Iterator var2 = this.groups.iterator();

         while(var2.hasNext()) {
            Group group = (Group)var2.next();
            HashSet groupsOfGroup = new HashSet();
            groupsOfGroup.add(group);
            this.addInheritedGroups(group, groupsOfGroup);
            this.expandedGroups.add(new GroupWithInheritance(groupsOfGroup));
            tmpGroups.addAll(groupsOfGroup);
         }

         this.groups = tmpGroups;
      }
   }

   public Iterator iterator() {
      return this.expandedGroups.iterator();
   }

   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (o != null && this.getClass() == o.getClass()) {
         Sequence sequence1 = (Sequence)o;
         if (this.groups != null) {
            if (!this.groups.equals(sequence1.groups)) {
               return false;
            }
         } else if (sequence1.groups != null) {
            return false;
         }

         if (this.sequence != null) {
            if (this.sequence.equals(sequence1.sequence)) {
               return true;
            }
         } else if (sequence1.sequence == null) {
            return true;
         }

         return false;
      } else {
         return false;
      }
   }

   public int hashCode() {
      int result = this.sequence != null ? this.sequence.hashCode() : 0;
      result = 31 * result + (this.groups != null ? this.groups.hashCode() : 0);
      return result;
   }

   public String toString() {
      StringBuilder sb = new StringBuilder();
      sb.append("Sequence");
      sb.append("{sequence=").append(this.sequence);
      sb.append(", groups=").append(this.groups);
      sb.append('}');
      return sb.toString();
   }

   private void addInheritedGroups(Group group, Set expandedGroups) {
      Class[] var3 = group.getDefiningClass().getInterfaces();
      int var4 = var3.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         Class inheritedGroup = var3[var5];
         if (this.isGroupSequence(inheritedGroup)) {
            throw LOG.getSequenceDefinitionsNotAllowedException();
         }

         Group g = new Group(inheritedGroup);
         expandedGroups.add(g);
         this.addInheritedGroups(g, expandedGroups);
      }

   }

   private boolean isGroupSequence(Class clazz) {
      return clazz.getAnnotation(GroupSequence.class) != null;
   }
}
