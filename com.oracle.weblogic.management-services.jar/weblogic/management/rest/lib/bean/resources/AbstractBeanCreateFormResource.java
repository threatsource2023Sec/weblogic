package weblogic.management.rest.lib.bean.resources;

import java.net.URI;
import javax.ws.rs.core.Response;
import org.codehaus.jettison.json.JSONObject;
import org.glassfish.admin.rest.model.RestJsonResponseBody;
import weblogic.management.rest.lib.bean.utils.BeanUtils;
import weblogic.management.rest.lib.bean.utils.ContainedBeanAttributeType;
import weblogic.management.rest.lib.bean.utils.PathUtils;

public abstract class AbstractBeanCreateFormResource extends BaseBeanResource implements GetterResource {
   private URI creatorUri;
   private ContainedBeanAttributeType propertyType;

   private ContainedBeanAttributeType getPropertyType() {
      return this.propertyType;
   }

   public void init(Object bean, ContainedBeanAttributeType propertyType, JSONObject query) throws Exception {
      super.init(bean, query);
      this.creatorUri = PathUtils.getTreeRelativeUri(this.invocationContext(), PathUtils.getBeanPath(this.invocationContext()), propertyType.getName());
      this.propertyType = propertyType;
   }

   protected Response get() throws Exception {
      RestJsonResponseBody rb = this.getRB();
      if (rb == null) {
         throw this.notFound();
      } else {
         return this.ok(rb);
      }
   }

   public RestJsonResponseBody getRB() throws Exception {
      if (this.invocationContext().bean() == null) {
         return null;
      } else {
         RestJsonResponseBody rb = this.restJsonResponseBody(this.getLinksFilter(this.invocationContext().query()));
         rb.addParentResourceLink(PathUtils.getUri(this.invocationContext()));
         rb.addSelfResourceLinks(this.getSubUri(new String[0]));
         if (this.creatorUri != null) {
            rb.addResourceLink("create", this.creatorUri);
         }

         rb.setEntity(BeanUtils.getCreateForm(this.invocationContext(), this.getPropertyType()));
         return rb;
      }
   }

   protected boolean supportsPost() throws Exception {
      return false;
   }
}
