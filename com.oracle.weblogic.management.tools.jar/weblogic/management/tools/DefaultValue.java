package weblogic.management.tools;

public class DefaultValue {
   private String m_value;
   private boolean m_isPrimitive;

   public DefaultValue(String value, boolean isPrimitive) {
      this.m_value = value;
      this.m_isPrimitive = isPrimitive;
   }

   public String testForEquality(String otherValue) {
      return this.m_isPrimitive ? this.m_value + " == " + otherValue : this.m_value + ".equals(" + otherValue + ")";
   }

   public String toString() {
      return "[DefaultValue:" + this.m_value + "]";
   }
}
