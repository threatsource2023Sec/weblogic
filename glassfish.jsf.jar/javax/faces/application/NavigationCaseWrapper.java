package javax.faces.application;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;
import javax.faces.FacesWrapper;
import javax.faces.context.FacesContext;

public abstract class NavigationCaseWrapper extends NavigationCase implements FacesWrapper {
   private NavigationCase wrapped;

   /** @deprecated */
   @Deprecated
   public NavigationCaseWrapper() {
      this((NavigationCase)null);
   }

   public NavigationCaseWrapper(NavigationCase wrapped) {
      super((String)null, (String)null, (String)null, (String)null, (String)null, (Map)null, false, false);
      this.wrapped = wrapped;
   }

   public NavigationCase getWrapped() {
      return this.wrapped;
   }

   public boolean equals(Object o) {
      return this.getWrapped().equals(o);
   }

   public int hashCode() {
      return this.getWrapped().hashCode();
   }

   public String toString() {
      return this.getWrapped().toString();
   }

   public URL getActionURL(FacesContext context) throws MalformedURLException {
      return this.getWrapped().getActionURL(context);
   }

   public URL getBookmarkableURL(FacesContext context) throws MalformedURLException {
      return this.getWrapped().getBookmarkableURL(context);
   }

   public Boolean getCondition(FacesContext context) {
      return this.getWrapped().getCondition(context);
   }

   public String getFromAction() {
      return this.getWrapped().getFromAction();
   }

   public String getFromOutcome() {
      return this.getWrapped().getFromOutcome();
   }

   public String getFromViewId() {
      return this.getWrapped().getFromViewId();
   }

   public Map getParameters() {
      return this.getWrapped().getParameters();
   }

   public URL getRedirectURL(FacesContext context) throws MalformedURLException {
      return this.getWrapped().getRedirectURL(context);
   }

   public URL getResourceURL(FacesContext context) throws MalformedURLException {
      return this.getWrapped().getResourceURL(context);
   }

   public String getToViewId(FacesContext context) {
      return this.getWrapped().getToViewId(context);
   }

   public String getToFlowDocumentId() {
      return this.getWrapped().getToFlowDocumentId();
   }

   public boolean hasCondition() {
      return this.getWrapped().hasCondition();
   }

   public boolean isIncludeViewParams() {
      return this.getWrapped().isIncludeViewParams();
   }

   public boolean isRedirect() {
      return this.getWrapped().isRedirect();
   }
}
