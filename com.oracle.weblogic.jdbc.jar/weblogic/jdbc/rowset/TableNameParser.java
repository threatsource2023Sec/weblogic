package weblogic.jdbc.rowset;

import java.util.ArrayList;
import java.util.regex.Pattern;

/** @deprecated */
@Deprecated
final class TableNameParser {
   private static final String EMPTY_STRING = "";
   private static final String SCHEMA_SEPARATOR = ".";
   private static final Pattern WHITESPACE_PATTERN = Pattern.compile("\\s");
   private static final int PATTERN_CATALOG_SCHEMA_TABLE = 1;
   private static final int PATTERN_CATALOG_SCHEMA_TABLE_X = 2;
   private static final int PATTERN_SCHEMA_TABLE_CATALOG = 3;
   private static final int PATTERN_CATALOG_TABLE = 4;
   private static final int PATTERN_TABLE_CATALOG = 5;
   private static final int PATTERN_SCHEMA_TABLE = 6;
   private static final int PATTERN_TABLE = 7;
   private String qualifiedTableName = null;
   private DatabaseMetaDataHolder metaData = null;

   TableNameParser(String name, DatabaseMetaDataHolder metaDataHolder) throws ParseException {
      if (name != null && metaDataHolder != null) {
         this.qualifiedTableName = name.trim();
         this.metaData = metaDataHolder;
      } else {
         throw new ParseException("Invalid parameters, construction is failed.");
      }
   }

   String[] parse() throws ParseException {
      String[] parts = null;
      int patternType = this.getPatternType();
      switch (patternType) {
         case 1:
            parts = this.parsePatternCatalogSchemaTable();
            break;
         case 2:
            parts = this.parsePatternCatalogSchemaTableX();
            break;
         case 3:
            parts = this.parsePatternSchemaTableCatalog();
            break;
         case 4:
            parts = this.parsePatternCatalogTable();
            break;
         case 5:
            parts = this.parsePatternTableCatalog();
            break;
         case 6:
            parts = this.parsePatternSchemaTable();
            break;
         case 7:
            parts = this.parsePatternTable();
            break;
         default:
            throw new ParseException("Parse error, unknown pattern.");
      }

      for(int i = 0; i < parts.length; ++i) {
         if (this.metaData.supportsIdentifierQuoting() && isQuotedIdentifier(parts[i], this.metaData.getIdentifierQuoteString())) {
            if (this.metaData.storesUpperCaseQuotedIdentifiers()) {
               parts[i] = parts[i].toUpperCase();
            } else if (this.metaData.storesLowerCaseQuotedIdentifiers()) {
               parts[i] = parts[i].toLowerCase();
            }
         } else {
            if (!isValidIdentifier(parts[i])) {
               throw new ParseException("Parse error, identifier should not contain whitespace characters, expected <" + this.getPatternName(patternType) + ">, got <" + this.qualifiedTableName + ">");
            }

            if (this.metaData.storesUpperCaseIdentifiers()) {
               parts[i] = parts[i].toUpperCase();
            } else if (this.metaData.storesLowerCaseIdentifiers()) {
               parts[i] = parts[i].toLowerCase();
            }
         }
      }

      if (parts[0].length() > this.metaData.getMaxCatalogNameLength()) {
         throw new ParseException("Parse error, catalog name length must <= " + this.metaData.getMaxCatalogNameLength() + ".");
      } else if (parts[1].length() > this.metaData.getMaxSchemaNameLength()) {
         throw new ParseException("Parse error, schema name length must <= " + this.metaData.getMaxSchemaNameLength() + ".");
      } else if (parts[2].length() > this.metaData.getMaxTableNameLength()) {
         throw new ParseException("Parse error, table name length must <= " + this.metaData.getMaxTableNameLength() + ".");
      } else {
         return parts;
      }
   }

   private int getPatternType() {
      if (this.metaData.supportsCatalogsInDataManipulation()) {
         if (this.metaData.supportsSchemasInDataManipulation()) {
            if (".".equals(this.metaData.getCatalogSeparator())) {
               return 1;
            } else {
               return this.metaData.isCatalogAtStart() ? 2 : 3;
            }
         } else {
            return this.metaData.isCatalogAtStart() ? 4 : 5;
         }
      } else {
         return this.metaData.supportsSchemasInDataManipulation() ? 6 : 7;
      }
   }

