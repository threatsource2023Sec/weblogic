package com.oracle.weblogic.diagnostics.expressions;

public abstract class AbstractTrackedValue implements TrackedValue {
   private String valuePath;
   private String instance;
   private String key;
   private Traceable parent;
   private Object value;
   private PathBuilder pathBuilder;

   protected AbstractTrackedValue(String instance, String pathToValue, Object v) {
      this((Traceable)null, instance, pathToValue, v);
   }

   protected AbstractTrackedValue(Traceable parent, String instance, String pathToValue, Object v) {
      this.instance = instance == null ? (parent != null ? parent.getKey() : null) : instance;
      this.parent = parent;
      this.valuePath = pathToValue;
      this.value = v;
   }

   public Traceable getTraceableParent() {
      return this.parent;
   }

   protected void setTraceableParent(Traceable parent) {
      this.parent = parent;
   }

   public Object getValue() {
      return this.value;
   }

   public String getValuePath() {
      if (this.valuePath == null) {
         this.valuePath = this.pathBuilder != null ? this.pathBuilder.getPath() : null;
      }

      return this.valuePath;
   }

   public boolean isPathSet() {
      return this.getValuePath() != null;
   }

   public void setValuePath(String path) {
      this.valuePath = path;
   }

   public void setInstanceName(String instance) {
      this.instance = instance;
   }

   public String getInstanceName() {
      return this.instance;
   }

   public String getKey() {
      if (this.key == null) {
         StringBuffer keybuf = new StringBuffer();
         String instanceName = this.getInstanceName();
         if (instanceName != null) {
            keybuf.append(instanceName);
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
      String currentKey = this.getKey();
      return currentKey.isEmpty() ? super.hashCode() : currentKey.hashCode();
   }

   public String toString() {
      return this.getValue() == null ? "" : this.getValue().toString();
   }

   public void setValuePath(PathBuilder pathBuilder) {
      this.pathBuilder = pathBuilder;
   }

   public boolean equals(Object obj) {
      if (obj instanceof TrackedValue) {
         TrackedValue rhs = (TrackedValue)obj;
         return this.value.equals(rhs.getValue());
      } else {
         return super.equals(obj);
      }
   }
}
