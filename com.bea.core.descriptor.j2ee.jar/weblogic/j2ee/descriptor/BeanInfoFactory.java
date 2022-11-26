package weblogic.j2ee.descriptor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import weblogic.utils.codegen.ImplementationFactory;
import weblogic.utils.codegen.RoleInfoImplementationFactory;

public class BeanInfoFactory implements RoleInfoImplementationFactory {
   private static final Map interfaceMap = new HashMap(180);
   private static final ArrayList roleInfoList;
   private static final BeanInfoFactory SINGLETON;

   public static final ImplementationFactory getInstance() {
      return SINGLETON;
   }

   public String getImplementationClassName(String interfaceName) {
      return (String)interfaceMap.get(interfaceName);
   }

   public String[] getInterfaces() {
      Set keySet = interfaceMap.keySet();
      return (String[])((String[])keySet.toArray(new String[keySet.size()]));
   }

   public String[] getInterfacesWithRoleInfo() {
      return (String[])((String[])roleInfoList.toArray(new String[roleInfoList.size()]));
   }

   public String getRoleInfoImplementationFactoryTimestamp() {
      try {
         InputStream is = this.getClass().getResourceAsStream("BeanInfoFactory.tstamp");
         return (new BufferedReader(new InputStreamReader(is))).readLine();
      } catch (IOException var2) {
         throw new RuntimeException(var2);
      }
   }

