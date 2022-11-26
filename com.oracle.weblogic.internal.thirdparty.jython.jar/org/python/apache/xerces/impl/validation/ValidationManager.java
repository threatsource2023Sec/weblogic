package org.python.apache.xerces.impl.validation;

import java.util.ArrayList;

public class ValidationManager {
   protected final ArrayList fVSs = new ArrayList();
   protected boolean fGrammarFound = false;
   protected boolean fCachedDTD = false;

   public final void addValidationState(ValidationState var1) {
      this.fVSs.add(var1);
   }

   public final void setEntityState(EntityState var1) {
      for(int var2 = this.fVSs.size() - 1; var2 >= 0; --var2) {
         ((ValidationState)this.fVSs.get(var2)).setEntityState(var1);
      }

   }

   public final void setGrammarFound(boolean var1) {
      this.fGrammarFound = var1;
   }

   public final boolean isGrammarFound() {
      return this.fGrammarFound;
   }

   public final void setCachedDTD(boolean var1) {
      this.fCachedDTD = var1;
   }

   public final boolean isCachedDTD() {
      return this.fCachedDTD;
   }

   public final void reset() {
      this.fVSs.clear();
      this.fGrammarFound = false;
      this.fCachedDTD = false;
   }
}
