package weblogic.coherence.descriptor.wl;

import weblogic.descriptor.SettableBean;

public interface CoherenceInitParamBean extends SettableBean {
   String STRING_TYPE_ABBREV = "string";
   String STRING_TYPE = "java.lang.String";
   String BOOLEAN_TYPE_ABBREV = "boolean";
   String BOOLEAN_TYPE = "java.lang.Boolean";
   String INT_TYPE_ABBREV = "int";
   String INT_TYPE = "java.lang.Integer";
   String LONG_TYPE_ABBREV = "long";
   String LONG_TYPE = "java.lang.Long";
   String DOUBLE_TYPE_ABBREV = "double";
   String DOUBLE_TYPE = "java.lang.Double";
   String DECIMAL_TYPE_ABBREV = "decimal";
   String DECIMAL_TYPE = "java.math.BigDecimal";
   String FILE_TYPE_ABBREV = "file";
   String FILE_TYPE = "java.io.File";
   String DATE_TYPE_ABBREV = "date";
   String DATE_TYPE = "java.sql.Date";
   String TIME_TYPE_ABBREV = "time";
   String TIME_TYPE = "java.sql.Time";
   String DATETIME_TYPE_ABBREV = "datetime";
   String DATETIME_TYPE = "java.sql.Timestamp";

   String getName();

   void setName(String var1);

   String getParamType();

   void setParamType(String var1);

   String getParamValue();

   void setParamValue(String var1);
}
