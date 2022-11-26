package org.hibernate.validator;

import java.security.BasicPermission;

public class HibernateValidatorPermission extends BasicPermission {
   public static final HibernateValidatorPermission ACCESS_PRIVATE_MEMBERS = new HibernateValidatorPermission("accessPrivateMembers");

   public HibernateValidatorPermission(String name) {
      super(name);
   }

   public HibernateValidatorPermission(String name, String actions) {
      super(name, actions);
   }
}
