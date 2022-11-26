package com.octetstring.ldapv3;

import com.asn1c.core.ASN1Object;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

public class Attribute_vals extends ArrayList implements ASN1Object {
   public Attribute_vals() {
   }

   public Attribute_vals(Collection value) {
      super(value);
   }

   public Attribute_vals(AttributeValue[] value) {
      super(Arrays.asList(value));
   }

   public void addElement(AttributeValue item) {
      this.add(item);
   }

   public void addElement(int index, AttributeValue item) {
      this.add(index, item);
   }

   public AttributeValue getElement(int index) {
      return (AttributeValue)this.get(index);
   }

   public boolean containsElement(AttributeValue elem) {
      return this.contains(elem);
   }

   public int indexOfElement(AttributeValue elem) {
      return this.indexOf(elem);
   }

   public int lastIndexOfElement(AttributeValue elem) {
      return this.lastIndexOf(elem);
   }

   public AttributeValue setElement(int index, AttributeValue elem) {
      return (AttributeValue)this.set(index, elem);
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
