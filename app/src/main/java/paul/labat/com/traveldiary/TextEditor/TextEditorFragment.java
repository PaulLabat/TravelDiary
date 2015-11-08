package paul.labat.com.traveldiary.TextEditor;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import paul.labat.com.traveldiary.R;
import paul.labat.com.traveldiary.Util.DataModel;
import paul.labat.com.traveldiary.Util.FileManager;

public class TextEditorFragment extends Fragment {

    private EditText editText;
    private ImageButton formatBold, formatItalic, formatListBulleted, formatListNumered, formatAddPhoto, formatAddLocation;


    @Nullable
    private String fileName;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.text_editor_layout, container, false);
        setHasOptionsMenu(true);
        editText = (EditText)view.findViewById(R.id.edit_text_entry);
        if(getArguments() != null && getArguments().getString("editText") != null) {
            editText.setText(getArguments().getString("editText"));
        }

        fileName = getArguments() == null ? null : getArguments().getString("fileName");


        formatBold = (ImageButton)view.findViewById(R.id.format_bold);
        formatItalic = (ImageButton)view.findViewById(R.id.format_italic);
        formatListBulleted = (ImageButton)view.findViewById(R.id.format_list_bulleted);
        formatListNumered = (ImageButton)view.findViewById(R.id.format_list_numered);
        formatAddPhoto = (ImageButton)view.findViewById(R.id.format_add_photo);
        formatAddLocation = (ImageButton)view.findViewById(R.id.format_add_location);

        formatBold.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = editText.getText()+ " ** ";
                editText.setText(s);
                editText.setSelection(editText.getText().length() - 2);

            }
        });

        formatItalic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = editText.getText()+ " **** ";
                editText.setText(s);
                editText.setSelection(editText.getText().length() - 3);
            }
        });

        formatListBulleted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = editText.getText()+ "- ";
                editText.setText(s);
                editText.setSelection(editText.getText().length());
            }
        });

        formatListNumered.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = editText.getText()+ "1. ";
                editText.setText(s);
                editText.setSelection(editText.getText().length());
            }
        });

        return view;
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_new_entry, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.action_save:

                DataModel dataModel = new DataModel();
                dataModel.setTextData(editText.getText().toString());

                FileManager.getInstance().saveEntry(getActivity(), fileName, dataModel);

                Toast.makeText(getContext(), "Saved", Toast.LENGTH_LONG).show();
                getActivity().setResult(TextEditorActivity.CODE_FOR_NEW_ENTRY);
                getActivity().finish();
                return true;
            case R.id.action_preview:
                Fragment fragment = new TextEditorPreviewFragment();
                Bundle bundle = new Bundle();
                bundle.putString("editText", editText.getText().toString());
                bundle.putString("fileName", fileName);
                fragment.setArguments(bundle);


                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_content_editor, fragment).commit();
            default:
        }

        return super.onOptionsItemSelected(item);
    }

}
