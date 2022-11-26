package weblogic.security.providers.saml;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public class SAMLAttributeInfo {
   private String name = null;
   private String namespace = null;
   private Collection values = null;

   public SAMLAttributeInfo() {
      this.name = null;
      this.namespace = null;
      this.values = null;
   }

   public SAMLAttributeInfo(String name, String namespace, Collection values) {
      if (name != null && namespace != null) {
         this.name = name;
         this.namespace = namespace;
         this.validateAndAddAttributeValues(values);
      }
   }

   public SAMLAttributeInfo(String name, String namespace, String value) {
      if (name != null && namespace != null) {
         this.name = name;
         this.namespace = namespace;
         this.values = new ArrayList();
         this.addAttributeValue(value);
      }
   }

   public String getAttributeName() {
      return this.name;
   }

   public String getAttributeNamespace() {
      return this.namespace;
   }

   public void setAttributeName(String name, String namespace) {
      if (name != null && namespace != null) {
         this.name = name;
         this.namespace = namespace;
      }
   }

   public void setAttributeValues(Collection values) {
      this.validateAndAddAttributeValues(values);
   }

   public void addAttributeValue(String value) {
      if (this.values == null) {
         this.values = new ArrayList();
      }

      this.values.add(value == null ? "" : value);
   }

   public Collection getAttributeValues() {
      return this.values;
   }

   private void validateAndAddAttributeValues(Collection attrValues) {
      if (this.values == null) {
         this.values = new ArrayList();
      }

      if (attrValues != null && !attrValues.isEmpty()) {
         Iterator var2 = attrValues.iterator();

         while(var2.hasNext()) {
            String s = (String)var2.next();
            this.values.add(s == null ? "" : s);
         }
      } else {
         this.values.add("");
      }

   }
}
