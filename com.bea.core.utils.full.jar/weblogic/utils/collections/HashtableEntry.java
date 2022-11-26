package weblogic.utils.collections;

class HashtableEntry implements Cloneable {
   long key;
   Object value;
   HashtableEntry next;

   protected Object clone() {
      HashtableEntry ent = new HashtableEntry();
      ent.key = this.key;
      ent.value = this.value;
      ent.next = this.next != null ? (HashtableEntry)this.next.clone() : null;
      return ent;
   }
}
