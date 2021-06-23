/*
 * Description: Describes the wallet for an end-user to a given coin
 * License: Apache-2.0
 * Copyright: x-file.xyz
 */
package coins;

import java.util.ArrayList;

/**
 * Date: 2021-06-14
 * Place: Zwingenberg, Germany
 * @author brito
 */
public class CoinWallet {
    
    // coins available (not locked)
    private long valueSpot;
    
    // transactions such as buy/sell/transfer
    private ArrayList<CoinTransaction> transactions = new ArrayList();
    
}
