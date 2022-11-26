package weblogic.diagnostics.instrumentation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.diagnostics.i18n.DiagnosticsLogger;

public class ValueRenderingManager {
   private static boolean initialized = false;
   private static DebugLogger debugLog = DebugLogger.getDebugLogger("DebugDiagnosticDataGathering");
   public static final ValueRenderer defaultValueRenderer = new DefaultValueRenderer();
   private static Map typeBasedValueRendererMap = new HashMap();
   private static Map rendererMap = new HashMap();

   public static Object renderReturnValue(String monitorType, Object valueToRender, Map infoMap) {
      if (monitorType != null && infoMap != null) {
         if (valueToRender == null) {
            return null;
         } else {
            PointcutHandlingInfo phInfo = (PointcutHandlingInfo)infoMap.get(monitorType);
            if (phInfo == null) {
               if (debugLog.isDebugEnabled()) {
                  debugLog.debug(" phInfo null, make args sensitive");
               }

               return "*****";
            } else {
               return renderValue(valueToRender, phInfo.getReturnValueHandlingInfo());
            }
         }
      } else {
         if (debugLog.isDebugEnabled()) {
            debugLog.debug(" renderReturnValue sensitive, monitorType = " + monitorType + " infoMap = " + infoMap);
         }

         return "*****";
      }
   }

   public static Object[] renderArgumentValues(String monitorType, boolean renderClass, Object[] args, Map infoMap) {
      if (args != null && args.length != 0) {
         if (monitorType != null && infoMap != null) {
            PointcutHandlingInfo phInfo = (PointcutHandlingInfo)infoMap.get(monitorType);
            if (phInfo == null) {
               if (debugLog.isDebugEnabled()) {
                  debugLog.debug(" phInfo null, make args sensitive");
               }

               return InstrumentationSupport.toSensitive(args);
            } else {
               ValueHandlingInfo classInfo = phInfo.getClassValueHandlingInfo();
               ValueHandlingInfo[] argInfos = phInfo.getArgumentValueHandlingInfo();
               if (argInfos == null && (!renderClass || renderClass && classInfo == null)) {
                  if (debugLog.isDebugEnabled()) {
                     debugLog.debug(" sensitive 2 argInfos = " + argInfos + " renderClass = " + renderClass + " classInfo = " + classInfo);
                  }

                  return InstrumentationSupport.toSensitive(args);
               } else {
                  Object[] returnArgs = new Object[args.length];
                  int argIndex = 0;
                  if (renderClass) {
                     returnArgs[argIndex++] = renderValue(args[0], classInfo);
                     if (debugLog.isDebugEnabled()) {
                        debugLog.debug(" renderClass in = " + args[0] + " out = " + returnArgs[0]);
                     }
                  }

                  for(int argInfoIndex = 0; argIndex < args.length; ++argIndex) {
                     if (argInfos == null) {
                        if (debugLog.isDebugEnabled()) {
                           debugLog.debug(" argInfos null sensitive " + argIndex);
                        }

                        returnArgs[argIndex] = "*****";
                     } else {
                        returnArgs[argIndex] = renderValue(args[argIndex], argInfos[argInfoIndex++]);
                        if (debugLog.isDebugEnabled()) {
                           debugLog.debug(" renderClass in = " + args[argIndex] + " out = " + returnArgs[argIndex]);
                        }
                     }
                  }

                  return returnArgs;
               }
            }
         } else {
            if (debugLog.isDebugEnabled()) {
               debugLog.debug(" sensitive 1 monType = " + monitorType + " infoMap = " + infoMap);
            }

            return InstrumentationSupport.toSensitive(args);
         }
      } else {
         return args;
      }
   }

   public static Object renderValue(Object valueToRender, ValueHandlingInfo valInfo) {
      if (debugLog.isDebugEnabled()) {
         debugLog.debug("renderValue(), value = " + valueToRender + " valInfo = " + valInfo);
      }

      if (valInfo != null && !valInfo.isSensitive()) {
         if (valueToRender == null) {
            if (debugLog.isDebugEnabled()) {
               debugLog.debug(" renderValue null value");
            }

            return null;
         } else {
            String rendererClassName = valInfo.getRendererClassName();
            ValueRenderer renderer = null;
            if (rendererClassName != null) {
               renderer = ((ValueHandlingInfoImpl)valInfo).getValueRenderer();
               if (renderer == null) {
                  if (debugLog.isDebugEnabled()) {
                     debugLog.debug(" renderValue find renderer by class");
                  }

                  renderer = findRendererByClassName(rendererClassName, valueToRender.getClass());
                  ((ValueHandlingInfoImpl)valInfo).setValueRenderer(renderer);
               }
            } else {
               if (debugLog.isDebugEnabled()) {
                  debugLog.debug(" renderValue find renderer by type or default");
               }

               renderer = findRendererByTypeClass(valueToRender.getClass());
            }

            return renderer.render(valueToRender);
         }
      } else {
         if (debugLog.isDebugEnabled()) {
            debugLog.debug(" renderValue sensitive, valInfo = " + valInfo);
         }

         return "*****";
      }
   }

