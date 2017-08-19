package realmstudy;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Locale;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import realmstudy.data.CommanData;
import realmstudy.data.RealmObjectData.Player;
import realmstudy.data.SessionSave;
import realmstudy.fragments.AddNewGround;
import realmstudy.fragments.DialogFragment.NewPlayerDialog;
import realmstudy.fragments.DialogFragment.NewTeamDialog;
import realmstudy.fragments.DialogFragment.OutDialogFragment;
import realmstudy.fragments.DialogFragment.SelectMultiPlayerDialog;
import realmstudy.fragments.DialogFragment.SelectPlayerDialog;
import realmstudy.fragments.DialogFragment.SelectTeamDialog;
import realmstudy.fragments.EditPlayerProfile;
import realmstudy.fragments.GroundListFragment;
import realmstudy.fragments.MatchInfo;
import realmstudy.fragments.MenuActivity;
import realmstudy.fragments.PlayerListFragment;
import realmstudy.fragments.ScheduleNewGame;
import realmstudy.fragments.TeamListFragment;
import realmstudy.fragments.regLogin.SocialLoginCustom;
import realmstudy.interfaces.DialogInterface;
import realmstudy.interfaces.ItemClickInterface;
import realmstudy.interfaces.MsgFromDialog;
import realmstudy.interfaces.MsgToFragment;
import realmstudy.matchList.MatchListMainFragment;

/**
 * Created by developer on 26/12/16.
 */
