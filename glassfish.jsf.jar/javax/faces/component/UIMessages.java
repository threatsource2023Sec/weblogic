package javax.faces.component;

public class UIMessages extends UIComponentBase {
   public static final String COMPONENT_TYPE = "javax.faces.Messages";
   public static final String COMPONENT_FAMILY = "javax.faces.Messages";

   public UIMessages() {
      this.setRendererType("javax.faces.Messages");
   }

   public String getFamily() {
      return "javax.faces.Messages";
   }

   public String getFor() {
      return (String)this.getStateHelper().eval(UIMessages.PropertyKeys.forValue);
   }

   public void setFor(String newFor) {
      this.getStateHelper().put(UIMessages.PropertyKeys.forValue, newFor);
   }

   public boolean isGlobalOnly() {
      return (Boolean)this.getStateHelper().eval(UIMessages.PropertyKeys.globalOnly, false);
   }

   public void setGlobalOnly(boolean globalOnly) {
      this.getStateHelper().put(UIMessages.PropertyKeys.globalOnly, globalOnly);
   }

   public boolean isShowDetail() {
      return (Boolean)this.getStateHelper().eval(UIMessages.PropertyKeys.showDetail, false);
   }

   public void setShowDetail(boolean showDetail) {
      this.getStateHelper().put(UIMessages.PropertyKeys.showDetail, showDetail);
   }

   public boolean isShowSummary() {
      return (Boolean)this.getStateHelper().eval(UIMessages.PropertyKeys.showSummary, true);
   }

   public void setShowSummary(boolean showSummary) {
      this.getStateHelper().put(UIMessages.PropertyKeys.showSummary, showSummary);
   }

   public boolean isRedisplay() {
      return (Boolean)this.getStateHelper().eval(UIMessages.PropertyKeys.redisplay, true);
   }

   public void setRedisplay(boolean redisplay) {
      this.getStateHelper().put(UIMessages.PropertyKeys.redisplay, redisplay);
   }

   static enum PropertyKeys {
      forValue("for"),
      globalOnly,
      showDetail,
      showSummary,
      redisplay;

      String toString;

      private PropertyKeys(String toString) {
         this.toString = toString;
      }

      private PropertyKeys() {
      }

      public String toString() {
         return this.toString != null ? this.toString : super.toString();
      }
   }
}