   private static ValueRenderer findRendererByClassName(String rendererClassName, Class typeClass) {
      if (rendererClassName != null && rendererClassName.length() != 0) {
         ValueRenderer renderer = (ValueRenderer)rendererMap.get(rendererClassName);
         if (renderer == null) {
            Class var3 = ValueRenderingManager.class;
            synchronized(ValueRenderingManager.class) {
               renderer = (ValueRenderer)rendererMap.get(rendererClassName);
               if (renderer == null) {
                  try {
                     Class rendererClass = Class.forName(rendererClassName);
                     renderer = (ValueRenderer)rendererClass.newInstance();
                  } catch (Exception var6) {
                     renderer = findRendererByTypeClass(typeClass);
                     DiagnosticsLogger.logCannotLoadRenderer(rendererClassName, var6, renderer.getClass().getName());
                  }

                  rendererMap.put(rendererClassName, renderer);
               }
            }
         }

         return renderer;
      } else {
         return defaultValueRenderer;
      }
   }

   private static ValueRenderer findRendererByTypeClass(Class typeClass) {
      if (typeClass != null && typeBasedValueRendererMap.size() != 0) {
         ValueRenderer renderer = (ValueRenderer)typeBasedValueRendererMap.get(typeClass);
         if (renderer == null) {
            Class var2 = ValueRenderingManager.class;
            synchronized(ValueRenderingManager.class) {
               renderer = (ValueRenderer)typeBasedValueRendererMap.get(typeClass);
               if (renderer == null) {
                  Iterator var3 = getCandidateClasses(typeClass).iterator();

                  while(var3.hasNext()) {
                     Class candidate = (Class)var3.next();
                     renderer = (ValueRenderer)typeBasedValueRendererMap.get(typeClass);
                     if (renderer != null) {
                        break;
                     }
                  }

                  if (renderer == null) {
                     renderer = defaultValueRenderer;
                  }

                  typeBasedValueRendererMap.put(typeClass, renderer);
               }
            }
         }

         return renderer;
      } else {
         return defaultValueRenderer;
      }
   }

   private static List getCandidateClasses(Class typeClass) {
      List tempList = new ArrayList();
      if (typeClass == null) {
         return tempList;
      } else if (typeClass.isPrimitive()) {
         tempList.add(typeClass);
         return tempList;
      } else {
         Class[] var2 = typeClass.getInterfaces();
         int var3 = var2.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            Class theInterface = var2[var4];
            if (theInterface != null) {
               Class rtClass = theInterface.getClass();
               if (!rtClass.equals(Class.class) && !tempList.contains(rtClass)) {
                  tempList.add(rtClass);
                  tempList.addAll(getCandidateClasses(rtClass));
               }
            }
         }

         Class superClass = typeClass.getSuperclass();
         if (superClass != null) {
            tempList.addAll(getCandidateClasses(superClass));
         }

         return tempList;
      }
   }

   public static synchronized void initialize(Map typeBasedValueRendererMappings) {
      if (!initialized) {
         initialized = true;
         if (typeBasedValueRendererMappings != null) {
            Iterator var1 = typeBasedValueRendererMappings.entrySet().iterator();

            while(var1.hasNext()) {
               Map.Entry entry = (Map.Entry)var1.next();

               try {
                  Class typeClass = Class.forName((String)entry.getKey());
                  Class rendererClass = Class.forName((String)entry.getValue());
                  Object renderer = rendererClass.newInstance();
                  typeBasedValueRendererMap.put(typeClass, (ValueRenderer)renderer);
               } catch (Exception var6) {
                  DiagnosticsLogger.logCannotLoadTypeBasedRenderer((String)entry.getValue(), (String)entry.getKey(), var6);
               }
            }

         }
      }
   }

   private static class DefaultValueRenderer implements ValueRenderer {
      public DefaultValueRenderer() {
      }

      public Object render(Object input) {
         if (ValueRenderingManager.debugLog.isDebugEnabled()) {
            ValueRenderingManager.debugLog.debug("DefaultValueRenderer.render() input = " + input);
         }

         return input == null ? null : input;
      }
   }
}
