package realmstudy.di.component;

import javax.inject.Singleton;

import dagger.Component;
import realmstudy.MainFragmentActivity;
import realmstudy.adapter.SavedGameListAdapter;
import realmstudy.databaseFunctions.RealmDB;
import realmstudy.di.modules.StorageModule;
import realmstudy.fragments.InfoFragment;
import realmstudy.fragments.OversFragment;
import realmstudy.fragments.PlayerListFragment;
import realmstudy.fragments.ScheduleListFragment;
import realmstudy.fragments.ScheduleNewGame;
import realmstudy.fragments.ScorecardDetailFragment;
import realmstudy.fragments.TeamListFragment;
import realmstudy.fragments.TossFragment;


/**
 * Created by vivz on 11/09/15.
 */
@Singleton
@Component(modules = StorageModule.class)
public interface StorageComponent {
    void inject(MainFragmentActivity activity);
    void inject(TeamListFragment teamListFragment);
    void inject(ScheduleListFragment scheduleListFragment);
    void inject(PlayerListFragment playerListFragment);
    void inject(ScheduleNewGame scheduleNewGame);
    void inject(SavedGameListAdapter savedGameListAdapter);
    void inject(TossFragment tossFragment);
    void inject(InfoFragment infoFragment);
   void inject(OversFragment oversFragment);
    void inject(ScorecardDetailFragment scorecardDetailFragment);
}