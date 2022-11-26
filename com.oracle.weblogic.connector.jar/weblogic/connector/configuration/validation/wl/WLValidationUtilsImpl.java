package weblogic.connector.configuration.validation.wl;

import java.util.ArrayList;
import java.util.List;
import weblogic.j2ee.descriptor.AdminObjectBean;
import weblogic.j2ee.descriptor.ConnectionDefinitionBean;
import weblogic.j2ee.descriptor.ConnectorBean;
import weblogic.j2ee.descriptor.OutboundResourceAdapterBean;

class WLValidationUtilsImpl implements WLValidationUtils {
   public ConnectionDefinitionBean findMatchingConnectionDefinitionInRA(ConnectorBean raConnectorBean, String wlConnFactoryInterface) {
      if (raConnectorBean != null && raConnectorBean.getResourceAdapter() != null) {
         OutboundResourceAdapterBean outboundResourceAdapter = raConnectorBean.getResourceAdapter().getOutboundResourceAdapter();
         if (outboundResourceAdapter != null) {
            ConnectionDefinitionBean[] connDefs = outboundResourceAdapter.getConnectionDefinitions();
            if (connDefs != null) {
               ConnectionDefinitionBean[] var5 = connDefs;
               int var6 = connDefs.length;

               for(int var7 = 0; var7 < var6; ++var7) {
                  ConnectionDefinitionBean connDef = var5[var7];
                  if (wlConnFactoryInterface.equals(connDef.getConnectionFactoryInterface())) {
                     return connDef;
                  }
               }
            }
         }
      }

      return null;
   }

   private List findAdminObjectInRA(ConnectorBean raConnectorBean, String adminInterface, String adminClasse) {
      List adminClasses = new ArrayList();
      boolean checkClass = null != adminClasse && adminClasse.length() > 0;
      if (raConnectorBean != null && raConnectorBean.getResourceAdapter() != null) {
         AdminObjectBean[] raAdminBeans = raConnectorBean.getResourceAdapter().getAdminObjects();

         for(int i = 0; i < raAdminBeans.length; ++i) {
            AdminObjectBean raAdminBean = raAdminBeans[i];
            String raAdminInterface = raAdminBean.getAdminObjectInterface();
            if (adminInterface.equals(raAdminInterface) && (!checkClass || checkClass && adminClasse.equals(raAdminBean.getAdminObjectClass()))) {
               adminClasses.add(raAdminBean.getAdminObjectClass());
            }
         }
      }

      return adminClasses;
   }

   public List findMatchingAdminInterfaceInRA(ConnectorBean raConnectorBean, String adminInterface) {
      return this.findAdminObjectInRA(raConnectorBean, adminInterface, (String)null);
   }

   public boolean hasMatchingAdminInterfaceInRA(ConnectorBean raConnectorBean, String adminInterface, String adminClass) {
      return this.findAdminObjectInRA(raConnectorBean, adminInterface, adminClass).size() > 0;
   }
}
