package com.oracle.xmlns.weblogic.persistenceConfiguration.impl;

import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlBoolean;
import com.bea.xml.XmlInt;
import com.bea.xml.XmlString;
import com.oracle.xmlns.weblogic.persistenceConfiguration.BuiltInDbdictionaryType;
import javax.xml.namespace.QName;

public class BuiltInDbdictionaryTypeImpl extends DbDictionaryTypeImpl implements BuiltInDbdictionaryType {
   private static final long serialVersionUID = 1L;
   private static final QName CHARTYPENAME$0 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "char-type-name");
   private static final QName OUTERJOINCLAUSE$2 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "outer-join-clause");
   private static final QName BINARYTYPENAME$4 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "binary-type-name");
   private static final QName CLOBTYPENAME$6 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "clob-type-name");
   private static final QName SUPPORTSLOCKINGWITHDISTINCTCLAUSE$8 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "supports-locking-with-distinct-clause");
   private static final QName SIMULATELOCKING$10 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "simulate-locking");
   private static final QName SYSTEMTABLES$12 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "system-tables");
   private static final QName CONCATENATEFUNCTION$14 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "concatenate-function");
   private static final QName SUBSTRINGFUNCTIONNAME$16 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "substring-function-name");
   private static final QName SUPPORTSQUERYTIMEOUT$18 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "supports-query-timeout");
   private static final QName USESETBYTESFORBLOBS$20 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "use-set-bytes-for-blobs");
   private static final QName MAXCONSTRAINTNAMELENGTH$22 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "max-constraint-name-length");
   private static final QName SEARCHSTRINGESCAPE$24 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "search-string-escape");
   private static final QName SUPPORTSCASCADEUPDATEACTION$26 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "supports-cascade-update-action");
   private static final QName STRINGLENGTHFUNCTION$28 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "string-length-function");
   private static final QName LONGVARBINARYTYPENAME$30 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "long-varbinary-type-name");
   private static final QName SUPPORTSUNIQUECONSTRAINTS$32 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "supports-unique-constraints");
   private static final QName SUPPORTSRESTRICTDELETEACTION$34 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "supports-restrict-delete-action");
   private static final QName TRIMLEADINGFUNCTION$36 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "trim-leading-function");
   private static final QName SUPPORTSDEFAULTDELETEACTION$38 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "supports-default-delete-action");
   private static final QName NEXTSEQUENCEQUERY$40 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "next-sequence-query");
   private static final QName LONGVARCHARTYPENAME$42 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "long-varchar-type-name");
   private static final QName CROSSJOINCLAUSE$44 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "cross-join-clause");
   private static final QName MAXEMBEDDEDCLOBSIZE$46 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "max-embedded-clob-size");
   private static final QName DATETYPENAME$48 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "date-type-name");
   private static final QName SUPPORTSSCHEMAFORGETTABLES$50 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "supports-schema-for-get-tables");
   private static final QName SUPPORTSALTERTABLEWITHDROPCOLUMN$52 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "supports-alter-table-with-drop-column");
   private static final QName CURRENTTIMEFUNCTION$54 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "current-time-function");
   private static final QName REQUIRESCONDITIONFORCROSSJOIN$56 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "requires-condition-for-cross-join");
   private static final QName REFTYPENAME$58 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "ref-type-name");
   private static final QName CONCATENATEDELIMITER$60 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "concatenate-delimiter");
   private static final QName CATALOGSEPARATOR$62 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "catalog-separator");
   private static final QName SUPPORTSMODOPERATOR$64 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "supports-mod-operator");
   private static final QName SCHEMACASE$66 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "schema-case");
   private static final QName JAVAOBJECTTYPENAME$68 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "java-object-type-name");
   private static final QName DRIVERVENDOR$70 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "driver-vendor");
   private static final QName SUPPORTSLOCKINGWITHMULTIPLETABLES$72 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "supports-locking-with-multiple-tables");
   private static final QName MAXCOLUMNNAMELENGTH$74 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "max-column-name-length");
   private static final QName DOUBLETYPENAME$76 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "double-type-name");
   private static final QName USEGETSTRINGFORCLOBS$78 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "use-get-string-for-clobs");
   private static final QName DECIMALTYPENAME$80 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "decimal-type-name");
   private static final QName SMALLINTTYPENAME$82 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "smallint-type-name");
   private static final QName DATEPRECISION$84 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "date-precision");
   private static final QName SUPPORTSALTERTABLEWITHADDCOLUMN$86 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "supports-alter-table-with-add-column");
   private static final QName BITTYPENAME$88 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "bit-type-name");
   private static final QName SUPPORTSNULLTABLEFORGETCOLUMNS$90 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "supports-null-table-for-get-columns");
   private static final QName TOUPPERCASEFUNCTION$92 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "to-upper-case-function");
   private static final QName SUPPORTSSELECTENDINDEX$94 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "supports-select-end-index");
   private static final QName SUPPORTSAUTOASSIGN$96 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "supports-auto-assign");
   private static final QName STORELARGENUMBERSASSTRINGS$98 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "store-large-numbers-as-strings");
   private static final QName CONSTRAINTNAMEMODE$100 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "constraint-name-mode");
   private static final QName ALLOWSALIASINBULKCLAUSE$102 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "allows-alias-in-bulk-clause");
   private static final QName SUPPORTSSELECTFORUPDATE$104 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "supports-select-for-update");
   private static final QName DISTINCTCOUNTCOLUMNSEPARATOR$106 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "distinct-count-column-separator");
   private static final QName SUPPORTSSUBSELECT$108 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "supports-subselect");
   private static final QName TIMETYPENAME$110 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "time-type-name");
   private static final QName AUTOASSIGNTYPENAME$112 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "auto-assign-type-name");
   private static final QName USEGETOBJECTFORBLOBS$114 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "use-get-object-for-blobs");
   private static final QName MAXAUTOASSIGNNAMELENGTH$116 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "max-auto-assign-name-length");
   private static final QName VALIDATIONSQL$118 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "validation-sql");
   private static final QName STRUCTTYPENAME$120 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "struct-type-name");
   private static final QName VARCHARTYPENAME$122 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "varchar-type-name");
   private static final QName RANGEPOSITION$124 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "range-position");
   private static final QName SUPPORTSRESTRICTUPDATEACTION$126 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "supports-restrict-update-action");
   private static final QName AUTOASSIGNCLAUSE$128 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "auto-assign-clause");
   private static final QName SUPPORTSMULTIPLENONTRANSACTIONALRESULTSETS$130 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "supports-multiple-nontransactional-result-sets");
   private static final QName BITLENGTHFUNCTION$132 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "bit-length-function");
   private static final QName CREATEPRIMARYKEYS$134 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "create-primary-keys");
   private static final QName NULLTYPENAME$136 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "null-type-name");
   private static final QName FLOATTYPENAME$138 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "float-type-name");
   private static final QName USEGETBYTESFORBLOBS$140 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "use-get-bytes-for-blobs");
   private static final QName TABLETYPES$142 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "table-types");
   private static final QName NUMERICTYPENAME$144 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "numeric-type-name");
   private static final QName TABLEFORUPDATECLAUSE$146 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "table-for-update-clause");
   private static final QName INTEGERTYPENAME$148 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "integer-type-name");
   private static final QName BLOBTYPENAME$150 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "blob-type-name");
   private static final QName FORUPDATECLAUSE$152 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "for-update-clause");
   private static final QName BOOLEANTYPENAME$154 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "boolean-type-name");
   private static final QName USEGETBESTROWIDENTIFIERFORPRIMARYKEYS$156 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "use-get-best-row-identifier-for-primary-keys");
   private static final QName SUPPORTSFOREIGNKEYS$158 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "supports-foreign-keys");
   private static final QName DROPTABLESQL$160 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "drop-table-sql");
   private static final QName USESETSTRINGFORCLOBS$162 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "use-set-string-for-clobs");
   private static final QName SUPPORTSLOCKINGWITHORDERCLAUSE$164 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "supports-locking-with-order-clause");
   private static final QName PLATFORM$166 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "platform");
   private static final QName FIXEDSIZETYPENAMES$168 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "fixed-size-type-names");
   private static final QName STORECHARSASNUMBERS$170 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "store-chars-as-numbers");
   private static final QName MAXINDEXESPERTABLE$172 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "max-indexes-per-table");
   private static final QName REQUIRESCASTFORCOMPARISONS$174 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "requires-cast-for-comparisons");
   private static final QName SUPPORTSHAVING$176 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "supports-having");
   private static final QName SUPPORTSLOCKINGWITHOUTERJOIN$178 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "supports-locking-with-outer-join");
   private static final QName SUPPORTSCORRELATEDSUBSELECT$180 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "supports-correlated-subselect");
   private static final QName SUPPORTSNULLTABLEFORGETIMPORTEDKEYS$182 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "supports-null-table-for-get-imported-keys");
   private static final QName BIGINTTYPENAME$184 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "bigint-type-name");
   private static final QName LASTGENERATEDKEYQUERY$186 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "last-generated-key-query");
   private static final QName RESERVEDWORDS$188 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "reserved-words");
   private static final QName SUPPORTSNULLUPDATEACTION$190 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "supports-null-update-action");
   private static final QName USESCHEMANAME$192 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "use-schema-name");
   private static final QName SUPPORTSDEFERREDCONSTRAINTS$194 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "supports-deferred-constraints");
   private static final QName REALTYPENAME$196 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "real-type-name");
   private static final QName REQUIRESALIASFORSUBSELECT$198 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "requires-alias-for-subselect");
   private static final QName SUPPORTSNULLTABLEFORGETINDEXINFO$200 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "supports-null-table-for-get-index-info");
   private static final QName TRIMTRAILINGFUNCTION$202 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "trim-trailing-function");
   private static final QName SUPPORTSLOCKINGWITHSELECTRANGE$204 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "supports-locking-with-select-range");
   private static final QName STORAGELIMITATIONSFATAL$206 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "storage-limitations-fatal");
   private static final QName SUPPORTSLOCKINGWITHINNERJOIN$208 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "supports-locking-with-inner-join");
   private static final QName CURRENTTIMESTAMPFUNCTION$210 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "current-timestamp-function");
   private static final QName CASTFUNCTION$212 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "cast-function");
   private static final QName OTHERTYPENAME$214 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "other-type-name");
   private static final QName MAXINDEXNAMELENGTH$216 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "max-index-name-length");
   private static final QName DISTINCTTYPENAME$218 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "distinct-type-name");
   private static final QName CHARACTERCOLUMNSIZE$220 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "character-column-size");
   private static final QName VARBINARYTYPENAME$222 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "varbinary-type-name");
   private static final QName MAXTABLENAMELENGTH$224 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "max-table-name-length");
   private static final QName CLOSEPOOLSQL$226 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "close-pool-sql");
   private static final QName CURRENTDATEFUNCTION$228 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "current-date-function");
   private static final QName JOINSYNTAX$230 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "join-syntax");
   private static final QName MAXEMBEDDEDBLOBSIZE$232 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "max-embedded-blob-size");
   private static final QName TRIMBOTHFUNCTION$234 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "trim-both-function");
   private static final QName SUPPORTSSELECTSTARTINDEX$236 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "supports-select-start-index");
   private static final QName TOLOWERCASEFUNCTION$238 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "to-lower-case-function");
   private static final QName ARRAYTYPENAME$240 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "array-type-name");
   private static final QName INNERJOINCLAUSE$242 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "inner-join-clause");
   private static final QName SUPPORTSDEFAULTUPDATEACTION$244 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "supports-default-update-action");
   private static final QName SUPPORTSSCHEMAFORGETCOLUMNS$246 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "supports-schema-for-get-columns");
   private static final QName TINYINTTYPENAME$248 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "tinyint-type-name");
   private static final QName SUPPORTSNULLTABLEFORGETPRIMARYKEYS$250 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "supports-null-table-for-get-primary-keys");
   private static final QName SYSTEMSCHEMAS$252 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "system-schemas");
   private static final QName REQUIRESCASTFORMATHFUNCTIONS$254 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "requires-cast-for-math-functions");
   private static final QName SUPPORTSNULLDELETEACTION$256 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "supports-null-delete-action");
   private static final QName REQUIRESAUTOCOMMITFORMETADATA$258 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "requires-auto-commit-for-meta-data");
   private static final QName TIMESTAMPTYPENAME$260 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "timestamp-type-name");
   private static final QName INITIALIZATIONSQL$262 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "initialization-sql");
   private static final QName SUPPORTSCASCADEDELETEACTION$264 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "supports-cascade-delete-action");
   private static final QName SUPPORTSTIMESTAMPNANOS$266 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "supports-timestamp-nanos");

   public BuiltInDbdictionaryTypeImpl(SchemaType sType) {
      super(sType);
   }

   public String getCharTypeName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(CHARTYPENAME$0, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetCharTypeName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(CHARTYPENAME$0, 0);
         return target;
      }
   }

   public boolean isNilCharTypeName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(CHARTYPENAME$0, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetCharTypeName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(CHARTYPENAME$0) != 0;
      }
   }

   public void setCharTypeName(String charTypeName) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(CHARTYPENAME$0, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(CHARTYPENAME$0);
         }

         target.setStringValue(charTypeName);
      }
   }

   public void xsetCharTypeName(XmlString charTypeName) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(CHARTYPENAME$0, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(CHARTYPENAME$0);
         }

         target.set(charTypeName);
      }
   }

   public void setNilCharTypeName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(CHARTYPENAME$0, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(CHARTYPENAME$0);
         }

         target.setNil();
      }
   }

   public void unsetCharTypeName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(CHARTYPENAME$0, 0);
      }
   }

   public String getOuterJoinClause() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(OUTERJOINCLAUSE$2, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetOuterJoinClause() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(OUTERJOINCLAUSE$2, 0);
         return target;
      }
   }

   public boolean isNilOuterJoinClause() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(OUTERJOINCLAUSE$2, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetOuterJoinClause() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(OUTERJOINCLAUSE$2) != 0;
      }
   }

   public void setOuterJoinClause(String outerJoinClause) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(OUTERJOINCLAUSE$2, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(OUTERJOINCLAUSE$2);
         }

         target.setStringValue(outerJoinClause);
      }
   }

   public void xsetOuterJoinClause(XmlString outerJoinClause) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(OUTERJOINCLAUSE$2, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(OUTERJOINCLAUSE$2);
         }

         target.set(outerJoinClause);
      }
   }

   public void setNilOuterJoinClause() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(OUTERJOINCLAUSE$2, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(OUTERJOINCLAUSE$2);
         }

         target.setNil();
      }
   }

   public void unsetOuterJoinClause() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(OUTERJOINCLAUSE$2, 0);
      }
   }

   public String getBinaryTypeName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(BINARYTYPENAME$4, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetBinaryTypeName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(BINARYTYPENAME$4, 0);
         return target;
      }
   }

   public boolean isNilBinaryTypeName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(BINARYTYPENAME$4, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetBinaryTypeName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(BINARYTYPENAME$4) != 0;
      }
   }

   public void setBinaryTypeName(String binaryTypeName) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(BINARYTYPENAME$4, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(BINARYTYPENAME$4);
         }

         target.setStringValue(binaryTypeName);
      }
   }

   public void xsetBinaryTypeName(XmlString binaryTypeName) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(BINARYTYPENAME$4, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(BINARYTYPENAME$4);
         }

         target.set(binaryTypeName);
      }
   }

   public void setNilBinaryTypeName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(BINARYTYPENAME$4, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(BINARYTYPENAME$4);
         }

         target.setNil();
      }
   }

   public void unsetBinaryTypeName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(BINARYTYPENAME$4, 0);
      }
   }

   public String getClobTypeName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(CLOBTYPENAME$6, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetClobTypeName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(CLOBTYPENAME$6, 0);
         return target;
      }
   }

   public boolean isNilClobTypeName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(CLOBTYPENAME$6, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetClobTypeName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(CLOBTYPENAME$6) != 0;
      }
   }

   public void setClobTypeName(String clobTypeName) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(CLOBTYPENAME$6, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(CLOBTYPENAME$6);
         }

         target.setStringValue(clobTypeName);
      }
   }

   public void xsetClobTypeName(XmlString clobTypeName) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(CLOBTYPENAME$6, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(CLOBTYPENAME$6);
         }

         target.set(clobTypeName);
      }
   }

   public void setNilClobTypeName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(CLOBTYPENAME$6, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(CLOBTYPENAME$6);
         }

         target.setNil();
      }
   }

   public void unsetClobTypeName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(CLOBTYPENAME$6, 0);
      }
   }

   public boolean getSupportsLockingWithDistinctClause() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(SUPPORTSLOCKINGWITHDISTINCTCLAUSE$8, 0);
         return target == null ? false : target.getBooleanValue();
      }
   }

   public XmlBoolean xgetSupportsLockingWithDistinctClause() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(SUPPORTSLOCKINGWITHDISTINCTCLAUSE$8, 0);
         return target;
      }
   }

   public boolean isSetSupportsLockingWithDistinctClause() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(SUPPORTSLOCKINGWITHDISTINCTCLAUSE$8) != 0;
      }
   }

   public void setSupportsLockingWithDistinctClause(boolean supportsLockingWithDistinctClause) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(SUPPORTSLOCKINGWITHDISTINCTCLAUSE$8, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(SUPPORTSLOCKINGWITHDISTINCTCLAUSE$8);
         }

         target.setBooleanValue(supportsLockingWithDistinctClause);
      }
   }

   public void xsetSupportsLockingWithDistinctClause(XmlBoolean supportsLockingWithDistinctClause) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(SUPPORTSLOCKINGWITHDISTINCTCLAUSE$8, 0);
         if (target == null) {
            target = (XmlBoolean)this.get_store().add_element_user(SUPPORTSLOCKINGWITHDISTINCTCLAUSE$8);
         }

         target.set(supportsLockingWithDistinctClause);
      }
   }

   public void unsetSupportsLockingWithDistinctClause() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(SUPPORTSLOCKINGWITHDISTINCTCLAUSE$8, 0);
      }
   }

   public boolean getSimulateLocking() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(SIMULATELOCKING$10, 0);
         return target == null ? false : target.getBooleanValue();
      }
   }

   public XmlBoolean xgetSimulateLocking() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(SIMULATELOCKING$10, 0);
         return target;
      }
   }

   public boolean isSetSimulateLocking() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(SIMULATELOCKING$10) != 0;
      }
   }

   public void setSimulateLocking(boolean simulateLocking) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(SIMULATELOCKING$10, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(SIMULATELOCKING$10);
         }

         target.setBooleanValue(simulateLocking);
      }
   }

   public void xsetSimulateLocking(XmlBoolean simulateLocking) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(SIMULATELOCKING$10, 0);
         if (target == null) {
            target = (XmlBoolean)this.get_store().add_element_user(SIMULATELOCKING$10);
         }

         target.set(simulateLocking);
      }
   }

   public void unsetSimulateLocking() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(SIMULATELOCKING$10, 0);
      }
   }

   public String getSystemTables() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(SYSTEMTABLES$12, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetSystemTables() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(SYSTEMTABLES$12, 0);
         return target;
      }
   }

   public boolean isNilSystemTables() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(SYSTEMTABLES$12, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetSystemTables() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(SYSTEMTABLES$12) != 0;
      }
   }

   public void setSystemTables(String systemTables) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(SYSTEMTABLES$12, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(SYSTEMTABLES$12);
         }

         target.setStringValue(systemTables);
      }
   }

   public void xsetSystemTables(XmlString systemTables) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(SYSTEMTABLES$12, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(SYSTEMTABLES$12);
         }

         target.set(systemTables);
      }
   }

   public void setNilSystemTables() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(SYSTEMTABLES$12, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(SYSTEMTABLES$12);
         }

         target.setNil();
      }
   }

   public void unsetSystemTables() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(SYSTEMTABLES$12, 0);
      }
   }

   public String getConcatenateFunction() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(CONCATENATEFUNCTION$14, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetConcatenateFunction() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(CONCATENATEFUNCTION$14, 0);
         return target;
      }
   }

   public boolean isNilConcatenateFunction() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(CONCATENATEFUNCTION$14, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetConcatenateFunction() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(CONCATENATEFUNCTION$14) != 0;
      }
   }

   public void setConcatenateFunction(String concatenateFunction) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(CONCATENATEFUNCTION$14, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(CONCATENATEFUNCTION$14);
         }

         target.setStringValue(concatenateFunction);
      }
   }

   public void xsetConcatenateFunction(XmlString concatenateFunction) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(CONCATENATEFUNCTION$14, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(CONCATENATEFUNCTION$14);
         }

         target.set(concatenateFunction);
      }
   }

   public void setNilConcatenateFunction() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(CONCATENATEFUNCTION$14, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(CONCATENATEFUNCTION$14);
         }

         target.setNil();
      }
   }

   public void unsetConcatenateFunction() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(CONCATENATEFUNCTION$14, 0);
      }
   }

   public String getSubstringFunctionName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(SUBSTRINGFUNCTIONNAME$16, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetSubstringFunctionName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(SUBSTRINGFUNCTIONNAME$16, 0);
         return target;
      }
   }

   public boolean isNilSubstringFunctionName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(SUBSTRINGFUNCTIONNAME$16, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetSubstringFunctionName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(SUBSTRINGFUNCTIONNAME$16) != 0;
      }
   }

   public void setSubstringFunctionName(String substringFunctionName) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(SUBSTRINGFUNCTIONNAME$16, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(SUBSTRINGFUNCTIONNAME$16);
         }

         target.setStringValue(substringFunctionName);
      }
   }

   public void xsetSubstringFunctionName(XmlString substringFunctionName) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(SUBSTRINGFUNCTIONNAME$16, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(SUBSTRINGFUNCTIONNAME$16);
         }

         target.set(substringFunctionName);
      }
   }

   public void setNilSubstringFunctionName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(SUBSTRINGFUNCTIONNAME$16, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(SUBSTRINGFUNCTIONNAME$16);
         }

         target.setNil();
      }
   }

   public void unsetSubstringFunctionName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(SUBSTRINGFUNCTIONNAME$16, 0);
      }
   }

   public boolean getSupportsQueryTimeout() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(SUPPORTSQUERYTIMEOUT$18, 0);
         return target == null ? false : target.getBooleanValue();
      }
   }

   public XmlBoolean xgetSupportsQueryTimeout() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(SUPPORTSQUERYTIMEOUT$18, 0);
         return target;
      }
   }

   public boolean isSetSupportsQueryTimeout() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(SUPPORTSQUERYTIMEOUT$18) != 0;
      }
   }

   public void setSupportsQueryTimeout(boolean supportsQueryTimeout) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(SUPPORTSQUERYTIMEOUT$18, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(SUPPORTSQUERYTIMEOUT$18);
         }

         target.setBooleanValue(supportsQueryTimeout);
      }
   }

   public void xsetSupportsQueryTimeout(XmlBoolean supportsQueryTimeout) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(SUPPORTSQUERYTIMEOUT$18, 0);
         if (target == null) {
            target = (XmlBoolean)this.get_store().add_element_user(SUPPORTSQUERYTIMEOUT$18);
         }

         target.set(supportsQueryTimeout);
      }
   }

   public void unsetSupportsQueryTimeout() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(SUPPORTSQUERYTIMEOUT$18, 0);
      }
   }

   public boolean getUseSetBytesForBlobs() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(USESETBYTESFORBLOBS$20, 0);
         return target == null ? false : target.getBooleanValue();
      }
   }

   public XmlBoolean xgetUseSetBytesForBlobs() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(USESETBYTESFORBLOBS$20, 0);
         return target;
      }
   }

   public boolean isSetUseSetBytesForBlobs() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(USESETBYTESFORBLOBS$20) != 0;
      }
   }

   public void setUseSetBytesForBlobs(boolean useSetBytesForBlobs) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(USESETBYTESFORBLOBS$20, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(USESETBYTESFORBLOBS$20);
         }

         target.setBooleanValue(useSetBytesForBlobs);
      }
   }

   public void xsetUseSetBytesForBlobs(XmlBoolean useSetBytesForBlobs) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(USESETBYTESFORBLOBS$20, 0);
         if (target == null) {
            target = (XmlBoolean)this.get_store().add_element_user(USESETBYTESFORBLOBS$20);
         }

         target.set(useSetBytesForBlobs);
      }
   }

   public void unsetUseSetBytesForBlobs() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(USESETBYTESFORBLOBS$20, 0);
      }
   }

   public int getMaxConstraintNameLength() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(MAXCONSTRAINTNAMELENGTH$22, 0);
         return target == null ? 0 : target.getIntValue();
      }
   }

   public XmlInt xgetMaxConstraintNameLength() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlInt target = null;
         target = (XmlInt)this.get_store().find_element_user(MAXCONSTRAINTNAMELENGTH$22, 0);
         return target;
      }
   }

   public boolean isSetMaxConstraintNameLength() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(MAXCONSTRAINTNAMELENGTH$22) != 0;
      }
   }

   public void setMaxConstraintNameLength(int maxConstraintNameLength) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(MAXCONSTRAINTNAMELENGTH$22, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(MAXCONSTRAINTNAMELENGTH$22);
         }

         target.setIntValue(maxConstraintNameLength);
      }
   }

   public void xsetMaxConstraintNameLength(XmlInt maxConstraintNameLength) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlInt target = null;
         target = (XmlInt)this.get_store().find_element_user(MAXCONSTRAINTNAMELENGTH$22, 0);
         if (target == null) {
            target = (XmlInt)this.get_store().add_element_user(MAXCONSTRAINTNAMELENGTH$22);
         }

         target.set(maxConstraintNameLength);
      }
   }

   public void unsetMaxConstraintNameLength() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(MAXCONSTRAINTNAMELENGTH$22, 0);
      }
   }

   public String getSearchStringEscape() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(SEARCHSTRINGESCAPE$24, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetSearchStringEscape() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(SEARCHSTRINGESCAPE$24, 0);
         return target;
      }
   }

   public boolean isNilSearchStringEscape() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(SEARCHSTRINGESCAPE$24, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetSearchStringEscape() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(SEARCHSTRINGESCAPE$24) != 0;
      }
   }

   public void setSearchStringEscape(String searchStringEscape) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(SEARCHSTRINGESCAPE$24, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(SEARCHSTRINGESCAPE$24);
         }

         target.setStringValue(searchStringEscape);
      }
   }

   public void xsetSearchStringEscape(XmlString searchStringEscape) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(SEARCHSTRINGESCAPE$24, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(SEARCHSTRINGESCAPE$24);
         }

         target.set(searchStringEscape);
      }
   }

   public void setNilSearchStringEscape() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(SEARCHSTRINGESCAPE$24, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(SEARCHSTRINGESCAPE$24);
         }

         target.setNil();
      }
   }

   public void unsetSearchStringEscape() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(SEARCHSTRINGESCAPE$24, 0);
      }
   }

   public boolean getSupportsCascadeUpdateAction() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(SUPPORTSCASCADEUPDATEACTION$26, 0);
         return target == null ? false : target.getBooleanValue();
      }
   }

   public XmlBoolean xgetSupportsCascadeUpdateAction() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(SUPPORTSCASCADEUPDATEACTION$26, 0);
         return target;
      }
   }

   public boolean isSetSupportsCascadeUpdateAction() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(SUPPORTSCASCADEUPDATEACTION$26) != 0;
      }
   }

   public void setSupportsCascadeUpdateAction(boolean supportsCascadeUpdateAction) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(SUPPORTSCASCADEUPDATEACTION$26, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(SUPPORTSCASCADEUPDATEACTION$26);
         }

         target.setBooleanValue(supportsCascadeUpdateAction);
      }
   }

   public void xsetSupportsCascadeUpdateAction(XmlBoolean supportsCascadeUpdateAction) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(SUPPORTSCASCADEUPDATEACTION$26, 0);
         if (target == null) {
            target = (XmlBoolean)this.get_store().add_element_user(SUPPORTSCASCADEUPDATEACTION$26);
         }

         target.set(supportsCascadeUpdateAction);
      }
   }

   public void unsetSupportsCascadeUpdateAction() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(SUPPORTSCASCADEUPDATEACTION$26, 0);
      }
   }

   public String getStringLengthFunction() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(STRINGLENGTHFUNCTION$28, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetStringLengthFunction() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(STRINGLENGTHFUNCTION$28, 0);
         return target;
      }
   }

   public boolean isNilStringLengthFunction() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(STRINGLENGTHFUNCTION$28, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetStringLengthFunction() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(STRINGLENGTHFUNCTION$28) != 0;
      }
   }

   public void setStringLengthFunction(String stringLengthFunction) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(STRINGLENGTHFUNCTION$28, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(STRINGLENGTHFUNCTION$28);
         }

         target.setStringValue(stringLengthFunction);
      }
   }

   public void xsetStringLengthFunction(XmlString stringLengthFunction) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(STRINGLENGTHFUNCTION$28, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(STRINGLENGTHFUNCTION$28);
         }

         target.set(stringLengthFunction);
      }
   }

   public void setNilStringLengthFunction() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(STRINGLENGTHFUNCTION$28, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(STRINGLENGTHFUNCTION$28);
         }

         target.setNil();
      }
   }

   public void unsetStringLengthFunction() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(STRINGLENGTHFUNCTION$28, 0);
      }
   }

   public String getLongVarbinaryTypeName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(LONGVARBINARYTYPENAME$30, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetLongVarbinaryTypeName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(LONGVARBINARYTYPENAME$30, 0);
         return target;
      }
   }

   public boolean isNilLongVarbinaryTypeName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(LONGVARBINARYTYPENAME$30, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetLongVarbinaryTypeName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(LONGVARBINARYTYPENAME$30) != 0;
      }
   }

   public void setLongVarbinaryTypeName(String longVarbinaryTypeName) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(LONGVARBINARYTYPENAME$30, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(LONGVARBINARYTYPENAME$30);
         }

         target.setStringValue(longVarbinaryTypeName);
      }
   }

   public void xsetLongVarbinaryTypeName(XmlString longVarbinaryTypeName) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(LONGVARBINARYTYPENAME$30, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(LONGVARBINARYTYPENAME$30);
         }

         target.set(longVarbinaryTypeName);
      }
   }

   public void setNilLongVarbinaryTypeName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(LONGVARBINARYTYPENAME$30, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(LONGVARBINARYTYPENAME$30);
         }

         target.setNil();
      }
   }

   public void unsetLongVarbinaryTypeName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(LONGVARBINARYTYPENAME$30, 0);
      }
   }

   public boolean getSupportsUniqueConstraints() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(SUPPORTSUNIQUECONSTRAINTS$32, 0);
         return target == null ? false : target.getBooleanValue();
      }
   }

   public XmlBoolean xgetSupportsUniqueConstraints() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(SUPPORTSUNIQUECONSTRAINTS$32, 0);
         return target;
      }
   }

   public boolean isSetSupportsUniqueConstraints() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(SUPPORTSUNIQUECONSTRAINTS$32) != 0;
      }
   }

   public void setSupportsUniqueConstraints(boolean supportsUniqueConstraints) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(SUPPORTSUNIQUECONSTRAINTS$32, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(SUPPORTSUNIQUECONSTRAINTS$32);
         }

         target.setBooleanValue(supportsUniqueConstraints);
      }
   }

   public void xsetSupportsUniqueConstraints(XmlBoolean supportsUniqueConstraints) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(SUPPORTSUNIQUECONSTRAINTS$32, 0);
         if (target == null) {
            target = (XmlBoolean)this.get_store().add_element_user(SUPPORTSUNIQUECONSTRAINTS$32);
         }

         target.set(supportsUniqueConstraints);
      }
   }

   public void unsetSupportsUniqueConstraints() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(SUPPORTSUNIQUECONSTRAINTS$32, 0);
      }
   }

   public boolean getSupportsRestrictDeleteAction() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(SUPPORTSRESTRICTDELETEACTION$34, 0);
         return target == null ? false : target.getBooleanValue();
      }
   }

   public XmlBoolean xgetSupportsRestrictDeleteAction() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(SUPPORTSRESTRICTDELETEACTION$34, 0);
         return target;
      }
   }

   public boolean isSetSupportsRestrictDeleteAction() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(SUPPORTSRESTRICTDELETEACTION$34) != 0;
      }
   }

   public void setSupportsRestrictDeleteAction(boolean supportsRestrictDeleteAction) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(SUPPORTSRESTRICTDELETEACTION$34, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(SUPPORTSRESTRICTDELETEACTION$34);
         }

         target.setBooleanValue(supportsRestrictDeleteAction);
      }
   }

   public void xsetSupportsRestrictDeleteAction(XmlBoolean supportsRestrictDeleteAction) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(SUPPORTSRESTRICTDELETEACTION$34, 0);
         if (target == null) {
            target = (XmlBoolean)this.get_store().add_element_user(SUPPORTSRESTRICTDELETEACTION$34);
         }

         target.set(supportsRestrictDeleteAction);
      }
   }

   public void unsetSupportsRestrictDeleteAction() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(SUPPORTSRESTRICTDELETEACTION$34, 0);
      }
   }

   public String getTrimLeadingFunction() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(TRIMLEADINGFUNCTION$36, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetTrimLeadingFunction() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(TRIMLEADINGFUNCTION$36, 0);
         return target;
      }
   }

   public boolean isNilTrimLeadingFunction() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(TRIMLEADINGFUNCTION$36, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetTrimLeadingFunction() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(TRIMLEADINGFUNCTION$36) != 0;
      }
   }

   public void setTrimLeadingFunction(String trimLeadingFunction) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(TRIMLEADINGFUNCTION$36, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(TRIMLEADINGFUNCTION$36);
         }

         target.setStringValue(trimLeadingFunction);
      }
   }

   public void xsetTrimLeadingFunction(XmlString trimLeadingFunction) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(TRIMLEADINGFUNCTION$36, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(TRIMLEADINGFUNCTION$36);
         }

         target.set(trimLeadingFunction);
      }
   }

   public void setNilTrimLeadingFunction() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(TRIMLEADINGFUNCTION$36, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(TRIMLEADINGFUNCTION$36);
         }

         target.setNil();
      }
   }

   public void unsetTrimLeadingFunction() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(TRIMLEADINGFUNCTION$36, 0);
      }
   }

   public boolean getSupportsDefaultDeleteAction() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(SUPPORTSDEFAULTDELETEACTION$38, 0);
         return target == null ? false : target.getBooleanValue();
      }
   }

   public XmlBoolean xgetSupportsDefaultDeleteAction() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(SUPPORTSDEFAULTDELETEACTION$38, 0);
         return target;
      }
   }

   public boolean isSetSupportsDefaultDeleteAction() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(SUPPORTSDEFAULTDELETEACTION$38) != 0;
      }
   }

   public void setSupportsDefaultDeleteAction(boolean supportsDefaultDeleteAction) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(SUPPORTSDEFAULTDELETEACTION$38, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(SUPPORTSDEFAULTDELETEACTION$38);
         }

         target.setBooleanValue(supportsDefaultDeleteAction);
      }
   }

   public void xsetSupportsDefaultDeleteAction(XmlBoolean supportsDefaultDeleteAction) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(SUPPORTSDEFAULTDELETEACTION$38, 0);
         if (target == null) {
            target = (XmlBoolean)this.get_store().add_element_user(SUPPORTSDEFAULTDELETEACTION$38);
         }

         target.set(supportsDefaultDeleteAction);
      }
   }

   public void unsetSupportsDefaultDeleteAction() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(SUPPORTSDEFAULTDELETEACTION$38, 0);
      }
   }

   public String getNextSequenceQuery() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(NEXTSEQUENCEQUERY$40, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetNextSequenceQuery() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(NEXTSEQUENCEQUERY$40, 0);
         return target;
      }
   }

   public boolean isNilNextSequenceQuery() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(NEXTSEQUENCEQUERY$40, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetNextSequenceQuery() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(NEXTSEQUENCEQUERY$40) != 0;
      }
   }

   public void setNextSequenceQuery(String nextSequenceQuery) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(NEXTSEQUENCEQUERY$40, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(NEXTSEQUENCEQUERY$40);
         }

         target.setStringValue(nextSequenceQuery);
      }
   }

   public void xsetNextSequenceQuery(XmlString nextSequenceQuery) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(NEXTSEQUENCEQUERY$40, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(NEXTSEQUENCEQUERY$40);
         }

         target.set(nextSequenceQuery);
      }
   }

   public void setNilNextSequenceQuery() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(NEXTSEQUENCEQUERY$40, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(NEXTSEQUENCEQUERY$40);
         }

         target.setNil();
      }
   }

   public void unsetNextSequenceQuery() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(NEXTSEQUENCEQUERY$40, 0);
      }
   }

   public String getLongVarcharTypeName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(LONGVARCHARTYPENAME$42, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetLongVarcharTypeName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(LONGVARCHARTYPENAME$42, 0);
         return target;
      }
   }

   public boolean isNilLongVarcharTypeName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(LONGVARCHARTYPENAME$42, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetLongVarcharTypeName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(LONGVARCHARTYPENAME$42) != 0;
      }
   }

   public void setLongVarcharTypeName(String longVarcharTypeName) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(LONGVARCHARTYPENAME$42, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(LONGVARCHARTYPENAME$42);
         }

         target.setStringValue(longVarcharTypeName);
      }
   }

   public void xsetLongVarcharTypeName(XmlString longVarcharTypeName) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(LONGVARCHARTYPENAME$42, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(LONGVARCHARTYPENAME$42);
         }

         target.set(longVarcharTypeName);
      }
   }

   public void setNilLongVarcharTypeName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(LONGVARCHARTYPENAME$42, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(LONGVARCHARTYPENAME$42);
         }

         target.setNil();
      }
   }

   public void unsetLongVarcharTypeName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(LONGVARCHARTYPENAME$42, 0);
      }
   }

   public String getCrossJoinClause() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(CROSSJOINCLAUSE$44, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetCrossJoinClause() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(CROSSJOINCLAUSE$44, 0);
         return target;
      }
   }

   public boolean isNilCrossJoinClause() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(CROSSJOINCLAUSE$44, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetCrossJoinClause() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(CROSSJOINCLAUSE$44) != 0;
      }
   }

   public void setCrossJoinClause(String crossJoinClause) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(CROSSJOINCLAUSE$44, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(CROSSJOINCLAUSE$44);
         }

         target.setStringValue(crossJoinClause);
      }
   }

   public void xsetCrossJoinClause(XmlString crossJoinClause) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(CROSSJOINCLAUSE$44, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(CROSSJOINCLAUSE$44);
         }

         target.set(crossJoinClause);
      }
   }

   public void setNilCrossJoinClause() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(CROSSJOINCLAUSE$44, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(CROSSJOINCLAUSE$44);
         }

         target.setNil();
      }
   }

   public void unsetCrossJoinClause() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(CROSSJOINCLAUSE$44, 0);
      }
   }

   public int getMaxEmbeddedClobSize() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(MAXEMBEDDEDCLOBSIZE$46, 0);
         return target == null ? 0 : target.getIntValue();
      }
   }

   public XmlInt xgetMaxEmbeddedClobSize() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlInt target = null;
         target = (XmlInt)this.get_store().find_element_user(MAXEMBEDDEDCLOBSIZE$46, 0);
         return target;
      }
   }

   public boolean isSetMaxEmbeddedClobSize() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(MAXEMBEDDEDCLOBSIZE$46) != 0;
      }
   }

   public void setMaxEmbeddedClobSize(int maxEmbeddedClobSize) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(MAXEMBEDDEDCLOBSIZE$46, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(MAXEMBEDDEDCLOBSIZE$46);
         }

         target.setIntValue(maxEmbeddedClobSize);
      }
   }

   public void xsetMaxEmbeddedClobSize(XmlInt maxEmbeddedClobSize) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlInt target = null;
         target = (XmlInt)this.get_store().find_element_user(MAXEMBEDDEDCLOBSIZE$46, 0);
         if (target == null) {
            target = (XmlInt)this.get_store().add_element_user(MAXEMBEDDEDCLOBSIZE$46);
         }

         target.set(maxEmbeddedClobSize);
      }
   }

   public void unsetMaxEmbeddedClobSize() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(MAXEMBEDDEDCLOBSIZE$46, 0);
      }
   }

   public String getDateTypeName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(DATETYPENAME$48, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetDateTypeName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(DATETYPENAME$48, 0);
         return target;
      }
   }

   public boolean isNilDateTypeName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(DATETYPENAME$48, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetDateTypeName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(DATETYPENAME$48) != 0;
      }
   }

   public void setDateTypeName(String dateTypeName) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(DATETYPENAME$48, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(DATETYPENAME$48);
         }

         target.setStringValue(dateTypeName);
      }
   }

   public void xsetDateTypeName(XmlString dateTypeName) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(DATETYPENAME$48, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(DATETYPENAME$48);
         }

         target.set(dateTypeName);
      }
   }

   public void setNilDateTypeName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(DATETYPENAME$48, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(DATETYPENAME$48);
         }

         target.setNil();
      }
   }

   public void unsetDateTypeName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(DATETYPENAME$48, 0);
      }
   }

   public boolean getSupportsSchemaForGetTables() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(SUPPORTSSCHEMAFORGETTABLES$50, 0);
         return target == null ? false : target.getBooleanValue();
      }
   }

   public XmlBoolean xgetSupportsSchemaForGetTables() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(SUPPORTSSCHEMAFORGETTABLES$50, 0);
         return target;
      }
   }

   public boolean isSetSupportsSchemaForGetTables() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(SUPPORTSSCHEMAFORGETTABLES$50) != 0;
      }
   }

   public void setSupportsSchemaForGetTables(boolean supportsSchemaForGetTables) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(SUPPORTSSCHEMAFORGETTABLES$50, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(SUPPORTSSCHEMAFORGETTABLES$50);
         }

         target.setBooleanValue(supportsSchemaForGetTables);
      }
   }

   public void xsetSupportsSchemaForGetTables(XmlBoolean supportsSchemaForGetTables) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(SUPPORTSSCHEMAFORGETTABLES$50, 0);
         if (target == null) {
            target = (XmlBoolean)this.get_store().add_element_user(SUPPORTSSCHEMAFORGETTABLES$50);
         }

         target.set(supportsSchemaForGetTables);
      }
   }

   public void unsetSupportsSchemaForGetTables() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(SUPPORTSSCHEMAFORGETTABLES$50, 0);
      }
   }

   public boolean getSupportsAlterTableWithDropColumn() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(SUPPORTSALTERTABLEWITHDROPCOLUMN$52, 0);
         return target == null ? false : target.getBooleanValue();
      }
   }

   public XmlBoolean xgetSupportsAlterTableWithDropColumn() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(SUPPORTSALTERTABLEWITHDROPCOLUMN$52, 0);
         return target;
      }
   }

   public boolean isSetSupportsAlterTableWithDropColumn() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(SUPPORTSALTERTABLEWITHDROPCOLUMN$52) != 0;
      }
   }

   public void setSupportsAlterTableWithDropColumn(boolean supportsAlterTableWithDropColumn) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(SUPPORTSALTERTABLEWITHDROPCOLUMN$52, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(SUPPORTSALTERTABLEWITHDROPCOLUMN$52);
         }

         target.setBooleanValue(supportsAlterTableWithDropColumn);
      }
   }

   public void xsetSupportsAlterTableWithDropColumn(XmlBoolean supportsAlterTableWithDropColumn) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(SUPPORTSALTERTABLEWITHDROPCOLUMN$52, 0);
         if (target == null) {
            target = (XmlBoolean)this.get_store().add_element_user(SUPPORTSALTERTABLEWITHDROPCOLUMN$52);
         }

         target.set(supportsAlterTableWithDropColumn);
      }
   }

   public void unsetSupportsAlterTableWithDropColumn() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(SUPPORTSALTERTABLEWITHDROPCOLUMN$52, 0);
      }
   }

   public String getCurrentTimeFunction() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(CURRENTTIMEFUNCTION$54, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetCurrentTimeFunction() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(CURRENTTIMEFUNCTION$54, 0);
         return target;
      }
   }

   public boolean isNilCurrentTimeFunction() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(CURRENTTIMEFUNCTION$54, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetCurrentTimeFunction() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(CURRENTTIMEFUNCTION$54) != 0;
      }
   }

   public void setCurrentTimeFunction(String currentTimeFunction) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(CURRENTTIMEFUNCTION$54, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(CURRENTTIMEFUNCTION$54);
         }

         target.setStringValue(currentTimeFunction);
      }
   }

   public void xsetCurrentTimeFunction(XmlString currentTimeFunction) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(CURRENTTIMEFUNCTION$54, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(CURRENTTIMEFUNCTION$54);
         }

         target.set(currentTimeFunction);
      }
   }

   public void setNilCurrentTimeFunction() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(CURRENTTIMEFUNCTION$54, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(CURRENTTIMEFUNCTION$54);
         }

         target.setNil();
      }
   }

   public void unsetCurrentTimeFunction() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(CURRENTTIMEFUNCTION$54, 0);
      }
   }

   public boolean getRequiresConditionForCrossJoin() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(REQUIRESCONDITIONFORCROSSJOIN$56, 0);
         return target == null ? false : target.getBooleanValue();
      }
   }

   public XmlBoolean xgetRequiresConditionForCrossJoin() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(REQUIRESCONDITIONFORCROSSJOIN$56, 0);
         return target;
      }
   }

   public boolean isSetRequiresConditionForCrossJoin() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(REQUIRESCONDITIONFORCROSSJOIN$56) != 0;
      }
   }

   public void setRequiresConditionForCrossJoin(boolean requiresConditionForCrossJoin) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(REQUIRESCONDITIONFORCROSSJOIN$56, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(REQUIRESCONDITIONFORCROSSJOIN$56);
         }

         target.setBooleanValue(requiresConditionForCrossJoin);
      }
   }

   public void xsetRequiresConditionForCrossJoin(XmlBoolean requiresConditionForCrossJoin) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(REQUIRESCONDITIONFORCROSSJOIN$56, 0);
         if (target == null) {
            target = (XmlBoolean)this.get_store().add_element_user(REQUIRESCONDITIONFORCROSSJOIN$56);
         }

         target.set(requiresConditionForCrossJoin);
      }
   }

   public void unsetRequiresConditionForCrossJoin() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(REQUIRESCONDITIONFORCROSSJOIN$56, 0);
      }
   }

   public String getRefTypeName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(REFTYPENAME$58, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetRefTypeName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(REFTYPENAME$58, 0);
         return target;
      }
   }

   public boolean isNilRefTypeName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(REFTYPENAME$58, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetRefTypeName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(REFTYPENAME$58) != 0;
      }
   }

   public void setRefTypeName(String refTypeName) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(REFTYPENAME$58, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(REFTYPENAME$58);
         }

         target.setStringValue(refTypeName);
      }
   }

   public void xsetRefTypeName(XmlString refTypeName) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(REFTYPENAME$58, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(REFTYPENAME$58);
         }

         target.set(refTypeName);
      }
   }

   public void setNilRefTypeName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(REFTYPENAME$58, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(REFTYPENAME$58);
         }

         target.setNil();
      }
   }

   public void unsetRefTypeName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(REFTYPENAME$58, 0);
      }
   }

   public String getConcatenateDelimiter() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(CONCATENATEDELIMITER$60, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetConcatenateDelimiter() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(CONCATENATEDELIMITER$60, 0);
         return target;
      }
   }

   public boolean isNilConcatenateDelimiter() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(CONCATENATEDELIMITER$60, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetConcatenateDelimiter() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(CONCATENATEDELIMITER$60) != 0;
      }
   }

   public void setConcatenateDelimiter(String concatenateDelimiter) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(CONCATENATEDELIMITER$60, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(CONCATENATEDELIMITER$60);
         }

         target.setStringValue(concatenateDelimiter);
      }
   }

   public void xsetConcatenateDelimiter(XmlString concatenateDelimiter) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(CONCATENATEDELIMITER$60, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(CONCATENATEDELIMITER$60);
         }

         target.set(concatenateDelimiter);
      }
   }

   public void setNilConcatenateDelimiter() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(CONCATENATEDELIMITER$60, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(CONCATENATEDELIMITER$60);
         }

         target.setNil();
      }
   }

   public void unsetConcatenateDelimiter() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(CONCATENATEDELIMITER$60, 0);
      }
   }

   public String getCatalogSeparator() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(CATALOGSEPARATOR$62, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetCatalogSeparator() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(CATALOGSEPARATOR$62, 0);
         return target;
      }
   }

   public boolean isNilCatalogSeparator() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(CATALOGSEPARATOR$62, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetCatalogSeparator() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(CATALOGSEPARATOR$62) != 0;
      }
   }

   public void setCatalogSeparator(String catalogSeparator) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(CATALOGSEPARATOR$62, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(CATALOGSEPARATOR$62);
         }

         target.setStringValue(catalogSeparator);
      }
   }

   public void xsetCatalogSeparator(XmlString catalogSeparator) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(CATALOGSEPARATOR$62, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(CATALOGSEPARATOR$62);
         }

         target.set(catalogSeparator);
      }
   }

   public void setNilCatalogSeparator() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(CATALOGSEPARATOR$62, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(CATALOGSEPARATOR$62);
         }

         target.setNil();
      }
   }

   public void unsetCatalogSeparator() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(CATALOGSEPARATOR$62, 0);
      }
   }

   public boolean getSupportsModOperator() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(SUPPORTSMODOPERATOR$64, 0);
         return target == null ? false : target.getBooleanValue();
      }
   }

   public XmlBoolean xgetSupportsModOperator() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(SUPPORTSMODOPERATOR$64, 0);
         return target;
      }
   }

   public boolean isSetSupportsModOperator() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(SUPPORTSMODOPERATOR$64) != 0;
      }
   }

   public void setSupportsModOperator(boolean supportsModOperator) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(SUPPORTSMODOPERATOR$64, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(SUPPORTSMODOPERATOR$64);
         }

         target.setBooleanValue(supportsModOperator);
      }
   }

   public void xsetSupportsModOperator(XmlBoolean supportsModOperator) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(SUPPORTSMODOPERATOR$64, 0);
         if (target == null) {
            target = (XmlBoolean)this.get_store().add_element_user(SUPPORTSMODOPERATOR$64);
         }

         target.set(supportsModOperator);
      }
   }

   public void unsetSupportsModOperator() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(SUPPORTSMODOPERATOR$64, 0);
      }
   }

   public String getSchemaCase() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(SCHEMACASE$66, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetSchemaCase() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(SCHEMACASE$66, 0);
         return target;
      }
   }

   public boolean isNilSchemaCase() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(SCHEMACASE$66, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetSchemaCase() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(SCHEMACASE$66) != 0;
      }
   }

   public void setSchemaCase(String schemaCase) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(SCHEMACASE$66, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(SCHEMACASE$66);
         }

         target.setStringValue(schemaCase);
      }
   }

   public void xsetSchemaCase(XmlString schemaCase) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(SCHEMACASE$66, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(SCHEMACASE$66);
         }

         target.set(schemaCase);
      }
   }

   public void setNilSchemaCase() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(SCHEMACASE$66, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(SCHEMACASE$66);
         }

         target.setNil();
      }
   }

   public void unsetSchemaCase() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(SCHEMACASE$66, 0);
      }
   }

   public String getJavaObjectTypeName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(JAVAOBJECTTYPENAME$68, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetJavaObjectTypeName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(JAVAOBJECTTYPENAME$68, 0);
         return target;
      }
   }

   public boolean isNilJavaObjectTypeName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(JAVAOBJECTTYPENAME$68, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetJavaObjectTypeName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(JAVAOBJECTTYPENAME$68) != 0;
      }
   }

   public void setJavaObjectTypeName(String javaObjectTypeName) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(JAVAOBJECTTYPENAME$68, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(JAVAOBJECTTYPENAME$68);
         }

         target.setStringValue(javaObjectTypeName);
      }
   }

   public void xsetJavaObjectTypeName(XmlString javaObjectTypeName) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(JAVAOBJECTTYPENAME$68, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(JAVAOBJECTTYPENAME$68);
         }

         target.set(javaObjectTypeName);
      }
   }

   public void setNilJavaObjectTypeName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(JAVAOBJECTTYPENAME$68, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(JAVAOBJECTTYPENAME$68);
         }

         target.setNil();
      }
   }

   public void unsetJavaObjectTypeName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(JAVAOBJECTTYPENAME$68, 0);
      }
   }

   public String getDriverVendor() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(DRIVERVENDOR$70, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetDriverVendor() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(DRIVERVENDOR$70, 0);
         return target;
      }
   }

   public boolean isNilDriverVendor() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(DRIVERVENDOR$70, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetDriverVendor() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(DRIVERVENDOR$70) != 0;
      }
   }

   public void setDriverVendor(String driverVendor) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(DRIVERVENDOR$70, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(DRIVERVENDOR$70);
         }

         target.setStringValue(driverVendor);
      }
   }

   public void xsetDriverVendor(XmlString driverVendor) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(DRIVERVENDOR$70, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(DRIVERVENDOR$70);
         }

         target.set(driverVendor);
      }
   }

   public void setNilDriverVendor() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(DRIVERVENDOR$70, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(DRIVERVENDOR$70);
         }

         target.setNil();
      }
   }

   public void unsetDriverVendor() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(DRIVERVENDOR$70, 0);
      }
   }

   public boolean getSupportsLockingWithMultipleTables() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(SUPPORTSLOCKINGWITHMULTIPLETABLES$72, 0);
         return target == null ? false : target.getBooleanValue();
      }
   }

   public XmlBoolean xgetSupportsLockingWithMultipleTables() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(SUPPORTSLOCKINGWITHMULTIPLETABLES$72, 0);
         return target;
      }
   }

   public boolean isSetSupportsLockingWithMultipleTables() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(SUPPORTSLOCKINGWITHMULTIPLETABLES$72) != 0;
      }
   }

   public void setSupportsLockingWithMultipleTables(boolean supportsLockingWithMultipleTables) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(SUPPORTSLOCKINGWITHMULTIPLETABLES$72, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(SUPPORTSLOCKINGWITHMULTIPLETABLES$72);
         }

         target.setBooleanValue(supportsLockingWithMultipleTables);
      }
   }

   public void xsetSupportsLockingWithMultipleTables(XmlBoolean supportsLockingWithMultipleTables) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(SUPPORTSLOCKINGWITHMULTIPLETABLES$72, 0);
         if (target == null) {
            target = (XmlBoolean)this.get_store().add_element_user(SUPPORTSLOCKINGWITHMULTIPLETABLES$72);
         }

         target.set(supportsLockingWithMultipleTables);
      }
   }

   public void unsetSupportsLockingWithMultipleTables() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(SUPPORTSLOCKINGWITHMULTIPLETABLES$72, 0);
      }
   }

   public int getMaxColumnNameLength() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(MAXCOLUMNNAMELENGTH$74, 0);
         return target == null ? 0 : target.getIntValue();
      }
   }

   public XmlInt xgetMaxColumnNameLength() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlInt target = null;
         target = (XmlInt)this.get_store().find_element_user(MAXCOLUMNNAMELENGTH$74, 0);
         return target;
      }
   }

   public boolean isSetMaxColumnNameLength() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(MAXCOLUMNNAMELENGTH$74) != 0;
      }
   }

   public void setMaxColumnNameLength(int maxColumnNameLength) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(MAXCOLUMNNAMELENGTH$74, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(MAXCOLUMNNAMELENGTH$74);
         }

         target.setIntValue(maxColumnNameLength);
      }
   }

   public void xsetMaxColumnNameLength(XmlInt maxColumnNameLength) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlInt target = null;
         target = (XmlInt)this.get_store().find_element_user(MAXCOLUMNNAMELENGTH$74, 0);
         if (target == null) {
            target = (XmlInt)this.get_store().add_element_user(MAXCOLUMNNAMELENGTH$74);
         }

         target.set(maxColumnNameLength);
      }
   }

   public void unsetMaxColumnNameLength() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(MAXCOLUMNNAMELENGTH$74, 0);
      }
   }

   public String getDoubleTypeName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(DOUBLETYPENAME$76, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetDoubleTypeName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(DOUBLETYPENAME$76, 0);
         return target;
      }
   }

   public boolean isNilDoubleTypeName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(DOUBLETYPENAME$76, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetDoubleTypeName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(DOUBLETYPENAME$76) != 0;
      }
   }

   public void setDoubleTypeName(String doubleTypeName) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(DOUBLETYPENAME$76, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(DOUBLETYPENAME$76);
         }

         target.setStringValue(doubleTypeName);
      }
   }

   public void xsetDoubleTypeName(XmlString doubleTypeName) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(DOUBLETYPENAME$76, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(DOUBLETYPENAME$76);
         }

         target.set(doubleTypeName);
      }
   }

   public void setNilDoubleTypeName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(DOUBLETYPENAME$76, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(DOUBLETYPENAME$76);
         }

         target.setNil();
      }
   }

   public void unsetDoubleTypeName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(DOUBLETYPENAME$76, 0);
      }
   }

   public boolean getUseGetStringForClobs() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(USEGETSTRINGFORCLOBS$78, 0);
         return target == null ? false : target.getBooleanValue();
      }
   }

   public XmlBoolean xgetUseGetStringForClobs() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(USEGETSTRINGFORCLOBS$78, 0);
         return target;
      }
   }

   public boolean isSetUseGetStringForClobs() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(USEGETSTRINGFORCLOBS$78) != 0;
      }
   }

   public void setUseGetStringForClobs(boolean useGetStringForClobs) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(USEGETSTRINGFORCLOBS$78, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(USEGETSTRINGFORCLOBS$78);
         }

         target.setBooleanValue(useGetStringForClobs);
      }
   }

   public void xsetUseGetStringForClobs(XmlBoolean useGetStringForClobs) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(USEGETSTRINGFORCLOBS$78, 0);
         if (target == null) {
            target = (XmlBoolean)this.get_store().add_element_user(USEGETSTRINGFORCLOBS$78);
         }

         target.set(useGetStringForClobs);
      }
   }

   public void unsetUseGetStringForClobs() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(USEGETSTRINGFORCLOBS$78, 0);
      }
   }

   public String getDecimalTypeName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(DECIMALTYPENAME$80, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetDecimalTypeName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(DECIMALTYPENAME$80, 0);
         return target;
      }
   }

   public boolean isNilDecimalTypeName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(DECIMALTYPENAME$80, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetDecimalTypeName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(DECIMALTYPENAME$80) != 0;
      }
   }

   public void setDecimalTypeName(String decimalTypeName) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(DECIMALTYPENAME$80, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(DECIMALTYPENAME$80);
         }

         target.setStringValue(decimalTypeName);
      }
   }

   public void xsetDecimalTypeName(XmlString decimalTypeName) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(DECIMALTYPENAME$80, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(DECIMALTYPENAME$80);
         }

         target.set(decimalTypeName);
      }
   }

   public void setNilDecimalTypeName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(DECIMALTYPENAME$80, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(DECIMALTYPENAME$80);
         }

         target.setNil();
      }
   }

   public void unsetDecimalTypeName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(DECIMALTYPENAME$80, 0);
      }
   }

   public String getSmallintTypeName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(SMALLINTTYPENAME$82, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetSmallintTypeName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(SMALLINTTYPENAME$82, 0);
         return target;
      }
   }

   public boolean isNilSmallintTypeName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(SMALLINTTYPENAME$82, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetSmallintTypeName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(SMALLINTTYPENAME$82) != 0;
      }
   }

   public void setSmallintTypeName(String smallintTypeName) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(SMALLINTTYPENAME$82, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(SMALLINTTYPENAME$82);
         }

         target.setStringValue(smallintTypeName);
      }
   }

   public void xsetSmallintTypeName(XmlString smallintTypeName) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(SMALLINTTYPENAME$82, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(SMALLINTTYPENAME$82);
         }

         target.set(smallintTypeName);
      }
   }

   public void setNilSmallintTypeName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(SMALLINTTYPENAME$82, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(SMALLINTTYPENAME$82);
         }

         target.setNil();
      }
   }

   public void unsetSmallintTypeName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(SMALLINTTYPENAME$82, 0);
      }
   }

   public int getDatePrecision() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(DATEPRECISION$84, 0);
         return target == null ? 0 : target.getIntValue();
      }
   }

   public XmlInt xgetDatePrecision() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlInt target = null;
         target = (XmlInt)this.get_store().find_element_user(DATEPRECISION$84, 0);
         return target;
      }
   }

   public boolean isSetDatePrecision() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(DATEPRECISION$84) != 0;
      }
   }

   public void setDatePrecision(int datePrecision) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(DATEPRECISION$84, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(DATEPRECISION$84);
         }

         target.setIntValue(datePrecision);
      }
   }

   public void xsetDatePrecision(XmlInt datePrecision) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlInt target = null;
         target = (XmlInt)this.get_store().find_element_user(DATEPRECISION$84, 0);
         if (target == null) {
            target = (XmlInt)this.get_store().add_element_user(DATEPRECISION$84);
         }

         target.set(datePrecision);
      }
   }

   public void unsetDatePrecision() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(DATEPRECISION$84, 0);
      }
   }

   public boolean getSupportsAlterTableWithAddColumn() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(SUPPORTSALTERTABLEWITHADDCOLUMN$86, 0);
         return target == null ? false : target.getBooleanValue();
      }
   }

   public XmlBoolean xgetSupportsAlterTableWithAddColumn() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(SUPPORTSALTERTABLEWITHADDCOLUMN$86, 0);
         return target;
      }
   }

   public boolean isSetSupportsAlterTableWithAddColumn() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(SUPPORTSALTERTABLEWITHADDCOLUMN$86) != 0;
      }
   }

   public void setSupportsAlterTableWithAddColumn(boolean supportsAlterTableWithAddColumn) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(SUPPORTSALTERTABLEWITHADDCOLUMN$86, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(SUPPORTSALTERTABLEWITHADDCOLUMN$86);
         }

         target.setBooleanValue(supportsAlterTableWithAddColumn);
      }
   }

   public void xsetSupportsAlterTableWithAddColumn(XmlBoolean supportsAlterTableWithAddColumn) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(SUPPORTSALTERTABLEWITHADDCOLUMN$86, 0);
         if (target == null) {
            target = (XmlBoolean)this.get_store().add_element_user(SUPPORTSALTERTABLEWITHADDCOLUMN$86);
         }

         target.set(supportsAlterTableWithAddColumn);
      }
   }

   public void unsetSupportsAlterTableWithAddColumn() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(SUPPORTSALTERTABLEWITHADDCOLUMN$86, 0);
      }
   }

   public String getBitTypeName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(BITTYPENAME$88, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetBitTypeName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(BITTYPENAME$88, 0);
         return target;
      }
   }

   public boolean isNilBitTypeName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(BITTYPENAME$88, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetBitTypeName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(BITTYPENAME$88) != 0;
      }
   }

   public void setBitTypeName(String bitTypeName) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(BITTYPENAME$88, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(BITTYPENAME$88);
         }

         target.setStringValue(bitTypeName);
      }
   }

   public void xsetBitTypeName(XmlString bitTypeName) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(BITTYPENAME$88, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(BITTYPENAME$88);
         }

         target.set(bitTypeName);
      }
   }

   public void setNilBitTypeName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(BITTYPENAME$88, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(BITTYPENAME$88);
         }

         target.setNil();
      }
   }

   public void unsetBitTypeName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(BITTYPENAME$88, 0);
      }
   }

   public boolean getSupportsNullTableForGetColumns() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(SUPPORTSNULLTABLEFORGETCOLUMNS$90, 0);
         return target == null ? false : target.getBooleanValue();
      }
   }

   public XmlBoolean xgetSupportsNullTableForGetColumns() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(SUPPORTSNULLTABLEFORGETCOLUMNS$90, 0);
         return target;
      }
   }

   public boolean isSetSupportsNullTableForGetColumns() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(SUPPORTSNULLTABLEFORGETCOLUMNS$90) != 0;
      }
   }

   public void setSupportsNullTableForGetColumns(boolean supportsNullTableForGetColumns) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(SUPPORTSNULLTABLEFORGETCOLUMNS$90, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(SUPPORTSNULLTABLEFORGETCOLUMNS$90);
         }

         target.setBooleanValue(supportsNullTableForGetColumns);
      }
   }

   public void xsetSupportsNullTableForGetColumns(XmlBoolean supportsNullTableForGetColumns) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(SUPPORTSNULLTABLEFORGETCOLUMNS$90, 0);
         if (target == null) {
            target = (XmlBoolean)this.get_store().add_element_user(SUPPORTSNULLTABLEFORGETCOLUMNS$90);
         }

         target.set(supportsNullTableForGetColumns);
      }
   }

   public void unsetSupportsNullTableForGetColumns() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(SUPPORTSNULLTABLEFORGETCOLUMNS$90, 0);
      }
   }

   public String getToUpperCaseFunction() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(TOUPPERCASEFUNCTION$92, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetToUpperCaseFunction() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(TOUPPERCASEFUNCTION$92, 0);
         return target;
      }
   }

   public boolean isNilToUpperCaseFunction() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(TOUPPERCASEFUNCTION$92, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetToUpperCaseFunction() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(TOUPPERCASEFUNCTION$92) != 0;
      }
   }

   public void setToUpperCaseFunction(String toUpperCaseFunction) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(TOUPPERCASEFUNCTION$92, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(TOUPPERCASEFUNCTION$92);
         }

         target.setStringValue(toUpperCaseFunction);
      }
   }

   public void xsetToUpperCaseFunction(XmlString toUpperCaseFunction) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(TOUPPERCASEFUNCTION$92, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(TOUPPERCASEFUNCTION$92);
         }

         target.set(toUpperCaseFunction);
      }
   }

   public void setNilToUpperCaseFunction() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(TOUPPERCASEFUNCTION$92, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(TOUPPERCASEFUNCTION$92);
         }

         target.setNil();
      }
   }

   public void unsetToUpperCaseFunction() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(TOUPPERCASEFUNCTION$92, 0);
      }
   }

   public boolean getSupportsSelectEndIndex() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(SUPPORTSSELECTENDINDEX$94, 0);
         return target == null ? false : target.getBooleanValue();
      }
   }

   public XmlBoolean xgetSupportsSelectEndIndex() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(SUPPORTSSELECTENDINDEX$94, 0);
         return target;
      }
   }

   public boolean isSetSupportsSelectEndIndex() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(SUPPORTSSELECTENDINDEX$94) != 0;
      }
   }

   public void setSupportsSelectEndIndex(boolean supportsSelectEndIndex) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(SUPPORTSSELECTENDINDEX$94, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(SUPPORTSSELECTENDINDEX$94);
         }

         target.setBooleanValue(supportsSelectEndIndex);
      }
   }

   public void xsetSupportsSelectEndIndex(XmlBoolean supportsSelectEndIndex) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(SUPPORTSSELECTENDINDEX$94, 0);
         if (target == null) {
            target = (XmlBoolean)this.get_store().add_element_user(SUPPORTSSELECTENDINDEX$94);
         }

         target.set(supportsSelectEndIndex);
      }
   }

   public void unsetSupportsSelectEndIndex() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(SUPPORTSSELECTENDINDEX$94, 0);
      }
   }

   public boolean getSupportsAutoAssign() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(SUPPORTSAUTOASSIGN$96, 0);
         return target == null ? false : target.getBooleanValue();
      }
   }

   public XmlBoolean xgetSupportsAutoAssign() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(SUPPORTSAUTOASSIGN$96, 0);
         return target;
      }
   }

   public boolean isSetSupportsAutoAssign() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(SUPPORTSAUTOASSIGN$96) != 0;
      }
   }

   public void setSupportsAutoAssign(boolean supportsAutoAssign) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(SUPPORTSAUTOASSIGN$96, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(SUPPORTSAUTOASSIGN$96);
         }

         target.setBooleanValue(supportsAutoAssign);
      }
   }

   public void xsetSupportsAutoAssign(XmlBoolean supportsAutoAssign) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(SUPPORTSAUTOASSIGN$96, 0);
         if (target == null) {
            target = (XmlBoolean)this.get_store().add_element_user(SUPPORTSAUTOASSIGN$96);
         }

         target.set(supportsAutoAssign);
      }
   }

   public void unsetSupportsAutoAssign() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(SUPPORTSAUTOASSIGN$96, 0);
      }
   }

   public boolean getStoreLargeNumbersAsStrings() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(STORELARGENUMBERSASSTRINGS$98, 0);
         return target == null ? false : target.getBooleanValue();
      }
   }

   public XmlBoolean xgetStoreLargeNumbersAsStrings() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(STORELARGENUMBERSASSTRINGS$98, 0);
         return target;
      }
   }

   public boolean isSetStoreLargeNumbersAsStrings() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(STORELARGENUMBERSASSTRINGS$98) != 0;
      }
   }

   public void setStoreLargeNumbersAsStrings(boolean storeLargeNumbersAsStrings) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(STORELARGENUMBERSASSTRINGS$98, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(STORELARGENUMBERSASSTRINGS$98);
         }

         target.setBooleanValue(storeLargeNumbersAsStrings);
      }
   }

   public void xsetStoreLargeNumbersAsStrings(XmlBoolean storeLargeNumbersAsStrings) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(STORELARGENUMBERSASSTRINGS$98, 0);
         if (target == null) {
            target = (XmlBoolean)this.get_store().add_element_user(STORELARGENUMBERSASSTRINGS$98);
         }

         target.set(storeLargeNumbersAsStrings);
      }
   }

   public void unsetStoreLargeNumbersAsStrings() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(STORELARGENUMBERSASSTRINGS$98, 0);
      }
   }

   public String getConstraintNameMode() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(CONSTRAINTNAMEMODE$100, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetConstraintNameMode() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(CONSTRAINTNAMEMODE$100, 0);
         return target;
      }
   }

   public boolean isNilConstraintNameMode() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(CONSTRAINTNAMEMODE$100, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetConstraintNameMode() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(CONSTRAINTNAMEMODE$100) != 0;
      }
   }

   public void setConstraintNameMode(String constraintNameMode) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(CONSTRAINTNAMEMODE$100, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(CONSTRAINTNAMEMODE$100);
         }

         target.setStringValue(constraintNameMode);
      }
   }

   public void xsetConstraintNameMode(XmlString constraintNameMode) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(CONSTRAINTNAMEMODE$100, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(CONSTRAINTNAMEMODE$100);
         }

         target.set(constraintNameMode);
      }
   }

   public void setNilConstraintNameMode() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(CONSTRAINTNAMEMODE$100, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(CONSTRAINTNAMEMODE$100);
         }

         target.setNil();
      }
   }

   public void unsetConstraintNameMode() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(CONSTRAINTNAMEMODE$100, 0);
      }
   }

   public boolean getAllowsAliasInBulkClause() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(ALLOWSALIASINBULKCLAUSE$102, 0);
         return target == null ? false : target.getBooleanValue();
      }
   }

   public XmlBoolean xgetAllowsAliasInBulkClause() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(ALLOWSALIASINBULKCLAUSE$102, 0);
         return target;
      }
   }

   public boolean isSetAllowsAliasInBulkClause() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(ALLOWSALIASINBULKCLAUSE$102) != 0;
      }
   }

   public void setAllowsAliasInBulkClause(boolean allowsAliasInBulkClause) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(ALLOWSALIASINBULKCLAUSE$102, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(ALLOWSALIASINBULKCLAUSE$102);
         }

         target.setBooleanValue(allowsAliasInBulkClause);
      }
   }

   public void xsetAllowsAliasInBulkClause(XmlBoolean allowsAliasInBulkClause) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(ALLOWSALIASINBULKCLAUSE$102, 0);
         if (target == null) {
            target = (XmlBoolean)this.get_store().add_element_user(ALLOWSALIASINBULKCLAUSE$102);
         }

         target.set(allowsAliasInBulkClause);
      }
   }

   public void unsetAllowsAliasInBulkClause() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(ALLOWSALIASINBULKCLAUSE$102, 0);
      }
   }

   public boolean getSupportsSelectForUpdate() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(SUPPORTSSELECTFORUPDATE$104, 0);
         return target == null ? false : target.getBooleanValue();
      }
   }

   public XmlBoolean xgetSupportsSelectForUpdate() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(SUPPORTSSELECTFORUPDATE$104, 0);
         return target;
      }
   }

   public boolean isSetSupportsSelectForUpdate() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(SUPPORTSSELECTFORUPDATE$104) != 0;
      }
   }

   public void setSupportsSelectForUpdate(boolean supportsSelectForUpdate) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(SUPPORTSSELECTFORUPDATE$104, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(SUPPORTSSELECTFORUPDATE$104);
         }

         target.setBooleanValue(supportsSelectForUpdate);
      }
   }

   public void xsetSupportsSelectForUpdate(XmlBoolean supportsSelectForUpdate) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(SUPPORTSSELECTFORUPDATE$104, 0);
         if (target == null) {
            target = (XmlBoolean)this.get_store().add_element_user(SUPPORTSSELECTFORUPDATE$104);
         }

         target.set(supportsSelectForUpdate);
      }
   }

   public void unsetSupportsSelectForUpdate() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(SUPPORTSSELECTFORUPDATE$104, 0);
      }
   }

   public String getDistinctCountColumnSeparator() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(DISTINCTCOUNTCOLUMNSEPARATOR$106, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetDistinctCountColumnSeparator() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(DISTINCTCOUNTCOLUMNSEPARATOR$106, 0);
         return target;
      }
   }

   public boolean isNilDistinctCountColumnSeparator() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(DISTINCTCOUNTCOLUMNSEPARATOR$106, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetDistinctCountColumnSeparator() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(DISTINCTCOUNTCOLUMNSEPARATOR$106) != 0;
      }
   }

   public void setDistinctCountColumnSeparator(String distinctCountColumnSeparator) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(DISTINCTCOUNTCOLUMNSEPARATOR$106, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(DISTINCTCOUNTCOLUMNSEPARATOR$106);
         }

         target.setStringValue(distinctCountColumnSeparator);
      }
   }

   public void xsetDistinctCountColumnSeparator(XmlString distinctCountColumnSeparator) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(DISTINCTCOUNTCOLUMNSEPARATOR$106, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(DISTINCTCOUNTCOLUMNSEPARATOR$106);
         }

         target.set(distinctCountColumnSeparator);
      }
   }

   public void setNilDistinctCountColumnSeparator() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(DISTINCTCOUNTCOLUMNSEPARATOR$106, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(DISTINCTCOUNTCOLUMNSEPARATOR$106);
         }

         target.setNil();
      }
   }

   public void unsetDistinctCountColumnSeparator() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(DISTINCTCOUNTCOLUMNSEPARATOR$106, 0);
      }
   }

   public boolean getSupportsSubselect() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(SUPPORTSSUBSELECT$108, 0);
         return target == null ? false : target.getBooleanValue();
      }
   }

   public XmlBoolean xgetSupportsSubselect() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(SUPPORTSSUBSELECT$108, 0);
         return target;
      }
   }

   public boolean isSetSupportsSubselect() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(SUPPORTSSUBSELECT$108) != 0;
      }
   }

   public void setSupportsSubselect(boolean supportsSubselect) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(SUPPORTSSUBSELECT$108, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(SUPPORTSSUBSELECT$108);
         }

         target.setBooleanValue(supportsSubselect);
      }
   }

   public void xsetSupportsSubselect(XmlBoolean supportsSubselect) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(SUPPORTSSUBSELECT$108, 0);
         if (target == null) {
            target = (XmlBoolean)this.get_store().add_element_user(SUPPORTSSUBSELECT$108);
         }

         target.set(supportsSubselect);
      }
   }

   public void unsetSupportsSubselect() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(SUPPORTSSUBSELECT$108, 0);
      }
   }

   public String getTimeTypeName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(TIMETYPENAME$110, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetTimeTypeName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(TIMETYPENAME$110, 0);
         return target;
      }
   }

   public boolean isNilTimeTypeName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(TIMETYPENAME$110, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetTimeTypeName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(TIMETYPENAME$110) != 0;
      }
   }

   public void setTimeTypeName(String timeTypeName) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(TIMETYPENAME$110, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(TIMETYPENAME$110);
         }

         target.setStringValue(timeTypeName);
      }
   }

   public void xsetTimeTypeName(XmlString timeTypeName) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(TIMETYPENAME$110, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(TIMETYPENAME$110);
         }

         target.set(timeTypeName);
      }
   }

   public void setNilTimeTypeName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(TIMETYPENAME$110, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(TIMETYPENAME$110);
         }

         target.setNil();
      }
   }

   public void unsetTimeTypeName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(TIMETYPENAME$110, 0);
      }
   }

   public String getAutoAssignTypeName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(AUTOASSIGNTYPENAME$112, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetAutoAssignTypeName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(AUTOASSIGNTYPENAME$112, 0);
         return target;
      }
   }

   public boolean isNilAutoAssignTypeName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(AUTOASSIGNTYPENAME$112, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetAutoAssignTypeName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(AUTOASSIGNTYPENAME$112) != 0;
      }
   }

   public void setAutoAssignTypeName(String autoAssignTypeName) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(AUTOASSIGNTYPENAME$112, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(AUTOASSIGNTYPENAME$112);
         }

         target.setStringValue(autoAssignTypeName);
      }
   }

   public void xsetAutoAssignTypeName(XmlString autoAssignTypeName) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(AUTOASSIGNTYPENAME$112, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(AUTOASSIGNTYPENAME$112);
         }

         target.set(autoAssignTypeName);
      }
   }

   public void setNilAutoAssignTypeName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(AUTOASSIGNTYPENAME$112, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(AUTOASSIGNTYPENAME$112);
         }

         target.setNil();
      }
   }

   public void unsetAutoAssignTypeName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(AUTOASSIGNTYPENAME$112, 0);
      }
   }

   public boolean getUseGetObjectForBlobs() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(USEGETOBJECTFORBLOBS$114, 0);
         return target == null ? false : target.getBooleanValue();
      }
   }

   public XmlBoolean xgetUseGetObjectForBlobs() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(USEGETOBJECTFORBLOBS$114, 0);
         return target;
      }
   }

   public boolean isSetUseGetObjectForBlobs() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(USEGETOBJECTFORBLOBS$114) != 0;
      }
   }

   public void setUseGetObjectForBlobs(boolean useGetObjectForBlobs) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(USEGETOBJECTFORBLOBS$114, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(USEGETOBJECTFORBLOBS$114);
         }

         target.setBooleanValue(useGetObjectForBlobs);
      }
   }

   public void xsetUseGetObjectForBlobs(XmlBoolean useGetObjectForBlobs) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(USEGETOBJECTFORBLOBS$114, 0);
         if (target == null) {
            target = (XmlBoolean)this.get_store().add_element_user(USEGETOBJECTFORBLOBS$114);
         }

         target.set(useGetObjectForBlobs);
      }
   }

   public void unsetUseGetObjectForBlobs() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(USEGETOBJECTFORBLOBS$114, 0);
      }
   }

   public int getMaxAutoAssignNameLength() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(MAXAUTOASSIGNNAMELENGTH$116, 0);
         return target == null ? 0 : target.getIntValue();
      }
   }

   public XmlInt xgetMaxAutoAssignNameLength() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlInt target = null;
         target = (XmlInt)this.get_store().find_element_user(MAXAUTOASSIGNNAMELENGTH$116, 0);
         return target;
      }
   }

   public boolean isSetMaxAutoAssignNameLength() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(MAXAUTOASSIGNNAMELENGTH$116) != 0;
      }
   }

   public void setMaxAutoAssignNameLength(int maxAutoAssignNameLength) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(MAXAUTOASSIGNNAMELENGTH$116, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(MAXAUTOASSIGNNAMELENGTH$116);
         }

         target.setIntValue(maxAutoAssignNameLength);
      }
   }

   public void xsetMaxAutoAssignNameLength(XmlInt maxAutoAssignNameLength) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlInt target = null;
         target = (XmlInt)this.get_store().find_element_user(MAXAUTOASSIGNNAMELENGTH$116, 0);
         if (target == null) {
            target = (XmlInt)this.get_store().add_element_user(MAXAUTOASSIGNNAMELENGTH$116);
         }

         target.set(maxAutoAssignNameLength);
      }
   }

   public void unsetMaxAutoAssignNameLength() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(MAXAUTOASSIGNNAMELENGTH$116, 0);
      }
   }

   public String getValidationSql() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(VALIDATIONSQL$118, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetValidationSql() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(VALIDATIONSQL$118, 0);
         return target;
      }
   }

   public boolean isNilValidationSql() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(VALIDATIONSQL$118, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetValidationSql() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(VALIDATIONSQL$118) != 0;
      }
   }

   public void setValidationSql(String validationSql) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(VALIDATIONSQL$118, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(VALIDATIONSQL$118);
         }

         target.setStringValue(validationSql);
      }
   }

   public void xsetValidationSql(XmlString validationSql) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(VALIDATIONSQL$118, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(VALIDATIONSQL$118);
         }

         target.set(validationSql);
      }
   }

   public void setNilValidationSql() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(VALIDATIONSQL$118, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(VALIDATIONSQL$118);
         }

         target.setNil();
      }
   }

   public void unsetValidationSql() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(VALIDATIONSQL$118, 0);
      }
   }

   public String getStructTypeName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(STRUCTTYPENAME$120, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetStructTypeName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(STRUCTTYPENAME$120, 0);
         return target;
      }
   }

   public boolean isNilStructTypeName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(STRUCTTYPENAME$120, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetStructTypeName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(STRUCTTYPENAME$120) != 0;
      }
   }

   public void setStructTypeName(String structTypeName) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(STRUCTTYPENAME$120, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(STRUCTTYPENAME$120);
         }

         target.setStringValue(structTypeName);
      }
   }

   public void xsetStructTypeName(XmlString structTypeName) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(STRUCTTYPENAME$120, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(STRUCTTYPENAME$120);
         }

         target.set(structTypeName);
      }
   }

   public void setNilStructTypeName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(STRUCTTYPENAME$120, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(STRUCTTYPENAME$120);
         }

         target.setNil();
      }
   }

   public void unsetStructTypeName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(STRUCTTYPENAME$120, 0);
      }
   }

   public String getVarcharTypeName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(VARCHARTYPENAME$122, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetVarcharTypeName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(VARCHARTYPENAME$122, 0);
         return target;
      }
   }

   public boolean isNilVarcharTypeName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(VARCHARTYPENAME$122, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetVarcharTypeName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(VARCHARTYPENAME$122) != 0;
      }
   }

   public void setVarcharTypeName(String varcharTypeName) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(VARCHARTYPENAME$122, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(VARCHARTYPENAME$122);
         }

         target.setStringValue(varcharTypeName);
      }
   }

   public void xsetVarcharTypeName(XmlString varcharTypeName) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(VARCHARTYPENAME$122, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(VARCHARTYPENAME$122);
         }

         target.set(varcharTypeName);
      }
   }

   public void setNilVarcharTypeName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(VARCHARTYPENAME$122, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(VARCHARTYPENAME$122);
         }

         target.setNil();
      }
   }

   public void unsetVarcharTypeName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(VARCHARTYPENAME$122, 0);
      }
   }

   public int getRangePosition() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(RANGEPOSITION$124, 0);
         return target == null ? 0 : target.getIntValue();
      }
   }

   public XmlInt xgetRangePosition() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlInt target = null;
         target = (XmlInt)this.get_store().find_element_user(RANGEPOSITION$124, 0);
         return target;
      }
   }

   public boolean isSetRangePosition() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(RANGEPOSITION$124) != 0;
      }
   }

   public void setRangePosition(int rangePosition) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(RANGEPOSITION$124, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(RANGEPOSITION$124);
         }

         target.setIntValue(rangePosition);
      }
   }

   public void xsetRangePosition(XmlInt rangePosition) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlInt target = null;
         target = (XmlInt)this.get_store().find_element_user(RANGEPOSITION$124, 0);
         if (target == null) {
            target = (XmlInt)this.get_store().add_element_user(RANGEPOSITION$124);
         }

         target.set(rangePosition);
      }
   }

   public void unsetRangePosition() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(RANGEPOSITION$124, 0);
      }
   }

   public boolean getSupportsRestrictUpdateAction() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(SUPPORTSRESTRICTUPDATEACTION$126, 0);
         return target == null ? false : target.getBooleanValue();
      }
   }

   public XmlBoolean xgetSupportsRestrictUpdateAction() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(SUPPORTSRESTRICTUPDATEACTION$126, 0);
         return target;
      }
   }

   public boolean isSetSupportsRestrictUpdateAction() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(SUPPORTSRESTRICTUPDATEACTION$126) != 0;
      }
   }

   public void setSupportsRestrictUpdateAction(boolean supportsRestrictUpdateAction) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(SUPPORTSRESTRICTUPDATEACTION$126, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(SUPPORTSRESTRICTUPDATEACTION$126);
         }

         target.setBooleanValue(supportsRestrictUpdateAction);
      }
   }

   public void xsetSupportsRestrictUpdateAction(XmlBoolean supportsRestrictUpdateAction) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(SUPPORTSRESTRICTUPDATEACTION$126, 0);
         if (target == null) {
            target = (XmlBoolean)this.get_store().add_element_user(SUPPORTSRESTRICTUPDATEACTION$126);
         }

         target.set(supportsRestrictUpdateAction);
      }
   }

   public void unsetSupportsRestrictUpdateAction() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(SUPPORTSRESTRICTUPDATEACTION$126, 0);
      }
   }

   public String getAutoAssignClause() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(AUTOASSIGNCLAUSE$128, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetAutoAssignClause() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(AUTOASSIGNCLAUSE$128, 0);
         return target;
      }
   }

   public boolean isNilAutoAssignClause() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(AUTOASSIGNCLAUSE$128, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetAutoAssignClause() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(AUTOASSIGNCLAUSE$128) != 0;
      }
   }

   public void setAutoAssignClause(String autoAssignClause) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(AUTOASSIGNCLAUSE$128, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(AUTOASSIGNCLAUSE$128);
         }

         target.setStringValue(autoAssignClause);
      }
   }

   public void xsetAutoAssignClause(XmlString autoAssignClause) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(AUTOASSIGNCLAUSE$128, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(AUTOASSIGNCLAUSE$128);
         }

         target.set(autoAssignClause);
      }
   }

   public void setNilAutoAssignClause() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(AUTOASSIGNCLAUSE$128, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(AUTOASSIGNCLAUSE$128);
         }

         target.setNil();
      }
   }

   public void unsetAutoAssignClause() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(AUTOASSIGNCLAUSE$128, 0);
      }
   }

   public boolean getSupportsMultipleNontransactionalResultSets() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(SUPPORTSMULTIPLENONTRANSACTIONALRESULTSETS$130, 0);
         return target == null ? false : target.getBooleanValue();
      }
   }

   public XmlBoolean xgetSupportsMultipleNontransactionalResultSets() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(SUPPORTSMULTIPLENONTRANSACTIONALRESULTSETS$130, 0);
         return target;
      }
   }

   public boolean isSetSupportsMultipleNontransactionalResultSets() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(SUPPORTSMULTIPLENONTRANSACTIONALRESULTSETS$130) != 0;
      }
   }

   public void setSupportsMultipleNontransactionalResultSets(boolean supportsMultipleNontransactionalResultSets) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(SUPPORTSMULTIPLENONTRANSACTIONALRESULTSETS$130, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(SUPPORTSMULTIPLENONTRANSACTIONALRESULTSETS$130);
         }

         target.setBooleanValue(supportsMultipleNontransactionalResultSets);
      }
   }

   public void xsetSupportsMultipleNontransactionalResultSets(XmlBoolean supportsMultipleNontransactionalResultSets) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(SUPPORTSMULTIPLENONTRANSACTIONALRESULTSETS$130, 0);
         if (target == null) {
            target = (XmlBoolean)this.get_store().add_element_user(SUPPORTSMULTIPLENONTRANSACTIONALRESULTSETS$130);
         }

         target.set(supportsMultipleNontransactionalResultSets);
      }
   }

   public void unsetSupportsMultipleNontransactionalResultSets() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(SUPPORTSMULTIPLENONTRANSACTIONALRESULTSETS$130, 0);
      }
   }

   public String getBitLengthFunction() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(BITLENGTHFUNCTION$132, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetBitLengthFunction() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(BITLENGTHFUNCTION$132, 0);
         return target;
      }
   }

   public boolean isNilBitLengthFunction() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(BITLENGTHFUNCTION$132, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetBitLengthFunction() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(BITLENGTHFUNCTION$132) != 0;
      }
   }

   public void setBitLengthFunction(String bitLengthFunction) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(BITLENGTHFUNCTION$132, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(BITLENGTHFUNCTION$132);
         }

         target.setStringValue(bitLengthFunction);
      }
   }

   public void xsetBitLengthFunction(XmlString bitLengthFunction) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(BITLENGTHFUNCTION$132, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(BITLENGTHFUNCTION$132);
         }

         target.set(bitLengthFunction);
      }
   }

   public void setNilBitLengthFunction() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(BITLENGTHFUNCTION$132, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(BITLENGTHFUNCTION$132);
         }

         target.setNil();
      }
   }

   public void unsetBitLengthFunction() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(BITLENGTHFUNCTION$132, 0);
      }
   }

   public boolean getCreatePrimaryKeys() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(CREATEPRIMARYKEYS$134, 0);
         return target == null ? false : target.getBooleanValue();
      }
   }

   public XmlBoolean xgetCreatePrimaryKeys() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(CREATEPRIMARYKEYS$134, 0);
         return target;
      }
   }

   public boolean isSetCreatePrimaryKeys() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(CREATEPRIMARYKEYS$134) != 0;
      }
   }

   public void setCreatePrimaryKeys(boolean createPrimaryKeys) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(CREATEPRIMARYKEYS$134, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(CREATEPRIMARYKEYS$134);
         }

         target.setBooleanValue(createPrimaryKeys);
      }
   }

   public void xsetCreatePrimaryKeys(XmlBoolean createPrimaryKeys) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(CREATEPRIMARYKEYS$134, 0);
         if (target == null) {
            target = (XmlBoolean)this.get_store().add_element_user(CREATEPRIMARYKEYS$134);
         }

         target.set(createPrimaryKeys);
      }
   }

   public void unsetCreatePrimaryKeys() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(CREATEPRIMARYKEYS$134, 0);
      }
   }

   public String getNullTypeName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(NULLTYPENAME$136, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetNullTypeName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(NULLTYPENAME$136, 0);
         return target;
      }
   }

   public boolean isNilNullTypeName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(NULLTYPENAME$136, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetNullTypeName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(NULLTYPENAME$136) != 0;
      }
   }

   public void setNullTypeName(String nullTypeName) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(NULLTYPENAME$136, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(NULLTYPENAME$136);
         }

         target.setStringValue(nullTypeName);
      }
   }

   public void xsetNullTypeName(XmlString nullTypeName) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(NULLTYPENAME$136, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(NULLTYPENAME$136);
         }

         target.set(nullTypeName);
      }
   }

   public void setNilNullTypeName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(NULLTYPENAME$136, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(NULLTYPENAME$136);
         }

         target.setNil();
      }
   }

   public void unsetNullTypeName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(NULLTYPENAME$136, 0);
      }
   }

   public String getFloatTypeName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(FLOATTYPENAME$138, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetFloatTypeName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(FLOATTYPENAME$138, 0);
         return target;
      }
   }

   public boolean isNilFloatTypeName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(FLOATTYPENAME$138, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetFloatTypeName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(FLOATTYPENAME$138) != 0;
      }
   }

   public void setFloatTypeName(String floatTypeName) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(FLOATTYPENAME$138, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(FLOATTYPENAME$138);
         }

         target.setStringValue(floatTypeName);
      }
   }

   public void xsetFloatTypeName(XmlString floatTypeName) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(FLOATTYPENAME$138, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(FLOATTYPENAME$138);
         }

         target.set(floatTypeName);
      }
   }

   public void setNilFloatTypeName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(FLOATTYPENAME$138, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(FLOATTYPENAME$138);
         }

         target.setNil();
      }
   }

   public void unsetFloatTypeName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(FLOATTYPENAME$138, 0);
      }
   }

   public boolean getUseGetBytesForBlobs() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(USEGETBYTESFORBLOBS$140, 0);
         return target == null ? false : target.getBooleanValue();
      }
   }

   public XmlBoolean xgetUseGetBytesForBlobs() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(USEGETBYTESFORBLOBS$140, 0);
         return target;
      }
   }

   public boolean isSetUseGetBytesForBlobs() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(USEGETBYTESFORBLOBS$140) != 0;
      }
   }

   public void setUseGetBytesForBlobs(boolean useGetBytesForBlobs) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(USEGETBYTESFORBLOBS$140, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(USEGETBYTESFORBLOBS$140);
         }

         target.setBooleanValue(useGetBytesForBlobs);
      }
   }

   public void xsetUseGetBytesForBlobs(XmlBoolean useGetBytesForBlobs) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(USEGETBYTESFORBLOBS$140, 0);
         if (target == null) {
            target = (XmlBoolean)this.get_store().add_element_user(USEGETBYTESFORBLOBS$140);
         }

         target.set(useGetBytesForBlobs);
      }
   }

   public void unsetUseGetBytesForBlobs() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(USEGETBYTESFORBLOBS$140, 0);
      }
   }

   public String getTableTypes() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(TABLETYPES$142, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetTableTypes() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(TABLETYPES$142, 0);
         return target;
      }
   }

   public boolean isNilTableTypes() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(TABLETYPES$142, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetTableTypes() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(TABLETYPES$142) != 0;
      }
   }

   public void setTableTypes(String tableTypes) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(TABLETYPES$142, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(TABLETYPES$142);
         }

         target.setStringValue(tableTypes);
      }
   }

   public void xsetTableTypes(XmlString tableTypes) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(TABLETYPES$142, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(TABLETYPES$142);
         }

         target.set(tableTypes);
      }
   }

   public void setNilTableTypes() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(TABLETYPES$142, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(TABLETYPES$142);
         }

         target.setNil();
      }
   }

   public void unsetTableTypes() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(TABLETYPES$142, 0);
      }
   }

   public String getNumericTypeName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(NUMERICTYPENAME$144, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetNumericTypeName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(NUMERICTYPENAME$144, 0);
         return target;
      }
   }

   public boolean isNilNumericTypeName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(NUMERICTYPENAME$144, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetNumericTypeName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(NUMERICTYPENAME$144) != 0;
      }
   }

   public void setNumericTypeName(String numericTypeName) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(NUMERICTYPENAME$144, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(NUMERICTYPENAME$144);
         }

         target.setStringValue(numericTypeName);
      }
   }

   public void xsetNumericTypeName(XmlString numericTypeName) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(NUMERICTYPENAME$144, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(NUMERICTYPENAME$144);
         }

         target.set(numericTypeName);
      }
   }

   public void setNilNumericTypeName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(NUMERICTYPENAME$144, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(NUMERICTYPENAME$144);
         }

         target.setNil();
      }
   }

   public void unsetNumericTypeName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(NUMERICTYPENAME$144, 0);
      }
   }

   public String getTableForUpdateClause() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(TABLEFORUPDATECLAUSE$146, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetTableForUpdateClause() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(TABLEFORUPDATECLAUSE$146, 0);
         return target;
      }
   }

   public boolean isNilTableForUpdateClause() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(TABLEFORUPDATECLAUSE$146, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetTableForUpdateClause() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(TABLEFORUPDATECLAUSE$146) != 0;
      }
   }

   public void setTableForUpdateClause(String tableForUpdateClause) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(TABLEFORUPDATECLAUSE$146, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(TABLEFORUPDATECLAUSE$146);
         }

         target.setStringValue(tableForUpdateClause);
      }
   }

   public void xsetTableForUpdateClause(XmlString tableForUpdateClause) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(TABLEFORUPDATECLAUSE$146, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(TABLEFORUPDATECLAUSE$146);
         }

         target.set(tableForUpdateClause);
      }
   }

   public void setNilTableForUpdateClause() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(TABLEFORUPDATECLAUSE$146, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(TABLEFORUPDATECLAUSE$146);
         }

         target.setNil();
      }
   }

   public void unsetTableForUpdateClause() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(TABLEFORUPDATECLAUSE$146, 0);
      }
   }

   public String getIntegerTypeName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(INTEGERTYPENAME$148, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetIntegerTypeName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(INTEGERTYPENAME$148, 0);
         return target;
      }
   }

   public boolean isNilIntegerTypeName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(INTEGERTYPENAME$148, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetIntegerTypeName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(INTEGERTYPENAME$148) != 0;
      }
   }

   public void setIntegerTypeName(String integerTypeName) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(INTEGERTYPENAME$148, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(INTEGERTYPENAME$148);
         }

         target.setStringValue(integerTypeName);
      }
   }

   public void xsetIntegerTypeName(XmlString integerTypeName) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(INTEGERTYPENAME$148, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(INTEGERTYPENAME$148);
         }

         target.set(integerTypeName);
      }
   }

   public void setNilIntegerTypeName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(INTEGERTYPENAME$148, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(INTEGERTYPENAME$148);
         }

         target.setNil();
      }
   }

   public void unsetIntegerTypeName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(INTEGERTYPENAME$148, 0);
      }
   }

   public String getBlobTypeName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(BLOBTYPENAME$150, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetBlobTypeName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(BLOBTYPENAME$150, 0);
         return target;
      }
   }

   public boolean isNilBlobTypeName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(BLOBTYPENAME$150, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetBlobTypeName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(BLOBTYPENAME$150) != 0;
      }
   }

   public void setBlobTypeName(String blobTypeName) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(BLOBTYPENAME$150, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(BLOBTYPENAME$150);
         }

         target.setStringValue(blobTypeName);
      }
   }

   public void xsetBlobTypeName(XmlString blobTypeName) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(BLOBTYPENAME$150, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(BLOBTYPENAME$150);
         }

         target.set(blobTypeName);
      }
   }

   public void setNilBlobTypeName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(BLOBTYPENAME$150, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(BLOBTYPENAME$150);
         }

         target.setNil();
      }
   }

   public void unsetBlobTypeName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(BLOBTYPENAME$150, 0);
      }
   }

   public String getForUpdateClause() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(FORUPDATECLAUSE$152, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetForUpdateClause() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(FORUPDATECLAUSE$152, 0);
         return target;
      }
   }

   public boolean isNilForUpdateClause() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(FORUPDATECLAUSE$152, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetForUpdateClause() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(FORUPDATECLAUSE$152) != 0;
      }
   }

   public void setForUpdateClause(String forUpdateClause) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(FORUPDATECLAUSE$152, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(FORUPDATECLAUSE$152);
         }

         target.setStringValue(forUpdateClause);
      }
   }

   public void xsetForUpdateClause(XmlString forUpdateClause) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(FORUPDATECLAUSE$152, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(FORUPDATECLAUSE$152);
         }

         target.set(forUpdateClause);
      }
   }

   public void setNilForUpdateClause() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(FORUPDATECLAUSE$152, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(FORUPDATECLAUSE$152);
         }

         target.setNil();
      }
   }

   public void unsetForUpdateClause() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(FORUPDATECLAUSE$152, 0);
      }
   }

   public String getBooleanTypeName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(BOOLEANTYPENAME$154, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetBooleanTypeName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(BOOLEANTYPENAME$154, 0);
         return target;
      }
   }

   public boolean isNilBooleanTypeName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(BOOLEANTYPENAME$154, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetBooleanTypeName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(BOOLEANTYPENAME$154) != 0;
      }
   }

   public void setBooleanTypeName(String booleanTypeName) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(BOOLEANTYPENAME$154, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(BOOLEANTYPENAME$154);
         }

         target.setStringValue(booleanTypeName);
      }
   }

   public void xsetBooleanTypeName(XmlString booleanTypeName) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(BOOLEANTYPENAME$154, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(BOOLEANTYPENAME$154);
         }

         target.set(booleanTypeName);
      }
   }

   public void setNilBooleanTypeName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(BOOLEANTYPENAME$154, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(BOOLEANTYPENAME$154);
         }

         target.setNil();
      }
   }

   public void unsetBooleanTypeName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(BOOLEANTYPENAME$154, 0);
      }
   }

   public boolean getUseGetBestRowIdentifierForPrimaryKeys() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(USEGETBESTROWIDENTIFIERFORPRIMARYKEYS$156, 0);
         return target == null ? false : target.getBooleanValue();
      }
   }

   public XmlBoolean xgetUseGetBestRowIdentifierForPrimaryKeys() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(USEGETBESTROWIDENTIFIERFORPRIMARYKEYS$156, 0);
         return target;
      }
   }

   public boolean isSetUseGetBestRowIdentifierForPrimaryKeys() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(USEGETBESTROWIDENTIFIERFORPRIMARYKEYS$156) != 0;
      }
   }

   public void setUseGetBestRowIdentifierForPrimaryKeys(boolean useGetBestRowIdentifierForPrimaryKeys) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(USEGETBESTROWIDENTIFIERFORPRIMARYKEYS$156, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(USEGETBESTROWIDENTIFIERFORPRIMARYKEYS$156);
         }

         target.setBooleanValue(useGetBestRowIdentifierForPrimaryKeys);
      }
   }

   public void xsetUseGetBestRowIdentifierForPrimaryKeys(XmlBoolean useGetBestRowIdentifierForPrimaryKeys) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(USEGETBESTROWIDENTIFIERFORPRIMARYKEYS$156, 0);
         if (target == null) {
            target = (XmlBoolean)this.get_store().add_element_user(USEGETBESTROWIDENTIFIERFORPRIMARYKEYS$156);
         }

         target.set(useGetBestRowIdentifierForPrimaryKeys);
      }
   }

   public void unsetUseGetBestRowIdentifierForPrimaryKeys() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(USEGETBESTROWIDENTIFIERFORPRIMARYKEYS$156, 0);
      }
   }

   public boolean getSupportsForeignKeys() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(SUPPORTSFOREIGNKEYS$158, 0);
         return target == null ? false : target.getBooleanValue();
      }
   }

   public XmlBoolean xgetSupportsForeignKeys() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(SUPPORTSFOREIGNKEYS$158, 0);
         return target;
      }
   }

   public boolean isSetSupportsForeignKeys() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(SUPPORTSFOREIGNKEYS$158) != 0;
      }
   }

   public void setSupportsForeignKeys(boolean supportsForeignKeys) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(SUPPORTSFOREIGNKEYS$158, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(SUPPORTSFOREIGNKEYS$158);
         }

         target.setBooleanValue(supportsForeignKeys);
      }
   }

   public void xsetSupportsForeignKeys(XmlBoolean supportsForeignKeys) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(SUPPORTSFOREIGNKEYS$158, 0);
         if (target == null) {
            target = (XmlBoolean)this.get_store().add_element_user(SUPPORTSFOREIGNKEYS$158);
         }

         target.set(supportsForeignKeys);
      }
   }

   public void unsetSupportsForeignKeys() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(SUPPORTSFOREIGNKEYS$158, 0);
      }
   }

   public String getDropTableSql() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(DROPTABLESQL$160, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetDropTableSql() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(DROPTABLESQL$160, 0);
         return target;
      }
   }

   public boolean isNilDropTableSql() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(DROPTABLESQL$160, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetDropTableSql() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(DROPTABLESQL$160) != 0;
      }
   }

   public void setDropTableSql(String dropTableSql) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(DROPTABLESQL$160, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(DROPTABLESQL$160);
         }

         target.setStringValue(dropTableSql);
      }
   }

   public void xsetDropTableSql(XmlString dropTableSql) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(DROPTABLESQL$160, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(DROPTABLESQL$160);
         }

         target.set(dropTableSql);
      }
   }

   public void setNilDropTableSql() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(DROPTABLESQL$160, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(DROPTABLESQL$160);
         }

         target.setNil();
      }
   }

   public void unsetDropTableSql() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(DROPTABLESQL$160, 0);
      }
   }

   public boolean getUseSetStringForClobs() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(USESETSTRINGFORCLOBS$162, 0);
         return target == null ? false : target.getBooleanValue();
      }
   }

   public XmlBoolean xgetUseSetStringForClobs() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(USESETSTRINGFORCLOBS$162, 0);
         return target;
      }
   }

   public boolean isSetUseSetStringForClobs() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(USESETSTRINGFORCLOBS$162) != 0;
      }
   }

   public void setUseSetStringForClobs(boolean useSetStringForClobs) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(USESETSTRINGFORCLOBS$162, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(USESETSTRINGFORCLOBS$162);
         }

         target.setBooleanValue(useSetStringForClobs);
      }
   }

   public void xsetUseSetStringForClobs(XmlBoolean useSetStringForClobs) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(USESETSTRINGFORCLOBS$162, 0);
         if (target == null) {
            target = (XmlBoolean)this.get_store().add_element_user(USESETSTRINGFORCLOBS$162);
         }

         target.set(useSetStringForClobs);
      }
   }

   public void unsetUseSetStringForClobs() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(USESETSTRINGFORCLOBS$162, 0);
      }
   }

   public boolean getSupportsLockingWithOrderClause() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(SUPPORTSLOCKINGWITHORDERCLAUSE$164, 0);
         return target == null ? false : target.getBooleanValue();
      }
   }

   public XmlBoolean xgetSupportsLockingWithOrderClause() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(SUPPORTSLOCKINGWITHORDERCLAUSE$164, 0);
         return target;
      }
   }

   public boolean isSetSupportsLockingWithOrderClause() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(SUPPORTSLOCKINGWITHORDERCLAUSE$164) != 0;
      }
   }

   public void setSupportsLockingWithOrderClause(boolean supportsLockingWithOrderClause) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(SUPPORTSLOCKINGWITHORDERCLAUSE$164, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(SUPPORTSLOCKINGWITHORDERCLAUSE$164);
         }

         target.setBooleanValue(supportsLockingWithOrderClause);
      }
   }

   public void xsetSupportsLockingWithOrderClause(XmlBoolean supportsLockingWithOrderClause) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(SUPPORTSLOCKINGWITHORDERCLAUSE$164, 0);
         if (target == null) {
            target = (XmlBoolean)this.get_store().add_element_user(SUPPORTSLOCKINGWITHORDERCLAUSE$164);
         }

         target.set(supportsLockingWithOrderClause);
      }
   }

   public void unsetSupportsLockingWithOrderClause() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(SUPPORTSLOCKINGWITHORDERCLAUSE$164, 0);
      }
   }

   public String getPlatform() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(PLATFORM$166, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetPlatform() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(PLATFORM$166, 0);
         return target;
      }
   }

   public boolean isNilPlatform() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(PLATFORM$166, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetPlatform() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(PLATFORM$166) != 0;
      }
   }

   public void setPlatform(String platform) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(PLATFORM$166, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(PLATFORM$166);
         }

         target.setStringValue(platform);
      }
   }

   public void xsetPlatform(XmlString platform) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(PLATFORM$166, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(PLATFORM$166);
         }

         target.set(platform);
      }
   }

   public void setNilPlatform() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(PLATFORM$166, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(PLATFORM$166);
         }

         target.setNil();
      }
   }

   public void unsetPlatform() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(PLATFORM$166, 0);
      }
   }

   public String getFixedSizeTypeNames() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(FIXEDSIZETYPENAMES$168, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetFixedSizeTypeNames() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(FIXEDSIZETYPENAMES$168, 0);
         return target;
      }
   }

   public boolean isNilFixedSizeTypeNames() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(FIXEDSIZETYPENAMES$168, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetFixedSizeTypeNames() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(FIXEDSIZETYPENAMES$168) != 0;
      }
   }

   public void setFixedSizeTypeNames(String fixedSizeTypeNames) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(FIXEDSIZETYPENAMES$168, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(FIXEDSIZETYPENAMES$168);
         }

         target.setStringValue(fixedSizeTypeNames);
      }
   }

   public void xsetFixedSizeTypeNames(XmlString fixedSizeTypeNames) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(FIXEDSIZETYPENAMES$168, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(FIXEDSIZETYPENAMES$168);
         }

         target.set(fixedSizeTypeNames);
      }
   }

   public void setNilFixedSizeTypeNames() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(FIXEDSIZETYPENAMES$168, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(FIXEDSIZETYPENAMES$168);
         }

         target.setNil();
      }
   }

   public void unsetFixedSizeTypeNames() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(FIXEDSIZETYPENAMES$168, 0);
      }
   }

   public boolean getStoreCharsAsNumbers() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(STORECHARSASNUMBERS$170, 0);
         return target == null ? false : target.getBooleanValue();
      }
   }

   public XmlBoolean xgetStoreCharsAsNumbers() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(STORECHARSASNUMBERS$170, 0);
         return target;
      }
   }

   public boolean isSetStoreCharsAsNumbers() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(STORECHARSASNUMBERS$170) != 0;
      }
   }

   public void setStoreCharsAsNumbers(boolean storeCharsAsNumbers) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(STORECHARSASNUMBERS$170, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(STORECHARSASNUMBERS$170);
         }

         target.setBooleanValue(storeCharsAsNumbers);
      }
   }

   public void xsetStoreCharsAsNumbers(XmlBoolean storeCharsAsNumbers) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(STORECHARSASNUMBERS$170, 0);
         if (target == null) {
            target = (XmlBoolean)this.get_store().add_element_user(STORECHARSASNUMBERS$170);
         }

         target.set(storeCharsAsNumbers);
      }
   }

   public void unsetStoreCharsAsNumbers() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(STORECHARSASNUMBERS$170, 0);
      }
   }

   public int getMaxIndexesPerTable() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(MAXINDEXESPERTABLE$172, 0);
         return target == null ? 0 : target.getIntValue();
      }
   }

   public XmlInt xgetMaxIndexesPerTable() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlInt target = null;
         target = (XmlInt)this.get_store().find_element_user(MAXINDEXESPERTABLE$172, 0);
         return target;
      }
   }

   public boolean isSetMaxIndexesPerTable() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(MAXINDEXESPERTABLE$172) != 0;
      }
   }

   public void setMaxIndexesPerTable(int maxIndexesPerTable) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(MAXINDEXESPERTABLE$172, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(MAXINDEXESPERTABLE$172);
         }

         target.setIntValue(maxIndexesPerTable);
      }
   }

   public void xsetMaxIndexesPerTable(XmlInt maxIndexesPerTable) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlInt target = null;
         target = (XmlInt)this.get_store().find_element_user(MAXINDEXESPERTABLE$172, 0);
         if (target == null) {
            target = (XmlInt)this.get_store().add_element_user(MAXINDEXESPERTABLE$172);
         }

         target.set(maxIndexesPerTable);
      }
   }

   public void unsetMaxIndexesPerTable() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(MAXINDEXESPERTABLE$172, 0);
      }
   }

   public boolean getRequiresCastForComparisons() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(REQUIRESCASTFORCOMPARISONS$174, 0);
         return target == null ? false : target.getBooleanValue();
      }
   }

   public XmlBoolean xgetRequiresCastForComparisons() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(REQUIRESCASTFORCOMPARISONS$174, 0);
         return target;
      }
   }

   public boolean isSetRequiresCastForComparisons() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(REQUIRESCASTFORCOMPARISONS$174) != 0;
      }
   }

   public void setRequiresCastForComparisons(boolean requiresCastForComparisons) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(REQUIRESCASTFORCOMPARISONS$174, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(REQUIRESCASTFORCOMPARISONS$174);
         }

         target.setBooleanValue(requiresCastForComparisons);
      }
   }

   public void xsetRequiresCastForComparisons(XmlBoolean requiresCastForComparisons) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(REQUIRESCASTFORCOMPARISONS$174, 0);
         if (target == null) {
            target = (XmlBoolean)this.get_store().add_element_user(REQUIRESCASTFORCOMPARISONS$174);
         }

         target.set(requiresCastForComparisons);
      }
   }

   public void unsetRequiresCastForComparisons() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(REQUIRESCASTFORCOMPARISONS$174, 0);
      }
   }

   public boolean getSupportsHaving() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(SUPPORTSHAVING$176, 0);
         return target == null ? false : target.getBooleanValue();
      }
   }

   public XmlBoolean xgetSupportsHaving() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(SUPPORTSHAVING$176, 0);
         return target;
      }
   }

   public boolean isSetSupportsHaving() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(SUPPORTSHAVING$176) != 0;
      }
   }

   public void setSupportsHaving(boolean supportsHaving) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(SUPPORTSHAVING$176, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(SUPPORTSHAVING$176);
         }

         target.setBooleanValue(supportsHaving);
      }
   }

   public void xsetSupportsHaving(XmlBoolean supportsHaving) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(SUPPORTSHAVING$176, 0);
         if (target == null) {
            target = (XmlBoolean)this.get_store().add_element_user(SUPPORTSHAVING$176);
         }

         target.set(supportsHaving);
      }
   }

   public void unsetSupportsHaving() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(SUPPORTSHAVING$176, 0);
      }
   }

   public boolean getSupportsLockingWithOuterJoin() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(SUPPORTSLOCKINGWITHOUTERJOIN$178, 0);
         return target == null ? false : target.getBooleanValue();
      }
   }

   public XmlBoolean xgetSupportsLockingWithOuterJoin() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(SUPPORTSLOCKINGWITHOUTERJOIN$178, 0);
         return target;
      }
   }

   public boolean isSetSupportsLockingWithOuterJoin() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(SUPPORTSLOCKINGWITHOUTERJOIN$178) != 0;
      }
   }

   public void setSupportsLockingWithOuterJoin(boolean supportsLockingWithOuterJoin) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(SUPPORTSLOCKINGWITHOUTERJOIN$178, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(SUPPORTSLOCKINGWITHOUTERJOIN$178);
         }

         target.setBooleanValue(supportsLockingWithOuterJoin);
      }
   }

   public void xsetSupportsLockingWithOuterJoin(XmlBoolean supportsLockingWithOuterJoin) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(SUPPORTSLOCKINGWITHOUTERJOIN$178, 0);
         if (target == null) {
            target = (XmlBoolean)this.get_store().add_element_user(SUPPORTSLOCKINGWITHOUTERJOIN$178);
         }

         target.set(supportsLockingWithOuterJoin);
      }
   }

   public void unsetSupportsLockingWithOuterJoin() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(SUPPORTSLOCKINGWITHOUTERJOIN$178, 0);
      }
   }

   public boolean getSupportsCorrelatedSubselect() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(SUPPORTSCORRELATEDSUBSELECT$180, 0);
         return target == null ? false : target.getBooleanValue();
      }
   }

   public XmlBoolean xgetSupportsCorrelatedSubselect() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(SUPPORTSCORRELATEDSUBSELECT$180, 0);
         return target;
      }
   }

   public boolean isSetSupportsCorrelatedSubselect() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(SUPPORTSCORRELATEDSUBSELECT$180) != 0;
      }
   }

   public void setSupportsCorrelatedSubselect(boolean supportsCorrelatedSubselect) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(SUPPORTSCORRELATEDSUBSELECT$180, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(SUPPORTSCORRELATEDSUBSELECT$180);
         }

         target.setBooleanValue(supportsCorrelatedSubselect);
      }
   }

   public void xsetSupportsCorrelatedSubselect(XmlBoolean supportsCorrelatedSubselect) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(SUPPORTSCORRELATEDSUBSELECT$180, 0);
         if (target == null) {
            target = (XmlBoolean)this.get_store().add_element_user(SUPPORTSCORRELATEDSUBSELECT$180);
         }

         target.set(supportsCorrelatedSubselect);
      }
   }

   public void unsetSupportsCorrelatedSubselect() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(SUPPORTSCORRELATEDSUBSELECT$180, 0);
      }
   }

   public boolean getSupportsNullTableForGetImportedKeys() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(SUPPORTSNULLTABLEFORGETIMPORTEDKEYS$182, 0);
         return target == null ? false : target.getBooleanValue();
      }
   }

   public XmlBoolean xgetSupportsNullTableForGetImportedKeys() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(SUPPORTSNULLTABLEFORGETIMPORTEDKEYS$182, 0);
         return target;
      }
   }

   public boolean isSetSupportsNullTableForGetImportedKeys() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(SUPPORTSNULLTABLEFORGETIMPORTEDKEYS$182) != 0;
      }
   }

   public void setSupportsNullTableForGetImportedKeys(boolean supportsNullTableForGetImportedKeys) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(SUPPORTSNULLTABLEFORGETIMPORTEDKEYS$182, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(SUPPORTSNULLTABLEFORGETIMPORTEDKEYS$182);
         }

         target.setBooleanValue(supportsNullTableForGetImportedKeys);
      }
   }

   public void xsetSupportsNullTableForGetImportedKeys(XmlBoolean supportsNullTableForGetImportedKeys) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(SUPPORTSNULLTABLEFORGETIMPORTEDKEYS$182, 0);
         if (target == null) {
            target = (XmlBoolean)this.get_store().add_element_user(SUPPORTSNULLTABLEFORGETIMPORTEDKEYS$182);
         }

         target.set(supportsNullTableForGetImportedKeys);
      }
   }

   public void unsetSupportsNullTableForGetImportedKeys() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(SUPPORTSNULLTABLEFORGETIMPORTEDKEYS$182, 0);
      }
   }

   public String getBigintTypeName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(BIGINTTYPENAME$184, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetBigintTypeName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(BIGINTTYPENAME$184, 0);
         return target;
      }
   }

   public boolean isNilBigintTypeName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(BIGINTTYPENAME$184, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetBigintTypeName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(BIGINTTYPENAME$184) != 0;
      }
   }

   public void setBigintTypeName(String bigintTypeName) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(BIGINTTYPENAME$184, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(BIGINTTYPENAME$184);
         }

         target.setStringValue(bigintTypeName);
      }
   }

   public void xsetBigintTypeName(XmlString bigintTypeName) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(BIGINTTYPENAME$184, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(BIGINTTYPENAME$184);
         }

         target.set(bigintTypeName);
      }
   }

   public void setNilBigintTypeName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(BIGINTTYPENAME$184, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(BIGINTTYPENAME$184);
         }

         target.setNil();
      }
   }

   public void unsetBigintTypeName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(BIGINTTYPENAME$184, 0);
      }
   }

   public String getLastGeneratedKeyQuery() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(LASTGENERATEDKEYQUERY$186, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetLastGeneratedKeyQuery() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(LASTGENERATEDKEYQUERY$186, 0);
         return target;
      }
   }

   public boolean isNilLastGeneratedKeyQuery() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(LASTGENERATEDKEYQUERY$186, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetLastGeneratedKeyQuery() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(LASTGENERATEDKEYQUERY$186) != 0;
      }
   }

   public void setLastGeneratedKeyQuery(String lastGeneratedKeyQuery) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(LASTGENERATEDKEYQUERY$186, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(LASTGENERATEDKEYQUERY$186);
         }

         target.setStringValue(lastGeneratedKeyQuery);
      }
   }

   public void xsetLastGeneratedKeyQuery(XmlString lastGeneratedKeyQuery) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(LASTGENERATEDKEYQUERY$186, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(LASTGENERATEDKEYQUERY$186);
         }

         target.set(lastGeneratedKeyQuery);
      }
   }

   public void setNilLastGeneratedKeyQuery() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(LASTGENERATEDKEYQUERY$186, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(LASTGENERATEDKEYQUERY$186);
         }

         target.setNil();
      }
   }

   public void unsetLastGeneratedKeyQuery() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(LASTGENERATEDKEYQUERY$186, 0);
      }
   }

   public String getReservedWords() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(RESERVEDWORDS$188, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetReservedWords() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(RESERVEDWORDS$188, 0);
         return target;
      }
   }

   public boolean isNilReservedWords() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(RESERVEDWORDS$188, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetReservedWords() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(RESERVEDWORDS$188) != 0;
      }
   }

   public void setReservedWords(String reservedWords) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(RESERVEDWORDS$188, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(RESERVEDWORDS$188);
         }

         target.setStringValue(reservedWords);
      }
   }

   public void xsetReservedWords(XmlString reservedWords) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(RESERVEDWORDS$188, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(RESERVEDWORDS$188);
         }

         target.set(reservedWords);
      }
   }

   public void setNilReservedWords() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(RESERVEDWORDS$188, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(RESERVEDWORDS$188);
         }

         target.setNil();
      }
   }

   public void unsetReservedWords() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(RESERVEDWORDS$188, 0);
      }
   }

   public boolean getSupportsNullUpdateAction() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(SUPPORTSNULLUPDATEACTION$190, 0);
         return target == null ? false : target.getBooleanValue();
      }
   }

   public XmlBoolean xgetSupportsNullUpdateAction() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(SUPPORTSNULLUPDATEACTION$190, 0);
         return target;
      }
   }

   public boolean isSetSupportsNullUpdateAction() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(SUPPORTSNULLUPDATEACTION$190) != 0;
      }
   }

   public void setSupportsNullUpdateAction(boolean supportsNullUpdateAction) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(SUPPORTSNULLUPDATEACTION$190, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(SUPPORTSNULLUPDATEACTION$190);
         }

         target.setBooleanValue(supportsNullUpdateAction);
      }
   }

   public void xsetSupportsNullUpdateAction(XmlBoolean supportsNullUpdateAction) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(SUPPORTSNULLUPDATEACTION$190, 0);
         if (target == null) {
            target = (XmlBoolean)this.get_store().add_element_user(SUPPORTSNULLUPDATEACTION$190);
         }

         target.set(supportsNullUpdateAction);
      }
   }

   public void unsetSupportsNullUpdateAction() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(SUPPORTSNULLUPDATEACTION$190, 0);
      }
   }

   public boolean getUseSchemaName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(USESCHEMANAME$192, 0);
         return target == null ? false : target.getBooleanValue();
      }
   }

   public XmlBoolean xgetUseSchemaName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(USESCHEMANAME$192, 0);
         return target;
      }
   }

   public boolean isSetUseSchemaName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(USESCHEMANAME$192) != 0;
      }
   }

   public void setUseSchemaName(boolean useSchemaName) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(USESCHEMANAME$192, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(USESCHEMANAME$192);
         }

         target.setBooleanValue(useSchemaName);
      }
   }

   public void xsetUseSchemaName(XmlBoolean useSchemaName) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(USESCHEMANAME$192, 0);
         if (target == null) {
            target = (XmlBoolean)this.get_store().add_element_user(USESCHEMANAME$192);
         }

         target.set(useSchemaName);
      }
   }

   public void unsetUseSchemaName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(USESCHEMANAME$192, 0);
      }
   }

   public boolean getSupportsDeferredConstraints() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(SUPPORTSDEFERREDCONSTRAINTS$194, 0);
         return target == null ? false : target.getBooleanValue();
      }
   }

   public XmlBoolean xgetSupportsDeferredConstraints() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(SUPPORTSDEFERREDCONSTRAINTS$194, 0);
         return target;
      }
   }

   public boolean isSetSupportsDeferredConstraints() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(SUPPORTSDEFERREDCONSTRAINTS$194) != 0;
      }
   }

   public void setSupportsDeferredConstraints(boolean supportsDeferredConstraints) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(SUPPORTSDEFERREDCONSTRAINTS$194, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(SUPPORTSDEFERREDCONSTRAINTS$194);
         }

         target.setBooleanValue(supportsDeferredConstraints);
      }
   }

   public void xsetSupportsDeferredConstraints(XmlBoolean supportsDeferredConstraints) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(SUPPORTSDEFERREDCONSTRAINTS$194, 0);
         if (target == null) {
            target = (XmlBoolean)this.get_store().add_element_user(SUPPORTSDEFERREDCONSTRAINTS$194);
         }

         target.set(supportsDeferredConstraints);
      }
   }

   public void unsetSupportsDeferredConstraints() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(SUPPORTSDEFERREDCONSTRAINTS$194, 0);
      }
   }

   public String getRealTypeName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(REALTYPENAME$196, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetRealTypeName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(REALTYPENAME$196, 0);
         return target;
      }
   }

   public boolean isNilRealTypeName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(REALTYPENAME$196, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetRealTypeName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(REALTYPENAME$196) != 0;
      }
   }

   public void setRealTypeName(String realTypeName) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(REALTYPENAME$196, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(REALTYPENAME$196);
         }

         target.setStringValue(realTypeName);
      }
   }

   public void xsetRealTypeName(XmlString realTypeName) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(REALTYPENAME$196, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(REALTYPENAME$196);
         }

         target.set(realTypeName);
      }
   }

   public void setNilRealTypeName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(REALTYPENAME$196, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(REALTYPENAME$196);
         }

         target.setNil();
      }
   }

   public void unsetRealTypeName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(REALTYPENAME$196, 0);
      }
   }

   public boolean getRequiresAliasForSubselect() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(REQUIRESALIASFORSUBSELECT$198, 0);
         return target == null ? false : target.getBooleanValue();
      }
   }

   public XmlBoolean xgetRequiresAliasForSubselect() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(REQUIRESALIASFORSUBSELECT$198, 0);
         return target;
      }
   }

   public boolean isSetRequiresAliasForSubselect() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(REQUIRESALIASFORSUBSELECT$198) != 0;
      }
   }

   public void setRequiresAliasForSubselect(boolean requiresAliasForSubselect) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(REQUIRESALIASFORSUBSELECT$198, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(REQUIRESALIASFORSUBSELECT$198);
         }

         target.setBooleanValue(requiresAliasForSubselect);
      }
   }

   public void xsetRequiresAliasForSubselect(XmlBoolean requiresAliasForSubselect) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(REQUIRESALIASFORSUBSELECT$198, 0);
         if (target == null) {
            target = (XmlBoolean)this.get_store().add_element_user(REQUIRESALIASFORSUBSELECT$198);
         }

         target.set(requiresAliasForSubselect);
      }
   }

   public void unsetRequiresAliasForSubselect() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(REQUIRESALIASFORSUBSELECT$198, 0);
      }
   }

   public boolean getSupportsNullTableForGetIndexInfo() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(SUPPORTSNULLTABLEFORGETINDEXINFO$200, 0);
         return target == null ? false : target.getBooleanValue();
      }
   }

   public XmlBoolean xgetSupportsNullTableForGetIndexInfo() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(SUPPORTSNULLTABLEFORGETINDEXINFO$200, 0);
         return target;
      }
   }

   public boolean isSetSupportsNullTableForGetIndexInfo() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(SUPPORTSNULLTABLEFORGETINDEXINFO$200) != 0;
      }
   }

   public void setSupportsNullTableForGetIndexInfo(boolean supportsNullTableForGetIndexInfo) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(SUPPORTSNULLTABLEFORGETINDEXINFO$200, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(SUPPORTSNULLTABLEFORGETINDEXINFO$200);
         }

         target.setBooleanValue(supportsNullTableForGetIndexInfo);
      }
   }

   public void xsetSupportsNullTableForGetIndexInfo(XmlBoolean supportsNullTableForGetIndexInfo) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(SUPPORTSNULLTABLEFORGETINDEXINFO$200, 0);
         if (target == null) {
            target = (XmlBoolean)this.get_store().add_element_user(SUPPORTSNULLTABLEFORGETINDEXINFO$200);
         }

         target.set(supportsNullTableForGetIndexInfo);
      }
   }

   public void unsetSupportsNullTableForGetIndexInfo() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(SUPPORTSNULLTABLEFORGETINDEXINFO$200, 0);
      }
   }

   public String getTrimTrailingFunction() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(TRIMTRAILINGFUNCTION$202, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetTrimTrailingFunction() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(TRIMTRAILINGFUNCTION$202, 0);
         return target;
      }
   }

   public boolean isNilTrimTrailingFunction() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(TRIMTRAILINGFUNCTION$202, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetTrimTrailingFunction() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(TRIMTRAILINGFUNCTION$202) != 0;
      }
   }

   public void setTrimTrailingFunction(String trimTrailingFunction) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(TRIMTRAILINGFUNCTION$202, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(TRIMTRAILINGFUNCTION$202);
         }

         target.setStringValue(trimTrailingFunction);
      }
   }

   public void xsetTrimTrailingFunction(XmlString trimTrailingFunction) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(TRIMTRAILINGFUNCTION$202, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(TRIMTRAILINGFUNCTION$202);
         }

         target.set(trimTrailingFunction);
      }
   }

   public void setNilTrimTrailingFunction() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(TRIMTRAILINGFUNCTION$202, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(TRIMTRAILINGFUNCTION$202);
         }

         target.setNil();
      }
   }

   public void unsetTrimTrailingFunction() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(TRIMTRAILINGFUNCTION$202, 0);
      }
   }

   public boolean getSupportsLockingWithSelectRange() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(SUPPORTSLOCKINGWITHSELECTRANGE$204, 0);
         return target == null ? false : target.getBooleanValue();
      }
   }

   public XmlBoolean xgetSupportsLockingWithSelectRange() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(SUPPORTSLOCKINGWITHSELECTRANGE$204, 0);
         return target;
      }
   }

   public boolean isSetSupportsLockingWithSelectRange() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(SUPPORTSLOCKINGWITHSELECTRANGE$204) != 0;
      }
   }

   public void setSupportsLockingWithSelectRange(boolean supportsLockingWithSelectRange) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(SUPPORTSLOCKINGWITHSELECTRANGE$204, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(SUPPORTSLOCKINGWITHSELECTRANGE$204);
         }

         target.setBooleanValue(supportsLockingWithSelectRange);
      }
   }

   public void xsetSupportsLockingWithSelectRange(XmlBoolean supportsLockingWithSelectRange) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(SUPPORTSLOCKINGWITHSELECTRANGE$204, 0);
         if (target == null) {
            target = (XmlBoolean)this.get_store().add_element_user(SUPPORTSLOCKINGWITHSELECTRANGE$204);
         }

         target.set(supportsLockingWithSelectRange);
      }
   }

   public void unsetSupportsLockingWithSelectRange() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(SUPPORTSLOCKINGWITHSELECTRANGE$204, 0);
      }
   }

   public boolean getStorageLimitationsFatal() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(STORAGELIMITATIONSFATAL$206, 0);
         return target == null ? false : target.getBooleanValue();
      }
   }

   public XmlBoolean xgetStorageLimitationsFatal() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(STORAGELIMITATIONSFATAL$206, 0);
         return target;
      }
   }

   public boolean isSetStorageLimitationsFatal() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(STORAGELIMITATIONSFATAL$206) != 0;
      }
   }

   public void setStorageLimitationsFatal(boolean storageLimitationsFatal) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(STORAGELIMITATIONSFATAL$206, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(STORAGELIMITATIONSFATAL$206);
         }

         target.setBooleanValue(storageLimitationsFatal);
      }
   }

   public void xsetStorageLimitationsFatal(XmlBoolean storageLimitationsFatal) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(STORAGELIMITATIONSFATAL$206, 0);
         if (target == null) {
            target = (XmlBoolean)this.get_store().add_element_user(STORAGELIMITATIONSFATAL$206);
         }

         target.set(storageLimitationsFatal);
      }
   }

   public void unsetStorageLimitationsFatal() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(STORAGELIMITATIONSFATAL$206, 0);
      }
   }

   public boolean getSupportsLockingWithInnerJoin() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(SUPPORTSLOCKINGWITHINNERJOIN$208, 0);
         return target == null ? false : target.getBooleanValue();
      }
   }

   public XmlBoolean xgetSupportsLockingWithInnerJoin() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(SUPPORTSLOCKINGWITHINNERJOIN$208, 0);
         return target;
      }
   }

   public boolean isSetSupportsLockingWithInnerJoin() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(SUPPORTSLOCKINGWITHINNERJOIN$208) != 0;
      }
   }

   public void setSupportsLockingWithInnerJoin(boolean supportsLockingWithInnerJoin) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(SUPPORTSLOCKINGWITHINNERJOIN$208, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(SUPPORTSLOCKINGWITHINNERJOIN$208);
         }

         target.setBooleanValue(supportsLockingWithInnerJoin);
      }
   }

   public void xsetSupportsLockingWithInnerJoin(XmlBoolean supportsLockingWithInnerJoin) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(SUPPORTSLOCKINGWITHINNERJOIN$208, 0);
         if (target == null) {
            target = (XmlBoolean)this.get_store().add_element_user(SUPPORTSLOCKINGWITHINNERJOIN$208);
         }

         target.set(supportsLockingWithInnerJoin);
      }
   }

   public void unsetSupportsLockingWithInnerJoin() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(SUPPORTSLOCKINGWITHINNERJOIN$208, 0);
      }
   }

   public String getCurrentTimestampFunction() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(CURRENTTIMESTAMPFUNCTION$210, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetCurrentTimestampFunction() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(CURRENTTIMESTAMPFUNCTION$210, 0);
         return target;
      }
   }

   public boolean isNilCurrentTimestampFunction() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(CURRENTTIMESTAMPFUNCTION$210, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetCurrentTimestampFunction() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(CURRENTTIMESTAMPFUNCTION$210) != 0;
      }
   }

   public void setCurrentTimestampFunction(String currentTimestampFunction) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(CURRENTTIMESTAMPFUNCTION$210, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(CURRENTTIMESTAMPFUNCTION$210);
         }

         target.setStringValue(currentTimestampFunction);
      }
   }

   public void xsetCurrentTimestampFunction(XmlString currentTimestampFunction) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(CURRENTTIMESTAMPFUNCTION$210, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(CURRENTTIMESTAMPFUNCTION$210);
         }

         target.set(currentTimestampFunction);
      }
   }

   public void setNilCurrentTimestampFunction() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(CURRENTTIMESTAMPFUNCTION$210, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(CURRENTTIMESTAMPFUNCTION$210);
         }

         target.setNil();
      }
   }

   public void unsetCurrentTimestampFunction() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(CURRENTTIMESTAMPFUNCTION$210, 0);
      }
   }

   public String getCastFunction() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(CASTFUNCTION$212, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetCastFunction() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(CASTFUNCTION$212, 0);
         return target;
      }
   }

   public boolean isNilCastFunction() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(CASTFUNCTION$212, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetCastFunction() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(CASTFUNCTION$212) != 0;
      }
   }

   public void setCastFunction(String castFunction) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(CASTFUNCTION$212, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(CASTFUNCTION$212);
         }

         target.setStringValue(castFunction);
      }
   }

   public void xsetCastFunction(XmlString castFunction) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(CASTFUNCTION$212, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(CASTFUNCTION$212);
         }

         target.set(castFunction);
      }
   }

   public void setNilCastFunction() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(CASTFUNCTION$212, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(CASTFUNCTION$212);
         }

         target.setNil();
      }
   }

   public void unsetCastFunction() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(CASTFUNCTION$212, 0);
      }
   }

   public String getOtherTypeName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(OTHERTYPENAME$214, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetOtherTypeName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(OTHERTYPENAME$214, 0);
         return target;
      }
   }

   public boolean isNilOtherTypeName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(OTHERTYPENAME$214, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetOtherTypeName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(OTHERTYPENAME$214) != 0;
      }
   }

   public void setOtherTypeName(String otherTypeName) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(OTHERTYPENAME$214, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(OTHERTYPENAME$214);
         }

         target.setStringValue(otherTypeName);
      }
   }

   public void xsetOtherTypeName(XmlString otherTypeName) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(OTHERTYPENAME$214, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(OTHERTYPENAME$214);
         }

         target.set(otherTypeName);
      }
   }

   public void setNilOtherTypeName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(OTHERTYPENAME$214, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(OTHERTYPENAME$214);
         }

         target.setNil();
      }
   }

   public void unsetOtherTypeName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(OTHERTYPENAME$214, 0);
      }
   }

   public int getMaxIndexNameLength() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(MAXINDEXNAMELENGTH$216, 0);
         return target == null ? 0 : target.getIntValue();
      }
   }

   public XmlInt xgetMaxIndexNameLength() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlInt target = null;
         target = (XmlInt)this.get_store().find_element_user(MAXINDEXNAMELENGTH$216, 0);
         return target;
      }
   }

   public boolean isSetMaxIndexNameLength() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(MAXINDEXNAMELENGTH$216) != 0;
      }
   }

   public void setMaxIndexNameLength(int maxIndexNameLength) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(MAXINDEXNAMELENGTH$216, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(MAXINDEXNAMELENGTH$216);
         }

         target.setIntValue(maxIndexNameLength);
      }
   }

   public void xsetMaxIndexNameLength(XmlInt maxIndexNameLength) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlInt target = null;
         target = (XmlInt)this.get_store().find_element_user(MAXINDEXNAMELENGTH$216, 0);
         if (target == null) {
            target = (XmlInt)this.get_store().add_element_user(MAXINDEXNAMELENGTH$216);
         }

         target.set(maxIndexNameLength);
      }
   }

   public void unsetMaxIndexNameLength() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(MAXINDEXNAMELENGTH$216, 0);
      }
   }

   public String getDistinctTypeName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(DISTINCTTYPENAME$218, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetDistinctTypeName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(DISTINCTTYPENAME$218, 0);
         return target;
      }
   }

   public boolean isNilDistinctTypeName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(DISTINCTTYPENAME$218, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetDistinctTypeName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(DISTINCTTYPENAME$218) != 0;
      }
   }

   public void setDistinctTypeName(String distinctTypeName) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(DISTINCTTYPENAME$218, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(DISTINCTTYPENAME$218);
         }

         target.setStringValue(distinctTypeName);
      }
   }

   public void xsetDistinctTypeName(XmlString distinctTypeName) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(DISTINCTTYPENAME$218, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(DISTINCTTYPENAME$218);
         }

         target.set(distinctTypeName);
      }
   }

   public void setNilDistinctTypeName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(DISTINCTTYPENAME$218, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(DISTINCTTYPENAME$218);
         }

         target.setNil();
      }
   }

   public void unsetDistinctTypeName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(DISTINCTTYPENAME$218, 0);
      }
   }

   public int getCharacterColumnSize() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(CHARACTERCOLUMNSIZE$220, 0);
         return target == null ? 0 : target.getIntValue();
      }
   }

   public XmlInt xgetCharacterColumnSize() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlInt target = null;
         target = (XmlInt)this.get_store().find_element_user(CHARACTERCOLUMNSIZE$220, 0);
         return target;
      }
   }

   public boolean isSetCharacterColumnSize() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(CHARACTERCOLUMNSIZE$220) != 0;
      }
   }

   public void setCharacterColumnSize(int characterColumnSize) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(CHARACTERCOLUMNSIZE$220, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(CHARACTERCOLUMNSIZE$220);
         }

         target.setIntValue(characterColumnSize);
      }
   }

   public void xsetCharacterColumnSize(XmlInt characterColumnSize) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlInt target = null;
         target = (XmlInt)this.get_store().find_element_user(CHARACTERCOLUMNSIZE$220, 0);
         if (target == null) {
            target = (XmlInt)this.get_store().add_element_user(CHARACTERCOLUMNSIZE$220);
         }

         target.set(characterColumnSize);
      }
   }

   public void unsetCharacterColumnSize() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(CHARACTERCOLUMNSIZE$220, 0);
      }
   }

   public String getVarbinaryTypeName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(VARBINARYTYPENAME$222, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetVarbinaryTypeName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(VARBINARYTYPENAME$222, 0);
         return target;
      }
   }

   public boolean isNilVarbinaryTypeName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(VARBINARYTYPENAME$222, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetVarbinaryTypeName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(VARBINARYTYPENAME$222) != 0;
      }
   }

   public void setVarbinaryTypeName(String varbinaryTypeName) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(VARBINARYTYPENAME$222, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(VARBINARYTYPENAME$222);
         }

         target.setStringValue(varbinaryTypeName);
      }
   }

   public void xsetVarbinaryTypeName(XmlString varbinaryTypeName) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(VARBINARYTYPENAME$222, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(VARBINARYTYPENAME$222);
         }

         target.set(varbinaryTypeName);
      }
   }

   public void setNilVarbinaryTypeName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(VARBINARYTYPENAME$222, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(VARBINARYTYPENAME$222);
         }

         target.setNil();
      }
   }

   public void unsetVarbinaryTypeName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(VARBINARYTYPENAME$222, 0);
      }
   }

   public int getMaxTableNameLength() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(MAXTABLENAMELENGTH$224, 0);
         return target == null ? 0 : target.getIntValue();
      }
   }

   public XmlInt xgetMaxTableNameLength() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlInt target = null;
         target = (XmlInt)this.get_store().find_element_user(MAXTABLENAMELENGTH$224, 0);
         return target;
      }
   }

   public boolean isSetMaxTableNameLength() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(MAXTABLENAMELENGTH$224) != 0;
      }
   }

   public void setMaxTableNameLength(int maxTableNameLength) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(MAXTABLENAMELENGTH$224, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(MAXTABLENAMELENGTH$224);
         }

         target.setIntValue(maxTableNameLength);
      }
   }

   public void xsetMaxTableNameLength(XmlInt maxTableNameLength) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlInt target = null;
         target = (XmlInt)this.get_store().find_element_user(MAXTABLENAMELENGTH$224, 0);
         if (target == null) {
            target = (XmlInt)this.get_store().add_element_user(MAXTABLENAMELENGTH$224);
         }

         target.set(maxTableNameLength);
      }
   }

   public void unsetMaxTableNameLength() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(MAXTABLENAMELENGTH$224, 0);
      }
   }

   public String getClosePoolSql() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(CLOSEPOOLSQL$226, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetClosePoolSql() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(CLOSEPOOLSQL$226, 0);
         return target;
      }
   }

   public boolean isNilClosePoolSql() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(CLOSEPOOLSQL$226, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetClosePoolSql() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(CLOSEPOOLSQL$226) != 0;
      }
   }

   public void setClosePoolSql(String closePoolSql) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(CLOSEPOOLSQL$226, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(CLOSEPOOLSQL$226);
         }

         target.setStringValue(closePoolSql);
      }
   }

   public void xsetClosePoolSql(XmlString closePoolSql) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(CLOSEPOOLSQL$226, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(CLOSEPOOLSQL$226);
         }

         target.set(closePoolSql);
      }
   }

   public void setNilClosePoolSql() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(CLOSEPOOLSQL$226, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(CLOSEPOOLSQL$226);
         }

         target.setNil();
      }
   }

   public void unsetClosePoolSql() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(CLOSEPOOLSQL$226, 0);
      }
   }

   public String getCurrentDateFunction() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(CURRENTDATEFUNCTION$228, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetCurrentDateFunction() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(CURRENTDATEFUNCTION$228, 0);
         return target;
      }
   }

   public boolean isNilCurrentDateFunction() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(CURRENTDATEFUNCTION$228, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetCurrentDateFunction() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(CURRENTDATEFUNCTION$228) != 0;
      }
   }

   public void setCurrentDateFunction(String currentDateFunction) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(CURRENTDATEFUNCTION$228, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(CURRENTDATEFUNCTION$228);
         }

         target.setStringValue(currentDateFunction);
      }
   }

   public void xsetCurrentDateFunction(XmlString currentDateFunction) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(CURRENTDATEFUNCTION$228, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(CURRENTDATEFUNCTION$228);
         }

         target.set(currentDateFunction);
      }
   }

   public void setNilCurrentDateFunction() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(CURRENTDATEFUNCTION$228, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(CURRENTDATEFUNCTION$228);
         }

         target.setNil();
      }
   }

   public void unsetCurrentDateFunction() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(CURRENTDATEFUNCTION$228, 0);
      }
   }

   public String getJoinSyntax() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(JOINSYNTAX$230, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetJoinSyntax() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(JOINSYNTAX$230, 0);
         return target;
      }
   }

   public boolean isNilJoinSyntax() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(JOINSYNTAX$230, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetJoinSyntax() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(JOINSYNTAX$230) != 0;
      }
   }

   public void setJoinSyntax(String joinSyntax) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(JOINSYNTAX$230, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(JOINSYNTAX$230);
         }

         target.setStringValue(joinSyntax);
      }
   }

   public void xsetJoinSyntax(XmlString joinSyntax) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(JOINSYNTAX$230, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(JOINSYNTAX$230);
         }

         target.set(joinSyntax);
      }
   }

   public void setNilJoinSyntax() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(JOINSYNTAX$230, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(JOINSYNTAX$230);
         }

         target.setNil();
      }
   }

   public void unsetJoinSyntax() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(JOINSYNTAX$230, 0);
      }
   }

   public int getMaxEmbeddedBlobSize() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(MAXEMBEDDEDBLOBSIZE$232, 0);
         return target == null ? 0 : target.getIntValue();
      }
   }

   public XmlInt xgetMaxEmbeddedBlobSize() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlInt target = null;
         target = (XmlInt)this.get_store().find_element_user(MAXEMBEDDEDBLOBSIZE$232, 0);
         return target;
      }
   }

   public boolean isSetMaxEmbeddedBlobSize() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(MAXEMBEDDEDBLOBSIZE$232) != 0;
      }
   }

   public void setMaxEmbeddedBlobSize(int maxEmbeddedBlobSize) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(MAXEMBEDDEDBLOBSIZE$232, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(MAXEMBEDDEDBLOBSIZE$232);
         }

         target.setIntValue(maxEmbeddedBlobSize);
      }
   }

   public void xsetMaxEmbeddedBlobSize(XmlInt maxEmbeddedBlobSize) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlInt target = null;
         target = (XmlInt)this.get_store().find_element_user(MAXEMBEDDEDBLOBSIZE$232, 0);
         if (target == null) {
            target = (XmlInt)this.get_store().add_element_user(MAXEMBEDDEDBLOBSIZE$232);
         }

         target.set(maxEmbeddedBlobSize);
      }
   }

   public void unsetMaxEmbeddedBlobSize() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(MAXEMBEDDEDBLOBSIZE$232, 0);
      }
   }

   public String getTrimBothFunction() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(TRIMBOTHFUNCTION$234, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetTrimBothFunction() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(TRIMBOTHFUNCTION$234, 0);
         return target;
      }
   }

   public boolean isNilTrimBothFunction() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(TRIMBOTHFUNCTION$234, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetTrimBothFunction() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(TRIMBOTHFUNCTION$234) != 0;
      }
   }

   public void setTrimBothFunction(String trimBothFunction) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(TRIMBOTHFUNCTION$234, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(TRIMBOTHFUNCTION$234);
         }

         target.setStringValue(trimBothFunction);
      }
   }

   public void xsetTrimBothFunction(XmlString trimBothFunction) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(TRIMBOTHFUNCTION$234, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(TRIMBOTHFUNCTION$234);
         }

         target.set(trimBothFunction);
      }
   }

   public void setNilTrimBothFunction() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(TRIMBOTHFUNCTION$234, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(TRIMBOTHFUNCTION$234);
         }

         target.setNil();
      }
   }

   public void unsetTrimBothFunction() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(TRIMBOTHFUNCTION$234, 0);
      }
   }

   public boolean getSupportsSelectStartIndex() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(SUPPORTSSELECTSTARTINDEX$236, 0);
         return target == null ? false : target.getBooleanValue();
      }
   }

   public XmlBoolean xgetSupportsSelectStartIndex() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(SUPPORTSSELECTSTARTINDEX$236, 0);
         return target;
      }
   }

   public boolean isSetSupportsSelectStartIndex() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(SUPPORTSSELECTSTARTINDEX$236) != 0;
      }
   }

   public void setSupportsSelectStartIndex(boolean supportsSelectStartIndex) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(SUPPORTSSELECTSTARTINDEX$236, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(SUPPORTSSELECTSTARTINDEX$236);
         }

         target.setBooleanValue(supportsSelectStartIndex);
      }
   }

   public void xsetSupportsSelectStartIndex(XmlBoolean supportsSelectStartIndex) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(SUPPORTSSELECTSTARTINDEX$236, 0);
         if (target == null) {
            target = (XmlBoolean)this.get_store().add_element_user(SUPPORTSSELECTSTARTINDEX$236);
         }

         target.set(supportsSelectStartIndex);
      }
   }

   public void unsetSupportsSelectStartIndex() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(SUPPORTSSELECTSTARTINDEX$236, 0);
      }
   }

   public String getToLowerCaseFunction() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(TOLOWERCASEFUNCTION$238, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetToLowerCaseFunction() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(TOLOWERCASEFUNCTION$238, 0);
         return target;
      }
   }

   public boolean isNilToLowerCaseFunction() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(TOLOWERCASEFUNCTION$238, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetToLowerCaseFunction() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(TOLOWERCASEFUNCTION$238) != 0;
      }
   }

   public void setToLowerCaseFunction(String toLowerCaseFunction) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(TOLOWERCASEFUNCTION$238, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(TOLOWERCASEFUNCTION$238);
         }

         target.setStringValue(toLowerCaseFunction);
      }
   }

   public void xsetToLowerCaseFunction(XmlString toLowerCaseFunction) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(TOLOWERCASEFUNCTION$238, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(TOLOWERCASEFUNCTION$238);
         }

         target.set(toLowerCaseFunction);
      }
   }

   public void setNilToLowerCaseFunction() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(TOLOWERCASEFUNCTION$238, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(TOLOWERCASEFUNCTION$238);
         }

         target.setNil();
      }
   }

   public void unsetToLowerCaseFunction() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(TOLOWERCASEFUNCTION$238, 0);
      }
   }

   public String getArrayTypeName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(ARRAYTYPENAME$240, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetArrayTypeName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(ARRAYTYPENAME$240, 0);
         return target;
      }
   }

   public boolean isNilArrayTypeName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(ARRAYTYPENAME$240, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetArrayTypeName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(ARRAYTYPENAME$240) != 0;
      }
   }

   public void setArrayTypeName(String arrayTypeName) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(ARRAYTYPENAME$240, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(ARRAYTYPENAME$240);
         }

         target.setStringValue(arrayTypeName);
      }
   }

   public void xsetArrayTypeName(XmlString arrayTypeName) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(ARRAYTYPENAME$240, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(ARRAYTYPENAME$240);
         }

         target.set(arrayTypeName);
      }
   }

   public void setNilArrayTypeName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(ARRAYTYPENAME$240, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(ARRAYTYPENAME$240);
         }

         target.setNil();
      }
   }

   public void unsetArrayTypeName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(ARRAYTYPENAME$240, 0);
      }
   }

   public String getInnerJoinClause() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(INNERJOINCLAUSE$242, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetInnerJoinClause() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(INNERJOINCLAUSE$242, 0);
         return target;
      }
   }

   public boolean isNilInnerJoinClause() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(INNERJOINCLAUSE$242, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetInnerJoinClause() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(INNERJOINCLAUSE$242) != 0;
      }
   }

   public void setInnerJoinClause(String innerJoinClause) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(INNERJOINCLAUSE$242, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(INNERJOINCLAUSE$242);
         }

         target.setStringValue(innerJoinClause);
      }
   }

   public void xsetInnerJoinClause(XmlString innerJoinClause) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(INNERJOINCLAUSE$242, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(INNERJOINCLAUSE$242);
         }

         target.set(innerJoinClause);
      }
   }

   public void setNilInnerJoinClause() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(INNERJOINCLAUSE$242, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(INNERJOINCLAUSE$242);
         }

         target.setNil();
      }
   }

   public void unsetInnerJoinClause() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(INNERJOINCLAUSE$242, 0);
      }
   }

   public boolean getSupportsDefaultUpdateAction() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(SUPPORTSDEFAULTUPDATEACTION$244, 0);
         return target == null ? false : target.getBooleanValue();
      }
   }

   public XmlBoolean xgetSupportsDefaultUpdateAction() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(SUPPORTSDEFAULTUPDATEACTION$244, 0);
         return target;
      }
   }

   public boolean isSetSupportsDefaultUpdateAction() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(SUPPORTSDEFAULTUPDATEACTION$244) != 0;
      }
   }

   public void setSupportsDefaultUpdateAction(boolean supportsDefaultUpdateAction) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(SUPPORTSDEFAULTUPDATEACTION$244, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(SUPPORTSDEFAULTUPDATEACTION$244);
         }

         target.setBooleanValue(supportsDefaultUpdateAction);
      }
   }

   public void xsetSupportsDefaultUpdateAction(XmlBoolean supportsDefaultUpdateAction) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(SUPPORTSDEFAULTUPDATEACTION$244, 0);
         if (target == null) {
            target = (XmlBoolean)this.get_store().add_element_user(SUPPORTSDEFAULTUPDATEACTION$244);
         }

         target.set(supportsDefaultUpdateAction);
      }
   }

   public void unsetSupportsDefaultUpdateAction() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(SUPPORTSDEFAULTUPDATEACTION$244, 0);
      }
   }

   public boolean getSupportsSchemaForGetColumns() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(SUPPORTSSCHEMAFORGETCOLUMNS$246, 0);
         return target == null ? false : target.getBooleanValue();
      }
   }

   public XmlBoolean xgetSupportsSchemaForGetColumns() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(SUPPORTSSCHEMAFORGETCOLUMNS$246, 0);
         return target;
      }
   }

   public boolean isSetSupportsSchemaForGetColumns() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(SUPPORTSSCHEMAFORGETCOLUMNS$246) != 0;
      }
   }

   public void setSupportsSchemaForGetColumns(boolean supportsSchemaForGetColumns) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(SUPPORTSSCHEMAFORGETCOLUMNS$246, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(SUPPORTSSCHEMAFORGETCOLUMNS$246);
         }

         target.setBooleanValue(supportsSchemaForGetColumns);
      }
   }

   public void xsetSupportsSchemaForGetColumns(XmlBoolean supportsSchemaForGetColumns) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(SUPPORTSSCHEMAFORGETCOLUMNS$246, 0);
         if (target == null) {
            target = (XmlBoolean)this.get_store().add_element_user(SUPPORTSSCHEMAFORGETCOLUMNS$246);
         }

         target.set(supportsSchemaForGetColumns);
      }
   }

   public void unsetSupportsSchemaForGetColumns() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(SUPPORTSSCHEMAFORGETCOLUMNS$246, 0);
      }
   }

   public String getTinyintTypeName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(TINYINTTYPENAME$248, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetTinyintTypeName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(TINYINTTYPENAME$248, 0);
         return target;
      }
   }

   public boolean isNilTinyintTypeName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(TINYINTTYPENAME$248, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetTinyintTypeName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(TINYINTTYPENAME$248) != 0;
      }
   }

   public void setTinyintTypeName(String tinyintTypeName) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(TINYINTTYPENAME$248, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(TINYINTTYPENAME$248);
         }

         target.setStringValue(tinyintTypeName);
      }
   }

   public void xsetTinyintTypeName(XmlString tinyintTypeName) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(TINYINTTYPENAME$248, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(TINYINTTYPENAME$248);
         }

         target.set(tinyintTypeName);
      }
   }

   public void setNilTinyintTypeName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(TINYINTTYPENAME$248, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(TINYINTTYPENAME$248);
         }

         target.setNil();
      }
   }

   public void unsetTinyintTypeName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(TINYINTTYPENAME$248, 0);
      }
   }

   public boolean getSupportsNullTableForGetPrimaryKeys() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(SUPPORTSNULLTABLEFORGETPRIMARYKEYS$250, 0);
         return target == null ? false : target.getBooleanValue();
      }
   }

   public XmlBoolean xgetSupportsNullTableForGetPrimaryKeys() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(SUPPORTSNULLTABLEFORGETPRIMARYKEYS$250, 0);
         return target;
      }
   }

   public boolean isSetSupportsNullTableForGetPrimaryKeys() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(SUPPORTSNULLTABLEFORGETPRIMARYKEYS$250) != 0;
      }
   }

   public void setSupportsNullTableForGetPrimaryKeys(boolean supportsNullTableForGetPrimaryKeys) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(SUPPORTSNULLTABLEFORGETPRIMARYKEYS$250, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(SUPPORTSNULLTABLEFORGETPRIMARYKEYS$250);
         }

         target.setBooleanValue(supportsNullTableForGetPrimaryKeys);
      }
   }

   public void xsetSupportsNullTableForGetPrimaryKeys(XmlBoolean supportsNullTableForGetPrimaryKeys) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(SUPPORTSNULLTABLEFORGETPRIMARYKEYS$250, 0);
         if (target == null) {
            target = (XmlBoolean)this.get_store().add_element_user(SUPPORTSNULLTABLEFORGETPRIMARYKEYS$250);
         }

         target.set(supportsNullTableForGetPrimaryKeys);
      }
   }

   public void unsetSupportsNullTableForGetPrimaryKeys() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(SUPPORTSNULLTABLEFORGETPRIMARYKEYS$250, 0);
      }
   }

   public String getSystemSchemas() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(SYSTEMSCHEMAS$252, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetSystemSchemas() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(SYSTEMSCHEMAS$252, 0);
         return target;
      }
   }

   public boolean isNilSystemSchemas() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(SYSTEMSCHEMAS$252, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetSystemSchemas() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(SYSTEMSCHEMAS$252) != 0;
      }
   }

   public void setSystemSchemas(String systemSchemas) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(SYSTEMSCHEMAS$252, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(SYSTEMSCHEMAS$252);
         }

         target.setStringValue(systemSchemas);
      }
   }

   public void xsetSystemSchemas(XmlString systemSchemas) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(SYSTEMSCHEMAS$252, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(SYSTEMSCHEMAS$252);
         }

         target.set(systemSchemas);
      }
   }

   public void setNilSystemSchemas() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(SYSTEMSCHEMAS$252, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(SYSTEMSCHEMAS$252);
         }

         target.setNil();
      }
   }

   public void unsetSystemSchemas() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(SYSTEMSCHEMAS$252, 0);
      }
   }

   public boolean getRequiresCastForMathFunctions() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(REQUIRESCASTFORMATHFUNCTIONS$254, 0);
         return target == null ? false : target.getBooleanValue();
      }
   }

   public XmlBoolean xgetRequiresCastForMathFunctions() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(REQUIRESCASTFORMATHFUNCTIONS$254, 0);
         return target;
      }
   }

   public boolean isSetRequiresCastForMathFunctions() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(REQUIRESCASTFORMATHFUNCTIONS$254) != 0;
      }
   }

   public void setRequiresCastForMathFunctions(boolean requiresCastForMathFunctions) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(REQUIRESCASTFORMATHFUNCTIONS$254, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(REQUIRESCASTFORMATHFUNCTIONS$254);
         }

         target.setBooleanValue(requiresCastForMathFunctions);
      }
   }

   public void xsetRequiresCastForMathFunctions(XmlBoolean requiresCastForMathFunctions) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(REQUIRESCASTFORMATHFUNCTIONS$254, 0);
         if (target == null) {
            target = (XmlBoolean)this.get_store().add_element_user(REQUIRESCASTFORMATHFUNCTIONS$254);
         }

         target.set(requiresCastForMathFunctions);
      }
   }

   public void unsetRequiresCastForMathFunctions() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(REQUIRESCASTFORMATHFUNCTIONS$254, 0);
      }
   }

   public boolean getSupportsNullDeleteAction() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(SUPPORTSNULLDELETEACTION$256, 0);
         return target == null ? false : target.getBooleanValue();
      }
   }

   public XmlBoolean xgetSupportsNullDeleteAction() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(SUPPORTSNULLDELETEACTION$256, 0);
         return target;
      }
   }

   public boolean isSetSupportsNullDeleteAction() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(SUPPORTSNULLDELETEACTION$256) != 0;
      }
   }

   public void setSupportsNullDeleteAction(boolean supportsNullDeleteAction) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(SUPPORTSNULLDELETEACTION$256, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(SUPPORTSNULLDELETEACTION$256);
         }

         target.setBooleanValue(supportsNullDeleteAction);
      }
   }

   public void xsetSupportsNullDeleteAction(XmlBoolean supportsNullDeleteAction) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(SUPPORTSNULLDELETEACTION$256, 0);
         if (target == null) {
            target = (XmlBoolean)this.get_store().add_element_user(SUPPORTSNULLDELETEACTION$256);
         }

         target.set(supportsNullDeleteAction);
      }
   }

   public void unsetSupportsNullDeleteAction() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(SUPPORTSNULLDELETEACTION$256, 0);
      }
   }

   public boolean getRequiresAutoCommitForMetaData() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(REQUIRESAUTOCOMMITFORMETADATA$258, 0);
         return target == null ? false : target.getBooleanValue();
      }
   }

   public XmlBoolean xgetRequiresAutoCommitForMetaData() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(REQUIRESAUTOCOMMITFORMETADATA$258, 0);
         return target;
      }
   }

   public boolean isSetRequiresAutoCommitForMetaData() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(REQUIRESAUTOCOMMITFORMETADATA$258) != 0;
      }
   }

   public void setRequiresAutoCommitForMetaData(boolean requiresAutoCommitForMetaData) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(REQUIRESAUTOCOMMITFORMETADATA$258, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(REQUIRESAUTOCOMMITFORMETADATA$258);
         }

         target.setBooleanValue(requiresAutoCommitForMetaData);
      }
   }

   public void xsetRequiresAutoCommitForMetaData(XmlBoolean requiresAutoCommitForMetaData) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(REQUIRESAUTOCOMMITFORMETADATA$258, 0);
         if (target == null) {
            target = (XmlBoolean)this.get_store().add_element_user(REQUIRESAUTOCOMMITFORMETADATA$258);
         }

         target.set(requiresAutoCommitForMetaData);
      }
   }

   public void unsetRequiresAutoCommitForMetaData() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(REQUIRESAUTOCOMMITFORMETADATA$258, 0);
      }
   }

   public String getTimestampTypeName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(TIMESTAMPTYPENAME$260, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetTimestampTypeName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(TIMESTAMPTYPENAME$260, 0);
         return target;
      }
   }

   public boolean isNilTimestampTypeName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(TIMESTAMPTYPENAME$260, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetTimestampTypeName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(TIMESTAMPTYPENAME$260) != 0;
      }
   }

   public void setTimestampTypeName(String timestampTypeName) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(TIMESTAMPTYPENAME$260, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(TIMESTAMPTYPENAME$260);
         }

         target.setStringValue(timestampTypeName);
      }
   }

   public void xsetTimestampTypeName(XmlString timestampTypeName) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(TIMESTAMPTYPENAME$260, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(TIMESTAMPTYPENAME$260);
         }

         target.set(timestampTypeName);
      }
   }

   public void setNilTimestampTypeName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(TIMESTAMPTYPENAME$260, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(TIMESTAMPTYPENAME$260);
         }

         target.setNil();
      }
   }

   public void unsetTimestampTypeName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(TIMESTAMPTYPENAME$260, 0);
      }
   }

   public String getInitializationSql() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(INITIALIZATIONSQL$262, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetInitializationSql() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(INITIALIZATIONSQL$262, 0);
         return target;
      }
   }

   public boolean isNilInitializationSql() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(INITIALIZATIONSQL$262, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetInitializationSql() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(INITIALIZATIONSQL$262) != 0;
      }
   }

   public void setInitializationSql(String initializationSql) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(INITIALIZATIONSQL$262, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(INITIALIZATIONSQL$262);
         }

         target.setStringValue(initializationSql);
      }
   }

   public void xsetInitializationSql(XmlString initializationSql) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(INITIALIZATIONSQL$262, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(INITIALIZATIONSQL$262);
         }

         target.set(initializationSql);
      }
   }

   public void setNilInitializationSql() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(INITIALIZATIONSQL$262, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(INITIALIZATIONSQL$262);
         }

         target.setNil();
      }
   }

   public void unsetInitializationSql() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(INITIALIZATIONSQL$262, 0);
      }
   }

   public boolean getSupportsCascadeDeleteAction() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(SUPPORTSCASCADEDELETEACTION$264, 0);
         return target == null ? false : target.getBooleanValue();
      }
   }

   public XmlBoolean xgetSupportsCascadeDeleteAction() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(SUPPORTSCASCADEDELETEACTION$264, 0);
         return target;
      }
   }

   public boolean isSetSupportsCascadeDeleteAction() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(SUPPORTSCASCADEDELETEACTION$264) != 0;
      }
   }

   public void setSupportsCascadeDeleteAction(boolean supportsCascadeDeleteAction) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(SUPPORTSCASCADEDELETEACTION$264, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(SUPPORTSCASCADEDELETEACTION$264);
         }

         target.setBooleanValue(supportsCascadeDeleteAction);
      }
   }

   public void xsetSupportsCascadeDeleteAction(XmlBoolean supportsCascadeDeleteAction) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(SUPPORTSCASCADEDELETEACTION$264, 0);
         if (target == null) {
            target = (XmlBoolean)this.get_store().add_element_user(SUPPORTSCASCADEDELETEACTION$264);
         }

         target.set(supportsCascadeDeleteAction);
      }
   }

   public void unsetSupportsCascadeDeleteAction() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(SUPPORTSCASCADEDELETEACTION$264, 0);
      }
   }

   public boolean getSupportsTimestampNanos() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(SUPPORTSTIMESTAMPNANOS$266, 0);
         return target == null ? false : target.getBooleanValue();
      }
   }

   public XmlBoolean xgetSupportsTimestampNanos() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(SUPPORTSTIMESTAMPNANOS$266, 0);
         return target;
      }
   }

   public boolean isSetSupportsTimestampNanos() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(SUPPORTSTIMESTAMPNANOS$266) != 0;
      }
   }

   public void setSupportsTimestampNanos(boolean supportsTimestampNanos) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(SUPPORTSTIMESTAMPNANOS$266, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(SUPPORTSTIMESTAMPNANOS$266);
         }

         target.setBooleanValue(supportsTimestampNanos);
      }
   }

   public void xsetSupportsTimestampNanos(XmlBoolean supportsTimestampNanos) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(SUPPORTSTIMESTAMPNANOS$266, 0);
         if (target == null) {
            target = (XmlBoolean)this.get_store().add_element_user(SUPPORTSTIMESTAMPNANOS$266);
         }

         target.set(supportsTimestampNanos);
      }
   }

   public void unsetSupportsTimestampNanos() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(SUPPORTSTIMESTAMPNANOS$266, 0);
      }
   }
}
