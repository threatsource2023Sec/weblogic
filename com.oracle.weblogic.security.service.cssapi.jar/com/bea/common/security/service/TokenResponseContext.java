package com.bea.common.security.service;

public interface TokenResponseContext {
   boolean isChallengeComplete();

   Object issueToken();

   Object getChallengeToken();
}
