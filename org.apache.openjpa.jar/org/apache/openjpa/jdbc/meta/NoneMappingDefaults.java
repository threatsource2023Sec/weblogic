package org.apache.openjpa.jdbc.meta;

import org.apache.openjpa.jdbc.schema.Column;
import org.apache.openjpa.jdbc.schema.ForeignKey;
import org.apache.openjpa.jdbc.schema.Index;
import org.apache.openjpa.jdbc.schema.Schema;
import org.apache.openjpa.jdbc.schema.Table;
import org.apache.openjpa.jdbc.schema.Unique;
import serp.util.Strings;

public class NoneMappingDefaults implements MappingDefaults {
   private static final NoneMappingDefaults _instance = new NoneMappingDefaults();

   public static NoneMappingDefaults getInstance() {
      return _instance;
   }

   public boolean defaultMissingInfo() {
      return false;
   }

   public boolean useClassCriteria() {
      return false;
   }

   public Object getStrategy(ClassMapping cls, boolean adapt) {
      return null;
   }

   public Object getStrategy(Version vers, boolean adapt) {
      return null;
   }

   public Object getStrategy(Discriminator disc, boolean adapt) {
      return null;
   }

   public Object getStrategy(ValueMapping vm, Class type, boolean adapt) {
      return null;
   }

   public Object getDiscriminatorValue(Discriminator disc, boolean adapt) {
      return null;
   }

   public String getTableName(ClassMapping cls, Schema schema) {
      return Strings.getClassName(cls.getDescribedType()).replace('$', '_');
   }

   public String getTableName(FieldMapping fm, Schema schema) {
      return fm.getName();
   }

   public void populateDataStoreIdColumns(ClassMapping cls, Table table, Column[] cols) {
   }

   public void populateColumns(Version vers, Table table, Column[] cols) {
   }

   public void populateColumns(Discriminator disc, Table table, Column[] cols) {
   }

   public void populateJoinColumn(ClassMapping cm, Table local, Table foreign, Column col, Object target, int pos, int cols) {
   }

   public void populateJoinColumn(FieldMapping fm, Table local, Table foreign, Column col, Object target, int pos, int cols) {
   }

   public void populateForeignKeyColumn(ValueMapping vm, String name, Table local, Table foreign, Column col, Object target, boolean inverse, int pos, int cols) {
   }

   public void populateColumns(ValueMapping vm, String name, Table table, Column[] cols) {
   }

   public boolean populateOrderColumns(FieldMapping fm, Table table, Column[] cols) {
      return false;
   }

   public boolean populateNullIndicatorColumns(ValueMapping vm, String name, Table table, Column[] cols) {
      return false;
   }

   public ForeignKey getJoinForeignKey(ClassMapping cls, Table local, Table foreign) {
      return null;
   }

   public ForeignKey getJoinForeignKey(FieldMapping fm, Table local, Table foreign) {
      return null;
   }

   public ForeignKey getForeignKey(ValueMapping vm, String name, Table local, Table foreign, boolean inverse) {
      return null;
   }

   public Index getJoinIndex(FieldMapping fm, Table table, Column[] cols) {
      return null;
   }

   public Index getIndex(ValueMapping vm, String name, Table table, Column[] cols) {
      return null;
   }

   public Index getIndex(Version vers, Table table, Column[] cols) {
      return null;
   }

   public Index getIndex(Discriminator disc, Table table, Column[] cols) {
      return null;
   }

   public Unique getJoinUnique(FieldMapping fm, Table table, Column[] cols) {
      return null;
   }

   public Unique getUnique(ValueMapping vm, String name, Table table, Column[] cols) {
      return null;
   }

   public String getPrimaryKeyName(ClassMapping cm, Table table) {
      return null;
   }

   public void installPrimaryKey(FieldMapping fm, Table table) {
   }
}
