package weblogic.cache.lld;

import java.util.Map;

public abstract class LLDFactory {
   public static LLDFactory getInstance() {
      return LLDFactory.Factory.THE_ONE;
   }

   protected LLDFactory() {
   }

   public abstract ChangeListener createLLDInvalidator(String var1, Map var2);

   private static final class Factory {
      static final LLDFactory THE_ONE;

      static {
         try {
            THE_ONE = (LLDFactory)Class.forName("weblogic.cache.lld.LLDFactoryImpl").newInstance();
         } catch (IllegalAccessException var1) {
            throw new AssertionError(var1);
         } catch (ClassNotFoundException var2) {
            throw new AssertionError(var2);
         } catch (InstantiationException var3) {
            throw new AssertionError(var3);
         }
      }
   }
}
