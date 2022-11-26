package com.bea.staxb.buildtime.internal.joust;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class AnnotationImpl implements Annotation {
   private Map mNameToValue = new HashMap();
   private String mType;
   private List mKeyList = new ArrayList();

   AnnotationImpl(String type) {
      if (type == null) {
         throw new IllegalArgumentException("null type");
      } else {
         this.mType = type;
      }
   }

   String getType() {
      return this.mType;
   }

   Iterator getPropertyNames() {
      return this.mKeyList.iterator();
   }

   Object getValue(String name) {
      return this.mNameToValue.get(name);
   }

   String getValueDeclaration(String name) {
      Object o = this.getValue(name);
      if (o == null) {
         return null;
      } else if (o instanceof String) {
         return "\"" + o + "\"";
      } else {
         return o instanceof Character ? "'" + o + "'" : o.toString();
      }
   }

   public void setValue(String name, Annotation ann) {
      this.add(name, ann);
   }

   public void setValue(String name, boolean value) {
      this.add(name, value);
   }

   public void setValue(String name, String value) {
      this.add(name, value);
   }

   public void setValue(String name, byte value) {
      this.add(name, new Byte(value));
   }

   public void setValue(String name, int value) {
      this.add(name, new Integer(value));
   }

   public void setValue(String name, long value) {
      this.add(name, new Long(value));
   }

   public void setValue(String name, char value) {
      this.add(name, new Character(value));
   }

   private void add(String name, Object value) {
      if (name == null) {
         throw new IllegalArgumentException("null name");
      } else if (value == null) {
         throw new IllegalArgumentException("null value");
      } else {
         this.mKeyList.add(name);
         this.mNameToValue.put(name, value);
      }
   }
}
