package org.apache.openjpa.lib.rop;

import java.util.ArrayList;

public class EagerResultList extends ListResultList implements ResultList {
   public EagerResultList(ResultObjectProvider rop) {
      super(new ArrayList());

      try {
         rop.open();

         while(rop.next()) {
            this.getDelegate().add(rop.getResultObject());
         }
      } catch (RuntimeException var12) {
         throw var12;
      } catch (Exception var13) {
         rop.handleCheckedException(var13);
      } finally {
         try {
            rop.close();
         } catch (Exception var11) {
         }

      }

   }
}
