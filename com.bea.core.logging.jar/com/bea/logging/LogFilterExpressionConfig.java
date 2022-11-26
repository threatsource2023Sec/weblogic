package com.bea.logging;

import java.io.Serializable;

public interface LogFilterExpressionConfig extends Serializable {
   String getName();

   void setName(String var1);

   String getLogFilterExpression();

   void setLogFilterExpression(String var1);
}
