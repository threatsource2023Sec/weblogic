package javax.faces.view.facelets;

import javax.el.MethodExpression;
import javax.el.ValueExpression;
import javax.faces.view.Location;

public abstract class TagAttribute {
   public abstract boolean getBoolean(FaceletContext var1);

   public abstract int getInt(FaceletContext var1);

   public abstract String getLocalName();

   public abstract Location getLocation();

   public abstract MethodExpression getMethodExpression(FaceletContext var1, Class var2, Class[] var3);

   public abstract String getNamespace();

   public abstract Object getObject(FaceletContext var1);

   public abstract String getQName();

   public abstract String getValue();

   public abstract String getValue(FaceletContext var1);

   public abstract Object getObject(FaceletContext var1, Class var2);

   public abstract ValueExpression getValueExpression(FaceletContext var1, Class var2);

   public abstract boolean isLiteral();

   public Tag getTag() {
      return null;
   }

   public void setTag(Tag tag) {
   }
}
