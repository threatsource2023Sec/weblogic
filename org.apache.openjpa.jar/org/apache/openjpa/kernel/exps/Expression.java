package org.apache.openjpa.kernel.exps;

import java.io.Serializable;

public interface Expression extends Serializable {
   void acceptVisit(ExpressionVisitor var1);
}
