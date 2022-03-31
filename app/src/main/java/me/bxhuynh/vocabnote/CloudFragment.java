package me.bxhuynh.vocabnote;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class CloudFragment extends Fragment {
    Button btnSaveToCloud, btnImportFromCloud;
    FirebaseDBHandler firebaseDBHandler;
    public CloudFragment(){

    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cloud, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btnSaveToCloud = view.findViewById(R.id.btnSaveToCloud);
        btnImportFromCloud = view.findViewById(R.id.btnImportFromCloud);

        firebaseDBHandler = new FirebaseDBHandler(getActivity());

        btnSaveToCloud.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String key = firebaseDBHandler.uploadToCloud(getActivity());
                // on success and get backup
                AlertDialog dialog = new AlertDialog.Builder(getActivity()).create();
                dialog.setTitle("Save to cloud");

                LayoutInflater inflater = requireActivity().getLayoutInflater();
                View dialogView = inflater.inflate(R.layout.dialog_success_save_to_cloud, null);
                TextView backupKey = dialogView.findViewById(R.id.tv_backupKey);
                backupKey.setText(key);

                backupKey.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ClipboardManager clipboard = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
                        ClipData clip = ClipData.newPlainText("Back-up Key", key);
                        clipboard.setPrimaryClip(clip);

                        Toast.makeText(getActivity(), "Copied to clipboard", Toast.LENGTH_SHORT).show();
                    }
                });

                dialog.setView(dialogView);
                dialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();;
                    }
                });
                dialog.show();
            }
        });

        btnImportFromCloud.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog dialog = new AlertDialog.Builder(getActivity()).create();
                dialog.setTitle("Import from cloud");
                LayoutInflater inflater = requireActivity().getLayoutInflater();
                View dialogView = inflater.inflate(R.layout.dialog_import_from_cloud, null);
                EditText etBackupKey = dialogView.findViewById(R.id.editTextBackupKey);
                Button cancel = dialogView.findViewById(R.id.btnCancelImport);
                Button confirm = dialogView.findViewById(R.id.btnConfirmImport);

                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });

                confirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String bk = etBackupKey.getText().toString();
                        if (bk.equalsIgnoreCase("")){
                            Toast.makeText(getActivity(), "You must enter back up key to import.", Toast.LENGTH_LONG).show();
                            etBackupKey.setError("Required");
                        }
                        else {
                            firebaseDBHandler.importFromCloud(getActivity(), bk);
                            //import success
                            Toast.makeText(getActivity(), "Import from cloud success.", Toast.LENGTH_LONG).show();
                            dialog.dismiss();;
                        }
                    }
                });

                dialog.setView(dialogView);
                dialog.show();

            }
        });
    }

}