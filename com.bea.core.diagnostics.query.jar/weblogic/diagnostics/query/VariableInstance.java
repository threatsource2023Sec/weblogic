package weblogic.diagnostics.query;

public interface VariableInstance {
   String getInstanceName();

   Object getInstanceValue();

   String getAttributeName();
}
