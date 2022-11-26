package weblogic.diagnostics.lifecycle;

import java.lang.reflect.Method;

public final class ComponentRegistry {
   private static DiagnosticComponentLifecycle[] allComponents = null;
   private static int[] foundationComponents = null;
   private static int[] nonFoundationComponents = null;
   private static final Boolean NOT_FOUNDATION = new Boolean(false);
   private static final Boolean IS_FOUNDATION = new Boolean(true);
   private static final Object[][] componentSpecs;

   public static String getWLDFComponentName(DiagnosticComponentLifecycle component) {
      if (allComponents == null) {
         return null;
      } else {
         for(int i = 0; i < allComponents.length; ++i) {
            DiagnosticComponentLifecycle thisComponent = allComponents[i];
            if (component == thisComponent) {
               return (String)componentSpecs[i][0];
            }
         }

         return null;
      }
   }

   static DiagnosticComponentLifecycle[] getNonFoundationWLDFComponents() {
      try {
         if (nonFoundationComponents == null) {
            initComponents();
         }

         return getComponentArray(nonFoundationComponents);
      } catch (RuntimeException var1) {
         throw var1;
      } catch (Exception var2) {
         throw new RuntimeException(var2);
      }
   }

   static DiagnosticComponentLifecycle[] getFoundationWLDFComponents() {
      try {
         if (foundationComponents == null) {
            initComponents();
         }

         return getComponentArray(foundationComponents);
      } catch (RuntimeException var1) {
         throw var1;
      } catch (Exception var2) {
         throw new RuntimeException(var2);
      }
   }

   private static DiagnosticComponentLifecycle[] getComponentArray(int[] componentIndexArray) {
      DiagnosticComponentLifecycle[] componentArray = new DiagnosticComponentLifecycle[componentIndexArray.length];

      for(int i = 0; i < componentIndexArray.length; ++i) {
         componentArray[i] = allComponents[componentIndexArray[i]];
      }

      return componentArray;
   }

   private static void initComponents() throws Exception {
      int[] foundationComponentsTemp = new int[componentSpecs.length];
      int foundationComponentsTempIndex = 0;
      int[] nonFoundationComponentsTemp = new int[componentSpecs.length];
      int nonFoundationComponentsTempIndex = 0;
      allComponents = new DiagnosticComponentLifecycle[componentSpecs.length];

      for(int i = 0; i < componentSpecs.length; ++i) {
         Object[] spec = (Object[])componentSpecs[i];
         String componentName = (String)spec[0];
         String componentClassName = (String)spec[1];
         Boolean componentType = (Boolean)spec[2];
         boolean isFoundation = componentType;
         Class componentClass = null;

         try {
            componentClass = Class.forName(componentClassName);
            Method getInstanceMethod = componentClass.getDeclaredMethod("getInstance");
            DiagnosticComponentLifecycle component = (DiagnosticComponentLifecycle)getInstanceMethod.invoke((Object)null);
            allComponents[i] = component;
            if (isFoundation) {
               foundationComponentsTemp[foundationComponentsTempIndex] = i;
               ++foundationComponentsTempIndex;
            } else {
               nonFoundationComponentsTemp[nonFoundationComponentsTempIndex] = i;
               ++nonFoundationComponentsTempIndex;
            }
         } catch (RuntimeException var13) {
            throw var13;
         } catch (Exception var14) {
            throw new RuntimeException(var14);
         }
      }

      foundationComponents = new int[foundationComponentsTempIndex];
      System.arraycopy(foundationComponentsTemp, 0, foundationComponents, 0, foundationComponentsTempIndex);
      nonFoundationComponents = new int[nonFoundationComponentsTempIndex];
      System.arraycopy(nonFoundationComponentsTemp, 0, nonFoundationComponents, 0, nonFoundationComponentsTempIndex);
   }

   static {
      componentSpecs = new Object[][]{{"Diagnostic Image", "weblogic.diagnostics.lifecycle.DiagnosticImageLifecycleImpl", IS_FOUNDATION}, {"Diagnostic Context", "weblogic.diagnostics.lifecycle.DiagnosticContextLifecycleImpl", IS_FOUNDATION}, {"Manager", "weblogic.diagnostics.lifecycle.ManagerLifecycleImpl", IS_FOUNDATION}, {"Archive", "weblogic.diagnostics.lifecycle.ArchiveLifecycleImpl", IS_FOUNDATION}, {"WLDF Module Deployment Extension", "weblogic.diagnostics.lifecycle.WLDFModuleLifecycleImpl", IS_FOUNDATION}, {"Domain Logging", "weblogic.diagnostics.lifecycle.LoggingLifecycleImpl", NOT_FOUNDATION}, {"Harvester", "weblogic.diagnostics.harvester.internal.HarvesterLifecycleImpl", NOT_FOUNDATION}, {"Watches & Notifications", "weblogic.diagnostics.watch.WatchLifecycleImpl", NOT_FOUNDATION}, {"Accessor", "weblogic.diagnostics.lifecycle.AccessorLifecycleImpl", NOT_FOUNDATION}, {"Instrumentation System", "weblogic.diagnostics.lifecycle.InstrumentationLifecycleImpl", NOT_FOUNDATION}, {"WLDF archive data retirement", "weblogic.diagnostics.lifecycle.DataRetirementLifecycleImpl", NOT_FOUNDATION}, {"WLDF Server Profile and Runtime Control Service", "weblogic.diagnostics.lifecycle.RuntimeControlService", NOT_FOUNDATION}, {"WLDF HealthCheck Service", "weblogic.diagnostics.lifecycle.HealthCheckLifecycleImpl", NOT_FOUNDATION}};
   }
}
