package org.apache.openjpa.jdbc.meta.strats;

import org.apache.openjpa.jdbc.meta.Version;

public class NoneVersionStrategy extends AbstractVersionStrategy {
   public static final String ALIAS = "none";
   private static final NoneVersionStrategy _instance = new NoneVersionStrategy();

   public static NoneVersionStrategy getInstance() {
      return _instance;
   }

   private NoneVersionStrategy() {
   }

   public String getAlias() {
      return "none";
   }

   public void setVersion(Version owner) {
   }
}
