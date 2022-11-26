package com.octetstring.ldapv3;

import com.asn1c.core.ASN1Object;
import com.asn1c.core.Bool;
import com.asn1c.core.Int32;
import com.asn1c.core.Int8;
import com.asn1c.core.OctetString;
import java.io.PrintWriter;

public class SearchRequest implements ASN1Object {
   protected OctetString baseObject;
   protected Int8 scope;
   protected Int8 derefAliases;
   protected Int32 sizeLimit;
   protected Int32 timeLimit;
   protected Bool typesOnly;
   protected Filter filter;
   protected AttributeDescriptionList attributes;

   public SearchRequest() {
   }

   public SearchRequest(OctetString baseObject, Int8 scope, Int8 derefAliases, Int32 sizeLimit, Int32 timeLimit, Bool typesOnly, Filter filter, AttributeDescriptionList attributes) {
      if (baseObject == null) {
         throw new IllegalArgumentException();
      } else {
         this.baseObject = baseObject;
         if (scope == null) {
            throw new IllegalArgumentException();
         } else {
            this.scope = scope;
            if (derefAliases == null) {
               throw new IllegalArgumentException();
            } else {
               this.derefAliases = derefAliases;
               if (sizeLimit == null) {
                  throw new IllegalArgumentException();
               } else {
                  this.sizeLimit = sizeLimit;
                  if (timeLimit == null) {
                     throw new IllegalArgumentException();
                  } else {
                     this.timeLimit = timeLimit;
                     if (typesOnly == null) {
                        throw new IllegalArgumentException();
                     } else {
                        this.typesOnly = typesOnly;
                        if (filter == null) {
                           throw new IllegalArgumentException();
                        } else {
                           this.filter = filter;
                           if (attributes == null) {
                              throw new IllegalArgumentException();
                           } else {
                              this.attributes = attributes;
                           }
                        }
                     }
                  }
               }
            }
         }
      }
   }

   public SearchRequest(SearchRequest value) {
      this.baseObject = value.getBaseObject();
      this.scope = value.getScope();
      this.derefAliases = value.getDerefAliases();
      this.sizeLimit = value.getSizeLimit();
      this.timeLimit = value.getTimeLimit();
      this.typesOnly = value.getTypesOnly();
      this.filter = value.getFilter();
      this.attributes = value.getAttributes();
   }

   public OctetString getBaseObject() {
      return this.baseObject;
   }

   public void setBaseObject(OctetString val) {
      if (val == null) {
         throw new IllegalArgumentException();
      } else {
         this.baseObject = val;
      }
   }

   public Int8 getScope() {
      return this.scope;
   }

   public void setScope(Int8 val) {
      if (val == null) {
         throw new IllegalArgumentException();
      } else {
         this.scope = val;
      }
   }

   public Int8 getDerefAliases() {
      return this.derefAliases;
   }

   public void setDerefAliases(Int8 val) {
      if (val == null) {
         throw new IllegalArgumentException();
      } else {
         this.derefAliases = val;
      }
   }

   public Int32 getSizeLimit() {
      return this.sizeLimit;
   }

   public void setSizeLimit(Int32 val) {
      if (val == null) {
         throw new IllegalArgumentException();
      } else {
         this.sizeLimit = val;
      }
   }

   public Int32 getTimeLimit() {
      return this.timeLimit;
   }

   public void setTimeLimit(Int32 val) {
      if (val == null) {
         throw new IllegalArgumentException();
      } else {
         this.timeLimit = val;
      }
   }

   public Bool getTypesOnly() {
      return this.typesOnly;
   }

   public void setTypesOnly(Bool val) {
      if (val == null) {
         throw new IllegalArgumentException();
      } else {
         this.typesOnly = val;
      }
   }

   public Filter getFilter() {
      return this.filter;
   }

   public void setFilter(Filter val) {
      if (val == null) {
         throw new IllegalArgumentException();
      } else {
         this.filter = val;
      }
   }

   public AttributeDescriptionList getAttributes() {
      return this.attributes;
   }

   public void setAttributes(AttributeDescriptionList val) {
      if (val == null) {
         throw new IllegalArgumentException();
      } else {
         this.attributes = val;
      }
   }

   public String toString() {
      StringBuffer buf = new StringBuffer("{ ");
      buf.append("baseObject ").append(this.baseObject.toString());
      buf.append(", scope ").append(this.scope.toString());
      buf.append(", derefAliases ").append(this.derefAliases.toString());
      buf.append(", sizeLimit ").append(this.sizeLimit.toString());
      buf.append(", timeLimit ").append(this.timeLimit.toString());
      buf.append(", typesOnly ").append(this.typesOnly.toString());
      buf.append(", filter ").append(this.filter.toString());
      buf.append(", attributes ").append(this.attributes.toString());
      return buf.append(" }").toString();
   }

   public void print(PrintWriter out, String indent, String prefix, String suffix, int flags) {
      String newindent = indent + "    ";
      out.println(indent + prefix + "{");
      this.baseObject.print(out, newindent, "baseObject ", ",", flags);
      this.scope.print(out, newindent, "scope ", ",", flags);
      this.derefAliases.print(out, newindent, "derefAliases ", ",", flags);
      this.sizeLimit.print(out, newindent, "sizeLimit ", ",", flags);
      this.timeLimit.print(out, newindent, "timeLimit ", ",", flags);
      this.typesOnly.print(out, newindent, "typesOnly ", ",", flags);
      this.filter.print(out, newindent, "filter ", ",", flags);
      this.attributes.print(out, newindent, "attributes ", "", flags);
      out.println(indent + "}" + suffix);
   }
}
