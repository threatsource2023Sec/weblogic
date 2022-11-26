package weblogic.ejb.container.cmp.rdbms;

import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import weblogic.ejb.container.persistence.spi.CmrField;
import weblogic.ejb.container.persistence.spi.EjbRelationshipRole;
import weblogic.ejb.container.utils.ClassUtils;
import weblogic.ejb.container.utils.MethodUtils;
import weblogic.utils.Debug;
import weblogic.utils.StackTraceUtilsClient;

public final class RDBMSUtils {
   public static final String ONE = "One";
   public static final String MANY = "Many";
   public static final String EJB_CREATE = "ejbCreate";
   public static final String EJB_POST_CREATE = "ejbPostCreate";
   public static final String COMMIT = "commit";
   public static final String DEFAULT_GROUP_NAME = "defaultGroup";
   public static final short STATE_INVALID = -2;
   public static final short STATE_VALID = 0;
   protected static final String[] validRdbmsCmp20JarPublicIds = new String[]{"-//BEA Systems, Inc.//DTD WebLogic 6.0.0 EJB RDBMS Persistence//EN", "-//BEA Systems, Inc.//DTD WebLogic 7.0.0 EJB RDBMS Persistence//EN", "-//BEA Systems, Inc.//DTD WebLogic 8.1.0 EJB RDBMS Persistence//EN"};
   public static final short GEN_KEY_PK_CLASS_INTEGER = 0;
   public static final short GEN_KEY_PK_CLASS_LONG = 1;
   public static final short IDENTITY = 1;
   public static final short SEQUENCE = 2;
   public static final short SEQUENCE_TABLE = 3;
   public static final short RELATIONSHIP_TYPE_NOT_APPLICABLE = -1;
   public static final short CMP_FIELD = 0;
   public static final short SINGLE_BEAN_NO_RELATION = 1;
   public static final short ONE_TO_ONE_RELATION_FK_ON_LHS = 2;
   public static final short ONE_TO_ONE_RELATION_FK_ON_RHS = 3;
   public static final short ONE_TO_MANY_RELATION = 4;
   public static final short MANY_TO_ONE_RELATION = 5;
   public static final short MANY_TO_MANY_RELATION = 6;
   public static final short REMOTE_RELATION_FK_ON_LHS = 7;
   public static final short REMOTE_RELATION_W_JOIN_TABLE = 8;
   private static final String CONTAINER_SEQUENCE_SUFFIX = "_WL";
   public static final String ACCESS_ORDER = "AccessOrder";
   public static final String VALUE_ORDER = "ValueOrder";

   public static String relationshipTypeToString(int type) {
      switch (type) {
         case -1:
            return "relationship type unknown or not applicable";
         case 0:
            return "cmp-field";
         case 1:
            return "single bean only, no relationship";
         case 2:
            return "one-to-one relationship, foreign key on left hand side";
         case 3:
            return "one-to-one relationship, foreign key on right hand side";
         case 4:
            return "one-to-many relationship";
         case 5:
            return "many-to-one relationship";
         case 6:
            return "many-to-many relationship";
         case 7:
            return "remote relationship foreign key on left hand side";
         case 8:
            return "remote relationship involving a join table";
         default:
            return "unknown relationship type";
      }
   }

   public static String getContainerSequenceName(String sequenceName) {
      Debug.assertion(!isContainerSequenceName(sequenceName), "called getContainerSequenceName on '" + sequenceName + "' which is already a containerSequenceName");
      return sequenceName + "_WL";
   }

   public static boolean isContainerSequenceName(String sequenceName) {
      return sequenceName.indexOf("_WL") != -1;
   }

   public static String throwable2StackTrace(Throwable th) {
      return StackTraceUtilsClient.throwable2StackTrace(th);
   }

   public static String setterMethodName(String fieldName) {
      return "set" + fieldName.substring(0, 1).toUpperCase(Locale.ENGLISH) + fieldName.substring(1);
   }

   public static String getterMethodName(String fieldName) {
      return "get" + fieldName.substring(0, 1).toUpperCase(Locale.ENGLISH) + fieldName.substring(1);
   }

   public static String getCmrFieldName(EjbRelationshipRole role, EjbRelationshipRole other) {
      String cmrFieldName = null;
      CmrField roleField = role.getCmrField();
      if (roleField != null) {
         cmrFieldName = roleField.getName();
      } else {
         String otherName = other.getRoleSource().getEjbName();
         cmrFieldName = MethodUtils.decapitalize(ClassUtils.makeLegalName(otherName)) + "_" + other.getCmrField().getName();
      }

      return cmrFieldName;
   }

