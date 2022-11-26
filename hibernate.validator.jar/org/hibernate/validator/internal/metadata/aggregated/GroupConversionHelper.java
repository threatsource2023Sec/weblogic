package org.hibernate.validator.internal.metadata.aggregated;

import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import org.hibernate.validator.internal.metadata.descriptor.GroupConversionDescriptorImpl;
import org.hibernate.validator.internal.util.CollectionHelper;

public class GroupConversionHelper {
   static final GroupConversionHelper EMPTY = new GroupConversionHelper(Collections.emptyMap());
   private final Map groupConversions;

   private GroupConversionHelper(Map groupConversions) {
      this.groupConversions = CollectionHelper.toImmutableMap(groupConversions);
   }

   public static GroupConversionHelper of(Map groupConversions) {
      return groupConversions.isEmpty() ? EMPTY : new GroupConversionHelper(groupConversions);
   }

   public Class convertGroup(Class from) {
      Class to = (Class)this.groupConversions.get(from);
      return to != null ? to : from;
   }

   public Set asDescriptors() {
      Set descriptors = CollectionHelper.newHashSet(this.groupConversions.size());
      Iterator var2 = this.groupConversions.entrySet().iterator();

      while(var2.hasNext()) {
         Map.Entry conversion = (Map.Entry)var2.next();
         descriptors.add(new GroupConversionDescriptorImpl((Class)conversion.getKey(), (Class)conversion.getValue()));
      }

      return CollectionHelper.toImmutableSet(descriptors);
   }

   boolean isEmpty() {
      return this.groupConversions.isEmpty();
   }

   public String toString() {
      StringBuilder sb = new StringBuilder();
      sb.append(this.getClass().getSimpleName());
      sb.append(" [");
      sb.append("groupConversions=").append(this.groupConversions);
      sb.append("]");
      return sb.toString();
   }
}
