package kodo.profile;

import com.solarmetric.profile.ProfilingAgentMBean;
import java.util.HashMap;
import javax.management.MBeanOperationInfo;
import javax.management.MBeanParameterInfo;
import org.apache.openjpa.conf.OpenJPAConfiguration;
import org.apache.openjpa.lib.util.Localizer;
import org.apache.openjpa.meta.ClassMetaData;

public class KodoProfilingAgentMBean extends ProfilingAgentMBean implements KodoProfilingAgent {
   private static final Localizer s_loc = Localizer.forPackage(KodoProfilingAgentMBean.class);
   private HashMap _pmetas = new HashMap();

   public KodoProfilingAgentMBean() {
   }

   public KodoProfilingAgentMBean(OpenJPAConfiguration conf) {
      super(conf);
   }

   protected String getMBeanDescription() {
      return s_loc.get("kodo-prof-agent-desc").getMessage();
   }

   protected MBeanOperationInfo[] createMBeanOperationInfo() {
      return new MBeanOperationInfo[]{new MBeanOperationInfo("getMetaData", "Gets meta data for a given class name.", new MBeanParameterInfo[]{new MBeanParameterInfo("className", String.class.getName(), "Class name")}, ProfilingClassMetaData.class.getName(), 0)};
   }

   public String getCustomMBeanViewerName() {
      return "kodo.profile.gui.KodoProfilingMBeanViewer";
   }

   public ProfilingClassMetaData getMetaData(String className) {
      ProfilingClassMetaData pmeta = (ProfilingClassMetaData)this._pmetas.get(className);
      return pmeta;
   }

   public ProfilingClassMetaData registerMetaData(ClassMetaData meta) {
      ProfilingClassMetaData pmeta = (ProfilingClassMetaData)this._pmetas.get(meta.getDescribedType().getName());
      if (pmeta == null) {
         pmeta = new ProfilingClassMetaData(meta);
         this._pmetas.put(meta.getDescribedType().getName(), pmeta);
      }

      return pmeta;
   }
}
