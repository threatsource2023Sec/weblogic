package serp.bytecode.lowlevel;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import serp.bytecode.visitor.BCVisitor;
import serp.bytecode.visitor.VisitAcceptor;
import serp.util.Numbers;

public class ConstantPool implements VisitAcceptor {
   private List _entries = new ArrayList(50);
   private Map _lookup = new HashMap(50);

   public Entry[] getEntries() {
      List entries = new ArrayList(this._entries.size());
      Iterator itr = this._entries.iterator();

      while(itr.hasNext()) {
         Entry entry = (Entry)itr.next();
         if (entry != null) {
            entries.add(entry);
         }
      }

      return (Entry[])((Entry[])entries.toArray(new Entry[entries.size()]));
   }

   public Entry getEntry(int index) {
      Entry entry = (Entry)this._entries.get(index - 1);
      if (entry == null) {
         throw new IndexOutOfBoundsException("index = " + index);
      } else {
         return entry;
      }
   }

   public int indexOf(Entry entry) {
      return entry != null && entry.getPool() == this ? entry.getIndex() : 0;
   }

   public int addEntry(Entry entry) {
      if (entry.getPool() != this) {
         this.addEntry(getKey(entry), entry);
      }

      return entry.getIndex();
   }

   private int addEntry(Object key, Entry entry) {
      entry.setPool(this);
      this._entries.add(entry);
      entry.setIndex(this._entries.size());
      this._lookup.put(key, entry);
      if (entry.isWide()) {
         this._entries.add((Object)null);
      }

      return entry.getIndex();
   }

   public boolean removeEntry(Entry entry) {
      if (entry != null && entry.getPool() == this) {
         int index = entry.getIndex() - 1;
         entry.setPool((ConstantPool)null);
         entry.setIndex(0);
         this._entries.remove(index);
         if (entry.isWide()) {
            this._entries.remove(index);
         }

         this._lookup.remove(getKey(entry));

         for(int i = index; i < this._entries.size(); ++i) {
            entry = (Entry)this._entries.get(i);
            if (entry != null) {
               Object key = getKey(entry);
               this._lookup.remove(key);
               entry.setIndex(i + 1);
               this._lookup.put(key, entry);
            }
         }

         return true;
      } else {
         return false;
      }
   }

   public void clear() {
      Iterator itr = this._entries.iterator();

      while(itr.hasNext()) {
         Entry entry = (Entry)itr.next();
         if (entry != null) {
            entry.setPool((ConstantPool)null);
            entry.setIndex(0);
         }
      }

      this._entries.clear();
      this._lookup.clear();
   }

   public int size() {
      return this._entries.size();
   }

   public int findUTF8Entry(String value, boolean add) {
      if (value == null) {
         if (add) {
            throw new NullPointerException("value = null");
         } else {
            return 0;
         }
      } else {
         int index = this.find(value);
         return add && index <= 0 ? this.addEntry(value, new UTF8Entry(value)) : index;
      }
   }

   public int findDoubleEntry(double value, boolean add) {
      Double key = new Double(value);
      int index = this.find(key);
      return add && index <= 0 ? this.addEntry(key, new DoubleEntry(value)) : index;
   }

   public int findFloatEntry(float value, boolean add) {
      Float key = new Float(value);
      int index = this.find(key);
      return add && index <= 0 ? this.addEntry(key, new FloatEntry(value)) : index;
   }

   public int findIntEntry(int value, boolean add) {
      Integer key = Numbers.valueOf(value);
      int index = this.find(key);
      return add && index <= 0 ? this.addEntry(key, new IntEntry(value)) : index;
   }

   public int findLongEntry(long value, boolean add) {
      Long key = Numbers.valueOf(value);
      int index = this.find(key);
      return add && index <= 0 ? this.addEntry(key, new LongEntry(value)) : index;
   }

