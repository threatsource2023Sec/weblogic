package weblogic.management.rest.lib.bean.resources;

import java.util.List;
import javax.ws.rs.core.Response;
import org.codehaus.jettison.json.JSONObject;
import org.glassfish.admin.rest.model.RestJsonResponseBody;
import weblogic.management.rest.lib.bean.utils.BeanConfigurationTransaction;
import weblogic.management.rest.lib.bean.utils.BeanCreator;
import weblogic.management.rest.lib.bean.utils.ContainedBeanType;
import weblogic.management.rest.lib.bean.utils.InvocationContext;
import weblogic.management.rest.lib.bean.utils.MethodType;
import weblogic.management.rest.lib.bean.utils.PartitionUtils;
import weblogic.management.rest.lib.bean.utils.PathUtils;
import weblogic.management.rest.lib.bean.utils.SingletonChildBeanCreator;
import weblogic.management.rest.lib.bean.utils.SingletonChildBeanDeleter;
import weblogic.management.rest.lib.utils.ConfigurationTransaction;

public abstract class AbstractSingletonChildBeanResource extends AbstractBeanResource {
   private Object parentBean;
   private ContainedBeanType propertyType;

   public void init(Object parentBean, Object childBean, ContainedBeanType propertyType, JSONObject query) throws Exception {
      super.init(childBean, query);
      this.parentBean = parentBean;
      this.propertyType = propertyType;
   }

   public RestJsonResponseBody getRB() throws Exception {
      RestJsonResponseBody rb = super.getRB();
      if (rb != null && this.isEditableTree() && this.hasCreator()) {
         List path = PathUtils.getBeanPath(this.invocationContext().clone(PathUtils.getParent(this.invocationContext())));
         rb.addResourceLink("create-form", PathUtils.getTreeRelativeUri(this.invocationContext(), path, this.getPropertyType().getCreateFormResourceName()));
      }

      return rb;
   }

   protected Response post(JSONObject entity) throws Exception {
      return this.exists() ? this.update(entity) : this.create(entity);
   }

   protected void verifyPost() throws Exception {
      super.verifyPost();
      if (this.exists()) {
         if (this.isReadOnly()) {
            throw this.badRequest(this.beanFormatter().msgCannotEditReadOnlyExistingOptionalSingleton());
         }
      } else if (!this.hasCreator()) {
         throw this.badRequest(this.beanFormatter().msgCannotCreateNonExistingOptionalSingleton());
      }

   }

   protected Response create(JSONObject entity) throws Exception {
      this.verifyPost();
      return this._create(entity);
   }

   protected Response _create(JSONObject entity) throws Exception {
      return this._create(entity, new SingletonChildBeanCreator(this.invocationContext().clone(this.parentBean), this.getPropertyType()));
   }

   protected Response _create(JSONObject entity, BeanCreator contents) throws Exception {
      ConfigurationTransaction.Result result = BeanConfigurationTransaction.doTransaction(this.invocationContext(), entity, contents, this.isEditTree(), this.invocationContext().saveChanges());
      if (result.succeeded()) {
         return this.created(this.responseBody(), this.getSubUri(new String[0]));
      } else if (result.getException() == null) {
         throw this.badRequest(this.formatter().msgAlreadyExists(this.getIdentityAsString()));
      } else {
         return this.badRequest(result.report(this.responseBody()));
      }
   }

   protected boolean supportsPost() throws Exception {
      if (!this.isEditableTree()) {
         return false;
      } else {
         return this.hasCreator() || !this.isReadOnly();
      }
   }

   private boolean hasCreator() throws Exception {
      MethodType mt = this.getPropertyType().getCreator(this.invocationContext().request());
      return mt != null && PartitionUtils.isVisible((InvocationContext)this.invocationContext(), mt);
   }

   protected Response _delete() throws Exception {
      return this._delete(new SingletonChildBeanDeleter(this.invocationContext(), this.getPropertyType()));
   }

   protected void verifyDelete() throws Exception {
      super.verifyDelete();
      if (!this.exists()) {
         throw this.notFound();
      }
   }

   protected boolean supportsDelete() throws Exception {
      return this.isEditableTree() && this.hasDestroyer();
   }

   private boolean hasDestroyer() throws Exception {
      MethodType mt = this.getPropertyType().getDestroyer(this.invocationContext().request());
      return mt != null && PartitionUtils.isVisible((InvocationContext)this.invocationContext(), mt);
   }

   protected Object _getSubResource(String subResourceName, JSONObject query) throws Exception {
      return !this.exists() ? null : super._getSubResource(subResourceName, query);
   }

   private boolean exists() {
      return this.getBean() != null;
   }

   protected ContainedBeanType getPropertyType() {
      return this.propertyType;
   }
}
