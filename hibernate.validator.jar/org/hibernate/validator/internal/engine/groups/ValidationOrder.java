package org.hibernate.validator.internal.engine.groups;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import javax.validation.GroupDefinitionException;

public interface ValidationOrder {
   ValidationOrder DEFAULT_GROUP = new DefaultGroupValidationOrder();
   ValidationOrder DEFAULT_SEQUENCE = new DefaultSequenceValidationOrder();

   Iterator getGroupIterator();

   Iterator getSequenceIterator();

   void assertDefaultGroupSequenceIsExpandable(List var1) throws GroupDefinitionException;

   public static class DefaultGroupValidationOrder implements ValidationOrder {
      private final List defaultGroups;

      private DefaultGroupValidationOrder() {
         this.defaultGroups = Collections.singletonList(Group.DEFAULT_GROUP);
      }

      public Iterator getGroupIterator() {
         return this.defaultGroups.iterator();
      }

      public Iterator getSequenceIterator() {
         return Collections.emptyIterator();
      }

      public void assertDefaultGroupSequenceIsExpandable(List defaultGroupSequence) throws GroupDefinitionException {
      }

      // $FF: synthetic method
      DefaultGroupValidationOrder(Object x0) {
         this();
      }
   }

   public static class DefaultSequenceValidationOrder implements ValidationOrder {
      private final List defaultSequences;

      private DefaultSequenceValidationOrder() {
         this.defaultSequences = Collections.singletonList(Sequence.DEFAULT);
      }

      public Iterator getGroupIterator() {
         return Collections.emptyIterator();
      }

      public Iterator getSequenceIterator() {
         return this.defaultSequences.iterator();
      }

      public void assertDefaultGroupSequenceIsExpandable(List defaultGroupSequence) throws GroupDefinitionException {
      }

      // $FF: synthetic method
      DefaultSequenceValidationOrder(Object x0) {
         this();
      }
   }
}
