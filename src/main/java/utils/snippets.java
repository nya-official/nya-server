/*
 * Description: Useful code snippets
 * License: Apache-2.0
 * Copyright: x-file.xyz
 */
package utils;

/**
 * Date: 2019-03-26
 * Place: Erlangen, Germany
 * @author brito
 */
public class snippets {
    /*
    
    final HashSet<String> list = new HashSet(); 
    
    // ordered alphabetically
    private final TreeSet<String> listUniqueCopyrights = new TreeSet();
    TreeSet<Item> items = new TreeSet<>(Comparator.reverseOrder());
        
    
    // solve the concurrency modification
    public CopyOnWriteArrayList<LinkAbout>  getLinks() {
    
    // UserLogin user = UserGroup.get(req);
    
    // launch the thread
    Thread thread = new Thread(){
        @Override
        public void run(){
        }
    };
    thread.start();
    
    
        // sort the versions according to their text
        Collections.sort(version, new Comparator<String>() {
        @Override
            public int compare(String s1, String s2) {
                return s1.compareToIgnoreCase(s2);
            }
        });
    
    
     @SuppressWarnings("CallToPrintStackTrace")
    public static TwitterCredential jsonImport(String textJSON){
        try{
            Gson gson = new GsonBuilder()
                .setDateFormat(TwitterDate.dateFormat)
                .create();
            return gson.fromJson(textJSON, TwitterCredential.class);
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }
    
   
    public String jsonExport() {
        Gson gson = new GsonBuilder()
                .setDateFormat(TwitterDate.dateFormat)
                .excludeFieldsWithoutExposeAnnotation()
                //.enableComplexMapKeySerialization()
                //.setLenient()
                .setPrettyPrinting()
                .create();
        
        return gson.toJson(this);
    }
    
    
    -----------------------------------------------------------
    
    public ArrayList<String> getSortedGroups() {
        ArrayList<String> listFinal = new ArrayList();
        while(listFinal.size() != groups.size()){
            int highest = -1;
            String keyToAdd = null;
            for(String key : groups.keySet()){
                int value = groups.get(key).getProfiles().size();
                if(value > highest && listFinal.contains(key) == false){
                    highest = value;
                    keyToAdd = key;
                }
            }
            // add the highest to the list
            listFinal.add(keyToAdd);
        }
        return listFinal;
    }
    
    
    -----------------------------------------------------------
    
    TerminalProperties<?> props = terminal.getProperties();
        props.setPromptBold(true);
        props.setPromptUnderline(true);
        props.setPromptColor("cyan");
        terminal.print("");
        
        
        terminal.setBookmark("COUNTDOWN");
        terminal.println("Seconds to start:");
        for(int i=5; i>=0; i--) {
            terminal.resetLine();
            utils.time.waitMs(500);
            terminal.print("      " + i);
            utils.time.waitMs(500);
        }
        terminal.resetToBookmark("COUNTDOWN");

        terminal.println("################");
        terminal.println("# WEATHER INFO #");
        terminal.println("################");
        terminal.println();
    
    
    // write a cookie
    context.getResponse().cookie("whiskey", "make-it-rye");
    // read a cookie
    String username = context.getRequest().oneCookie("username");
     
    // info:
    https://ratpack.io/manual/current/http.html#cookies
    
    */
}
