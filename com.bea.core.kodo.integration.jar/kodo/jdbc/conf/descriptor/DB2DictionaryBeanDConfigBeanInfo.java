package kodo.jdbc.conf.descriptor;

import java.beans.BeanDescriptor;
import java.beans.PropertyDescriptor;
import java.beans.SimpleBeanInfo;
import java.util.ArrayList;
import java.util.List;

public class DB2DictionaryBeanDConfigBeanInfo extends SimpleBeanInfo {
   BeanDescriptor bd = new BeanDescriptor(DB2DictionaryBeanDConfig.class);
   static PropertyDescriptor[] pds = null;

   public BeanDescriptor getBeanDescriptor() {
      return this.bd;
   }

   public PropertyDescriptor[] getPropertyDescriptors() {
      if (pds != null) {
         return pds;
      } else {
         List plist = new ArrayList();

         try {
            PropertyDescriptor pd = new PropertyDescriptor("BinaryTypeName", Class.forName("kodo.jdbc.conf.descriptor.DB2DictionaryBeanDConfig"), "getBinaryTypeName", "setBinaryTypeName");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", true);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("ClobTypeName", Class.forName("kodo.jdbc.conf.descriptor.DB2DictionaryBeanDConfig"), "getClobTypeName", "setClobTypeName");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", true);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("SupportsLockingWithDistinctClause", Class.forName("kodo.jdbc.conf.descriptor.DB2DictionaryBeanDConfig"), "getSupportsLockingWithDistinctClause", "setSupportsLockingWithDistinctClause");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", true);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("SupportsLockingWithSelectRange", Class.forName("kodo.jdbc.conf.descriptor.DB2DictionaryBeanDConfig"), "getSupportsLockingWithSelectRange", "setSupportsLockingWithSelectRange");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", true);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("MaxConstraintNameLength", Class.forName("kodo.jdbc.conf.descriptor.DB2DictionaryBeanDConfig"), "getMaxConstraintNameLength", "setMaxConstraintNameLength");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", true);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("StringLengthFunction", Class.forName("kodo.jdbc.conf.descriptor.DB2DictionaryBeanDConfig"), "getStringLengthFunction", "setStringLengthFunction");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", true);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("LongVarbinaryTypeName", Class.forName("kodo.jdbc.conf.descriptor.DB2DictionaryBeanDConfig"), "getLongVarbinaryTypeName", "setLongVarbinaryTypeName");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", true);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("TrimLeadingFunction", Class.forName("kodo.jdbc.conf.descriptor.DB2DictionaryBeanDConfig"), "getTrimLeadingFunction", "setTrimLeadingFunction");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", true);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("SupportsDefaultDeleteAction", Class.forName("kodo.jdbc.conf.descriptor.DB2DictionaryBeanDConfig"), "getSupportsDefaultDeleteAction", "setSupportsDefaultDeleteAction");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", true);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("NextSequenceQuery", Class.forName("kodo.jdbc.conf.descriptor.DB2DictionaryBeanDConfig"), "getNextSequenceQuery", "setNextSequenceQuery");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", true);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("LongVarcharTypeName", Class.forName("kodo.jdbc.conf.descriptor.DB2DictionaryBeanDConfig"), "getLongVarcharTypeName", "setLongVarcharTypeName");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", true);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("CrossJoinClause", Class.forName("kodo.jdbc.conf.descriptor.DB2DictionaryBeanDConfig"), "getCrossJoinClause", "setCrossJoinClause");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", true);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("SupportsAlterTableWithDropColumn", Class.forName("kodo.jdbc.conf.descriptor.DB2DictionaryBeanDConfig"), "getSupportsAlterTableWithDropColumn", "setSupportsAlterTableWithDropColumn");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", true);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("RequiresConditionForCrossJoin", Class.forName("kodo.jdbc.conf.descriptor.DB2DictionaryBeanDConfig"), "getRequiresConditionForCrossJoin", "setRequiresConditionForCrossJoin");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", true);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("SupportsLockingWithMultipleTables", Class.forName("kodo.jdbc.conf.descriptor.DB2DictionaryBeanDConfig"), "getSupportsLockingWithMultipleTables", "setSupportsLockingWithMultipleTables");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", true);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("MaxColumnNameLength", Class.forName("kodo.jdbc.conf.descriptor.DB2DictionaryBeanDConfig"), "getMaxColumnNameLength", "setMaxColumnNameLength");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", true);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("SmallintTypeName", Class.forName("kodo.jdbc.conf.descriptor.DB2DictionaryBeanDConfig"), "getSmallintTypeName", "setSmallintTypeName");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", true);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("BitTypeName", Class.forName("kodo.jdbc.conf.descriptor.DB2DictionaryBeanDConfig"), "getBitTypeName", "setBitTypeName");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", true);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("SupportsNullTableForGetColumns", Class.forName("kodo.jdbc.conf.descriptor.DB2DictionaryBeanDConfig"), "getSupportsNullTableForGetColumns", "setSupportsNullTableForGetColumns");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", true);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("ToUpperCaseFunction", Class.forName("kodo.jdbc.conf.descriptor.DB2DictionaryBeanDConfig"), "getToUpperCaseFunction", "setToUpperCaseFunction");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", true);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("SupportsSelectEndIndex", Class.forName("kodo.jdbc.conf.descriptor.DB2DictionaryBeanDConfig"), "getSupportsSelectEndIndex", "setSupportsSelectEndIndex");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", true);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("SupportsAutoAssign", Class.forName("kodo.jdbc.conf.descriptor.DB2DictionaryBeanDConfig"), "getSupportsAutoAssign", "setSupportsAutoAssign");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", true);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("ValidationSQL", Class.forName("kodo.jdbc.conf.descriptor.DB2DictionaryBeanDConfig"), "getValidationSQL", "setValidationSQL");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", true);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("AutoAssignClause", Class.forName("kodo.jdbc.conf.descriptor.DB2DictionaryBeanDConfig"), "getAutoAssignClause", "setAutoAssignClause");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", true);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("NumericTypeName", Class.forName("kodo.jdbc.conf.descriptor.DB2DictionaryBeanDConfig"), "getNumericTypeName", "setNumericTypeName");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", true);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("ForUpdateClause", Class.forName("kodo.jdbc.conf.descriptor.DB2DictionaryBeanDConfig"), "getForUpdateClause", "setForUpdateClause");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", true);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("SupportsLockingWithOrderClause", Class.forName("kodo.jdbc.conf.descriptor.DB2DictionaryBeanDConfig"), "getSupportsLockingWithOrderClause", "setSupportsLockingWithOrderClause");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", true);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("Platform", Class.forName("kodo.jdbc.conf.descriptor.DB2DictionaryBeanDConfig"), "getPlatform", "setPlatform");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", true);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("SupportsLockingWithOuterJoin", Class.forName("kodo.jdbc.conf.descriptor.DB2DictionaryBeanDConfig"), "getSupportsLockingWithOuterJoin", "setSupportsLockingWithOuterJoin");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", true);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("LastGeneratedKeyQuery", Class.forName("kodo.jdbc.conf.descriptor.DB2DictionaryBeanDConfig"), "getLastGeneratedKeyQuery", "setLastGeneratedKeyQuery");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", true);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("SupportsDeferredConstraints", Class.forName("kodo.jdbc.conf.descriptor.DB2DictionaryBeanDConfig"), "getSupportsDeferredConstraints", "setSupportsDeferredConstraints");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", true);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("RequiresAliasForSubselect", Class.forName("kodo.jdbc.conf.descriptor.DB2DictionaryBeanDConfig"), "getRequiresAliasForSubselect", "setRequiresAliasForSubselect");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", true);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("TrimTrailingFunction", Class.forName("kodo.jdbc.conf.descriptor.DB2DictionaryBeanDConfig"), "getTrimTrailingFunction", "setTrimTrailingFunction");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", true);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("SupportsLockingWithInnerJoin", Class.forName("kodo.jdbc.conf.descriptor.DB2DictionaryBeanDConfig"), "getSupportsLockingWithInnerJoin", "setSupportsLockingWithInnerJoin");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", true);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("MaxIndexNameLength", Class.forName("kodo.jdbc.conf.descriptor.DB2DictionaryBeanDConfig"), "getMaxIndexNameLength", "setMaxIndexNameLength");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", true);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("VarbinaryTypeName", Class.forName("kodo.jdbc.conf.descriptor.DB2DictionaryBeanDConfig"), "getVarbinaryTypeName", "setVarbinaryTypeName");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", true);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("TrimBothFunction", Class.forName("kodo.jdbc.conf.descriptor.DB2DictionaryBeanDConfig"), "getTrimBothFunction", "setTrimBothFunction");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", true);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("ToLowerCaseFunction", Class.forName("kodo.jdbc.conf.descriptor.DB2DictionaryBeanDConfig"), "getToLowerCaseFunction", "setToLowerCaseFunction");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", true);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("TinyintTypeName", Class.forName("kodo.jdbc.conf.descriptor.DB2DictionaryBeanDConfig"), "getTinyintTypeName", "setTinyintTypeName");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", true);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("RequiresAutoCommitForMetaData", Class.forName("kodo.jdbc.conf.descriptor.DB2DictionaryBeanDConfig"), "getRequiresAutoCommitForMetaData", "setRequiresAutoCommitForMetaData");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", true);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pds = (PropertyDescriptor[])((PropertyDescriptor[])plist.toArray(new PropertyDescriptor[0]));
            return pds;
         } catch (Throwable var4) {
            var4.printStackTrace();
            throw new AssertionError("Failed to create PropertyDescriptors for DB2DictionaryBeanDConfigBeanInfo");
         }
      }
   }
}