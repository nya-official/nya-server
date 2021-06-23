/*
 * Description: <add description>
 * License: Apache-2.0
 * Copyright: x-file.xyz
 */
package console.demo;

import ratpack.server.RatpackServer;

/**
 * Date: 2021-06-17
 * Place: Zwingenberg, Germany
 * @author brito
 */
public class test1 {
public static void main(String... args) throws Exception {
   RatpackServer.start(server -> server 
     .handlers(chain -> chain
       .get(ctx -> ctx.render("Hello World!"))
       .get(":name", ctx -> ctx.render("Hello " + ctx.getPathTokens().get("name") + "!"))     
     )
   );
 }
}
