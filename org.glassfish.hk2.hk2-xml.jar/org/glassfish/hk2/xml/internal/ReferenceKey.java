package org.glassfish.hk2.xml.internal;

import org.glassfish.hk2.utilities.general.GeneralUtilities;

class ReferenceKey {
   private final String type;
   private final String xmlID;

   ReferenceKey(String type, String xmlID) {
      this.type = type;
      this.xmlID = xmlID;
   }

   public int hashCode() {
      return this.type.hashCode() ^ this.xmlID.hashCode();
   }

   public boolean equals(Object o) {
      if (o == null) {
         return false;
      } else if (!(o instanceof ReferenceKey)) {
         return false;
      } else {
         ReferenceKey other = (ReferenceKey)o;
         return GeneralUtilities.safeEquals(this.type, other.type) && GeneralUtilities.safeEquals(this.xmlID, other.xmlID);
      }
   }

   public String toString() {
      return "ReferenceKey(" + this.type + "," + this.xmlID + "," + System.identityHashCode(this) + ")";
   }
}
