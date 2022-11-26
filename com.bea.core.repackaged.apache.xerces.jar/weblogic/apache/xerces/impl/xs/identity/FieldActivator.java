package weblogic.apache.xerces.impl.xs.identity;

public interface FieldActivator {
   void startValueScopeFor(IdentityConstraint var1, int var2);

   XPathMatcher activateField(Field var1, int var2);

   void endValueScopeFor(IdentityConstraint var1, int var2);
}
