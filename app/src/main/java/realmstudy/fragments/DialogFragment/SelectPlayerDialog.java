package realmstudy.fragments.DialogFragment;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.AppCompatButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import io.realm.RealmList;
import realmstudy.MainFragmentActivity;
import realmstudy.data.CommanData;
import realmstudy.data.RealmObjectData.BatingProfile;
import realmstudy.data.RealmObjectData.BowlingProfile;
import realmstudy.data.RealmObjectData.MatchDetails;
import realmstudy.databaseFunctions.RealmDB;
import realmstudy.R;
import realmstudy.data.RealmObjectData.Player;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * This dialog creates/add existing player to the current match and set its recent batting/bowling profile
 */
public class SelectPlayerDialog extends DialogFragment {
    private static final int PICK_CONTACT = 420;
    int mNum;
    private boolean ishomeTeam;
    private boolean toAddForBattingTeam;

    Realm realm;
    private MatchDetails matchDetails;
    private String title_txt;
    private Player current_bowler;
    private int assignTo;
    Spinner bat_style, bowl_style;
    static SelectPlayerDialog f;
    int teamID;
    private LinearLayout list_player_dialog_title_lay;
    private LinearLayout new_player_dialog_lay;
    private ImageView new_player_arrow;
    private LinearLayout new_player_dialog_title_lay;
    private ImageView list_arrow;

    public static SelectPlayerDialog newInstance(int match_id, boolean ishomeTeam, int current_bowler_id, String title, int assignTo) {
        if (f != null)
            f.dismiss();
        f = new SelectPlayerDialog();

        // Supply input as an argument.
        Bundle args = new Bundle();
        args.putInt("match_id", match_id);
        args.putBoolean("ishomeTeam", ishomeTeam);
        args.putInt("current_bowler_id", current_bowler_id);
        args.putInt("assignTo", assignTo);
        args.putString("title_txt", title);
        f.setArguments(args);

        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.out.println("_________SHH3");
        int match_id = getArguments().getInt("match_id");
        ishomeTeam = getArguments().getBoolean("ishomeTeam");
        title_txt = getArguments().getString("title_txt");
        int cb = getArguments().getInt("current_bowler_id");
        assignTo = getArguments().getInt("assignTo");
        Realm.init(getActivity());
        RealmConfiguration config = new RealmConfiguration.Builder()
                .build();
        realm = Realm.getInstance(config);
        matchDetails = RealmDB.getMatchById(getActivity(), realm, match_id);
        current_bowler = RealmDB.getPlayer(realm, cb);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.select_player, container, false);
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        System.out.println("_________SHH2");
        selectPlayerDialog(v, realm, title_txt);
        setCancelable(false);
        return v;
    }

    private void pickFromContacts() {
        Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
        startActivityForResult(intent, PICK_CONTACT);
    }

