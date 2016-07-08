package me.czmc.widgetdemo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class TagFragment extends Fragment{

    public TagFragment() {
    }

    public static TagFragment newInstance(String title) {
        TagFragment fragment = new TagFragment();
        Bundle bundle = new Bundle();
        bundle.putString("TITLE",title);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        String title =  getArguments().getString("TITLE");
        View rootView = inflater.inflate(R.layout.fragment_tag, container, false);
        ((TextView)rootView.findViewById(R.id.tv_content)).setText(title);
        return rootView;
    }
}
