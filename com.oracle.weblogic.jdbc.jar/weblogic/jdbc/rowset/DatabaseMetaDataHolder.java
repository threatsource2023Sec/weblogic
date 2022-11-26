package weblogic.jdbc.rowset;

import java.io.Serializable;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;

/** @deprecated */
@Deprecated
final class DatabaseMetaDataHolder implements Serializable {
   private static final long serialVersionUID = -3120626766920233516L;
   private int maxCatalogNameLength = 0;
   private int maxSchemaNameLength = 30;
   private int maxTableNameLength = 30;
   private String identifierQuoteString = "\"";
   private String catalogSeparator = "";
   private boolean isCatalogAtStart = false;
   private boolean supportsSchemasInDataManipulation = true;
   private boolean supportsCatalogsInDataManipulation = false;
   private boolean storesUpperCaseIdentifiers = true;
   private boolean storesLowerCaseIdentifiers = false;
   private boolean storesMixedCaseIdentifiers = false;
   private boolean storesUpperCaseQuotedIdentifiers = false;
   private boolean storesLowerCaseQuotedIdentifiers = false;
   private boolean storesMixedCaseQuotedIdentifiers = true;

   DatabaseMetaDataHolder(DatabaseMetaData databaseMetaData) throws SQLException {
      if (databaseMetaData == null) {
         throw new SQLException("Invalid parameter, can't initialize DatabaseMetaDataHolder.");
      } else {
         this.maxCatalogNameLength = databaseMetaData.getMaxCatalogNameLength();
         this.maxSchemaNameLength = databaseMetaData.getMaxSchemaNameLength();
         this.maxTableNameLength = databaseMetaData.getMaxTableNameLength();
         this.identifierQuoteString = databaseMetaData.getIdentifierQuoteString();
         this.catalogSeparator = databaseMetaData.getCatalogSeparator();
         this.isCatalogAtStart = databaseMetaData.isCatalogAtStart();
         this.supportsSchemasInDataManipulation = databaseMetaData.supportsSchemasInDataManipulation();
         this.supportsCatalogsInDataManipulation = databaseMetaData.supportsCatalogsInDataManipulation();
         this.storesUpperCaseIdentifiers = databaseMetaData.storesUpperCaseIdentifiers();
         this.storesLowerCaseIdentifiers = databaseMetaData.storesLowerCaseIdentifiers();
         this.storesMixedCaseIdentifiers = databaseMetaData.storesMixedCaseIdentifiers();
         this.storesUpperCaseQuotedIdentifiers = databaseMetaData.storesUpperCaseQuotedIdentifiers();
         this.storesLowerCaseQuotedIdentifiers = databaseMetaData.storesLowerCaseQuotedIdentifiers();
         this.storesMixedCaseQuotedIdentifiers = databaseMetaData.storesMixedCaseQuotedIdentifiers();
      }
   }

   DatabaseMetaDataHolder(int maxCatalogNameLength, int maxSchemaNameLength, int maxTableNameLength, String identifierQuoteString, String catalogSeparator, boolean isCatalogAtStart, boolean supportsSchemasInDataManipulation, boolean supportsCatalogsInDataManipulation, boolean storesUpperCaseIdentifiers, boolean storesLowerCaseIdentifiers, boolean storesMixedCaseIdentifiers, boolean storesUpperCaseQuotedIdentifiers, boolean storesLowerCaseQuotedIdentifiers, boolean storesMixedCaseQuotedIdentifiers) {
      this.maxCatalogNameLength = maxCatalogNameLength;
      this.maxSchemaNameLength = maxSchemaNameLength;
      this.maxTableNameLength = maxSchemaNameLength;
      this.identifierQuoteString = identifierQuoteString;
      this.catalogSeparator = catalogSeparator;
      this.isCatalogAtStart = isCatalogAtStart;
      this.supportsSchemasInDataManipulation = supportsSchemasInDataManipulation;
      this.supportsCatalogsInDataManipulation = supportsCatalogsInDataManipulation;
      this.storesUpperCaseIdentifiers = storesUpperCaseIdentifiers;
      this.storesLowerCaseIdentifiers = storesLowerCaseIdentifiers;
      this.storesMixedCaseIdentifiers = storesMixedCaseIdentifiers;
      this.storesUpperCaseQuotedIdentifiers = storesUpperCaseQuotedIdentifiers;
      this.storesLowerCaseQuotedIdentifiers = storesLowerCaseQuotedIdentifiers;
      this.storesMixedCaseQuotedIdentifiers = storesMixedCaseQuotedIdentifiers;
   }

   int getMaxCatalogNameLength() {
      return this.maxCatalogNameLength;
   }

   int getMaxSchemaNameLength() {
      return this.maxSchemaNameLength;
   }

   int getMaxTableNameLength() {
      return this.maxTableNameLength;
   }

   String getIdentifierQuoteString() {
      return this.identifierQuoteString;
   }

   String getCatalogSeparator() {
      return this.catalogSeparator;
   }

   boolean isCatalogAtStart() {
      return this.isCatalogAtStart;
   }

   boolean supportsCatalogsInDataManipulation() {
      return this.supportsCatalogsInDataManipulation;
   }

   boolean supportsSchemasInDataManipulation() {
      return this.supportsSchemasInDataManipulation;
   }

