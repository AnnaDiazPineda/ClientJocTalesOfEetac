package dsa.eetac.upc.edu.clientjoc.inputOutput.Response;

import com.google.gson.annotations.SerializedName;

/**
 * Created by anita on 04/12/2017.
 */

public class LoginBody {
    @SerializedName("mail")

    /*para aclararle a Gson cuál será el nombre exacto del atributo JSON que será interpretado.*/
    private  String mailUser;
    private  String passwordUser;

    public LoginBody(String mailUser, String passwordUser) {
        this.mailUser = mailUser;
        this.passwordUser = passwordUser;
    }

    public String getUserId() {
        return mailUser;
    }

    public void setUserId(String mailUser) {
        this.mailUser = mailUser;
    }

    public String getPassword() {
        return passwordUser;
    }

    public void setPassword(String password) {
        this.passwordUser = password;
    }

}