public class MainFragmentActivity extends AppCompatActivity implements
        NavigationView.OnNavigationItemSelectedListener, MsgToFragment, MsgFromDialog,
        Toolbar.OnMenuItemClickListener, ItemClickInterface {
    public static final int REQUEST_SIGN_UP = 420;
    private FrameLayout content_frame, shadow;
    private android.support.v4.widget.DrawerLayout drawer_layout;
    private android.support.v7.widget.Toolbar tool_bar;
    private ImageView toolbar_logo;
    private ImageButton right_icon;
    private TextView cancel_b;
    private android.support.v7.widget.SwitchCompat switch_right_icon;
    private FrameLayout mainFrag;
    public Realm realm;
    private int dialogType;
    public static DialogInterface dialogInterface;
    private DrawerLayout drawer;
    TextView nav_name, nav_email, nav_signin;
    private ImageView nav_image;
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private ProgressDialog mProgressDialog;
    private LinearLayout naviHeader;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //  setLocale();
        setContentView(realmstudy.R.layout.home_fragment);
        Realm.init(this);
        mAuth = FirebaseAuth.getInstance();
        RealmConfiguration config = new RealmConfiguration.Builder()
                .build();


        final Bundle bundle = getIntent().getExtras();
        if (bundle != null)
            if (bundle.getString("fragmentToLoad") != null)
                setFragment(bundle.getString("fragmentToLoad"));

        realm = Realm.getInstance(config);
//        realm.beginTransaction();
//        realm.delete(InningsData.class);
//        realm.commitTransaction();
        content_frame = (FrameLayout) findViewById(realmstudy.R.id.content_frame);
        //   drawer_layout = (android.support.v4.widget.DrawerLayout) findViewById(realmstudy.R.id.drawer_layout);
//        tool_bar = (android.support.v7.widget.Toolbar) findViewById(realmstudy.R.id.tool_bar);
//        tool_bar.inflateMenu(R.menu.main_menu);
//        toolbar_logo = (ImageView) findViewById(realmstudy.R.id.toolbar_logo);


        tool_bar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(tool_bar);


        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        setNaviHome();
        tool_bar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fromFragment = getSupportFragmentManager().findFragmentById(R.id.mainFrag);
                System.out.println("helloo" + (fromFragment instanceof PlayerListFragment));
                if (fromFragment != null)
                    if (fromFragment instanceof PlayerListFragment || fromFragment instanceof GroundListFragment
                            || fromFragment instanceof MainActivity || fromFragment instanceof MatchDetailActivity
                            ) {
                        onBackPressed();
                    } else {

                        drawer.openDrawer(Gravity.LEFT);

                    }
            }
        });
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        nav_image = (ImageView) navigationView.getHeaderView(0).findViewById(R.id.imageView);
        naviHeader = (LinearLayout) navigationView.getHeaderView(0).findViewById(R.id.naviHeader);
        nav_name = (TextView) navigationView.getHeaderView(0).findViewById(R.id.nav_name);
        nav_email = (TextView) navigationView.getHeaderView(0).findViewById(R.id.nav_email);
        nav_signin = (TextView) navigationView.getHeaderView(0).findViewById(R.id.nav_signin);
        setUserNav();
        // SessionSave.getSession(CommanData.PROFILE_IMAGE, SocialLoginCustom.this)
        navigationView.getHeaderView(0).findViewById(R.id.nav_signin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //   getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.mainFrag, new Signup()).commit();
                if (mAuth.getCurrentUser() == null)
                    startActivityForResult(new Intent(MainFragmentActivity.this, SocialLoginCustom.class), REQUEST_SIGN_UP);
                else {

                    mAuth.addAuthStateListener(new FirebaseAuth.AuthStateListener() {
                        @Override
                        public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                            if (firebaseAuth != null) {
                                if (firebaseAuth.getCurrentUser() == null)
                                    clearSession();
                            }
                        }
                    });
                    showProgressDialog(getString(R.string.processing));
                    mAuth.signOut();
                    hideProgressDialog();

//                    clearSession();
                }
            }
        });

        naviHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment f=new EditPlayerProfile();
                Bundle b=new Bundle();
                b.putString("type","1");
                f.setArguments(b);
                getSupportFragmentManager().beginTransaction()
                        .add(realmstudy.R.id.mainFrag, f)
                        .commit();
            }
        });
        right_icon = (ImageButton) findViewById(realmstudy.R.id.right_icon);
        cancel_b = (TextView) findViewById(realmstudy.R.id.cancel_b);
        switch_right_icon = (android.support.v7.widget.SwitchCompat) findViewById(realmstudy.R.id.switch_right_icon);
        mainFrag = (FrameLayout) findViewById(realmstudy.R.id.mainFrag);
        shadow = (FrameLayout) findViewById(realmstudy.R.id.shadow);
        //  left_drawer = (LinearLayout) findViewById(realmstudy.R.id.left_drawer);
    }

    public void showProgressDialog(String msg) {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage(msg);
            mProgressDialog.setIndeterminate(true);
        }

        mProgressDialog.show();
    }

    public void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    private void setUserNav() {
        user = mAuth.getCurrentUser();
        if (user != null) {
            if (user.getPhotoUrl() != null && !user.getPhotoUrl().toString().trim().equals(""))
                Picasso.with(this).load(user.getPhotoUrl()).error((ContextCompat.getDrawable(this, R.drawable.no_image))).into(nav_image);
            nav_name.setVisibility(View.VISIBLE);
            System.out.println("disppppp" + user.getDisplayName() + user.getUid());
            nav_name.setText(user.getDisplayName());
            nav_email.setText(user.getEmail());
            nav_signin.setText(getString(R.string.sign_out));
            nav_signin.setVisibility(View.VISIBLE);
        } else {
            clearSession();
        }
    }

    private void clearSession() {

        Picasso.with(this).load(R.drawable.no_image).into(nav_image);
        nav_name.setText(getString(R.string.guest_user));
        nav_signin.setText(getString(R.string.sign_in));
    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem menuItem) {
//        System.out.println("happening:" + menuItem.getItemId() + "__" + android.R.id.home);
//        switch (menuItem.getItemId()) {
//            case android.R.id.home:
//                Toast.makeText(MainFragmentActivity.this, "clicked", Toast.LENGTH_SHORT).show();
//        }
//        return (super.onOptionsItemSelected(menuItem));
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);


        return true;
        //  return super.onCreateOptionsMenu(menu);
    }


    public void setLocale() {
        Locale locale = new Locale("en");
        Locale.setDefault(locale);
        Configuration config = getBaseContext().getResources().getConfiguration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config,
                getBaseContext().getResources().getDisplayMetrics());
    }

    private void setFragment(String id) {
        switch (id) {
            case CommanData.AddNewTeam:
                getSupportFragmentManager().beginTransaction()
                        .add(realmstudy.R.id.mainFrag, new MatchListMainFragment())
                        .commit();
                break;
            case CommanData.NEW_GROUND:
                Fragment f = new AddNewGround();
                Bundle b = new Bundle();
                b.putInt("type", 1);
                f.setArguments(b);
                getSupportFragmentManager().beginTransaction()
                        .add(realmstudy.R.id.mainFrag, f)
                        .commit();
                break;
        }
    }


    public void homePage_title(String title) {
        setSupportActionBar(tool_bar);
        tool_bar.setTitle("ILC");
        tool_bar.hideOverflowMenu();


    }

    @Override
    public void onBackPressed() {

        Fragment fromFragment = getSupportFragmentManager().findFragmentById(R.id.mainFrag);
        if (fromFragment != null)
            if (fromFragment instanceof MatchListMainFragment) {
                finish();
            } else if (fromFragment instanceof MainActivity || fromFragment instanceof MatchInfo
                    || fromFragment instanceof ScheduleNewGame
                    || fromFragment instanceof TeamListFragment
                    || fromFragment instanceof MatchDetailActivity) {
                getSupportFragmentManager().beginTransaction()
                        .addToBackStack(null)
                        .add(R.id.mainFrag, new MatchListMainFragment()).commit();
            } else {
                super.onBackPressed();

            }

    }

    public void onClick(View v) {
        System.out.println("________dottt1");
        Fragment f = getSupportFragmentManager().findFragmentById(realmstudy.R.id.mainFrag);
        if (f instanceof MainActivity) {
            System.out.println("________dottt2");
            ((MainActivity) f).onClick(v);
        } else if (f instanceof MenuActivity)
            ((MenuActivity) f).onClick(v);

    }

    @Override
    public boolean onSupportNavigateUp() {
        System.out.println("happening");
        onBackPressed();
        return true;
    }

    public Realm getRealm() {
        return realm;
    }

    /**
     * create and show dialog for add team and players
     *
     * @param type 0-->TeamDialog
     *             1-->PlayerDialog
     */
    public void showNewTeamDialog(int type, DialogInterface dialogInterface, int teamID) {

        // DialogFragment.show() will take care of adding the fragment
        // in a transaction.  We also want to remove any currently showing
        // dialog, so make our own transaction and take care of that here.

        this.dialogInterface = dialogInterface;
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Fragment prev = getSupportFragmentManager().findFragmentByTag("dialog");
        System.out.println("_______DDDDDD__" + prev);
        if (prev != null) {
            System.out.println("_______DDDDDD__");
            ft.remove(prev);
            //  ((DialogFragment)prev).dismiss();
        }
        // ft.addToBackStack(null);

        // Create and show the dialog.
        DialogFragment newFragment;
        if (type == 0)
            newFragment = NewTeamDialog.newInstance(0);
        else
            newFragment = NewPlayerDialog.newInstance(teamID);

        newFragment.show(ft, "dialog");


    }


    public void showMultiTeamSelect(int match_id, boolean ishomeTeam, int current_bowler) {

        // DialogFragment.show() will take care of adding the fragment
        // in a transaction.  We also want to remove any currently showing
        // dialog, so make our own transaction and take care of that here.

        this.dialogInterface = dialogInterface;
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Fragment prev = getSupportFragmentManager().findFragmentByTag("dialog");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);

        // Create and show the dialog.
        DialogFragment newFragment = SelectTeamDialog.newInstance(match_id, ishomeTeam);
        //   ((SelectMultiPlayerDialog) newFragment).setDialogInterface(dialogInterface, match_id, ishomeTeam, current_bowler);
        System.out.println("______________cccc");

        newFragment.show(ft, "dialog");


    }

    public void closePrevSelectPlayer() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Fragment prev = getSupportFragmentManager().findFragmentByTag("dialog");
        System.out.println("_________________prev" + prev);
        if (prev != null) {
            System.out.println("_________________prevrr" + prev);
            ((DialogFragment) prev).dismiss();
            ft.remove(prev);

        }
    }

    public void showSelectplayer(int match_id, boolean ishomeTeam, Player current_bowler, String title, int assignTo) {

        // DialogFragment.show() will take care of adding the fragment
        // in a transaction.  We also want to remove any currently showing
        // dialog, so make our own transaction and take care of that here.

        this.dialogInterface = dialogInterface;

        // ft.addToBackStack(null);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        closePrevSelectPlayer();
        int cb = -1;
        if (current_bowler != null)
            cb = current_bowler.getpID();
        // Create and show the dialog.
        DialogFragment newFragment = SelectPlayerDialog.newInstance(match_id, ishomeTeam, cb, title, assignTo);
        // ((SelectPlayerDialog) newFragment).setDialogInterface(dialogInterface, match_id, ishomeTeam, current_bowler,title);
//        if (type == 0)
//            newFragment = NewTeamDialog.newInstance();
//        else
//            newFragment = NewPlayerDialog.newInstance().setDialogInterface(dialogInterface);
        System.out.println("_________SHH");
        newFragment.show(ft, "dialog");


    }

    public void showOutDialog(int striker, int non_striker, int current_bowler, int matchDetails, float over) {

        // DialogFragment.show() will take care of adding the fragment
        // in a transaction.  We also want to remove any currently showing
        // dialog, so make our own transaction and take care of that here.

        this.dialogInterface = dialogInterface;
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Fragment prev = getSupportFragmentManager().findFragmentByTag("dialog");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);

        // Create and show the dialog.
        DialogFragment newFragment = OutDialogFragment.newInstance(striker, non_striker, current_bowler, matchDetails, over);
        //   ((SelectMultiPlayerDialog) newFragment).setDialogInterface(dialogInterface, match_id, ishomeTeam, current_bowler);


        newFragment.show(ft, "dialog");


    }

    public void showMultiPlayerSelect(int match_id, boolean ishomeTeam, int current_bowler) {

        // DialogFragment.show() will take care of adding the fragment
        // in a transaction.  We also want to remove any currently showing
        // dialog, so make our own transaction and take care of that here.

        this.dialogInterface = dialogInterface;
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Fragment prev = getSupportFragmentManager().findFragmentByTag("dialog");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);

        // Create and show the dialog.
        DialogFragment newFragment = SelectMultiPlayerDialog.newInstance(match_id, ishomeTeam);
        //   ((SelectMultiPlayerDialog) newFragment).setDialogInterface(dialogInterface, match_id, ishomeTeam, current_bowler);


        newFragment.show(ft, "dialog");


    }

    public static void startInstalledAppDetailsActivity(final Activity context) {
        if (context == null) {
            return;
        }
        final Intent i = new Intent();
        i.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        i.addCategory(Intent.CATEGORY_DEFAULT);
        i.setData(Uri.parse("package:" + context.getPackageName()));
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        i.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
        context.startActivity(i);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        System.out.println("CametoEditaa"+requestCode);
        Fragment ffs = getSupportFragmentManager().findFragmentById(R.id.mainFrag);
        if(ffs!=null){
            ffs.onActivityResult(requestCode,resultCode,data);
        }
        if (requestCode == REQUEST_SIGN_UP) {
            setUserNav();
        } else {
            Fragment ff = getSupportFragmentManager().findFragmentByTag("dialog");
            System.out.println("_____PP" + 1);
            if (ff != null)
                ff.onActivityResult(requestCode, resultCode, data);
            else if (getSupportFragmentManager().findFragmentById(R.id.mainFrag) != null)
                getSupportFragmentManager().findFragmentById(R.id.mainFrag).onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void msg(String s) {
        Fragment ff = getSupportFragmentManager().findFragmentById(realmstudy.R.id.mainFrag);
        if (ff instanceof MsgToFragment) {
            ((MsgToFragment) ff).msg(s);
        }
    }

    @Override
    public void messageFromDialog(int dialogType, boolean success, String data, String message, int assignTo) {
        Fragment ff = getSupportFragmentManager().findFragmentById(realmstudy.R.id.mainFrag);
        if (ff instanceof MsgFromDialog) {
            ((MsgFromDialog) ff).messageFromDialog(dialogType, success, data, message, assignTo);
        }
    }

    public void messageFromDialog(int dialogType, boolean success, String data, String message) {
        Fragment ff = getSupportFragmentManager().findFragmentById(realmstudy.R.id.mainFrag);
        if (ff instanceof MsgFromDialog) {
            ((MsgFromDialog) ff).messageFromDialog(dialogType, success, data, message);
        }
    }

    @Override
    public void messageFromDialog(int dialogType, boolean success, ArrayList<Integer> data, String message) {
        Fragment ff = getSupportFragmentManager().findFragmentById(realmstudy.R.id.mainFrag);
        if (ff instanceof MsgFromDialog) {
            ((MsgFromDialog) ff).messageFromDialog(dialogType, success, data, message);
        }
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {

        return false;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_saved_game) {
            getSupportFragmentManager().beginTransaction().replace(R.id.mainFrag, new MatchListMainFragment()).commit();
        } else if (id == R.id.nav_viewer) {
            MatchListMainFragment f = new MatchListMainFragment();
            Bundle b = new Bundle();
            b.putBoolean("is_online", true);
            f.setArguments(b);
            getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.mainFrag, f).commit();
        } else if (id == R.id.nav_schedule_game) {
            getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.mainFrag, new ScheduleNewGame()).commit();
        } else if (id == R.id.nav_add_player) {
            getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.mainFrag, new PlayerListFragment()).commit();
        } else if (id == R.id.nav_add_team) {
            getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.mainFrag, new TeamListFragment()).commit();
        } else if (id == R.id.nav_new_game) {
            getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.mainFrag, new MatchInfo()).commit();
        } else if (id == R.id.nav_add_ground) {

            getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.mainFrag, new GroundListFragment()).commit();
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    public void itemPicked(int id, String message) {
        Fragment f = getSupportFragmentManager().findFragmentById(R.id.mainFrag);
        if (f != null)
            if (f instanceof ItemClickInterface)
                ((ItemClickInterface) f).itemPicked(id, message);
    }

    public void setNaviHome() {
//        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
//                this, drawer, tool_bar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
//        drawer.addDrawerListener(toggle);
//        toggle.syncState();
        getSupportActionBar().setHomeButtonEnabled(true);
        tool_bar.setNavigationIcon(R.drawable.navi_home);
    }

    public void removeNaviHome() {
        // getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        tool_bar.setNavigationIcon(R.drawable.navi_back_white);
    }
}
