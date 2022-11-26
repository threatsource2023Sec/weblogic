package com.bea.staxb.types;

import java.util.regex.Pattern;
import javax.xml.namespace.QName;

public class IndigoGuid implements DotNetType {
   public static final QName QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "guid");
   private static final String patternvalue = "[\\da-fA-F]{8}-[\\da-fA-F]{4}-[\\da-fA-F]{4}-[\\da-fA-F]{4}-[\\da-fA-F]{12}";
   private static final Pattern pattern = Pattern.compile("[\\da-fA-F]{8}-[\\da-fA-F]{4}-[\\da-fA-F]{4}-[\\da-fA-F]{4}-[\\da-fA-F]{12}");
   private String value;

   public IndigoGuid(String value) {
      this.valueOf(value);
   }

   public void valueOf(String value) {
      if (!pattern.matcher(value).matches()) {
         throw new IllegalArgumentException("'" + value + "' doesn't match pattern " + "[\\da-fA-F]{8}-[\\da-fA-F]{4}-[\\da-fA-F]{4}-[\\da-fA-F]{4}-[\\da-fA-F]{12}");
      } else {
         this.value = value;
      }
   }

   public String getStringValue() {
      return this.value;
   }

   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (o != null && this.getClass() == o.getClass()) {
         IndigoGuid that = (IndigoGuid)o;
         return this.value.equals(that.value);
      } else {
         return false;
      }
   }

   public int hashCode() {
      return this.value.hashCode();
   }
}
