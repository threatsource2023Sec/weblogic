package weblogic.diagnostics.instrumentation.engine.base;

import java.io.Serializable;

public interface ModifierExpression extends Serializable {
   boolean isMatch(int var1);
}
