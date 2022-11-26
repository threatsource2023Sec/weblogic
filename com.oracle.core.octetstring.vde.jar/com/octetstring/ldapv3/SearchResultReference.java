package com.octetstring.ldapv3;

import com.asn1c.core.ASN1Object;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

public class SearchResultReference extends ArrayList implements ASN1Object {
   public SearchResultReference() {
   }

   public SearchResultReference(Collection value) {
      super(value);
   }

   public SearchResultReference(LDAPURL[] value) {
      super(Arrays.asList(value));
   }

   public void addElement(LDAPURL item) {
      this.add(item);
   }

   public void addElement(int index, LDAPURL item) {
      this.add(index, item);
   }

   public LDAPURL getElement(int index) {
      return (LDAPURL)this.get(index);
   }

   public boolean containsElement(LDAPURL elem) {
      return this.contains(elem);
   }

   public int indexOfElement(LDAPURL elem) {
      return this.indexOf(elem);
   }

   public int lastIndexOfElement(LDAPURL elem) {
      return this.lastIndexOf(elem);
   }

   public LDAPURL setElement(int index, LDAPURL elem) {
      return (LDAPURL)this.set(index, elem);
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
