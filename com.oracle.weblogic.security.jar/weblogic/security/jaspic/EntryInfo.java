package weblogic.security.jaspic;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.security.auth.message.config.AuthConfigFactory;

public class EntryInfo {
   private final String className;
   private final Map properties;
   private List regContexts;

   public EntryInfo(String className, Map properties) {
      if (className == null) {
         throw new IllegalArgumentException("Class name for registration entry cannot be null");
      } else {
         this.className = className;
         this.properties = properties;
      }
   }

   public EntryInfo(String className, Map properties, List ctxs) {
      if (ctxs != null && !ctxs.isEmpty()) {
         this.className = className;
         this.properties = properties;
         this.regContexts = ctxs;
      } else {
         throw new IllegalArgumentException("Registration entry must contain one ormore registration contexts");
      }
   }

   public EntryInfo(String className, Map properties, AuthConfigFactory.RegistrationContext ctx) {
      this.className = className;
      this.properties = properties;
      if (ctx != null) {
         AuthConfigFactory.RegistrationContext ctxImpl = new RegistrationContextImpl(ctx.getMessageLayer(), ctx.getAppContext(), ctx.getDescription(), ctx.isPersistent());
         List newList = new ArrayList(1);
         newList.add(ctxImpl);
         this.regContexts = newList;
      }

   }

   public boolean isConstructorEntry() {
      return this.regContexts == null;
   }

   public String getClassName() {
      return this.className;
   }

   public Map getProperties() {
      return this.properties;
   }

   public List getRegContexts() {
      return this.regContexts;
   }

   boolean equals(EntryInfo target) {
      if (target == null) {
         return false;
      } else {
         return !(this.isConstructorEntry() ^ target.isConstructorEntry()) && matchStrings(this.className, target.getClassName()) && matchMaps(this.properties, target.getProperties());
      }
   }

   static boolean matchStrings(String s1, String s2) {
      if (s1 == null && s2 == null) {
         return true;
      } else {
         return s1 != null && s2 != null ? s1.equals(s2) : false;
      }
   }

   static boolean matchMaps(Map m1, Map m2) {
      if (m1 == null && m2 == null) {
         return true;
      } else {
         return m1 != null && m2 != null ? m1.equals(m2) : false;
      }
   }
}