   private String getPatternName(int patternType) {
      String message;
      switch (patternType) {
         case 1:
            message = "[[catalog.]schema.]table";
            break;
         case 2:
            message = "[[catalog" + this.metaData.getCatalogSeparator() + "]schema" + "." + "]table";
            break;
         case 3:
            message = "[schema.]table[" + this.metaData.getCatalogSeparator() + "catalog]";
            break;
         case 4:
            message = "[catalog" + this.metaData.getCatalogSeparator() + "]table";
            break;
         case 5:
            message = "table[" + this.metaData.getCatalogSeparator() + "catalog]";
            break;
         case 6:
            message = "[schema.]table";
            break;
         case 7:
            message = "table";
            break;
         default:
            message = "Unknown pattern";
      }

      return message;
   }

   private String[] parsePatternCatalogSchemaTable() throws ParseException {
      String[] parts = null;
      if (this.metaData.supportsIdentifierQuoting() && this.qualifiedTableName.contains(this.metaData.getIdentifierQuoteString())) {
         parts = parseQuotedFull(this.qualifiedTableName, ".", this.getPatternName(1), this.metaData.getIdentifierQuoteString());
      } else {
         parts = parseFull(this.qualifiedTableName, ".", this.getPatternName(1));
      }

      if (parts.length == 1) {
         return new String[]{"", "", parts[0]};
      } else {
         return parts.length == 2 ? new String[]{"", parts[0], parts[1]} : parts;
      }
   }

   private String[] parsePatternCatalogSchemaTableX() throws ParseException {
      String[] parts = null;
      String[] subParts = null;
      boolean containsQuoteString = false;
      if (this.metaData.supportsIdentifierQuoting() && this.qualifiedTableName.contains(this.metaData.getIdentifierQuoteString())) {
         containsQuoteString = true;
      }

      if (containsQuoteString) {
         parts = parseQuotedPart(this.qualifiedTableName, this.metaData.getCatalogSeparator(), this.getPatternName(2), this.metaData.getIdentifierQuoteString());
      } else {
         parts = parsePart(this.qualifiedTableName, this.metaData.getCatalogSeparator(), this.getPatternName(2));
      }

      if (parts.length == 1) {
         if (containsQuoteString) {
            subParts = parseQuotedPart(parts[0], ".", this.getPatternName(6), this.metaData.getIdentifierQuoteString());
         } else {
            subParts = parsePart(parts[0], ".", this.getPatternName(6));
         }

         return subParts.length == 1 ? new String[]{"", "", subParts[0]} : new String[]{"", subParts[0], subParts[1]};
      } else {
         if (containsQuoteString) {
            subParts = parseQuotedPart(parts[1], ".", this.getPatternName(6), this.metaData.getIdentifierQuoteString());
         } else {
            subParts = parsePart(parts[1], ".", this.getPatternName(6));
         }

         return subParts.length == 1 ? new String[]{parts[0], "", subParts[0]} : new String[]{parts[0], subParts[0], subParts[1]};
      }
   }

   private String[] parsePatternSchemaTableCatalog() throws ParseException {
      String[] parts = null;
      String[] subParts = null;
      if (this.metaData.supportsIdentifierQuoting() && this.qualifiedTableName.contains(this.metaData.getIdentifierQuoteString())) {
         parts = parseQuotedPart(this.qualifiedTableName, this.metaData.getCatalogSeparator(), this.getPatternName(3), this.metaData.getIdentifierQuoteString());
         subParts = parseQuotedPart(parts[0], ".", this.getPatternName(6), this.metaData.getIdentifierQuoteString());
      } else {
         parts = parsePart(this.qualifiedTableName, this.metaData.getCatalogSeparator(), this.getPatternName(3));
         subParts = parsePart(parts[0], ".", this.getPatternName(6));
      }

      if (parts.length == 1) {
         return subParts.length == 1 ? new String[]{"", "", subParts[0]} : new String[]{"", subParts[0], subParts[1]};
      } else {
         return subParts.length == 1 ? new String[]{parts[1], "", subParts[0]} : new String[]{parts[1], subParts[0], subParts[1]};
      }
   }

   private String[] parsePatternCatalogTable() throws ParseException {
      String[] parts = null;
      if (this.metaData.supportsIdentifierQuoting() && this.qualifiedTableName.contains(this.metaData.getIdentifierQuoteString())) {
         parts = parseQuotedPart(this.qualifiedTableName, this.metaData.getCatalogSeparator(), this.getPatternName(4), this.metaData.getIdentifierQuoteString());
      } else {
         parts = parsePart(this.qualifiedTableName, this.metaData.getCatalogSeparator(), this.getPatternName(4));
      }

      return parts.length == 1 ? new String[]{"", "", parts[0]} : new String[]{parts[0], "", parts[1]};
   }

