package weblogic.management.descriptors.webservice;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import weblogic.management.descriptors.XMLElementMBeanDelegate;
import weblogic.management.tools.ToXML;

public class InitParamsMBeanImpl extends XMLElementMBeanDelegate implements InitParamsMBean {
   static final long serialVersionUID = 1L;
   private boolean isSet_initParams = false;
   private List initParams;

   public InitParamMBean[] getInitParams() {
      if (this.initParams == null) {
         return new InitParamMBean[0];
      } else {
         InitParamMBean[] result = new InitParamMBean[this.initParams.size()];
         result = (InitParamMBean[])((InitParamMBean[])this.initParams.toArray(result));
         return result;
      }
   }

   public void setInitParams(InitParamMBean[] value) {
      InitParamMBean[] _oldVal = null;
      if (this.changeSupport != null) {
         _oldVal = this.getInitParams();
      }

      this.isSet_initParams = true;
      if (this.initParams == null) {
         this.initParams = Collections.synchronizedList(new ArrayList());
      } else {
         this.initParams.clear();
      }

      if (null != value) {
         for(int i = 0; i < value.length; ++i) {
            this.initParams.add(value[i]);
         }
      }

      if (this.changeSupport != null) {
         this.checkChange("InitParams", _oldVal, this.getInitParams());
      }

   }

   public void addInitParam(InitParamMBean value) {
      this.isSet_initParams = true;
      if (this.initParams == null) {
         this.initParams = Collections.synchronizedList(new ArrayList());
      }

      this.initParams.add(value);
   }

   public void removeInitParam(InitParamMBean value) {
      if (this.initParams != null) {
         this.initParams.remove(value);
      }
   }

   public String toXML(int indentLevel) {
      StringBuffer result = new StringBuffer();
      result.append(ToXML.indent(indentLevel)).append("<init-params");
      result.append(">\n");
      if (null != this.getInitParams()) {
         for(int i = 0; i < this.getInitParams().length; ++i) {
            result.append(this.getInitParams()[i].toXML(indentLevel + 2));
         }
      }

      result.append(ToXML.indent(indentLevel)).append("</init-params>\n");
      return result.toString();
   }
}
