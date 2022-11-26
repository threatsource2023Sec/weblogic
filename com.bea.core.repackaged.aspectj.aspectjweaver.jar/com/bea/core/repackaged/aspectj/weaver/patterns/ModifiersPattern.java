package com.bea.core.repackaged.aspectj.weaver.patterns;

import com.bea.core.repackaged.aspectj.weaver.CompressingDataOutputStream;
import com.bea.core.repackaged.aspectj.weaver.VersionedDataInputStream;
import java.io.IOException;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

public class ModifiersPattern extends PatternNode {
   private int requiredModifiers;
   private int forbiddenModifiers;
   public static final ModifiersPattern ANY = new ModifiersPattern(0, 0);
   private static Map modifierFlags = null;

   public ModifiersPattern(int requiredModifiers, int forbiddenModifiers) {
      this.requiredModifiers = requiredModifiers;
      this.forbiddenModifiers = forbiddenModifiers;
   }

   public String toString() {
      if (this == ANY) {
         return "";
      } else {
         String ret = Modifier.toString(this.requiredModifiers);
         return this.forbiddenModifiers == 0 ? ret : ret + " !" + Modifier.toString(this.forbiddenModifiers);
      }
   }

   public boolean equals(Object other) {
      if (!(other instanceof ModifiersPattern)) {
         return false;
      } else {
         ModifiersPattern o = (ModifiersPattern)other;
         return o.requiredModifiers == this.requiredModifiers && o.forbiddenModifiers == this.forbiddenModifiers;
      }
   }

   public int hashCode() {
      int result = 17;
      result = 37 * result + this.requiredModifiers;
      result = 37 * result + this.forbiddenModifiers;
      return result;
   }

   public boolean matches(int modifiers) {
      return (modifiers & this.requiredModifiers) == this.requiredModifiers && (modifiers & this.forbiddenModifiers) == 0;
   }

   public static ModifiersPattern read(VersionedDataInputStream s) throws IOException {
      int requiredModifiers = s.readShort();
      int forbiddenModifiers = s.readShort();
      return requiredModifiers == 0 && forbiddenModifiers == 0 ? ANY : new ModifiersPattern(requiredModifiers, forbiddenModifiers);
   }

   public void write(CompressingDataOutputStream s) throws IOException {
      s.writeShort(this.requiredModifiers);
      s.writeShort(this.forbiddenModifiers);
   }

   public static int getModifierFlag(String name) {
      Integer flag = (Integer)modifierFlags.get(name);
      return flag == null ? -1 : flag;
   }

   public Object accept(PatternNodeVisitor visitor, Object data) {
      return visitor.visit(this, data);
   }

   static {
      modifierFlags = new HashMap();

      for(int flag = 1; flag <= 2048; flag <<= 1) {
         String flagName = Modifier.toString(flag);
         modifierFlags.put(flagName, new Integer(flag));
      }

      modifierFlags.put("synthetic", new Integer(4096));
   }
}
