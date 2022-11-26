package javax.faces.component;

public class UINamingContainer extends UIComponentBase implements NamingContainer {
   public static final String COMPONENT_TYPE = "javax.faces.NamingContainer";
   public static final String COMPONENT_FAMILY = "javax.faces.NamingContainer";

   public UINamingContainer() {
      this.setRendererType((String)null);
   }

   public String getFamily() {
      return "javax.faces.NamingContainer";
   }
}
