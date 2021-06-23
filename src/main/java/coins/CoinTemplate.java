/*
 * Description: Template for new coins
 * License: Apache-2.0
 * Copyright: x-file.xyz
 */
package coins;

/**
 * Date: 2021-06-14
 * Place: Zwingenberg, Germany
 * @author brito
 */
public abstract class CoinTemplate {

    /**
     * Initiate the databases and transactions
     */
    public CoinTemplate() {
        
    }

    
    public abstract String getId();
    public abstract String getDescription();
    
}
