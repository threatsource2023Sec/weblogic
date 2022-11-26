package weblogic.application.descriptor.parser;

import weblogic.descriptor.internal.SchemaHelper;

public class StepExpression {
   private String pathName;
   private PredicateInfo predicateInfo;
   private SchemaHelper schemaHelper;

   public void addPredicate(PredicateInfo predicate) {
      this.predicateInfo = predicate;
   }

   public PredicateInfo getPredicate() {
      return this.predicateInfo;
   }

   public String getPathName() {
      return this.pathName;
   }

   public void setPathName(String name) {
      this.pathName = name;
   }

   public SchemaHelper getSchemaHelper() {
      return this.schemaHelper;
   }

   public void setSchemaHelper(SchemaHelper helper) {
      this.schemaHelper = helper;
   }
}
