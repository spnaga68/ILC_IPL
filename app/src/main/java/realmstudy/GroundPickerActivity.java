
package realmstudy;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import javax.inject.Inject;

import io.realm.Realm;
import realmstudy.adapter.GroundListAdapter;
import realmstudy.data.CommanData;
import realmstudy.data.RealmObjectData.Ground;
import realmstudy.fragments.DialogFragment.NewPlayerDialog;
import realmstudy.fragments.DialogFragment.NewTeamDialog;
import realmstudy.interfaces.DialogInterface;
import realmstudy.interfaces.ItemClickInterface;
import tourguide.tourguide.Overlay;
import tourguide.tourguide.Pointer;
import tourguide.tourguide.ToolTip;
import tourguide.tourguide.TourGuide;

/**
 * Created by developer on 1/7/17.
 */

public class GroundPickerActivity extends AppCompatActivity implements ItemClickInterface, DialogInterface {

    private RecyclerView
            list_view;
    private android.support.design.widget.FloatingActionButton add;
    GroundListAdapter adapter;
    @Inject
    Realm realm;
    TextView selected_teams;
    String pickerFor;
    private TourGuide tourGuide;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.team_list_view);

//        if (getIntent() != null && getIntent().getStringExtra("for") != null)
//            pickerFor = getIntent().getStringExtra("for");
        ((MyApplication) getApplication()).getComponent().inject(this);
        list_view = (RecyclerView) findViewById(R.id.list_view);
        add = (android.support.design.widget.FloatingActionButton) findViewById(R.id.add);

        selected_teams = (TextView) findViewById(R.id.selected_teams);
        selected_teams.setSelected(true);
        adapter = new GroundListAdapter(this, this, realm.where(Ground.class).findAll());
        list_view.setAdapter(adapter);
        list_view.setLayoutManager(new LinearLayoutManager(this));
        add.setVisibility(View.VISIBLE);
        if (adapter.getItemCount() == 0) {
            ToolTip toolTip = new ToolTip()

                    .setDescription(getString(R.string.tour_add_ground))
                    .setTextColor(Color.parseColor("#bdc3c7"))
                    .setBackgroundColor(Color.parseColor("#e74c3c"))
                    .setShadow(true)
                    .setGravity(Gravity.TOP | Gravity.LEFT);


            tourGuide = TourGuide.init(GroundPickerActivity.this).with(TourGuide.Technique.Click)
                    .setPointer(new Pointer())
                    .setToolTip(toolTip)
                    .setOverlay(new Overlay())
                    .playOn(add);
        }
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tourGuide != null)
                    tourGuide.cleanUp();
                Intent i = new Intent(GroundPickerActivity.this, MainFragmentActivity.class);
                i.putExtra("fragmentToLoad", CommanData.NEW_GROUND);
                startActivityForResult(i, 0);

            }
        });
    }

    @Override
    public void itemPicked(int id, String message) {
        Bundle conData = new Bundle();
        conData.putInt("id", id);
        //conData.putString("for", pickerFor);
        Intent intent = new Intent();
        intent.putExtras(conData);
        setResult(RESULT_OK, intent);
        finish();
    }


    public void showNewTeamDialog(int type, DialogInterface dialogInterface, int teamID) {

        // DialogFragment.show() will take care of adding the fragment
        // in a transaction.  We also want to remove any currently showing
        // dialog, so make our own transaction and take care of that here.

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Fragment prev = getSupportFragmentManager().findFragmentByTag("dialog");
        if (prev != null) {
            ft.remove(prev);
        }

        // Create and show the dialog.
        DialogFragment newFragment;
        if (type == 0)
            newFragment = NewTeamDialog.newInstance(0);
        else
            newFragment = NewPlayerDialog.newInstance(teamID);

        newFragment.show(ft, "dialog");


    }

    @Override
    protected void onStop() {
        if (tourGuide != null)
            tourGuide.cleanUp();

        super.onStop();
    }

    @Override
    public void onSuccess(String result, boolean success) {
        adapter = new GroundListAdapter(this, this, realm.where(Ground.class).findAll());
        list_view.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }
}
