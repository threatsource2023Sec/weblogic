package weblogic.management.rest.lib.bean.resources;

import java.util.Iterator;
import java.util.Map;
import javax.ws.rs.core.Response;
import org.codehaus.jettison.json.JSONObject;
import org.glassfish.admin.rest.model.RestJsonResponseBody;
import weblogic.management.rest.lib.bean.utils.ActionType;
import weblogic.management.rest.lib.bean.utils.AttributeType;
import weblogic.management.rest.lib.bean.utils.BeanConfigurationTransaction;
import weblogic.management.rest.lib.bean.utils.BeanType;
import weblogic.management.rest.lib.bean.utils.BeanUpdater;
import weblogic.management.rest.lib.bean.utils.BeanUtils;
import weblogic.management.rest.lib.bean.utils.ContainedBeanType;
import weblogic.management.rest.lib.bean.utils.ContainedBeansType;
import weblogic.management.rest.lib.bean.utils.CustomResourceType;
import weblogic.management.rest.lib.bean.utils.InvocationContext;
import weblogic.management.rest.lib.bean.utils.PartitionUtils;
import weblogic.management.rest.lib.bean.utils.PathUtils;
import weblogic.management.rest.lib.bean.utils.PropertyType;
import weblogic.management.rest.lib.bean.utils.QueryUtils;
import weblogic.management.rest.lib.bean.utils.ReferencedBeanType;
import weblogic.management.rest.lib.bean.utils.ReferencedBeansType;
import weblogic.management.rest.lib.bean.utils.ResourceDef;
import weblogic.management.rest.lib.utils.ConfigurationTransaction;
import weblogic.management.rest.lib.utils.SecurityUtils;

