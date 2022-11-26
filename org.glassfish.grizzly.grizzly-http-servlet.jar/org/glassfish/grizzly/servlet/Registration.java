package org.glassfish.grizzly.servlet;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

public abstract class Registration {
   protected String name;
   protected String className;
   protected Map initParameters;
   protected final WebappContext ctx;

   protected Registration(WebappContext ctx, String name, String className) {
      this.ctx = ctx;
      this.name = name;
      this.className = className;
      this.initParameters = new LinkedHashMap(4, 1.0F);
   }

   public String getName() {
      return this.name;
   }

   public String getClassName() {
      return this.className;
   }

   public boolean setInitParameter(String name, String value) {
      if (this.ctx.deployed) {
         throw new IllegalStateException("WebappContext has already been deployed");
      } else if (name == null) {
         throw new IllegalArgumentException("'name' cannot be null");
      } else if (value == null) {
         throw new IllegalArgumentException("'value' cannot be null");
      } else if (!this.initParameters.containsKey(name)) {
         this.initParameters.put(name, value);
         return true;
      } else {
         return false;
      }
   }

   public String getInitParameter(String name) {
      return name == null ? null : (String)this.initParameters.get(name);
   }

   public Set setInitParameters(Map initParameters) {
      if (this.ctx.deployed) {
         throw new IllegalStateException("WebappContext has already been deployed");
      } else if (initParameters == null) {
         return Collections.emptySet();
      } else {
         Set conflicts = new LinkedHashSet(4, 1.0F);
         Iterator var3 = initParameters.entrySet().iterator();

         while(var3.hasNext()) {
            Map.Entry entry = (Map.Entry)var3.next();
            if (!this.setInitParameter((String)entry.getKey(), (String)entry.getValue())) {
               conflicts.add(entry.getKey());
            }
         }

         return conflicts;
      }
   }

   public Map getInitParameters() {
      return Collections.unmodifiableMap(this.initParameters);
   }

   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (!(o instanceof Registration)) {
         return false;
      } else {
         Registration that;
         label44: {
            that = (Registration)o;
            if (this.className != null) {
               if (this.className.equals(that.className)) {
                  break label44;
               }
            } else if (that.className == null) {
               break label44;
            }

            return false;
         }

         if (this.ctx != null) {
            if (!this.ctx.equals(that.ctx)) {
               return false;
            }
         } else if (that.ctx != null) {
            return false;
         }

         if (this.name != null) {
            if (!this.name.equals(that.name)) {
               return false;
            }
         } else if (that.name != null) {
            return false;
         }

         return true;
      }
   }

   public int hashCode() {
      int result = this.name != null ? this.name.hashCode() : 0;
      result = 31 * result + (this.className != null ? this.className.hashCode() : 0);
      result = 31 * result + (this.ctx != null ? this.ctx.hashCode() : 0);
      return result;
   }
}
