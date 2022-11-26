package weblogic.diagnostics.instrumentation;

import java.util.Iterator;
import java.util.Properties;

public class StandardMonitorControl extends DiagnosticMonitorControl implements StandardMonitor {
   static final long serialVersionUID = 3811279294479551784L;

   public StandardMonitorControl(StandardMonitorControl mon) {
      super((DiagnosticMonitorControl)mon);
   }

   public StandardMonitorControl(String type) {
      this(type, type);
   }

   public StandardMonitorControl(String name, String type) {
      super(name, type);
   }

   public synchronized boolean merge(DiagnosticMonitorControl other) {
      return super.merge(other);
   }

   protected void mergeProperties(Properties p) {
      if (p != null) {
         if (this.getType().equals("DyeInjection")) {
            this.mergeDyeProperties(p);
         }

      }
   }

   private void mergeDyeProperties(Properties p) {
      Properties newProps = (Properties)this.getProperties().clone();
      Iterator otherkeysIt = p.keySet().iterator();

      while(otherkeysIt.hasNext()) {
         String key = (String)otherkeysIt.next();
         String otherValues = (String)p.get(key);
         if (otherValues != null) {
            String currentValues = (String)newProps.get(key);
            if (currentValues != null) {
               String merge = this.mergeValues(currentValues, otherValues);
               newProps.setProperty(key, merge);
            } else {
               newProps.setProperty(key, otherValues);
            }
         }
      }

      this.setProperties(newProps);
   }

   private String mergeValues(String currentValues, String otherValues) {
      String merge = null;
      String[] currentArray = currentValues.split(",");
      String[] otherArray = otherValues.split(",");
      String[] union = InstrumentationUtils.unionOf(currentArray, otherArray);
      String[] var7 = union;
      int var8 = union.length;

      for(int var9 = 0; var9 < var8; ++var9) {
         String s = var7[var9];
         if (merge == null) {
            merge = s;
         } else {
            merge = merge + "," + s;
         }
      }

      return merge;
   }
}
