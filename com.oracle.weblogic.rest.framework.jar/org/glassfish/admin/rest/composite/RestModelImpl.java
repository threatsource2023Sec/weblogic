package org.glassfish.admin.rest.composite;

import java.util.Locale;
import java.util.Set;
import java.util.TreeSet;

public class RestModelImpl {
   private boolean allFieldsSet = false;
   private Set setFields = new TreeSet();

   public boolean isSet(String fieldName) {
      return this.allFieldsSet || this.setFields.contains(fieldName.toLowerCase(Locale.US));
   }

   public void allFieldsSet() {
      this.allFieldsSet = true;
   }

   public void fieldSet(String fieldName) {
      this.setFields.add(fieldName.toLowerCase(Locale.US));
   }
}