   private String[] parsePatternTableCatalog() throws ParseException {
      String[] parts = null;
      if (this.metaData.supportsIdentifierQuoting() && this.qualifiedTableName.contains(this.metaData.getIdentifierQuoteString())) {
         parts = parseQuotedPart(this.qualifiedTableName, this.metaData.getCatalogSeparator(), this.getPatternName(5), this.metaData.getIdentifierQuoteString());
      } else {
         parts = parsePart(this.qualifiedTableName, this.metaData.getCatalogSeparator(), this.getPatternName(5));
      }

      return parts.length == 1 ? new String[]{"", "", parts[0]} : new String[]{parts[1], "", parts[0]};
   }

   private String[] parsePatternSchemaTable() throws ParseException {
      String[] parts = null;
      if (this.metaData.supportsIdentifierQuoting() && this.qualifiedTableName.contains(this.metaData.getIdentifierQuoteString())) {
         parts = parseQuotedPart(this.qualifiedTableName, ".", this.getPatternName(6), this.metaData.getIdentifierQuoteString());
      } else {
         parts = parsePart(this.qualifiedTableName, ".", this.getPatternName(6));
      }

      return parts.length == 1 ? new String[]{"", "", parts[0]} : new String[]{"", parts[0], parts[1]};
   }

   private String[] parsePatternTable() throws ParseException {
      if (this.qualifiedTableName != null && "".equals(this.qualifiedTableName) && (!this.metaData.supportsIdentifierQuoting() || (this.qualifiedTableName.contains(this.metaData.getIdentifierQuoteString()) || !this.qualifiedTableName.contains(".")) && (!this.qualifiedTableName.contains(this.metaData.getIdentifierQuoteString()) || isQuotedIdentifier(this.qualifiedTableName, this.metaData.getIdentifierQuoteString())))) {
         return new String[]{"", "", this.qualifiedTableName};
      } else {
         throw new ParseException("Parse error, expected <" + this.getPatternName(7) + ">, got <" + this.qualifiedTableName + ">");
      }
   }

   private static String[] parseFull(String name, String separator, String expected) throws ParseException {
      if (name != null && !"".equals(name)) {
         if (separator != null && !"".equals(separator)) {
            if (".".equals(separator)) {
               separator = "\\" + separator;
            }

            String[] parts = name.split(separator);
            if (parts.length != 0 && parts.length <= 3 && (parts.length < 1 || parts[0].length() != 0) && (parts.length < 2 || parts[1].length() != 0) && (parts.length < 3 || parts[2].length() != 0)) {
               return parts;
            } else {
               throw new ParseException("Parse error, expected <" + expected + ">, got <" + name + ">");
            }
         } else {
            throw new ParseException("Parse error, separator should not be empty.");
         }
      } else {
         throw new ParseException("Parse error, parsed string should not be empty.");
      }
   }

   private static String[] parseQuotedFull(String name, String separator, String expected, String identifierQuoteString) throws ParseException {
      if (name != null && !"".equals(name)) {
         if (separator != null && !"".equals(separator)) {
            String[] parts = split(name, separator, identifierQuoteString);
            if (parts.length != 0 && parts.length <= 3 && (parts.length < 1 || parts[0].length() != 0) && (parts.length < 2 || parts[1].length() != 0) && (parts.length < 3 || parts[2].length() != 0) && isQuotedIdentifiers(parts, identifierQuoteString)) {
               return parts;
            } else {
               throw new ParseException("Parse error, expected <" + expected + ">, got <" + name + ">");
            }
         } else {
            throw new ParseException("Parse error, separator should not be empty.");
         }
      } else {
         throw new ParseException("Parse error, parsed string should not be empty.");
      }
   }

   private static String[] parsePart(String name, String separator, String expected) throws ParseException {
      if (name != null && !"".equals(name)) {
         if (separator != null && !"".equals(separator)) {
            if (".".equals(separator)) {
               separator = "\\" + separator;
            }

            String[] parts = name.split(separator);
            if (parts.length == 0 || parts.length > 2 || parts.length >= 1 && parts[0].length() == 0 || parts.length >= 2 && parts[1].length() == 0) {
               throw new ParseException("Parse error, expected <" + expected + ">, got <" + name + ">");
            } else {
               return parts;
            }
         } else {
            throw new ParseException("Parse error, separator should not be empty.");
         }
      } else {
         throw new ParseException("Parse error, string to be parsed should not be empty.");
      }
   }

