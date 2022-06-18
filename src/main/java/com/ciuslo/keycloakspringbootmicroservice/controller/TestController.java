package com.ciuslo.keycloakspringbootmicroservice.controller;

import com.ciuslo.keycloakspringbootmicroservice.service.JwtDecoder;
import com.sun.tools.sjavac.Log;

import java.security.Principal;
import java.util.Map;

import javax.annotation.security.RolesAllowed;

import org.keycloak.KeycloakPrincipal;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.keycloak.representations.IDToken;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.xml.SourceHttpMessageConverter;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
// import javax.annotation.security.RolesAllowed;

@RestController
@RequestMapping("/test")
public class TestController {

    @RequestMapping(value = {"/anonim","/none","/anonymous"}, method = RequestMethod.GET)
    public ResponseEntity<String> getAnonymous() {
        return ResponseEntity.ok("Hello ngawur!!");
    }

    // @RolesAllowed("user")
    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public ResponseEntity<String> getUser(@RequestHeader String Authorization) {
        return ResponseEntity.ok("Hello User");
    }

    // @RolesAllowed("admin")
    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public ResponseEntity<String> getAdmin(@RequestHeader String Authorization) {
        return ResponseEntity.ok("Hello Admin");
    }

    @RolesAllowed({ "admin", "user" })
    @RequestMapping(value = "/all-user", method = RequestMethod.GET)
    // public ResponseEntity<String> getAllUser(@RequestHeader String Authorization) {
        // System.out.println("Auth : "+Authorization);
        // System.out.println(new JwtDecoder().decodeJwt(Authorization));
    public ResponseEntity<String> getAllUser() {
        KeycloakAuthenticationToken authentication = (KeycloakAuthenticationToken) 
        SecurityContextHolder.getContext().getAuthentication();
      
      Principal principal = (Principal) authentication.getPrincipal();        
      String id="";
      String email="";
      
      if (principal instanceof KeycloakPrincipal) {
          KeycloakPrincipal kPrincipal = (KeycloakPrincipal) principal;
          IDToken token = kPrincipal.getKeycloakSecurityContext().getIdToken();
          email = token.getEmail();
          Map<String, Object> customClaims = token.getOtherClaims();

          if (customClaims.containsKey("IDuser")) {
              id = String.valueOf(customClaims.get("IDuser"));
          }
      }
        
        return ResponseEntity.ok("Hello All User "+email+" "+id);
    }

}