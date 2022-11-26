package javax.faces.render;

import java.io.OutputStream;
import java.io.Writer;
import java.util.Collections;
import java.util.Iterator;
import java.util.Set;
import javax.faces.context.ResponseStream;
import javax.faces.context.ResponseWriter;

public abstract class RenderKit {
   public abstract void addRenderer(String var1, String var2, Renderer var3);

   public abstract Renderer getRenderer(String var1, String var2);

   public abstract ResponseStateManager getResponseStateManager();

   public abstract ResponseWriter createResponseWriter(Writer var1, String var2, String var3);

   public abstract ResponseStream createResponseStream(OutputStream var1);

   public Iterator getComponentFamilies() {
      Set empty = Collections.emptySet();
      return empty.iterator();
   }

   public Iterator getRendererTypes(String componentFamily) {
      Set empty = Collections.emptySet();
      return empty.iterator();
   }

   public void addClientBehaviorRenderer(String type, ClientBehaviorRenderer renderer) {
      throw new UnsupportedOperationException("The default implementation must override this method");
   }

   public ClientBehaviorRenderer getClientBehaviorRenderer(String type) {
      throw new UnsupportedOperationException("The default implementation must override this method");
   }

   public Iterator getClientBehaviorRendererTypes() {
      throw new UnsupportedOperationException("The default implementation must override this method");
   }
}
