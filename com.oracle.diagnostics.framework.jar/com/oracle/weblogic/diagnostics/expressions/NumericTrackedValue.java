package com.oracle.weblogic.diagnostics.expressions;

import java.util.Comparator;

public class NumericTrackedValue extends Number implements TrackedValue, Comparator {
   private static final long serialVersionUID = 1L;
   private String valuePath;
   private Number value;
   private String instance;
   private String key;
   private Traceable parent;
   private transient PathBuilder pathBuilder;

   public NumericTrackedValue(String instance, Number n) {
      this(instance, (String)null, n);
   }

   public NumericTrackedValue(String instance, String pathToValue, Number n) {
      this((Traceable)null, instance, pathToValue, n);
   }

   public NumericTrackedValue(Traceable parent, String instance, String pathToValue, Number n) {
      this.instance = instance == null ? (parent != null ? parent.getKey() : null) : instance;
      this.parent = parent;
      this.valuePath = pathToValue;
      this.value = n;
   }

   public Number getValue() {
      return this.value;
   }

   public String getValuePath() {
      if (this.valuePath == null) {
         this.valuePath = this.pathBuilder != null ? this.pathBuilder.getPath() : null;
      }

      return this.valuePath;
   }

   public void setValuePath(String path) {
      this.valuePath = path;
   }

   public boolean isPathSet() {
      return this.valuePath != null;
   }

   public String getInstanceName() {
      return this.instance;
   }

   public void setInstanceName(String instanceName) {
      this.instance = instanceName;
   }

   public void setValuePath(PathBuilder pathBuilder) {
      this.pathBuilder = pathBuilder;
   }

   public String getKey() {
      if (this.key == null) {
         StringBuffer keybuf = new StringBuffer();
         if (this.instance != null) {
            keybuf.append(this.instance);
         }

         String path = this.getValuePath();
         if (path != null) {
            keybuf.append("//").append(path);
         }

         this.key = keybuf.toString();
      }

      return this.key;
   }

   public int hashCode() {
      return this.value.hashCode();
   }

   public boolean equals(Object obj) {
      return this.value != null && obj instanceof Number ? this.value.equals(obj) : super.equals(obj);
   }

   public double doubleValue() {
      return this.value.doubleValue();
   }

   public float floatValue() {
      return this.value.floatValue();
   }

   public int intValue() {
      return this.value.intValue();
   }

   public long longValue() {
      return this.value.longValue();
   }

   public byte byteValue() {
      return this.value.byteValue();
   }

   public short shortValue() {
      return this.value.shortValue();
   }

   public String toString() {
      return this.value.toString();
   }

   public int compare(Number o1, Number o2) {
      return (int)(o1.doubleValue() - o2.doubleValue());
   }

   public Traceable getTraceableParent() {
      return this.parent;
   }

   public void setParent(Traceable parent) {
      this.parent = parent;
   }
}
