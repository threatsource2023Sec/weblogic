package javax.faces.view.facelets;

import java.net.URL;

/** @deprecated */
@Deprecated
public abstract class ResourceResolver {
   public static final String FACELETS_RESOURCE_RESOLVER_PARAM_NAME = "javax.faces.FACELETS_RESOURCE_RESOLVER";

   public abstract URL resolveUrl(String var1);
}
