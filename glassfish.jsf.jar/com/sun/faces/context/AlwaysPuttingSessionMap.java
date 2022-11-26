package com.sun.faces.context;

import javax.faces.application.ProjectStage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class AlwaysPuttingSessionMap extends SessionMap {
   public AlwaysPuttingSessionMap(HttpServletRequest request, ProjectStage stage) {
      super(request, stage);
   }

   public Object put(String key, Object value) {
      HttpSession session = this.getSession(true);
      Object result = session.getAttribute(key);
      session.setAttribute(key, value);
      return result;
   }
}
