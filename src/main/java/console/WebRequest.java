/*
 * Description: Handles a web request from a user
 * License: Apache-2.0
 * Copyright: x-file.xyz
 */
package console;

import java.io.File;
import ratpack.handling.Context;

/**
 * Date: 2021-06-25
 * Place: Zwingenberg, Germany
 * @author brito
 */
public class WebRequest {

    private final Context context;
    
    public WebRequest(Context ctx) {
        this.context = ctx;
    }
    
    public void process(){
        context.getResponse().status(200);
        processPath(context, context.getRequest().getPath());
    }

    public Context getContext() {
        return context;
    }

    /**
     * Handle the path request
     * @param ctx
     * @param path 
     */
    private void processPath(Context ctx, String path) {
        
        // nothing was done
        if(path.isEmpty()){
            processHomepage(ctx);
            return;
        }
        
        // no path means root request
        if(path.contains("/") == false){
            processRootRequest(ctx, path);
            return;
        }
        
        // we have something to break apart
        processMultipleRequest(ctx, path);
        
        
        ctx.render("--> " + ctx.getRequest().getPath());
    }

    /**
     * Show the homepage
     * @param context 
     */
    private void processHomepage(Context context) {
        File folder = new File("html-public");
        File file = new File(folder, "console.html");
        // send the file over
        context.getResponse().sendFile(file.toPath());
    }

    private void processRootRequest(Context context, String path) {
        context.render("--> " + context.getRequest().getPath());
    }

    private void processMultipleRequest(Context context, String path) {
        processHomepage(context);
    }

    
    
}
