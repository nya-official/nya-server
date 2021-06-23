/*
 * Description: The coins available
 * License: Apache-2.0
 * Copyright: x-file.xyz
 */
package coins;

import static base.Log.log;
import coins.nya.CoinNYA;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Date: 2021-06-14
 * Place: Zwingenberg, Germany
 * @author brito
 */
public class CoinManager {

    private final ArrayList<CoinTemplate> list;

    public CoinManager() {
        this.list = new ArrayList();
    }
    
    public void start(){
        // boot up the exchange
        log("Starting the exchange");
        log("Added coin: " + "NYA");
        list.add(new CoinNYA());
    }
    
}
