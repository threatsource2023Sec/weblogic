package org.apache.openjpa.conf;

public class Compatibility {
   public static final int JPQL_STRICT = 0;
   public static final int JPQL_WARN = 1;
   public static final int JPQL_EXTENDED = 2;
   private boolean _strictIdValues = false;
   private boolean _hollowLookups = true;
   private boolean _checkStore = false;
   private boolean _copyIds = false;
   private boolean _closeOnCommit = true;
   private boolean _quotedNumbers = false;
   private boolean _nonOptimisticVersionCheck = false;
   private int _jpql = 1;
   private boolean _storeMapCollectionInEntityAsBlob = false;
   private boolean _flushBeforeDetach = true;
   private boolean _autoOff = true;
   private boolean _superclassDiscriminatorStrategyByDefault = true;

   public boolean getStrictIdentityValues() {
      return this._strictIdValues;
   }

   public void setStrictIdentityValues(boolean strictVals) {
      this._strictIdValues = strictVals;
   }

   public boolean getAutoOff() {
      return this._autoOff;
   }

   public void setAutoOff(boolean autoOff) {
      this._autoOff = autoOff;
   }

   public boolean getSuperclassDiscriminatorStrategyByDefault() {
      return this._superclassDiscriminatorStrategyByDefault;
   }

   public void setSuperclassDiscriminatorStrategyByDefault(boolean superclassDiscriminatorStrategyByDefault) {
      this._superclassDiscriminatorStrategyByDefault = superclassDiscriminatorStrategyByDefault;
   }

   public boolean getQuotedNumbersInQueries() {
      return this._quotedNumbers;
   }

   public void setQuotedNumbersInQueries(boolean quotedNumbers) {
      this._quotedNumbers = quotedNumbers;
   }

   public boolean getValidateFalseReturnsHollow() {
      return this._hollowLookups;
   }

   public void setValidateFalseReturnsHollow(boolean hollow) {
      this._hollowLookups = hollow;
   }

   public boolean getValidateTrueChecksStore() {
      return this._checkStore;
   }

   public void setValidateTrueChecksStore(boolean check) {
      this._checkStore = check;
   }

   public boolean getCopyObjectIds() {
      return this._copyIds;
   }

   public void setCopyObjectIds(boolean copy) {
      this._copyIds = copy;
   }

   public boolean getCloseOnManagedCommit() {
      return this._closeOnCommit;
   }

   public void setCloseOnManagedCommit(boolean close) {
      this._closeOnCommit = close;
   }

   public void setNonOptimisticVersionCheck(boolean nonOptimisticVersionCheck) {
      this._nonOptimisticVersionCheck = nonOptimisticVersionCheck;
   }

   public boolean getNonOptimisticVersionCheck() {
      return this._nonOptimisticVersionCheck;
   }

   public int getJPQL() {
      return this._jpql;
   }

   public void setJPQL(String jpql) {
      if ("warn".equals(jpql)) {
         this._jpql = 1;
      } else if ("strict".equals(jpql)) {
         this._jpql = 0;
      } else {
         if (!"extended".equals(jpql)) {
            throw new IllegalArgumentException(jpql);
         }

         this._jpql = 2;
      }

   }

   public boolean getStoreMapCollectionInEntityAsBlob() {
      return this._storeMapCollectionInEntityAsBlob;
   }

   public void setStoreMapCollectionInEntityAsBlob(boolean storeAsBlob) {
      this._storeMapCollectionInEntityAsBlob = storeAsBlob;
   }

   public boolean getFlushBeforeDetach() {
      return this._flushBeforeDetach;
   }

   public void setFlushBeforeDetach(boolean beforeDetach) {
      this._flushBeforeDetach = beforeDetach;
   }
}
