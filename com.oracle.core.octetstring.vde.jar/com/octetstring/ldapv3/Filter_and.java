package com.octetstring.ldapv3;

import com.asn1c.core.ASN1Object;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

public class Filter_and extends ArrayList implements ASN1Object {
   public Filter_and() {
   }

   public Filter_and(Collection value) {
      super(value);
   }

   public Filter_and(Filter[] value) {
      super(Arrays.asList(value));
   }

   public void addElement(Filter item) {
      this.add(item);
   }

   public void addElement(int index, Filter item) {
      this.add(index, item);
   }

   public Filter getElement(int index) {
      return (Filter)this.get(index);
   }

   public boolean containsElement(Filter elem) {
      return this.contains(elem);
   }

   public int indexOfElement(Filter elem) {
      return this.indexOf(elem);
   }

   public int lastIndexOfElement(Filter elem) {
      return this.lastIndexOf(elem);
   }

   public Filter setElement(int index, Filter elem) {
      return (Filter)this.set(index, elem);
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
