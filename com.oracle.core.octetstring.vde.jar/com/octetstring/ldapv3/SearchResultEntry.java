package com.octetstring.ldapv3;

import com.asn1c.core.ASN1Object;
import com.asn1c.core.OctetString;
import java.io.PrintWriter;

public class SearchResultEntry implements ASN1Object {
   protected OctetString objectName;
   protected PartialAttributeList attributes;

   public SearchResultEntry() {
   }

   public SearchResultEntry(OctetString objectName, PartialAttributeList attributes) {
      if (objectName == null) {
         throw new IllegalArgumentException();
      } else {
         this.objectName = objectName;
         if (attributes == null) {
            throw new IllegalArgumentException();
         } else {
            this.attributes = attributes;
         }
      }
   }

   public SearchResultEntry(SearchResultEntry value) {
      this.objectName = value.getObjectName();
      this.attributes = value.getAttributes();
   }

   public OctetString getObjectName() {
      return this.objectName;
   }

   public void setObjectName(OctetString val) {
      if (val == null) {
         throw new IllegalArgumentException();
      } else {
         this.objectName = val;
      }
   }

   public PartialAttributeList getAttributes() {
      return this.attributes;
   }

   public void setAttributes(PartialAttributeList val) {
      if (val == null) {
         throw new IllegalArgumentException();
      } else {
         this.attributes = val;
      }
   }

   public String toString() {
      StringBuffer buf = new StringBuffer("{ ");
      buf.append("objectName ").append(this.objectName.toString());
      buf.append(", attributes ").append(this.attributes.toString());
      return buf.append(" }").toString();
   }

   public void print(PrintWriter out, String indent, String prefix, String suffix, int flags) {
      String newindent = indent + "    ";
      out.println(indent + prefix + "{");
      this.objectName.print(out, newindent, "objectName ", ",", flags);
      this.attributes.print(out, newindent, "attributes ", "", flags);
      out.println(indent + "}" + suffix);
   }
}
