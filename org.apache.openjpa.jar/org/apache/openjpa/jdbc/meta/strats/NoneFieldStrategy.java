package org.apache.openjpa.jdbc.meta.strats;

import org.apache.openjpa.jdbc.meta.FieldMapping;

public class NoneFieldStrategy extends AbstractFieldStrategy {
   public static final String ALIAS = "none";
   private static final NoneFieldStrategy _instance = new NoneFieldStrategy();

   public static NoneFieldStrategy getInstance() {
      return _instance;
   }

   private NoneFieldStrategy() {
   }

   public String getAlias() {
      return "none";
   }

   public void setFieldMapping(FieldMapping owner) {
   }
}
