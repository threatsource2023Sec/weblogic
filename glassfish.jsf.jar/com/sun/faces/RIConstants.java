package com.sun.faces;

public class RIConstants {
   public static final String FACES_PREFIX = "com.sun.faces.";
   public static final String HTML_BASIC_RENDER_KIT = "com.sun.faces.HTML_BASIC";
   public static final String SAVESTATE_FIELD_DELIMITER = "~";
   public static final String SAVESTATE_FIELD_MARKER = "~com.sun.faces.saveStateFieldMarker~";
   public static final String SAVED_STATE = "com.sun.faces.savedState";
   public static final String TLV_RESOURCE_LOCATION = "com.sun.faces.resources.Resources";
   public static final String NO_VALUE = "";
   public static final String CORE_NAMESPACE = "http://java.sun.com/jsf/core";
   public static final String HTML_NAMESPACE = "http://java.sun.com/jsf/html";
   public static final String CORE_NAMESPACE_NEW = "http://xmlns.jcp.org/jsf/core";
   public static final String HTML_NAMESPACE_NEW = "http://xmlns.jcp.org/jsf/html";
   public static final String FACELET_NAMESPACE = "http://java.sun.com/jsf/facelets";
   public static final String FACELET_NAMESPACE_NEW = "http://xmlns.jcp.org/jsf/facelets";
   public static final Class[] EMPTY_CLASS_ARGS = new Class[0];
   public static final Object[] EMPTY_METH_ARGS = new Object[0];
   public static final String HTML_CONTENT_TYPE = "text/html";
   public static final String XHTML_CONTENT_TYPE = "application/xhtml+xml";
   public static final String APPLICATION_XML_CONTENT_TYPE = "application/xml";
   public static final String TEXT_XML_CONTENT_TYPE = "text/xml";
   public static final String ALL_MEDIA = "*/*";
   public static final String CHAR_ENCODING = "UTF-8";
   public static final String FACELETS_ENCODING_KEY = "facelets.Encoding";
   public static final String DEFAULT_LIFECYCLE = "com.sun.faces.DefaultLifecycle";
   public static final String DEFAULT_STATEMANAGER = "com.sun.faces.DefaultStateManager";
   public static final String ERROR_PAGE_PRESENT_KEY_NAME = "com.sun.faces.errorPagePresent";
   public static final String FACES_INITIALIZER_MAPPINGS_ADDED = "com.sun.faces.facesInitializerMappingsAdded";
   public static final String VIEWID_KEY_NAME = "com.sun.faces.viewId";
   public static final String PUSH_RESOURCE_URLS_KEY_NAME = "com.sun.faces.resourceUrls";
   public static final String DYNAMIC_ACTIONS = "com.sun.faces.DynamicActions";
   public static final String DYNAMIC_CHILD_COUNT = "com.sun.faces.DynamicChildCount";
   public static final String DYNAMIC_COMPONENT = "com.sun.faces.DynamicComponent";
   public static final String TREE_HAS_DYNAMIC_COMPONENTS = "com.sun.faces.TreeHasDynamicComponents";
   public static final String FLOW_DEFINITION_ID_SUFFIX = "-flow.xml";
   public static final int FLOW_DEFINITION_ID_SUFFIX_LENGTH = "-flow.xml".length();
   public static final String FLOW_IN_JAR_PREFIX = "META-INF/flows";
   public static final int FLOW_IN_JAR_PREFIX_LENGTH = "META-INF/flows".length();
   public static final String FLOW_DISCOVERY_CDI_HELPER_BEAN_NAME = "csfFLOWDISCOVERYCDIHELPER";
   public static final String JAVAEE_XMLNS = "http://xmlns.jcp.org/xml/ns/javaee";
   public static final String CDI_AVAILABLE = "com.sun.faces.cdi.AvailableFlag";
   public static final String CDI_BEAN_MANAGER = "com.sun.faces.cdi.BeanManager";
   public static final String CDI_1_1_OR_LATER = "com.sun.faces.cdi.OneOneOrLater";
   public static final String FACES_CONFIG_VERSION = "com.sun.faces.facesConfigVersion";
   public static final String ANNOTATED_CLASSES = "com.sun.faces.AnnotatedClasses";

   private RIConstants() {
      throw new IllegalStateException();
   }
}
