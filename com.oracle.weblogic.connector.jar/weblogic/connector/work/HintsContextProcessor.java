package weblogic.connector.work;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import javax.resource.spi.work.HintsContext;
import javax.resource.spi.work.WorkContext;
import weblogic.connector.ConnectorLogger;
import weblogic.connector.common.Debug;
import weblogic.connector.security.SubjectStack;
import weblogic.connector.security.layer.HintsContextImpl;
import weblogic.connector.security.layer.WorkContextWrapper;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.utils.StackTraceUtils;

public class HintsContextProcessor extends BaseWorkContextProcessor {
   public Class getSupportedContextClass() {
      return HintsContext.class;
   }

   public WorkContextProcessor.WMPreference getpreferredWM(WorkContextWrapper context) {
      HintsContextImpl hic = (HintsContextImpl)context;
      this.validate(context, (WorkRuntimeMetadata)null);
      return hic.isLongRunningValue() ? WorkContextProcessor.WMPreference.longRunningWM : WorkContextProcessor.WMPreference.defaultWM;
   }

   public String validate(WorkContextWrapper context, WorkRuntimeMetadata work) {
      HintsContextImpl hic = (HintsContextImpl)context;
      if (!hic.validated()) {
         hic.setErrors(new ArrayList());

         try {
            Map hints = hic.getHints();
            Iterator var5 = hints.entrySet().iterator();

            while(var5.hasNext()) {
               Map.Entry entry = (Map.Entry)var5.next();
               String hintName = (String)entry.getKey();
               Serializable value = (Serializable)entry.getValue();
               if ("javax.resource.Name".equals(hintName)) {
                  if (this.isLegalNameHint(value, hic.getErrors())) {
                     hic.setNameValue(((String)value).toString().trim());
                     if (Debug.isWorkEnabled()) {
                        Debug.work("HintsContext: find valid NAME hint [" + hic.getNameValue() + "]");
                     }
                  }
               } else if ("javax.resource.LongRunning".equals(hintName)) {
                  if (this.isLegalLongRunningHint(value, hic.getErrors())) {
                     hic.setLongRunningValue((Boolean)value);
                     if (Debug.isWorkEnabled()) {
                        Debug.work("HintsContext: find valid LONGRUNNING hint [" + hic.isLongRunningValue() + "]");
                     }
                  }
               } else {
                  ConnectorLogger.logUnknownHintWarning(hintName, value == null ? "null" : value.toString());
               }
            }
         } catch (Throwable var9) {
            hic.setNameValue((String)null);
            hic.setLongRunningValue(false);
            hic.getErrors().add("exception when access HintsContext: " + StackTraceUtils.throwable2StackTrace(var9));
         }
      }

      if (hic.getErrors().isEmpty()) {
         return VALIDATION_OK;
      } else {
         if (Debug.isWorkEnabled()) {
            Debug.work("HintsContext: failed to validate: " + hic.getErrors().toString());
         }

         return hic.getErrors().toString();
      }
   }

   protected boolean isLegalLongRunningHint(Object value, ArrayList errors) {
      if (value == null) {
         errors.add("value of LONGRUNNING hint must not be null");
         return false;
      } else if (!(value instanceof Boolean)) {
         errors.add("value of LONGRUNNING hint is expected be boolean/Boolean but actually is [" + value.getClass().getName() + "]");
         return false;
      } else {
         return true;
      }
   }

   protected boolean isLegalNameHint(Object value, ArrayList errors) {
      if (value == null) {
         errors.add("value of NAME hint must not be null");
         return false;
      } else if (!(value instanceof String)) {
         errors.add("value of NAME hint is expected be String but actually is [" + value.getClass().getName() + "]");
         return false;
      } else if ("".equals(value.toString().trim())) {
         errors.add("value of NAME hint must not be empty String or contain white-space only");
         return false;
      } else {
         return true;
      }
   }

   public void setupContext(WorkContextWrapper context, WorkRuntimeMetadata metadata) {
      HintsContextImpl hic = (HintsContextImpl)context;
      String name = hic.getNameValue();
      if (name != null) {
         metadata.setWorkName(name);
      }

      if (hic.isLongRunningValue()) {
         metadata.setLongRunning(true);
      }

   }

   public WorkContextWrapper createWrapper(WorkContext originalWorkContext, SubjectStack adapterLayer, AuthenticatedSubject kernelId) {
      return new HintsContextImpl((HintsContext)originalWorkContext, adapterLayer, kernelId);
   }
}
