/*
 * Copyright 2016 the original author or authors.
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
package console;

import console.demo.WebTextIoExecutor;
import java.util.HashMap;
import org2.beryx.textio.*;
import org2.beryx.textio.web.*;

import java.util.function.BiConsumer;
import ratpack.handling.Context;

/**
 * Demo application showing various TextTerminals.
 */
public class ConsoleWeb {
    
    //TODO please remove this awfull hack one day
    public static HashMap<String, DataExchange> dataList = new HashMap();
    
    public static void addDataExchange(DataExchange dataExchange, Context context){
        if(context == null || dataExchange == null){
            return;
        }
        dataExchange.setContext(context);
        String sessionId = dataExchange.getSessionId();
        dataList.put(sessionId, dataExchange);
    }
    
    /**
     * Update the connections with the data we want to persist
     */
    public static void updateData(){
    
    }

    public static void main(String[] args) {
        BiConsumer<TextIO, RunnerData> app = new BootStart();
        WebTextTerminal term = new WebTextTerminal();
        term.init();
        TextIO textIO = new TextIO(term);
        WebTextTerminal webTextTerm = (WebTextTerminal) textIO.getTextTerminal();
        RatpackTextIoApp textIoApp = new RatpackTextIoApp(app, webTextTerm);
        
        
        WebTextIoExecutor webTextIoExecutor = new WebTextIoExecutor();
        webTextIoExecutor.withPort(8080);
        try{
            webTextIoExecutor.execute(textIoApp);
        }catch (Exception e){
        }
    }

    public static boolean hasSessionId(String sessionId) {
        return dataList.containsKey(sessionId);
    }

}
