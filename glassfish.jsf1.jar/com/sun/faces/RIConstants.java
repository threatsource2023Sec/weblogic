package com.sun.faces;

public class RIConstants {
   public static final String FACES_PREFIX = "com.sun.faces.";
   public static final String HTML_BASIC_RENDER_KIT = "com.sun.faces.HTML_BASIC";
   public static final String SAVESTATE_FIELD_DELIMITER = "~";
   public static final String SAVESTATE_FIELD_MARKER = "~com.sun.faces.saveStateFieldMarker~";
   public static final String SAVED_STATE = "com.sun.faces.savedState";
   public static final String TLV_RESOURCE_LOCATION = "com.sun.faces.resources.Resources";
   public static final Object NO_VALUE = "";
   public static final String CORE_NAMESPACE = "http://java.sun.com/jsf/core";
   public static final String HTML_NAMESPACE = "http://java.sun.com/jsf/html";
   public static final Class[] EMPTY_CLASS_ARGS = new Class[0];
   public static final Object[] EMPTY_METH_ARGS = new Object[0];
   public static final String HTML_CONTENT_TYPE = "text/html";
   public static final String XHTML_CONTENT_TYPE = "application/xhtml+xml";
   public static final String APPLICATION_XML_CONTENT_TYPE = "application/xml";
   public static final String TEXT_XML_CONTENT_TYPE = "text/xml";
   public static final String ALL_MEDIA = "*/*";
   public static final String CHAR_ENCODING = "ISO-8859-1";
   public static final String SUN_JSF_JS_URI = "com_sun_faces_sunjsf.js";
   public static final String DEFAULT_LIFECYCLE = "com.sun.faces.DefaultLifecycle";
   public static final String DEFAULT_STATEMANAGER = "com.sun.faces.DefaultStateManager";

   private RIConstants() {
      throw new IllegalStateException();
   }
}
