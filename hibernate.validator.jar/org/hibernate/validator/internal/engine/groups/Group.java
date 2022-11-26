package org.hibernate.validator.internal.engine.groups;

import javax.validation.groups.Default;

public class Group {
   public static final Group DEFAULT_GROUP = new Group(Default.class);
   private Class group;

   public Group(Class group) {
      this.group = group;
   }

   public Class getDefiningClass() {
      return this.group;
   }

   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (o != null && this.getClass() == o.getClass()) {
         Group group1 = (Group)o;
         if (this.group != null) {
            if (!this.group.equals(group1.group)) {
               return false;
            }
         } else if (group1.group != null) {
            return false;
         }

         return true;
      } else {
         return false;
      }
   }

   public boolean isDefaultGroup() {
      return this.getDefiningClass().getName().equals(Default.class.getName());
   }

   public int hashCode() {
      return this.group != null ? this.group.hashCode() : 0;
   }

   public String toString() {
      return "Group{group=" + this.group.getName() + '}';
   }
}
