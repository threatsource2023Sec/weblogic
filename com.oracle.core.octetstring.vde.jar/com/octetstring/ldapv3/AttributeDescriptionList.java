package com.octetstring.ldapv3;

import com.asn1c.core.ASN1Object;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

public class AttributeDescriptionList extends ArrayList implements ASN1Object {
   public AttributeDescriptionList() {
   }

   public AttributeDescriptionList(Collection value) {
      super(value);
   }

   public AttributeDescriptionList(AttributeDescription[] value) {
      super(Arrays.asList(value));
   }

   public void addElement(AttributeDescription item) {
      this.add(item);
   }

   public void addElement(int index, AttributeDescription item) {
      this.add(index, item);
   }

   public AttributeDescription getElement(int index) {
      return (AttributeDescription)this.get(index);
   }

   public boolean containsElement(AttributeDescription elem) {
      return this.contains(elem);
   }

   public int indexOfElement(AttributeDescription elem) {
      return this.indexOf(elem);
   }

   public int lastIndexOfElement(AttributeDescription elem) {
      return this.lastIndexOf(elem);
   }

   public AttributeDescription setElement(int index, AttributeDescription elem) {
      return (AttributeDescription)this.set(index, elem);
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
