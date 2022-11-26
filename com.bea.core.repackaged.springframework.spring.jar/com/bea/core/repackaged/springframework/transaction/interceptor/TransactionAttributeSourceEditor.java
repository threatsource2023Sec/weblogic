package com.bea.core.repackaged.springframework.transaction.interceptor;

import com.bea.core.repackaged.springframework.beans.propertyeditors.PropertiesEditor;
import com.bea.core.repackaged.springframework.util.StringUtils;
import java.beans.PropertyEditorSupport;
import java.util.Enumeration;
import java.util.Properties;

public class TransactionAttributeSourceEditor extends PropertyEditorSupport {
   public void setAsText(String text) throws IllegalArgumentException {
      MethodMapTransactionAttributeSource source = new MethodMapTransactionAttributeSource();
      if (StringUtils.hasLength(text)) {
         PropertiesEditor propertiesEditor = new PropertiesEditor();
         propertiesEditor.setAsText(text);
         Properties props = (Properties)propertiesEditor.getValue();
         TransactionAttributeEditor tae = new TransactionAttributeEditor();
         Enumeration propNames = props.propertyNames();

         while(propNames.hasMoreElements()) {
            String name = (String)propNames.nextElement();
            String value = props.getProperty(name);
            tae.setAsText(value);
            TransactionAttribute attr = (TransactionAttribute)tae.getValue();
            source.addTransactionalMethod(name, attr);
         }
      }

      this.setValue(source);
   }
}
