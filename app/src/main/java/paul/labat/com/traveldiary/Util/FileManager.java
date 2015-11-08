package paul.labat.com.traveldiary.Util;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Calendar;
import java.util.TimeZone;
import java.util.UUID;

public class FileManager {
    private static FileManager ourInstance = new FileManager();

    public static FileManager getInstance() {
        return ourInstance;
    }

    private FileManager() {
    }

    public void saveEntry(@NonNull Activity activity,@Nullable String fileName, @NonNull DataModel dataModel) {

        JSONObject newEntry = new JSONObject();
        JSONObject infosObject = new JSONObject();
        JSONObject textObject = new JSONObject();
        JSONObject gpsOjbect = new JSONObject();
        JSONObject weatherOjbect = new JSONObject();

        if (fileName == null) {
            fileName = UUID.randomUUID().toString();
            try {

                //administrative
                infosObject.put("fileName", fileName);
                infosObject.put("TimeZone", Calendar.getInstance().getTimeZone());
                infosObject.put("Date", Calendar.getInstance(TimeZone.getDefault()).getTimeInMillis());


                //Text
                textObject.put("Text", dataModel.getTextData() == null ? "" : dataModel.getTextData());


                //GPS position
                gpsOjbect.put("Country", "France");
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
                outputStream = activity.openFileOutput(fileName, Context.MODE_PRIVATE);
                outputStream.write(newEntry.toString().getBytes());
                outputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(activity, "An error occured while saving the entry", Toast.LENGTH_LONG).show();
            }

        } else {

            FileInputStream inputStream;
            String tmp="";

            try {
                inputStream = activity.openFileInput(fileName);
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
                Log.e(getClass().getName(), "Could not parse json file: " + fileName);
                jsonObject = null;
            }


            if (jsonObject != null){

                try {

                    //administrative
                    JSONObject tmpObject = jsonObject.getJSONObject("System");
                    infosObject.put("fileName", fileName);
                    infosObject.put("TimeZone", tmpObject.getString("TimeZone"));
                    infosObject.put("Date", tmpObject.getLong("Date"));


                    //Text
                    textObject.put("Text", dataModel.getTextData());


                    //GPS position
                    gpsOjbect.put("Country", "France");
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
                    outputStream = activity.openFileOutput(fileName, Context.MODE_PRIVATE);
                    outputStream.write(newEntry.toString().getBytes());
                    outputStream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(activity, "An error occured while saving the entry", Toast.LENGTH_LONG).show();
                }


            }


        }
    }


}
