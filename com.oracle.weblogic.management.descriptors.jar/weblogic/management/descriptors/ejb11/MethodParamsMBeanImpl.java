package weblogic.management.descriptors.ejb11;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import weblogic.management.descriptors.XMLElementMBeanDelegate;
import weblogic.management.tools.ToXML;

public class MethodParamsMBeanImpl extends XMLElementMBeanDelegate implements MethodParamsMBean {
   static final long serialVersionUID = 1L;
   private boolean isSet_methodParams = false;
   private List methodParams;

   public String[] getMethodParams() {
      if (this.methodParams == null) {
         return new String[0];
      } else {
         String[] result = new String[this.methodParams.size()];
         result = (String[])((String[])this.methodParams.toArray(result));
         return result;
      }
   }

   public void setMethodParams(String[] value) {
      String[] _oldVal = null;
      if (this.changeSupport != null) {
         _oldVal = this.getMethodParams();
      }

      this.isSet_methodParams = true;
      if (this.methodParams == null) {
         this.methodParams = Collections.synchronizedList(new ArrayList());
      } else {
         this.methodParams.clear();
      }

      if (null != value) {
         for(int i = 0; i < value.length; ++i) {
            this.methodParams.add(value[i]);
         }
      }

      if (this.changeSupport != null) {
         this.checkChange("MethodParams", _oldVal, this.getMethodParams());
      }

   }

   public void addMethodParam(String value) {
      this.isSet_methodParams = true;
      if (this.methodParams == null) {
         this.methodParams = Collections.synchronizedList(new ArrayList());
      }

      this.methodParams.add(value);
   }

   public String toXML(int indentLevel) {
      StringBuffer result = new StringBuffer();
      result.append(ToXML.indent(indentLevel)).append("<method-params");
      result.append(">\n");

      for(int i = 0; i < this.getMethodParams().length; ++i) {
         result.append(ToXML.indent(indentLevel + 2)).append("<method-param>").append(this.getMethodParams()[i]).append("</method-param>\n");
      }

      result.append(ToXML.indent(indentLevel)).append("</method-params>\n");
      return result.toString();
   }
}
