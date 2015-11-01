package paul.labat.com.traveldiary.TextEditor;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.commonsware.cwac.anddown.AndDown;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import paul.labat.com.traveldiary.R;

public class TextEditorPreviewFragment extends Fragment{

    private String rawString;
    private String fileName;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.text_editor_preview_layout, container, false);
        setHasOptionsMenu(true);
        TextView textView = (TextView)view.findViewById(R.id.markdow_textview);

        if(getArguments() != null){
            if( getArguments().getString("editText") != null) {
                rawString = getArguments().getString("editText");
            }else if(getArguments().getString("FileName") != null){
                fileName = getArguments().getString("FileName");
                FileInputStream inputStream;
                String tmp="";

                try {
                    inputStream = getContext().openFileInput(fileName);
                    BufferedReader br = new BufferedReader(new InputStreamReader(inputStream,"utf8"));
                    String str;
                    while ((str = br.readLine()) != null) {
                        tmp += str;
                    }
                    inputStream.close();
                }catch (IOException e) {
                    e.printStackTrace();
                }

                JSONObject jsonObject;
                try {
                    jsonObject = new JSONObject(tmp);
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.e(getClass().getName(), "Could not parse json file: " + getArguments().getString("FileName"));
                    jsonObject = null;
                }

                if(jsonObject != null){
                    try {
                        JSONObject dataObject = jsonObject.getJSONObject("Data");
                        rawString = dataObject.getString("Text");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }
        }


        if (rawString != null){
            AndDown converter = new AndDown();

            String cooked = converter.markdownToHtml(rawString);

            CharSequence cs = Html.fromHtml(cooked);
            textView.setText(cs);
        }

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_preview_entry, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.action_save_preview:
                Toast.makeText(getContext(), "save action", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.action_edit:

                Bundle bundle = new Bundle();
                bundle.putString("editText", rawString);
                bundle.putString("FileName", fileName);
                Fragment fragment = new TextEditorFragment();
                fragment.setArguments(bundle);

                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_content_editor, fragment).commit();
            default:
        }

        return super.onOptionsItemSelected(item);
    }


}
