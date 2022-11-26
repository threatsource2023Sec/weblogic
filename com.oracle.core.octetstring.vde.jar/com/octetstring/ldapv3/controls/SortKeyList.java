package com.octetstring.ldapv3.controls;

import com.asn1c.core.ASN1Object;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

public class SortKeyList extends ArrayList implements ASN1Object {
   public SortKeyList() {
   }

   public SortKeyList(Collection value) {
      super(value);
   }

   public SortKeyList(SortKeyList_Seq[] value) {
      super(Arrays.asList(value));
   }

   public void addElement(SortKeyList_Seq item) {
      this.add(item);
   }

   public void addElement(int index, SortKeyList_Seq item) {
      this.add(index, item);
   }

   public SortKeyList_Seq getElement(int index) {
      return (SortKeyList_Seq)this.get(index);
   }

   public boolean containsElement(SortKeyList_Seq elem) {
      return this.contains(elem);
   }

   public int indexOfElement(SortKeyList_Seq elem) {
      return this.indexOf(elem);
   }

   public int lastIndexOfElement(SortKeyList_Seq elem) {
      return this.lastIndexOf(elem);
   }

   public SortKeyList_Seq setElement(int index, SortKeyList_Seq elem) {
      return (SortKeyList_Seq)this.set(index, elem);
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
