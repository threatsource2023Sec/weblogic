package com.oracle.xmlns.weblogic.persistenceConfiguration;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlBoolean;
import com.bea.xml.XmlException;
import com.bea.xml.XmlInt;
import com.bea.xml.XmlOptions;
import com.bea.xml.XmlString;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.URL;
import javax.xml.stream.XMLStreamReader;
import org.w3c.dom.Node;
import weblogic.xml.stream.XMLInputStream;
import weblogic.xml.stream.XMLStreamException;

public interface BuiltInDbdictionaryType extends DbDictionaryType {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(BuiltInDbdictionaryType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_kodo_integration_binding_3_0_0_0").resolveHandle("builtindbdictionarytype821ftype");

   String getCharTypeName();

   XmlString xgetCharTypeName();

   boolean isNilCharTypeName();

   boolean isSetCharTypeName();

   void setCharTypeName(String var1);

   void xsetCharTypeName(XmlString var1);

   void setNilCharTypeName();

   void unsetCharTypeName();

   String getOuterJoinClause();

   XmlString xgetOuterJoinClause();

   boolean isNilOuterJoinClause();

   boolean isSetOuterJoinClause();

   void setOuterJoinClause(String var1);

   void xsetOuterJoinClause(XmlString var1);

   void setNilOuterJoinClause();

   void unsetOuterJoinClause();

   String getBinaryTypeName();

   XmlString xgetBinaryTypeName();

   boolean isNilBinaryTypeName();

   boolean isSetBinaryTypeName();

   void setBinaryTypeName(String var1);

   void xsetBinaryTypeName(XmlString var1);

   void setNilBinaryTypeName();

   void unsetBinaryTypeName();

   String getClobTypeName();

   XmlString xgetClobTypeName();

   boolean isNilClobTypeName();

   boolean isSetClobTypeName();

   void setClobTypeName(String var1);

   void xsetClobTypeName(XmlString var1);

   void setNilClobTypeName();

   void unsetClobTypeName();

   boolean getSupportsLockingWithDistinctClause();

   XmlBoolean xgetSupportsLockingWithDistinctClause();

   boolean isSetSupportsLockingWithDistinctClause();

   void setSupportsLockingWithDistinctClause(boolean var1);

   void xsetSupportsLockingWithDistinctClause(XmlBoolean var1);

   void unsetSupportsLockingWithDistinctClause();

   boolean getSimulateLocking();

   XmlBoolean xgetSimulateLocking();

   boolean isSetSimulateLocking();

   void setSimulateLocking(boolean var1);

   void xsetSimulateLocking(XmlBoolean var1);

   void unsetSimulateLocking();

   String getSystemTables();

   XmlString xgetSystemTables();

   boolean isNilSystemTables();

   boolean isSetSystemTables();

   void setSystemTables(String var1);

   void xsetSystemTables(XmlString var1);

   void setNilSystemTables();

   void unsetSystemTables();

   String getConcatenateFunction();

   XmlString xgetConcatenateFunction();

   boolean isNilConcatenateFunction();

   boolean isSetConcatenateFunction();

   void setConcatenateFunction(String var1);

   void xsetConcatenateFunction(XmlString var1);

   void setNilConcatenateFunction();

   void unsetConcatenateFunction();

   String getSubstringFunctionName();

   XmlString xgetSubstringFunctionName();

   boolean isNilSubstringFunctionName();

   boolean isSetSubstringFunctionName();

   void setSubstringFunctionName(String var1);

   void xsetSubstringFunctionName(XmlString var1);

   void setNilSubstringFunctionName();

   void unsetSubstringFunctionName();

   boolean getSupportsQueryTimeout();

   XmlBoolean xgetSupportsQueryTimeout();

   boolean isSetSupportsQueryTimeout();

   void setSupportsQueryTimeout(boolean var1);

   void xsetSupportsQueryTimeout(XmlBoolean var1);

   void unsetSupportsQueryTimeout();

   boolean getUseSetBytesForBlobs();

   XmlBoolean xgetUseSetBytesForBlobs();

   boolean isSetUseSetBytesForBlobs();

   void setUseSetBytesForBlobs(boolean var1);

   void xsetUseSetBytesForBlobs(XmlBoolean var1);

   void unsetUseSetBytesForBlobs();

   int getMaxConstraintNameLength();

   XmlInt xgetMaxConstraintNameLength();

   boolean isSetMaxConstraintNameLength();

   void setMaxConstraintNameLength(int var1);

   void xsetMaxConstraintNameLength(XmlInt var1);

   void unsetMaxConstraintNameLength();

   String getSearchStringEscape();

   XmlString xgetSearchStringEscape();

   boolean isNilSearchStringEscape();

   boolean isSetSearchStringEscape();

   void setSearchStringEscape(String var1);

   void xsetSearchStringEscape(XmlString var1);

   void setNilSearchStringEscape();

   void unsetSearchStringEscape();

   boolean getSupportsCascadeUpdateAction();

   XmlBoolean xgetSupportsCascadeUpdateAction();

   boolean isSetSupportsCascadeUpdateAction();

   void setSupportsCascadeUpdateAction(boolean var1);

   void xsetSupportsCascadeUpdateAction(XmlBoolean var1);

   void unsetSupportsCascadeUpdateAction();

   String getStringLengthFunction();

   XmlString xgetStringLengthFunction();

   boolean isNilStringLengthFunction();

   boolean isSetStringLengthFunction();

   void setStringLengthFunction(String var1);

   void xsetStringLengthFunction(XmlString var1);

   void setNilStringLengthFunction();

   void unsetStringLengthFunction();

   String getLongVarbinaryTypeName();

   XmlString xgetLongVarbinaryTypeName();

   boolean isNilLongVarbinaryTypeName();

   boolean isSetLongVarbinaryTypeName();

   void setLongVarbinaryTypeName(String var1);

   void xsetLongVarbinaryTypeName(XmlString var1);

   void setNilLongVarbinaryTypeName();

   void unsetLongVarbinaryTypeName();

   boolean getSupportsUniqueConstraints();

   XmlBoolean xgetSupportsUniqueConstraints();

   boolean isSetSupportsUniqueConstraints();

   void setSupportsUniqueConstraints(boolean var1);

   void xsetSupportsUniqueConstraints(XmlBoolean var1);

   void unsetSupportsUniqueConstraints();

   boolean getSupportsRestrictDeleteAction();

   XmlBoolean xgetSupportsRestrictDeleteAction();

   boolean isSetSupportsRestrictDeleteAction();

   void setSupportsRestrictDeleteAction(boolean var1);

   void xsetSupportsRestrictDeleteAction(XmlBoolean var1);

   void unsetSupportsRestrictDeleteAction();

   String getTrimLeadingFunction();

   XmlString xgetTrimLeadingFunction();

   boolean isNilTrimLeadingFunction();

   boolean isSetTrimLeadingFunction();

   void setTrimLeadingFunction(String var1);

   void xsetTrimLeadingFunction(XmlString var1);

   void setNilTrimLeadingFunction();

   void unsetTrimLeadingFunction();

   boolean getSupportsDefaultDeleteAction();

   XmlBoolean xgetSupportsDefaultDeleteAction();

   boolean isSetSupportsDefaultDeleteAction();

   void setSupportsDefaultDeleteAction(boolean var1);

   void xsetSupportsDefaultDeleteAction(XmlBoolean var1);

   void unsetSupportsDefaultDeleteAction();

   String getNextSequenceQuery();

   XmlString xgetNextSequenceQuery();

   boolean isNilNextSequenceQuery();

   boolean isSetNextSequenceQuery();

   void setNextSequenceQuery(String var1);

   void xsetNextSequenceQuery(XmlString var1);

   void setNilNextSequenceQuery();

   void unsetNextSequenceQuery();

   String getLongVarcharTypeName();

   XmlString xgetLongVarcharTypeName();

   boolean isNilLongVarcharTypeName();

   boolean isSetLongVarcharTypeName();

   void setLongVarcharTypeName(String var1);

   void xsetLongVarcharTypeName(XmlString var1);

   void setNilLongVarcharTypeName();

   void unsetLongVarcharTypeName();

   String getCrossJoinClause();

   XmlString xgetCrossJoinClause();

   boolean isNilCrossJoinClause();

   boolean isSetCrossJoinClause();

   void setCrossJoinClause(String var1);

   void xsetCrossJoinClause(XmlString var1);

   void setNilCrossJoinClause();

   void unsetCrossJoinClause();

   int getMaxEmbeddedClobSize();

   XmlInt xgetMaxEmbeddedClobSize();

   boolean isSetMaxEmbeddedClobSize();

   void setMaxEmbeddedClobSize(int var1);

   void xsetMaxEmbeddedClobSize(XmlInt var1);

   void unsetMaxEmbeddedClobSize();

   String getDateTypeName();

   XmlString xgetDateTypeName();

   boolean isNilDateTypeName();

   boolean isSetDateTypeName();

   void setDateTypeName(String var1);

   void xsetDateTypeName(XmlString var1);

   void setNilDateTypeName();

   void unsetDateTypeName();

   boolean getSupportsSchemaForGetTables();

   XmlBoolean xgetSupportsSchemaForGetTables();

   boolean isSetSupportsSchemaForGetTables();

   void setSupportsSchemaForGetTables(boolean var1);

   void xsetSupportsSchemaForGetTables(XmlBoolean var1);

   void unsetSupportsSchemaForGetTables();

   boolean getSupportsAlterTableWithDropColumn();

   XmlBoolean xgetSupportsAlterTableWithDropColumn();

   boolean isSetSupportsAlterTableWithDropColumn();

   void setSupportsAlterTableWithDropColumn(boolean var1);

   void xsetSupportsAlterTableWithDropColumn(XmlBoolean var1);

   void unsetSupportsAlterTableWithDropColumn();

   String getCurrentTimeFunction();

   XmlString xgetCurrentTimeFunction();

   boolean isNilCurrentTimeFunction();

   boolean isSetCurrentTimeFunction();

   void setCurrentTimeFunction(String var1);

   void xsetCurrentTimeFunction(XmlString var1);

   void setNilCurrentTimeFunction();

   void unsetCurrentTimeFunction();

   boolean getRequiresConditionForCrossJoin();

   XmlBoolean xgetRequiresConditionForCrossJoin();

   boolean isSetRequiresConditionForCrossJoin();

   void setRequiresConditionForCrossJoin(boolean var1);

   void xsetRequiresConditionForCrossJoin(XmlBoolean var1);

   void unsetRequiresConditionForCrossJoin();

   String getRefTypeName();

   XmlString xgetRefTypeName();

   boolean isNilRefTypeName();

   boolean isSetRefTypeName();

   void setRefTypeName(String var1);

   void xsetRefTypeName(XmlString var1);

   void setNilRefTypeName();

   void unsetRefTypeName();

   String getConcatenateDelimiter();

   XmlString xgetConcatenateDelimiter();

   boolean isNilConcatenateDelimiter();

   boolean isSetConcatenateDelimiter();

   void setConcatenateDelimiter(String var1);

   void xsetConcatenateDelimiter(XmlString var1);

   void setNilConcatenateDelimiter();

   void unsetConcatenateDelimiter();

   String getCatalogSeparator();

   XmlString xgetCatalogSeparator();

   boolean isNilCatalogSeparator();

   boolean isSetCatalogSeparator();

   void setCatalogSeparator(String var1);

   void xsetCatalogSeparator(XmlString var1);

   void setNilCatalogSeparator();

   void unsetCatalogSeparator();

   boolean getSupportsModOperator();

   XmlBoolean xgetSupportsModOperator();

   boolean isSetSupportsModOperator();

   void setSupportsModOperator(boolean var1);

   void xsetSupportsModOperator(XmlBoolean var1);

   void unsetSupportsModOperator();

   String getSchemaCase();

   XmlString xgetSchemaCase();

   boolean isNilSchemaCase();

   boolean isSetSchemaCase();

   void setSchemaCase(String var1);

   void xsetSchemaCase(XmlString var1);

   void setNilSchemaCase();

   void unsetSchemaCase();

   String getJavaObjectTypeName();

   XmlString xgetJavaObjectTypeName();

   boolean isNilJavaObjectTypeName();

   boolean isSetJavaObjectTypeName();

   void setJavaObjectTypeName(String var1);

   void xsetJavaObjectTypeName(XmlString var1);

   void setNilJavaObjectTypeName();

   void unsetJavaObjectTypeName();

   String getDriverVendor();

   XmlString xgetDriverVendor();

   boolean isNilDriverVendor();

   boolean isSetDriverVendor();

   void setDriverVendor(String var1);

   void xsetDriverVendor(XmlString var1);

   void setNilDriverVendor();

   void unsetDriverVendor();

   boolean getSupportsLockingWithMultipleTables();

   XmlBoolean xgetSupportsLockingWithMultipleTables();

   boolean isSetSupportsLockingWithMultipleTables();

   void setSupportsLockingWithMultipleTables(boolean var1);

   void xsetSupportsLockingWithMultipleTables(XmlBoolean var1);

   void unsetSupportsLockingWithMultipleTables();

   int getMaxColumnNameLength();

   XmlInt xgetMaxColumnNameLength();

   boolean isSetMaxColumnNameLength();

   void setMaxColumnNameLength(int var1);

   void xsetMaxColumnNameLength(XmlInt var1);

   void unsetMaxColumnNameLength();

   String getDoubleTypeName();

   XmlString xgetDoubleTypeName();

   boolean isNilDoubleTypeName();

   boolean isSetDoubleTypeName();

   void setDoubleTypeName(String var1);

   void xsetDoubleTypeName(XmlString var1);

   void setNilDoubleTypeName();

   void unsetDoubleTypeName();

   boolean getUseGetStringForClobs();

   XmlBoolean xgetUseGetStringForClobs();

   boolean isSetUseGetStringForClobs();

   void setUseGetStringForClobs(boolean var1);

   void xsetUseGetStringForClobs(XmlBoolean var1);

   void unsetUseGetStringForClobs();

   String getDecimalTypeName();

   XmlString xgetDecimalTypeName();

   boolean isNilDecimalTypeName();

   boolean isSetDecimalTypeName();

   void setDecimalTypeName(String var1);

   void xsetDecimalTypeName(XmlString var1);

   void setNilDecimalTypeName();

   void unsetDecimalTypeName();

   String getSmallintTypeName();

   XmlString xgetSmallintTypeName();

   boolean isNilSmallintTypeName();

   boolean isSetSmallintTypeName();

   void setSmallintTypeName(String var1);

   void xsetSmallintTypeName(XmlString var1);

   void setNilSmallintTypeName();

   void unsetSmallintTypeName();

   int getDatePrecision();

   XmlInt xgetDatePrecision();

   boolean isSetDatePrecision();

   void setDatePrecision(int var1);

   void xsetDatePrecision(XmlInt var1);

   void unsetDatePrecision();

   boolean getSupportsAlterTableWithAddColumn();

   XmlBoolean xgetSupportsAlterTableWithAddColumn();

   boolean isSetSupportsAlterTableWithAddColumn();

   void setSupportsAlterTableWithAddColumn(boolean var1);

   void xsetSupportsAlterTableWithAddColumn(XmlBoolean var1);

   void unsetSupportsAlterTableWithAddColumn();

   String getBitTypeName();

   XmlString xgetBitTypeName();

   boolean isNilBitTypeName();

   boolean isSetBitTypeName();

   void setBitTypeName(String var1);

   void xsetBitTypeName(XmlString var1);

   void setNilBitTypeName();

   void unsetBitTypeName();

   boolean getSupportsNullTableForGetColumns();

   XmlBoolean xgetSupportsNullTableForGetColumns();

   boolean isSetSupportsNullTableForGetColumns();

   void setSupportsNullTableForGetColumns(boolean var1);

   void xsetSupportsNullTableForGetColumns(XmlBoolean var1);

   void unsetSupportsNullTableForGetColumns();

   String getToUpperCaseFunction();

   XmlString xgetToUpperCaseFunction();

   boolean isNilToUpperCaseFunction();

   boolean isSetToUpperCaseFunction();

   void setToUpperCaseFunction(String var1);

   void xsetToUpperCaseFunction(XmlString var1);

   void setNilToUpperCaseFunction();

   void unsetToUpperCaseFunction();

   boolean getSupportsSelectEndIndex();

   XmlBoolean xgetSupportsSelectEndIndex();

   boolean isSetSupportsSelectEndIndex();

   void setSupportsSelectEndIndex(boolean var1);

   void xsetSupportsSelectEndIndex(XmlBoolean var1);

   void unsetSupportsSelectEndIndex();

   boolean getSupportsAutoAssign();

   XmlBoolean xgetSupportsAutoAssign();

   boolean isSetSupportsAutoAssign();

   void setSupportsAutoAssign(boolean var1);

   void xsetSupportsAutoAssign(XmlBoolean var1);

   void unsetSupportsAutoAssign();

   boolean getStoreLargeNumbersAsStrings();

   XmlBoolean xgetStoreLargeNumbersAsStrings();

   boolean isSetStoreLargeNumbersAsStrings();

   void setStoreLargeNumbersAsStrings(boolean var1);

   void xsetStoreLargeNumbersAsStrings(XmlBoolean var1);

   void unsetStoreLargeNumbersAsStrings();

   String getConstraintNameMode();

   XmlString xgetConstraintNameMode();

   boolean isNilConstraintNameMode();

   boolean isSetConstraintNameMode();

   void setConstraintNameMode(String var1);

   void xsetConstraintNameMode(XmlString var1);

   void setNilConstraintNameMode();

   void unsetConstraintNameMode();

   boolean getAllowsAliasInBulkClause();

   XmlBoolean xgetAllowsAliasInBulkClause();

   boolean isSetAllowsAliasInBulkClause();

   void setAllowsAliasInBulkClause(boolean var1);

   void xsetAllowsAliasInBulkClause(XmlBoolean var1);

   void unsetAllowsAliasInBulkClause();

   boolean getSupportsSelectForUpdate();

   XmlBoolean xgetSupportsSelectForUpdate();

   boolean isSetSupportsSelectForUpdate();

   void setSupportsSelectForUpdate(boolean var1);

   void xsetSupportsSelectForUpdate(XmlBoolean var1);

   void unsetSupportsSelectForUpdate();

   String getDistinctCountColumnSeparator();

   XmlString xgetDistinctCountColumnSeparator();

   boolean isNilDistinctCountColumnSeparator();

   boolean isSetDistinctCountColumnSeparator();

   void setDistinctCountColumnSeparator(String var1);

   void xsetDistinctCountColumnSeparator(XmlString var1);

   void setNilDistinctCountColumnSeparator();

   void unsetDistinctCountColumnSeparator();

   boolean getSupportsSubselect();

   XmlBoolean xgetSupportsSubselect();

   boolean isSetSupportsSubselect();

   void setSupportsSubselect(boolean var1);

   void xsetSupportsSubselect(XmlBoolean var1);

   void unsetSupportsSubselect();

   String getTimeTypeName();

   XmlString xgetTimeTypeName();

   boolean isNilTimeTypeName();

   boolean isSetTimeTypeName();

   void setTimeTypeName(String var1);

   void xsetTimeTypeName(XmlString var1);

   void setNilTimeTypeName();

   void unsetTimeTypeName();

   String getAutoAssignTypeName();

   XmlString xgetAutoAssignTypeName();

   boolean isNilAutoAssignTypeName();

   boolean isSetAutoAssignTypeName();

   void setAutoAssignTypeName(String var1);

   void xsetAutoAssignTypeName(XmlString var1);

   void setNilAutoAssignTypeName();

   void unsetAutoAssignTypeName();

   boolean getUseGetObjectForBlobs();

   XmlBoolean xgetUseGetObjectForBlobs();

   boolean isSetUseGetObjectForBlobs();

   void setUseGetObjectForBlobs(boolean var1);

   void xsetUseGetObjectForBlobs(XmlBoolean var1);

   void unsetUseGetObjectForBlobs();

   int getMaxAutoAssignNameLength();

   XmlInt xgetMaxAutoAssignNameLength();

   boolean isSetMaxAutoAssignNameLength();

   void setMaxAutoAssignNameLength(int var1);

   void xsetMaxAutoAssignNameLength(XmlInt var1);

   void unsetMaxAutoAssignNameLength();

   String getValidationSql();

   XmlString xgetValidationSql();

   boolean isNilValidationSql();

   boolean isSetValidationSql();

   void setValidationSql(String var1);

   void xsetValidationSql(XmlString var1);

   void setNilValidationSql();

   void unsetValidationSql();

   String getStructTypeName();

   XmlString xgetStructTypeName();

   boolean isNilStructTypeName();

   boolean isSetStructTypeName();

   void setStructTypeName(String var1);

   void xsetStructTypeName(XmlString var1);

   void setNilStructTypeName();

   void unsetStructTypeName();

   String getVarcharTypeName();

   XmlString xgetVarcharTypeName();

   boolean isNilVarcharTypeName();

   boolean isSetVarcharTypeName();

   void setVarcharTypeName(String var1);

   void xsetVarcharTypeName(XmlString var1);

   void setNilVarcharTypeName();

   void unsetVarcharTypeName();

   int getRangePosition();

   XmlInt xgetRangePosition();

   boolean isSetRangePosition();

   void setRangePosition(int var1);

   void xsetRangePosition(XmlInt var1);

   void unsetRangePosition();

   boolean getSupportsRestrictUpdateAction();

   XmlBoolean xgetSupportsRestrictUpdateAction();

   boolean isSetSupportsRestrictUpdateAction();

   void setSupportsRestrictUpdateAction(boolean var1);

   void xsetSupportsRestrictUpdateAction(XmlBoolean var1);

   void unsetSupportsRestrictUpdateAction();

   String getAutoAssignClause();

   XmlString xgetAutoAssignClause();

   boolean isNilAutoAssignClause();

   boolean isSetAutoAssignClause();

   void setAutoAssignClause(String var1);

   void xsetAutoAssignClause(XmlString var1);

   void setNilAutoAssignClause();

   void unsetAutoAssignClause();

   boolean getSupportsMultipleNontransactionalResultSets();

   XmlBoolean xgetSupportsMultipleNontransactionalResultSets();

   boolean isSetSupportsMultipleNontransactionalResultSets();

   void setSupportsMultipleNontransactionalResultSets(boolean var1);

   void xsetSupportsMultipleNontransactionalResultSets(XmlBoolean var1);

   void unsetSupportsMultipleNontransactionalResultSets();

   String getBitLengthFunction();

   XmlString xgetBitLengthFunction();

   boolean isNilBitLengthFunction();

   boolean isSetBitLengthFunction();

   void setBitLengthFunction(String var1);

   void xsetBitLengthFunction(XmlString var1);

   void setNilBitLengthFunction();

   void unsetBitLengthFunction();

   boolean getCreatePrimaryKeys();

   XmlBoolean xgetCreatePrimaryKeys();

   boolean isSetCreatePrimaryKeys();

   void setCreatePrimaryKeys(boolean var1);

   void xsetCreatePrimaryKeys(XmlBoolean var1);

   void unsetCreatePrimaryKeys();

   String getNullTypeName();

   XmlString xgetNullTypeName();

   boolean isNilNullTypeName();

   boolean isSetNullTypeName();

   void setNullTypeName(String var1);

   void xsetNullTypeName(XmlString var1);

   void setNilNullTypeName();

   void unsetNullTypeName();

   String getFloatTypeName();

   XmlString xgetFloatTypeName();

   boolean isNilFloatTypeName();

   boolean isSetFloatTypeName();

   void setFloatTypeName(String var1);

   void xsetFloatTypeName(XmlString var1);

   void setNilFloatTypeName();

   void unsetFloatTypeName();

   boolean getUseGetBytesForBlobs();

   XmlBoolean xgetUseGetBytesForBlobs();

   boolean isSetUseGetBytesForBlobs();

   void setUseGetBytesForBlobs(boolean var1);

   void xsetUseGetBytesForBlobs(XmlBoolean var1);

   void unsetUseGetBytesForBlobs();

   String getTableTypes();

   XmlString xgetTableTypes();

   boolean isNilTableTypes();

   boolean isSetTableTypes();

   void setTableTypes(String var1);

   void xsetTableTypes(XmlString var1);

   void setNilTableTypes();

   void unsetTableTypes();

   String getNumericTypeName();

   XmlString xgetNumericTypeName();

   boolean isNilNumericTypeName();

   boolean isSetNumericTypeName();

   void setNumericTypeName(String var1);

   void xsetNumericTypeName(XmlString var1);

   void setNilNumericTypeName();

   void unsetNumericTypeName();

   String getTableForUpdateClause();

   XmlString xgetTableForUpdateClause();

   boolean isNilTableForUpdateClause();

   boolean isSetTableForUpdateClause();

   void setTableForUpdateClause(String var1);

   void xsetTableForUpdateClause(XmlString var1);

   void setNilTableForUpdateClause();

   void unsetTableForUpdateClause();

   String getIntegerTypeName();

   XmlString xgetIntegerTypeName();

   boolean isNilIntegerTypeName();

   boolean isSetIntegerTypeName();

   void setIntegerTypeName(String var1);

   void xsetIntegerTypeName(XmlString var1);

   void setNilIntegerTypeName();

   void unsetIntegerTypeName();

   String getBlobTypeName();

   XmlString xgetBlobTypeName();

   boolean isNilBlobTypeName();

   boolean isSetBlobTypeName();

   void setBlobTypeName(String var1);

   void xsetBlobTypeName(XmlString var1);

   void setNilBlobTypeName();

   void unsetBlobTypeName();

   String getForUpdateClause();

   XmlString xgetForUpdateClause();

   boolean isNilForUpdateClause();

   boolean isSetForUpdateClause();

   void setForUpdateClause(String var1);

   void xsetForUpdateClause(XmlString var1);

   void setNilForUpdateClause();

   void unsetForUpdateClause();

   String getBooleanTypeName();

   XmlString xgetBooleanTypeName();

   boolean isNilBooleanTypeName();

   boolean isSetBooleanTypeName();

   void setBooleanTypeName(String var1);

   void xsetBooleanTypeName(XmlString var1);

   void setNilBooleanTypeName();

   void unsetBooleanTypeName();

   boolean getUseGetBestRowIdentifierForPrimaryKeys();

   XmlBoolean xgetUseGetBestRowIdentifierForPrimaryKeys();

   boolean isSetUseGetBestRowIdentifierForPrimaryKeys();

   void setUseGetBestRowIdentifierForPrimaryKeys(boolean var1);

   void xsetUseGetBestRowIdentifierForPrimaryKeys(XmlBoolean var1);

   void unsetUseGetBestRowIdentifierForPrimaryKeys();

   boolean getSupportsForeignKeys();

   XmlBoolean xgetSupportsForeignKeys();

   boolean isSetSupportsForeignKeys();

   void setSupportsForeignKeys(boolean var1);

   void xsetSupportsForeignKeys(XmlBoolean var1);

   void unsetSupportsForeignKeys();

   String getDropTableSql();

   XmlString xgetDropTableSql();

   boolean isNilDropTableSql();

   boolean isSetDropTableSql();

   void setDropTableSql(String var1);

   void xsetDropTableSql(XmlString var1);

   void setNilDropTableSql();

   void unsetDropTableSql();

   boolean getUseSetStringForClobs();

   XmlBoolean xgetUseSetStringForClobs();

   boolean isSetUseSetStringForClobs();

   void setUseSetStringForClobs(boolean var1);

   void xsetUseSetStringForClobs(XmlBoolean var1);

   void unsetUseSetStringForClobs();

   boolean getSupportsLockingWithOrderClause();

   XmlBoolean xgetSupportsLockingWithOrderClause();

   boolean isSetSupportsLockingWithOrderClause();

   void setSupportsLockingWithOrderClause(boolean var1);

   void xsetSupportsLockingWithOrderClause(XmlBoolean var1);

   void unsetSupportsLockingWithOrderClause();

   String getPlatform();

   XmlString xgetPlatform();

   boolean isNilPlatform();

   boolean isSetPlatform();

   void setPlatform(String var1);

   void xsetPlatform(XmlString var1);

   void setNilPlatform();

   void unsetPlatform();

   String getFixedSizeTypeNames();

   XmlString xgetFixedSizeTypeNames();

   boolean isNilFixedSizeTypeNames();

   boolean isSetFixedSizeTypeNames();

   void setFixedSizeTypeNames(String var1);

   void xsetFixedSizeTypeNames(XmlString var1);

   void setNilFixedSizeTypeNames();

   void unsetFixedSizeTypeNames();

   boolean getStoreCharsAsNumbers();

   XmlBoolean xgetStoreCharsAsNumbers();

   boolean isSetStoreCharsAsNumbers();

   void setStoreCharsAsNumbers(boolean var1);

   void xsetStoreCharsAsNumbers(XmlBoolean var1);

   void unsetStoreCharsAsNumbers();

   int getMaxIndexesPerTable();

   XmlInt xgetMaxIndexesPerTable();

   boolean isSetMaxIndexesPerTable();

   void setMaxIndexesPerTable(int var1);

   void xsetMaxIndexesPerTable(XmlInt var1);

   void unsetMaxIndexesPerTable();

   boolean getRequiresCastForComparisons();

   XmlBoolean xgetRequiresCastForComparisons();

   boolean isSetRequiresCastForComparisons();

   void setRequiresCastForComparisons(boolean var1);

   void xsetRequiresCastForComparisons(XmlBoolean var1);

   void unsetRequiresCastForComparisons();

   boolean getSupportsHaving();

   XmlBoolean xgetSupportsHaving();

   boolean isSetSupportsHaving();

   void setSupportsHaving(boolean var1);

   void xsetSupportsHaving(XmlBoolean var1);

   void unsetSupportsHaving();

   boolean getSupportsLockingWithOuterJoin();

   XmlBoolean xgetSupportsLockingWithOuterJoin();

   boolean isSetSupportsLockingWithOuterJoin();

   void setSupportsLockingWithOuterJoin(boolean var1);

   void xsetSupportsLockingWithOuterJoin(XmlBoolean var1);

   void unsetSupportsLockingWithOuterJoin();

   boolean getSupportsCorrelatedSubselect();

   XmlBoolean xgetSupportsCorrelatedSubselect();

   boolean isSetSupportsCorrelatedSubselect();

   void setSupportsCorrelatedSubselect(boolean var1);

   void xsetSupportsCorrelatedSubselect(XmlBoolean var1);

   void unsetSupportsCorrelatedSubselect();

   boolean getSupportsNullTableForGetImportedKeys();

   XmlBoolean xgetSupportsNullTableForGetImportedKeys();

   boolean isSetSupportsNullTableForGetImportedKeys();

   void setSupportsNullTableForGetImportedKeys(boolean var1);

   void xsetSupportsNullTableForGetImportedKeys(XmlBoolean var1);

   void unsetSupportsNullTableForGetImportedKeys();

   String getBigintTypeName();

   XmlString xgetBigintTypeName();

   boolean isNilBigintTypeName();

   boolean isSetBigintTypeName();

   void setBigintTypeName(String var1);

   void xsetBigintTypeName(XmlString var1);

   void setNilBigintTypeName();

   void unsetBigintTypeName();

   String getLastGeneratedKeyQuery();

   XmlString xgetLastGeneratedKeyQuery();

   boolean isNilLastGeneratedKeyQuery();

   boolean isSetLastGeneratedKeyQuery();

   void setLastGeneratedKeyQuery(String var1);

   void xsetLastGeneratedKeyQuery(XmlString var1);

   void setNilLastGeneratedKeyQuery();

   void unsetLastGeneratedKeyQuery();

   String getReservedWords();

   XmlString xgetReservedWords();

   boolean isNilReservedWords();

   boolean isSetReservedWords();

   void setReservedWords(String var1);

   void xsetReservedWords(XmlString var1);

   void setNilReservedWords();

   void unsetReservedWords();

   boolean getSupportsNullUpdateAction();

   XmlBoolean xgetSupportsNullUpdateAction();

   boolean isSetSupportsNullUpdateAction();

   void setSupportsNullUpdateAction(boolean var1);

   void xsetSupportsNullUpdateAction(XmlBoolean var1);

   void unsetSupportsNullUpdateAction();

   boolean getUseSchemaName();

   XmlBoolean xgetUseSchemaName();

   boolean isSetUseSchemaName();

   void setUseSchemaName(boolean var1);

   void xsetUseSchemaName(XmlBoolean var1);

   void unsetUseSchemaName();

   boolean getSupportsDeferredConstraints();

   XmlBoolean xgetSupportsDeferredConstraints();

   boolean isSetSupportsDeferredConstraints();

   void setSupportsDeferredConstraints(boolean var1);

   void xsetSupportsDeferredConstraints(XmlBoolean var1);

   void unsetSupportsDeferredConstraints();

   String getRealTypeName();

   XmlString xgetRealTypeName();

   boolean isNilRealTypeName();

   boolean isSetRealTypeName();

   void setRealTypeName(String var1);

   void xsetRealTypeName(XmlString var1);

   void setNilRealTypeName();

   void unsetRealTypeName();

   boolean getRequiresAliasForSubselect();

   XmlBoolean xgetRequiresAliasForSubselect();

   boolean isSetRequiresAliasForSubselect();

   void setRequiresAliasForSubselect(boolean var1);

   void xsetRequiresAliasForSubselect(XmlBoolean var1);

   void unsetRequiresAliasForSubselect();

   boolean getSupportsNullTableForGetIndexInfo();

   XmlBoolean xgetSupportsNullTableForGetIndexInfo();

   boolean isSetSupportsNullTableForGetIndexInfo();

   void setSupportsNullTableForGetIndexInfo(boolean var1);

   void xsetSupportsNullTableForGetIndexInfo(XmlBoolean var1);

   void unsetSupportsNullTableForGetIndexInfo();

   String getTrimTrailingFunction();

   XmlString xgetTrimTrailingFunction();

   boolean isNilTrimTrailingFunction();

   boolean isSetTrimTrailingFunction();

   void setTrimTrailingFunction(String var1);

   void xsetTrimTrailingFunction(XmlString var1);

   void setNilTrimTrailingFunction();

   void unsetTrimTrailingFunction();

   boolean getSupportsLockingWithSelectRange();

   XmlBoolean xgetSupportsLockingWithSelectRange();

   boolean isSetSupportsLockingWithSelectRange();

   void setSupportsLockingWithSelectRange(boolean var1);

   void xsetSupportsLockingWithSelectRange(XmlBoolean var1);

   void unsetSupportsLockingWithSelectRange();

   boolean getStorageLimitationsFatal();

   XmlBoolean xgetStorageLimitationsFatal();

   boolean isSetStorageLimitationsFatal();

   void setStorageLimitationsFatal(boolean var1);

   void xsetStorageLimitationsFatal(XmlBoolean var1);

   void unsetStorageLimitationsFatal();

   boolean getSupportsLockingWithInnerJoin();

   XmlBoolean xgetSupportsLockingWithInnerJoin();

   boolean isSetSupportsLockingWithInnerJoin();

   void setSupportsLockingWithInnerJoin(boolean var1);

   void xsetSupportsLockingWithInnerJoin(XmlBoolean var1);

   void unsetSupportsLockingWithInnerJoin();

   String getCurrentTimestampFunction();

   XmlString xgetCurrentTimestampFunction();

   boolean isNilCurrentTimestampFunction();

   boolean isSetCurrentTimestampFunction();

   void setCurrentTimestampFunction(String var1);

   void xsetCurrentTimestampFunction(XmlString var1);

   void setNilCurrentTimestampFunction();

   void unsetCurrentTimestampFunction();

   String getCastFunction();

   XmlString xgetCastFunction();

   boolean isNilCastFunction();

   boolean isSetCastFunction();

   void setCastFunction(String var1);

   void xsetCastFunction(XmlString var1);

   void setNilCastFunction();

   void unsetCastFunction();

   String getOtherTypeName();

   XmlString xgetOtherTypeName();

   boolean isNilOtherTypeName();

   boolean isSetOtherTypeName();

   void setOtherTypeName(String var1);

   void xsetOtherTypeName(XmlString var1);

   void setNilOtherTypeName();

   void unsetOtherTypeName();

   int getMaxIndexNameLength();

   XmlInt xgetMaxIndexNameLength();

   boolean isSetMaxIndexNameLength();

   void setMaxIndexNameLength(int var1);

   void xsetMaxIndexNameLength(XmlInt var1);

   void unsetMaxIndexNameLength();

   String getDistinctTypeName();

   XmlString xgetDistinctTypeName();

   boolean isNilDistinctTypeName();

   boolean isSetDistinctTypeName();

   void setDistinctTypeName(String var1);

   void xsetDistinctTypeName(XmlString var1);

   void setNilDistinctTypeName();

   void unsetDistinctTypeName();

   int getCharacterColumnSize();

   XmlInt xgetCharacterColumnSize();

   boolean isSetCharacterColumnSize();

   void setCharacterColumnSize(int var1);

   void xsetCharacterColumnSize(XmlInt var1);

   void unsetCharacterColumnSize();

   String getVarbinaryTypeName();

   XmlString xgetVarbinaryTypeName();

   boolean isNilVarbinaryTypeName();

   boolean isSetVarbinaryTypeName();

   void setVarbinaryTypeName(String var1);

   void xsetVarbinaryTypeName(XmlString var1);

   void setNilVarbinaryTypeName();

   void unsetVarbinaryTypeName();

   int getMaxTableNameLength();

   XmlInt xgetMaxTableNameLength();

   boolean isSetMaxTableNameLength();

   void setMaxTableNameLength(int var1);

   void xsetMaxTableNameLength(XmlInt var1);

   void unsetMaxTableNameLength();

   String getClosePoolSql();

   XmlString xgetClosePoolSql();

   boolean isNilClosePoolSql();

   boolean isSetClosePoolSql();

   void setClosePoolSql(String var1);

   void xsetClosePoolSql(XmlString var1);

   void setNilClosePoolSql();

   void unsetClosePoolSql();

   String getCurrentDateFunction();

   XmlString xgetCurrentDateFunction();

   boolean isNilCurrentDateFunction();

   boolean isSetCurrentDateFunction();

   void setCurrentDateFunction(String var1);

   void xsetCurrentDateFunction(XmlString var1);

   void setNilCurrentDateFunction();

   void unsetCurrentDateFunction();

   String getJoinSyntax();

   XmlString xgetJoinSyntax();

   boolean isNilJoinSyntax();

   boolean isSetJoinSyntax();

   void setJoinSyntax(String var1);

   void xsetJoinSyntax(XmlString var1);

   void setNilJoinSyntax();

   void unsetJoinSyntax();

   int getMaxEmbeddedBlobSize();

   XmlInt xgetMaxEmbeddedBlobSize();

   boolean isSetMaxEmbeddedBlobSize();

   void setMaxEmbeddedBlobSize(int var1);

   void xsetMaxEmbeddedBlobSize(XmlInt var1);

   void unsetMaxEmbeddedBlobSize();

   String getTrimBothFunction();

   XmlString xgetTrimBothFunction();

   boolean isNilTrimBothFunction();

   boolean isSetTrimBothFunction();

   void setTrimBothFunction(String var1);

   void xsetTrimBothFunction(XmlString var1);

   void setNilTrimBothFunction();

   void unsetTrimBothFunction();

   boolean getSupportsSelectStartIndex();

   XmlBoolean xgetSupportsSelectStartIndex();

   boolean isSetSupportsSelectStartIndex();

   void setSupportsSelectStartIndex(boolean var1);

   void xsetSupportsSelectStartIndex(XmlBoolean var1);

   void unsetSupportsSelectStartIndex();

   String getToLowerCaseFunction();

   XmlString xgetToLowerCaseFunction();

   boolean isNilToLowerCaseFunction();

   boolean isSetToLowerCaseFunction();

   void setToLowerCaseFunction(String var1);

   void xsetToLowerCaseFunction(XmlString var1);

   void setNilToLowerCaseFunction();

   void unsetToLowerCaseFunction();

   String getArrayTypeName();

   XmlString xgetArrayTypeName();

   boolean isNilArrayTypeName();

   boolean isSetArrayTypeName();

   void setArrayTypeName(String var1);

   void xsetArrayTypeName(XmlString var1);

   void setNilArrayTypeName();

   void unsetArrayTypeName();

   String getInnerJoinClause();

   XmlString xgetInnerJoinClause();

   boolean isNilInnerJoinClause();

   boolean isSetInnerJoinClause();

   void setInnerJoinClause(String var1);

   void xsetInnerJoinClause(XmlString var1);

   void setNilInnerJoinClause();

   void unsetInnerJoinClause();

   boolean getSupportsDefaultUpdateAction();

   XmlBoolean xgetSupportsDefaultUpdateAction();

   boolean isSetSupportsDefaultUpdateAction();

   void setSupportsDefaultUpdateAction(boolean var1);

   void xsetSupportsDefaultUpdateAction(XmlBoolean var1);

   void unsetSupportsDefaultUpdateAction();

   boolean getSupportsSchemaForGetColumns();

   XmlBoolean xgetSupportsSchemaForGetColumns();

   boolean isSetSupportsSchemaForGetColumns();

   void setSupportsSchemaForGetColumns(boolean var1);

   void xsetSupportsSchemaForGetColumns(XmlBoolean var1);

   void unsetSupportsSchemaForGetColumns();

   String getTinyintTypeName();

   XmlString xgetTinyintTypeName();

   boolean isNilTinyintTypeName();

   boolean isSetTinyintTypeName();

   void setTinyintTypeName(String var1);

   void xsetTinyintTypeName(XmlString var1);

   void setNilTinyintTypeName();

   void unsetTinyintTypeName();

   boolean getSupportsNullTableForGetPrimaryKeys();

   XmlBoolean xgetSupportsNullTableForGetPrimaryKeys();

   boolean isSetSupportsNullTableForGetPrimaryKeys();

   void setSupportsNullTableForGetPrimaryKeys(boolean var1);

   void xsetSupportsNullTableForGetPrimaryKeys(XmlBoolean var1);

   void unsetSupportsNullTableForGetPrimaryKeys();

   String getSystemSchemas();

   XmlString xgetSystemSchemas();

   boolean isNilSystemSchemas();

   boolean isSetSystemSchemas();

   void setSystemSchemas(String var1);

   void xsetSystemSchemas(XmlString var1);

   void setNilSystemSchemas();

   void unsetSystemSchemas();

   boolean getRequiresCastForMathFunctions();

   XmlBoolean xgetRequiresCastForMathFunctions();

   boolean isSetRequiresCastForMathFunctions();

   void setRequiresCastForMathFunctions(boolean var1);

   void xsetRequiresCastForMathFunctions(XmlBoolean var1);

   void unsetRequiresCastForMathFunctions();

   boolean getSupportsNullDeleteAction();

   XmlBoolean xgetSupportsNullDeleteAction();

   boolean isSetSupportsNullDeleteAction();

   void setSupportsNullDeleteAction(boolean var1);

   void xsetSupportsNullDeleteAction(XmlBoolean var1);

   void unsetSupportsNullDeleteAction();

   boolean getRequiresAutoCommitForMetaData();

   XmlBoolean xgetRequiresAutoCommitForMetaData();

   boolean isSetRequiresAutoCommitForMetaData();

   void setRequiresAutoCommitForMetaData(boolean var1);

   void xsetRequiresAutoCommitForMetaData(XmlBoolean var1);

   void unsetRequiresAutoCommitForMetaData();

   String getTimestampTypeName();

   XmlString xgetTimestampTypeName();

   boolean isNilTimestampTypeName();

   boolean isSetTimestampTypeName();

   void setTimestampTypeName(String var1);

   void xsetTimestampTypeName(XmlString var1);

   void setNilTimestampTypeName();

   void unsetTimestampTypeName();

   String getInitializationSql();

   XmlString xgetInitializationSql();

   boolean isNilInitializationSql();

   boolean isSetInitializationSql();

   void setInitializationSql(String var1);

   void xsetInitializationSql(XmlString var1);

   void setNilInitializationSql();

   void unsetInitializationSql();

   boolean getSupportsCascadeDeleteAction();

   XmlBoolean xgetSupportsCascadeDeleteAction();

   boolean isSetSupportsCascadeDeleteAction();

   void setSupportsCascadeDeleteAction(boolean var1);

   void xsetSupportsCascadeDeleteAction(XmlBoolean var1);

   void unsetSupportsCascadeDeleteAction();

   boolean getSupportsTimestampNanos();

   XmlBoolean xgetSupportsTimestampNanos();

   boolean isSetSupportsTimestampNanos();

   void setSupportsTimestampNanos(boolean var1);

   void xsetSupportsTimestampNanos(XmlBoolean var1);

   void unsetSupportsTimestampNanos();

   public static final class Factory {
      public static BuiltInDbdictionaryType newInstance() {
         return (BuiltInDbdictionaryType)XmlBeans.getContextTypeLoader().newInstance(BuiltInDbdictionaryType.type, (XmlOptions)null);
      }

      public static BuiltInDbdictionaryType newInstance(XmlOptions options) {
         return (BuiltInDbdictionaryType)XmlBeans.getContextTypeLoader().newInstance(BuiltInDbdictionaryType.type, options);
      }

      public static BuiltInDbdictionaryType parse(String xmlAsString) throws XmlException {
         return (BuiltInDbdictionaryType)XmlBeans.getContextTypeLoader().parse(xmlAsString, BuiltInDbdictionaryType.type, (XmlOptions)null);
      }

      public static BuiltInDbdictionaryType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (BuiltInDbdictionaryType)XmlBeans.getContextTypeLoader().parse(xmlAsString, BuiltInDbdictionaryType.type, options);
      }

      public static BuiltInDbdictionaryType parse(File file) throws XmlException, IOException {
         return (BuiltInDbdictionaryType)XmlBeans.getContextTypeLoader().parse(file, BuiltInDbdictionaryType.type, (XmlOptions)null);
      }

      public static BuiltInDbdictionaryType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (BuiltInDbdictionaryType)XmlBeans.getContextTypeLoader().parse(file, BuiltInDbdictionaryType.type, options);
      }

