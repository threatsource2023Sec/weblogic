package com.oracle.weblogic.diagnostics.watch.beans.jmx;

import com.oracle.weblogic.diagnostics.expressions.Exclude;
import com.oracle.weblogic.diagnostics.expressions.ExpressionBeanRuntimeException;
import com.oracle.weblogic.diagnostics.expressions.NotEnoughDataException;
import com.oracle.weblogic.diagnostics.expressions.Traceable;
import com.oracle.weblogic.diagnostics.expressions.TrackedValueSource;
import com.oracle.weblogic.diagnostics.expressions.Utils;
import com.oracle.weblogic.diagnostics.expressions.WLDFI18n;
import com.oracle.weblogic.diagnostics.l10n.DiagnosticsFrameworkTextTextFormatter;
import com.oracle.weblogic.diagnostics.utils.DiagnosticsUtils;
import java.util.HashMap;
import java.util.Set;
import javax.inject.Inject;
import javax.management.AttributeNotFoundException;
import javax.management.InstanceNotFoundException;
import javax.management.MBeanServerConnection;
import javax.management.ObjectName;
import javax.management.QueryExp;
import org.glassfish.hk2.api.ServiceLocator;
import weblogic.diagnostics.debug.DebugLogger;

@WLDFI18n(
   resourceBundle = "com.oracle.weblogic.diagnostics.watch.beans.jmx.AbstractJMXMetricSource"
)
public abstract class AbstractJMXMetricSource implements JMXMBeanServerSource {
   private static final DiagnosticsFrameworkTextTextFormatter txtFormatter = DiagnosticsFrameworkTextTextFormatter.getInstance();
   private static final DebugLogger debugLogger = DebugLogger.getDebugLogger("DebugDiagnosticsExpressionMetrics");
   @Inject
   private ServiceLocator locator;
   private final HashMap specsMap = new HashMap();
   private MBeanServerConnection mbeanServer;

   protected AbstractJMXMetricSource() {
   }

   @WLDFI18n("platform.query.short")
   public Iterable query(@WLDFI18n(name = "onPattern",value = "onPattern.desc") String onPattern, @WLDFI18n(name = "attributePattern",value = "attributePattern.desc") String attributePattern) throws JMXExpressionBeanRuntimeException {
      return this.query(onPattern, attributePattern, (QueryExp)null);
   }

   @WLDFI18n("platform.getAttribute.short")
   public Object getAttribute(@WLDFI18n(name = "objectNamePattern",value = "platform.getAttribute.param.onPattern") String objectNamePattern, @WLDFI18n(name = "attribute",value = "platform.getAttribute.param.attribute") String attribute) {
      ObjectName instance = this.locateInstance(objectNamePattern);
      return this.getValue(instance, attribute);
   }

   protected Iterable query(String onPattern, String attributePattern, QueryExp filter) throws JMXExpressionBeanRuntimeException {
      TrackedValueSource collector = this.findOrCreateMetricSpec(onPattern, attributePattern, filter);
      return collector;
   }

   @Exclude
   protected TrackedValueSource findOrCreateMetricSpec(String namePattern, String attributePattern, QueryExp filter) {
      String key = this.createMetricKey(namePattern, attributePattern);
      TrackedValueSource collector = (TrackedValueSource)this.specsMap.get(key);
      if (collector == null) {
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug("Adding collector for " + key);
         }

         collector = new JMXTrackedItemCollection(this, namePattern, attributePattern, key, filter);
         this.locator.inject(collector);
         this.locator.postConstruct(collector);
         this.addSpec((TrackedValueSource)collector);
      } else if (debugLogger.isDebugEnabled()) {
         debugLogger.debug("Found collector for " + key);
      }

      return (TrackedValueSource)collector;
   }

   @Exclude
   protected TrackedValueSource findOrCreateMetricSpec(String namePattern, String attributePattern) {
      return this.findOrCreateMetricSpec(namePattern, attributePattern, (QueryExp)null);
   }

   @Exclude
   public synchronized MBeanServerConnection getMBeanServerConnection() {
      if (this.mbeanServer == null) {
         this.mbeanServer = this.lookupMBeanServerReference();
      }

      return this.mbeanServer;
   }

   protected abstract MBeanServerConnection lookupMBeanServerReference();

   protected ServiceLocator getLocator() {
      return this.locator;
   }

   protected TrackedValueSource findSpec(String key) {
      return (TrackedValueSource)this.specsMap.get(key);
   }

   protected TrackedValueSource addSpec(TrackedValueSource spec) {
      return (TrackedValueSource)this.specsMap.put(spec.getKey(), spec);
   }

   protected ObjectName locateInstance(String instancePattern) {
      ObjectName foundInstance = null;

      try {
         ObjectName objectNamePattern = new ObjectName(instancePattern);
         if (objectNamePattern.isPattern()) {
            Set names = this.lookupMBeanServerReference().queryNames(objectNamePattern, (QueryExp)null);
            if (names.size() > 1) {
               throw new ExpressionBeanRuntimeException(txtFormatter.getGetAttributeNameMapsToMultipleInstances());
            }

            if (names.size() <= 0) {
               throw new ExpressionBeanRuntimeException(txtFormatter.getGetAttributeInstanceNotFoundForPattern(objectNamePattern.getCanonicalName()));
            }

            foundInstance = (ObjectName)names.iterator().next();
         } else {
            foundInstance = objectNamePattern;
         }

         return foundInstance;
      } catch (Throwable var5) {
         throw new ExpressionBeanRuntimeException(var5);
      }
   }

   private String createMetricKey(String namePattern, String attributePattern) {
      return namePattern + "//" + attributePattern;
   }

   private Object getValue(ObjectName instance, String attribute) {
      Object value = null;

      try {
         value = this.lookupMBeanServerReference().getAttribute(instance, attribute);
         if (DiagnosticsUtils.isLeafValueType(value.getClass())) {
            value = Utils.convertPrimitiveToMetricValue(value, attribute, (Traceable)null, instance.getCanonicalName());
         }

         return value;
      } catch (InstanceNotFoundException | AttributeNotFoundException var5) {
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug("Caught exception accessing value " + attribute + " from instance " + instance, var5);
         }

         throw new NotEnoughDataException(this.createMetricKey(instance.getCanonicalName(), attribute));
      } catch (ExpressionBeanRuntimeException var6) {
         throw var6;
      } catch (Throwable var7) {
         if (!DiagnosticsUtils.isIgnorable(var7)) {
            throw new ExpressionBeanRuntimeException(var7);
         } else {
            return value;
         }
      }
   }
}
