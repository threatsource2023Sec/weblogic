package com.octetstring.ldapv3;

import com.asn1c.core.ASN1Object;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

public class ModifyRequest_modification extends ArrayList implements ASN1Object {
   public ModifyRequest_modification() {
   }

   public ModifyRequest_modification(Collection value) {
      super(value);
   }

   public ModifyRequest_modification(ModifyRequest_modification_Seq[] value) {
      super(Arrays.asList(value));
   }

   public void addElement(ModifyRequest_modification_Seq item) {
      this.add(item);
   }

   public void addElement(int index, ModifyRequest_modification_Seq item) {
      this.add(index, item);
   }

   public ModifyRequest_modification_Seq getElement(int index) {
      return (ModifyRequest_modification_Seq)this.get(index);
   }

   public boolean containsElement(ModifyRequest_modification_Seq elem) {
      return this.contains(elem);
   }

   public int indexOfElement(ModifyRequest_modification_Seq elem) {
      return this.indexOf(elem);
   }

   public int lastIndexOfElement(ModifyRequest_modification_Seq elem) {
      return this.lastIndexOf(elem);
   }

   public ModifyRequest_modification_Seq setElement(int index, ModifyRequest_modification_Seq elem) {
      return (ModifyRequest_modification_Seq)this.set(index, elem);
   }

   public String toString() {
      StringBuffer buf = new StringBuffer("{ ");
      Iterator iterator = this.iterator();

      while(iterator.hasNext()) {
         buf.append(iterator.next());
         if (iterator.hasNext()) {
            buf.append(", ");
         }
      }

      return buf.append(" }").toString();
   }

   public void print(PrintWriter out, String indent, String prefix, String suffix, int flags) {
      Iterator iterator = this.iterator();
      String newindent = indent + "    ";
      out.println(indent + prefix + "{");

      while(iterator.hasNext()) {
         ASN1Object subval = (ASN1Object)iterator.next();
         subval.print(out, newindent, "", iterator.hasNext() ? "," : "", flags);
      }

      out.println(indent + "}" + suffix);
   }
}
