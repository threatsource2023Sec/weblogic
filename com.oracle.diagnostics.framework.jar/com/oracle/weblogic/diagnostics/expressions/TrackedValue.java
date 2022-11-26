package com.oracle.weblogic.diagnostics.expressions;

public interface TrackedValue extends Traceable {
   void setInstanceName(String var1);

   boolean isPathSet();

   String getValuePath();

   void setValuePath(String var1);

   void setValuePath(PathBuilder var1);

   Object getValue();
}
