package org.apache.openjpa.jdbc.meta.strats;

import org.apache.openjpa.jdbc.meta.Discriminator;

public class NoneDiscriminatorStrategy extends AbstractDiscriminatorStrategy {
   public static final String ALIAS = "none";
   private static final NoneDiscriminatorStrategy _instance = new NoneDiscriminatorStrategy();

   public static NoneDiscriminatorStrategy getInstance() {
      return _instance;
   }

   private NoneDiscriminatorStrategy() {
   }

   public String getAlias() {
      return "none";
   }

   public void setDiscriminator(Discriminator owner) {
      owner.setSubclassesLoaded(true);
   }
}
