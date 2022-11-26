package weblogic.descriptor.internal;

public class AbstractSchemaHelper2 implements SchemaHelper {
   public int getPropertyIndex(String propName) {
      return -1;
   }

   public SchemaHelper getSchemaHelper(int propIndex) {
      return this;
   }

   public String getElementName(int propIndex) {
      return null;
   }

   public boolean isArray(int propIndex) {
      return false;
   }

   public boolean isAttribute(int propIndex) {
      return false;
   }

   public String getAttributeName(int propIndex) {
      return null;
   }

   public boolean isBean(int propIndex) {
      return false;
   }

   public boolean isConfigurable(int propIndex) {
      return false;
   }

   public boolean isKey(int propIndex) {
      return false;
   }

   public boolean isKeyChoice(int propIndex) {
      return false;
   }

   public boolean isKeyComponent(int propIndex) {
      return false;
   }

   public boolean hasKey() {
      return false;
   }

   public String[] getKeyElementNames() {
      return new String[0];
   }

   public String getRootElementName() {
      return null;
   }

   public String toString() {
      String s = super.toString();
      return s.substring(s.lastIndexOf(".") + 1);
   }

   public boolean isMergeRulePrependDefined(int propIndex) {
      return false;
   }

   public boolean isMergeRuleIgnoreSourceDefined(int propIndex) {
      return false;
   }

   public boolean isMergeRuleIgnoreTargetDefined(int propIndex) {
      return false;
   }
}
