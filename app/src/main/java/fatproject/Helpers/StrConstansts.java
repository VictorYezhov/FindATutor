package fatproject.Helpers;

/**
 * Created by Victor on 23.02.2018.
 */

public enum StrConstansts {

    PATHTOPHOTO("pathToPhoto"), CURRENTUSER("currentUser");


    private String parameter;

    StrConstansts(String parameter) {
        this.parameter = parameter;
    }

    public String getParameter() {
        return parameter;
    }

    public void setParameter(String parameter) {
        this.parameter = parameter;
    }
}
