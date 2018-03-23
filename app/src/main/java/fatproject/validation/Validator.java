package fatproject.validation;

import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by Victor on 31.12.2017.
 */

public interface Validator {
    boolean validateActivity(AppCompatActivity activity);
    boolean validateFragment(View fragment);
}
