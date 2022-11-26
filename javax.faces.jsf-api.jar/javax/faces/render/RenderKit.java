package javax.faces.render;

import java.io.OutputStream;
import java.io.Writer;
import javax.faces.context.ResponseStream;
import javax.faces.context.ResponseWriter;

public abstract class RenderKit {
   public abstract void addRenderer(String var1, String var2, Renderer var3);

   public abstract Renderer getRenderer(String var1, String var2);

   public abstract ResponseStateManager getResponseStateManager();

   public abstract ResponseWriter createResponseWriter(Writer var1, String var2, String var3);

   public abstract ResponseStream createResponseStream(OutputStream var1);
}
