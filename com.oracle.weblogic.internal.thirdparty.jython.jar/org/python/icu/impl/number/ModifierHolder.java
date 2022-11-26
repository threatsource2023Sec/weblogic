package org.python.icu.impl.number;

import java.util.ArrayDeque;
import java.util.Iterator;

public class ModifierHolder {
   private ArrayDeque mods = new ArrayDeque();

   public ModifierHolder clear() {
      this.mods.clear();
      return this;
   }

   public void add(Modifier modifier) {
      if (modifier != null) {
         this.mods.addFirst(modifier);
      }

   }

   public Modifier peekLast() {
      return (Modifier)this.mods.peekLast();
   }

   public Modifier removeLast() {
      return (Modifier)this.mods.removeLast();
   }

   public int applyAll(NumberStringBuilder string, int leftIndex, int rightIndex) {
      int addedLength;
      Modifier mod;
      for(addedLength = 0; !this.mods.isEmpty(); addedLength += mod.apply(string, leftIndex, rightIndex + addedLength)) {
         mod = (Modifier)this.mods.removeFirst();
      }

      return addedLength;
   }

   public int applyStrong(NumberStringBuilder string, int leftIndex, int rightIndex) {
      int addedLength;
      Modifier mod;
      for(addedLength = 0; !this.mods.isEmpty() && ((Modifier)this.mods.peekFirst()).isStrong(); addedLength += mod.apply(string, leftIndex, rightIndex + addedLength)) {
         mod = (Modifier)this.mods.removeFirst();
      }

      return addedLength;
   }

   public int totalLength() {
      int length = 0;
      Iterator var2 = this.mods.iterator();

      while(var2.hasNext()) {
         Modifier mod = (Modifier)var2.next();
         if (mod != null) {
            length += mod.length();
         }
      }

      return length;
   }
}
