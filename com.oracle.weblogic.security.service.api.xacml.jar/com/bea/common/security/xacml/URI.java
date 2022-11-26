package com.bea.common.security.xacml;

import java.io.Serializable;

public class URI implements Comparable, Serializable {
   private static final long serialVersionUID = 1L;
   private String uri_as_string = null;
   private java.net.URI uri = null;
   private static final String IDDNAME_SCHEME = "iddname:";
   private static final String URN_SCHEME = "urn:";
   private static final String STANDARD_XACML_TYPE = "http://www.w3.org";
   private static Boolean useURIOnly = Boolean.getBoolean("weblogic.security.xacml.useURIOnly");

   public URI(String str) throws java.net.URISyntaxException {
      if (str == null) {
         throw new NullPointerException("URI string value must not be null");
      } else if (useURIOnly) {
         this.uri = new java.net.URI(str);
      } else {
         if (str.startsWith("iddname:")) {
            this.uri_as_string = str;
         } else if (str.startsWith("urn:")) {
            this.uri_as_string = str;
         } else if (str.startsWith("http://www.w3.org")) {
            this.uri_as_string = str;
         } else {
            this.uri = new java.net.URI(str);
         }

      }
   }

   public int hashCode() {
      return this.uri == null ? this.uri_as_string.hashCode() : this.uri.hashCode();
   }

   public boolean equals(Object obj) {
      if (this == obj) {
         return true;
      } else if (obj == null) {
         return false;
      } else if (this.getClass() != obj.getClass()) {
         return false;
      } else {
         URI other = (URI)obj;
         return this.uri == null ? this.uri_as_string.equals(other.uri_as_string) : this.uri.equals(other.uri);
      }
   }

   public String toString() {
      return this.uri == null ? this.uri_as_string : this.uri.toString();
   }

   public int compareTo(URI value) {
      return this.uri == null ? this.uri_as_string.compareToIgnoreCase(value.uri_as_string) : this.uri.compareTo(value.uri);
   }
}
