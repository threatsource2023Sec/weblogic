package weblogic.spring.monitoring.instrumentation;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Hashtable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.diagnostics.instrumentation.engine.InstrumentorEngine;
import weblogic.utils.classloaders.ClassPreProcessor;
import weblogic.utils.classloaders.GenericClassLoader;

public class SpringClassPreprocessor implements ClassPreProcessor {
   private static DebugLogger debugLogger = DebugLogger.getDebugLogger("DebugSpringStatistics");
   private static final Map ENGINES = new ConcurrentHashMap();
   private final GenericClassLoader gcl;
   private final InstrumentorEngine springInstrumentorEngine;

   public SpringClassPreprocessor(GenericClassLoader gcl, String springVersion) {
      this.gcl = gcl;
      this.springInstrumentorEngine = this.createSpringInstrumentorEngineIfNecessary(springVersion);
   }

   public void initialize(Hashtable params) {
   }

   public byte[] preProcess(String className, byte[] classBytes) {
      if (this.springInstrumentorEngine != null) {
         byte[] oBytes = this.springInstrumentorEngine.instrumentClass(this.gcl, className, classBytes);
         if (oBytes != null) {
            if (debugLogger.isDebugEnabled()) {
               debugLogger.debug("SpringClassPreprocessor.preProcess instrumented: " + className);
            }

            return oBytes;
         }
      }

      return classBytes;
   }

   private InstrumentorEngine createSpringInstrumentorEngineIfNecessary(String springVersion) throws NoSupportedSpringInstrumentorEngineCreatorFoundException {
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug("SpringClassPreprocessor.createSpringInstrumentorEngineIfNecessary()");
      }

      if (ENGINES.containsKey(springVersion)) {
         return (InstrumentorEngine)ENGINES.get(springVersion);
      } else {
         SpringInstrumentorEngineCreatorFactory factory = SpringInstrumentorEngineCreatorFactoryImpl.getInstance();
         SpringInstrumentorEngineCreator creator = factory.build(springVersion);
         if (creator == null) {
            throw new NoSupportedSpringInstrumentorEngineCreatorFoundException(springVersion);
         } else {
            InstrumentorEngine engine = creator.createSpringInstrumentorEngine(this.isHotswapAvailable());
            ENGINES.put(springVersion, engine);
            return engine;
         }
      }
   }

   private boolean isHotswapAvailable() {
      try {
         Class instMgrClass = Class.forName("weblogic.diagnostics.instrumentation.InstrumentationManager");
         Method getInstMgrMethod = instMgrClass.getMethod("getInstrumentationManager", (Class[])null);
         Object instMgrInstance = getInstMgrMethod.invoke((Object)null, (Object[])null);
         Method isHotswapMethod = instMgrClass.getMethod("isHotswapAvailable", (Class[])null);
         return (Boolean)isHotswapMethod.invoke(instMgrInstance, (Object[])null);
      } catch (ClassNotFoundException var5) {
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug("Failed to check for Hotswap availability", var5);
         }
      } catch (SecurityException var6) {
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug("Failed to check for Hotswap availability", var6);
         }
      } catch (NoSuchMethodException var7) {
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug("Failed to check for Hotswap availability", var7);
         }
      } catch (IllegalArgumentException var8) {
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug("Failed to check for Hotswap availability", var8);
         }
      } catch (IllegalAccessException var9) {
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug("Failed to check for Hotswap availability", var9);
         }
      } catch (InvocationTargetException var10) {
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug("Failed to check for Hotswap availability", var10);
         }
      }

      return false;
   }
}
