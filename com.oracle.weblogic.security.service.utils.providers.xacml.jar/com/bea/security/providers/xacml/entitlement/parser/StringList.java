package com.bea.security.providers.xacml.entitlement.parser;

import java.util.ArrayList;
import java.util.List;

public class StringList {
   private List data;

   public StringList() {
      this((List)null);
   }

   public StringList(String data) {
      this.data = new ArrayList();
      this.data.add(data);
   }

   public StringList(List data) {
      this.data = (List)(data != null ? data : new ArrayList());
   }

   public List getData() {
      return this.data;
   }
}
