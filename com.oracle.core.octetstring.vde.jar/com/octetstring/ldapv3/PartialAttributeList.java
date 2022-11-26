package com.octetstring.ldapv3;

import com.asn1c.core.ASN1Object;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

public class PartialAttributeList extends ArrayList implements ASN1Object {
   public PartialAttributeList() {
   }

   public PartialAttributeList(Collection value) {
      super(value);
   }

   public PartialAttributeList(PartialAttributeList_Seq[] value) {
      super(Arrays.asList(value));
   }

   public void addElement(PartialAttributeList_Seq item) {
      this.add(item);
   }

   public void addElement(int index, PartialAttributeList_Seq item) {
      this.add(index, item);
   }

   public PartialAttributeList_Seq getElement(int index) {
      return (PartialAttributeList_Seq)this.get(index);
   }

   public boolean containsElement(PartialAttributeList_Seq elem) {
      return this.contains(elem);
   }

   public int indexOfElement(PartialAttributeList_Seq elem) {
      return this.indexOf(elem);
   }

   public int lastIndexOfElement(PartialAttributeList_Seq elem) {
      return this.lastIndexOf(elem);
   }

   public PartialAttributeList_Seq setElement(int index, PartialAttributeList_Seq elem) {
      return (PartialAttributeList_Seq)this.set(index, elem);
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
