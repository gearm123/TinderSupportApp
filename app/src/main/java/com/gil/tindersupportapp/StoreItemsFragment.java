package com.gil.tindersupportapp;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import com.gil.tindersupportapp.adapters.AppsAdapter;
import com.gil.tindersupportapp.dialogs.AppDetailsDialog;
import com.gil.tindersupportapp.interfaces.OnAppClickListener;
import com.gil.tindersupportapp.model.MatchData;
import com.gil.tindersupportapp.widgets.SpacesItemDecoration;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 */
public class StoreItemsFragment extends Fragment implements OnAppClickListener, AppDetailsDialog.AppActionsClickListener {
    private View mRoot;
    private RecyclerView mRecycler;
    public static AppsAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    public static List<MatchData> mData = new ArrayList<>();
    private List<MatchData> currentDataArray = new ArrayList<>();
    public StoreItemsFragment() {

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.v("MGCarAppStore","on create view");
        mRoot = inflater.inflate(R.layout.fragment_store_items, container, false);
        mRecycler = mRoot.findViewById(R.id.appsRecycler);
        mRecycler.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new StaggeredGridLayoutManager(2, 1);
        mRecycler.setLayoutManager(mLayoutManager);
        SpacesItemDecoration itemDecoration = new SpacesItemDecoration((int) getContext().getResources().getDimension(R.dimen.space_between_cards));
        mRecycler.addItemDecoration(itemDecoration);
        dispaydata();

        ((SimpleItemAnimator) mRecycler.getItemAnimator()).setSupportsChangeAnimations(false);
        return mRoot;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume(){
        super.onResume();
        Log.v("MGCarAppStore","frag resume");
    }

    @Override
    public void onStop() {
        super.onStop();
    }


    private void dispaydata() {
        mData.clear(); //TODO: improve this , editing existing items
        Log.v("MGCarAppStore","here at disp");
        MatchData tmpObject=null;
        SharedPreferences prefs = getContext().getSharedPreferences("tinder_support", MODE_PRIVATE);
        Gson gson=new Gson();
        String currentApp = prefs.getString("current_app", null);
        currentDataArray=gson.fromJson(currentApp,List.class);
        if(currentDataArray!= null) {
            for (int i = 0; i < currentDataArray.size(); i++) {
                try {
                    tmpObject = currentDataArray.get(i);
                    MatchData matchData = new MatchData(tmpObject.getRealNameame(), tmpObject.getDescription(), tmpObject.getIconUrl(), tmpObject.getPhoneSavedName(),
                            tmpObject.getFacebookLink(), tmpObject.getInstagramLink(), tmpObject.getLocation(), tmpObject.getSeeking());

                    mData.add(matchData);
                } catch (Exception e) {
                    Log.v("MGCarAppStore", "the exception " + e);

                }
            }
        }
        Log.v("MGCarAppStore","setting elements");
        mAdapter = new AppsAdapter(mData, this);
        mRecycler.setAdapter(mAdapter);

        ((MainActivity)getActivity()).setAdapter(mAdapter);


    }

    @Override
    public void onItemClick(MatchData item) {
        AppDetailsDialog dialog = AppDetailsDialog.build(item, this,getContext());

        dialog.show(getFragmentManager(), "appDetails");
    }

    @Override
    public void onItemActionClick(MatchData item) {
        AppDetailsDialog dialog = AppDetailsDialog.build(item, this,getContext());
        dialog.show(getFragmentManager(), "appDetails");
    }


    private ArrayList<String> getAppParameters(JSONObject tmpObject)
    {
        ArrayList<String> appParameters=new ArrayList<String>();
        try {
            appParameters.add(tmpObject.getString("app_name"));
            appParameters.add(tmpObject.getString("description"));
            appParameters.add(tmpObject.getString("icon_url"));
            appParameters.add(tmpObject.getString("tooltip"));
            appParameters.add(tmpObject.getString("TitleSmall"));
            appParameters.add(tmpObject.getString("Action"));
            appParameters.add(tmpObject.getString("Cause"));
            appParameters.add(tmpObject.getString("Color"));

        }

        catch(Exception e)
        {
            Log.v("MGCarAppStore", "exception recevieng app parameters "+e);
            return null;
        }

        return appParameters;
    }


    @Override
    public void onInstallClicked(MatchData matchData) {

    }

    @Override
    public void onRemoveClicked(MatchData matchData) {

    }
}
