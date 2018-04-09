package realmstudy.tournament;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import realmstudy.R;
import realmstudy.extras.Utils;
import realmstudy.view.TextView;


/**
 * Created by developer on 12/4/17.
 */
public class InfoFragment extends Fragment {

//    private TextView
//            squad_home_team, squad_away_team, info_match_id, info_series, info_date, info_time, info_toss, info_venue, info_umpires, info_third_umpire, info_refree;
   // MatchShortSummaryData md;
  //  LinearLayout series_lay, umpire_lay, third_umpire_lay, refree_lay;
    private TournamentData aboutData;
    private TextView aboutText;
//    @Inject
//    Realm realm;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        String data = getActivity().getIntent().getStringExtra("about");
        if(data!=null) {
            System.out.println("nagaa"+data);
            aboutData = Utils.fromJson(data, TournamentData.class);
            System.out.println("nagaa"+aboutData.getAbout());
        }
        aboutText=   ((realmstudy.view.TextView)getActivity().findViewById(R.id.aboutText));
        aboutText.setText(Html.fromHtml(aboutData.getAbout()));
//        ((MyApplication) getActivity().getApplication()).getComponent().inject(this);
//       md= CommanData.fromJson(id, MatchShortSummaryData.class);
//        System.out.println("____"+md+"__"+id);
//        if (md != null) {
//            squad_home_team.setText(md.getHomeTeam());
//            squad_away_team.setText(md.getAwayTeam());
//            info_match_id.setText(String.valueOf(md.getQuotes()));
//            info_series.setText("-");
//            info_date.setText(CommanData.getDate(md.getTime()));
//            info_time.setText(CommanData.getTime(md.getTime()));
//            info_toss.setText(md.getToss());
//            info_venue.setText(md.getLocation());
//            refree_lay.setVisibility(View.GONE);
//            third_umpire_lay.setVisibility(View.GONE);
//            umpire_lay.setVisibility(View.GONE);
//            series_lay.setVisibility(View.GONE);
//            //  squad_home_team.setText(md.getHomeTeam().name);
//        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.tournament_info_layout, container, false);
//        squad_home_team = (TextView) v.findViewById(R.id.squad_home_team);
//        squad_away_team = (TextView) v.findViewById(R.id.squad_away_team);
//        info_match_id = (TextView) v.findViewById(R.id.info_match_id);
//        info_series = (TextView) v.findViewById(R.id.info_series);
//        info_date = (TextView) v.findViewById(R.id.info_date);
//        info_time = (TextView) v.findViewById(R.id.info_time);
//        info_toss = (TextView) v.findViewById(R.id.info_toss);
//        info_venue = (TextView) v.findViewById(R.id.info_venue);
//        info_umpires = (TextView) v.findViewById(R.id.info_umpires);
//        info_third_umpire = (TextView) v.findViewById(R.id.info_third_umpire);
//        info_refree = (TextView) v.findViewById(R.id.info_refree);
//        series_lay = (LinearLayout) v.findViewById(R.id.series_lay);
//        umpire_lay = (LinearLayout) v.findViewById(R.id.umpire_lay);
//        third_umpire_lay = (LinearLayout) v.findViewById(R.id.third_umpire_lay);
//        refree_lay = (LinearLayout) v.findViewById(R.id.refree_lay);

        return v;
    }

//    public void setData(List<OverAdapterData> overAdapterData, ScoreBoardData scoreBoardData) {
//
//    }
}
