package weblogic.jaxrs.monitoring.resource.data;

import java.util.Date;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import weblogic.management.runtime.JaxRsMonitoringInfoRuntimeMBean;

@XmlRootElement
public class JaxRsMonitoringInfo {
   private JaxRsMonitoringInfoRuntimeMBean monitorBean = null;

   public JaxRsMonitoringInfo(JaxRsMonitoringInfoRuntimeMBean monitoringBean) {
      this.monitorBean = monitoringBean;
   }

   public JaxRsMonitoringInfo() {
   }

   @XmlElement
   public Date getStartTime() {
      return new Date(this.monitorBean.getStartTime());
   }

   @XmlElement
   public long getTotalInvocations() {
      return this.monitorBean.getInvocationCount();
   }

   @XmlElement
   public Date getLastInvokedTime() {
      return this.monitorBean.getLastInvocationTime() > 0L ? new Date(this.monitorBean.getLastInvocationTime()) : null;
   }

   @XmlElement
   public long getTotalExecutionTimeInMillis() {
      return this.monitorBean.getExecutionTimeTotal();
   }

   @XmlElement
   public float getAverageExecutionTimeInMillis() {
      return (float)this.monitorBean.getExecutionTimeAverage();
   }

   @XmlElement
   public long getLowExecutionTimeInMillis() {
      return this.monitorBean.getExecutionTimeLow();
   }

   @XmlElement
   public long getHighExecutionTimeInMillis() {
      return this.monitorBean.getExecutionTimeHigh();
   }
}