   public int findStringEntry(String value, boolean add) {
      int valueIndex = this.findUTF8Entry(value, add);
      if (valueIndex == 0) {
         return 0;
      } else {
         StringKey key = new StringKey(valueIndex);
         int index = this.find(key);
         return add && index <= 0 ? this.addEntry(key, new StringEntry(valueIndex)) : index;
      }
   }

   public int findClassEntry(String name, boolean add) {
      int nameIndex = this.findUTF8Entry(name, add);
      if (nameIndex == 0) {
         return 0;
      } else {
         ClassKey key = new ClassKey(nameIndex);
         int index = this.find(key);
         return add && index <= 0 ? this.addEntry(key, new ClassEntry(nameIndex)) : index;
      }
   }

   public int findNameAndTypeEntry(String name, String desc, boolean add) {
      int nameIndex = this.findUTF8Entry(name, add);
      if (nameIndex == 0) {
         return 0;
      } else {
         int descIndex = this.findUTF8Entry(desc, add);
         if (descIndex == 0) {
            return 0;
         } else {
            NameAndTypeKey key = new NameAndTypeKey(nameIndex, descIndex);
            int index = this.find(key);
            return add && index <= 0 ? this.addEntry(key, new NameAndTypeEntry(nameIndex, descIndex)) : index;
         }
      }
   }

   public int findFieldEntry(String owner, String name, String desc, boolean add) {
      return this.findComplexEntry(owner, name, desc, 9, add);
   }

   public int findMethodEntry(String owner, String name, String desc, boolean add) {
      return this.findComplexEntry(owner, name, desc, 10, add);
   }

   public int findInterfaceMethodEntry(String owner, String name, String desc, boolean add) {
      return this.findComplexEntry(owner, name, desc, 11, add);
   }

   public int findInvokeDynamicEntry(int bootstrapMethodIndex, String name, String desc, boolean add) {
      int descIndex = this.findNameAndTypeEntry(name, desc, add);
      if (descIndex == 0) {
         return 0;
      } else {
         Object key = new InvokeDynamicKey(bootstrapMethodIndex, descIndex);
         int index = this.find(key);
         if (add && index <= 0) {
            Entry entry = new InvokeDynamicEntry(bootstrapMethodIndex, descIndex);
            return this.addEntry(key, entry);
         } else {
            return index;
         }
      }
   }

   private int findComplexEntry(String owner, String name, String desc, int type, boolean add) {
      int classIndex = this.findClassEntry(owner, add);
      if (classIndex == 0) {
         return 0;
      } else {
         int descIndex = this.findNameAndTypeEntry(name, desc, add);
         if (descIndex == 0) {
            return 0;
         } else {
            Object key = null;
            switch (type) {
               case 9:
                  key = new FieldKey(classIndex, descIndex);
                  break;
               case 10:
                  key = new MethodKey(classIndex, descIndex);
                  break;
               case 11:
                  key = new InterfaceMethodKey(classIndex, descIndex);
            }

            int index = this.find(key);
            if (add && index <= 0) {
               Entry entry = null;
               switch (type) {
                  case 9:
                     entry = new FieldEntry(classIndex, descIndex);
                     break;
                  case 10:
                     entry = new MethodEntry(classIndex, descIndex);
                     break;
                  case 11:
                     entry = new InterfaceMethodEntry(classIndex, descIndex);
               }

               return this.addEntry(key, (Entry)entry);
            } else {
               return index;
            }
         }
      }
   }

   public void acceptVisit(BCVisitor visit) {
      visit.enterConstantPool(this);
      Iterator itr = this._entries.iterator();

      while(itr.hasNext()) {
         Entry entry = (Entry)itr.next();
         if (entry != null) {
            visit.enterEntry(entry);
            entry.acceptVisit(visit);
            visit.exitEntry(entry);
         }
      }

      visit.exitConstantPool(this);
   }

   public void read(DataInput in) throws IOException {
      this.clear();
      int entryCount = in.readUnsignedShort();

      for(int i = 1; i < entryCount; ++i) {
         Entry entry = Entry.read(in);
         this.addEntry(entry);
         if (entry.isWide()) {
            ++i;
         }
      }

   }

