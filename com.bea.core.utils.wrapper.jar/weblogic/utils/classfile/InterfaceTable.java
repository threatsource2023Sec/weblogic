package weblogic.utils.classfile;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Iterator;
import java.util.LinkedList;
import weblogic.utils.classfile.cp.CPClass;
import weblogic.utils.classfile.cp.ConstantPool;

public class InterfaceTable {
   private ConstantPool cp;
   private LinkedList interfaces = new LinkedList();

   public InterfaceTable(ClassFile classFile) {
      this.cp = classFile.getConstantPool();
   }

   public Iterator classes() {
      return this.interfaces.iterator();
   }

   public void addInterface(String name) {
      this.interfaces.add(this.cp.getClass(name));
   }

   public void removeInterface(String name) {
      this.interfaces.remove(this.cp.getClass(name));
      this.cp.removeClass(name);
   }

   public boolean hasInterface(String name) {
      int i = 0;

      for(int len = this.interfaces.size(); i < len; ++i) {
         CPClass c = (CPClass)this.interfaces.get(i);
         if (c.name.getValue().equals(name)) {
            return true;
         }
      }

      return false;
   }

   public void read(DataInput in) throws IOException {
      try {
         int size = in.readUnsignedShort();

         for(int i = 0; i < size; ++i) {
            int idx = in.readUnsignedShort();
            this.interfaces.add(this.cp.classAt(idx));
         }

      } catch (MalformedClassException var5) {
         throw new IOException(String.valueOf(var5));
      }
   }

   public void write(DataOutput out) throws IOException {
      int idx = -1;

      try {
         synchronized(this.interfaces) {
            out.writeShort(this.interfaces.size());
            Iterator i = this.interfaces.iterator();

            while(i.hasNext()) {
               idx = ((CPClass)i.next()).getIndex();
               out.writeShort(idx);
            }

         }
      } catch (ClassCastException var7) {
         throw new IOException("Entry " + idx + " in constant_pool not a CONSTANT_Class.");
      }
   }

   public void dump(PrintStream out) {
      out.println("Interfaces:");
      Iterator i = this.interfaces.iterator();

      while(i.hasNext()) {
         CPClass cl = (CPClass)i.next();
         System.out.println("  " + cl.name.getValue());
      }

   }

   private static void say(String s) {
      System.out.println(s);
   }
}