//    int addPlayerToMatch(String name, String ph_no) {
//        Player dummy = null;
//        boolean playerExtra = true;
//        System.out.println("_____________" + matchDetails.getTotalPlayers() + "___" + matchDetails.getHomeTeam().totalPlayers());
//        if (ishomeTeam)
//            playerExtra = matchDetails.getTotalPlayers() > matchDetails.getHomeTeam().totalPlayers();
//        else
//            playerExtra = matchDetails.getTotalPlayers() > matchDetails.getAwayTeam().totalPlayers();
//        if (playerExtra) {
//            BatingProfile bf = RealmDB.createBattingProfile( realm);
//            dummy = RealmDB.AddPlayer(getActivity(), realm, name, ph_no);
//            realm.beginTransaction();
//            if (ishomeTeam)
//                matchDetails.addHomePlayer(dummy);
//            else
//                matchDetails.addAwayPlayer(dummy);
//            bf.setCurrentStatus(CommanData.StatusBatting);
//            dummy.setRecentBatingProfile(bf);
//            realm.commitTransaction();
//        } else {
//            Toast.makeText(getActivity(), "Already added", Toast.LENGTH_SHORT).show();
//        }
//        if (getDialog() != null)
//            dismiss();
//        dialogInterface.onSuccess("hii", true);
//        //  updateUI();
//        if (dummy != null)
//            return dummy.getpID();
//        else
//            return -1;
//    }

    private void selectPlayerDialog(View selectPlayerDialog, final Realm realm, final String title_txt) {
        TextView
                title, submit_new_player, submit_from_db;
        final LinearLayout database_lay;
        final Spinner player_db_spinner;
        final EditText name;
        final EditText ph_no;
        RealmList<Player> home_team_players = null;
        System.out.println("_________SHH1");

        if ((matchDetails.isHomeTeamBatting() && ishomeTeam) || (!matchDetails.isHomeTeamBatting() && !ishomeTeam)) {
            toAddForBattingTeam = true;
        }
        ArrayList<Player> otherPlayers = null;
//        ArrayList<Player> otherPlayer = null;
//        if (otherPlayers != null)
//            otherPlayer = new ArrayList<>(otherPlayers.subList(0, otherPlayers.size()));
//        home_team_players = realm.where(MatchDetails.class).equalTo("match_id", matchDetails.getMatch_id()).findFirst().getHomeTeamPlayers();
//
//        RealmList<Player> away_team_players = realm.where(MatchDetails.class).equalTo("match_id", matchDetails.getMatch_id()).findFirst().getAwayTeamPlayers();
//
////

        if (ishomeTeam) {
            //  players = realm.where(MatchDetails.class).equalTo("match_id", matchDetails.getMatch_id()).findFirst().getHomeTeamPlayers();
            otherPlayers = new ArrayList<>(realm.where(Player.class).equalTo("teamID", matchDetails.getHomeTeam().team_id).findAll());
            //otherPlayers = RealmDB.getPlayerNotInHomeTeam(getActivity(), realm, matchDetails);
        } else {
            otherPlayers = new ArrayList<>(realm.where(Player.class).equalTo("teamID", matchDetails.getAwayTeam().team_id).findAll());
            // otherPlayers = RealmDB.getPlayerNotInAwayTeam(getActivity(), realm, matchDetails);
            //   players = realm.where(MatchDetails.class).equalTo("match_id", matchDetails.getMatch_id()).findFirst().getAwayTeamPlayers();
        }
        //   System.out.println("______________" + home_team_players.size() + "__" + away_team_players.size() + "___" + otherPlayer.size() + "___" + otherPlayers.size());
        ArrayAdapter<Player> adapter;
        adapter = new ArrayAdapter<>(
                getActivity(), R.layout.player_spinner_item, otherPlayers);

// Set other dialog properties


        title = (TextView) selectPlayerDialog.findViewById(R.id.title);
        database_lay = (LinearLayout) selectPlayerDialog.findViewById(R.id.database_lay);
        player_db_spinner = (Spinner) selectPlayerDialog.findViewById(R.id.player_db_spinner);
        name = (EditText) selectPlayerDialog.findViewById(R.id.name);
        ph_no = (EditText) selectPlayerDialog.findViewById(R.id.time);
        bat_style = (Spinner) selectPlayerDialog.findViewById(R.id.bat_style);
        bowl_style = (Spinner) selectPlayerDialog.findViewById(R.id.bowl_style);
        submit_new_player = (AppCompatButton) selectPlayerDialog.findViewById(R.id.submit_new_player);
        submit_from_db = (AppCompatButton) selectPlayerDialog.findViewById(R.id.submit_from_db);
        TextView from_contacts = (TextView) selectPlayerDialog.findViewById(R.id.from_contacts);

        list_player_dialog_title_lay = (LinearLayout) selectPlayerDialog.findViewById(R.id.list_player_dialog_title_lay);
        new_player_dialog_lay = (LinearLayout) selectPlayerDialog.findViewById(R.id.new_player_dialog_lay);
        new_player_arrow = (ImageView) selectPlayerDialog.findViewById(R.id.new_player_arrow);
        new_player_dialog_title_lay = (LinearLayout) selectPlayerDialog.findViewById(R.id.new_player_dialog_title_lay);
        list_arrow = (ImageView) selectPlayerDialog.findViewById(R.id.list_arrow);
        list_player_dialog_title_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!database_lay.isShown()) {
                    database_lay.setVisibility(View.VISIBLE);
                    list_arrow.setImageResource(R.drawable.down_arrow);
                    new_player_arrow.setImageResource(R.drawable.up_arrow);
                    new_player_dialog_lay.setVisibility(View.GONE);
                } else {
                    database_lay.setVisibility(View.GONE);
                    list_arrow.setImageResource(R.drawable.up_arrow);
                    new_player_arrow.setImageResource(R.drawable.down_arrow);
                    new_player_dialog_lay.setVisibility(View.VISIBLE);
                }
            }
        });
        new_player_dialog_title_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (new_player_dialog_lay.isShown()) {
                    database_lay.setVisibility(View.VISIBLE);
                    list_arrow.setImageResource(R.drawable.down_arrow);
                    new_player_arrow.setImageResource(R.drawable.up_arrow);
                    new_player_dialog_lay.setVisibility(View.GONE);
                } else {
                    database_lay.setVisibility(View.GONE);
                    list_arrow.setImageResource(R.drawable.up_arrow);
                    new_player_arrow.setImageResource(R.drawable.down_arrow);
                    new_player_dialog_lay.setVisibility(View.VISIBLE);
                }
            }
        });
        //set value
        title.setText(title_txt);
        player_db_spinner.setAdapter(adapter);
        if (otherPlayers.size() <= 0)
            database_lay.setVisibility(View.GONE);
        from_contacts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED
                        ) {

                    ((MainFragmentActivity) getActivity()).startInstalledAppDetailsActivity(getActivity());
                } else {
                    //dismiss();
                    pickFromContacts();
                }
            }
        });

        submit_new_player.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {


                if (!name.getText().toString().isEmpty()) {
                    // addPlayerToMatch(name.getText().toString(), ph_no.getText().toString());

                    int pID = RealmDB.addNewPlayerToMatch(getActivity(), realm, name.getText().toString(), "", bat_style.getSelectedItemPosition(), bowl_style.getSelectedItemPosition(), 0, matchDetails, ishomeTeam, ishomeTeam ? matchDetails.getHomeTeam().team_id : matchDetails.getAwayTeam().team_id);
                    if (pID != -1 && getDialog() != null) {
                        dismiss();
                        ((MainFragmentActivity) getActivity()).messageFromDialog(CommanData.DIALOG_SELECT_PLAYER, true, String.valueOf(pID), "success", assignTo);
                    }
                }
            }
        });
        submit_from_db.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Player bb;
                bb = (Player) player_db_spinner.getSelectedItem();
                Player dummy;
                boolean ss = isEligible(bb.getpID(), ishomeTeam);
                System.out.println("_________________dd5" + matchDetails.getBattingTeamPlayer());
                System.out.println("checkkkk" + ss);

                if (ss) {
                    Player p;
                    dummy = RealmDB.getPlayer(realm, bb.getpID());
                    BatingProfile bf = RealmDB.getBattingProfile(realm, dummy.getpID(), matchDetails.getMatch_id());
                    if (bf == null)
                        bf = RealmDB.createBattingProfile(realm, dummy.getpID(), matchDetails.getMatch_id());
                    BowlingProfile bwf = RealmDB.getBowlingProfile(realm, dummy.getpID(), matchDetails.getMatch_id());
                    if (bwf == null)
                        bwf = RealmDB.createBowlingProfile(realm, dummy.getpID(), matchDetails.getMatch_id());
                    realm.beginTransaction();
//                    dummy.setRecentBatingProfile(bf);
//                    dummy.setRecentBowlingProfile(bwf);
                    if (ishomeTeam)
                        p = matchDetails.addHomePlayer(dummy);
                    else
                        p = matchDetails.addAwayPlayer(dummy);
                    realm.commitTransaction();
                    System.out.println("_________________dd5.1" + p);
                    if (p == null)
                        ((MainFragmentActivity) getActivity()).messageFromDialog(CommanData.DIALOG_SELECT_PLAYER, false, String.valueOf(dummy.getpID()), "Success", assignTo);
                    else
                        ((MainFragmentActivity) getActivity()).messageFromDialog(CommanData.DIALOG_SELECT_PLAYER, true, String.valueOf(dummy.getpID()), "Player invalid", assignTo);
                    // dialogInterface.onSuccess("hii", true);
                    dismiss();

                }


            }
        });


    }

    boolean isEligible(int id, boolean assignToPlayer) {
        boolean eligible = true;
        if (matchDetails.getHomeTeam() != null) {
            System.out.println("_________________ss" + matchDetails.getHomeTeam().name);
            System.out.println("_________________dd" + matchDetails.getBattingTeamPlayer() + "000");
            RealmList<Player> battingTeamPlayers = matchDetails.getBattingTeamPlayer();
            RealmList<Player> bowlingTeamPlayers = matchDetails.getBowlingTeamPlayer();


            System.out.println("_________________dd1__" + matchDetails.getBattingTeamPlayer() + "____" + matchDetails.getBowlingTeamPlayer() + "__" + ishomeTeam + "___" + matchDetails.isHomeTeamBatting());
            if ((matchDetails.isHomeTeamBatting() && ishomeTeam) || (!matchDetails.isHomeTeamBatting() && !ishomeTeam)) {
                System.out.println("_________________dd2" + bowlingTeamPlayers);
                if (battingTeamPlayers != null && !battingTeamPlayers.equals("")) {

                    // String battingTeamPlayer[] = matchDetails.getBattingTeamPlayer().split(",");

                    for (int i = 0; i < battingTeamPlayers.size(); i++) {
                        int batStatus = RealmDB.getBattingProfile(realm, id, matchDetails.getMatch_id()).getCurrentStatus();
                        System.out.println("_________________dd2" + batStatus);
                        if (id == battingTeamPlayers.get(i).getpID() && (batStatus == CommanData.StatusOut || batStatus == CommanData.StatusBatting)) {
                            eligible = false;
                            Toast.makeText(getActivity(), getString(R.string.player_already_batted), Toast.LENGTH_SHORT).show();
                            //Toast.makeText(getActivity(), "Player already batted/batting", Toast.LENGTH_SHORT).show();
                        }
                    }
                    if (bowlingTeamPlayers != null && !bowlingTeamPlayers.equals("")) {
                        //   String bowlingTeamPlayer[] = matchDetails.getBowlingTeamPlayer().split(",");
                        for (int i = 0; i < bowlingTeamPlayers.size(); i++) {
                            if (id == bowlingTeamPlayers.get(i).getpID()) {
                                eligible = false;
                                Toast.makeText(getActivity(), "Player in oponent team", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }
            } else {
                if (battingTeamPlayers != null && !battingTeamPlayers.equals("")) {
                    System.out.println("_________________dd3" + matchDetails.getBattingTeamPlayer());
                    // String battingTeamPlayer[] = matchDetails.getBattingTeamPlayer().split(",");
                    for (int i = 0; i < battingTeamPlayers.size(); i++) {
                        if (id == battingTeamPlayers.get(i).getpID()) {
                            eligible = false;
                            Toast.makeText(getActivity(), "Player in oponent team___away", Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                if (bowlingTeamPlayers != null && !bowlingTeamPlayers.equals("")) {
                    //   String bowlingTeamPlayer[] = matchDetails.getBowlingTeamPlayer().split(",");

                    for (int i = 0; i < bowlingTeamPlayers.size(); i++) {
                        if (current_bowler != null && id == current_bowler.getpID()) {
                            eligible = false;
                            Toast.makeText(getActivity(), getString(R.string.no_spell), Toast.LENGTH_SHORT).show();
                        } else if (RealmDB.getBowlingProfile(realm, id, matchDetails.getMatch_id()).getCurrentBowlerStatus() == CommanData.StatusBowled) {
                            eligible = false;
                            Toast.makeText(getActivity(), getString(R.string.bowled_limited), Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                System.out.println("_________________dd0d" + bowlingTeamPlayers);
//                if (!bowlingTeamPlayers.isEmpty()) {
//                    System.out.println("_________________dd4" + matchDetails.getBowlingTeamPlayer());
//                    String bowlingTeamPlayer[] = matchDetails.getBowlingTeamPlayer().split(",");
////                    for (int i = 0; i < bowlingTeamPlayer.length; i++) {
////                        if (id == Integer.parseInt(bowlingTeamPlayer[i]) && id == current_bowler.getpID()) {
////                            eligible = false;
////                            Toast.makeText(getActivity(), "Player can't bowl continous spell", Toast.LENGTH_SHORT).show();
////                        }
////                    }
//                }
            }
        } else {
            System.out.println("_____________elsee");
        }


        return eligible;
    }


    @Override
    public void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);
        String cNumber = "";
        String name = "";
        switch (reqCode) {
            case (PICK_CONTACT):
                if (resultCode == Activity.RESULT_OK) {

                    Uri contactData = data.getData();
                    Cursor c = getActivity().getContentResolver().query(contactData, null, null, null, null);
                    if (c.moveToFirst()) {


                        String id = c.getString(c.getColumnIndexOrThrow(ContactsContract.Contacts._ID));

                        String hasPhone = c.getString(c.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));

                        if (hasPhone.equalsIgnoreCase("1")) {
                            Cursor phones = getActivity().getContentResolver().query(
                                    ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + id,
                                    null, null);
                            phones.moveToFirst();
                            cNumber = phones.getString(phones.getColumnIndex("data1"));
                            System.out.println("number is:" + cNumber);
                        }
                        name = c.getString(c.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));


                    }

                    if (!name.trim().isEmpty()) {
//                        realm.beginTransaction();
//                        Player playerObj = realm.createObject(Player.class);
//                        playerObj.setpID(realm.where(Player.class).findAll().size());
//                        playerObj.setName(name);
//                        playerObj.setPh_no(cNumber);
//                        realm.commitTransaction();
                        // RealmDB.AddPlayer(getActivity(), realm, name, cNumber);

                        if (!name.isEmpty()) {

                            int pID = RealmDB.addNewPlayerToMatch(getActivity(), realm, name, cNumber, bat_style.getSelectedItemPosition(), bowl_style.getSelectedItemPosition(), 0, matchDetails, ishomeTeam, ishomeTeam ? matchDetails.getHomeTeam().team_id : matchDetails.getAwayTeam().team_id);
                            if (getDialog() != null && pID != -1 ? true : false)
                                dismiss();

                            ((MainFragmentActivity) getActivity()).messageFromDialog(CommanData.DIALOG_SELECT_PLAYER, pID != -1 ? true : false, String.valueOf(pID), "success", assignTo);
//                            if (assignToPlayer == 5) {
//                                ArrayList<Player> bowlingTeamPlayers = getBowlingTeamPlayer();
//                                ArrayAdapter<Player> bowling_team_player_adapter = new ArrayAdapter<>(
//                                        getActivity(), realmstudy.R.layout.player_spinner_item, bowlingTeamPlayers);
//                                if (caught_by != null && run_out_by != null) {
//                                    System.out.println();
//                                    caught_by.setAdapter(bowling_team_player_adapter);
//                                    run_out_by.setAdapter(bowling_team_player_adapter);
//                                    int ids = 0;
//                                    for (int i = 0; i < bowlingTeamPlayers.size(); i++) {
//                                        if (bowlingTeamPlayers.get(i).getpID() == id)
//                                            ids = i;
//                                    }
//                                    caught_by.setSelection(ids);
//                                    run_out_by.setSelection(ids);
//                                }
//                            }
                        }


                    }
                    break;
                }
        }
    }








   /* int newPlayerAdded(String name, String ph_no, Dialog dialog) {
        Player dummy = null;
        if (isEligible(ph_no,ishomeTeam)) {

            dummy = RealmDB.AddPlayer(getActivity(), realm, name, ph_no);
            BatingProfile bf = RealmDB.createBattingProfile( realm, dummy.getpID(), matchDetails.getMatch_id());
            BowlingProfile bwf = RealmDB.createBowlingProfile( realm, dummy.getpID(), matchDetails.getMatch_id());
            realm.beginTransaction();
            if (matchDetails.isHomeTeamBatting())
                matchDetails.addHomePlayer(dummy);
            else
                matchDetails.addAwayPlayer(dummy);
            bf.setCurrentStatus(CommanData.StatusBatting);
            // striker.setRecentBatingProfile(bf);
            realm.commitTransaction();
        }


        if (dummy != null)
            return dummy.getpID();
        else
            return -1;
    }
*/
}