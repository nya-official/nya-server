/*
 * Description: Core functions and variables
 * License: Apache-2.0
 * Copyright: x-file.xyz
 */
package base;

import genesis.Genesis;
import static base.Log.log;
import channel.ChannelManager;
import database.ConnectIPFS;
import coins.CoinManager;
import console.Commands;
import console.ConsoleWeb;
import user.Users;
import java.io.File;

/**
 * Date: 2021-06-14
 * Place: Zwingenberg, Germany
 * @author brito
 */
public class core {
    
    // definitions
    public static String 
        dbFolderUsers = "users",
        dbNameUsers = "users.db",
        dbUserJSON = "user.json",
        dbUsersJSON = "users.json",
        dbUserKey = "c",
        dbUserPassword = "p",
        dateFormat = "yyyy-MM-dd'_'HH:mm:ss";
    
    public static final File 
        folderRoot = new File("."),
        folderDB = new File(folderRoot, "db"),
        folderTest = new File(folderRoot, "test"),
        fileGenesis = new File("genesis.json");

    
    // global variables
    public static Config config;
    public static Genesis genesis;
    public static CoinManager coinManager = new CoinManager();
    public static ChannelManager channelManager = new ChannelManager();
    public static Users users;
    public static ConnectIPFS ipfs;
    public static Commands commands = new Commands();
    
    public static String 
            version = "1.0.0";

    static void start() {
        // print the logo
        System.out.println(Logo.get() 
                + " " 
                + version
                + "\n" 
                );
        
        // start the config
        startConfig();
        
        
        // initiate the coins
        coinManager.start();
        
        //String result = ipfs.putContentAsText("Hello World 123!");
//        String result = ipfs.getContentAsString("QmNacaCtPPduuiRvTdrEtxrBqHxcPBsN3r28Ng3S2a9Wuk");
//        log("Result: " + result);

        // start the command line 
        startCommandLine();  
    }

    /**
     * Close all the databases and related services
     */
    public static void close() {
        // close the user database
        //dbUsers.close();
    }

    private static void startConfig() {
        // read the genesis from disk or use default values
        if(utils.files.isValidFile(fileGenesis)){
            genesis = Genesis.jsonImportFile(fileGenesis);
        }
        // in case it did not work, just create a new one
        if(genesis == null){
            genesis = new Genesis();
            // save it to disk like this
            String output = genesis.jsonExport();
            utils.files.SaveStringToFile(fileGenesis, output);
        }
        
        // connect to the IPFS services
        log("Connecting to InterPlanetary File System..");
        ipfs = new ConnectIPFS();
        ipfs.start();
        if(ipfs.canBeUsed()){
            log("IPFS is connected");
        }else{
            log("Error: IPFS is NOT connected");
            log("Please check your network connection or firewalls");
            return;
        }
        
        // check if the genesis has a valid config id
        String id = genesis.getGenesis();
        String text = core.ipfs.getContentAsString(id);
        if(text == null){
            log("ERROR core-111: No valid genesis block found");
            log("Creating a new genesis");
            config = new Config();
        }else{
            // load up the config
            config = Config.jsonImport(text);
        }
        
        if(config == null){
            log("ERROR core-127: Genesis config is invalid, using a default one");
            config = new Config();
        }
        
        // start up
        config.setup();
        
        // save this on IPFS
        config.saveToIPFS();
    }

    /**
     * Provides the command line for interacting with nya
     */
    private static void startCommandLine() {
            ConsoleWeb.main(null);
    }
    

}
