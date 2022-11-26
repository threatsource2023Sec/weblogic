package org.python.apache.xerces.impl.xs.identity;

import org.python.apache.xerces.xs.XSIDCDefinition;

public class KeyRef extends IdentityConstraint {
   protected final UniqueOrKey fKey;

   public KeyRef(String var1, String var2, String var3, UniqueOrKey var4) {
      super(var1, var2, var3);
      this.fKey = var4;
      this.type = 2;
   }

   public UniqueOrKey getKey() {
      return this.fKey;
   }

   public XSIDCDefinition getRefKey() {
      return this.fKey;
   }
}
