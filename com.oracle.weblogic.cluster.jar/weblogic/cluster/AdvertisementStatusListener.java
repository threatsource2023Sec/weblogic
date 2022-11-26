package weblogic.cluster;

interface AdvertisementStatusListener {
   void onConflict(String var1, Object var2, int var3);

   void onRemoval(String var1, Object var2);
}
