problems faced:-
1. which pattern multitenant to use
2. some entity classes can be location independent and some dont

jwt auth:-
    Dependencies — WHY we need them
1. why spring-boot-starter-security:-
Gives:
Filter chain
AuthenticationManager
SecurityContext
Authorization rules

2. jjwt-api / impl / jackson
👉 Used to:
Create JWT
Sign JWT
Parse & validate JWT

    JwtUtil — WHY this class exists
3. JwtUtil :- “Everything related to token creation & validation”
4. SECRET_KEY :- 👉 Used to sign the token
                 👉 Same key is required to verify it
                 If key changes → all tokens become invalid
5. .setSubject(username) :- ✔ Subject = identity of user
                            ✔ This is how we know who is making request
6. .signWith(...) :- ✔ Prevents tampering
                     ✔ If user changes token → signature fails
                     Without signing → anyone can fake tokens
7. .parseClaimsJws(token) :- ✔ Verifies:
                             Signature, Expiry, Token integrity
                             If token is invalid → exception thrown
8. JwtAuthFilter :- This is where JWT replaces session.
                    Spring will NOT automatically check JWT
                    So we intercept every request.
9. extends OncePerRequestFilter:- ✔ Ensures filter runs only once per request
                                  ✔ Prevents duplicate authentication
10. Why check Bearer :- ✔ Standard format
                        ✔ Prevents parsing junk headers
11. jwtUtil.validateToken(token) :- ✔ Reject expired / modified tokens
                                    ✔ No DB hit here → fast
12. Load userDetails :- userDetailsService.loadUserByUsername(username);
                        ❓ Why DB call again if token already has username?
                        Because:
                        Authorities (roles) come from DB
                        Account may be disabled/locked
                        Token ≠ permission source
                        ⚠️ JWT stores identity, not trust
13. Create authentication object:-
    UsernamePasswordAuthenticationToken :- This is Spring Security’s internal proof
                                           “Yes, this user is authenticated”
                                           Without this → Spring thinks user is anonymous
14. Set SecurityContext :- SecurityContextHolder.getContext().setAuthentication(auth);
                            ✔ After this:
                            @PreAuthorize works
                            @AuthenticationPrincipal works
                            authenticated() works
                            Without this → request always unauthorized

          SecurityConfig — WHY each line exists
15. Stateless Session:- sessionCreationPolicy(STATELESS)
                        ✔ No HttpSession
                        ✔ No JSESSIONID
                        ✔ Every request must bring token
16. Add Jwt filter:- addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                       ✔ JWT must run before Spring’s auth logic
                       ✔ Otherwise Spring blocks request early
                       Wrong order = token ignored

        AuthController — WHY AuthenticationManager
17.authenticationManager.authenticate(...) :- ✔ Delegates to:
                                              UserDetailsService
                                              PasswordEncoder
                                              ✔ Prevents writing password logic manually
                                              ✔ Uses Spring Security internals safely
                                              If this fails → exception thrown automatically
18. Why generate token AFTER authentication :- String token = jwtUtil.generateToken(username);
                        ✔ Token only issued for valid users
                        ✔ No token logic inside filter (clean separation)
19.  Why JWT works WITHOUT session
    Traditional:
        Session ID → Server memory → User
    JWT:
        Token → Signature → User
 ✔ No server memory
 ✔ Horizontal scaling
 ✔ Microservice friendly

 ⚠️ Common Mistakes (PLEASE AVOID)

 ❌ Storing refresh token in localStorage
 ❌ Same secret for access & refresh token
 ❌ Very long access token expiry
 ❌ Not rotating refresh tokens
 ❌ Allowing refresh token on all paths