   private static String[] parseQuotedPart(String name, String separator, String expected, String identifierQuoteString) throws ParseException {
      if (name != null && !"".equals(name)) {
         if (separator != null && !"".equals(separator)) {
            String[] parts = split(name, separator, identifierQuoteString);
            if (parts.length != 0 && parts.length <= 2 && (parts.length < 1 || parts[0].length() != 0) && (parts.length < 2 || parts[1].length() != 0) && isQuotedIdentifiers(parts, identifierQuoteString)) {
               return parts;
            } else {
               throw new ParseException("Parse error, expected <" + expected + ">, got <" + name + ">");
            }
         } else {
            throw new ParseException("Parse error, separator should not be empty.");
         }
      } else {
         throw new ParseException("Parse error, string to be parsed should not be empty.");
      }
   }

   private static String[] split(String name, String separator, String identifierQuoteString) throws ParseException {
      Integer[] quoteStringPositions = getQuoteStringPositions(name, identifierQuoteString);
      if (quoteStringPositions.length % 2 == 0 && quoteStringPositions.length >= 2 && quoteStringPositions.length <= 6) {
         int previousPosition = -separator.length();
         ArrayList parts = new ArrayList();

         while(true) {
            int currentPosition;
            for(currentPosition = name.indexOf(separator, previousPosition + separator.length()); currentPosition != -1 && !isValidSeparator(separator, currentPosition, identifierQuoteString, quoteStringPositions); currentPosition = name.indexOf(separator, currentPosition + separator.length())) {
            }

            if (currentPosition == -1) {
               parts.add(name.substring(previousPosition + separator.length()));
               return (String[])parts.toArray(new String[parts.size()]);
            }

            parts.add(name.substring(previousPosition + separator.length(), currentPosition));
            previousPosition = currentPosition;
         }
      } else {
         throw new ParseException("Parse error, string to be parsed is invalid.");
      }
   }

   private static Integer[] getQuoteStringPositions(String name, String quoteString) {
      if (name != null && !"".equals(name) && quoteString != null && !"".equals(quoteString)) {
         int position = -quoteString.length();
         ArrayList positions = new ArrayList();

         while(true) {
            position = name.indexOf(quoteString, position + quoteString.length());
            if (position == -1) {
               return (Integer[])positions.toArray(new Integer[positions.size()]);
            }

            positions.add(position);
         }
      } else {
         return new Integer[0];
      }
   }

   private static boolean isValidSeparator(String separator, int sepratorPosition, String quoteString, Integer[] quoteStringPositions) {
      if (sepratorPosition + separator.length() > quoteStringPositions[0] && quoteStringPositions[quoteStringPositions.length - 1] + quoteString.length() > sepratorPosition) {
         for(int i = 0; i < quoteStringPositions.length; i += 2) {
            if (quoteStringPositions[i] + quoteString.length() <= sepratorPosition && sepratorPosition + separator.length() <= quoteStringPositions[i + 1]) {
               return false;
            }
         }

         return true;
      } else {
         return true;
      }
   }

   private static boolean isQuotedIdentifiers(String[] identifiers, String identifierQuoteString) {
      String[] var2 = identifiers;
      int var3 = identifiers.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         String identifier = var2[var4];
         if (isQuotedIdentifier(identifier, identifierQuoteString)) {
            return true;
         }
      }

      return false;
   }

   static boolean isQuotedIdentifier(String identifier, String identifierQuoteString) {
      return identifier.startsWith(identifierQuoteString) && identifier.endsWith(identifierQuoteString);
   }

   private static boolean isValidIdentifier(String identifier) {
      return !WHITESPACE_PATTERN.matcher(identifier).matches();
   }

   boolean identifierEqual(String columnIdentifier, String writeIdentifier) {
      if (writeIdentifier != null && !"".equals(writeIdentifier)) {
         if (columnIdentifier != null && !"".equals(columnIdentifier)) {
            if (this.metaData.supportsIdentifierQuoting()) {
               String identifierQuoteString = this.metaData.getIdentifierQuoteString();
               if (isQuotedIdentifier(writeIdentifier, identifierQuoteString)) {
                  writeIdentifier = writeIdentifier.replace(identifierQuoteString, "");
               }

               if (isQuotedIdentifier(columnIdentifier, identifierQuoteString)) {
                  columnIdentifier = columnIdentifier.replace(identifierQuoteString, "");
               }
            }

            return writeIdentifier.equals(columnIdentifier);
         } else {
            return false;
         }
      } else {
         return true;
      }
   }
}
