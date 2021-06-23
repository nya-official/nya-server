/*
 * Copyright 2017 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package console.demo;

import com.sun.org.slf4j.internal.LoggerFactory;
import org2.beryx.textio.web.TextIoApp;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import org2.beryx.textio.web.RatpackTextIoApp;
import org2.beryx.textio.web.WebTextTerminal;

/**
 * Allows executing code in a {@link org.beryx.textio.web.WebTextTerminal}
 * by configuring and initializing a {@link TextIoApp}.
 */
public class WebTextIoExecutor {
    private Integer port;

    public WebTextIoExecutor withPort(int port) {
        this.port = port;
        return this;
    }

    @SuppressWarnings("CallToPrintStackTrace")
    public void execute(TextIoApp<?> app) {
        
        try{
            Consumer<String> stopServer = sessionId
                    -> Executors.newSingleThreadScheduledExecutor().schedule(() 
                            -> {
                                System.exit(0);
                                 }, 2, TimeUnit.SECONDS);

            
            
            RatpackTextIoApp app2 = (RatpackTextIoApp) app;
            //app2.withSessionDataProvider(null, null);
            
            
            app.withOnDispose(stopServer);
            app.withPort(port);
            app.withMaxInactiveSeconds(600);
            app.withStaticFilesLocation("public-html");
            app.init();
            
        }catch (Exception e){
        }
        String url = "http://localhost:" + app.getPort() + "/console.html";
        boolean browserStarted = false;
        if(Desktop.isDesktopSupported()) {
            try {
                Desktop.getDesktop().browse(new URI(url));
                browserStarted = true;
            } catch (IOException | URISyntaxException e) {
                e.printStackTrace();
            }
        }
        if(!browserStarted) {
            System.out.println("Please open the following link in your browser: " + url);
        }
    }
}
