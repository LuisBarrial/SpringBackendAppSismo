package backendpage.proyectosismo.Security.Filter;

import backendpage.proyectosismo.Security.ScopedBeans;
import backendpage.proyectosismo.Service.BlackListingService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JWTInterceptor extends OncePerRequestFilter {

    @Autowired
    private BlackListingService blackListingService;

    @Autowired
    private ScopedBeans scopedBeans;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {


            System.out.println("Hola soy el filtro");
            var token = recuperarToken(request);
            if (token==null)  token="Token";

            String blackListedToken = blackListingService.findJwtBlackList(token);
            if (blackListedToken != null) {
                System.out.println("JwtInterceptor: Token is blacklisted");
                response.sendError(401);
            }
            scopedBeans.setJwt(token);


            filterChain.doFilter(request,response);
    }
    private String recuperarToken(HttpServletRequest request){
        var authorizationHeader = request.getHeader("Authorization");
        if(authorizationHeader!=null){
            int startIndex = "Bearer ".length();
            if(authorizationHeader.length() > startIndex){
                return authorizationHeader.substring(startIndex);
            }
        }
        return null;
    }

}
