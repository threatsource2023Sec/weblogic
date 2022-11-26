package weblogic.diagnostics.query;

public final class VariableInstanceImpl implements VariableInstance {
   private String instanceName;
   private String attributeName;
   private Object instanceValue;

   public VariableInstanceImpl(String var, String attrName, Object val) {
      this.instanceName = var;
      this.attributeName = attrName;
      this.instanceValue = val;
   }

   public String getInstanceName() {
      return this.instanceName;
   }

   public String getAttributeName() {
      return this.attributeName;
   }

   public Object getInstanceValue() {
      return this.instanceValue;
   }

   public boolean equals(Object o) {
      return o instanceof VariableInstance ? ((VariableInstance)o).getInstanceName().equals(this.instanceName) : false;
   }

   public int hashCode() {
      return this.instanceName.hashCode();
   }
}
