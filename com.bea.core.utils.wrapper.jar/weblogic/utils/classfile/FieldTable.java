package weblogic.utils.classfile;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Comparator;
import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeSet;
import weblogic.utils.collections.ArrayMap;

public class FieldTable {
   private ClassFile classFile;
   private ArrayMap fields = new ArrayMap();
   private static final boolean debug = false;

   public FieldTable(ClassFile classFile) {
      this.classFile = classFile;
   }

   public Iterator getFields() {
      return this.fields.values().iterator();
   }

   public void addField(String name, FieldInfo fi) {
      this.fields.put(name, fi);
   }

   public FieldInfo getField(String name) {
      return (FieldInfo)this.fields.get(name);
   }

   public void read(DataInput in) throws IOException, BadBytecodesException {
      int size = in.readUnsignedShort();
      debug("field_table.size = " + size);

      for(int i = 0; i < size; ++i) {
         FieldInfo field = new FieldInfo(this.classFile);
         field.read(in);
         String fieldName = field.getName() + ":" + field.getDescriptor();
         this.fields.put(fieldName, field);
         debug("field_info[" + i + "] = " + field.name.getValue() + "/" + field.descriptor.getValue());
      }

   }

   public void write(DataOutput out) throws IOException, BadBytecodesException {
      synchronized(this.fields) {
         SortedSet sortedFields = new TreeSet(new FieldComparator());
         Iterator mi = this.fields.values().iterator();

         while(mi.hasNext()) {
            sortedFields.add(mi.next());
         }

         Iterator field = sortedFields.iterator();
         out.writeShort(sortedFields.size());

         while(field.hasNext()) {
            ((ClassMember)field.next()).write(out);
         }

      }
   }

   public void dump(PrintStream out) {
      out.println("fields: ...");
   }

   public static void debug(String msg) {
   }

   private static class FieldComparator implements Comparator {
      private FieldComparator() {
      }

      public int compare(FieldInfo f1, FieldInfo f2) {
         return f1.getName().compareTo(f2.getName());
      }

      // $FF: synthetic method
      FieldComparator(Object x0) {
         this();
      }
   }
}
