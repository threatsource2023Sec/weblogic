package org.glassfish.tyrus.core.uri;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.glassfish.tyrus.core.DebugContext;
import org.glassfish.tyrus.core.TyrusEndpointWrapper;
import org.glassfish.tyrus.core.uri.internal.PathSegment;
import org.glassfish.tyrus.core.uri.internal.UriComponent;

public class Match {
   private final TyrusEndpointWrapper endpointWrapper;
   private final Map parameters = new HashMap();
   private final List variableSegmentIndices = new ArrayList();
   private static final Logger LOGGER = Logger.getLogger(Match.class.getName());

   private Match(TyrusEndpointWrapper endpointWrapper) {
      this.endpointWrapper = endpointWrapper;
   }

   List getVariableSegmentIndices() {
      return this.variableSegmentIndices;
   }

   int getLowestVariableSegmentIndex() {
      return this.getVariableSegmentIndices().isEmpty() ? -1 : (Integer)this.getVariableSegmentIndices().get(0);
   }

   void addParameter(String name, String value, int index) {
      this.parameters.put(name, value);
      this.variableSegmentIndices.add(index);
   }

   public Map getParameters() {
      return this.parameters;
   }

   public TyrusEndpointWrapper getEndpointWrapper() {
      return this.endpointWrapper;
   }

   public String toString() {
      return this.endpointWrapper.getEndpointPath();
   }

   boolean isExact() {
      return this.getLowestVariableSegmentIndex() == -1;
   }

   public static List getAllMatches(String requestPath, Set endpoints, DebugContext debugContext) {
      List matches = new ArrayList();
      Iterator var4 = endpoints.iterator();

      while(var4.hasNext()) {
         TyrusEndpointWrapper endpoint = (TyrusEndpointWrapper)var4.next();
         Match m = matchPath(requestPath, endpoint, debugContext);
         if (m != null) {
            matches.add(m);
         }
      }

      Collections.sort(matches, new MatchComparator(debugContext));
      debugContext.appendTraceMessage(LOGGER, Level.FINE, DebugContext.Type.MESSAGE_IN, "Endpoints matched to the request URI: ", matches);
      return matches;
   }

   private static Match matchPath(String requestPath, TyrusEndpointWrapper endpoint, DebugContext debugContext) {
      debugContext.appendTraceMessage(LOGGER, Level.FINE, DebugContext.Type.MESSAGE_IN, "Matching request URI ", requestPath, " against ", endpoint.getEndpointPath());
      List requestPathSegments = UriComponent.decodePath(requestPath, true);
      List endpointPathSegments = UriComponent.decodePath(endpoint.getEndpointPath(), true);
      if (requestPathSegments.size() != endpointPathSegments.size()) {
         debugContext.appendTraceMessage(LOGGER, Level.FINE, DebugContext.Type.MESSAGE_IN, "URIs ", requestPath, " and ", endpoint.getEndpointPath(), " have different length");
         return null;
      } else {
         Match m = new Match(endpoint);
         boolean somethingMatched = false;

         for(int i = 0; i < requestPathSegments.size(); ++i) {
            String requestSegment = ((PathSegment)requestPathSegments.get(i)).getPath();
            String endpointSegment = ((PathSegment)endpointPathSegments.get(i)).getPath();
            if (requestSegment.equals(endpointSegment)) {
               somethingMatched = true;
            } else {
               if (!isVariable(endpointSegment)) {
                  debugContext.appendTraceMessage(LOGGER, Level.FINE, DebugContext.Type.MESSAGE_IN, "Segment \"", endpointSegment, "\" does not match");
                  return null;
               }

               somethingMatched = true;
               m.addParameter(getVariableName(endpointSegment), requestSegment, i);
            }
         }

         if (somethingMatched) {
            return m;
         } else {
            return null;
         }
      }
   }

   public static boolean isEquivalent(String path1, String path2) {
      List path1EList = asEquivalenceList(path1);
      List path2EList = asEquivalenceList(path2);
      return path1EList.equals(path2EList);
   }

   private static List asEquivalenceList(String path) {
      List equivalenceList = new ArrayList();
      List segments = UriComponent.decodePath(path, true);
      Iterator var3 = segments.iterator();

      while(var3.hasNext()) {
         PathSegment next = (PathSegment)var3.next();
         if (isVariable(next.getPath())) {
            equivalenceList.add("{x}");
         } else {
            equivalenceList.add(next.getPath());
         }
      }

      return equivalenceList;
   }

   private static boolean isVariable(String segment) {
      return segment.startsWith("{") && segment.endsWith("}");
   }

   private static String getVariableName(String segment) {
      return segment.substring(1, segment.length() - 1);
   }
}
