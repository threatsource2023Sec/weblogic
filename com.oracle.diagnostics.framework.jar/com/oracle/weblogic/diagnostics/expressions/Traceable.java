package com.oracle.weblogic.diagnostics.expressions;

public interface Traceable {
   Traceable getTraceableParent();

   String getInstanceName();

   String getKey();
}
