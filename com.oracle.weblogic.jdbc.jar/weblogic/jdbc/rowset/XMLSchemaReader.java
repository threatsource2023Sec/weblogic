package weblogic.jdbc.rowset;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import weblogic.xml.stream.StartElement;
import weblogic.xml.stream.XMLEvent;
import weblogic.xml.stream.XMLInputStream;
import weblogic.xml.stream.XMLInputStreamFactory;
import weblogic.xml.stream.XMLName;
import weblogic.xml.stream.util.TypeFilter;

public final class XMLSchemaReader implements XMLSchemaConstants {
   private static final boolean DEBUG = true;
   private static final boolean VERBOSE = true;
   private CachedRowSetMetaData metaData;
   private XMLInputStreamFactory factory = XMLInputStreamFactory.newInstance();

   public XMLSchemaReader(WLRowSetMetaData metaData) {
      this.metaData = (CachedRowSetMetaData)metaData;
   }

   public void loadSchema(XMLInputStream xis) throws IOException, SQLException {
      this.parse(this.factory.newInputStream(xis, new TypeFilter(2)));
   }

   private void parse(XMLInputStream xis) throws IOException, SQLException {
      while(true) {
         if (xis.skip(ELEMENT_NAME)) {
            StartElement se = this.getNext(xis);
            String rowSetName = XMLUtils.getRequiredAttribute(se, "name").getValue();
            if (!this.metaData.claimSchema(rowSetName)) {
               continue;
            }

            this.parseRowSet(xis, se);
            return;
         }

         throw new IOException("XMLInputStream did not contain a row set matching the name: " + this.metaData.getRowSetName() + ".  Please ensure your XMLInputStream does contain a rowset schema.  Also ensure that the WLRowSetMetaData.getRowSetName() matches the name attribute in the schema");
      }
   }

   private void parseRowSet(XMLInputStream xis, StartElement se) throws IOException, SQLException {
      String defaultNamespace = XMLUtils.getRequiredAttribute(se, WLDD_DEFAULT_NAMESPACE).getValue();
      this.metaData.setDefaultNamespace(defaultNamespace);
      String rowSetName = XMLUtils.getRequiredAttribute(se, "name").getValue();
      this.metaData.setRowSetName(rowSetName);
      String writeTableName = XMLUtils.getOptionalStringAttribute(se, WLDD_WRITE_TABLE_NAME);
      this.metaData.setWriteTableName(writeTableName);
      String optimisticPolicy = XMLUtils.getOptionalStringAttribute(se, WLDD_OPTPOLICY);
      if (optimisticPolicy != null) {
         this.metaData.setOptimisticPolicyAsString(optimisticPolicy);
      }

      int version;
      try {
         version = Integer.parseInt(XMLUtils.getOptionalStringAttribute(se, WLDD_METADATA_VERSION));
      } catch (NumberFormatException var10) {
         version = 1;
      }

      this.metaData.setVersion(version);
      if (version >= 2) {
         boolean isValidMetaData = XMLUtils.getOptionalBooleanAttribute(se, WLDD_VALID_METADATA);
         DatabaseMetaDataHolder dmdHolder = null;
         if (isValidMetaData) {
            dmdHolder = new DatabaseMetaDataHolder(Integer.parseInt(XMLUtils.getOptionalStringAttribute(se, WLDD_MAX_CATALOG_NAME_LENGTH)), Integer.parseInt(XMLUtils.getOptionalStringAttribute(se, WLDD_MAX_SCHEMA_NAME_LENGTH)), Integer.parseInt(XMLUtils.getOptionalStringAttribute(se, WLDD_MAX_TABLE_NAME_LENGTH)), XMLUtils.getOptionalStringAttribute(se, WLDD_IDENTIFIER_QUOTE_STRING), XMLUtils.getOptionalStringAttribute(se, WLDD_CATALOG_SEPARATOR), XMLUtils.getOptionalBooleanAttribute(se, WLDD_CATALOG_AT_START), XMLUtils.getOptionalBooleanAttribute(se, WLDD_SUPPORTS_SCHEMAS_IN_DML), XMLUtils.getOptionalBooleanAttribute(se, WLDD_SUPPORTS_CATALOGS_IN_DML), XMLUtils.getOptionalBooleanAttribute(se, WLDD_STORES_UPPER_CASE_IDENTIFIERS), XMLUtils.getOptionalBooleanAttribute(se, WLDD_STORES_LOWER_CASE_IDENTIFIERS), XMLUtils.getOptionalBooleanAttribute(se, WLDD_STORES_MIXED_CASE_IDENTIFIERS), XMLUtils.getOptionalBooleanAttribute(se, WLDD_STORES_UPPER_CASE_QUOTED_IDENTIFIERS), XMLUtils.getOptionalBooleanAttribute(se, WLDD_STORES_LOWER_CASE_QUOTED_IDENTIFIERS), XMLUtils.getOptionalBooleanAttribute(se, WLDD_STORES_MIXED_CASE_QUOTED_IDENTIFIERS));
         }

         this.metaData.setMetaDataHolder(dmdHolder);
      } else {
         this.metaData.setMetaDataHolder((DatabaseMetaDataHolder)null);
      }

      xis.skip(ELEMENT_NAME);
      this.parseRow(xis);
   }

