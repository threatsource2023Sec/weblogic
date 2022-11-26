package com.sun.faces.facelets.tag.jsf.core;

import com.sun.faces.facelets.tag.AbstractTagLibrary;

public final class CoreLibrary extends AbstractTagLibrary {
   public static final String Namespace = "http://java.sun.com/jsf/core";
   public static final String XMLNSNamespace = "http://xmlns.jcp.org/jsf/core";
   public static final CoreLibrary Instance = new CoreLibrary();

   public CoreLibrary() {
      this("http://java.sun.com/jsf/core");
   }

   public CoreLibrary(String namespace) {
      super(namespace);
      this.addTagHandler("actionListener", ActionListenerHandler.class);
      this.addTagHandler("ajax", AjaxHandler.class);
      this.addTagHandler("attribute", AttributeHandler.class);
      this.addTagHandler("attributes", AttributesHandler.class);
      this.addTagHandler("passThroughAttribute", PassThroughAttributeHandler.class);
      this.addTagHandler("passThroughAttributes", PassThroughAttributesHandler.class);
      this.addConverter("convertDateTime", "javax.faces.DateTime", ConvertDateTimeHandler.class);
      this.addConverter("convertNumber", "javax.faces.Number", ConvertNumberHandler.class);
      this.addConverter("converter", (String)null, ConvertDelegateHandler.class);
      this.addTagHandler("event", EventHandler.class);
      this.addTagHandler("facet", FacetHandler.class);
      this.addTagHandler("metadata", MetadataHandler.class);
      this.addComponent("importConstants", "javax.faces.ImportConstants", (String)null);
      this.addTagHandler("loadBundle", LoadBundleHandler.class);
      this.addTagHandler("resetValues", ResetValuesHandler.class);
      this.addComponent("viewParam", "javax.faces.ViewParameter", (String)null);
      this.addComponent("viewAction", "javax.faces.ViewAction", (String)null);
      this.addComponent("param", "javax.faces.Parameter", (String)null);
      this.addTagHandler("phaseListener", PhaseListenerHandler.class);
      this.addComponent("selectItem", "javax.faces.SelectItem", (String)null);
      this.addComponent("selectItems", "javax.faces.SelectItems", (String)null);
      this.addTagHandler("setPropertyActionListener", SetPropertyActionListenerHandler.class);
      this.addComponent("subview", "javax.faces.NamingContainer", (String)null);
      this.addValidator("validateBean", "javax.faces.Bean");
      this.addValidator("validateLength", "javax.faces.Length");
      this.addValidator("validateLongRange", "javax.faces.LongRange");
      this.addValidator("validateDoubleRange", "javax.faces.DoubleRange");
      this.addValidator("validateRegex", "javax.faces.RegularExpression");
      this.addValidator("validateRequired", "javax.faces.Required");
      this.addComponent("validateWholeBean", "com.sun.faces.ext.validateWholeBean", (String)null);
      this.addValidator("validator", (String)null, ValidateDelegateHandler.class);
      this.addTagHandler("valueChangeListener", ValueChangeListenerHandler.class);
      this.addTagHandler("view", ViewHandler.class);
      this.addComponent("verbatim", "javax.faces.HtmlOutputText", "javax.faces.Text", VerbatimHandler.class);
      this.addComponent("websocket", "javax.faces.Websocket", "javax.faces.Websocket");
   }
}
