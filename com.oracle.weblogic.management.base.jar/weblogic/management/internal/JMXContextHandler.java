package weblogic.management.internal;

import javax.management.ObjectName;
import weblogic.security.service.ContextElement;
import weblogic.security.service.ContextHandler;

public class JMXContextHandler implements ContextHandler {
   private static final String[] common_keys = new String[]{"com.bea.contextelement.jmx.ObjectName", "com.bea.contextelement.jmx.ShortName"};
   private static final String[] invoke_keys = new String[]{"com.bea.contextelement.jmx.ObjectName", "com.bea.contextelement.jmx.ShortName", "com.bea.contextelement.jmx.Parameters", "com.bea.contextelement.jmx.Signature", "com.bea.contextelement.jmx.AuditProtectedArgInfo", "com.bea.contextelement.jmx.OldAttributeValue"};
   private final ObjectName objectName;
   private final String[] keys;
   private final Object[] parameters;
   private final String[] signature;
   private final String auditProtectedArgInfo;
   private final Object oldAttributeValue;

   public JMXContextHandler(ObjectName objectName) {
      this.objectName = objectName;
      this.keys = common_keys;
      this.parameters = null;
      this.signature = null;
      this.auditProtectedArgInfo = null;
      this.oldAttributeValue = null;
   }

   public JMXContextHandler(ObjectName objectName, Object[] parameters, String[] signature, String auditProtectedArgInfo, Object oldValue) {
      this.objectName = objectName;
      this.parameters = parameters;
      this.signature = signature;
      this.auditProtectedArgInfo = auditProtectedArgInfo;
      this.keys = invoke_keys;
      this.oldAttributeValue = oldValue;
   }

   public int size() {
      return this.keys.length;
   }

   public String[] getNames() {
      return this.keys;
   }

   public Object getValue(String name) {
      if ("com.bea.contextelement.jmx.ObjectName".equals(name)) {
         return this.objectName;
      } else if ("com.bea.contextelement.jmx.ShortName".equals(name) && this.objectName != null) {
         return this.objectName.getKeyProperty("Name");
      } else if ("com.bea.contextelement.jmx.Parameters".equals(name)) {
         return this.parameters;
      } else if ("com.bea.contextelement.jmx.Signature".equals(name)) {
         return this.signature;
      } else if ("com.bea.contextelement.jmx.AuditProtectedArgInfo".equals(name)) {
         return this.auditProtectedArgInfo;
      } else {
         return "com.bea.contextelement.jmx.OldAttributeValue".equals(name) ? this.oldAttributeValue : null;
      }
   }

   public ContextElement[] getValues(String[] requested) {
      ContextElement[] result = new ContextElement[requested.length];
      int found = 0;

      for(int i = 0; i < requested.length; ++i) {
         Object val = this.getValue(requested[i]);
         if (val != null) {
            result[found++] = new ContextElement(requested[i], val);
         }
      }

      if (found < requested.length) {
         ContextElement[] tooBig = result;
         result = new ContextElement[found];
         System.arraycopy(tooBig, 0, result, 0, found);
      }

      return result;
   }
}
