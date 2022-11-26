package weblogic.messaging.interception.interfaces;

public interface AssociationListener {
   void onAddAssociation(String var1, String[] var2, String var3, String var4, boolean var5, int var6);

   void onRemoveAssociation(String var1, String[] var2, String var3, String var4);
}
