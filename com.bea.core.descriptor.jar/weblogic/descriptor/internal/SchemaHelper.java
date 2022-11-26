package weblogic.descriptor.internal;

public interface SchemaHelper {
   int getPropertyIndex(String var1);

   SchemaHelper getSchemaHelper(int var1);

   String getElementName(int var1);

   boolean isArray(int var1);

   boolean isAttribute(int var1);

   String getAttributeName(int var1);

   boolean isBean(int var1);

   boolean isConfigurable(int var1);

   boolean isKey(int var1);

   boolean isKeyChoice(int var1);

   boolean isKeyComponent(int var1);

   boolean hasKey();

   String[] getKeyElementNames();

   String getRootElementName();

   boolean isMergeRulePrependDefined(int var1);

   boolean isMergeRuleIgnoreSourceDefined(int var1);

   boolean isMergeRuleIgnoreTargetDefined(int var1);
}
