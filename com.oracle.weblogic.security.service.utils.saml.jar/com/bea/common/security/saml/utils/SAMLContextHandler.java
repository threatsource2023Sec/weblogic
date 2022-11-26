package com.bea.common.security.saml.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.Vector;
import weblogic.security.service.ContextElement;
import weblogic.security.service.ContextHandler;

public final class SAMLContextHandler implements ContextHandler {
   private HashMap map = new HashMap();

   public SAMLContextHandler() {
      ArrayList principals = new ArrayList();
      ContextElement element = new ContextElement("com.bea.contextelement.saml.AttributePrincipals", principals);
      this.addElement(element);
   }

   public int size() {
      return this.map.size();
   }

   public boolean addElement(ContextElement e) {
      if (this.map.containsKey(e.getName())) {
         return false;
      } else {
         this.map.put(e.getName(), e);
         return true;
      }
   }

   public String[] getNames() {
      Set nameset = this.map.keySet();
      return nameset == null ? new String[0] : (String[])((String[])nameset.toArray(new String[0]));
   }

   public Object getValue(String name) {
      ContextElement e = (ContextElement)this.map.get(name);
      return e == null ? null : e.getValue();
   }

   public ContextElement[] getValues(String[] names) {
      Vector values = new Vector();
      if (names != null) {
         for(int loop = 0; loop < names.length; ++loop) {
            Object foo = null;
            foo = this.map.get(names[loop]);
            if (foo != null) {
               values.add(foo);
            }
         }
      }

      return (ContextElement[])((ContextElement[])values.toArray(new ContextElement[0]));
   }
}
