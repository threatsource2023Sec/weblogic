package weblogic.cache.lld;

import java.util.Map;

public final class LLDFactoryImpl extends LLDFactory {
   static final int DEFAULT_SIZE = 64;
   static final int DEFAULT_TTL = 1800;

   public ChangeListener createLLDInvalidator(String name, Map map) {
      return new LLDGroupMessageInvalidator(name, map);
   }
}