   private void parseRow(XMLInputStream xis) throws IOException, SQLException {
      StartElement se = this.getNext(xis);
      this.checkName(se, ELEMENT_NAME);
      this.metaData.readRowAttributes(se);
      String rowName = XMLUtils.getRequiredAttribute(se, "name").getValue();
      this.metaData.setRowName(rowName);
      xis.skip(ELEMENT_NAME);
      this.parseColumns(xis);
   }

   private void parseColumns(XMLInputStream xis) throws IOException, SQLException {
      List columnList = new ArrayList();

      StartElement se;
      while(xis.hasNext()) {
         XMLEvent e = xis.peek();
         if (e instanceof StartElement) {
            if (!ELEMENT_NAME.equals(e.getName())) {
               break;
            }

            se = (StartElement)xis.next();
            ((List)columnList).add(se);
         } else {
            xis.next();
         }
      }

      if (this.metaData.getColumnCount() > 0) {
         columnList = this.pruneColumnList((List)columnList);
      } else {
         this.metaData.setColumnCount(((List)columnList).size());
      }

      for(int i = 0; i < ((List)columnList).size(); ++i) {
         se = (StartElement)((List)columnList).get(i);
         this.metaData.readXMLAttributes(i, se);
      }

   }

   private List pruneColumnList(List schemaList) throws IOException, SQLException {
      if (this.metaData.getColumnCount() > schemaList.size()) {
         throw new IOException("Existing WLCachedRowSetMetaData contains " + this.metaData.getColumnCount() + " columns, but the XML Schema contains " + schemaList.size() + " columns.  The XML Schema must be a superset of the existing WLCachedRowSetMetaData.");
      } else {
         Iterator it = schemaList.iterator();
         List columnList = new ArrayList();
         int expected = 1;

         int stop;
         while(it.hasNext()) {
            StartElement se = (StartElement)it.next();
            String colName = XMLUtils.getRequiredAttribute(se, "name").getValue();

            try {
               stop = this.metaData.findColumn(colName);
               if (stop != expected) {
                  throw new IOException("The column named: " + colName + " was found in the existing WLCachedRowSetMetaData at column " + stop + " however we expected it at " + expected + ".  This could indicate the column " + this.metaData.getColumnName(expected) + " is not in the XML Schema, or the order of columns in the schema is not the same as the order of columns in the WLCachedRowSetMetaData.");
               }

               columnList.add(se);
               ++expected;
            } catch (SQLException var8) {
            }
         }

         if (expected == this.metaData.getColumnCount() + 1) {
            return columnList;
         } else {
            String sep = "";
            StringBuffer sb = new StringBuffer();

            for(stop = this.metaData.getColumnCount() + 1; expected != stop; ++expected) {
               sb.append(sep);
               sep = ", ";
               sb.append(this.metaData.getColumnName(expected));
            }

            throw new IOException("The following columns are in the existing WLCachedRowSetMetaData, but are not in the XML Schema: " + sb.toString() + ".  The XML Schema must be a superset of the existing WLCachedRowSetMetaData.");
         }
      }
   }

   private StartElement getNext(XMLInputStream xis) throws IOException {
      return xis.hasNext() ? (StartElement)xis.next() : null;
   }

   private void checkName(StartElement se, XMLName n) throws IOException {
      if (se == null) {
         throw new IOException("Expected Element: " + n + " but found end of file (EOF)");
      } else if (!n.equals(se.getName())) {
         throw new IOException("Expected Element: " + n + " but found " + se.getName());
      }
   }
}
