package weblogic.spring.monitoring.instrumentation;

import java.util.HashMap;
import java.util.Map;
import weblogic.utils.StringUtils;

public class SpringInstrumentorEngineCreatorFactoryImpl implements SpringInstrumentorEngineCreatorFactory {
   private static final SpringInstrumentorEngineCreatorFactory INSTANCE = new SpringInstrumentorEngineCreatorFactoryImpl();
   private final Map creators = new HashMap();

   private SpringInstrumentorEngineCreatorFactoryImpl() {
   }

   public static SpringInstrumentorEngineCreatorFactory getInstance() {
      return INSTANCE;
   }

   public SpringInstrumentorEngineCreator build(String springVersion) {
      if (StringUtils.isEmptyString(springVersion)) {
         return null;
      } else {
         synchronized(this.creators) {
            if (this.creators.containsKey(springVersion)) {
               return (SpringInstrumentorEngineCreator)this.creators.get(springVersion);
            } else {
               SpringVersionLoader loader = SpringVersionLoader.getInstance();
               String implClassName = loader.getSpringInstrumentorEngineCreatorImplClassName(springVersion);
               if (implClassName == null) {
                  String majorSpringVersion = springVersion.substring(0, springVersion.indexOf("."));
                  majorSpringVersion = majorSpringVersion + ".*";
                  implClassName = loader.getSpringInstrumentorEngineCreatorImplClassName(majorSpringVersion);
               }

               if (implClassName == null) {
                  return null;
               } else {
                  SpringInstrumentorEngineCreator creator = this.loadClass(implClassName);
                  this.creators.put(springVersion, creator);
                  return creator;
               }
            }
         }
      }
   }

   private SpringInstrumentorEngineCreator loadClass(String implClassName) {
      try {
         Class clz = Class.forName(implClassName);
         return (SpringInstrumentorEngineCreator)clz.newInstance();
      } catch (Exception var3) {
         throw new CannotBuildSpringInstrumentorEngineCreatorException(implClassName, var3);
      }
   }
}