      public static BuiltInDbdictionaryType parse(URL u) throws XmlException, IOException {
         return (BuiltInDbdictionaryType)XmlBeans.getContextTypeLoader().parse(u, BuiltInDbdictionaryType.type, (XmlOptions)null);
      }

      public static BuiltInDbdictionaryType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (BuiltInDbdictionaryType)XmlBeans.getContextTypeLoader().parse(u, BuiltInDbdictionaryType.type, options);
      }

      public static BuiltInDbdictionaryType parse(InputStream is) throws XmlException, IOException {
         return (BuiltInDbdictionaryType)XmlBeans.getContextTypeLoader().parse(is, BuiltInDbdictionaryType.type, (XmlOptions)null);
      }

      public static BuiltInDbdictionaryType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (BuiltInDbdictionaryType)XmlBeans.getContextTypeLoader().parse(is, BuiltInDbdictionaryType.type, options);
      }

      public static BuiltInDbdictionaryType parse(Reader r) throws XmlException, IOException {
         return (BuiltInDbdictionaryType)XmlBeans.getContextTypeLoader().parse(r, BuiltInDbdictionaryType.type, (XmlOptions)null);
      }

      public static BuiltInDbdictionaryType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (BuiltInDbdictionaryType)XmlBeans.getContextTypeLoader().parse(r, BuiltInDbdictionaryType.type, options);
      }

      public static BuiltInDbdictionaryType parse(XMLStreamReader sr) throws XmlException {
         return (BuiltInDbdictionaryType)XmlBeans.getContextTypeLoader().parse(sr, BuiltInDbdictionaryType.type, (XmlOptions)null);
      }

      public static BuiltInDbdictionaryType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (BuiltInDbdictionaryType)XmlBeans.getContextTypeLoader().parse(sr, BuiltInDbdictionaryType.type, options);
      }

      public static BuiltInDbdictionaryType parse(Node node) throws XmlException {
         return (BuiltInDbdictionaryType)XmlBeans.getContextTypeLoader().parse(node, BuiltInDbdictionaryType.type, (XmlOptions)null);
      }

      public static BuiltInDbdictionaryType parse(Node node, XmlOptions options) throws XmlException {
         return (BuiltInDbdictionaryType)XmlBeans.getContextTypeLoader().parse(node, BuiltInDbdictionaryType.type, options);
      }

      /** @deprecated */
      public static BuiltInDbdictionaryType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (BuiltInDbdictionaryType)XmlBeans.getContextTypeLoader().parse(xis, BuiltInDbdictionaryType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static BuiltInDbdictionaryType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (BuiltInDbdictionaryType)XmlBeans.getContextTypeLoader().parse(xis, BuiltInDbdictionaryType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, BuiltInDbdictionaryType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, BuiltInDbdictionaryType.type, options);
      }

      private Factory() {
      }
   }
}
