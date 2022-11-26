package weblogic.ejb.container.interfaces;

public interface WLEntityBean extends WLEnterpriseBean {
   void __WL_setOperationsComplete(boolean var1);

   boolean __WL_getOperationsComplete();

   void __WL_setBeanStateValid(boolean var1);

   boolean __WL_isBeanStateValid();

   boolean __WL_isBusy();

   void __WL_setBusy(boolean var1);

   boolean __WL_needsRemove();

   void __WL_setNeedsRemove(boolean var1);

   void __WL_setLastLoadTime(long var1);

   long __WL_getLastLoadTime();

   boolean __WL_getIsLocal();

   void __WL_setIsLocal(boolean var1);

   void __WL_setLoadUser(Object var1);

   Object __WL_getLoadUser();

   void __WL_setCreatorOfTx(boolean var1);

   boolean __WL_isCreatorOfTx();
}