   static {
      interfaceMap.put("weblogic.j2ee.descriptor.AbsoluteOrderingBean", "weblogic.j2ee.descriptor.AbsoluteOrderingBeanImplBeanInfo");
      interfaceMap.put("weblogic.j2ee.descriptor.AccessTimeoutBean", "weblogic.j2ee.descriptor.AccessTimeoutBeanImplBeanInfo");
      interfaceMap.put("weblogic.j2ee.descriptor.ActivationConfigBean", "weblogic.j2ee.descriptor.ActivationConfigBeanImplBeanInfo");
      interfaceMap.put("weblogic.j2ee.descriptor.ActivationConfigPropertyBean", "weblogic.j2ee.descriptor.ActivationConfigPropertyBeanImplBeanInfo");
      interfaceMap.put("weblogic.j2ee.descriptor.ActivationSpecBean", "weblogic.j2ee.descriptor.ActivationSpecBeanImplBeanInfo");
      interfaceMap.put("weblogic.j2ee.descriptor.AddressingBean", "weblogic.j2ee.descriptor.AddressingBeanImplBeanInfo");
      interfaceMap.put("weblogic.j2ee.descriptor.AdminObjectBean", "weblogic.j2ee.descriptor.AdminObjectBeanImplBeanInfo");
      interfaceMap.put("weblogic.j2ee.descriptor.AdministeredObjectBean", "weblogic.j2ee.descriptor.AdministeredObjectBeanImplBeanInfo");
      interfaceMap.put("weblogic.j2ee.descriptor.ApplicationClientBean", "weblogic.j2ee.descriptor.ApplicationClientBeanImplBeanInfo");
      interfaceMap.put("weblogic.j2ee.descriptor.ApplicationExceptionBean", "weblogic.j2ee.descriptor.ApplicationExceptionBeanImplBeanInfo");
      interfaceMap.put("weblogic.j2ee.descriptor.AroundInvokeBean", "weblogic.j2ee.descriptor.AroundInvokeBeanImplBeanInfo");
      interfaceMap.put("weblogic.j2ee.descriptor.AroundTimeoutBean", "weblogic.j2ee.descriptor.AroundTimeoutBeanImplBeanInfo");
      interfaceMap.put("weblogic.j2ee.descriptor.AssemblyDescriptorBean", "weblogic.j2ee.descriptor.AssemblyDescriptorBeanImplBeanInfo");
      interfaceMap.put("weblogic.j2ee.descriptor.AsyncMethodBean", "weblogic.j2ee.descriptor.AsyncMethodBeanImplBeanInfo");
      interfaceMap.put("weblogic.j2ee.descriptor.AuthConstraintBean", "weblogic.j2ee.descriptor.AuthConstraintBeanImplBeanInfo");
      interfaceMap.put("weblogic.j2ee.descriptor.AuthenticationMechanismBean", "weblogic.j2ee.descriptor.AuthenticationMechanismBeanImplBeanInfo");
      interfaceMap.put("weblogic.j2ee.descriptor.CmpFieldBean", "weblogic.j2ee.descriptor.CmpFieldBeanImplBeanInfo");
      interfaceMap.put("weblogic.j2ee.descriptor.CmrFieldBean", "weblogic.j2ee.descriptor.CmrFieldBeanImplBeanInfo");
      interfaceMap.put("weblogic.j2ee.descriptor.ConcurrentMethodBean", "weblogic.j2ee.descriptor.ConcurrentMethodBeanImplBeanInfo");
      interfaceMap.put("weblogic.j2ee.descriptor.ConfigPropertyBean", "weblogic.j2ee.descriptor.ConfigPropertyBeanImplBeanInfo");
      interfaceMap.put("weblogic.j2ee.descriptor.ConnectionDefinitionBean", "weblogic.j2ee.descriptor.ConnectionDefinitionBeanImplBeanInfo");
      interfaceMap.put("weblogic.j2ee.descriptor.ConnectionFactoryResourceBean", "weblogic.j2ee.descriptor.ConnectionFactoryResourceBeanImplBeanInfo");
      interfaceMap.put("weblogic.j2ee.descriptor.ConnectorBean", "weblogic.j2ee.descriptor.ConnectorBeanImplBeanInfo");
      interfaceMap.put("weblogic.j2ee.descriptor.ConstructorParameterOrderBean", "weblogic.j2ee.descriptor.ConstructorParameterOrderBeanImplBeanInfo");
      interfaceMap.put("weblogic.j2ee.descriptor.ContainerTransactionBean", "weblogic.j2ee.descriptor.ContainerTransactionBeanImplBeanInfo");
      interfaceMap.put("weblogic.j2ee.descriptor.CookieConfigBean", "weblogic.j2ee.descriptor.CookieConfigBeanImplBeanInfo");
      interfaceMap.put("weblogic.j2ee.descriptor.DataSourceBean", "weblogic.j2ee.descriptor.DataSourceBeanImplBeanInfo");
      interfaceMap.put("weblogic.j2ee.descriptor.DependsOnBean", "weblogic.j2ee.descriptor.DependsOnBeanImplBeanInfo");
      interfaceMap.put("weblogic.j2ee.descriptor.EjbJarBean", "weblogic.j2ee.descriptor.EjbJarBeanImplBeanInfo");
      interfaceMap.put("weblogic.j2ee.descriptor.EjbLocalRefBean", "weblogic.j2ee.descriptor.EjbLocalRefBeanImplBeanInfo");
      interfaceMap.put("weblogic.j2ee.descriptor.EjbRefBean", "weblogic.j2ee.descriptor.EjbRefBeanImplBeanInfo");
      interfaceMap.put("weblogic.j2ee.descriptor.EjbRelationBean", "weblogic.j2ee.descriptor.EjbRelationBeanImplBeanInfo");
      interfaceMap.put("weblogic.j2ee.descriptor.EjbRelationshipRoleBean", "weblogic.j2ee.descriptor.EjbRelationshipRoleBeanImplBeanInfo");
      interfaceMap.put("weblogic.j2ee.descriptor.EmptyBean", "weblogic.j2ee.descriptor.EmptyBeanImplBeanInfo");
      interfaceMap.put("weblogic.j2ee.descriptor.EnterpriseBeansBean", "weblogic.j2ee.descriptor.EnterpriseBeansBeanImplBeanInfo");
      interfaceMap.put("weblogic.j2ee.descriptor.EntityBeanBean", "weblogic.j2ee.descriptor.EntityBeanBeanImplBeanInfo");
      interfaceMap.put("weblogic.j2ee.descriptor.EnvEntryBean", "weblogic.j2ee.descriptor.EnvEntryBeanImplBeanInfo");
      interfaceMap.put("weblogic.j2ee.descriptor.ErrorPageBean", "weblogic.j2ee.descriptor.ErrorPageBeanImplBeanInfo");
      interfaceMap.put("weblogic.j2ee.descriptor.ExceptionMappingBean", "weblogic.j2ee.descriptor.ExceptionMappingBeanImplBeanInfo");
      interfaceMap.put("weblogic.j2ee.descriptor.ExcludeListBean", "weblogic.j2ee.descriptor.ExcludeListBeanImplBeanInfo");
      interfaceMap.put("weblogic.j2ee.descriptor.FilterBean", "weblogic.j2ee.descriptor.FilterBeanImplBeanInfo");
      interfaceMap.put("weblogic.j2ee.descriptor.FilterMappingBean", "weblogic.j2ee.descriptor.FilterMappingBeanImplBeanInfo");
      interfaceMap.put("weblogic.j2ee.descriptor.FormLoginConfigBean", "weblogic.j2ee.descriptor.FormLoginConfigBeanImplBeanInfo");
      interfaceMap.put("weblogic.j2ee.descriptor.HandlerChainBean", "weblogic.j2ee.descriptor.HandlerChainBeanImplBeanInfo");
      interfaceMap.put("weblogic.j2ee.descriptor.HandlerChainsBean", "weblogic.j2ee.descriptor.HandlerChainsBeanImplBeanInfo");
      interfaceMap.put("weblogic.j2ee.descriptor.IconBean", "weblogic.j2ee.descriptor.IconBeanImplBeanInfo");
      interfaceMap.put("weblogic.j2ee.descriptor.InboundResourceAdapterBean", "weblogic.j2ee.descriptor.InboundResourceAdapterBeanImplBeanInfo");
      interfaceMap.put("weblogic.j2ee.descriptor.InitMethodBean", "weblogic.j2ee.descriptor.InitMethodBeanImplBeanInfo");
      interfaceMap.put("weblogic.j2ee.descriptor.InjectionTargetBean", "weblogic.j2ee.descriptor.InjectionTargetBeanImplBeanInfo");
      interfaceMap.put("weblogic.j2ee.descriptor.InterceptorBean", "weblogic.j2ee.descriptor.InterceptorBeanImplBeanInfo");
      interfaceMap.put("weblogic.j2ee.descriptor.InterceptorBindingBean", "weblogic.j2ee.descriptor.InterceptorBindingBeanImplBeanInfo");
      interfaceMap.put("weblogic.j2ee.descriptor.InterceptorMethodsBean", "weblogic.j2ee.descriptor.InterceptorMethodsBeanImplBeanInfo");
      interfaceMap.put("weblogic.j2ee.descriptor.InterceptorOrderBean", "weblogic.j2ee.descriptor.InterceptorOrderBeanImplBeanInfo");
      interfaceMap.put("weblogic.j2ee.descriptor.InterceptorsBean", "weblogic.j2ee.descriptor.InterceptorsBeanImplBeanInfo");
      interfaceMap.put("weblogic.j2ee.descriptor.JavaEEModuleNameBean", "weblogic.j2ee.descriptor.JavaEEModuleNameBeanImplBeanInfo");
      interfaceMap.put("weblogic.j2ee.descriptor.JavaEEPropertyBean", "weblogic.j2ee.descriptor.JavaEEPropertyBeanImplBeanInfo");
      interfaceMap.put("weblogic.j2ee.descriptor.JavaWsdlMappingBean", "weblogic.j2ee.descriptor.JavaWsdlMappingBeanImplBeanInfo");
      interfaceMap.put("weblogic.j2ee.descriptor.JavaXmlTypeMappingBean", "weblogic.j2ee.descriptor.JavaXmlTypeMappingBeanImplBeanInfo");
      interfaceMap.put("weblogic.j2ee.descriptor.JmsConnectionFactoryBean", "weblogic.j2ee.descriptor.JmsConnectionFactoryBeanImplBeanInfo");
      interfaceMap.put("weblogic.j2ee.descriptor.JmsDestinationBean", "weblogic.j2ee.descriptor.JmsDestinationBeanImplBeanInfo");
      interfaceMap.put("weblogic.j2ee.descriptor.JspConfigBean", "weblogic.j2ee.descriptor.JspConfigBeanImplBeanInfo");
      interfaceMap.put("weblogic.j2ee.descriptor.JspPropertyGroupBean", "weblogic.j2ee.descriptor.JspPropertyGroupBeanImplBeanInfo");
      interfaceMap.put("weblogic.j2ee.descriptor.LicenseBean", "weblogic.j2ee.descriptor.LicenseBeanImplBeanInfo");
      interfaceMap.put("weblogic.j2ee.descriptor.LifecycleCallbackBean", "weblogic.j2ee.descriptor.LifecycleCallbackBeanImplBeanInfo");
      interfaceMap.put("weblogic.j2ee.descriptor.ListenerBean", "weblogic.j2ee.descriptor.ListenerBeanImplBeanInfo");
      interfaceMap.put("weblogic.j2ee.descriptor.LocaleEncodingMappingBean", "weblogic.j2ee.descriptor.LocaleEncodingMappingBeanImplBeanInfo");
      interfaceMap.put("weblogic.j2ee.descriptor.LocaleEncodingMappingListBean", "weblogic.j2ee.descriptor.LocaleEncodingMappingListBeanImplBeanInfo");
      interfaceMap.put("weblogic.j2ee.descriptor.LoginConfigBean", "weblogic.j2ee.descriptor.LoginConfigBeanImplBeanInfo");
      interfaceMap.put("weblogic.j2ee.descriptor.MailSessionBean", "weblogic.j2ee.descriptor.MailSessionBeanImplBeanInfo");
      interfaceMap.put("weblogic.j2ee.descriptor.MessageAdapterBean", "weblogic.j2ee.descriptor.MessageAdapterBeanImplBeanInfo");
      interfaceMap.put("weblogic.j2ee.descriptor.MessageDestinationBean", "weblogic.j2ee.descriptor.MessageDestinationBeanImplBeanInfo");
      interfaceMap.put("weblogic.j2ee.descriptor.MessageDestinationRefBean", "weblogic.j2ee.descriptor.MessageDestinationRefBeanImplBeanInfo");
      interfaceMap.put("weblogic.j2ee.descriptor.MessageDrivenBeanBean", "weblogic.j2ee.descriptor.MessageDrivenBeanBeanImplBeanInfo");
      interfaceMap.put("weblogic.j2ee.descriptor.MessageListenerBean", "weblogic.j2ee.descriptor.MessageListenerBeanImplBeanInfo");
      interfaceMap.put("weblogic.j2ee.descriptor.MethodBean", "weblogic.j2ee.descriptor.MethodBeanImplBeanInfo");
      interfaceMap.put("weblogic.j2ee.descriptor.MethodParamPartsMappingBean", "weblogic.j2ee.descriptor.MethodParamPartsMappingBeanImplBeanInfo");
      interfaceMap.put("weblogic.j2ee.descriptor.MethodParamsBean", "weblogic.j2ee.descriptor.MethodParamsBeanImplBeanInfo");
      interfaceMap.put("weblogic.j2ee.descriptor.MethodPermissionBean", "weblogic.j2ee.descriptor.MethodPermissionBeanImplBeanInfo");
      interfaceMap.put("weblogic.j2ee.descriptor.MimeMappingBean", "weblogic.j2ee.descriptor.MimeMappingBeanImplBeanInfo");
      interfaceMap.put("weblogic.j2ee.descriptor.ModuleNameBean", "weblogic.j2ee.descriptor.ModuleNameBeanImplBeanInfo");
      interfaceMap.put("weblogic.j2ee.descriptor.MultipartConfigBean", "weblogic.j2ee.descriptor.MultipartConfigBeanImplBeanInfo");
      interfaceMap.put("weblogic.j2ee.descriptor.NameOrOrderingOthersBean", "weblogic.j2ee.descriptor.NameOrOrderingOthersBeanImplBeanInfo");
      interfaceMap.put("weblogic.j2ee.descriptor.NamedMethodBean", "weblogic.j2ee.descriptor.NamedMethodBeanImplBeanInfo");
      interfaceMap.put("weblogic.j2ee.descriptor.OrderingBean", "weblogic.j2ee.descriptor.OrderingBeanImplBeanInfo");
      interfaceMap.put("weblogic.j2ee.descriptor.OrderingOrderingBean", "weblogic.j2ee.descriptor.OrderingOrderingBeanImplBeanInfo");
      interfaceMap.put("weblogic.j2ee.descriptor.OrderingOthersBean", "weblogic.j2ee.descriptor.OrderingOthersBeanImplBeanInfo");
      interfaceMap.put("weblogic.j2ee.descriptor.OutboundResourceAdapterBean", "weblogic.j2ee.descriptor.OutboundResourceAdapterBeanImplBeanInfo");
      interfaceMap.put("weblogic.j2ee.descriptor.PackageMappingBean", "weblogic.j2ee.descriptor.PackageMappingBeanImplBeanInfo");
      interfaceMap.put("weblogic.j2ee.descriptor.ParamValueBean", "weblogic.j2ee.descriptor.ParamValueBeanImplBeanInfo");
      interfaceMap.put("weblogic.j2ee.descriptor.PermissionBean", "weblogic.j2ee.descriptor.PermissionBeanImplBeanInfo");
      interfaceMap.put("weblogic.j2ee.descriptor.PermissionsBean", "weblogic.j2ee.descriptor.PermissionsBeanImplBeanInfo");
      interfaceMap.put("weblogic.j2ee.descriptor.PersistenceBean", "weblogic.j2ee.descriptor.PersistenceBeanImplBeanInfo");
      interfaceMap.put("weblogic.j2ee.descriptor.PersistenceContextRefBean", "weblogic.j2ee.descriptor.PersistenceContextRefBeanImplBeanInfo");
      interfaceMap.put("weblogic.j2ee.descriptor.PersistencePropertiesBean", "weblogic.j2ee.descriptor.PersistencePropertiesBeanImplBeanInfo");
      interfaceMap.put("weblogic.j2ee.descriptor.PersistencePropertyBean", "weblogic.j2ee.descriptor.PersistencePropertyBeanImplBeanInfo");
      interfaceMap.put("weblogic.j2ee.descriptor.PersistenceUnitBean", "weblogic.j2ee.descriptor.PersistenceUnitBeanImplBeanInfo");
      interfaceMap.put("weblogic.j2ee.descriptor.PersistenceUnitRefBean", "weblogic.j2ee.descriptor.PersistenceUnitRefBeanImplBeanInfo");
      interfaceMap.put("weblogic.j2ee.descriptor.PortComponentBean", "weblogic.j2ee.descriptor.PortComponentBeanImplBeanInfo");
      interfaceMap.put("weblogic.j2ee.descriptor.PortComponentHandlerBean", "weblogic.j2ee.descriptor.PortComponentHandlerBeanImplBeanInfo");
      interfaceMap.put("weblogic.j2ee.descriptor.PortComponentRefBean", "weblogic.j2ee.descriptor.PortComponentRefBeanImplBeanInfo");
      interfaceMap.put("weblogic.j2ee.descriptor.PortMappingBean", "weblogic.j2ee.descriptor.PortMappingBeanImplBeanInfo");
      interfaceMap.put("weblogic.j2ee.descriptor.QueryBean", "weblogic.j2ee.descriptor.QueryBeanImplBeanInfo");
      interfaceMap.put("weblogic.j2ee.descriptor.QueryMethodBean", "weblogic.j2ee.descriptor.QueryMethodBeanImplBeanInfo");
      interfaceMap.put("weblogic.j2ee.descriptor.RelationshipRoleSourceBean", "weblogic.j2ee.descriptor.RelationshipRoleSourceBeanImplBeanInfo");
      interfaceMap.put("weblogic.j2ee.descriptor.RelationshipsBean", "weblogic.j2ee.descriptor.RelationshipsBeanImplBeanInfo");
      interfaceMap.put("weblogic.j2ee.descriptor.RemoveMethodBean", "weblogic.j2ee.descriptor.RemoveMethodBeanImplBeanInfo");
      interfaceMap.put("weblogic.j2ee.descriptor.RequiredConfigPropertyBean", "weblogic.j2ee.descriptor.RequiredConfigPropertyBeanImplBeanInfo");
      interfaceMap.put("weblogic.j2ee.descriptor.ResourceAdapterBean", "weblogic.j2ee.descriptor.ResourceAdapterBeanImplBeanInfo");
      interfaceMap.put("weblogic.j2ee.descriptor.ResourceEnvRefBean", "weblogic.j2ee.descriptor.ResourceEnvRefBeanImplBeanInfo");
      interfaceMap.put("weblogic.j2ee.descriptor.ResourceRefBean", "weblogic.j2ee.descriptor.ResourceRefBeanImplBeanInfo");
      interfaceMap.put("weblogic.j2ee.descriptor.RespectBindingBean", "weblogic.j2ee.descriptor.RespectBindingBeanImplBeanInfo");
      interfaceMap.put("weblogic.j2ee.descriptor.RunAsBean", "weblogic.j2ee.descriptor.RunAsBeanImplBeanInfo");
      interfaceMap.put("weblogic.j2ee.descriptor.SecurityConstraintBean", "weblogic.j2ee.descriptor.SecurityConstraintBeanImplBeanInfo");
      interfaceMap.put("weblogic.j2ee.descriptor.SecurityIdentityBean", "weblogic.j2ee.descriptor.SecurityIdentityBeanImplBeanInfo");
      interfaceMap.put("weblogic.j2ee.descriptor.SecurityPermissionBean", "weblogic.j2ee.descriptor.SecurityPermissionBeanImplBeanInfo");
      interfaceMap.put("weblogic.j2ee.descriptor.SecurityRoleBean", "weblogic.j2ee.descriptor.SecurityRoleBeanImplBeanInfo");
      interfaceMap.put("weblogic.j2ee.descriptor.SecurityRoleRefBean", "weblogic.j2ee.descriptor.SecurityRoleRefBeanImplBeanInfo");
      interfaceMap.put("weblogic.j2ee.descriptor.ServiceEndpointInterfaceMappingBean", "weblogic.j2ee.descriptor.ServiceEndpointInterfaceMappingBeanImplBeanInfo");
      interfaceMap.put("weblogic.j2ee.descriptor.ServiceEndpointMethodMappingBean", "weblogic.j2ee.descriptor.ServiceEndpointMethodMappingBeanImplBeanInfo");
      interfaceMap.put("weblogic.j2ee.descriptor.ServiceImplBeanBean", "weblogic.j2ee.descriptor.ServiceImplBeanBeanImplBeanInfo");
      interfaceMap.put("weblogic.j2ee.descriptor.ServiceInterfaceMappingBean", "weblogic.j2ee.descriptor.ServiceInterfaceMappingBeanImplBeanInfo");
      interfaceMap.put("weblogic.j2ee.descriptor.ServiceRefBean", "weblogic.j2ee.descriptor.ServiceRefBeanImplBeanInfo");
      interfaceMap.put("weblogic.j2ee.descriptor.ServiceRefHandlerBean", "weblogic.j2ee.descriptor.ServiceRefHandlerBeanImplBeanInfo");
      interfaceMap.put("weblogic.j2ee.descriptor.ServiceRefHandlerChainBean", "weblogic.j2ee.descriptor.ServiceRefHandlerChainBeanImplBeanInfo");
      interfaceMap.put("weblogic.j2ee.descriptor.ServiceRefHandlerChainsBean", "weblogic.j2ee.descriptor.ServiceRefHandlerChainsBeanImplBeanInfo");
      interfaceMap.put("weblogic.j2ee.descriptor.ServletBean", "weblogic.j2ee.descriptor.ServletBeanImplBeanInfo");
      interfaceMap.put("weblogic.j2ee.descriptor.ServletMappingBean", "weblogic.j2ee.descriptor.ServletMappingBeanImplBeanInfo");
      interfaceMap.put("weblogic.j2ee.descriptor.SessionBeanBean", "weblogic.j2ee.descriptor.SessionBeanBeanImplBeanInfo");
      interfaceMap.put("weblogic.j2ee.descriptor.SessionConfigBean", "weblogic.j2ee.descriptor.SessionConfigBeanImplBeanInfo");
      interfaceMap.put("weblogic.j2ee.descriptor.SourceTrackerBean", "weblogic.j2ee.descriptor.SourceTrackerBeanImplBeanInfo");
      interfaceMap.put("weblogic.j2ee.descriptor.StatefulTimeoutBean", "weblogic.j2ee.descriptor.StatefulTimeoutBeanImplBeanInfo");
      interfaceMap.put("weblogic.j2ee.descriptor.TagLibBean", "weblogic.j2ee.descriptor.TagLibBeanImplBeanInfo");
      interfaceMap.put("weblogic.j2ee.descriptor.TimerBean", "weblogic.j2ee.descriptor.TimerBeanImplBeanInfo");
      interfaceMap.put("weblogic.j2ee.descriptor.TimerScheduleBean", "weblogic.j2ee.descriptor.TimerScheduleBeanImplBeanInfo");
      interfaceMap.put("weblogic.j2ee.descriptor.UserDataConstraintBean", "weblogic.j2ee.descriptor.UserDataConstraintBeanImplBeanInfo");
      interfaceMap.put("weblogic.j2ee.descriptor.VariableMappingBean", "weblogic.j2ee.descriptor.VariableMappingBeanImplBeanInfo");
      interfaceMap.put("weblogic.j2ee.descriptor.WebAppBaseBean", "weblogic.j2ee.descriptor.WebAppBaseBeanImplBeanInfo");
      interfaceMap.put("weblogic.j2ee.descriptor.WebAppBean", "weblogic.j2ee.descriptor.WebAppBeanImplBeanInfo");
      interfaceMap.put("weblogic.j2ee.descriptor.WebFragmentBean", "weblogic.j2ee.descriptor.WebFragmentBeanImplBeanInfo");
      interfaceMap.put("weblogic.j2ee.descriptor.WebResourceCollectionBean", "weblogic.j2ee.descriptor.WebResourceCollectionBeanImplBeanInfo");
      interfaceMap.put("weblogic.j2ee.descriptor.WebserviceDescriptionBean", "weblogic.j2ee.descriptor.WebserviceDescriptionBeanImplBeanInfo");
      interfaceMap.put("weblogic.j2ee.descriptor.WebservicesBean", "weblogic.j2ee.descriptor.WebservicesBeanImplBeanInfo");
      interfaceMap.put("weblogic.j2ee.descriptor.WelcomeFileListBean", "weblogic.j2ee.descriptor.WelcomeFileListBeanImplBeanInfo");
      interfaceMap.put("weblogic.j2ee.descriptor.WsdlMessageMappingBean", "weblogic.j2ee.descriptor.WsdlMessageMappingBeanImplBeanInfo");
      interfaceMap.put("weblogic.j2ee.descriptor.WsdlReturnValueMappingBean", "weblogic.j2ee.descriptor.WsdlReturnValueMappingBeanImplBeanInfo");
      interfaceMap.put("weblogic.j2ee.descriptor.wl.ApplicationAdminModeTriggerBean", "weblogic.j2ee.descriptor.wl.ApplicationAdminModeTriggerBeanImplBeanInfo");
      interfaceMap.put("weblogic.j2ee.descriptor.wl.CapacityBean", "weblogic.j2ee.descriptor.wl.CapacityBeanImplBeanInfo");
      interfaceMap.put("weblogic.j2ee.descriptor.wl.ConnectionPoolParamsBean", "weblogic.j2ee.descriptor.wl.ConnectionPoolParamsBeanImplBeanInfo");
      interfaceMap.put("weblogic.j2ee.descriptor.wl.ContextCaseBean", "weblogic.j2ee.descriptor.wl.ContextCaseBeanImplBeanInfo");
      interfaceMap.put("weblogic.j2ee.descriptor.wl.ContextRequestClassBean", "weblogic.j2ee.descriptor.wl.ContextRequestClassBeanImplBeanInfo");
      interfaceMap.put("weblogic.j2ee.descriptor.wl.DefaultResourcePrincipalBean", "weblogic.j2ee.descriptor.wl.DefaultResourcePrincipalBeanImplBeanInfo");
      interfaceMap.put("weblogic.j2ee.descriptor.wl.EjbReferenceDescriptionBean", "weblogic.j2ee.descriptor.wl.EjbReferenceDescriptionBeanImplBeanInfo");
      interfaceMap.put("weblogic.j2ee.descriptor.wl.FairShareRequestClassBean", "weblogic.j2ee.descriptor.wl.FairShareRequestClassBeanImplBeanInfo");
      interfaceMap.put("weblogic.j2ee.descriptor.wl.LoggingBean", "weblogic.j2ee.descriptor.wl.LoggingBeanImplBeanInfo");
      interfaceMap.put("weblogic.j2ee.descriptor.wl.ManagedBeanBean", "weblogic.j2ee.descriptor.wl.ManagedBeanBeanImplBeanInfo");
      interfaceMap.put("weblogic.j2ee.descriptor.wl.ManagedBeansBean", "weblogic.j2ee.descriptor.wl.ManagedBeansBeanImplBeanInfo");
      interfaceMap.put("weblogic.j2ee.descriptor.wl.ManagedExecutorServiceBean", "weblogic.j2ee.descriptor.wl.ManagedExecutorServiceBeanImplBeanInfo");
      interfaceMap.put("weblogic.j2ee.descriptor.wl.ManagedScheduledExecutorServiceBean", "weblogic.j2ee.descriptor.wl.ManagedScheduledExecutorServiceBeanImplBeanInfo");
      interfaceMap.put("weblogic.j2ee.descriptor.wl.ManagedThreadFactoryBean", "weblogic.j2ee.descriptor.wl.ManagedThreadFactoryBeanImplBeanInfo");
      interfaceMap.put("weblogic.j2ee.descriptor.wl.MaxThreadsConstraintBean", "weblogic.j2ee.descriptor.wl.MaxThreadsConstraintBeanImplBeanInfo");
      interfaceMap.put("weblogic.j2ee.descriptor.wl.MessageDestinationDescriptorBean", "weblogic.j2ee.descriptor.wl.MessageDestinationDescriptorBeanImplBeanInfo");
      interfaceMap.put("weblogic.j2ee.descriptor.wl.MethodParamsBean", "weblogic.j2ee.descriptor.wl.MethodParamsBeanImplBeanInfo");
      interfaceMap.put("weblogic.j2ee.descriptor.wl.MinThreadsConstraintBean", "weblogic.j2ee.descriptor.wl.MinThreadsConstraintBeanImplBeanInfo");
      interfaceMap.put("weblogic.j2ee.descriptor.wl.OperationInfoBean", "weblogic.j2ee.descriptor.wl.OperationInfoBeanImplBeanInfo");
      interfaceMap.put("weblogic.j2ee.descriptor.wl.OwsmPolicyBean", "weblogic.j2ee.descriptor.wl.OwsmPolicyBeanImplBeanInfo");
      interfaceMap.put("weblogic.j2ee.descriptor.wl.PojoEnvironmentBean", "weblogic.j2ee.descriptor.wl.PojoEnvironmentBeanImplBeanInfo");
      interfaceMap.put("weblogic.j2ee.descriptor.wl.PortInfoBean", "weblogic.j2ee.descriptor.wl.PortInfoBeanImplBeanInfo");
      interfaceMap.put("weblogic.j2ee.descriptor.wl.PropertyNamevalueBean", "weblogic.j2ee.descriptor.wl.PropertyNamevalueBeanImplBeanInfo");
      interfaceMap.put("weblogic.j2ee.descriptor.wl.ResourceDescriptionBean", "weblogic.j2ee.descriptor.wl.ResourceDescriptionBeanImplBeanInfo");
      interfaceMap.put("weblogic.j2ee.descriptor.wl.ResourceEnvDescriptionBean", "weblogic.j2ee.descriptor.wl.ResourceEnvDescriptionBeanImplBeanInfo");
      interfaceMap.put("weblogic.j2ee.descriptor.wl.ResponseTimeRequestClassBean", "weblogic.j2ee.descriptor.wl.ResponseTimeRequestClassBeanImplBeanInfo");
      interfaceMap.put("weblogic.j2ee.descriptor.wl.RestWebserviceDescriptionBean", "weblogic.j2ee.descriptor.wl.RestWebserviceDescriptionBeanImplBeanInfo");
      interfaceMap.put("weblogic.j2ee.descriptor.wl.RestWebservicesBean", "weblogic.j2ee.descriptor.wl.RestWebservicesBeanImplBeanInfo");
      interfaceMap.put("weblogic.j2ee.descriptor.wl.RunAsRoleAssignmentBean", "weblogic.j2ee.descriptor.wl.RunAsRoleAssignmentBeanImplBeanInfo");
      interfaceMap.put("weblogic.j2ee.descriptor.wl.SecurityPermissionBean", "weblogic.j2ee.descriptor.wl.SecurityPermissionBeanImplBeanInfo");
      interfaceMap.put("weblogic.j2ee.descriptor.wl.SecurityRoleAssignmentBean", "weblogic.j2ee.descriptor.wl.SecurityRoleAssignmentBeanImplBeanInfo");
      interfaceMap.put("weblogic.j2ee.descriptor.wl.ServiceReferenceDescriptionBean", "weblogic.j2ee.descriptor.wl.ServiceReferenceDescriptionBeanImplBeanInfo");
      interfaceMap.put("weblogic.j2ee.descriptor.wl.WSATConfigBean", "weblogic.j2ee.descriptor.wl.WSATConfigBeanImplBeanInfo");
      interfaceMap.put("weblogic.j2ee.descriptor.wl.WorkManagerBean", "weblogic.j2ee.descriptor.wl.WorkManagerBeanImplBeanInfo");
      interfaceMap.put("weblogic.j2ee.descriptor.wl.WorkManagerShutdownTriggerBean", "weblogic.j2ee.descriptor.wl.WorkManagerShutdownTriggerBeanImplBeanInfo");
      roleInfoList = new ArrayList(0);
      SINGLETON = new BeanInfoFactory();
   }
}
