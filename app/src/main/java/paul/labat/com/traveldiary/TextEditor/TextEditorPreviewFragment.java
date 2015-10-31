package paul.labat.com.traveldiary.TextEditor;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.commonsware.cwac.anddown.AndDown;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import paul.labat.com.traveldiary.R;

public class TextEditorPreviewFragment extends Fragment{

    private String rawString;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.text_editor_preview_layout, container, false);
        setHasOptionsMenu(true);

        TextView textView = (TextView)view.findViewById(R.id.markdow_textview);
        rawString = getArguments().getString("editText");

        AndDown converter = new AndDown();

        String cooked = converter.markdownToHtml(rawString);

        CharSequence cs = Html.fromHtml(cooked);
        textView.setText(cs);

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
                Fragment fragment = new TextEditorFragment();
                fragment.setArguments(bundle);

                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_content, fragment).commit();
            default:
        }

        return super.onOptionsItemSelected(item);
    }


}
