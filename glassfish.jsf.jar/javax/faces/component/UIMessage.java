package javax.faces.component;

public class UIMessage extends UIComponentBase {
   public static final String COMPONENT_TYPE = "javax.faces.Message";
   public static final String COMPONENT_FAMILY = "javax.faces.Message";

   public UIMessage() {
      this.setRendererType("javax.faces.Message");
   }

   public String getFamily() {
      return "javax.faces.Message";
   }

   public String getFor() {
      return (String)this.getStateHelper().eval(UIMessage.PropertyKeys.forValue);
   }

   public void setFor(String newFor) {
      this.getStateHelper().put(UIMessage.PropertyKeys.forValue, newFor);
   }

   public boolean isShowDetail() {
      return (Boolean)this.getStateHelper().eval(UIMessage.PropertyKeys.showDetail, true);
   }

   public void setShowDetail(boolean showDetail) {
      this.getStateHelper().put(UIMessage.PropertyKeys.showDetail, showDetail);
   }

   public boolean isShowSummary() {
      return (Boolean)this.getStateHelper().eval(UIMessage.PropertyKeys.showSummary, false);
   }

   public void setShowSummary(boolean showSummary) {
      this.getStateHelper().put(UIMessage.PropertyKeys.showSummary, showSummary);
   }

   public boolean isRedisplay() {
      return (Boolean)this.getStateHelper().eval(UIMessage.PropertyKeys.redisplay, true);
   }

   public void setRedisplay(boolean redisplay) {
      this.getStateHelper().put(UIMessage.PropertyKeys.redisplay, redisplay);
   }

   static enum PropertyKeys {
      forValue("for"),
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
