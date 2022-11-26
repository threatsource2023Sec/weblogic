package com.octetstring.ldapv3;

import com.asn1c.core.ASN1Object;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

public class SubstringFilter_substrings extends ArrayList implements ASN1Object {
   public SubstringFilter_substrings() {
   }

   public SubstringFilter_substrings(Collection value) {
      super(value);
   }

   public SubstringFilter_substrings(SubstringFilter_substrings_Seq[] value) {
      super(Arrays.asList(value));
   }

   public void addElement(SubstringFilter_substrings_Seq item) {
      this.add(item);
   }

   public void addElement(int index, SubstringFilter_substrings_Seq item) {
      this.add(index, item);
   }

   public SubstringFilter_substrings_Seq getElement(int index) {
      return (SubstringFilter_substrings_Seq)this.get(index);
   }

   public boolean containsElement(SubstringFilter_substrings_Seq elem) {
      return this.contains(elem);
   }

   public int indexOfElement(SubstringFilter_substrings_Seq elem) {
      return this.indexOf(elem);
   }

   public int lastIndexOfElement(SubstringFilter_substrings_Seq elem) {
      return this.lastIndexOf(elem);
   }

   public SubstringFilter_substrings_Seq setElement(int index, SubstringFilter_substrings_Seq elem) {
      return (SubstringFilter_substrings_Seq)this.set(index, elem);
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
