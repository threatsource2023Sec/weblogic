package com.bea.httppubsub.servlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public interface HttpSessionRetriever {
   HttpSession retrieve(HttpServletRequest var1);
}
