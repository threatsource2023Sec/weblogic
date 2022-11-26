package org.apache.openjpa.slice;

import org.apache.openjpa.conf.OpenJPAConfiguration;

public class Slice implements Comparable {
   private final String name;
   private final transient OpenJPAConfiguration conf;
   private transient Status status;

   public Slice(String name, OpenJPAConfiguration conf) {
      this.name = name;
      this.conf = conf;
      this.status = Slice.Status.NOT_INITIALIZED;
   }

   public String getName() {
      return this.name;
   }

   public OpenJPAConfiguration getConfiguration() {
      return this.conf;
   }

   public Status getStatus() {
      return this.status;
   }

   public void setStatus(Status status) {
      this.status = status;
   }

   public boolean isActive() {
      return this.status == Slice.Status.ACTIVE;
   }

   public String toString() {
      return this.name;
   }

   public int compareTo(Slice other) {
      return this.name.compareTo(other.name);
   }

   public boolean equals(Object other) {
      if (this == other) {
         return true;
      } else if (other == null) {
         return false;
      } else {
         return other instanceof Slice ? this.name.equals(((Slice)other).getName()) : false;
      }
   }

   public int hashCode() {
      return this.name.hashCode();
   }

   public static enum Status {
      NOT_INITIALIZED,
      ACTIVE,
      INACTIVE,
      EXCLUDED;
   }
}
