package dec.team3.spiritanimal.services;

import dec.team3.spiritanimal.model.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuthService{

    // TODO: AuthService testen.
    //  Dazu: schnell API anlegen und zunächst über Postman testen, ob Funktionen arbeiten wie erwartet

    // TODO:
    //  In allen Controllern wird zunächst der Token im Header geprüft, bevor der Call durchgelassen wird
    //  Frontend Seite zum Anzeigen und kopieren des Tokens anlegen (für B2B Kunden)
    //  Ist der Token abgelaufen, muss der User sich wieder übers Frontend einloggen um einen neuen Token zu erhalten.
    //  Der Authenticator überprüft also schlicht, ob ein Token gültig ist

    String secretKey = "animallover";

    // create token from username and role
    public String createToken(String username, Role role){
        List<GrantedAuthority> grantedAuthorities;
        grantedAuthorities = AuthorityUtils
                .commaSeparatedStringToAuthorityList(role.toString());

        String token = Jwts
                .builder()
                .setId("spiritanimal")
                .setSubject(username)
                .claim("authorities",
                        grantedAuthorities.stream()
                                .map(GrantedAuthority::getAuthority)
                                .collect(Collectors.toList()))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 3600000)) // 60.000 millis = 1 min / 3.600.000 millis = 1 h
                .signWith(SignatureAlgorithm.HS512,
                        secretKey.getBytes()).compact();
        return "Bearer " + token;
    }


    // check if valid token (authentication)
    // (used for authorizing api calls, login is simply done in the UserService with username/pw connection)
    public boolean isTokenValid(String authorizationToken) {
        if (authorizeUserRole(Role.USER, authorizationToken)) {
            return true;
        }
        return authorizeUserRole(Role.ADMIN, authorizationToken);
    }


    // get Userrole from Token (Authorization)
    public boolean authorizeUserRole(Role role, String authorizationToken){
        String preparedAuthorizationToken = prepareAuthorizationToken(authorizationToken);
        try{
            List<String> roles = (List) parseAuthorizationToken(preparedAuthorizationToken).get("authorities");
            return roles.get(0).equals(role.toString());
        } catch(JwtException jwtException){
            return false;
        }
    }

    // get Username from Token (Authorization)
    public String getUsername(String authorizationToken){
        String preparedAuthorizationToken = prepareAuthorizationToken(authorizationToken);
        return parseAuthorizationToken(preparedAuthorizationToken).getSubject();
    }

    // helpers
    public Claims parseAuthorizationToken(String preparedAuthorizationToken){
        return Jwts.parser()
                .setSigningKey(secretKey.getBytes())
                .parseClaimsJws(preparedAuthorizationToken)
                .getBody();
    }

    public String prepareAuthorizationToken(String authorizationToken){
        return authorizationToken.
                substring("Bearer".length()+1);
    }
}
