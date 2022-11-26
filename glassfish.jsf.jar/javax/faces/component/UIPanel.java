package javax.faces.component;

public class UIPanel extends UIComponentBase {
   public static final String COMPONENT_TYPE = "javax.faces.Panel";
   public static final String COMPONENT_FAMILY = "javax.faces.Panel";

   public UIPanel() {
      this.setRendererType((String)null);
   }

   public String getFamily() {
      return "javax.faces.Panel";
   }
}
