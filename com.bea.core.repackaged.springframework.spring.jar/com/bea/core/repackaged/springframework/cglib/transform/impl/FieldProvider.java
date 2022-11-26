package com.bea.core.repackaged.springframework.cglib.transform.impl;

public interface FieldProvider {
   String[] getFieldNames();

   Class[] getFieldTypes();

   void setField(int var1, Object var2);

   Object getField(int var1);

   void setField(String var1, Object var2);

   Object getField(String var1);
}
