package org.jboss.weld.contexts.beanstore;

import org.jboss.weld.serialization.BeanIdentifierIndex;

public class ConversationNamingScheme extends BeanIdentifierIndexNamingScheme {
   public static final String PARAMETER_NAME = ConversationNamingScheme.class.getName();
   private String cid;
   private final String prefixBase;

   public ConversationNamingScheme(String prefixBase, String cid, BeanIdentifierIndex index) {
      super("#", index);
      this.cid = cid;
      this.prefixBase = prefixBase;
   }

   public void setCid(String cid) {
      this.cid = cid;
   }

   protected String getPrefix() {
      return this.prefixBase + "." + this.cid;
   }
}
