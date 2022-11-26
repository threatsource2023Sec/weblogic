package weblogic.management.descriptors.application.weblogic.jdbc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import weblogic.management.descriptors.XMLElementMBeanDelegate;
import weblogic.management.tools.ToXML;

public class ConnectionParamsMBeanImpl extends XMLElementMBeanDelegate implements ConnectionParamsMBean {
   static final long serialVersionUID = 1L;
   private boolean isSet_parameters = false;
   private List parameters;

   public ParameterMBean[] getParameters() {
      if (this.parameters == null) {
         return new ParameterMBean[0];
      } else {
         ParameterMBean[] result = new ParameterMBean[this.parameters.size()];
         result = (ParameterMBean[])((ParameterMBean[])this.parameters.toArray(result));
         return result;
      }
   }

   public void setParameters(ParameterMBean[] value) {
      ParameterMBean[] _oldVal = null;
      if (this.changeSupport != null) {
         _oldVal = this.getParameters();
      }

      this.isSet_parameters = true;
      if (this.parameters == null) {
         this.parameters = Collections.synchronizedList(new ArrayList());
      } else {
         this.parameters.clear();
      }

      if (null != value) {
         for(int i = 0; i < value.length; ++i) {
            this.parameters.add(value[i]);
         }
      }

      if (this.changeSupport != null) {
         this.checkChange("Parameters", _oldVal, this.getParameters());
      }

   }

   public void addParameter(ParameterMBean value) {
      this.isSet_parameters = true;
      if (this.parameters == null) {
         this.parameters = Collections.synchronizedList(new ArrayList());
      }

      this.parameters.add(value);
   }

   public void removeParameter(ParameterMBean value) {
      if (this.parameters != null) {
         this.parameters.remove(value);
      }
   }

   public String toXML(int indentLevel) {
      StringBuffer result = new StringBuffer();
      result.append(ToXML.indent(indentLevel)).append("<connection-params");
      result.append(">\n");
      result.append(ToXML.indent(indentLevel)).append("</connection-params>\n");
      return result.toString();
   }
}
