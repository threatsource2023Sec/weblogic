package javax.faces.component;

public class UIColumn extends UIComponentBase {
   public static final String COMPONENT_TYPE = "javax.faces.Column";
   public static final String COMPONENT_FAMILY = "javax.faces.Column";

   public UIColumn() {
      this.setRendererType((String)null);
   }

   public String getFamily() {
      return "javax.faces.Column";
   }

   public UIComponent getFooter() {
      return this.getFacet("footer");
   }

   public void setFooter(UIComponent footer) {
      this.getFacets().put("footer", footer);
   }

   public UIComponent getHeader() {
      return this.getFacet("header");
   }

   public void setHeader(UIComponent header) {
      this.getFacets().put("header", header);
   }
}
