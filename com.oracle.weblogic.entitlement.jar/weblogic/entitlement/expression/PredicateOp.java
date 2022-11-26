package weblogic.entitlement.expression;

import weblogic.entitlement.engine.EEngine;
import weblogic.entitlement.engine.ESubject;
import weblogic.entitlement.engine.PredicateRegistry;
import weblogic.entitlement.engine.ResourceNode;
import weblogic.entitlement.engine.UnregisteredPredicateException;
import weblogic.security.providers.authorization.IllegalPredicateArgumentException;
import weblogic.security.providers.authorization.Predicate;
import weblogic.security.service.ContextHandler;

public class PredicateOp extends EExprRep {
   private Predicate mPredicate;
   private String[] mArgs;

   public PredicateOp() {
      this.mPredicate = null;
      this.mArgs = null;
   }

   public PredicateOp(String className) throws InvalidPredicateClassException, IllegalPredicateArgumentException {
      this(className, (String[])null);
   }

   public PredicateOp(String className, String[] args) throws InvalidPredicateClassException, IllegalPredicateArgumentException {
      this.mPredicate = null;
      this.mArgs = null;
      this.mPredicate = EEngine.validatePredicate(className);
      this.mArgs = args;
      String[] nArgs = new String[args == null ? 0 : args.length];

      for(int i = 0; i < nArgs.length; ++i) {
         if (args[i].startsWith("\"") && args[i].endsWith("\"")) {
            nArgs[i] = args[i].substring(1, args[i].length() - 1);
         } else {
            nArgs[i] = args[i];
         }
      }

      this.mPredicate.init(nArgs);
   }

   protected int getDependsOnInternal() {
      return 4;
   }

   public final boolean evaluate(ESubject subject, ResourceNode resource, ContextHandler context, PredicateRegistry registry) {
      if (!registry.isRegistered(this.getPredicateClassName())) {
         throw new UnregisteredPredicateException(this.getPredicateClassName());
      } else {
         return this.mPredicate.evaluate(subject.getSubject(), resource.getResource(), context);
      }
   }

   public String getPredicateClassName() {
      return this.mPredicate.getClass().getName();
   }

   public String[] getPredicateArguments() {
      return this.mArgs;
   }

   char getTypeId() {
      return 'p';
   }

   void outForPersist(StringBuffer buf) {
      this.writeTypeId(buf);
      writeStr(this.getPredicateClassName(), buf);
      int count = this.mArgs == null ? 0 : this.mArgs.length;
      buf.append((char)count);

      for(int i = 0; i < count; ++i) {
         writeStr(this.mArgs[i], buf);
      }

   }

   protected void writeExternalForm(StringBuffer buf) {
      if (this.Enclosed) {
         buf.append('{');
      }

      buf.append('?');
      buf.append(this.getPredicateClassName());
      buf.append('(');
      String[] args = this.getPredicateArguments();
      if (args != null && args.length > 0) {
         for(int i = 0; i < args.length; ++i) {
            if (i > 0) {
               buf.append(',');
            }

            buf.append(args[i]);
         }
      }

      buf.append(')');
      if (this.Enclosed) {
         buf.append('}');
      }

   }
}
