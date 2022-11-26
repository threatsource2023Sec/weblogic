package org.hibernate.validator.internal.engine.groups;

import java.util.Iterator;
import java.util.Set;
import org.hibernate.validator.internal.util.CollectionHelper;

public class GroupWithInheritance implements Iterable {
   private final Set groups;

   public GroupWithInheritance(Set groups) {
      this.groups = CollectionHelper.toImmutableSet(groups);
   }

   public Iterator iterator() {
      return this.groups.iterator();
   }
}