public abstract class AbstractBeanResource extends BaseBeanResource implements GetterResource {
   protected Response get() throws Exception {
      this.verifyGet();
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
         rb.addParentResourceLink(PathUtils.getParentUri(this.invocationContext()));
         BeanUtils.getBean(this.invocationContext(), rb);
         Map children = QueryUtils.getChildren(this.invocationContext().request(), this.invocationContext().query());
         if (children != null) {
            JSONObject model = rb.getEntity();
            Iterator var4 = children.entrySet().iterator();

            while(var4.hasNext()) {
               Map.Entry e = (Map.Entry)var4.next();
               String childPropertyName = (String)e.getKey();
               JSONObject childQuery = (JSONObject)e.getValue();
               Object childResource = this._getSubResource(childPropertyName, childQuery);
               if (childResource != null && childResource instanceof GetterResource) {
                  RestJsonResponseBody childRb = ((GetterResource)childResource).getRB();
                  Object child = childRb != null ? childRb.toJson() : JSONObject.NULL;
                  model.put(childPropertyName, child);
               }
            }
         }

         return rb;
      }
   }

   protected Response update(JSONObject entity) throws Exception {
      this.verifyPost();
      return this._update(entity);
   }

   protected Response _update(JSONObject entity) throws Exception {
      return this._update(entity, new BeanUpdater(this.invocationContext()));
   }

   protected Response _update(JSONObject entity, ConfigurationTransaction.TransactionContents contents) throws Exception {
      ConfigurationTransaction.Result result = BeanConfigurationTransaction.doTransaction(this.invocationContext(), entity, contents, this.isEditTree(), this.invocationContext().saveChanges(), true);
      if (result.succeeded()) {
         return this.updated(result.report(this.responseBody()));
      } else if (result.getException() == null) {
         throw this.notFound();
      } else {
         return this.badRequest(result.report(this.responseBody()));
      }
   }

   protected boolean supportsPost() throws Exception {
      return this.isEditableTree() && !this.isReadOnly();
   }

   protected Response _delete(ConfigurationTransaction.TransactionContents contents) throws Exception {
      ConfigurationTransaction.Result result = BeanConfigurationTransaction.doTransaction(this.invocationContext(), (JSONObject)null, contents, this.isEditTree(), this.invocationContext().saveChanges());
      if (result.succeeded()) {
         return this.deleted(this.responseBody());
      } else if (result.getException() == null) {
         throw this.notFound();
      } else {
         return this.badRequest(result.report(this.responseBody()));
      }
   }

   protected String getIdentityAsString() throws Exception {
      return this.getResourceName();
   }

   protected boolean isReadOnly() throws Exception {
      BeanType type = BeanType.getBeanType(this.invocationContext().request(), this.invocationContext().bean());
      Iterator var2 = type.getPropertyTypes().iterator();

      PropertyType t;
      do {
         if (!var2.hasNext()) {
            var2 = type.getReferencedBeanTypes().iterator();

            ReferencedBeanType t;
            do {
               if (!var2.hasNext()) {
                  var2 = type.getReferencedBeansTypes().iterator();

                  ReferencedBeansType t;
                  do {
                     if (!var2.hasNext()) {
                        return true;
                     }

                     t = (ReferencedBeansType)var2.next();
                  } while(!t.isVisibleToRequest(this.invocationContext()) || !this.isWritable(t));

                  return false;
               }

               t = (ReferencedBeanType)var2.next();
            } while(!t.isVisibleToRequest(this.invocationContext()) || !this.isWritable(t));

            return false;
         }

         t = (PropertyType)var2.next();
      } while(!t.isVisibleToRequest(this.invocationContext()) || !this.isWritable(t));

      return false;
   }

   private boolean isWritable(AttributeType t) throws Exception {
      return !t.isWritable() ? false : PartitionUtils.isVisible((InvocationContext)this.invocationContext(), t);
   }

   public Object getSubResource(String subResourceName, JSONObject query) throws Exception {
      Object subResource = this._getSubResource(subResourceName, query);
      if (subResource == null) {
         throw this.notFound(this.formatter().msgNotFound(subResourceName));
      } else {
         return subResource;
      }
   }

   protected Object _getSubResource(String subResourceName, JSONObject query) throws Exception {
      BeanType type = BeanType.getBeanType(this.invocationContext().request(), this.invocationContext().bean());
      CustomResourceType t = type.getCustomResourceType(subResourceName);
      Class clazz;
      Object subResource;
      if (t != null && t.isVisibleToRequest(this.invocationContext())) {
         clazz = ResourceDef.getResourceClass(t.getResourceDef(this.getBeanTree()));
         if (clazz == null) {
            return null;
         } else if (!PartitionUtils.isVisible((InvocationContext)this.invocationContext(), t)) {
            return null;
         } else {
            subResource = this.getSubResource(clazz);
            if (subResource instanceof AbstractCustomResource) {
               ((AbstractCustomResource)subResource).init(this, this.invocationContext().bean(), t, query);
            }

            return subResource;
         }
      } else {
         ContainedBeanType t = type.getContainedBeanType(subResourceName);
         if (t != null && t.isVisibleToRequest(this.invocationContext())) {
            clazz = ResourceDef.getResourceClass(t.getResourceDef(this.getBeanTree()));
            if (clazz == null) {
               return null;
            } else if (!PartitionUtils.isVisible((InvocationContext)this.invocationContext(), t)) {
               return null;
            } else {
               subResource = this.getSubResource(clazz);
               if (subResource instanceof AbstractSingletonChildBeanResource) {
                  ((AbstractSingletonChildBeanResource)subResource).init(this.invocationContext().bean(), BeanUtils.getBeanProperty(this.invocationContext(), (AttributeType)t), t, query);
               }

               return subResource;
            }
         } else {
            t = type.getContainedBeanCreateFormType(subResourceName);
            if (t != null && t.isVisibleToRequest(this.invocationContext())) {
               if (!PartitionUtils.isVisible((InvocationContext)this.invocationContext(), t)) {
                  return null;
               } else {
                  clazz = ResourceDef.getResourceClass(t.getCreateFormResourceDef(this.getBeanTree()));
                  if (clazz == null) {
                     return null;
                  } else {
                     subResource = this.getSubResource(clazz);
                     if (subResource instanceof AbstractBeanCreateFormResource) {
                        ((AbstractBeanCreateFormResource)subResource).init(this.invocationContext().bean(), t, query);
                     }

                     return subResource;
                  }
               }
            } else {
               ContainedBeansType t = type.getContainedBeansType(subResourceName);
               if (t != null && t.isVisibleToRequest(this.invocationContext())) {
                  if (!PartitionUtils.isVisible((InvocationContext)this.invocationContext(), t)) {
                     return null;
                  } else {
                     clazz = ResourceDef.getResourceClass(t.getResourceDef(this.getBeanTree()));
                     if (clazz == null) {
                        return null;
                     } else {
                        subResource = this.getSubResource(clazz);
                        if (subResource instanceof AbstractBeanCollectionResource) {
                           ((AbstractBeanCollectionResource)subResource).init(this.invocationContext().bean(), t, query);
                        }

                        return subResource;
                     }
                  }
               } else {
                  t = type.getContainedBeansCreateFormType(subResourceName);
                  if (t != null && t.isVisibleToRequest(this.invocationContext())) {
                     if (!PartitionUtils.isVisible((InvocationContext)this.invocationContext(), t)) {
                        return null;
                     } else {
                        clazz = ResourceDef.getResourceClass(t.getCreateFormResourceDef(this.getBeanTree()));
                        if (clazz == null) {
                           return null;
                        } else {
                           subResource = this.getSubResource(clazz);
                           if (subResource instanceof AbstractBeanCreateFormResource) {
                              ((AbstractBeanCreateFormResource)subResource).init(this.invocationContext().bean(), t, query);
                           }

                           return subResource;
                        }
                     }
                  } else {
                     ActionType t = type.getActionType(subResourceName);
                     if (t != null && t.isVisibleToRequest(this.invocationContext())) {
                        if (!PartitionUtils.isVisible((InvocationContext)this.invocationContext(), t)) {
                           return null;
                        } else {
                           clazz = ResourceDef.getResourceClass(t.getResourceDef(this.getBeanTree()));
                           if (clazz == null) {
                              return null;
                           } else if (this.isEditTree() && SecurityUtils.isSecurityMBean(this.invocationContext().bean())) {
                              return null;
                           } else {
                              subResource = this.getSubResource(clazz);
                              if (subResource instanceof AbstractBeanActionResource) {
                                 ((AbstractBeanActionResource)subResource).init(this.invocationContext().bean(), t, query);
                              }

                              return subResource;
                           }
                        }
                     } else {
                        return null;
                     }
                  }
               }
            }
         }
      }
   }
}