   boolean storesUpperCaseIdentifiers() {
      return this.storesUpperCaseIdentifiers;
   }

   boolean storesLowerCaseIdentifiers() {
      return this.storesLowerCaseIdentifiers;
   }

   boolean storesMixedCaseIdentifiers() {
      return this.storesMixedCaseIdentifiers;
   }

   boolean storesUpperCaseQuotedIdentifiers() {
      return this.storesUpperCaseQuotedIdentifiers;
   }

   boolean storesLowerCaseQuotedIdentifiers() {
      return this.storesLowerCaseQuotedIdentifiers;
   }

   boolean storesMixedCaseQuotedIdentifiers() {
      return this.storesMixedCaseQuotedIdentifiers;
   }

   boolean supportsIdentifierQuoting() {
      return !this.identifierQuoteString.equals(" ");
   }

   public boolean equals(Object obj) {
      if (obj == this) {
         return true;
      } else if (!(obj instanceof DatabaseMetaDataHolder)) {
         return false;
      } else {
         DatabaseMetaDataHolder metaDataHolder = (DatabaseMetaDataHolder)obj;
         return this.maxCatalogNameLength == metaDataHolder.maxCatalogNameLength && this.maxSchemaNameLength == metaDataHolder.maxSchemaNameLength && this.maxTableNameLength == metaDataHolder.maxTableNameLength && this.identifierQuoteString.equals(metaDataHolder.identifierQuoteString) && this.catalogSeparator.equals(metaDataHolder.catalogSeparator) && this.isCatalogAtStart == metaDataHolder.isCatalogAtStart && this.supportsSchemasInDataManipulation == metaDataHolder.supportsSchemasInDataManipulation && this.supportsCatalogsInDataManipulation == metaDataHolder.supportsCatalogsInDataManipulation && this.storesUpperCaseIdentifiers == metaDataHolder.storesUpperCaseIdentifiers && this.storesLowerCaseIdentifiers == metaDataHolder.storesLowerCaseIdentifiers && this.storesMixedCaseIdentifiers == metaDataHolder.storesMixedCaseIdentifiers && this.storesUpperCaseQuotedIdentifiers == metaDataHolder.storesUpperCaseQuotedIdentifiers && this.storesLowerCaseQuotedIdentifiers == metaDataHolder.storesLowerCaseQuotedIdentifiers && this.storesMixedCaseQuotedIdentifiers == metaDataHolder.storesMixedCaseQuotedIdentifiers;
      }
   }

   public int hashCode() {
      int result = 17;
      result = 37 * result + this.maxCatalogNameLength;
      result = 37 * result + this.maxSchemaNameLength;
      result = 37 * result + this.maxTableNameLength;
      result = 37 * result + (this.identifierQuoteString == null ? 0 : this.identifierQuoteString.hashCode());
      result = 37 * result + (this.catalogSeparator == null ? 0 : this.catalogSeparator.hashCode());
      result = 37 * result + (this.isCatalogAtStart ? 0 : 1);
      result = 37 * result + (this.supportsSchemasInDataManipulation ? 0 : 1);
      result = 37 * result + (this.supportsCatalogsInDataManipulation ? 0 : 1);
      result = 37 * result + (this.storesUpperCaseIdentifiers ? 0 : 1);
      result = 37 * result + (this.storesLowerCaseIdentifiers ? 0 : 1);
      result = 37 * result + (this.storesMixedCaseIdentifiers ? 0 : 1);
      result = 37 * result + (this.storesUpperCaseQuotedIdentifiers ? 0 : 1);
      result = 37 * result + (this.storesLowerCaseQuotedIdentifiers ? 0 : 1);
      result = 37 * result + (this.storesMixedCaseQuotedIdentifiers ? 0 : 1);
      return result;
   }

   public String toString() {
      StringBuffer sb = new StringBuffer();
      sb.append("maxCatalogNameLength=" + this.maxCatalogNameLength).append(",");
      sb.append("maxSchemaNameLength=" + this.maxSchemaNameLength).append(",");
      sb.append("maxTableNameLength=" + this.maxTableNameLength).append(",");
      sb.append("identifierQuoteString=" + this.identifierQuoteString).append(",");
      sb.append("catalogSeparator=" + this.catalogSeparator).append(",");
      sb.append("isCatalogAtStart=" + this.isCatalogAtStart).append(",");
      sb.append("supportsSchemasInDataManipulation=" + this.supportsSchemasInDataManipulation).append(",");
      sb.append("supportsCatalogsInDataManipulation=" + this.supportsCatalogsInDataManipulation).append(",");
      sb.append("storesUpperCaseIdentifiers=" + this.storesUpperCaseIdentifiers).append(",");
      sb.append("storesLowerCaseIdentifiers=" + this.storesLowerCaseIdentifiers).append(",");
      sb.append("storesMixedCaseIdentifiers=" + this.storesMixedCaseIdentifiers).append(",");
      sb.append("storesUpperCaseQuotedIdentifiers=" + this.storesUpperCaseQuotedIdentifiers).append(",");
      sb.append("storesLowerCaseQuotedIdentifiers=" + this.storesLowerCaseQuotedIdentifiers).append(",");
      sb.append("storesMixedCaseQuotedIdentifiers=" + this.storesMixedCaseQuotedIdentifiers);
      return sb.toString();
   }
}
