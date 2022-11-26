package weblogic.ejb.container.cmp.rdbms;

import java.util.TreeSet;

public final class FieldGroup {
   private String name;
   private int index;
   private final TreeSet cmpFields = new TreeSet();
   private final TreeSet cmrFields = new TreeSet();

   public void setName(String name) {
      this.name = name;
   }

   public String getName() {
      return this.name;
   }

   public void addCmpField(String fieldName) {
      this.cmpFields.add(fieldName);
   }

   public TreeSet getCmpFields() {
      return this.cmpFields;
   }

   public void addCmrField(String fieldName) {
      this.cmrFields.add(fieldName);
   }

   public TreeSet getCmrFields() {
      return this.cmrFields;
   }

   public void setIndex(int index) {
      this.index = index;
   }

   public int getIndex() {
      return this.index;
   }

   public String toString() {
      return "[FieldGroup name:" + this.name + " cmp fields:" + this.cmpFields + " cmr fields:" + this.cmrFields + "]";
   }
}
