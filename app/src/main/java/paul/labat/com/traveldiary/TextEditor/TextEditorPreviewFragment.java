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
import android.webkit.WebView;
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
import paul.labat.com.traveldiary.Util.DataModel;
import paul.labat.com.traveldiary.Util.FileManager;

public class TextEditorPreviewFragment extends Fragment{

    private String rawString;
    private String fileName;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.text_editor_preview_layout, container, false);
        setHasOptionsMenu(true);
        WebView textView = (WebView)view.findViewById(R.id.markdow_textview);

        if(getArguments() != null){
            if(getArguments().getString("fileName") != null){
                fileName = getArguments().getString("fileName");

                if( getArguments().getString("editText") != null) {
                    rawString = getArguments().getString("editText");
                }else {
                    FileInputStream inputStream;
                    String tmp = "";

                    try {
                        inputStream = getContext().openFileInput(fileName);
                        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream, "utf8"));
                        String str;
                        while ((str = br.readLine()) != null) {
                            tmp += str;
                        }
                        inputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    JSONObject jsonObject;
                    try {
                        jsonObject = new JSONObject(tmp);
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Log.e(getClass().getName(), "Could not parse json file: " + getArguments().getString("fileName"));
                        jsonObject = null;
                    }

                    if (jsonObject != null) {
                        try {
                            JSONObject dataObject = jsonObject.getJSONObject("Data");
                            rawString = dataObject.getString("Text");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }

            }else{
                if( getArguments().getString("editText") != null) {
                    rawString = getArguments().getString("editText");
                }
            }
        }


        if (rawString != null){
            AndDown converter = new AndDown();

            String cooked = converter.markdownToHtml(rawString);

            /*CharSequence cs = Html.fromHtml(cooked);
            textView.setText(cs);*/

            textView.loadData(cooked, "text/html", "UTF-8");

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
                Toast.makeText(getContext(), "Saved", Toast.LENGTH_SHORT).show();
                DataModel dataModel = new DataModel();
                dataModel.setTextData(rawString);
                FileManager.getInstance().saveEntry(getActivity(), fileName, dataModel);
                getActivity().setResult(TextEditorActivity.CODE_FOR_NEW_ENTRY);
                getActivity().finish();
                return true;
            case R.id.action_edit:

                Bundle bundle = new Bundle();
                bundle.putString("editText", rawString);
                bundle.putString("fileName", fileName);
                Fragment fragment = new TextEditorFragment();
                fragment.setArguments(bundle);

                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_content_editor, fragment).commit();
            default:
        }

        return super.onOptionsItemSelected(item);
    }


}
