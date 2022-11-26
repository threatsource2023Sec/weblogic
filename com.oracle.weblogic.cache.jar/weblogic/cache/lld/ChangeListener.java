package weblogic.cache.lld;

public interface ChangeListener {
   void onClear();

   void onCreate(CacheEntry var1);

   void onUpdate(CacheEntry var1, Object var2);

   void onDelete(CacheEntry var1);
}
