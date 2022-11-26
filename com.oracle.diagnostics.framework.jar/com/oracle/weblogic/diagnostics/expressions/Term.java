package com.oracle.weblogic.diagnostics.expressions;

interface Term {
   boolean isArray();

   boolean isMap();

   boolean isList();

   boolean isTerminalNode();

   boolean isRoot();

   boolean isMethod();

   Object getValue();

   String toString();

   boolean isTraceable();

   boolean isTrackedValue();
}
