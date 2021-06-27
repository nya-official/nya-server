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
package console;

import base.core;
import org2.beryx.textio.TextIO;
import org2.beryx.textio.TextIoFactory;
import org2.beryx.textio.TextTerminal;
import org2.beryx.textio.web.RunnerData;

import java.util.function.BiConsumer;
import ratpack.handling.Context;
import user.User;
import user.UserType;
import user.Users;

/**
 * Illustrates some features introduced in version 3 of Text-IO: line reset, bookmarking etc.
 */
public class BootStart implements BiConsumer<TextIO, RunnerData> {
    
    public static void main(String[] args) {
        TextIO textIO = TextIoFactory.getTextIO();
        new BootStart().accept(textIO, null);
    }

    @Override
    public void accept(TextIO textIO, RunnerData runnerData) {
        
        TextTerminal<?> terminal = textIO.getTextTerminal();
        
        @SuppressWarnings("null")
        Context context = runnerData.getContext();
        
        String key = context.getRequest().oneCookie(core.dbUserKey);
        String password = context.getRequest().oneCookie(core.dbUserPassword);
        
        User user = User.getUserSHA1(key);
        // password needs to be accepted
        if(user.getPassword().equals(password) == false){
            user = Users.getAnon();
        }
        
        // start the main banner
        terminal.println(AsciiArt.nya());
        terminal.println();
        
        // show that we are logged
        if(user.getUserType() != UserType.anon){
            //terminal.println("logged as: " + user.getId());
        }else{
            terminal.println("Welcome! Type \"help\" to get started");
        }
        
        // reset the current folder
        user.setFolderCurrent(user.getFilesystem());
        
        terminal.println();
        terminal.setBookmark("MAIN");
        
        
        boolean continueLooping = true;
        while(continueLooping) {
            
            // generate the command prompt
            String commandPrompt = generateCommandPrompt(user);
            // wait for a command to be input
            String command = textIO.newStringInputReader().read(commandPrompt);
            
            
            // try to process the command
            DataExchange data = processCommand(command, user, terminal, context);
            if(data != null && data.getUser() != null){
                user = data.getUser();
            }
            // put it in memory
            ConsoleWeb.addDataExchange(data, context);
            
        //delay(500);
        }
    }
    
    @SuppressWarnings("CallToPrintStackTrace")
    public static void delay(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Process a command and acts accordingly
     * @param command
     * @param user
     * @param terminal
     * @param context 
     */
    private DataExchange processCommand(String command, User user,
            TextTerminal<?> terminal, Context context) {
        if(command == null){
            command = "";
        }
        // avoid overflow
        if(command.length() > 1000){
            command = command.substring(0, 1000);
        }
        
        // run it
        DataExchange data = core.commands.run(command, user, terminal, context);
        return data;
    }

    private String getFolder(User user) {
        return user.getFolderCurrent().getPath();
    }

    private String generateCommandPrompt(User user) {
        String commandPrompt = user.getId()
                + "@nya:~";

        if (getFolder(user).equalsIgnoreCase("/") == false) {
            commandPrompt += getFolder(user);
        }
        commandPrompt += "$";
        return commandPrompt;
    }
}
