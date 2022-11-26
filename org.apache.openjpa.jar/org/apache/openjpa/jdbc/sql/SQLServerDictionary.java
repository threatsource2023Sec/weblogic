package org.apache.openjpa.jdbc.sql;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import org.apache.openjpa.jdbc.kernel.exps.FilterValue;
import org.apache.openjpa.jdbc.schema.Column;
import org.apache.openjpa.kernel.Filters;
import org.apache.openjpa.lib.util.Localizer;
import org.apache.openjpa.meta.JavaTypes;

public class SQLServerDictionary extends AbstractSQLServerDictionary {
   public static final String VENDOR_MICROSOFT = "microsoft";
   public static final String VENDOR_NETDIRECT = "netdirect";
   public static final String VENDOR_JTDS = "jtds";
   private static final Localizer _loc = Localizer.forPackage(SQLServerDictionary.class);
   private String schemaCase = "preserve";
   public boolean uniqueIdentifierAsVarbinary = true;

   public SQLServerDictionary() {
      this.platform = "Microsoft SQL Server";
      this.forUpdateClause = null;
      this.tableForUpdateClause = "WITH (UPDLOCK)";
      this.supportsNullTableForGetColumns = false;
      this.requiresAliasForSubselect = true;
      this.stringLengthFunction = "LEN({0})";
   }

   public void connectedConfiguration(Connection conn) throws SQLException {
      super.connectedConfiguration(conn);
      DatabaseMetaData meta = conn.getMetaData();
      String driverName = meta.getDriverName();
      String url = meta.getURL();
      String versionString;
      if (this.driverVendor == null) {
         if ("NetDirect JSQLConnect".equals(driverName)) {
            this.driverVendor = "netdirect";
         } else if (driverName != null && driverName.startsWith("jTDS")) {
            this.driverVendor = "jtds";
         } else if ("SQLServer".equals(driverName)) {
            if (url != null && url.startsWith("jdbc:microsoft:sqlserver:")) {
               this.driverVendor = "microsoft";
            } else if (url != null && url.startsWith("jdbc:datadirect:sqlserver:")) {
               this.driverVendor = "datadirect";
            } else {
               this.driverVendor = "other";
            }
         } else {
            this.driverVendor = "other";
         }

         if (driverName.indexOf(this.platform) != -1) {
            versionString = driverName.substring(this.platform.length() + 1);
            if (versionString.indexOf(" ") != -1) {
               versionString = versionString.substring(0, versionString.indexOf(" "));
            }

            int version = Integer.parseInt(versionString);
            if (version >= 2005) {
               this.supportsXMLColumn = true;
            }
         }
      }

      if (("microsoft".equalsIgnoreCase(this.driverVendor) || "datadirect".equalsIgnoreCase(this.driverVendor)) && url.toLowerCase().indexOf("selectmethod=cursor") == -1) {
         this.log.warn(_loc.get("sqlserver-cursor", (Object)url));
      }

      versionString = this.conf.getConnectionFactoryProperties();
      if (versionString == null) {
         versionString = "";
      }

      if ("microsoft".equalsIgnoreCase(this.driverVendor) && versionString.toLowerCase().indexOf("maxcachedstatements=0") == -1) {
         this.log.warn(_loc.get("sqlserver-cachedstmnts"));
      }

   }

   public Column[] getColumns(DatabaseMetaData meta, String catalog, String schemaName, String tableName, String columnName, Connection conn) throws SQLException {
      Column[] cols = super.getColumns(meta, catalog, schemaName, tableName, columnName, conn);

      for(int i = 0; cols != null && i < cols.length; ++i) {
         String typeName = cols[i].getTypeName();
         if (typeName != null) {
            typeName = typeName.toUpperCase();
            if ("NVARCHAR".equals(typeName)) {
               cols[i].setType(12);
            } else if ("UNIQUEIDENTIFIER".equals(typeName)) {
               if (this.uniqueIdentifierAsVarbinary) {
                  cols[i].setType(-3);
               } else {
                  cols[i].setType(12);
               }
            } else if ("NCHAR".equals(typeName)) {
               cols[i].setType(1);
            } else if ("NTEXT".equals(typeName)) {
               cols[i].setType(2005);
            }
         }
      }

      return cols;
   }

   protected void appendLength(SQLBuffer buf, int type) {
      if (type == 12) {
         buf.append("(").append(Integer.toString(this.characterColumnSize)).append(")");
      }

   }

   public void appendXmlComparison(SQLBuffer buf, String op, FilterValue lhs, FilterValue rhs, boolean lhsxml, boolean rhsxml) {
      super.appendXmlComparison(buf, op, lhs, rhs, lhsxml, rhsxml);
      if (lhsxml && rhsxml) {
         this.appendXmlComparison2(buf, op, lhs, rhs);
      } else if (lhsxml) {
         this.appendXmlComparison1(buf, op, lhs, rhs);
      } else {
         this.appendXmlComparison1(buf, op, rhs, lhs);
      }

   }

   private void appendXmlComparison1(SQLBuffer buf, String op, FilterValue lhs, FilterValue rhs) {
      boolean castrhs = rhs.isConstant();
      if (castrhs) {
         this.appendXmlValue(buf, lhs);
      } else {
         this.appendXmlExist(buf, lhs);
      }

      buf.append(" ").append(op).append(" ");
      if (castrhs) {
         rhs.appendTo(buf);
      } else {
         buf.append("sql:column(\"");
         rhs.appendTo(buf);
         buf.append("\")").append("]') = 1");
      }

   }

   private void appendXmlExist(SQLBuffer buf, FilterValue lhs) {
      buf.append(lhs.getColumnAlias(lhs.getFieldMapping().getColumns()[0])).append(".exist('").append("/*[");
      lhs.appendTo(buf);
   }

   private void appendXmlComparison2(SQLBuffer buf, String op, FilterValue lhs, FilterValue rhs) {
      this.appendXmlValue(buf, lhs);
      buf.append(" ").append(op).append(" ");
      this.appendXmlValue(buf, rhs);
   }

   private void appendXmlValue(SQLBuffer buf, FilterValue val) {
      Class rc = Filters.wrap(val.getType());
      int type = this.getJDBCType(JavaTypes.getTypeCode(rc), false);
      boolean isXmlAttribute = val.getXmlMapping() == null ? false : val.getXmlMapping().isXmlAttribute();
      buf.append(val.getColumnAlias(val.getFieldMapping().getColumns()[0])).append(".value(").append("'(/*/");
      val.appendTo(buf);
      if (!isXmlAttribute) {
         buf.append("/text()");
      }

      buf.append(")[1]','").append(this.getTypeName(type));
      this.appendLength(buf, type);
      buf.append("')");
   }

   public String getSchemaCase() {
      return this.schemaCase;
   }
}
