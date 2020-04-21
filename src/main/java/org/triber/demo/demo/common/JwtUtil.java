package org.triber.demo.demo.common;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.util.*;

public class JwtUtil {
    /**
     * token 前缀
     */
    public static final String TOKEN_PREFIX = "Bearer ";
    private static final Logger log = LoggerFactory.getLogger(JwtUtil.class);
    /**
     * 过期时间为一天
     * TODO 正式上线更换为15分钟
     */
    private static final long EXPIRATION_TIME = 24 * 60 * 60 * 1000;
    /**
     * token私钥
     */
    private static final String SECRET = "Pass@word";
    /**
     * 存放Token的Header Key
     */
    private static final String HEADER_STRING = "Authorization";

    public static String generateToken(String userId) {
        HashMap<String, Object> map = new HashMap<>();
        //可以将自定义相关的数据放入Map中
        map.put("USER_NAME", userId);
        String jwt = Jwts.builder()
                .setClaims(map)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact();
        //jwt前面一般都会加Bearer
        return TOKEN_PREFIX + jwt;
    }

    public static HttpServletRequest validateTokenAndAddUserIdToHeader(HttpServletRequest request) {
        String token = request.getHeader(HEADER_STRING);
        log.info("请求token:" + token);
        if (token != null) {
            // 解析令牌token.
            try {
                Map<String, Object> body = Jwts.parser()
                        .setSigningKey(SECRET)
                        .parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
                        .getBody();
                //遍历自定的相关数据，可以删除
                for (Map.Entry<String, Object> entry : body.entrySet()) {

                    log.info("****JWT paser Key = " + entry.getKey() + ", Value = " + entry.getValue());

                }
                return new CustomHttpServletRequest(request, body);
            } catch (Exception e) {
                log.info(e.getMessage());
                throw new TokenValidationException(e.getMessage());
            }
        } else {
            throw new TokenValidationException("The token is invalid!");
        }
    }

    public static class CustomHttpServletRequest extends HttpServletRequestWrapper {
        private final Map<String, String> claims;

        public CustomHttpServletRequest(HttpServletRequest request, Map<String, ?> claims) {
            super(request);
            this.claims = new HashMap<>();
            claims.forEach((k, v) -> this.claims.put(k, String.valueOf(v)));
        }

        @Override
        public Enumeration<String> getHeaders(String name) {
            if (claims != null && claims.containsKey(name)) {
                return Collections.enumeration(Arrays.asList(claims.get(name)));
            }
            return super.getHeaders(name);
        }

        public Map<String, String> getClaims() {
            return claims;
        }
    }

    public static class TokenValidationException extends RuntimeException {
        public TokenValidationException(String msg) {
            super(msg);
        }
    }
}