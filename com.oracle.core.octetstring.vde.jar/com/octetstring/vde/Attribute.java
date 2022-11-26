package com.octetstring.vde;

import com.octetstring.vde.syntax.DirectoryString;
import java.util.Vector;

public class Attribute {
   public DirectoryString type = null;
   public Vector values = null;

   Attribute() {
   }

   Attribute(DirectoryString type, Vector values) {
      this.type = type;
      this.values = values;
   }

   public Object clone() {
      DirectoryString newType = new DirectoryString(this.type.getBytes());
      Vector newValues = new Vector(this.values);
      return new Attribute(newType, newValues);
   }

   public boolean equals(Attribute other) {
      return this.type.equals(other.type);
   }

   public boolean equals(DirectoryString other) {
      return this.type.equals(other);
   }
}
