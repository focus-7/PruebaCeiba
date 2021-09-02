package co.com.ceiba.mobile.pruebadeingreso.utilities;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.View;

import co.com.ceiba.mobile.pruebadeingreso.R;

public class ProgressDialogLoading {
    private final Activity activity;
    private AlertDialog dialog;

    public ProgressDialogLoading(Activity activity) {
        this.activity = activity;
        iniciarDialogoProgreso();
    }

    private void iniciarDialogoProgreso() {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);

        View view = activity.getLayoutInflater().inflate(R.layout.progress_dialog, null);

        builder.setView(view);
        builder.setCancelable(false);

        dialog = builder.create();
        dialog.show();
    }

    public void closeDialog() {
        dialog.dismiss();
    }

    public void openDialog() {
        dialog.show();
    }
}
