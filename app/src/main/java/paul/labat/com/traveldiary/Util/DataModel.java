package paul.labat.com.traveldiary.Util;

import android.support.annotation.Nullable;

public class DataModel {
    @Nullable
    public String getTextData() {
        return textData;
    }

    public void setTextData(@Nullable String textData) {
        this.textData = textData;
    }

    @Nullable
    private String textData;


}
