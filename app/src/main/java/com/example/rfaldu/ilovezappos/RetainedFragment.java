package com.example.rfaldu.ilovezappos;

/**
 * Created by rfaldu on 2/10/2017.
 */
import android.app.Fragment;
import android.os.Bundle;

public class RetainedFragment extends Fragment{
    public DisplayItem data;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    public void setData(DisplayItem data){
        this.data = data;
    }

    public DisplayItem getData(){
        return data;
    }
}
