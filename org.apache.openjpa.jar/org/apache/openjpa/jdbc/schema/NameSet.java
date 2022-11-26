package org.apache.openjpa.jdbc.schema;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import org.apache.commons.lang.StringUtils;
import org.apache.openjpa.lib.util.Localizer;

public class NameSet implements Serializable {
   private static final Localizer _loc = Localizer.forPackage(NameSet.class);
   private Set _names = null;
   private Set _subNames = null;

   public boolean isNameTaken(String name) {
      if (name == null) {
         return true;
      } else {
         return this._names != null && this._names.contains(name.toUpperCase()) || this._subNames != null && this._subNames.contains(name.toUpperCase());
      }
   }

   protected void addName(String name, boolean validate) {
      if (StringUtils.isEmpty(name)) {
         if (validate) {
            throw new IllegalArgumentException(_loc.get("bad-name", (Object)name).getMessage());
         }
      } else {
         if (this._names == null) {
            this._names = new HashSet();
         }

         this._names.add(name.toUpperCase());
      }
   }

   protected void removeName(String name) {
      if (name != null && this._names != null) {
         this._names.remove(name.toUpperCase());
      }

   }

   protected void addSubName(String name) {
      if (this._subNames == null) {
         this._subNames = new HashSet();
      }

      this._subNames.add(name.toUpperCase());
   }

   protected void resetSubNames() {
      this._subNames = null;
   }
}
