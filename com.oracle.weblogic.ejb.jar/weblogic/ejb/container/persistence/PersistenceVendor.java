package weblogic.ejb.container.persistence;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public final class PersistenceVendor {
   private String vendorName;
   private final Set persistenceTypes = new HashSet();

   public String getName() {
      return this.vendorName;
   }

   public void setName(String name) {
      this.vendorName = name;
   }

   public void addType(PersistenceType type) {
      this.persistenceTypes.add(type);
   }

   public Set getTypes() {
      return this.persistenceTypes;
   }

   public String toString() {
      StringBuilder str = new StringBuilder();
      str.append("[PersistenceVendor: ");
      str.append("\n\tvendorName : ").append(this.vendorName);
      Iterator var2 = this.persistenceTypes.iterator();

      while(var2.hasNext()) {
         PersistenceType pt = (PersistenceType)var2.next();
         str.append("\n\t" + pt);
      }

      str.append("\n PersistenceVendor]");
      return str.toString();
   }

   public boolean equals(Object other) {
      if (null == other) {
         return false;
      } else if (!(other instanceof PersistenceVendor)) {
         return false;
      } else {
         PersistenceVendor pv = (PersistenceVendor)other;
         if (this.getName() == null != (pv.getName() == null)) {
            return false;
         } else {
            return this.getName() == null && pv.getName() == null || this.getName().equals(pv.getName());
         }
      }
   }

   public int hashCode() {
      return this.vendorName.hashCode();
   }
}
