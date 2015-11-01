package paul.labat.com.traveldiary.TextEditor;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.FileOutputStream;
import java.util.Calendar;
import java.util.TimeZone;
import java.util.UUID;
import paul.labat.com.traveldiary.R;

public class TextEditorFragment extends Fragment {

    private EditText editText;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.text_editor_layout, container, false);
        setHasOptionsMenu(true);
        editText = (EditText)view.findViewById(R.id.edit_text_entry);
        if(getArguments() != null && getArguments().getString("editText") != null) {
            editText.setText(getArguments().getString("editText"));
        }
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
                saveEntry();
                Toast.makeText(getContext(), "Saved", Toast.LENGTH_LONG).show();
                getActivity().setResult(TextEditorActivity.CODE_FOR_NEW_ENTRY);
                getActivity().finish();
                return true;
            case R.id.action_preview:
                Fragment fragment = new TextEditorPreviewFragment();
                Bundle bundle = new Bundle();
                bundle.putString("editText", editText.getText().toString());
                fragment.setArguments(bundle);


                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_content_editor, fragment).commit();
            default:
        }

        return super.onOptionsItemSelected(item);
    }


    private void saveEntry(){
        String fileName = UUID.randomUUID().toString();

        JSONObject newEntry = new JSONObject();
        JSONObject infosObject = new JSONObject();
        JSONObject textObject = new JSONObject();
        JSONObject gpsOjbect = new JSONObject();
        JSONObject weatherOjbect = new JSONObject();
        try {

            //administrative
            infosObject.put("FileName", fileName);
            infosObject.put("TimeZone", Calendar.getInstance().getTimeZone());
            infosObject.put("Date", Calendar.getInstance(TimeZone.getDefault()).getTimeInMillis());


            //Text
            textObject.put("Text", editText.getText());


            //GPS position
            gpsOjbect.put("Country","France");
            gpsOjbect.put("City", "Orlean");
            gpsOjbect.put("Street", "5 Rue du Maréchal de Lattre de Tassigny");
            gpsOjbect.put("Longitude", 1.8951917);
            gpsOjbect.put("Latitude", 47.9195645);

            //weather


            newEntry.put("System", infosObject);
            newEntry.put("Data", textObject);
            newEntry.put("Location", gpsOjbect);
            newEntry.put("Weather", weatherOjbect);


        } catch (JSONException e) {
            e.printStackTrace();
        }

        FileOutputStream outputStream;

        try {
            outputStream = getActivity().openFileOutput(fileName+".json", Context.MODE_PRIVATE);
            outputStream.write(newEntry.toString().getBytes());
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getContext(), "An error occured while saving the entry", Toast.LENGTH_LONG).show();
        }

    }


}
