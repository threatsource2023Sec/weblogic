package weblogic.management.descriptors.weblogic;

import weblogic.management.descriptors.XMLElementMBeanDelegate;
import weblogic.management.tools.ToXML;

public class StatelessSessionDescriptorMBeanImpl extends XMLElementMBeanDelegate implements StatelessSessionDescriptorMBean {
   static final long serialVersionUID = 1L;
   private boolean isSet_pool = false;
   private PoolMBean pool;
   private boolean isSet_statelessClustering = false;
   private StatelessClusteringMBean statelessClustering;

   public PoolMBean getPool() {
      return this.pool;
   }

   public void setPool(PoolMBean value) {
      PoolMBean old = this.pool;
      this.pool = value;
      this.isSet_pool = value != null;
      this.checkChange("pool", old, this.pool);
   }

   public StatelessClusteringMBean getStatelessClustering() {
      return this.statelessClustering;
   }

   public void setStatelessClustering(StatelessClusteringMBean value) {
      StatelessClusteringMBean old = this.statelessClustering;
      this.statelessClustering = value;
      this.isSet_statelessClustering = value != null;
      this.checkChange("statelessClustering", old, this.statelessClustering);
   }

   public String toXML(int indentLevel) {
      StringBuffer result = new StringBuffer();
      result.append(ToXML.indent(indentLevel)).append("<stateless-session-descriptor");
      result.append(">\n");
      if (null != this.getPool()) {
         result.append(this.getPool().toXML(indentLevel + 2)).append("\n");
      }

      if (null != this.getStatelessClustering()) {
         result.append(this.getStatelessClustering().toXML(indentLevel + 2)).append("\n");
      }

      result.append(ToXML.indent(indentLevel)).append("</stateless-session-descriptor>\n");
      return result.toString();
   }
}
