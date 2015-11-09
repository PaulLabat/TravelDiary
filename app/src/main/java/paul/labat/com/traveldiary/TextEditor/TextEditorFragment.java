package paul.labat.com.traveldiary.TextEditor;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

import paul.labat.com.traveldiary.R;
import paul.labat.com.traveldiary.Util.DataModel;
import paul.labat.com.traveldiary.Util.DateTimeModel;
import paul.labat.com.traveldiary.Util.FileManager;

public class TextEditorFragment extends Fragment {

    private EditText editText;
    private ImageButton formatBold, formatItalic, formatListBulleted, formatAddPhoto, formatAddLocation;

    private DatePickerDialog datePicker;
    private TimePickerDialog timePicker;

    @Nullable
    private String fileName;

    private DateTimeModel dateTimeModel = new DateTimeModel();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.text_editor_layout, container, false);
        setHasOptionsMenu(true);
        editText = (EditText)view.findViewById(R.id.edit_text_entry);

        
        if(getArguments() != null){

            if(getArguments().getString("fileName") != null){
                fileName = getArguments().getString("fileName");
            }

            if(getArguments().getString("editText") != null) {
                editText.setText(getArguments().getString("editText"));
            }

            if(getArguments().getInt("year") != 0){
             dateTimeModel.setYear(getArguments().getInt("year"));
             dateTimeModel.setMonth(getArguments().getInt("month"));
             dateTimeModel.setDay(getArguments().getInt("day"));
             dateTimeModel.setHour(getArguments().getInt("hour"));
             dateTimeModel.setMinutes(getArguments().getInt("minute"));
            }
        }


        formatBold = (ImageButton)view.findViewById(R.id.format_bold);
        formatItalic = (ImageButton)view.findViewById(R.id.format_italic);
        formatListBulleted = (ImageButton)view.findViewById(R.id.format_list_bulleted);
        formatAddPhoto = (ImageButton)view.findViewById(R.id.format_add_photo);
        formatAddLocation = (ImageButton)view.findViewById(R.id.format_add_location);

        formatBold.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText.getText().insert(editText.getSelectionStart(), " **** ");
                editText.setSelection(editText.getSelectionStart() - 3);
            }
        });

        formatItalic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText.getText().insert(editText.getSelectionStart(), " ** ");
                editText.setSelection(editText.getSelectionStart() - 2);
            }
        });

        formatListBulleted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText.getText().insert(editText.getSelectionStart(), "- ");
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

                final DataModel dataModel = new DataModel();
                dataModel.setTextData(editText.getText().toString());

                FileManager.getInstance().saveEntry(getActivity(), fileName, dataModel, dateTimeModel);

                Toast.makeText(getContext(), "Saved", Toast.LENGTH_LONG).show();
                getActivity().setResult(TextEditorActivity.CODE_TIMELINE_DATA_CHANGED);
                getActivity().finish();
                return true;
            case R.id.action_preview:
                Fragment fragment = new TextEditorPreviewFragment();
                Bundle bundle = new Bundle();
                bundle.putString("editText", editText.getText().toString());
                bundle.putString("fileName", fileName);
                bundle.putInt("year", dateTimeModel.getYear());
                bundle.putInt("month", dateTimeModel.getMonth());
                bundle.putInt("day", dateTimeModel.getDay());
                bundle.putInt("hour", dateTimeModel.getHour());
                bundle.putInt("minute", dateTimeModel.getMinutes());
                fragment.setArguments(bundle);
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_content_editor, fragment).commit();
                return true;
            case R.id.action_choose_date:
                Calendar calendar = Calendar.getInstance();


                timePicker = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        dateTimeModel.setHour(hourOfDay);
                        dateTimeModel.setMinutes(minute);
                    }
                },dateTimeModel.getHour(), dateTimeModel.getMinutes(), true);

                datePicker = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        dateTimeModel.setYear(year);
                        dateTimeModel.setMonth(monthOfYear);
                        dateTimeModel.setDay(dayOfMonth);
                        timePicker.show();
                    }
                }, dateTimeModel.getYear(), dateTimeModel.getMonth(), dateTimeModel.getDay());



                datePicker.show();



                return true;


            case R.id.action_cancel:
                getActivity().setResult(Activity.RESULT_CANCELED);
                getActivity().finish();
            default:
        }

        return super.onOptionsItemSelected(item);
    }

}
