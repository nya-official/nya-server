/*
 * Description: Adds the NYA coin
 * License: Apache-2.0
 * Copyright: x-file.xyz
 */
package coins.nya;

import coins.CoinTemplate;

/**
 * Date: 2021-06-14
 * Place: Zwingenberg, Germany
 * @author brito
 */
public class CoinNYA extends CoinTemplate{

    
    public CoinNYA() {
        // start reading the respective database
        
    }

    
    
    @Override
    public String getId() {
        return "NYA";
    }

    @Override
    public String getDescription() {
        return "Not Your Average cat coin";
    }
    
}
