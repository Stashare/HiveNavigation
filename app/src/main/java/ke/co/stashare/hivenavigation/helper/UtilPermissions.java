package ke.co.stashare.hivenavigation.helper;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;

/**
 * Created by Ken Wainaina on 16/03/2017.
 */

public class UtilPermissions {

    public static boolean hasPermissions(Context context, String[] allPermissionNeeded)
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                && context != null && allPermissionNeeded != null)
            for (String permission : allPermissionNeeded)
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED)
                    return false;
        return true;
    }

}
