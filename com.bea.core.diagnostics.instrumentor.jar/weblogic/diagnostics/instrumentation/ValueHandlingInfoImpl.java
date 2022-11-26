package weblogic.diagnostics.instrumentation;

import java.io.Serializable;

public class ValueHandlingInfoImpl implements ValueHandlingInfo, Serializable {
   private static final long serialVersionUID = 1L;
   private String name = null;
   private String rendererClassName = null;
   private boolean sensitive = true;
   private boolean gathered = false;
   private ValueRenderer renderer = null;

   public ValueHandlingInfoImpl() {
   }

   public ValueHandlingInfoImpl(String name, String rendererClassName, boolean sensitive, boolean gathered) {
      this.name = name;
      this.rendererClassName = rendererClassName;
      this.sensitive = sensitive;
      this.gathered = gathered;
   }

   public String getName() {
      return this.name;
   }

   public String getRendererClassName() {
      return this.rendererClassName;
   }

   public boolean isSensitive() {
      return this.sensitive;
   }

   public boolean isGathered() {
      return this.gathered;
   }

   public void setName(String name) {
      this.name = name;
   }

   public void setRendererClassName(String rendererClassName) {
      this.rendererClassName = rendererClassName;
   }

   public void setSensitive(boolean sensitive) {
      this.sensitive = sensitive;
   }

   public void setGathered(boolean gathered) {
      this.gathered = gathered;
   }

   public ValueRenderer getValueRenderer() {
      return this.renderer;
   }

   public void setValueRenderer(ValueRenderer renderer) {
      this.renderer = renderer;
   }

   public static boolean compareInfo(ValueHandlingInfo info1, ValueHandlingInfo info2) {
      if (info1 == info2) {
         return true;
      } else if (info1 != null && info2 != null) {
         if (info1.isSensitive() == info2.isSensitive() && info1.isGathered() == info2.isGathered()) {
            if (info1.getName() == null && info2.getName() == null && info1.getRendererClassName() == null && info2.getRendererClassName() == null) {
               return true;
            } else if (info1.getName() != null && info2.getName() != null || info1.getRendererClassName() != null && info2.getRendererClassName() != null) {
               return info1.getName().equals(info2.getName()) && info1.getRendererClassName().equals(info2.getRendererClassName());
            } else {
               return false;
            }
         } else {
            return false;
         }
      } else {
         return false;
      }
   }

   public String toString() {
      StringBuffer buf = new StringBuffer();
      buf.append("Name=");
      buf.append(this.name);
      buf.append(", rendererClassName=");
      buf.append(this.rendererClassName);
      buf.append(", sensitive=");
      buf.append(this.sensitive);
      buf.append(", gathered=");
      buf.append(this.gathered);
      return new String(buf);
   }
}
