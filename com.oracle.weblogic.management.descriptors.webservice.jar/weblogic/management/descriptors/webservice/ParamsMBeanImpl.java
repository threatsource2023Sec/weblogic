package weblogic.management.descriptors.webservice;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import weblogic.management.descriptors.XMLElementMBeanDelegate;
import weblogic.management.tools.ToXML;

public class ParamsMBeanImpl extends XMLElementMBeanDelegate implements ParamsMBean {
   static final long serialVersionUID = 1L;
   private boolean isSet_returnParam = false;
   private ReturnParamMBean returnParam;
   private boolean isSet_params = false;
   private List params;
   private boolean isSet_faults = false;
   private List faults;

   public ReturnParamMBean getReturnParam() {
      return this.returnParam;
   }

   public void setReturnParam(ReturnParamMBean value) {
      ReturnParamMBean old = this.returnParam;
      this.returnParam = value;
      this.isSet_returnParam = value != null;
      this.checkChange("returnParam", old, this.returnParam);
   }

   public ParamMBean[] getParams() {
      if (this.params == null) {
         return new ParamMBean[0];
      } else {
         ParamMBean[] result = new ParamMBean[this.params.size()];
         result = (ParamMBean[])((ParamMBean[])this.params.toArray(result));
         return result;
      }
   }

   public void setParams(ParamMBean[] value) {
      ParamMBean[] _oldVal = null;
      if (this.changeSupport != null) {
         _oldVal = this.getParams();
      }

      this.isSet_params = true;
      if (this.params == null) {
         this.params = Collections.synchronizedList(new ArrayList());
      } else {
         this.params.clear();
      }

      if (null != value) {
         for(int i = 0; i < value.length; ++i) {
            this.params.add(value[i]);
         }
      }

      if (this.changeSupport != null) {
         this.checkChange("Params", _oldVal, this.getParams());
      }

   }

   public void addParam(ParamMBean value) {
      this.isSet_params = true;
      if (this.params == null) {
         this.params = Collections.synchronizedList(new ArrayList());
      }

      this.params.add(value);
   }

   public void removeParam(ParamMBean value) {
      if (this.params != null) {
         this.params.remove(value);
      }
   }

   public FaultMBean[] getFaults() {
      if (this.faults == null) {
         return new FaultMBean[0];
      } else {
         FaultMBean[] result = new FaultMBean[this.faults.size()];
         result = (FaultMBean[])((FaultMBean[])this.faults.toArray(result));
         return result;
      }
   }

   public void setFaults(FaultMBean[] value) {
      FaultMBean[] _oldVal = null;
      if (this.changeSupport != null) {
         _oldVal = this.getFaults();
      }

      this.isSet_faults = true;
      if (this.faults == null) {
         this.faults = Collections.synchronizedList(new ArrayList());
      } else {
         this.faults.clear();
      }

      if (null != value) {
         for(int i = 0; i < value.length; ++i) {
            this.faults.add(value[i]);
         }
      }

      if (this.changeSupport != null) {
         this.checkChange("Faults", _oldVal, this.getFaults());
      }

   }

   public void addFault(FaultMBean value) {
      this.isSet_faults = true;
      if (this.faults == null) {
         this.faults = Collections.synchronizedList(new ArrayList());
      }

      this.faults.add(value);
   }

   public void removeFault(FaultMBean value) {
      if (this.faults != null) {
         this.faults.remove(value);
      }
   }

   public String toXML(int indentLevel) {
      StringBuffer result = new StringBuffer();
      result.append(ToXML.indent(indentLevel)).append("<params");
      result.append(">\n");
      int i;
      if (null != this.getParams()) {
         for(i = 0; i < this.getParams().length; ++i) {
            result.append(this.getParams()[i].toXML(indentLevel + 2));
         }
      }

      if (null != this.getReturnParam()) {
         result.append(this.getReturnParam().toXML(indentLevel + 2)).append("\n");
      }

      if (null != this.getFaults()) {
         for(i = 0; i < this.getFaults().length; ++i) {
            result.append(this.getFaults()[i].toXML(indentLevel + 2));
         }
      }

      result.append(ToXML.indent(indentLevel)).append("</params>\n");
      return result.toString();
   }
}
