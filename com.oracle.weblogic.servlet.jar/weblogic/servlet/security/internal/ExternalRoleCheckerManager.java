package weblogic.servlet.security.internal;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import weblogic.servlet.internal.WebAppModule;

public final class ExternalRoleCheckerManager implements ExternalRoleChecker {
   private static final List externalRoleCheckerFactories = new LinkedList();
   private WebAppModule module;
   private List externalRoleCheckers;

   public static synchronized void addExternalRoleCheckerFactory(ExternalRoleCheckerFactory factory) {
      externalRoleCheckerFactories.add(factory);
   }

   public static List getExternalRoleCheckerFactories() {
      return externalRoleCheckerFactories;
   }

   public ExternalRoleCheckerManager(WebAppModule module) {
      this.module = module;
   }

   public boolean isExternalRole(String roleName) {
      Iterator var2;
      if (this.externalRoleCheckers == null) {
         this.externalRoleCheckers = new ArrayList();
         var2 = getExternalRoleCheckerFactories().iterator();

         while(var2.hasNext()) {
            ExternalRoleCheckerFactory factory = (ExternalRoleCheckerFactory)var2.next();
            ExternalRoleChecker checker = factory.getExternalRoleChecker(this.module);
            if (checker != null) {
               this.externalRoleCheckers.add(checker);
            }
         }
      }

      var2 = this.externalRoleCheckers.iterator();

      ExternalRoleChecker checker;
      do {
         if (!var2.hasNext()) {
            return false;
         }

         checker = (ExternalRoleChecker)var2.next();
      } while(!checker.isExternalRole(roleName));

      return true;
   }

   static {
      addExternalRoleCheckerFactory(new ExternalRoleCheckerFactory() {
         public ExternalRoleChecker getExternalRoleChecker(WebAppModule module) {
            return new ExternalRoleChecker() {
               public boolean isExternalRole(String roleName) {
                  return false;
               }
            };
         }
      });
   }
}
