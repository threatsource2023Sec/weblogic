package weblogic.security.providers.saml;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public class SAMLAttributeStatementInfo {
   private ArrayList attributes = null;

   public SAMLAttributeStatementInfo() {
   }

   public SAMLAttributeStatementInfo(Collection attrs) {
      if (attrs != null && attrs.size() > 0) {
         this.attributes = new ArrayList();
         this.addToAttributes(attrs);
      }

   }

   public Collection getAttributeInfo() {
      return this.attributes;
   }

   public void setAttributeInfo(Collection attrs) {
      if (attrs != null && attrs.size() > 0) {
         if (this.attributes == null) {
            this.attributes = new ArrayList();
         }

         this.addToAttributes(attrs);
      }

   }

   public void addAttributeInfo(SAMLAttributeInfo info) {
      if (info != null && info.getAttributeName() != null && !info.getAttributeName().equals("") && info.getAttributeNamespace() != null && !info.getAttributeNamespace().equals("")) {
         if (this.attributes == null) {
            this.attributes = new ArrayList();
         }

         this.attributes.add(info);
      }

   }

   private void addToAttributes(Collection attrs) {
      if (attrs != null && attrs.size() > 0) {
         if (this.attributes == null) {
            this.attributes = new ArrayList();
         }

         Iterator var2 = attrs.iterator();

         while(var2.hasNext()) {
            SAMLAttributeInfo info = (SAMLAttributeInfo)var2.next();
            this.addAttributeInfo(info);
         }
      }

   }
}
