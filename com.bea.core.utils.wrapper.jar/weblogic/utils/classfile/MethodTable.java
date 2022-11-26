package weblogic.utils.classfile;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Comparator;
import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeSet;

public class MethodTable {
   private static final boolean verbose = false;
   private ClassFile classFile;
   SortedSet methods = new TreeSet(new MethodComparator());

   public MethodTable(ClassFile classFile) {
      this.classFile = classFile;
   }

   public Iterator getMethods() {
      return this.methods.iterator();
   }

   public void addMethod(MethodInfo mi) {
      this.methods.add(mi);
   }

   public void read(DataInput in) throws IOException, BadBytecodesException {
      int size = in.readUnsignedShort();

      for(int i = 0; i < size; ++i) {
         MethodInfo method = new MethodInfo(this.classFile);
         method.read(in);
         this.methods.add(method);
      }

   }

   public void write(DataOutput out) throws IOException, BadBytecodesException {
      synchronized(this.methods) {
         Iterator i = this.methods.iterator();
         out.writeShort(this.methods.size());

         while(i.hasNext()) {
            ClassMember member = (ClassMember)i.next();
            member.write(out);
         }

      }
   }

   public void dump(PrintStream out) throws BadBytecodesException {
      out.println("methods: ...");
      Iterator i = this.methods.iterator();

      while(i.hasNext()) {
         ((ClassMember)i.next()).dump(out);
      }

   }

   public static void say(String s) {
      System.out.println(s);
   }

   private static class MethodComparator implements Comparator {
      private MethodComparator() {
      }

      public int compare(MethodInfo m1, MethodInfo m2) {
         return m1.toString().compareTo(m2.toString());
      }

      // $FF: synthetic method
      MethodComparator(Object x0) {
         this();
      }
   }
}
