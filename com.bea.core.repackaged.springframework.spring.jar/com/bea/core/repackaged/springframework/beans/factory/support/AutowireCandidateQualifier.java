package com.bea.core.repackaged.springframework.beans.factory.support;

import com.bea.core.repackaged.springframework.beans.BeanMetadataAttributeAccessor;
import com.bea.core.repackaged.springframework.util.Assert;

public class AutowireCandidateQualifier extends BeanMetadataAttributeAccessor {
   public static final String VALUE_KEY = "value";
   private final String typeName;

   public AutowireCandidateQualifier(Class type) {
      this(type.getName());
   }

   public AutowireCandidateQualifier(String typeName) {
      Assert.notNull(typeName, (String)"Type name must not be null");
      this.typeName = typeName;
   }

   public AutowireCandidateQualifier(Class type, Object value) {
      this(type.getName(), value);
   }

   public AutowireCandidateQualifier(String typeName, Object value) {
      Assert.notNull(typeName, (String)"Type name must not be null");
      this.typeName = typeName;
      this.setAttribute("value", value);
   }

   public String getTypeName() {
      return this.typeName;
   }
}
