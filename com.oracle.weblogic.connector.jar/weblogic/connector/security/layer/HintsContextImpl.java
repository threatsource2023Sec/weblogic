package weblogic.connector.security.layer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;
import javax.resource.spi.work.HintsContext;
import javax.resource.spi.work.WorkContext;
import javax.resource.spi.work.WorkContextLifecycleListener;
import weblogic.connector.security.SubjectStack;
import weblogic.security.acl.internal.AuthenticatedSubject;

public class HintsContextImpl extends HintsContext implements WorkContextWrapper {
   private static final long serialVersionUID = -451873090649376697L;
   private WorkContextImpl contextImpl;
   private ArrayList errors;
   private boolean isLongRunningValue;
   private String nameValue;

   public HintsContextImpl(HintsContext context, SubjectStack adapterLayer, AuthenticatedSubject kernelId) {
      this.contextImpl = new WorkContextImpl(context, adapterLayer, "HintsContext", kernelId);
   }

   public Class getOriginalClass() {
      return this.contextImpl.getOriginalClass();
   }

   public WorkContext getOriginalWorkContext() {
      return this.contextImpl.getOriginalWorkContext();
   }

   public WorkContextLifecycleListener getOriginalWorkContextLifecycleListener() {
      return this.contextImpl.getOriginalWorkContextLifecycleListener();
   }

   public boolean supportWorkContextLifecycleListener() {
      return this.contextImpl.supportWorkContextLifecycleListener();
   }

   public String getDescription() {
      return this.contextImpl.getDescription();
   }

   public String getName() {
      return this.contextImpl.getName();
   }

   public void contextSetupComplete() {
      this.contextImpl.contextSetupComplete();
   }

   public void contextSetupFailed(String errorCode) {
      this.contextImpl.contextSetupFailed(errorCode);
   }

   public boolean equals(Object other) {
      return this == other;
   }

   public int hashCode() {
      return this.contextImpl.hashCode();
   }

   public String toString() {
      return this.contextImpl.toString();
   }

   public HintsContext getOriginalHintsContext() {
      return (HintsContext)this.getOriginalWorkContext();
   }

   public void setDescription(String description) {
      this.contextImpl.rejectIllegalUpdate();
   }

   public void setName(String name) {
      this.contextImpl.rejectIllegalUpdate();
   }

   public void setHint(String hintName, Serializable value) {
      this.contextImpl.rejectIllegalUpdate();
   }

   public Map getHints() {
      this.contextImpl.push();

      Map var1;
      try {
         var1 = this.getOriginalHintsContext().getHints();
      } finally {
         this.contextImpl.pop();
      }

      return var1;
   }

   public final ArrayList getErrors() {
      return this.errors;
   }

   public final void setErrors(ArrayList errors) {
      this.errors = errors;
   }

   public final boolean isLongRunningValue() {
      return this.isLongRunningValue;
   }

   public final void setLongRunningValue(boolean isLongRunningValue) {
      this.isLongRunningValue = isLongRunningValue;
   }

   public final String getNameValue() {
      return this.nameValue;
   }

   public final void setNameValue(String nameValue) {
      this.nameValue = nameValue;
   }

   public final boolean validated() {
      return this.errors != null;
   }
}
