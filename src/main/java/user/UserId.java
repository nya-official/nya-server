/*
 * Description: Only include the specific ID for an user
 * License: Apache-2.0
 * Copyright: x-file.xyz
 */
package user;

import com.google.gson.annotations.Expose;

/**
 * Date: 2021-06-21
 * Place: Zwingenberg, Germany
 * @author brito
 */
public class UserId {

    @Expose
    private final String idSHA1, idIPFS;

    public UserId(String idSHA, String idIPFS) {
        this.idSHA1 = idSHA;
        this.idIPFS = idIPFS;
    }

    public String getIdSHA1() {
        return idSHA1;
    }

    public String getIdIPFS() {
        return idIPFS;
    }
    
    @Override
    public String toString(){
        return idSHA1;
    }
    
}
