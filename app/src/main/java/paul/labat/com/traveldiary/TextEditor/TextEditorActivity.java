package paul.labat.com.traveldiary.TextEditor;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import paul.labat.com.traveldiary.R;


public class TextEditorActivity extends AppCompatActivity {
public static int CODE_FOR_NEW_ENTRY = 42;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.text_editor_activity);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();

        Fragment fragment;

        if (intent != null){

            if(intent.getAction().equalsIgnoreCase("NewEntry")){
                fragment = new TextEditorFragment();
            }else if(intent.getAction().equalsIgnoreCase("editEntry")){
                fragment = new TextEditorPreviewFragment();
                Bundle bundle = new Bundle();
                bundle.putString("fileName", intent.getStringExtra("fileName"));
                fragment.setArguments(bundle);
            }else{
                fragment = new TextEditorFragment();
            }
        }else{
            fragment = new TextEditorFragment();
        }
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_content_editor, fragment).commit();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        setResult(RESULT_CANCELED);
        finish();
    }

}
