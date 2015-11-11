package paul.labat.com.traveldiary.TextEditor;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
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

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import paul.labat.com.traveldiary.R;
import paul.labat.com.traveldiary.Util.DataModel;
import paul.labat.com.traveldiary.Util.DateTimeModel;
import paul.labat.com.traveldiary.Util.FileManager;
import paul.labat.com.traveldiary.Util.LocationModel;

public class TextEditorFragment extends Fragment {

    private EditText editText;
    private ImageButton formatBold, formatItalic, formatListBulleted, formatAddPhoto, formatAddLocation;

    private DatePickerDialog datePicker;
    private TimePickerDialog timePicker;

    @Nullable
    private String fileName;

    private LocationModel locationModel = new LocationModel();

    private final int REQUEST_CODE_LOCATION = 1;


    private LocationListener listener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            locationModel.setLocation(location);
            Geocoder geocoder = new Geocoder(getContext(), Locale.getDefault());
            try{
                Address address = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1).get(0);
                locationModel.setCountryName(address.getCountryName());
                locationModel.setLocality(address.getLocality());
                locationModel.setStreet(address.getFeatureName() +" "+ address.getThoroughfare());

                Log.d("geocoder reverse", address.toString());
            }catch (IOException e){
                Log.e(getClass().getName(), "IOException : " + e);
            }

        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {
        }

        @Override
        public void onProviderDisabled(String provider) {
        }
    };

    private LocationManager locationManager;


    private DateTimeModel dateTimeModel = new DateTimeModel();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.text_editor_layout, container, false);
        setHasOptionsMenu(true);
        editText = (EditText) view.findViewById(R.id.edit_text_entry);
        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);


        if (getArguments() != null) {

            if (getArguments().getString("fileName") != null) {
                fileName = getArguments().getString("fileName");
            }

            if (getArguments().getString("editText") != null) {
                editText.setText(getArguments().getString("editText"));
            }

            if (getArguments().getInt("year") != 0) {
                dateTimeModel.setYear(getArguments().getInt("year"));
                dateTimeModel.setMonth(getArguments().getInt("month"));
                dateTimeModel.setDay(getArguments().getInt("day"));
                dateTimeModel.setHour(getArguments().getInt("hour"));
                dateTimeModel.setMinutes(getArguments().getInt("minute"));
            }
        }


        formatBold = (ImageButton) view.findViewById(R.id.format_bold);
        formatItalic = (ImageButton) view.findViewById(R.id.format_italic);
        formatListBulleted = (ImageButton) view.findViewById(R.id.format_list_bulleted);
        formatAddPhoto = (ImageButton) view.findViewById(R.id.format_add_photo);
        formatAddLocation = (ImageButton) view.findViewById(R.id.format_add_location);

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

        formatAddLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                        ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE_LOCATION);
                    return;
                }
                if(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
                    locationManager.requestSingleUpdate(LocationManager.GPS_PROVIDER, listener, null);
                }else if(locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)){
                    locationManager.requestSingleUpdate(LocationManager.NETWORK_PROVIDER, listener, null);
                }

            }
        });

        return view;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_LOCATION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                            && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }
                    if(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
                        locationManager.requestSingleUpdate(LocationManager.GPS_PROVIDER, listener, null);
                    }else if(locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)){
                        locationManager.requestSingleUpdate(LocationManager.NETWORK_PROVIDER, listener, null);
                    }

                }
                break;
            default:
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_new_entry, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        locationManager.removeUpdates(listener);


        switch (item.getItemId()) {
            case R.id.action_save:

                final DataModel dataModel = new DataModel();
                dataModel.setTextData(editText.getText().toString());

                FileManager.getInstance().saveEntry(getActivity(), fileName, dataModel, dateTimeModel, locationModel);

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
                if(locationModel.getLocation() != null) {
                    bundle.putDouble("latitude", locationModel.getLocation().getLatitude());
                    bundle.putDouble("longitude", locationModel.getLocation().getLongitude());
                }
                if(locationModel.getCountryName() != null){
                    bundle.putString("street", locationModel.getStreet());
                    bundle.putString("city", locationModel.getLocality());
                    bundle.putString("country", locationModel.getCountryName());
                }
                fragment.setArguments(bundle);
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_content_editor, fragment).commit();
                return true;
            case R.id.action_choose_date:
                timePicker = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        dateTimeModel.setHour(hourOfDay);
                        dateTimeModel.setMinutes(minute);
                    }
                }, dateTimeModel.getHour(), dateTimeModel.getMinutes(), true);

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

    @Override
    public void onPause() {
        super.onPause();
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        locationManager.removeUpdates(listener);
    }
}