   public void write(DataOutput out) throws IOException {
      out.writeShort(this._entries.size() + 1);
      Iterator itr = this._entries.iterator();

      while(itr.hasNext()) {
         Entry entry = (Entry)itr.next();
         if (entry != null) {
            Entry.write(entry, out);
         }
      }

   }

   void modifyEntry(Object origKey, Entry entry) {
      this._lookup.remove(origKey);
      this._lookup.put(getKey(entry), entry);
   }

   private int find(Object key) {
      Entry entry = (Entry)this._lookup.get(key);
      return entry == null ? 0 : entry.getIndex();
   }

   static Object getKey(Entry entry) {
      switch (entry.getType()) {
         case 1:
         case 3:
         case 4:
         case 5:
         case 6:
            return ((ConstantEntry)entry).getConstant();
         case 2:
         case 13:
         case 14:
         case 15:
         case 16:
         case 17:
         default:
            return null;
         case 7:
            return new ClassKey(((ClassEntry)entry).getNameIndex());
         case 8:
            return new StringKey(((StringEntry)entry).getStringIndex());
         case 9:
            FieldEntry fe = (FieldEntry)entry;
            return new FieldKey(fe.getClassIndex(), fe.getNameAndTypeIndex());
         case 10:
            MethodEntry me = (MethodEntry)entry;
            return new MethodKey(me.getClassIndex(), me.getNameAndTypeIndex());
         case 11:
            InterfaceMethodEntry ime = (InterfaceMethodEntry)entry;
            return new InterfaceMethodKey(ime.getClassIndex(), ime.getNameAndTypeIndex());
         case 12:
            NameAndTypeEntry nte = (NameAndTypeEntry)entry;
            return new NameAndTypeKey(nte.getNameIndex(), nte.getDescriptorIndex());
         case 18:
            InvokeDynamicEntry ide = (InvokeDynamicEntry)entry;
            return new InvokeDynamicKey(ide.getBootstrapMethodAttrIndex(), ide.getNameAndTypeIndex());
      }
   }

   private static class InvokeDynamicKey extends DoublePtrKey {
      public InvokeDynamicKey(int index1, int index2) {
         super(index1, index2);
      }
   }

   private static class InterfaceMethodKey extends DoublePtrKey {
      public InterfaceMethodKey(int index1, int index2) {
         super(index1, index2);
      }
   }

   private static class MethodKey extends DoublePtrKey {
      public MethodKey(int index1, int index2) {
         super(index1, index2);
      }
   }

   private static class FieldKey extends DoublePtrKey {
      public FieldKey(int index1, int index2) {
         super(index1, index2);
      }
   }

   private static class NameAndTypeKey extends DoublePtrKey {
      public NameAndTypeKey(int index1, int index2) {
         super(index1, index2);
      }
   }

   private abstract static class DoublePtrKey {
      private final int _index1;
      private final int _index2;

      public DoublePtrKey(int index1, int index2) {
         this._index1 = index1;
         this._index2 = index2;
      }

      public int hashCode() {
         return this._index1 ^ this._index2;
      }

      public boolean equals(Object other) {
         if (other == this) {
            return true;
         } else if (other.getClass() != this.getClass()) {
            return false;
         } else {
            DoublePtrKey key = (DoublePtrKey)other;
            return key._index1 == this._index1 && key._index2 == this._index2;
         }
      }
   }

   private static class ClassKey extends PtrKey {
      public ClassKey(int index) {
         super(index);
      }
   }

   private static class StringKey extends PtrKey {
      public StringKey(int index) {
         super(index);
      }
   }

   private abstract static class PtrKey {
      private final int _index;

      public PtrKey(int index) {
         this._index = index;
      }

      public int hashCode() {
         return this._index;
      }

      public boolean equals(Object other) {
         if (other == this) {
            return true;
         } else if (other.getClass() != this.getClass()) {
            return false;
         } else {
            return ((PtrKey)other)._index == this._index;
         }
      }
   }
}
