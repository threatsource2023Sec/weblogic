package kodo.conf.descriptor;

public interface KodoCompatibilityBean {
   boolean getCopyObjectIds();

   void setCopyObjectIds(boolean var1);

   boolean getCloseOnManagedCommit();

   void setCloseOnManagedCommit(boolean var1);

   boolean getValidateTrueChecksStore();

   void setValidateTrueChecksStore(boolean var1);

   boolean getValidateFalseReturnsHollow();

   void setValidateFalseReturnsHollow(boolean var1);

   boolean getStrictIdentityValues();

   void setStrictIdentityValues(boolean var1);

   boolean getQuotedNumbersInQueries();

   void setQuotedNumbersInQueries(boolean var1);
}
