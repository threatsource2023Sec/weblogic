package com.octetstring.ldapv3;

import com.asn1c.core.ASN1Object;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

public class AttributeList extends ArrayList implements ASN1Object {
   public AttributeList() {
   }

   public AttributeList(Collection value) {
      super(value);
   }

   public AttributeList(AttributeList_Seq[] value) {
      super(Arrays.asList(value));
   }

   public void addElement(AttributeList_Seq item) {
      this.add(item);
   }

   public void addElement(int index, AttributeList_Seq item) {
      this.add(index, item);
   }

   public AttributeList_Seq getElement(int index) {
      return (AttributeList_Seq)this.get(index);
   }

   public boolean containsElement(AttributeList_Seq elem) {
      return this.contains(elem);
   }

   public int indexOfElement(AttributeList_Seq elem) {
      return this.indexOf(elem);
   }

   public int lastIndexOfElement(AttributeList_Seq elem) {
      return this.lastIndexOf(elem);
   }

   public AttributeList_Seq setElement(int index, AttributeList_Seq elem) {
      return (AttributeList_Seq)this.set(index, elem);
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
