package com.bea.core.repackaged.springframework.core;

import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.Assert;
import com.bea.core.repackaged.springframework.util.StringUtils;
import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

public abstract class AttributeAccessorSupport implements AttributeAccessor, Serializable {
   private final Map attributes = new LinkedHashMap();

   public void setAttribute(String name, @Nullable Object value) {
      Assert.notNull(name, (String)"Name must not be null");
      if (value != null) {
         this.attributes.put(name, value);
      } else {
         this.removeAttribute(name);
      }

   }

   @Nullable
   public Object getAttribute(String name) {
      Assert.notNull(name, (String)"Name must not be null");
      return this.attributes.get(name);
   }

   @Nullable
   public Object removeAttribute(String name) {
      Assert.notNull(name, (String)"Name must not be null");
      return this.attributes.remove(name);
   }

   public boolean hasAttribute(String name) {
      Assert.notNull(name, (String)"Name must not be null");
      return this.attributes.containsKey(name);
   }

   public String[] attributeNames() {
      return StringUtils.toStringArray((Collection)this.attributes.keySet());
   }

   protected void copyAttributesFrom(AttributeAccessor source) {
      Assert.notNull(source, (String)"Source must not be null");
      String[] attributeNames = source.attributeNames();
      String[] var3 = attributeNames;
      int var4 = attributeNames.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         String attributeName = var3[var5];
         this.setAttribute(attributeName, source.getAttribute(attributeName));
      }

   }

   public boolean equals(Object other) {
      return this == other || other instanceof AttributeAccessorSupport && this.attributes.equals(((AttributeAccessorSupport)other).attributes);
   }

   public int hashCode() {
      return this.attributes.hashCode();
   }
}
