package paul.labat.com.traveldiary.TextEditor;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import paul.labat.com.traveldiary.R;

public class TextEditorFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.text_editor_layout, container, false);
        setHasOptionsMenu(true);

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
                Toast.makeText(getContext(),"save action", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.action_preview:
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_content, new TextEditorPreviewFragment()).commit();
            default:
        }

        return super.onOptionsItemSelected(item);
    }
}
