package com.bea.core.repackaged.springframework.beans;

import com.bea.core.repackaged.springframework.core.convert.TypeDescriptor;
import com.bea.core.repackaged.springframework.lang.Nullable;
import java.util.Map;

public interface PropertyAccessor {
   String NESTED_PROPERTY_SEPARATOR = ".";
   char NESTED_PROPERTY_SEPARATOR_CHAR = '.';
   String PROPERTY_KEY_PREFIX = "[";
   char PROPERTY_KEY_PREFIX_CHAR = '[';
   String PROPERTY_KEY_SUFFIX = "]";
   char PROPERTY_KEY_SUFFIX_CHAR = ']';

   boolean isReadableProperty(String var1);

   boolean isWritableProperty(String var1);

   @Nullable
   Class getPropertyType(String var1) throws BeansException;

   @Nullable
   TypeDescriptor getPropertyTypeDescriptor(String var1) throws BeansException;

   @Nullable
   Object getPropertyValue(String var1) throws BeansException;

   void setPropertyValue(String var1, @Nullable Object var2) throws BeansException;

   void setPropertyValue(PropertyValue var1) throws BeansException;

   void setPropertyValues(Map var1) throws BeansException;

   void setPropertyValues(PropertyValues var1) throws BeansException;

   void setPropertyValues(PropertyValues var1, boolean var2) throws BeansException;

   void setPropertyValues(PropertyValues var1, boolean var2, boolean var3) throws BeansException;
}