   public static String escQuotedID(String in) {
      StringBuffer result = new StringBuffer();
      char[] var2 = in.toCharArray();
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         char input = var2[var4];
         if (input == '"') {
            result.append('\\');
         }

         result.append(input);
      }

      return result.toString();
   }

   public static String head(String name) {
      int i = name.lastIndexOf(".");
      return i == -1 ? "" : name.substring(0, i);
   }

   public static String tail(String name) {
      int i = name.lastIndexOf(".");
      return name.substring(i + 1);
   }

   public static String sqlTypeToString(int sqlType) {
      switch (sqlType) {
         case -7:
            return "java.sql.Types.BIT";
         case -6:
            return "java.sql.Types.TINYINT";
         case -5:
            return "java.sql.Types.BIGINT";
         case -4:
            return "java.sql.Types.LONGVARBINARY";
         case -3:
            return "java.sql.Types.VARBINARY";
         case -2:
            return "java.sql.Types.BINARY";
         case -1:
            return "java.sql.Types.LONGVARCHAR";
         case 0:
            return "java.sql.Types.NULL";
         case 1:
            return "java.sql.Types.CHAR";
         case 2:
            return "java.sql.Types.NUMERIC";
         case 3:
            return "java.sql.Types.DECIMAL";
         case 4:
            return "java.sql.Types.INTEGER";
         case 5:
            return "java.sql.Types.SMALLINT";
         case 6:
            return "java.sql.Types.FLOAT";
         case 7:
            return "java.sql.Types.REAL";
         case 8:
            return "java.sql.Types.DOUBLE";
         case 12:
            return "java.sql.Types.VARCHAR";
         case 91:
            return "java.sql.Types.DATE";
         case 92:
            return "java.sql.Types.TIME";
         case 93:
            return "java.sql.Types.TIMESTAMP";
         case 1111:
            return "java.sql.Types.OTHER";
         case 2000:
            return "java.sql.Types.JAVA_OBJECT";
         case 2001:
            return "java.sql.Types.DISTINCT";
         case 2002:
            return "java.sql.Types.STRUCT";
         case 2003:
            return "java.sql.Types.ARRAY";
         case 2004:
            return "java.sql.Types.BLOB";
         case 2005:
            return "java.sql.Types.CLOB";
         case 2006:
            return "java.sql.Types.REF";
         default:
            return "Invalid SQL type: " + sqlType;
      }
   }

   public static String selectForUpdateToString(int val) {
      switch (val) {
         case 0:
            return "";
         case 1:
            return " FOR UPDATE ";
         case 2:
            return " FOR UPDATE NOWAIT ";
         default:
            throw new AssertionError("Unknown selectForUpdate type: '" + val + "'");
      }
   }

   public static boolean dbSupportForSingleLeftOuterJoin(int dbType) {
      return dbSupportForSingleLeftOuterJoinANSI(dbType) || dbSupportForSingleLeftOuterJoinInWhereClause(dbType);
   }

   public static boolean dbSupportForMultiLeftOuterJoin(int dbType) {
      return dbSupportForMultiLeftOuterJoinANSI(dbType) || dbSupportForMultiLeftOuterJoinInWhereClause(dbType);
   }

   public static boolean dbSupportForSingleLeftOuterJoinANSI(int dbType) {
      switch (dbType) {
         case 4:
         case 6:
         case 7:
         case 8:
         case 9:
         case 10:
            return true;
         case 5:
         default:
            return false;
      }
   }

   public static boolean dbSupportForMultiLeftOuterJoinANSI(int dbType) {
      switch (dbType) {
         case 4:
         case 7:
         case 8:
         case 9:
            return true;
         case 5:
         case 6:
         default:
            return false;
      }
   }

   public static boolean dbSupportForSingleLeftOuterJoinInWhereClause(int dbType) {
      switch (dbType) {
         case 0:
         case 4:
         case 7:
         case 8:
         case 9:
         default:
            return false;
         case 1:
            return true;
         case 2:
         case 5:
         case 10:
            return true;
         case 3:
            return false;
         case 6:
            return false;
      }
   }

   public static boolean dbSupportForMultiLeftOuterJoinInWhereClause(int dbType) {
      switch (dbType) {
         case 0:
         case 4:
         case 7:
         case 8:
         default:
            return false;
         case 1:
            return true;
         case 2:
         case 5:
            return false;
         case 3:
            return false;
         case 6:
            return false;
      }
   }

   public static String getFROMClauseSelectForUpdate(int dbtype, int selectForUpdateVal) {
      switch (dbtype) {
         case 0:
         case 1:
         case 3:
         case 4:
         case 6:
         case 8:
         case 9:
         case 10:
            return "";
         case 2:
         case 7:
            if (selectForUpdateVal == 1) {
               return " WITH(UPDLOCK) ";
            }

            if (selectForUpdateVal == 2) {
               return "";
            }

            if (selectForUpdateVal == 0) {
               return "";
            }
            break;
         case 5:
            if (selectForUpdateVal == 1) {
               return " HOLDLOCK ";
            }

            if (selectForUpdateVal == 2) {
               return "";
            }

            if (selectForUpdateVal == 0) {
               return "";
            }
            break;
         default:
            Debug.assertion(false, "Undefined database type " + dbtype);
      }

      return "";
   }

   public static short getGenKeyTypeAsConstant(String genKeyType) {
      if (genKeyType.equalsIgnoreCase("Identity")) {
         return 1;
      } else if (genKeyType.equalsIgnoreCase("Sequence")) {
         return 2;
      } else {
         return (short)(genKeyType.equalsIgnoreCase("SequenceTable") ? 3 : -1);
      }
   }

   public static boolean isOracleNLSDataType(RDBMSBean bean, String variable, Map globalRangeVariableMap) {
      if (globalRangeVariableMap != null && !globalRangeVariableMap.isEmpty() && variable.indexOf(46) != -1) {
         int dotIndex = variable.indexOf(46);
         String abstractSchemaName = (String)globalRangeVariableMap.get(variable.substring(0, dotIndex));
         if (abstractSchemaName != null) {
            Map beanMap = bean.getRdbmsBeanMap();
            if (beanMap != null && !beanMap.isEmpty()) {
               RDBMSBean relBean = null;
               Iterator var7 = beanMap.values().iterator();

               while(var7.hasNext()) {
                  RDBMSBean b = (RDBMSBean)var7.next();
                  if (abstractSchemaName.equals(b.getAbstractSchemaName())) {
                     relBean = b;
                     break;
                  }
               }

               if (relBean != null) {
                  return isOracleNLSDataType(relBean, variable.substring(dotIndex + 1));
               }
            }
         }

         return isOracleNLSDataType(bean, variable);
      } else {
         return isOracleNLSDataType(bean, variable);
      }
   }

   public static boolean isOracleNLSDataType(RDBMSBean bean, String variable) {
      if (bean.getDatabaseType() != 1) {
         return false;
      } else {
         String fieldName = bean.getField(variable);
         if (!variable.equals(fieldName)) {
            String relatedFieldName = null;
            if (fieldName == null) {
               int dotIndex = variable.indexOf(".");
               if (dotIndex != -1) {
                  fieldName = variable.substring(0, dotIndex);
                  relatedFieldName = variable.substring(dotIndex + 1);
               }
            }

            int dotIndex;
            for(RDBMSBean relatedBean = bean; fieldName != null && relatedBean.getCmrFieldNames().contains(fieldName); relatedFieldName = relatedFieldName.substring(dotIndex + 1)) {
               relatedBean = relatedBean.getRelatedRDBMSBean(fieldName);
               if (relatedFieldName == null) {
                  String columnName = bean.getCmpColumnForVariable(variable);
                  if (columnName != null) {
                     relatedFieldName = bean.getRelatedPkFieldName(fieldName, columnName);
                     return "NCHAR".equalsIgnoreCase(relatedBean.getCmpColumnTypeForField(relatedFieldName)) || "NVARCHAR2".equalsIgnoreCase(relatedBean.getCmpColumnTypeForField(relatedFieldName)) || "NCLOB".equalsIgnoreCase(relatedBean.getCmpColumnTypeForField(relatedFieldName));
                  }
                  break;
               }

               dotIndex = relatedFieldName.indexOf(".");
               if (dotIndex == -1) {
                  return "NCHAR".equalsIgnoreCase(relatedBean.getCmpColumnTypeForField(relatedFieldName)) || "NVARCHAR2".equalsIgnoreCase(relatedBean.getCmpColumnTypeForField(relatedFieldName)) || "NCLOB".equalsIgnoreCase(relatedBean.getCmpColumnTypeForField(relatedFieldName));
               }

               fieldName = relatedFieldName.substring(0, dotIndex);
            }
         }

         if (!"NCHAR".equalsIgnoreCase(bean.getCmpColumnTypeForField(variable)) && !"NVARCHAR2".equalsIgnoreCase(bean.getCmpColumnTypeForField(variable)) && !"NCLOB".equalsIgnoreCase(bean.getCmpColumnTypeForField(variable))) {
            return false;
         } else {
            return true;
         }
      }
   }
}
