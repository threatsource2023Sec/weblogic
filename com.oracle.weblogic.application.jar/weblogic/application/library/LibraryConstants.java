package weblogic.application.library;

import java.util.jar.Attributes.Name;

public interface LibraryConstants {
   boolean DEFAULT_EXACT_MATCH = false;
   byte SPEC_VERSION_DEPTH = 2;
   byte IMPL_VERSION_DEPTH = 3;
   String LIBRARY_NAME = Name.EXTENSION_NAME.toString();
   String SPEC_VERSION_NAME = Name.SPECIFICATION_VERSION.toString();
   String IMPL_VERSION_NAME = Name.IMPLEMENTATION_VERSION.toString();
   String SOURCE_NAME = "referenced from";
   String EXACT_MATCH_NAME = "exact-match";
   String CONTEXT_ROOT_NAME = "context-root";
   String DEBUG_LIBRARIES = "DebugLibraries";
   String OPT_PACK_SWITCH = "weblogic.application.RequireOptionalPackages";
   String NAME_MUST_BE_SET_ERROR = "Library \"" + LIBRARY_NAME + "\" must be specified.";
   String NO_IMPL_WITHOUT_SPEC_ERROR = "Library cannot have " + IMPL_VERSION_NAME + " set, without also specifying its " + SPEC_VERSION_NAME;
   String LIBRARY_ALREADY_REGISTERED_ERROR = "Cannot register the same Library twice - this Library has already been registered: ";
   String OPT_PACK_ERROR = "Unresolved Optional Package references (in META-INF/MANIFEST.MF):";
   String LIBRARY_OPT = "library";
   String LIBRARY_DATA_SEP = "@";
   String EQUALS = "=";
   String LIBDIR_OPT = "librarydir";
   String NAME_ARG = "name";
   String SPEC_ARG = "libspecver";
   String IMPL_ARG = "libimplver";
   String AUTOREFLIB_KEY = "Auto-Ref-By";

   public static enum AutoReferrer {
      WebApp,
      EJBApp;
   }
}
