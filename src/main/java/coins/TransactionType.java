/*
 * Description: Type of transactions
 * License: Apache-2.0
 * Copyright: x-file.xyz
 */
package coins;

/**
 * Date: 2021-06-14
 * Place: Zwingenberg, Germany
 * @author brito
 */
public enum TransactionType {
    buy, // offer to buy some coin
    sell, // offer to sell some coin
    transfer // transfer this coin to an external wallet
}
